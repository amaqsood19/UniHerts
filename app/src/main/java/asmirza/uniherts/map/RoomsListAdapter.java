package asmirza.uniherts.map;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import asmirza.uniherts.R;

public class RoomsListAdapter extends BaseExpandableListAdapter implements Filterable {

    private ArrayList<Building> buildingList;
    private Context context;
    private Filter roomsFilter;
    private ArrayList<Building> origBuildingList;

    public RoomsListAdapter(ArrayList<Building> buildings, Context ctx) {
        this.buildingList = buildings;
        this.origBuildingList = buildings;
        this.context = ctx;
    }

    public void addItem(Room room, Building building) {
        if (!buildingList.contains(building)) {
            buildingList.add(building);
        }
        int index = buildingList.indexOf(building);
        ArrayList<Room> rooms = buildingList.get(index).getRooms();
        rooms.add(room);
        buildingList.get(index).setRooms(rooms);
    }

    public Object getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        ArrayList<Room> roomsList = buildingList.get(groupPosition).getRooms();
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
        TextView tvRoom = (TextView) view.findViewById(R.id.room_name);
        TextView tvDesc = (TextView) view.findViewById(R.id.room_description);
        tvRoom.setText(child.getName().toString());
        tvDesc.setText(child.getType());
        //tvRoom.setTag(child.getType());
        // TODO Auto-generated method stub
        return view;
    }

    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
        ArrayList<Room> chList = buildingList.get(groupPosition).getRooms();

        return chList.size();

    }

    public Object getGroup(int groupPosition) {
        // TODO Auto-generated method stub
        return buildingList.get(groupPosition);
    }

    public int getGroupCount() {
        // TODO Auto-generated method stub
        return buildingList.size();
    }

    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isLastChild, View view,
                             ViewGroup parent) {
        Building building = (Building) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.rooms_group_list_item, null);
        }
        TextView tvName = (TextView) view.findViewById(R.id.building_name);
        TextView tvDescp = (TextView) view.findViewById(R.id.building_description);
        tvName.setText(building.getName());
        tvDescp.setText(building.getAddress());
        // TODO Auto-generated method stub
        return view;
    }

    public void resetData() {
        buildingList = origBuildingList;
    }

    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }

    public boolean isChildSelectable(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return true;
    }

    /*
* We create our filter
*/
    @Override
    public Filter getFilter() {
        if (roomsFilter == null)
            roomsFilter = new RoomFilter();
        return roomsFilter;
    }

    /* *********************************
    * We use the holder pattern
    * It makes the view faster and avoid finding the component
    * **********************************/
    private static class PlaceHolder {
        public TextView placeNameView;
        public TextView distView;
    }

    private class RoomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = origBuildingList;
                results.count = origBuildingList.size();
            } else {
                // We perform filtering operation
                ArrayList<Building> nBuildingList = new ArrayList<Building>();
                for (Building building : origBuildingList) {

                    ArrayList<Room> roomList = building.getRooms();
                    ArrayList<Room> newRoomList = new ArrayList<Room>();
                    for (Room room : roomList) {
                        if (room.getName().toUpperCase().contains(constraint.toString().toUpperCase())) {
                            newRoomList.add(room);
                        }
                    }
                    if (newRoomList.size() > 0) {
                        Building nBuilding = new Building(building.getLat(), building.getLat(), building.getName(), building.getAddress(), building.getZoom(), newRoomList);
                        nBuildingList.add(nBuilding);
                    }

                }
                results.values = nBuildingList;
                results.count = nBuildingList.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            // Now we have to inform the adapter about the new list filtered
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                buildingList = (ArrayList<Building>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}


