package stud11318057.develops.belber;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class RateActivity extends AppCompatActivity {
    private TextView getRating;
    private Button submit;
    private AppCompatRatingBar RatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        getRating = findViewById(R.id.rate);
        submit = findViewById(R.id.submit);
        RatingBar = findViewById(R.id.penilaian);

        RatingBar.setOnRatingBarChangeListener(new android.widget.RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(android.widget.RatingBar ratingBar, float rating, boolean fromUser) {
                getRating.setText("Rating : "+rating);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Nilai Yang anda Kirimkan: "+ RatingBar.getRating(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
