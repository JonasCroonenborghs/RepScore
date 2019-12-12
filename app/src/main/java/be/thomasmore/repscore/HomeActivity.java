package be.thomasmore.repscore;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Home");

        loadFragment(new UserFragment());

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.action_user:
//                        Bundle bundle = new Bundle();
//
//                        bundle.putString("id", getIntent().getExtras().getString("id"));
//                        bundle.putString("name", getIntent().getExtras().getString("id"));
//                        bundle.putString("first_name", getIntent().getExtras().getString("first_name"));
//                        bundle.putString("last_name", getIntent().getExtras().getString("last_name"));
//                        bundle.putString("email", getIntent().getExtras().getString("email"));
//                        bundle.putString("gender", getIntent().getExtras().getString("gender"));
//
//                        fragment.setArguments(bundle);
                        fragment = new UserFragment();
                        break;
                    case R.id.action_workout:
                        fragment = new WorkoutFragment();
                        break;
                    case R.id.action_highscores:
                        fragment = new HighscoresFragment();
                        break;
                }
                return loadFragment(fragment);
            }
        });


//        String id = getIntent().getExtras().getString("id");
//        String name = getIntent().getExtras().getString("name");
//        String first_name = getIntent().getExtras().getString("first_name");
//        String last_name = getIntent().getExtras().getString("last_name");
//        String email = getIntent().getExtras().getString("email");
//        String gender = getIntent().getExtras().getString("gender");
//
//        TextView textViewName = (TextView) findViewById(R.id.textViewName);
//        textViewName.setText("Welcome " + name);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
