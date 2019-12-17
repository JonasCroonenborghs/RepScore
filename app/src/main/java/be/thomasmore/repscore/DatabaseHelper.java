package be.thomasmore.repscore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 5;
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
        db.execSQL("INSERT INTO compoundlift (id, name, description) VALUES (1, 'Bench Press', 'Do this if you want a BIG CHEST !!');");
        db.execSQL("INSERT INTO compoundlift (id, name, description) VALUES (2, 'Deadlift','D Lift Desc');");
        db.execSQL("INSERT INTO compoundlift (id, name, description) VALUES (3, 'Squat', 'SQUAT BITCH!');");
        db.execSQL("INSERT INTO compoundlift (id, name, description) VALUES (4, 'Military Press','Boulders for Shoulders');");
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


    public List<Workout> getWorkouts() {
        List<Workout> lijst = new ArrayList<Workout>();

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

                lijst.add(workout);
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

    public int getCountCompoundLifts() {
        String selectQuery = "SELECT  * FROM compoundlift";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int aantal = cursor.getCount();

        cursor.close();
        db.close();
        return aantal;
    }
}
