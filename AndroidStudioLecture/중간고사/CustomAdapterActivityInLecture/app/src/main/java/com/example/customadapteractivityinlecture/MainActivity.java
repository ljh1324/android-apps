package com.example.customadapteractivityinlecture;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ListActivity {
    TextView txtMsg; // The n-th row in the list will consist of [icon, label] // where icon = thumbnail[n] and label=items[n]
    String[] items = {"Data-1", "Data-2", "Data-3", "Data-4", "Data-5", "Data-6", "Data-7", "Data-8", "Data-9", "Data-10", "Data-11", "Data-12", "Data-13", "Data-14", "Data-15"};
    Integer[] thumbnails = {R.drawable.pic01_small, R.drawable.pic02_small, R.drawable.pic03_small, R.drawable.pic04_small, R.drawable.pic05_small, R.drawable.pic06_small, R.drawable.pic07_small, R.drawable.pic08_small, R.drawable.pic09_small, R.drawable.pic10_small, R.drawable.pic11_small, R.drawable.pic12_small, R.drawable.pic13_small, R.drawable.pic14_small, R.drawable.pic15_small};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtMsg = (TextView) findViewById(R.id.txtMsg);

        // the arguments of the custom adapter are:
        // activityContex, layout-to-be-inflated, labels, icons
        CustomIconLabelAdapter adapter = new CustomIconLabelAdapter(this, R.layout.custom_row_icon_label, items, thumbnails);

        // bind intrinsic ListView to custom adapter
        setListAdapter(adapter);

        // if inheritance Activitty
        // listView.setAdapter(adapter)
    }//onCreate

    // react to user's selection of a row
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String text = " Position: " + position + " " + items[position];
        txtMsg.setText(text);
    }
}//listener }//class