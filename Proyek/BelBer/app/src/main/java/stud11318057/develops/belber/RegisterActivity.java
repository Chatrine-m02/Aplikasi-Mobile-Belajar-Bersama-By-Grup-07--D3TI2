package stud11318057.develops.belber;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    MaterialEditText userName, email, password, mobile;
    RadioGroup radioGroup;
    Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = (MaterialEditText) findViewById (R.id.username);
        email = (MaterialEditText) findViewById (R.id.email);
        password = (MaterialEditText) findViewById (R.id.password);
        mobile = (MaterialEditText) findViewById (R.id.mobile);
        radioGroup = (RadioGroup) findViewById (R.id.radiogp);
        register = (Button) findViewById (R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtUsername = userName.getText().toString();
                String txtEmail = email.getText().toString();
                String txtPassword = password.getText().toString();
                String txtmobile = mobile.getText().toString();

                if (TextUtils.isEmpty(txtUsername) ||
                        TextUtils.isEmpty(txtEmail)||
                        TextUtils.isEmpty(txtPassword)||
                        TextUtils.isEmpty(txtmobile)){
                    Toast.makeText(RegisterActivity.this,"All fields required",Toast.LENGTH_SHORT).show();
                }
                else {
                    int genderID = radioGroup.getCheckedRadioButtonId();
                    RadioButton selected_gender = radioGroup.findViewById(genderID);
                    if(selected_gender == null){
                        Toast.makeText(RegisterActivity.this,"select gender please",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String selectGender = selected_gender.getText().toString();
                        registerNewAccount(txtUsername,txtEmail,txtPassword,txtmobile,selectGender);
                    }

                }
            }
        });



    }
    private void registerNewAccount(final String username, final String email, final String password, final String mobile, final String gender){
        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Registering new Account");
        progressDialog.show();
        String uRL = "http://10.114.12.180:8080/Webservice/register.php";

        StringRequest request = new StringRequest(Request.Method.POST, uRL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Succesfully registered!")){
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this,response,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                }

                else{
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param  = new HashMap<>();
                param.put("username",username);
                param.put("email",email);
                param.put("password",password);
                param.put("mobile",mobile);
                param.put("gender",gender);

                return  param;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(RegisterActivity.this).addToRequeestQueue(request);
    }
}
