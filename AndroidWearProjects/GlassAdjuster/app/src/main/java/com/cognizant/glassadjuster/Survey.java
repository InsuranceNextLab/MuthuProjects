package com.cognizant.glassadjuster;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;

import com.cognizant.utils.Appsettings;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

import org.json.JSONObject;

import java.util.ArrayList;

public class Survey extends Activity {

    private static final int TAKE_VIDEO_REQUEST = 2;
    Context context;
    TextView survey_date, claim_number;
    Bundle data, survey_detilas;
    JSONObject obj;
    ProgressDialog dialog;
    String claimnumber, surveydate, claimant_name, loss_date, extend_damage,
            claim_status;
    ClaimData claim_details1;
    private GestureDetector mGestureDetector;
    private ArrayList<String> surveydetails;
    private long cardId = -1L; // ???

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        context = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        data = getIntent().getExtras();
        survey_detilas = new Bundle();
        mGestureDetector = createGestureDetector(this);
        claim_details1 = new ClaimData();
        survey_date = (TextView) findViewById(R.id.txtdate);
        survey_date.setText(data.getString("current_date"));
        Appsettings.putString(context, "Claim_number",
                data.getString("claim_number"));
        claimnumber = data.getString("claim_number");
        surveydate = data.getString("current_date");
        claimant_name = data.getString("claimant_name");
        loss_date = data.getString("loss_date");

    }

    private GestureDetector createGestureDetector(Survey claimdetails_info) {
        GestureDetector gestureDetector = new GestureDetector(context);
        // Create a base listener for generic gestures
        gestureDetector.setBaseListener(new GestureDetector.BaseListener() {
            public boolean onGesture(Gesture gesture) {
                if (gesture == Gesture.TAP) {
                    // openOptionsMenu();
                    return true;
                } else if (gesture == Gesture.SWIPE_RIGHT) {

                    survey_detilas.putString("claim_number",
                            data.getString("claim_number"));
                    survey_detilas.putString("current_date",
                            data.getString("current_date"));
                    survey_detilas.putString("claimant_name",
                            data.getString("claimant_name"));
                    survey_detilas.putString("loss_date",
                            data.getString("loss_date"));
                    Intent surevey_intent = new Intent(context,
                            ExtentActivity.class);
                    surevey_intent.putExtras(survey_detilas);
                    startActivity(surevey_intent);
                    // do something on right (forward) swipe

                } else if (gesture == Gesture.SWIPE_LEFT) {

                } else if (gesture == Gesture.SWIPE_DOWN) {
                    // finish();
                }
                return false;
            }

        });

        return gestureDetector;

    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (mGestureDetector != null) {
            return mGestureDetector.onMotionEvent(event);
        }
        return super.onGenericMotionEvent(event);
    }

}
