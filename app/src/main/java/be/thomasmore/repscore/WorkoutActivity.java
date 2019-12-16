package be.thomasmore.repscore;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

public class WorkoutActivity extends AppCompatActivity {
    final Context context = this;
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


    private void buttonAdd_onClick(View v){

        EditText editWeight = (EditText) findViewById(R.id.editKg);
        double weight = Double.parseDouble(editWeight.getText().toString());

        DatePicker editDate = (DatePicker) findViewById(R.id.datePicker1);
        String date = String.valueOf(editDate.getDayOfMonth());

        Spinner editCompound = (Spinner) findViewById(R.id.listViewCompoundLifts);
        int compoundId = editCompound.getId();


        Workout workout = new Workout();
        workout.setWeight(weight);
        workout.setDate(date);
        workout.setCompoundId(compoundId);

        db.insertWorkout(workout);


    }

    private void readCompoundLifts(){
        List<CompoundLift> compoundLifts = db.getCompoundLifts();

        ArrayAdapter<CompoundLift> adapter = new ArrayAdapter<CompoundLift>(this,
                android.R.layout.simple_spinner_dropdown_item,compoundLifts);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = findViewById(R.id.listViewCompoundLifts);

        listViewWorkouts.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parentView,
                                            View childView, int position, long id) {
                        toon(workouts.get(position).getDate());
                    }
                });
        spinner.setAdapter(adapter);

    }

//    private void readCompoundLifts() {
//        final List<CompoundLift> compoundLifts = db.getCompoundLifts();
//
//        ArrayAdapter<CompoundLift> adapter =
//                new ArrayAdapter<CompoundLift>(this,
//                        android.R.layout.simple_list_item_1, compoundLifts);
//
//        final ListView listViewCompoundLifts =
//                (ListView) findViewById(R.id.listViewCompoundLifts);
//        listViewCompoundLifts.setAdapter(adapter);
//
//        listViewCompoundLifts.setOnItemClickListener(
//                new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parentView,
//                                            View childView, int position, long id) {
//                        toon(compoundLifts.get(position).getName());
//                    }
//                });
//    }





    private void toon(String tekst) {
        Toast.makeText(getBaseContext(), tekst, Toast.LENGTH_SHORT).show();
    }
}