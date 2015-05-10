package com.ally.structures.routes;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.ally.interfaces.Parser;
import com.ally.utilities.Utilities;

public class Properties_Routes implements Parser {

	//Attributes members
	private String address;
	private String model;
	private String license_plate;
	private Integer fuel_level;
	private String engine_type;
	private String internal_cleanliness;
	private String description;
	private Integer seats;
	private Integer doors;
	private String id;
	private String available_bikes;
	private ArrayList<Companies> companies;
	//Constants
	static final String address_name = "address";
	static final String model_name = "model";
	static final String license_plate_name = "license_plate";
	static final String fuel_level_name = "fuel_level";
	static final String engine_type_name = "engine_type";
	static final String internal_cleanliness_name = "internal_cleanliness";
	static final String description_name = "description";
	static final String seats_name = "seats";
	static final String doors_name = "doors";
	static final String id_name = "id";
	static final String available_bikes_name = "available_bikes";
	static final String companie_name = "companies";

	public Properties_Routes() {
		companies = new ArrayList<Companies>();
	}

	@Override
	public void setElements(JSONObject Object) {
		//Set Address from JSON
		setAddress(Utilities.getString(Object, address_name));
		//Set Model from JSON
		setModel(Utilities.getString(Object, model_name));
		//Set License plate from JSON
		setLicense_plate(Utilities.getString(Object, license_plate_name));
		//Set Fuel level from JSON
		setFuel_level(Utilities.getInt(Object, fuel_level_name));
		//Set Engine type from JSON
		setEngine_type(Utilities.getString(Object, engine_type_name));
		//Set Internal clean liness from JSON
		setInternal_cleanliness(Utilities.getString(Object, internal_cleanliness_name));
		//Set Description from JSON
		setDescription(Utilities.getString(Object, description_name));
		//Set Seats from JSON
		setSeats(Utilities.getInt(Object, seats_name));
		//Set id name from JSON
		setId(Utilities.getString(Object, id_name));
		//Set Available bikes from JSON
		setAvailable_bikes(Utilities.getString(Object, available_bikes_name));
		//Set Doors from JSON
		setDoors(Utilities.getInt(Object, doors_name));
		//Set all companies 
		setArray(Object);

	}

	@Override
	public void setArray(JSONObject Object) {
		JSONArray objarr;
		Companies company;
		try {
			//Get all companies
			objarr = Object.getJSONArray(companie_name);
			for (int i = 0; i < objarr.length(); i++) {
				//Get companie i
				JSONObject obj = objarr.getJSONObject(i);
				company = new Companies();
				company.setElements(obj);
				//Set company into companies array
				companies.add(company);
			}
		} catch (JSONException e) {}
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getLicense_plate() {
		return license_plate;
	}

	public void setLicense_plate(String license_plate) {
		this.license_plate = license_plate;
	}

	public Integer getFuel_level() {
		return fuel_level;
	}

	public void setFuel_level(Integer fuel_level) {
		this.fuel_level = fuel_level;
	}

	public String getEngine_type() {
		return engine_type;
	}

	public void setEngine_type(String engine_type) {
		this.engine_type = engine_type;
	}

	public String getInternal_cleanliness() {
		return internal_cleanliness;
	}

	public void setInternal_cleanliness(String internal_cleanliness) {
		this.internal_cleanliness = internal_cleanliness;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSeats() {
		return seats;
	}

	public void setSeats(Integer seats) {
		this.seats = seats;
	}

	public Integer getDoors() {
		return doors;
	}

	public void setDoors(Integer doors) {
		this.doors = doors;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAvailable_bikes() {
		return available_bikes;
	}

	public void setAvailable_bikes(String available_bikes) {
		this.available_bikes = available_bikes;
	}

}
