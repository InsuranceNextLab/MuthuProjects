package com.cognizant.adpter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cognizant.glassadjuster.Claimlist;
import com.cognizant.glassadjuster.R;

import java.util.ArrayList;


public class ClaimlistAdapter extends ArrayAdapter<Claimlist> {
    private Context cxt;
    private ArrayList<Claimlist> items;

    public ClaimlistAdapter(Context context, ArrayList<Claimlist> claim_list) {
        super(context, 0, claim_list);
        this.cxt = context;
        this.items = claim_list;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View view;
        view = null;
        try {
            view = View.inflate(cxt, R.layout.claim_rowitem, null);
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
            Claim_number.setText(items.get(position).getClaimNumber().trim());
            account_name.setText(items.get(position).getReportedby().trim());
            claim_status.setText(items.get(position).getClaimStatus().trim());
            surveydate.setText(items.get(position).getReportedDate().trim());
            cause_demage.setText(items.get(position).getLosscause().trim());
            // profile_img.setImageResource(items.get(position).getProfileImage());


        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

}
