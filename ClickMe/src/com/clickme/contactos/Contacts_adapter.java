package com.clickme.contactos;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;

import com.clickme.R;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ClipData.Item;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

@SuppressLint("NewApi")
public class Contacts_adapter extends ArrayAdapter<Contact> {

	Context context;	
	ArrayList<Contact> Contacts = new ArrayList<Contact>();
	//Id correspondiente al Layout del item del contacto
	int layoutResourceId;
	
	public Contacts_adapter(Context context, int layoutResourceId, ArrayList<Contact> Contacts) {
		super(context, layoutResourceId,Contacts);
		this.Contacts = Contacts;
		this.context = context;
		this.layoutResourceId = layoutResourceId;	
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RecordHolder holder = null;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new RecordHolder();
			holder.name = (TextView)  row.findViewById(R.id.name_contact);
			holder.foto = (ImageView) row.findViewById(R.id.image_contact);
			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}				
		
		String name_item = Contacts.get(position).getName();	
		holder.name.setText(name_item);
		
		
		//convert byte to bitmap
		//byte[] outImage = Contacts.get(position).getFoto();
		
		
		//Bitmap theImage = BitmapFactory.decodeByteArray(outImage, 0 ,outImage.length);
		
		//ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
		//Bitmap theImage = BitmapFactory.decodeStream(imageStream);
		Bitmap bmp = Contacts.get(position).getFoto();
		holder.foto.setImageBitmap(bmp);
		
		
		
		
		LayerDrawable layers = (LayerDrawable)  this.context.getResources().getDrawable(R.drawable.shape);
		GradientDrawable shape = (GradientDrawable) (layers.findDrawableByLayerId(R.id.customShape));
			
		shape.setColor(Contacts.get(position).getCategory());
		
		
		row.setBackground(shape);
			
		return row;
	}
	public void removeElement(int pos){

	}

	public static class RecordHolder {
		TextView name;
		ImageView foto;
	}

}

