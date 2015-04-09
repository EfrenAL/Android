package com.clickme;

import java.util.ArrayList;

import com.clickme.colors.Colors_adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.AdapterView.OnItemClickListener;

public class ColorsActivity extends Activity{
	
	
	private int color_selected;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_colors);
		
		Intent mIntent = getIntent();
		color_selected = mIntent.getIntExtra("color_selected", 0);
		
		
		int[] colors = this.getResources().getIntArray(R.array.androidcolors);
		
		ArrayList<String> al_colors = new ArrayList();
		for (int i = 0; i < colors.length; i++) {			
			String hexColor = String.format("#%06X", (0xFFFFFF & colors[i]));			
			al_colors.add(hexColor);
		}
				
		Colors_adapter colors_adapter = new Colors_adapter(this,R.layout.color_item,al_colors,color_selected);
		
		GridView gridview = (GridView) findViewById(R.id.colors_grid);
	    gridview.setAdapter(colors_adapter);
	    
	    
	    gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
				ImageButton b_circle = (ImageButton)view.findViewById(R.id.b_circle);
				b_circle.setVisibility(View.VISIBLE);
				goBack(arg2);
//				view.setSelected(true);

			}
	    });	   

	}
	
	public void goBack(int col_sel){
		this.color_selected = col_sel;
		
		Intent data = new Intent();
		data.putExtra("color_selected", color_selected );
		
		if (getParent() == null) {
		    setResult(Activity.RESULT_OK, data);
		} else {
		    getParent().setResult(Activity.RESULT_OK, data);
		}
		finish();
		
		
	}
	
}
