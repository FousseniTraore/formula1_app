package com.example.traore_fousseni_s2110850_resit;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;

public class ScheduleDetailActivity extends AppCompatActivity {

    private TextView raceNameTextView;
    private TextView circuitTextView;
    private TextView roundTextView;
    private TextView dateTimeTextView;
    private TextView localityTextView;
    private TextView countryTextView;
    private TextView firstPracticeTextView;
    private TextView secondPracticeTextView;
    private TextView thirdPracticeTextView;
    private TextView qualifyingTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_detail);

        // Initialize TextViews
        raceNameTextView = findViewById(R.id.raceNameDetail);
        circuitTextView = findViewById(R.id.circuitDetail);
        roundTextView = findViewById(R.id.roundDetail);
        dateTimeTextView = findViewById(R.id.dateTimeDetail);
        localityTextView = findViewById(R.id.localityDetail);
        countryTextView = findViewById(R.id.countryDetail);
        firstPracticeTextView = findViewById(R.id.firstPracticeDetail);
        secondPracticeTextView = findViewById(R.id.secondPracticeDetail);
        thirdPracticeTextView = findViewById(R.id.thirdPracticeDetail);
        qualifyingTextView = findViewById(R.id.qualifyingDetail);

        // Get the round passed from the previous activity
        String scheduleRound = getIntent().getStringExtra("scheduleRound");

        if (scheduleRound != null) {
            new FetchScheduleDetails(this).execute();
        }

        // Initialize the ImageView and set an onClickListener
        ImageView myIconImageView = findViewById(R.id.returnArrow);
        myIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go back to the previous page
                finish();
            }
        });

    }

    public void parseXML(InputStream inputStream) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(inputStream, null);

            int eventType = parser.getEventType();
            boolean insideRace = false;
            boolean insideFirstPractice = false;
            boolean insideSecondPractice = false;
            boolean insideThirdPractice = false;
            boolean insideQualifying = false;
            String raceName = null;
            String circuitName = null;
            String round = null;
            String date = null;
            String time = null;
            String locality = null;
            String country = null;
            String firstPracticeDate = null;
            String firstPracticeTime = null;
            String secondPracticeDate = null;
            String secondPracticeTime = null;
            String thirdPracticeDate = null;
            String thirdPracticeTime = null;
            String qualifyingDate = null;
            String qualifyingTime = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagName.equalsIgnoreCase("Race")) {
                            insideRace = true;
                            round = parser.getAttributeValue(null, "round");
                        } else if (insideRace && tagName.equalsIgnoreCase("RaceName")) {
                            raceName = parser.nextText();
                        } else if (insideRace && tagName.equalsIgnoreCase("CircuitName")) {
                            circuitName = parser.nextText();
                        } else if (insideRace && tagName.equalsIgnoreCase("Date")) {
                            date = parser.nextText();
                        } else if (insideRace && tagName.equalsIgnoreCase("Time")) {
                            time = parser.nextText();
                        } else if (insideRace && tagName.equalsIgnoreCase("Locality")) {
                            locality = parser.nextText();
                        } else if (insideRace && tagName.equalsIgnoreCase("Country")) {
                            country = parser.nextText();
                        } else if (insideRace && tagName.equalsIgnoreCase("FirstPractice")) {
                            insideFirstPractice = true;
                        } else if (insideFirstPractice && tagName.equalsIgnoreCase("Date")) {
                            firstPracticeDate = parser.nextText();
                        } else if (insideFirstPractice && tagName.equalsIgnoreCase("Time")) {
                            firstPracticeTime = parser.nextText();
                        } else if (insideRace && tagName.equalsIgnoreCase("SecondPractice")) {
                            insideSecondPractice = true;
                        } else if (insideSecondPractice && tagName.equalsIgnoreCase("Date")) {
                            secondPracticeDate = parser.nextText();
                        } else if (insideSecondPractice && tagName.equalsIgnoreCase("Time")) {
                            secondPracticeTime = parser.nextText();
                        } else if (insideRace && tagName.equalsIgnoreCase("ThirdPractice")) {
                            insideThirdPractice = true;
                        } else if (insideThirdPractice && tagName.equalsIgnoreCase("Date")) {
                            thirdPracticeDate = parser.nextText();
                        } else if (insideThirdPractice && tagName.equalsIgnoreCase("Time")) {
                            thirdPracticeTime = parser.nextText();
                        } else if (insideRace && tagName.equalsIgnoreCase("Qualifying")) {
                            insideQualifying = true;
                        } else if (insideQualifying && tagName.equalsIgnoreCase("Date")) {
                            qualifyingDate = parser.nextText();
                        } else if (insideQualifying && tagName.equalsIgnoreCase("Time")) {
                            qualifyingTime = parser.nextText();
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagName.equalsIgnoreCase("FirstPractice")) {
                            insideFirstPractice = false;
                        } else if (tagName.equalsIgnoreCase("SecondPractice")) {
                            insideSecondPractice = false;
                        } else if (tagName.equalsIgnoreCase("ThirdPractice")) {
                            insideThirdPractice = false;
                        } else if (tagName.equalsIgnoreCase("Qualifying")) {
                            insideQualifying = false;
                        } else if (tagName.equalsIgnoreCase("Race")) {
                            insideRace = false;

                            // Check if this is the race for the selected round
                            if (round.equals(getIntent().getStringExtra("scheduleRound"))) {
                                // Set the details to the TextViews
                                raceNameTextView.setText(raceName);
                                circuitTextView.setText(circuitName);
                                roundTextView.setText(round);
                                dateTimeTextView.setText(date + " " + time);
                                localityTextView.setText(locality);
                                countryTextView.setText(country);
                                firstPracticeTextView.setText("First Practice: " + firstPracticeDate + " " + firstPracticeTime);
                                secondPracticeTextView.setText("Second Practice: " + secondPracticeDate + " " + secondPracticeTime);
                                thirdPracticeTextView.setText("Third Practice: " + thirdPracticeDate + " " + thirdPracticeTime);
                                qualifyingTextView.setText("Qualifying: " + qualifyingDate + " " + qualifyingTime);
                                return;
                            }
                        }
                        break;
                }

                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
