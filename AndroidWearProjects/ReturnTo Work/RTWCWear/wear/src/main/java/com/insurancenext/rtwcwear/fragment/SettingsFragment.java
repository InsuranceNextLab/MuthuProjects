package com.insurancenext.rtwcwear.fragment;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cognizant.workactivate.R;
import com.insurancenext.rtwcwear.services.StepCounter;
import com.insurancenext.rtwcwear.view.StepCountNotificationView;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by ${MUTHU} on 4/9/2015.
 */
public class SettingsFragment extends Fragment {
    protected StepCounter stepCountMonitor;
    private TextView mCounterText;
    private Timer mAnimationTimer;
    private Handler mHandler;
    private TimerTask mAnimationTask;
    private boolean up = false;
    private Drawable mDownDrawable;
    private Drawable mUpDrawable;
    private StepCountNotificationView notificationview = new StepCountNotificationView();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_layout, container, false);
        Button button = (Button) view.findViewById(R.id.btn);
        this.stepCountMonitor = new StepCounter();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepCountMonitor.terminate();
                notificationview.dismiss();
            }
        });
        return view;
    }
}
