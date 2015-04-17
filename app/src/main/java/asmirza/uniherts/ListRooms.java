package asmirza.uniherts;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.SearchView;

import java.util.ArrayList;


public class ListRooms extends Activity {

    ArrayList<Building> roomsList;
    RoomsListAdapter adapter;
    ExpandableListView lv;
    EditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_rooms);


        lv =  (ExpandableListView)  findViewById(R.id.rooms_list_view);

        MapXML mapXML = MapXML.getInstance();

        roomsList = new ArrayList<Building>(mapXML.getBuildings().values());

        adapter = new RoomsListAdapter(roomsList,this);
        lv.setAdapter(adapter);

        expandAll();

        lv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                //get the group header
                Building building = roomsList.get(groupPosition);
                //get the child info
                Room room = building.getRooms().get(childPosition);

                Intent intent = new Intent();
                intent.putExtra("place", room.getName());
                setResult(RESULT_OK, intent);
                finish();
                return true;
            }
        });

        inputSearch = (EditText) findViewById(R.id.inputSearchRooms);

        // TextFilter
        lv.setTextFilterEnabled(true);
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("Text ["+s+"] - Start ["+start+"] - Before ["+before+"] - Count ["+count+"]");
                if (count < before) {
                // We're deleting char so we need to reset the adapter data
                    adapter.resetData();
                }
                adapter.getFilter().filter(s.toString());
                expandAll();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    //method to expand all groups
    private void expandAll() {
        int count = adapter.getGroupCount();
        for (int i = 0; i < count; i++){
            lv.expandGroup(i);
        }
    }

    //method to collapse all groups
    private void collapseAll() {
        int count = adapter.getGroupCount();
        for (int i = 0; i < count; i++){
            lv.collapseGroup(i);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map_buildings, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

