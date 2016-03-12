package com.cognizant.db;

import android.content.ContentValues;

/**
 * Created by 367025 on 1/5/2015.
 */
public class ClaimFieldList {
    public static final String CLAIMNUMBER = "Claim_number";
    public static final String CLAIMUNIQUEID = "Claim_uniqueId";
    public static final String REPORTEDBY = "Claim_reportedby";
    public static final String ADDRESS = "Claim_address";
    public static final String REPORTEDDT = "Claim_reporteddt";
    public static final String LOSSDATE = "Claim_lossdt";
    public static final String LOSSCAUSE = "Claim_losscause";
    public static final String PRIORLOSS = "Claim_priorloss";
    public static final String STATUS = "Claim_status";
    public static final String CLAIM_TABLE = "Claims_details";
    public static ContentValues claimFieldList = new ContentValues();

    public static ContentValues getClaimFieldList() {
        return claimFieldList;
    }

    public static void setClaimFieldList() {
        claimFieldList.put(CLAIMNUMBER, "Claim Number");
        claimFieldList.put(CLAIMUNIQUEID, "Unique Number");
        claimFieldList.put(REPORTEDDT, "Reported Date");
        claimFieldList.put(REPORTEDBY, "Reported By");
        claimFieldList.put(LOSSCAUSE, "Loss Cause");
        claimFieldList.put(LOSSDATE, "Loss Date");
        claimFieldList.put(PRIORLOSS, "Prior Loss");
        claimFieldList.put(ADDRESS, "Address");
        claimFieldList.put(STATUS, "Status");


    }
}
