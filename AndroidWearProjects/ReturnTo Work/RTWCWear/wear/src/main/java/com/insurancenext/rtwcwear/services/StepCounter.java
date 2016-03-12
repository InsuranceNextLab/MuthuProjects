package com.insurancenext.rtwcwear.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.insurancenext.rtwcwear.entity.SensorValue;
import com.insurancenext.rtwcwear.fragment.MainFragment;

/**
 * Created by ${MUTHU} on 4/13/2015.
 */
public class StepCounter extends Service implements SensorEventListener {
    String TAG = "StepCounter Services";
    private long currentStep = 0;
    private MainFragment listener;
    private float initialValue = 0;
    private SensorValue currentValue = new SensorValue(0, 0f);
    private Context context;
    private SensorManager sensorManager;
    private Sensor stepCountSensor;
    private StepCountBinder binder = new StepCountBinder();

    public void init(Context context,
                     SensorManager sensorManager,
                     MainFragment listener) {

        this.context = context;
        this.sensorManager = sensorManager;
        this.listener = listener;
        Log.d("Service Initiated", "initiated");
        this.stepCountSensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (this.stepCountSensor == null) {
            Log.w(TAG, "no Step Cound sensor");
            return;
        }
        this.sensorManager.registerListener(this,
                this.stepCountSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.binder;
    }

    public void terminate() {
        if (this.sensorManager == null) {
            return;
        }
        this.sensorManager.unregisterListener(this, this.stepCountSensor);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Log.d("OnSensorChanged", "Changed = 0");
        switch (sensorEvent.accuracy) {
            case SensorManager.SENSOR_STATUS_ACCURACY_HIGH:
            case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM:
            case SensorManager.SENSOR_STATUS_ACCURACY_LOW:
                break;
            default:
                return;
        }

        long newTimestamp = sensorEvent.timestamp / (1000 * 1000);
        float newValue = sensorEvent.values[0];
        Log.d("OnSensorChanged", newValue + "");
        if (this.initialValue == 0) {
            this.initialValue = newValue;
        } else if (newValue != this.currentValue.getValue()) {
            float steps = newValue - this.initialValue;
            this.listener.onStepCountChanged(newTimestamp, (int) steps);
            Log.d("Step Count", steps + "");
        }

        this.currentValue.setTimestamp(newTimestamp)
                .setValue(newValue);
        Log.d("Comparision", newValue + "==" + currentValue.getValue());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public class StepCountBinder extends Binder {

        public StepCounter getService() {
            return StepCounter.this;
        }
    }
}