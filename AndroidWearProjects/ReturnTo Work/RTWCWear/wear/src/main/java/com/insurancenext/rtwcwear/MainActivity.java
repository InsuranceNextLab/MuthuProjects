package com.insurancenext.rtwcwear;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cognizant.workactivate.R;
import com.insurancenext.rtwcwear.entity.Item;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity implements WearableListView.ClickListener {

    private final String[] exercise_name = new String[]{"Side Raise", "Front Raise", "Biceps", "Step Countdown"};
    private final int[] excrise_icons = new int[]{R.drawable.jump_up_50, R.drawable.rotate, R.drawable.ellbow, R.drawable.stepcount};
    ArrayList<HashMap<String, String>> menu_list;
    MessageReceiver messageReceiver;
    private WearableListView mListView;
    private float mDefaultCircleRadius;
    private float mSelectedCircleRadius;
    private MyListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDefaultCircleRadius = getResources().getDimension(R.dimen.default_settings_circle_radius);
        mSelectedCircleRadius = getResources().getDimension(R.dimen.selected_settings_circle_radius);
        menu_list = buildMenulist();
        mAdapter = new MyListAdapter();

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mListView = (WearableListView) stub.findViewById(R.id.listView1);
                mListView.setAdapter(mAdapter);
                mListView.setClickListener(MainActivity.this);
            }


        });

        // Register the local broadcast receiver
        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        messageReceiver = new MessageReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, messageFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver);
    }

    private ArrayList<HashMap<String, String>> buildMenulist() {
        menu_list = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < exercise_name.length; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("menu_name", exercise_name[i]);
            map.put("menu_icons", excrise_icons[i] + "");
            menu_list.add(map);
        }
        return menu_list;
    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        //Toast.makeText(this, String.format("You selected item #%s", viewHolder.getPosition()), Toast.LENGTH_SHORT).show();
        switch (viewHolder.getPosition()) {
            case 0:
                startActivity(new Intent(this, SideRaise.class));
                break;

            case 1:
                startActivity(new Intent(this, FrontRaise.class));
                break;
            case 2:

                startActivity(new Intent(this, Elbow.class));
                break;

            case 3:

                startActivity(new Intent(this, StepcountActivity.class));
                break;
            default:
                break;

        }
    }

    @Override
    public void onTopEmptyRegionClick() {
        Toast.makeText(this, "You tapped Top empty area", Toast.LENGTH_SHORT).show();
    }

    public class MyListAdapter extends WearableListView.Adapter {

        @Override
        public WearableListView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new WearableListView.ViewHolder(new MyItemView(MainActivity.this));
        }

        @Override
        public void onBindViewHolder(WearableListView.ViewHolder viewHolder, int i) {
            MyItemView itemView = (MyItemView) viewHolder.itemView;

            TextView txtView = (TextView) itemView.findViewById(R.id.text);
            txtView.setText(menu_list.get(i).get("menu_name"));
            CircledImageView imgView = (CircledImageView) itemView.findViewById(R.id.image);
            imgView.setImageResource(Integer.parseInt(menu_list
                    .get(i).get("menu_icons")));
        }

        @Override
        public int getItemCount() {
            return menu_list.size();
        }
    }

    private final class MyItemView extends FrameLayout implements Item {

        final CircledImageView imgView;
        final TextView txtView;
        private final int mFadedCircleColor;
        private final int mChosenCircleColor;
        private float mScale;

        public MyItemView(Context context) {
            super(context);
            View.inflate(context, R.layout.row_advanced_item_layout, this);
            imgView = (CircledImageView) findViewById(R.id.image);
            txtView = (TextView) findViewById(R.id.text);
            mFadedCircleColor = getResources().getColor(android.R.color.darker_gray);
            mChosenCircleColor = getResources().getColor(android.R.color.holo_blue_dark);
        }

        @Override
        public float getProximityMinValue() {
            return mDefaultCircleRadius;
        }

        @Override
        public float getProximityMaxValue() {
            return mSelectedCircleRadius;
        }

        @Override
        public float getCurrentProximityValue() {
            return mScale;
        }

        @Override
        public void setScalingAnimatorValue(float value) {
            mScale = value;
            imgView.setCircleRadius(mScale);
            imgView.setCircleRadiusPressed(mScale);
        }

        @Override
        public void onScaleUpStart() {
            imgView.setAlpha(1f);
            txtView.setAlpha(1f);
            imgView.setCircleColor(mChosenCircleColor);
        }

        @Override
        public void onScaleDownStart() {
            imgView.setAlpha(0.5f);
            txtView.setAlpha(0.5f);
            imgView.setCircleColor(mFadedCircleColor);
        }
    }

    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            Log.v("myTag", "Main activity received message: " + message);
            Toast.makeText(context, message + "", Toast.LENGTH_LONG).show();
            // Display message in UI
            //mTextView.setText(message);

            switch (message) {
                case "Side":
                    startActivity(new Intent(context, SideRaise.class));
                    break;
                case "Step":
                    startActivity(new Intent(context, StepcountActivity.class));
                    break;
                case "Front":
                    startActivity(new Intent(context, FrontRaise.class));
                    break;
                case "Elbow":
                    startActivity(new Intent(context, Elbow.class));
                    break;
                default:
                    break;

            }
        }
    }
}
