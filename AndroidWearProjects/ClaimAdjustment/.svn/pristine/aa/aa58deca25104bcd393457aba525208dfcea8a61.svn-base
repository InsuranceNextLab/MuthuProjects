package com.cognizant.claimadjustment;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.cognizant.livestreaming.StreamingActivity;
import com.cognizant.server.ImageUploader;
import com.cognizant.server.LowFreqLiveCardService;
import com.cognizant.server.ServiceConnector;
import com.cognizant.utils.Appsettings;
import com.google.android.glass.content.Intents;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.Toast;

public class ProofOfDemage extends Activity {

	private GestureDetector mGestureDetector;
	Context context;
	private static final int TAKE_VIDEO_REQUEST = 2;
	JSONObject obj;
	ProgressDialog dialog;
	String claimnumber, surveydate, claimant_name, loss_date, extend_damage,
			claim_status;
	ClaimData claim_details1;
	private long cardId = -1L; // ???

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_proofofdemage);
		context = this;
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		mGestureDetector = createGestureDetector();
		claim_details1 = new ClaimData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.survey, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		switch (id) {
		case R.id.capturevideo:
			if (Appsettings.isCheckConnectivity(context)) {
				Appsettings.putString(context, "video_flag", "1");
				Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
				if (intent != null) {
					startActivityForResult(intent, TAKE_VIDEO_REQUEST);
				}
			} else {
				Toast.makeText(context, "Please check your wifi connection",
						Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.captureimage:
			if (Appsettings.isCheckConnectivity(context)) {
				Appsettings.putString(context, "image_flag", "1");
				startActivity(new Intent(context, CaptureView.class));
			} else {
				Toast.makeText(context, "Please check your wifi connection",
						Toast.LENGTH_SHORT).show();
			}
			break;
//		case R.id.livestreaming:
//			startActivity(new Intent(context, StreamingActivity.class));
//			break;
		case R.id.weatherreport:
			startActivity(new Intent(context, WeatherDetailsActivity.class));
			break;
		case R.id.recordmemo:
			if (Appsettings.isCheckConnectivity(context)) {
				Appsettings.putString(context, "audio_flag", "1");
			startActivity(new Intent(context, WitnessActivity.class));
			} else {
				Toast.makeText(context, "Please check your wifi connection",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.submitsurvey:

			UpdateDetails();
			break;
		default:
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void UpdateDetails() {
		// TODO Auto-generated method stub
		if (Appsettings.isCheckConnectivity(context)) {
			showDialog(1);
			try {
				obj = new JSONObject();
				obj.put("claim_number",
						Appsettings.getString(context, "claim_number").trim());
				obj.put("claimant_name",
						Appsettings.getString(context, "claim_name").trim());
				obj.put("claim_status", "Completed".trim());
				obj.put("insured_location", "Sunnyvale".trim());
				obj.put("adjuster_name", "Robin Jackson".trim());
				obj.put("loss_date", Appsettings
						.getString(context, "loss_date").trim());
				obj.put("survey_date",
						Appsettings.getString(context, "survey_date").trim());
				obj.put("extend_demage",
						Appsettings.getString(context, "extend_demage").trim());
				obj.put("roof_type", Appsettings
						.getString(context, "roof_type").trim());
				obj.put("cause_ofloss",
						Appsettings.getString(context, "cause_ofloss").trim());
				obj.put("is_video",
						Appsettings.getString(context, "video_flag"));
				obj.put("is_image",
						Appsettings.getString(context, "image_flag"));
				obj.put("is_audio",
						Appsettings.getString(context, "audio_flag"));
				ServiceConnector upload = new ServiceConnector(this, obj,
						handler);
				upload.execute();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				dialog.dismiss();
			}
		} else {
			Toast.makeText(
					this,
					"ClaimAdjustement: Offline Mode \n Please Check your Internet Connectivity",
					Toast.LENGTH_LONG).show();
		}

	}

	private final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Boolean result = msg.getData().getBoolean("data");
			String Msg = msg.getData().getString("Msg");
			dialog.dismiss();
			if (result) {
				startService(new Intent(context, LowFreqLiveCardService.class));
				Toast.makeText(context, "Survey Details Updated Successfully",
						Toast.LENGTH_SHORT).show();
				finish();
			} else {
				Log.i("Sync operation", "Failed");
				finish();
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
			dialog.setMessage("Saving Survey Details!...");
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			return dialog;
		case 2:
			dialog.setMessage("Uploading Video!...");
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			return dialog;
		default:
			break;
		}
		return dialog;

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == TAKE_VIDEO_REQUEST && resultCode == RESULT_OK) {
			String picturePath = data
					.getStringExtra(Intents.EXTRA_VIDEO_FILE_PATH);
			processPictureWhenReady(picturePath);
			String upload_path = Appsettings.fileCreation(picturePath, context);
			Log.i("Video Path", upload_path + "");
			File path = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath().toString()
					+ "/claimadjuster/");
			fileUpload(upload_path);
			// Appsettings.deleteDirectory(path);

		}

	}

	private void fileUpload(String file_path) {
		showDialog(2);
		ImageUploader uploader = new ImageUploader(file_path, handler_videos);
		uploader.execute();
	}

	private final Handler handler_videos = new Handler() {
		public void handleMessage(Message msg) {
			dialog.dismiss();
			String result = msg.getData().getString("Msg");
			Log.i("Handler msg", result + "");
			try {
				Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	};

	private void processPictureWhenReady(final String picturePath) {
		final File pictureFile = new File(picturePath);

		if (pictureFile.exists()) {
			// The picture is ready; process it.
			String path = Appsettings.fileCreation(picturePath, context);
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
