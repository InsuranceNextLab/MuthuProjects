package com.cognizant.db;

import android.content.Context;

import java.io.File;
import java.io.IOException;

public class DBUtil {

    private static DBHelper dbHelper;

    public static DBHelper getDataBaseHelper(Context context) {
        if (dbHelper == null) {
            dbHelper = new DBHelper(context);
            try {
                dbHelper.createDatabase();
            } catch (IOException e) {
                throw new Error("Error in creating Database");
            }
        }
        return dbHelper;
    }

    public static void DeleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                DeleteRecursive(child);
        fileOrDirectory.delete();
    }
}
