package comcognizant.adpter;

import java.util.ArrayList;

import com.cognizant.claimadjustment.ClaimData;
import com.cognizant.claimadjustment.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ClaimListAdapter extends ArrayAdapter<ClaimData> {
	private Context cxt;
	private ArrayList<ClaimData> items;

	public ClaimListAdapter(Context context, ArrayList<ClaimData> claim_list) {
		super(context, 0, claim_list);
		this.cxt = context;
		this.items = claim_list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final View view = View.inflate(cxt, R.layout.claim_rowitem, null);
		final TextView Claim_number = (TextView) view
				.findViewById(R.id.claim_no);
		final TextView account_name = (TextView) view
				.findViewById(R.id.account_name);
		final TextView cause_demage = (TextView) view
				.findViewById(R.id.causedemage);
		final TextView surveydate = (TextView) view
				.findViewById(R.id.surveydate);
		final TextView claim_status = (TextView) view
				.findViewById(R.id.claim_status);
		final ImageView profile_img = (ImageView) view
				.findViewById(R.id.profile_pic);

		Claim_number.setText(items.get(position).getClaimNumber());
		account_name.setText(items.get(position).getClaimName());
		claim_status.setText(items.get(position).getClaimStatus());
		surveydate.setText(items.get(position).getlossDate());
		cause_demage.setText(items.get(position).getCauseLoss());
		profile_img.setImageResource(items.get(position).getProfileImage());

		return view;
	}

}
