package com.luis.map;

import com.luis.strategy.UserInput;

public abstract class MapObject implements Selectable{
	
	protected float x;
	protected float y;
	protected int width;
	protected int height;
	
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
	}
	
	public boolean isFocus(){
		if(UserInput.getInstance().getMultiTouchHandler().isTouchingScreen()){
			return(UserInput.getInstance().
					compareTouch(
							getAbsoluteX()-width/2, 
							getAbsoluteY()-height/2, 
							getAbsoluteX()+width/2, 
							getAbsoluteY()+height/2, 0));
		}
		return false;
	}
	
	public boolean isSelect(){
		if(!UserInput.getInstance().getMultiTouchHandler().isTouchingScreen()){
			return(UserInput.getInstance().
					compareTouch(
							getAbsoluteX()-width/2, 
							getAbsoluteY()-height/2, 
							getAbsoluteX()+width/2, 
							getAbsoluteY()+height/2, 0));
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
	
	
	

}
