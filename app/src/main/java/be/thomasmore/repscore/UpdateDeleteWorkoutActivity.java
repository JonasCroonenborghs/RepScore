package be.thomasmore.repscore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class UpdateDeleteWorkoutActivity extends AppCompatActivity {

    private EditText editWeight;
    private Spinner spinner;
    private Button buttonUpdate, buttonDelete;
    private DatabaseHelper db;
    private String date = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_workout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        final Long workoutId = getIntent().getExtras().getLong("workoutId");

        db = new DatabaseHelper(this);
        final Workout workout = db.getWorkoutById(workoutId);

        editWeight = (EditText) findViewById(R.id.editTextWeight);
        spinner = (Spinner) findViewById(R.id.listViewCompoundLifts);
        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);


        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);

            editWeight.setText(workout.getWeight()+"");


             // spinner.setSelection((int) workout.getCompoundId());





        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.updateWorkout(workout);

                Intent intent = new Intent(UpdateDeleteWorkoutActivity.this,HighscoresActivity.class);
                startActivity(intent);
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteWorkout(workoutId);


                Intent intent = new Intent(UpdateDeleteWorkoutActivity.this, HighscoresActivity.class);
                startActivity(intent);

            }
        });
    }

}
