package com.ally.structures.routes;

import org.json.JSONObject;


import com.ally.interfaces.Parser;
import com.ally.utilities.Utilities;

public class Price implements Parser{
	//Attributes members
	private String currency;
	private double amount;
	//Constants
	static final String currency_name = "currency";
	static final String amount_name = "amount";
	
	//Constructor
	public Price(){}
	
	@Override
	public void setElements(JSONObject Object){
		//Set currency from JSONObject
		setCurrency(Utilities.getString(Object, currency_name));
		//Set amount from JSONObject
		setAmount (Utilities.getDouble(Object, amount_name));		
	}
	public String getCurrency(){
		return currency;
	}
	public double getAmount(){
		return amount;
	}
	public void setAmount(double amount){
		this.amount = amount/100;
	}
	public void setCurrency(String currency){
		this.currency = currency;
	}
	@Override
	public void setArray(JSONObject Object) {}	
}
