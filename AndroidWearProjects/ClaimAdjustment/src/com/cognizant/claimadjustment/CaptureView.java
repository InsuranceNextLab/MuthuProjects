package com.cognizant.claimadjustment;

import java.io.File;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cognizant.server.ImageUploader;
import com.cognizant.utils.Appsettings;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

public class CaptureView extends Activity {
	private static final String TAG = MainActivity.class.getSimpleName();
	private static final int TAKE_PHOTO_CODE = 1;
	private static String IMAGE_FILE_NAME = "";
	private boolean picTaken = false;
	// private ProgressBar myProgressBar;
	protected boolean mbActive;
	final Handler myHandler = new Handler(); // handles looking for the returned
												// image file
	private TextToSpeech mSpeech;
	private GestureDetector mGestureDetector;
	ProgressDialog dialog;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.image_view);
		context = this;
		IMAGE_FILE_NAME = Appsettings.finalPath(context);
		TextView tvResult = (TextView) findViewById(R.id.tap_instruction);
		tvResult.setVisibility(View.INVISIBLE);
		mSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
			@Override
			public void onInit(int status) {
				// Do nothing.
			}
		});

		mGestureDetector = createGestureDetector(this);

		Intent intent = new Intent(this, CameraSnap.class);
		intent.putExtra("imageFileName", IMAGE_FILE_NAME);
		intent.putExtra("previewWidth", 640);
		intent.putExtra("previewHeight", 360);
		intent.putExtra("snapshotWidth", 1280);
		intent.putExtra("snapshotHeight", 720);
		intent.putExtra("maximumWaitTimeForCamera", 2000);
		startActivityForResult(intent, 1);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.capture_menu, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	/*
	 * Send generic motion events to the gesture detector
	 */
	@Override
	public boolean onGenericMotionEvent(MotionEvent event) {
		if (mGestureDetector != null) {
			return mGestureDetector.onMotionEvent(event);
		}
		return false;
	}

	private GestureDetector createGestureDetector(Context context) {
		GestureDetector gestureDetector = new GestureDetector(context);
		// Create a base listener for generic gestures
		gestureDetector.setBaseListener(new GestureDetector.BaseListener() {
			@Override
			public boolean onGesture(Gesture gesture) {
				if (gesture == Gesture.TAP) {
					// do something on tap
					Log.v(TAG, "tap");
					// if (readyForMenu) {

					// }
					return true;
				} else if (gesture == Gesture.TWO_TAP) {
					// do something on two finger tap

					return true;
				} else if (gesture == Gesture.SWIPE_RIGHT) {
					// do something on right (forward) swipe
					openOptionsMenu();
					return true;
				} else if (gesture == Gesture.SWIPE_LEFT) {
					// do something on left (backwards) swipe
					return true;
				}
				return false;
			}
		});
		gestureDetector.setFingerListener(new GestureDetector.FingerListener() {
			@Override
			public void onFingerCountChanged(int previousCount, int currentCount) {
				// do something on finger count changes
			}
		});
		gestureDetector.setScrollListener(new GestureDetector.ScrollListener() {
			@Override
			public boolean onScroll(float displacement, float delta,
					float velocity) {
				// do something on scrolling
				return false;
			}
		});
		return gestureDetector;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.btnsave:
			showDialog(1);
			fileUpload();
			return true;
		case R.id.btncancel:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		picTaken = true;
		switch (requestCode) {
		case 1: {
			if (resultCode == Activity.RESULT_OK) {
				// TODO Extract the data returned from the child Activity.
				Log.v(TAG, "onActivityResult");

				File f = new File(IMAGE_FILE_NAME);
				if (f.exists()) {
					Log.v(TAG, "image file from camera was found");

					Bitmap b = BitmapFactory.decodeFile(IMAGE_FILE_NAME);
					Log.v(TAG,
							"bmp width=" + b.getWidth() + " height="
									+ b.getHeight());
					ImageView image = (ImageView) findViewById(R.id.bgPhoto);
					image.setImageBitmap(b);
					TextView tap = (TextView) findViewById(R.id.tap_instruction);
					tap.setVisibility(View.VISIBLE);
				}
			} else {
				Log.v(TAG, "onActivityResult returned bad result code");
				finish();
			}
			break;
		}
		}
	}

	@Override
	protected void onDestroy() {

		// Close the Text to Speech Library
		if (mSpeech != null) {
			mSpeech.stop();
			mSpeech.shutdown();
			mSpeech = null;
			Log.d(TAG, "TTS Destroyed");
		}
		super.onDestroy();
	}

	private void fileUpload() {
		Log.i("Image Path", IMAGE_FILE_NAME + "");
		ImageUploader uploader = new ImageUploader(IMAGE_FILE_NAME, handler);
		uploader.execute();
	}

	private final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			dialog.dismiss();
			String result = msg.getData().getString("Msg");
			Log.i("Handler Error Message", result + "");
			try {
				Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
				finish();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	};

	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		dialog = new ProgressDialog(this);
		switch (id) {
		case 1:
			// dialog.setContentView(R.layout.progressdialog);
			dialog.setMessage("Uploading!...");
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			return dialog;
		default:
			break;
		}
		return dialog;

	}
}
