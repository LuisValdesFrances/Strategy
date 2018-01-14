package com.luis.strategy.game;

import java.util.List;

import android.util.Log;

import com.luis.army.gui.ArmyBox;
import com.luis.army.gui.BattleBox;
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
	
	private int turnCount = 0;
	
	public static GameCamera gameCamera;
	public static WorldConver worldConver;
	
	
	private Map map;
	
	private int currentPlayer;
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
	public static final int SUB_STATE_MANAGEMENT = 6;
	
	//GUI
	private static Button btnNext;
	private static Button btnCancel;
	private static FlagButton btnFlagHelmet;
	private static FlagButton btnFlagCastle;
	private ArmyBox armyBox;
	private BattleBox battleBox;
	private SimpleBox economyBox;
	private SimpleBox resultBox;
	private SimpleBox discardBox;
	
	
	public GameManager(WorldConver worldConver, GameCamera gameCamera, Map map, List<Player> pList){
		this.worldConver = worldConver;
		this.gameCamera = gameCamera;
		this.map = map;
		this.playerList = pList;
		
		MenuElement.imgBG = GfxManager.imgBlackBG;
		MenuElement.bgAlpha = GameParams.BG_BLACK_ALPHA;
		
		
		btnNext = new Button(
				GfxManager.imgButtonNextRelease, 
				GfxManager.imgButtonNextFocus, 
				Define.SIZEX2, 
				Define.SIZEY-GfxManager.imgGameHud.getHeight()/2,
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
				Define.SIZEY-GfxManager.imgGameHud.getHeight()/2,
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
				changeSubState(SUB_STATE_MANAGEMENT);
				armyBox.start(activeArmy, isSelectedArmyFromCurrentPlayer(getSelectedArmy()));
				hide();
			}
		};
		
		armyBox = new ArmyBox(){
			@Override
			public void onButtonPressUp(){
				switch(this.getIndexPressed()){
				case 0:
					Log.i("Debug", "Opcion 0");
					break;
				}
			}
			@Override
			public void check(){
				if(state == STATE_DISCARD){
					int dif = playerList.get(currentPlayer).getTaxes() - playerList.get(currentPlayer).getSalaries();
					//discardBox.start(null, "SALARY OT TROOPS EXCEEDS THEIR TREASURE (" + dif + ")." + "DISCARD TROOPS.");
					discardBox.setTextBody("SALARY OT TROOPS EXCEEDS THEIR TREASURE (" + dif + ")." + "DISCARD TROOPS.");
					if(dif >= 0){
						discardBox.cancel();
					}
				}
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
		
		resultBox = new SimpleBox(GfxManager.imgSmallBox, true){
			@Override
			public void onButtonPressUp(){
				startEscape();
			}
		};
		
		economyBox = new SimpleBox(GfxManager.imgSmallBox, true){
			@Override
			public void onButtonPressUp(){
				//Si hay acciones obligatorias, como descartarse de tropas, se accede al estado de management
				int dif = playerList.get(currentPlayer).getGold() - playerList.get(currentPlayer).getSalaries();
				if(dif >= 0){
					changeState(STATE_ACTION);
				}else{
					changeState(STATE_DISCARD);
				}
			}
		};
		
		discardBox = new SimpleBox(GfxManager.imgNotificationBox, false);
		discardBox.setModY(-Define.SIZEY2 + GfxManager.imgNotificationBox.getHeight()/2);
		
		
		turnCount = 1;
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
				int dif = playerList.get(currentPlayer).getTaxes() - playerList.get(currentPlayer).getSalaries();
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
				
				//Actualizar animaciones
				playerList.get(currentPlayer).updateAnimations(delta);
				
				for(int i = 0; i < playerList.size(); i++){
					for(Army army: playerList.get(i).getArmyList()){
						if(army.isSelect()){
							cleanArmyAction();
							activeArmy = army;
							activeArmy.setSelected(true);
							btnFlagCastle.start();
							if(i == currentPlayer && army.getState() == Army.STATE_ON){
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
						changeSubState(SUB_STATE_ACTION_WAIT);
						
					}else{
						//Si el territorio es mio
						if(playerList.get(currentPlayer).hasKingom(activeArmy.getKingdom())){
							//Si hay un ejercito enemigo
							if(getEnemyAtKingdom(playerList.get(currentPlayer), activeArmy.getKingdom()) != null){
								changeSubState(SUB_STATE_ACTION_COMBAT);
								battleBox.start(BattleBox.TYPE_BATTLE_ARMY);
							}else{
								activeArmy.getKingdom().setTarget(-1);
								activeArmy.changeState(Army.STATE_OFF);
								changeSubState(SUB_STATE_ACTION_WAIT);
							}
						}else{
							
							changeSubState(SUB_STATE_ACTION_COMBAT);
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
					defeat.changeState(Army.STATE_ON);
						
					changeSubState(SUB_STATE_ACTION_WAIT);
					}
				break;
			case SUB_STATE_MANAGEMENT:
				if(!armyBox.update(UserInput.getInstance().getMultiTouchHandler(), delta)){
					changeSubState(lastSubState);
				}
				break;
			}
		break;
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
						gameCamera.getPosX(), kingdom.getTerrainList().get(kingdom.getTerrainList().size()-1).getAbsoluteX())+GfxManager.imgPlain.getWidth()/2,
						worldConver.getConversionDrawY(
						gameCamera.getPosY(), kingdom.getTerrainList().get(kingdom.getTerrainList().size()-1).getAbsoluteY())+GfxManager.imgPlain.getHeight()/2,
						
						Graphics.BOTTOM | Graphics.HCENTER);
			}
		}
		
		//Army
		for(int i = 0; i < playerList.size(); i++){
			for(Army army: playerList.get(i).getArmyList()){
				boolean isSelected =  subState == SUB_STATE_ACTION_WAIT && 
						getSelectedArmy() != null && getSelectedArmy().getId() == army.getId();
				army.draw(g, getSelectedArmy()!= null && isSelected, i == currentPlayer && army.getState() == Army.STATE_ON);
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
		
		economyBox.draw(g, true);
		armyBox.draw(g, true);
		discardBox.draw(g, false);
		battleBox.draw(g, true);
		resultBox.draw(g, true);
		drawPresentation(g);
	}
	
	private void updateGUI(float delta){
		btnCancel.update(UserInput.getInstance().getMultiTouchHandler());
		btnNext.update(UserInput.getInstance().getMultiTouchHandler());
		btnFlagHelmet.update(UserInput.getInstance().getMultiTouchHandler(), delta);
		btnFlagCastle.update(UserInput.getInstance().getMultiTouchHandler(), delta);
	}
	
	private void drawGUI(Graphics g) {
		g.drawImage(GfxManager.imgGameHud, 0, Define.SIZEY, Graphics.BOTTOM | Graphics.LEFT);
		btnCancel.draw(g, 0, 0);
		btnNext.draw(g, 0, 0);
		btnFlagHelmet.draw(g);
		btnFlagCastle.draw(g);
		
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
			startPresentation(Font.FONT_BIG, "PLAYER " + (currentPlayer+1));
			break;
		case STATE_ECONOMY:
			
			
			//Activo tropas
			for(Player player : playerList)
				for(Army army : player.getArmyList())
					army.changeState(Army.STATE_ON);
			
			//Calculo de ganancias:
			int tax = playerList.get(currentPlayer).getTaxes();
			int salary = playerList.get(currentPlayer).getSalaries();
			
			//Calculo los salarios
			playerList.get(currentPlayer).setGold(tax);
			
			economyBox.start("ECONOMY", "EARNING: +" +tax + " SALARY: -" + salary);
			
			break;
		case STATE_DISCARD:
			int dif = playerList.get(currentPlayer).getTaxes() - playerList.get(currentPlayer).getSalaries();
			
			discardBox.start(null, "SALARY OT TROOPS EXCEEDS THEIR TREASURE (" + dif + ")." + "DISCARD TROOPS.");
			
			break;
		case STATE_ACTION:
			changeSubState(SUB_STATE_ACTION_WAIT);
			break;
		case STATE_END:
			turnCount++;
			currentPlayer = (currentPlayer+1)%playerList.size();
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
			case SUB_STATE_MANAGEMENT:
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
		for(Army a : playerList.get(currentPlayer).getArmyList()){
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
				resultBox.start("RESULT", "VICTORY");
			}else{
				resultBox.start("RESULT", "MASSACRE");
				removeArmy(enemy);
			}
			
			
			activeArmy.getKingdom().setState(0);
			activeArmy.getKingdom().setTarget(-1);
			
			
			
		}else{
			//Resolucion del combate
			int state = activeArmy.getKingdom().getState()+1;
			int totalStates = activeArmy.getKingdom().getTotalStates();
			if(state < totalStates){
				activeArmy.getKingdom().setState(state);
				activeArmy.getKingdom().setTarget(-1);
				resultBox.start("RESULT", "VICTORY");
				
			}
			//Conquista
			else{
				activeArmy.getKingdom().setState(0);
				activeArmy.getKingdom().setTarget(-1);
				addNewConquest(playerList.get(currentPlayer), activeArmy.getKingdom());
				resultBox.start("RESULT", "CONQUEST");
			}
		}
		
		changeSubState(SUB_STATE_ACTION_RESULT);
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
