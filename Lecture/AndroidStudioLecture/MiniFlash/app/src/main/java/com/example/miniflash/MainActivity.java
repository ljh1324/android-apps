package com.example.miniflash;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    Button btnWhite, btnYello, btnGreen;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = (LinearLayout) findViewById(R.id.layout);

        btnWhite = (Button) findViewById(R.id.btnWhite);
        btnWhite.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                layout.getRootView().setBackgroundColor(Color.WHITE);
            }
        });

        btnYello = (Button) findViewById(R.id.btnYello);
        btnYello.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                layout.getRootView().setBackgroundColor(Color.YELLOW);

            }
        });

        btnGreen = (Button) findViewById(R.id.btnGreen);
        btnGreen.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                layout.getRootView().setBackgroundColor(Color.GREEN);
            }
        });
    }

    /*class ButtonActionHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == btnOK) {
                txtOut.setText("Clicked!");
                txtOut.setTextSize(32.0f);
            }
            else if (v == btnCancle) {
                txtOut.setText("Hello World!");
                txtOut.setTextSize(16.0f);
            }
        }
    }
    */
}
