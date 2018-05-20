package com.luis.strategy.game;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.gameutils.gameworld.GameCamera;
import com.luis.lgameengine.gameutils.gameworld.WorldConver;
import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.gui.MenuElement;
import com.luis.lgameengine.gui.NotificationBox;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;
import com.luis.lgameengine.implementation.input.TouchData;
import com.luis.strategy.GameState;
import com.luis.strategy.GfxManager;
import com.luis.strategy.Main;
import com.luis.strategy.ModeGame;
import com.luis.strategy.RscManager;
import com.luis.strategy.UserInput;
import com.luis.strategy.constants.Define;
import com.luis.strategy.constants.GameParams;
import com.luis.strategy.data.DataKingdom;
import com.luis.strategy.gui.ArmyBox;
import com.luis.strategy.gui.BattleBox;
import com.luis.strategy.gui.FlagButton;
import com.luis.strategy.gui.MapBox;
import com.luis.strategy.gui.SimpleBox;
import com.luis.strategy.gui.TerrainBox;
import com.luis.strategy.map.Army;
import com.luis.strategy.map.Kingdom;
import com.luis.strategy.map.GameScene;
import com.luis.strategy.map.Player;
import com.luis.strategy.map.Terrain;
import com.luis.strategy.map.Troop;



public class GameManager {
	
	//
	private Image gameBuffer;
	public float distorsion = 1.12f;
	
	//
	public boolean isAutoPlay(){
		return 
				getCurrentPlayer().getActionIA() != null &&
				//Si la victima es el jugador, se desactivan los triggers automaticos
				(getEnemyAtKingdom(getCurrentPlayer()) == null || (getEnemyAtKingdom(getCurrentPlayer()) != null && getEnemyAtKingdom(getCurrentPlayer()).getPlayer().getActionIA() != null));
	}
	
	public DataSender dataSender;
	
	public static Button btnDebugPause;
	private static boolean isDebugPaused;
	
	private static final float IA_WAIT = 1f;
	
	private static float IAWaitCount;
	
	//Pad
	private int lastTouchX;
	private int lastTouchY;
	private float cameraTargetX;
	private float cameraTargetY;
	
	public GameCamera gameCamera;
	public WorldConver worldConver;
	
	private GameScene gameScene;
	
	private int state;
	public static final int STATE_INCOME = 0;
	public static final int STATE_ECONOMY = 1;
	public static final int STATE_DISCARD = 2;
	public static final int STATE_ACTION = 3;
	public static final int STATE_END = 4;
	public static final int STATE_FINISH = 5;
	public static final int STATE_DEBUG = 6;
	
	
	//SUB-STATE ACTION
	public int subState;
	public int lastSubState;
	public static final int SUB_STATE_ACTION_IA_WAIT_START = 0;
	public static final int SUB_STATE_ACTION_WAIT = 1;
	public static final int SUB_STATE_ACTION_SELECT = 2;
	public static final int SUB_STATE_ACTION_MOVE = 3;
	public static final int SUB_STATE_ACTION_RESOLVE_MOVE = 4;
	public static final int SUB_STATE_ACTION_COMBAT_ANIM = 5;
	public static final int SUB_STATE_ACTION_COMBAT = 6;
	public static final int SUB_STATE_ACTION_RESULT = 7;
	public static final int SUB_STATE_ACTION_CONQUEST = 8;
	public static final int SUB_STATE_ACTION_SCAPE = 9;
	public static final int SUB_STATE_ACTION_RESOLVE_SCAPE = 10;
	public static final int SUB_STATE_ARMY_MANAGEMENT = 11;
	public static final int SUB_STATE_CITY_MANAGEMENT = 12;
	public static final int SUB_STATE_MAP_MANAGEMENT = 13;
	public static final int SUB_STATE_ACTION_IA_WAIT_END = 14;
	
	//GUI
	private Button btnNext;
	private Button btnCancel;
	private FlagButton btnFlagHelmet;
	private FlagButton btnFlagCastle;
	
	private Button btnMap;
	
	private ArmyBox armyBox;
	private BattleBox battleBox;
	private TerrainBox terrainBox;
	private MapBox mapBox;
	private SimpleBox economyBox;
	private SimpleBox resultBox;
	private SimpleBox discardBox;
	private SimpleBox endGameBox;
	
	public GameManager(WorldConver wc, GameCamera gc, GameScene gs){
		this.gameBuffer = Image.createImage(
				(int)wc.getGameLayoutX(), 
				(int)wc.getGameLayoutY());
		this.worldConver = wc;
		this.gameCamera = gc;
		this.gameScene = gs;
		this.lastTouchX = UserInput.getInstance().getMultiTouchHandler().getTouchX(0);
		this.lastTouchY = UserInput.getInstance().getMultiTouchHandler().getTouchY(0);
		
		this.cameraTargetX=0;//worldConver.getCentlayoutX();
		this.cameraTargetY=0;//=worldConver.getCentlayoutY();
		
		this.dataSender = new DataSender();
		MenuElement.bgAlpha = GameParams.BG_BLACK_ALPHA;
		
		btnDebugPause = new Button(
				GfxManager.imgButtonDebugPauseRelease, 
				GfxManager.imgButtonDebugPauseFocus, 
				Define.SIZEX64 + GfxManager.imgButtonDebugPauseRelease.getWidth()/2, 
				Define.SIZEY4,
				null, 0){
			@Override
			public void onButtonPressDown(){}
			
			@Override
			public void onButtonPressUp(){
				
				isDebugPaused = !isDebugPaused;
				if(state == STATE_DEBUG){
					changeState(STATE_END);
				}else{
					btnDebugPause.setDisabled(true);
				}
			}
		};
		
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
				hide();
				btnFlagCastle.hide();
				
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
				btnFlagHelmet.hide();
				hide();
			}
		};
		
		btnMap = new Button(
				GfxManager.imgButtonMapRelease,
				GfxManager.imgButtonMapFocus, 
				Define.SIZEX64 + GfxManager.imgButtonMapRelease.getWidth()/2, 
				Define.SIZEY8,
				null, 0){
			@Override
			public void onButtonPressDown(){}
			
			@Override
			public void onButtonPressUp(){
				if(state == STATE_DEBUG){
					mapBox.start(gameScene.getPlayerList());
					btnMap.setDisabled(true);
				}else{
					if(getCurrentPlayer().getActionIA() == null && subState == SUB_STATE_ACTION_WAIT){
						changeSubState(SUB_STATE_MAP_MANAGEMENT);
						mapBox.start(gameScene.getPlayerList());
						setDisabled(true);
					}
				}
			}
		};
		
		armyBox = new ArmyBox(){
			@Override
			public void check(){
				if(state == STATE_DISCARD){
					/*
					int dif = getCurrentPlayer().getTaxes() - getCurrentPlayer().getCost(false);
					if(dif >= 0){
						discardBox.cancel();
					}
					*/
					if(getCurrentPlayer().getGold() >= 0){
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
						gameScene.getMapObject(), 
						getCurrentPlayer(),
						getKingdom(),
						getCurrentPlayer().getFlag(), 
						gameScene.getMapObject().getX(), gameScene.getMapObject().getY(), gameScene.getMapObject().getWidth(), gameScene.getMapObject().getHeight());
				army.initTroops();
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
		
		mapBox = new MapBox(worldConver, gameScene.getNumberPartsW(), gameScene.getNumberPartsH());
		
		battleBox = new BattleBox(){
			@Override
			public void onFinish(){
				switch(this.getIndexPressed()){
				case 0:
					resolveCombat(battleBox.getResult());
					break;
				case 1:
					if(isScape()){
						Army defeated = getEnemyAtKingdom(getCurrentPlayer());
						defeated.setDefeat(true);
						NotificationBox.getInstance().addMessage(getCurrentPlayer().getName() + " scape!");
						startEscape();
					}else{
						changeSubState(SUB_STATE_ACTION_WAIT);
					}
					break;
				}
			}
		};
		
		resultBox = new SimpleBox(GfxManager.imgSmallBox, true, false){
			@Override
			public void onFinish(){
				if(startConquest){
					changeSubState(SUB_STATE_ACTION_CONQUEST);
				}else{
					startEscape();
				}
			}
		};
		
		economyBox = new SimpleBox(GfxManager.imgSmallBox, true, false){
			@Override
			public void onFinish(){
				//Compruebo si estoy en saldo negativo
				if(getCurrentPlayer().getGold() < 0){
					changeState(STATE_DISCARD);
				}else{
					changeState(STATE_ACTION);
				}
			}
		};
		
		endGameBox = new SimpleBox(GfxManager.imgSmallBox, true, false){
			@Override
			public void onFinish() {
				super.onWaitFinish();
				if(GameState.getInstance().getGameMode() == GameState.GAME_MODE_ONLINE){
					dataSender.sendGameScene(gameScene, 2);
					//Envio notificaciones
					Main.changeState(Define.ST_MENU_ON_LINE_LIST_ALL_GAME, true);
				}else{
					Main.changeState(Define.ST_MENU_MAIN, true);
				}
			}
		};
		discardBox = new SimpleBox(GfxManager.imgNotificationBox, false, false){
			@Override
			public void onFinish() {
				super.onFinish();
				armyBox.cancel();
			}
		};
		discardBox.setY(GfxManager.imgNotificationBox.getHeight()/2);
		
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
				/*
				int dif = getCurrentPlayer().getTaxes() - getCurrentPlayer().getCost(false);
				if(dif >= 0){
					changeState(STATE_ACTION);
				}
				*/
				if(getCurrentPlayer().getGold() >= 0){
					changeState(STATE_ACTION);
				}
			}
				
			for(int i = 0; i < gameScene.getPlayerList().size(); i++){
				for(Army army: gameScene.getPlayerList().get(i).getArmyList()){
					if(army.isSelect()){
						army.getButton().reset();
						cleanArmyAction();
						setSelectedArmy(army);
						btnFlagCastle.start();
					}
				}
			}
		break;
		
		case STATE_ACTION:
			switch(subState){
			
			
			case SUB_STATE_ACTION_IA_WAIT_START:
				
				//Si ya estaba esperando, espera menos
				float t = lastSubState != SUB_STATE_ACTION_WAIT?IA_WAIT:IA_WAIT/2; 
				
				if(getCurrentPlayer().getActionIA() != null && IAWaitCount < t){
					IAWaitCount+=delta;
				}else{
					IAWaitCount = 0;
					changeSubState(SUB_STATE_ACTION_WAIT);
				}
				break;
			case SUB_STATE_ACTION_WAIT:
				
				//Atcualizar iteracion terreno:
				for(Kingdom kingdom : gameScene.getKingdomList()){
					for(Terrain terrain : kingdom.getTerrainList()){
						if(terrain.isSelect()){
							terrain.getButton().reset();
							
							//Porculeria de los terrenos
							if(terrain.getType() >= GameParams.SMALL_CITY){
							
								terrainBox.start(getCurrentPlayer(), kingdom,
									terrain.getType() >= GameParams.SMALL_CITY &&
									getArmyAtKingdom(kingdom)== null && 
									getCurrentPlayer().hasKingom(kingdom), 
									terrain.getType());
									
								changeSubState(SUB_STATE_CITY_MANAGEMENT);
							
							}
						}
					}
				}
					
				for(int i = 0; i < gameScene.getPlayerList().size(); i++){
					for(Army army: gameScene.getPlayerList().get(i).getArmyList()){
						if(army.isSelect()){
							army.getButton().reset();
							cleanArmyAction();
							setSelectedArmy(army);
							btnFlagCastle.start();
							//Camara se posiciona en el seleccionado
							cameraTargetX = getSelectedArmy().getAbsoluteX();
							cameraTargetY = getSelectedArmy().getAbsoluteY();
							if(i == gameScene.getPlayerIndex() && army.getState() == Army.STATE_ON){
								btnFlagHelmet.start();
								setDataTarget(getSelectedArmy());
								changeSubState(SUB_STATE_ACTION_SELECT);
							}else{
								btnFlagHelmet.hide();
							}
						}
					}
				}
			break;
				
			case SUB_STATE_ACTION_SELECT:
				for(Kingdom kingdom: gameScene.getKingdomList()){
					
					//Chequea si el reino que se ha tocado (isSelect) es valido(target != -1)
					if(
						(getCurrentPlayer().getActionIA() == null && kingdom.getTarget()!= -1 && kingdom.isSelect())
						||
						(getCurrentPlayer().getActionIA() != null && getCurrentPlayer().getActionIA().isKingdomToMove(gameScene, kingdom))
							
					){
						kingdom.getButton().reset();
						
						boolean move = kingdom.getId() != getSelectedArmy().getKingdom().getId();
						
						//Si abandono una conquista, se pierde el progreso
						if(move){
							getSelectedArmy().getKingdom().setState(0);
						}
						
						//Actualizo el reino del ejercito
						putArmyAtKingdom(getSelectedArmy(), getSelectedArmy().getKingdom(), kingdom);
						
						//Quito todos los otros indicadores de target
						for(Kingdom k: gameScene.getKingdomList()){
							if(k.getId() != getSelectedArmy().getKingdom().getId()){
								k.setTarget(-1);
							}
						}
						if(move){
							getSelectedArmy().changeState(Army.STATE_MOVE);
							changeSubState(SUB_STATE_ACTION_MOVE);
						}else{
							changeSubState(SUB_STATE_ACTION_RESOLVE_MOVE);
						}
						break;
					}
				}
				break;
				
			case SUB_STATE_ACTION_MOVE:
				if(getSelectedArmy().getPlayer().getActionIA()!=null){
					cameraTargetX = getSelectedArmy().getAbsoluteX();
					cameraTargetY = getSelectedArmy().getAbsoluteY();
				}
				
				//Colision
				int x1 = getSelectedArmy().getAbsoluteX();
				int y1 = getSelectedArmy().getAbsoluteY();
				int w1 = GfxManager.imgArmyIdle.getWidth()/28;
				int h1 = GfxManager.imgArmyIdle.getHeight()/8;
				int x2 = getSelectedArmy().getKingdom().getAbsoluteX();
				int y2 = getSelectedArmy().getKingdom().getAbsoluteY();
				int w2 = GfxManager.imgTargetDomain.getWidth()/4;
				int h2 = GfxManager.imgTargetDomain.getHeight()/4;
				
				if(x1+w1/2>x2-w2/2 && x1-w1/2<x2+w2 && y1+h1/2>y2-h2/2 && y1-h1/2<y2+h2){
					changeSubState(SUB_STATE_ACTION_RESOLVE_MOVE);
				}
				break;
				
			case SUB_STATE_ACTION_COMBAT_ANIM:
				if(getSelectedArmy().getState() == Army.STATE_OFF){
					changeSubState(SUB_STATE_ACTION_COMBAT);
				}
				break;
			case SUB_STATE_ACTION_COMBAT:
				battleBox.update(UserInput.getInstance().getMultiTouchHandler(), delta);
				break;
				
			case SUB_STATE_ACTION_RESULT:
				resultBox.update(UserInput.getInstance().getMultiTouchHandler(), delta);
				break;
			case SUB_STATE_ACTION_CONQUEST:
				if(!updateConquest(delta)){
					startEscape();
				}
				break;
				
			case SUB_STATE_ACTION_SCAPE:
				
				if(getDefeatArmy().getPlayer().getActionIA()!=null){
					cameraTargetX = getDefeatArmy().getAbsoluteX();
					cameraTargetY = getDefeatArmy().getAbsoluteY();
				}
				
				//Colision
				x1 = getDefeatArmy().getAbsoluteX();
				y1 = getDefeatArmy().getAbsoluteY();
				w1 = GfxManager.imgArmyIdle.getWidth()/28;
				h1 = GfxManager.imgArmyIdle.getHeight()/8;
				x2 = getDefeatArmy().getKingdom().getAbsoluteX();
				y2 = getDefeatArmy().getKingdom().getAbsoluteY();
				w2 = GfxManager.imgTargetDomain.getWidth()/4;
				h2 = GfxManager.imgTargetDomain.getHeight()/4;
					
				if(x1+w1/2>x2-w2/2 && x1-w1/2<x2+w2 && y1+h1/2>y2-h2/2 && y1-h1/2<y2+h2){
					changeSubState(SUB_STATE_ACTION_RESOLVE_SCAPE);
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
			case SUB_STATE_MAP_MANAGEMENT:
				if(!mapBox.update(UserInput.getInstance().getMultiTouchHandler(), delta)){
					changeSubState(SUB_STATE_ACTION_WAIT);
				}
				break;
			case SUB_STATE_ACTION_IA_WAIT_END:
				if(getCurrentPlayer().getActionIA() != null && IAWaitCount < IA_WAIT){
					IAWaitCount+=delta;
				}else{
					IAWaitCount = 0;
					changeState(STATE_END);
				}
				break;
			}
		break;
		
		
		case STATE_FINISH:
			if(!endGameBox.update(UserInput.getInstance().getMultiTouchHandler(), delta)){
				
			}
			break;
		case STATE_DEBUG:
			if(!mapBox.update(UserInput.getInstance().getMultiTouchHandler(), delta)){
				btnMap.setDisabled(false);
			}
			break;
		}
		
		if(
			!armyBox.isActive() && 
			!battleBox.isActive() && 
			!economyBox.isActive() &&
			!resultBox.isActive() &&
			!endGameBox.isActive() &&
			!terrainBox.isActive()){
			updateCamera();
			if(getCurrentPlayer().getActionIA() == null && state == STATE_ACTION){
				gameScene.update(UserInput.getInstance().getMultiTouchHandler(), worldConver, gameCamera, delta);
			}
		}
		
		updateGUI(UserInput.getInstance().getMultiTouchHandler(), delta);
		
		
		//Actualizar animaciones
		for(Player player : gameScene.getPlayerList())
			player.updateArmies(UserInput.getInstance().getMultiTouchHandler(), worldConver, gameCamera, Main.getDeltaSec());
		
	}
	
	public void draw(Graphics g){
		
		//Pintado en el buffer
		gameScene.drawMap(gameBuffer.getGraphics(), worldConver, gameCamera, gameScene.getPlayerList());
		
		//Flags
		for(Player player : gameScene.getPlayerList()){
			int i=0;
			for(Kingdom kingdom : player.getKingdomList()){
				//Controla que no se pinta la ultima bandera mientras se ejecuta e efecto de conquista del jugador en curso
				if(
						player.getId() != getCurrentPlayer().getId() ||
						(!startConquest && subState != SUB_STATE_ACTION_CONQUEST) || 
						i!=player.getKingdomList().size()-1){
					
					gameBuffer.getGraphics().setClip(0, 0, gameBuffer.getWidth(), gameBuffer.getHeight());
					gameBuffer.getGraphics().drawImage(GfxManager.imgFlagList.get(player.getFlag()),
							worldConver.getConversionDrawX(
							gameCamera.getPosX(), kingdom.getTerrainList().get(kingdom.getTerrainList().size()-1).getAbsoluteX())+
								GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth()/2,
							worldConver.getConversionDrawY(
							gameCamera.getPosY(), kingdom.getTerrainList().get(kingdom.getTerrainList().size()-1).getAbsoluteY())+
								GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight()/2,
							
							Graphics.BOTTOM | Graphics.HCENTER);
					i++;
				}
			}
		}
		
		
		if(subState == SUB_STATE_ACTION_SELECT && getCurrentPlayer().getActionIA() == null)
			gameScene.drawTarget(gameBuffer.getGraphics(), worldConver, gameCamera);
		
		
		 //float totalW = (float)gameBuffer.getWidth()*distorsion;
		 //float totalH = (float)gameBuffer.getHeight()*distorsion;
		 
		 //Army
		 for(int i = 0; i < gameScene.getPlayerList().size(); i++){
			 for(Army army: gameScene.getPlayerList().get(i).getArmyList()){
				 boolean isSelected =  subState == SUB_STATE_ACTION_WAIT && 
						 getSelectedArmy() != null && getSelectedArmy().getId() == army.getId();
				 army.draw(gameBuffer.getGraphics(), worldConver, gameCamera,
						 getSelectedArmy()!= null && isSelected, i == gameScene.getPlayerIndex() && army.getState() == Army.STATE_ON,
						 distorsion, distorsion, gameScene);
			 }
		 }
		 /*
		 g.drawDistorisionImage(
				 gameBuffer, 
				 (int)worldConver.getCentGameLayoutX() - gameBuffer.getWidth()/2 , (int)worldConver.getCentGameLayoutY() - gameBuffer.getHeight()/2, //Point corner top-left 
				 (int)worldConver.getCentGameLayoutX() + gameBuffer.getWidth()/2 , (int)worldConver.getCentGameLayoutY() - gameBuffer.getHeight()/2, //Point corner top-right
				 (int)worldConver.getCentGameLayoutX() - (int)totalW/2, (int)worldConver.getCentGameLayoutY() - gameBuffer.getHeight()/2 + (int)totalH,  //Point corner button-left
				 (int)worldConver.getCentGameLayoutX() + (int)totalW/2, (int)worldConver.getCentGameLayoutY() - gameBuffer.getHeight()/2 + (int)totalH	 //Point corner button-right
				 );
		*/
		g.drawImage(gameBuffer, Define.SIZEX2, Define.SIZEY2-GfxManager.imgGameHud.getHeight()/2, Graphics.VCENTER | Graphics.HCENTER);
		
		
		//Fin buffer
		g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
		
		
		
		drawGUI(g);
		
		if(ModeGame.showDebugInfo){
			for(Kingdom k : gameScene.getKingdomList())
				TextManager.drawSimpleText(g, Font.FONT_MEDIUM, 
						"ST:" + k.getState(),
					worldConver.getConversionDrawX(gameCamera.getPosX(), k.getAbsoluteX()),
					worldConver.getConversionDrawY(gameCamera.getPosY(), k.getAbsoluteY()),
					Graphics.BOTTOM | Graphics.RIGHT);
			
			for(Player p : gameScene.getPlayerList())
				for(Army a : p.getArmyList())
					TextManager.drawSimpleText(g, Font.FONT_SMALL, 
					""+a.getKingdom().getId() + "-" + a.getId(),
					worldConver.getConversionDrawX(gameCamera.getPosX(), a.getAbsoluteX()-Define.SIZEX64),
					worldConver.getConversionDrawY(gameCamera.getPosY(), a.getAbsoluteY()-Define.SIZEX64),
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
			
			
			g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			g.setTextSize(32);
			g.setAlpha(160);
			g.setColor(0x88000000);
			g.fillRect(0, 0, Define.SIZEX, g.getTextHeight() * 3);
			g.setAlpha(255);
			
			g.drawText("CameraX: " + gameCamera.getPosX(), 0, g.getTextHeight(), Main.COLOR_WHITE);
			g.drawText("CameraY: " + gameCamera.getPosY(), (int)(Define.SIZEX*0.33), g.getTextHeight(), Main.COLOR_WHITE);
			g.drawText("State: " + state, 0, g.getTextHeight()*2, Main.COLOR_WHITE);
			g.drawText("Sub-State: " + subState, (int)(Define.SIZEX*0.33), g.getTextHeight()*2, Main.COLOR_WHITE);
			
			String kingdomList = "";
			for(Kingdom kingdom : getCurrentPlayer().getKingdomList()){
				kingdomList += kingdom.getId() + ", ";
			}
			g.drawText("Domains: " + kingdomList, 0, g.getTextHeight()*3, Main.COLOR_WHITE);
		}
		drawConquest(g, getSelectedArmy());
		drawPresentation(g);
	}
	
	private void updateGUI(MultiTouchHandler multiTouchHandler, float delta){
		NotificationBox.getInstance().update(delta);
		
		btnCancel.update(multiTouchHandler);
		btnNext.update(multiTouchHandler);
		btnMap.update(multiTouchHandler);
		btnDebugPause.update(multiTouchHandler);
		btnFlagHelmet.update(multiTouchHandler, delta);
		btnFlagCastle.update(multiTouchHandler, delta);
		
	}
	
	private void drawGUI(Graphics g) {
		if(state == STATE_DEBUG){
			TextManager.drawSimpleText(g, Font.FONT_MEDIUM, "DEBUG PAUSE", 
				GfxManager.imgButtonDebugPauseRelease.getWidth()*2, 
				GfxManager.imgButtonDebugPauseRelease.getHeight(), 
				Graphics.VCENTER | Graphics.LEFT);
		}
		drawRanking(g);
		TextManager.drawSimpleText(g, Font.FONT_MEDIUM, getCurrentPlayer().getName(), 
				0, Define.SIZEY-GfxManager.imgGameHud.getHeight(), Graphics.BOTTOM | Graphics.LEFT);
		g.drawImage(GfxManager.imgGameHud, 0, Define.SIZEY, Graphics.BOTTOM | Graphics.LEFT);
		drawGold(g);
		
		economyBox.draw(g, GfxManager.imgBlackBG);
		armyBox.draw(g);
		discardBox.draw(g, null);
		battleBox.draw(g);
		terrainBox.draw(g);
		mapBox.draw(g);
		endGameBox.draw(g, GfxManager.imgBlackBG);
		resultBox.draw(g, GfxManager.imgBlackBG);
		btnCancel.draw(g, 0, 0);
		btnNext.draw(g, 0, 0);
		btnFlagHelmet.draw(g);
		btnFlagCastle.draw(g);
		btnMap.draw(g, 0, 0);
		btnDebugPause.draw(g, 0, 0);
		NotificationBox.getInstance().draw(g);
	}
	
	private void drawGold(Graphics g){
		int margin = Define.SIZEY64;
		String text = "" + getCurrentPlayer().getGold();
		int totalWidth = GfxManager.imgChest.getWidth() + margin + Font.getFontWidth(Font.FONT_MEDIUM)*text.length();
		int x = Define.SIZEX - Define.SIZEX4 - totalWidth/2; 
		int y = Define.SIZEY - GfxManager.imgGameHud.getHeight()/2 + GfxManager.imgChest.getHeight()/4; 
		g.drawImage(GfxManager.imgChest, x, y, Graphics.VCENTER | Graphics.HCENTER);
		TextManager.drawSimpleText(g, Font.FONT_MEDIUM, text, 
			x+margin + GfxManager.imgChest.getWidth()/2 + Font.getFontWidth(Font.FONT_MEDIUM)*text.length()/2,
			y, 
			Graphics.VCENTER | Graphics.HCENTER);
	}
	
	private void drawRanking(Graphics g){
		int nPlayer = 0;
		for(Player player : gameScene.getPlayerList()){
			if(player.getCapitalkingdom() != null){
				nPlayer++;
			}
		}
		Player[] pList = new Player[nPlayer];
		int index = 0;
		for(int i = 0; i < gameScene.getPlayerList().size(); i++){
			if(gameScene.getPlayerList().get(i).getCapitalkingdom() != null){
				pList[index++] = gameScene.getPlayerList().get(i);
			}
		}
		
		//Ordeno
		for(int i = 0; i < pList.length-1; i++){
			for(int j = 0; j < pList.length-1; j++){
				if(pList[j].getTaxes() >  pList[j+1].getTaxes()){
					Player aux = pList[j+1];
					pList[j+1] = pList[j];
					pList[j] = aux;
				}
			}
		}
		g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
		for(int i = pList.length-1; i > -1; i--){
			TextManager.drawSimpleText(g, Font.FONT_SMALL, 
					pList[i].getName() + " " + pList[i].getTaxes(), 
					Define.SIZEX-Define.SIZEX64, 
					Define.SIZEY-GfxManager.imgGameHud.getHeight() - (i*Font.getFontHeight(Font.FONT_SMALL)), 
					Graphics.BOTTOM | Graphics.RIGHT);
		}
		
	}
	
	private void cleanArmyAction(){
		for(Player player : gameScene.getPlayerList()){
			for(Army army : player.getArmyList()){
				army.setSelected(false);
				army.setDefeat(false);
				army.getButton().reset();
			}
		}
	}
	
	private void setDataTarget(Army army){
		
		//Quito todos lo indicadores de target
		for(Kingdom k: gameScene.getKingdomList()){
			k.setTarget(-1);
		}
		
		
		for(Kingdom border : getSelectedArmy().getKingdom() .getBorderList()){
			for(Kingdom kingdom: gameScene.getKingdomList()){
				if(kingdom.getTarget()== -1 && kingdom.getId() == border.getId()){
					
					//Si tengo un ejercito en la zona me uno
					if(
						getCurrentPlayer().getArmy(kingdom) != null){
						kingdom.setTarget(Kingdom.TARGET_AGGREGATION);
						kingdom.setTouchX(kingdom.getAbsoluteX());
						kingdom.setTouchY(kingdom.getAbsoluteY());
					}else{
						//Si el territorio es mio
						if(getCurrentPlayer().hasKingom(kingdom)){
							//Busco ejercitos enemigos
							if(getEnemyAtKingdom(getCurrentPlayer(), kingdom) != null){
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
							if(getEnemyAtKingdom(getCurrentPlayer(), kingdom) != null){
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
		//Si estoy en territorio hostil(A midad de una conquista), lo a�ado a los seleccionables
		if(!getCurrentPlayer().hasKingom(getSelectedArmy().getKingdom())){
			getSelectedArmy().getKingdom().setTarget(Kingdom.TARGET_BATTLE);
			getSelectedArmy().getKingdom().setTouchX(getSelectedArmy().getKingdom().
					getTerrainList().get(getSelectedArmy().getKingdom().getState()).getAbsoluteX());
			getSelectedArmy().getKingdom().setTouchY(getSelectedArmy().getKingdom().
					getTerrainList().get(getSelectedArmy().getKingdom().getState()).getAbsoluteY());
		}
	}

	private void cancelAction(){
		btnCancel.setDisabled(true);
		switch(state){
		case STATE_INCOME:
			
			break;
		case STATE_ECONOMY:
			
			break;
		case STATE_ACTION:
			
			switch(subState){
			case SUB_STATE_ACTION_WAIT:
			case SUB_STATE_ACTION_SELECT:
				btnFlagHelmet.hide();
				btnFlagCastle.hide();
				//Quito todos lo indicadores de target
				for(Kingdom k: gameScene.getKingdomList()){
					k.setTarget(-1);
				}
				changeSubState(SUB_STATE_ACTION_WAIT);
				break;
			}
			break;
		}
	}
	
	private void changeState(int newState){
		UserInput.getInstance().getMultiTouchHandler().resetTouch();
		cleanArmyAction();
		gameScene.clean();
		btnNext.setDisabled(true);
		btnCancel.setDisabled(true);
		btnMap.setDisabled(true);
		btnFlagHelmet.hide();
		btnFlagCastle.hide();
		subState = 0;
		state = newState;
		switch(state){
		case STATE_INCOME:
			btnDebugPause.setDisabled(getCurrentPlayer().getActionIA() == null);
			
			startPresentation(Font.FONT_BIG, RscManager.allText[RscManager.TXT_GAME_TURN] + 
					(gameScene.getTurnCount()+1) + " - " + getCurrentPlayer().getName());
			cameraTargetX = getCurrentPlayer().getCapitalkingdom().getAbsoluteX();
			cameraTargetY = getCurrentPlayer().getCapitalkingdom().getAbsoluteY();
			break;
		case STATE_ECONOMY:
			
			//Activo tropas
			for(Player player : gameScene.getPlayerList())
				for(Army army : player.getArmyList())
					army.changeState(Army.STATE_ON);
			
			//Calculo de ganancias:
			int tax = getCurrentPlayer().getTaxes();
			//Calculo de salarios
			int salary = getCurrentPlayer().getCost(false);
			
			getCurrentPlayer().setGold(getCurrentPlayer().getGold()+tax-salary);
			
			if(getCurrentPlayer().getActionIA() == null){
				economyBox.start("ECONOMY", "EARNING: +" +tax + " SALARY: -" + salary);
			}else{
				if(getCurrentPlayer().getGold() < 0){
					changeState(STATE_DISCARD);
				}else{
					changeState(STATE_ACTION);
				}
			}
			
			break;
		case STATE_DISCARD:
			if(getCurrentPlayer().getActionIA() == null){
				discardBox.start(null, "Cost of troops exceedes your trasure. Discard troops.");
			}else{
				getCurrentPlayer().getActionIA().discard();
				changeState(STATE_ACTION);
			}
			break;
		case STATE_ACTION:
			/*
			if(getCurrentPlayer().getActionIA() != null){
				changeSubState(SUB_STATE_ACTION_IA_WAIT_START);
			}else{
				changeSubState(SUB_STATE_ACTION_WAIT);
			}
			*/
			changeSubState(SUB_STATE_ACTION_WAIT);
			break;
		case STATE_END:
			if(isDebugPaused){
				changeState(STATE_DEBUG);
			}else{
				if(isFinishGame()){
					changeState(STATE_FINISH);
				}else{
					do{
						gameScene.setPlayerIndex((gameScene.getPlayerIndex()+1)%gameScene.getPlayerList().size());
					}
					while(getCurrentPlayer().getCapitalkingdom() == null);
					if(gameScene.getPlayerIndex()==0)
						gameScene.setTurnCount(gameScene.getTurnCount()+1);
					
					
					if(GameState.getInstance().getGameMode() == GameState.GAME_MODE_ONLINE){
						//Notification online
						String message = 
								GameState.getInstance().getSceneData().getId() + "-" +
										DataKingdom.SCENARY_NAME_LIST[GameState.getInstance().getMap()] + " " +
										RscManager.allText[RscManager.TXT_GAME_TURN] + (gameScene.getTurnCount()+1);
						
						dataSender.addNotification(getCurrentPlayer().getName(), message);
						dataSender.sendGameScene(gameScene, 1);
						dataSender.sendGameNotifications();
						
						
						Main.changeState(Define.ST_MENU_ON_LINE_LIST_ALL_GAME, true);
					}else{
						changeState(STATE_INCOME);
					}
				}
			}
			break;
		case STATE_FINISH:
			endGameBox.start(
					RscManager.allText[RscManager.TXT_GAME_VICTORY], 
					RscManager.allText[RscManager.TXT_GAME_PLAYER] + " " + getWinner().getName() + " " +
							RscManager.allText[RscManager.TXT_GAME_IS_WINNER]);
			break;
		case STATE_DEBUG:
			btnDebugPause.setDisabled(false);
			break;
		}
	}
	
	private void changeSubState(int newSubState){
		lastSubState = subState;
		btnNext.setDisabled(true);
		btnCancel.setDisabled(true);
		btnMap.setDisabled(true);
		
		subState = newSubState;
		
		gameScene.clean();
		
		switch(state){
		case STATE_INCOME:
			break;
		case STATE_ECONOMY:
			break;
		case STATE_ACTION:
			
			btnNext.setDisabled(true);
			btnCancel.setDisabled(true);
			
			switch(subState){
			case SUB_STATE_ACTION_IA_WAIT_START:
				IAWaitCount = 0;
				//Solo espero si IA va a dar una torta
				if(!(getSelectedArmy() != null && 
						(
						getSelectedArmy().getIaDecision().getDecision() == ActionIA.DECISION_ATACK || 
						getSelectedArmy().getIaDecision().getDecision() == ActionIA.DECISION_MOVE_AND_ATACK
						)
					)){
					changeSubState(SUB_STATE_ACTION_WAIT);
				}
				break;
			case SUB_STATE_ACTION_WAIT:
				btnFlagHelmet.hide();
				btnFlagCastle.hide();
				btnNext.setDisabled(getCurrentPlayer().getActionIA()!=null);
				btnCancel.setDisabled(true);
				btnMap.setDisabled(false);
				cleanArmyAction();
				//
				if(getDefeatArmy() != null){
					getDefeatArmy().setDefeat(false);
				}
				if(getCurrentPlayer().getActionIA() != null){
					
					//Management
					getCurrentPlayer().getActionIA().management(worldConver, gameCamera, gameScene.getMapObject(), gameScene.getPlayerList());
					
					//Activo los ejercitos uno a uno
					Army iaArmy = getCurrentPlayer().getActionIA().getActiveArmy(gameScene.getPlayerList());
					
					if(iaArmy != null){
						setSelectedArmy(iaArmy);
						
						//Camara se posiciona en el seleccionado
						cameraTargetX = getSelectedArmy().getAbsoluteX();
						cameraTargetY = getSelectedArmy().getAbsoluteY();
						setDataTarget(getSelectedArmy());
						changeSubState(SUB_STATE_ACTION_SELECT);
					//Cuando no me quedan mas ejercitos, cambio de estado
					}else{
						if(isAutoPlay()){
							changeSubState(SUB_STATE_ACTION_IA_WAIT_END);
						}
					}
				}
				break;
			case SUB_STATE_ACTION_SELECT:
				btnNext.setDisabled(true);
				btnCancel.setDisabled(getCurrentPlayer().getActionIA()!=null);
				break;
			case SUB_STATE_ACTION_MOVE:
				break;
			case SUB_STATE_ACTION_RESOLVE_MOVE:
				resolveMovement();
				break;
			case SUB_STATE_ACTION_COMBAT_ANIM:
				getSelectedArmy().changeState(Army.STATE_ATACK);
				break;
			case SUB_STATE_ACTION_COMBAT:
				getSelectedArmy().changeState(Army.STATE_OFF);
				btnFlagHelmet.hide();
				btnFlagCastle.hide();
				break;
			case SUB_STATE_ACTION_RESULT:
				break;
			case SUB_STATE_ACTION_CONQUEST:
				startConquest(getSelectedArmy().getKingdom(), getSelectedArmy().getPlayer().getFlag());
				break;
			case SUB_STATE_ACTION_SCAPE:
				//Si el que huye no es el que ataca
				if(getDefeatArmy().getId() != getSelectedArmy().getId()){
					getSelectedArmy().changeState(Army.STATE_OFF);
					
					getSelectedArmy().getKingdom().setState(0);//control
				}
				Kingdom defeatTarget = getBorderKingdom(getDefeatArmy());
				putArmyAtKingdom(getDefeatArmy(), getDefeatArmy().getKingdom(), defeatTarget);
				getDefeatArmy().changeState(Army.STATE_MOVE);
				break;
			case SUB_STATE_ACTION_RESOLVE_SCAPE:
				resolveScape();
				break;
			case SUB_STATE_ARMY_MANAGEMENT:
				break;
			case SUB_STATE_CITY_MANAGEMENT:
				break;
			case SUB_STATE_MAP_MANAGEMENT:
				break;
			case SUB_STATE_ACTION_IA_WAIT_END:
				IAWaitCount = 0;
				break;
			}
			
			break;
		case STATE_END:
			break;
		case STATE_DEBUG:
			btnMap.setDisabled(false);
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
	
	private void startPresentation(int font, String text){
		presentationState = STATE_PRESENTATION_MOVE_1;
		presentationFont = font;
		presentationText = text;
		presentationShowCount = 0;
		presentationModX = -Define.SIZEX;
	}
	
	private boolean updatePresentation(float delta){
		
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
				presentationState = 0;
			}
			return true;
		}
		
		return false;
	}
	
	private void drawPresentation(Graphics g){
		if(presentationState != 0){
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
	
	private boolean startConquest;
	private float modSizeConquest;
	private int modAlphaConquest;
	public static final float MAX_SIZE_CONSQUEST = 16f;
	private void startConquest(Kingdom kingdom, int flag){
		this.startConquest = false;
		this.modAlphaConquest = 255;
		this.modSizeConquest = MAX_SIZE_CONSQUEST;
	}
	
	private boolean updateConquest(float delta){
		if(modSizeConquest > 0.01f){
			modSizeConquest -= (modSizeConquest*8f)*delta;
			modAlphaConquest = (int)((modSizeConquest*255f)/MAX_SIZE_CONSQUEST);
			return true;
		}
		if(modAlphaConquest >= 255){
			modSizeConquest = 0f;
			modAlphaConquest = 255;
		}
		//Log.i("INFO", "modSizeConquest: " + modSizeConquest);
		return false;	
	}
	
	private void drawConquest(Graphics g, Army army){
		if(subState == SUB_STATE_ACTION_CONQUEST){
			g.setAlpha(255-modAlphaConquest);
			g.setImageSize(
					1f+modSizeConquest, 
					1f+modSizeConquest);
			
			int flagX = worldConver.getConversionDrawX(gameCamera.getPosX(), 
					army.getKingdom().getTerrainList().get(army.getKingdom().getTerrainList().size()-1).getAbsoluteX())
					+GfxManager.imgTerrain.get(0).getWidth()/2;
			int flagY = worldConver.getConversionDrawX(gameCamera.getPosY(), 
					army.getKingdom().getTerrainList().get(army.getKingdom().getTerrainList().size()-1).getAbsoluteY())
					-GfxManager.imgTerrain.get(0).getHeight()/2;
			
			int modX = (int)(((Define.SIZEX2-flagX)*modSizeConquest)/MAX_SIZE_CONSQUEST);
			int modY = (int)(((Define.SIZEY2-flagY)*modSizeConquest)/MAX_SIZE_CONSQUEST);
			
			g.drawImage(GfxManager.imgFlagList.get(army.getPlayer().getFlag()), 
					flagX+modX,
					flagY+modY,
					Graphics.VCENTER | Graphics.HCENTER);
			g.setAlpha(255);
			g.setImageSize(1f, 1f);
		}
	}
	
	/**
	 * Devuelve el ejercito seleccionado
	 * @return
	 */
	private Army getSelectedArmy(){
		Army selected = null;
		for(Player player : gameScene.getPlayerList()){
			for(Army army : player.getArmyList()){
				if(army.isSelected()){
					selected = army;
					break;
				}
			}
		}
		return selected;
	}
	
	private void setSelectedArmy(Army army){
		for(Player player : gameScene.getPlayerList()){
			for(Army a : player.getArmyList()){
				if(a.getId() == army.getId()){
					a.setSelected(true);
					break;
				}
			}
		}
	}
	
	private Player getWinner(){
		Player winner = null;
		if(isFinishGame()){
			for(int i = 0; i < gameScene.getPlayerList().size() && winner == null; i++){
				if(gameScene.getPlayerList().get(i).getCapitalkingdom() != null){
					winner = gameScene.getPlayerList().get(i);
				}
			}
		}
		return winner;
	}
	
	private Player getPlayerByKingdom(Kingdom kingdom){
		Player player = null;
		for(int i = 0; i < gameScene.getPlayerList().size() && player == null; i++){
			for(int j = 0; j < gameScene.getPlayerList().get(i).getKingdomList().size() && player == null; j++){
				if(gameScene.getPlayerList().get(i).getKingdomList().get(j).getId() == kingdom.getId()){
					player = gameScene.getPlayerList().get(i);
				}
			}
		}
		return player;
	}
	
	private boolean isFinishGame(){
		boolean end = true;
		for(int i = 0; i < gameScene.getPlayerList().size() && end; i++){
			if(gameScene.getPlayerList().get(i).getId() != getCurrentPlayer().getId()){
				if(gameScene.getPlayerList().get(i).getCapitalkingdom() != null){
					end = false;
				}
			}
		}
		return end;
	}
	
	/**
	 * Devuelve true si el ejercito que se pasa por parametro pertenece al jugador en curso.
	 * @param army
	 * @return
	 */
	private boolean isSelectedArmyFromCurrentPlayer(Army army){
		boolean current = false;
		for(Army a : getCurrentPlayer().getArmyList()){
			if(a.getId() == army.getId()){
				current = true;
				break;
			}
		}
		return current;
	}
	
	private Army getDefeatArmy(){
		Army army = null;
		for(Player player: gameScene.getPlayerList()){
			for(Army a : player.getArmyList()){
				if(a.isDefeat()){
					army = a;
					break;
				}
			}
		}
		return army;
	}
	
	private Army getArmyAtKingdom(Kingdom kingdom){
		Army army = null;
		for(Player player : gameScene.getPlayerList()){
			army = player.getArmy(kingdom);
			if(army != null)
				break;
		}
		return army;
	}
	
	/**
	 * Devuelve true si el ejercito del reino que se pasa por parametro contiene un ejercito enemigo (Explorar fronteras)
	 */
	private Army getEnemyAtKingdom(Player player, Kingdom kingdom){
		Army enemy = null;
		for(Player p : gameScene.getPlayerList()){
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
	
	/**
	 * Devuelve true hay un ejercito enemigo en el reino donde se encuentra el ejercito seleccionado
	 */
	private Army getEnemyAtKingdom(Player player){
		if(player.getSelectedArmy() != null){
			Kingdom kingdom = player.getSelectedArmy().getKingdom();
			return getEnemyAtKingdom(player, kingdom);
		}else{
			return null;
		}
	}
	
	private void removeArmy(Army army){
		for(Player player: gameScene.getPlayerList()){
			for(int i = 0; i < player.getArmyList().size(); i++){
				if(player.getArmyList().get(i).getId() == army.getId()){
					player.getArmyList().remove(i);
					break;
				}
			}
		}
	}
	
	private void startEscape(){
		
		Army defeat = getDefeatArmy();
		if(defeat != null){
			changeSubState(SUB_STATE_ACTION_SCAPE);
		}else{
			if(getCurrentPlayer().getActionIA() != null){
				changeSubState(SUB_STATE_ACTION_IA_WAIT_START);
			}else{
				changeSubState(SUB_STATE_ACTION_WAIT);
			}
		}
	}
	
	private void aggregation(Army army1, Army army2){
		for(Troop troop : army2.getTroopList()){
			troop.setSubject(false);
			army1.getTroopList().add(troop);
		}
		army1.setSelected(true);
		army1.setDefeat(army1.isDefeat() || army2.isDefeat());
		removeArmy(army2);
	}
	
	private void addNewConquest(Player player, Kingdom kingdom){
		//Elimino el territorio del domino de cualquier jugador
		for(Player p : gameScene.getPlayerList()){
			p.removeKingdom(kingdom);
		}
		
		player.getKingdomList().add(kingdom);
	}

	private void putArmyAtKingdom(Army army, Kingdom lastKingdom, Kingdom newKingdom){
		army.setLastKingdom(lastKingdom);
		army.setKingdom(newKingdom);
		//Actulizo su zona de touch (Se altera al moverse)
		army.setTouchX(newKingdom.getAbsoluteX());
		army.setTouchY(newKingdom.getAbsoluteY());
	}
	
	private void combat(Terrain terrain, Army armyAtack, Army armyDefense){
		
		int diceDifficult = GameUtils.getInstance().calculateDifficult(terrain, armyAtack, armyDefense);
		
		int result = 0;
		int[] diceValue = new int[]{
				Main.getRandom(1, GameParams.ROLL_SYSTEM), 
				Main.getRandom(1, GameParams.ROLL_SYSTEM), 
				Main.getRandom(1, GameParams.ROLL_SYSTEM)};
		
		for(int i = 0;i < 3; i++){
			if(diceValue[i] == GameParams.ROLL_SYSTEM || diceValue[i] >= diceDifficult){
				result++;
			}
		}
		resolveCombat(result);
		
		if(armyAtack!=null && !armyAtack.isDefeat()){
			armyAtack.changeState(Army.STATE_ATACK);
		}
	}
	private void resolveCombat(int result){
		
		Player defeatPlayer = null;
		Army enemy = getEnemyAtKingdom(getCurrentPlayer());
		
		/*
		 * El ejercito ganador genera la mitad de su valor en da�o al ejercito perdedor
		 * El ejercito perdedor genera la la cuarta parte de su da�o al ejercito ganador
		 */
		boolean showResultBox = 
				getSelectedArmy().getPlayer().getActionIA() == null ||
				(enemy != null && enemy.getPlayer().getActionIA() == null);
		
		boolean changeCapital = false;
		boolean deletePlayer = false;
		
		String textH = "";
		String textB = "";
		String textN1 = "";
		String textN2 = "";
		textH=RscManager.allText[RscManager.TXT_GAME_RESULT];
		switch(result){
			case 0: 
				textB=RscManager.allText[RscManager.TXT_GAME_BIG_DEFEAT];
				break;
			case 1: 
				textB=RscManager.allText[RscManager.TXT_GAME_DEFEAT];
				break;
			case 2: 
				textB=RscManager.allText[RscManager.TXT_GAME_VICTORY];
				break;
			case 3: 
				textB=RscManager.allText[RscManager.TXT_GAME_BIG_VICTORY];
				break;
		}
		
		textN1=textB;
		
		////Hay ejercito enemigo
		if(enemy != null){
			//Resolucion del combate
			Army defeated = null;
			if(result > 1)
				defeated = getEnemyAtKingdom(getCurrentPlayer());
			else
				defeated = getSelectedArmy();
			
			//Comparo si alguno de los territorios adyacentes pertenece al derrotado
			Kingdom defeatTarget = getBorderKingdom(defeated);
			
			
			boolean aniquilation = 
				(result == 3 && defeated.getPlayer().getId() != getCurrentPlayer().getId())
				||
				(result == 0 && defeated.getPlayer().getId() == getCurrentPlayer().getId());
			
			if(defeatTarget == null || aniquilation){
				
				removeArmy(defeated);
				
				textH = RscManager.allText[RscManager.TXT_GAME_BIG_VICTORY];
				textB = 
						RscManager.allText[RscManager.TXT_GAME_THE_ARMY_FROM_PLAYER] + 
						defeated.getPlayer().getName() + " " +
						RscManager.allText[RscManager.TXT_GAME_HAS_BEEN_DESTROYED];
				textN2=textB;
			}else{
				defeated.setDefeat(true);
				//Da�o
				int casualtiesFromArmy = 0;
				int casualtiesFromEnemy = 0;
				if(result == 1){//Ejercito selecionado pierde
					casualtiesFromArmy = (getSelectedArmy().getCost() * 25) / 100; 
					casualtiesFromEnemy = (enemy.getCost() * 50) / 100; 
				}else if(result == 2){//Ejercito selecionado gana
					casualtiesFromArmy = (getSelectedArmy().getCost() * 50) / 100; 
					casualtiesFromEnemy = (enemy.getCost() * 25) / 100;
				}
				getSelectedArmy().setDamage(casualtiesFromEnemy);
				enemy.setDamage(casualtiesFromArmy);
				textH = RscManager.allText[RscManager.TXT_GAME_DEFEAT];
				textB = 
						RscManager.allText[RscManager.TXT_GAME_ATTACKER_LOST] + 
						casualtiesFromEnemy + RscManager.allText[RscManager.TXT_GAME_LOSSES] + " " +
						RscManager.allText[RscManager.TXT_GAME_DEFENSER_LOST] + 
						casualtiesFromArmy + RscManager.allText[RscManager.TXT_GAME_LOSSES];
				textN2=textB;
			}
			//Si el atacante gana, le quita el territorio al defensor
			if(getSelectedArmy() != null && result > 1){
				getSelectedArmy().getKingdom().setState(0);//control
				getSelectedArmy().getKingdom().setTarget(-1);
			}
			
		}
		
		//No hay ejercito enemigo pues se combate en un territorio vacio
		else{
			if(result > 1){
				//Resolucion del combate
				int newState = getSelectedArmy().getKingdom().getState()+1;
				int totalStates = getSelectedArmy().getKingdom().getTotalStates();
				if(newState < totalStates){
					getSelectedArmy().getKingdom().setState(newState);
					getSelectedArmy().getKingdom().setTarget(-1);
				}else{//Conquista
					startConquest = true;
					defeatPlayer = getPlayerByKingdom(getSelectedArmy().getKingdom());
					
					getSelectedArmy().getKingdom().setState(0);
					getSelectedArmy().getKingdom().setTarget(-1);
					addNewConquest(getCurrentPlayer(), getSelectedArmy().getKingdom());
					
					//Cambio de capital
					changeCapital = defeatPlayer != null && defeatPlayer.changeCapital();
					
					//Eliminacion jugador
					deletePlayer = defeatPlayer != null && defeatPlayer.getCapitalkingdom() == null;
				}
			}
		}
		
		//Notificaciones:
		if(!showResultBox){
			NotificationBox.getInstance().addMessage(textN1);
		}
		if(!textN2.equals("")){
			NotificationBox.getInstance().addMessage(textN2);
			dataSender.addNotification(getCurrentPlayer().getName(), textN2);
		}
		if(changeCapital){
			String message = 
					RscManager.allText[RscManager.TXT_GAME_PLAYER] + defeatPlayer.getName() +
					" change his capital";
			cameraTargetX = defeatPlayer.getCapitalkingdom().getAbsoluteX();
			cameraTargetY = defeatPlayer.getCapitalkingdom().getAbsoluteY();
			NotificationBox.getInstance().addMessage(message);
			dataSender.addNotification(getCurrentPlayer().getName(), message);
		}
		if(deletePlayer){
			String message = 
					RscManager.allText[RscManager.TXT_GAME_PLAYER] + defeatPlayer.getName() +
					RscManager.allText[RscManager.TXT_GAME_HAS_BEEN_DESTROYED];
			NotificationBox.getInstance().addMessage(message);
			dataSender.addNotification(getCurrentPlayer().getName(), message);
		}
		
		
		if(showResultBox){
			resultBox.start(textH, textB);
			changeSubState(SUB_STATE_ACTION_RESULT);
		}else{
			if(startConquest){
				changeSubState(SUB_STATE_ACTION_CONQUEST);
			}else{
				startEscape();
			}
		}
	}
	
	private Kingdom getBorderKingdom(Army army) {
		//Comparo si alguno de los territorios adyacentes pertenece al derrotado
		Kingdom defeatTarget = null;
		for(Kingdom domain : army.getPlayer().getKingdomList()){
			for(Kingdom border : army.getKingdom().getBorderList()){
				if(domain.getId() == border.getId() 
						&& getEnemyAtKingdom(army.getPlayer(), border) == null){
					defeatTarget = domain;
					break;
				}
			}
		}
		return defeatTarget;
	}

	private void updateCamera(){
		if(
				(getCurrentPlayer().getActionIA() == null && (state == STATE_ACTION || state == STATE_DISCARD))
				||
				state == STATE_DEBUG){
			if(
					UserInput.getInstance().getMultiTouchHandler().getTouchAction(0) == TouchData.ACTION_MOVE
					&& UserInput.getInstance().getMultiTouchHandler().getTouchFrames(0) > 1){
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
	
	private void resolveMovement(){
		//getSelectedArmy().setX(getSelectedArmy().getKingdom().getX());
		//getSelectedArmy().setY(getSelectedArmy().getKingdom().getY());
		getSelectedArmy().getKingdom().setTarget(-1);
		getSelectedArmy().changeState(Army.STATE_OFF);
		
		
		//Ia ataca libre
		
		//Para ver la opcion de escapar, debe de haber un defensor, territorio al que escapar y el defensor no debe de ser la IA
		boolean scapeOption =
				getEnemyAtKingdom(getCurrentPlayer()) != null  //Hay un defensor
				&&
				getBorderKingdom(getEnemyAtKingdom(getCurrentPlayer())) != null //Hay espacio para el defensor
				&&
				getEnemyAtKingdom(getCurrentPlayer()).getPlayer().getActionIA() == null; //El defensor NO es IA
		
		boolean cancelOption = getCurrentPlayer().getActionIA() == null && getEnemyAtKingdom(getCurrentPlayer()) == null;
		
		
		
		//Si tengo un ejercito en la zona y no soy yo ese ejercito me uno
		List <Army> armyFiendList = new ArrayList<Army>();
		for(int i = 0; i < getCurrentPlayer().getArmyList().size(); i++){
			if(getCurrentPlayer().getArmyList().get(i).getKingdom().getId() == getSelectedArmy().getKingdom().getId()){
				armyFiendList.add(getCurrentPlayer().getArmyList().get(i));
			}
		}
		if(armyFiendList.size() > 1){
			aggregation(armyFiendList.get(0), armyFiendList.get(1));
			//activeArmy = armyList.get(0);
			NotificationBox.getInstance().addMessage("The armies have joined forces");
			if(getCurrentPlayer().getActionIA() != null){
				changeSubState(SUB_STATE_ACTION_IA_WAIT_START);
			}else{
				changeSubState(SUB_STATE_ACTION_WAIT);
			}
			
		}else{
			
			
			//Si hay un ejercito enemigo
			if(getEnemyAtKingdom(getCurrentPlayer()) != null){
				
				//Si hay un efercito de la IA y decide huir
				if(
						getEnemyAtKingdom(getCurrentPlayer()).getPlayer().getActionIA() != null //Es un ejercito de la IA
						&& getBorderKingdom(getEnemyAtKingdom(getCurrentPlayer())) != null //Tiene territorios adyancentes libres
						&& getEnemyAtKingdom(getCurrentPlayer()).getPlayer().getActionIA().
						scape(getSelectedArmy(), getEnemyAtKingdom(getCurrentPlayer()))){//Ha elegido huir
					
					Army defeated = getEnemyAtKingdom(getCurrentPlayer());
					defeated.setDefeat(true);
					NotificationBox.getInstance().addMessage(getDefeatArmy().getPlayer().getName() + " scape!");
					startEscape();
				
				}else{
					//Para batallas, el tipo de box es el primer elemento del territorio
					if(
							getSelectedArmy().getPlayer().getActionIA() != null && 
							getEnemyAtKingdom(getCurrentPlayer()).getPlayer().getActionIA() != null){
						
						combat(getSelectedArmy().getKingdom().getTerrainList().get(0), 
								getSelectedArmy(),
								getEnemyAtKingdom(getCurrentPlayer()));
						
					}else{
						battleBox.start(
								getSelectedArmy().getKingdom().getTerrainList().get(0), 
								getSelectedArmy(),
								getEnemyAtKingdom(getCurrentPlayer()),
								-1,
								cancelOption, scapeOption, isAutoPlay());
						changeSubState(SUB_STATE_ACTION_COMBAT_ANIM);
					}
				}
			}else{
				//Si el territorio es mio
				if(getCurrentPlayer().hasKingom(getSelectedArmy().getKingdom())){
					getSelectedArmy().getKingdom().setTarget(-1);
					getSelectedArmy().changeState(Army.STATE_OFF);
					if(getCurrentPlayer().getActionIA() != null){
						changeSubState(SUB_STATE_ACTION_IA_WAIT_START);
					}else{
						changeSubState(SUB_STATE_ACTION_WAIT);
					}
				}else{
					//Si es la IA, solo se muestra la ventana de combate si se va a producir un combate
					if(getCurrentPlayer().getActionIA() != null){
						if(
							getSelectedArmy().getIaDecision().getDecision() == ActionIA.DECISION_ATACK
							||
							getSelectedArmy().getIaDecision().getDecision() == ActionIA.DECISION_MOVE_AND_ATACK
						){
							combat(getSelectedArmy().getKingdom().getTerrainList().get(0), 
									getSelectedArmy(),
									null);
						}else{
							getSelectedArmy().getKingdom().setTarget(-1);
							getSelectedArmy().changeState(Army.STATE_OFF);
							changeSubState(SUB_STATE_ACTION_IA_WAIT_START);
						}
					}else{
						int kingdomState = getSelectedArmy().getKingdom().getState();
						battleBox.start(
								getSelectedArmy().getKingdom().getTerrainList().get(kingdomState), 
								getSelectedArmy(),
								null,
								getPlayerByKingdom(getSelectedArmy().getKingdom()) != null?
								getPlayerByKingdom(getSelectedArmy().getKingdom()).getFlag():GfxManager.imgFlagBigList.size()-1,
								cancelOption, scapeOption, isAutoPlay());
						changeSubState(SUB_STATE_ACTION_COMBAT_ANIM);
					}
				}
			}
			
			
			
			
			
			
			
			/*
			//Si el territorio es mio
			if(getCurrentPlayer().hasKingom(getSelectedArmy().getKingdom())){
				//Si hay un ejercito enemigo
				if(getEnemyAtKingdom(getCurrentPlayer()) != null){
					
					//Si hay un efercito de la IA y decide huir
					if(
							getEnemyAtKingdom(getCurrentPlayer()).getPlayer().getActionIA() != null //Es un ejercito de la IA
							&& getBorderKingdom(getEnemyAtKingdom(getCurrentPlayer())) != null //Tiene territorios adyancentes libres
							&& getEnemyAtKingdom(getCurrentPlayer()).getPlayer().getActionIA().
							scape(getSelectedArmy(), getEnemyAtKingdom(getCurrentPlayer()))){//Ha elegido huir
						
						Army defeated = getEnemyAtKingdom(getCurrentPlayer());
						defeated.setDefeat(true);
						NotificationBox.getInstance().addMessage(getDefeatArmy().getPlayer().getName() + " scape!");
						startEscape();
					
					}else{
						//Para batallas, el tipo de box es el primer elemento del territorio
						if(
								getSelectedArmy().getPlayer().getActionIA() != null && 
								getEnemyAtKingdom(getCurrentPlayer()).getPlayer().getActionIA() != null){
							
							combat(getSelectedArmy().getKingdom().getTerrainList().get(0), 
									getSelectedArmy(),
									getEnemyAtKingdom(getCurrentPlayer()));
							
						}else{
							battleBox.start(
									getSelectedArmy().getKingdom().getTerrainList().get(0), 
									getSelectedArmy(),
									getEnemyAtKingdom(getCurrentPlayer()),
									-1,
									cancelOption, scapeOption, isAutoPlay());
							changeSubState(SUB_STATE_ACTION_COMBAT_ANIM);
						}
					}
				}else{
					getSelectedArmy().getKingdom().setTarget(-1);
					getSelectedArmy().changeState(Army.STATE_OFF);
					changeSubState(SUB_STATE_ACTION_WAIT);
				}
			}else{
				
				//Si hay un ejercito enemigo
				if(getEnemyAtKingdom(getCurrentPlayer()) != null){
					
					//Si hay el efercito es de la IA y decide huir
					if(
							getEnemyAtKingdom(getCurrentPlayer()).getPlayer().getActionIA() != null //Es un ejercito de la IA
							&& getBorderKingdom(getEnemyAtKingdom(getCurrentPlayer())) != null //Tiene territorios adyancentes libres
							&& getEnemyAtKingdom(getCurrentPlayer()).getPlayer().getActionIA().
							scape(getSelectedArmy(), getEnemyAtKingdom(getCurrentPlayer()))){//Ha elegido huir
						
						Army defeated = getEnemyAtKingdom(getCurrentPlayer());
						defeated.setDefeat(true);
						NotificationBox.getInstance().addMessage(getDefeatArmy().getPlayer().getName() + " scape!");
						startEscape();
						
					}else{
						//Para batallas, el tipo de box es el primer elemento del territorio
						if(
								getSelectedArmy().getPlayer().getActionIA() != null && 
								getEnemyAtKingdom(getCurrentPlayer()).getPlayer().getActionIA() != null){
							
							combat(getSelectedArmy().getKingdom().getTerrainList().get(0), 
									getSelectedArmy(),
									getEnemyAtKingdom(getCurrentPlayer()));
						}else{
							//Si el que recibe ataque es la IA, ya ha decidido no huir, con lo que sehabilito esta opcion
							battleBox.start(
									getSelectedArmy().getKingdom().getTerrainList().get(0), 
									getSelectedArmy(),
									getEnemyAtKingdom(getCurrentPlayer()),
									-1,
									cancelOption, scapeOption, isAutoPlay());
							
							changeSubState(SUB_STATE_ACTION_COMBAT_ANIM);
						}
					}
				//Movimiento a territorios vacios
				}else{
					
					//Si es la IA, solo se muestra la ventana de combate si se va a producir un combate
					if(getCurrentPlayer().getActionIA() != null){
						if(
							getSelectedArmy().getIaDecision().getDecision() == ActionIA.DECISION_ATACK
							||
							getSelectedArmy().getIaDecision().getDecision() == ActionIA.DECISION_MOVE_AND_ATACK
						){
							combat(getSelectedArmy().getKingdom().getTerrainList().get(0), 
									getSelectedArmy(),
									null);
						}else{
							getSelectedArmy().getKingdom().setTarget(-1);
							getSelectedArmy().changeState(Army.STATE_OFF);
							changeSubState(SUB_STATE_ACTION_WAIT);
						}
					}else{
						int kingdomState = getSelectedArmy().getKingdom().getState();
						battleBox.start(
								getSelectedArmy().getKingdom().getTerrainList().get(kingdomState), 
								getSelectedArmy(),
								null,
								getPlayerByKingdom(getSelectedArmy().getKingdom()) != null?
								getPlayerByKingdom(getSelectedArmy().getKingdom()).getFlag():GfxManager.imgFlagBigList.size()-1,
								cancelOption, scapeOption, isAutoPlay());
						changeSubState(SUB_STATE_ACTION_COMBAT_ANIM);
					}
				}
			}
			*/
		}
	}
	
	private void resolveScape(){
		//defeat.setX(defeat.getKingdom().getX());
		//defeat.setY(defeat.getKingdom().getY());
		getDefeatArmy().getKingdom().setTarget(-1);
		
		//Si hay un ejercito amigo, se unen
		//Si tengo un ejercito en la zona y no soy yo ese ejercito me uno
		List <Army> armyFiendList = new ArrayList<Army>();
		for(int i = 0; i < getDefeatArmy().getPlayer().getArmyList().size(); i++){
			if(getDefeatArmy().getPlayer().getArmyList().get(i).getKingdom().getId() == getDefeatArmy().getKingdom().getId()){
				armyFiendList.add(getDefeatArmy().getPlayer().getArmyList().get(i));
			}
		}
		if(armyFiendList.size() > 1){
			aggregation(armyFiendList.get(0), armyFiendList.get(1));
			NotificationBox.getInstance().addMessage("The armies have joined forces");
		}
		
		try{
		if(getDefeatArmy().getPlayer() != null && getDefeatArmy().getPlayer().getId() == getCurrentPlayer().getId()){
			getDefeatArmy().changeState(Army.STATE_OFF);
		}else{
			getDefeatArmy().changeState(Army.STATE_ON);
		}
		}catch(Exception e){
			Log.i("Debug", getDefeatArmy().toString());
			Log.i("Debug", getDefeatArmy().getPlayer().toString());
			Log.i("Debug", getCurrentPlayer().toString());
			changeState(STATE_DEBUG);
		}
			
		if(getCurrentPlayer().getActionIA() != null){
			changeSubState(SUB_STATE_ACTION_IA_WAIT_START);
		}else{
			changeSubState(SUB_STATE_ACTION_WAIT);
		}
	}
	
	private Player getCurrentPlayer() {
		return gameScene.getPlayerList().get(gameScene.getPlayerIndex());
	}
		
}