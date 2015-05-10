package com.ally.activities;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.ally.utilities.LoadDataTask;
import com.ally.utilities.Utilities;
import com.example.ally.R;

@SuppressLint("NewApi")
public class LoadActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Hide action bar
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	    getActionBar().hide();
	    //Set content view
		setContentView(R.layout.activity_load);
		try {
			//Get json into a string from file JSON.json
			String json = Utilities.jsonToStringFromAssetFolder("JSON.json", this);
			//Loading data in memory using asynctask 
			new LoadDataTask(this, LoadActivity.this).execute(json);
		} catch (IOException e) {
			//ToDo show message: json couldn't be loaded
		}	
	}
}
