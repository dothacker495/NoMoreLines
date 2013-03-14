package com.example.nomorelines;

import static com.example.adapter.utils.CommonUtilities.URL_INIT;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.example.adapter.utils.DrawableManager;
import com.example.objectClass.FoodChain;

public class DescriptionActivity extends SherlockActivity {
	private FoodChain fc;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.foodchain_description);
		setTheme(R.style.Sherlock___Theme_Light);
		getSupportActionBar().setTitle("");
		Bundle b = getIntent().getExtras();
		fc = (FoodChain) b.getSerializable("fc");
		initialize();

	}

	public void initialize() {
		TextView description, location, title;
		ImageView logo;
		description = (TextView) findViewById(R.id.desc_desc);
		location = (TextView) findViewById(R.id.desc_location);
		title = (TextView) findViewById(R.id.desc_title);
		logo = (ImageView) findViewById(R.id.desc_image);
		setValues(description, location, title, logo);
	}

	public void setValues(TextView description, TextView location,
			TextView title, ImageView logo) {
		String name=fc.getName(),desc=fc.getDescription(),loc=fc.getLocation();
		title.setText(name);
		description.setText(desc);
		location.setText(loc);

		new DrawableManager().fetchDrawableOnThread(URL_INIT+fc.getPhoto()+ ".jpg",logo);
		
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
