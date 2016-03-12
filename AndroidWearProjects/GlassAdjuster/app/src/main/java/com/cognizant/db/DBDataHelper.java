package com.cognizant.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cognizant.glassadjuster.Claimlist;

import java.util.ArrayList;

import static com.cognizant.db.ClaimFieldList.ADDRESS;
import static com.cognizant.db.ClaimFieldList.CLAIMNUMBER;
import static com.cognizant.db.ClaimFieldList.CLAIMUNIQUEID;
import static com.cognizant.db.ClaimFieldList.CLAIM_TABLE;
import static com.cognizant.db.ClaimFieldList.LOSSCAUSE;
import static com.cognizant.db.ClaimFieldList.LOSSDATE;
import static com.cognizant.db.ClaimFieldList.PRIORLOSS;
import static com.cognizant.db.ClaimFieldList.REPORTEDBY;
import static com.cognizant.db.ClaimFieldList.REPORTEDDT;
import static com.cognizant.db.ClaimFieldList.STATUS;

/**
 * Created by 367025 on 1/5/2015.
 */
public class DBDataHelper {
    private DBHelper dbHelper;
    private Context context;

    public DBDataHelper(Context context) {
        this.dbHelper = DBUtil.getDataBaseHelper(context);
        this.context = context;
    }

    public boolean insertClaim(Claimlist claim) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues claimValues = convertIntoClaimContentValues(claim);
        sqLiteDatabase.insert(CLAIM_TABLE, null, claimValues);

        sqLiteDatabase.close();
        return true;
    }

    public ContentValues convertIntoClaimContentValues(Claimlist claim) {
        ContentValues claimValues = new ContentValues();
        try {
            Log.i("LIST == ", claim.getClaimNumber());
            claimValues.put(CLAIMNUMBER, claim.getClaimNumber());
            claimValues.put(CLAIMUNIQUEID, claim.getUniqueNumber());
            claimValues.put(REPORTEDBY, claim.getReportedby());
            claimValues.put(REPORTEDDT, claim.getReportedDate());
            claimValues.put(LOSSCAUSE, claim.getLosscause());
            claimValues.put(LOSSDATE, claim.getLossDate());
            claimValues.put(ADDRESS, claim.getClaimAddress());
            claimValues.put(STATUS, claim.getClaimStatus());
            claimValues.put(PRIORLOSS, claim.getPriorLoss());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claimValues;
    }

    public boolean checkIfClaimNumberAvailable(String ClaimNumber) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        String selectionQuery = String.format(
                "select %s from  %s where %s = '%s'", CLAIMNUMBER, CLAIM_TABLE,
                CLAIMNUMBER, ClaimNumber);
        Cursor cursor = sqLiteDatabase.rawQuery(selectionQuery, null);
        return cursor.getCount() > 0;
    }

    public int getProfilesCount() {
        String countQuery = "SELECT  * FROM " + CLAIM_TABLE;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    public ArrayList<Claimlist> getClaimById() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String selectionQuery = String.format("select * from  %s", CLAIM_TABLE);
        Cursor cursor = sqLiteDatabase.rawQuery(selectionQuery, null);
        ArrayList<Claimlist> claim = new ArrayList<Claimlist>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            claim = getClaimListObject(cursor);
        }
        cursor.close();
        sqLiteDatabase.close();
        return claim;
    }

    public ArrayList<Claimlist> getClaimListObject(Cursor cursor) {
        ArrayList<Claimlist> claim_details = new ArrayList<Claimlist>();
        Log.i("CursorCount", cursor.getCount() + "" + cursor.toString());
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    Claimlist claim = new Claimlist();
                    Log.i("CLAIM NUMBER DB =", cursor.getString(cursor.getColumnIndex(CLAIMNUMBER)));
                    claim.setClaimNumber(cursor.getString(cursor.getColumnIndex(CLAIMNUMBER)));
                    claim.setUniqueNumber(cursor.getInt(cursor.getColumnIndex(CLAIMUNIQUEID)) + "");
                    claim.setReportedby(cursor.getString(cursor.getColumnIndex(REPORTEDBY)));
                    claim.setClaimAddress(cursor.getString(cursor.getColumnIndex(ADDRESS)));
                    claim.setReportedDate(cursor.getString(cursor.getColumnIndex(REPORTEDDT)));
                    claim.setLossDate(cursor.getString(cursor.getColumnIndex(LOSSDATE)));
                    claim.setLosscause(cursor.getString(cursor.getColumnIndex(LOSSCAUSE)));
                    claim.setPriorLoss(cursor.getInt(cursor.getColumnIndex(PRIORLOSS)) + "");
                    claim.setClaimStatus(cursor.getString(cursor.getColumnIndex(STATUS)));
                    claim_details.add(claim);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return claim_details;
    }


}
