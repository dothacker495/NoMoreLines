package com.example.nomorelines;

import static com.example.adapter.utils.CommonUtilities.URL_INIT;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockMapActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.actionbarsherlock.widget.ShareActionProvider;
import com.example.adapter.utils.CommonUtilities;
import com.example.adapter.utils.ConnectionDetector;
import com.example.adapter.utils.CustomItemizedOverlay;
import com.example.adapter.utils.CustomOverlayItem;
import com.example.adapter.utils.JsonClient;
import com.example.adapter.utils.MapOverlaystart;
import com.example.handlers.AlertDialogManager;
import com.example.objectClass.MyApplication;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;
import com.readystatesoftware.maps.OnSingleTapListener;
import com.readystatesoftware.maps.TapControlledMapView;

@SuppressLint("NewApi")
public class HomeActivity extends SherlockMapActivity {
	private MyApplication app;
	private Button fancy;
	private static final String SHARED_FILE_NAME = "shared.png";
	TapControlledMapView mapView;
	Projection projection;
	MapController mapController;
	List<Overlay> listOfOverlays;
	MapOverlaystart userOverlay;
	ImageButton refresh;
	GeoPoint user;
	AlertDialogManager alert = new AlertDialogManager();

	// Connection detector
	ConnectionDetector cd;
	Drawable drawable;
	Drawable drawable2;

	CustomItemizedOverlay<CustomOverlayItem> itemizedOverlay;
	CustomItemizedOverlay<CustomOverlayItem> itemizedOverlay2;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		getSupportActionBar().setTitle("");

		initialize();
		setListeners();
		copyPrivateRawResuorceToPubliclyAccessibleFile();
		//projectTo();
		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(HomeActivity.this, "Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		} else {
		new FoodChainTask().execute();
		}
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.share_action_provider, menu);
		MenuItem actionItem = (MenuItem) menu.findItem(R.id.share_item);
		ShareActionProvider actionProvider = (ShareActionProvider) actionItem
				.getActionProvider();
		actionProvider
				.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
		// Note that you can set/change the intent any time,
		// say when the user has selected an image.

		actionProvider.setShareIntent(createShareIntent());

		menu.add("Profile")
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {
						goProfile();
						return false;
					}
				}).setIcon(R.drawable.social_person)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return true;
	}

	public void goShare() {
		Toast.makeText(getBaseContext(), "Share", Toast.LENGTH_SHORT).show();

	}

	public void goProfile() {
		Intent i;
		if (!app.isLoggedIn()) {
			i = new Intent(this, LoginActivity.class);
		} else {
			i = new Intent(this, ProfileActivity.class);

		}
		Bundle b = new Bundle();
		i.putExtras(b);
		startActivity(i);
	}

	private void goPushActivity() {

		Intent i = new Intent(this, ResultActivity.class);
		startActivity(i);
	}

	public void initialize() {

		app = (MyApplication)getApplicationContext();
		//app = MyApplication.getMyApplication();
		fancy = (Button) findViewById(R.id.fancyButton);
		mapView = (TapControlledMapView) findViewById(R.id.maps1);
		mapView.setTraffic(true);
		projection = mapView.getProjection();
		mapController = mapView.getController();
		mapController.setZoom(18);
		listOfOverlays = mapView.getOverlays();
		user = null;
		userOverlay = null;
		mapView.setOnSingleTapListener(new OnSingleTapListener() {		

			public boolean onSingleTap(MotionEvent e) {
				itemizedOverlay.hideAllBalloons();
				return true;
			}
		});
		refresh = (ImageButton) findViewById(R.id.projectButton);
		
		// first overlay
				drawable = getResources().getDrawable(R.drawable.marker);
				itemizedOverlay = new CustomItemizedOverlay<CustomOverlayItem>(drawable, mapView);
				// set iOS behavior attributes for overlay
				itemizedOverlay.setShowClose(false);
				itemizedOverlay.setShowDisclosure(true);
				itemizedOverlay.setSnapToCenter(false);

				drawable2 = getResources().getDrawable(R.drawable.marker2);
				itemizedOverlay2 = new CustomItemizedOverlay<CustomOverlayItem>(drawable2, mapView);
				// set iOS behavior attributes for overlay
				itemizedOverlay2.setShowClose(false);
				itemizedOverlay2.setShowDisclosure(true);
				itemizedOverlay2.setSnapToCenter(false);
				
				
				
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

		}
	}
	public void setListeners() {
		fancy.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				goPushActivity();
			}
		});
		refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				projectTo();
			}
		});
	}

	public void toastThis(int sel) {
		switch (sel) {

		case (1):
			Toast.makeText(getBaseContext(), "GPS satellite not enabled",
					Toast.LENGTH_LONG).show();
			break;
		case (2):
			Toast.makeText(getBaseContext(), "Not yet detected your location",
					Toast.LENGTH_LONG).show();
			break;
		case (3):
			break;

		}
	}

	private void projectTo() {
		if (app.hasNoGeo() != true) {
			if (userOverlay != null)
				mapView.getOverlays().remove(userOverlay);
			mapView.invalidate();
			user = app.getGeoPoint();
			userOverlay = new MapOverlaystart(user, getApplicationContext());
			listOfOverlays = mapView.getOverlays();
			listOfOverlays.add(userOverlay);
			mapView.invalidate();
			mapController.animateTo(user);
		} else {

			CommonUtilities.checkNetwork(HomeActivity.this,this);
				
		}
	}

	@SuppressLint("WorldReadableFiles")
	private void copyPrivateRawResuorceToPubliclyAccessibleFile() {
		InputStream inputStream = null;
		FileOutputStream outputStream = null;
		try {
			inputStream = getResources()
					.openRawResource(R.drawable.defaultfood);
			outputStream = openFileOutput(SHARED_FILE_NAME,
					Context.MODE_WORLD_READABLE | Context.MODE_APPEND);
			byte[] buffer = new byte[1024];
			int length = 0;
			try {
				while ((length = inputStream.read(buffer)) > 0) {
					outputStream.write(buffer, 0, length);
				}
			} catch (IOException ioe) {
				/* ignore */
			}
		} catch (FileNotFoundException fnfe) {
			/* ignore */
		} finally {
			try {
				inputStream.close();
			} catch (IOException ioe) {
				/* ignore */
			}
			try {
				outputStream.close();
			} catch (IOException ioe) {
				/* ignore */
			}
		}
	}

	private Intent createShareIntent() {
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("image/*");
		Uri uri = Uri.fromFile(getFileStreamPath("shared.png"));
		shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
		return shareIntent;
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	private class FoodChainTask extends AsyncTask<Void, Void, Boolean> {
		
		private ProgressDialog pd = new ProgressDialog(HomeActivity.this);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			pd.setMessage("Downloading");
			pd.show();
		}

		@Override
		protected Boolean doInBackground(Void... urls) {
			Boolean response = true;

			JsonClient client = new JsonClient(CommonUtilities.URL_INIT);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("longitude", app.getLongitude());
			map.put("latitude", app.getLatitude());
			try {
				JSONArray json = client.getArray(CommonUtilities.URL_FOODCHAIN,
						map);
				
				for (int i = 0; i < json.length(); i++) {

					JSONObject j = json.getJSONObject(i);
					GeoPoint p = new GeoPoint((int)(Double.parseDouble(j.getString("latitude"))*1E6),(int)(Double.parseDouble(j.getString("longitude"))*1E6));
					CustomOverlayItem overlayItem = new CustomOverlayItem(p, j.getString("name"), 
							j.getString("description"),URL_INIT+j.getString("thumb_photo"));
					if(j.getString("type").contains("fast"))
						itemizedOverlay.addOverlay(overlayItem);
					else
						itemizedOverlay2.addOverlay(overlayItem);
				}

			} catch (ClientProtocolException e) {
				response = false;
				e.printStackTrace();
				Log.e("Download", e.getMessage());
			} catch (IOException e) {
				response = false;
				Log.e("Download", e.getMessage());
				e.printStackTrace();
			} catch (JSONException e) {
				response = false;
				Log.e("Download", e.getMessage());
				e.printStackTrace();
			}

			return response;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			listOfOverlays.add(itemizedOverlay);
			listOfOverlays.add(itemizedOverlay2);
			projectTo();
			pd.dismiss();
		}
	}
}
