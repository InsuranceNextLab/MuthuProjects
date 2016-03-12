package com.cognizant.claimadjustment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;
import comcognizant.adpter.ClaimListAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity implements OnItemClickListener {

	String[] claim_number = new String[] { "CL-342784", "CL-002342",
			"CL-833343", "CL-2100", "CL-005225", "CL-076520" };
	int[] iconlist = new int[] { R.drawable.claimant1, R.drawable.claimant2,
			R.drawable.claimant3, R.drawable.claimant3, R.drawable.claimant2,
			R.drawable.claimant1 };

	String[] policy_number = new String[] { "AU-000100011", "AU-00010004",
			"AU-00010006", "AU-00010007", "AU-00010008", "AU-00010009" };
	String[] surveyor_number = new String[] { "123456", "112345", "128978",
			"156456", "789654", "789456" };
	String[] cause_loss = new String[] { "Hail", "Hail", "Hail", "Hail",
			"Hail", "Hail" };
	String[] address = new String[] {
			"445 Broadway, Room 112, Albany, NY 12207",
			"1200 Southwest Expressway, SanJose CA 95126",
			"1800 Stokes St, Apt 61, SanJose, CA 95126",
			"1800 Stokes St, Apt 61, SanJose, CA 95126",
			"132nd Street, New York, NY, United States",
			"156th Avenue Southeast, Bellevue, WA, United State" };
	String[] account_name = new String[] { "John Waternoose", "Tom Cook",
			" Rick Richard", "Baker Gonzalez", "Hernandez Wright",
			"Campbell Evans" };
	String[] claim_status = new String[] { "Assigned", "Assigned", "Assigned",
			"Assigned", "Assigned", "Assigned" };
	String[] loss_date = new String[] { "5-12-2014", "5-10-2014", "5-11-2014",
			"5-11-2014", "5-10-2014", "10-05-2014" };
	ClaimData claim_details1, claim_details2, claim_details3, claim_details4,
			claim_details5, claim_details6;
	private GestureDetector mGestureDetector;
	ListView claimdetails;
	Context context;
	ArrayList<ClaimData> claim_list;
	ClaimListAdapter claimdetails_adapter;
	TextView subtitle;
	private String listcount, original_loss_date;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_YEAR, -3);
		Date daysBeforeDate = cal.getTime();
		original_loss_date = new SimpleDateFormat("MM-dd-yyy", Locale.UK)
				.format(daysBeforeDate);
		mGestureDetector = createGestureDetector(this);
		claimdetails = (ListView) findViewById(R.id.claim_details);
		claim_list = new ArrayList<ClaimData>();
		// listcount = getResources().getString(R.string.queue_count);
		// subtitle = (TextView) findViewById(R.id.sub_title);
		buildingArraylist();
		setadapter();
		// subtitle.setText(String.format(listcount, claim_number.length));

	}

	private GestureDetector createGestureDetector(MainActivity claimdetails_info) {
		GestureDetector gestureDetector = new GestureDetector(context);
		// Create a base listener for generic gestures
		gestureDetector.setBaseListener(new GestureDetector.BaseListener() {
			public boolean onGesture(Gesture gesture) {
				if (gesture == Gesture.TWO_TAP) {
					// do something on two finger tap
					return true;
				} else if (gesture == Gesture.SWIPE_RIGHT) {
					if (claimdetails.getSelectedItemPosition() < claimdetails
							.getCount() - 1) {

						claimdetails.setSelection(claimdetails
								.getSelectedItemPosition() + 1);

						claimdetails
								.playSoundEffect(SoundEffectConstants.NAVIGATION_UP);
						return true;
					}
					// do something on right (forward) swipe

				} else if (gesture == Gesture.SWIPE_LEFT) {
					if (claimdetails.getSelectedItemPosition() > 0) {
						claimdetails.setSelection(claimdetails
								.getSelectedItemPosition() - 1);
						claimdetails
								.playSoundEffect(SoundEffectConstants.NAVIGATION_DOWN);
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

	private void buildingArraylist() {
		for (int i = 0; i < claim_number.length; i++) {
			switch (i) {
			case 0:
				claim_details1 = new ClaimData();
				claim_details1.setClaimNumber(claim_number[i]);
				claim_details1.setClaimName(account_name[i]);
				claim_details1.setClaimStatus(claim_status[i]);
				claim_details1.setPolicyNumber(policy_number[i]);
				claim_details1.setSurveyNumber(surveyor_number[i]);
				claim_details1.setCauseLoss(cause_loss[i]);
				claim_details1.setAddress(address[i]);
				claim_details1.setlossDate(original_loss_date);
				claim_details1.setProfileImage(iconlist[i]);
				claim_list.add(claim_details1);
				break;
			case 1:
				claim_details2 = new ClaimData();
				claim_details2.setClaimNumber(claim_number[i]);
				claim_details2.setClaimName(account_name[i]);
				claim_details2.setClaimStatus(claim_status[i]);
				claim_details2.setPolicyNumber(policy_number[i]);
				claim_details2.setSurveyNumber(surveyor_number[i]);
				claim_details2.setCauseLoss(cause_loss[i]);
				claim_details2.setAddress(address[i]);
				claim_details2.setlossDate(original_loss_date);
				claim_details2.setProfileImage(iconlist[i]);
				claim_list.add(claim_details2);
				break;
			case 2:
				claim_details3 = new ClaimData();
				claim_details3.setClaimNumber(claim_number[i]);
				claim_details3.setClaimName(account_name[i]);
				claim_details3.setClaimStatus(claim_status[i]);
				claim_details3.setPolicyNumber(policy_number[i]);
				claim_details3.setSurveyNumber(surveyor_number[i]);
				claim_details3.setCauseLoss(cause_loss[i]);
				claim_details3.setAddress(address[i]);
				claim_details3.setlossDate(original_loss_date);
				claim_details3.setProfileImage(iconlist[i]);
				claim_list.add(claim_details3);
				break;
			case 3:
				claim_details4 = new ClaimData();
				claim_details4.setClaimNumber(claim_number[i]);
				claim_details4.setClaimName(account_name[i]);
				claim_details4.setClaimStatus(claim_status[i]);
				claim_details4.setPolicyNumber(policy_number[i]);
				claim_details4.setSurveyNumber(surveyor_number[i]);
				claim_details4.setCauseLoss(cause_loss[i]);
				claim_details4.setAddress(address[i]);
				claim_details4.setlossDate(original_loss_date);
				claim_details4.setProfileImage(iconlist[i]);
				claim_list.add(claim_details4);
				break;
			case 4:
				claim_details5 = new ClaimData();
				claim_details5.setClaimNumber(claim_number[i]);
				claim_details5.setClaimName(account_name[i]);
				claim_details5.setClaimStatus(claim_status[i]);
				claim_details5.setPolicyNumber(policy_number[i]);
				claim_details5.setSurveyNumber(surveyor_number[i]);
				claim_details5.setCauseLoss(cause_loss[i]);
				claim_details5.setAddress(address[i]);
				claim_details5.setlossDate(original_loss_date);
				claim_details5.setProfileImage(iconlist[i]);
				claim_list.add(claim_details5);
				break;
			case 5:
				claim_details6 = new ClaimData();
				claim_details6.setClaimNumber(claim_number[i]);
				claim_details6.setClaimName(account_name[i]);
				claim_details6.setClaimStatus(claim_status[i]);
				claim_details6.setPolicyNumber(policy_number[i]);
				claim_details6.setSurveyNumber(surveyor_number[i]);
				claim_details6.setCauseLoss(cause_loss[i]);
				claim_details6.setAddress(address[i]);
				claim_details6.setlossDate(original_loss_date);
				claim_details6.setProfileImage(iconlist[i]);
				claim_list.add(claim_details6);
				break;
			default:
				break;
			}
		}
	}

	private void setadapter() {
		claimdetails_adapter = new ClaimListAdapter(this, claim_list);
		claimdetails.setAdapter(claimdetails_adapter);
		claimdetails.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Bundle data = new Bundle();
		data.putString("claim_number", claim_list.get(position)
				.getClaimNumber());
		// data.putString("survey_number", claim_list.get(position)
		// .getSurveyNumber());
		data.putString("cause_loss", claim_list.get(position).getCauseLoss());
		data.putString("address", claim_list.get(position).getAddress());
		data.putString("reported_by", claim_list.get(position).getClaimName());
		data.putString("claim_number", claim_list.get(position)
				.getClaimNumber());
		data.putString("loss_date", claim_list.get(position).getlossDate());
		data.putString("status", claim_list.get(position).getClaimStatus());
		Log.i("OnitemClick", "Click Event");
		Intent intent = new Intent(this, ViewClaim.class);
		intent.putExtras(data);
		startActivity(intent);

	}
}
