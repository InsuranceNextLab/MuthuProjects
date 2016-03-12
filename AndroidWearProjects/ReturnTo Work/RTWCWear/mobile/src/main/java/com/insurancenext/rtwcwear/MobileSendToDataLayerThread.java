package com.insurancenext.rtwcwear;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import common.logger.Log;

/**
 * Created by ${MUTHU} on 4/20/2015.
 */
public class MobileSendToDataLayerThread extends Thread {
    String path;
    DataMap dataMap;
    GoogleApiClient googleClient;
    Context con;

    // Constructor for sending data objects to the data layer
    public MobileSendToDataLayerThread(String p, DataMap data, GoogleApiClient GoogleClient,Context context) {
        path = p;
        dataMap = data;
        googleClient = GoogleClient;
        con = context;
        Log.i("=============================================Mobile","Start");
    }

    public void run() {
        try{
        NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(googleClient).await();
        for (Node node : nodes.getNodes()) {
            // Construct a DataRequest and send over the data layer
            PutDataMapRequest putDMR = PutDataMapRequest.create(path);
            putDMR.getDataMap().putAll(dataMap);
            PutDataRequest request = putDMR.asPutDataRequest();
            DataApi.DataItemResult result = Wearable.DataApi.putDataItem(googleClient, request).await();
            if (result.getStatus().isSuccess()) {
                Log.i("isSuccess===============", "DataMap: " + dataMap + " sent to: " + node.getDisplayName());

            } else {
                // Log an error
                Log.i("isFailed=================", "ERROR: failed to send DataMap");

            }
        }
    }catch (Exception e)
        {
        e.printStackTrace();
        }
    }

}
