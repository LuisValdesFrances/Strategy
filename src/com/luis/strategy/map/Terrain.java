package com.luis.strategy.map;

import com.luis.lgameengine.gameutils.gameworld.GameCamera;
import com.luis.lgameengine.gameutils.gameworld.WorldConver;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;


public class Terrain extends MapObject{
	
	private int type;
	private boolean conquest;
	
	public Terrain(
			WorldConver worldConver, GameCamera gameCamera, Map map,
			float x, float y, int width, int height,
			float mapX, float mapY,
			int mapWidth, int mapHeight, int type, boolean conquest) {
		super(worldConver, gameCamera, map, x, y, width, height, mapX, mapY, mapWidth, mapHeight);
		this.type = type;
		this.conquest = conquest;
	}
	
	public void update(MultiTouchHandler multiTouchHandler){
		super.update(multiTouchHandler);
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public boolean isConquest() {
		return conquest;
	}
	public void setConquest(boolean conquest) {
		this.conquest = conquest;
	}
	
	
	

}
