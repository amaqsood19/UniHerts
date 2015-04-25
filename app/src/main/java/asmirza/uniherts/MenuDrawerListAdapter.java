package asmirza.uniherts;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ASMIRZA on 23/04/2015.
 */
public class MenuDrawerListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<MenuDrawerItem> menuDrawerItems;

    public MenuDrawerListAdapter(Context context, ArrayList<MenuDrawerItem> menuDrawerItems) {
        this.context = context;
        this.menuDrawerItems = menuDrawerItems;
    }

    @Override
    public int getCount() {
        return menuDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return menuDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.map_menu_drawer_item, null);
        }
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);

        imgIcon.setImageResource(menuDrawerItems.get(position).getIcon());
        txtTitle.setText(menuDrawerItems.get(position).getTitle());

        return convertView;
    }

}