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

import adapter.BicepsAdapter;
import common.logger.Log;
import db.DBUtils;
import db.DatabaseHelper;
import db.RTWFireBase;
import domain.BicepsCount;


public class BicepsView extends Activity {

    Context context;
    ListView history_list;
    ArrayList<BicepsCount> historylist;
    BicepsAdapter myadapter;
    Map<String, Object> data_values;
    MessageReceiver messageReceiver;
    RTWFireBase firebase_sync;
    private DatabaseHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biceps_view);
        history_list = (ListView) findViewById(R.id.hostorylist);
        context = this;
        firebase_sync = new RTWFireBase();
        if (myDbHelper == null) {
            myDbHelper = DBUtils.getDatabaseHelper(getApplicationContext());
        }
        historylist = new ArrayList<BicepsCount>();
        myadapter = new BicepsAdapter(historylist, context);
        history_list.setAdapter(myadapter);
        historylist = getAllItems();
        data_values = new HashMap<String, Object>();

        // Register the local broadcast receiver
        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        messageReceiver = new MessageReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, messageFilter);
    }

    private ArrayList<BicepsCount> getAllItems() {

        Cursor cursor = myDbHelper.getAllBicepsCount();
        //Toast.makeText(context, cursor.getCount() + "", Toast.LENGTH_SHORT).show();
        if (cursor.getCount() > 0) {
            int i = 0;
            historylist.clear();
            do {
                BicepsCount counts = new BicepsCount();
                counts.setBicepsCount(cursor.getString(cursor.getColumnIndex("biceps_count")));
                counts.setBicepsId(cursor.getInt(cursor.getColumnIndex("biceps_id")));
                counts.setBicepsTime(cursor.getString(cursor.getColumnIndex("biceps_time")));
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
        getMenuInflater().inflate(R.menu.menu_biceps_view, menu);
        return true;
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
            Toast.makeText(context, "Your Biceps Count is "+stepcount, Toast.LENGTH_SHORT).show();
            data_values.put("count", stepcount);
            data_values.put("count_time", steptime);
            data_values.put("current_time", currentDateandTime);
            Log.d("Biceps", "Biceps");
            firebase_sync.SyncCount(context, "biceps", data_values);
            long last_insertId = myDbHelper.InsertBicepscount(stepcount, steptime, currentDateandTime);
            getAllItems();


        }
    }
}
