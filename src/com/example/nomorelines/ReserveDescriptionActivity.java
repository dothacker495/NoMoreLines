package com.example.nomorelines;

import static com.example.adapter.utils.CommonUtilities.URL_INIT;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.example.adapter.utils.DrawableManager;
import com.example.adapter.utils.JsonClient;
import com.example.objectClass.Reservation;

public class ReserveDescriptionActivity extends SherlockActivity {
	private Reservation reserve;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.reservation_description);
		setTheme(R.style.Sherlock___Theme_Light);
		getSupportActionBar().setTitle("");
		Bundle b = getIntent().getExtras();
		reserve = (Reservation) b.getSerializable("rs");
		initialize();

	}

	public void initialize() {
		TextView date, time, title;
		ImageView logo;
		date = (TextView) findViewById(R.id.res_date);
		time = (TextView) findViewById(R.id.res_time);
		title = (TextView) findViewById(R.id.res_title);
		logo = (ImageView) findViewById(R.id.res_image);
		setValues(date, time, title, logo);
		Button b = (Button)findViewById(R.id.cancelReservation);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(ReserveDescriptionActivity.this)
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

								 
								dialog.dismiss();
								new DeleteReserveTask().execute();
							}

						}).show();

			}
		});
	}
	class DeleteReserveTask extends AsyncTask<Void, Void, Boolean> {
		ProgressDialog pd = new ProgressDialog(ReserveDescriptionActivity.this);
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd.setMessage("Cancelling Reservation");
			pd.show();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			Boolean result = false;
			
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("id", Integer.toString(reserve.getId()));
			JsonClient client = new JsonClient(URL_INIT);
			try {
				JSONArray json = client.getArray("deleteReservation.php", map);
				if(json.length()>0);
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
			pd.dismiss();
			setResult(0,getIntent());
			Toast.makeText(getBaseContext(), "Successfully Canceled Reservation", Toast.LENGTH_SHORT).show();
			finish();
		}

	}

	
	public void setValues(TextView date, TextView time,
			TextView title, ImageView logo) {
		String name=reserve.getName(),sdate=reserve.getDate(),stime=reserve.getTime();
		title.setText(name);
		date.setText(sdate);
		time.setText(stime);

		new DrawableManager().fetchDrawableOnThread(URL_INIT+reserve.getThumbPhoto(),logo);
		
	}

	public int deviceDensity() {

		int density = 0;
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		switch (metrics.densityDpi) {
		case DisplayMetrics.DENSITY_LOW:
			density = 1;
			break;
		case DisplayMetrics.DENSITY_MEDIUM:
			density = 2;
			break;
		case DisplayMetrics.DENSITY_HIGH:
			density = 3;
			break;

		case DisplayMetrics.DENSITY_XHIGH:
			density = 4;
			break;

		}
		return density;
	}
}
