package com.luis.strategy;

import java.util.ArrayList;
import java.util.List;

import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.gui.MenuBox;
import com.luis.lgameengine.gui.MenuManager;
import com.luis.lgameengine.gameutils.GamePerformance;
import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.gameworld.GameCamera;
import com.luis.lgameengine.gameutils.gameworld.GfxEffects;
import com.luis.lgameengine.gameutils.gameworld.ParticleManager;
import com.luis.lgameengine.gameutils.gameworld.WorldConver;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.strategy.army.Army;
import com.luis.strategy.army.Troop;
import com.luis.strategy.connection.Download;
import com.luis.strategy.constants.Define;
import com.luis.strategy.constants.GameParams;
import com.luis.strategy.data.DataKingdom;
import com.luis.strategy.game.ActionIA;
import com.luis.strategy.game.GameManager;
import com.luis.strategy.game.Player;
import com.luis.strategy.map.Kingdom;
import com.luis.strategy.map.Map;

/**
 * 
 * @author Luis Valdes Frances
 */
public class ModeGame {
	
	/**/
	public static boolean isGamePaused;
	private static int gameFrame;
	
	public static float worldWidth;
	public static float worldHeight;
	
	private static GameCamera gameCamera;
	private static WorldConver worldConver;
	
	
	private static ParticleManager particleManager;
	private static GfxEffects gfxEffects;
	
	private static GameManager gameManager;
	private static Map map;
	
	/**
	 * Interface
	 */
	private static Button btnPause;
	
	private static int optionSelect;
	private static MenuBox confirmationQuit;
	
	public static void init(int _iState) {
		
		switch(_iState){
		case Define.ST_GAME_INIT:
			
			//Test api
			//http://172.104.228.65:8080/StrategyServer/json/test?id=2&name=test%20name&surname=test%20surname
			//https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml
			//Download d = new Download();
			//d.download("http://172.104.228.65:8080/StrategyServer/json/test?id=2&name=test%20name&surname=test%20surname");
			
			switch(GameState.getInstance().getLevel()){
			case 0:
				break;
			case 1:
				break;
			}
			
			/**
			 * Game GUI
			 */
			btnPause = new Button(
					GfxManager.imgButtonPauseRelease, 
					GfxManager.imgButtonPauseFocus, 
					Define.SIZEX-GfxManager.imgButtonPauseRelease.getWidth(), 
					GfxManager.imgButtonPauseRelease.getHeight()*2,
					null, 0){
				@Override
				public void onButtonPressDown(){}
				
				@Override
				public void onButtonPressUp(){
					isGamePaused = true;
					Main.changeState(Define.ST_GAME_PAUSE, false);
				}
			};
			
			particleManager = ParticleManager.getInstance();
			gfxEffects = GfxEffects.getInstance();
			
			gameFrame = 0;
			
			worldConver = new WorldConver(
					Define.SIZEX, Define.SIZEY,
					0,//GfxManager.imgGameHud.getHeight()*2, 
					GfxManager.imgGameHud.getHeight(),
					0,//GfxManager.imgGameHud.getHeight()*2, 
					0,//GfxManager.imgGameHud.getHeight(),
					GfxManager.imgMap.getWidth(), GfxManager.imgMap.getHeight());
			
			gameCamera = new GameCamera(worldConver, 0, 0, 
					GamePerformance.getInstance().getFrameMult(Main.targetFPS));
			
			map = new Map(
					worldConver, gameCamera, 
					0,//GfxManager.imgMap.getWidth()/2, 
					0,//GfxManager.imgMap.getHeight()/2,
					GfxManager.imgMap,
					GfxManager.imgTerrain.get(GameParams.SMALL_CITY), 
					GfxManager.imgTerrain.get(GameParams.MEDIUM_CITY), 
					GfxManager.imgTerrain.get(GameParams.BIG_CITY), 
					GfxManager.imgTerrain.get(GameParams.PLAIN), 
					GfxManager.imgTerrain.get(GameParams.FOREST), 
					GfxManager.imgTerrain.get(GameParams.MONTAIN), 
					null,
					GfxManager.imgCrown);
			
			
			switch(GameState.getInstance().getLevel()){
        	case 0:
        		map.setKingdomList(DataKingdom.getCrom(worldConver, gameCamera, map));
	            break;
        	case 1:
        		map.setKingdomList(DataKingdom.getGenterex(worldConver, gameCamera, map));
        		break;
        	}
			
			Player player1 = new Player("Genterex", new ActionIA(), 1, 1);
			player1.setGold(10);
			player1.getKingdomList().add(map.getKingdom(1));
			player1.getKingdomList().add(map.getKingdom(2));
			Army army = new Army(worldConver, gameCamera, map, player1, map.getKingdom(1),player1.getFlag(), 
					map.getX(), map.getY(), GfxManager.imgMap.getWidth(), GfxManager.imgMap.getHeight());
			player1.getArmyList().add(army);
			
			///*
			Player player2 = new Player("Quaca", new ActionIA(), 4, 14);
			player2.setGold(10);
			player2.getKingdomList().add(map.getKingdom(14));
			player2.getKingdomList().add(map.getKingdom(15));
			
			army = new Army(worldConver, gameCamera, map, player2, map.getKingdom(14),player2.getFlag(), 
					map.getX(), map.getY(), GfxManager.imgMap.getWidth(), GfxManager.imgMap.getHeight());
			player2.getArmyList().add(army);
			//*/
			
			Player player3 = new Player("Lye", new ActionIA(), 3, 19);
			player3.setGold(10);
			player3.getKingdomList().add(map.getKingdom(19));
			player3.getKingdomList().add(map.getKingdom(21));
			
			army = new Army(worldConver, gameCamera, map, player3, map.getKingdom(19),player3.getFlag(), 
					map.getX(), map.getY(), GfxManager.imgMap.getWidth(), GfxManager.imgMap.getHeight());
			player3.getArmyList().add(army);
			
			
			Player player4 = new Player("Crom",  new ActionIA(), 2, 25);
			player4.setGold(10);
			player4.getKingdomList().add(map.getKingdom(25));
			player4.getKingdomList().add(map.getKingdom(26));
			
			army = new Army(worldConver, gameCamera, map, player4, map.getKingdom(25),player4.getFlag(), 
					map.getX(), map.getY(), GfxManager.imgMap.getWidth(), GfxManager.imgMap.getHeight());
			player4.getArmyList().add(army);
			
			Player player5 = new Player("Gapeangue", new ActionIA(), 5, 36);
			player5.setGold(10);
			player5.getKingdomList().add(map.getKingdom(36));
			player5.getKingdomList().add(map.getKingdom(37));
			
			army = new Army(worldConver, gameCamera, map, player5, map.getKingdom(36),player5.getFlag(), 
					map.getX(), map.getY(), GfxManager.imgMap.getWidth(), GfxManager.imgMap.getHeight());
			player5.getArmyList().add(army);
			
			Player player6 = new Player("Levantia", new ActionIA(), 0, 51);
			player6.setGold(10);
			player6.getKingdomList().add(map.getKingdom(51));
			player6.getKingdomList().add(map.getKingdom(53));
			
			army = new Army(worldConver, gameCamera, map, player6, map.getKingdom(51),player6.getFlag(), 
					map.getX(), map.getY(), GfxManager.imgMap.getWidth(), GfxManager.imgMap.getHeight());
			player6.getArmyList().add(army);
			
			
			List<Player> playerList = new ArrayList<Player>();
			playerList.add(player1);
			playerList.add(player2);
			playerList.add(player3);
			playerList.add(player4);
			playerList.add(player5);
			playerList.add(player6);
			
			gameManager = new GameManager(worldConver, gameCamera, map, playerList);
			
			/*
			army = new Army();
			army.setKingdom(map.getKingdom(2));
			player.getArmyList().add(army);
			army = new Army();
			army.setKingdom(map.getKingdom(3));
			player.getArmyList().add(army);
			army = new Army();
			army.setKingdom(map.getKingdom(4));
			player.getArmyList().add(army);
			army = new Army();
			army.setKingdom(map.getKingdom(5));
			player.getArmyList().add(army);
			army = new Army();
			army.setKingdom(map.getKingdom(6));
			player.getArmyList().add(army);
			army = new Army();
			army.setKingdom(map.getKingdom(7));
			player.getArmyList().add(army);
			army = new Army();
			army.setKingdom(map.getKingdom(8));
			player.getArmyList().add(army);
			*/
			
			Main.changeState(Define.ST_GAME_RUN, false);
			break;
			
		case Define.ST_GAME_RUN:
			
			break;
			
		case Define.ST_GAME_PAUSE:
			optionSelect = 0;
			
			confirmationQuit = new MenuBox(
					Define.SIZEX, Define.SIZEY, 
					GfxManager.imgMenuBox, 
					GfxManager.imgButtonRelease, GfxManager.imgButtonFocus, 
					Define.SIZEX2, Define.SIZEY2,
					RscManager.allText[RscManager.TXT_RETURN_MENU],
					new String[]{RscManager.allText[RscManager.TXT_NO], RscManager.allText[RscManager.TXT_YES]},
					Font.FONT_MEDIUM, Font.FONT_MEDIUM){
				@Override
				public void onFinish(){
					switch(this.getIndexPressed()){
					case 0:
						UserInput.getInstance().getMultiTouchHandler().resetTouch();
						UserInput.getInstance().getKeyboardHandler().resetKeys();
						optionSelect = 0;
						break;
					case 1:
						Main.changeState(Define.ST_MENU_SELECT_GAME, false);
					break;
					}
				}
			};
			
			break;
		}
	}

	public static void update(int _iState) {
		switch(_iState){
		case Define.ST_GAME_INIT:
			break;
			
		case Define.ST_GAME_RUN:
			
			gameFrame++;
			btnPause.update(UserInput.getInstance().getMultiTouchHandler());
			
			
			/*
			float cameraSpeed = GameParams.CAMERA_SPEED * Main.getDeltaSec();
			gamePad.update(UserInput.getInstance().getMultiTouchHandler());
			if(UserInput.getInstance().getKeyboardHandler().getPressedKeys(UserInput.KEYCODE_LEFT).getAction() == KeyData.KEY_DOWN
				||
				UserInput.getInstance().getKeyboardHandler().getPressedKeys(UserInput.KEYCODE_LEFT).getAction() == KeyData.KEY_PRESS){
					cameraTargetX -=cameraSpeed;
				}
			else if(UserInput.getInstance().getKeyboardHandler().getPressedKeys(UserInput.KEYCODE_RIGHT).getAction() == KeyData.KEY_DOWN
				||
				UserInput.getInstance().getKeyboardHandler().getPressedKeys(UserInput.KEYCODE_RIGHT).getAction() == KeyData.KEY_PRESS){
					cameraTargetX +=cameraSpeed;
				}
			if(UserInput.getInstance().getKeyboardHandler().getPressedKeys(UserInput.KEYCODE_UP).getAction() == KeyData.KEY_DOWN
				||
				UserInput.getInstance().getKeyboardHandler().getPressedKeys(UserInput.KEYCODE_UP).getAction() == KeyData.KEY_PRESS){
					cameraTargetY -=cameraSpeed;
			}
			else if(UserInput.getInstance().getKeyboardHandler().getPressedKeys(UserInput.KEYCODE_DOWN).getAction() == KeyData.KEY_DOWN
				||
				UserInput.getInstance().getKeyboardHandler().getPressedKeys(UserInput.KEYCODE_DOWN).getAction() == KeyData.KEY_PRESS){
					cameraTargetY +=cameraSpeed;
			}
			*/
			
			particleManager.update(Main.getDeltaSec());
			gfxEffects.update(Main.getDeltaSec());
			gameManager.update(Main.getDeltaSec());
			updateDebugButton();
			break;
			
		case Define.ST_GAME_PAUSE:
			if(!confirmationQuit.update(UserInput.getInstance().getMultiTouchHandler(), Main.getDeltaSec())){
				
				optionSelect = UserInput.getInstance().getOptionMenuTouched_Y(2, optionSelect);
				
				if (UserInput.getInstance().getOkTouched_Y(optionSelect)) {

					switch (optionSelect) {
					case 0:
						Main.changeState(Define.ST_GAME_RUN, true);
						break;
					case 1:
						UserInput.getInstance().getMultiTouchHandler().resetTouch();
						UserInput.getInstance().getKeyboardHandler().resetKeys();
						confirmationQuit.start();
						break;
					}
				}
				break;
				}
			}
		
	}

	public static void draw(Graphics _g, int _iState) {
		_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
		switch(_iState){
		case Define.ST_GAME_INIT:
			break;
			
		case Define.ST_GAME_RUN:
			_g.setColor(0xff000000);
			_g.setClip(0, 0, Define.SIZEX, Define.SIZEX);
			_g.fillRect(0, 0, Define.SIZEX, Define.SIZEX);
			
			gameManager.draw(_g);
			btnPause.draw(_g, 0, 0);
			//gamePad.draw(_g);
			
			drawDebugButton(_g);
			
			break;
			
		case Define.ST_GAME_PAUSE:
			_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			//_g.drawImage(GfxManager.vImgBackground, 0, 0, 0);
			_g.setColor(Main.COLOR_RED);
			_g.fillRect(0, 0, Define.SIZEX, Define.SIZEY);
			MenuManager.drawButtonsAndTextY(_g, 2, new String[]{"CONTINUAR", "SALIR"},
				    Font.FONT_BIG, optionSelect, null, GfxManager.vImgMenuButtons, Main.iFrame);
			
			confirmationQuit.draw(_g, false);
			break;
		}
	}
	
	public static boolean showDebugInfo;
	public static final int DEBUG_BUTTON_W = Define.SIZEX32;
	public static final int DEBUG_BUTTON_H = Define.SIZEX32;
	public static final int DEBUG_BUTTON_X = Define.SIZEX-DEBUG_BUTTON_W-DEBUG_BUTTON_W/2;
	public static final int DEBUG_BUTTON_Y = Define.SIZEX8;
	private static void drawDebugButton(Graphics _g){
		_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
		if(!showDebugInfo){
			_g.setColor(Main.COLOR_RED);
		}else{
			_g.setColor(Main.COLOR_GREEN);
		}
		_g.fillRect(DEBUG_BUTTON_X, DEBUG_BUTTON_Y, DEBUG_BUTTON_W, DEBUG_BUTTON_H);
	}
	
	private static void updateDebugButton(){
		if((UserInput.getInstance().getMultiTouchHandler().getTouchFrames(0) == 1) && 
			UserInput.getInstance().compareTouch(
				DEBUG_BUTTON_X, DEBUG_BUTTON_Y, DEBUG_BUTTON_X + DEBUG_BUTTON_W, DEBUG_BUTTON_Y + DEBUG_BUTTON_H, 0)){
			showDebugInfo = !showDebugInfo;
		}
	}
	
}
