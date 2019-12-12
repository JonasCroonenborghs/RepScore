package be.thomasmore.repscore;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Workout");

        Spinner dropdown = findViewById(R.id.spinner3);
        String[] items = new String[]{"Choose a bodypart", "Chest", "Back", "Legs", "Shoulders"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        setTitle("All Exercises");
        db = new DatabaseHelper(this);
        readCompoundLifts();

    }
    private void toon(String tekst)
    {
        Toast.makeText(getBaseContext(), tekst, Toast.LENGTH_SHORT).show();
    }



    private void readCompoundLifts(){
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

    public void btn_add(View arg0) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.modal);

        dialog.show();
    }
}