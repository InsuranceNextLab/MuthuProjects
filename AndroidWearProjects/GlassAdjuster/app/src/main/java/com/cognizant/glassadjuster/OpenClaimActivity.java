package com.cognizant.glassadjuster;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.cognizant.utils.Appsettings;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class OpenClaimActivity extends Activity {

    TextView txt_policy_no, txt_survey_no, txt_cause_loss, txt_address,
            txt_reported_by, claim_number;
    Bundle data, survey_detilas;
    String Claimno;
    Context con;
    int count = 0;

    String current_date, original_loss_date;
    TextView txt_title, txt_sample1, txt_sample2, txt_sample3, txt_name,
            txt_place;

    private GestureDetector mGestureDetector;

    public static void setLayoutFont(Typeface tf, TextView... params) {
        for (TextView tv : params) {
            tv.setTypeface(tf, 1);
        }
    }

    public static void setLayoutBoldFont(Typeface btf, TextView... params) {
        for (TextView tv : params) {
            tv.setTypeface(btf, 1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openclaim);
        con = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        survey_detilas = new Bundle();
        mGestureDetector = createGestureDetector(this);
        txt_title = (TextView) findViewById(R.id.openclaim_title);
        txt_sample1 = (TextView) findViewById(R.id.sample_txt);
        txt_sample2 = (TextView) findViewById(R.id.sample_txt1);
        txt_sample3 = (TextView) findViewById(R.id.sample_txt2);
        txt_title = (TextView) findViewById(R.id.samplle_claimno);
        txt_name = (TextView) findViewById(R.id.sample_name);
        txt_place = (TextView) findViewById(R.id.sample_place);

        Typeface thin_font = Typeface.createFromAsset(con.getAssets(),
                "Roboto-Thin.ttf");
        Typeface light_font = Typeface.createFromAsset(con.getAssets(),
                "Roboto-Light.ttf");
        /*
         * setLayoutFont(thin_font, txt_title, txt_sample1, txt_sample2,
		 * txt_sample3); setLayoutBoldFont(light_font, txt_name, txt_place);
		 */

        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, -3);
        Date daysBeforeDate = cal.getTime();
        original_loss_date = new SimpleDateFormat("MM-dd-yyy", Locale.UK)
                .format(daysBeforeDate);
    }

    private GestureDetector createGestureDetector(Context context) {
        GestureDetector gestureDetector = new GestureDetector(context);
        // Create a base listener for generic gestures
        gestureDetector.setBaseListener(new GestureDetector.BaseListener() {
            public boolean onGesture(Gesture gesture) {
                if (gesture == Gesture.TAP) {
                    // do something on two finger tap
                    if (Appsettings.isCheckConnectivity(con)) {
                        survey_detilas.putString("claim_number", "CL-187654");
                        survey_detilas.putString("reported_by", "John Waternoose");
                        survey_detilas.putString("cause_loss", "Hail");
                        survey_detilas.putString("loss_date",
                                original_loss_date);
                        survey_detilas.putString("status", "Assigned");
                        Intent surevey_intent = new Intent(con, ViewClaim.class);
                        surevey_intent.putExtras(survey_detilas);
                        startActivity(surevey_intent);
                        return true;
                    } else {

                        Toast.makeText(con,
                                "Please check your Wifi connection",
                                Toast.LENGTH_SHORT).show();
                    }
                } else if (gesture == Gesture.SWIPE_RIGHT) {
                    startActivity(new Intent(con, ClaimListActivity.class));

                } else if (gesture == Gesture.SWIPE_LEFT) {

                } else if (gesture == Gesture.SWIPE_DOWN) {
                    finish();
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
