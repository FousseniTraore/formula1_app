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

public class DriverDetailActivity extends AppCompatActivity {

    private TextView driverNameTextView;
    private TextView driverDOBTextView;
    private TextView driverNationalityTextView;
    private TextView driverPositionTextView;
    private TextView driverPointsTextView;
    private TextView driverWinsTextView;
    private TextView driverPermanentNumberTextView;
    private TextView constructorNameTextView;
    private TextView constructorNationalityTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_detail);

        driverNameTextView = findViewById(R.id.driverNameTextView);
        driverDOBTextView = findViewById(R.id.driverDOBTextView);
        driverNationalityTextView = findViewById(R.id.driverNationalityTextView);
        driverPositionTextView = findViewById(R.id.driverPositionTextView);
        driverPointsTextView = findViewById(R.id.driverPointsTextView);
        driverWinsTextView = findViewById(R.id.driverWinsTextView);
        driverPermanentNumberTextView = findViewById(R.id.driverPermanentNumberTextView);
        constructorNameTextView = findViewById(R.id.constructorNameTextView);
        constructorNationalityTextView = findViewById(R.id.constructorNationalityTextView);

        int driverPosition = getIntent().getIntExtra("driverPosition", -1);

        if (driverPosition != -1) {
            new FetchDriverDetails(this).execute();
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
            boolean insideDriverStanding = false;
            boolean insideDriver = false;
            boolean insideConstructor = false;
            int position = -1;
            String points = null;
            String wins = null;
            String permanentNumber = null;
            String givenName = null;
            String familyName = null;
            String dob = null;
            String nationality = null;
            String constructorName = null;
            String constructorNationality = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagName.equalsIgnoreCase("DriverStanding")) {
                            insideDriverStanding = true;
                            position = Integer.parseInt(parser.getAttributeValue(null, "position"));
                            points = parser.getAttributeValue(null, "points");
                            wins = parser.getAttributeValue(null, "wins");
                        } else if (insideDriverStanding && tagName.equalsIgnoreCase("Driver")) {
                            insideDriver = true;
                        } else if (insideDriver && tagName.equalsIgnoreCase("PermanentNumber")) {
                            permanentNumber = parser.nextText();
                        } else if (insideDriver && tagName.equalsIgnoreCase("GivenName")) {
                            givenName = parser.nextText();
                        } else if (insideDriver && tagName.equalsIgnoreCase("FamilyName")) {
                            familyName = parser.nextText();
                        } else if (insideDriver && tagName.equalsIgnoreCase("DateOfBirth")) {
                            dob = parser.nextText();
                        } else if (insideDriver && tagName.equalsIgnoreCase("Nationality")) {
                            nationality = parser.nextText();
                        } else if (insideDriverStanding && tagName.equalsIgnoreCase("Constructor")) {
                            insideConstructor = true;
                        } else if (insideConstructor && tagName.equalsIgnoreCase("Name")) {
                            constructorName = parser.nextText();
                        } else if (insideConstructor && tagName.equalsIgnoreCase("Nationality")) {
                            constructorNationality = parser.nextText();
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagName.equalsIgnoreCase("DriverStanding")) {
                            insideDriverStanding = false;
                            if (position == getIntent().getIntExtra("driverPosition", -1)) {
                                driverNameTextView.setText(givenName + " " + familyName);
                                driverDOBTextView.setText(dob);
                                driverNationalityTextView.setText(nationality);
                                driverPositionTextView.setText("Position: " + position);
                                driverPointsTextView.setText(points);
                                driverWinsTextView.setText(wins);
                                driverPermanentNumberTextView.setText(permanentNumber);
                                constructorNameTextView.setText(constructorName);
                                constructorNationalityTextView.setText(constructorNationality);
                                return;
                            }
                        } else if (tagName.equalsIgnoreCase("Driver")) {
                            insideDriver = false;
                        } else if (tagName.equalsIgnoreCase("Constructor")) {
                            insideConstructor = false;
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
