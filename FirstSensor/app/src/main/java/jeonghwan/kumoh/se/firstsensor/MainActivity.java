package jeonghwan.kumoh.se.firstsensor;

import android.hardware.SensorEventListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.hardware.*;              // Sensor, SensorManager

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    // 가속도 센서값을 출력하기 위한 TextView
    TextView tvAX = null;
    TextView tvAY = null;
    TextView tvAZ = null;
    double previousAX;
    double previousAY;
    double previousAZ;
    boolean isFirst1;       // accelerometer값이 처음으로 변했나 체크

    // for no gravity
    double[] gravity = new double[3];

    // 방향 센서값을 출력하기 위한 TextView
    TextView tvOX = null;
    TextView tvOY = null;
    TextView tvOZ = null;
    double previousOX;
    double previousOY;
    double previousOZ;
    boolean isFirst2;       // orientation값이 처음으로 변했나 체크

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

        isFirst1 = isFirst2 = false;

        // SensorManager 인스턴스를 가져옴
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        // 가속도 센서
        accSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // 방향 센서
        oriSensor = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);

    }

    /*

    이벤트를 구현하기 위하여 인터페이스를 상속받고, 센서 객체를 정의하였으면 이제 센서 객체와 이벤트를 연결해 주어야 합니다.
    센서 객체는 이벤트가 연결되고 나면 쉴세 없이 핸드폰의 움직임을 이벤트 핸들러에게 전달해 주기 때문에 필요한 경우에만 연결되어 있도록 하는 것이 좋습니다.

    만약, App을 사용하다가 전화가 와서 통화를 해야 하는 상황이 발생했다면 그 동안에는 센서의 움직임을 감지할 필요가 없을 것입니다.

    Activity는 명시적으로 back키를 누르거나 finish() 메서드가 호출되기 전에는 계속 살아 있기 때문에
    onCreate() 에서 초기화 하고나면 전화를 걸거나 문자메시지를 수신하는 등 외부의 이벤트가 발생할 경우 일시 중지 상태가 되는 것이 아니라
    지속적으로 실행되게 됩니다. 이런 현상을 방지하고 적정시기에 센서 이벤트를 중지/재시작 하기 위해서
    Activity LifeCycle에 해당하는 onResume() 메서드와 onPause() 메서드에서 각각 Sensor 이벤트 연결과 Sensor이벤트 연결 끊기를 처리합니다.

    */
    @Override
    public void onResume() {
        super.onResume();
        // 가속도 센서 리스너 오브젝트를 등록
        sm.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_GAME);
        // 방향 센서 리스너 오브젝트를 등록
        sm.registerListener(this, oriSensor, SensorManager.SENSOR_DELAY_GAME);

        /*
        마지막 파라미터인 SensorManager.SENSOR_DELAY_NORMAL 값은 센서의 민감도를 의미하는데 다음의 네 가지 값이 있습니다.

        SensorManager.SENSOR_DELAY_FASTEST
        SensorManager.SENSOR_DELAY_GAME
        SensorManager.SENSOR_DELAY_NORMAL
        SensorManager.SENSOR_DELAY_UI
         */
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
            case Sensor.TYPE_ACCELEROMETER:         // 가속도센서

                tvAX.setText(String.valueOf(event.values[0]));          // X축
                tvAY.setText(String.valueOf(event.values[1]));          // Y축
                tvAZ.setText(String.valueOf(event.values[2]));          // Z축

                if (!isFirst1)
                {
                    previousAX = Double.valueOf(tvAX.getText().toString());
                    previousAY = Double.valueOf(tvAY.getText().toString());
                    previousAZ = Double.valueOf(tvAZ.getText().toString());

                    isFirst1 = true;
                }
                else
                {
                    TextView tempX = (TextView) findViewById(R.id.delta01XValue);
                    TextView tempY = (TextView) findViewById(R.id.delta01YValue);
                    TextView tempZ = (TextView) findViewById(R.id.delta01ZValue);

                    double curAX = Double.valueOf(tvAX.getText().toString());
                    double curAY = Double.valueOf(tvAY.getText().toString());
                    double curAZ = Double.valueOf(tvAZ.getText().toString());

                    double deltaX = previousAX - curAX;
                    double deltaY = previousAY - curAY;
                    double deltaZ = previousAZ - curAZ;

                    if (deltaX > 18.5 || deltaX < -18.5) {
                        previousAX = curAX;
                        tempX.setText(String.valueOf(deltaX));
                    }


                    if (deltaY > 18.5 || deltaY < -18.5) {
                        previousAY = curAY;
                        tempY.setText(String.valueOf(deltaY));
                    }

                    if (deltaZ > 18.5 || deltaZ < -18.5) {
                        previousAZ = curAZ;
                        tempZ.setText(String.valueOf(deltaZ));
                    }
                }
                break;

            case Sensor.TYPE_ORIENTATION:           // 방향센서
                tvOX.setText(String.valueOf(event.values[0]));          // Z축 중심으로 회전 (0 <= azimuth < 360)
                tvOY.setText(String.valueOf(event.values[1]));          // X축 중심으로 회전 (-180 <= pitch <= 180)
                tvOZ.setText(String.valueOf(event.values[2]));          // Y축 중심으로 회전 (-90 <= roll <= 90)

                if (!isFirst2)
                {
                    previousOX = Double.valueOf(tvOX.getText().toString());
                    previousOY = Double.valueOf(tvOY.getText().toString());
                    previousOZ = Double.valueOf(tvOZ.getText().toString());

                    isFirst2 = true;
                }
                else
                {
                    TextView tempX = (TextView) findViewById(R.id.delta02XValue);
                    TextView tempY = (TextView) findViewById(R.id.delta02YValue);
                    TextView tempZ = (TextView) findViewById(R.id.delta02ZValue);

                    double curOX = Double.valueOf(tvOX.getText().toString());
                    double curOY = Double.valueOf(tvOY.getText().toString());
                    double curOZ = Double.valueOf(tvOZ.getText().toString());

                    double deltaX = previousOX - curOX;
                    double deltaY = previousOY - curOY;
                    double deltaZ = previousOZ - curOZ;

                    if (deltaX > 30.0 || deltaX < -30.0)
                    {
                        previousOX = curOX;
                        tempX.setText(String.valueOf(deltaX));
                    }

                    if (deltaY > 30.0 || deltaY < -30.0)
                    {
                        previousOY = curOY;
                        tempY.setText(String.valueOf(deltaY));
                    }

                    if (deltaZ > 30.0 || deltaZ < -30.0)
                    {
                        previousOY = curOY;
                        tempZ.setText(String.valueOf(deltaZ));
                    }
                }
                break;
            /*
            values[0] : Z 축을 중심으로 회전 (0 <= azimuth < 360)
                 0  = 북, 90 = 동, 180 = 남, 270 = 서

            values[1] : X축을 중심으로 회전 (-180 <= pitch <= 180)
                 Z축이 Y축 방향으로 향하면 0보다 큰값
                 화면이 하늘을 향하고 테이블위에 수평으로 놓여있는  상태 0, 화면이 아래를 향하면 -180 or 180,
                 똑바로 세우면 -90, 거꾸로 세우면 +90

            values[2] : Y축을 중심으로 회전 (-90 <= roll <= 90)
                  Z축이 X축 방향으로 향하면 0보다 큰값
             */
        }
    }
}
