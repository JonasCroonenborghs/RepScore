package be.thomasmore.repscore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class WorkoutActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private Bundle bundle = new Bundle();
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Workout");

        bundle.putString("name", getIntent().getExtras().getString("name"));

        db = new DatabaseHelper(this);
        readCompoundLifts();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Boolean started = false;

                switch (item.getItemId()) {
                    case R.id.action_user:
                        Intent userActivity = new Intent(WorkoutActivity.this, HomeActivity.class);
                        userActivity.putExtras(bundle);
                        startActivity(userActivity);
                        started = true;
                        break;
                    case R.id.action_workout:
                        break;
                    case R.id.action_highscores:
                        Intent highscoresActivity = new Intent(WorkoutActivity.this, HighscoresActivity.class);
                        highscoresActivity.putExtras(bundle);
                        startActivity(highscoresActivity);
                        started = true;
                        break;
                }
                return started;
            }
        });
    }

    private void readCompoundLifts() {
        List<CompoundLift> compoundLifts = db.getCompoundLifts();

        ArrayAdapter<CompoundLift> adapter = new ArrayAdapter<CompoundLift>(this,
                android.R.layout.simple_spinner_item, compoundLifts);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = findViewById(R.id.listViewCompoundLifts);

        spinner.setAdapter(adapter);
        spinner.setSelection(0);
    }


    public void buttonAddWorkout_onClick(View view) {
        double weight = 0;
        String date = null;
        Long compoundId = null;

        EditText editTextWeight = (EditText) findViewById(R.id.editTextWeight);
        weight = Double.parseDouble(editTextWeight.getText().toString());

        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        date = String.valueOf(datePicker.getDayOfMonth()) + "/" + String.valueOf(datePicker.getMonth() + "/" + String.valueOf(datePicker.getYear()));

        Spinner editCompound = (Spinner) findViewById(R.id.listViewCompoundLifts);
        compoundId = editCompound.getSelectedItemId();

        Workout workout = new Workout();

        if (weight == 0) {
            TextView textViewWeightError = (TextView) findViewById(R.id.textViewWeightError);
            textViewWeightError.setVisibility(View.VISIBLE);
        } else {
            workout.setWeight(weight);
            workout.setDate(date);
            workout.setCompoundId(compoundId);
            Log.i("INFO", "workout: " + workout);
//        db.insertWorkout(workout);
        }
    }
}