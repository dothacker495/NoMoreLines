package com.example.objectClass;

public class FoodOrder {
private int id,quantity,foodchain_id,price;
private String photo,name;
public FoodOrder(){
	setId(0);
	setQuantity(0);
}
public FoodOrder(int id,int quantity,int foodchain_id, String photo, int price, String name){
	this.setId(id);
	this.setFoodchain_id(foodchain_id);
	this.setQuantity(quantity);
	this.setPhoto(photo);
	this.setPrice(price);
	this.setName(name);
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getQuantity() {
	return quantity;
}
public void setQuantity(int quantity) {
	this.quantity = quantity;
}

public String toString(){
	return Integer.toString(foodchain_id)+" Item id:"+Integer.toString(id)+" qty: "+Integer.toString(quantity);
	
}
public String getId_toS(){
	return Integer.toString(foodchain_id);
}
public int getFoodchain_id() {
	return foodchain_id;
}
public String getQuantity_toS(){
	return Integer.toString(quantity);
}
public void setFoodchain_id(int foodchain_id) {
	this.foodchain_id = foodchain_id;
}
public String getPhoto() {
	return photo;
}
public void setPhoto(String photo) {
	this.photo = photo;
}
public int getPrice() {
	return price;
}
public void setPrice(int price) {
	this.price = price;
}
public int calculatePrice(){
	return price*quantity;
}
public String totalPrice_toS(){
	return Integer.toString(calculatePrice());
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
}
