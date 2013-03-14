package com.example.objectClass;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Random;

public class Reservation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4908738201404060306L;
	private int id;
	private String time;
	private String date;
	private String thumbPhoto;
	private double longitude, latitude;
	private String name;

	public Reservation(int id, String name, String time, String date,
			String thumbPhoto, double longitude, double latitude) {
		this.id = id;
		this.setDate(date);
		this.setTime(time);
		this.setThumbPhoto(thumbPhoto);
		this.setLongitude(longitude);
		this.setLatitude(latitude);
		this.setName(name);
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getThumbPhoto() {
		return thumbPhoto;
	}

	public void setThumbPhoto(String thumbPhoto) {
		this.thumbPhoto = thumbPhoto;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
