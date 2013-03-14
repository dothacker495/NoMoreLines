package com.example.nomorelines;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.adapter.utils.ConnectionDetector;
import com.example.adapter.utils.ServerUtilities;
import com.example.handlers.AlertDialogManager;

public class Register extends Activity {
	private EditText username, password, name;
	private Button register;
	private String user, pass, name1;
	private AsyncTask<Void, Void, Void> mRegisterTask;
	private ProgressDialog dialog;
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();

	// Connection detector
	ConnectionDetector cd;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		dialog = new ProgressDialog(this);
		dialog.setTitle("Registering Account...");
		register = (Button) findViewById(R.id.submitReg);
		username = (EditText) findViewById(R.id.usernameReg);
		password = (EditText) findViewById(R.id.passwordReg);
		name = (EditText) findViewById(R.id.nameReg);
		register.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				goRegister();
			}
		});
	}

	public void goRegister() {
		user = username.getText().toString().trim();
		pass = password.getText().toString().trim();
		name1 = name.getText().toString();
		final Context context = this;
		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(Register.this, "Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		} else {
			mRegisterTask = new AsyncTask<Void, Void, Void>() {
				protected void onPreExecute() {
					dialog.show();
				}

				@Override
				protected Void doInBackground(Void... params) {
					// Register on our server
					// On server creates a new user
					ServerUtilities.register(context, user, pass, name1);
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					goLogin();
				}

			};
			mRegisterTask.execute(null, null, null);
		}
	}

	public void goLogin() {
		finish();
	}
}
