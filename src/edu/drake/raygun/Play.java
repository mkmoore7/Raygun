package edu.drake.raygun;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Play extends ActionBarActivity {

	int pos =0;
	
	public void message(String message){
		Context context = getApplicationContext();
		CharSequence text = message;
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		
		//set onClick listeners
		
		//up Button
		Button up = (Button)findViewById(R.id.button1);
		up.setOnClickListener( new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ImageView cat = (ImageView) findViewById(R.id.imageView1);
				pos-=130;
				message("Cat is at "+ pos);
				cat.setTranslationY(pos);
			}
			
		});
		Button down = (Button)findViewById(R.id.button2);
		down.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ImageView cat = (ImageView) findViewById(R.id.imageView1);
				pos+=130;
				message("Cat is at "+ pos);
				cat.setTranslationY(pos);
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
