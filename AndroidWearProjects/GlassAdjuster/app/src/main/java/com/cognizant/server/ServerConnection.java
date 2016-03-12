package com.cognizant.server;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.cognizant.db.DBDataHelper;
import com.cognizant.glassadjuster.Claimlist;
import com.cognizant.utils.Appsettings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * Created by 367025 on 12/30/2014.
 */
public class ServerConnection extends AsyncTask<Void, Void, String> {
    Context con;
    JSONObject item_collection;
    InputStream is;
    int i = 0;
    DBDataHelper dBDataHelper;
    private Handler handler;
    private String result = null;

    public ServerConnection(Context context, Handler handler) {
        this.handler = handler;
        con = context;
        dBDataHelper = new DBDataHelper(context);
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            streamdata details = new streamdata();
            is = details.OpenHttpConnection(Appsettings.ClaimList_URL);
            streamtostring convert = new streamtostring();
            result = convert.convertStreamToString(is);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String resultset) {
        super.onPostExecute(resultset);
        try {
            JSONArray jsonObjs = new JSONArray(result);
            ArrayList<Claimlist> claim_list = new ArrayList<Claimlist>();
            for (i = 0; i < jsonObjs.length(); i++) {
                JSONObject itemCollection = jsonObjs.getJSONObject(i);
                Claimlist claim = new Claimlist();
                claim.setClaimNumber(itemCollection.getString("claimnumber"));
                claim.setClaimID(itemCollection.getString("claimId"));
                claim.setUniqueNumber(itemCollection.getString("claim_unique_id"));
                claim.setLosscause(itemCollection.getString("loss_cause"));
                claim.setPriorLoss(itemCollection.getString("prior_loss"));
                claim.setReportedby(itemCollection.getString("reportedby"));
                claim.setReportedDate(itemCollection.getString("reporteddt"));
                claim.setLossDate(itemCollection.getString("lossdt"));
                claim.setPolicyRefNo(itemCollection.getString("police_ref_no"));
                claim.setClaimStatus(itemCollection.getString("status"));
                claim.setClaimAddress(itemCollection.getString("address"));
                claim_list.add(claim);
                dBDataHelper.insertClaim(claim);

            }
            Message msgObj = handler.obtainMessage();
            Bundle b = new Bundle();
            //  b.putParcelable("claim_list", (android.os.Parcelable) jsonObjs);
            b.putString("Msg", "Claim Details Registered Successfully!");
            msgObj.setData(b);
            handler.sendMessage(msgObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
