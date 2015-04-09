package com.clickme.conversation;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import com.clickme.R;
import com.clickme.contactos.Contact;
import com.clickme.contactos.Contacts_adapter.RecordHolder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class Conversation_adapter extends ArrayAdapter<String> {
	
	Context context;	
	ArrayList<String> Messages = new ArrayList<String>();
	int layoutResourceId;
	int color_me;
	int color_contact;
	
	public Conversation_adapter(Context context,int layoutResourceId, ArrayList<String> messages, int color_me, int color_cont) {
		super(context, layoutResourceId, messages);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.Messages = messages;
		this.color_contact = color_cont;
		this.color_me = color_me;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RecordHolder holder = null;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new RecordHolder();
			holder.text = (TextView)  row.findViewById(R.id.tv_text);
			holder.rlayout = (RelativeLayout) row.findViewById(R.id.rl_text);
			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}				
		//Get relative layout params
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)holder.text.getLayoutParams();
		//Get backgroung
		LayerDrawable layers = (LayerDrawable)  this.context.getResources().getDrawable(R.drawable.shape);
		GradientDrawable shape = (GradientDrawable) (layers.findDrawableByLayerId(R.id.customShape));
		
		
		String text_item = Messages.get(position);		
		String[] separated = text_item.split("\\:");
		if (!separated[0].equals("Yo")) {			
			//params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			shape.setColor(color_contact);
			holder.text.setBackground(shape);

		}else{
			//params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			shape.setColor(color_me);
			holder.text.setBackground(shape);
		}
		holder.text.setText(separated[1]);
		
		//row.setBackground(shape);
		
		return row;
	}
	
	public static class RecordHolder {
		TextView text;
		RelativeLayout rlayout;
	}

}
