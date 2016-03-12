package com.insurancenext.rtwcwear;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.cognizant.workactivate.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataMap;
import com.insurancenext.rtwcwear.fragment.MainFragment;
import com.insurancenext.rtwcwear.fragment.SettingsFragment;

public class StepcountActivity extends Activity {

    private static final String TAG = "JJMainActivity";
    /**
     * How long to keep the screen on when no activity is happening *
     */
    private static final long SCREEN_ON_TIMEOUT_MS = 20000; // in milliseconds
    /**
     * an up-down movement that takes more than this will not be registered as such *
     */
    private static final long TIME_THRESHOLD_NS = 2000000000; // in nanoseconds (= 2sec)
    GoogleApiClient googleClient;
    DataMap datamap;
    private TextView mTextView;
    private ViewPager mPager;
    private MainFragment mCounterPage;
    private SettingsFragment mSettingPage;
    private ImageView mSecondIndicator;
    private ImageView mFirstIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepcount);
        mPager = (ViewPager) findViewById(R.id.pager);
        mFirstIndicator = (ImageView) findViewById(R.id.indicator_0);
        mSecondIndicator = (ImageView) findViewById(R.id.indicator_1);
        final PagerAdapter adapter = new PagerAdapter(getFragmentManager());
        mCounterPage = new MainFragment();
        mSettingPage = new SettingsFragment();
        adapter.addFragment(mCounterPage);
        adapter.addFragment(mSettingPage);
        setIndicator(0);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int i) {
                setIndicator(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        mPager.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
    }

    /**
     * Sets the page indicator for the ViewPager.
     */
    private void setIndicator(int i) {
        switch (i) {
            case 0:
                mFirstIndicator.setImageResource(R.drawable.full_10);
                mSecondIndicator.setImageResource(R.drawable.empty_10);
                break;
            case 1:
                mFirstIndicator.setImageResource(R.drawable.empty_10);
                mSecondIndicator.setImageResource(R.drawable.full_10);
                break;
        }
    }
}
