package com.example.nomorelines;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import com.actionbarsherlock.app.SherlockActivity;
import com.example.objectClass.MyApplication;

public class InitialScreenActivity extends SherlockActivity {
	MyApplication app;
	LocationListener locationListener;
	LocationManager locationManager;
	Handler h;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homescreen);
		initialize();
		checkGps();
		getSupportActionBar().hide();
		new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
            	goToHome();
            }
        }, 5000);
		
	}

	public void goToHome() {
		deleteDatabase("foodOrder");
		Bundle b = new Bundle();
		Intent i = new Intent(this, HomeActivity.class);
		i.putExtras(b);

		startActivityForResult(i, 1);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		finish();
	}

	public void initialize() {
		

		app = (MyApplication)getApplicationContext();
		app.initialize();
		//app = MyApplication.getMyApplication();
		app.setUser_geo(new Double[] { 0.0, 0.0 });
		locationListener = new myLocationListener();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		try {
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 6, 4, locationListener);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void chooseToSet() {
		new AlertDialog.Builder(InitialScreenActivity.this)
				.setTitle("Network Location is not enabled")
				.setMessage("Enable it?")
				.setNegativeButton("NO", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						dialog.dismiss();

					}
				})
				.setPositiveButton("YES",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(
										Settings.ACTION_LOCATION_SOURCE_SETTINGS);
								startActivity(intent);

							}

						}).show();
	}

	public boolean checkGps() {
		LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
		boolean enabled = service
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		// Check if enabled and if not send user to the GSP settings
		// Better solution would be to display a dialog and suggesting to
		// go to the settings
		if (!enabled) {
			chooseToSet();
		}
		return service.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	}

	class myLocationListener implements LocationListener {

		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub

			if (location != null) {
				app.setUser_geo(new Double[] { location.getLatitude(),
						location.getLongitude() });
				Log.d("Location Listener",
						Double.toString(location.getLongitude()));
			}

		}

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}
	}
}
