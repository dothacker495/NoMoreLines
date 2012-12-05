package com.example.nomorelines;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;


@SuppressLint("WorldReadableFiles")
public class HomeActivity extends SherlockActivity{
private int logged_in;
private final int PROFILE_CODE = 1;
private final int SEARCH_CODE = 2;
private Button fancy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Sherlock_Light); 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        
        initialize();
        setListeners();
        
    }
    public void initialize(){

        logged_in=0;
        fancy = (Button) findViewById(R.id.fancyButton);
    
    }
    
    public void setListeners(){
    	fancy.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				goNearest();
			}
		});
    	
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
		 // Inflate your menu.
		addMenuItems(menu);
		return super.onCreateOptionsMenu(menu);
     
    }
	
	
	public void goShare(){
		Toast.makeText(getBaseContext(),"Share", Toast.LENGTH_SHORT).show();
		
	}
	
	public void goProfile(){
		Toast.makeText(getBaseContext(),"Profile", Toast.LENGTH_SHORT).show();
		Bundle b = new Bundle();
		b.putInt("session", logged_in);
		Intent i = new Intent(this,ProfileActivity.class);
		i.putExtras(b);
		startActivityForResult(i,PROFILE_CODE);
	}
	
	public void goNearest(){
		Bundle b = new Bundle();
		b.putInt("session", logged_in);
		Intent i = new Intent(this, ProfileActivity.class);
		i.putExtras(b);
		startActivityForResult(i,SEARCH_CODE);
	}
	
	public void addMenuItems(Menu menu){
		menu.add("Profile").setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			public boolean onMenuItemClick(MenuItem item) {

				goProfile();
				return false;
			}
		})
		.setIcon(R.drawable.profile)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		menu.add("Share").setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			public boolean onMenuItemClick(MenuItem item) {
				goShare();
				return false;
			}
		})
		.setIcon(R.drawable.ic_compose_inverse)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
	}
}
