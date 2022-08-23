package com.assassin.timetableapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListAdapter  extends ArrayAdapter<Today> {
    public ListAdapter(@NonNull Context context, List<Today> todayList) {
        super(context, 0, todayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Today today = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.table_card, parent, false);

        }
        TextView dayTextView = convertView.findViewById(R.id.dayTextView);
        TextView courseNameTextView = convertView.findViewById(R.id.courseNameTextView);
        TextView timeTextView = convertView.findViewById(R.id.timeTextView);
        TextView venueTextView = convertView.findViewById(R.id.venueTextView);
        courseNameTextView.setText(today.getCourse_name());
        dayTextView.setText(today.getDay());
        timeTextView.setText(today.getTime());
        venueTextView.setText(today.getVenue());

        return convertView;
    }
}
