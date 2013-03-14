package com.example.objectClass;

import android.app.Application;

import com.google.android.maps.GeoPoint;

public class MyApplication extends Application{
	private int logged_in;
	private String name = null;
	private String thumbImage=null;
	private Double []user_geo;
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	/*
	private static MyApplication app=null;
	
	private MyApplication(){
		initialize();
		
	}
	
	public static MyApplication getMyApplication(){
		if(app==null){
			app = new MyApplication();
		}
		return app;
	}*/
	public void initialize(){
		logged_in=0;
		user_geo= new Double[2];
		// index 0 latitude , index 1 longitude
		user_geo[0]=user_geo[1]=0.0;
	}
	public GeoPoint getGeoPoint(){
		return new GeoPoint((int)(user_geo[0]*1E6), (int)(user_geo[1]*1E6));
	}
	public int getLogged_in() {
		return logged_in;
	}
	public String getId_toS(){
		return Integer.toString(logged_in);
	}
	public boolean isLoggedIn(){
		return logged_in>0?true:false;
	}
	public void setLogged_in(int logged_in) {
		this.logged_in = logged_in;
	}
	public Double [] getUser_geo() {
		return user_geo;
	}
	public void setUser_geo(Double [] user_geo) {
		this.user_geo = user_geo;
	}
	public boolean hasNoGeo(){
		return (user_geo[0]==0&&user_geo[0]==0)?true:false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getThumbImage() {
		return thumbImage;
	}
	
	public String getLongitude(){
		return Double.toString(user_geo[1]);
	}

	public String getLatitude(){
		return Double.toString(user_geo[0]);
	}
	public void setThumbImage(String thumbImage) {
		this.thumbImage = thumbImage;
	}

}
