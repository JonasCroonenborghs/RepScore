package be.thomasmore.repscore;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Home");

        String id = getIntent().getExtras().getString("id");
        String name = getIntent().getExtras().getString("name");
        String first_name = getIntent().getExtras().getString("first_name");
        String last_name = getIntent().getExtras().getString("last_name");
        String email = getIntent().getExtras().getString("email");
        String gender = getIntent().getExtras().getString("gender");

        Log.i("INFO", "first_name" + first_name);
        Log.i("INFO", "last_name" + last_name);
        Log.i("INFO", "email" + email);
        Log.i("INFO", "gender" + gender);

//        TextView textViewUserName = (TextView) findViewById(R.id.textViewUserName);
//        textViewUserName.setText(getIntent().getExtras().getString("first_name"));
//
//        TextView textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
//        textViewUserName.setText(getIntent().getExtras().getString("email"));


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Boolean started = false;

                switch (item.getItemId()) {
                    case R.id.action_user:
                        Bundle bundle = new Bundle();

                        bundle.putString("id", getIntent().getExtras().getString("id"));
                        bundle.putString("name", getIntent().getExtras().getString("id"));
                        bundle.putString("first_name", getIntent().getExtras().getString("first_name"));
                        bundle.putString("last_name", getIntent().getExtras().getString("last_name"));
                        bundle.putString("email", getIntent().getExtras().getString("email"));
                        bundle.putString("gender", getIntent().getExtras().getString("gender"));

                        Intent userActivity = new Intent(HomeActivity.this, HomeActivity.class);
                        userActivity.putExtras(bundle);
                        startActivity(userActivity);
                        started = true;
                        break;
                    case R.id.action_workout:
                        Intent workoutActivity = new Intent(HomeActivity.this, WorkoutActivity.class);
                        startActivity(workoutActivity);
                        started = true;
                        break;
                    case R.id.action_highscores:
                        Intent highscoresActivity = new Intent(HomeActivity.this, HighscoresActivity.class);
                        startActivity(highscoresActivity);
                        started = true;
                        break;
                }
                return started;
            }
        });
    }
}
