package com.example.handlers;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.objectClass.FoodOrder;

public class FoodOrderHandler extends SQLiteOpenHelper {
	public static FoodOrderHandler foh = null;
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "foodOrder";

	// Contacts table name
	private static final String TABLE_FOODORDERS = "fooditems";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_FID = "fooditem";
	private static final String KEY_QTY = "quantity";
	private static final String KEY_PHOTO = "photo";
	private static final String KEY_PRICE = "price";
	private static final String KEY_NAME = "name";
	public static FoodOrderHandler getInstance(Context context) {
		if (foh == null) {
			foh = new FoodOrderHandler(context);
		}
		return foh;

	}

	private FoodOrderHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_FOODORDER_TABLE = "CREATE TABLE " + TABLE_FOODORDERS
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_QTY
				+ " INTEGER," + KEY_FID + " INTEGER," + KEY_PHOTO + " STRING,"
				+ KEY_PRICE + " INTEGER," + KEY_NAME+ " STRING"+ ")";
		db.execSQL(CREATE_FOODORDER_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOODORDERS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new food item
	public void addOrder(FoodOrder foodOrder) {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from fooditems where " + KEY_ID
				+ " = " + foodOrder.getId(), null);
		if (cursor.getCount() == 0) {
			// Insert FoodOrder
			ContentValues values = new ContentValues();
			values.put(KEY_ID, foodOrder.getId());
			values.put(KEY_QTY, foodOrder.getQuantity());
			values.put(KEY_FID, foodOrder.getFoodchain_id());
			values.put(KEY_PHOTO, foodOrder.getPhoto());
			values.put(KEY_PRICE, foodOrder.getPrice());
			values.put(KEY_NAME, foodOrder.getName());
			db.insert(TABLE_FOODORDERS, null, values);
		
		}
		db.close(); 
	}

	// Getting single FoodOrder
	public FoodOrder getOrder(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_FOODORDERS, new String[] { KEY_ID,
				KEY_QTY, KEY_FID, KEY_PHOTO, KEY_PRICE, KEY_NAME }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		FoodOrder foodOrder = new FoodOrder(Integer.parseInt(cursor
				.getString(0)), Integer.parseInt(cursor.getString(1)),
				Integer.parseInt(cursor.getString(2)), cursor.getString(3),
				Integer.parseInt(cursor.getString(4)), cursor.getString(5));
		// return foodOrder
		return foodOrder;
	}

	// Getting All FoodOrder
	public ArrayList<FoodOrder> getAllFoodOrder() {
		ArrayList<FoodOrder> foodList = new ArrayList<FoodOrder>();
		// Select All Query With greater than 0 quantity
		String selectQuery = "SELECT  * FROM " + TABLE_FOODORDERS + " WHERE "
				+ KEY_QTY + " > 0";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				FoodOrder foodOrder = new FoodOrder(Integer.parseInt(cursor
						.getString(0)), Integer.parseInt(cursor.getString(1)),
						Integer.parseInt(cursor.getString(2)),
						cursor.getString(3), Integer.parseInt(cursor
								.getString(4)),cursor.getString(5));

				
				// Adding foodOrder to list
				foodList.add(foodOrder);
			} while (cursor.moveToNext());
		}

		// return contact list
		return foodList;
	}

	// Getting All FoodOrder
	public ArrayList<FoodOrder> getAll() {
		ArrayList<FoodOrder> foodList = new ArrayList<FoodOrder>();
		// Select All Query With greater than 0 quantity
		String selectQuery = "SELECT  * FROM " + TABLE_FOODORDERS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				FoodOrder foodOrder = new FoodOrder(Integer.parseInt(cursor
						.getString(0)), Integer.parseInt(cursor.getString(1)),
						Integer.parseInt(cursor.getString(2)),
						cursor.getString(3),
						Integer.parseInt(cursor.getString(2)),cursor.getString(5));

				// Adding foodOrder to list
				foodList.add(foodOrder);
			} while (cursor.moveToNext());
		}

		// return contact list
		return foodList;
	}

	// Updating single order
	public int updateOrder(FoodOrder foodOrder) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_QTY, foodOrder.getQuantity());

		// updating row
		return db.update(TABLE_FOODORDERS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(foodOrder.getId()) });
	}

	// Deleting single Order
	public void deleteOrder(FoodOrder foodOrder) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_FOODORDERS, KEY_ID + " = ?",
				new String[] { String.valueOf(foodOrder.getId()) });
		db.close();
	}

	public int getOrderCount() {
		String countQuery = "SELECT  * FROM " + TABLE_FOODORDERS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		return cursor.getCount();
	}

}