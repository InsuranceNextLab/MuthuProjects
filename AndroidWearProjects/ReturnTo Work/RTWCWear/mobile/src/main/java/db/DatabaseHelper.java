package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import common.logger.Log;

/**
 * Created by ${MUTHU} on 4/29/2015.
 */


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String TAG = "DataBaseHelper";
    private static final String STEP_TABLE = "stepcount";
    private static final String SIDE_TABLE = "sideraise";
    private static final String FRONT_TABLE = "frontraise";
    private static final String BICEPS_TABLE = "bicepscount";
    private static String DB_PATH = "/data/data/com.cognizant.workactivate/databases/";
    private static String DB_NAME = "WearDB.db";
    private final Context myContext;
    private SQLiteDatabase myDataBase;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.myContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     */
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        Log.d("Database Exists ?", "" + dbExist);
        if (dbExist) {
            // do nothing - database already exist
        } else {

            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            // database does't exist yet.
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {
        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;
        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
        Log.d("Database copied", "");
    }

    public void openDataBase() throws SQLException {
        // Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);

    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
    }

    public long Insertstepcount(String step_count, String step_walked, String datetime) {
        ContentValues cv = new ContentValues();
        cv.put("step_count", step_count);
        cv.put("step_walktime", step_walked);
        cv.put("created_date", datetime);
        long returnId = myDataBase.insert(STEP_TABLE, null, cv);
        if (returnId > 0) {
            return returnId;
        } else {
            return -1;
        }
    }

    public long InsertBicepscount(String step_count, String step_walked, String datetime) {
        ContentValues cv = new ContentValues();
        cv.put("biceps_count", step_count);
        cv.put("biceps_time", step_walked);
        cv.put("created_date", datetime);
        long returnId = myDataBase.insert(BICEPS_TABLE, null, cv);
        if (returnId > 0) {
            return returnId;
        } else {
            return -1;
        }
    }

    public long InsertSidecount(String step_count, String step_walked, String datetime) {
        ContentValues cv = new ContentValues();
        cv.put("sideraise_count", step_count);
        cv.put("sideraise_time", step_walked);
        cv.put("created_date", datetime);
        long returnId = myDataBase.insert(SIDE_TABLE, null, cv);
        if (returnId > 0) {
            return returnId;
        } else {
            return -1;
        }
    }

    public long InsertFrontcount(String step_count, String step_walked, String datetime) {
        ContentValues cv = new ContentValues();
        cv.put("front_raisecount", step_count);
        cv.put("front_raisetime", step_walked);
        cv.put("created_date", datetime);
        long returnId = myDataBase.insert(FRONT_TABLE, null, cv);
        if (returnId > 0) {
            return returnId;
        } else {
            return -1;
        }
    }

    public Cursor getAllItems() {
        String query = "SELECT step_id,step_count,step_walktime,created_date FROM "
                + STEP_TABLE + " order by step_id desc";
        return executeRawQuery(query);
    }

    public Cursor getAllSideRaise() {
        String query = "SELECT sideraise_id,sideraise_count,sideraise_time,created_date FROM "
                + SIDE_TABLE + " order by sideraise_id desc";
        return executeRawQuery(query);
    }

    public Cursor getAllFrontRaise() {
        String query = "SELECT front_raiseId,front_raisecount,front_raisetime,created_date FROM "
                + FRONT_TABLE + " order by front_raiseId desc";
        return executeRawQuery(query);
    }

    public Cursor getAllBicepsCount() {
        String query = "SELECT biceps_id,biceps_count,biceps_time,created_date FROM "
                + BICEPS_TABLE + " order by biceps_id desc";
        return executeRawQuery(query);
    }

    public Cursor executeRawQuery(String query) {
        Cursor mCursor = myDataBase.rawQuery(query, null);
        // Log.d("Question Count 1" , "" + mCursor.getCount());
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
}
