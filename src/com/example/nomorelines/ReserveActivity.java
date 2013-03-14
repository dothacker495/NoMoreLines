package com.example.nomorelines;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.example.adapter.utils.ConnectionDetector;
import com.example.adapter.utils.ServerUtilities;
import com.example.handlers.AlertDialogManager;
import com.example.handlers.FoodOrderHandler;
import com.example.objectClass.FoodOrder;
import com.example.objectClass.MyApplication;
import com.example.objectClass.TableReserve;

public class ReserveActivity extends SherlockActivity {
	MyApplication app;
	Bundle b;
	TableReserve table;
	FoodOrderHandler db;
	String time;
	String date;
	AlertDialogManager alert = new AlertDialogManager();

	// Connection detector
	ConnectionDetector cd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.reserve_layout);
		super.onCreate(savedInstanceState);
		getSupportActionBar().setTitle("Reservation");
		initialize();

	}

	public void initialize() {
		b = getIntent().getExtras();
		Log.d("FOODCHAIN", Integer.toString(b.getInt("id")));

		app = (MyApplication) getApplicationContext();
		// app = MyApplication.getMyApplication();
		db = FoodOrderHandler.getInstance(this);
		table = TableReserve.getInstance();
		time = new String();
		date = new String();
		setListeners();
	}

	public void setListeners() {
		(findViewById(R.id.setTimeButton))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						goSetTime();
					}
				});
		(findViewById(R.id.setDateButton))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						goSetDate();
					}
				});
		((Button) findViewById(R.id.reserveButton))
				.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						goReserve();
					}
				});
	}

	public void goReserve() {
		if (app.isLoggedIn()) {
			if (checkValidity())
				goCart(7);
			else
				toastThis(0);

		} else {
			toastThis(1);
			goProfile();
		}
	}
	public void goAlert(){
		new AlertDialog.Builder(ReserveActivity.this)
		.setTitle("Reserve")
		.setMessage("Are you sure with everything?")
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
						clickedButton();
						
						dialog.dismiss();
					}

				}).show();

	}

	public void goSetTime() {
		Intent intent = new Intent(this, TimePickActivity.class);
		startActivityForResult(intent, 3);
	}

	public void goSetDate() {
		Intent intent = new Intent(this, DatePickActivity.class);
		startActivityForResult(intent, 2);
	}

	public void toastThis(int x) {
		switch (x) {
		case 0:
			toast("Invalid Date or Time");
			break;
		case 1:
			toast("Please Login before reserving");
			break;
		case 2:
			toast("Failed to Reserve");
			break;
		}
	}

	public void toast(String str) {
		Toast.makeText(getBaseContext(), str, Toast.LENGTH_SHORT).show();
	}

	public void goProfile() {
		Intent i;
		if (!app.isLoggedIn()) {
			i = new Intent(this, LoginActivity.class);
		} else {
			i = new Intent(this, ProfileActivity.class);

		}

		startActivity(i);
	}

	@SuppressLint("SimpleDateFormat")
	public boolean checkValidity() {
		boolean ret = false;
		if (time.isEmpty() != true && date.isEmpty() != true) {
			Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR);
			int minute = c.get(Calendar.MINUTE);
			Date compareOne = parseTime(hour + 1 + ":" + minute);

			Date compareTwo = parseTime(time);

			int day = c.get(Calendar.DAY_OF_MONTH);
			int month = c.get(Calendar.MONTH);
			int year = c.get(Calendar.YEAR);
			int[] dmy = new int[3];
			int ctr = 0;
			StringTokenizer st = new StringTokenizer(date, "-");
			while (st.hasMoreTokens()) {
				dmy[ctr] = Integer.parseInt(st.nextToken().toString());
				ctr++;
			}
			if (isEqualOrGreater(year, dmy[0])
					&& isEqualOrGreater(month, dmy[1])
					&& (isEqual(day, dmy[2]) && (compareTwo.after(compareOne)) || day < dmy[2]))
				ret = true;
		}
		return ret;
	}

	private boolean isEqual(int x, int y) {
		return x == y ? true : false;
	}

	private boolean isEqualOrGreater(int x, int y) {
		return x <= y ? true : false;
	}

	private Date parseTime(String date) {
		String inputFormat = "HH:mm";
		SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat,
				Locale.US);

		try {
			return inputParser.parse(date);
		} catch (java.text.ParseException e) {
			return new Date(0);
		}
	}

	public void clickedButton() {
		Log.d("Value of table", table.getValuesString());
		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(ReserveActivity.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		} else {
			new AsyncTask<Void, Void, Boolean>() {
				ProgressDialog pd = new ProgressDialog(ReserveActivity.this);

				protected void onPreExecute() {
					pd.setMessage("Sumbiting Reservation Request");
					pd.show();
				}

				@Override
				protected Boolean doInBackground(Void... params) {
					Boolean res = false;
					try {
						String r = ServerUtilities.reserve(app.getId_toS(),
								time + ":00", date,
								Integer.toString(b.getInt("id")));
						Log.d("Reserve", r);
						ArrayList<FoodOrder> foodlist = db.getAllFoodOrder();
						for (FoodOrder temp : foodlist) {
							ServerUtilities.postFoodOrder(r, temp.getId_toS(),
									temp.getQuantity_toS());
						}
						if (table.getTables() > 0) {
							ServerUtilities.postTableOrder(r,
									table.getTables_toS(),
									table.getPeople_toS());
						}
						res = true;
					} catch (Exception e) {
						// TODO: handle exception
					}

					return res;
				}

				protected void onPostExecute(Boolean result) {
					if (result == true) {
						table.setPeople(0);
						table.setTables(0);
						setResult(1);
						finish();
					} else {
						toastThis(2);
					}
					pd.dismiss();

				}

			}.execute();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Profile")
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {
						goProfile();
						return false;
					}
				}).setIcon(R.drawable.social_person)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

		menu.add("Cart")
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {
						goCart(6);
						return false;
					}
				}).setIcon(R.drawable.cart)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

		return true;
	}

	public void goCart(int req) {
		Intent i = new Intent(this, OrderListActivity.class);
		startActivityForResult(i,req);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 3) {
			if (resultCode == 1) {
				time = data.getExtras().getString("time");
				((TextView) findViewById(R.id.timeSet)).setText(time);
			}
		} else if (requestCode == 2) {
			if (resultCode == 1) {
				date = data.getExtras().getString("date");
				((TextView) findViewById(R.id.dateSet)).setText(date);
			}
		}
		else if(requestCode==7){
			goAlert();
		}
	}
}
