package com.example.listviewdata;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends ListActivity {  // List이면서 Activity를 상속한 Activity

    TextView txtMsg;
    String[] items = { "Data-0", "Data-1", "Data-2", "Data-3",
            "Data-4", "Data-5", "Data-6", "Data-7" };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                items));
//getListView().setBackgroundColor(Color.GRAY); //try this idea later
        txtMsg = (TextView) findViewById(R.id.txtMsg);
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Log.e("id:", String.valueOf(id));
        Log.e("position: ", String.valueOf(position));
        String text = " Position: " + position + " " + items[position];
        txtMsg.setText(text);
    }
}
