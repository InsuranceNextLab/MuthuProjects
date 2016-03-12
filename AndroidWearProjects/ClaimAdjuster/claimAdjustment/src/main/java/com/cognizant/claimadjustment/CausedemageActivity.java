package com.cognizant.claimadjustment;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.cognizant.server.ServiceConnector;
import com.cognizant.utils.Appsettings;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class CausedemageActivity extends Activity {
	private static final int SPEECH_RECOGNIZER = 22;
	private GestureDetector mGestureDetector;
	Context context;
	Bundle data;
	TextView surevy_details, claim_number;
	HashMap<String, String> update_claimDetails;
	String claimnumber, surveydate, claimant_name, loss_date, extend_damage,
			claim_status;
	JSONObject obj;
	ProgressDialog dialog;
	ClaimData claim_details1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_causeloss);
		context = this;
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		claim_details1 = new ClaimData();
		mGestureDetector = createGestureDetector(this);
		data = getIntent().getExtras();
		// claim_number = (TextView) findViewById(R.id.claim_number);
		surevy_details = (TextView) findViewById(R.id.txtdamage);
		// claim_number.setText("#"
		// + Appsettings.getString(context, "Claim_number"));

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		extend_damage = data.getStringArrayListExtra(
				RecognizerIntent.EXTRA_RESULTS).get(0);
		Appsettings.putString(context, "cause_ofloss", extend_damage);
		surevy_details.setText(extend_damage);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.extent_damage, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.save:
			break;
		case R.id.cancel:

			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private GestureDetector createGestureDetector(
			CausedemageActivity claimdetails_info) {
		GestureDetector gestureDetector = new GestureDetector(context);
		// Create a base listener for generic gestures
		gestureDetector.setBaseListener(new GestureDetector.BaseListener() {
			public boolean onGesture(Gesture gesture) {
				if (gesture == Gesture.TAP) {
					// do something on two finger tap
					Intent speech_intent = new Intent(context,
							VoiceToSpeechActivity.class);
					startActivityForResult(speech_intent, SPEECH_RECOGNIZER);
					return true;
				} else if (gesture == Gesture.SWIPE_RIGHT) {
					Appsettings.putString(context, "image_flag", "0");
					Appsettings.putString(context, "video_flag", "0");
					Appsettings.putString(context, "audio_flag", "0");
					startActivity(new Intent(context, ProofOfDemage.class));
				} else if (gesture == Gesture.SWIPE_LEFT) {
					// startActivity(new Intent(context, RoofActivity.class));
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
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		dialog = new ProgressDialog(this);
		switch (id) {
		case 1:
			// dialog.setContentView(R.layout.progressdialog);
			dialog.setMessage("Saving Claim Details!...");
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			return dialog;
		default:
			break;
		}
		return dialog;

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
