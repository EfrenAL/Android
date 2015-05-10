package com.ally.adapters;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.ally.utilities.ExtendedRoute;
import com.example.ally.R;

public class AdapterExtendedRoutes extends ArrayAdapter<ExtendedRoute> {

	private Context context;
	private int layoutResourceId;
	private ArrayList<ExtendedRoute> extendedRoute = new ArrayList<ExtendedRoute>();

	public AdapterExtendedRoutes(Context context, int resource, ArrayList<ExtendedRoute> objects) {
		super(context, resource, objects);
		this.context = context;
		this.extendedRoute = objects;
		this.layoutResourceId = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//Data declaration
		View row = convertView;
		RecordHolder holder = null;
		LayoutInflater inflater;
		ExtendedRoute extendeRoute;
		if (row == null) {
			inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new RecordHolder();
			holder.tv_date_time = (TextView) row.findViewById(R.id.tv_data_time);
			holder.tv_location = (TextView) row.findViewById(R.id.tv_location);

			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}
		//Get extended route 
		extendeRoute = extendedRoute.get(position);
		// Set date into text view
		holder.tv_date_time.setText(extendeRoute.getDatetime());
		// Set location into text view
		holder.tv_location.setText(extendeRoute.getDescription());
		// Set location color
		holder.tv_location.setTextColor(Color.parseColor(extendeRoute.getColor()));

		return row;
	}

	public static class RecordHolder {
		TextView tv_date_time;
		TextView tv_location;
	}
}