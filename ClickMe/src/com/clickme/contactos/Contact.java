package com.clickme.contactos;

import com.clickme.utility.Utility;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable {

	private String name;
	private String surname;
	private int category;
	private Bitmap foto;
	private String user;
	
	public Contact(){		
	}	
	public Contact(String name, String surname, int category, Bitmap foto, String user) {
		this.name = name;
		this.surname = surname;
		this.category = category;
		this.foto = foto;
		this.user = user;
	}
	public Contact(Parcel in) {
        readFromParcel(in);
    }
	
	public void setName(String name){
		this.name = name;
	}
	public void setSurname(String surname){
		this.surname = surname;
	}
	public void setCategory(int category){
		this.category = category;
	}
	public void setFoto(Bitmap foto){
		this.foto = foto;
	}
	public void setUser(String user){
		this.user = user;
	}
	public String getName(){
		return this.name;
	}
	public String getSurname(){
		return this.surname;
	}
	public int getCategory(){
		return this.category;
	}
	public Bitmap getFoto(){
		return this.foto;
	}
	public String getUser(){
		return this.user;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
	    dest.writeString(surname);
	    dest.writeInt(category);	    
	    dest.writeString(user);
	    dest.writeInt(Utility.getBytes(foto).length);
	    dest.writeByteArray(Utility.getBytes(foto));
	    
	    
	}
	private void readFromParcel(Parcel in) {	  
	    name = in.readString();
	    surname = in.readString();
	    category = in.readInt();
	    user = in.readString();
	    int foto_size = in.readInt();
	    byte[] foto_b = new byte[foto_size];
	    in.readByteArray(foto_b);
	    foto = Utility.getPhoto(foto_b);
	}
	public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }
 
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
	
}
