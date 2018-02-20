package ozog.development.feistyball;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "contactsManager";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "create table LOGIN( ID integer primary key autoincrement,FIRSTNAME  " +
                "text,LASTNAME  text,USERNAME text,PASSWORD text);";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS LOGIN");
        onCreate(db);
    }
}
