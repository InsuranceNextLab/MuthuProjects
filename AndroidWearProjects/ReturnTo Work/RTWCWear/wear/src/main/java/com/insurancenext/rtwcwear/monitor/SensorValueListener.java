package com.insurancenext.rtwcwear.monitor;

/**
 * Created by MUTHU.
 */
public interface SensorValueListener {
    public void onStepCountChanged(long timestamp, int stepCount);
}
