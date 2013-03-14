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
import com.example.objectClass.FoodChain;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
public class LazyAdapter extends BaseAdapter implements Filterable {
    private Activity activity;
    private ArrayList<FoodChain> data;
    ArrayList<FoodChain> temp;
    private static LayoutInflater inflater=null;
    DisplayImageOptions options;
    ImageLoader imageLoader;
    
    public LazyAdapter(Activity a, ArrayList<FoodChain> d) {
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
    @Override
    public Filter getFilter(){
    	filter_here filter = new filter_here();
    	
    	return filter;
    }
    
    public Filter getFilter(int ind){
    	filter_fancy filter = new filter_fancy();
    	return filter;
    }
    public synchronized View getView(int position, View convertView, ViewGroup parent) {
    	
    	final FoodChain foodchain = data.get(position);
    	final View vi=(convertView==null)?inflater.inflate(R.layout.list_row, null):convertView;
    	
    	ImageView thumb_image;
    	thumb_image=(ImageView) vi.findViewById(R.id.list_image);
    	//imageManager.displayImage(URL_INIT+foodchain.getThumb_photo(), activity, thumb_image);
    	
    	imageLoader.displayImage(URL_INIT+foodchain.getThumb_photo(), thumb_image, options);
    	//imageManager.fetchDrawableOnThread(URL_INIT+foodchain.getThumb_photo(), thumb_image);
		TextView dist = (TextView)vi.findViewById(R.id.distanceText);
        TextView name = (TextView)vi.findViewById(R.id.title); // title
        TextView location = (TextView)vi.findViewById(R.id.location); // artist name
        
       
        
        
        
        // Setting all values in listview
        name.setText(foodchain.getName());
        location.setText(foodchain.getLocation());
        dist.setText(foodchain.getDistanceToString()+"Km");
        // Setting the photo
        vi.setTag(foodchain);
        	
		  	      	 
		  	 
        	
        return vi;
    }
    public class filter_here extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // TODO Auto-generated method stub
        	data = temp;
            FilterResults Result = new FilterResults();
            // if constraint is empty return the original names
            if(constraint.length() == 0 ){
                Result.values = data;
                Result.count = data.size();
                return Result;
            }

            ArrayList<FoodChain> Filtered_Names = new ArrayList<FoodChain>();
            String filterString = constraint.toString().toLowerCase();
            String filterableString;

            for(int i = 0; i<data.size(); i++){
                filterableString = data.get(i).getName();
                if(filterableString.toLowerCase().contains(filterString)){
                    Filtered_Names.add(data.get(i));
                }
            }
            Result.values = Filtered_Names;
            Result.count = Filtered_Names.size();

            return Result;
        }

		@SuppressWarnings("unchecked")
		@Override
        protected void publishResults(CharSequence constraint,FilterResults results) {
            // TODO Auto-generated method stub
            data = (ArrayList<FoodChain>) results.values;
            notifyDataSetChanged();
        }

    }

    public class filter_fancy extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // TODO Auto-generated method stub
        	data = temp;
            FilterResults Result = new FilterResults();
            // if constraint is empty return the original names
            if(constraint.length() == 0 ){
                Result.values = data;
                Result.count = data.size();
                return Result;
            }

            ArrayList<FoodChain> Filtered_Names = new ArrayList<FoodChain>();
            String filterString = constraint.toString().toLowerCase();
            String filterableString;

            for(int i = 0; i<data.size(); i++){
                filterableString = data.get(i).getType();
                if(filterableString.toLowerCase().contains(filterString)){
                    Filtered_Names.add(data.get(i));
                }
            }
            Result.values = Filtered_Names;
            Result.count = Filtered_Names.size();

            return Result;
        }

		@SuppressWarnings("unchecked")
		@Override
        protected void publishResults(CharSequence constraint,FilterResults results) {
            // TODO Auto-generated method stub
            data = (ArrayList<FoodChain>) results.values;
            notifyDataSetChanged();
        }

    }
   
}