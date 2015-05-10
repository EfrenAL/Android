package com.ally.structures.provider_attributes;

import org.json.JSONObject;

import com.ally.interfaces.Parser;
import com.ally.utilities.Utilities;

public class Car2go extends Basis_extend implements Parser {
	
	public Car2go(){};
	public Car2go(String icon_url, String disc, String ios_itunes_url, String ios_app_url, String android_package_name, String display_name) {
		super(icon_url, disc, ios_itunes_url, ios_app_url, android_package_name, display_name);
	}

	@Override
	public void setElements(JSONObject Object) {
		//Set Icon from Json
		this.setIcon(Utilities.getString(Object, this.getIconName()) );
		//Set Disclaimer from Json	
		this.setDisclaimer( Utilities.getString(Object, this.getDisclaimerName()));		
		//Set IosItunesUrl from Json
		this.setIosItunesUrl(Utilities.getString(Object, this.getIosItunesUrlName()) );
		//Set IosAppUrl from Json
		this.setIosAppUrl(Utilities.getString(Object, this.getIosAppUrlName()));	
		//Set AndroidPackageName from Json
		this.setAndroidPackageName(Utilities.getString(Object, this.getAndroidPackageNameName()) );
		//Set DisplayName from Json
		this.setDisplayName(Utilities.getString(Object, this.getDisplayNameName()));	
	}

	@Override
	public void setArray(JSONObject Object) {}

}
