package com.cognizant.glassadjuster;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.FileObserver;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;

import com.cognizant.server.LowFreqLiveCardService;
import com.cognizant.utils.Appsettings;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ViewClaim extends Activity {
    public static final String EXTRA_VIDEO_FILE_PATH = "video_file_path";
    private static final int TAKE_VIDEO_REQUEST = 2;
    TextView txt_policy_no, txt_claimant_name, txt_cause_loss, txt_status,
            txt_loss_date, claim_number;
    Bundle data, survey_detilas;
    String Claimno;
    Context con;
    String current_date;
    ClaimData claim_details1;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_claim);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        stopService(new Intent(this, LowFreqLiveCardService.class));
        current_date = new SimpleDateFormat("MM-dd-yyy", Locale.UK)
                .format(new Date());
        con = this;
        claim_details1 = new ClaimData();
        Appsettings.putString(con, "survey_date", current_date);
        survey_detilas = new Bundle();
        txt_policy_no = (TextView) findViewById(R.id.claim_no);
        txt_claimant_name = (TextView) findViewById(R.id.claimant_name);
        txt_loss_date = (TextView) findViewById(R.id.loss_date);
        txt_cause_loss = (TextView) findViewById(R.id.cause_loss);
        txt_status = (TextView) findViewById(R.id.address);
        data = getIntent().getExtras();
        txt_policy_no.setText(data.getString("claim_number"));
        txt_claimant_name.setText(data.getString("reported_by"));
        txt_loss_date.setText(data.getString("loss_date"));
        txt_status.setText(data.getString("status"));
        txt_cause_loss.setText(data.getString("cause_loss"));
        mGestureDetector = createGestureDetector(this);

        Appsettings.putString(con, "claim_number",
                data.getString("claim_number") + "");
        Appsettings.putString(con, "claim_name", data.getString("reported_by")
                + "");
        Appsettings.putString(con, "loss_date", data.getString("loss_date")
                + "");
        Appsettings.putString(con, "cause_loss", data.getString("cause_loss")
                + "");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_claim, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.startsurvey:
                survey_detilas.putString("claim_number",
                        data.getString("claim_number"));
                survey_detilas.putString("current_date", current_date);
                survey_detilas.putString("claimant_name",
                        data.getString("reported_by"));
                survey_detilas.putString("loss_date", data.getString("loss_date"));
                Intent surevey_intent = new Intent(con, Survey.class);
                surevey_intent.putExtras(survey_detilas);
                startActivity(surevey_intent);
                break;
            case R.id.capturevideo:
                break;
            case R.id.captureimage:
                startActivity(new Intent(con, CaptureView.class));
                break;
            case R.id.weatherreport:
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivityForResult(intent, TAKE_VIDEO_REQUEST);
                break;
            case R.id.recordmemo:

                break;
            default:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_VIDEO_REQUEST && resultCode == RESULT_OK) {
            String picturePath =data.getStringExtra(EXTRA_VIDEO_FILE_PATH);
            processPictureWhenReady(picturePath);
        }
    }

    private void processPictureWhenReady(final String picturePath) {
        final File pictureFile = new File(picturePath);

        if (pictureFile.exists()) {
            // The picture is ready; process it.
            String path = Appsettings.fileCreation(picturePath, con);
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

    private GestureDetector createGestureDetector(Context context) {
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

                } else if (gesture == Gesture.SWIPE_DOWN) {
                    startActivity(new Intent(con, ClaimListActivity.class));
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
