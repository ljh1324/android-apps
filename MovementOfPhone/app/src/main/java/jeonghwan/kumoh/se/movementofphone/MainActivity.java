package jeonghwan.kumoh.se.movementofphone;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import jeonghwan.kumoh.se.movementofphone.moving.AccManager;
import jeonghwan.kumoh.se.movementofphone.moving.MotionDetector;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private AccManager accManager;
    private MotionDetector motionDetector;

    private SensorManager sensorManager;
    private Sensor accSensor;
    private TextView stateText;
    private TextView accX, accY, accZ;                          // 가속도 저장
    private EditText rightEdit, leftEdit, skyEdit, groundEdit, thresholdEdit;
    private Button applyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        // 가속도 센서 불러오기
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        /*
        마지막 파라미터인 SensorManager.SENSOR_DELAY_NORMAL 값은 센서의 민감도를 의미하는데 다음의 네 가지 값이 있습니다.

        SensorManager.SENSOR_DELAY_FASTEST
        SensorManager.SENSOR_DELAY_GAME
        SensorManager.SENSOR_DELAY_NORMAL
        SensorManager.SENSOR_DELAY_UI
         */

        stateText = (TextView) findViewById(R.id.stateText);

        accX = (TextView) findViewById(R.id.accX);
        accY = (TextView) findViewById(R.id.accY);
        accZ = (TextView) findViewById(R.id.accZ);

        rightEdit = (EditText) findViewById(R.id.rightEdit);
        leftEdit = (EditText) findViewById(R.id.leftEdit);
        skyEdit = (EditText) findViewById(R.id.skyEdit);
        groundEdit = (EditText) findViewById(R.id.groundEdit);
        thresholdEdit = (EditText) findViewById(R.id.thresholdEdit);

        applyButton = (Button) findViewById(R.id.applyButton);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    float rightLimit = Float.valueOf(rightEdit.getText().toString());
                    float leftLimit = Float.valueOf(leftEdit.getText().toString());
                    float skyLimit = Float.valueOf(skyEdit.getText().toString());
                    float groundLimit = Float.valueOf(groundEdit.getText().toString());
                    int threshold = Integer.valueOf(thresholdEdit.getText().toString());
                    accManager.setLimit(rightLimit, leftLimit, skyLimit, groundLimit, threshold);
                    Log.d("Change Status", "OK");
                } catch (Exception e) {
                    Log.d("Change Status", "NO");
                }

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        // 가속도 센서 리스너 오브젝트 등록
        sensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType())
        {
            case Sensor.TYPE_ACCELEROMETER:     // 가속도 센서
                if (accManager == null) {
                    accManager = new AccManager(event.values[0], event.values[1], event.values[2]);
                    leftEdit.setText(String.valueOf(accManager.getLeftLimit()));
                    rightEdit.setText(String.valueOf(accManager.getRightLimit()));
                    skyEdit.setText(String.valueOf(accManager.getSkyLimit()));
                    groundEdit.setText(String.valueOf(accManager.getGroundLimit()));
                    thresholdEdit.setText(String.valueOf(accManager.getShakeThreshold()));

                    motionDetector = new MotionDetector(event.values);
                }
                else
                {
                    /*
                    //accManager.setState(event.values[0], event.values[1], event.values[2]);
                    //accManager.checkShake(event.values[0], event.values[1], event.values[2]);
                    //accManager.setState2(event.values[0], event.values[1], event.values[2]);
                    //accManager.checkShake(event.values[0], event.values[1], event.values[2]);
                    accManager.setState3(event.values[0], event.values[1], event.values[2]);        // 상태설정
                    AccManager.STATE state = accManager.getState();                                 // 설정한 상태를 받아온다.

                    accX.setText(String.valueOf(event.values[0]));
                    accY.setText(String.valueOf(event.values[1]));
                    accZ.setText(String.valueOf(event.values[2]));



                    if (state == AccManager.STATE.UP) {
                        Log.d("Phone State: ", "UP!");          // 로그에 기록을 남긴다.
                        //Log.d("Phone State", accX.getText().toString() + ", " + accY.getText().toString() + ", " + accZ.getText().toString());
                        //Log.d("Phone State", accManager.deltaX + ", " + accManager.deltaY + ", " + accManager.deltaZ);
                        stateText.setText("UP!");
                    }
                    else if (state == AccManager.STATE.DOWN) {
                        Log.d("Phone State: ", "DOWN!");
                        //Log.d("Phone State", accX.getText().toString() + ", " + accY.getText().toString() + ", " + accZ.getText().toString());
                        //Log.d("Phone State", accManager.deltaX + ", " + accManager.deltaY + ", " + accManager.deltaZ);
                        stateText.setText("DOWN!");
                    }
                    else if (state == AccManager.STATE.LEFT) {
                        Log.d("Phone State: ", "LEFT!");
                        //Log.d("Phone State", accX.getText().toString() + ", " + accY.getText().toString() + ", " + accZ.getText().toString());
                        //Log.d("Phone State", accManager.deltaX + ", " + accManager.deltaY + ", " + accManager.deltaZ);
                        stateText.setText("LEFT!");
                    }
                    else if (state == AccManager.STATE.RIGHT) {
                        Log.d("Phone State: ", "RIGHT!");
                        //Log.d("Phone State", accX.getText().toString() + ", " + accY.getText().toString() + ", " + accZ.getText().toString());
                        //Log.d("Phone State", accManager.deltaX + ", " + accManager.deltaY + ", " + accManager.deltaZ);
                        stateText.setText("RIGHT!");
                    }
                    else if (state == AccManager.STATE.SKY) {
                        Log.d("Phone State: ", "SKY!");
                        //Log.d("Phone State", accX.getText().toString() + ", " + accY.getText().toString() + ", " + accZ.getText().toString());
                        //Log.d("Phone State", accManager.deltaX + ", " + accManager.deltaY + ", " + accManager.deltaZ);
                        stateText.setText("SKY!");
                    }
                    else if (state == AccManager.STATE.GROUND) {
                        Log.d("Phone State: ", "GROUND!");
                        //Log.d("Phone State", accX.getText().toString() + ", " + accY.getText().toString() + ", " + accZ.getText().toString());
                        //Log.d("Phone State", accManager.deltaX + ", " + accManager.deltaY + ", " + accManager.deltaZ);
                        stateText.setText("GROUND!");
                    }

                    else if (state == AccManager.STATE.SAHKE) {
                        Log.d("Phone State: ", "SAHKE!");
                        //Log.d("Phone State", accX.getText().toString() + ", " + accY.getText().toString() + ", " + accZ.getText().toString());
                        //Log.d("Phone State", accManager.deltaX + ", " + accManager.deltaY + ", " + accManager.deltaZ);
                        stateText.setText("SHAKE!");
                    }
                    Log.d("Phone State", accManager.deltaX + ", " + accManager.deltaY + ", " + accManager.deltaZ); */


                    //  Moving Test
                    // motionDetector.move(event.values);
                    // MotionDetector.Moving state = motionDetector.getMoving();


                    // Moving2 Test
                    // motionDetector.move2(event.values);
                    // MotionDetector.Moving2 state = motionDetector.getMoving2();

                    // Moving3 Test
                   // motionDetector.move3(event.values);
                    // MotionDetector.Moving3 state = motionDetector.getMoving3();

                    accX.setText(String.valueOf(event.values[0]));
                    accX.setText(String.valueOf(event.values[1]));
                    accX.setText(String.valueOf(event.values[2]));

                    // if (state != MotionDetector.Moving3.NOTING) {
                    // if (state != MotionDetector.Moving.NOTING) {
                    // if (state != MotionDetector.Moving2.NOTING) {
                    //    stateText.setText(state.toString());
                    //    Log.d("Phone State: ", state.toString());
                    //}

                    int detect = motionDetector.chkShake(event.values);
                    if (detect == 1) {
                        Log.d("Phone State: ", "WEEK!");
                        stateText.setText("WEEK!");
                    }
                    else if (detect == 2) {
                        Log.d("Phone State: ", "STRONG!");
                        stateText.setText("STRONG!");
                    }
                    Log.d("Phone State: ", motionDetector.getDeltaGravity()[0] + ", " + motionDetector.getDeltaGravity()[1] + ", " + motionDetector.getDeltaGravity()[2]);

                }   // end else
        }   // end swtich
    }
}
