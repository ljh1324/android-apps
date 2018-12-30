package com.example.locationexample;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyGPS extends Activity implements View.OnClickListener {
    TextView txtMsg;
    Button btnStopService;
    Button btnDrawGoogleMap;
    TextView txtTopMsg;
    ComponentName service;
    Intent intentMyService;
    BroadcastReceiver receiver;
    String GPS_FILTER = "cis470.action.GPS_LOCATION";
    double latitude;
    double longitude;
    String provider;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtMsg = (TextView) findViewById(R.id.txtMsg);
        txtTopMsg = (TextView) findViewById(R.id.txtTopLine);
        findViewById(R.id.btnStopService).setOnClickListener(this);
        findViewById(R.id.btnStartService).setOnClickListener(this);
        findViewById(R.id.btnDrawMap).setOnClickListener(this);
        getMyLocationServiceStarted();
// register & define filter for local listener
        IntentFilter myLocationFilter = new IntentFilter(GPS_FILTER);       // GPS_FILTER intent filter를 가진 컴포넌트를 실행할 수 있게 해준다.
        receiver = new MyMainLocalReceiver();                               // BroadCastReceiver 생성
        registerReceiver(receiver, myLocationFilter);                       // BroadCastReceiver 등록
    }//onCreate

    public void getMyLocationServiceStarted(){
// get background service started
        txtMsg.append("\nMyGpsService started/restarted - (see LogCat)");
        intentMyService = new Intent(this, MyGpsService.class);                    // MyGpsService 앱 구성요소로 작업 요청 준비
        service = startService(intentMyService);                                   // 백그라운드에서 수행
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            stopService(intentMyService);
            unregisterReceiver(receiver);
        } catch (Exception e) {
            Log.e ("MAIN-DESTROY>>>", e.getMessage() );
        }
        Log.e ("MAIN-DESTROY>>>" , "Adios" );
    }// onDestroy
    // local RECEIVER
    private class MyMainLocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context localContext, Intent intentFilteredResponse) {
            latitude = intentFilteredResponse.getDoubleExtra("latitude",-1);
            longitude = intentFilteredResponse.getDoubleExtra("longitude",-1);
            provider = intentFilteredResponse.getStringExtra("provider");
            Log.e ("MAIN>>>", Double.toString(latitude));
            Log.e ("MAIN>>>", Double.toString(longitude));
            Log.e ("MAIN>>>", provider);
            String msg = provider
                    + " lat: " + Double.toString(latitude) + " "
                    + " lon: " + Double.toString(longitude);
            txtMsg.append("\n" + msg);
        }
    }//MyMainLocalReceiver

    public void drawGoogleMap(double latitude, double longitude){
// // this looks good on a big screen
     String myGeoCode = "https://maps.google.com/maps?q="
     + latitude
     + ","
     + longitude
     + "(You are here!)&iwloc=near&hl=en";

// this looks better on a small screen
//        String myGeoCode = "geo:" + latitude
//                + "," + longitude
//                + "?z=15";
        Intent intentViewMap = new Intent(Intent.ACTION_VIEW,
                Uri.parse(myGeoCode));
        startActivity(intentViewMap);
    }
    @Override
    public void onClick(View v) {
// stop service
        if ( v.getId() == R.id.btnStopService ) {
            try {
                stopService(new Intent(intentMyService) );
                txtMsg.setText("After stopping Service: " +
                        service.getClassName());
                btnStopService.setText("Finished");
                btnStopService.setClickable(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
// draw a Google map with given coordinates
        } else if (v.getId() == R.id.btnDrawMap ){
            drawGoogleMap(latitude, longitude);
// re-start service
        } else if (v.getId() == R.id.btnStartService ){
            getMyLocationServiceStarted();
        }
    }
}//MyGPS