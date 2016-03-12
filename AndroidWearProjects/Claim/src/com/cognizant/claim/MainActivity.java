package com.cognizant.claim;

import java.io.File;
import java.util.ArrayList;

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
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.cognizant.claim.adapter.ImageUploader;
import com.cognizant.claim.adapter.MoreAdapter;
import com.google.android.glass.media.CameraManager;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;
import com.google.android.glass.view.WindowUtils;

public class MainActivity extends Activity implements OnItemClickListener {

	private GestureDetector mGestureDetector;
	Context con;
	ProgressDialog dialog;
	ListView menulist;
	String[] names = new String[] { "Policy information", "Claim Information",
			"Past claim information", "Photographs of damage",
			"Record replacement values", "Take notes", "Record interview",
			"Add witness report" };
	int[] iconlist = new int[] { R.drawable.track_claims_icons,
			R.drawable.track_claims_icons, R.drawable.track_claims_icons,
			R.drawable.track_claims_icons, R.drawable.track_claims_icons,
			R.drawable.track_claims_icons, R.drawable.track_claims_icons,
			R.drawable.track_claims_icons };
	ArrayList<ClaimData> fillMaps;
	MoreAdapter moreadpter;
	ClaimData data_details, data_details1, data_details2, data_details3,
			data_details4, data_details5, data_details6, data_details7;
	private static final int TAKE_VIDEO_REQUEST = 2;
	String upload_path;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_main);
		mGestureDetector = createGestureDetector(this);
		con = this;
		menulist = (ListView) findViewById(R.id.menus_list);
		fillMaps = new ArrayList<ClaimData>();
		BuildingList();
		setadapter();
	}

	public void setadapter() {
		moreadpter = new MoreAdapter(this, fillMaps);
		menulist.setAdapter(moreadpter);
		menulist.setOnItemClickListener(this);
	}

	private void BuildingList() {

		for (int i = 0; i < names.length; i++) {
			switch (i) {
			case 0:
				data_details = new ClaimData();
				data_details.setMenuName(names[i]);
				data_details.setMenuImage(iconlist[i]);
				fillMaps.add(data_details);
				break;
			case 1:
				data_details1 = new ClaimData();
				data_details1.setMenuName(names[i]);
				data_details1.setMenuImage(iconlist[i]);
				fillMaps.add(data_details1);
				break;
			case 2:
				data_details2 = new ClaimData();
				data_details2.setMenuName(names[i]);
				data_details2.setMenuImage(iconlist[i]);
				fillMaps.add(data_details2);
				break;
			case 3:
				data_details3 = new ClaimData();
				data_details3.setMenuName(names[i]);
				data_details3.setMenuImage(iconlist[i]);
				fillMaps.add(data_details3);
				break;
			case 4:
				data_details4 = new ClaimData();
				data_details4.setMenuName(names[i]);
				data_details4.setMenuImage(iconlist[i]);
				fillMaps.add(data_details4);
				break;
			case 5:
				data_details5 = new ClaimData();
				data_details5.setMenuName(names[i]);
				data_details5.setMenuImage(iconlist[i]);
				fillMaps.add(data_details5);
				break;
			case 6:
				data_details6 = new ClaimData();
				data_details6.setMenuName(names[i]);
				data_details6.setMenuImage(iconlist[i]);
				fillMaps.add(data_details6);
				break;
			case 7:
				data_details7 = new ClaimData();
				data_details7.setMenuName(names[i]);
				data_details7.setMenuImage(iconlist[i]);
				fillMaps.add(data_details7);
				break;
			default:
				break;
			}

		}

	}

	private GestureDetector createGestureDetector(Context context) {
		GestureDetector gestureDetector = new GestureDetector(context);
		// Create a base listener for generic gestures
		gestureDetector.setBaseListener(new GestureDetector.BaseListener() {
			public boolean onGesture(Gesture gesture) {
				if (gesture == Gesture.TWO_TAP) {
					// do something on two finger tap
					return true;
				} else if (gesture == Gesture.SWIPE_RIGHT) {
					if (menulist.getSelectedItemPosition() < menulist
							.getCount() - 1) {

						menulist.setSelection(menulist
								.getSelectedItemPosition() + 1);

						menulist.playSoundEffect(SoundEffectConstants.NAVIGATION_UP);
						return true;
					}
					// do something on right (forward) swipe

				} else if (gesture == Gesture.SWIPE_LEFT) {
					if (menulist.getSelectedItemPosition() > 0) {
						menulist.setSelection(menulist
								.getSelectedItemPosition() - 1);
						menulist.playSoundEffect(SoundEffectConstants.NAVIGATION_DOWN);
						return true;
					}
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		switch (position) {
		case 0:
			startActivity(new Intent(con, ClaimInfo.class));

			break;
		case 1:
			startActivity(new Intent(con, Claimdetails_info.class));
			break;
		case 2:

			startActivity(new Intent(con, PolicyInfo.class));
			break;
		case 3:
			startActivity(new Intent(con, CaptureView.class));
			break;

		case 4:
			Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
			if (intent != null) {
				startActivityForResult(intent, TAKE_VIDEO_REQUEST);
			}
			break;
		case 5:
			startActivity(new Intent(con, NotesActivity.class));
			break;
		case 6:
			startActivity(new Intent(con, StreamingActivity.class));
			break;
		case 7:
			startActivity(new Intent(con, WitnessActivity.class));
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onCreatePanelMenu(int featureId, Menu menu) {
		if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}
		// Pass through to super to setup touch menu.
		return super.onCreatePanelMenu(featureId, menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
			switch (item.getItemId()) {
			case R.id.menu_item1:
				// handle top-level dogs menu item
				startActivity(new Intent(con, ClaimInfo.class));
				break;
			case R.id.menu_item2:
				// handle top-level cats menu item
				startActivity(new Intent(con, Claimdetails_info.class));

				break;
			case R.id.menu_item3:
				// handle top-level cats menu item
				startActivity(new Intent(con, PolicyInfo.class));

				break;
			case R.id.menu_item4:
				// handle second-level labrador menu item
				startActivity(new Intent(con, CaptureView.class));

				break;
			case R.id.create_note:
				// handle second-level golden menu item
				Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
				if (intent != null) {
					startActivityForResult(intent, TAKE_VIDEO_REQUEST);
				}

				break;
			case R.id.view_note:
				// handle second-level golden menu item
				startActivity(new Intent(con, NotesActivity.class));

				break;

			case R.id.menu_item6:
				// handle second-level calico menu item
				Intent serviceIntent = new Intent(this, NotesService.class);
				serviceIntent.putExtra("update", true);
				startService(serviceIntent);

				break;
			case R.id.menu_item7:
				// handle second-level cheshire menu item
				startActivity(new Intent(con, StreamingActivity.class));

				break;
			case R.id.menu_item8:
				startActivity(new Intent(con, WitnessActivity.class));
				break;
			default:
				return true;
			}
			return true;
		}
		// Good practice to pass through to super if not handled
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == TAKE_VIDEO_REQUEST && resultCode == RESULT_OK) {
			String picturePath = data
					.getStringExtra(CameraManager.EXTRA_VIDEO_FILE_PATH);
			processPictureWhenReady(picturePath);
			upload_path = Appsettings.fileCreation(picturePath);
			Log.i("Video Path", picturePath + "");
			File path = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath().toString()
					+ "/claimadjuster/");
			fileUpload(upload_path);
			// deleteDirectory(path);

		}

	}

	private void fileUpload(String file_path) {
		showDialog(1);
		ImageUploader uploader = new ImageUploader(file_path, handler);
		uploader.execute();
	}

	private final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			dialog.dismiss();
			String result = msg.getData().getString("Msg");
			Log.i("Handler msg", result + "");
			try {
				Toast.makeText(con, "Upload successfully", Toast.LENGTH_SHORT)
						.show();
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

}