package com.clickme.colors;

import java.util.ArrayList;

import com.clickme.R;
import com.clickme.contactos.Contacts_adapter.RecordHolder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


@SuppressLint("NewApi")
public class Colors_adapter extends ArrayAdapter<String>{

	int color_selected;
	Context context;	
	ArrayList<String> colors = new ArrayList<String>();
	//Id correspondiente al Layout del item del contacto
	int layoutResourceId;
	
	public Colors_adapter(Context context, int layoutResourceId, ArrayList<String> colors, int color_selected) {
		super(context, layoutResourceId,colors);
		this.colors = colors;
		this.context = context;
		this.layoutResourceId = layoutResourceId;	
		this.color_selected = color_selected;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RecordHolder holder = null;
		
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new RecordHolder();
			holder.circle = (ImageButton) row.findViewById(R.id.b_circle);
			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}				
		
		/*
		if (holder.circle.getVisibility() == View.VISIBLE) {
			holder.circle.setVisibility(View.VISIBLE);
		}else{
			holder.circle.setVisibility(View.INVISIBLE);
		}
		
		holder.circle.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				if(v.getVisibility() == View.INVISIBLE){
					v.setVisibility(View.VISIBLE);
				}else{
					v.setVisibility(View.INVISIBLE);
				}
			}
		});*/
		
		if (position == color_selected) {
			holder.circle.setVisibility(View.VISIBLE);
		}
		
		String color_item = colors.get(position);				
		GradientDrawable gradient = (GradientDrawable) this.context.getResources().getDrawable(R.drawable.circle_color);				
		gradient.setColor(Color.parseColor(color_item));
		row.setBackground(gradient);
			
		return row;
	}

	public static class RecordHolder {		
		ImageButton circle;
	}
	
}
