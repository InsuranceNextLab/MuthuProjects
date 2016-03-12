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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import adapter.SideRaiseAdapter;
import common.logger.Log;
import db.DBUtils;
import db.DatabaseHelper;
import db.RTWFireBase;
import domain.SideRaiseCount;


public class SideRaiseView extends Activity {
    ArrayList<SideRaiseCount> historylist;
    Context context;
    Map<String, Object> data_values;
    MessageReceiver messageReceiver;
    ListView history_list;
    SideRaiseAdapter myadapter;
    RTWFireBase firebase_sync;
    private DatabaseHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_raise_view);
        history_list = (ListView) findViewById(R.id.hostorylist);
        context = this;
        firebase_sync = new RTWFireBase();
        if (myDbHelper == null) {
            myDbHelper = DBUtils.getDatabaseHelper(getApplicationContext());
        }
        historylist = new ArrayList<SideRaiseCount>();
        myadapter = new SideRaiseAdapter(historylist, context);
        history_list.setAdapter(myadapter);
        historylist = getAllItems();
        data_values = new HashMap<String, Object>();
        // Register the local broadcast receiver
        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        messageReceiver = new MessageReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, messageFilter);
    }

    private void setAdapter() {
        myadapter = new SideRaiseAdapter(historylist, context);
        history_list.setAdapter(myadapter);
    }

    private ArrayList<SideRaiseCount> getAllItems() {

        Cursor cursor = myDbHelper.getAllSideRaise();
        if (cursor.getCount() > 0) {
            int i = 0;
            historylist.clear();
            do {
                SideRaiseCount counts = new SideRaiseCount();
               // Toast.makeText(context, cursor.getString(cursor.getColumnIndex("sideraise_count")) + "", Toast.LENGTH_SHORT).show();
                counts.setSideraiseCount(cursor.getString(cursor.getColumnIndex("sideraise_count")));
                counts.setSideRaiseId(cursor.getInt(cursor.getColumnIndex("sideraise_id")));
                counts.setSideRaiseTime(cursor.getString(cursor.getColumnIndex("sideraise_time")));
                counts.setdateTime(cursor.getString(cursor.getColumnIndex("created_date")));
                i++;
                historylist.add(counts);
                myadapter.notifyDataSetChanged();
            } while (cursor.moveToNext());
        }
        return historylist;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_side_raise_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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


    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle data = intent.getExtras();
            String stepcount = data.getString("step_count");
            String steptime = data.getString("step_time");
            String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            //Toast.makeText(context, steptime + "=" + stepcount + "=" + currentDateandTime, Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "Your SideRaise Count is "+stepcount, Toast.LENGTH_SHORT).show();
            data_values.put("count", stepcount);
            data_values.put("count_time", steptime);
            data_values.put("current_time", currentDateandTime);
            Log.d("Sideraie", "Sideraie");
            firebase_sync.SyncCount(context, "sideraise", data_values);
            //txtside_raise.setText("Your SideRaise Count is " + stepcount);
            long last_insertId = myDbHelper.InsertSidecount(stepcount, steptime, currentDateandTime);
            getAllItems();

        }
    }
}
