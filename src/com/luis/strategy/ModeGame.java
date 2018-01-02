package com.luis.strategy;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.luis.lgameengine.menu.Button;
import com.luis.lgameengine.menu.MenuBox;
import com.luis.lgameengine.menu.MenuManager;
import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.gameworld.GameCamera;
import com.luis.lgameengine.gameutils.gameworld.GfxEffects;
import com.luis.lgameengine.gameutils.gameworld.ParticleManager;
import com.luis.lgameengine.gameutils.gameworld.WorldConver;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.input.TouchData;
import com.luis.strategy.army.Army;
import com.luis.strategy.connection.Download;
import com.luis.strategy.constants.Define;
import com.luis.strategy.data.DataKingdom;
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
	
	public static final int TILE_SET_SIZE[] = {24,32,48,64};
	
	public static float worldWidth;
	public static float worldHeight;
	
	static GameCamera gameCamera;
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
					GfxManager.imgButtonPauseRelease.getHeight(),
					null, 0){
				@Override
				public void onButtonPressDown(){}
				
				@Override
				public void onButtonPressUp(){
					isGamePaused = true;
					Main.changeState(Define.ST_GAME_PAUSE, false);
				}
			};
			
			worldConver = new WorldConver(Define.SIZEX, Define.SIZEY, 0, 0, 0, 0, worldWidth, worldHeight);
			
			particleManager = ParticleManager.getInstance();
			gfxEffects = GfxEffects.getInstance();
			
			gameFrame = 0;
			
			map = new Map(
					Define.SIZEX2,
					Define.SIZEY2,
					DataKingdom.getGenterex(Define.SIZEX2, Define.SIZEY2, GfxManager.imgMap),
					GfxManager.imgMap,
					GfxManager.imgSmallCity, GfxManager.imgMediumCity, GfxManager.imgBigCity, 
					GfxManager.imgPlain, GfxManager.imgForest, GfxManager.imgMontain, null);
			
			Player player1 = new Player(1);
			player1.getKingdomList().add(map.getKingdom(1));
			player1.getKingdomList().add(map.getKingdom(2));
			player1.getKingdomList().add(map.getKingdom(3));
			player1.getArmyList().add(new Army(map.getKingdom(1),player1.getFlag(), 
					map.getX(), map.getY(), GfxManager.imgMap.getWidth(), GfxManager.imgMap.getHeight()));
			player1.getArmyList().add(new Army(map.getKingdom(2),player1.getFlag(), 
					map.getX(), map.getY(), GfxManager.imgMap.getWidth(), GfxManager.imgMap.getHeight()));
			
			Player player2 = new Player(2);
			player2.getKingdomList().add(map.getKingdom(7));
			player2.getKingdomList().add(map.getKingdom(8));
			player2.getKingdomList().add(map.getKingdom(6));
			player2.getArmyList().add(new Army(map.getKingdom(7),player2.getFlag(), 
					map.getX(), map.getY(), GfxManager.imgMap.getWidth(), GfxManager.imgMap.getHeight()));
			player2.getArmyList().add(new Army(map.getKingdom(8),player2.getFlag(), 
					map.getX(), map.getY(), GfxManager.imgMap.getWidth(), GfxManager.imgMap.getHeight()));
			
			List<Player> playerList = new ArrayList<Player>();
			
			playerList.add(player1);
			playerList.add(player2);
			
			gameManager = new GameManager(map, playerList);
			
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
					RscManager.sAllTexts[RscManager.TXT_RETURN_MENU],
					new String[]{RscManager.sAllTexts[RscManager.TXT_NO], RscManager.sAllTexts[RscManager.TXT_YES]},
					Font.FONT_MEDIUM, Font.FONT_MEDIUM){
				@Override
				public void onButtonPressUp(){
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
			
			//gameCamera.updateCamera(player.getPosX(), player.getPosY());
			particleManager.update(Main.getDeltaSec());
			gfxEffects.update(Main.getDeltaSec());
			
			gameManager.update(Main.getDeltaSec());
			updateDebugButton();
			break;
			
		case Define.ST_GAME_PAUSE:
			if(!confirmationQuit.update(Main.getDeltaSec(), UserInput.getInstance().getMultiTouchHandler())){
				
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
			
			if (showDebugInfo) {
				_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
				_g.setTextSize(32);
				_g.setAlpha(160);
				_g.setColor(0x88000000);
				_g.fillRect(0, 0, Define.SIZEX, _g.getTextHeight() * 2);
				_g.setAlpha(255);
				
				_g.drawText("State: " + gameManager.getState(), 0, _g.getTextHeight(), Main.COLOR_WHITE);
				_g.drawText("Sub-State: " + gameManager.getSubState(), (int)(Define.SIZEX*0.33), _g.getTextHeight(), Main.COLOR_WHITE);
				_g.drawText("Player: " + (gameManager.getCurrentPlayer()+1), (int)(Define.SIZEX*0.66), _g.getTextHeight(), Main.COLOR_WHITE);
				
				String kingdomList = "";
				for(Kingdom kingdom : gameManager.getPlayerList().get(gameManager.getCurrentPlayer()).getKingdomList()){
					kingdomList += kingdom.getId() + ", ";
				}
				_g.drawText("Domains: " + kingdomList, 0, _g.getTextHeight()*2, Main.COLOR_WHITE);
				
				
				//Reinos
				/*
				_g.drawText("FramesXSecond: " + Main.iFramesXSecond, 0, _g.getTextHeight(), Main.COLOR_WHITE);
				_g.drawText("DeltaTime: " + Main.getDeltaSec(), Define.SIZEX2, _g.getTextHeight(), Main.COLOR_WHITE);
				*/
			}
			drawDebugButton(_g);
			
			
			break;
			
		case Define.ST_GAME_PAUSE:
			_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			//_g.drawImage(GfxManager.vImgBackground, 0, 0, 0);
			_g.setColor(Main.COLOR_RED);
			_g.fillRect(0, 0, Define.SIZEX, Define.SIZEY);
			MenuManager.drawButtonsAndTextY(_g, 2, new String[]{"CONTINUAR", "SALIR"},
				    Font.FONT_BIG, optionSelect, null, GfxManager.vImgMenuButtons, Main.iFrame);
			
			confirmationQuit.draw(_g);
			break;
		}
	}
	
	public static boolean showDebugInfo;
	public static final int DEBUG_BUTTON_W = Define.SIZEX12+Define.SIZEX16;
	public static final int DEBUG_BUTTON_H = Define.SIZEY12-Define.SIZEY32;
	private static void drawDebugButton(Graphics _g){
		_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
		if(!showDebugInfo){
			_g.setColor(Main.COLOR_RED);
		}else{
			_g.setColor(Main.COLOR_GREEN);
		}
		_g.fillRect(0, Define.SIZEY - DEBUG_BUTTON_H, DEBUG_BUTTON_W, DEBUG_BUTTON_H);
		_g.drawText("DEBUG", DEBUG_BUTTON_W/8, Define.SIZEY-DEBUG_BUTTON_H/8, Main.COLOR_WHITE);
	}
	
	private static void updateDebugButton(){
		if((UserInput.getInstance().getMultiTouchHandler().getTouchFrames(0) == 1) && 
			UserInput.getInstance().compareTouch(0, Define.SIZEY - DEBUG_BUTTON_H, DEBUG_BUTTON_W, Define.SIZEY, 0)){
			showDebugInfo = !showDebugInfo;
		}
	}
	
}
