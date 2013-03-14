package com.example.adapter.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import com.example.objectClass.MyApplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import static com.example.adapter.utils.CommonUtilities.URL_INIT;
//Uploader class
public class HttpUploader extends AsyncTask<String, Void, String> {
	String id;
	MyApplication app;
	public HttpUploader(String id, MyApplication app) {
	this.id=id;
	this.app = app;
	}
	@Override
	protected String doInBackground(String... path) {

		String outPut = null;

		for (String sdPath : path) {

			Bitmap bitmapOrg = BitmapFactory.decodeFile(sdPath);
			ByteArrayOutputStream bao = new ByteArrayOutputStream();

			// Resize the image
			double width = bitmapOrg.getWidth();
			double height = bitmapOrg.getHeight();
			double ratio = 400 / width;
			int newheight = (int) (ratio * height);

			System.out.println("———-width" + width);
			System.out.println("———-height" + height);

			bitmapOrg = Bitmap.createScaledBitmap(bitmapOrg, 400, newheight,
					true);

			// Here you can define .PNG as well
			bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 95, bao);
			byte[] ba = bao.toByteArray();
			String ba1 = Base64.encodeToString(ba, Base64.DEFAULT);

			System.out.println("uploading image now ——–" + ba1);

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("image", ba1));
			nameValuePairs.add(new BasicNameValuePair("id", id));
			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(
						"http://nomorelines.zz.mu/imageUpload.php");
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();

				// print responce
				outPut = EntityUtils.toString(entity);
				Log.i("GET RESPONSE—-", outPut);

				// is = entity.getContent();
				Log.e("log_tag ******", "good connection");

				bitmapOrg.recycle();

			} catch (Exception e) {
				Log.e("log_tag ******",
						"Error in http connection " + e.toString());
			}
			JsonClient client= new JsonClient(URL_INIT);
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			try {
				JSONArray json = client.getArray("getUser.php", map);

				if (json.length() > 0) {
					
					
					app.setThumbImage(json.getJSONObject(0).getString("photo"));
				}
					

			} catch (ClientProtocolException e) {
				e.printStackTrace();
				Log.e("Download", e.getMessage());
			} catch (IOException e) {
				Log.e("Download", e.getMessage());
				e.printStackTrace();
			} catch (JSONException e) {
				Log.e("Download", e.getMessage());
				e.printStackTrace();
			}
			
		}
		return outPut;
	}
}