package stud11318057.develops.belber;



import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import stud11318057.develops.belber.dao.UserPrefsData;

public class MainActivity extends Activity
{
    private UserPrefsData userPrefsData;
    Button logout;

    //Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userPrefsData = new UserPrefsData(this);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        final SharedPreferences sharedPreferences = getSharedPreferences("UserInfo",MODE_PRIVATE);

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getResources().getString(R.string.prefLoginState),"loggedout");
                editor.apply();
                startActivity(new Intent(MainActivity.this,MainActivity2.class));
            }
        });



        // Allow the user to control the media volume of their device.
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    //Override
    public void onDestroy()
    {
        super.onDestroy();


        if (userPrefsData != null)
        {
            userPrefsData.close();
        }
    }

    /**
     * Displays the trivia categories list.
     *
     * //param View view - The current view.
     */
    public void showCategories(View view)
    {

        Intent i = new Intent(view.getContext(), CategoriesActivity.class);
        startActivity(i);
    }

    /**
     * Displays the trivia scores list.
     *
     * //param View view - The current view.
     */
    public void showScores(View view)
    {

        Intent i = new Intent(view.getContext(), ScoresActivity.class);
        startActivity(i);
    }

    public void rating(View view) {
        startActivity(new Intent(getApplicationContext(),RateActivity.class));
    }

   public void petunjuk(View view){
        Intent newIntent = new Intent(getApplicationContext(),Petunjuk.class);
        startActivity(newIntent);
   }

}