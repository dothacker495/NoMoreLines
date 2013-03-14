package com.example.handlers;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class GeoUpdateHandler implements LocationListener {
Location location;
	
	public void onLocationChanged(Location newLocation) {
		
		location = newLocation;
	}

	
	public void onProviderDisabled(String provider) {
	}

	
	public void onProviderEnabled(String provider) {
	}

	
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
}