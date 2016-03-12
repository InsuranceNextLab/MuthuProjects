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
import com.insurancenext.rtwcwear.view.SideRaiseView;
import com.insurancenext.rtwcwear.view.SplitTimeView;

import java.util.concurrent.TimeUnit;

public class SideRaise extends Activity implements SensorEventListener, Stopwatch.OnTickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, UserInputManager.UserInputListener {
    private static final float POSITIVE_GRAVITY_Z_THRESHOLD = 8.0f;
    private static final float NEGATIVE_GRAVITY_X_THRESHOLD = -8.0f;
    protected Stopwatch stopwatch;
    ImageButton btnstart, btnstop;
    float standardGravity;
    float thresholdGraqvity;
    GoogleApiClient googleClient;
    DataMap datamap;
    long time_count;
    Context context;
    SideRaise activity;
    private TextView mTextView;
    private SideRaiseView notificationView = new SideRaiseView();
    private SplitTimeView splitTimeView = new SplitTimeView();
    private UserInputManager userInputManager;
    private SensorManager senSensorManager;
    private Sensor myGravitySensor;
    private int sideraiseCounter = 0;
    private boolean down_flag = false, up_flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_raise);
        context = this;
        activity = this;
        userInputManager = new UserInputManager(this);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);

        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.sideraise);
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
        standardGravity = SensorManager.STANDARD_GRAVITY;
        thresholdGraqvity = standardGravity / 2;
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

        this.notificationView.initialize(this, SideRaise.class);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        senSensorManager.unregisterListener(this);
        this.notificationView.dismiss();
        this.finish();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor source = event.sensor;
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        Log.d("XYZ Values", x + " == " + y + " == " + z);

        if (source.getType() == Sensor.TYPE_GRAVITY) {
            if (up_flag && z > POSITIVE_GRAVITY_Z_THRESHOLD) {
                sideraiseCounter++;
                Log.d("SideraiseCount", sideraiseCounter + "");
                mTextView.setText(String.valueOf(sideraiseCounter));
                up_flag = false;
            } else if (x < NEGATIVE_GRAVITY_X_THRESHOLD) {
                up_flag = true;
                Log.d("UPFLAG", "TRUE");

            }

        }
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
        if(timeSeconds>=9 && sideraiseCounter>0) {
            this.showNotification(sideraiseCounter);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void startWorkout() {
        senSensorManager.registerListener(this, myGravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        this.splitTimeView.setTime(0)
                .refresh();
        this.mTextView.setText(String.valueOf(0));
        this.stopwatch.start();
    }

    protected void showNotification(long elapsed) {
        this.notificationView.show(elapsed);

    }

    protected void stopWorkout() {
        this.stopwatch.stop();
        if (sideraiseCounter > 0) {
            datamap.putString("step_count", sideraiseCounter + "");
            datamap.putString("step_time", DateUtils.formatElapsedTime(time_count / 1000) + "");
            new WearSendToDataLayerThread(this, "/Step_Count", datamap, googleClient).start();
        }
        sideraiseCounter = 0;
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
