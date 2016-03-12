package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cognizant.workactivate.R;

import java.util.ArrayList;

import domain.SideRaiseCount;

/**
 * Created by ${MUTHU} on 5/18/2015.
 */
public class SideRaiseAdapter extends BaseAdapter {
    private ArrayList<SideRaiseCount> listdetails;
    private LayoutInflater mInflater;
    private Context activity;

    public SideRaiseAdapter(ArrayList<SideRaiseCount> listcollection,
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

            vi = mInflater.inflate(R.layout.sideraise_row, null);

            holder = new ViewHolder();
            holder.step_count = (TextView) vi.findViewById(R.id.valstepcount);
            holder.step_time = (TextView) vi.findViewById(R.id.valsteptime);
            holder.date_time = (TextView) vi.findViewById(R.id.valdate);
            vi.setTag(holder);

        } else
            holder = (ViewHolder) vi.getTag();
        holder.step_count.setText(listdetails.get(position).getSideraiseCount());
        holder.step_time.setText(listdetails.get(position).getSideRaiseTime());
        holder.date_time.setText(listdetails.get(position).getdateTime());
        return vi;
    }

    public static class ViewHolder {
        public TextView step_count;
        public TextView step_time;
        public TextView date_time;

    }
}

