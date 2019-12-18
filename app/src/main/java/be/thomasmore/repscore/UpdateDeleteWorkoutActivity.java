package be.thomasmore.repscore;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
        Long workoutId = getIntent().getExtras().getLong("workoutId");

        db = new DatabaseHelper(this);
        Workout workout = db.getWorkoutById(workoutId);

//        editWeight = (EditText) findViewById(R.id.editTextWeight);
//        spinner = (Spinner) findViewById(R.id.listViewCompoundLifts);
//        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
//        buttonDelete = (Button) findViewById(R.id.buttondDelete);
//        buttonUpdate = (Button) findViewById(R.id.buttondUpdate);

//        editWeight.setText((int) workout.getWeight());
//        spinner.setSelection((int) workout.getCompoundId());


//        buttonUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                db.updateWorkout(workout.getId(),workout.getWeight(),workout.getCompoundId(),workout.getDate());
//
//
//                Toast.makeText(UpdateDeleteWorkoutActivity.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(UpdateDeleteWorkoutActivity.this,WorkoutActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//            }
//        });

//        buttonDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                db.deleteWorkout(workout.getId());
//                Toast.makeText(UpdateDeleteWorkoutActivity.this, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(UpdateDeleteWorkoutActivity.this, WorkoutActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//            }
//        });
    }

}
