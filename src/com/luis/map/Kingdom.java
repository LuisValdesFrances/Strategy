package com.luis.map;

import java.util.ArrayList;
import java.util.List;

import com.luis.strategy.GfxManager;

public class Kingdom extends MapObject{
	
	public static int DOMAIN_FREE = 0;
	public static int DOMAIN_1 = 1;
	public static int DOMAIN_2 = 2;
	public static int DOMAIN_3 = 4;
	public static int DOMAIN_4 = 5;
	
	private String name;
	private int id;
	
	private List<Terrain> terrainList;
	private List<Kingdom> borderList;
	
	//Numero de combates por territorios = MONTANYA,BOSQUE, LLANO, CIUDAD
	private int state;
	private int totalStates;
	
	private int target;
	public static final int TARGET_BATTLE = 0;
	public static final int TARGET_DOMAIN = 1;
	public static final int TARGET_AGGREGATION = 2;
	
	
	public Kingdom(
		int x, int y, int mapX, int mapY, int mapWidth, int mapHeight) {
		super(x, y, 
				GfxManager.imgTargetDomain.getWidth(), GfxManager.imgTargetDomain.getHeight(), 
				mapX, mapY, mapWidth, mapHeight);
		this.mapX = mapX;
		this.mapY = mapY;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		this.state = 0;
		
		terrainList = new ArrayList<Terrain>();
		borderList = new ArrayList<Kingdom>();
		
		totalStates = terrainList.size();
		
		target = -1;
	}
	
	public int getGold(){
		int gold;
		return 0;
	}
	
	public int getDificult(){
		int dificult;
		return 0;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Terrain> getTerrainList() {
		return terrainList;
	}

	public void setTerrainList(List<Terrain> terrainList) {
		this.terrainList = terrainList;
	}

	public List<Kingdom> getBorderList() {
		return borderList;
	}

	public void setBorderList(List<Kingdom> borderList) {
		this.borderList = borderList;
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	@Override
	public boolean onFocus() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onSelect() {
		// TODO Auto-generated method stub
		return false;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
