package asmirza.uniherts.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import asmirza.uniherts.R;

public class PlacesListAdapter extends ArrayAdapter<Place> implements Filterable {
    private List<Place> placeList;
    private Context context;
    private Filter placeFilter;
    private List<Place> origPlaceList;


    public PlacesListAdapter(List<Place> planetList, Context ctx) {
        super(ctx, R.layout.place_list_item, planetList);
        this.placeList = planetList;
        this.context = ctx;
        this.origPlaceList = planetList;
    }

    public int getCount() {
        return placeList.size();
    }

    public Place getItem(int position) {
        return placeList.get(position);
    }

    public long getItemId(int position) {
        return placeList.get(position).hashCode();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        PlaceHolder holder = new PlaceHolder();
        // First let's verify the convertView is not null
        if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.place_list_item, null);
            // Now we can fill the layout with the right values
            TextView tv = (TextView) v.findViewById(R.id.place_name);
            TextView distView = (TextView) v.findViewById(R.id.place_description);
            holder.placeNameView = tv;
            holder.distView = distView;
            v.setTag(holder);
        }

        holder = (PlaceHolder) v.getTag();
        Place p = placeList.get(position);
        holder.placeNameView.setText(p.getName());
        holder.distView.setText(p.getMarker().getSnippet());

        return v;
    }


    public void resetData() {
        placeList = origPlaceList;
    }

    /*
    * We create our filter
    */
    @Override
    public Filter getFilter() {
        if (placeFilter == null)
            placeFilter = new PlaceFilter();
        return placeFilter;
    }

    /* *********************************
    * We use the holder pattern
    * It makes the view faster and avoid finding the component
    * **********************************/
    private static class PlaceHolder {
        public TextView placeNameView;
        public TextView distView;
    }

    private class PlaceFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = origPlaceList;
                results.count = origPlaceList.size();
            } else {
                // We perform filtering operation
                List<Place> nPlaceList = new ArrayList<Place>();
                for (Place p : placeList) {
                    if (p.getName().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        nPlaceList.add(p);
                }
                results.values = nPlaceList;
                results.count = nPlaceList.size();


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
                placeList = (List<Place>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}