package com.luis.strategy.map;

import com.luis.lgameengine.implementation.input.TouchData;
import com.luis.strategy.UserInput;

public abstract class MapObject implements Selectable{
	
	protected float x;
	protected float y;
	protected int width;
	protected int height;
	
	//Si hay ejercito enemigo o es del dominio, estandarte
	//En otro caso state
	protected int touchX;
	protected int touchY;
	
	protected int mapX;
	protected int mapY;
	protected int mapWidth;
	protected int mapHeight;
	
	public MapObject(float x, float y, int width, int height, 
			int mapX, int mapY, int mapWidth, int mapHeight) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.mapX = mapX;
		this.mapY = mapY;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		this.touchX = getAbsoluteX();
		this.touchY = getAbsoluteY();
	}
	
	public boolean isFocus(){
		if(UserInput.getInstance().getMultiTouchHandler().isTouchingScreen()){
			return(UserInput.getInstance().
					compareTouch(
							getTouchX()-width/2, 
							getTouchY()-height/2, 
							getTouchX()+width/2, 
							getTouchY()+height/2, 0));
		}
		return false;
	}
	
	public boolean isSelect(){
		if(UserInput.getInstance().getMultiTouchHandler().getTouchAction(0) == TouchData.ACTION_DOWN
			&& UserInput.getInstance().getMultiTouchHandler().getTouchFrames(0) == 1){
			return(UserInput.getInstance().
					compareTouch(
							getTouchX()-width/2, 
							getTouchY()-height/2, 
							getTouchX()+width/2, 
							getTouchY()+height/2, 0));
		}
		return false;
	}
	
	public int getRelativeX() {
		return (int)(x*mapWidth)/100;
	}
	public int getRelativeY() {
		return (int)(y*mapHeight)/100;
	}
	
	public int getAbsoluteX() {
		return mapX-mapWidth/2 + getRelativeX();
	}
	public int getAbsoluteY() {
		return mapY-mapHeight/2 + getRelativeY();
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public int getTouchX() {
		return touchX;
	}

	public void setTouchX(int touchX) {
		this.touchX = touchX;
	}
	public int getTouchY() {
		return touchY;
	}

	public void setTouchY(int touchY) {
		this.touchY = touchY;
	}

}
