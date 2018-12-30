package jeonghwan.kumoh.se.movementofphone.moving;

import android.util.Log;

/**
 * Created by 이정환 on 2016-07-10.
 */


public class AccManager {
    public enum STATE {SAHKE, UP, DOWN, LEFT, RIGHT, SKY, GROUND, NOTING }      // 흔들기, (휴대폰을 눕혔을때)+Y, -Y, -X, +X, +Z, -Z

    private float lastX, lastY, lastZ;
    public float deltaX, deltaY, deltaZ;

    private float rightLimit, leftLimit, skyLimit, groundLimit;

    private int calmCount;

    private STATE curState;

    // for SHAKE
    private long lastTime;
    private static int shakeThreshold = 3500;

    // Constructor
    public AccManager(float accX, float accY, float accZ) {
        lastX = accX; lastY = accY; lastZ = accZ;
        lastTime = 0;
        calmCount = 0;
        skyLimit = 15.0f;
        groundLimit = 5.2f;
        rightLimit = 7.0f;
        leftLimit = -7.0f;

        //setState(accX, accY, accZ);
        setState2(accX, accY, accZ);
    }

    // 중력 가속도 값에 따라 휴대폰이 바라보고 있는 방향 결정.
    public void setState(float accX, float accY, float accZ) {
        final float GRAVITY = 6.0f;

        if (accX > GRAVITY && accX > accY && accX > accZ)
            curState = STATE.LEFT;
        else if (accX < -GRAVITY && accX < accY && accX < accZ)
            curState = STATE.RIGHT;

        else if (accY > GRAVITY && accY > accX && accY > accZ)
            curState = STATE.DOWN;
        else if (accY < -GRAVITY && accY < accX)        // 중력을 무시하기 위해서 accZ와는 비교하지 않았음.
            curState = STATE.UP;

        else if (accZ > GRAVITY && accZ > accX && accZ > accY)
            curState = STATE.SKY;
        else if (accZ < -GRAVITY && accZ < accX && accZ < accY)
            curState = STATE.GROUND;

        else
            curState = STATE.NOTING;





    }

    public void setState2(float accX, float accY, float accZ)
    {
        if (curState == STATE.NOTING) {  // 아무런 상태가 아닐 때 값 변화.

            if (accZ > skyLimit)              // + 중력가속도(9.0~9.5) || 위로 들면서 휴대폰이 내 쪽으로 기울어질 경우
                curState = STATE.SKY;
            else if (accZ < groundLimit)              // + 중력가속도(9.0~9.5)
                curState = STATE.GROUND;
            else if (accX > rightLimit)
                curState = STATE.RIGHT;
            else if (accX < leftLimit)
                curState = STATE.LEFT;
            else
                return;

            calmCount = 5;          // 가속도 값이 5번정도로 일정할 때 Nothing 상태로 본다
        }
        else if (-4.3f < accX && accX < 4.9f && 6.7f < accZ && accZ < 9.9f)    // 하늘을 보고 가만이 놔두었을때.
        {
            if (calmCount == 0) {
                curState = STATE.NOTING;
            } else {
                calmCount -= 1;
            }
        }

        //Log.d("Phone State: ", curState.toString());

    }

    public void setState3(float accX, float accY, float accZ) {
        deltaX = accX - lastX;  // 휴대폰이 하늘방향을 보고 있을때 양수이면 오른쪽으로 이동
        deltaY = accY - lastY;  // 양수이면 위쪽으로 이동
        deltaZ = accZ - lastZ;  // 양수이면 하늘쪽으로 이동

        lastX = accX; lastY = accY; lastZ = accZ;

        float absDeltaX = Math.abs(deltaX);
        float absDeltaY = Math.abs(deltaY);
        float absDeltaZ = Math.abs(deltaZ);

        if (curState == STATE.NOTING) {
            if (absDeltaX > absDeltaY && absDeltaX > absDeltaZ) {   // 변화값이 X가 가장 클 때
                if (deltaX < -3.0f)
                    curState = STATE.LEFT;
                else if (deltaX > 3.0f)
                    curState = STATE.RIGHT;
                else
                    return;
                calmCount = 5;
            }

            else if (absDeltaY > absDeltaZ) {   // 변화 값이 Y가 Z보다 클 때
                if (deltaY < -3.0f)
                    curState = STATE.DOWN;
                else if (deltaY > 3.0f)
                    curState = STATE.UP;
                else
                    return;
                calmCount = 5;
            }

            if (absDeltaZ > absDeltaX && absDeltaZ > absDeltaY) {   // 변화 값이 Z가 가장 클 때
                if (deltaZ < -3.0f)
                    curState = STATE.GROUND;
                else if (deltaZ > 3.0f)
                    curState = STATE.SKY;
                else
                    return;
                calmCount = 5;
            }
        }
        else if (absDeltaX < 0.9f && absDeltaY < 0.9f && absDeltaZ < 0.9f) {
            if (calmCount == 0)
            {
                curState = STATE.NOTING;
            } else {
                calmCount--;
            }
        }
    }

    public STATE getState() {
        return curState;
    }


    public void checkShake(float accX, float accY, float accZ)
    { // http://pulsebeat.tistory.com/44
        long curTime = System.currentTimeMillis();
        long gabOfTime =  (curTime - lastTime);
        if (gabOfTime > 100)
        {
            lastTime = curTime;
            float speed = Math.abs(accX + accY + accZ - lastX - lastY - lastZ) / gabOfTime * 10000;

            if (speed > shakeThreshold) {
                curState =  STATE.SAHKE;
                calmCount = 10;
            }

            lastX = accX;
            lastY = accY;
            lastZ = accZ;
        }
    }

    public float getRightLimit() { return rightLimit; }
    public float getLeftLimit() { return leftLimit; }
    public float getSkyLimit() { return skyLimit; }
    public float getGroundLimit() { return groundLimit; }
    public int getShakeThreshold() { return shakeThreshold; }

    public void setLimit(float right, float left, float sky, float ground, int threshold)
    {
        rightLimit = right;
        leftLimit = left;
        skyLimit = sky;
        groundLimit = ground;
        shakeThreshold = threshold;
    }
}
