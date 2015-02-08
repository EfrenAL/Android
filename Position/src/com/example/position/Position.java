package com.example.position;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bd.AdminSQLiteOpenHelper;

//http://stackoverflow.com/questions/17591147/how-to-get-current-location-in-android

public class Position extends Activity {

	public GPSTracker mGPS;
	private static final int HANDLER_DELAY = 1000 * 60 * 5;
	private ArrayList<String> elements;
	private ArrayList<String> latitud;
	private ArrayList<String> longitud;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_position);
		mGPS = new GPSTracker(this);

		Button b_position = (Button) findViewById(R.id.b_posicion);
		b_position.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setPosition();
			}
		});

		Button b_show = (Button) findViewById(R.id.b_Show);
		b_show.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {				
				request();
			}
		});

		Button b_delete = (Button) findViewById(R.id.b_borrar);
		b_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				delete();
			}
		});

		ListView lista_posi = (ListView) findViewById(R.id.listView1);
		lista_posi.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + latitud.get(position).replace(",", ".") + "," + longitud.get(position).replace(",", ".") + "&daddr=" + latitud.get(position).replace(",", ".") + "," + longitud.get(position).replace(",", ".")));
				startActivity(intent);

				Toast.makeText(getBaseContext(), "posicion: " + position + " id: " + id, Toast.LENGTH_SHORT).show();
			}
		});

		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				setPosition();
				handler.postDelayed(this, HANDLER_DELAY);
			}
		}, HANDLER_DELAY);
	}

	public void setPosition() {
		double longitude;
		double latitude;
		String data_time;

		if (mGPS.canGetLocation) {
			mGPS.getLocation();
			data_time = getDateTime();
			latitude = mGPS.getLatitude();
			longitude = mGPS.getLongitude();

			AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "positions", null, 1);
			SQLiteDatabase bd = admin.getWritableDatabase();
			ContentValues registro = new ContentValues();
			registro.put("position_at", data_time);
			registro.put("latitud", latitude);
			registro.put("longitud", longitude);
			bd.insert("positions", null, registro);
			bd.close();
			Toast.makeText(this, "Lat: " + latitude + " Long: " + longitude, Toast.LENGTH_SHORT).show();

		} else {
			Toast.makeText(getApplicationContext(), "Unable", Toast.LENGTH_SHORT).show();
			System.out.println("Unable");
		}
	}

	public void request() {		
		AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "positions", null, 1);
		SQLiteDatabase bd = admin.getWritableDatabase();

		ListView lista_posi = (ListView) findViewById(R.id.listView1);

		Cursor fila = bd.rawQuery("select * from positions", null);
		elements = new ArrayList<String>();

		if (fila.moveToFirst()) {

			while (fila.isAfterLast() == false) {

				String name1 = fila.getString(fila.getColumnIndex("position_at"));
				String name2 = fila.getString(fila.getColumnIndex("latitud"));
				String name3 = fila.getString(fila.getColumnIndex("longitud"));

				elements.add("Time:" + name1 + "\n Lat: " + name2 + " Long: " + name3);
				fila.moveToNext();
			}
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, elements);

		lista_posi.setAdapter(adapter);

		fila.close();
		bd.close();

	}

	public void delete() {
		AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "positions", null, 1);
		SQLiteDatabase bd = admin.getWritableDatabase();
		int cant = bd.delete("positions", null, null);
		bd.close();
		if (cant == 1)
			Toast.makeText(this, "All positions deleted", Toast.LENGTH_SHORT).show();
	}

	private String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}

}
