package jeonghwan.kumoh.se.movementofphone.moving;

/**
 * Created by 이정환 on 2016-07-29.
 */
// 가속도를 N번 입력 받아서 평균을 계산해서 반환하기 위한 클래스
public class AccControl {
    private float [][] beforeAcc;
    private int cnt;

    private static final int N = 3;

    public AccControl() {
        beforeAcc = new float [3][N];
        cnt = 0;
    }

    public void move(float [] acc)
    {
        beforeAcc[0][cnt] = acc[0];
        beforeAcc[1][cnt] = acc[1];
        beforeAcc[2][cnt++] = acc[2];
        cnt %= N;
    }

    public boolean canReturn()
    {
        return (cnt == N - 1);
    }

    public float[] getAverage()
    {
        float[] avg = new float[3];
        for (int i = 1; i < N; ++i)
        {
            avg[0] += beforeAcc[0][i] - beforeAcc[0][i - 1];
            avg[1] += beforeAcc[1][i] - beforeAcc[1][i - 1];
            avg[2] += beforeAcc[2][i] - beforeAcc[2][i - 1];
        }

        for (int i = 0; i < 3; ++i)
            avg[i] /= (float) N;
        return avg;
    }
}
