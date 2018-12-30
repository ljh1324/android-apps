package com.example.buycoffewithradiobutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    CheckBox chkCream;
    CheckBox chkSugar;
    Button btnPay;
    RadioGroup radCoffeeType;
    RadioButton radDecaf;
    RadioButton radExpresso;
    RadioButton radColombian;
    TextView labelCoffe;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//binding XMl controls with Java code
        chkCream = (CheckBox) findViewById(R.id.chkCream);
        chkSugar = (CheckBox) findViewById(R.id.chkSugar);
        btnPay = (Button) findViewById(R.id.btnPay);
        labelCoffe = (TextView) findViewById(R.id.labelCoffee);

        radCoffeeType = (RadioGroup) findViewById(R.id.radioGroupCoffeeType);
        radDecaf = (RadioButton) findViewById(R.id.radDecaf);
        radExpresso = (RadioButton) findViewById(R.id.radExpresso);
        radColombian = (RadioButton) findViewById(R.id.radColombian);

//LISTENER: wiring button-events-&-code
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                String msg = "Coffee ";
                if (chkCream.isChecked()) msg += " & cream ";
                if (chkSugar.isChecked())
                    msg += " & Sugar";
                // get selected radio button ID number
                int radioId = radCoffeeType.getCheckedRadioButtonId();
                // compare selected's Id with individual RadioButtons ID
                if (radColombian.getId() == radioId) msg = "Colombian " + msg;
                // similarly you may use .isChecked() on each RadioButton
                if (radExpresso.isChecked()) msg = "Expresso " + msg;
                // similarly you may use .isChecked() on each RadioButton
                if (radDecaf.isChecked()) msg = "Decaf " + msg;
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                // go now and compute cost... }// onClick
            }//onClick
        });
    }//onCreate
}//class
