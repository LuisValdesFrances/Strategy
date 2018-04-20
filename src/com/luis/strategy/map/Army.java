package com.luis.strategy.map;

import java.util.ArrayList;
import java.util.List;

import com.luis.lgameengine.gameutils.gameworld.GameCamera;
import com.luis.lgameengine.gameutils.gameworld.Math2D;
import com.luis.lgameengine.gameutils.gameworld.SpriteImage;
import com.luis.lgameengine.gameutils.gameworld.WorldConver;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;
import com.luis.strategy.GfxManager;
import com.luis.strategy.Main;
import com.luis.strategy.constants.Define;
import com.luis.strategy.constants.GameParams;

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
	public static final int STATE_ATACK = 2;
	public static final int STATE_OFF = 3;
	
	
	private boolean flip;
	public int anim;
	public static final int ANIN_IDLE = 0;
	public static final int ANIN_MOVE = 1;
	public static final int ANIN_ATACK = 2;
	
	private static final float SPEED = 10f;
	
	private float idleCount;
	private float idleWait;
	
	private List<SpriteImage> spriteList;
	
	private int flag;
	
	//IA
	private IADecision iaDecision;
	
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
		spriteList.add(new SpriteImage(GfxManager.imgArmyIdle.getWidth(), GfxManager.imgArmyIdle.getHeight(), 0.10f, 7));	
		spriteList.add(new SpriteImage(GfxManager.imgArmyRun.getWidth(), GfxManager.imgArmyRun.getHeight(), 0.12f, 8));
		spriteList.add(new SpriteImage(GfxManager.imgArmyAtack.getWidth(), GfxManager.imgArmyRun.getHeight(), 0.09f, 7));
		
		if(getPlayer().getActionIA() != null){
			iaDecision = new IADecision();
		}
		
		//Añado el minimo de tropas
		for(int i = 0; i < GameParams.TROOP_START.length; i++){
			for(int j = 0; j < GameParams.TROOP_START[i]; j++){
				getTroopList().add(new Troop(i, true));
			}
		}
		
		anim = ANIN_IDLE;
	}
	
	public void update(MultiTouchHandler multiTouchHandler, float delta){
		spriteList.get(anim).updateAnimation(delta);
		
		switch(state){
		case STATE_ON:
			super.update(multiTouchHandler);
			if(idleCount < idleWait){
				idleCount+= delta;
				spriteList.get(anim).setFrame(0);
			}else{
				if(spriteList.get(anim).isEndAnimation()){
					idleWait = Main.getRandom(1, 5);
					idleCount = 0;
				}
			}
			break;
		case STATE_OFF:
			spriteList.get(anim).setFrame(0);
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
		case STATE_ATACK:
			if(spriteList.get(anim).isEndAnimation()){
				changeState(STATE_OFF);
			}
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
		
		float flagX;
		if(state != STATE_MOVE && state != STATE_ATACK){
			flagX = (float) (getAbsoluteX()
				-GfxManager.imgFlagSmallList.get(flag).getWidth()*0.75);
		}else{
			flagX = getAbsoluteX()
				+
				(int)(flip?
				-GfxManager.imgFlagSmallList.get(flag).getWidth()*0.25:
				-GfxManager.imgFlagSmallList.get(flag).getWidth()*0.75);
		}
					  
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
		/*
		g.drawImage(GfxManager.imgFlagSmallList.get(flag), 
				worldConver.getConversionDrawX(gameCamera.getPosX(), (int)flagX+GfxManager.imgFlagSmallList.get(flag).getWidth()/2), 
				worldConver.getConversionDrawY(gameCamera.getPosY(), (int)flagY+GfxManager.imgFlagSmallList.get(flag).getHeight()/2), 
				Graphics.VCENTER |Graphics.HCENTER);
		*/
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
		case ANIN_ATACK:
			spriteList.get(anim).drawFrame(g, GfxManager.imgArmyAtack,
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
	
	public void changeState(int newState){
		switch(newState){
		case STATE_ON: 
			anim = ANIN_IDLE;
			spriteList.get(anim).resetAnimation(0);
			flip = false;
			break;
		case STATE_MOVE: 
			anim = ANIN_MOVE;
			spriteList.get(anim).resetAnimation(0);
			flip = lastKingdom.getAbsoluteX() > kingdom.getAbsoluteX(); 
			break;
		case STATE_ATACK: 
			anim = ANIN_ATACK;
			spriteList.get(anim).resetAnimation(0);
			break;
		case STATE_OFF:
			anim = ANIN_IDLE;
			spriteList.get(anim).resetAnimation(0);
			flip = lastKingdom.getAbsoluteX() > kingdom.getAbsoluteX();
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
	
	public int getCost(){
		int cost = 0;
		for(int i = 0; i < troopList.size(); i++){
			cost +=  GameParams.TROOP_COST [troopList.get(i).getType()];
		}
		return cost;
	}
	
	public void setDamage(int costTarget){
		int costCount = 0;
		for(int i = 0; i < troopList.size() && costCount < costTarget; i++){
			//Maquinas de asedio
			if(!troopList.get(i).isSubject() && troopList.get(i).getType() == GameParams.SIEGE){
				costCount+= GameParams.TROOP_COST[GameParams.SIEGE];
				troopList.remove(i);
				i--;
			}
		}
		
		for(int i = 0; i < troopList.size() && costCount < costTarget; i++){
			//Maquinas de asedio
			if(!troopList.get(i).isSubject() && troopList.get(i).getType() == GameParams.HARASSERES){
				costCount+= GameParams.TROOP_COST[GameParams.HARASSERES];
				troopList.remove(i);
				i--;
			}
		}
		
		for(int i = 0; i < troopList.size() && costCount < costTarget; i++){
			//Maquinas de asedio
			if(!troopList.get(i).isSubject() && troopList.get(i).getType() == GameParams.KNIGHT){
				costCount+= GameParams.TROOP_COST[GameParams.KNIGHT];
				troopList.remove(i);
				i--;
			}
		}
		
		for(int i = 0; i < troopList.size() && costCount < costTarget; i++){
			//Maquinas de asedio
			if(!troopList.get(i).isSubject() && troopList.get(i).getType() == GameParams.INFANTRY){
				costCount+= GameParams.TROOP_COST[GameParams.INFANTRY];
				troopList.remove(i);
				i--;
			}
		}
	}

	public int getState() {
		return state;
	}
	
	public void setState(int state) {
		this.state = state;
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
	
	

	public IADecision getIaDecision() {
		return iaDecision;
	}

	public void setIaDecision(IADecision iaDecision) {
		this.iaDecision = iaDecision;
	}

	public void discardTroop(int discardNumber){
		int discardCount = 0;
		
		//Obtengo el minimo de tropas
		int min = 0;
		for(int i = 0; i < GameParams.TROOP_START[i]; i++){
			min+=GameParams.TROOP_START[i];
		}
		
		for(int i = 0; 
			troopList.size() > min && discardCount > discardNumber; i++){
		
				if(!troopList.get(i).isSubject()){
				troopList.remove(i);
				discardCount++;
			}
		}
	}
	

	public class IADecision{
		private int decision;
		private int kingdomDecision;
		
		public int getDecision() {
			return decision;
		}

		public void setDecision(int decision) {
			this.decision = decision;
		}
		
		public int getKingdomDecision() {
			return kingdomDecision;
		}

		public void setKingdomDecision(int kingdomDecision) {
			this.kingdomDecision = kingdomDecision;
		}
	}
}
