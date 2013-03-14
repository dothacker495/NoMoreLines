package com.example.nomorelines;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TimePicker;

public class TimePickActivity extends Activity {
	String str;
	TimePicker timePicker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timepicker);
		initialize();
	}
	public void initialize(){
		timePicker = (TimePicker) findViewById(R.id.timePicker1);
		findViewById(R.id.setCancel).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setRes(0);
				
			}
		});
		findViewById(R.id.setTime).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getTime();
				
				
			}
		});
	}
	public void getTime(){
		str = new String(timePicker.getCurrentHour() + ":"
				+ timePicker.getCurrentMinute());
		setRes(1);
	}
	public void setRes(int x) {
		Bundle b = new Bundle();
		Intent i = getIntent();
		if (x == 1) {
			
			b.putString("time", str);
			i.putExtras(b);
			setResult(x, i);
		}
		else 
			setResult(x,i);
		finish();
	}
}
