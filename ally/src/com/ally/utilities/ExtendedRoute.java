package com.ally.utilities;

//Class used for activity ExtendedRoute
public class ExtendedRoute {
	//Attributes
	private String datetime;
	private String description;
	private String color;
	
	public ExtendedRoute(String datetime, String description, String color){
		this.setDatetime(datetime);
		this.setDescription(description);
		this.setColor(color);	
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
}
