package com.example.adapter.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import com.example.nomorelines.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class MapOverlay extends Overlay
{
	GeoPoint startpoint; 
	Context cont;
	
	public MapOverlay(GeoPoint point,Context context){
		
		this.startpoint=point;
		this.cont=context;
		
	}
    @Override
    public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) {  
        super.draw(canvas, mapView, shadow);  
        //---translate the GeoPoint to screen pixels---  
        Point screenPts = new Point();  
        mapView.getProjection().toPixels(startpoint, screenPts);  
        /*
        //--------------draw circle----------------------  
        Point pt = mapView.getProjection().toPixels(geop_startpoint,screenPts);  
        Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);  
        circlePaint.setColor(0x30000000);  
        circlePaint.setStyle(Style.FILL_AND_STROKE);  
        canvas.drawCircle(screenPts.x, screenPts.y, 50, circlePaint);  
		*/ 
        //---add the marker---  
        Bitmap bmp = BitmapFactory.decodeResource(cont.getResources(),R.drawable.marker);  
        
        canvas.drawBitmap(bmp, screenPts.x-bmp.getWidth()/2, screenPts.y-bmp.getHeight()/2, null);
        //canvas.drawBitmap(bmp, screenPts.x, screenPts.y-bmp.getHeight(), null);  
        
        super.draw(canvas,mapView,shadow);  

        return true;  
    }
    
}
