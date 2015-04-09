package com.clickme.messages;

import java.util.ArrayList;

import android.content.Context;

//Clase obsoleta creo que no la necesitamos
public class Messages {
	private ArrayList<Message> messages;
	private Context context;
	private String user;
	private Messages_bd msg_bd;
	
	public Messages(Context context, String user){
		this.context = context;
		this.user = user;
		msg_bd = new Messages_bd(context);		
	}
	public void getMessages(){
		messages = msg_bd.getMessages(user);		
	}
	
	public ArrayList<String> getTexts(){
		ArrayList<String> msg_text = new ArrayList();
		for (int i = 0; i < messages.size(); i++) {
			msg_text.add(messages.get(i).getMessage());
		}
		return msg_text;		
	}
	
}
