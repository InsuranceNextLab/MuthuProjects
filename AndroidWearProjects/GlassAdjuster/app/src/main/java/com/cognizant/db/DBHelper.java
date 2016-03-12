package com.cognizant.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "GlassAdjuster";
    private static final String DB_PATH = "/data/data/com.cognizant.glassadjuster/databases/";
    private final Context myContext;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

    // Checks whether the database already exists
    private boolean checkDataBaseExists() {
        SQLiteDatabase checkDatabase = null;
        try {
            String dbpath = DB_PATH + DB_NAME;
            checkDatabase = SQLiteDatabase.openDatabase(dbpath, null,
                    SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        if (checkDatabase != null) {
            checkDatabase.close();
        }
        return checkDatabase != null;
    }

    // creates database if it is not present already
    public void createDatabase() throws IOException {
        boolean dbExists = checkDataBaseExists();
        if (dbExists) {

        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
                throw new Error("Error while copying Database");
            }
        }
    }

    public SQLiteDatabase openDataBase() throws SQLException {
        String dbpath = DB_PATH + DB_NAME;
        return SQLiteDatabase.openDatabase(dbpath, null,
                SQLiteDatabase.OPEN_READWRITE);
    }

    private void copyDataBase() throws IOException {
        InputStream inputStream = myContext.getAssets().open(DB_NAME);
        String dbpath = DB_PATH + DB_NAME;
        OutputStream outputStream = new FileOutputStream(dbpath);
        byte[] buffer = new byte[1024];
        int bufflen;
        while ((bufflen = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, bufflen);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

}
