package com.luis.strategy.game;

import java.util.List;

import android.util.Log;

import com.luis.army.Army;
import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.menu.Button;
import com.luis.map.Kingdom;
import com.luis.map.Map;
import com.luis.strategy.GfxManager;
import com.luis.strategy.Main;
import com.luis.strategy.UserInput;
import com.luis.strategy.constants.Define;

public class GameManager {
	
	private int turnCount = 0;
	private static Button btnNext;
	private static Button btnCancel;
	
	private Map map;
	
	private int currentPlayer;
	private List<Player>playerList;
	
	private Army activeArmy;
	
	private int state;
	public static final int STATE_INCOME = 0;
	public static final int STATE_PAY = 1;
	public static final int STATE_MANAGEMENT = 2;
	public static final int STATE_ACTION = 3;
	
	
	//SUB-STATE MANAGEMENT
	public static final int SUB_STATE_MANAGEMENT_PRESENTATION = 0;
	public static final int SUB_STATE_MANAGEMENT_SELECT= 1;
	
	//SUB-STATE ACTION
	public int subState;
	public static final int SUB_STATE_ACTION_PRESENTATION = 0;
	public static final int SUB_STATE_ACTION_WAIT = 1;
	public static final int SUB_STATE_ACTION_ARMY_SELECT = 2;
	public static final int SUB_STATE_ACTION_ARMY_MOVE = 3;
	
	private boolean nextAvailable;
	private boolean cancelAvailable;
	
	public GameManager(Map map, List<Player> playerList){
		this.map = map;
		this.playerList = playerList;
		
		
		btnNext = new Button(
				GfxManager.imgButtonNextRelease, 
				GfxManager.imgButtonNextFocus, 
				Define.SIZEX-GfxManager.imgButtonNextRelease.getWidth(), 
				Define.SIZEY-GfxManager.imgButtonNextRelease.getHeight(),
				null, 0){
			@Override
			public void onButtonPressDown(){}
			
			@Override
			public void onButtonPressUp(){
				nextState();
				reset();
			}
		};
		
		btnCancel = new Button(
				GfxManager.imgButtonCancelRelease, 
				GfxManager.imgButtonCancelFocus, 
				GfxManager.imgButtonCancelRelease.getWidth(), 
				Define.SIZEY-GfxManager.imgButtonCancelRelease.getHeight(),
				null, 0){
			@Override
			public void onButtonPressDown(){}
			
			@Override
			public void onButtonPressUp(){
				cancelAction();
				reset();
			}
		};
		
		turnCount = 1;
		startPresentation(Font.FONT_BIG, "PLAYER " + (currentPlayer+1));
	}
	
	public void update(float delta){
		
		cancelAvailable = false;
		nextAvailable = false;
		
		switch(state){
		case STATE_INCOME:
			if(!updatePresentation(delta)){
				//Calculo de cosas
				nextState();
			}
			break;
		case STATE_PAY:
			
			nextState();
			break;
		case STATE_MANAGEMENT:
			switch(subState){
			case SUB_STATE_MANAGEMENT_PRESENTATION:
				if(!updatePresentation(delta))
					subState = SUB_STATE_MANAGEMENT_SELECT;
				break;
				
			case SUB_STATE_MANAGEMENT_SELECT:
				nextAvailable = true;
				break;
			}
			break;
		case STATE_ACTION:
			switch(subState){
			case SUB_STATE_ACTION_PRESENTATION:
				if(!updatePresentation(delta))
					subState = SUB_STATE_ACTION_WAIT;
				
				break;
			case SUB_STATE_ACTION_WAIT:
				nextAvailable = true;
				
				//Atcualizar interacion terreno:
				
				//Actualizar animaciones
				playerList.get(currentPlayer).updateAnimations(Main.getDeltaSec());
				
				for(Army army: playerList.get(currentPlayer).getArmyList()){
					if(army.getState() == Army.STATE_ON && army.isFocus()){
						
						subState = SUB_STATE_ACTION_ARMY_SELECT;
						activeArmy = army;
						
						Log.i("Debug", "Seleccionado ejercito de: " + activeArmy.getKingdom().getName());
						
						//
						for(Kingdom border : army.getKingdom().getBorderList()){
							for(Kingdom kingdom: map.getKingdomList()){
								if(kingdom.getTarget()== -1 && kingdom.getId() == border.getId()){
									
									//Busco si poseo el territorio
									if(playerList.get(currentPlayer).hasKingom(kingdom)){
										
										//Busco si hay un ejercito amigo en la zona
										if(playerList.get(currentPlayer).getArmy(kingdom) != null){
											kingdom.setTarget(Kingdom.TARGET_AGGREGATION);
										}else{
											kingdom.setTarget(Kingdom.TARGET_DOMAIN);
										}
										
										
									}else{
										//Busco si hay un ejercito amigo en la zona
										if(playerList.get(currentPlayer).getArmy(kingdom) != null){
											kingdom.setTarget(Kingdom.TARGET_AGGREGATION);
										}else{
											kingdom.setTarget(Kingdom.TARGET_BATTLE);
										}
									}
								}
							}
						}
					}
				}
				
				break;
				
			case SUB_STATE_ACTION_ARMY_SELECT:
				cancelAvailable = true;
				for(Kingdom kingdom: map.getKingdomList()){
					if(kingdom.getTarget()!= -1 && kingdom.isSelect()){
						Log.i("Debug", "Seleccionado reino: " + kingdom.getName());
						activeArmy.setLastKingdom(activeArmy.getKingdom());
						activeArmy.setTargetKingdom(kingdom);
						activeArmy.changeState(Army.STATE_MOVE);
						
						//Poner target en army
						
						subState = SUB_STATE_ACTION_ARMY_MOVE;
						
						
						//Quito todos lo indicadores de target
						for(Kingdom k: map.getKingdomList()){
							if(k.getId() != activeArmy.getTargetKingdom().getId()){
								k.setTarget(-1);
							}
						}
						
						
						break;
					}
				}
				
				
				
				
				break;
				
			case SUB_STATE_ACTION_ARMY_MOVE:
				playerList.get(currentPlayer).updateAnimations(Main.getDeltaSec());
				
				//Colision
				int x1 = activeArmy.getAbsoluteX();
				int y1 = activeArmy.getAbsoluteY();
				int w1 = GfxManager.imgArmyIdle.getWidth()/36;
				int h1 = GfxManager.imgArmyIdle.getHeight()/4;
				int x2 = activeArmy.getTargetKingdom().getAbsoluteX();
				int y2 = activeArmy.getTargetKingdom().getAbsoluteY();
				int w2 = GfxManager.imgTargetDomain.getWidth()/4;
				int h2 = GfxManager.imgTargetDomain.getHeight()/4;
				
				if(x1+w1/2>x2-w2/2 && x1-w1/2<x2+w2 && y1+h1/2>y2-h2/2 && y1-h1/2<y2+h2){
					activeArmy.setX(activeArmy.getTargetKingdom().getX());
					activeArmy.setY(activeArmy.getTargetKingdom().getY());
					activeArmy.getTargetKingdom().setTarget(-1);
					activeArmy.changeState(Army.STATE_OFF);
					subState = SUB_STATE_ACTION_WAIT;
					nextAvailable = true;
					activeArmy.setKingdom(activeArmy.getTargetKingdom());
				}
				

				break;
			}
		break;
		}
		
		if(cancelAvailable)
			btnCancel.update(UserInput.getInstance().getMultiTouchHandler());
		if(nextAvailable)
			btnNext.update(UserInput.getInstance().getMultiTouchHandler());
		map.update(delta);
		
		//Actualizar animaciones
		for(Player player : playerList)
			player.updateAnimations(Main.getDeltaSec());
		
	}
	
	public void draw(Graphics g){
		
		map.draw(g);
		
		//Flags
		
		for(Player player : playerList){
			for(Kingdom kingdom : player.getKingdomList()){
				g.setClip(0, 0, Define.SIZEX, Define.SIZEX);
				g.drawImage(GfxManager.imgFlagList.get(player.getFlag()), 
						kingdom.getAbsoluteX(), kingdom.getAbsoluteY(), 
						Graphics.VCENTER | Graphics.HCENTER);
			}
		}
		
		//Army
		for(Player player : playerList){
			for(Army army: player.getArmyList())
				army.draw(g);
		}
			
		
		
		
		g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
		
		if(cancelAvailable)
			btnCancel.draw(g, 0, 0);
		if(nextAvailable)
			btnNext.draw(g, 0, 0);
		
		drawPresentation(g);
		}
	
	public void cancelAction(){
		cancelAvailable = false;
		switch(state){
		case STATE_INCOME:
			
			break;
		case STATE_PAY:
			
			break;
		case STATE_MANAGEMENT:
			
			break;
		case STATE_ACTION:
			
			switch(subState){
			case SUB_STATE_ACTION_ARMY_SELECT:
				
				//Quito todos lo indicadores de target
				for(Kingdom k: map.getKingdomList()){
					k.setTarget(-1);
				}
				
				subState = SUB_STATE_ACTION_WAIT;
				break;
			}
			break;
		}
		
	}
	public void nextState(){
		nextAvailable = false;
		cancelAvailable = false;
		subState = 0;
		switch(state){
		case STATE_INCOME:
			state=STATE_PAY;
			
			//Activo tropas
			for(Army army : playerList.get(currentPlayer).getArmyList())
				army.changeState(Army.STATE_ON);
			
			
			
			break;
		case STATE_PAY:
			state=STATE_MANAGEMENT;
			startPresentation(Font.FONT_MEDIUM, "MANAGEMENT");
			break;
		case STATE_MANAGEMENT:
			state=STATE_ACTION;
			startPresentation(Font.FONT_MEDIUM, "ACTION");
			break;
		case STATE_ACTION:
			state = STATE_INCOME;
			turnCount++;
			currentPlayer = (currentPlayer+1)%playerList.size();
			startPresentation(Font.FONT_BIG, "PLAYER " + (currentPlayer+1));
			break;
		}
	}
	
	private int presentationState;
	private int presentationFont;
	private String presentationText;
	private float presentationShowCount;
	private float presentationModX;
	private static final int STATE_PRESENTATION_MOVE_1 = 1;
	private static final int STATE_PRESENTATION_SHOW = 2;
	private static final int STATE_PRESENTATION_MOVE_2 = 3;
	
	public void startPresentation(int font, String text){
		presentationState = STATE_PRESENTATION_MOVE_1;
		presentationFont = font;
		presentationText = text;
		presentationShowCount = 0;
		presentationModX = -Define.SIZEX;
	}
	
	public boolean updatePresentation(float delta){
		
		switch(presentationState){
		case STATE_PRESENTATION_MOVE_1:
			if(presentationModX < 0){
				presentationModX -= (presentationModX*4f-Define.SIZEX8) * delta; 
			}else{
				presentationState = STATE_PRESENTATION_SHOW;
			}
			return true;
		case STATE_PRESENTATION_SHOW:
			if(presentationShowCount >=  0.35f){
				presentationState = STATE_PRESENTATION_MOVE_2;
			}else{
				presentationShowCount+= delta;
			}
			return true;
		case STATE_PRESENTATION_MOVE_2:
			if(presentationModX < Define.SIZEX){
				presentationModX += (presentationModX*4f+Define.SIZEX8) * delta;
			}else{
				presentationState = -1;
			}
			return true;
		}
		
		return false;
	}
	
	public void drawPresentation(Graphics g){
		if(presentationState != -1){
			TextManager.drawSimpleText(g, presentationFont, presentationText, 
				(int) (Define.SIZEX2+presentationModX), 
				Define.SIZEY2, Graphics.VCENTER | Graphics.HCENTER);
		}
	}
	
	public Army getArmy(Kingdom kingdom){
		Army army = null;
		for(Player player : playerList){
			army = player.getArmy(kingdom);
			if(army != null)
				break;
		}
		return army;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getSubState() {
		return subState;
	}

	public void setSubState(int state) {
		this.subState = state;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	
	
	

}
