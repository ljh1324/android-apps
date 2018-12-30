package com.example.customadapteractivityinlecture;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 이정환 on 2017-04-05.
 */

class CustomIconLabelAdapter extends ArrayAdapter<String> {
    Context context;
    Integer[] thumbnails;
    String[] items;
    int layoutToBeInflated;
    public CustomIconLabelAdapter( Context context, int layoutToBeInflated,
                                   String[] items, Integer[] thumbnails) {
        //super(context, R.layout.custom_row_icon_label, items);
        super(context, layoutToBeInflated, items);
        this.layoutToBeInflated = layoutToBeInflated;
        this.context = context;
        this.thumbnails = thumbnails;
        this.items = items;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {         // why not use convertView???? so i'm fixing!
        /*
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(layoutToBeInflated, null); // shell view (껍데기 뷰)
        TextView label = (TextView) row.findViewById(R.id.label);
        ImageView icon = (ImageView) row.findViewById(R.id.icon);
        label.setText(items[position]);
        icon.setImageResource(thumbnails[position]);
        return (row);*/

        // why not use convertView???? so I'm fixing!!!!
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutToBeInflated, null); // shell view (껍데기 뷰)
        }
        TextView label = (TextView) convertView.findViewById(R.id.label);
        ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
        label.setText(items[position]);
        icon.setImageResource(thumbnails[position]);

        return (convertView);
    }
}// CustomAdapter