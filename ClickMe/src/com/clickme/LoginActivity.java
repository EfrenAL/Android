package com.clickme;

import com.clickme.server.Login;
import com.clickme.server.Server;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	// http://www.tutorialspoint.com/android/android_shared_preferences.htm
	private EditText username = null;
	private EditText password = null;
	private TextView attempts;
	private Button login;
	private Server server;
	private SharedPreferences sharedpreferences;
	public static final String MyLogin = "ClickMe";
	public static final String Username = "usernameKey";
	public static final String Password = "passwordKey";
	public static final String Phone = "phoneKey";
	public static final String Email = "emailKey";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		server = Server.getServer();

		if (isLoged()) {
			navegate();
		} else {
			setContentView(R.layout.activity_login);
			login = (Button) findViewById(R.id.b_login);
			username = (EditText) findViewById(R.id.et_username);
			password = (EditText) findViewById(R.id.et_pass);
		}
	}

	public void login(View view) {

		Editor editor = sharedpreferences.edit();
		String uname = username.getText().toString();
		String pass = password.getText().toString();
		int result = Login.login(uname, pass, uname);

		if (result == 1) {
			editor.putString(Username, uname);
			editor.putString(Phone, pass);

			server.setUser(uname);
			server.setPass(pass);

			editor.commit();

			navegate();
		}else{
			String message = "Error in login";
		}

	}

	public boolean isLoged() {
		sharedpreferences = getSharedPreferences(MyLogin, Context.MODE_PRIVATE);
		if (sharedpreferences.contains(Username)) {
			server.setUser(sharedpreferences.getString(Username, ""));
			server.setPass(sharedpreferences.getString(Password, ""));
			return true;
		} else {
			return false;
		}
	}

	public void navegate() {
		Intent lv_nIntent;
		lv_nIntent = new Intent(getBaseContext(), ContactsActivity.class);
		lv_nIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(lv_nIntent);
		finish();
	}
}