package asmirza.uniherts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ASMIRZA on 15/02/2015.
 */

public class PlacesListAdapter extends BaseAdapter {
    private final ArrayList mData;

    public PlacesListAdapter(Map<String, Place> map) {
        mData = new ArrayList();
        mData.addAll(map.entrySet());
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Map.Entry<String,Place> getItem(int position) {
        return (Map.Entry) mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO implement you own logic with ID
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;

        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_list_item, parent, false);
        } else {
            result = convertView;
        }

        Map.Entry<String, Place> item = getItem(position);

        // TODO replace findViewById by ViewHolder
        ((TextView) result.findViewById(R.id.location_name)).setText(item.getKey());
        ((TextView) result.findViewById(R.id.location_description)).setText(item.getValue().getName());

        return result;
    }
}
