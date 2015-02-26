package asmirza.uniherts;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RoomsListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<Building> buildings;
    public RoomsListAdapter(ArrayList<Building> buildings)
    {
        this.buildings = buildings;
    }

    public void addItem(Room room, Building building) {
        if (!buildings.contains(building)) {
            buildings.add(building);
        }
        int index = buildings.indexOf(building);
        ArrayList<Room> rooms = buildings.get(index).getRooms();
        rooms.add(room);
        buildings.get(index).setRooms(rooms);
    }
    public Object getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        ArrayList<Room> roomsList = buildings.get(groupPosition).getRooms();
        return roomsList.get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view,
                             ViewGroup parent) {
        Room child = (Room) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.room_list_item, null);
        }
        TextView tv = (TextView) view.findViewById(R.id.room_text_view);
        tv.setText(child.getName().toString());
        tv.setTag(child.getType());
        // TODO Auto-generated method stub
        return view;
    }

    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
        ArrayList<Room> chList = buildings.get(groupPosition).getRooms();

        return chList.size();

    }

    public Object getGroup(int groupPosition) {
        // TODO Auto-generated method stub
        return buildings.get(groupPosition);
    }

    public int getGroupCount() {
        // TODO Auto-generated method stub
        return buildings.size();
    }

    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isLastChild, View view,
                             ViewGroup parent) {
        Building group = (Building) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.rooms_group_list_item, null);
        }
        TextView tv = (TextView) view.findViewById(R.id.rooms_group);
        tv.setText(group.getName());
        // TODO Auto-generated method stub
        return view;
    }

    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }

    public boolean isChildSelectable(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return true;
    }

}


