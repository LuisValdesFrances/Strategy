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



public class Map extends MapObject{
	
	private List<Player>playerList;
	private List<Kingdom> kingdomList;
	
	private int playerIndex;
	private int turnCount;
	
	private int numberPartsW;
	private int numberPartsH;
	
	public Map(
			WorldConver worldConver, GameCamera gameCamera, int x, int y, int w, int h,
			int numberPartsW, int numberPartsH) {
		super(worldConver, gameCamera, null, x, y, w, h, x, y, w, h);
		this.map = this;
		this.numberPartsW = numberPartsW;
		this.numberPartsH = numberPartsH;
	}
	
	public void update(MultiTouchHandler multiTouchHandler, float delta){
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
			k.update(multiTouchHandler);
			for (Terrain t : k.getTerrainList()) {
				t.update(multiTouchHandler);
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
	public void drawMap(Graphics g, List<Player> playerList){
		g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
		int pW = getWidth()/numberPartsW;
		int pH = getHeight()/numberPartsH;
		{
		int i = 0;
		for(int y = 0; y < numberPartsH; y++){
			for(int x = 0; x < numberPartsW; x++){
				g.drawImage(GfxManager.imgMapList.get(i),
						worldConver.getConversionDrawX(gameCamera.getPosX(), getX())+x*pW,
						worldConver.getConversionDrawY(gameCamera.getPosY(), getY())+y*pH,
						Graphics.TOP | Graphics.LEFT
						);
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
				
				//Capitales
				for(Player player : playerList){
					if(player.getCapital() != null){
						int modW = player.getCapital().getTerrainList().get(player.getCapital().getTerrainList().size()-1).getWidth()/2-
								GfxManager.imgCrown.getWidth()/3;
						int modH = player.getCapital().getTerrainList().get(player.getCapital().getTerrainList().size()-1).getHeight()/2-
								GfxManager.imgCrown.getHeight()/3;
						g.drawImage(GfxManager.imgCrown, 
							worldConver.getConversionDrawX(gameCamera.getPosX(), 
									player.getCapital().getTerrainList().get(player.getCapital().getTerrainList().size()-1).getAbsoluteX()-
									modW),
							worldConver.getConversionDrawY(gameCamera.getPosY(), 
									player.getCapital().getTerrainList().get(player.getCapital().getTerrainList().size()-1).getAbsoluteY()-
									modH),
							Graphics.HCENTER | Graphics.VCENTER);
					}
				}
				
				g.setAlpha(255);
				g.setImageSize(1f, 1f);
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
	
	public void drawTarget(Graphics g){
		
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
				g.drawImage(imgTarget, k.getTouchX(), k.getTouchY(), Graphics.VCENTER | Graphics.HCENTER);
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
}