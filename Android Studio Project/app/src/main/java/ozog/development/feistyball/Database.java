package ozog.development.feistyball;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Database  {

    static private String databaseName;
    static private SQLiteDatabase db;
    static private Context context;

    static public void createDatabase() {
        String sql = "create table LOGIN( ID integer primary key autoincrement,FIRSTNAME  " +
                "text,LASTNAME  text,USERNAME text,PASSWORD text);";
        String databaseName = "db1.db";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/sqlite/db/db1.db")) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
