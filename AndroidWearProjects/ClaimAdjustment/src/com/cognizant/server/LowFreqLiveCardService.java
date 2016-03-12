package com.cognizant.server;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.cognizant.claimadjustment.MainActivity;
import com.cognizant.claimadjustment.R;
import com.google.android.glass.timeline.LiveCard;

public class LowFreqLiveCardService extends Service {

	private static final String TAG = "LowFreqLiveCardService";

	private LiveCard mLiveCard;
	private RemoteViews mLiveCardView;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand");

		if (mLiveCard == null) {

			// Get an instance of a live card
			mLiveCard = new LiveCard(this, TAG);

			// Inflate a layout into a remote view
			mLiveCardView = new RemoteViews(getPackageName(),
					R.layout.live_card);

			// Set up initial RemoteViews values
			mLiveCardView.setTextViewText(R.id.textView1, getResources()
					.getString(R.string.notify));

			Intent menuIntent = new Intent(this, MainActivity.class);
			menuIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TASK);
			mLiveCard.setAction(PendingIntent.getActivity(this, 0, menuIntent,
					0));

			mLiveCard.setViews(mLiveCardView);

			// Publish the live card
			mLiveCard.publish(LiveCard.PublishMode.REVEAL);

			Log.d(TAG, "mLiveCard.publish " + mLiveCard.isPublished());

		} else if (!mLiveCard.isPublished()) {
			mLiveCard.publish(LiveCard.PublishMode.REVEAL);
		}

		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy");

		if (mLiveCard != null && mLiveCard.isPublished()) {
			// Stop the handler from queuing more Runnable jobs

			mLiveCard.unpublish();
			mLiveCard = null;
		}
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "onBind");
		return null;
	}

}
