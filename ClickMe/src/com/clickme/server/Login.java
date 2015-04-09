package com.clickme.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import android.os.AsyncTask;
import android.util.Log;


public class Login {
	
	
	private static String ip = "192.168.1.14";
	private static int port = 8888;
	
	public static int login(String username, String pass, String mail) {
		
		Log.v("Conexion:", " socket " + ip + " " + port);
		new setConection().execute(username + "/" + pass + "/" + mail);		
		return 1;
	}
}

class setConection extends AsyncTask<String, Void, String> {
	private static String ip = "192.168.1.14";
	private static int port = 8888;
	private Exception exception;

	protected String doInBackground(String... params) {
		String result = null; 
		try {
			Socket socket = new Socket(ip, port);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter salida = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
			salida.println(params[0]);
			Log.v("Sending:", params[0]);
			result = entrada.readLine();
			Log.v("Reciving:", result);
			socket.close();
		} catch (Exception e) {
			Log.v("Error: ", e.toString());
		}
		return result;
	}
}
