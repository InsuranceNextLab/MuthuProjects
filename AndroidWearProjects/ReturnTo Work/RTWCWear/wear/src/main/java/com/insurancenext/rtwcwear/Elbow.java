package com.insurancenext.rtwcwear;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cognizant.workactivate.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.Wearable;
import com.insurancenext.rtwcwear.monitor.Stopwatch;
import com.insurancenext.rtwcwear.services.WearSendToDataLayerThread;
import com.insurancenext.rtwcwear.view.BicepsView;
import com.insurancenext.rtwcwear.view.SplitTimeView;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Elbow extends Activity implements SensorEventListener, Stopwatch.OnTickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, UserInputManager.UserInputListener {

    private static final long TIME_THRESHOLD_NS = 2000000000; // in nanoseconds (= 2sec)
    private static final float POSITIVE_GRAVITY_THRESHOLD = 6.0f;
    private static final float NEGATIVE_GRAVITY_THRESHOLD = -7.0f;
    protected Stopwatch stopwatch;
    float standardGravity;
    float thresholdGraqvity;
    GoogleApiClient googleClient;
    DataMap datamap;
    long time_count;
    Context context;
    Elbow activity;
    ImageButton btnstart, btnstop;
    private TextView mTextView;
    private SensorManager senSensorManager;
    private Sensor myGravitySensor;
    private boolean track_flag = false, up_flag = false;
    private long mLastTime = 0;
    private int bicepsCounter = 0;
    private BicepsView notificationView = new BicepsView();
    private SplitTimeView splitTimeView = new SplitTimeView();
    private UserInputManager userInputManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elbow);
        context = this;
        activity = this;
        userInputManager = new UserInputManager(this);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub_elbow);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.biceps);
                splitTimeView.initialize((TextView) stub.findViewById(R.id.txttimer));
                userInputManager.initTouch(context,
                        (LinearLayout) stub.findViewById(R.id.main_layout));
                btnstart = (ImageButton) stub.findViewById(R.id.start);
                btnstop = (ImageButton) stub.findViewById(R.id.stop);
                userInputManager.initButtons(btnstart, btnstop);
            }
        });

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        myGravitySensor = senSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        /*final List<Sensor> deviceSensors = senSensorManager.getSensorList(Sensor.TYPE_ALL);
        for(Sensor type : deviceSensors){
            Log.e("sensors",type.getStringType());
        }*/
        standardGravity = SensorManager.GRAVITY_EARTH;
        thresholdGraqvity = standardGravity / 2;
        Log.d("thresholdGraqvity = ",thresholdGraqvity+"");
        if (this.myGravitySensor == null) {
            Toast.makeText(this, "GravitySensor Not Available", Toast.LENGTH_SHORT).show();
            return;
        }
        this.stopwatch = new Stopwatch(1000L, this);
        // Build a new GoogleApiClient
        googleClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        if (!googleClient.isConnected()) {
            googleClient.connect();
        }
        datamap = new DataMap();
        activity = this;

        this.notificationView.initialize(this, Elbow.class);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor source = event.sensor;
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        Log.d("XYZ Values", x + " == " + y + " == " + z);
       /* if (source.getType() == Sensor.TYPE_GRAVITY) {
            detectBiceps(event.values[0], event.timestamp);

        }*/
    }

    private void detectBiceps(float xValue, long timestamp) {
        Log.d("XValue", "= " + xValue);

        if (up_flag && xValue > POSITIVE_GRAVITY_THRESHOLD) {
            bicepsCounter++;
            mTextView.setText(String.valueOf(bicepsCounter));
            up_flag = false;
        } else if (xValue < NEGATIVE_GRAVITY_THRESHOLD) {
            up_flag = true;

        }


    }

    protected void showNotification(long elapsed) {
        this.notificationView.show(elapsed);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        senSensorManager.unregisterListener(this);
        this.notificationView.dismiss();
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, myGravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onTick(long elapsed) {
        long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(elapsed);

        if (timeSeconds > 9) {
            stopWorkout();
            btnstop.setVisibility(View.GONE);
            btnstart.setVisibility(View.VISIBLE);
            this.splitTimeView.setTime(0).refresh();


        }
        this.splitTimeView.setTime(elapsed);
        time_count = elapsed;
        this.runOnUiThread(this.splitTimeView);
        if (timeSeconds >= 9 && bicepsCounter > 0) {
            this.showNotification(bicepsCounter);
        }

    }
    public void onConnected(Bundle bundle) {

    }


    public void onConnectionSuspended(int i) {

    }

    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    protected void startWorkout() {
        senSensorManager.registerListener(this, myGravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        this.splitTimeView.setTime(0)
                .refresh();
        this.mTextView.setText(String.valueOf(0));
        this.stopwatch.start();

    }

    protected void stopWorkout() {
        this.stopwatch.stop();
        if (bicepsCounter > 0) {
            datamap.putString("step_count", bicepsCounter + "");
            datamap.putString("step_time", DateUtils.formatElapsedTime(time_count / 1000) + "");
            new WearSendToDataLayerThread(this, "/Step_Count", datamap, googleClient).start();
        }
        bicepsCounter = 0;
        this.notificationView.dismiss();
        senSensorManager.unregisterListener(this);
    }

    @Override
    public void onUserStart() {
        this.startWorkout();
    }

    @Override
    public void onUserStop() {
        this.stopWorkout();
    }
}
