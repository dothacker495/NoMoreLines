package com.example.nomorelines;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
public class ResultActivity extends Activity{
	private int logged_in;
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.);
	        
	        
	    }
	
	 @Override
	 public boolean onCreateOptionsMenu(Menu menu) {
			 // Inflate your menu.
			addMenuItems(menu);
			return super.onCreateOptionsMenu(menu);
	     
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
			menu.add("Filter By").setOnMenuItemClickListener(new OnMenuItemClickListener() {
				
				public boolean onMenuItemClick(MenuItem item) {

					return false;
				}
			})
			.setIcon(R.drawable.content_split)
	        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
			
			
		}
		public void goProfile(){
			Toast.makeText(getBaseContext(),"Profile", Toast.LENGTH_SHORT).show();
			Bundle b = new Bundle();
			b.putInt("session", logged_in);
			Intent i = new Intent(this,ProfileActivity.class);
			i.putExtras(b);
			startActivity(i);
		}
		
}
