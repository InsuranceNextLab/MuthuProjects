package com.insurancenext.rtwcwear.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cognizant.workactivate.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.Wearable;
import com.insurancenext.rtwcwear.StepcountActivity;
import com.insurancenext.rtwcwear.UserInputManager;
import com.insurancenext.rtwcwear.monitor.StepCountValueListener;
import com.insurancenext.rtwcwear.monitor.Stopwatch;
import com.insurancenext.rtwcwear.services.StepCounter;
import com.insurancenext.rtwcwear.services.WearSendToDataLayerThread;
import com.insurancenext.rtwcwear.view.SplitTimeView;
import com.insurancenext.rtwcwear.view.StepCountNotificationView;
import com.insurancenext.rtwcwear.view.StepCountView;

import java.util.concurrent.TimeUnit;

public class MainFragment extends Fragment
        implements Stopwatch.OnTickListener, ServiceConnection, StepCountValueListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, UserInputManager.UserInputListener {
    private static final String TAG = "MainFragment";
    protected Stopwatch stopwatch;
    protected SharedPreferences prefs;
    protected StepCounter stepCounterMonitor;
    int final_stepcount = 0;
    GoogleApiClient googleClient;
    DataMap datamap;
    long time_count;
    ImageButton btnstart, btnstop;
    Activity activity;
    Context appContext;
    private SensorManager sensorManager;
    private SplitTimeView splitTimeView = new SplitTimeView();
    private StepCountView stepCountView = new StepCountView();
    private StepCountNotificationView notificationView = new StepCountNotificationView();
    private UserInputManager userInputManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        activity = this.getActivity();
        appContext = activity.getApplicationContext();
        this.sensorManager = (SensorManager) activity.getSystemService(Activity.SENSOR_SERVICE);
        Sensor sensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (sensor != null) {
            Log.d("BindService", "Service Started");
            Intent intent = new Intent(activity, StepCounter.class);
            appContext.bindService(intent, this, Context.BIND_AUTO_CREATE);
            this.stepCounterMonitor = new StepCounter(); // temporary
        }
        this.stopwatch = new Stopwatch(1000L, this);

        // Build a new GoogleApiClient
        googleClient = new GoogleApiClient.Builder(activity)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        if (!googleClient.isConnected()) {
            googleClient.connect();
        }
        datamap = new DataMap();

        this.notificationView.initialize(appContext, StepcountActivity.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.counter_layout, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
        this.initializeUI();
        // this.voiceAction(savedInstanceState);
    }

    protected void initializeUI() {
        Activity activity = this.getActivity();
        btnstart = (ImageButton) activity.findViewById(R.id.start);
        btnstop = (ImageButton) activity.findViewById(R.id.stop);
        this.splitTimeView.initialize((TextView) activity.findViewById(R.id.txttimer));
        this.stepCountView.initialize((TextView) activity.findViewById(R.id.counter));

        this.userInputManager = new UserInputManager(this)
                .initTouch(activity,
                        (LinearLayout) activity.findViewById(R.id.main_layout))
                .initButtons(btnstart, btnstop);


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putLong("start_time", this.stopwatch.getStartTime());

        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: " + outState.getLong("start_time"));
    }


    /* private void voiceAction(Bundle savedInstanceState) {
         String actionStatus =
                 this.getActivity().getIntent().getStringExtra("actionStatus");
         if (actionStatus == null) {
             return;
         }

         if (actionStatus.equals("ActiveActionStatus")) {
             //  this.startWorkout();
         } else if (actionStatus.equals("CompletedActionStatus")) {
             if (savedInstanceState == null) {
                 Log.d(TAG, "savedInstanceState is null");
                 return;
             }
             if (savedInstanceState.getLong("start_time") == 0) {
                 Log.d(TAG, "Not started:");
                 return;
             }
             Log.d(TAG, "workout stop");
             // this.stopWorkout();
         }

     }*/
    protected void startWorkout() {

        this.stepCountView.setStepCount(0)
                .refresh();
        this.splitTimeView.setTime(0)
                .refresh();
        this.stopwatch.start();
    }

    protected void stopWorkout() {
        this.showNotification(final_stepcount);
        this.stopwatch.stop();
        this.stepCountView.setStepCount(0)
                .refresh();
        if (final_stepcount > 0) {
            datamap.putString("step_count", final_stepcount + "");
            datamap.putString("step_time", DateUtils.formatElapsedTime(time_count / 1000) + "");
            new WearSendToDataLayerThread(getActivity(), "/Step_Count", datamap, googleClient).start();
        }
        this.notificationView.dismiss();

    }

    public void onStepCountChanged(long timestamp, int stepCount) {
        if (!this.stopwatch.isRunning()) {
            return;
        }
        Toast.makeText(getActivity(), "StepChanged", Toast.LENGTH_SHORT).show();
        this.stepCountView.setStepCount(stepCount);
        this.getActivity().runOnUiThread(this.stepCountView);
        final_stepcount = stepCount;
    }

    // Stopwatch.OnTickListener
    @Override
    public void onTick(long elapsed) {
        long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(elapsed);
        this.splitTimeView.setTime(elapsed);
        time_count = elapsed;
        this.getActivity().runOnUiThread(this.splitTimeView);
        Log.i("elapsed", elapsed + "");
        //this.showNotification(final_stepcount);
    }

    protected void showNotification(long elapsed) {
        this.notificationView.show(elapsed);

    }

    @Override
    public void onStart() {
        googleClient.connect();
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.stopWorkout();
        if (this.stepCounterMonitor != null) {
            this.stepCounterMonitor.terminate();
            this.getActivity().getApplicationContext().unbindService(this);
        }
        this.notificationView.dismiss();
        if (googleClient.isConnected()) {
            googleClient.disconnect();
        }

    }


    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Log.d("onServiceConnected: ", componentName.toString());
        if (iBinder instanceof StepCounter.StepCountBinder) {
            this.stepCounterMonitor = ((StepCounter.StepCountBinder) iBinder).getService();
            Log.d("ServiceConnection", "Connected");
            this.stepCounterMonitor.init(this.getActivity(), sensorManager, this);
        }
    }

    public void onServiceDisconnected(ComponentName name) {
        Log.d("onServiceDisconnected: ", name.toString());

    }

    // Send a message when the data layer connection is successful.
    @Override
    public void onConnected(Bundle connectionHint) {
        String message = "Hello wearable\n Via the data layer";
        //Requires a new thread to avoid blocking the UI
        Toast.makeText(getActivity(), "Connected", Toast.LENGTH_SHORT).show();
    }

    // Disconnect from the data layer when the Activity stops


    @Override
    public void onStop() {
        if (null != googleClient && googleClient.isConnected()) {
            googleClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

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
