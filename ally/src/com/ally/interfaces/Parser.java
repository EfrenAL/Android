package com.ally.interfaces;

import org.json.JSONObject;
/**
 * Json loader interface 
 * @author Efren
 *
 */
public interface Parser {
	void setElements(JSONObject Object);
	void setArray (JSONObject Object);
}
