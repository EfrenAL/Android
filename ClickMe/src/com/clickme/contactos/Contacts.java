package com.clickme.contactos;

import java.util.ArrayList;

import com.clickme.bd.BDManager;

import android.content.Context;

public class Contacts {
	
	private ArrayList<Contact> contacts;
	private BDManager contacts_bd;
	private Context conext;
	
	public Contacts(Context context){
		this.conext = context;
		contacts_bd = new BDManager(context);
		contacts = contacts_bd.getContacts();
	}
	
	public ArrayList<Contact> getContacts(){
		return contacts;
	}
	public int setContact(Contact contact){		
		// 1.- Sucesfully  2.-Error
		//Hay que ver el control de errores
		contacts = contacts_bd.setContact(contact);
		return 1;
	}
	public Contact getContact(int pos){
		return contacts.get(pos);
	}
}
