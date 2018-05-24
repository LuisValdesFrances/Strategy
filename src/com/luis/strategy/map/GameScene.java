package com.luis.strategy.map;

import java.util.List;

import com.luis.lgameengine.gameutils.gameworld.GameCamera;
import com.luis.lgameengine.gameutils.gameworld.WorldConver;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;
import com.luis.strategy.GfxManager;
import com.luis.strategy.constants.Define;
import com.luis.strategy.constants.GameParams;


public class GameScene{
	
	
	private int map;
	private int playerIndex;
	private int turnCount;
	
	private List<Player>playerList;
	private List<Kingdom> kingdomList;
	
	private int numberPartsW;
	private int numberPartsH;
	
	private MapObject mapObject;
	
	public GameScene(
			int map,
			int mapX, int mapY,
			int numberPartsW, int numberPartsH) {
		this.map = map;
		this.numberPartsW = numberPartsW;
		this.numberPartsH = numberPartsH;
		mapObject = new MapObject(
				null,
				mapX, mapY, GfxManager.imgMapList.get(0).getWidth()*numberPartsW, GfxManager.imgMapList.get(0).getWidth()*numberPartsH,
				mapX, mapY, GfxManager.imgMapList.get(0).getWidth()*numberPartsW, GfxManager.imgMapList.get(0).getWidth()*numberPartsH,
				-1, -1) {
		};
	}
	
	public void init(){
		
	}
	
	public void update(MultiTouchHandler multiTouchHandler, WorldConver worldConver, GameCamera gameCamera, float delta){
		if(alphaFlag){
			alpha-= 60f*delta;
		}else{
			alpha+= 60f*delta;
		}
		if(alpha < 100f || alpha > 200f)
			alphaFlag = !alphaFlag;
		
		alpha = Math.max(100f, alpha);
		alpha = Math.min(200f, alpha);
		
		//Evemtos touch que chocan contra los de la GUI
		for (Kingdom k : kingdomList) {
			k.update(multiTouchHandler, worldConver, gameCamera);
			for (Terrain t : k.getTerrainList()) {
				t.update(multiTouchHandler, worldConver, gameCamera);
			}
		}
	}
	
	public void clean(){
		for (Kingdom k : kingdomList) {
			k.getButton().reset();
			for (Terrain t : k.getTerrainList()) {
				t.getButton().reset();
			}
		}
	}
	
	private boolean alphaFlag;
	private float alpha = 255;
	public void drawMap(Graphics g, WorldConver worldConver, GameCamera gameCamera, List<Player> playerList){
		g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
		int pW = GfxManager.imgMapList.get(0).getWidth();
		int pH = GfxManager.imgMapList.get(0).getHeight();
		{
		int i = 0;
		for(int y = 0; y < numberPartsH; y++){
			for(int x = 0; x < numberPartsW; x++){
				
				if(worldConver.isObjectInGameLayout(
						gameCamera.getPosX(), gameCamera.getPosY(), 
						mapObject.getX()+x*pW, mapObject.getY()+y*pH, 
						pW, pH)){
				
					g.drawImage(GfxManager.imgMapList.get(i),
							worldConver.getConversionDrawX(gameCamera.getPosX(), mapObject.getX()+x*pW),
							worldConver.getConversionDrawY(gameCamera.getPosY(), mapObject.getY()+y*pH),
							Graphics.TOP | Graphics.LEFT
							);
				}
				i++;
			}
		}
		}
		for(Kingdom k : kingdomList){
			for(int i = 0; i < k.getTerrainList().size(); i++){
				Image img = null;
				switch(k.getTerrainList().get(i).getType()){
				case GameParams.BIG_CITY : img = GfxManager.imgTerrain.get(GameParams.BIG_CITY); break;
				case GameParams.MEDIUM_CITY : img = GfxManager.imgTerrain.get(GameParams.MEDIUM_CITY); break;
				case GameParams.SMALL_CITY : img = GfxManager.imgTerrain.get(GameParams.SMALL_CITY); break;
				case GameParams.CASTLE : img = null; break;
				case GameParams.PLAIN : img = GfxManager.imgTerrain.get(GameParams.PLAIN); break;
				case GameParams.FOREST : img = GfxManager.imgTerrain.get(GameParams.FOREST); break;
				case GameParams.MONTAIN : img = GfxManager.imgTerrain.get(GameParams.MONTAIN); break;
				}
				
				if(k.getTerrainList().get(i).getButton().isTouching())
					g.setImageSize(1.25f, 1.25f);
				
				if(i < k.getState()){
					g.setAlpha(100);
				}
				
				g.drawImage(img, 
					worldConver.getConversionDrawX(gameCamera.getPosX(), k.getTerrainList().get(i).getAbsoluteX()),
					worldConver.getConversionDrawY(gameCamera.getPosY(), k.getTerrainList().get(i).getAbsoluteY()),
					Graphics.VCENTER | Graphics.HCENTER);
				
				g.setAlpha(255);
				g.setImageSize(1f, 1f);
			}
			
			//Capitales
			for(Player player : playerList){
				if(player.getCapitalkingdom() != null){
					int modW = player.getCapitalkingdom().getTerrainList().get(player.getCapitalkingdom().getTerrainList().size()-1).getWidth()/2-
							GfxManager.imgCrown.getWidth()/3;
					int modH = player.getCapitalkingdom().getTerrainList().get(player.getCapitalkingdom().getTerrainList().size()-1).getHeight()/2-
							GfxManager.imgCrown.getHeight()/3;
					g.drawImage(GfxManager.imgCrown, 
						worldConver.getConversionDrawX(gameCamera.getPosX(), 
								player.getCapitalkingdom().getTerrainList().get(player.getCapitalkingdom().getTerrainList().size()-1).getAbsoluteX()-
								modW),
						worldConver.getConversionDrawY(gameCamera.getPosY(), 
								player.getCapitalkingdom().getTerrainList().get(player.getCapitalkingdom().getTerrainList().size()-1).getAbsoluteY()-
								modH),
						Graphics.HCENTER | Graphics.VCENTER);
				}
			}
			
			//OK
			for(int i = 0; i < k.getState(); i++){
				g.drawImage(GfxManager.imgTerrainOk,
					worldConver.getConversionDrawX(gameCamera.getPosX(), (k.getTerrainList().get(i).getAbsoluteX()+
							GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth()*0.30f)),
					worldConver.getConversionDrawY(gameCamera.getPosY(), (k.getTerrainList().get(i).getAbsoluteY()+
							GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight()*0.30f)),
					Graphics.VCENTER | Graphics.HCENTER);
			}
		}
	}
	
	public void drawTarget(Graphics g, WorldConver worldConver, GameCamera gameCamera){
		
		g.setClip(0, 0, Define.SIZEX, Define.SIZEX);
		for(Kingdom k : kingdomList){
			
			if(k.getTarget() != -1){
				
				Image imgTarget = null;
				
				switch(k.getTarget()){
				case Kingdom.TARGET_BATTLE:
					imgTarget = GfxManager.imgTargetBattle;
					break;
				case Kingdom.TARGET_DOMAIN:
					imgTarget = GfxManager.imgTargetDomain;
					break;
				case Kingdom.TARGET_AGGREGATION:
					imgTarget = GfxManager.imgTargetAggregation;
					break;
				}
				
				g.setAlpha((int)alpha);
				g.drawImage(imgTarget, k.getTouchX(worldConver, gameCamera), k.getTouchY(worldConver, gameCamera), Graphics.VCENTER | Graphics.HCENTER);
				g.setAlpha(255);
			}
		}
	}
	
	public Kingdom getKingdom(int id){
		Kingdom kingdom = null;
		for(int i = 0; kingdom == null && i < kingdomList.size(); i++){
			if(kingdomList.get(i).getId() == id)
				kingdom= kingdomList.get(i);
		}
		/*
		for(Kingdom k : kingdomList){
			if(k.getId() == id){
				kingdom = k;
				break;
			}
		}
		*/
		return kingdom;
	}

	
	public int getMap() {
		return map;
	}

	public void setMap(int map) {
		this.map = map;
	}

	public List<Player> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(List<Player> playerList) {
		this.playerList = playerList;
	}

	public List<Kingdom> getKingdomList() {
		return kingdomList;
	}

	public void setKingdomList(List<Kingdom> kingdomList) {
		this.kingdomList = kingdomList;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public int getTurnCount() {
		return turnCount;
	}

	public void setTurnCount(int turnCount) {
		this.turnCount = turnCount;
	}

	public MapObject getMapObject() {
		return mapObject;
	}

	public void setMapObject(MapObject mapObject) {
		this.mapObject = mapObject;
	}

	public int getNumberPartsW() {
		return numberPartsW;
	}

	public void setNumberPartsW(int numberPartsW) {
		this.numberPartsW = numberPartsW;
	}

	public int getNumberPartsH() {
		return numberPartsH;
	}

	public void setNumberPartsH(int numberPartsH) {
		this.numberPartsH = numberPartsH;
	}

	
	
	
	
}