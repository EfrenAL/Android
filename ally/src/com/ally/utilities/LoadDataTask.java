package com.ally.utilities;

import java.io.IOException;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.ally.activities.MainActivity;
import com.ally.data.Data;

//Class for load data from json ussing AsyncTask
public class LoadDataTask extends AsyncTask<String, String, String> {

	private Context context;
	private Activity activity;

	public LoadDataTask(Context cont, Activity actv) {
		super();
		context = cont;
		activity = actv;
	}

	@Override
	protected String doInBackground(String... params) {
		String result = "1";
		Data data = Data.getInstance();
		//Delete data
		data.clear();
		data = Data.getInstance();
		try {
			data.load(params[0]);
		} catch (IOException | JSONException e) {
			return null;
		}
		return result;
	}

	@Override
	protected void onPostExecute(String result) {

		if (result == null) {
			return;
		}
		// If the data were loaded start activity MainActivity
		Intent intent = new Intent(context, MainActivity.class);
		// Flag needed for call activity from outside of an Activitys
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
		activity.finish();
	}

}
