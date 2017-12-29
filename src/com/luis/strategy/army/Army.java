package com.luis.strategy.army;

import java.util.ArrayList;
import java.util.List;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.gameutils.gameworld.Math2D;
import com.luis.lgameengine.gameutils.gameworld.SpriteImage;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.strategy.GfxManager;
import com.luis.strategy.Main;
import com.luis.strategy.constants.Define;
import com.luis.strategy.map.Kingdom;
import com.luis.strategy.map.MapObject;

public class Army extends MapObject{
	
	private static int idCount;
	private int id;
	
	public boolean controller;
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
	
	private float angle;
	private double sin;
	private double cos;
	private static final float SPEED = 10f;
	
	private int idleRepeatCount;
	
	private List<SpriteImage> spriteList;
	
	private int flag;
	
	public Army (Kingdom kingdom, int flag, int mapX, int mapY, int mapWidth, int mapHeight) {
		super(
				kingdom.getX(), kingdom.getY(),
				GfxManager.imgArmyIdle.getWidth()/9, GfxManager.imgArmyIdle.getHeight(), 
				mapX, mapY, mapWidth, mapHeight);
		
		this.id = idCount++;
		this.kingdom = kingdom;
		this.flag = flag;
		this.state = STATE_ON;
		spriteList = new ArrayList<SpriteImage>();
		spriteList.add(new SpriteImage(GfxManager.imgArmyIdle.getWidth(), GfxManager.imgArmyIdle.getHeight(), 0.25f, 9));	
		spriteList.add(new SpriteImage(GfxManager.imgArmyRun.getWidth(), GfxManager.imgArmyRun.getHeight(), 0.25f, 8));
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
			
			float speedX = (float)((SPEED*cos)*delta);
			float speedY = (float)((SPEED*sin)*delta);
			setX(getX()+speedX);
			setY(getY()-speedY);
			break;
		}
	}
	
	public void draw(Graphics g){
		
		if(state == STATE_OFF){
			g.setAlpha(140);
		}
		g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
		int flagX = getAbsoluteX() + 
			(int)(flip?-GfxManager.imgFlagSmallList.get(flag).getWidth()*0.5:
					   -GfxManager.imgFlagSmallList.get(flag).getWidth()*0.25);
		int flagY = getAbsoluteY()-GfxManager.imgFlagSmallList.get(flag).getHeight();
		
		int angle = flip?15:-15;
		
		g.drawRegion(GfxManager.imgFlagSmallList.get(flag), flagX, flagY, 0, 0, 
				GfxManager.imgFlagSmallList.get(flag).getWidth(), GfxManager.imgFlagSmallList.get(flag).getHeight(), 
				angle, flagX+GfxManager.imgFlagSmallList.get(flag).getWidth()/2, flagY+GfxManager.imgFlagSmallList.get(flag).getHeight()/2);
		
		//g.drawImage(GfxManager.imgFlagSmallList.get(flag), flagX, flagY, Graphics.HFLIP);
		
		switch(anim){
		case ANIN_IDLE:
			spriteList.get(anim).drawFrame(g, GfxManager.imgArmyIdle, getAbsoluteX(), getAbsoluteY(), flip, Graphics.VCENTER | Graphics.HCENTER);
			break;
		case ANIN_MOVE:
			spriteList.get(anim).drawFrame(g, GfxManager.imgArmyRun, getAbsoluteX(), getAbsoluteY(), flip, Graphics.VCENTER | Graphics.HCENTER);
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
			
			angle = Math2D.getAngle360(
				lastKingdom.getAbsoluteX(), lastKingdom.getAbsoluteY(), kingdom.getAbsoluteX(), kingdom.getAbsoluteY());
			cos = Math.cos(angle * (Math.PI/180f));
			sin = Math.sin(angle * (Math.PI/180f));
			break;
		case STATE_OFF:
			anim = ANIN_IDLE;
			spriteList.get(anim).resetAnimation(0);
			break;
		}
		state = newState;
		
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

	public boolean isController() {
		return controller;
	}

	public void setController(boolean controller) {
		this.controller = controller;
	}

	public boolean isDefeat() {
		return defeat;
	}

	public void setDefeat(boolean defeat) {
		this.defeat = defeat;
	}
	
	
	
	

}
