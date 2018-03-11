package ozog.development.feistyball;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "bestScores";
    private static final String TABLE_SCORES = "scores";

    private static String KEY_LEVEL_NUMBER = "level";
    private static String KEY_SCORE = "score";

    public static Database bestScores;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creates database
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_SCORES + "("
                + KEY_LEVEL_NUMBER + " INT PRIMARY KEY," + KEY_SCORE + " INT)";
        db.execSQL(CREATE_TASKS_TABLE);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
        onCreate(db);
    }

    // Adds single record to "scores" table
    public void addRecord(int level, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_LEVEL_NUMBER, level);
        values.put(KEY_SCORE, score);

        db.insert(TABLE_SCORES, null, values);
        db.close();
    }

    //Remove table "tasks" from the database
    public void dropTasksTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE " + TABLE_SCORES);
        db.close();
    }

    public void createTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
    }


    // Fetch single record stored in the "scores" table
    public int getRecord(int level) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SCORES, new String[] { KEY_LEVEL_NUMBER, KEY_SCORE }, KEY_LEVEL_NUMBER + "=?",
                new String[] { String.valueOf(level) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        int record = Integer.parseInt(cursor.getString(1));

        return record;
    }

}
