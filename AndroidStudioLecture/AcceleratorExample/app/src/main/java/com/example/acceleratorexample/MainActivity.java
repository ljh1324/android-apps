package com.example.acceleratorexample;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends Activity implements SensorEventListener {

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD =100;// 400;

    private ArrayList<Integer> lotteryNumbers;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
//        for (int i = 0; i < lotteryNumbers.size(); i++) {
//             Log.i("LOG", " @@@@@@@@" + lotteryNumbers.get(i));
//        }

    }

    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            long curTime = System.currentTimeMillis();
            // only allow one update every 100ms.
            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    Toast.makeText(this, "shake detected w/ speed: " + speed, Toast.LENGTH_SHORT).show();
                    getRandomNumber();
                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // can be safely ignored for this demo
    }


    private void getRandomNumber() {
        ArrayList numbersGenerated = new ArrayList<Integer>();

        for (int i = 0; i < 6; i++) {
            Random randNumber = new Random();
            int iNumber = randNumber.nextInt(48) + 1;

            if (!numbersGenerated.contains(iNumber)) {
                numbersGenerated.add(iNumber);
            } else {
                i--;
            }
        }
        TextView text = (TextView) findViewById(R.id.number_1);
        text.setText("" + numbersGenerated.get(0));
        text = (TextView) findViewById(R.id.number_2);
        text.setText("" + numbersGenerated.get(1));
        text = (TextView) findViewById(R.id.number_3);
        text.setText("" + numbersGenerated.get(2));
        text = (TextView) findViewById(R.id.number_4);
        text.setText("" + numbersGenerated.get(3));
        text = (TextView) findViewById(R.id.number_5);
        text.setText("" + numbersGenerated.get(4));
        text = (TextView) findViewById(R.id.number_6);
        text.setText("" + numbersGenerated.get(5));

        FrameLayout ball1 = (FrameLayout) findViewById(R.id.ball_1);
        ball1.setVisibility(View.INVISIBLE);
        FrameLayout ball2 = (FrameLayout) findViewById(R.id.ball_2);
        ball2.setVisibility(View.INVISIBLE);
        FrameLayout ball3 = (FrameLayout) findViewById(R.id.ball_3);
        ball3.setVisibility(View.INVISIBLE);
        FrameLayout ball4 = (FrameLayout) findViewById(R.id.ball_4);
        ball4.setVisibility(View.INVISIBLE);
        FrameLayout ball5 = (FrameLayout) findViewById(R.id.ball_5);
        ball5.setVisibility(View.INVISIBLE);
        FrameLayout ball6 = (FrameLayout) findViewById(R.id.ball_6);
        ball6.setVisibility(View.INVISIBLE);

        Animation a = AnimationUtils.loadAnimation(this, R.anim.move_down_ball_first);
        ball6.setVisibility(View.VISIBLE);
        ball6.clearAnimation();
        ball6.startAnimation(a);

        ball5.setVisibility(View.VISIBLE);
        ball5.clearAnimation();
        ball5.startAnimation(a);

        ball4.setVisibility(View.VISIBLE);
        ball4.clearAnimation();
        ball4.startAnimation(a);

        ball3.setVisibility(View.VISIBLE);
        ball3.clearAnimation();
        ball3.startAnimation(a);

        ball2.setVisibility(View.VISIBLE);
        ball2.clearAnimation();
        ball2.startAnimation(a);

        ball1.setVisibility(View.VISIBLE);
        ball1.clearAnimation();
        ball1.startAnimation(a);
    }
}