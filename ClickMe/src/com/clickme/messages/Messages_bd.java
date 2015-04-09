package com.clickme.messages;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.clickme.utility.Utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Messages_bd extends SQLiteOpenHelper{
	

	private static String BdName = "ClickMe";
	private static String TableName = "Messages";
	private static String TableName_foreign = "Contacts";
	// Table columns
	private static String ID = "Id";
	private static String User = "User";
	private static String TimeStamp = "TimeStamp";
	private static String Source = "Source";
	private static String Message = "Message";
	
	
	public Messages_bd(Context context) {
		super(context, BdName, null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TableName + " ( " + this.ID + " integer primary key autoincrement not null," 
				   + this.User + " TEXT," 
				   + this.TimeStamp + " String," 
				   + this.Source + " TEXT," 
				   + " FOREIGN KEY ("+ User +") REFERENCES "+ TableName_foreign + " (" + User + "));");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS '" + TableName + "'");
		onCreate(db);		
	}
	public void deleteMessages(){
		SQLiteDatabase db = this.getWritableDatabase();
		
		db.delete(TableName, null, null);
		try {
			db.execSQL("DROP TABLE IF EXISTS '" + TableName + "'");
		} catch (Exception e) {
			SQLiteDatabase db_Read = null;
			db_Read = this.getReadableDatabase(); 
			db_Read.close();
			onCreate(db);
		}
		
	}
	
	public ArrayList<Message> getMessages(){
        
        ArrayList<Message> messages = new ArrayList<Message>();
        String selectQuery = "SELECT * FROM " + this.TableName;        
        
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Message message = new Message();
                message.setUser(cursor.getString(1));
                message.setTimeStamp(Timestamp.valueOf(cursor.getString(2)));
                message.setSource(cursor.getString(3));
                message.setMessage(cursor.getString(4));
                
                messages.add(message);
            } while (cursor.moveToNext());
        }     
        return messages;                       
	}
	
	public ArrayList<Message> getMessages(String user){
        
        ArrayList<Message> messages = new ArrayList<Message>();
        String selectQuery = "SELECT * FROM " + this.TableName + " WHERE User = "+ user;        
        
        SQLiteDatabase db = this.getWritableDatabase();
        
        if (selectQuery != null) {
            Cursor cursor = db.rawQuery(selectQuery, null);
            
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Message message = new Message();
                    message.setUser(cursor.getString(1));
                    message.setTimeStamp(Timestamp.valueOf(cursor.getString(2)));
                    message.setSource(cursor.getString(3));
                    message.setMessage(cursor.getString(4));
                    
                    messages.add(message);
                } while (cursor.moveToNext());
            }			
		}
        return messages;                       
	}
	
	
	
	
	public ArrayList<Message> setMessage(Message message){
		//1.- Sucesfully 2.-Error
		SQLiteDatabase db = getWritableDatabase();
	
		ContentValues cv = new ContentValues();
		cv.put(User, message.getUser());
		cv.put(TimeStamp, message.getTimeStamp().toString());
		cv.put(Source, message.getSource());
		cv.put(Message, message.getMessage());
		db.insert(TableName, null, cv);
	
		
		return this.getMessages();
	}
	

	
}
