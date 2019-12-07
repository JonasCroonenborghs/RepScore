package be.thomasmore.repscore;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.widget.AdapterView.OnItemClickListener;

public class ExcerisesActivity extends AppCompatActivity {

    List<MuscleGroup> muscleGroups;
    List<Exercise> exercises;

    String exerciseName;
    String exerciseDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excerises);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("All Exercises");

        readMuscleGroups();

        final ListView listViewMuscleGroups = (ListView) findViewById(R.id.listViewMuscleGroups);
        listViewMuscleGroups.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listViewMuscleGroups.setAdapter(null);
                readExercises(position);
            }
        });

        final ListView listViewExercises = (ListView) findViewById(R.id.listViewExercises);
        listViewExercises.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                listViewExercises.setAdapter(null);

                Log.i("INFO", "exercises: " + exercises.size());

//                exerciseName = exercises.get(position).getName();
//                exerciseDescription = exercises.get(position).getDescription();

//                Log.i("INFO", "exerciseName: " + exerciseName);
//                Log.i("INFO", "exerciseDescription: " + exerciseDescription);

//                TextView textViewExerciseName = (TextView) findViewById(R.id.textViewExerciseName);
//                textViewExerciseName.setText("Exercise: " + exerciseName);
//
//                TextView textViewExerciseDescription = (TextView) findViewById(R.id.textViewExerciseDescription);
//                textViewExerciseDescription.setText("Description: " + exerciseDescription);
            }
        });
    }

    private void readMuscleGroups() {
        HttpReader httpReader = new HttpReader();
        httpReader.setOnResultReadyListener(new HttpReader.OnResultReadyListener() {
            @Override
            public void resultReady(String result) {
                JsonHelper jsonHelper = new JsonHelper();
                muscleGroups = jsonHelper.getMuscleGroups(result);
                List<String> muscleGroupNames = new ArrayList<>();

                for (int i = 0; i < muscleGroups.size(); i++) {
                    muscleGroupNames.add(muscleGroups.get(i).getName());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ExcerisesActivity.this, android.R.layout.simple_list_item_1, muscleGroupNames);
                ListView listViewMuscleGroups = (ListView) findViewById(R.id.listViewMuscleGroups);
                listViewMuscleGroups.setAdapter(adapter);
            }
        });
        httpReader.execute("https://wger.de/api/v2/exercisecategory/?format=json");
    }

    private void readExercises(final long category) {
        final List<String> exerciseNames = new ArrayList<>();

        for (int i = 1; i <= 31; i++) {
            HttpReader httpReader = new HttpReader();
            final int pageCounter = i;
            httpReader.setOnResultReadyListener(new HttpReader.OnResultReadyListener() {
                @Override
                public void resultReady(String result) {
                    JsonHelper jsonHelper = new JsonHelper();
                    exercises = jsonHelper.getExercises(result, category, muscleGroups);

                    for (int j = 0; j < exercises.size(); j++) {
                        exerciseNames.add(exercises.get(j).getName());
                    }

                    if (pageCounter == 31) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ExcerisesActivity.this, android.R.layout.simple_list_item_1, exerciseNames);
                        ListView listViewExercises = (ListView) findViewById(R.id.listViewExercises);
                        listViewExercises.setAdapter(adapter);
                    }
                }
            });
            httpReader.execute("https://wger.de/api/v2/exercise/?format=json&page=" + i);
        }
    }
}
