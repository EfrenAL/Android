package com.clickme.texts;

import java.util.ArrayList;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.clickme.R;


public class Texts_adapter extends ArrayAdapter<String> {
	Context context;	
	ArrayList<String> Texts = new ArrayList<String>();
	int layoutResourceId;
	
	public Texts_adapter(Context context,int layoutResourceId, ArrayList<String> texts) {
		super(context, layoutResourceId, texts);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.Texts = texts;
		
	}
	@SuppressLint("NewApi") 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RecordHolder holder = null;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new RecordHolder();
			holder.text = (TextView)  row.findViewById(R.id.textGV);
			holder.rlayout = (RelativeLayout) row.findViewById(R.id.relativeGV);
			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}				
		String text_item = Texts.get(position);
		holder.text.setText(text_item);
			
		return row;
	}
	
	public static class RecordHolder {
		TextView text;
		RelativeLayout rlayout;
	}

}
