package com.example.spinnerlaoutexample;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity
        implements AdapterView.OnItemSelectedListener {
    // GUI objects
    TextView txtMsg;
    Spinner spinner;
    // options to be offered by the spinner
    String[] items = {"Data-0", "Data-1", "Data-2", "Data-3", "Data-4",
            "Data-5", "Data-6", "Data-7"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtMsg = (TextView) findViewById(R.id.txtMsg);
        spinner = (Spinner) findViewById(R.id.spinner1);
// use adapter to bind items array to GUI layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
        // add spinner a listener so user can meake selections by tapping an item
        spinner.setOnItemSelectedListener(this);
    } // next two methods implement the spinner's listener

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) { // echo on the textbox the user's selection
        txtMsg.setText(items[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO do nothing – needed by the interface }
    }
}