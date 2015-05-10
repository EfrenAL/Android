package com.ally.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;

import com.ally.activities.ExtendedRouteActivity;
import com.ally.activities.MapActivity;
import com.example.ally.R;

public class Utilities {
	// Get string from JSONObject. Return null if doesn't exist 
	public static String getString(JSONObject Object, String name_value) {
		String value = null;
		try {
			value = Object.getString(name_value);
			if (value == "null") {
				value = null;
			}
		} catch (JSONException e) {
		}
		return value;
	}

	// Get int from JSONObject.  Return -1 if doesn't exist
	public static int getInt(JSONObject Object, String name_value) {
		int value;
		try {
			value = Object.getInt(name_value);
		} catch (JSONException e) {
			value = -1;
		}
		return value;
	}

	// Get int from JSONObject.  Return -1 if doesn't exist
	public static double getDouble(JSONObject Object, String name_value) {
		double value;
		try {
			value = Object.getDouble(name_value);
		} catch (JSONException e) {
			value = -1;
		}
		return value;
	}

	// Get array from JSONObject.
	public static ArrayList<JSONObject> getArray(JSONObject Object, String name_value) {
		ArrayList<JSONObject> array = new ArrayList<JSONObject>();

		JSONArray objarr;
		try {
			objarr = Object.getJSONArray(name_value);
			for (int i = 0; i < objarr.length(); i++) {
				JSONObject obj = objarr.getJSONObject(i);
				array.add(obj);
			}
		} catch (JSONException e) {
		}

		return array;
	}

	// Get Json Object
	public static JSONObject getObject(JSONObject Object, String name) {
		// Get properties object from json
		JSONObject jObjProp = new JSONObject();
		try {
			jObjProp = Object.getJSONObject(name);
		} catch (JSONException e) {
		}
		return jObjProp;
	}

	// Get json from file in assets
	public static String jsonToStringFromAssetFolder(String fileName, Context context) throws IOException {
		AssetManager manager = context.getAssets();
		InputStream file = manager.open(fileName);

		byte[] data = new byte[file.available()];
		file.read(data);
		file.close();
		return new String(data);
	}
	//Got to activity map. Send selected route in position
	public static void goToMapActivity(int position, Context context) {
		Intent intent = new Intent(context, MapActivity.class);
		// Send selected route
		intent.putExtra(context.getResources().getString(R.string.activity_parameter), position);
		// Flag needed for call activity from outside of an Activitys
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	//Go to selected (position) extended routes activity 
	public static void goToExtRoutActivity(int position, Context context) {
		Intent intent = new Intent(context, ExtendedRouteActivity.class);
		// Send selected route
		intent.putExtra(context.getResources().getString(R.string.activity_parameter), position);
		// Flag needed for call activity from outside of an Activitys
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	//Get date time in format Date from String
	@SuppressLint("SimpleDateFormat")
	public static Date getTime(String datetime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:yyzzz");
		Date parseDate = null;
		try {
			parseDate = formatter.parse(datetime);
		} catch (ParseException e) {}
		return parseDate;
	}

	//Convert long milisecond to format: X h X mins X sec   
	public static String milisToTimeString(long diff) {
		int timeInSeconds = (int) (diff / 1000);
		int hours, minutes, seconds;
		String diffTime;
		hours = timeInSeconds / 3600;
		timeInSeconds = timeInSeconds - (hours * 3600);
		minutes = timeInSeconds / 60;
		timeInSeconds = timeInSeconds - (minutes * 60);
		seconds = timeInSeconds;
		diffTime = (hours > 0 ? (hours + " h ") : " ") + (minutes > 0 ? (minutes + " mins ") : " ") + (seconds > 10 ? (seconds + " sec") : " ");
		return diffTime;
	}

}
