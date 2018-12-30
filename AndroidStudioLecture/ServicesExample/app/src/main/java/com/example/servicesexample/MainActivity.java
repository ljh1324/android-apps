package com.example.servicesexample;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity  implements View.OnClickListener {

    TextView txtMsg;
    ComponentName service;
    Intent intentMyService1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtMsg = (TextView) findViewById(R.id.txtMsg);
        findViewById(R.id.btnStopService).setOnClickListener(this);
        intentMyService1 = new Intent(this, MyService1.class);
        service = startService(intentMyService1);
        txtMsg.setText("MyService1 started\n (see LogCat)");
    }

    @Override
    public void onClick(View v) {
        // assume: v.getid == R.id.btnStopService
        try {
            stopService(intentMyService1);
            txtMsg.setText("After stopping Service: \n"
                    + service.getClassName());
        } catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();

        }
    }//onClick
}
