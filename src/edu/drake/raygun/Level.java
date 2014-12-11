package edu.drake.raygun;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;



public class Level extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level);
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.hide();
		
		ImageButton dsm = (ImageButton) findViewById(R.id.imageButton1);
		
		//set an onClick listener for the des moines button
		//when clicked it should pull up a pop up of the image explaining the mission.
		
		//So this creates like 4 activities... need to figure out why this is happening.
		dsm.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) { 	
			     View popupView = new View(Level.this);
			     final PopupWindow popupWindow = new PopupWindow(popupView,
			     ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
			     Drawable drawable = getResources().getDrawable(R.drawable.dsmmissionstatement);

			     popupWindow.setBackgroundDrawable(drawable);
			     popupWindow.setHeight(700);
			     popupWindow.setWidth(1200);      
			     popupWindow.setTouchable(true);
			     popupWindow.setFocusable(true);
			     popupWindow.setTouchInterceptor(new View.OnTouchListener() {

			            @Override
			            public boolean onTouch(View v, MotionEvent event) {
			            	
//CHANGE THIS LINE IF YOU WANT TO RUN THE OLD play.java file ----------v			            	
			        		Intent intent = new Intent(Level.this, Play.class);
			        		startActivity(intent);
			                return false;
			            }
			        });

			     popupWindow.showAtLocation(findViewById(R.id.imageButton1), Gravity.CENTER, 0, 0);

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.level, menu);
		return true;
	}

	public void desMoines(View v){
		Intent intent = new Intent(this, Play2.class);
		startActivity(intent);
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
