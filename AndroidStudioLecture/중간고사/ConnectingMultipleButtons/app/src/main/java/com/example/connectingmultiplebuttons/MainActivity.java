package com.example.connectingmultiplebuttons;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {
    TextView txtMsg;
    Button btnBegin;
    Button btnExit;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main );
        txtMsg = (TextView) findViewById(R.id.txtMsg);
        btnBegin = (Button) findViewById(R.id.btnBegin);
        btnExit = (Button) findViewById(R.id.btnExit);
        btnBegin.setOnClickListener(this);
        btnExit.setOnClickListener(this);
    }//onCreate
    @Override
    public void onClick(View v) {
        if (v.getId() == btnBegin.getId()) {
            txtMsg.setText("1-You clicked the 'BEGIN' button");
        }
        if (v.getId() == btnExit.getId()) {
            txtMsg.setText("2-You clicked the 'EXIT' button");
        }
    }//onClick
}