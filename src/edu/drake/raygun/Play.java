package edu.drake.raygun;

import java.util.Random;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Play extends ActionBarActivity {

	int pos =0;
	RelativeLayout rl;        //create layout to add images to
	ImageView img;            //declare imageView
	Button b;					//declare button
	int xPos = 1150;           //initialize x position for enemies
	int yPos = 0;				//declare variable that will be the lane
	int flag = 0;				//flag will decide what lane the enemies end up in
	int randInt;				//rand integer to set lanes
	int enemyIndex = 0;			//set up index of arrays 
	int[] translation;			//array of the xvalues for enemies so that they can get translated left
	ImageView[] cats;			//array of images of enemies, this makes sure that the images are moving on screen
	Enemy dummyEnemy;			//object
	Enemy[] enemyArray;			//array of all the enemies
	Bullet[] bulletArray;		//array of all of the bullets
	int bulletNdx=0;			//index for the bullet array
	ImageView[] bulletsPics;	//index for the images of the bullets
	int[] bulletTranslation;	//array of xvalues for the bullets 
	


	
	
	
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
		
		//Start Conor's Code
		//declare all the arrays with 100 elements
		final Enemy[] enemyArray = new Enemy[1000];
		final Bullet[] bulletArray = new Bullet[1000];
		final int[] bulletTranslation = new int[1000];
		final int[] translation = new int[1000];
		final ImageView[] bulletsPics = new ImageView[1000];
		final ImageView[] cats = new ImageView[1000];
		//initializes our translation array with 0's
		for(int i = 0; i < translation.length; i++){
			translation[i]=0;
			bulletTranslation[i] =0;
		}
		//set up our layout and buttons
		rl = (RelativeLayout) findViewById(R.id.rl);
		b = (Button) findViewById(R.id.button1);
		Button left = (Button)findViewById(R.id.button2);

		//create a new enemy
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//set the image for enemy
				img = new ImageView (Play.this);
				//get the random number for the lane, initialize our xPos for the enemies.
				Random rand = new Random();
				randInt = rand.nextInt();
				flag = Math.abs(randInt%5);
				message("flag is set: " + flag);
				xPos = 1100;

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
						else
							if(flag==3)
							{
								yPos=500;
							}
							else
								if(flag==4)
								{
									yPos=600;

								}
				//create enemy, update all the arrays and index
				Enemy enemy = new Enemy(xPos,yPos,img,rl);
				cats[enemyIndex] = img;						//sets image for the enemy
				message("enemy created" );
				enemyArray[enemyIndex] = enemy;
				enemyIndex+=1;
			}
		});


		
		left.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) { 		
				int speed = 10;
				//goes through all the enemies, advances them left across the screen
				for(int i = 0; i < enemyIndex; i++){
					translation[i] += speed;
					enemyArray[i].moveLeft(translation[i], cats[i]);
				}    			
			}
		});
		
		//end Conor's Code

		//set onClick listeners
		
		//up Button
		Button up = (Button)findViewById(R.id.button3);
		up.setOnClickListener( new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ImageView cat = (ImageView) findViewById(R.id.imageView1);
				pos-=130;
				message("Cat is at "+ pos);
				cat.setTranslationY(pos);
			}
			
		});
		Button down = (Button)findViewById(R.id.button5);
		down.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ImageView cat = (ImageView) findViewById(R.id.imageView1);
				pos+=130;
				message("Cat is at "+ pos);
				cat.setTranslationY(pos);
			}
			
		});
		
		//create the shoot button, and make it shoot a bullet horizonally
		Button mew = (Button) findViewById(R.id.button4);
		mew.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				message("Mew Mew Mew!!!!");
				ImageView cat = (ImageView) findViewById(R.id.imageView1);
				img = new ImageView (Play.this);
				
				//get the position of the player when the button is pressed
				float catXPos = cat.getX();
				float catYPos = cat.getY();
				
				message("player is at "+catXPos+", "+catYPos+".");
				
				//create a bullet at that position and add it to bullet array
				Bullet bullet = new Bullet(catXPos-25,catYPos-21,img,rl);
				bulletArray[bulletNdx] = bullet;
				bulletsPics[bulletNdx] = img;
				bulletNdx+=1;
				message("bullet index updated"+ bulletNdx);
			}
			
		});
		
		Button right = (Button) findViewById(R.id.button6);
		right.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) { 	
				System.out.println("in rightButton");
				int speed = 10;
				//goes through all the enemies, advances them left across the screen
				for(int i = 0; i < bulletNdx; i++){
					message("in for loop");
					bulletTranslation[i] += speed;
					message("bullet trans is "+ bulletTranslation[i]);
					bulletArray[i].moveRight(bulletTranslation[i], bulletsPics[i]);
				}    			
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
