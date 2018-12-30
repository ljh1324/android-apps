package com.example.gpsexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
	TextView tv;
	double latitude;
	double longi;
	private double pLati, plongi, dLati, dlongi;// previous latitude and
												// longitude
	private GoogleMap map;
	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			Log.i("Here", "onreceived");
			if (bundle != null) {

				latitude = bundle.getDouble("lati");
				longi = bundle.getDouble("longi");
				drawmap(latitude, longi);
			}
		}
	};

	public void drawmap(double latid, double longid) {
		Log.i("map", "drawmap" + latid + "  " + longid);

		LatLng prev = new LatLng(pLati, plongi);
		LatLng my = new LatLng(latid, longid);
		map.addPolyline(new PolylineOptions().add(prev, my).width(3)
				.color(Color.BLUE)); // make polyline
		map.addMarker(new MarkerOptions().position(my).title(
				"Lat:" + latid + " Lon:" + longid)); // add marker
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(my, 10.0f), 2000, null);
		pLati = latid;
		plongi = longid;
	}

	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(receiver, new IntentFilter("com.example.map"));
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiver);
		this.onStop();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
//
//		int permissionCheck = ContextCompat.checkSelfPermission(this,
//				android.Manifest.permission.ACCESS_FINE_LOCATION);
//		if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
//			// ask permissions here using below code
//			ActivityCompat.requestPermissions(this,
//					new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//					111);
//		}

		final double src_lati = 36.143108; // initial position
		final double src_longi = 128.393569;
		final double dest_lati = 36.143108;
		final double dest_longi = 128.393569;

		pLati = src_lati;
		plongi = src_longi;

		dLati = dest_lati;
		dlongi = dest_longi;

		Intent loc_intent;
		loc_intent = new Intent(getBaseContext(), GetLoc.class);
		loc_intent.putExtra("lat", dLati);
		loc_intent.putExtra("lon", dlongi);
		startService(loc_intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		map = googleMap;

		UiSettings mapUI = map.getUiSettings();
		// enable: pan, zoom, tilt, rotate
		mapUI.setAllGesturesEnabled(true);
		// enable compass
		mapUI.setCompassEnabled(true);
		// enable zoom controls
		mapUI.setZoomControlsEnabled(true);

		// draw line between initial and final location.
		LatLng strt = new LatLng(pLati, plongi);
		LatLng end = new LatLng(dLati, dlongi);
		map.addMarker(new MarkerOptions().position(strt).title("Start"));
		//map.animateCamera(CameraUpdateFactory.newLatLngZoom(strt, 16.0f));
		map.setOnMapClickListener(new GoogleMap.OnMapClickListener()
		{
			@Override
			public void onMapClick(LatLng arg0)
			{
				// do something
			}
		});
	}
}