package com.example.adapter.utils;

import static com.example.adapter.utils.CommonUtilities.URL_INIT;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nomorelines.FoodListActivity;
import com.example.nomorelines.R;
import com.example.objectClass.FoodItems;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class FoodListAdapter extends BaseAdapter implements Filterable {
	private ArrayList<FoodItems> data;
	private ArrayList<FoodItems> temp;
	private static LayoutInflater inflater = null;
	FoodListActivity foodActivity;
	DisplayImageOptions options;
    ImageLoader imageLoader;
	public FoodListAdapter(FoodListActivity foodActivity, ArrayList<FoodItems> d) {

		temp=data = d;
		this.foodActivity = foodActivity;

		inflater = (LayoutInflater) foodActivity.getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.defaultfood)
		.showImageForEmptyUri(R.drawable.defaultfood)
		.showImageOnFail(R.drawable.defaultfood)
		.cacheInMemory()
		.cacheOnDisc()
		.displayer(new RoundedBitmapDisplayer(20))
		.build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(foodActivity.getActivity()));
        
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

	public synchronized View getView(int position, View convertView,
			ViewGroup parent) {

		final FoodItems fooditem = data.get(position);
		final View vi = (convertView == null) ? inflater.inflate(
				R.layout.food_row, null) : convertView;
		final QuantityListener ql = new QuantityListener(
				((TextView) vi.findViewById(R.id.quantityOrder)), position);
		ImageView thumb_image;
		thumb_image=(ImageView)vi.findViewById(R.id.food_image);
		
		imageLoader.displayImage(URL_INIT+fooditem.getThumb_photo(), thumb_image, options);
    	
		TextView price = (TextView) vi.findViewById(R.id.foodPrice); 
		TextView name = (TextView) vi.findViewById(R.id.foodTitle); // title
		TextView description = (TextView) vi.findViewById(R.id.foodDescription); // artist
																					// name
		vi.findViewById(R.id.plusButton).setOnClickListener(ql);
		vi.findViewById(R.id.minusButton).setOnClickListener(ql);

		String string = fooditem.getDescription();
		price.setText(fooditem.getPrice_toS()+"PHP");
		// Setting all values in listview
		name.setText(fooditem.getName());
		description.setText(string);
		// Setting the photo
		vi.setTag(fooditem);

		return vi;
	}


	private int incrementButton(TextView tv) {
		int num = Integer.parseInt(tv.getText().toString());
		++num;
		tv.setText(Integer.toString(num));
		return num;
	}

	private int decrementButton(TextView tv) {
		int num = Integer.parseInt(tv.getText().toString());
		if (num > 0) {
			--num;
			tv.setText(Integer.toString(num));
		}
		return num;
	}

	class QuantityListener implements OnClickListener {
		TextView quantity;
		int index;

		public QuantityListener(TextView quantity, int index) {
			this.quantity = quantity;
			this.index = index;
		}

		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.minusButton:
				foodActivity.onChangeQty(index, decrementButton(quantity));
				break;
			case R.id.plusButton:
				foodActivity.onChangeQty(index, incrementButton(quantity));
				break;
			}
		}

	}

	@Override
	public Filter getFilter() {
		return null;
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

            ArrayList<FoodItems> Filtered_Names = new ArrayList<FoodItems>();
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
            data = (ArrayList<FoodItems>) results.values;
            notifyDataSetChanged();
        }

    }
}