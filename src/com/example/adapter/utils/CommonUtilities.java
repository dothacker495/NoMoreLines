package com.example.adapter.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.actionbarsherlock.app.SherlockMapActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CommonUtilities {
	public final static String URL_INIT = "http://nomorelines.zz.mu/";
	// public final static String URL_INIT = "http://10.0.2.2/no/";
	public final static String URL_FOODCHAIN = "getFoodChain.php";
	public final static String URL_FOODITEM = "getFoodItems.php";
	public final static String URL_RESERVE = "postReservation.php";
	public final static String URL_FOODORDER = "postFoodOrder.php";
	public final static String URL_TABLEORDER = "postTableOrder.php";
	public final static String URL_TABLES = "getAvailableTable.php";
	public final static String URL_RESERVATIONS = "getReservations.php";
	public static final String SERVER_URL = URL_INIT + "reg.php";
	public static final String SERVER_URL2 = URL_INIT + "session.php";
	public static final String SERVER_URL3 = URL_INIT + "session_delete.php";
	public static final String TAG = "GCM";
	// Google project id
	public static final String SENDER_ID = "599398208923";
	static final String DISPLAY_MESSAGE_ACTION = "Push Notification";
	static final String EXTRA_MESSAGE = "message";

	public static Bitmap getLogo(String str) {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream((InputStream) new URL(URL_INIT
					+ str).getContent());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;
	}

	public static void displayMessage(Context context, String message) {
		Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
		intent.putExtra(EXTRA_MESSAGE, message);
		context.sendBroadcast(intent);
	}

	public static void scaleImage(ImageView view, int boundBoxInDp) {
		// Get the ImageView and its bitmap
		Drawable drawing = view.getDrawable();
		Bitmap bitmap = ((BitmapDrawable) drawing).getBitmap();

		// Get current dimensions
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		// Determine how much to scale: the dimension requiring less scaling is
		// closer to the its side. This way the image always stays inside your
		// bounding box AND either x/y axis touches it.
		float xScale = ((float) boundBoxInDp) / width;
		float yScale = ((float) boundBoxInDp) / height;
		float scale = (xScale <= yScale) ? xScale : yScale;

		// Create a matrix for the scaling and add the scaling data
		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);

		// Create a new bitmap and convert it to a format understood by the
		// ImageView
		Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		@SuppressWarnings("deprecation")
		BitmapDrawable result = new BitmapDrawable(scaledBitmap);
		width = scaledBitmap.getWidth();
		height = scaledBitmap.getHeight();

		// Apply the scaled bitmap
		view.setImageDrawable(result);

		// Now change ImageView's dimensions to match the scaled image
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view
				.getLayoutParams();
		params.width = width;
		params.height = height;
		view.setLayoutParams(params);
	}

	public static int dpToPx(int dp, Context context) { // context should be
														// getApplicationContext();
		float density = context.getResources().getDisplayMetrics().density;
		return Math.round((float) dp * density);
	}

	public boolean isOnline(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		boolean isConnectedToNetwork = (networkInfo != null && networkInfo
				.isConnected());
		return isConnectedToNetwork;
	}
	

	public static boolean checkNetwork(Context c,SherlockMapActivity a) {
		LocationManager service = (LocationManager) a.getSystemService(c.LOCATION_SERVICE);
		boolean enabled = service
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		// Check if enabled and if not send user to the network settings
		// Better solution would be to display a dialog and suggesting to
		// go to the settings
		if (!enabled) {
			chooseToSet(c,a);
		}
		return service.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	}

	public static void chooseToSet(Context c, SherlockMapActivity a) {
		final SherlockMapActivity ac=a;
		
		new AlertDialog.Builder(c)
				.setTitle("Location Service not enabled")
				.setMessage("Enable it?")
				.setNegativeButton("NO", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						dialog.dismiss();

					}
				})
				.setPositiveButton("YES",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(
										Settings.ACTION_LOCATION_SOURCE_SETTINGS);
								ac.startActivity(intent);
								dialog.dismiss();
							}

						}).show();
	}

}
