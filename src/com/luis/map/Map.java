package com.luis.map;

import java.util.List;

import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.strategy.GfxManager;
import com.luis.strategy.constants.GameParams;



public class Map {
	
	private int x;
	private int y;
	
	private List<Kingdom> kingdomList;
	private Image imgMap;
	private Image imgSmallCity;
	private Image imgMediumCity;
	private Image imgBigCity;
	private Image imgPlain;
	private Image imgForest;
	private Image imgMontain;
	private Image imgCastle;
	
	private boolean alphaFlag;
	private float alpha = 255;
	
	
	public Map(int x, int y,
			List<Kingdom> kingdomList, Image imgMap, Image imgSmallCity,
			Image imgMediumCity, Image imgBigCity, Image imgPlain,
			Image imgForest, Image imgMontain, Image imgCastle) {
		super();
		this.x = x;
		this.y = y;
		this.kingdomList = kingdomList;
		this.imgMap = imgMap;
		this.imgSmallCity = imgSmallCity;
		this.imgMediumCity = imgMediumCity;
		this.imgBigCity = imgBigCity;
		this.imgPlain = imgPlain;
		this.imgForest = imgForest;
		this.imgMontain = imgMontain;
		this.imgCastle = imgCastle;
	}
	
	public void update(float delta){
		if(alphaFlag){
			alpha-= 60f*delta;
		}else{
			alpha+= 60f*delta;
		}
		if(alpha < 100f || alpha > 200f)
			alphaFlag = !alphaFlag;
		
		alpha = Math.max(100f, alpha);
		alpha = Math.min(200f, alpha);
	}
	public void draw(Graphics g){
		
		g.drawImage(imgMap, x, y, Graphics.VCENTER | Graphics.HCENTER);
		
		for(Kingdom k : kingdomList){
			
			
			
			
			for(Terrain t : k.getTerrainList()){
				Image img = null;
				switch(t.getType()){
				case GameParams.BIG_CITY : img = imgBigCity; break;
				case GameParams.MEDIUM_CITY : img = imgMediumCity; break;
				case GameParams.SMALL_CITY : img = imgSmallCity; break;
				case GameParams.CASTLE : img = imgCastle; break;
				case GameParams.PLAIN : img = imgPlain; break;
				case GameParams.FOREST : img = imgForest; break;
				case GameParams.MONTAIN : img = imgMontain; break;
				}
				
				if(t.isFocus())
					g.setImageSize(1.25f, 1.25f);
				g.drawImage(img, 
						t.getAbsoluteX(),
						t.getAbsoluteY(),
						Graphics.VCENTER | Graphics.HCENTER);
				g.setImageSize(1f, 1f);
			}
			
			if(k.getTarget() != -1){
				
				int x = 0;
				int y = 0;
				Image imgTarget = null;
				
				switch(k.getTarget()){
				case Kingdom.TARGET_BATTLE:
					imgTarget = GfxManager.imgTargetBattle;
					x = k.getTerrainList().get(k.getState()).getAbsoluteX();
					y = k.getTerrainList().get(k.getState()).getAbsoluteY();
					break;
				case Kingdom.TARGET_DOMAIN:
					imgTarget = GfxManager.imgTargetDomain;
					x = k.getAbsoluteX();
					y = k.getAbsoluteY();
					break;
				case Kingdom.TARGET_AGGREGATION:
					imgTarget = GfxManager.imgTargetAggregation;
					x = k.getAbsoluteX();
					y = k.getAbsoluteY();
					break;
				}
				x = k.getAbsoluteX();
				y = k.getAbsoluteY();
				
				g.setAlpha((int)alpha);
				g.drawImage(imgTarget,
						x,
						y,
						Graphics.VCENTER | Graphics.HCENTER);
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

	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
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
}