package com.buntorotandjaja.www.capstoneproject;

import android.os.AsyncTask;
import android.widget.RemoteViews;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class SoldJobService extends JobService {

    public static final String JOB_TAG = "check_sold_service";
    BackgroundTask backgroundTask;

    @Override
    public boolean onStartJob(final JobParameters job) {
        backgroundTask = new BackgroundTask() {
            @Override
            protected void onPostExecute(String s) {
                jobFinished(job, SellActivity.CHANGE_IMAGE == 1 ? false : true);
                int imageResource = SellActivity.CHANGE_IMAGE == 1 ? R.drawable.garage_sale_sold
                        : R.drawable.garage_sale_icon;
                RemoteViews rv = new RemoteViews(getPackageName(), R.layout.capstone_widget);
                rv.setImageViewResource(R.id.imageView_capstoneWidget, imageResource);
            }
        };

        backgroundTask.execute();
        // run on seperate thread
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        /* true means, we're not done, please reschedule */
        return true;
    }

    public static class BackgroundTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            SellActivity.CHANGE_IMAGE = CheckDBListing.getSold() ? 1 : 0;
            return "testing service runs";
        }
    }
}
