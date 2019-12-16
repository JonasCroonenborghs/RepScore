package be.thomasmore.repscore;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class HomeActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private Bundle bundle = new Bundle();

    LineChartView lineChartView;
    String[] axisData = null;
    int[] yAxisData = null;
//    String[] axisData = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept",
//            "Oct", "Nov", "Dec"};
//    int[] yAxisData = {50, 20, 15, 30, 20, 60, 15, 40, 45, 10, 90, 18};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Home");

        db = new DatabaseHelper(this);

        // Put data in textViews
        String name = getIntent().getExtras().getString("name");
        bundle.putString("name", name);
        TextView textViewUserName = (TextView) findViewById(R.id.textViewUserName);
        textViewUserName.setText("Welcome " + name);

        TextView textViewTotalWorkouts = (TextView) findViewById(R.id.textViewTotalWorkouts);
        textViewTotalWorkouts.setText("You've registered " + db.getCountWorkouts() + " workouts so far");

        Workout lastWorkout = db.getLastWorkout();
        TextView textViewLastWorkout = (TextView) findViewById(R.id.textViewLastWorkout);
        textViewLastWorkout.setText(lastWorkout.getDate() + " you've lifted " + lastWorkout.getWeight() + " on the " + lastWorkout.getCoumpound() + "!");

        // Chart
        lineChartView = findViewById(R.id.chart);

        List axisValues = new ArrayList();
        List yAxisValues = new ArrayList();

        List<Workout> workouts = db.getWorkouts();
        axisData = new String[workouts.size()];
        yAxisData = new int[workouts.size()];
        int counter = 0;

        for (Workout workout : workouts) {
            axisData[counter] = workout.getCoumpound();
            yAxisData[counter] = (int)workout.getWeight();

            Log.i("INFO", "a " + axisData[counter]);
            Log.i("INFO", "y " + yAxisData[counter]);
            counter++;
        }

        Log.i("INFO", "a " + axisData);
        Log.i("INFO", "y " + yAxisData);

        Line line = new Line(yAxisValues).setColor(Color.parseColor("#FF6E00"));

        for (int i = 0; i < axisData.length; i++) {
            axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
        }

        for (int i = 0; i < yAxisData.length; i++) {
            yAxisValues.add(new PointValue(i, yAxisData[i]));
        }

        List lines = new ArrayList();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        Axis axis = new Axis();
        axis.setValues(axisValues);
        axis.setTextSize(16);
        axis.setTextColor(Color.parseColor("#03A9F4"));
        data.setAxisXBottom(axis);

        Axis yAxis = new Axis();
        yAxis.setName("Kilograms");
        yAxis.setTextColor(Color.parseColor("#03A9F4"));
        yAxis.setTextSize(16);
        data.setAxisYLeft(yAxis);

        lineChartView.setLineChartData(data);
        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
        viewport.top = 150;
        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);

        // BottomNavigation
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Boolean started = false;

                switch (item.getItemId()) {
                    case R.id.action_user:
                        break;
                    case R.id.action_workout:
                        Intent workoutActivity = new Intent(HomeActivity.this, WorkoutActivity.class);
                        workoutActivity.putExtras(bundle);
                        startActivity(workoutActivity);
                        started = true;
                        break;
                    case R.id.action_highscores:
                        Intent highscoresActivity = new Intent(HomeActivity.this, HighscoresActivity.class);
                        highscoresActivity.putExtras(bundle);
                        startActivity(highscoresActivity);
                        started = true;
                        break;
                }
                return started;
            }
        });
    }
}
