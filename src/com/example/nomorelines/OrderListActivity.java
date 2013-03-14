package com.example.nomorelines;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.example.adapter.utils.OrderAdapter;
import com.example.handlers.FoodOrderHandler;
import com.example.objectClass.FoodOrder;

public class OrderListActivity extends SherlockActivity {
FoodOrderHandler db;
ListView list;
OrderAdapter adapter;
ArrayList<FoodOrder> foodorders;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_list);
		getSupportActionBar().setTitle("Check the List");
		initialize();
	}
	public void initialize(){
		db = FoodOrderHandler.getInstance(this);
		list = (ListView)findViewById(R.id.listView1);
		foodorders = db.getAllFoodOrder();
		View footerView =  ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.order_list_footer, null, false);
		setTotalPrice(footerView);
		list.addFooterView(footerView);
		populate();
	}
	public void populate(){
		adapter = new OrderAdapter(OrderListActivity.this, foodorders);
		
		list.setAdapter(adapter);
		
	}
	public void setTotalPrice(View view){
		int total=0;
		for(FoodOrder temp : foodorders){
			total+=temp.calculatePrice();
		}
		TextView tv= (TextView)(view.findViewById(R.id.total));
		tv.setText(Integer.toString(total));
	}
}
