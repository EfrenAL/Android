package com.ally.activities;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.ally.data.Data;
import com.ally.utilities.MapDrawing;
import com.example.ally.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

@SuppressLint("NewApi")
public class MapActivity extends Activity {
	private int idRoute;
	private GoogleMap myMap = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Data declaration
		ActionBar bar;
		Data data;
		//Change actionbar title and color
		setTitle(getResources().getString(R.string.map));
		bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue_sky)));
		//Set content view
		setContentView(R.layout.activity_map);
		// Get selected route
		getIdRoute();
		//Get json data
		data = Data.getInstance();
		// Set myMap properties
		myMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		// Draw routes
		MapDrawing.drawRoute(data.getRoute(idRoute), myMap);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
	}
	//Get setelected route from ExtendedRouteActivity
	public void getIdRoute() {
		Intent intent = getIntent();
		idRoute = intent.getIntExtra(getResources().getString(R.string.activity_parameter), 0);
	}

}
