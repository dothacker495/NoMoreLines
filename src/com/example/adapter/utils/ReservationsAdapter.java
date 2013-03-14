package com.example.adapter.utils;


import static com.example.adapter.utils.CommonUtilities.URL_INIT;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nomorelines.R;
import com.example.objectClass.Reservation;
public class ReservationsAdapter extends BaseAdapter {
	  private Activity activity;
	    private ArrayList<Reservation> data;
	    private static LayoutInflater inflater=null;
	    private DrawableManager imageManager;
	    public ReservationsAdapter(Activity a, ArrayList<Reservation> d) {
	        activity = a;
	        data=d;
	        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	       imageManager= new DrawableManager();
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
	    	final Reservation reserve = data.get(position);
	    	final View vi=(convertView==null)?inflater.inflate(R.layout.list_reservation, null):convertView;
	    	
			TextView status = (TextView)vi.findViewById(R.id.statusReservation);
	        TextView title = (TextView)vi.findViewById(R.id.title2); // title
	        TextView time = (TextView)vi.findViewById(R.id.profTime);
	        TextView date = (TextView)vi.findViewById(R.id.profDate);
	        ImageView foodImage = (ImageView)vi.findViewById(R.id.foodchain_image);
	        imageManager.fetchDrawableOnThread(URL_INIT+reserve.getThumbPhoto(), foodImage);
	        // Setting all values in listview
	       
	        title.setText(reserve.getName());
	     
	        time.setText(reserve.getTime());
	        
	        date.setText(reserve.getDate());
	        if(checkValidity(reserve)){
	        	status.setText("Soon to Hapen");
	        	status.setTextColor(Color.BLUE);
	        }
	        else{
	        	status.setText("Expired");
	        	status.setTextColor(Color.RED);
	        }
	        vi.setTag(reserve);
	        
			    		
			  	      	 
			  	 
	        	
	        return vi;
	    }
	 
	    public boolean checkValidity(Reservation res) {
			boolean ret = false;
			Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR);
			int minute = c.get(Calendar.MINUTE);
			Date compareOne = parseTime(hour  + ":" + minute);
			Date compareTwo = parseTime(res.getTime());

			int day = c.get(Calendar.DAY_OF_MONTH);
			int month = c.get(Calendar.MONTH);
			int year = c.get(Calendar.YEAR);
			int []dateP = new int[3];
			int ctr=0;
			StringTokenizer st = new StringTokenizer(res.getDate(), "-");
			while (st.hasMoreElements()) {
				dateP[ctr++] = Integer.parseInt(st.nextToken());
			}
			if (isEqualOrGreater(year, dateP[0])
					&& isEqualOrGreater(month, dateP[1])
					&& (isEqual(day, dateP[2])
							&& (compareTwo.after(compareOne)) || day < dateP[2]))
				ret = true;
			;
			return ret;
		}

		private boolean isEqual(int x, int y) {
			return x == y ? true : false;
		}

		private boolean isEqualOrGreater(int x, int y) {
			return x <= y ? true : false;
		}
	    private Date parseTime(String date) {
			String inputFormat = "HH:mm";
			SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat,
					Locale.US);

			try {
				return inputParser.parse(date);
			} catch (java.text.ParseException e) {
				return new Date(0);
			}
		}

	}