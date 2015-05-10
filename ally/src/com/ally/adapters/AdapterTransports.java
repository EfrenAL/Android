package com.ally.adapters;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.ally.structures.routes.Segments;
import com.example.ally.R;

@SuppressLint("NewApi")
public class AdapterTransports extends ArrayAdapter<Segments>{
	
	private Context context;
	private ArrayList<Segments> segments = new ArrayList<Segments>();
	private int layoutResourceId;
	
	public AdapterTransports(Context context, int resource, ArrayList<Segments> objects) {
		super(context, resource, objects);
		this.context = context;
		this.layoutResourceId = resource;
		this.segments = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RecordHolder holder = null;
		LayoutInflater inflater;
		Segments segment;
		
		if (row == null) {
			inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new RecordHolder();
			holder.iv_transport = (ImageView) row.findViewById(R.id.iv_transport);
			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}
		//Get all segment
		segment = segments.get(position);
		// Set transport icon
		holder.iv_transport.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		holder.iv_transport.setImageDrawable(segment.getIcon());
	
		return row;
	}

	public static class RecordHolder {
		ImageView iv_transport;
	}
	
	public static Drawable updateImageView(Drawable drawable){
	    return drawable;
	}
	
}
