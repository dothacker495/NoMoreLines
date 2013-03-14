package com.example.handlers;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;

public class RoutePathOverlay extends Overlay{static int adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdfsgd;List<GeoPoint> adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdfs;boolean adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdfsg;public RoutePathOverlay() {}public RoutePathOverlay(List<GeoPoint> adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdf) {this(adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdf,adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdfsgd,true);}@SuppressWarnings("static-access")public RoutePathOverlay(List<GeoPoint> adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdf, int adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdfsgd, boolean adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdfsg) {adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdfs = adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdf;this.adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdfsgd = adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdfsgd;this.adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdfsg = adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdfsg;}private void drawOval(Canvas adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdfs, Paint adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdf, Point adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sd) {Paint adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325s = new Paint(adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdf);adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325s.setStyle(Paint.Style.FILL_AND_STROKE);adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325s.setStrokeWidth(2);
            int adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325 = 6;
            RectF adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv312423432 = new RectF(adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sd.x - adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325, adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sd.y - adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325, adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sd.x + adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325, adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sd.y + adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325);
            adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdfs.drawOval(adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv312423432, adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325s);               
    }

    @Override
	public synchronized boolean draw(Canvas adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sd, MapView adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdf, boolean adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325s, long when) {
            Projection adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325 = adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdf.getProjection();
            if (adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325s == false && adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdfs != null) {
                    Point adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv312423432 = null, adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv31242 = null;
                    Path adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv31242343 = new Path();
                    //We are creating the path
                   synchronized (adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdfs) { for (int i = 0; i < adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdfs.size(); i++) {
                            GeoPoint adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234 = adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdfs.get(i);
                            Point adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv312423 = new Point();
                            adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325.toPixels(adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234, adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv312423);
                            if (i == 0) { //This is the start point
                                    adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv312423432 = adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv312423;
                                    adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdfsgd = Color.RED;
                                    adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv31242343.moveTo(adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv312423.x, adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv312423.y);
                                    
                            } 
                           
                            else {
                                    if (i == adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdfs.size() - 1){//This is the end point
                                            adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv31242 = adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv312423;
                                    		adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdfsgd = Color.BLUE;
                                    		
                                    }
                             
                                    adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv31242343.lineTo(adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv312423.x, adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv312423.y);
                            }
                            
                           
                    }
            }

                    Paint paint = new Paint();
                    paint.setAntiAlias(true);
                    paint.setColor(adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdfsgd);
                    paint.setStyle(Paint.Style.STROKE);	
                    paint.setStrokeWidth(5);
                    paint.setAlpha(90);
                    if (getDrawStartEnd()) {
                            if (adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv312423432 != null) {
                                    drawOval(adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sd, paint, adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv312423432);
                            }
                            if (adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv31242 != null) {
                                    drawOval(adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sd, paint, adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv31242);
                            }
                    }
                    if (!adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv31242343.isEmpty())
                            adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sd.drawPath(adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv31242343, paint);
            }
            return super.draw(adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sd, adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdf, adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325s, when);
    }

    public synchronized boolean getDrawStartEnd() {
            return adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdfsg;
    }

    public synchronized void setDrawStartEnd(boolean adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdfsg) {
            this.adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdfsg = adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdfsg;
    }
}
