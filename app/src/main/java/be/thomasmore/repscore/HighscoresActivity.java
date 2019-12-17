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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class HighscoresActivity extends AppCompatActivity {
    private Bundle bundle = new Bundle();
    private DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bundle.putString("name", getIntent().getExtras().getString("name"));


        setTitle("Highscores");

        db = new DatabaseHelper(this);
        readWorkouts();

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
    private void toon(String tekst) {
        Toast.makeText(getBaseContext(), tekst, Toast.LENGTH_SHORT).show();
    }
    private void readWorkouts() {
        final List<Workout> workouts = db.getWorkouts();

        ArrayAdapter<Workout> adapter =
                new ArrayAdapter<Workout>(this,
                        android.R.layout.simple_list_item_1, workouts);

        final ListView listViewWorkouts =
                (ListView) findViewById(R.id.listViewWorkouts);
        listViewWorkouts.setAdapter(adapter);

        listViewWorkouts.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parentView,
                                            View childView, int position, long id) {
                        toon(workouts.get(position).toString());
                    }
                });
    }

}
