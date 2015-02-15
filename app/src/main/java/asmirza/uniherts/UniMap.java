package asmirza.uniherts;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.ui.IconGenerator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UniMap extends FragmentActivity {

    static final LatLng CollegeLane = new LatLng(51.752375,-0.241353);

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    MapXML mapXML;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uni_map);
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
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
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




    private void createBoundary()  {

        Polygon polygon = mMap.addPolygon(new PolygonOptions()
                .add(
                new LatLng(-0.244145,51.755648),
                new LatLng(-0.234189,51.752912),
                new LatLng(-0.234103,51.749883),
                new LatLng(-0.239038,51.749750),
                new LatLng(-0.241227,51.748395),
                new LatLng(-0.242558,51.748050),
                new LatLng(-0.243931,51.750547),
                new LatLng(-0.244660,51.752673),
                new LatLng(-0.244575,51.754426),
                new LatLng(-0.244145,51.755648))
                .strokeColor(Color.BLUE)
                .fillColor(Color.LTGRAY));
    }


    private void setUpMap() {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setMyLocationEnabled(true);


        CameraUpdate intialCameraUpdate = CameraUpdateFactory.newLatLngZoom(CollegeLane, 18);
        mMap.animateCamera(intialCameraUpdate);


        createBoundary();

        mapXML = new MapXML(getResources().getXml(R.xml.building_markers));


        mapXML.readXML();

        //getMakersfromXML(buildingMarkers);

        plotMarkers(mapXML.getBuildings());



    }



    private void plotMarkers(HashMap<String,Place> places)
    {
        IconGenerator iconFactory = new IconGenerator(this);

        Iterator<Map.Entry<String, Place>> iterator = places.entrySet().iterator() ;
        while(iterator.hasNext()) {
            Map.Entry<String, Place> placeEntry = iterator.next();
            Building building = (Building) placeEntry.getValue();

            HashMap<String, Place> rooms = building.getRooms();
            System.out.println("" + building.getName());

            mMap.addMarker(building.getMarker().anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV()).snippet(building.getAddress()).icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(building.getName()))));

            if (rooms.size() != 0)
            {

                Iterator<Map.Entry<String, Place>> iteratorRooms = rooms.entrySet().iterator();
                while (iteratorRooms.hasNext()) {
                    Map.Entry<String, Place> roomEntry = iteratorRooms.next();
                    Room room = (Room) roomEntry.getValue();

                    System.out.println(room.getName());



                    iconFactory.setRotation(0);
                    iconFactory.setContentRotation(90);
                    iconFactory.setStyle(IconGenerator.STYLE_GREEN);

                    mMap.addMarker(room.getMarker().anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV()).snippet(room.getType()).icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(room.getName()))));
                }
            }
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
            case R.id.buildings:
                Intent intent = new Intent(this, MapBuildings.class);
                startActivity(intent);
                return true;
            case R.id.rooms:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
