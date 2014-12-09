package edu.drake.raygun;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Play extends ActionBarActivity {

	Timer myTimer;				// create timer
	RelativeLayout rl;          //create layout to add images to
	ImageView img;  			//declare imageView
	ImageView frank;			//image of hero
	Button b;					//declare button
	int xPos = 1150;            //initialize x position for enemies
	int yPos = 0;				//declare variable that will be the lane
	int flag = 0;				//flag will decide what lane the enemies end up in
	int randInt;				//rand integer to set lanes
	int pos = 0;
	int x = 100;
	int y = 300;
	int xBull;
	int yBull;
	Hero hero;
	ArrayList<Enemy> enemyArray;
	ArrayList<Bullet> bulletArray;
	ArrayList<Integer> translation;
	ArrayList<Integer> bulletPos;
	ArrayList<ImageView> cats;
	ArrayList<ImageView> bullets;
	
	
	// Not used, were used in failed collision attempt
	//ArrayList<Integer> bulhitcount;
	//ArrayList<Integer> enehitcount;
	

	//We don't use any of these, but will leave them just in case we want to go back to them
	//int[] translation;			//array of the xvalues for enemies so that they can get translated left
	//ImageView[] cats;			//array of images of enemies, this makes sure that the images are moving on screen
	//Enemy dummyEnemy;			//object
	//Enemy[] enemyArray;			//array of all the enemies
	//Bullet[] bulletArray;		
	//ImageView[] bullets;
	//int[] bulletPosition;
	//int bulletIndex = 0;
	//int enemyIndex = 0;			//set up index of arrays 
	//int killedEnemy = -1;


	public void message(String message){
		Context context = getApplicationContext();
		CharSequence text = message;
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play );
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.hide();

		//create a timer
		myTimer = new Timer(true);
		rl = (RelativeLayout) findViewById(R.id.rl);

		//Create arraylists for everything
		enemyArray = new ArrayList<Enemy>();
		bulletArray = new ArrayList<Bullet>();
		translation = new ArrayList<Integer>();
		bulletPos = new ArrayList<Integer>();
		cats = new ArrayList<ImageView>();
		bullets = new ArrayList<ImageView>();
		
		//Used in failed collision attempt
		//bulhitcount = new ArrayList<Integer>();
		//enehitcount = new ArrayList<Integer> ();

		/* We don't use this anymore, left it just in case
		//declare all the arrays with 100 elements
		enemyArray = new Enemy[100];
		translation = new int[100];
		cats = new ImageView[100];


		//Creates everything for the bullets 
		bulletArray = new Bullet[100];
		bulletPosition = new int[100];
		bullets = new ImageView[100];
		 */

		frank = new ImageView (Play.this);
		System.out.println("Created frank");		
		//create hero
		x = 50;
		y = 300;
		hero = new Hero(x,y,frank,rl);
		System.out.println("Created Hero");

		//initializes our translation array with 0's
		//for(int i = 0; i < 100; i++){
		//	translation[i]=0;
		//}

		//set up our layout and buttons
		Button start = (Button)findViewById(R.id.button2);
		Button stop = (Button)findViewById(R.id.button3);
		Button Up = (Button)findViewById(R.id.button4);
		Button Down = (Button)findViewById(R.id.button5);
		Button shoot = (Button)findViewById(R.id.button6);
		//final Enemy enemy1 = new Enemy(300,img, rl);
		//enemyArray[0] = enemy1;


		//create a popup of the mission statement
		//createPopUp();


		//Starts all the timer activity stuff when you press the start button
		start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) { 		
				myTimer.scheduleAtFixedRate(new MyTimerTask(), 500, 2000);
			}
		});



		stop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) { 		
				myTimer.cancel();
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


		//shoots the bullet
		shoot.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) { 	
				message("bullet shot");
				//creates bullets
				//set the image for bullet
				img = new ImageView (Play.this);
				
				//Set set x, y for bullet as the same as that of the hero
				xBull = 400;
				yBull = (int) frank.getY();		

				//create bullet, update all the arrays and index
				Bullet bullet = new Bullet(xBull,yBull,img,rl);
				bullets.add(img);						//sets image for the enemy
				bulletArray.add(bullet);
				bulletPos.add(0);


			}
		});

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void createPopUp(){
		Button dsm = (Button)findViewById(R.id.button2);
		message("in createPopUp");
		View popupView = new View(Play.this);
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
				myTimer.scheduleAtFixedRate(new MyTimerTask(), 500, 2000);
				return false;
			}
		});
		popupWindow.showAtLocation(dsm, Gravity.CENTER, 0, 0);
	}

	private class MyTimerTask extends TimerTask {

		int count =0;
		@Override
		public void run() {
			// This calls the timer on special "timer" thread
			//goes through all the enemies, advances them left across the screen
			runOnUiThread(new Runnable() {         //This tells the computer that when a timer event happens, update the user interface thread
				int speed = 75;

				@Override
				public void run() {

					// TODO Auto-generated method stub
					for(int i = 0; i < enemyArray.size(); i++){
						Enemy enemy1 = enemyArray.get(i);
						Integer dummySpeed;
						dummySpeed = translation.get(i);
						dummySpeed += speed;
						//System.out.print("Moved enemy");
						translation.set(i,dummySpeed);	
						//System.out.print("enemy move set");
						enemy1.moveLeft(translation.get(i), cats.get(i));
					}
					//This moves the bullets right
					for(int j = 0; j < bulletArray.size(); j++){	
						Bullet bullet1 = bulletArray.get(j);
						Integer dummySpeed;
						dummySpeed = bulletPos.get(j);
						dummySpeed += speed;
						bulletPos.set(j, dummySpeed);
						bulletArray.get(j).moveRight(bulletPos.get(j), bullets.get(j));
						
						/*
						for(int i = 0; i < enemyArray.size(); i++){
							if(bulletArray.get(j).getY() == enemyArray.get(i).getY()){	
								if(bulletArray.get(j).getX() >= enemyArray.get(i).getX())  {
									bulhitcount.add(j);
									enehitcount.add(i);
								}
							}
						}
						*/
					}
					
					
					/*        Failed simple collision detection
					for(int i = 0; i < bulhitcount.size(); i++){
						bulletArray.remove(bulletArray.get(i));
						bulletPos.remove(bulletPos.get(i));
						bullets.remove(bullets.get(i));
					}
					for(int j = 0; j < enehitcount.size(); j++){
						enemyArray.remove(enemyArray.get(j));
						translation.remove(translation.get(j));
						cats.remove(cats.get(j));
					}
					 */

					//This all creates the enemies on the timer

					//set the image for enemy
					img = new ImageView (Play.this);
					//get the random number for the lane, initialize our xPos for the enemies.
					Random rand = new Random();
					randInt = rand.nextInt();
					flag = Math.abs(randInt%4);
					xPos = 1150;

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


					//create enemy, update all the arrays and index
					Enemy enemy = new Enemy(xPos,yPos,img,rl);
					cats.add(img);						//sets image for the enemy
					//message("enemy created" );
					enemyArray.add(enemy);
					translation.add(0);
					//enemyIndex+=1;
					
				}

				// TODO Auto-generated method stub

			});

		}

	}

}
