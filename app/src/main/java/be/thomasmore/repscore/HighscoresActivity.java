package be.thomasmore.repscore;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class HighscoresActivity extends AppCompatActivity {
    private Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bundle.putString("name", getIntent().getExtras().getString("name"));

        Spinner dropdown = findViewById(R.id.spinner2);
        String[] items = new String[]{"Choose a exercise", "Bench Press", "Deadlift", "Squat", "Military press"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        setTitle("Highscores");

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Boolean started = false;

                switch (item.getItemId()) {
                    case R.id.action_user:
                        Intent userActivity = new Intent(HighscoresActivity.this, HomeActivity.class);
                        userActivity.putExtras(bundle);
                        startActivity(userActivity);
                        started = true;
                        break;
                    case R.id.action_workout:
                        Intent workoutActivity = new Intent(HighscoresActivity.this, WorkoutActivity.class);
                        workoutActivity.putExtras(bundle);
                        startActivity(workoutActivity);
                        started = true;
                        break;
                    case R.id.action_highscores:
                        break;
                }
                return started;
            }
        });
    }

}
