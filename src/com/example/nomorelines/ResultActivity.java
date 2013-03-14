package com.example.nomorelines;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.example.adapter.utils.CommonUtilities;
import com.example.adapter.utils.JsonClient;
import com.example.adapter.utils.LazyAdapter;
import com.example.objectClass.FoodChain;
import com.example.objectClass.MyApplication;
import com.example.quickaction.ActionItem;
import com.example.quickaction.ActionItem3D;
import com.example.quickaction.QuickAction;
import com.example.quickaction.QuickAction3D;
import com.example.quickaction.QuickAction3D.OnActionItemClickListener;
import com.pineda.refresh.widget.RefreshableListView;
import com.pineda.refresh.widget.RefreshableListView.OnRefreshListener;

@SuppressLint("NewApi")
public class ResultActivity extends SherlockActivity {
	private ArrayList<FoodChain> foodchain;
	private RefreshableListView list;
	private LazyAdapter adapter;
	private MyApplication app;
	private QuickAction mQuickAction;
	private FoodChain clickedFoodChain;
	private QuickAction3D quickAction;
	private EditText search;
	private final int PROFILE_REQUEST = 3;
	@SuppressWarnings("unused")
	private int x, y;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_layout);
		getSupportActionBar().setTitle("");
		initialize();

		new FoodChainTask().execute();

		setQuickAction();
		setAction3d();
		setListeners();

	}

	private void initialize() {
		this.x = this.y = 0;

		app = (MyApplication)getApplicationContext();
		//app = MyApplication.getMyApplication();
		list = (RefreshableListView) findViewById(R.id.list);

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		deleteDatabase("foodOrder");
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
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
		startActivityForResult(i, PROFILE_REQUEST);
	}

	public void setAction3d() {
		ActionItem3D fItem = new ActionItem3D(RESULT_FANCY, "Fancy",
				getResources().getDrawable(R.drawable.navigation_forward));
		ActionItem3D ffItem = new ActionItem3D(RESULT_FAST, "Fast",
				getResources().getDrawable(R.drawable.social_forward));
		ActionItem3D resetItem = new ActionItem3D(5, "Reset", getResources()
				.getDrawable(R.drawable.back));

		quickAction = new QuickAction3D(this, QuickAction3D.VERTICAL);

		quickAction.addActionItem(fItem);
		quickAction.addActionItem(ffItem);
		quickAction.addActionItem(resetItem);
	}

	public void setRows(ArrayList<FoodChain> foodchain) {

		adapter = new LazyAdapter(this, foodchain);
		list.setAdapter(adapter);

	}

	public void descAction() {
		Intent intent = new Intent(ResultActivity.this,
				DescriptionActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("fc", clickedFoodChain);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	public void locAction() {
		Intent intent = new Intent(ResultActivity.this,
				LocationActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("fc", clickedFoodChain);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	public void dineAction() {
		pushToDine();
	}

	private void setListeners() {
		mQuickAction
				.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {

					public void onItemClick(QuickAction quickAction, int pos,
							int actionId) {

						if (actionId == 1)
							descAction();
						else if (actionId == 2)
							locAction();
						else
							dineAction();
					}
				});

		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {

				clickedFoodChain = (FoodChain) view.getTag();
				mQuickAction.show(view);

			}
		});
		list.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh(RefreshableListView listView) {
				// TODO Auto-generated method stub
				new FoodChainTask().execute();
			}
		});
		quickAction
				.setOnActionItemClickListener(new OnActionItemClickListener() {

					public void onItemClick(QuickAction3D source, int pos,
							int actionId) {
						if (actionId == 5)
							x = 0;
						else if (actionId < 3)
							x = (actionId == RESULT_FANCY) ? RESULT_FANCY
									: RESULT_FAST;

						filter(x);

					}
				});
	}

	private void setQuickAction() {
		ActionItem descItem = new ActionItem(1, "Desc", getResources()
				.getDrawable(R.drawable.info));
		ActionItem locItem = new ActionItem(2, "Location", getResources()
				.getDrawable(R.drawable.pin));
		ActionItem dineItem = new ActionItem(3, "Dine", getResources()
				.getDrawable(R.drawable.fork));
		mQuickAction = new QuickAction(this);
		mQuickAction.addActionItem(descItem);
		mQuickAction.addActionItem(locItem);
		mQuickAction.addActionItem(dineItem);

	}

	private void filter(int x) {
		// this implementation works
		TextView tv = (TextView) findViewById(R.id.waiting);
		list.setVisibility(View.GONE);
		tv.setVisibility(View.VISIBLE);
		filterBy(x);
		tv.setVisibility(View.GONE);
		list.setVisibility(View.VISIBLE);
	}

	private void filterBy(int type) {
		// ArrayList<FoodChain> fc = new ArrayList<FoodChain>();
		/*
		 * Type is for the type of foodchain to be displayed 0 - for no specific
		 * type 1 - for fastfood 2 - for fancy
		 */
		if (type == 0)
			ResultActivity.this.adapter.getFilter().filter("");
		else {

			if (type == RESULT_FAST)
				ResultActivity.this.adapter.getFilter(1).filter("fancy");
			else
				ResultActivity.this.adapter.getFilter(1).filter("fast");

		}
	}

	public void pushToDine() {

		// Intent intent = new Intent(this, FoodListActivity.class);
		Intent intent = new Intent(this, FoodTableTabActivity.class);
		Bundle bundle = new Bundle();
		// for foodchain id
		bundle.putInt("id", clickedFoodChain.getId());
		intent.putExtras(bundle);
		startActivity(intent);

	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		menu.add("Filter")
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {
						quickAction.show(findViewById(item.getItemId()));
						return false;
					}
				}).setIcon(R.drawable.content_split)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.add("Search")
				.setIcon(R.drawable.action_search)
				.setActionView(R.layout.collapsible_edittext)
				.setShowAsAction(
						MenuItem.SHOW_AS_ACTION_ALWAYS
								| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		menu.add("Profile")
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					public boolean onMenuItemClick(MenuItem item) {
						goProfile();
						return false;
					}
				}).setIcon(R.drawable.social_person)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		MenuItem mi = (MenuItem) menu.getItem(1);
		View vi = mi.getActionView();
		search = (EditText) vi.findViewById(R.id.searchResult);
		search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

				ResultActivity.this.adapter.getFilter().filter(s);

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
		return super.onCreateOptionsMenu(menu);
	}
	private class FoodChainTask extends AsyncTask<Void, Void, Boolean> {
		// private ProgressDialog pd = new ProgressDialog(HomeActivity.this);
		
		FrameLayout fl;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			foodchain = new ArrayList<FoodChain>();
			fl = (FrameLayout) findViewById(R.id.foodchainLoadingFrame);
			
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
				// ImageCollection im = ImageCollection.getInstance();

				for (int i = 0; i < json.length(); i++) {

					JSONObject j = json.getJSONObject(i);
					FoodChain temp = new FoodChain(j.getInt("id"),
							Double.parseDouble(j.getString("longitude")),
							Double.parseDouble(j.getString("latitude")),
							j.getString("name"), j.getString("location"),
							j.getString("description"), j.getString("type"),
							j.getString("photo"), j.getString("thumb_photo"),
							j.getDouble("distance")*100);
					foodchain.add(temp);
					// MyImage tempImage = new MyImage(temp.getId(),
					// CommonUtilities.getLogo(temp.getThumb_photo()));
					// im.addFoodChain(tempImage);
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
			if (foodchain != null)
				setRows(foodchain);
			else
				findViewById(R.id.resultFrame).setVisibility(View.VISIBLE);
			fl.setVisibility(View.GONE);
			list.completeRefreshing();
		}
	}

	private final int RESULT_FAST = 2;
	private final int RESULT_FANCY = 1;
}
