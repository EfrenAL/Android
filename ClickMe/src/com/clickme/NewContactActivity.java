package com.clickme;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import com.clickme.bd.BDManager;
import com.clickme.contactos.Contact;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

@SuppressLint("NewApi")
public class NewContactActivity extends Activity {
	private int SELECT_IMAGE = 1;
	private int SELECT_COLOR = 2;
	private int color_selected = 12;
	private int color_selected_hex;
	// private String path;
	private Bitmap bmp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newcontact);

		ImageButton button_colors = (ImageButton) findViewById(R.id.b_color);
		button_colors.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent lv_nIntent;
				lv_nIntent = new Intent(getBaseContext(), ColorsActivity.class);
				// Mirar estas banderas para que son !!!!!
				lv_nIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); // singleTop
				lv_nIntent.putExtra("color_selected", color_selected);
				startActivityForResult(lv_nIntent, SELECT_COLOR);
			}
		});

		ImageButton b_face = (ImageButton) findViewById(R.id.b_face);
		b_face.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Seleccionamos imagen del contacto
				Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
				intent.setType("image/*");
				startActivityForResult(intent, SELECT_IMAGE);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_new_contact, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_ok) {

			// Get name edittext
			EditText et_name = (EditText) findViewById(R.id.et_name);
			String name = et_name.getText().toString();

			// convert bitmap to byte
			// ByteArrayOutputStream stream = new ByteArrayOutputStream();
			// bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			// byte imageInByte[] = stream.toByteArray();

			// ////
			// int bytes = bmp.getByteCount();
			// ByteBuffer buffer = ByteBuffer.allocate(bytes); //Create a new
			// buffer
			// bmp.copyPixelsToBuffer(buffer); //Move the byte data to the
			// buffer
			// byte[] imageInByte = buffer.array(); //Get the underlying array
			// containing the data.
			// ////

			Contact contact = new Contact(name, "Prueba", color_selected_hex, bmp, name);
			BDManager cont_bd = new BDManager(this);
			cont_bd.setContact(contact);
			Intent lv_nIntent;
			lv_nIntent = new Intent(getBaseContext(), ContactsActivity.class);
			startActivity(lv_nIntent);

			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		try {
			if (requestCode == SELECT_IMAGE) {
				if (resultCode == Activity.RESULT_OK) {
					Uri selectedImage = data.getData();
					ImageButton b_face = (ImageButton) findViewById(R.id.b_face);

					bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

					// //////
					bmp = getRoundedShape(bmp, b_face.getWidth(), b_face.getHeight());
					b_face.setImageBitmap(bmp);
					// b_face.setImageURI(selectedImage);
				}
			} else if (requestCode == SELECT_COLOR) {

				color_selected = data.getIntExtra("color_selected", 0);

				// Get colors
				int[] colors = this.getResources().getIntArray(R.array.androidcolors);
				String[] colorDesc = this.getResources().getStringArray(R.array.androidcolors_name);
				ArrayList<String> al_colors = new ArrayList();
				for (int i = 0; i < colors.length; i++) {
					String hexColor = String.format("#%06X", (0xFFFFFF & colors[i]));
					al_colors.add(hexColor);
				}

				TextView tv_color = (TextView) findViewById(R.id.tv_color);
				tv_color.setText(colorDesc[color_selected]);

				GradientDrawable gradient = (GradientDrawable) this.getResources().getDrawable(R.drawable.circle_color);
				color_selected_hex = Color.parseColor(al_colors.get(color_selected));
				gradient.setColor(color_selected_hex);

				ImageButton b_color = (ImageButton) findViewById(R.id.b_color);
				b_color.setBackground(gradient);

			}
		} catch (Exception e) {
		}
	}

	// Funcion para redondear imagen
	public Bitmap getRoundedShape(Bitmap scaleBitmapImage, int width, int height) {
		int targetWidth = width;
		int targetHeight = height;
		Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(targetBitmap);
		Path path = new Path();
		path.addCircle(((float) targetWidth - 1) / 2, ((float) targetHeight - 1) / 2, (Math.min(((float) targetWidth), ((float) targetHeight)) / 2), Path.Direction.CCW);

		canvas.clipPath(path);
		Bitmap sourceBitmap = scaleBitmapImage;
		canvas.drawBitmap(sourceBitmap, new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight()), new Rect(0, 0, targetWidth, targetHeight), null);
		return targetBitmap;
	}

}
