package be.thomasmore.repscore;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ExcerisesActivity extends AppCompatActivity {

    List<MuscleGroup> muscleGroups;
    List<Exercise> exercisesList = new ArrayList<>();
    List<ExerciseImage> exerciseImagesList = new ArrayList<>();

    long exerciseID;
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
                readExercises(position);
                LinearLayout linearLayoutMuscleGroups = (LinearLayout) findViewById(R.id.linearLayoutMuscleGroups);
                linearLayoutMuscleGroups.setVisibility(View.INVISIBLE);
            }
        });

        final ListView listViewExercises = (ListView) findViewById(R.id.listViewExercises);
        listViewExercises.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listViewExercises.setVisibility(View.INVISIBLE);
                LinearLayout linearLayoutExerciseInfo = (LinearLayout) findViewById(R.id.linearLayoutExerciseInfo);
                linearLayoutExerciseInfo.setVisibility(View.VISIBLE);

                exerciseID = exercisesList.get(position).getId();
                exerciseName = exercisesList.get(position).getName();
                exerciseDescription = exercisesList.get(position).getDescription();

                // Remove html-tags from data
                exerciseDescription = exerciseDescription.replaceAll("<p>", "");
                exerciseDescription = exerciseDescription.replaceAll("</p>", "");
                exerciseDescription = exerciseDescription.replaceAll("<ol>", "");
                exerciseDescription = exerciseDescription.replaceAll("</ol>", "");
                exerciseDescription = exerciseDescription.replaceAll("<ul>", "");
                exerciseDescription = exerciseDescription.replaceAll("</ul>", "");
                exerciseDescription = exerciseDescription.replaceAll("<li>", "");
                exerciseDescription = exerciseDescription.replaceAll("</li>", "");
                exerciseDescription = exerciseDescription.replaceAll("<em>", "");
                exerciseDescription = exerciseDescription.replaceAll("</em>", "");

                TextView textViewExerciseName = (TextView) findViewById(R.id.textViewExerciseName);
                textViewExerciseName.setText(exerciseName);

                TextView textViewExerciseDescription = (TextView) findViewById(R.id.textViewExerciseDescription);
                textViewExerciseDescription.setText(exerciseDescription);

                readExerciseImage(exerciseID);
            }
        });
    }

    public void buttonGoBack_onClick(View view) {
        LinearLayout linearLayoutExerciseInfo = (LinearLayout) findViewById(R.id.linearLayoutExerciseInfo);
        linearLayoutExerciseInfo.setVisibility(View.INVISIBLE);
        ListView listViewExercises = (ListView) findViewById(R.id.listViewExercises);
        listViewExercises.setVisibility(View.VISIBLE);
    }

    public void buttonGoHome_onClick(View view) {
        onBackPressed();
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
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        final TextView textViewWaiting = (TextView) findViewById(R.id.textViewWaiting);

        progressBar.setVisibility(View.VISIBLE);
        textViewWaiting.setVisibility(View.VISIBLE);

        for (int i = 1; i <= 31; i++) {
            HttpReader httpReader = new HttpReader();
            final int pageCounter = i;

            httpReader.setOnResultReadyListener(new HttpReader.OnResultReadyListener() {
                @Override
                public void resultReady(String result) {
                    JsonHelper jsonHelper = new JsonHelper();
                    List<Exercise> exercises = jsonHelper.getExercises(result, category, muscleGroups);

                    for (int j = 0; j < exercises.size(); j++) {
                        exercisesList.add(exercises.get(j));
                        exerciseNames.add(exercises.get(j).getName());
                    }

                    if (pageCounter == 31) {
                        progressBar.setVisibility(View.INVISIBLE);
                        textViewWaiting.setVisibility(View.INVISIBLE);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ExcerisesActivity.this, android.R.layout.simple_list_item_1, exerciseNames);
                        ListView listViewExercises = (ListView) findViewById(R.id.listViewExercises);
                        listViewExercises.setAdapter(adapter);
                    }
                }
            });
            httpReader.execute("https://wger.de/api/v2/exercise/?format=json&page=" + i);
        }
    }

    private void readExerciseImage(final long exerciseID) {
        for (int i = 1; i <= 11; i++) {
            HttpReader httpReader = new HttpReader();
            final int pageCounter = i;
            httpReader.setOnResultReadyListener(new HttpReader.OnResultReadyListener() {
                @Override
                public void resultReady(String result) {
                    JsonHelper jsonHelper = new JsonHelper();
                    ExerciseImage exerciseImage = jsonHelper.getExerciseImage(result, exerciseID);
                    ImageView imageViewExerciseImage = (ImageView) findViewById(R.id.imageViewExerciseImage);

                    if (exerciseImage.getImage() != null) {
                        Picasso.get().load(exerciseImage.getImage()).into(imageViewExerciseImage);
                    }
                }
            });
            httpReader.execute("https://wger.de/api/v2/exerciseimage/?format=json&page=" + i);
        }
    }
}
