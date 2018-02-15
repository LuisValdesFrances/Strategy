package com.luis.strategy.game;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.luis.army.gui.ArmyBox;
import com.luis.army.gui.BattleBox;
import com.luis.army.gui.TerrainBox;
import com.luis.army.gui.FlagButton;
import com.luis.army.gui.SimpleBox;
import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.gameutils.gameworld.GameCamera;
import com.luis.lgameengine.gameutils.gameworld.WorldConver;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.lgameengine.implementation.input.KeyData;
import com.luis.lgameengine.implementation.input.TouchData;
import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.gui.MenuBox;
import com.luis.lgameengine.gui.MenuElement;
import com.luis.lgameengine.gui.NotificationBox;
import com.luis.strategy.GfxManager;
import com.luis.strategy.Main;
import com.luis.strategy.ModeGame;
import com.luis.strategy.UserInput;
import com.luis.strategy.army.Army;
import com.luis.strategy.army.Troop;
import com.luis.strategy.constants.Define;
import com.luis.strategy.constants.GameParams;
import com.luis.strategy.map.Kingdom;
import com.luis.strategy.map.Map;
import com.luis.strategy.map.Terrain;

public class GameManager {
	
	//Pad
	private int lastTouchX;
	private int lastTouchY;
	private float cameraTargetX;
	private float cameraTargetY;
	
	public GameCamera gameCamera;
	public WorldConver worldConver;
	
	private int turnCount;
	private Map map;
	
	private int playerIndex;
	private List<Player>playerList;
	
	private Army activeArmy;
	
	private int state;
	private int lastState;
	public static final int STATE_INCOME = 0;
	public static final int STATE_ECONOMY = 1;
	public static final int STATE_DISCARD = 2;
	public static final int STATE_ACTION = 3;
	public static final int STATE_END = 4;
	
	
	//SUB-STATE ACTION
	public int subState;
	private int lastSubState;
	public static final int SUB_STATE_ACTION_WAIT = 0;
	public static final int SUB_STATE_ACTION_ARMY_SELECT = 1;
	public static final int SUB_STATE_ACTION_ARMY_MOVE = 2;
	public static final int SUB_STATE_ACTION_COMBAT = 3;
	public static final int SUB_STATE_ACTION_RESULT = 4;
	public static final int SUB_STATE_ACTION_ESCAPE = 5;
	public static final int SUB_STATE_ARMY_MANAGEMENT = 6;
	public static final int SUB_STATE_CITY_MANAGEMENT = 7;
	
	//GUI
	private static Button btnNext;
	private static Button btnCancel;
	private static FlagButton btnFlagHelmet;
	private static FlagButton btnFlagCastle;
	private ArmyBox armyBox;
	private BattleBox battleBox;
	private TerrainBox terrainBox;
	private SimpleBox economyBox;
	private SimpleBox resultBox;
	private SimpleBox discardBox;
	private SimpleBox infoBox;
	
	public GameManager(WorldConver wc, GameCamera gc, Map m, List<Player> pList){
		this.worldConver = wc;
		this.gameCamera = gc;
		this.map = m;
		this.lastTouchX = UserInput.getInstance().getMultiTouchHandler().getTouchX(0);
		this.lastTouchY = UserInput.getInstance().getMultiTouchHandler().getTouchY(0);
		
		this.cameraTargetX=0;//worldConver.getCentlayoutX();
		this.cameraTargetY=0;//=worldConver.getCentlayoutY();
		
		this.playerList = pList;
		
		MenuElement.imgBG = GfxManager.imgBlackBG;
		MenuElement.bgAlpha = GameParams.BG_BLACK_ALPHA;
		
		int modImageFrame = GfxManager.imgGameHud.getHeight()/8;
		btnNext = new Button(
				GfxManager.imgButtonNextRelease, 
				GfxManager.imgButtonNextFocus, 
				Define.SIZEX2, 
				Define.SIZEY-GfxManager.imgGameHud.getHeight()/2 + modImageFrame,
				null, 0){
			@Override
			public void onButtonPressDown(){}
			
			@Override
			public void onButtonPressUp(){
				changeState(state+1);
				reset();
			}
		};
		
		btnCancel = new Button(
				GfxManager.imgButtonCancelRelease, 
				GfxManager.imgButtonCancelFocus, 
				Define.SIZEX2- GfxManager.imgButtonCancelRelease.getWidth()*2, 
				Define.SIZEY-GfxManager.imgGameHud.getHeight()/2 + modImageFrame,
				null, 0){
			@Override
			public void onButtonPressDown(){}
			
			@Override
			public void onButtonPressUp(){
				cancelAction();
				reset();
			}
		};
		
		btnFlagHelmet = new FlagButton(
				GfxManager.imgButtonFlagHelmetRelease, 
				GfxManager.imgButtonFlagHelmetFocus, 
				GfxManager.imgButtonFlagHelmetRelease.getWidth()/2, 
				Define.SIZEY-GfxManager.imgButtonFlagHelmetRelease.getHeight()/2){
			@Override
			public void onButtonPressDown(){}
			
			@Override
			public void onButtonPressUp(){
				reset();
				if(subState == SUB_STATE_ACTION_WAIT){
					setDataTarget(activeArmy);
					changeSubState(SUB_STATE_ACTION_ARMY_SELECT);
					hide();
				}
			}
		};
		
		btnFlagCastle = new FlagButton(
				GfxManager.imgButtonFlagCastleRelease,
				GfxManager.imgButtonFlagCastleFocus, 
				Define.SIZEX-GfxManager.imgButtonFlagCastleRelease.getWidth()/2, 
				Define.SIZEY-GfxManager.imgButtonFlagCastleRelease.getHeight()/2){
			@Override
			public void onButtonPressDown(){}
			
			@Override
			public void onButtonPressUp(){
				changeSubState(SUB_STATE_ARMY_MANAGEMENT);
				armyBox.start(
						getSelectedArmy(), 
						isSelectedArmyFromCurrentPlayer(getSelectedArmy()),
						state==STATE_DISCARD);
				hide();
			}
		};
		
		armyBox = new ArmyBox(){
			@Override
			public void check(){
				if(state == STATE_DISCARD){
					int dif = playerList.get(playerIndex).getTaxes() - playerList.get(playerIndex).getSalaries();
					if(dif >= 0){
						discardBox.cancel();
					}
				}
			}
		};
		
		terrainBox = new TerrainBox(){
			@Override
			public void onBuy(){
				super.onBuy();
				getCurrentPlayer().setGold(getCurrentPlayer().getGold()-GameParams.ARMY_COST);
				
				Army army = new Army(
						worldConver, 
						gameCamera, 
						map, 
						getCurrentPlayer(), 
						getKingdom(),
						getCurrentPlayer().getFlag(), 
						map.getX(), map.getY(), GfxManager.imgMap.getWidth(), GfxManager.imgMap.getHeight());
				army.setState(Army.STATE_OFF);
				getCurrentPlayer().getArmyList().add(army);
				cancel();
			}
			@Override
			public void onFinish() {
				if(isRecruited())
					NotificationBox.getInstance().addMessage("New army recruited");
			}
		};
		
		battleBox = new BattleBox(){
			@Override
			public void onFinish(){
				switch(this.getIndexPressed()){
				case 0:
					resolveCombat(battleBox.getResult());
					break;
				case 1:
					changeSubState(SUB_STATE_ACTION_WAIT);
					break;
				}
			}
		};
		
		resultBox = new SimpleBox(GfxManager.imgSmallBox, true){
			@Override
			public void onFinish(){
				startEscape();
			}
		};
		
		economyBox = new SimpleBox(GfxManager.imgSmallBox, true){
			@Override
			public void onFinish(){
				//Compruebo si estoy en saldo negativo
				if(playerList.get(playerIndex).getGold() < 0){
					changeState(STATE_DISCARD);
				}else{
					changeState(STATE_ACTION);
				}
			}
		};
		
		infoBox = new SimpleBox(GfxManager.imgSmallBox, true);
		discardBox = new SimpleBox(GfxManager.imgNotificationBox, false);
		discardBox.setY(GfxManager.imgNotificationBox.getHeight()/2);
		
		turnCount = 1;
		
		NotificationBox.getInstance().init(
				Define.SIZEX, Define.SIZEY, GfxManager.imgNotificationBox, NotificationBox.DURATION_MEDIUM);
		
		changeState(STATE_INCOME);
	}
	
	public void update(float delta){
		
		switch(state){
		case STATE_INCOME:
			if(!updatePresentation(delta)){
				changeState(STATE_ECONOMY);
			}
			break;
		case STATE_ECONOMY:
			economyBox.update(UserInput.getInstance().getMultiTouchHandler(), delta);
			break;
		case STATE_DISCARD:
			discardBox.update(UserInput.getInstance().getMultiTouchHandler(), delta);
			if(!armyBox.update(UserInput.getInstance().getMultiTouchHandler(), delta)){
				int dif = playerList.get(playerIndex).getTaxes() - playerList.get(playerIndex).getSalaries();
				if(dif >= 0){
					changeState(STATE_ACTION);
				}
			}
			
			for(int i = 0; i < playerList.size(); i++){
				for(Army army: playerList.get(i).getArmyList()){
					if(army.isSelect()){
						cleanArmyAction();
						activeArmy = army;
						activeArmy.setSelected(true);
						btnFlagCastle.start();
					}
				}
			}
			break;
		
		case STATE_ACTION:
			discardBox.update(UserInput.getInstance().getMultiTouchHandler(), delta);
			switch(subState){
			case SUB_STATE_ACTION_WAIT:
				//Atcualizar interacion terreno:
				for(Kingdom kingdom : map.getKingdomList()){
					for(Terrain terrain : kingdom.getTerrainList()){
						if(terrain.isSelect()){
							terrainBox.start(getCurrentPlayer(), kingdom,
								terrain.getType() >= GameParams.SMALL_CITY &&
								getArmyAtKingdom(kingdom)== null && 
								getCurrentPlayer().hasKingom(kingdom), 
								terrain.getType());
							
							changeSubState(SUB_STATE_CITY_MANAGEMENT);
						}
					}
					
				}
				
				//Actualizar animaciones
				playerList.get(playerIndex).updateAnimations(delta);
				
				for(int i = 0; i < playerList.size(); i++){
					for(Army army: playerList.get(i).getArmyList()){
						if(army.isSelect()){
							cleanArmyAction();
							activeArmy = army;
							activeArmy.setSelected(true);
							btnFlagCastle.start();
							//Camara se posiciona en el seleccionado
							cameraTargetX = getSelectedArmy().getAbsoluteX();
							cameraTargetY = getSelectedArmy().getAbsoluteY();
							if(i == playerIndex && army.getState() == Army.STATE_ON){
								btnFlagHelmet.start();
							}else{
								btnFlagHelmet.hide();
							}
						}
					}
				}
				break;
				
			case SUB_STATE_ACTION_ARMY_SELECT:
				for(Kingdom kingdom: map.getKingdomList()){
					if(kingdom.getTarget()!= -1 && kingdom.isSelect()){
						
						
						//Si abandono una conquista, se pierde el progreso
						if(kingdom.getId() != activeArmy.getKingdom().getId()){
							activeArmy.getKingdom().setState(0);
						}
						
						putArmyAtKingdom(activeArmy, activeArmy.getKingdom(), kingdom);
						
						
						activeArmy.changeState(Army.STATE_MOVE);
						
						changeSubState(SUB_STATE_ACTION_ARMY_MOVE);
						
						
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
				playerList.get(playerIndex).updateAnimations(Main.getDeltaSec());
				
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
					List <Army> armyList = new ArrayList<Army>();
					for(int i = 0; i < playerList.get(playerIndex).getArmyList().size(); i++){
						if(playerList.get(playerIndex).getArmyList().get(i).getKingdom().getId() == getSelectedArmy().getKingdom().getId()){
							armyList.add(playerList.get(playerIndex).getArmyList().get(i));
						}
					}
					
					if(armyList.size() > 1){
						
						aggregation(armyList.get(0), armyList.get(1));
						activeArmy = armyList.get(0);
						activeArmy.getKingdom().setTarget(-1);
						activeArmy.changeState(Army.STATE_OFF);
						changeSubState(SUB_STATE_ACTION_WAIT);
						NotificationBox.getInstance().addMessage("The armies have joined forces");
						
					}else{
						//Si el territorio es mio
						if(playerList.get(playerIndex).hasKingom(activeArmy.getKingdom())){
							//Si hay un ejercito enemigo
							if(getEnemyAtKingdom(playerList.get(playerIndex), activeArmy.getKingdom()) != null){
								changeSubState(SUB_STATE_ACTION_COMBAT);
								
								//Para batallas, el tipo de box es el primer elemento del territorio
								battleBox.start(
										getSelectedArmy().getKingdom().getTerrainList().get(0), 
										getSelectedArmy(),
										getEnemyAtKingdom(playerList.get(playerIndex), activeArmy.getKingdom()));
							}else{
								activeArmy.getKingdom().setTarget(-1);
								activeArmy.changeState(Army.STATE_OFF);
								changeSubState(SUB_STATE_ACTION_WAIT);
							}
						}else{
							
							changeSubState(SUB_STATE_ACTION_COMBAT);
							//Si hay un ejercito enemigo
							if(getEnemyAtKingdom(playerList.get(playerIndex), activeArmy.getKingdom()) != null){
								
								//Para batallas, el tipo de box es el primer elemento del territorio
								battleBox.start(
										getSelectedArmy().getKingdom().getTerrainList().get(0), 
										getSelectedArmy(),
										getEnemyAtKingdom(playerList.get(playerIndex), activeArmy.getKingdom()));
								
							}else{
								int kingdomState = getSelectedArmy().getKingdom().getState();
								battleBox.start(
										getSelectedArmy().getKingdom().getTerrainList().get(kingdomState), 
										getSelectedArmy(),
										null);
								
							}
						}
					}
				}
				break;
				
			case SUB_STATE_ACTION_COMBAT:
				battleBox.update(UserInput.getInstance().getMultiTouchHandler(), delta);
				break;
				
			case SUB_STATE_ACTION_RESULT:
				resultBox.update(UserInput.getInstance().getMultiTouchHandler(), delta);
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
					
					//Si hay un ejercito amigo, se unen
						
					defeat.setX(defeat.getKingdom().getX());
					defeat.setY(defeat.getKingdom().getY());
					defeat.getKingdom().setTarget(-1);
					if(defeat.getPlayer().getId() == getCurrentPlayer().getId()){
						defeat.changeState(Army.STATE_OFF);
					}else{
						defeat.changeState(Army.STATE_ON);
					}
						
					changeSubState(SUB_STATE_ACTION_WAIT);
					}
				break;
			case SUB_STATE_ARMY_MANAGEMENT:
				//Si no queda ninguna caja en primer plano
				if(!armyBox.update(UserInput.getInstance().getMultiTouchHandler(), delta)){
					changeSubState(SUB_STATE_ACTION_WAIT);
				}
				break;
			case SUB_STATE_CITY_MANAGEMENT:
				if(!terrainBox.update(UserInput.getInstance().getMultiTouchHandler(), delta)){
					changeSubState(SUB_STATE_ACTION_WAIT);
				}
				break;
			}
		break;
		}
		
		/*
		private ArmyBox armyBox;
		private BattleBox battleBox;
		private SimpleBox economyBox;
		private SimpleBox resultBox;
		private SimpleBox discardBox;
		 */
		if(
			!armyBox.isActive() && 
			!battleBox.isActive() && 
			!economyBox.isActive() &&
			!resultBox.isActive() &&
			!infoBox.isActive() &&
			!terrainBox.isActive()){
			updateCamera();
		}
		
		updateGUI(delta);
		
		
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
						worldConver.getConversionDrawX(
						gameCamera.getPosX(), kingdom.getTerrainList().get(kingdom.getTerrainList().size()-1).getAbsoluteX())+
							GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth()/2,
						worldConver.getConversionDrawY(
						gameCamera.getPosY(), kingdom.getTerrainList().get(kingdom.getTerrainList().size()-1).getAbsoluteY())+
							GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight()/2,
						
						Graphics.BOTTOM | Graphics.HCENTER);
			}
		}
		
		//Army
		for(int i = 0; i < playerList.size(); i++){
			for(Army army: playerList.get(i).getArmyList()){
				boolean isSelected =  subState == SUB_STATE_ACTION_WAIT && 
						getSelectedArmy() != null && getSelectedArmy().getId() == army.getId();
				army.draw(g, getSelectedArmy()!= null && isSelected, i == playerIndex && army.getState() == Army.STATE_ON);
			}
		}
		
		if(subState == SUB_STATE_ACTION_ARMY_SELECT)
			map.drawTarget(g);
		
		
		g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
		
		drawGUI(g);
		
		if(ModeGame.showDebugInfo){
			for(Kingdom k : map.getKingdomList())
				TextManager.drawSimpleText(g, Font.FONT_MEDIUM, 
						"ST:" + k.getState(),
					worldConver.getConversionDrawX(gameCamera.getPosX(), k.getAbsoluteX()),
					worldConver.getConversionDrawY(gameCamera.getPosY(), k.getAbsoluteY()),
					Graphics.BOTTOM | Graphics.RIGHT);
			
			for(Player p : playerList)
				for(Army a : p.getArmyList())
					TextManager.drawSimpleText(g, Font.FONT_SMALL, 
						""+a.getKingdom().getId() + "-" + a.getId(),
					worldConver.getConversionDrawX(gameCamera.getPosX(), a.getAbsoluteX()-GfxManager.imgArmyOff.getWidth()/4),
					worldConver.getConversionDrawY(gameCamera.getPosY(), a.getAbsoluteY()-GfxManager.imgArmyOff.getHeight()/4),
					Graphics.BOTTOM | Graphics.RIGHT);
			
			//Margenes
			g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			g.setColor(Main.COLOR_PURPLE_GALAXY);
			g.fillRect(0, 0, Define.SIZEX, worldConver.getMarginN());
			g.fillRect(0, Define.SIZEY-worldConver.getMarginS(), Define.SIZEX, worldConver.getMarginS());
			g.fillRect(0, 0, worldConver.getMarginW(), Define.SIZEY);
			g.fillRect(Define.SIZEX-worldConver.getMarginE(), 0, worldConver.getMarginE(), Define.SIZEY);
			
			g.fillRect((int)worldConver.getCentGameLayoutX()-2, 0, 4, Define.SIZEY);
			g.fillRect(0, (int)worldConver.getCentGameLayoutY()-2, Define.SIZEX, 4);
			g.setColor(Main.COLOR_BLUE);
			int cameraR = ((Define.SIZEX+Define.SIZEY)/2)/22;
			g.fillCircle(
					(int) gameCamera.getPosX()+worldConver.getMarginW(),
					(int) gameCamera.getPosY()+worldConver.getMarginN(),
					cameraR);
		}
		drawPresentation(g);
	}
	
	private void updateGUI(float delta){
		btnCancel.update(UserInput.getInstance().getMultiTouchHandler());
		btnNext.update(UserInput.getInstance().getMultiTouchHandler());
		btnFlagHelmet.update(UserInput.getInstance().getMultiTouchHandler(), delta);
		btnFlagCastle.update(UserInput.getInstance().getMultiTouchHandler(), delta);
		
		NotificationBox.getInstance().update(delta);
	}
	
	private void drawGUI(Graphics g) {
		economyBox.draw(g, true);
		armyBox.draw(g, true);
		discardBox.draw(g, false);
		battleBox.draw(g, true);
		resultBox.draw(g, true);
		terrainBox.draw(g, true);
		infoBox.draw(g, true);
		
		g.drawImage(GfxManager.imgGameHud, 0, Define.SIZEY, Graphics.BOTTOM | Graphics.LEFT);
		btnCancel.draw(g, 0, 0);
		btnNext.draw(g, 0, 0);
		btnFlagHelmet.draw(g);
		btnFlagCastle.draw(g);
		drawGold(g);
		NotificationBox.getInstance().draw(g);
	}
	
	private void drawGold(Graphics g){
		int margin = Define.SIZEY64;
		String text = "" + playerList.get(playerIndex).getGold();
		int totalWidth = GfxManager.imgChest.getWidth() + margin + Font.getFontWidth(Font.FONT_MEDIUM)*text.length();
		int x = Define.SIZEX - Define.SIZEX4 - totalWidth/2; 
		int y = Define.SIZEY - GfxManager.imgGameHud.getHeight()/2 + GfxManager.imgChest.getHeight()/4; 
		g.drawImage(GfxManager.imgChest, x, y, Graphics.VCENTER | Graphics.HCENTER);
		TextManager.drawSimpleText(g, Font.FONT_MEDIUM, text, 
			x+margin + GfxManager.imgChest.getWidth()/2 + Font.getFontWidth(Font.FONT_MEDIUM)*text.length()/2,
			y, 
			Graphics.VCENTER | Graphics.HCENTER);
	}
	
	private void cleanArmyAction(){
		for(Player player:playerList){
			for(Army army : player.getArmyList()){
				army.setSelected(false);
				army.setDefeat(false);
			}
		}
	}
	
	public void setDataTarget(Army army){
		
		//Quito todos lo indicadores de target
		for(Kingdom k: map.getKingdomList()){
			k.setTarget(-1);
		}
		
		
		for(Kingdom border : activeArmy.getKingdom() .getBorderList()){
			for(Kingdom kingdom: map.getKingdomList()){
				if(kingdom.getTarget()== -1 && kingdom.getId() == border.getId()){
					
					//Si tengo un ejercito en la zona me uno
					if(
						playerList.get(playerIndex).getArmy(kingdom) != null){
						kingdom.setTarget(Kingdom.TARGET_AGGREGATION);
						kingdom.setTouchX(kingdom.getAbsoluteX());
						kingdom.setTouchY(kingdom.getAbsoluteY());
					}else{
						//Si el territorio es mio
						if(playerList.get(playerIndex).hasKingom(kingdom)){
							//Busco ejercitos enemigos
							if(getEnemyAtKingdom(playerList.get(playerIndex), kingdom) != null){
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
							if(getEnemyAtKingdom(playerList.get(playerIndex), kingdom) != null){
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
		if(!playerList.get(playerIndex).hasKingom(activeArmy.getKingdom())){
			activeArmy.getKingdom().setTarget(Kingdom.TARGET_BATTLE);
			activeArmy.getKingdom().setTouchX(activeArmy.getKingdom().
					getTerrainList().get(activeArmy.getKingdom().getState()).getAbsoluteX());
			activeArmy.getKingdom().setTouchY(activeArmy.getKingdom().
					getTerrainList().get(activeArmy.getKingdom().getState()).getAbsoluteY());
		}
	}

	public void cancelAction(){
		btnCancel.setDisabled(true);
		switch(state){
		case STATE_INCOME:
			
			break;
		case STATE_ECONOMY:
			
			break;
		case STATE_ACTION:
			
			switch(subState){
			case SUB_STATE_ACTION_WAIT:
			case SUB_STATE_ACTION_ARMY_SELECT:
				btnFlagHelmet.hide();
				btnFlagCastle.hide();
				//Quito todos lo indicadores de target
				for(Kingdom k: map.getKingdomList()){
					k.setTarget(-1);
				}
				changeSubState(SUB_STATE_ACTION_WAIT);
				break;
			}
			break;
		}
		
	}
	public void changeState(int newState){
		UserInput.getInstance().getMultiTouchHandler().resetTouch();
		cleanArmyAction();
		btnNext.setDisabled(true);
		btnCancel.setDisabled(true);
		btnFlagHelmet.hide();
		btnFlagCastle.hide();
		subState = 0;
		lastSubState = 0;
		lastState = state;
		state = newState;
		switch(state){
		case STATE_INCOME:
			startPresentation(Font.FONT_BIG, "PLAYER " + (playerIndex+1));
			cameraTargetX = playerList.get(playerIndex).getCapital().getAbsoluteX();
			cameraTargetY = playerList.get(playerIndex).getCapital().getAbsoluteY();
			break;
		case STATE_ECONOMY:
			
			
			//Activo tropas
			for(Player player : playerList)
				for(Army army : player.getArmyList())
					army.changeState(Army.STATE_ON);
			
			//Calculo de ganancias:
			int tax = playerList.get(playerIndex).getTaxes();
			int salary = playerList.get(playerIndex).getSalaries();
			
			playerList.get(playerIndex).setGold(playerList.get(playerIndex).getGold()+tax-salary);
			
			int dif = tax- salary;
			
			economyBox.start("ECONOMY", "EARNING: +" +tax + " SALARY: -" + salary);
			
			break;
		case STATE_DISCARD:
			
			dif = playerList.get(playerIndex).getTaxes() - playerList.get(playerIndex).getSalaries();
			
			discardBox.start(null, "Cost of troops exceedes your trasure. Discard troops.");
			
			break;
		case STATE_ACTION:
			changeSubState(SUB_STATE_ACTION_WAIT);
			break;
		case STATE_END:
			turnCount++;
			playerIndex = (playerIndex+1)%playerList.size();
			changeState(STATE_INCOME);
			break;
		}
	}
	
	private void changeSubState(int newSubState){
		
		btnNext.setDisabled(true);
		btnCancel.setDisabled(true);
		btnFlagHelmet.hide();
		btnFlagCastle.hide();
		lastSubState =subState;
		subState = newSubState;
		switch(state){
		case STATE_INCOME:
			break;
		case STATE_ECONOMY:
			break;
		case STATE_ACTION:
			
			btnNext.setDisabled(true);
			btnCancel.setDisabled(true);
			
			switch(subState){
				
			case SUB_STATE_ACTION_WAIT:
				btnNext.setDisabled(false);
				btnCancel.setDisabled(true);
				cleanArmyAction();
				break;
			case SUB_STATE_ACTION_ARMY_SELECT:
				btnNext.setDisabled(true);
				btnCancel.setDisabled(false);
				break;
			case SUB_STATE_ACTION_ARMY_MOVE:
				break;
			case SUB_STATE_ACTION_COMBAT:
				getSelectedArmy().changeState(Army.STATE_OFF);
				break;
			case SUB_STATE_ACTION_RESULT:
				break;
			case SUB_STATE_ACTION_ESCAPE:
				break;
			case SUB_STATE_ARMY_MANAGEMENT:
				break;
			case SUB_STATE_CITY_MANAGEMENT:
				break;
			}
			
			break;
		case STATE_END:
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
				presentationModX = 0;
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
			g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			g.setAlpha(GameParams.BG_BLACK_ALPHA);
			g.setImageSize(1f, 1f-(Math.abs(presentationModX)/Define.SIZEX));
			g.drawImage(GfxManager.imgTextBG, Define.SIZEX2, Define.SIZEY2, Graphics.VCENTER | Graphics.HCENTER);
			g.setImageSize(1f, 1f);
			g.setAlpha(255);
			TextManager.drawSimpleText(g, presentationFont, presentationText, 
				(int) (Define.SIZEX2+presentationModX), 
				Define.SIZEY2, Graphics.VCENTER | Graphics.HCENTER);
		}
	}
	
	/**
	 * Devuelve el ejercito seleccionado
	 * @return
	 */
	public Army getSelectedArmy(){
		Army selected = null;
		for(Player player : playerList){
			for(Army army : player.getArmyList()){
				if(army.isSelected()){
					selected = army;
					break;
				}
			}
		}
		return selected;
	}
	
	/**
	 * Devuelve true si el ejercito que se pasa por parametro pertenece al jugador en curso.
	 * @param army
	 * @return
	 */
	public boolean isSelectedArmyFromCurrentPlayer(Army army){
		boolean current = false;
		for(Army a : playerList.get(playerIndex).getArmyList()){
			if(a.getId() == army.getId()){
				current = true;
				break;
			}
		}
		return current;
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
	
	public Army getArmyAtKingdom(Kingdom kingdom){
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
			if(player.getId()!= playerList.get(playerIndex).getId()){
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
	
	private void startEscape(){
		
		Army defeat = getDefeatEnemy();
		if(defeat != null){
			defeat.changeState(Army.STATE_MOVE);
			changeSubState(SUB_STATE_ACTION_ESCAPE);
		}else{
			changeSubState(SUB_STATE_ACTION_WAIT);
			btnNext.setDisabled(false);
		}
	}
	
	private void aggregation(Army army1, Army army2){
		for(Troop troop : army2.getTroopList()){
			troop.setSubject(false);
			army1.getTroopList().add(troop);
		}
		removeArmy(army2);
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
	
	private void resolveCombat(int result){
		
		switch(result){
		case 0:
			resultBox.start("RESULT", "BIG DEFEAT");
		break;
		
		case 1:
			resultBox.start("RESULT", "DEFEAT");
		break;
		
		case 2:
			resultBox.start("RESULT", "VICTORY");
		break;
		
		case 3:
			resultBox.start("RESULT", "BIG VICTORY");
		break;
		}
		
		//combate contra otro ejercito
		if(getEnemyAtKingdom(playerList.get(playerIndex), activeArmy.getKingdom()) != null){
			
			//Resolucion del combate
			Army defeated = null;
			if(result > 1)
				defeated = getEnemyAtKingdom(playerList.get(playerIndex), activeArmy.getKingdom());
			else
				defeated = getSelectedArmy();
			
			//Comparo si alguno de los territorios adyacentes pertenece al derrotado
			Kingdom defeatTarget = null;
			for(Kingdom domain : defeated.getPlayer().getKingdomList()){
				for(Kingdom border : defeated.getKingdom().getBorderList()){
					if(domain.getId() == border.getId() 
							&& getEnemyAtKingdom(defeated.getPlayer(), border) == null){
						defeatTarget = domain;
						break;
					}
				}
			}
			
			boolean aniquilation = 
				(result == 3 && defeated.getPlayer().getId() != getCurrentPlayer().getId())
				||
				(result == 0 && defeated.getPlayer().getId() == getCurrentPlayer().getId());
			
			if(defeatTarget == null || aniquilation){
				removeArmy(defeated);
			}else{
				defeated.setDefeat(true);
				putArmyAtKingdom(defeated, defeated.getKingdom(), defeatTarget);
			}
			
			if(getSelectedArmy() != null){
				activeArmy.getKingdom().setState(0);
				activeArmy.getKingdom().setTarget(-1);
			}
			
			
		}else{
			if(result > 1){
				//Resolucion del combate
				int state = activeArmy.getKingdom().getState()+1;
				int totalStates = activeArmy.getKingdom().getTotalStates();
				if(state < totalStates){
					activeArmy.getKingdom().setState(state);
					activeArmy.getKingdom().setTarget(-1);
					
				}
				//Conquista
				else{
					activeArmy.getKingdom().setState(0);
					activeArmy.getKingdom().setTarget(-1);
					addNewConquest(playerList.get(playerIndex), activeArmy.getKingdom());
				}
			}else{
				if(result == 0){
					removeArmy(getSelectedArmy());
				}
			}
		}
		
		changeSubState(SUB_STATE_ACTION_RESULT);
	}
	
	private void updateCamera(){
		if(state == STATE_ACTION){
			if(UserInput.getInstance().getMultiTouchHandler().getTouchAction(0) == TouchData.ACTION_MOVE){
				if(lastTouchX != UserInput.getInstance().getMultiTouchHandler().getTouchX(0)){
					cameraTargetX = cameraTargetX + lastTouchX - UserInput.getInstance().getMultiTouchHandler().getTouchX(0);
				}
				if(lastTouchY != UserInput.getInstance().getMultiTouchHandler().getTouchY(0)){
					cameraTargetY = cameraTargetY + lastTouchY - UserInput.getInstance().getMultiTouchHandler().getTouchY(0);
				}
			}
		}
		lastTouchX = UserInput.getInstance().getMultiTouchHandler().getTouchX(0);
		lastTouchY = UserInput.getInstance().getMultiTouchHandler().getTouchY(0);
		cameraTargetX = Math.max(cameraTargetX, worldConver.getLayoutX() / 2f);
		cameraTargetX = Math.min(cameraTargetX, worldConver.getWorldWidth() - worldConver.getLayoutX() / 2f);
		cameraTargetY = Math.max(cameraTargetY, worldConver.getLayoutY() / 2f);
		cameraTargetY = Math.min(cameraTargetY, worldConver.getWorldHeight() - worldConver.getLayoutY() / 2f);
		
		gameCamera.updateCamera(
				(int)cameraTargetX-worldConver.getLayoutX()/2, 
				(int)cameraTargetY-worldConver.getLayoutY()/2);
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

	public Player getCurrentPlayer() {
		return playerList.get(playerIndex);
	}
}
