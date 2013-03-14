package com.example.objectClass;

import java.io.Serializable;

import android.graphics.Bitmap;


public class FoodChain implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = -773778311382234259L;
private int id;
private double longitude, latitude;
private double distance;
private String name,
			   description,
			   location,
			   type,
			   photo,
			   thumb_photo;
public FoodChain(int id, double longitude, double latitude, String name, String location, String description,String type, String photo, String thumb_photo, double distance){
	this.id = id;
	this.location = location;
	this.name = name;
	this.setLongitude(longitude);
	this.setLatitude(latitude);
	this.description = description;
	this.type = type;
	this.photo = photo;
	this.thumb_photo =thumb_photo;
	this.distance = distance;
	
}
public int getId() {
	return id;
}
public String getDistanceToString(){
	return Double.toString(Math.ceil(distance)/100.0);
}
public void setId(int id) {
	this.id = id;
}
public String getId_toS(){
	return Integer.toString(id);
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getLocation() {
	return location;
}
public void setLocation(String location) {
	this.location = location;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getPhoto() {
	return photo;
}
public void setPhoto(String photo) {
	this.photo = photo;
}
public String getThumb_photo() {
	return thumb_photo;
}
public void setThumb_photo(String thumb_photo) {
	this.thumb_photo = thumb_photo;
}
public double getLatitude(){
	return this.latitude;
}

public double getLongitude(){
	return this.longitude;
}
public void setLatitude(double latitude) {
	this.latitude = latitude;
}
public void setLongitude(double longitude) {
	this.longitude = longitude;
}
public double getDistance() {
	return distance;
}
public void setDistance(int distance) {
	this.distance = distance;
}
}
