package com.cognizant.glassadjuster;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder.AudioSource;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.cognizant.server.ImageUploader;
import com.cognizant.utils.Appsettings;
import com.cognizant.utils.WaveformView;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class WitnessActivity extends Activity {

    // The sampling rate for the audio recorder.
    private static final int SAMPLING_RATE = 44100;
    private final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            dialog.dismiss();
            String result = msg.getData().getString("Msg");
            Log.i("Handler Error Message", result + "");
            try {
                Toast.makeText(con, "uploaded successfully", Toast.LENGTH_SHORT)
                        .show();
                finish();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    };
    ProgressDialog dialog;
    AudioRecord record;
    int BytesPerElement = 2;
    Context con;
    String dir_path, filePath;
    RecMicToMp3 recordingthread;
    private WaveformView mWaveformView;
    private TextView mDecibelView;
    private RecordingThread mRecordingThread;
    private int mBufferSize;
    private short[] mAudioBuffer;
    private String mDecibelFormat;
    private boolean isRecording = false;
    private GestureDetector mGestureDetector;
    private boolean mShouldContinue = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_waveform);
        con = this;
        mWaveformView = (WaveformView) findViewById(R.id.waveform_view);
        mDecibelView = (TextView) findViewById(R.id.decibel_view);
        // mBufferSize = AudioRecord.getMinBufferSize(SAMPLING_RATE,
        // AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
        // mAudioBuffer = new short[mBufferSize / 2];
        mDecibelFormat = getResources().getString(R.string.decibel_format);
        mGestureDetector = createGestureDetector(this);
        filePath = Appsettings.getAudioPath(con);

        startRecording();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void startRecording() {
        // dir_path = Appsettings.finalPath();
        // isRecording = true;
        // mShouldContinue = true;

        // mRecordingThread = new RecordingThread();
        // mRecordingThread.start();
        recordingthread = new RecMicToMp3(mWaveformView, con, 8000);
        recordingthread.start();

    }

    private void stoprecording() {
        // isRecording = false;
        // mShouldContinue = false;
        // mRecordingThread.stopRunning();
        // mRecordingThread = null;
        recordingthread.stop();
        showDialog(1);
        fileUpload();

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mRecordingThread != null) {
            mRecordingThread.stopRunning();
            mRecordingThread = null;
        }
    }

    private byte[] short2byte(short[] sData) {
        int shortArrsize = sData.length;
        byte[] bytes = new byte[shortArrsize * 2];

        for (int i = 0; i < shortArrsize; i++) {
            bytes[i * 2] = (byte) (sData[i] & 0x00FF);
            bytes[(i * 2) + 1] = (byte) (sData[i] >> 8);
            sData[i] = 0;
        }
        return bytes;

    }

    private void writeAudioDataToFile() {
        // Write the output audio in byte

        short sData[] = new short[mBufferSize];

        FileOutputStream os = null;
        try {
            os = new FileOutputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (isRecording) {
            // gets the voice output from microphone to byte format

            record.read(sData, 0, mBufferSize);

            try {
                // // writes the data to file from buffer
                // // stores the voice buffer

                byte bData[] = short2byte(sData);

                assert os != null;
                os.write(bData, 0, mBufferSize * BytesPerElement);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            assert os != null;
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.record_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        switch (item.getItemId()) {
            case R.id.stop:
                stoprecording();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private GestureDetector createGestureDetector(Context context) {
        GestureDetector gestureDetector = new GestureDetector(context);
        // Create a base listener for generic gestures
        gestureDetector.setBaseListener(new GestureDetector.BaseListener() {
            public boolean onGesture(Gesture gesture) {
                if (gesture == Gesture.TAP) {
                    // do something on two finger tap
                    openOptionsMenu();
                    return true;
                } else if (gesture == Gesture.SWIPE_RIGHT) {

                } else if (gesture == Gesture.SWIPE_LEFT) {

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

    private void fileUpload() {

        ImageUploader uploader = new ImageUploader(
                Appsettings.getAudioPath(con), handler);
        uploader.execute();
    }

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

    /**
     * A background thread that receives audio from the microphone and sends it
     * to the waveform visualizing view.
     */

    // private RecMicToMp3 mRecMicToMp3 = new RecMicToMp3(con, 8000);

    private class RecordingThread extends Thread {

        @Override
        public void run() {
            android.os.Process
                    .setThreadPriority(android.os.Process.THREAD_PRIORITY_AUDIO);

            record = new AudioRecord(AudioSource.MIC, SAMPLING_RATE,
                    AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT, mBufferSize);
            record.startRecording();

            while (shouldContinue()) {
                record.read(mAudioBuffer, 0, mBufferSize / 2);
                mWaveformView.updateAudioData(mAudioBuffer);
                // writeAudioDataToFile();
                // updateDecibelLevel();
            }

            record.stop();
            record.release();
        }

        /**
         * Gets a value indicating whether the thread should continue running.
         *
         * @return true if the thread should continue running or false if it
         * should stop
         */
        private synchronized boolean shouldContinue() {
            return mShouldContinue;
        }

        /**
         * Notifies the thread that it should stop running at the next
         * opportunity.
         */
        public synchronized void stopRunning() {
            mShouldContinue = false;
        }

        /**
         * Computes the decibel level of the current sound buffer and updates
         * the appropriate text view.
         */
        private void updateDecibelLevel() {
            // Compute the root-mean-squared of the sound buffer and then apply
            // the formula for
            // computing the decibel level, 20 * log_10(rms). This is an
            // uncalibrated calculation
            // that assumes no noise in the samples; with 16-bit recording, it
            // can range from
            // -90 dB to 0 dB.
            double sum = 0;

            for (short rawSample : mAudioBuffer) {
                double sample = rawSample / 32768.0;
                sum += sample * sample;
            }

            double rms = Math.sqrt(sum / mAudioBuffer.length);
            final double db = 20 * Math.log10(rms);

            // Update the text view on the main thread.
            mDecibelView.post(new Runnable() {
                @Override
                public void run() {
                    mDecibelView.setText(String.format(mDecibelFormat, db));
                }
            });
        }
    }

}
