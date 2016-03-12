package com.insurancenext.rtwcwear.services;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by ${MUTHU} on 4/20/2015.
 */
public class WearSendToDataLayerThread extends Thread {
    String path;
    DataMap dataMap;
    GoogleApiClient googleClient;
    Activity activity;

    // Constructor for sending data objects to the data layer
    public WearSendToDataLayerThread(Activity activitys, String p, DataMap data, GoogleApiClient GoogleClient) {
        path = p;
        dataMap = data;
        googleClient = GoogleClient;
        activity = activitys;
    }

    public void run() {
        if (googleClient.isConnected()) {
            Log.i("Thread", "Runnable");

            PutDataMapRequest putDMR = PutDataMapRequest.create(path);
            putDMR.getDataMap().putAll(dataMap);
            PutDataRequest request = putDMR.asPutDataRequest();
            Wearable.DataApi.putDataItem(googleClient, request)
                    .setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
                        @Override
                        public void onResult(DataApi.DataItemResult dataItemResult) {
                            Log.d("putDataItem status: ", dataItemResult.getStatus().toString());
                        }
                    });
        }
    }
}
