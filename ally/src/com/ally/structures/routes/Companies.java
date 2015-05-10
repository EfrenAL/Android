package com.ally.structures.routes;

import org.json.JSONObject;


import com.ally.interfaces.Parser;
import com.ally.utilities.Utilities;

public class Companies implements Parser {
	//Attributes members
	private String name;
	private String phone;
	// Constants
	static final String name_name = "name";
	static final String phone_name = "phone";
	@Override
	public void setElements(JSONObject Object) {
		//Set name from JSONObject
		name = Utilities.getString(Object, name_name);
		//Set phone from JSONObject
		phone = Utilities.getString(Object, phone_name);

	}
	public String getName(){
		return name;
	}
	public String getPhone(){
		return phone;
	}
	@Override
	public void setArray(JSONObject Object) {}	

}
