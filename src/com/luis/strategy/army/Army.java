package com.luis.strategy.army;

import java.util.ArrayList;
import java.util.List;

import com.luis.lgameengine.gameutils.controls.GameControl;
import com.luis.lgameengine.gameutils.gameworld.GameCamera;
import com.luis.lgameengine.gameutils.gameworld.Math2D;
import com.luis.lgameengine.gameutils.gameworld.SpriteImage;
import com.luis.lgameengine.gameutils.gameworld.WorldConver;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.strategy.GfxManager;
import com.luis.strategy.Main;
import com.luis.strategy.constants.Define;
import com.luis.strategy.constants.GameParams;
import com.luis.strategy.game.Player;
import com.luis.strategy.map.Kingdom;
import com.luis.strategy.map.Map;
import com.luis.strategy.map.MapObject;
import com.luis.strategy.map.Terrain;

public class Army extends MapObject{
	
	public Player player;
	private static int idCount;
	private int id;
	
	public boolean defeat;
	
	private List<Troop> troopList;
	private Kingdom lastKingdom;
	private Kingdom kingdom;
	
	private int state;
	public static final int STATE_ON = 0;
	public static final int STATE_MOVE = 1;
	public static final int STATE_OFF = 2;
	
	
	private boolean flip;
	public int anim;
	public static final int ANIN_IDLE=0;
	public static final int ANIN_MOVE=1;
	
	private static final float SPEED = 10f;
	
	private int idleRepeatCount;
	
	private List<SpriteImage> spriteList;
	
	private int flag;
	
	public Army (WorldConver worldConver, GameCamera gameCamera, Map map,
			Player player,
			Kingdom kingdom, int flag, float mapX, float mapY, int mapWidth, int mapHeight) {
		
		super(worldConver, gameCamera, map,
			kingdom.getX(), kingdom.getY(),
			GfxManager.imgArmyIdle.getWidth()/9, GfxManager.imgArmyIdle.getHeight(), 
			mapX, mapY, mapWidth, mapHeight);
		this.id = idCount++;
		this.player = player;
		this.troopList = new ArrayList<Troop>();
		this.kingdom = kingdom;
		this.flag = flag;
		this.state = STATE_ON;
		spriteList = new ArrayList<SpriteImage>();
		spriteList.add(new SpriteImage(GfxManager.imgArmyIdle.getWidth(), GfxManager.imgArmyIdle.getHeight(), 0.25f, 9));	
		spriteList.add(new SpriteImage(GfxManager.imgArmyRun.getWidth(), GfxManager.imgArmyRun.getHeight(), 0.25f, 8));
		
		//Añado el minimo de tropas
		for(int i = 0; i < GameParams.TROOP_START.length; i++){
			for(int j = 0; j < GameParams.TROOP_START[i]; j++){
				getTroopList().add(new Troop(i, true));
			}
		}
		
		anim = ANIN_IDLE;
	}
	
	public void update(float delta){
		spriteList.get(anim).updateAnimation(delta);
		
		switch(state){
		case STATE_ON:
		case STATE_OFF:
			if(idleRepeatCount > 0){
				if(spriteList.get(anim).isEndAnimation()){
					spriteList.get(anim).setFrame(4);
					idleRepeatCount--;
				}
			}else{
				idleRepeatCount = Main.getRandom(6, 18);
				spriteList.get(anim).setFrame(0);
			}
			break;
		case STATE_MOVE:
			
			float angle = Math2D.getAngle360(
				getAbsoluteX(), getAbsoluteY(), 
				kingdom.getAbsoluteX(), kingdom.getAbsoluteY());
			double cos = Math.cos(angle * (Math.PI/180f));
			double sin = Math.sin(angle * (Math.PI/180f));
			
			float speedX = (float)((SPEED*cos)*delta);
			float speedY = (float)((SPEED*sin)*delta);
			setX(getX()+speedX);
			setY(getY()-speedY);
			break;
		}
	}
	
	public void draw(Graphics g, boolean isSelected, boolean isActive){
		
		g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
		
		if(isSelected){
			g.setAlpha((int)map.getAlpha());
			g.drawImage(
					isActive?GfxManager.imgMapSelectGreen:GfxManager.imgMapSelectRed, 
					worldConver.getConversionDrawX(gameCamera.getPosX(), getAbsoluteX()),
					worldConver.getConversionDrawY(gameCamera.getPosY(), getAbsoluteY())+height/3,
					
					Graphics.VCENTER | Graphics.HCENTER);
			g.setAlpha(255);
		}
		
		
		
		if(state == STATE_OFF){
			g.setAlpha(140);
		}
		g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
		float flagX = getAbsoluteX() + 
			(int)(flip?-GfxManager.imgFlagSmallList.get(flag).getWidth()*0.5:
					   -GfxManager.imgFlagSmallList.get(flag).getWidth()*0.25);
		float flagY = getAbsoluteY()-GfxManager.imgFlagSmallList.get(flag).getHeight();
		
		int angle = flip?15:-15;
		
		g.drawRegion(GfxManager.imgFlagSmallList.get(flag), 
				worldConver.getConversionDrawX(gameCamera.getPosX(), (int)flagX),
				worldConver.getConversionDrawY(gameCamera.getPosY(), (int)flagY),
				0, 0, 
				GfxManager.imgFlagSmallList.get(flag).getWidth(), GfxManager.imgFlagSmallList.get(flag).getHeight(), 
				angle, 
				worldConver.getConversionDrawX(gameCamera.getPosX(), (int)flagX+GfxManager.imgFlagSmallList.get(flag).getWidth()/2), 
				worldConver.getConversionDrawY(gameCamera.getPosY(), (int)flagY+GfxManager.imgFlagSmallList.get(flag).getHeight()/2));
		
		//g.drawImage(GfxManager.imgFlagSmallList.get(flag), flagX, flagY, Graphics.HFLIP);
		
		switch(anim){
		case ANIN_IDLE:
			spriteList.get(anim).drawFrame(g, GfxManager.imgArmyIdle, 
					worldConver.getConversionDrawX(gameCamera.getPosX(), getAbsoluteX()),
					worldConver.getConversionDrawY(gameCamera.getPosY(), getAbsoluteY()),
					flip, Graphics.VCENTER | Graphics.HCENTER);
			break;
		case ANIN_MOVE:
			spriteList.get(anim).drawFrame(g, GfxManager.imgArmyRun,
					worldConver.getConversionDrawX(gameCamera.getPosX(), getAbsoluteX()),
					worldConver.getConversionDrawY(gameCamera.getPosY(), getAbsoluteY()),
					flip, Graphics.VCENTER | Graphics.HCENTER);
			break;
		}
		g.setAlpha(255);
		/*
		else{
			g.drawImage(GfxManager.imgArmyOff, 
					getAbsoluteX()+GfxManager.imgArmyOff.getWidth()/2, 
					getAbsoluteY()-GfxManager.imgArmyOff.getHeight()/2,
					Graphics.HFLIP);
		}
		*/
	}
	
	public int getCombat(int terrain){
		return 0;
	}
	
	public int getCost(){
		return 0;
	}
	
	public void changeState(int newState){
		switch(newState){
		case STATE_ON: 
			anim = ANIN_IDLE;
			spriteList.get(anim).resetAnimation(0);
			break;
		case STATE_MOVE: 
			anim = ANIN_MOVE;
			spriteList.get(anim).resetAnimation(0);
			
			flip = lastKingdom.getAbsoluteX() > kingdom.getAbsoluteX(); 
			break;
		case STATE_OFF:
			anim = ANIN_IDLE;
			spriteList.get(anim).resetAnimation(0);
			break;
		}
		state = newState;
		
	}
	
	public int getNumberTroops(int type){
		int n = 0;
		for(int i = 0; i < troopList.size(); i++){
			if(troopList.get(i).getType() == type)
				n++;
		}
		return n;
	}
	
	public int getPower(Terrain terrain){
		int power = 0;
		for(int i = 0; i < troopList.size(); i++){
			switch(troopList.get(i).getType()){
			case GameParams.INFANTRY:
				power += GameParams.INFANTRY_COMBAT[terrain.getType()];
				break;
			case GameParams.KNIGHT:
				power += GameParams.KNIGHTS_COMBAT[terrain.getType()];
				break;
			case GameParams.HARASSERES:
				power += GameParams.HARASSERS_COMBAT[terrain.getType()];
				break;
			case GameParams.SIEGE:
				power += GameParams.SIEGE_COMBAT[terrain.getType()];
				break;
			}
		}
		return power;
	}

	public int getState() {
		return state;
	}

	public Kingdom getKingdom() {
		return kingdom;
	}

	public void setKingdom(Kingdom kingdom) {
		this.kingdom = kingdom;
	}

	
	public Kingdom getLastKingdom() {
		return lastKingdom;
	}

	public void setLastKingdom(Kingdom lastKingdom) {
		this.lastKingdom = lastKingdom;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isDefeat() {
		return defeat;
	}

	public void setDefeat(boolean defeat) {
		this.defeat = defeat;
	}

	public List<Troop> getTroopList() {
		return troopList;
	}

	public void setTroopList(List<Troop> troopList) {
		this.troopList = troopList;
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public boolean discardTroop(Troop troop){
		boolean discard = false;
		for(int i = 0; i < troopList.size(); i++){
			if(troop.getId() == troopList.get(i).getId()){
				troopList.remove(i);
				discard = true;
				break;
			}
		}
		return discard;
	}
	
	

}
