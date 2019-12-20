package be.thomasmore.repscore;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Handler handler = new Handler();
        final Intent mainActivity = new Intent(this, MainActivity.class);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(mainActivity);
                finish();
            }
        }, 3000);
    }
}
