package jeonghwan.kumoh.se.movementofphone.moving;

import android.util.Log;

import jeonghwan.kumoh.se.movementofphone.moving.AccControl;

/**
 * Created by 이정환 on 2016-07-27.
 */



public class MotionDetector {

    // 8방향 휴대폰 이동감지
    public enum Moving {UP, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, LEFT, UP_LEFT, NOTING};
    // 6방향(위, 아래, 왼쪽, 오른쪽, 하늘, 땅) 휴대폰 이동감지
    public enum Moving2 {UP, DOWN, LEFT, RIGHT, SKY, GROUND, NOTING };
    // 8방향 휴대폰 이동감지
    public enum Moving3 {UP, DOWN, TEMP_LEFT, TEMP_RIGHT, RIGHT, RIGHT_UP, RIGHT_DOWN, LEFT, LEFT_UP, LEFT_DOWN, NOTING };

    private float [] lastGravity;                   // x, y, z
    private float [] deltaGravity, absGravity;      // delta, abs(delta)

    private Moving lastMoving;              // 지난 동작
    private Moving2 lastMoving2;            // 지난 동작2
    private Moving3 lastMoving3;            // 지난 동작3

    private int calmCnt, changeCnt;

    // 여러 움직임으로 도형을 나타내는 용도.
    private static int [][] connected;      // 도형을 그릴 때 각각의 동작에 대해 연결되어 있나 확인하는 그래프
    private int moveCnt;                    // 몇 번 움직였는가

    // 흔들림 감지용도
    private long lastTime;
    static private final int SHAKE_THRESHOLD_STRONG = 800;
    static private final int SHAKE_THRESHOLD_WEEK = 600;

    static {
    }

    {   // 생성자에 포함되는 코드
        lastGravity = new float[3];
        deltaGravity = new float [3];
        absGravity = new float [3];
    }
    public MotionDetector(float [] gravity) {
        System.arraycopy(gravity, 0, lastGravity, 0, 3);

        lastMoving = Moving.NOTING;
        lastMoving2 = Moving2.NOTING;
        lastMoving3 = Moving3.NOTING;

        moveCnt = 0;
        calmCnt = 0;
        changeCnt = 0;

        lastTime = System.currentTimeMillis();
    }

    public void move(float [] gravity) {
       // Log.d("gravity", gravity[0] + ", " + gravity[1] + ", " + gravity[2]);

        for (int i = 0; i < 3; ++i) {
            deltaGravity[i] = gravity[i] - lastGravity[i];
            absGravity[i] = Math.abs(deltaGravity[i]);
            lastGravity[i] = gravity[i];                        // delta 값을 update후 변화
        }

        if (lastMoving == Moving.NOTING)
        {
            // 오른쪽 위(하늘) 방향
            if (deltaGravity[0] > 2.3f && deltaGravity[2] > 2.2f)
                lastMoving = Moving.UP_RIGHT;
            // 왼쪽 위(하늘) 방향
            else if (deltaGravity[0] < -2.3f && deltaGravity[2] > 2.2f)
                lastMoving = Moving.UP_LEFT;
            else if (deltaGravity[0] > 2.3f && deltaGravity[2] < -2.2f)
                lastMoving = Moving.DOWN_RIGHT;
            else if (deltaGravity[0] < -2.3f && deltaGravity[2] < -2.2f)
                lastMoving = Moving.DOWN_LEFT;
            else if (deltaGravity[0] > 3.0f)
                lastMoving = Moving.RIGHT;
            else if (deltaGravity[0] < -3.0f)
                lastMoving = Moving.LEFT;
            else if (deltaGravity[2] > 3.0f)
                lastMoving = Moving.UP;
            else if (deltaGravity[2] < -3.0f)
                lastMoving = Moving.DOWN;
            else
                return;

            // calmCnt 갱신
            calmCnt = 5;
        }
        else if (isCalm())
        {   // 한 쪽 방향으로 가속도를 받으면 다음에 반대 방향으로 가속도를 받기 때문에 안정된 상태를 calmCnt 만큼 유지하면 아무것도 아닌 상태로 돌아간다.
            calmCnt--;
            if (calmCnt <= 0)
                lastMoving = Moving.NOTING;
        }
        else
            calmCnt++;
    }

    public void move2(float [] gravity) {

        final int X = 0, Y = 1, Z = 2;

        // 값 갱신
        for (int i = 0; i < 3; ++i) {
            deltaGravity[i] = gravity[i] - lastGravity[i];
            absGravity[i] = Math.abs(deltaGravity[i]);
            lastGravity[i] = gravity[i];
        }

        if (lastMoving2 == Moving2.NOTING)
        {
            if (absGravity[X] > absGravity[Y] && absGravity[X] > absGravity[Z]) {   // 변화값이 X가 가장 클 때
                if (deltaGravity[X] < -2.5f)
                    lastMoving2 = Moving2.LEFT;     // 왼쪽이동
                else if (deltaGravity[X] > 2.5f)
                    lastMoving2 = Moving2.RIGHT;    // 오른쪽이동
                else
                    return;
            }

            else if (absGravity[Y] > absGravity[Z]) {   // 변화 값이 Y가 Z보다 클 때
                if (deltaGravity[Y] < -2.5f)
                    lastMoving2 = Moving2.DOWN;     // 아래 이동
                else if (deltaGravity[Y] > 2.5f)
                    lastMoving2 = Moving2.UP;       // 위(휴대폰 위 모서리) 이동
                else
                    return;
            }

            else if (absGravity[Z] > absGravity[X] && absGravity[Z] > absGravity[Y]) {   // 변화 값이 Z가 가장 클 때
                if (deltaGravity[Z] < -2.5f)
                    lastMoving2 = Moving2.GROUND;   // 땅 방향
                else if (deltaGravity[Z] > 2.5f)
                    lastMoving2 = Moving2.SKY;      // 하늘 방향
                else
                    return;
            }

            calmCnt = 5;
        }
        else if (isCalm())
        {
            calmCnt--;
            if (calmCnt <= 0)
                lastMoving2 = Moving2.NOTING;
        }
        else
            calmCnt++;
    }

    public void move3(float [] gravity)
    {
        final int X = 0, Y = 1, Z = 2;

        for (int i = 0; i < 3; ++i) {
            deltaGravity[i] = gravity[i] - lastGravity[i];
            absGravity[i] = Math.abs(deltaGravity[i]);
            lastGravity[i] = gravity[i];
        }

        if (lastMoving3 == Moving3.NOTING)
        {
            if (absGravity[X] > absGravity[Y] && absGravity[X] > absGravity[Z]) {   // 변화값이 X가 가장 클 때
                if (deltaGravity[X] < -2.0f)
                    lastMoving3 = Moving3.TEMP_LEFT;        // 일단 tempLeft지정
                else if (deltaGravity[X] > 2.0f)
                    lastMoving3 = Moving3.TEMP_RIGHT;
                else
                    return;
                // 대각선 방향으로 이동시켰을 때 Log값을 분석해보니 각각의 가속도 센서가 동시에 변하지 않아서 시간이 조금 지난 후에 체크를 한번더 하기 위해
                // 보통 오른쪽 위(하늘) 방향으로 움직였을때 X축 가속도 센서가 커지고 나서 가속도 센서의 값이 2번 정도 변하고 Z축 가속도 센서값이 커짐.
                changeCnt = 2;
            }

            else if (absGravity[Z] > absGravity[X] && absGravity[Z] > absGravity[Y]) {   // 변화 값이 Z가 가장 클 때
                if (deltaGravity[Z] < -2.0f)
                    lastMoving3 = Moving3.DOWN;
                else if (deltaGravity[Z] > 2.0f)
                    lastMoving3 = Moving3.UP;
                else
                    return;
                changeCnt = 2;
            }

            calmCnt = 5;
        }
        else if (lastMoving3 == Moving3.TEMP_LEFT)
        {
            if (changeCnt <= 0)
            {
                if (deltaGravity[Z] > 2.0f)
                    lastMoving3 = Moving3.LEFT_UP;
                else if (deltaGravity[Z] < -2.0f)
                    lastMoving3 = Moving3.LEFT_DOWN;
                else {
                    lastMoving3 = Moving3.LEFT;
                    calmCnt = 3;
                }
            }
        }
        else if (lastMoving3 == Moving3.TEMP_RIGHT)
        {
            if (changeCnt <= 0)
            {
                if (deltaGravity[Z] > 2.0f)
                    lastMoving3 = Moving3.RIGHT_UP;
                else if (deltaGravity[Z] < -2.0f)
                    lastMoving3 = Moving3.RIGHT_DOWN;
                else {
                    lastMoving3 = Moving3.RIGHT;
                    calmCnt = 3;
                }
            }
        }
        else if (isCalm())
        {
            calmCnt--;
            if (calmCnt <= 0)
                lastMoving3 = Moving3.NOTING;
        }
        else
            calmCnt++;
        changeCnt--;
    }

    public int chkShake(float [] gravity) {
        final int X = 0, Y = 1, Z = 2;

        long curTime = System.currentTimeMillis();
        long gabOfTime =  (curTime - lastTime);
        if (gabOfTime > 100)
        {
            lastTime = curTime;
            float speed = Math.abs(gravity[X] + gravity[Y] + gravity[Z] - lastGravity[X] - lastGravity[Y] - lastGravity[Z]) / gabOfTime * 10000;

            if (speed > SHAKE_THRESHOLD_STRONG)
                return 2;
            else if(speed > SHAKE_THRESHOLD_WEEK)
                return 1;

            lastGravity[X] = gravity[X];
            lastGravity[Y] = gravity[Y];
            lastGravity[Z] = gravity[Z];
        }

        return 0;
    }

    public Moving getMoving() {
        return lastMoving;
    }

    public Moving2 getMoving2() {
        return lastMoving2;
    }

    public Moving3 getMoving3() { return lastMoving3; }

    public float[] getDeltaGravity() {
        return deltaGravity;
    }

    private boolean isCalm()
    {
        return (absGravity[0] < 0.9f && absGravity[1] < 0.9f && absGravity[2] < 0.9f);
    }
}
