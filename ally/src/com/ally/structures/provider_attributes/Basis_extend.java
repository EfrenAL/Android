package com.ally.structures.provider_attributes;

//Class that extend Basis 
public class Basis_extend extends Basis {
	//Member elements
	private String ios_itunes_url;
	private String ios_app_url;
	private String android_package_name;
	private String display_name;
	//Constants
	static final String ios_itunes_url_name = "ios_itunes_url";
	static final String ios_app_url_name = "ios_app_url";
	static final String android_package_name_name = "android_package_name";
	static final String display_name_name = "display_name";

	public Basis_extend(){}	
	
	public Basis_extend(String icon_url, String disc, String ios_itunes_url, String ios_app_url, String android_package_name, String display_name) {
		super(icon_url, disc);
		this.ios_itunes_url = ios_itunes_url;
		this.ios_app_url = ios_app_url;
		this.android_package_name = android_package_name;
		this.display_name = display_name;
	}
	public String getIosItunesUrl(){
		return ios_itunes_url;
	}
	public String getIosItunesUrlName(){
		return ios_itunes_url_name;
	}
	public void setIosItunesUrl(String ios_itunes_url){
		this.ios_itunes_url = ios_itunes_url;
	}
	public String getIosAppUrl(){
		return ios_app_url;
	}
	public String getIosAppUrlName(){
		return ios_app_url_name;
	}
	public void setIosAppUrl(String ios_app_url){
		this.ios_app_url = ios_app_url;
	}
	public String getAndroidPackageName(){
		return android_package_name;
	}
	public String getAndroidPackageNameName(){
		return android_package_name_name;
	}
	public void setAndroidPackageName(String android_package_name){
		this.android_package_name = android_package_name;
	}
	public String getDisplayName(){
		return display_name;
	}
	public String getDisplayNameName(){
		return display_name_name;
	}
	public void setDisplayName(String display_name){
		this.display_name = display_name;
	}
}