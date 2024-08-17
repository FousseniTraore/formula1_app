package com.example.traore_fousseni_s2110850_resit;

import android.os.AsyncTask;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchScheduleDetails extends AsyncTask<Void, Void, InputStream> {

    private ScheduleDetailActivity activity;

    public FetchScheduleDetails(ScheduleDetailActivity activity) {
        this.activity = activity;
    }

    @Override
    protected InputStream doInBackground(Void... voids) {
        try {
            URL url = new URL("http://ergast.com/api/f1/2024");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            return connection.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(InputStream inputStream) {
        if (inputStream != null) {
            activity.parseXML(inputStream);
        }
    }
}