package com.example.nomorelines;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.example.adapter.utils.CommonUtilities;
import com.example.adapter.utils.ConnectionDetector;
import com.example.adapter.utils.JsonClient;
import com.example.adapter.utils.ServerUtilities;
import com.example.handlers.AlertDialogManager;
import com.example.objectClass.MyApplication;
import com.google.android.gcm.GCMRegistrar;

public class LoginActivity extends SherlockActivity {
	private ProgressDialog dialog;
	private int user_id;
	private String uname;
	private String photo;
	Button login;
	EditText username, password;
	String user, pass;
	MyApplication app;
	Intent i;
	private String regId;

	// Asynctask
	AsyncTask<Void, Void, Void> mRegisterTask;

	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();

	// Connection detector
	ConnectionDetector cd;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		getSupportActionBar().setTitle("Login");
		i = getIntent();

		app = (MyApplication)getApplicationContext();
		//app = MyApplication.getMyApplication();
		dialog = new ProgressDialog(this);
		dialog.setTitle("Logging in");
		dialog.setMessage("Verifying Account...");
		user_id = 0;
		uname = "Unknown";
		
		login = (Button) findViewById(R.id.submitLogReg);
		username = (EditText) findViewById(R.id.usernameLogReg);
		password = (EditText) findViewById(R.id.passwordLogReg);
		TextView rt = (TextView) findViewById(R.id.textView3);
		rt.setText("Register?");
		login.setText("Login");
		rt.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				goRegister();
			}
		});
		login.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				goLogin();
			}
		});
		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(LoginActivity.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}

	}

	public void goRegister() {
		Intent i = new Intent(this, Register.class);
		startActivity(i);
	}

	public void goLogin() {
		user = username.getText().toString();
		pass = password.getText().toString();
		doWebService();

	}

	public void goFish() {
		Log.d("goFish", "1");

		GCMRegistrar.checkDevice(this);
		Log.d("goFish", "2");
		GCMRegistrar.checkManifest(this);

		Log.d("goFish", "3");
		// Get GCM registration id
		regId = GCMRegistrar.getRegistrationId(this);
		final Context context = this;
		// Check if regid already presents
		if (regId.equals("")) {
			// Registration is not present, register now with GCM
			Log.d("goFish", "4");
			GCMRegistrar.register(this, CommonUtilities.SENDER_ID);
			regId = GCMRegistrar.getRegistrationId(this);
			
		} else {
			// Device is already registered on GCM
			Log.d("goFish", "6");
			if (GCMRegistrar.isRegisteredOnServer(this)) {
				Log.d("goFish", "7");
				// Skips registration.
				registerGCM();
			} else {

				// Try to register again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(),
				// hence the use of AsyncTask instead of a raw thread.
				Log.d("goFish", "8");
				mRegisterTask = new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						// Register on our server
						// On server creates a new user
						Log.d("goFish", "9");
						Log.d("Server", Integer.toString(user_id) + regId);
						ServerUtilities.registerSession(context,
								Integer.toString(user_id), regId);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						Log.d("goFish", "10");
						mRegisterTask = null;
						Log.d("goFish", "11");
						registerGCM();
					}

				};
				mRegisterTask.execute(null, null, null);

			}

		}
	}

	private void registerGCM() {

		SendServer s = new SendServer();

		s.execute();
	}

	

	public void goToMain() {
		Log.d("Profile", "999");
		finish();

	}

	public void goBack() {
		TextView tv = (TextView) findViewById(R.id.warning);
		tv.setText("Wrong Username or Password");
		username.setText("");
		password.setText("");
	}

	private void doWebService() {

		LoginWebService task = new LoginWebService();
		task.execute(new String[] { CommonUtilities.URL_INIT, "login.php" });
	}

	private class SendServer extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// Register on our server
			// On server creates a new user
			Log.d("Register", "1");
			Log.d("Register", "2-" + regId);
			ServerUtilities.registerToServer(Integer.toString(user_id), regId);

			Log.d("Register", "7");
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			Log.d("Register", "8");
			goToMain();
		}

	}

	private class LoginWebService extends AsyncTask<String, Void, String> {

		protected void onPreExecute() {
			dialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			String result = "";
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", user);
			map.put("pass", pass);
			JsonClient client = new JsonClient(params[0]);
			try {
				JSONArray json = client.getArray(params[1], map);

				if (json.length() > 0) {
					result = "true";
					user_id = json.getJSONObject(0).getInt("id");
					uname = json.getJSONObject(0).getString("name");
					photo = json.getJSONObject(0).getString("photo");
				} else
					result = "false";

			} catch (ClientProtocolException e) {
				result = "false";
				e.printStackTrace();
				Log.e("Download", e.getMessage());
			} catch (IOException e) {
				result = "false";
				Log.e("Download", e.getMessage());
				e.printStackTrace();
			} catch (JSONException e) {
				result = "false";
				Log.e("Download", e.getMessage());
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dialog.dismiss();
			if (result.compareTo("true") == 0) {
				app.setLogged_in(user_id);
				app.setName(uname);
				Log.d("name",uname);
				app.setThumbImage(photo);
				Log.d("photo", "+"+photo+"+");
				goFish();
			} else {

				goBack();
			}

		}

	}
}
