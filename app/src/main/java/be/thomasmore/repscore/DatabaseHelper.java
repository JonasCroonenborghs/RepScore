package be.thomasmore.repscore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 8;
    private static final String DATABASE_NAME = "repscore";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_COMPOUNDLIFT = "CREATE TABLE compoundlift (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT," +
                "description TEXT)";
        db.execSQL(CREATE_TABLE_COMPOUNDLIFT);

        String CREATE_TABLE_WORKOUT = "CREATE TABLE workout (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "weight DEC," +
                "date TEXT, " +
                "compoundId INTEGER, " +
                "FOREIGN KEY (compoundId) REFERENCES compoundlift(id))";
        db.execSQL(CREATE_TABLE_WORKOUT);

        insertCompoundLifts(db);
        insertWorkouts(db);
    }

    private void insertCompoundLifts(SQLiteDatabase db) {
        db.execSQL("INSERT INTO compoundlift (id, name, description) VALUES (1, 'Bench Press', 'Compound Flat Chest Exercise');");
        db.execSQL("INSERT INTO compoundlift (id, name, description) VALUES (2, 'Deadlift','Deadlift Compound');");
        db.execSQL("INSERT INTO compoundlift (id, name, description) VALUES (3, 'Military Press','Compound Shoulder Exercise');");
        db.execSQL("INSERT INTO compoundlift (id, name, description) VALUES (4, 'Squat', 'Compound leg Exercive');");
    }


    private void insertWorkouts(SQLiteDatabase db) {
        db.execSQL("INSERT INTO workout (id, weight, date, compoundId) VALUES (1, 50, '15/06/2019',1);");
        db.execSQL("INSERT INTO workout (id, weight, date, compoundId) VALUES (2, 120,'15/06/2019',2);");
        db.execSQL("INSERT INTO workout (id, weight, date, compoundId) VALUES (3, 110, '15/06/2019',3);");
        db.execSQL("INSERT INTO workout (id, weight, date, compoundId) VALUES (4, 60,'15/06/2019',4);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS compoundlift");
        db.execSQL("DROP TABLE IF EXISTS workout");

        // Create tables again
        onCreate(db);
    }

    public Workout getWorkoutById(long workoutId) {
        Workout workout = null;
        String selectQuery = "SELECT  * FROM workout WHERE id = " + workoutId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                workout = new Workout(cursor.getLong(0),
                        cursor.getDouble(1), cursor.getString(2),
                        cursor.getLong(3));

                List<CompoundLift> compoundLifts = getCompoundLifts();

                for (CompoundLift compoundLift : compoundLifts) {
                    if (compoundLift.getId() == workout.getCompoundId()) {
                        workout.setCoumpound(compoundLift.getName());
                    }
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return workout;
    }

    public ArrayList<Workout> getAllWorkouts() {
        ArrayList<Workout> userWorkoutArrayList = new ArrayList<Workout>();
        String selectQuery = "SELECT  * FROM workout ORDER BY id DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Workout workout = new Workout(cursor.getLong(0),
                        cursor.getDouble(1), cursor.getString(2),
                        cursor.getLong(3));

                List<CompoundLift> compoundLifts = getCompoundLifts();

                for (CompoundLift compoundLift : compoundLifts) {
                    if (compoundLift.getId() == workout.getCompoundId()) {
                        workout.setCoumpound(compoundLift.getName());
                    }
                }

                userWorkoutArrayList.add(workout);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return userWorkoutArrayList;
    }

    public ArrayList<Workout> getAllWorkoutsByFilter(long compoundId) {
        ArrayList<Workout> userWorkoutArrayList = new ArrayList<Workout>();
        String selectQuery = "SELECT  * FROM workout WHERE compoundId = " + compoundId + " ORDER BY id DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Workout workout = new Workout(cursor.getLong(0),
                        cursor.getDouble(1), cursor.getString(2),
                        cursor.getLong(3));

                List<CompoundLift> compoundLifts = getCompoundLifts();

                for (CompoundLift compoundLift : compoundLifts) {
                    if (compoundLift.getId() == workout.getCompoundId()) {
                        workout.setCoumpound(compoundLift.getName());
                    }
                }

                userWorkoutArrayList.add(workout);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return userWorkoutArrayList;
    }

    public long insertWorkout(Workout workout) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("weight", workout.getWeight());
        values.put("date", workout.getDate());
        values.put("compoundId", workout.getCompoundId());

        long id = db.insert("workout", null, values);
        db.close();
        return id;
    }

    public boolean updateWorkout(long id, Workout workout) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("weight", workout.getWeight());
        values.put("date", workout.getDate());
        values.put("compoundId", workout.getCompoundId());

        int numrows = db.update(
                "workout",
                values,
                "id = " + id,
                null);
        db.close();
        return numrows > 0;
    }

    public boolean deleteWorkout(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int numrows = db.delete(
                "workout",
                "id = ?",
                new String[]{String.valueOf(id)});

        db.close();
        return numrows > 0;
    }

    public List<CompoundLift> getCompoundLifts() {
        List<CompoundLift> lijst = new ArrayList<CompoundLift>();

        String selectQuery = "SELECT * FROM compoundlift ORDER BY name";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                CompoundLift compoundLift = new CompoundLift(cursor.getLong(0),
                        cursor.getString(1), cursor.getString(2));
                lijst.add(compoundLift);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lijst;
    }

    public int getCountWorkouts() {
        String selectQuery = "SELECT  * FROM workout";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int total = cursor.getCount();

        cursor.close();
        db.close();
        return total;
    }

    public Workout getLastWorkout() {
        Workout returnWorkout = new Workout();

        String selectQuery = "SELECT  * FROM workout";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Workout workout = new Workout(cursor.getLong(0),
                        cursor.getDouble(1), cursor.getString(2),
                        cursor.getLong(3));

                List<CompoundLift> compoundLifts = getCompoundLifts();

                for (CompoundLift compoundLift : compoundLifts) {
                    if (compoundLift.getId() == workout.getCompoundId()) {
                        workout.setCoumpound(compoundLift.getName());
                    }
                }

                returnWorkout = workout;
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnWorkout;
    }

    public List<Workout> getMaxWeightPerCompuntlift() {
        List<Workout> list = new ArrayList<Workout>();

        String selectQuery = "SELECT *, MAX(weight) as maxWeight FROM workout GROUP BY compoundId";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Workout workout = new Workout(cursor.getLong(0),
                        cursor.getDouble(1), cursor.getString(2),
                        cursor.getLong(3));

                List<CompoundLift> compoundLifts = getCompoundLifts();

                for (CompoundLift compoundLift : compoundLifts) {
                    if (compoundLift.getId() == workout.getCompoundId()) {
                        workout.setCoumpound(compoundLift.getName());
                    }
                }

                list.add(workout);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    public double getMaxWeight() {
        double maxWeight = 0;

        String selectQuery = "SELECT MAX(weight) FROM workout";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                maxWeight = cursor.getDouble(0);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return maxWeight;
    }

    public List<CompoundLift> getTotalAmountPerCompuntlift() {
        List<CompoundLift> list = new ArrayList<CompoundLift>();


        String selectQuery = "SELECT compoundId, COUNT(*) FROM workout GROUP BY compoundId";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                CompoundLift compoundLift = new CompoundLift(cursor.getLong(0), cursor.getLong(1));

                List<CompoundLift> compoundLifts = getCompoundLifts();

                for (CompoundLift c : compoundLifts) {
                    if (c.getId() == compoundLift.getId()) {
                        compoundLift.setName(c.getName());
                    }
                }

                list.add(compoundLift);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    public int getMaxAmount() {
        int totalAmount = 0;
        List<Integer> listTotalAmounts = new ArrayList<>();
        int counter = 0;

        String selectQuery = "SELECT COUNT(*) FROM workout GROUP BY compoundId";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                listTotalAmounts.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return Collections.max(listTotalAmounts);
    }
}
