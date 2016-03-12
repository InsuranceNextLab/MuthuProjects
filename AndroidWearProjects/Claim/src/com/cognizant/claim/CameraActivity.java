package com.cognizant.claim;

import java.io.File;

import org.w3c.dom.Document;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.FileObserver;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.Toast;

import com.cognizant.claim.adapter.ImageUploader;
import com.cognizant.utils.CameraView;
import com.google.android.glass.media.CameraManager;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

public class CameraActivity extends Activity {
	private static final int TAKE_PICTURE_REQUEST = 1;
	private static final int TAKE_VIDEO_REQUEST = 2;
	private GestureDetector mGestureDetector;
	private CameraView cameraView = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		cameraView = new CameraView(this);
		mGestureDetector = createGestureDetector(this);
		this.setContentView(cameraView);
	}

	private GestureDetector createGestureDetector(Context context) {
		// TODO Auto-generated method stub

		GestureDetector gestureDetector = new GestureDetector(context);

		gestureDetector.setBaseListener(new GestureDetector.BaseListener() {

			@Override
			public boolean onGesture(Gesture gesture) {
				// TODO Auto-generated method stub
				if (cameraView != null) {
					// Tap with a single finger for photo
					if (gesture == Gesture.TAP) {
						openOptionsMenu();
						return true;

					}
					if (gesture == Gesture.SWIPE_DOWN) {
						finish();
					}
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
	protected void onResume() {
		super.onResume();

		// Do not hold the camera during onResume
		if (cameraView != null) {
			cameraView.releaseCamera();
		}

		// Set the view
		this.setContentView(cameraView);
	}

	@Override
	protected void onPause() {
		super.onPause();

		// Do not hold the camera during onPause
		if (cameraView != null) {
			cameraView.releaseCamera();
		}
	}

	@Override
	public boolean onGenericMotionEvent(MotionEvent event) {
		if (mGestureDetector != null) {
			return mGestureDetector.onMotionEvent(event);
		}

		return super.onGenericMotionEvent(event);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Handle photos
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == TAKE_PICTURE_REQUEST && resultCode == RESULT_OK) {
			String picturePath = data
					.getStringExtra(CameraManager.EXTRA_PICTURE_FILE_PATH);
			processPictureWhenReady(picturePath);
			String imgpath = Appsettings.fileCreation(picturePath);
			ImageUploader uploader = new ImageUploader(imgpath, handler);
			uploader.execute();
		}

	}

	private final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			String result = msg.getData().getString("Msg");
			Log.i("Handler msg", result + "");
			try {

				// finish();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	};

	/**
	 * Process picture - from example GDK
	 * 
	 * @param picturePath
	 */
	private void processPictureWhenReady(final String picturePath) {
		final File pictureFile = new File(picturePath);

		if (pictureFile.exists()) {
			// The picture is ready; process it.
			String path = Appsettings.fileCreation(picturePath);
			Log.i("Path", path + "");
		} else {
			// The file does not exist yet. Before starting the file observer,
			// you
			// can update your UI to let the user know that the application is
			// waiting for the picture (for example, by displaying the thumbnail
			// image and a progress indicator).

			final File parentDirectory = pictureFile.getParentFile();
			FileObserver observer = new FileObserver(parentDirectory.getPath()) {
				// Protect against additional pending events after CLOSE_WRITE
				// is
				// handled.
				private boolean isFileWritten;

				@Override
				public void onEvent(int event, String path) {
					if (!isFileWritten) {
						// For safety, make sure that the file that was created
						// in
						// the directory is actually the one that we're
						// expecting.
						File affectedFile = new File(parentDirectory, path);
						isFileWritten = (event == FileObserver.CLOSE_WRITE && affectedFile
								.equals(pictureFile));

						if (isFileWritten) {
							stopWatching();

							// Now that the file is ready, recursively call
							// processPictureWhenReady again (on the UI thread).
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									processPictureWhenReady(picturePath);
								}
							});
						}
					}
				}
			};
			observer.startWatching();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.capture_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		// case R.id.btncapture:
		// Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// if (intent != null) {
		// startActivityForResult(intent, TAKE_PICTURE_REQUEST);
		// }
		// return true;
		case R.id.btnsave:

			break;
		case R.id.btncancel:
			finish();
			break;
		default:
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Added but irrelevant
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_CAMERA) {
			// Stop the preview and release the camera.
			// Execute your logic as quickly as possible
			// so the capture happens quickly.
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}
}