package com.insurancenext.rtwcwear;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.cognizant.workactivate.R;
import com.firebase.client.Firebase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import adapter.MoreAdapter;
import db.DBUtils;
import db.DatabaseHelper;
import db.RTWFireBase;
import domain.Stepcounts;


public class StepcountdownView extends Activity {

    Context context;
    ListView history_list;
    ArrayList<Stepcounts> historylist;
    MoreAdapter myadapter;
    Map<String, Object> data_values;
    Firebase myFirebaseRef, Countref;
    Map<String, Object> sync_values;
    MessageReceiver messageReceiver;
    RTWFireBase firebase_sync;
    private DatabaseHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepcountdown_view);
        history_list = (ListView) findViewById(R.id.hostorylist);
        context = this;
        firebase_sync = new RTWFireBase();
        if (myDbHelper == null) {
            myDbHelper = DBUtils.getDatabaseHelper(getApplicationContext());
        }
        historylist = new ArrayList<Stepcounts>();
        myadapter = new MoreAdapter(historylist, context);
        history_list.setAdapter(myadapter);
        historylist = getAllItems();
        data_values = new HashMap<String, Object>();
        // Register the local broadcast receiver
        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        MessageReceiver messageReceiver = new MessageReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, messageFilter);

    }

    private ArrayList<Stepcounts> getAllItems() {

        Cursor cursor = myDbHelper.getAllItems();
        if (cursor.getCount() > 0) {
            int i = 0;
            historylist.clear();
            do {
                Stepcounts counts = new Stepcounts();
                counts.setStepCount(cursor.getString(cursor.getColumnIndex("step_count")));
                counts.setStepId(cursor.getInt(cursor.getColumnIndex("step_id")));
                counts.setWalkTime(cursor.getString(cursor.getColumnIndex("step_walktime")));
                counts.setdateTime(cursor.getString(cursor.getColumnIndex("created_date")));
                i++;
                historylist.add(counts);
                myadapter.notifyDataSetChanged();
            } while (cursor.moveToNext());
        }
        return historylist;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stepcountdown_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle data = intent.getExtras();
            String stepcount = data.getString("step_count");
            String steptime = data.getString("step_time");
            String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
           // Toast.makeText(context, steptime + "=" + stepcount + "=" + currentDateandTime, Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "Your Step Count  is "+stepcount, Toast.LENGTH_SHORT).show();
            data_values.put("sideraise_count", stepcount);
            data_values.put("sideraise_count_time", steptime);
            data_values.put("current_time", currentDateandTime);
            firebase_sync.SyncCount(context, "StepCount", data_values);
            long last_insertId = myDbHelper.Insertstepcount(stepcount, steptime, currentDateandTime);
            getAllItems();
        }
    }
}
