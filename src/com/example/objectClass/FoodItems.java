package com.example.objectClass;

import java.io.Serializable;



public class FoodItems implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6290664781784169292L;
	private int id,
				foodchain_id, 
				quantity,
				orderqty,
				price;
	private String name,
				   description,
				   photo,
				   thumb_photo,type;
	public FoodItems(int id, String name, String description, String photo, String thumb_photo, int quantity){
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.description = description;
		this.photo = photo;
		this.thumb_photo =thumb_photo;
		orderqty=0;
		
	}
	public FoodItems(int id,String name, String description,String type, String photo, String thumb_photo, int quantity){
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.description = description;
		this.photo = photo;
		this.thumb_photo =thumb_photo;
		this.type = type;
		orderqty=0;
	}
	public FoodItems(int id,String name, String description,String type, String photo, String thumb_photo, int quantity,int price){
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.description = description;
		this.photo = photo;
		this.thumb_photo =thumb_photo;
		this.type = type;
		this.price = price;
		orderqty=0;
	}
	public FoodItems(int id,String name, String description, String photo, String thumb_photo, int quantity,int price){
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.description = description;
		this.photo = photo;
		this.thumb_photo =thumb_photo;
		this.price = price;
		orderqty=0;
	}
	public FoodItems(int id,int foodchain_id,String name, String description, String photo, String thumb_photo, int quantity,int price){
		this.id = id;
		this.foodchain_id=foodchain_id;
		this.name = name;
		this.quantity = quantity;
		this.description = description;
		this.photo = photo;
		this.thumb_photo =thumb_photo;
		this.price = price;
		orderqty=0;
	}
	public void incrementqty(){
		++orderqty;
	}
	public void decrementqty(){
		--orderqty;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFoodchain_id() {
		return foodchain_id;
	}
	public void setFoodchain_id(int foodchain_id) {
		this.foodchain_id = foodchain_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPrice() {
		return price;
	}
	public String getPrice_toS(){
		return Integer.toString(price);
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getOrderqty() {
		return orderqty;
	}
	public void setOrderqty(int orderqty) {
		this.orderqty = orderqty;
	}
}
