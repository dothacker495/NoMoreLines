package com.example.nomorelines;

import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

public class ProfileActivity extends SherlockActivity{
 private int session;
	  @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        setTheme(R.style.Theme_Sherlock_Light); 
	        super.onCreate(savedInstanceState);
	        session = getIntent().getExtras().getInt("session");
	        setContentView(R.layout.profile);
	        
	        Toast.makeText(getBaseContext(), Integer.toString(session), Toast.LENGTH_LONG).show();
	    }

}
