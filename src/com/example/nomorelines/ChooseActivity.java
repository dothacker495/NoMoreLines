package com.example.nomorelines;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

public class ChooseActivity extends Activity {
	private static final int CAMERA_REQUEST = 1888;
	WebView webview;
	String fileName = "capturedImage.jpg";
	FileOutputStream fo;
	String uri;
	Button camera, gallery;
	private static final int SELECT_PICTURE = 1;
	private String selectedImagePath;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_Sherlock_Light_Dialog);
		setContentView(R.layout.choice);
		
		
		camera = (Button) findViewById(R.id.takePhoto);
		gallery = (Button) findViewById(R.id.choosePhoto);
		selectedImagePath =  new String();
		camera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goCamera();
				
			}
		});
		gallery.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goGallery();
				
			}
		});
		
		setResult1("None");
	}
	
	

	public void goCamera() {
		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(cameraIntent, CAMERA_REQUEST);
	}

	public void goGallery() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		startActivityForResult(intent, SELECT_PICTURE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == SELECT_PICTURE) {
				Uri selectedImageUri = data.getData();
				selectedImagePath = getPath(selectedImageUri);
				/*
				try {
					FileInputStream fileis = new FileInputStream(
							selectedImagePath);
					BufferedInputStream bufferedstream = new BufferedInputStream(
							fileis);
					byte[] bMapArray = new byte[bufferedstream.available()];
					bufferedstream.read(bMapArray);
					Bitmap bMap = BitmapFactory.decodeByteArray(bMapArray, 0,
							bMapArray.length);
					// this is the image that you are choosen
					//setImageView(bMap);
					if (fileis != null) {
						fileis.close();
					}
					if (bufferedstream != null) {
						bufferedstream.close();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}*/
			}
			if (requestCode == CAMERA_REQUEST) 
	        {  Bitmap photo = (Bitmap) data.getExtras().get("data"); 
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
            Random randomGenerator = new Random();randomGenerator.nextInt();
            String newimagename=randomGenerator.toString()+".jpg";
            File f = new File(Environment.getExternalStorageDirectory()
                                    + File.separator + newimagename);
            try {
                f.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //write the bytes in file

            try {
                fo = new FileOutputStream(f.getAbsoluteFile());
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                fo.write(bytes.toByteArray());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
                selectedImagePath=f.getAbsolutePath(); 
    //this is the url that where you are saved the image
                /*
                try {
                    FileInputStream fileis=new FileInputStream(selectedImagePath);
                    BufferedInputStream bufferedstream=new BufferedInputStream(fileis);
                    byte[] bMapArray= new byte[bufferedstream.available()];
                    bufferedstream.read(bMapArray);
                    Bitmap bMap = BitmapFactory.decodeByteArray(bMapArray, 0, bMapArray.length);
  //this is the image that you are choosen
                    
                    if (fileis != null) 
                    {
                        fileis.close();
                    }
                    if (bufferedstream != null) 
                    {
                        bufferedstream.close();
                    }
                } catch (FileNotFoundException e) {                 
                    e.printStackTrace();
                } catch (IOException e) {                   
                    e.printStackTrace();
                }     */            
	      }
			setResult1(selectedImagePath);
			finish();
		}
	}

	@SuppressWarnings("deprecation")
	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
	
	public void setResult1(String str){
		Intent i = getIntent();
		Bundle b= new Bundle();
		b.putString("image", str);
		i.putExtras(b);
		setResult(1, i);
		
	}

}
