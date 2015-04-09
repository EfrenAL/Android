package com.clickme.messages;


import java.sql.Timestamp;
import java.util.Calendar;
import android.graphics.Bitmap;
import android.os.Parcel;

public class Message {
		
	private String User;
	private Timestamp TimeStamp;
	private String Source;
	private String Message;
	
	public Message(){		
	}
	public Message(String user, Timestamp timestamp, String source, String message) {
		this.User = user;
		this.TimeStamp = timestamp;
		this.Source = source;
		this.Message = message;
	}	
	public void setUser(String user){
		this.User = user;
	}
	public void setTimeStamp(Timestamp timestamp2){
		this.TimeStamp = timestamp2;
	}
	public void setSource(String source){
		this.Source = source;
	}
	public void setMessage(String message){
		this.Message = message;
	}	
	public String getUser(){
		return this.User;
	}
	public Timestamp getTimeStamp(){
		return this.TimeStamp;
	}
	public String getSource(){
		return this.Source;
	}
	public String getMessage(){
		return this.Message;
	}	
}
