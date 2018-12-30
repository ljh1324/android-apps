package jeonghwan.kumoh.se.linearacceleration;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    // 가속도 센서값을 출력하기 위한 TextView
    TextView tvAX = null;
    TextView tvAY = null;
    TextView tvAZ = null;
    float[] gravity;
    float[] linearAcceleration;
    boolean isFirstChange;

    // 방향 센서값을 출력하기 위한 TextView
    TextView tvOX = null;
    TextView tvOY = null;
    TextView tvOZ = null;

    // 센서 관리자
    SensorManager sm = null;
    // 가속도 센서
    Sensor accSensor = null;
    // 방향 센서
    Sensor oriSensor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvAX = (TextView) findViewById(R.id.tvAX);
        tvAY = (TextView) findViewById(R.id.tvAY);
        tvAZ = (TextView) findViewById(R.id.tvAZ);
        tvOX = (TextView) findViewById(R.id.tvOX);
        tvOY = (TextView) findViewById(R.id.tvOY);
        tvOZ = (TextView) findViewById(R.id.tvOZ);

        // SensorManager 인스턴스를 가져옴
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        // 가속도 센서
        accSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // 방향 센서
        oriSensor = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        gravity = new float[3];
        linearAcceleration = new float[3];
        isFirstChange = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 가속도 센서 리스너 오브젝트를 등록
        sm.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
        // 방향 센서 리스너 오브젝트를 등록
        sm.registerListener(this, oriSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        // 센서에서 이벤트 리스너 분리
        sm.unregisterListener(this);
        sm.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:

                if (isFirstChange) {
                    gravity[0] = event.values[0];
                    gravity[1] = event.values[1];
                    gravity[2] = event.values[2];
                    isFirstChange = false;
                } else {
                    final float alpha = 0.8f;

                    gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
                    gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
                    gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

                    linearAcceleration[0] = event.values[0] - gravity[0];
                    linearAcceleration[1] = event.values[1] - gravity[1];
                    linearAcceleration[2] = event.values[2] - gravity[2];

                    if (linearAcceleration[0] < 0.1f) linearAcceleration[0] = 0.0f;
                    if (linearAcceleration[1] < 0.1f) linearAcceleration[1] = 0.0f;
                    if (linearAcceleration[2] < 0.1f) linearAcceleration[2] = 0.0f;

                    tvAX.setText(String.valueOf(linearAcceleration[0]));
                    tvAY.setText(String.valueOf(linearAcceleration[1]));
                    tvAZ.setText(String.valueOf(linearAcceleration[2]));
                }
                break;
            case Sensor.TYPE_ORIENTATION:
                tvOX.setText(String.valueOf(event.values[0]));
                tvOY.setText(String.valueOf(event.values[1]));
                tvOZ.setText(String.valueOf(event.values[2]));
                break;
        }
    }

}
