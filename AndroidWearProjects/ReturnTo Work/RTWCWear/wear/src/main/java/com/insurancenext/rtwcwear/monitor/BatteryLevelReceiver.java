package com.insurancenext.rtwcwear.monitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by MUTHU.
 */
public class BatteryLevelReceiver extends BroadcastReceiver {
    private SensorValueListener listener;

    public BatteryLevelReceiver() {
        super();

    }

    public BatteryLevelReceiver(SensorValueListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BATTERY_LOW)) {
            //this.listener.onBatteryLow();
        } else if (intent.getAction().equals(Intent.ACTION_BATTERY_OKAY)) {
            // this.listener.onBatteryOkay();
        }

    }
}
