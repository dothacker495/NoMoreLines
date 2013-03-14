package com.example.nomorelines;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;

public class DatePickActivity extends Activity {
	String str;
	DatePicker datePicker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.datepicker);
		initialize();
	}
	public void initialize(){
		datePicker = (DatePicker) findViewById(R.id.datePicker1);
		findViewById(R.id.dsetCancel).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setRes(0);
				
			}
		});
		findViewById(R.id.dsetTime).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getDate();
				
				
			}
		});
	}
	public void getDate(){
		str = new String( datePicker.getYear() + "-"
				+ datePicker.getMonth()+"-"+datePicker.getDayOfMonth());
		Log.d("date", str);
		setRes(1);
	}
	public void setRes(int x) {
		Bundle b = new Bundle();
		Intent i = getIntent();
		if (x == 1) {
			
			b.putString("date", str);
			i.putExtras(b);
			setResult(x, i);
		}
		else 
			setResult(x,i);
		finish();
	}
}
