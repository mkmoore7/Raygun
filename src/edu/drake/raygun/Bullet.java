package edu.drake.raygun;

import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class Bullet {
	
	private int xPos;
	private int yPos;
	
	public Bullet(int xPosi, int yPosi, ImageView img, RelativeLayout rl){
		xPos = xPosi;
		yPos = yPosi;
		img.setImageResource(R.drawable.bullet);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams( 
				(int) LayoutParams.WRAP_CONTENT, (int) LayoutParams.WRAP_CONTENT);
		params.setMargins((int)xPos,(int)yPos,200,200);
		img.setLayoutParams(params);
		rl.addView(img);
	}
	//same as a "setY" method
	public void setLane(int lane){
		yPos = lane;
	}

	public int getX(){
		return xPos;
	}

	//Moves our bullet across the screen
	public void moveRight(int speed, ImageView img){
		xPos += speed;
		img.setTranslationX(speed);
	}
	
	public float getY(){
		return yPos;
	}


}