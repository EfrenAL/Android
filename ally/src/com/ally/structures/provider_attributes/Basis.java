package com.ally.structures.provider_attributes;

//Class with basic elements
public class Basis {
	private String provider_icon_url;
	private String disclaimer;
	
	static final String provider_icon_url_name = "provider_icon_url";
	static final String disclaimer_name = "disclaimer";
	
	public Basis(){}
	
	public Basis(String icon_url, String disc){
		provider_icon_url = icon_url;
		disclaimer = disc;
	}
	public String getIcon(){
		return provider_icon_url;
	}
	public String getIconName(){
		return provider_icon_url_name;
	}
	public String getDisclaimer(){
		return disclaimer;
	}
	public String getDisclaimerName(){
		return disclaimer_name;
	}
	public void setIcon(String icon){
		provider_icon_url = icon;
	}
	public void setDisclaimer(String disc){
		disclaimer = disc;
	}
}
