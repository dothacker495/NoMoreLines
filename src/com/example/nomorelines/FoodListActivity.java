package com.example.nomorelines;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.example.adapter.utils.CommonUtilities;
import com.example.adapter.utils.FoodListAdapter;
import com.example.adapter.utils.JsonClient;
import com.example.handlers.FoodOrderHandler;
import com.example.objectClass.FoodItems;
import com.example.objectClass.FoodOrder;
import com.example.objectClass.MyApplication;
import com.pineda.refresh.widget.RefreshableListView;
import com.pineda.refresh.widget.RefreshableListView.OnRefreshListener;

public class FoodListActivity extends SherlockFragment {
	static FoodOrderHandler db;
	MyApplication app;
	Bundle b;
	LocationListener locationListener;
	RefreshableListView list;
	LocationManager locationManager;
	ArrayList<FoodItems> fooditems;
	FoodListAdapter adapter;
	int foodchainId = 0;
	private EditText search;

	/*
	 * @Override protected void onCreate(Bundle savedInstanceState) {
	 * super.onCreate(savedInstanceState); setContentView(R.layout.food_list);
	 * initialize(); setLocationSettings(); if (fooditems != null) {
	 * findViewById(R.id.framList2).setVisibility(View.GONE);
	 * populateFoodItems(); setRows(fooditems); } }
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.food_list, container, false);
		// v.findViewById(R.id.framList2).setVisibility(View.GONE);

		return v;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		MenuItem mi = (MenuItem) menu.getItem(1);
		View vi = mi.getActionView();
		search = (EditText) vi.findViewById(R.id.searchResult);
		search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

				// ResultActivity.this.adapter.getFilter().filter(s);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		super.onCreateOptionsMenu(menu, inflater);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initialize();
		new FoodItemTask().execute();
	}

	public void populateFoodItems() {
		for (FoodItems fooditem : fooditems) {
			db.addOrder(new FoodOrder(fooditem.getId(), 0, fooditem
					.getFoodchain_id(), fooditem.getThumb_photo(), fooditem
					.getPrice(),fooditem.getName()));
		}
	}

	public void setRows(ArrayList<FoodItems> fi) {

		adapter = new FoodListAdapter(FoodListActivity.this, fi);
		list.setAdapter(adapter);
		list.setClickable(false);

	}

	public synchronized void onChangeQty(int index, int num) {
		db.updateOrder(new FoodOrder(fooditems.get(index).getId(), num,
				fooditems.get(index).getFoodchain_id(), fooditems.get(index)
						.getThumb_photo(), fooditems.get(index).getPrice(),fooditems.get(index).getName()));
	}

	public void initialize() {
		b = getArguments();
		app = (MyApplication) b.getSerializable("app");
		foodchainId = b.getInt("id");
		fooditems = null;
		list = (RefreshableListView) getView().findViewById(R.id.list1);
		list.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh(RefreshableListView listView) {
				new FoodItemTask().execute();
			}
		});
		db = FoodOrderHandler.getInstance(getActivity());

		list.setClickable(false);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		db.close();
	}

	class FoodItemTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			fooditems = new ArrayList<FoodItems>();

		}

		@Override
		protected Boolean doInBackground(Void... urls) {
			Boolean response = true;

			JsonClient client = new JsonClient(CommonUtilities.URL_INIT);

			try {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("id", Integer.toString(foodchainId));

				JSONArray json = client.getArray(CommonUtilities.URL_FOODITEM,
						params);

				for (int i = 0; i < json.length(); i++) {
					JSONObject j = json.getJSONObject(i);
					FoodItems temp = new FoodItems(j.getInt("id"),
							j.getInt("foodchain_id"), j.getString("name"),
							j.getString("description"), j.getString("photo"),
							j.getString("thumb_photo"), j.getInt("quantity"),
							j.getInt("price"));
					fooditems.add(temp);

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
			getView().findViewById(R.id.fooditemLoadingFrame).setVisibility(
					View.GONE);
			if (result == true) {
				if (fooditems.isEmpty() != true) {
					populateFoodItems();
					setRows(fooditems);
					list.completeRefreshing();
					getView().findViewById(R.id.framList2).setVisibility(
							View.GONE);
				} else
					getView().findViewById(R.id.framList2).setVisibility(
							View.VISIBLE);

			}

		}

	}
}