package com.cognizant.claimadjustment;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.WindowManager;

public class VoiceToSpeechActivity extends Activity {
	private static final int SPEECH_REQUEST = 0;
	private String memoResult;
	private ArrayList<String> memoList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		displaySpeechRecognizer();
	}

	private void displaySpeechRecognizer() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		startActivityForResult(intent, SPEECH_REQUEST);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == SPEECH_REQUEST && resultCode == RESULT_OK) {
			// Get results of speech to text
			memoResult = data.getStringArrayListExtra(
					RecognizerIntent.EXTRA_RESULTS).get(0);

		}
		setResult(22, data);
		finish();
	}

	@Override
	public void onResume() {
		super.onResume();
	}
}