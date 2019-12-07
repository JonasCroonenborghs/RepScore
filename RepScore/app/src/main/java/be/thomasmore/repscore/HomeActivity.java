package be.thomasmore.repscore;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Home");

        String id = getIntent().getExtras().getString("id");
        String name = getIntent().getExtras().getString("name");
        String first_name = getIntent().getExtras().getString("first_name");
        String last_name = getIntent().getExtras().getString("last_name");
        String email = getIntent().getExtras().getString("email");
        String gender = getIntent().getExtras().getString("gender");

        TextView textViewName = (TextView) findViewById(R.id.textViewName);
        textViewName.setText("Welcome " + name);
    }

    public void btn_exercises_onClick(View v){
        Intent exerciseActivity = new Intent(this, ExcerisesActivity.class);
        startActivity(exerciseActivity);
    }

    public void btn_workout_onClick(View v){
        Intent workoutActivity = new Intent(this, WorkoutActivity.class);
        startActivity(workoutActivity);
    }

    public void btn_highscores_onClick(View v){
        Intent highscoresActivity = new Intent(this, HighscoresActivity.class);
        startActivity(highscoresActivity);
    }
}
