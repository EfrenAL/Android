package com.ally.activities;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;

import com.ally.adapters.AdapterExtendedRoutes;
import com.ally.data.Data;
import com.ally.structures.routes.Routes;
import com.ally.utilities.ExtendedRoute;
import com.ally.utilities.Utilities;
import com.example.ally.R;

@SuppressLint("NewApi")
public class ExtendedRouteActivity extends Activity {

	private int idRoute;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_extended_route);
		//Data declaration
		ArrayList<ExtendedRoute> extendedRoutes = new ArrayList<ExtendedRoute>();
		AdapterExtendedRoutes adapterExtRout;
		ActionBar actionBar;
		Data data;
		Routes route;
		GridView gridview;
		//Set action bar configuration
		setTitle(getResources().getString(R.string.route));
		actionBar = getActionBar();
	    actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue_sky)));
		// Get selected route
		getIdRoute();
		// Get Json Instance
		data = Data.getInstance();
		// Get selected route
		route = data.getRoute(idRoute);
		// Get all extended routes
		extendedRoutes = route.getAllStopsDuration();
		// Give all stops to the adapter
		adapterExtRout = new AdapterExtendedRoutes(this, R.layout.item_extended_route, extendedRoutes);
		// Get gridview
		gridview = (GridView) findViewById(R.id.gv_extended_route);
		// Set adapter within gridview
		gridview.setAdapter(adapterExtRout);

	}
	//Save selected route in main activity
	public void getIdRoute() {
		Intent intent;
		intent = getIntent();
		idRoute = intent.getIntExtra(getResources().getString(R.string.activity_parameter), 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.extended_route, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		switch (itemId) {
		case R.id.action_show_map:
			Utilities.goToMapActivity(idRoute, this);			
			break;
		}
		return true;
	}

}
