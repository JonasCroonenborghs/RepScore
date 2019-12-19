package be.thomasmore.repscore;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class HighscoresActivity extends AppCompatActivity {
    private Bundle bundle = new Bundle();
    private DatabaseHelper db;
    private ArrayList<Workout> workoutArrayList;
    private ListView listViewWorkouts;
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Workouts");

        bundle.putString("name", getIntent().getExtras().getString("name"));

        db = new DatabaseHelper(this);
        workoutArrayList = db.getAllWorkouts();

        listViewWorkouts = (ListView) findViewById(R.id.listViewWorkouts);
        customAdapter = new CustomAdapter(this, workoutArrayList);
        listViewWorkouts.setAdapter(customAdapter);

        listViewWorkouts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent UpdateDeleteWorkoutActivity = new Intent(HighscoresActivity.this, UpdateDeleteWorkoutActivity.class);

                long workoutId = workoutArrayList.get(position).getId();

                bundle.putLong("workoutId", workoutId);
                UpdateDeleteWorkoutActivity.putExtras(bundle);
                startActivity(UpdateDeleteWorkoutActivity);
            }
        });

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

    public void buttonFilter_onClick(View view) {
        RadioGroup radioGroupCompound = (RadioGroup) findViewById(R.id.radioGroupCompound);
        long workoutId = radioGroupCompound.getCheckedRadioButtonId();

        if (workoutId != -1) {
            db = new DatabaseHelper(this);
            workoutArrayList = db.getAllWorkoutsByFilter(workoutId);

            listViewWorkouts = (ListView) findViewById(R.id.listViewWorkouts);
            customAdapter = new CustomAdapter(this, workoutArrayList);
            listViewWorkouts.setAdapter(customAdapter);
        }
    }
}
