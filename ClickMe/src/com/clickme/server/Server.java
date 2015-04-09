package com.clickme.server;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.util.StringUtils;

import com.clickme.messages.Messages_bd;

public class Server {

	private static Server server;
	private String host = "click.noip.me"; 
	private int port = 5222; //5222;
	private String service = "click.noip.me";
	private String username;//
	private String password;//

	// Variables de XMPP Client
	private ArrayList<String> messages = new ArrayList();
	private EditText mRecipient;
	private EditText mSendText;
	private ListView mList;
	private XMPPConnection connection;
	// Clase messages_bd para gestionar la cola de mensajes recibidos
	private Messages_bd msg_bd;
	// Enviamos el contexto de la conversacion actual
	private Context context;

	// Private constructor for clase Singleto
	private Server() {

	}

	public static Server getServer() {
		if (server == null) {
			server = new Server();
		}
		return server;
	}

	public void trySetConnection() {
		// Create a connection
		ConnectionConfiguration connConfig = new ConnectionConfiguration(host, port, service);		
		XMPPConnection connection = new XMPPConnection(connConfig);

		try {
			connection.connect();
			Log.i("XMPPClient", "[SettingsDialog] Connected to " + connection.getHost());
		} catch (XMPPException ex) {
			Log.e("XMPPClient", "[SettingsDialog] Failed to connect to " + connection.getHost());
			Log.e("XMPPClient", ex.toString());
			setConnection(null);
		}
		if (connection.isConnected()) {
			Log.i("XMPPClient", "[SettingsDialog] ID: " + connection.getConnectionID() + 
					" Host: " + connection.getHost() + 
					" Port: " + connection.getPort());			
		}
		try {
			connection.login(username, password);
			Log.i("XMPPClient", "Logged in as " + connection.getUser());
			// Set the status to available
			Presence presence = new Presence(Presence.Type.available);
			connection.sendPacket(presence);
			setConnection(connection);
		} catch (XMPPException ex) {
			Log.e("XMPPClient", "[SettingsDialog] Failed to log in as " + username);
			Log.e("XMPPClient", ex.toString());
			setConnection(null);
		}
	}

	public void setConnection(XMPPConnection connection) {
		this.connection = connection;
		if (connection != null) {
			// Add a packet listener to get messages sent to us
			PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
			connection.addPacketListener(new PacketListener() {
				public void processPacket(Packet packet) {
					Message message = (Message) packet;
					if (message.getBody() != null) {
						String fromName = StringUtils.parseBareAddress(message.getFrom());
						Log.i("XMPPClient", "Got text [" + message.getBody() + "] from [" + fromName + "]");
						messages.add(fromName + ":" + message.getBody());
						sendBroadCast();
						// Add the incoming message to the list view
						/*
						 * mHandler.post(new Runnable() { public void run() {
						 * setListAdapter(); } });
						 */
					}
				}
			}, filter);
		}
	}
	
	public void UpgradeContext(Context context){
		this.context = context;
	}
	public void sendPacket(Message msg) {
		connection.sendPacket(msg);
	}


	public void setUser(String username) {
		this.username = username;
	}
	public void setPass(String pass) {
		this.password = pass;
	}
	public String getUser() {
		return username;
	}
	public String getService() {
		return service;
	}
	
	private void setListAdapter() {
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		// R.layout.multi_line_list_item, messages);
		// mList.setAdapter(adapter);
	}
	//Llamar a UpgradeContext antes de este metodo
	private void sendBroadCast() {
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction("RECEIVED_MESSAGE");
		broadcastIntent.putStringArrayListExtra("messages", messages);
		context.sendBroadcast(broadcastIntent);
	}

}
