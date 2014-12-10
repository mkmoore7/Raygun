package edu.drake.raygun;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Play2 extends ActionBarActivity {

	Timer bulletTimer, enemyTimer;
	RelativeLayout rl;					
	ArrayList<ImageSprite> enemyArray, bulletArray;	//ArrayLists to store the data
	ArrayList<Integer> enemyTrans, bulletTrans;
	ImageSprite hero;								//object of hero
	ImageView frank,img;								//image of hero
	int pos = 0;


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
		setContentView(R.layout.activity_play2);

		//hide the action bar for more space
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.hide();
		message("In Play2");

		//create the timers
		bulletTimer = new Timer();
		enemyTimer = new Timer();
		rl = (RelativeLayout) findViewById(R.id.rl);

		//create ArrayLists
		enemyArray = new ArrayList<ImageSprite>();
		bulletArray = new ArrayList<ImageSprite>();
		enemyTrans= new ArrayList<Integer>();
		bulletTrans = new ArrayList<Integer>();

		//create the hero
		frank = new ImageView(Play2.this);
		hero = new ImageSprite('h', 50, 300, frank, rl);

		//create/attach the buttons
		Button start = (Button)findViewById(R.id.button2);
		Button stop = (Button)findViewById(R.id.button3);
		Button Up = (Button)findViewById(R.id.button4);
		Button Down = (Button)findViewById(R.id.button5);
		Button shoot = (Button)findViewById(R.id.button6);

		//create a popup of the mission statement... eventually

		//TODO: Add the bullet timer, but for now, just run it all from the enemy timer.
		enemyTimer.scheduleAtFixedRate(new MyTimerTask(), 500, 2000);
		bulletTimer.scheduleAtFixedRate(new MyTimerTask2(), 500, 500);

		stop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) { 		
				enemyTimer.cancel();
			}
		});

		//moves hero up
		Up.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) { 	
				pos += 100;
				hero.moveVertical(pos,frank);
			}
		});

		//moves hero down
		Down.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) { 
				System.out.println("Created Hero");
				pos -=100;
				hero.moveVertical(pos,frank);
			}
		});

		shoot.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) { 	
				//set the imageView for bullet
				img = new ImageView (Play2.this);

				//Set set x, y for bullet as the same as that of the hero
				ImageSprite bullet = new ImageSprite('b', 400, (int) (frank.getY()+25), img, rl);
				bulletArray.add(bullet);
				bulletTrans.add(0);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play2, menu);
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
	
	private void createEnemy(){
		//create the enemies
		//set the image for enemy
		img = new ImageView (Play2.this);
		//get the random number for the lane, initialize our xPos for the enemies.
		Random rand = new Random();
		int randInt = rand.nextInt();
		int flag = Math.abs(randInt%4);
		int xPos = 1150;
		int yPos = 0;

		//depending on the random number generated, assigns the correct lane
		if (flag==0)
		{
			yPos=200;					
		}
		else
			if(flag==1)
			{
				yPos=300;
			}
			else
				if(flag==2)
				{
					yPos=400;
				}

		System.out.println("Enemy is at (" + xPos+" ," +yPos+ ")");
		//create enemy, update all the arrays and index
		ImageSprite zombie = new ImageSprite('e', xPos, yPos, img, rl);
		//message("enemy created" );
		enemyArray.add(zombie);
		enemyTrans.add(0);
		System.out.println(enemyTrans);
	}
	
	private class MyTimerTask2 extends TimerTask {

		@Override
		public void run() {
			// This calls the timer on special "timer" thread
			//goes through all the enemies, advances them left across the screen
			runOnUiThread(new Runnable() {     //This tells the computer that when a timer event happens, update the user interface thread

				int speed = 75;
				ImageSprite bullet;
				@Override
				public void run() {
					
					//move the bullets
					
					for (int k=0; k<bulletArray.size(); k++){
						bullet = bulletArray.get(k);
						Integer dummySpeed;
						dummySpeed = bulletTrans.get(k);
						dummySpeed += speed;
						bulletTrans.set(k, dummySpeed);
						bullet.moveHorizontal(-bulletTrans.get(k), bullet.img);
					}
					
					//Check for Collisions
					//for(int m=0; m< bulletArray.size(); m++){
					//	for(int n=0; n<enemyArray.size(); n++){
					//		if(bulletArray.get(m).collidesWith(enemyArray.get(n))){
					//			bulletArray.get(m).img.setVisibility(View.GONE);
					//			enemyArray.get(n).img.setVisibility(View.GONE);
					//		}
					//	}

					//}
					
					
				}

			});

		}

	}
	
	
	private class MyTimerTask extends TimerTask {

		@Override
		public void run() {
			// This calls the timer on special "timer" thread
			//goes through all the enemies, advances them left across the screen
			runOnUiThread(new Runnable() {     //This tells the computer that when a timer event happens, update the user interface thread

				int speed = 75;
				ImageSprite enemy;
				@Override
				public void run() {
					
					//add an enemy to enemyArray
					createEnemy();
					//move the enemy
					for(int i = 0; i < enemyArray.size(); i++){
						enemy= enemyArray.get(i);
						Integer dummySpeed;
						dummySpeed = enemyTrans.get(i);
						dummySpeed += speed;
						//System.out.print("Moved enemy");
						enemyTrans.set(i,dummySpeed);	
						enemy.moveHorizontal(enemyTrans.get(i), enemy.img);
					}			
				}

			});

		}

	}

}
