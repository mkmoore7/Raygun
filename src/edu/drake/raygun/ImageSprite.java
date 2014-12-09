package edu.drake.raygun;

import android.graphics.Rect;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ImageSprite {
	public int x;
	public int y;
	public ImageView img;
	public RelativeLayout rl;
	public char type;
	
	
	//constructs a new ImageSprite
	ImageSprite(char typ, int xPos, int yPos, ImageView image, RelativeLayout rL ){
		type = typ;
		x= xPos;
		y= yPos;
		img = image;	
		rl = rL;

		//enemy
		if(type == 'e'){

			img.setImageResource(R.drawable.zombieburger);
		}
		//hero
		else if(type =='h'){
			img.setImageResource(R.drawable.raygun2);
		}
		//bullet
		else if(type =='b'){
			img.setImageResource(R.drawable.ic_launcher);
		}
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams( 
				(int) LayoutParams.WRAP_CONTENT, (int) LayoutParams.WRAP_CONTENT);
		params.setMargins(xPos,yPos,200,200);
		img.setLayoutParams(params);
		rl.addView(img);
	}


	public int getX(){
		return x;
	}
	public int gety(){
		return y;
	}

	public void moveVertical(int speed, ImageView img){
		y -= speed;
		img.setTranslationY(-speed);
	}
	
	//a positive value will move right, while a negative value will move left
	public void moveHorizontal(int speed, ImageView img){
		x += speed;
		img.setTranslationX(-speed);
	}
	
	/**
	 * A possible form of collision detection. Would be called like enemy.collidesWith(bullet);
	 * @param img2
	 */
	public boolean collidesWith(ImageSprite img2){
		boolean flag = false;
		
		//if the x and y coordinates match then return true.
		//TODO: Will need to add some wiggle room.
		
		Rect rc1 = new Rect();
		this.img.getDrawingRect(rc1);
		Rect rc2 = new Rect();
		img2.img.getDrawingRect(rc2);
		if (Rect.intersects(rc1, rc2)){
			System.out.println("COLLISION!!!! YAHOO!!!");
			flag = true;
		}
		return flag;
	}
}
