package com.example.leejeonghwan_20141000_1;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {
    // GUI objects
    int count = 1;
    int position = 0;
    TextView txtMsg, numTxt;
    Spinner spinner;
    Button plusBtn, minusBtn, selectBtn;
    // options to be offered by the spinner
    String[] items = {"라면", "스파게티", "햄버거"};
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtMsg = (TextView) findViewById(R.id.msgTxt);
        spinner = (Spinner) findViewById(R.id.spinner1);
        plusBtn = (Button) findViewById(R.id.btnAdd);
        minusBtn = (Button) findViewById(R.id.btnMinus);
        numTxt = (TextView) findViewById(R.id.numTxt);
        selectBtn = (Button) findViewById(R.id.selectBtn);

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count >= 5)
                    return;
                count++;
                numTxt.setText(String.valueOf(count));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count <= 1)
                    return;
                count--;
                numTxt.setText(String.valueOf(count));
            }
        });

        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtMsg.setText(items[position] + count + "개를 주문했습니다.");
            }
        });

// use adapter to bind items array to GUI layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
        // add spinner a listener so user can meake selections by tapping an item
        spinner.setOnItemSelectedListener(this);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Order App Basic");

    } // next two methods implement the spinner's listener

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) { // echo on the textbox the user's selection
        //txtMsg.setText(items[position]);
        this.position = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO do nothing – needed by the interface }
    }
}
