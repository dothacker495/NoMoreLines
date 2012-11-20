package com.example.nomorelines;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;


@SuppressLint("WorldReadableFiles")
public class HomeActivity extends SherlockActivity{
private int logged_in;
private MenuItem menuItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Sherlock_Light); 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        logged_in=0;
        
    }
    
    
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		 // Inflate your menu.
		
	    
		menu.add("Profile").setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			public boolean onMenuItemClick(MenuItem item) {
				goShare();
				return false;
			}
		})
		.setIcon(R.drawable.profile)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		menu.add("Share").setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			public boolean onMenuItemClick(MenuItem item) {
				goProfile();
				return false;
			}
		})
		.setIcon(R.drawable.ic_compose_inverse)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
        
		return super.onCreateOptionsMenu(menu);
     
    }
	public void goShare(){
		Toast.makeText(getBaseContext(),"Share", Toast.LENGTH_SHORT).show();
		
	}
	public void goProfile(){
		Toast.makeText(getBaseContext(),"Profile", Toast.LENGTH_SHORT).show();
		
	}
	
	
}
