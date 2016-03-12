package com.cognizant.claim.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cognizant.claim.ClaimData;
import com.cognizant.claim.R;

public class MoreAdapter extends ArrayAdapter<ClaimData> {
	private Context cxt;
	private ArrayList<ClaimData> items;

	public MoreAdapter(Context context, ArrayList<ClaimData> listdetails) {
		super(context, 0, listdetails);
		this.cxt = context;
		this.items = listdetails;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final View view = View.inflate(cxt, R.layout.menu_item, null);
		final ImageView airlineImage = (ImageView) view
				.findViewById(R.id.img_view);
		final TextView airlineName = (TextView) view
				.findViewById(R.id.txt_menuname);
		airlineImage.setImageResource(items.get(position).getMenuImage());
		airlineName.setText(items.get(position).getMenuName());

		return view;
	}

}
