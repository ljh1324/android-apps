package com.example.asynctaskexample;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    ProgressBar mProgress;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgress=(ProgressBar)findViewById(R.id.progress_bar);
        button=(Button)findViewById(R.id.btnStart);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                new MyAsyncTask().execute(15);
            }
        });
    }

    /**
     * AsyncTask<Integer, Integer, Boolean>은 아래와 같은 형식이다
     * AsyncTask<전달받은값의종류, Update값의종류, 결과값의종류>      // 전달받은 값(excute할 때 interger형식으로 받는다), Update를 할때 doInBackground에서 Integer로 반환한다, 결과값의 종류(onPostExcute(Integer))
     * ex) AsyncTask<Void, Integer, Void>
     */
    public class MyAsyncTask extends AsyncTask<Integer, Integer, Integer> {

        /**
         * execute() 메소드가 호출되면 doInBackground()가 돌기 전 이 메소드가 호출됨.
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * 백그라운드 작업
         *
         * @param integers
         * @return
         */
        @Override
        protected Integer doInBackground(Integer... integers) {
            // 1부터 integers[0]까지의 곱을 구한다.
            int result = 1;
            int num = integers[0];      // excute를 호출할때 받아온 파라미터


            try {
                for (int i = 1; i <= num; i++) {
                    result = result * i;
                    Thread.sleep(1000);
                    publishProgress((100/num) * i);
                }


            } catch (InterruptedException e) {
                Log.e("t1:<<runnable>>", e.getMessage());
            }
            return result;
        }

        /**
         * 백그라운드 진행 상황을 업데이트하기 위한 표시
         *
         * @param params
         */
        @Override
        protected void onProgressUpdate(Integer... params) {
            mProgress.setProgress(params[0]);
        }

        /**
         * 백그라운드 작업이 완료된 후 호출되는 메소드
         *
         * @param result
         */
        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            mProgress.setProgress(100);
            button.setText(result.toString());
        }
    }


}
