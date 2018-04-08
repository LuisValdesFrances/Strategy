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
import com.luis.strategy.game.Player;



public class Map extends MapObject{
	
	private List<Kingdom> kingdomList;
	private Image imgMap;
	private Image imgSmallCity;
	private Image imgMediumCity;
	private Image imgBigCity;
	private Image imgPlain;
	private Image imgForest;
	private Image imgMontain;
	private Image imgCastle;
	private Image imgCrown;
	
	private boolean alphaFlag;
	private float alpha = 255;
	
	
	public Map(
			WorldConver worldConver, GameCamera gameCamera,
			int x, int y,
			Image imgMap, Image imgSmallCity,
			Image imgMediumCity, Image imgBigCity, Image imgPlain,
			Image imgForest, Image imgMontain, Image imgCastle,
			Image imgCrown) {
		super(worldConver, gameCamera, null, x, y, imgMap.getWidth(), imgMap.getHeight(), x, y, imgMap.getWidth(), imgMap.getHeight());
		
		this.map = this;
		this.x = x;
		this.y = y;
		
		this.imgMap = imgMap;
		this.imgSmallCity = imgSmallCity;
		this.imgMediumCity = imgMediumCity;
		this.imgBigCity = imgBigCity;
		this.imgPlain = imgPlain;
		this.imgForest = imgForest;
		this.imgMontain = imgMontain;
		this.imgCastle = imgCastle;
		this.imgCrown = imgCrown;
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
	
	public void drawMap(Graphics g, List<Player> playerList){
		g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
		g.drawImage(imgMap, 
				worldConver.getConversionDrawX(gameCamera.getPosX(), x),
				worldConver.getConversionDrawY(gameCamera.getPosY(), y),
				Graphics.TOP | Graphics.LEFT
				);
		
		for(Kingdom k : kingdomList){
			for(int i = 0; i < k.getTerrainList().size(); i++){
				Image img = null;
				switch(k.getTerrainList().get(i).getType()){
				case GameParams.BIG_CITY : img = imgBigCity; break;
				case GameParams.MEDIUM_CITY : img = imgMediumCity; break;
				case GameParams.SMALL_CITY : img = imgSmallCity; break;
				case GameParams.CASTLE : img = imgCastle; break;
				case GameParams.PLAIN : img = imgPlain; break;
				case GameParams.FOREST : img = imgForest; break;
				case GameParams.MONTAIN : img = imgMontain; break;
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
						int modW = player.getCapital().getTerrainList().get(player.getCapital().getTerrainList().size()-1).getWidth()/2-imgCrown.getWidth()/3;
						int modH = player.getCapital().getTerrainList().get(player.getCapital().getTerrainList().size()-1).getHeight()/2-imgCrown.getHeight()/3;
						g.drawImage(imgCrown, 
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

	
	public List<Kingdom> getKingdomList() {
		return kingdomList;
	}

	public void setKingdomList(List<Kingdom> kingdomList) {
		this.kingdomList = kingdomList;
	}

	public Image getImgMap() {
		return imgMap;
	}

	public void setImgMap(Image imgMap) {
		this.imgMap = imgMap;
	}
	
	

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
}