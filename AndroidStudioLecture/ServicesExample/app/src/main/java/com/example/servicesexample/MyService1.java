package com.example.servicesexample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService1 extends Service {
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }
//    @Override
//    public void onStart(Intent intent, int startId) {
//        //handleStart(intent, startId);
//        Log.e ("<<Service-onStart>>", "******************************************** I am alive-1! *****************************************************************************");
//        Log.e ("<<Service-onStart>>", "******************************************** I did something ***************************************************************************");
//    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e ("<<Service-onDestroy>>", "****************************************** I am dead-1 *******************************************************************************");
    }
}
