package be.thomasmore.repscore;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "RepScore";




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
                "weight TEXT," +
                "date DATE, " +
                "compoundliftId INTEGER, " +
                "FOREIGN KEY (compoundliftId) REFERENCES compoundlift(id))";
        db.execSQL(CREATE_TABLE_WORKOUT);

        insertCompoundLifts(db);
    }

    private void insertCompoundLifts(SQLiteDatabase db) {
        db.execSQL("INSERT INTO compoundlift (id, name) VALUES (1, 'Bench Press');");
        db.execSQL("INSERT INTO compoundlift (id, name) VALUES (2, 'Deadlift');");
        db.execSQL("INSERT INTO compoundlift (id, name) VALUES (3, 'Squat');");
        db.execSQL("INSERT INTO compoundlift (id, name) VALUES (4, 'Military Press');");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS compoundlift");
        db.execSQL("DROP TABLE IF EXISTS workout");

        // Create tables again
        onCreate(db);
    }

}
