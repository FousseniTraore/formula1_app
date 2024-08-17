package com.example.traore_fousseni_s2110850_resit.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.traore_fousseni_s2110850_resit.DataTypes.Schedule_DataTypes;
import com.example.traore_fousseni_s2110850_resit.DriverDetailActivity;
import com.example.traore_fousseni_s2110850_resit.R;
import com.example.traore_fousseni_s2110850_resit.ScheduleDetailActivity;

import java.util.ArrayList;

public class Schedule_Adapter extends ArrayAdapter<Schedule_DataTypes> {
    private ArrayList<Schedule_DataTypes> scheduleList;
    private LayoutInflater inflater;
    private Context context;


    public Schedule_Adapter(Context context, ArrayList<Schedule_DataTypes> scheduleList) {
        super(context, 0, scheduleList);
        this.context = context;
        this.scheduleList = scheduleList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int round, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.schedule_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.raceNameTextView = convertView.findViewById(R.id.raceName);
            viewHolder.circuitTextView = convertView.findViewById(R.id.circuit);
            viewHolder.roundTextView = convertView.findViewById(R.id.round);
            viewHolder.dateTimeTextView = convertView.findViewById(R.id.dateTime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Schedule_DataTypes scheduleData = getItem(round);

        viewHolder.raceNameTextView.setText(scheduleData.getRaceName());
        viewHolder.circuitTextView.setText(scheduleData.getCircuit());
        viewHolder.roundTextView.setText(scheduleData.getRound());
        viewHolder.dateTimeTextView.setText(scheduleData.getDate() + " " + scheduleData.getTime());

        // Set an OnClickListener to navigate to DriverDetailActivity
        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ScheduleDetailActivity.class);
            intent.putExtra("scheduleRound", scheduleData.getRound());
            context.startActivity(intent);
        });

        return convertView;
    }

    static class ViewHolder {
        TextView raceNameTextView;
        TextView circuitTextView;
        TextView roundTextView;
        TextView dateTimeTextView;
    }
}
