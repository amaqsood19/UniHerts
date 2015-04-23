package asmirza.uniherts;

/**
 * Created by ASMIRZA on 23/04/2015.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import asmirza.uniherts.map.MapXML;

public class LoadingTask extends AsyncTask<String, Integer, Integer> {

    // This is the progress bar you want to update while the task is in progress
    private final ProgressBar progressBar;
    // This is the listener that will be told when this task is finished
    private final LoadingTaskFinishedListener finishedListener;
    private final Context ctx;

    /**
     * A Loading task that will load some resources that are necessary for the app to start
     *
     * @param progressBar      - the progress bar you want to update while the task is in progress
     * @param finishedListener - the listener that will be told when this task is finished
     */
    public LoadingTask(Context ctx, ProgressBar progressBar, LoadingTaskFinishedListener finishedListener) {
        this.ctx = ctx;
        this.progressBar = progressBar;
        this.finishedListener = finishedListener;
    }

    @Override
    protected Integer doInBackground(String... params) {
        Log.i("doInBackgrounf", "Starting task");
        if (resourcesDontAlreadyExist()) {
            loadMapMarkers();
        }
        // Perhaps you want to return something to your post execute
        return 1234;
    }

    private boolean resourcesDontAlreadyExist() {
        // Here you would query your app's internal state to see if this download had been performed before
        // Perhaps once checked save this in a shared preference for speed of access next time
        return true; // returning true so we show the splash every time
    }

    private void loadMapMarkers() {
        // We are just imitating some process thats takes a bit of time (loading of resources / downloading)
        int count = 5;
        for (int i = 0; i < count; i++) {

            // Update the progress bar after every step
            int progress = (int) ((i / (float) count) * 100);
            publishProgress(progress);

            MapXML.getInstance().getBuildings(ctx.getResources().getXml(R.xml.building_markers));
            MapXML.getInstance().getParkings(ctx.getResources().getXml(R.xml.parking_markers));
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressBar.setProgress(values[0]); // This is ran on the UI thread so it is ok to update our progress bar ( a UI view ) here
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        finishedListener.onTaskFinished(); // Tell whoever was listening we have finished
    }

    public interface LoadingTaskFinishedListener {
        void onTaskFinished(); // If you want to pass something back to the listener add a param to this method
    }
}