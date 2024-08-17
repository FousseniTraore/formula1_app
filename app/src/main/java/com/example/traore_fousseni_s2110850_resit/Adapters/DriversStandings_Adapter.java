package com.example.traore_fousseni_s2110850_resit.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.traore_fousseni_s2110850_resit.DataTypes.DriversStanding_DataTypes;
import com.example.traore_fousseni_s2110850_resit.DriverDetailActivity;
import com.example.traore_fousseni_s2110850_resit.R;


import java.util.ArrayList;

public class DriversStandings_Adapter extends ArrayAdapter<DriversStanding_DataTypes> {
    private ArrayList<DriversStanding_DataTypes> driverStandingsList;
    private LayoutInflater inflater;
    private Context context;

    public DriversStandings_Adapter(Context context, ArrayList<DriversStanding_DataTypes> driverStandingsList) {
        super(context, 0, driverStandingsList);
        this.context = context;
        this.driverStandingsList = driverStandingsList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.standing_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.positionTextView = convertView.findViewById(R.id.driverPos);
            viewHolder.driverGivenNameTextView = convertView.findViewById(R.id.givenName);
            viewHolder.driverFamilyNameTextView = convertView.findViewById(R.id.familyName);
            viewHolder.pointsTextView = convertView.findViewById(R.id.driverPoints);
            viewHolder.winsTextView = convertView.findViewById(R.id.driverWins);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        DriversStanding_DataTypes driverStandingData = getItem(position);

        viewHolder.positionTextView.setText(String.valueOf(driverStandingData.getPosition()));
        viewHolder.driverGivenNameTextView.setText(driverStandingData.getDriverGivenName());
        viewHolder.driverFamilyNameTextView.setText(driverStandingData.getDriverFamilyName());
        viewHolder.pointsTextView.setText(String.valueOf(driverStandingData.getPoints()));
        viewHolder.winsTextView.setText(String.valueOf(driverStandingData.getWins()));

        // Set an OnClickListener to navigate to DriverDetailActivity
        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DriverDetailActivity.class);
            intent.putExtra("driverPosition", driverStandingData.getPosition());
            context.startActivity(intent);
        });

        return convertView;
    }

    static class ViewHolder {
        TextView driverFamilyNameTextView;
        TextView driverGivenNameTextView;
        TextView positionTextView;
        TextView driverNameTextView;
        TextView pointsTextView;
        TextView winsTextView;
    }
}
