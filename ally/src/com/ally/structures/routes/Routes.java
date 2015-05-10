package com.ally.structures.routes;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ally.interfaces.Parser;
import com.ally.utilities.ExtendedRoute;
import com.ally.utilities.Utilities;

public class Routes implements Parser {
	//Attributes members
	private String type;
	private String provider;
	private Properties_Routes properties;
	private Price price;
	private ArrayList<Segments> segments;
	//Constants
	static final String type_name = "type";
	static final String provider_name = "provider";
	static final String properties_name = "properties";
	static final String price_name = "price";
	static final String segments_name = "segments";
	static final String euro = "€";
	static final String dolar = "$";
	static final String euro_name = "EUR";
	static final String dolar_name = "USD";

	public Routes() {
		properties = new Properties_Routes();
		price = new Price();
		segments = new ArrayList<Segments>();
	}

	@Override
	public void setElements(JSONObject Object) {
		//Set type from JSON
		setType(Utilities.getString(Object, type_name));
		//Set provider from JSON
		setProvider(Utilities.getString(Object, provider_name));
		//Set Object properties from JSON
		properties.setElements(Utilities.getObject(Object, properties_name));
		//Set Object Price from JSON
		price.setElements(Utilities.getObject(Object, price_name));
		//Set segments array
		setArray(Object);

	}

	@Override
	public void setArray(JSONObject Object) {
		// Get Segment array
		JSONArray objarr;
		Segments segment;
		try {
			//Get all Segments
			objarr = Object.getJSONArray(segments_name);
			for (int i = 0; i < objarr.length(); i++) {
				//Get segment i
				JSONObject obj = objarr.getJSONObject(i);
				segment = new Segments();
				segment.setElements(obj);
				// Set segment into routes array segments
				segments.add(segment);
			}
		} catch (JSONException e) {
			//ToDO: Show message: Error loading segments
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public ArrayList<Segments> getSegments() {
		return segments;
	}
	//Get Segment in index
	public Segments getSegment(int index){
		if (index < segments.size()) {
			return segments.get(index);
		}
		return null;
	}
	public int getSegmentsSize(){
		return segments.size();
	}

	public double getPrice() {
		return price.getAmount();
	}
	//Return currency simbol
	public String getCurrency() {
		String value = euro;
		String curr = price.getCurrency();
		if(curr != null){
			switch (curr) {
			case euro_name:
				value = euro;
				break;
			case dolar_name:
				value = euro;
				break;
			default:				
				break;
			}
		}
		
		return value;
	}
	//Get all stops from current route
	public ArrayList<Stops> getAllStops() {
		ArrayList<Stops> allStops = new ArrayList<Stops>();
		for (int i = 0; i < segments.size(); i++) {
			Segments segment = segments.get(i);
			allStops.addAll(segment.getAllStops());
		}
		return allStops;
	}
	//Get all stops with datetime formated and duration between stops
	public ArrayList<ExtendedRoute> getAllStopsDuration(){
		
		ArrayList<ExtendedRoute> allStops = new ArrayList<ExtendedRoute>();
		
		for (int i = 0; i < segments.size(); i++) {
			Segments segment = segments.get(i);
			allStops.addAll( segment.getAllStopsDuration(segments.get(i).getColor()));
		}
		return allStops;
	}
	//Get time start
	public String getStart(){
		String start = null;
		if (segments.size() > 0 ) {
			start = segments.get(0).getStart();
		}
		return start;
	}
	//Get time finish
	public String getEnd(){
		String end = null;
		if (segments.size() > 0 ) {
			end = segments.get(segments.size()-1).getEnd();
		}
		return end;
	}
	public String getDuration(){
		if (segments.size() > 0) {
			Date startd = segments.get(0).getStartDate();
			Date endd =   segments.get(segments.size()-1).getEndDate();
			String duration = Utilities.milisToTimeString( endd.getTime() - startd.getTime() );
			return duration;
		}
		return null;
	}
	
}
