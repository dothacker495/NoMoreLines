package com.example.nomorelines;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockMapActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.example.adapter.utils.CommonUtilities;
import com.example.adapter.utils.MapOverlay;
import com.example.adapter.utils.MapOverlaystart;
import com.example.handlers.JsonParsing;
import com.example.objectClass.FoodChain;
import com.example.objectClass.MyApplication;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class LocationActivity extends SherlockMapActivity {
	MapView mapView;
	Projection projection;
	MapController mapController;
	List<Overlay> listOfOverlays;
	MapOverlaystart userOverlay;
	MyApplication app;
	GeoPoint user;
	GeoPoint foodchain;
	ImageButton refresh;
	FoodChain fc;

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.location_layout);

		initialize();
		setListeners();
		addUserOverlay();
		projectTo(0);
	}

	private void setListeners() {
		refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				projectTo(1);
			}
		});
	}

	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add("Direction")
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {
						directUser();
						return false;
					}
				}).setIcon(R.drawable.redir)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return true;
	}

	private void directUser() {
		new LoadOverlay().execute(mapView);
	}

	private GeoPoint convertToGeoPoint(double lat, double lng) {
		return new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));

	}

	private void projectTo(int i) {
		mapView.invalidate();
		if (app.hasNoGeo()) {
			CommonUtilities.checkNetwork(LocationActivity.this, this);

		} else if (i == 1) {
			if (userOverlay != null)
				mapView.getOverlays().remove(userOverlay);
			addUserOverlay();

			mapController.animateTo(user);

		} else {
			MapOverlay foodchainOverlay = new MapOverlay(foodchain,
					LocationActivity.this);
			listOfOverlays = mapView.getOverlays();
			listOfOverlays.add(foodchainOverlay);
			mapController.animateTo(foodchain);
		}
		mapView.invalidate();
	}

	public void addUserOverlay() {
		if (app.hasNoGeo() == false) {
			user = app.getGeoPoint();
			userOverlay = new MapOverlaystart(user, getApplicationContext());
			listOfOverlays = mapView.getOverlays();
			listOfOverlays.add(userOverlay);
		}
	}

	public void initialize() {
		fc = (FoodChain) getIntent().getExtras().getSerializable("fc");
		getSupportActionBar().setTitle(fc.getName());

		app = (MyApplication)getApplicationContext();
		//app = MyApplication.getMyApplication();
		mapView = (MapView) findViewById(R.id.maps);
		mapView.setTraffic(true);
		mapView.setBuiltInZoomControls(true);
		projection = mapView.getProjection();
		mapController = mapView.getController();
		mapController.setZoom(18);
		listOfOverlays = mapView.getOverlays();
		userOverlay = null;
		foodchain = convertToGeoPoint(fc.getLatitude(), fc.getLongitude());
		refresh = (ImageButton) findViewById(R.id.projectButton2);
	}

	class LoadOverlay extends AsyncTask<MapView, Void, Boolean> {

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(MapView... params) {
			Boolean result = true;
			JsonParsing pars = new JsonParsing();
			try {
				synchronized (listOfOverlays) {
					if (user != null && foodchain != null)
						pars.parsing(user, foodchain, params[0]);
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = false;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = false;
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = false;
			}
			return result;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (result == false)
				Toastthis("Failed to route");

			super.onPostExecute(result);
		}
	}

	public void Toastthis(String str) {
		Toast.makeText(getBaseContext(), str, Toast.LENGTH_SHORT).show();

	}

}
