package com.ally.activities;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import com.ally.adapters.AdapterRoutes;
import com.ally.data.Data;
import com.ally.structures.routes.Routes;
import com.ally.utilities.Utilities;
import com.example.ally.R;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Data declaration
		ActionBar bar;
		Data data;
		ArrayList<Routes> al_routes;
		AdapterRoutes adapter_routes;
		GridView gridview;
		//Set action bar title
		setTitle(getResources().getText(R.string.routes));
		bar = getActionBar();
		//Set action bar color
		bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue_sky)));
		setContentView(R.layout.activity_main);
		//Get Json Instance
		data = Data.getInstance();
		//Get all availables routes
		al_routes = data.getRoutes();
		//Give all availables routes to the adapter
		adapter_routes = new AdapterRoutes(this,R.layout.item_route,al_routes);
		//Get gridview
		gridview = (GridView) findViewById(R.id.gv_routes);
		//Set adapter within gridview
		gridview.setAdapter(adapter_routes);
		//Gridview onclick handler		
		gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//Go to Extended route activity
				Utilities.goToExtRoutActivity(position, getBaseContext());
			}
		});		
	}
}
