package com.insurancenext.rtwcwear.monitor;

/**
 * Created by ${MUTHU} on 4/11/2015.
 */
public interface StepCountValueListener extends SensorValueListener {
    public void onStepCountChanged(long timestamp, int rate);
}
