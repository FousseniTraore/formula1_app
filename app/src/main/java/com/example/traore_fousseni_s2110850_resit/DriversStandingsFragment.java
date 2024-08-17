package com.example.traore_fousseni_s2110850_resit;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.traore_fousseni_s2110850_resit.Adapters.DriversStandings_Adapter;
import com.example.traore_fousseni_s2110850_resit.DataTypes.DriversStanding_DataTypes;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DriversStandingsFragment extends Fragment {

    private ListView driverStandingsListView;
    private ArrayList<DriversStanding_DataTypes> driverStandingsList = new ArrayList<>();
    private static final String DRIVER_STANDINGS_URL = "http://ergast.com/api/f1/current/driverStandings";
    private boolean dataLoadedFromFile = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the fragment layout
        View view = inflater.inflate(R.layout.activity_drivers_standings, container, false);
        driverStandingsListView = view.findViewById(R.id.driverStandingsListView);

        // Fetch data from URL and populate ArrayList
        new FetchDriverDataTask().execute();

        return view;
    }

    // Fetch drivers standings data asynchronously
    private class FetchDriverDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL feedUrl = new URL(DRIVER_STANDINGS_URL);
                HttpURLConnection conn = (HttpURLConnection) feedUrl.openConnection();

                InputStream inputStream = conn.getInputStream();

                ArrayList<DriversStanding_DataTypes> standingsData = parseXml(inputStream);

                driverStandingsList.addAll(standingsData);

                // Log success message and data obtained
                Log.d("FetchDriverDataTask", "Successfully fetched driver standings data.");

                conn.disconnect();

            } catch (IOException | XmlPullParserException e) {
                // Log error message
                Log.e("FetchDriverDataTask", "Error fetching driver standings data, loading from local file", e);

                dataLoadedFromFile = true;
                // Fetch data from the local XML file in assets
                try {
                    InputStream inputStream = getActivity().getAssets().open("f1_current_driverStandings.xml");
                    ArrayList<DriversStanding_DataTypes> standingsData = parseXml(inputStream);
                    driverStandingsList.addAll(standingsData);

                    Log.d("FetchDriverDataTask", "Successfully loaded driver standings data from local XML file.");
                } catch (IOException | XmlPullParserException ex) {
                    Log.e("FetchDriverDataTask", "Error loading data from local XML file", ex);
                    ex.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Set adapter to ListView
            if (getActivity() != null) {
                DriversStandings_Adapter adapter = new DriversStandings_Adapter(getActivity(), driverStandingsList);
                driverStandingsListView.setAdapter(adapter);

                if (dataLoadedFromFile) {
                    Toast.makeText(getActivity(), "No internet connection. Loaded schedule from local file.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    // Parse XML data
    private ArrayList<DriversStanding_DataTypes> parseXml(InputStream inputStream) throws XmlPullParserException, IOException {
        ArrayList<DriversStanding_DataTypes> driverStandingsList = new ArrayList<>();
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(inputStream, null);

        int eventType = parser.getEventType();
        DriversStanding_DataTypes currentDriver = null;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tagName;
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    tagName = parser.getName();
                    if (tagName.equals("DriverStanding")) {
                        int position = Integer.parseInt(parser.getAttributeValue(null, "position"));
                        int points = Integer.parseInt(parser.getAttributeValue(null, "points"));
                        int wins = Integer.parseInt(parser.getAttributeValue(null, "wins"));
                        currentDriver = new DriversStanding_DataTypes(position, "", "", points, wins);
                    } else if (currentDriver != null) {
                        if (tagName.equals("GivenName")) {
                            currentDriver.setDriverGivenName(parser.nextText());
                        } else if (tagName.equals("FamilyName")) {
                            currentDriver.setDriverFamilyName(parser.nextText());
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    tagName = parser.getName();
                    if (tagName.equals("DriverStanding") && currentDriver != null) {
                        driverStandingsList.add(currentDriver);
                    }
                    break;
            }
            eventType = parser.next();
        }
        return driverStandingsList;
    }
}
