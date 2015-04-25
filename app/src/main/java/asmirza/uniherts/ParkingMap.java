package asmirza.uniherts;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.ui.IconGenerator;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class ParkingMap extends FragmentActivity {

    static final LatLng collegeLane = new LatLng(51.752375, -0.241353);


    protected GoogleMap mMap; // Might be null if Google Play services APK is not available.
    protected LatLng start;
    protected LatLng end;
    protected MapXML mapXML;
    private Marker marker;
    private Hashtable<String, Parking> parkingMarkers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_map);
        setUpMapIfNeeded();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link com.google.android.gms.maps.SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(android.os.Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */


    private void createBoundary() {

        Polygon polygon = mMap.addPolygon(new PolygonOptions()
                .add(
                        new LatLng(-0.244145, 51.755648),
                        new LatLng(-0.234189, 51.752912),
                        new LatLng(-0.234103, 51.749883),
                        new LatLng(-0.239038, 51.749750),
                        new LatLng(-0.241227, 51.748395),
                        new LatLng(-0.242558, 51.748050),
                        new LatLng(-0.243931, 51.750547),
                        new LatLng(-0.244660, 51.752673),
                        new LatLng(-0.244575, 51.754426),
                        new LatLng(-0.244145, 51.755648))
                .strokeColor(Color.BLUE)
                .fillColor(Color.LTGRAY));
    }


    private void setUpMap() {

        parkingMarkers = new Hashtable<String, Parking>();

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setMyLocationEnabled(true);


        CameraUpdate intialCameraUpdate = CameraUpdateFactory.newLatLngZoom(collegeLane, 15);
        mMap.animateCamera(intialCameraUpdate);


        mapXML = MapXML.getInstance();


        plotMarkers(mapXML.getParkings());

        // Setting a custom info window adapter for the google map
        mMap.setInfoWindowAdapter(new ParkingInfoWindowAdapter());


    }

    private BitmapDescriptor getParkingIcon(Parking p) {
        int type = p.getType();
        BitmapDescriptor icon = null;
        if (type == 0) {
            icon = BitmapDescriptorFactory.fromResource(R.raw.parking_disabled);
        } else if (type == 1) {
            icon = BitmapDescriptorFactory.fromResource(R.raw.parking_yellow);
        } else if (type == 2) {
            icon = BitmapDescriptorFactory.fromResource(R.raw.parking_green);
        } else if (type == 3) {
            icon = BitmapDescriptorFactory.fromResource(R.raw.parking_purple);
        } else if (type == 4) {
            icon = BitmapDescriptorFactory.fromResource(R.raw.parking_blue);
        }
        return icon;


    }

    private void plotMarkers(HashMap<String, Parking> places) {
        IconGenerator iconFactory = new IconGenerator(this);
        System.out.println(places.values().toString());
        Iterator<Map.Entry<String, Parking>> iterator = places.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Parking> placeEntry = iterator.next();
            Parking parking = (Parking) placeEntry.getValue();

            System.out.println("" + parking.getName());

            Marker parkingMarker = mMap.addMarker(parking.getMarker().snippet(parking.getTypeString()).icon(getParkingIcon(parking)));
            parkingMarkers.put(parkingMarker.getId(), parking);


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.buildings_menu_button:
                Intent intent = new Intent(this, ListBuildings.class);
                startActivityForResult(intent, 1);
                return true;
            case R.id.rooms_menu_button:
                Intent listRoomsIntent = new Intent(this, ListRooms.class);
                startActivityForResult(listRoomsIntent, 1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class ParkingInfoWindowAdapter implements InfoWindowAdapter {

        private View view;

        public ParkingInfoWindowAdapter() {
            view = getLayoutInflater().inflate(R.layout.parking_marker_info,
                    null);
        }

        @Override
        public View getInfoContents(Marker marker) {

            ParkingMap.this.marker = marker;

            Parking parking = null;

            if (marker.getId() != null && parkingMarkers != null && parkingMarkers.size() > 0) {
                if (parkingMarkers.get(marker.getId()) != null &&
                        parkingMarkers.get(marker.getId()) != null) {
                    parking = parkingMarkers.get(marker.getId());
                }
            }

            TextView park_name_tv = (TextView) view.findViewById(R.id.park_name_tv);
            TextView park_type_tv = (TextView) view.findViewById(R.id.park_type_tv);
            TextView park_restrictions_time_tv = (TextView) view.findViewById(R.id.park_restrictions_time_tv);

            park_name_tv.setText(parking.getName());
            park_type_tv.setText(parking.getTypeString());
            park_restrictions_time_tv.setText(parking.getRestrictionsTime());

            return view;

        }

        @Override
        public View getInfoWindow(final Marker marker) {
            return null;
        }
    }


}
