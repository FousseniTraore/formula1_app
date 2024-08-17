package com.example.traore_fousseni_s2110850_resit;

import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchDriverDetails extends AsyncTask<Void, Void, InputStream> {

    private DriverDetailActivity activity;

    public FetchDriverDetails(DriverDetailActivity activity) {
        this.activity = activity;
    }

    @Override
    protected InputStream doInBackground(Void... voids) {
        InputStream inputStream = null;
        try {
            // Try fetching data from the API
            URL url = new URL("http://ergast.com/api/f1/current/driverStandings");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            inputStream = connection.getInputStream();
            Log.d("FetchDriverDetails", "Successfully fetched driver details from API for more details.");
        } catch (Exception e) {
            Log.e("FetchDriverDetails", "Error fetching driver details from API, loading from local file for more details", e);
            // Fallback to loading data from the local XML file in assets
            try {
                AssetManager assetManager = activity.getAssets();
                inputStream = assetManager.open("f1_current_driverStandings.xml");
                Log.d("FetchDriverDetails", "Successfully loaded driver details from local XML file for more details.");
            } catch (Exception ex) {
                Log.e("FetchDriverDetails", "Error loading driver details from local XML file for more details", ex);
            }
        }
        return inputStream;
    }

    @Override
    protected void onPostExecute(InputStream inputStream) {
        if (inputStream != null) {
            activity.parseXML(inputStream);
        } else {
            Log.e("FetchDriverDetails", "No data available to parse.");
        }
    }
}
