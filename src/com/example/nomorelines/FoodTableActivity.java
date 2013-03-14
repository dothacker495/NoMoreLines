package com.example.nomorelines;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.example.adapter.utils.CommonUtilities;
import com.example.adapter.utils.JsonClient;
import com.example.objectClass.MyApplication;
import com.example.objectClass.TableReserve;

public class FoodTableActivity extends SherlockFragment {

	MyApplication app;
	Bundle b;

	com.pineda.custom.view.NumberPicker guests, tables;
	TableReserve table;
	int availTables;

	/*
	 * @Override protected void onCreate(Bundle savedInstanceState) {
	 * super.onCreate(savedInstanceState); setContentView(R.layout.tables);
	 * initialize(); new TableTask().execute(b.getInt("id")); }
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.tables, container, false);

		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initialize();
		new TableTask().execute(b.getInt("id"));
	}

	public void initialize() {
		b = getArguments();
		app = (MyApplication) b.getSerializable("app");

		tables = (com.pineda.custom.view.NumberPicker) getView().findViewById(
				R.id.tableTables2);
		guests = (com.pineda.custom.view.NumberPicker) getView().findViewById(
				R.id.tableGuests);
		;
		table = TableReserve.getInstance();
		setListeners();
	}

	public void setListeners() {
	
	}

	public void clickedButton() {
		Log.d("Value of table", table.getValuesString());

	}

	public void setAvailable(int avail) {
		tables.setMaximum(avail);
		guests.setMaximum(avail * 3);
		tables.setNumberPicker(false, 3, guests);
		guests.setNumberPicker(true, 3, tables);

	}

	private void setAvailTables(int tables) {
		((TextView) getView().findViewById(R.id.availableTables))
				.setText(Integer.toString(tables));
		setAvailable(tables);
	}

	class TableTask extends AsyncTask<Integer, Void, Boolean> {
		private ProgressDialog pd = new ProgressDialog(getSherlockActivity());

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			availTables = 0;
			pd.setMessage("Loading...");
			pd.setIndeterminate(false);
			pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pd.show();
		}

		@Override
		protected Boolean doInBackground(Integer... params) {
			Boolean response = false;

			JsonClient client = new JsonClient(CommonUtilities.URL_INIT);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("id", Integer.toString(params[0]));
			try {
				JSONArray json = client.getArray(CommonUtilities.URL_TABLES,
						map);

				if (json.length() > 0) {
					availTables = json.getJSONObject(0).getInt(
							"available_tables");
				}
				response = true;

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
			super.onPostExecute(result);
			if (result == true)
				setAvailTables(availTables);
			Log.d("available tables", Integer.toBinaryString(availTables));

			pd.dismiss();

		}

	}

}
