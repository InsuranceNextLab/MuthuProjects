package com.insurancenext.rtwcwear;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by ${MUTHU} on 4/27/2015.
 */
public class HandheldListener extends WearableListenerService {
    private static final String WEARABLE_DATA_PATH = "/Step_Count";

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        DataMap dataMap;
        for (DataEvent event : dataEvents) {

            // Check the data type
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // Check the data path
                String path = event.getDataItem().getUri().getPath();
                if (path.equals(WEARABLE_DATA_PATH)) {
                    dataMap = DataMapItem.fromDataItem(event.getDataItem()).getDataMap();
                    Bundle data = new Bundle();
                    Intent messageIntent = new Intent();
                    messageIntent.setAction(Intent.ACTION_SEND);
                    data.putString("step_count", dataMap.getString("step_count"));
                    data.putString("step_time", dataMap.getString("step_time"));
                    messageIntent.putExtras(data);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);
                } else {
                    super.onDataChanged(dataEvents);
                }


            }
        }
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
    }
}