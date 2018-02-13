package com.luis.strategy.map;

import com.luis.lgameengine.gameutils.gameworld.GameCamera;
import com.luis.lgameengine.gameutils.gameworld.WorldConver;
import com.luis.lgameengine.implementation.input.TouchData;
import com.luis.strategy.UserInput;

public abstract class MapObject implements Selectable{
	
	protected WorldConver worldConver;
	protected GameCamera gameCamera;
	protected Map map;
	protected float x;
	protected float y;
	protected int width;
	protected int height;
	
	protected boolean selected;
	
	//Si hay ejercito enemigo o es del dominio, estandarte
	//En otro caso state
	protected int touchX;
	protected int touchY;
	
	protected float mapX;
	protected float mapY;
	protected int mapWidth;
	protected int mapHeight;
	
	public MapObject(
			WorldConver worldConver, GameCamera gameCamera,
			Map map,
			float x, float y, int width, int height, 
			float mapX, float mapY, int mapWidth, int mapHeight) {
		super();
		this.worldConver = worldConver;
		this.gameCamera = gameCamera;
		this.map = map;
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
	
	public int getAbsoluteX() {
		return (int)((x*mapWidth)/100f);
	}
	public int getAbsoluteY() {
		return (int)((y*mapHeight)/100f);
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
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getTouchX() {
		return worldConver.getConversionDrawX(gameCamera.getPosX(), touchX);
	}

	public void setTouchX(int touchX) {
		this.touchX = touchX;
	}
	public int getTouchY() {
		return worldConver.getConversionDrawY(gameCamera.getPosY(), touchY);
	}

	public void setTouchY(int touchY) {
		this.touchY = touchY;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
