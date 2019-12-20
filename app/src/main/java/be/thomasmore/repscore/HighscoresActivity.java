package be.thomasmore.repscore;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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

        RadioGroup radioGroupCompound = (RadioGroup) findViewById(R.id.radioGroupCompound);
        radioGroupCompound.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Button buttonFilter = (Button) findViewById(R.id.buttonFilter);
                buttonFilter.setVisibility(View.VISIBLE);
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
        long workoutId = 0;
        db = new DatabaseHelper(this);

        RadioButton radioButtonBenchPress = (RadioButton) findViewById(R.id.radioButtonBenchPress);
        RadioButton radioButtonDeadlift = (RadioButton) findViewById(R.id.radioButtonDeadlift);
        RadioButton radioButtonMilitaryPress = (RadioButton) findViewById(R.id.radioButtonMilitaryPress);
        RadioButton radioButtonSquad = (RadioButton) findViewById(R.id.radioButtonSquad);

        if (radioButtonBenchPress.isChecked()) {
            workoutId = 1;
        } else if (radioButtonDeadlift.isChecked()) {
            workoutId = 2;
        } else if (radioButtonMilitaryPress.isChecked()) {
            workoutId = 3;
        } else if (radioButtonSquad.isChecked()) {
            workoutId = 4;
        }

        workoutArrayList = db.getAllWorkoutsByFilter(workoutId);

        listViewWorkouts = (ListView) findViewById(R.id.listViewWorkouts);
        customAdapter = new CustomAdapter(this, workoutArrayList);
        listViewWorkouts.setAdapter(customAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
