package com.clickme;

import java.util.ArrayList;

import com.clickme.bd.BDManager;
import com.clickme.contactos.Contact;
import com.clickme.contactos.Contacts;
import com.clickme.contactos.Contacts_adapter;
import com.clickme.server.Server;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.os.Build;

public class ContactsActivity extends Activity {

	private Contacts contacts;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contactos);

		//Contacts_bd cbd = new Contacts_bd(this);
		//cbd.deleteContacts();
		
		contacts = new Contacts(this);
		
				
		Contacts_adapter contacts_adapter = new Contacts_adapter(this,R.layout.contact_item,contacts.getContacts());
		
		GridView gridview = (GridView) findViewById(R.id.contact_grid);
	    gridview.setAdapter(contacts_adapter);
		// Establecemos la conexion llamando a una asynctask para q no pete
	    // Comentamos esto para no tener q ejecutar el server cada vez!!
	    //new callServer().execute("");
	    
	    gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Contact contact = contacts.getContact(position);
				Intent lv_nIntent;
				lv_nIntent = new Intent(getBaseContext(), ConversationActivity.class);
				lv_nIntent.putExtra("contact", contact);
				lv_nIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(lv_nIntent);
				finish();
			}
	    });
        
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contactos, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_newContact) {
			Intent lv_nIntent;
			lv_nIntent = new Intent(getBaseContext(), NewContactActivity.class);
			//Mirar estas banderas para que son !!!!!
			lv_nIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(lv_nIntent);
			finish();
			return true;
		}else if(id == R.id.action_delete){
			BDManager contacts_bd = new BDManager(this);
			contacts_bd.deleteContacts();
		}else if(id == R.id.action_prueba){
			Intent lv_nIntent;
			lv_nIntent = new Intent(getBaseContext(), ConversationActivity.class);
			//Mirar estas banderas para que son !!!!!
			lv_nIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(lv_nIntent);
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
	private class callServer extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
    	    Server server;
            server = Server.getServer();
            server.trySetConnection();
            return "Executed";
        }
    }
}
