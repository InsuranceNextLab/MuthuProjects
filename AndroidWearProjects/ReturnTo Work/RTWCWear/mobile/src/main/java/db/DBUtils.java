package db;

import android.content.Context;
import android.database.SQLException;

import java.io.IOException;

public class DBUtils {
    private static DatabaseHelper myDbHelper;

    public static DatabaseHelper getDatabaseHelper(Context context) {
        if (myDbHelper == null) {
            myDbHelper = new DatabaseHelper(context);
            try {
                myDbHelper.createDataBase();
            } catch (IOException ioe) {
                throw new Error("Unable to create database");
            }
            try {
                myDbHelper.openDataBase();
            } catch (SQLException sqle) {
                throw sqle;
            }
        }
        return myDbHelper;
    }
}
