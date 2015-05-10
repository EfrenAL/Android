package com.ally.structures.routes;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;

import com.ally.interfaces.Parser;
import com.ally.utilities.ExtendedRoute;
import com.ally.utilities.Utilities;
import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGBuilder;

public class Segments implements Parser {
	//Attributes members
	private String name;
	private int num_stops;
	private ArrayList<Stops> stops;
	private String travel_mode;
	private String description;
	private String color;
	private String icon_url;
	private String polyline;
	private Drawable icon;
	//Constants
	static final String name_name = "name";
	static final String num_stops_name = "num_stops";
	static final String travel_mode_name = "travel_mode";
	static final String description_name = "description";
	static final String color_name = "color";
	static final String icon_url_name = "icon_url";
	static final String polyline_name = "polyline";
	static final String stops_name = "stops";
	static final String format = "yyyy-MM-dd'T'HH:mm:yyzzz";
	static final String format_time = "HH:mm:yy";
	static final String format_hours = "HH:mm";
	
	public Segments() {
		stops = new ArrayList<Stops>();
	}

	@Override
	public void setElements(JSONObject Object) {
		//Set Name from JSON
		setName(Utilities.getString(Object, name_name));
		//Set Stops from JSON
		setNum_stops(Utilities.getInt(Object, num_stops_name));
		//Set Travel mode from JSON
		setTravel_mode(Utilities.getString(Object, travel_mode_name));
		//Set Description from JSON
		setDescription(Utilities.getString(Object, description_name));
		//Set Color from JSON
		setColor(Utilities.getString(Object, color_name));
		//Set Icon url from JSON
		setIcon_url(Utilities.getString(Object, icon_url_name));
		//Set Polyline from JSON
		setPolyline(Utilities.getString(Object, polyline_name));
		//Set all Stops from JSON
		setArray(Object);
		//Set Icon from JSON
		setIcon(getIcon_url());

	}

	@Override
	public void setArray(JSONObject Object) {
		// Get Stops array
		Stops stop;
		ArrayList<JSONObject> array = Utilities.getArray(Object, stops_name);
		for (int i = 0; i < array.size(); i++) {
			JSONObject obj = array.get(i);
			stop = new Stops();
			stop.setElements(obj);
			// Set stop into stops array
			stops.add(stop);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNum_stops() {
		return num_stops;
	}

	public void setNum_stops(int num_stops) {
		this.num_stops = num_stops;
	}

	public String getTravel_mode() {
		return travel_mode;
	}

	public void setTravel_mode(String travel_mode) {
		this.travel_mode = travel_mode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (description != null) {
			try {
				//Decode specials characters
				String result = URLDecoder.decode(description, "UTF-8");
				description = result;
			} catch (UnsupportedEncodingException e) {
				this.description =null;
			}
		}
		this.description = description;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getIcon_url() {
		return icon_url;
	}

	public void setIcon_url(String icon_url) {
		this.icon_url = icon_url;
	}

	public String getPolyline() {
		return polyline;
	}

	public void setPolyline(String polyline) {
		this.polyline = polyline;
	}

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	public int getStopsSize() {
		return stops.size();
	}

	public Stops getStop(int index) {
		if (index < stops.size()) {
			return stops.get(index);
		}
		return null;
	}

	public ArrayList<Stops> getAllStops() {
		return stops;
	}

	@SuppressLint("SimpleDateFormat")
	public ArrayList<ExtendedRoute> getAllStopsDuration(String color) {

		ArrayList<ExtendedRoute> extendedRoutes = new ArrayList<ExtendedRoute>();
		Date dateprevious = null;
		Date date;
		ExtendedRoute extendedR;
		SimpleDateFormat formatter;
		String hours;
		for (int i = 0; i < stops.size(); i++) {
			//Get date from stop i
			date = Utilities.getTime(stops.get(i).getDatetime());
			//Check if we are in the first element
			if (dateprevious == null) {
				//Save previos date
				dateprevious = date;
			} else {
				//Duration between two stops
				long diff = date.getTime() - dateprevious.getTime();
				//Set travel mode and name if exist
				String descrp = (travel_mode != null ? (travel_mode) : "") + (name != null ? (": " + name) : "");
				//Create Extended route
				extendedR = new ExtendedRoute(Utilities.milisToTimeString(diff), descrp , color);
				//Insert Extended route
				extendedRoutes.add(extendedR);
				dateprevious = date;
			}
			//Set date format
			formatter = new SimpleDateFormat(format);
			formatter.applyPattern(format_time);
			//Get hours in string
			hours = formatter.format(date.getTime());
			//Create new Extended route
			extendedR = new ExtendedRoute(hours, stops.get(i).getName(), color);
			//Insert extended route
			extendedRoutes.add(extendedR);
		}
		return extendedRoutes;
	}
	//Get Icon from url
	public void setIcon(String st_url) {
		URL url = null;
		HttpURLConnection urlConnection;
		InputStream inputStream;
		SVG svg;
		try {
			url = new URL(st_url);
			//Set http conection
			urlConnection = (HttpURLConnection) url.openConnection();
			//Get input stream from conection
			inputStream = urlConnection.getInputStream();
			//Build svg image
			svg = new SVGBuilder().readFromInputStream(inputStream).build();
			//Convert svg in drawable and save in icon
			setIcon(svg.getDrawable());
		} catch (IOException e) {
			icon = null;
		}
	}
	public String getStart(){
		return getTime(0);
	}
	public Date getStartDate(){
		return getTimeDate(0);
	}
	//Get time from first stop
	public String getEnd(){
		return getTime(stops.size()-1);
	}
	//Get time from last stop
	public Date getEndDate(){
		return getTimeDate(stops.size()-1);
	}
	//Get time from stop pos in string format
	@SuppressLint("SimpleDateFormat")
	public String getTime(int pos){
		Date date;
		SimpleDateFormat formatter;
		String hours;
		if(stops.size() > pos){
			date = Utilities.getTime(stops.get(pos).getDatetime());		
			formatter = new SimpleDateFormat(format);
			formatter.applyPattern(format_hours);
			hours = formatter.format(date.getTime());			
			return hours;
		}
		return null;
	}
	//Get time from stop pos in date format
	public Date getTimeDate(int pos){
		if(stops.size() > pos){			
			return Utilities.getTime(stops.get(pos).getDatetime());
		}
		return null;
	}

}
