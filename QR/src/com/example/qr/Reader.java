package com.example.qr;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import com.google.zxing.ReaderException;

public class Reader extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.read);

		try {
			Button scanner = (Button) findViewById(R.id.scanner);
			scanner.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					
					Intent intent = new Intent("com.google.zxing.client.android.SCAN");
					intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
					startActivityForResult(intent, 0);
					/*Intent intent = new Intent("com.google.zxing.client.android.SCAN");
					intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
					startActivityForResult(intent, 0);
					
					
					String packageString = "com.example.qr";
					Intent intent = new Intent("com.google.zxing.client.android.SCAN");
					intent.setPackage(packageString);
					intent.putExtra("SCAN_MODE", "SCAN_MODE");
					startActivityForResult(intent, 123);*/
					/*int REQUEST_SCAN; //Request code for Intent result
					String packageString = "com.example.qr.Reader";

					Intent intent = new Intent("com.google.zxing.client.android.SCAN");
					intent.setPackage(packageString);
					//Add any optional extras to pass
					intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
					//Launch
					startActivityForResult(intent, 1234);*/
					
				}

			});

		} catch (ActivityNotFoundException anfe) {
			Log.e("onCreate", "Scanner Not Found", anfe);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    
	    if (item.getTitle().toString().equals(R.string.action_read)) {
	    	Intent lv_nIntent;
			lv_nIntent = new Intent(getBaseContext(), Reader.class);						
			startActivity(lv_nIntent);
			finish();
			return true;
	    }	   
	    return super.onOptionsItemSelected(item);
	}
	
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		   if (requestCode == 0) {
		      if (resultCode == RESULT_OK) {
		         String contents = intent.getStringExtra("SCAN_RESULT");
		         String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
		         // Handle successful scan
		      } else if (resultCode == RESULT_CANCELED) {
		         // Handle cancel
		      }
		   }
		}

}
