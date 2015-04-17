package asmirza.uniherts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.parse.Parse;


public class MainMenu extends Activity {

    // flag for Internet connection status
    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;
    Boolean isConnected =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // creating connection detector class instance
        cd = new ConnectionDetector(getApplicationContext());
        isConnected = cd.isConnectingToInternet();
        if(isConnected == false)
        {
            displayAlert();
        }

        Parse.initialize(this, "ziNzpLqUvT6z6kfsjHNlZPBtLb2FqjKMYulOnKjG", "NzX9eMVq72HVROEzm0Kg8TsBkqaeUl3cGj5lqOuK");

    }

    public void displayAlert()
    {
        new AlertDialog.Builder(this).setMessage("Please Check Your Internet Connection and Try Again")
                .setTitle("Network Error")
                .setCancelable(true)
                .setNeutralButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton){
                                finish();
                            }
                        })
                .show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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



    public void openMap(View view) {
        Intent intent = new Intent(this, UniMap.class);
        startActivity(intent);

    }

    public void openID(View view) {
    // Start an intent for the logged in activity
        Intent intent = new Intent(this, DispatchActivity.class);
        startActivity(intent);


    }


    public void openFood(View view) {
        Intent intent = new Intent(this, Food.class);
        startActivity(intent);

    }

    public void openNews(View view) {
        Intent intent = new Intent(this, FacebookNews.class);
        startActivity(intent);

    }

    public void openParkingMap(View view) {
        Intent intent = new Intent(this, ParkingMap.class);
        startActivity(intent);

    }

    public void openStaticMap(View view) {

        Intent intent = new Intent(this, StaticMap.class);
        startActivity(intent);


    }


}
