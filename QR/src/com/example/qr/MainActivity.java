package com.example.qr;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.InputFilter.LengthFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

//http://stackoverflow.com/questions/4782543/integration-zxing-library-directly-into-my-android-application/15510698#15510698
public class MainActivity extends Activity {
	private Bitmap qr_bitmap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		

		
		Button bt_generate = (Button)findViewById(R.id.bt_generate);
		bt_generate.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ImageView imageView = (ImageView) findViewById(R.id.qrCode);
				EditText et_text = (EditText) findViewById(R.id.et_text);
				String qrData = et_text.getText().toString();
				int qrCodeDimention = 500;

				QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrData, null,Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(), qrCodeDimention);

				try {
				    qr_bitmap = qrCodeEncoder.encodeAsBitmap();
				    imageView.setImageBitmap(qr_bitmap);
				    if (qr_bitmap != null) {
						//writeBitmap(bitmap);
					}
				} catch (WriterException e) {
				    e.printStackTrace();
				}
			}
		});
		
		Button bt_save = (Button)findViewById(R.id.bt_save);
		bt_save.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (qr_bitmap!=null) {
					writeBitmap(qr_bitmap);					
					final Toast toast = Toast.makeText(getApplicationContext(), "QR saved successfully", Toast.LENGTH_SHORT);
				    toast.show();
					
				}				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    
	    if (item.getTitle().toString().equals(getString(R.string.action_read))) {
	    	Intent lv_nIntent;
			lv_nIntent = new Intent(getBaseContext(), Reader.class);						
			startActivity(lv_nIntent);
			finish();
			return true;
	    }	   
	    return super.onOptionsItemSelected(item);
	}
	
	public void writeBitmap(Bitmap finalBitmap){
		String root = Environment.getExternalStorageDirectory().toString();
		File myDir = new File(root + "/saved_images");    
		myDir.mkdirs();
		Random generator = new Random();
		int n = 10000;
		n = generator.nextInt(n);
		String fname = "Image-"+ n +".jpg";
		File file = new File (myDir, fname);
		if (file.exists ()) file.delete (); 
		try {
		       FileOutputStream out = new FileOutputStream(file);
		       finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
		       out.flush();
		       out.close();

		} catch (Exception e) {
		       e.printStackTrace();
		}
	}
	

}
