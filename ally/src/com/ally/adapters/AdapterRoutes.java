package com.ally.adapters;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ally.structures.routes.Routes;
import com.ally.structures.routes.Segments;
import com.ally.utilities.Utilities;
import com.devsmart.android.ui.HorizontalListView;
import com.example.ally.R;

@SuppressLint("UseValueOf")
public class AdapterRoutes extends ArrayAdapter<Routes>{
	
	private Context context;	
	private ArrayList<Routes> routes = new ArrayList<Routes>();
	private int layoutResourceId;
	
	public AdapterRoutes(Context context, int resource, ArrayList<Routes> objects) {
		super(context, resource, objects);
		this.context = context;
		this.layoutResourceId = resource;
		this.routes = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//Date declaration
		View row = convertView;
		RecordHolder holder = null;
		LayoutInflater inflater;
		ArrayList<Segments> al_segments;
		AdapterTransports adapter_transport;
		double price;
		String stprice;
		String startEnd;
		String duration;
		//
		if (row == null) {
			inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new RecordHolder();
			holder.hlv_transports = (HorizontalListView) row.findViewById(R.id.hlv_transports);
			holder.tv_price = (TextView) row.findViewById(R.id.tv_price);
			holder.tv_start_end = (TextView) row.findViewById(R.id.tv_start_end);
			holder.tv_duration = (TextView) row.findViewById(R.id.tv_duration);
			
			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}
		
		//Get segments
		al_segments = routes.get(position).getSegments();
		//There are an horizontal array for each route
		adapter_transport = new AdapterTransports(context, R.layout.image_transport, al_segments);
		holder.hlv_transports.setAdapter(adapter_transport);
		//Set price
		price = routes.get(position).getPrice();
		if(price > 0){
			//Set price with two decimals and currency
			stprice = String.format( "%.2f", price ) + routes.get(position).getCurrency();
			holder.tv_price.setText(stprice);	
		}		
		// Set Start - End 
		startEnd = routes.get(position).getStart() + " - " + routes.get(position).getEnd();  
		holder.tv_start_end.setText(startEnd);
		
		//Set duration
		duration = routes.get(position).getDuration();  
		holder.tv_duration.setText(duration);
		
		//Save horizontal list view position in gridview 
		holder.hlv_transports.setTag(new Integer(position));
		holder.hlv_transports.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				int poss = (int)parent.getTag();
				Utilities.goToExtRoutActivity(poss, context);
			}
		});
		
		return row;
	}

	public static class RecordHolder {
		HorizontalListView hlv_transports;
		TextView tv_price;
		TextView tv_start_end;
		TextView tv_duration;
	}
}
