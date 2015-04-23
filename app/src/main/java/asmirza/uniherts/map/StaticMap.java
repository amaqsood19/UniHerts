package asmirza.uniherts.map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import asmirza.uniherts.R;


public class StaticMap extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_map);

        WebView webview = (WebView) findViewById(R.id.map_webView);
        webview.getSettings().setJavaScriptEnabled(true);
        String pdf = "http://www.herts.ac.uk/__data/assets/pdf_file/0018/9450/College-Lane-Campus-RoomNos-24Jan14.pdf";
        webview.loadUrl("http://docs.google.com/gview?embedded=true&url=" + pdf);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_static_map, menu);
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
