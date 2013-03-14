package com.example.nomorelines;

import static com.example.adapter.utils.CommonUtilities.URL_INIT;
import static com.example.adapter.utils.CommonUtilities.URL_RESERVATIONS;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.example.adapter.utils.ConnectionDetector;
import com.example.adapter.utils.JsonClient;
import com.example.adapter.utils.ReservationsAdapter;
import com.example.handlers.AlertDialogManager;
import com.example.objectClass.MyApplication;
import com.example.objectClass.Reservation;

public class ViewReservationsActivity extends SherlockActivity {
	ListView list;
	ReservationsAdapter adapter;
	ArrayList<Reservation> reservationList;
	MyApplication app;
	AlertDialogManager alert = new AlertDialogManager();
	
	// Connection detector
	ConnectionDetector cd;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewreservations);
		initialize();
	}

	public void initialize() {

		list = (ListView) findViewById(R.id.reserveList);
		app = (MyApplication) getApplicationContext();
		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(ViewReservationsActivity.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		} else {
			new GetReservaTask().execute();
		}
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				goEdit((Reservation) arg1.getTag());

			}
		});

	}

	public void goEdit(Reservation reserve) {
		Intent i = new Intent(this, ReserveDescriptionActivity.class);
		Bundle b = new Bundle();
		b.putSerializable("rs", reserve);
		i.putExtras(b);
		startActivityForResult(i, 4);
	}
	class GetReservaTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			findViewById(R.id.profileLoading).setVisibility(View.VISIBLE);
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			Boolean result = false;
			reservationList = new ArrayList<Reservation>();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("id", app.getId_toS());
			JsonClient client = new JsonClient(URL_INIT);
			try {
				JSONArray json = client.getArray(URL_RESERVATIONS, map);

				for (int i = 0; i < json.length(); i++) {
					JSONObject j = json.getJSONObject(i);
					Reservation temp = new Reservation(j.getInt("id"),
							j.getString("name"), j.getString("time"),
							j.getString("date"), j.getString("thumb_photo"),
							j.getDouble("longitude"), j.getDouble("latitude"));
					reservationList.add(temp);
					Log.d("TAGGGGG", temp.getName());
					Log.d("TAGGGGG", temp.getTime());
					Log.d("TAGGGGG", temp.getDate());
					Log.d("TAGGGGG", temp.getThumbPhoto());
					Log.d("TAGGGGG", Double.toString(temp.getLatitude()));
					Log.d("TAGGGGG", Double.toString(temp.getLongitude()));

				}
				result = true;
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("Download", e.getMessage());
				result = false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e("Download", e.getMessage());
				result = false;
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.e("Download", e.getMessage());
				e.printStackTrace();
				result = false;
			}

			return result;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			setRows(reservationList);
			findViewById(R.id.profileLoading).setVisibility(View.GONE);
		}

	}
	public void setRows(ArrayList<Reservation> reserves) {
		adapter = new ReservationsAdapter(this, reserves);
		list.setAdapter(adapter);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
				new GetReservaTask().execute();
	}

}
