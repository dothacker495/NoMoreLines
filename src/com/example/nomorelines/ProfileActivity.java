package com.example.nomorelines;

import static com.example.adapter.utils.CommonUtilities.URL_INIT;
import static com.example.adapter.utils.CommonUtilities.URL_RESERVATIONS;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.example.adapter.utils.CommonUtilities;
import com.example.adapter.utils.ConnectionDetector;
import com.example.adapter.utils.DrawableManager;
import com.example.adapter.utils.HttpUploader;
import com.example.adapter.utils.JsonClient;
import com.example.adapter.utils.ServerUtilities;
import com.example.handlers.AlertDialogManager;
import com.example.objectClass.MyApplication;

public class ProfileActivity extends SherlockActivity {
	@SuppressWarnings("unused")
	private int session;
	// Asynctask
	AsyncTask<Void, Void, Void> mRegisterTask;
	final Context Ctxt = this;
	// Alert dialog manager
	String url;
	ImageView iv;
	int user;
	MyApplication app;
	ImageButton edit;
	Button b;
	AlertDialogManager alert = new AlertDialogManager();

	// Connection detector
	ConnectionDetector cd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		getSupportActionBar().setTitle("Profile");
		initialize();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Logout")
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					public boolean onMenuItemClick(MenuItem item) {
						goAlert();
						return false;
					}
				}).setIcon(R.drawable.logout2)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return super.onCreateOptionsMenu(menu);
	}

	public void initialize() {
		url = null;

		app = (MyApplication) getApplicationContext();
		// app = MyApplication.getMyApplication();
		user = app.getLogged_in();
		((TextView) findViewById(R.id.profileName)).setText(app.getName());
		iv = (ImageView) findViewById(R.id.profPicture);

		if (app.getThumbImage().isEmpty())
			Toast.makeText(getBaseContext(), "no image", Toast.LENGTH_SHORT)
					.show();
		else {

			new DrawableManager().fetchDrawableOnThread(
					URL_INIT + app.getThumbImage(), iv);
			CommonUtilities.scaleImage(iv, 90);
		}
		edit = (ImageButton) findViewById(R.id.profEditButton);
		b = (Button) findViewById(R.id.saveButton);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				cd = new ConnectionDetector(getApplicationContext());

				// Check if Internet present
				if (!cd.isConnectingToInternet()) {
					// Internet Connection is not present
					alert.showAlertDialog(ProfileActivity.this,
							"Internet Connection Error",
							"Please connect to working Internet connection",
							false);
					// stop executing code by return
					return;
				} else {
					uploadImage();
				}
			}
		});
		edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goUpload();

			}
		});
		(findViewById(R.id.reservationButton)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				goView();
			}
		});
	}
	public void goView(){
		Intent i = new Intent(this, ViewReservationsActivity.class);
		startActivity(i);
	}
	public void setImage(Bitmap bitmap) {
		iv.setImageBitmap(bitmap);
		CommonUtilities.scaleImage(iv, 90);
	}

	public void logout() {

		mRegisterTask = new AsyncTask<Void, Void, Void>() {
			ProgressDialog pd = new ProgressDialog(ProfileActivity.this);
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				pd.setMessage("Logging Out...");
				pd.show();
			}
			@Override
			protected Void doInBackground(Void... params) {
				// Register on our server
				// On server creates a new user
				ServerUtilities.deleteSession(Integer.toString(user));
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {

				mRegisterTask = null;
				pd.dismiss();
				finishing();
			}

		};
		mRegisterTask.execute(null, null, null);

	}

	public void setRes(int x) {

		if (x == 0)
			app.setLogged_in(x);

	}

	private void finishing() {
		setRes(0);
		finish();

	}

	public void goUpload() {
		Intent i = new Intent(this, ChooseActivity.class);
		startActivityForResult(i, 1);
	}

	public void changeButton(boolean ind) {
		if (ind == true) {
			b.setVisibility(View.GONE);
			edit.setVisibility(View.VISIBLE);
		} else {
			b.setVisibility(View.VISIBLE);
			edit.setVisibility(View.GONE);
		}
	}

	public void uploadImage() {
		HttpUploader hu = new HttpUploader(app.getId_toS(), app);
		hu.execute(url);
		url = null;
		changeButton(true);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 1) {
			url = data.getExtras().getString("image");
			try {
				FileInputStream fileis = new FileInputStream(url);
				BufferedInputStream bufferedstream = new BufferedInputStream(
						fileis);
				byte[] bMapArray = new byte[bufferedstream.available()];
				bufferedstream.read(bMapArray);
				Bitmap bMap = BitmapFactory.decodeByteArray(bMapArray, 0,
						bMapArray.length);
				// this is the image that you are choosen
				setImage(bMap);
				changeButton(false);
				if (fileis != null) {
					fileis.close();
				}
				if (bufferedstream != null) {
					bufferedstream.close();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}


	public void goAlert(){
		new AlertDialog.Builder(ProfileActivity.this)
		.setTitle("Logout")
		.setMessage("Are you sure you really want to Logout?")
		.setNeutralButton("NO",
				new DialogInterface.OnClickListener() {

					public void onClick(
							DialogInterface dialog,
							int which) {

						dialog.dismiss();

					}
				})
		.setPositiveButton("YES",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(
							DialogInterface dialog,
							int which) {
						logout();
						
						dialog.dismiss();
					}

				}).show();

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

		}
	}

	class GetReservaTask extends AsyncTask<Void, Void, Boolean> {
		int count;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			Boolean result = false;
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("id", app.getId_toS());
			JsonClient client = new JsonClient(URL_INIT);
			try {
				JSONArray json = client.getArray(URL_RESERVATIONS, map);

				count = json.length();
				
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
			TextView tv =(TextView)findViewById(R.id.reservedCount);
			if(count>0)
				tv.setText(Integer.toString(count));
		}

	}

}
