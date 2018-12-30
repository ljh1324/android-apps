package com.example.listviewdata2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String[] items = {"Data-0", "Data-1", "Data-2", "Data-3", "Data-4", "Data-5", "Data-6", "Data-7"};

    ListView myListView;
    TextView txtMsg;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myListView = (ListView) findViewById(R.id.my_list);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                // R.layout.my_text, //try this later...
                items);
        myListView.setAdapter(aa);
        txtMsg = (TextView) findViewById(R.id.txtMsg);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v,
                                    int position, long id) {
                String text = "Position: " + position
                        + "\nData: " + items[position];
                txtMsg.setText(text);
            }
        });
    }//onCreate

}
