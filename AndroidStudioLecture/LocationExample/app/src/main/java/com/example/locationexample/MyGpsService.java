package com.example.locationexample;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class MyGpsService extends Service {     // 백 그라운드에서 작업된다.
    String GPS_FILTER = "cis470.action.GPS_LOCATION";
    Thread serviceThread;
    LocationManager lm;
    GPSListener myLocationListener;
    boolean isRunning = true;
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }
    ////@Override
    ////public void onStart(Intent intent, int startId) {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("<<MyGpsService===", "I am alive-GPS!");
// we place the slow work of the service in a back thread
        serviceThread = new Thread(new Runnable() {
            public void run() {
                //getGPSFix_Version1(); // coarse: network based
                getGPSFix_Version2(); // fine: gps-chip based
            }// run
        });
        serviceThread.start(); // get the thread going
        return START_NOT_STICKY;
    }// onStart

    public void getGPSFix_Version1() {
        try {
// Get a location as soon as possible
            LocationManager locationManager = (LocationManager)
                    getSystemService(Context.LOCATION_SERVICE);
// work with best available provider
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, false);
            Location location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
// capture location data sent by current provider
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
// assemble data bundle to be broadcasted
                Intent intentFilteredResponse = new Intent(GPS_FILTER);
                intentFilteredResponse.putExtra("latitude", latitude);
                intentFilteredResponse.putExtra("longitude", longitude);
                intentFilteredResponse.putExtra("provider", provider);
                Log.e(">>GPS_Service<<", provider + " =>Lat:" + latitude
                        + " lon:" + longitude);
// send the location data out
                sendBroadcast(intentFilteredResponse);
            }
    } catch (SecurityException e) {
        e.printStackTrace();
    }
    }

    public void getGPSFix_Version2() {
        boolean isGPSEnabled = false;
        Location location;
        double latitude = 30;
        double longitude = 30;
        try {
// using: GPS_PROVIDER
// more accuracy but needs to see the sky for satellite fixing
            Looper.prepare();
            lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            isGPSEnabled = lm
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            List<String> x = lm.getAllProviders();
            Log.e("===========", "===" + x.toString());

// This listener will catch and disseminate location updates
            myLocationListener = new GPSListener();

// define update frequency for GPS readings
            long minTime = 5; // best time: 5*60*1000 (5min)
            float minDistance = 5; // 5 meters
// request GPS updates
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    myLocationListener);
            Looper.loop();
        } catch (SecurityException e) {
            Log.e("===========", "Error in GPS" +lm.toString());
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("<<MyGpsService====>=", "I am dead-GPS");
        try {
            lm.removeUpdates(myLocationListener);
            isRunning = false;
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), 1).show();
        }
    }// onDestroy
    // ///////////////////////////////////////////////////////////////////////
    private class GPSListener implements LocationListener {
        public void onLocationChanged(Location location) {
// capture location data sent by current provider
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
// assemble data bundle to be broadcasted
            Intent myFilteredResponse = new Intent(GPS_FILTER);
            myFilteredResponse.putExtra("latitude", latitude);
            myFilteredResponse.putExtra("longitude", longitude);
            myFilteredResponse.putExtra("provider", location.getProvider());
            Log.e(">>GPS_Service<<", "Lat:" + latitude + " lon:" + longitude);
// send the location data out
            sendBroadcast(myFilteredResponse);
        }
        public void onProviderDisabled(String provider) {
        }
        public void onProviderEnabled(String provider) {
        }
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };// GPSListener class
}// MyGpsService
