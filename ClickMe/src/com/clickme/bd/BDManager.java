package com.clickme.bd;

import java.util.ArrayList;

import com.clickme.contactos.Contact;
import com.clickme.utility.Utility;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

// Buen ejemplo para manejo de blob en sqlite
//http://geekonjava.blogspot.com.es/2013/09/insert-image-in-sqlite-and-display-in.html

public class BDManager extends SQLiteOpenHelper {

	private static String BdName = "ClickMe";
	private static String TableNameContacts = "Contacts";
	// Table columns
	private static String Name = "Name";
	private static String Surname = "Surname";
	private static String Category = "Category";
	private static String Foto = "Foto";
	private static String User = "User";
	private Context context;
	// Texts
	private static String TableNameTexts = "Texts";
	private static String Text = "Text";
	private static String Text_id = "Text_id";

	public BDManager(Context context) {
		super(context, BdName, null, 1);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Create Table Contacts
		db.execSQL("CREATE TABLE " + TableNameContacts + " ( " + this.Name + " TEXT," + this.Surname + " TEXT," + this.Category + " INT," + this.Foto + " blob not null," + this.User + " TEXT PRIMARY KEY)");

		db.execSQL("CREATE TABLE " + TableNameTexts + " ( " + this.Text + " TEXT," + this.Text_id + " INTEGER PRIMARY KEY)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Delete Table just for testing
		db.execSQL("DROP TABLE IF EXISTS '" + TableNameContacts + "'");
		db.execSQL("DROP TABLE IF EXISTS '" + TableNameTexts + "'");
		onCreate(db);
	}

	public void deleteContacts() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TableNameContacts, null, null);
		db.execSQL("DROP TABLE IF EXISTS '" + TableNameContacts + "'");
		onCreate(db);
	}

	public ArrayList<Contact> getContacts() {

		ArrayList<Contact> contacts = new ArrayList<Contact>();
		String selectQuery = "SELECT * FROM " + this.TableNameContacts;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Contact contact = new Contact();
				contact.setName(cursor.getString(0));
				contact.setSurname(cursor.getString(1));
				contact.setCategory(cursor.getInt(2));
				byte[] blob = cursor.getBlob(3);
				contact.setFoto(Utility.getPhoto(blob));
				contact.setUser(cursor.getString(4));
				contacts.add(contact);
			} while (cursor.moveToNext());
		}
		return contacts;
	}

	public void setContacts(ArrayList<Contact> contactos) {
		SQLiteDatabase db = getWritableDatabase();
		for (int i = 0; i < contactos.size(); i++) {
			db.execSQL("INSERT INTO " + this.TableNameContacts + " VALUES ('" + contactos.get(i).getName() + "','" + contactos.get(i).getSurname() + "','" + contactos.get(i).getCategory() + "','" + Utility.getBytes(contactos.get(i).getFoto()) + "','" + contactos.get(i).getUser() + "') ");
		}
		db.close();
	}

	public ArrayList<Contact> setContact(Contact contact) {
		// 1.- Sucesfully 2.-Error
		SQLiteDatabase db = getWritableDatabase();

		ContentValues cv = new ContentValues();
		cv.put(Name, contact.getName());
		cv.put(Surname, contact.getSurname());
		cv.put(Category, contact.getCategory());
		cv.put(Foto, Utility.getBytes(contact.getFoto()));
		cv.put(User, contact.getUser());
		db.insert(TableNameContacts, null, cv);

		return this.getContacts();
	}

	public void deleteTextsDB() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TableNameTexts, null, null);
		db.execSQL("DROP TABLE IF EXISTS '" + TableNameTexts + "'");
		onCreate(db);
	}

	public void deleteTexts() {
		int element = getTextSize();
		for (int i = 0; i < element; i++) {
			deleteText(i);
		}
	}

	public int getTextSize() {
		SQLiteDatabase db = getReadableDatabase();
		SQLiteStatement s = db.compileStatement("select count(*) from " + TableNameTexts + "; ");
		int count = (int) s.simpleQueryForLong();
		return count;
	}

	public boolean deleteText(int index) {
		SQLiteDatabase db = this.getWritableDatabase();
		return db.delete(TableNameTexts, Text_id + "=" + getTextId(index), null) > 0;
	}

	public int getTextId(int index) {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		String selectQuery = "SELECT * FROM " + this.TableNameTexts;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				ids.add(cursor.getInt(1));
			} while (cursor.moveToNext());
		}
		return ids.get(index);
	}

	public ArrayList<String> getTexts() {

		ArrayList<String> texts = new ArrayList<String>();
		String selectQuery = "SELECT * FROM " + this.TableNameTexts;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				texts.add(cursor.getString(0));
			} while (cursor.moveToNext());
		}
		return texts;
	}

	public void setTexts(ArrayList<String> texts) {
		SQLiteDatabase db = getWritableDatabase();
		for (int i = 0; i < texts.size(); i++) {
			// db.execSQL("INSERT INTO " + this.TableName + " VALUES ('"+
			// texts.get(i) + "') " );
			try {
				setText(texts.get(i));
			} catch (SQLiteException s) {
				onCreate(db);
				setText(texts.get(i));
			}

		}
		db.close();
	}

	/*public void update(ArrayList<String> texts) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues data = new ContentValues();
		for (int i = 0; i < texts.size(); i++) {
			data.put(this.Text, texts.get(i));			
			db.update(TableNameTexts, data, this.Text_id + "=" + getTextId(i), null);
		}
	}*/

	public int setText(String text) {
		// 1.- Sucesfully 2.-Error
		SQLiteDatabase db = getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(Text, text);
		db.insert(TableNameTexts, null, cv);

		return 1;
	}
	public void update(String text,int pos) {
		// 1.- Sucesfully 2.-Error
		SQLiteDatabase db = getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(Text, text);
		db.update(TableNameTexts, cv, this.Text_id + "=" + getTextId(pos), null);
	}
}
