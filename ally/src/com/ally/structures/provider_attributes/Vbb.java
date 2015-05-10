package com.ally.structures.provider_attributes;

import org.json.JSONObject;

import com.ally.interfaces.Parser;
import com.ally.utilities.Utilities;

public class Vbb extends Basis implements Parser{
	
	public Vbb(){}
	
	public Vbb(String icon_url, String disc) {
		super(icon_url, disc);
	}

	@Override
	public void setElements(JSONObject Object) {
		//Set Icon from Json
		this.setIcon(Utilities.getString(Object, this.getIconName()) );
		//Set Disclaimer from Json
		this.setDisclaimer( Utilities.getString(Object, this.getDisclaimerName()));	
	}

	@Override
	public void setArray(JSONObject Object) {}

}
