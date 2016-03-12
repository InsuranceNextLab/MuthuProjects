package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cognizant.workactivate.R;

import java.util.ArrayList;

import common.logger.Log;

public class cardAdapter extends BaseAdapter {
    private Context cxt;
    private ArrayList<CardItemData> items;
    private LayoutInflater mInflater;

    public cardAdapter(ArrayList<CardItemData> listcollection, Context context) {

        this.cxt = context;
        this.items = listcollection;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        final ViewHolder holder;
        View vi = convertView;
        if (convertView == null) {

            vi = mInflater.inflate(R.layout.list_item_card, null);
            holder = new ViewHolder();
            holder.m_text1 = (TextView) vi.findViewById(R.id.text1);
            holder.m_text2 = (TextView) vi.findViewById(R.id.text2);
            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();
        Log.i("sdgsdgds", items.get(position).getText1() + "");
        holder.m_text1.setText(items.get(position).getText1() + "");
        holder.m_text2.setText(items.get(position).getText2() + "");
        return vi;
    }

    public static class ViewHolder {
        public TextView m_text1;
        public TextView m_text2;
    }
}
