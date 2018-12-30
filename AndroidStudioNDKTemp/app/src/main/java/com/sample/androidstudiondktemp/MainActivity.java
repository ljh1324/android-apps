package com.sample.androidstudiondktemp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final static String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NDKTemp ndk = new NDKTemp();
        String strResult = ndk.stringFromJNI();

        Log.d(LOG_TAG, "result:"+strResult);

        TextView tv = (TextView) findViewById(R.id.tv_jni);
        tv.setText(strResult);
    }
}