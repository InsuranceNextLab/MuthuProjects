package adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cognizant.workactivate.R;

import java.util.ArrayList;

import domain.BicepsCount;

/**
 * Created by ${MUTHU} on 5/18/2015.
 */
public class BicepsAdapter extends BaseAdapter {


    public final static Bundle data = new Bundle();
    private ArrayList<BicepsCount> listdetails;
    private LayoutInflater mInflater;
    private Context activity;

    public BicepsAdapter(ArrayList<BicepsCount> listcollection,
                         Context context) {
        // TODO Auto-generated constructor stub
        activity = context;
        listdetails = listcollection;
        mInflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listdetails.size();

    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return listdetails.get(position);

    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        View vi = convertView;

        if (convertView == null) {

            vi = mInflater.inflate(R.layout.biceps_row, null);

            holder = new ViewHolder();
            holder.step_count = (TextView) vi.findViewById(R.id.valbicepscount);
            holder.step_time = (TextView) vi.findViewById(R.id.valbiceps);
            holder.date_time = (TextView) vi.findViewById(R.id.valdate);
            vi.setTag(holder);

        } else
            holder = (ViewHolder) vi.getTag();
        holder.step_count.setText(listdetails.get(position).getBicepsCount());
        holder.step_time.setText(listdetails.get(position).getBicepsTime());
        holder.date_time.setText(listdetails.get(position).getdateTime());
        return vi;
    }

    public static class ViewHolder {
        public TextView step_count;
        public TextView step_time;
        public TextView date_time;

    }
}
