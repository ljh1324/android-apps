package com.example.helloworld;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//public class MainActivity extends AppCompatActivity {
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {    // 한 Activity 에서 다른 Activity 로 전달하고 싶은 데이터가 있을때도 이 Bundle을 사용
        super.onCreate(savedInstanceState);

        // Activity에 있는 methid
        setContentView(R.layout.activity_main);     // res - layout - activity_main


    }
}
