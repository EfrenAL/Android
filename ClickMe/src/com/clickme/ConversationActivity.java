package com.clickme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jivesoftware.smack.packet.Message;

import com.clickme.bd.BDManager;
import com.clickme.contactos.Contact;
import com.clickme.contactos.Contacts_adapter;
import com.clickme.conversation.Conversation_adapter;
import com.clickme.messages.Messages;
import com.clickme.messages.Messages_bd;
import com.clickme.server.Server;
import com.clickme.texts.Texts_adapter;

import android.R.color;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class ConversationActivity extends Activity {
	private Server server;
	private ListView lv_conversation;
	private ArrayList<String> messages = new ArrayList();
	private Contact contact;
	private IntentFilter myFilter;
	private int pos_selected = -1;
	private ListView navList;
	private Menu MenuOptions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conversation);
		// Instanciamos el filtro para que podamos recivir los nuevos mensajes
		myFilter = new IntentFilter();
		myFilter.addAction("RECEIVED_MESSAGE");
		// Leemos el contacto que nos envia la actividad contactos
		contact = getIntent().getParcelableExtra("contact");
		// Obtenemos referencia del servidor
		server = Server.getServer();
		server.UpgradeContext(this);
		// Obtenemos los mensajes del usuario
		Messages all_messages = new Messages(this, contact.getUser());
		// all_messages.getMessages();
		// messages = all_messages.getTexts();

		// Load an array of options names
		final String[] names = getResources().getStringArray(R.array.nav_options);
		this.navList = (ListView) findViewById(R.id.left_drawer);
		// Set previous array as adapter of the list
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
		navList.setAdapter(adapter);

		lv_conversation = (ListView) findViewById(R.id.lv_conversation);
		setListAdapter();

		// Prueba para ver como se insertan los mensajes
		//messages.add("Yo" + ":" + "Hola Sara");
		//messages.add("Sara" + ":" + "Hola");

		final BDManager contact_bd = new BDManager(this);
		// contact_bd.setTexts(texts);
		// texts = contact_bd.getTexts();

		final ListView listv = (ListView) findViewById(R.id.left_drawer);
		setGridAdapter(contact_bd.getTexts(), listv);

		listv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View v, int position, long id) {
				ArrayList<String> texts = new ArrayList();
				texts = contact_bd.getTexts();
				AlphaAnimation buck = new AlphaAnimation(0.0F, 1.0F);
				buck.setDuration(1000);
				TextView tv = (TextView) v.findViewById(R.id.textGV);
				v.startAnimation(buck);
				if (pos_selected == -1) {
					tv.setText("Enviar");
					tv.setTextColor(Color.WHITE);
					v.setBackgroundColor(getResources().getColor(R.color.red));
					pos_selected = position;
				} else if (position == pos_selected) {
					tv.setText(texts.get(position));
					tv.setTextColor(getResources().getColor(R.color.azul_oscuro));
					v.setBackgroundColor(getResources().getColor(R.color.azul_claro));
					sendMessage(texts.get(position));
					pos_selected = -1;
				} else {
					// Set last selected with normal colors
					View v2 = getViewByPosition(pos_selected, listv);
					v2.setBackgroundColor(getResources().getColor(R.color.azul_claro));
					TextView tv2 = (TextView) v2.findViewById(R.id.textGV);
					tv2.setText(texts.get(pos_selected));
					tv2.setTextColor(getResources().getColor(R.color.azul_oscuro));
					// Set new with send color
					tv.setText("Enviar");
					tv.setTextColor(Color.WHITE);
					v.setBackgroundColor(getResources().getColor(R.color.red));
					pos_selected = position;
				}

			}
		});

		/*
		 * Asi se envian los mensajes Button b_send = (Button)
		 * findViewById(R.id.b_send); b_send.setOnClickListener(new
		 * View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) {
		 * 
		 * Message msg = new Message("prueba@Efren", Message.Type.chat);
		 * msg.setBody("Click"); server.sendPacket(msg);
		 * messages.add(server.getUser() + ":"); messages.add("Click");
		 * setListAdapter(); } });
		 */
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawer, R.drawable.ic_launcher, R.string.hello_world, R.string.app_name) {

			/** Called when a drawer has settled in a completely closed state. */
			@SuppressLint("NewApi")
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getActionBar().setTitle("ClickMe");
				getActionBar().setSubtitle(null);
				MenuItem item = MenuOptions.findItem(R.id.action_add_text);
				item.setVisible(false);
			}

			/** Called when a drawer has settled in a completely open state. */
			@SuppressLint("NewApi")
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle("ClickMe");
				getActionBar().setSubtitle("Messages");

				MenuItem item = MenuOptions.findItem(R.id.action_add_text);
				item.setVisible(true);

			}
		};

		// Set the drawer toggle as the DrawerListener
		drawer.setDrawerListener(mDrawerToggle);
	}

	@Override
	public void onBackPressed() {
		Intent lv_nIntent;
		lv_nIntent = new Intent(getBaseContext(), ContactsActivity.class);
		lv_nIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(lv_nIntent);
		finish();
	}

	@Override
	protected void onPause() {
		unregisterReceiver(myReceiver);
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(myReceiver, myFilter);
	}

	public void sendMessage(String text) {
		String userContact;
		userContact = contact.getUser() + "@" + server.getService() ;
		Message msg = new Message(userContact, Message.Type.chat);
		msg.setBody(text);
		server.sendPacket(msg);
		// messages.add(server.getUser() + ":");
		// messages.add("Click");
		setListAdapter();
	}

	public View getViewByPosition(int pos, ListView listView) {
		final int firstListItemPosition = listView.getFirstVisiblePosition();
		final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

		if (pos < firstListItemPosition || pos > lastListItemPosition) {
			return listView.getAdapter().getView(pos, null, listView);
		} else {
			final int childIndex = pos - firstListItemPosition;
			return listView.getChildAt(childIndex);
		}
	}

	public void setListAdapter() {

		Conversation_adapter conversation_adapter = new Conversation_adapter(this, R.layout.text_item, messages, -1609780, contact.getCategory());
		// ArrayAdapter<String> adapter = new
		// ArrayAdapter<String>(this,R.layout.text_item,messages);
		lv_conversation.setAdapter(conversation_adapter);
	}

	public void setGridAdapter(ArrayList<String> texts, ListView listv) {

		Texts_adapter texts_adapter = new Texts_adapter(this, R.layout.gridview_list, texts);
		listv.setAdapter(texts_adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuOptions = menu;
		getMenuInflater().inflate(R.menu.menu_conversation, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		DrawerLayout drawer;
		if (item.getTitle().toString().equals("Texts")) {
			drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
			if (drawer.isDrawerOpen(Gravity.RIGHT)) {
				drawer.closeDrawer(Gravity.RIGHT);
			} else {
				// Hacer un metodo con esto en onCreate lo tenemos repetido
				// LLamarlo cargar texto o algo asi incluso meterlo en setgridadapter
				final BDManager contact_bd = new BDManager(this);
				final ListView listv = (ListView) findViewById(R.id.left_drawer);
				setGridAdapter(contact_bd.getTexts(), listv);
				drawer.openDrawer(Gravity.RIGHT);
			}
		} else {
			drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
			drawer.closeDrawer(Gravity.RIGHT);
			Intent intent = new Intent(this, TextsActivity.class);
			startActivity(intent);
		}
		return true;
	}

	private BroadcastReceiver myReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// ArrayList<String> messages =
			// intent.getStringArrayListExtra("messages");
			messages = intent.getStringArrayListExtra("messages");
			if (messages != null) {
				// actualizar el list de los mensajes recibidos
				Messages_bd msg_bd = new Messages_bd(context);
				// Tenemos que decodificar los mensajes recibidos
				// msg_bd.setMessage(message)
				setListAdapter();

			}
		}
	};

}
