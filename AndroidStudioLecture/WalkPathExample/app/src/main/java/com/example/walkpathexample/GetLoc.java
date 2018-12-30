package com.example.walkpathexample;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class GetLoc extends Service implements LocationListener {

	private Context Context;
	boolean isGPSEnabled = false;
	boolean isNetworkEnabled = false;
	//boolean canGetLocation = false;
	Location location;
	Location mylocation = null;
	public double latitude;
	double longitude;
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 40;// 40 meters
	private static final long MIN_TIME_BW_UPDATES = 1000 * 6; // ///1000 * 60 *
																// 2;
	// update location within a time period of 2 minutes

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		stopSelf();
		stopUsingGPS();
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startid) {
		// TODO Auto-generated method stub
		Context = this;
		Log.e("tag", "on start");
		mylocation = getLocation(Context);

		if( mylocation == null ) {
			mylocation = new Location("");
			mylocation.setLatitude(36.143108);
			mylocation.setLongitude(128.393569);
		}

		return START_NOT_STICKY;
	}

	protected LocationManager locationManager;

	public Location getLocation(Context Context) {

		try {
			locationManager = (LocationManager) Context
					.getSystemService(LOCATION_SERVICE);
			isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled) {
				Toast.makeText(this, "Enable Network or GPS",
						Toast.LENGTH_LONG).show();
			} else {
				//this.canGetLocation = true;
				Log.e("+++++!!!!", "Network " + isNetworkEnabled);
				if (isNetworkEnabled) {
					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER,
							MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					if (locationManager != null) {
						Log.e("=====", "manager OKkkkkkkk");

						location = locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						///Log.e("=====", "xxx" + location.toString());
						if (location != null) {
							latitude = location.getLatitude();
							longitude = location.getLongitude();
						}
						else {
							latitude = 127;
							longitude = 38;
						}
					}

				}

				if (isGPSEnabled) {
					if (location == null) {
						locationManager.requestLocationUpdates(
								LocationManager.GPS_PROVIDER,
								MIN_TIME_BW_UPDATES,
								MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						Log.d("GPS Enabled", "GPS Enabled");
						if (locationManager != null) {
							location = locationManager
									.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (location != null) {
								latitude = location.getLatitude();
								longitude = location.getLongitude();
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return location;
	}

	public void stopUsingGPS() {
		if (locationManager != null) {
			locationManager.removeUpdates(GetLoc.this);
		}
	}

	public double getLatitude() {
		if (location != null) {
			latitude = location.getLatitude();
		}
		return latitude;
	}

	public double getLongitude() {
		if (location != null) {
			longitude = location.getLongitude();
		}
		return longitude;
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.i("Tag", "location changed");
		Intent intent1 = new Intent("com.example.map");
		intent1.putExtra("lati", location.getLatitude());
		intent1.putExtra("longi", location.getLongitude());
		Log.e("Loc Change",
				location.getLatitude() + " " + location.getLongitude());
		sendBroadcast(intent1);
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}