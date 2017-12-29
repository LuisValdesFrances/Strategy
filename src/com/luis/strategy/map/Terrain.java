package com.luis.strategy.map;


public class Terrain extends MapObject{
	
	private int type;
	private boolean conquest;
	
	public Terrain(int x, int y, int width, int height,
			int mapX, int mapY,
			int mapWidth, int mapHeight, int type, boolean conquest) {
		super(x, y, width, height, mapX, mapY, mapWidth, mapHeight);
		this.type = type;
		this.conquest = conquest;
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
	
	

	@Override
	public boolean onFocus() {return false;}

	@Override
	public boolean onSelect() {return false;
	}
	
	
	

}
