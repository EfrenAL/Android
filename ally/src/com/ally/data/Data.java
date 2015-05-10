package com.ally.data;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ally.interfaces.Parser;
import com.ally.structures.provider_attributes.Provider_attributes;
import com.ally.structures.routes.Routes;
import com.ally.utilities.Utilities;

//Singleton class for managing resources
public class Data implements Parser {

	private static Data data;
	private ArrayList<Routes> routes;
	private Provider_attributes provider_attributes;
	// Constants
	static final String routes_name = "routes";
	static final String provider_attributes_name = "provider_attributes";

	// Private constructor for singleton
	private Data() {
		routes = new ArrayList<Routes>();
		provider_attributes = new Provider_attributes();
	}

	public static Data getInstance() {
		if (data == null) {
			data = new Data();
		}
		return data;
	}

	/**
	 * Public method for loading resources
	 * 
	 * @param st_json
	 *            : Json in string format
	 */
	public void load(String st_json) throws IOException, JSONException {
		JSONObject jObj = new JSONObject(st_json);
		setElements(jObj);
	}

	@Override
	public void setElements(JSONObject Object) {
		// Set provider attributes from JSON
		provider_attributes.setElements(Utilities.getObject(Object, provider_attributes_name));
		// Set routes array
		setArray(Object);
	}

	@Override
	public void setArray(JSONObject Object) {
		JSONArray objarr;
		JSONObject obj;
		Routes route;
		try {
			// Get all routes into array from JSON
			objarr = Object.getJSONArray(routes_name);
			for (int i = 0; i < objarr.length(); i++) {
				// Get route i
				obj = objarr.getJSONObject(i);
				route = new Routes();
				// Set route elements
				route.setElements(obj);
				// Set route into routes array
				routes.add(route);
			}
		} catch (JSONException e) {
			// ToDo show message: Error loading data!
		}
	}

	public ArrayList<Routes> getRoutes() {
		return routes;
	}

	// Get route index i
	public Routes getRoute(int i) {
		if (i < routes.size()) {
			return routes.get(i);
		}
		return null;
	}
	//Clear all data
	public void clear(){
		routes = null;
		provider_attributes = null;
		data = null;
		data = new Data();
	}

}
