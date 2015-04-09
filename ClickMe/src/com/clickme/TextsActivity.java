package com.clickme;

import java.util.ArrayList;

import com.clickme.bd.BDManager;
import com.clickme.texts.Texts_adapter;
import com.clickme.texts.Texts_new_adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class TextsActivity extends Activity {
	private Menu MenuOptions;
	private Context context = this;
	private BDManager bdManager = new BDManager(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_texts);

		ListView lv_texts = (ListView) findViewById(R.id.lv_texts);
		ArrayList<String> texts = bdManager.getTexts();

		Texts_new_adapter texts_new_adapter = new Texts_new_adapter(this, R.layout.new_text, texts);
		lv_texts.setAdapter(texts_new_adapter);

		lv_texts.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// Toast.makeText(TextsActivity.this,"List item clicked",Toast.LENGTH_SHORT).show();
				EditText et_textGV = (EditText) view.findViewById(R.id.textGV);
				et_textGV.requestFocus();
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuOptions = menu;
		getMenuInflater().inflate(R.menu.menu_texts, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_ok) {
			// setText();
			// Intent lv_nIntent;
			// lv_nIntent = new Intent(getBaseContext(),
			// ConversationActivity2.class);
			// lv_nIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT |
			// Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
			// startActivity(lv_nIntent);

			InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			finish();

		} else if (id == R.id.action_add_text) {
			add_text();
		}
		return true;
	}

	public void add_text() {
		bdManager.setText("");
		ListView lv_texts = (ListView) ((Activity) context).findViewById(R.id.lv_texts);
		ArrayList<String> texts = bdManager.getTexts();
		Texts_new_adapter texts_new_adapter = new Texts_new_adapter(context, R.layout.new_text, texts);
		lv_texts.setAdapter(texts_new_adapter);
	}

}
