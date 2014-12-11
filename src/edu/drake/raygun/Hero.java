package edu.drake.raygun;

import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Hero {


	private int x;
	private int y;

	public Hero() {	
		// TODO Auto-generated constructor stub
	}
	//creates an enemy and creates the image of the enemy on the screen
	
	public Hero(int x, int y, ImageView frank, RelativeLayout rl){
		
		
		frank.setImageResource(R.drawable.cat);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams( 
				(int) LayoutParams.WRAP_CONTENT, (int) LayoutParams.WRAP_CONTENT);
		params.setMargins(x,y,100,100);
		frank.setLayoutParams(params);
		rl.addView(frank);

		// TODO Auto-generated constructor stub
	}
	//same as a "setY" method
	public void setLane(int lane){
		y = lane;
	}

	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void moveVertical(int speed, ImageView img){
		y -= speed;
		img.setTranslationY(-speed);
	}

}
