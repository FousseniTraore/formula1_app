package com.example.traore_fousseni_s2110850_resit;

import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.traore_fousseni_s2110850_resit.Adapters.Schedule_Adapter;
import com.example.traore_fousseni_s2110850_resit.DataTypes.Schedule_DataTypes;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import android.widget.Toast;

public class ScheduleFragment extends Fragment {

    private ListView scheduleListView;
    private ArrayList<Schedule_DataTypes> scheduleList = new ArrayList<>();
    private static final String SCHEDULE_URL = "http://ergast.com/api/f1/2024";
    private boolean dataLoadedFromFile = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the fragment layout
        View view = inflater.inflate(R.layout.activity_schedule, container, false);
        scheduleListView = view.findViewById(R.id.scheduleListView);

        // Fetch data from URL and populate ArrayList
        new FetchScheduleDataTask().execute();

        return view;
    }

    // Fetch schedule data
    private class FetchScheduleDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                // Try fetching data from the API
                URL feedUrl = new URL(SCHEDULE_URL);
                HttpURLConnection conn = (HttpURLConnection) feedUrl.openConnection();
                InputStream inputStream = conn.getInputStream();
                ArrayList<Schedule_DataTypes> scheduleData = parseXml(inputStream);
                scheduleList.addAll(scheduleData);
                Log.d("FetchScheduleDataTask", "Successfully fetched schedule data.");
                conn.disconnect();
            } catch (IOException | XmlPullParserException e) {
                Log.e("FetchScheduleDataTask", "Error fetching schedule data, loading from local file", e);
                // Fallback to loading data from the local XML file in assets
                dataLoadedFromFile = true;
                try {
                    AssetManager assetManager = getActivity().getAssets();
                    InputStream inputStream = assetManager.open("f1_race_schedule.xml");
                    ArrayList<Schedule_DataTypes> scheduleData = parseXml(inputStream);
                    scheduleList.addAll(scheduleData);
                    Log.d("FetchScheduleDataTask", "Successfully loaded schedule data from local XML file.");
                } catch (Exception ex) {
                    Log.e("FetchScheduleDataTask", "Error loading schedule data from local XML file", ex);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Set adapter to ListView
            if (getActivity() != null) {
                Schedule_Adapter adapter = new Schedule_Adapter(getActivity(), scheduleList);
                scheduleListView.setAdapter(adapter);

                if (dataLoadedFromFile) {
                    Toast.makeText(getActivity(), "No internet connection. Loaded schedule from local file.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    // Parse XML data
    private ArrayList<Schedule_DataTypes> parseXml(InputStream inputStream) throws XmlPullParserException, IOException {
        ArrayList<Schedule_DataTypes> scheduleList = new ArrayList<>();
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(inputStream, null);

        int eventType = parser.getEventType();
        Schedule_DataTypes currentSchedule = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tagName;
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    tagName = parser.getName();
                    if (tagName.equals("Race")) {
                        // Get the round from the Race tag's attributes
                        String round = parser.getAttributeValue(null, "round");
                        currentSchedule = new Schedule_DataTypes("", "", round, "", "");
                    } else if (currentSchedule != null) {
                        if (tagName.equals("RaceName")) {
                            currentSchedule.setRaceName(parser.nextText());
                        } else if (tagName.equals("CircuitName")) {
                            currentSchedule.setCircuit(parser.nextText());
                        } else if (tagName.equals("Date")) {
                            currentSchedule.setDate(parser.nextText());
                        } else if (tagName.equals("Time")) {
                            currentSchedule.setTime(parser.nextText());
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    tagName = parser.getName();
                    if (tagName.equals("Race") && currentSchedule != null) {
                        scheduleList.add(currentSchedule);
                    }
                    break;
            }
            eventType = parser.next();
        }
        return scheduleList;
    }
}
