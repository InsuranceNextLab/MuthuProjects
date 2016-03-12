package com.cognizant.claimadjustment;

import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;

public class WeatherDetailsActivity extends Activity {
	Context context;
	private GestureDetector mGestureDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather_report);
		context = this;
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		mGestureDetector = createGestureDetector();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.weather, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.btnlossdate:
			startActivity(new Intent(context, NoHail.class));
			break;
		case R.id.btnhistory:
			startActivity(new Intent(context, HailFall.class));
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private GestureDetector createGestureDetector() {
		GestureDetector gestureDetector = new GestureDetector(context);
		// Create a base listener for generic gestures
		gestureDetector.setBaseListener(new GestureDetector.BaseListener() {
			public boolean onGesture(Gesture gesture) {
				if (gesture == Gesture.TAP) {
					// do something on two finger tap

					return true;
				} else if (gesture == Gesture.SWIPE_RIGHT) {
					openOptionsMenu();
				} else if (gesture == Gesture.SWIPE_LEFT) {
					// startActivity(new Intent(context, Survey.class));
					finish();
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
