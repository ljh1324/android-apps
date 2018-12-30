package com.example.lightsout;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnToggle;
    private LinearLayout myLayout;
    private ChangeColorThread thread;
    private boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();
        flag = false;
    }

    private void initializeComponents() {
        btnToggle = (Button) findViewById(R.id.btnToggle);
        btnToggle.setOnClickListener(this);
        myLayout = (LinearLayout) findViewById(R.id.mylayout);
    }

    public void onClick(View v) {
        if (v.getId() == btnToggle.getId()) {
            Log.e("ChecktedText","In");
            flag = !flag;
            if (flag) {
                btnToggle.setText("Stop");
                int color = myLayout.getSolidColor();
                Log.e("ChecktedText","Background Color: " + color);

                thread = new ChangeColorThread(color);
                thread.setRunningState(true);
                thread.start();
                Log.e("message: ", "running thread");
            } else {
                btnToggle.setText("Start");
                thread.setRunningState(false);
            }
        }
    }

    class ChangeColorThread extends Thread {
        private int color;
        private AtomicBoolean isRunning;

        public ChangeColorThread(int color) {
            this.color = color;
            isRunning = new AtomicBoolean();
            isRunning.set(false);
        }

        public void run() {
            try {
                while (isRunning.get()){
                    Log.e("in thread", String.valueOf(color));
                    Thread.sleep(500);
                    color += 16;
                    if (color >= 0xFFFFFF)
                        color = 0;
                    //myLayout.setBackgroundColor(color);
                    myLayout.getRootView().setBackgroundColor(color);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void setRunningState(boolean state) {
            isRunning.set(state);
        }
    }
}
