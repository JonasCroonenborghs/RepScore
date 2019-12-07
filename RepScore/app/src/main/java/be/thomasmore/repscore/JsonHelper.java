package be.thomasmore.repscore;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonHelper {
    public List<MuscleGroup> getMuscleGroups(String jsonText) {
        List<MuscleGroup> list = new ArrayList<MuscleGroup>();

        try {
            JSONObject jsonObjectMuscleGroups = new JSONObject(jsonText);
            JSONArray jsonArrayResult = jsonObjectMuscleGroups.getJSONArray("results");

            for (int i = 0; i < jsonArrayResult.length(); i++) {
                JSONObject jsonObjectMuscleGroup = jsonArrayResult.getJSONObject(i);
                MuscleGroup muscleGroup = new MuscleGroup();

                muscleGroup.setId(jsonObjectMuscleGroup.getLong("id"));
                muscleGroup.setName(jsonObjectMuscleGroup.getString("name"));

                list.add(muscleGroup);
            }
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        return list;
    }

    public List<Exercise> getExercises(String jsonText, long category, List<MuscleGroup> muscleGroups) {
        List<Exercise> list = new ArrayList<Exercise>();

        try {
            JSONObject jsonObjectExercises = new JSONObject(jsonText);
            JSONArray jsonArrayResult = jsonObjectExercises.getJSONArray("results");

            for (int i = 0; i < jsonArrayResult.length(); i++) {
                JSONObject jsonObjectExercise = jsonArrayResult.getJSONObject(i);

                Exercise exercise = new Exercise();

                if (jsonObjectExercise.getLong("category") == muscleGroups.get((int) category).getId() && jsonObjectExercise.getLong("language") == 2) {
                    if (!jsonObjectExercise.getString("name").equals("") && !jsonObjectExercise.getString("description").equals("")) {
                        exercise.setId(jsonObjectExercise.getLong("id"));
                        exercise.setName(jsonObjectExercise.getString("name"));
                        exercise.setDescription(jsonObjectExercise.getString("description"));
                        exercise.setCategory(jsonObjectExercise.getLong("category"));

                        list.add(exercise);
                    }
                }
            }
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        return list;
    }
}
