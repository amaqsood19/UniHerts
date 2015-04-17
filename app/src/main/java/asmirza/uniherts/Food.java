package asmirza.uniherts;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import asmirza.uniherts.lib.TouchImageView;


public class Food extends Activity {

    String jsonValue;
    TouchImageView img;
    private ProgressDialog pdia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        img = (TouchImageView) findViewById(R.id.food_menu_img);

        String graphRequestLink = "https://graph.facebook.com/FoodHerts?fields=albums.limit(1).fields(photos.limit(1).fields(picture).fields(source))";

        new GetImageLinkTask().execute(graphRequestLink);


    }

    private class GetImageLinkTask extends AsyncTask<String, Void, Bitmap> {
        protected void onPreExecute() {
            Log.d("Asyntask", "On preExceute...");
            pdia = ProgressDialog.show(Food.this, "Loading Menu", "Please wait", true, false);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Log.d("Asyntask", "On doInBackground...");
            String link = params[0];
            HttpClient httpclient = new DefaultHttpClient();
            StringBuilder builder = new StringBuilder();
            HttpGet httpGet = new HttpGet(link);
            Bitmap food_menu_bm = null;
            try {
                HttpResponse response = httpclient.execute(httpGet);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if (statusCode == 200) {
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }

                    String json = builder.toString();

                    JSONObject jsonObject = new JSONObject(json);

                    Log.i(Food.class.getName(), jsonObject.toString());

                    // Getting JSON Array node
                    JSONArray albumsData = jsonObject.getJSONObject("albums").getJSONArray("data");


                    Log.d("Asyntask",albumsData.toString());

                    JSONObject firstAlbum = albumsData.getJSONObject(0);

                    Log.d("Asyntask",firstAlbum.toString());

                    JSONArray firstAlbumPhotosData = firstAlbum.getJSONObject("photos").getJSONArray("data");


                    Log.d("Asyntask",firstAlbumPhotosData .toString());

                    JSONObject latestPicture= firstAlbumPhotosData.getJSONObject(0);

                    Log.d("Asyntask",latestPicture.toString());

                    String food_menu_url = latestPicture.getString("source");

                    Log.d("Asyntask",food_menu_url);

                    URL newurl = new URL(food_menu_url);
                    food_menu_bm = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                } else {
                    Log.e(Food.class.toString(), "Fail to get JSON object");
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return food_menu_bm;
        }

        protected void onProgressUpdate(Integer... a) {
            Log.d("Asyntask", "You are in progress update ... " + a[0]);
        }

        @Override
        protected void onPostExecute(Bitmap food_menu_bm) {
        Log.d("Asyntask","onPost");
        String food_menu_url;
            try {


                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                img.setImageBitmap(food_menu_bm);
                

            } catch (Exception e) {
                e.printStackTrace();
            } finally
            {
                pdia.dismiss();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_food, menu);
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
