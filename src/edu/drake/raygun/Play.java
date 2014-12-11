package edu.drake.raygun;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



public class Play extends ActionBarActivity {

	Timer enemyTimer, bulletTimer;				// create timer
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
	
	int centerXOnEnemy;
    int centerYOnEnemy;
    int centerXOnBullet;
    int centerYOnBullet;
    
    TextView scoreCount = (TextView)findViewById(R.id.score);
    int points = 0;
	
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

		//Start the timers
		enemyTimer = new Timer(true);
		bulletTimer = new Timer(true);
		rl = (RelativeLayout) findViewById(R.id.rl);
		
		//start timers
		enemyTimer.scheduleAtFixedRate(new MyTimerTask(), 800, 900);
		bulletTimer.scheduleAtFixedRate(new MyTimerTask2(), 250, 900);

		
		rl = (RelativeLayout) findViewById(R.id.rl);

		//Create arraylists for everything
		enemyArray = new ArrayList<Enemy>();
		bulletArray = new ArrayList<Bullet>();
		translation = new ArrayList<Integer>();
		bulletPos = new ArrayList<Integer>();
		cats = new ArrayList<ImageView>();
		bullets = new ArrayList<ImageView>();
		

		frank = new ImageView (Play.this);
		System.out.println("Created frank");		
		//create hero
		x = 50;
		y = 300;
		hero = new Hero(x,y,frank,rl);
		System.out.println("Created Hero");


		//set up our layout and buttons
		ImageButton Up = (ImageButton)findViewById(R.id.imageButton1);
		ImageButton Down = (ImageButton)findViewById(R.id.imageButton2);
		ImageButton shoot = (ImageButton)findViewById(R.id.imageButton3);


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
				yBull = (int) frank.getY()+120;		

				//create bullet, update all the arrays and index
				Bullet bullet = new Bullet(xBull,yBull,img,rl);
				bullets.add(img);						//sets image for the enemy
				bulletArray.add(bullet);
				bulletPos.add(0);
				
				centerXOnBullet=img.getWidth()/2;
			    centerYOnBullet=img.getHeight()/2;
			    
			    // Meant to update the score but seems to make the program crash
			    /*
			    points = points + 100;
			    scoreCount.setText("Score: " + points);
				*/

			}
		});

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class MyTimerTask2 extends TimerTask {
		@Override
		public void run() {
			System.out.print("Make it in here?");
			// This calls the timer on special "timer" thread
			//goes through all the enemies, advances them left across the screen
			runOnUiThread(new Runnable() {   
				//This tells the computer that when a timer event happens, update the user interface thread
				Integer speed = 75;

				@Override
				public void run() {


					for(int i = 0; i < enemyArray.size(); i++){
						Enemy enemy1 = enemyArray.get(i);
						ImageView img = cats.get(i);
						centerXOnEnemy=img.getWidth()/2;
						Integer translations = translation.get(i);
						Integer dummySpeed;
						dummySpeed = translation.get(i);
						dummySpeed += speed;
						//System.out.print("Moved enemy");
						translation.set(i,dummySpeed);	
						//System.out.print("enemy move set");
						enemy1.moveLeft(translation.get(i), cats.get(i));
						int centerXOfEnemyOnScreen=img.getLeft()+centerXOnEnemy - dummySpeed;
						
						if(centerXOfEnemyOnScreen <= 200){
					    	enemyArray.remove(enemy1);
				
					    	translation.set(i,0);
							translation.remove(translations);
				
							img.setVisibility(View.GONE);
							cats.remove(img);
							message("Enemy off screen");
					    }
					}
					
					//set the image for enemy
					img = new ImageView (Play.this);
					//System.out.print("Is this making the image?");
					//get the random number for the lane, initialize our xPos for the enemies.
					Random rand = new Random();
					randInt = rand.nextInt();
					flag = Math.abs(randInt%4);
					//message("flag is set: " + flag);
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
					System.out.print("enemy created in code");
					cats.add(img);	
					System.out.print("enemy created on screen");//sets image for the enemy
					message("enemy created" );
					enemyArray.add(enemy);
					translation.add(0);
					centerXOnEnemy=img.getWidth()/2;
				    centerYOnEnemy=img.getHeight()/2;
					
				}

			});

		}

	}

	private class MyTimerTask extends TimerTask {
		@Override
		public void run() {
			//System.out.print("Make it in here?");
			// This calls the timer on special "timer" thread
			//goes through all the enemies, advances them left across the screen
			runOnUiThread(new Runnable() {   
				//This tells the computer that when a timer event happens, update the user interface thread
				Integer speed = 80;

				@Override
				public void run() {
					//This moves the bullets right
					for(int j = 0; j < bulletArray.size(); j++){	
						//Regular movement of bullets
						Bullet bullet = bulletArray.get(j);
						ImageView img = bullets.get(j);
						Integer bull = bulletPos.get(j);
						Integer dummySpeed = 0;
						dummySpeed = bull;
						System.out.println("dummySpeed = " + dummySpeed);
						dummySpeed += speed;
						bulletPos.set(j, dummySpeed);
						dummySpeed = bulletPos.get(j);
						bulletArray.get(j).moveRight(dummySpeed, img);
						
						System.out.println(img.getLeft());
						int centerXOfBulletOnScreen=img.getLeft()+centerXOnBullet + dummySpeed;
					    int centerYOfBulletOnScreen=img.getTop()+centerYOnBullet;	
					    
					    
					    //tells if bullet if off screen
					    if(centerXOfBulletOnScreen >= 1200){
					    	bulletArray.remove(bullet);    	
					    	bulletPos.set(j, 0);
							bulletPos.remove(bull);
							img.setVisibility(View.GONE);
							bullets.remove(img);
							message("bullet off screen");
					    }
					    
					    
					    System.out.println("number of bullets: " +  bulletArray.size() );
						
						for(int i = 0; i < enemyArray.size(); i++){
							Enemy enemy1 = enemyArray.get(i);
							ImageView img1 = cats.get(i);
							Integer translations = translation.get(i);
							int centerXOfEnemyOnScreen=img1.getLeft()+centerXOnEnemy - translations;
						    int centerYOfEnemyOnScreen=img1.getTop()+centerYOnEnemy;	
						    
							if(centerYOfBulletOnScreen == centerYOfEnemyOnScreen){	
								System.out.println("hit");
								//System.out.println (centerXOfBulletOnScreen + " , " + centerXOfEnemyOnScreen);
								
								if(centerXOfBulletOnScreen >= centerXOfEnemyOnScreen)  {
									System.out.println("major hit");
									enemyArray.remove(enemy1);
									img1.setVisibility(View.GONE);
									cats.remove(img1);	
									translation.set(i, 0);
									translation.remove(translations);
									
									
									bulletArray.remove(bullet);
									bulletPos.set(j, 0);
									bulletPos.remove(bull);
									img.setVisibility(View.GONE);
									bullets.remove(img);
								}
							}
						}
					}
				}
				// TODO Auto-generated method stub

			});

		}

	}

}