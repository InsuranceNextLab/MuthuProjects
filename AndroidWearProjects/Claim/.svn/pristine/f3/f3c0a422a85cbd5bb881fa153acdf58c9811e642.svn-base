package com.cognizant.claim;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;
import com.google.android.glass.touchpad.GestureDetector.BaseListener;

public class ClaimInfo extends Activity {

	private GestureDetector mGestureDetector;
	Context con;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_claim_info);
		mGestureDetector = createGestureDetector(this);
		con = this;
	}

	private GestureDetector createGestureDetector(Context con) {
		GestureDetector gestureDetector = new GestureDetector(con);

		gestureDetector.setBaseListener(new BaseListener() {

			@Override
			public boolean onGesture(Gesture gesture) {
				// TODO Auto-generated method stub

				if (gesture == Gesture.SWIPE_LEFT) {
					return true;
				} else if (gesture == Gesture.SWIPE_RIGHT) {
					return true;
				} else if (gesture == Gesture.SWIPE_DOWN) {
					finish();
					return true;
				}
				return false;
			}
		});
		// TODO Auto-generated method stub
		return gestureDetector;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.claim_info, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return super.onOptionsItemSelected(item);
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
