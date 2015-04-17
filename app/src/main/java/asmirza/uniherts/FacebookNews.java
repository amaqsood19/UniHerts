package asmirza.uniherts;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import asmirza.uniherts.lib.ServiceHandler;
import asmirza.uniherts.util.Tools;


public class FacebookNews extends ListActivity {

    private ProgressDialog pDialog;

    // URL to get contacts JSON
    private static String url;


    // JSON Node names
    private static final String TAG_DATA= "data";

    private static final String TAG_MESSAGE = "message";
    private static final String TAG_FULL_PICTURE = "full_picture";
    private static final String TAG_NAME = "name";
    private static final String TAG_DESCP = "description";
    private static final String TAG_LINK = "link";
    private static final String TAG_TIME = "created_time";

    NewsListAdapter adapter;

    // contacts JSONArray
    JSONArray posts = null;

    // Hashmap for ListView
    ArrayList<NewsPost> postsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_news);

        postsList = new ArrayList<NewsPost>();

        ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                String url = adapter.getItem(position).getPictureLink();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

        // Calling async task to get json
        new GetPosts().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetPosts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(FacebookNews.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            try {
                url = "https://graph.facebook.com/uniofherts/posts?fields=message,full_picture,name,description,picture,link&limit=10&access_token=" + URLEncoder.encode("1399663470355158|8Nx8Zr1m2-Url9K7k7yXXB398xA", "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Log.e("url", url);

            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    posts = jsonObj.getJSONArray(TAG_DATA);

                    String message = "";
                    String pictureUrl= "";
                    Bitmap picture;
                    String name = "";
                    String description = "";
                    String link = "";
                    String time= "";

                    // looping through All Contacts
                    for (int i = 0; i < posts.length(); i++) {
                        JSONObject c = posts.getJSONObject(i);

                        if (c.has(TAG_MESSAGE)) {
                            message = c.getString(TAG_MESSAGE);
                        }

                        pictureUrl = c.getString(TAG_FULL_PICTURE);
                        URL picUrl = new URL(pictureUrl);
                        picture = BitmapFactory.decodeStream(picUrl.openConnection().getInputStream());

                        if (c.has(TAG_NAME)) {
                            name = c.getString(TAG_NAME);
                        }
                        if (c.has(TAG_DESCP)) {
                            description = c.getString(TAG_DESCP);
                        }
                        if (c.has(TAG_LINK)) {
                            link = c.getString(TAG_LINK);
                        }

                        time = Tools.getFriendlyStringDate(c.getString(TAG_TIME));

                        // tmp hashmap for single contact
                        NewsPost post = new NewsPost(message, picture, name, description, link, time);

                        Log.i("post details", post.toString());
                        // adding contact to contact list
                        postsList.add(post);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }




        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            adapter = new NewsListAdapter(postsList,FacebookNews.this);
            setListAdapter(adapter);
        }

    }

}