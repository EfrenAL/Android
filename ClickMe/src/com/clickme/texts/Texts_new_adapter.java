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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.clickme.R;
import com.clickme.TextsActivity;
import com.clickme.bd.BDManager;

public class Texts_new_adapter extends ArrayAdapter<String> {
	Context context;
	ArrayList<String> Texts = new ArrayList<String>();
	int layoutResourceId;
	private BDManager bdmanager;

	public Texts_new_adapter(Context context, int layoutResourceId, ArrayList<String> texts) {
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
			holder.et_text = (EditText) row.findViewById(R.id.textGV);
			holder.rlayout = (RelativeLayout) row.findViewById(R.id.relativeGV);
			holder.ibDelete = (ImageButton) row.findViewById(R.id.ib_delete);
			final int pos = position;
			holder.ibDelete.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Toast.makeText(context, "Borramos texto", Toast.LENGTH_SHORT).show();
					bdmanager = new BDManager(context);
					bdmanager.deleteText(pos);
					
					ListView lv_texts = (ListView) ((Activity) context).findViewById(R.id.lv_texts);
	        		ArrayList<String> texts = bdmanager.getTexts();
	        		Texts_new_adapter texts_new_adapter = new Texts_new_adapter(context, R.layout.new_text, texts);
	        		lv_texts.setAdapter(texts_new_adapter);
										
					notifyDataSetChanged();
				}
			});
			
			
			holder.rlayout.setOnClickListener(new View.OnClickListener() {				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					EditText et_textGV = (EditText)v.findViewById(R.id.textGV);
					et_textGV.requestFocus();
					//borrar lo de abajo ver si sirve lo de arriba
					//Toast.makeText(context,"List item clicked",Toast.LENGTH_SHORT).show();
				}
			});
			
			holder.et_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {				
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					BDManager bdManager = new BDManager(context);
					if (!hasFocus){
                        final int position = v.getId();
                        final EditText Caption = (EditText) v;
                        bdManager.update(Caption.getText().toString(), pos);
                        //myItems.get(position).caption = Caption.getText().toString();
                    }					
				}
			});
			
			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}
		String text_item = Texts.get(position);
		holder.et_text.setText(text_item);

		return row;
	}

	public static class RecordHolder {
		EditText et_text;
		RelativeLayout rlayout;
		ImageButton ibDelete;
	}

}
