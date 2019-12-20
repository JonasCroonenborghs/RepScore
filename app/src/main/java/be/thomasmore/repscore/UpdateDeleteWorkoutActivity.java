package be.thomasmore.repscore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class UpdateDeleteWorkoutActivity extends AppCompatActivity {
    private Workout workout = null;
    private Long workoutId = null;
    private String date = null;
    private EditText editWeight;
    private Spinner spinner;
    private DatabaseHelper db;
    private Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_workout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Update workout");

        bundle.putString("name", getIntent().getExtras().getString("name"));

        db = new DatabaseHelper(this);
        readCompoundLifts();

        Intent intent = getIntent();
        workoutId = getIntent().getExtras().getLong("workoutId");

        workout = db.getWorkoutById(workoutId);

        editWeight = (EditText) findViewById(R.id.editTextWeight);
        editWeight.setText(workout.getWeight() + "");

        int selectedCompound = (int) workout.getCompoundId() - 1;
        Spinner spinner = (Spinner) findViewById(R.id.listViewCompoundLifts);
        spinner.setSelection(selectedCompound);

        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        // Hier moet nog de workout.datum opgeladen worden
        Log.i("INFO", "date: " + workout.getDate());
    }

    public void buttonUpdate_onClick(View view) {
        Workout workoutEdit = new Workout();
        double weight = 0.0;
        String date = null;
        Long compoundId = null;

        EditText editTextWeight = (EditText) findViewById(R.id.editTextWeight);

        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        date = String.valueOf(datePicker.getDayOfMonth()) + "/" + String.valueOf(datePicker.getMonth() + "/" + String.valueOf(datePicker.getYear()));

        Spinner editCompound = (Spinner) findViewById(R.id.listViewCompoundLifts);
        compoundId = editCompound.getSelectedItemId() + 1;

        if (editTextWeight.getText().toString().matches("")) {
            TextView textViewWeightError = (TextView) findViewById(R.id.textViewWeightError);
            textViewWeightError.setText("Pleas fill in weight");
            textViewWeightError.setVisibility(View.VISIBLE);
        } else {
            weight = Double.parseDouble(editTextWeight.getText().toString());

            workoutEdit.setWeight(weight);
            workoutEdit.setDate(date);
            workoutEdit.setCompoundId(compoundId);

            Log.i("INFO", "workout: " + workout);
            Log.i("INFO", "workoutEdit: " + workoutEdit);

            db.updateWorkout(workoutId, workoutEdit);
            Toast.makeText(UpdateDeleteWorkoutActivity.this, "Updates succesfully ! ", Toast.LENGTH_SHORT).show();

            Intent highscoresActivity = new Intent(UpdateDeleteWorkoutActivity.this, HighscoresActivity.class);
            highscoresActivity.putExtras(bundle);
            startActivity(highscoresActivity);
        }
    }

    public void buttonDelete_onClick(View view) {
        db.deleteWorkout(workoutId);
        Toast.makeText(UpdateDeleteWorkoutActivity.this, "Deleted succesfully ! ", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(UpdateDeleteWorkoutActivity.this, HighscoresActivity.class);
        startActivity(intent);
    }

    private void readCompoundLifts() {
        List<CompoundLift> compoundLifts = db.getCompoundLifts();

        ArrayAdapter<CompoundLift> adapter = new ArrayAdapter<CompoundLift>(this,
                android.R.layout.simple_spinner_item, compoundLifts);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = findViewById(R.id.listViewCompoundLifts);

        spinner.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
