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

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class WorkoutActivity extends AppCompatActivity {
    final Context context = this;
    private DatabaseHelper db;
    private Bundle bundle = new Bundle();

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
        readWorkouts();

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
        final List<CompoundLift> compoundLifts = db.getCompoundLifts();

        ArrayAdapter<CompoundLift> adapter =
                new ArrayAdapter<CompoundLift>(this,
                        android.R.layout.simple_list_item_1, compoundLifts);

        final ListView listViewCompoundLifts =
                (ListView) findViewById(R.id.listViewCompoundLifts);
        listViewCompoundLifts.setAdapter(adapter);

        listViewCompoundLifts.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parentView,
                                            View childView, int position, long id) {
                        toon(compoundLifts.get(position).getName());
                    }
                });
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
                        toon(workouts.get(position).getWeight());
                    }
                });
    }

    public void btn_add(View arg0) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.modal);

        dialog.show();
    }

    private void toon(String tekst) {
        Toast.makeText(getBaseContext(), tekst, Toast.LENGTH_SHORT).show();
    }
}