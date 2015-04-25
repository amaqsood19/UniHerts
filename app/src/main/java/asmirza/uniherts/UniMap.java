package asmirza.uniherts;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.directions.route.Route;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.ui.IconGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UniMap extends FragmentActivity implements RoutingListener {

    private final LatLng collegeLane = new LatLng(51.752375, -0.241353);

    protected GoogleMap mMap; // Might be null if Google Play services APK is not available.
    protected LatLng start;
    protected LatLng end;
    protected MapXML mapXML;
    Toolbar mToolbar;
    List<DrawerItem> dataList;
    CustomDrawerAdapter adapter;
    ImageView iconr;
    private DrawerLayout mDrawerLayout;
    private ListView mRightDrawerList;
    private ListView mLeftDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mRightDrawerTitle;
    private CharSequence mLeftDrawerTitle;
    private CharSequence mActivityTitle;

    private String[] menuTitles;
    private TypedArray menuIcons;

    private ArrayList<MenuDrawerItem> menuDrawerItems;
    private MenuDrawerListAdapter menuAdapter;

    private HashMap<String, Building> buildings;
    private ArrayList<Building> buildingPlaces;
    private ArrayList<Room> classRoomPlaces;
    private ArrayList<Room> toiletPlaces;
    private ArrayList<Room> accessibleToiletPlaces;
    private ArrayList<Room> shopsPlaces;
    private ArrayList<Room> learningZonePlaces;
    private ArrayList<Room> foodPlaces;
    private ArrayList<Room> doorsPlaces;
    private ArrayList<Room> liftPlaces;
    private ArrayList<Room> showersChangingPlaces;
    private HashMap<String, Building> buildingMarkers;
    private HashMap<String, Room> classRoomMarkers;
    private HashMap<String, Room> toiletMarkers;
    private HashMap<String, Room> accessibleToiletMarkers;
    private HashMap<String, Room> shopsMarkers;
    private HashMap<String, Room> learningZoneMarkers;
    private HashMap<String, Room> foodMarkers;
    private HashMap<String, Room> doorsMarkers;
    private HashMap<String, Room> liftMarkers;
    private HashMap<String, Room> showersChangingMarkers;

    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uni_map);
        setUpMapIfNeeded();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.map_drawer_layout);
        mRightDrawerList = (ListView) findViewById(R.id.right_drawer);
        mLeftDrawerList = (ListView) findViewById(R.id.left_drawer);
        mActivityTitle = getTitle().toString();
        mRightDrawerTitle = "Select Layers to show";
        mLeftDrawerTitle = "Main Menu";
        // Set the adapter for the list view
        dataList = new ArrayList<DrawerItem>();
        dataList.add(new DrawerItem("Buildings", "building", R.raw.university, false));
        dataList.add(new DrawerItem("Classrooms", "classroom", R.raw.classroom, false));
        dataList.add(new DrawerItem("Toilets", "toilet", R.raw.toilets, false));
        dataList.add(new DrawerItem("Food Outlets", "food_outlet", R.raw.university, false));
        dataList.add(new DrawerItem("Shops", "shop", R.raw.university, false));
        adapter = new CustomDrawerAdapter(this, R.layout.drawer_list_item,
                dataList);

        mRightDrawerList.setAdapter(adapter);

        mRightDrawerList.setOnItemClickListener(new LayersDrawerItemClickListener());

        menuTitles = getResources().getStringArray(R.array.map_menu_array);

        menuIcons = getResources().obtainTypedArray(R.array.map_menu_drawer_icons);

        menuDrawerItems = new ArrayList<MenuDrawerItem>();

        menuDrawerItems.add(new MenuDrawerItem(menuTitles[0], menuIcons.getResourceId(0, -1)));

        menuDrawerItems.add(new MenuDrawerItem(menuTitles[1], menuIcons.getResourceId(1, -1)));


        menuIcons.recycle();

        // setting the nav drawer list adapter
        menuAdapter = new MenuDrawerListAdapter(getApplicationContext(),
                menuDrawerItems);
        mLeftDrawerList.setAdapter(menuAdapter);

        // Set the Left Menu list's click listener
        mLeftDrawerList.setOnItemClickListener(new MenuDrawerItemClickListener());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);


        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout
                , mToolbar, R.string.drawer_open, R.string.drawer_close) {

            /**
             * Called when a drawer has settled in a completely open state.
             */
            public void onDrawerOpened(View drawerView) {
                if (drawerView.equals(mLeftDrawerList)) {
                    getActionBar().setTitle(mLeftDrawerTitle);
                    supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                    mDrawerToggle.syncState();
                } else if (drawerView.equals(mRightDrawerList)) {
                    getActionBar().setTitle(mRightDrawerTitle);
                    supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /**
             * Called when a drawer has settled in a completely closed state.
             */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                if (item != null && item.getItemId() == android.R.id.home) {
                    if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                        mDrawerLayout.closeDrawer(Gravity.RIGHT);
                    } else if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                    } else {
                        mDrawerLayout.openDrawer(Gravity.LEFT);
                    }
                }
                return false;
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        iconr = (ImageView) findViewById(R.id.iconr);

        iconr.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);


                } else {
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);



    }


    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
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
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setMyLocationEnabled(true);
        CameraUpdate intialCameraUpdate = CameraUpdateFactory.newLatLngZoom(collegeLane, 19);
        mMap.animateCamera(intialCameraUpdate);
        mapXML = MapXML.getInstance();


        //plotBuildingsMarkers(mapXML.getBuildings(getResources().getXml(R.xml.building_markers)));

        buildings = mapXML.getBuildings();

        buildingPlaces = mapXML.getBuildingPlaces();
        classRoomPlaces = mapXML.getClassRoomPlaces();
        toiletPlaces = mapXML.getToiletPlaces();
        accessibleToiletPlaces = mapXML.getAccessibleToiletPlaces();
        shopsPlaces = mapXML.getShopsPlaces();
        learningZonePlaces = mapXML.getLearningZonePlaces();
        foodPlaces = mapXML.getFoodPlaces();
        doorsPlaces = mapXML.getDoorsPlaces();
        liftPlaces = mapXML.getLiftPlaces();
        showersChangingPlaces = mapXML.getShowersChangingPlaces();

        // Setting a custom info window adapter for the google map
        //mMap.setInfoWindowAdapter(new ParkingInfoWindowAdapter());

        set3DMap(true);
        setIndoorMap(false);
    }

    public void showDirections(LatLng start, LatLng end) {
        start = new LatLng(51.753167, -0.242132);
        end = new LatLng(51.752429, -0.242103);

        Routing routing = new Routing(Routing.TravelMode.WALKING);
        routing.registerListener(this);
        routing.execute(start, end);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data == null) {
            return;
        }
        String name = data.getStringExtra("place");

        Iterator<Map.Entry<String, Building>> iterator = buildings.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Building> buildingEntry = iterator.next();
            Building building = buildingEntry.getValue();

            ArrayList<Room> rooms = building.getRooms();
            System.out.println("" + building.getName());

            if (name.contentEquals(building.getName())) {
                zoomToBuilding(building);
                break;
            } else {
                if (rooms.size() != 0) {
                    for (Room room : building.getRooms()) {
                        if (name.contentEquals(room.getName())) {
                            if (room != null) {
                                zoomToRoom(room);
                            }
                            break;
                        }
                    }
                }
            }


        }


    }

    public void zoomTo(Place place) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(place.getMarker().getPosition(), place.getZoom());
        mMap.animateCamera(cameraUpdate);
    }

    public void zoomToBuilding(Building building) {
        IconGenerator iconFactory = new IconGenerator(this);
        iconFactory.setColor(Color.rgb(52, 181, 229));
        mMap.addMarker(building.getMarker().anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV()).snippet(building.getAddress()).icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(building.getName()))));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(building.getMarker().getPosition(), building.getZoom());
        mMap.animateCamera(cameraUpdate);
    }

    public void zoomToRoom(Room room) {
        IconGenerator iconFactory = new IconGenerator(this);
        iconFactory.setColor(Color.rgb(52, 181, 229));
        mMap.addMarker(room.getMarker().anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV()).snippet(room.getType()).icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(room.getName()))));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(room.getMarker().getPosition(), room.getZoom());
        mMap.animateCamera(cameraUpdate);
    }

    public void showMarkers() {

    }

    private void addOverlay() {

        //top left corner 51.748717, -0.236957
        //top right corner 51.754445, -0.238229
        // bottom right corner 51.754445, -0.236957
        // bottom left corner 51.748717, -0.238229

        //top =
        //left =
        //right =
        //bottom =

        LatLng southWest = new LatLng(51.754445, -0.238229);
        LatLng northEast = new LatLng(51.748717, -0.236957);

        LatLngBounds latLngBounds = new LatLngBounds(northEast, southWest);

        GroundOverlayOptions newarkMap = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.raw.overlay_map))
                .positionFromBounds(latLngBounds).transparency(0.5f);

        // Add an overlay to the map, retaining a handle to the GroundOverlay object.
        GroundOverlay imageOverlay = mMap.addGroundOverlay(newarkMap);


    }

    private void plotSelectedTypeMarkers(List<DrawerItem> layersList) {

        mMap.clear();
        for (int i = 0; i < layersList.size(); i++) {
            DrawerItem layer = layersList.get(i);
            if (layer.isSelected()) {
                layer.setSelected(true);
                plotLayerMarkers(layer.getType());
            }
        }

    }

    private void plotLayerMarkers(String layerType) {

        if (layerType.equalsIgnoreCase("building")) {
            plotBuildingsMarkers();
        } else if (layerType.equalsIgnoreCase("classroom")) {
            plotMarkers(classRoomPlaces);
        } else if (layerType.equalsIgnoreCase("toilet")) {
            plotMarkers(toiletPlaces);
        } else if (layerType.equalsIgnoreCase("accessiblity_toilet")) {
            plotMarkers(accessibleToiletPlaces);
        } else if (layerType.equalsIgnoreCase("food_outlet")) {
            plotMarkers(foodPlaces);
        } else if (layerType.equalsIgnoreCase("shop")) {
            plotMarkers(shopsPlaces);
        }

    }

    private BitmapDescriptor getRoomIcon(Room r) {
        String type = r.getType();
        BitmapDescriptor icon = null;

        if (type.equalsIgnoreCase("classroom")) {
            icon = BitmapDescriptorFactory.fromResource(R.raw.classroom);
        } else if (type.equalsIgnoreCase("toilet")) {
            icon = BitmapDescriptorFactory.fromResource(R.raw.toilets);
        } else if (type.equalsIgnoreCase("medical")) {
            icon = BitmapDescriptorFactory.fromResource(R.raw.medicine);
        } else if (type.equalsIgnoreCase("clinic")) {
            icon = BitmapDescriptorFactory.fromResource(R.raw.hospital);
        } else {
            icon = BitmapDescriptorFactory.fromResource(R.raw.university);
        }
        return icon;

    }

    private void plotBuildingsMarkers(HashMap<String, Building> buildings) {
        IconGenerator iconFactory = new IconGenerator(this);
        Iterator<Map.Entry<String, Building>> iterator = buildings.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Building> buildingEntry = iterator.next();
            Building building = buildingEntry.getValue();

            System.out.println("" + building.getName());

            mMap.addMarker(building.getMarker().snippet(building.getAddress()).icon(BitmapDescriptorFactory
                    .fromResource(R.raw.university)));

        }

    }

    private void plotMarkers(ArrayList<Room> rooms) {
        IconGenerator iconFactory = new IconGenerator(this);
        System.out.println(rooms.toString());

        HashMap<String, Room> roomsMarkers = new HashMap<String, Room>();

        BitmapDescriptor roomIcon = getRoomIcon(rooms.get(0));

        for (Room room : rooms) {
            System.out.println(room);

            System.out.println("" + room.getName());

            Marker roomMarker = mMap.addMarker(room.getMarker().snippet(room.getTypeString()).icon(roomIcon));

            roomsMarkers.put(roomMarker.getId(), room);

        }

        savePlottedMarkersByType(roomsMarkers);

    }

    private void plotBuildingsMarkers() {
        IconGenerator iconFactory = new IconGenerator(this);
        System.out.println(buildingPlaces.toString());

        BitmapDescriptor buildingIcon = BitmapDescriptorFactory.fromResource(R.raw.university);


        buildingMarkers = new HashMap<String, Building>();

        for (Building building : buildingPlaces) {
            System.out.println(building);

            System.out.println("" + building.getName());

            Marker buildingMarker = mMap.addMarker(building.getMarker().snippet(building.getAddress()).icon(buildingIcon));

            buildingMarkers.put(buildingMarker.getId(), building);

        }


    }


    private void savePlottedMarkersByType(HashMap<String, Room> roomsMarkers) {
        String roomsMarkersType = roomsMarkers.entrySet().iterator().next().getValue().getType();
        if (roomsMarkersType.equalsIgnoreCase("shop")) {
            shopsMarkers = roomsMarkers;
        } else if (roomsMarkersType.equalsIgnoreCase("toilet")) {
            toiletMarkers = roomsMarkers;
        } else {

        }
    }


    private void plotBuildingWithRoomsMarkers(HashMap<String, Building> buildings) {
        IconGenerator iconFactory = new IconGenerator(this);

        Iterator<Map.Entry<String, Building>> iterator = buildings.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Building> buildingEntry = iterator.next();
            Building building = buildingEntry.getValue();


            System.out.println("" + building.getName());

            mMap.addMarker(building.getMarker().snippet(building.getAddress()).icon(BitmapDescriptorFactory
                    .fromResource(R.raw.university)));


            ArrayList<Room> rooms = building.getRooms();
            if (rooms.size() != 0) {
                System.out.println("Adding Rooms");
                for (Room room : building.getRooms()) {
                    System.out.println(room.getName());

                    iconFactory.setColor(Color.rgb(52, 181, 229));

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
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

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

    private void set3DMap(boolean value) {
        mMap.setBuildingsEnabled(true);
    }

    private void setIndoorMap(boolean value) {
        mMap.setIndoorEnabled(value);
    }

    @Override
    public void onRoutingFailure() {
        // The Routing request failed
    }

    @Override
    public void onRoutingStart() {
        // The Routing Request starts
    }

    @Override
    public void onRoutingSuccess(PolylineOptions mPolyOptions, Route route) {
        PolylineOptions polyoptions = new PolylineOptions();
        polyoptions.color(Color.BLUE);
        polyoptions.width(10);
        polyoptions.addAll(mPolyOptions.getPoints());
        mMap.addPolyline(polyoptions);

        // Start marker
        MarkerOptions options = new MarkerOptions();
        options.position(start);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue));
        mMap.addMarker(options);

        // End marker
        options = new MarkerOptions();
        options.position(end);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green));
        mMap.addMarker(options);
    }

    private void selectItem(int position) {
        // Highlight the selected item, update the title, and close the drawer
        switch (position) {
            case 0:
                mDrawerLayout.closeDrawer(mLeftDrawerList);
                Intent intent = new Intent(this, ListBuildings.class);
                startActivityForResult(intent, 1);
            case 1:
                mDrawerLayout.closeDrawer(mLeftDrawerList);
                Intent listRoomsIntent = new Intent(this, ListRooms.class);
                startActivityForResult(listRoomsIntent, 1);
            default:
                mDrawerLayout.closeDrawer(mLeftDrawerList);
                break;
        }
    }

    /* The click listner for Layers ListView in the right drawer */
    private class LayersDrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            StringBuffer responseText = new StringBuffer();
            responseText.append("The following were selected...\n");
            // When clicked, show a toast with the TextView text
            DrawerItem drawerItem = (DrawerItem) parent.getItemAtPosition(position);
            if (!drawerItem.isSelected()) {
                CheckBox box = (CheckBox) view.findViewById(R.id.drawer_checkBox);
                box.setChecked(true);
                drawerItem.setSelected(true);
            } else {
                CheckBox box = (CheckBox) view.findViewById(R.id.drawer_checkBox);
                box.setChecked(false);
                drawerItem.setSelected(false);
            }


            List<DrawerItem> layersList = adapter.drawerItemList;
            for (int i = 0; i < layersList.size(); i++) {
                DrawerItem layer = layersList.get(i);
                if (layer.isSelected()) {
                    layer.setSelected(true);
                    responseText.append("\n" + layer.getItemName());
                }
            }

            Toast.makeText(getApplicationContext(),
                    responseText, Toast.LENGTH_LONG).show();

            plotSelectedTypeMarkers(layersList);


        }
    }

    private class MenuDrawerItemClickListener implements ListView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

//    private class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
//
//        private View view;
//
//        public MarkerInfoWindowAdapter() {
//            view = getLayoutInflater().inflate(R.layout.parking_marker_info,
//                    null);
//        }
//
//        @Override
//        public View getInfoContents(Marker marker) {
//
//            UniMap.this.marker = marker;
//
//            Parking parking = null;
//
//
//
//            if (marker.getId() != null && parkingMarkers != null && parkingMarkers.size() > 0) {
//                if (parkingMarkers.get(marker.getId()) != null &&
//                        parkingMarkers.get(marker.getId()) != null) {
//                    parking = parkingMarkers.get(marker.getId());
//                }
//            }
//
//            TextView park_name_tv = (TextView) view.findViewById(R.id.park_name_tv);
//            TextView park_type_tv = (TextView) view.findViewById(R.id.park_type_tv);
//            TextView park_restrictions_time_tv = (TextView) view.findViewById(R.id.park_restrictions_time_tv);
//
//            park_name_tv.setText(parking.getName());
//            park_type_tv.setText(parking.getTypeString());
//            park_restrictions_time_tv.setText(parking.getRestrictionsTime());
//
//            return view;
//
//        }
//
//        @Override
//        public View getInfoWindow(final Marker marker) {
//            return null;
//        }
//    }
}
