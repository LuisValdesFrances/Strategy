package com.luis.strategy.game;

import java.util.List;

import android.util.Log;

import com.luis.army.gui.BattleBox;
import com.luis.army.gui.ResultBox;
import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.menu.Button;
import com.luis.strategy.GfxManager;
import com.luis.strategy.Main;
import com.luis.strategy.ModeGame;
import com.luis.strategy.UserInput;
import com.luis.strategy.army.Army;
import com.luis.strategy.constants.Define;
import com.luis.strategy.constants.GameParams;
import com.luis.strategy.map.Kingdom;
import com.luis.strategy.map.Map;

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
	public static final int SUB_STATE_ACTION_COMBAT = 4;
	public static final int SUB_STATE_ACTION_RESULT = 5;
	public static final int SUB_STATE_ACTION_ESCAPE = 6;
	
	private boolean nextAvailable;
	private boolean cancelAvailable;
	
	//GUI
	private BattleBox battleBox;
	private ResultBox resultBox;
	
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
		
		battleBox = new BattleBox(){
			@Override
			public void onButtonPressUp(){
				switch(this.getIndexPressed()){
				case 0:
					Log.i("Debug", "Opcion 0");
					combat();
					break;
				case 1:
					Log.i("Debug", "Opcion 1");
					combat();
					break;
				}
			}
		};
		
		resultBox = new ResultBox(){
			@Override
			public void onButtonPressUp(){
				startEscape();
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
				if(!updatePresentation(delta)){
					subState = SUB_STATE_ACTION_WAIT;
					cleanArmyAction();
				}
				
				break;
			case SUB_STATE_ACTION_WAIT:
				nextAvailable = true;
				
				//Atcualizar interacion terreno:
				
				//Actualizar animaciones
				playerList.get(currentPlayer).updateAnimations(Main.getDeltaSec());
				
				for(Army army: playerList.get(currentPlayer).getArmyList()){
					if(army.getState() == Army.STATE_ON && army.isSelect()){
						subState = SUB_STATE_ACTION_ARMY_SELECT;
						activeArmy = army;
						
						Log.i("Debug", "Seleccionado ejercito de: " + activeArmy.getKingdom().getName());
						
						for(Kingdom border : activeArmy.getKingdom() .getBorderList()){
							for(Kingdom kingdom: map.getKingdomList()){
								if(kingdom.getTarget()== -1 && kingdom.getId() == border.getId()){
									
									//Si tengo un ejercito en la zona me uno
									if(
										playerList.get(currentPlayer).getArmy(kingdom) != null){
										kingdom.setTarget(Kingdom.TARGET_AGGREGATION);
										kingdom.setTouchX(kingdom.getAbsoluteX());
										kingdom.setTouchY(kingdom.getAbsoluteY());
									}else{
										//Si el territorio es mio
										if(playerList.get(currentPlayer).hasKingom(kingdom)){
											//Busco ejercitos enemigos
											if(getEnemyAtKingdom(playerList.get(currentPlayer), kingdom) != null){
												kingdom.setTarget(Kingdom.TARGET_BATTLE);
												kingdom.setTouchX(kingdom.getAbsoluteX());
												kingdom.setTouchY(kingdom.getAbsoluteY());
											}else{
												kingdom.setTarget(Kingdom.TARGET_DOMAIN);
												kingdom.setTouchX(kingdom.getAbsoluteX());
												kingdom.setTouchY(kingdom.getAbsoluteY());
											}
										}else{
											kingdom.setTarget(Kingdom.TARGET_BATTLE);
											//Busco ejercitos enemigos
											if(getEnemyAtKingdom(playerList.get(currentPlayer), kingdom) != null){
												kingdom.setTouchX(kingdom.getAbsoluteX());
												kingdom.setTouchY(kingdom.getAbsoluteY());
											}else{
												kingdom.setTouchX(kingdom.getTerrainList().get(0).getAbsoluteX());
												kingdom.setTouchY(kingdom.getTerrainList().get(0).getAbsoluteY());
											}
										}
									}
								}
							}
						}
						//Si estoy en territorio hostil(A midad de una conquista), lo añado a los seleccionables
						if(!playerList.get(currentPlayer).hasKingom(activeArmy.getKingdom())){
							activeArmy.getKingdom().setTarget(Kingdom.TARGET_BATTLE);
							activeArmy.getKingdom().setTouchX(activeArmy.getKingdom().
									getTerrainList().get(activeArmy.getKingdom().getState()).getAbsoluteX());
							activeArmy.getKingdom().setTouchY(activeArmy.getKingdom().
									getTerrainList().get(activeArmy.getKingdom().getState()).getAbsoluteY());
						}
					}
				}
				
				break;
				
			case SUB_STATE_ACTION_ARMY_SELECT:
				cancelAvailable = true;
				for(Kingdom kingdom: map.getKingdomList()){
					if(kingdom.getTarget()!= -1 && kingdom.isSelect()){
						
						
						//Si abandono una conquista, se pierde el progreso
						if(kingdom.getId() != activeArmy.getKingdom().getId()){
							activeArmy.getKingdom().setState(0);
						}
						
						putArmyAtKingdom(activeArmy, activeArmy.getKingdom(), kingdom);
						
						
						activeArmy.changeState(Army.STATE_MOVE);
						
						subState = SUB_STATE_ACTION_ARMY_MOVE;
						
						
						
						//Quito todos los otros indicadores de target
						for(Kingdom k: map.getKingdomList()){
							if(k.getId() != activeArmy.getKingdom().getId()){
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
				int x2 = activeArmy.getKingdom().getAbsoluteX();
				int y2 = activeArmy.getKingdom().getAbsoluteY();
				int w2 = GfxManager.imgTargetDomain.getWidth()/4;
				int h2 = GfxManager.imgTargetDomain.getHeight()/4;
				
				if(x1+w1/2>x2-w2/2 && x1-w1/2<x2+w2 && y1+h1/2>y2-h2/2 && y1-h1/2<y2+h2){
					activeArmy.setX(activeArmy.getKingdom().getX());
					activeArmy.setY(activeArmy.getKingdom().getY());
					
					//Si tengo un ejercito en la zona y no soy yo ese ejercito me uno
					if(playerList.get(currentPlayer).getArmy(activeArmy.getKingdom()) != null
						&&
						playerList.get(currentPlayer).getArmy(activeArmy.getKingdom()).getId() != activeArmy.getId()){
						
						aggregation(activeArmy, playerList.get(currentPlayer).getArmy(activeArmy.getKingdom()));
						activeArmy.getKingdom().setTarget(-1);
						activeArmy.changeState(Army.STATE_OFF);
						subState = SUB_STATE_ACTION_WAIT;
						cleanArmyAction();
						nextAvailable = true;
						
					}else{
						//Si el territorio es mio
						if(playerList.get(currentPlayer).hasKingom(activeArmy.getKingdom())){
							//Si hay un ejercito enemigo
							if(getEnemyAtKingdom(playerList.get(currentPlayer), activeArmy.getKingdom()) != null){
								subState = SUB_STATE_ACTION_COMBAT;
								battleBox.start(BattleBox.TYPE_BATTLE_ARMY);
							}else{
								activeArmy.getKingdom().setTarget(-1);
								activeArmy.changeState(Army.STATE_OFF);
								subState = SUB_STATE_ACTION_WAIT;
								cleanArmyAction();
								nextAvailable = true;
							}
						}else{
							
							subState = SUB_STATE_ACTION_COMBAT;
							//Si hay un ejercito enemigo
							if(getEnemyAtKingdom(playerList.get(currentPlayer), activeArmy.getKingdom()) != null){
								battleBox.start(BattleBox.TYPE_BATTLE_ARMY);
							}else{
								int kingdomState = activeArmy.getKingdom().getState();
								switch(activeArmy.getKingdom().getTerrainList().get(kingdomState).getType()){
								case GameParams.PLAIN:battleBox.start(BattleBox.TYPE_BATTLE_PLAIN);break;
								case GameParams.FOREST:battleBox.start(BattleBox.TYPE_BATTLE_FOREST);break;
								case GameParams.MONTAIN:battleBox.start(BattleBox.TYPE_BATTLE_MONTAIN);break;
								case GameParams.SMALL_CITY:battleBox.start(BattleBox.TYPE_BATTLE_SMALL_CITY);break;
								case GameParams.MEDIUM_CITY:battleBox.start(BattleBox.TYPE_BATTLE_MEDIUM_CITY);break;
								case GameParams.BIG_CITY:battleBox.start(BattleBox.TYPE_BATTLE_BIG_CITY);break;
								}
							}
						}
					}
				}
				

				break;
			case SUB_STATE_ACTION_COMBAT:
				activeArmy.changeState(Army.STATE_OFF);
				battleBox.update(delta, UserInput.getInstance().getMultiTouchHandler());
				break;
				
			case SUB_STATE_ACTION_RESULT:
				resultBox.update(delta, UserInput.getInstance().getMultiTouchHandler());
				break;
			case SUB_STATE_ACTION_ESCAPE:
				
				Army defeat = getDefeatEnemy();
				
				//Colision
				x1 = defeat.getAbsoluteX();
				y1 = defeat.getAbsoluteY();
				w1 = GfxManager.imgArmyIdle.getWidth()/36;
				h1 = GfxManager.imgArmyIdle.getHeight()/4;
				x2 = defeat.getKingdom().getAbsoluteX();
				y2 = defeat.getKingdom().getAbsoluteY();
				w2 = GfxManager.imgTargetDomain.getWidth()/4;
				h2 = GfxManager.imgTargetDomain.getHeight()/4;
					
				if(x1+w1/2>x2-w2/2 && x1-w1/2<x2+w2 && y1+h1/2>y2-h2/2 && y1-h1/2<y2+h2){
					cleanArmyAction();
						
					//Si hay un ejercito amigo, se unen
						
					defeat.setX(defeat.getKingdom().getX());
					defeat.setY(defeat.getKingdom().getY());
					defeat.getKingdom().setTarget(-1);
					defeat.changeState(Army.STATE_ON);
						
					subState = SUB_STATE_ACTION_WAIT;
					nextAvailable = true;
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
		
		map.drawMap(g);
		
		//Flags
		for(Player player : playerList){
			for(Kingdom kingdom : player.getKingdomList()){
				g.setClip(0, 0, Define.SIZEX, Define.SIZEX);
				g.drawImage(GfxManager.imgFlagList.get(player.getFlag()), 
						
						kingdom.getTerrainList().get(kingdom.getTerrainList().size()-1).getAbsoluteX()+GfxManager.imgPlain.getWidth()/2,
						kingdom.getTerrainList().get(kingdom.getTerrainList().size()-1).getAbsoluteY()+GfxManager.imgPlain.getHeight()/2,
						
						//kingdom.getAbsoluteX()-GfxManager.imgFlagList.get(player.getFlag()).getWidth()/2, 
						//kingdom.getAbsoluteY()-GfxManager.imgFlagList.get(player.getFlag()).getHeight()/2, 
						Graphics.BOTTOM | Graphics.HCENTER);
			}
		}
		
		//Army
		for(Player player : playerList){
			for(Army army: player.getArmyList())
				army.draw(g);
		}
			
		map.drawTarget(g);
		
		
		g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
		
		if(cancelAvailable)
			btnCancel.draw(g, 0, 0);
		if(nextAvailable)
			btnNext.draw(g, 0, 0);
		
		
		if(ModeGame.showDebugInfo){
			for(Kingdom k : map.getKingdomList())
				TextManager.drawSimpleText(g, Font.FONT_MEDIUM, 
						"ST:" + k.getState(),
					k.getAbsoluteX(), 
					k.getAbsoluteY(),
					Graphics.BOTTOM | Graphics.RIGHT);
			
		for(Player p : playerList)
			for(Army a : p.getArmyList())
				TextManager.drawSimpleText(g, Font.FONT_SMALL, 
						""+a.getKingdom().getId() + "-" + a.getId(),
					a.getAbsoluteX()-GfxManager.imgArmyOff.getWidth()/4, 
					a.getAbsoluteY()-GfxManager.imgArmyOff.getHeight()/4,
					Graphics.BOTTOM | Graphics.RIGHT);
		}
		
		battleBox.draw(g);
		resultBox.draw(g);
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
		UserInput.getInstance().getMultiTouchHandler().resetTouch();
		cleanArmyAction();
		nextAvailable = false;
		cancelAvailable = false;
		subState = 0;
		switch(state){
		case STATE_INCOME:
			
			
			//Activo tropas
			for(Player player : playerList)
				for(Army army : player.getArmyList())
					army.changeState(Army.STATE_ON);
			
			state=STATE_PAY;
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
	
	public Army getActiveArmy(){
		return null;
	}
	
	public Army getDefeatEnemy(){
		Army enemy = null;
		for(Player player:playerList){
			for(Army army : player.getArmyList()){
				if(army.isDefeat()){
					enemy = army;
					break;
				}
			}
		}
		return enemy;
	}
	
	public Army getArmyFromKingdom(Kingdom kingdom){
		Army army = null;
		for(Player player : playerList){
			army = player.getArmy(kingdom);
			if(army != null)
				break;
		}
		return army;
	}
	
	
	
	
	
	
	
	
	
	
	
	private Army getEnemyArmyFromKingdom(Kingdom kingdom) {
		Army enemy = null;
		for(Player player:playerList){
			if(player.getId()!= playerList.get(currentPlayer).getId()){
				for(Army army : player.getArmyList()){
					if(army.getKingdom().getId() == kingdom.getId()){
						enemy = army;
						break;
					}
				}
			}
		}
		return enemy;
	}
	
	
	
	
	private Army getEnemyAtKingdom(Player player, Kingdom kingdom){
		Army enemy = null;
		for(Player p : playerList){
			if(p.getId() != player.getId()){
				for(Army a : p.getArmyList()){
					if(a.getKingdom().getId() == kingdom.getId()){
						enemy = a;
						break;
					}
				}
			}
		}
		return enemy;
	}
	
	private Army getFriendAtKingdom(Player player, Kingdom kingdom){
		Army enemy = null;
		for(Player p : playerList){
			if(p.getId() == player.getId()){
				for(Army a : player.getArmyList()){
					if(a.getKingdom().getId() == kingdom.getId()){
						enemy = a;
						break;
					}
				}
			}
		}
		return enemy;
	}
	
	
	private void removeArmy(Army army){
		for(Player player:playerList){
			for(int i = 0; i < player.getArmyList().size(); i++){
				if(player.getArmyList().get(i).getId() == army.getId()){
					player.getArmyList().remove(i);
					break;
				}
			}
		}
	}
	
	private void cleanArmyAction(){
		for(Player player:playerList){
			for(Army army : player.getArmyList()){
				army.setController(false);
				army.setDefeat(false);
			}
		}
	}
	
	private void startEscape(){
		
		Army defeat = getDefeatEnemy();
		if(defeat != null){
			defeat.changeState(Army.STATE_MOVE);
			subState = SUB_STATE_ACTION_ESCAPE;
		}else{
			subState = SUB_STATE_ACTION_WAIT;
			nextAvailable = true;
		}
		
	}
	
	private void aggregation(Army army1, Army army2){

		Log.i("Debug", "Aggregation");
	}
	
	private void putArmyAtKingdom(Army army, Kingdom lastKingdom, Kingdom newKingdom){
		army.setLastKingdom(lastKingdom);
		army.setKingdom(newKingdom);
		//Actulizo su zona de touch (Se altera al moverse)
		army.setTouchX(newKingdom.getAbsoluteX());
		army.setTouchY(newKingdom.getAbsoluteY());
	}
	private void addNewConquest(Player player, Kingdom kingdom){
		//Elimino el territorio del domino de cualquier jugador
		for(Player p : playerList){
			p.removeKingdom(kingdom);
		}
		
		player.getKingdomList().add(kingdom);
	}
	
	private void combat(){
		
		//combate contra otro ejercito
		if(getEnemyAtKingdom(playerList.get(currentPlayer), activeArmy.getKingdom()) != null){
			//Resolucion del combate
			
			Army enemy = getEnemyAtKingdom(playerList.get(currentPlayer), activeArmy.getKingdom());
			
			
			//Busco al dueño del ejercito
			int indexPlayer = -1;
			for(int i = 0; indexPlayer == -1 && i < playerList.size(); i++){
				for(Army army : playerList.get(i).getArmyList()){
					if(army.getId() == enemy.getId()){
						indexPlayer = i;
						break;
					}
				}
			}
			//Comparo si alguno de los territorios adyacentes pertenece al derrotado
			Kingdom defeatTarget = null;
			for(Kingdom domain : playerList.get(indexPlayer).getKingdomList()){
				for(Kingdom border : enemy.getKingdom().getBorderList()){
					if(domain.getId() == border.getId() && getEnemyAtKingdom(playerList.get(indexPlayer), border) == null){
						defeatTarget = domain;
						break;
					}
				}
			}
			
			if(defeatTarget != null){
				enemy.setDefeat(true);
				
				putArmyAtKingdom(enemy, enemy.getKingdom(), defeatTarget);
				resultBox.start(ResultBox.TYPE_VICTORY);
			}else{
				resultBox.start(ResultBox.TYPE_MASSACRE);
				removeArmy(enemy);
			}
			
			
			activeArmy.getKingdom().setState(0);
			activeArmy.getKingdom().setTarget(-1);
			activeArmy.changeState(Army.STATE_OFF);
			
			
			
		}else{
			//Resolucion del combate
			int state = activeArmy.getKingdom().getState()+1;
			int totalStates = activeArmy.getKingdom().getTotalStates();
			if(state < totalStates){
				activeArmy.getKingdom().setState(state);
				activeArmy.getKingdom().setTarget(-1);
				activeArmy.changeState(Army.STATE_OFF);
				resultBox.start(ResultBox.TYPE_VICTORY);
				
			}
			//Conquista
			else{
				activeArmy.getKingdom().setState(0);
				activeArmy.getKingdom().setTarget(-1);
				activeArmy.changeState(Army.STATE_OFF);
				addNewConquest(playerList.get(currentPlayer), activeArmy.getKingdom());
				resultBox.start(ResultBox.TYPE_CONQUEST);
			}
		}
		
		subState = SUB_STATE_ACTION_RESULT;
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

	
	public List<Player> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(List<Player> playerList) {
		this.playerList = playerList;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	
	
	

}
