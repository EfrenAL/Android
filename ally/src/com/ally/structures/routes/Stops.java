package com.ally.structures.routes;

import org.json.JSONObject;

import android.net.Uri;

import com.ally.interfaces.Parser;
import com.ally.utilities.Utilities;

public class Stops implements Parser {
	//Attributes
	private double lat;
	private double lng;
	private String datetime;
	private String name;
	private String properties;
	//Constants
	static final String lat_name = "lat";
	static final String lng_name = "lng";
	static final String datetime_name = "datetime";
	static final String name_name = "name";
	static final String properties_name = "properties";

	public Stops() {
	}

	public Stops(String datetime, String name) {
		this.datetime = datetime;
		this.name = name;
	}

	@Override
	public void setElements(JSONObject Object) {
		//Set Lat from JSON
		setLat(Utilities.getDouble(Object, lat_name));
		//Set Lng from JSON
		setLng(Utilities.getDouble(Object, lng_name));
		//Set Datetime from JSON
		setDatetime(Utilities.getString(Object, datetime_name));
		//Set Name from JSON
		setName(Utilities.getString(Object, name_name));
		//Set properties from JSON
		setProperties(Utilities.getString(Object, properties_name));
	}

	@Override
	public void setArray(JSONObject Object) {
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name != null) {
			String result = Uri.decode(name);
			name = result;
		}
		this.name = name;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

}
