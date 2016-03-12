package com.cognizant.glassadjuster;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cognizant.adpter.ClaimlistAdapter;
import com.cognizant.db.DBDataHelper;
import com.cognizant.server.ServerConnection;
import com.cognizant.utils.Appsettings;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class ClaimListActivity extends Activity
        implements AdapterView.OnItemClickListener {
    static ProgressDialog dialog;
    static Context con;
    protected final Handler handler;
    {
        handler = new Handler() {

            public void handleMessage(Message msg) {
                dialog.dismiss();
                setadapter();
            }
        };
    }

    int[] iconlist = new int[]{R.drawable.claimant1, R.drawable.claimant2,
            R.drawable.claimant3, R.drawable.claimant3, R.drawable.claimant2,
            R.drawable.claimant1};

    ListView claimdetails;
    Context context;
    ArrayList<Claimlist> claim_list;
    ClaimlistAdapter claimdetails_adapter;
    TextView subtitle;
    ArrayList<String> headers;
    int count = 0;
    DBDataHelper dBDataHelper;
    private GestureDetector mGestureDetector;
    private String listcount, original_loss_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_list);
        context = this;
        dBDataHelper = new DBDataHelper(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, -3);
        Date daysBeforeDate = cal.getTime();
        original_loss_date = new SimpleDateFormat("MM-dd-yyy", Locale.UK)
                .format(daysBeforeDate);
        mGestureDetector = createGestureDetector(this);
        claimdetails = (ListView) findViewById(R.id.claim_details);
        int profile_counts = dBDataHelper.getProfilesCount();
        if (profile_counts == 0) {
            syncItems();
        } else {
            setadapter();
        }
    }

    private GestureDetector createGestureDetector(ClaimListActivity claimdetails_info) {
        GestureDetector gestureDetector = new GestureDetector(context);
        // Create a base listener for generic gestures
        gestureDetector.setBaseListener(new GestureDetector.BaseListener() {
            public boolean onGesture(Gesture gesture) {
                if (gesture == Gesture.TWO_TAP) {
                    // do something on two finger tap
                    return true;
                } else if (gesture == Gesture.SWIPE_RIGHT) {
                    if (claimdetails.getSelectedItemPosition() < claimdetails
                            .getCount() - 1) {

                        claimdetails.setSelection(claimdetails
                                .getSelectedItemPosition() + 1);

                        claimdetails
                                .playSoundEffect(SoundEffectConstants.NAVIGATION_UP);
                        return true;
                    }
                    // do something on right (forward) swipe

                } else if (gesture == Gesture.SWIPE_LEFT) {
                    if (claimdetails.getSelectedItemPosition() > 0) {
                        claimdetails.setSelection(claimdetails
                                .getSelectedItemPosition() - 1);
                        claimdetails
                                .playSoundEffect(SoundEffectConstants.NAVIGATION_DOWN);
                        return true;
                    }
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

        if (mGestureDetector != null) {
            return mGestureDetector.onMotionEvent(event);
        }
        return super.onGenericMotionEvent(event);
    }

    private void syncItems() {
        if (Appsettings.isCheckConnectivity(context)) {
            showDialog(0);
            ServerConnection connector = new ServerConnection(context, handler);
            connector.execute();
        } else {
            Toast.makeText(context, "Please Check Your Internet Connectivity", Toast.LENGTH_LONG).show();
        }

    }

    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 0: {
                dialog = new ProgressDialog(this);
                dialog.setMessage("Please wait Sync From Server...");
                dialog.setIndeterminate(true);
                dialog.setCancelable(true);
                return dialog;
            }
            case 1: {
                dialog = new ProgressDialog(this);
                dialog.setMessage("Please wait while downloading...");
                dialog.setIndeterminate(true);
                dialog.setCancelable(true);
                return dialog;
            }
        }
        return null;
    }

    private void setadapter() {
        claim_list = dBDataHelper.getClaimById();
        claimdetails_adapter = new ClaimlistAdapter(this, claim_list);
        claimdetails.setAdapter(claimdetails_adapter);
        claimdetails.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // TODO Auto-generated method stub
        Bundle data = new Bundle();
        data.putString("claim_number", claim_list.get(position)
                .getClaimNumber());
        data.putString("cause_loss", claim_list.get(position).getLosscause());
        data.putString("address", claim_list.get(position).getClaimAddress());
        data.putString("reported_by", claim_list.get(position).getReportedby());
        data.putString("claim_number", claim_list.get(position).getClaimNumber());
        data.putString("loss_date", claim_list.get(position).getLossDate());
        data.putString("status", claim_list.get(position).getClaimStatus());
        Intent intent = new Intent(this, ViewClaim.class);
        intent.putExtras(data);
        startActivity(intent);

    }

}
