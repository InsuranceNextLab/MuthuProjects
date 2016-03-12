package com.cognizant.server;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.cognizant.utils.Appsettings;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

public class ServiceConnector extends AsyncTask<Void, Void, String> {
    JSONObject item_collection;
    InputStream is;
    int Img_upload_flag = 0;
    private Handler handler;
    private String result = null;

    public ServiceConnector(Context context, JSONObject obj,
                            Handler handler) {
        this.handler = handler;
        this.item_collection = obj;
    }

    @Override
    protected String doInBackground(Void... params) {

        try {
            streamdata details = new streamdata();
            is = details.OpenHttpConnection(Appsettings.REGISTER_URL + URLEncoder.encode(item_collection.toString(), "UTF-8"));
           /* is = details.OpenHttpConnection(Appsettings.REGISTER_URL + "?data="
                    + URLEncoder.encode(item_collection.toString(), "UTF-8"));*/
            Log.i("Welcome", Appsettings.REGISTER_URL + URLEncoder.encode(item_collection.toString(), "UTF-8"));
            streamtostring convert = new streamtostring();
            result = convert.convertStreamToString(is);
            Log.i("result", result + "");
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    protected void onPostExecute(String result) {
        Log.i("result", result + "");
        Message msgObj = handler.obtainMessage();
        Bundle b = new Bundle();
        b.putBoolean("data", true);
        b.putString("Msg", "Claim Details Registered Successfully!");
        msgObj.setData(b);
        handler.sendMessage(msgObj);

    }

}
