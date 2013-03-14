package com.example.adapter.utils;

import static com.example.adapter.utils.CommonUtilities.URL_INIT;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nomorelines.R;
import com.example.objectClass.FoodOrder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
public class OrderAdapter extends BaseAdapter{
    private Activity activity;
    private ArrayList<FoodOrder> data;
    ArrayList<FoodOrder> temp;
    private static LayoutInflater inflater=null;
    DisplayImageOptions options;
    ImageLoader imageLoader;
    
    public OrderAdapter(Activity a, ArrayList<FoodOrder> d) {
        activity = a;
        temp=data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.defaultfood)
		.showImageForEmptyUri(R.drawable.defaultfood)
		.showImageOnFail(R.drawable.defaultfood)
		.cacheInMemory()
		.cacheOnDisc()
		.displayer(new RoundedBitmapDisplayer(20))
		.build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(a));
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public synchronized View getView(int position, View convertView, ViewGroup parent) {
    	
    	final FoodOrder foodOrder = data.get(position);
    	final View vi=(convertView==null)?inflater.inflate(R.layout.order_detail_row, null):convertView;
    	
    	ImageView thumb_image;
    	thumb_image=(ImageView) vi.findViewById(R.id.orderImage);
    	//imageManager.displayImage(URL_INIT+FoodOrder.getThumb_photo(), activity, thumb_image);
    	
    	imageLoader.displayImage(URL_INIT+foodOrder.getPhoto(), thumb_image, options);
    	//imageManager.fetchDrawableOnThread(URL_INIT+FoodOrder.getThumb_photo(), thumb_image);
		TextView price = (TextView)vi.findViewById(R.id.orderPrice);
        TextView name = (TextView)vi.findViewById(R.id.orderName); // title
        TextView quantity = (TextView)vi.findViewById(R.id.qty); // artist name
        TextView qPrice = (TextView)vi.findViewById(R.id.qtyPrice);
        
        
        
        price.setText(Integer.toString(foodOrder.getPrice()));
        quantity.setText(Integer.toString(foodOrder.getQuantity()));
        qPrice.setText(foodOrder.totalPrice_toS());
        // Setting all values in listview
        name.setText(foodOrder.getName());
        
        // Setting the photo
        vi.setTag(foodOrder);
        	
		  	      	 
		  	 
        	
        return vi;
    }
   
}