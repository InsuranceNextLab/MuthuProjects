package com.cognizant.claimadjustment;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

public class CameraSnap extends Activity implements SurfaceHolder.Callback {

	private static final String TAG = CameraSnap.class.getSimpleName();
	public static final int BUFFER_SIZE = 1024 * 8;
	// values passed in intent
	private String imageFileName = "";
	private int previewWidth = 0;
	private int previewHeight = 0;
	private int snapshotWidth = 0;
	private int snapshotHeight = 0;
	private int maximumWaitTimeForCamera = 0;
	private ImageView iv_image;
	private SurfaceView sv;
	// a bitmap to display the captured image
	private Bitmap bmp;
	// Camera variables
	// a surface holder
	private SurfaceHolder sHolder;
	// a variable to control the camera
	private Camera mCamera;
	// the camera parameters
	private Parameters parameters;
	// toggle for interrupted activity
	private boolean gotInterrupted = false;
	private boolean cameraPreviouslyAcquired = false;
	private GestureDetector mGestureDetector;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		iv_image = (ImageView) findViewById(R.id.imageView);
		sv = (SurfaceView) findViewById(R.id.surfaceView);
		mGestureDetector = createGestureDetector(this);
		// Get a surface
		sHolder = sv.getHolder();
		sHolder.addCallback(this);
		Bundle extras = getIntent().getExtras();
		// save all the values found in the extras...
		imageFileName = extras.getString("imageFileName");
		previewWidth = extras.getInt("previewWidth");
		previewHeight = extras.getInt("previewHeight");
		;
		snapshotWidth = extras.getInt("snapshotWidth");
		snapshotHeight = extras.getInt("snapshotHeight");
		maximumWaitTimeForCamera = extras.getInt("maximumWaitTimeForCamera");
		if (imageFileName.length() == 0 || previewWidth == 0
				|| previewHeight == 0 || snapshotWidth == 0
				|| snapshotHeight == 0 || maximumWaitTimeForCamera == 0) {
			// abandon the activity if extras are not complete
			Log.e(TAG, "Extras specified in the call are invalid");
			Intent resultIntent = new Intent();
			setResult(Activity.RESULT_CANCELED, resultIntent);
			finish();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.v(TAG, "onResume");
		if (gotInterrupted && cameraPreviouslyAcquired) {
			Log.v(TAG, "returned from interrupt by KeyDown");
			if (!getCameraAndSetPreview(sHolder)) {
				Log.e(TAG, "Exception encountered creating surface, exiting");
				mCamera = null;
				Intent resultIntent = new Intent();
				setResult(Activity.RESULT_CANCELED, resultIntent);
				finish();
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_CAMERA) {
			Log.v(TAG, "onKeyDown");
			if (mCamera != null) {
				mCamera.stopPreview();
				mCamera.release();
				mCamera = null;
			}
			gotInterrupted = true;
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		Log.v(TAG, "surfaceChanged");
		// get camera parameters
		try {
			parameters = mCamera.getParameters();
			Log.v(TAG, "got parms");

			// set camera parameters
			parameters.setPreviewSize(previewWidth, previewHeight);
			parameters.setPictureSize(snapshotWidth, snapshotHeight);
			parameters.setPreviewFpsRange(30000, 30000);
			Log.v(TAG, "parms were set");
			mCamera.setParameters(parameters);

			mCamera.startPreview();
			Log.v(TAG, "preview started");

		} catch (Exception e) {
			try {
				mCamera.release();
				Log.e(TAG, "released the camera");
			} catch (Exception ee) {
				// do nothing
				Log.e(TAG, "error releasing camera");
				Log.e(TAG, "Exception encountered releasing camera, exiting:"
						+ ee.getLocalizedMessage());
			}
			Log.e(TAG,
					"Exception encountered, exiting:" + e.getLocalizedMessage());
			mCamera = null;
			Intent resultIntent = new Intent();
			setResult(Activity.RESULT_CANCELED, resultIntent);
			finish();
		}
	}

	public static Bitmap decodeSampledBitmapFromData(byte[] data, int reqWidth,
			int reqHeight) {

		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(data, 0, data.length, options);
		options.inSampleSize = 2;
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeByteArray(data, 0, data.length, options);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		Log.v(TAG, "surfaceCreated");
		if (!getCameraAndSetPreview(holder)) {
			Log.e(TAG, "Exception encountered creating surface, exiting");
			mCamera = null;
			Intent resultIntent = new Intent();
			setResult(Activity.RESULT_CANCELED, resultIntent);
			finish();
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.v(TAG, "surfaceDestroyed");
		if (mCamera != null) {
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}
	}

	@Override
	public void onPause() {
		Log.v(TAG, "onPause");
		super.onPause();
		if (mCamera != null) {
			mCamera.stopPreview();
			// release the camera
			mCamera.release();
			// unbind the camera from this object
			mCamera = null;
		}
	}

	@Override
	public void onDestroy() {
		Log.v(TAG, "onDestroy");
		super.onDestroy();

		if (mCamera != null) {
			mCamera.stopPreview();
			// release the camera
			mCamera.release();
			// unbind the camera from this object
			mCamera = null;
		}
	}

	private boolean getCameraAndSetPreview(SurfaceHolder holder) {
		// get the camera and set the preview surface
		if (getTheCamera(holder)) {
			try {
				mCamera.setPreviewDisplay(holder);
				Log.v(TAG, "surface holder for preview was set");
				cameraPreviouslyAcquired = true;
				return true; // the camera was acquired and the preview surface
								// set
			} catch (Exception e) {
				Log.e(TAG,
						"Exception encountered setting camera preview display:"
								+ e.getLocalizedMessage());
			}
		} else {
			Log.e(TAG, "Exception encountered getting camera, exiting");
			mCamera = null;
		}
		return false;
	}

	private boolean getTheCamera(SurfaceHolder holder) {
		Log.v(TAG, "getTheCamera");
		boolean acquiredCam = false;
		int timePassed = 0;
		while (!acquiredCam && timePassed < maximumWaitTimeForCamera) {
			try {
				mCamera = Camera.open();
				Log.v(TAG, "acquired the camera");
				acquiredCam = true;
				return true;
			} catch (Exception e) {
				Log.e(TAG,
						"Exception encountered opening camera:"
								+ e.getLocalizedMessage());
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException ee) {
				Log.e(TAG,
						"Exception encountered sleeping:"
								+ ee.getLocalizedMessage());
			}
			timePassed += 200;
		}
		return false;
	}

	private GestureDetector createGestureDetector(Context context) {
		// TODO Auto-generated method stub

		GestureDetector gestureDetector = new GestureDetector(context);

		gestureDetector.setBaseListener(new GestureDetector.BaseListener() {

			@Override
			public boolean onGesture(Gesture gesture) {
				// TODO Auto-generated method stub

				if (gesture == Gesture.TAP) {
					TakeShot();
					return true;

				}
				if (gesture == Gesture.SWIPE_DOWN) {
					finish();
				}

				return false;
			}

		});
		gestureDetector.setFingerListener(new GestureDetector.FingerListener() {

			@Override
			public void onFingerCountChanged(int previousCount, int currentCount) {
				// TODO Auto-generated method stub

				Log.i("onFingerCountChanged", "Listeener");

			}
		});
		gestureDetector.setScrollListener(new GestureDetector.ScrollListener() {

			@Override
			public boolean onScroll(float displacement, float delta,
					float velocity) {
				// TODO Auto-generated method stub

				Log.i("onScroll", "Listeener");
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

	private void TakeShot() {
		// sets what code should be executed after the picture is taken
		Camera.PictureCallback mCall = new Camera.PictureCallback() {
			public void onPictureTaken(byte[] data, Camera camera) {
				Log.v(TAG, "pictureTaken");
				Log.v(TAG, "data bytes=" + data.length);
				// decode the data obtained by the camera into a Bitmap
				Bitmap bmp = decodeSampledBitmapFromData(data, 640, 360);
				Log.v(TAG,
						"bmp width=" + bmp.getWidth() + " height="
								+ bmp.getHeight());
				FileOutputStream outStream = null;
				try {
					FileOutputStream fos = new FileOutputStream(imageFileName);
					final BufferedOutputStream bos = new BufferedOutputStream(
							fos, BUFFER_SIZE);
					bmp.compress(CompressFormat.JPEG, 100, bos);
					bos.flush();
					bos.close();
					fos.close();
				} catch (FileNotFoundException e) {
					Log.v(TAG, e.getMessage());
				} catch (IOException e) {
					Log.v(TAG, e.getMessage());
				}
				Intent resultIntent = new Intent();
				// TODO Add extras or a data URI to this intent as
				// appropriate.
				resultIntent.putExtra("testString", "here is my test");
				setResult(Activity.RESULT_OK, resultIntent);
				finish();
			}
		};
		Log.v(TAG, "set callback");
		mCamera.takePicture(null, null, mCall);
	}
}
