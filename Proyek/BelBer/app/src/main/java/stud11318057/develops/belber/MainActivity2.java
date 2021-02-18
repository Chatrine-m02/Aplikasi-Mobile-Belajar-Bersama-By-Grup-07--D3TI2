package stud11318057.develops.belber;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;


public class MainActivity2 extends AppCompatActivity {
    MaterialEditText email, password;
    CheckBox loginstate;
    Button login, register;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        email = (MaterialEditText) findViewById(R.id.email);
        password = (MaterialEditText) findViewById(R.id.password);
        register = (Button) findViewById(R.id.register);
        login = (Button) findViewById(R.id.loggin);
        loginstate = findViewById(R.id.checkbox);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtEmail = email.getText().toString();
                String txtPassword = password.getText().toString();

                if (TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)) {
                    Toast.makeText(MainActivity2.this, "This field required", Toast.LENGTH_SHORT).show();
                } else {
                    login(txtEmail, txtPassword);
                }
            }
        });

        String loginStatus = sharedPreferences.getString(getResources().getString(R.string.prefLoginState), "");
        if (loginStatus.equals("loggedin")) {
            startActivity(new Intent(MainActivity2.this, MainActivity.class));

        }

    }

    public void login(final String email, final String password) {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity2.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Login");
        progressDialog.show();
        String uRL = "http://10.114.12.180:8080/Webservice/login.php";

        StringRequest request = new StringRequest(Request.Method.POST, uRL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("\r\n\r\n\r\nLoggin Success\r\n\r\n")) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity2.this, response, Toast.LENGTH_SHORT).show();

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (loginstate.isChecked()) {
                        editor.putString(getResources().getString(R.string.prefLoginState), "loggedin");
                    } else {
                        editor.putString(getResources().getString(R.string.prefLoginState), "loggedout");
                    }
                    editor.apply();
                    startActivity(new Intent(MainActivity2.this, MainActivity.class));
                    finish();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity2.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity2.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("email", email);
                param.put("password", password);

                return param;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(MainActivity2.this).addToRequeestQueue(request);
    }
}