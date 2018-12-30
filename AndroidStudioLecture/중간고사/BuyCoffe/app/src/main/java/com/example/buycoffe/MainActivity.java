package com.example.buycoffe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    CheckBox chkCream;
    CheckBox chkSugar;
    Button btnPay;
    TextView labelCoffe;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//binding XMl controls with Java code
        chkCream = (CheckBox)findViewById(R.id.chkCream);
        chkSugar = (CheckBox)findViewById(R.id.chkSugar);
        btnPay = (Button) findViewById(R.id.btnPay);
        labelCoffe = (TextView) findViewById(R.id.labelCoffee);

//LISTENER: wiring button-events-&-code
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "Coffee ";
                if (chkCream.isChecked()) {
                    msg += " & cream ";
                }
                if (chkSugar.isChecked()){
                    msg += " & Sugar";
                }
                Toast.makeText(getApplicationContext(),
                        msg, Toast.LENGTH_SHORT).show();
                labelCoffe.setText(msg);
//go now and compute cost...
            }//onClick
        });
    }//onCreate
}//class