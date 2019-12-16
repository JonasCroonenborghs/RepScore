package be.thomasmore.repscore;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Home");

        db = new DatabaseHelper(this);

        String name = getIntent().getExtras().getString("name");
        bundle.putString("name", name);
        TextView textViewUserName = (TextView) findViewById(R.id.textViewUserName);
        textViewUserName.setText("Welcome " + name);

        TextView textViewTotalWorkouts = (TextView) findViewById(R.id.textViewTotalWorkouts);
        textViewTotalWorkouts.setText("You've registered " + db.getCountWorkouts() + " workouts so far");

        Workout lastWorkout = db.getLastWorkout();
        TextView textViewLastWorkout = (TextView) findViewById(R.id.textViewLastWorkout);
        textViewLastWorkout.setText("Last " + lastWorkout.getCoumpound() + " workout you've lifted " + lastWorkout.getWeight() + "!");

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Boolean started = false;

                switch (item.getItemId()) {
                    case R.id.action_user:
                        break;
                    case R.id.action_workout:
                        Intent workoutActivity = new Intent(HomeActivity.this, WorkoutActivity.class);
                        workoutActivity.putExtras(bundle);
                        startActivity(workoutActivity);
                        started = true;
                        break;
                    case R.id.action_highscores:
                        Intent highscoresActivity = new Intent(HomeActivity.this, HighscoresActivity.class);
                        highscoresActivity.putExtras(bundle);
                        startActivity(highscoresActivity);
                        started = true;
                        break;
                }
                return started;
            }
        });
    }
}
