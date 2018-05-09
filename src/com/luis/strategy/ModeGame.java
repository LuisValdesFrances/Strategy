package com.luis.strategy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.gui.MenuBox;
import com.luis.lgameengine.gameutils.GamePerformance;
import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.gameworld.GameCamera;
import com.luis.lgameengine.gameutils.gameworld.GfxEffects;
import com.luis.lgameengine.gameutils.gameworld.ParticleManager;
import com.luis.lgameengine.gameutils.gameworld.WorldConver;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.strategy.connection.Download;
import com.luis.strategy.connection.GameBuilder;
import com.luis.strategy.constants.Define;
import com.luis.strategy.data.DataKingdom;
import com.luis.strategy.data.GameBuild;
import com.luis.strategy.game.GameManager;
import com.luis.strategy.map.GameScene;
import com.luis.strategy.map.Player;

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
	private static GameScene gameScene;
	
	/**
	 * Interface
	 */
	private static Button btnPause;
	
	private static MenuBox confirmationQuitBox;
	private static MenuBox pauseBox;
	
	public static void init(int _iState) {
		
		switch(_iState){
		case Define.ST_GAME_INIT:
			
			//Test api
			//http://172.104.228.65:8080/StrategyServer/json/test?id=2&name=test%20name&surname=test%20surname
			//https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml
			//Download d = new Download();
			//d.download("http://172.104.228.65:8080/StrategyServer/json/test?id=2&name=test%20name&surname=test%20surname");
			
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
			
			int mapWidth = GfxManager.imgMapList.get(0).getWidth()*
					DataKingdom.MAP_PARTS[GameState.getInstance().getMap()][0];
			int mapHeight = GfxManager.imgMapList.get(0).getHeight()*
					DataKingdom.MAP_PARTS[GameState.getInstance().getMap()][1];
			
			worldConver = new WorldConver(
					Define.SIZEX, Define.SIZEY,
					0,//GfxManager.imgGameHud.getHeight()*2, 
					GfxManager.imgGameHud.getHeight(),
					0,//GfxManager.imgGameHud.getHeight()*2, 
					0,//GfxManager.imgGameHud.getHeight(),
					mapWidth, mapHeight);
			
			gameCamera = new GameCamera(worldConver, 0, 0, 
					GamePerformance.getInstance().getFrameMult(Main.targetFPS));
			
			
			
			if(gameScene == null){
				gameScene = new GameScene(
						GameState.getInstance().getMap(),
						worldConver, gameCamera, 
						0,//GfxManager.imgMap.getWidth()/2, 
						0,//GfxManager.imgMap.getHeight()/2,
						DataKingdom.MAP_PARTS[GameState.getInstance().getMap()][0],
						DataKingdom.MAP_PARTS[GameState.getInstance().getMap()][1]
						);
				
				switch(GameState.getInstance().getMap()){
	        	case 0:
	        		gameScene.setKingdomList(DataKingdom.getGenterex(worldConver, gameCamera, gameScene.getMap()));
		            break;
	        	case 1:
	        	case 2:
	        		gameScene.setKingdomList(DataKingdom.getCrom(worldConver, gameCamera, gameScene.getMap()));
	        		break;
	        	}
				
				List<Player> playerList = GameBuild.getInstance().build(
						GameState.getInstance(), gameScene, worldConver, gameCamera);
				
				gameScene.setPlayerList(playerList);
			}else{
				
			}
			
			gameManager = new GameManager(worldConver, gameCamera, gameScene);
			
			break;
			
		case Define.ST_GAME_RUN:
			
			break;
			
		case Define.ST_GAME_PAUSE:
			
			
			HttpURLConnection connection = null;
			try {

				// open URL connection
				URL url = new URL("http://192.168.1.110:8080/KingServer/mapTestServlet2");
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestProperty("Content-Type", "application/octet-stream");
				connection.setRequestMethod("POST");
				connection.setDoInput(true);
				connection.setDoOutput(true);
				connection.setUseCaches(false);

				// send object
				ObjectOutputStream objOut = new ObjectOutputStream(
						connection.getOutputStream());
				objOut.writeObject(GameBuilder.getInstance().build(gameScene));
				objOut.flush();
				objOut.close();

				BufferedReader in = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));

				String str = "";
				String result = "";
				while ((str = in.readLine()) != null) {
					result += str + "\n";
				}
				in.close();

				System.out.println(result);
				connection.disconnect();

				// Envia el post
				// ObjectInputStream objIn = new
				// ObjectInputStream(conn.getInputStream());

			} catch (Exception ex) {
				ex.printStackTrace();
			}	
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			pauseBox = new MenuBox(
					Define.SIZEX, Define.SIZEY, 
					GfxManager.imgBigBox, 
					GfxManager.imgButtonMenuBigRelease, GfxManager.imgButtonMenuBigFocus, 
					Define.SIZEX2, Define.SIZEY2,
					null,
					new String[]{
							RscManager.allText[RscManager.TXT_CONTINUE], 
							RscManager.allText[RscManager.TXT_OPTIONS],
							RscManager.allText[RscManager.TXT_QUIT]
					},
					Font.FONT_MEDIUM, Font.FONT_MEDIUM){
				@Override
				public void onFinish(){}
			};
			pauseBox.start();
			break;
		case Define.ST_GAME_CONFIRMATION_QUIT:
			confirmationQuitBox = new MenuBox(
					Define.SIZEX, Define.SIZEY, 
					GfxManager.imgBigBox, 
					GfxManager.imgButtonMenuBigRelease, GfxManager.imgButtonMenuBigFocus, 
					Define.SIZEX2, Define.SIZEY2,
					RscManager.allText[RscManager.TXT_RETURN_MENU],
					new String[]{RscManager.allText[RscManager.TXT_NO], RscManager.allText[RscManager.TXT_YES]},
					Font.FONT_MEDIUM, Font.FONT_MEDIUM){
				@Override
				public void onFinish(){}
			};
			confirmationQuitBox.start();
			break;
		}
	}

	public static void update(int _iState) {
		switch(_iState){
		case Define.ST_GAME_INIT:
			Main.changeState(Define.ST_GAME_RUN, false);
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
			if(!pauseBox.update(UserInput.getInstance().getMultiTouchHandler(), Main.getDeltaSec())){
				Main.changeState(
						pauseBox.getIndexPressed()==0?Define.ST_GAME_RUN:Define.ST_GAME_CONFIRMATION_QUIT, 
						false);
			}
			break;
		case Define.ST_GAME_CONFIRMATION_QUIT:
			if(!confirmationQuitBox.update(UserInput.getInstance().getMultiTouchHandler(), Main.getDeltaSec())){
				//Limio el juego
				gameScene =null;
				System.gc();
				Main.changeState(
						confirmationQuitBox.getIndexPressed()==0?Define.ST_GAME_PAUSE:Define.ST_MENU_MAIN, 
						confirmationQuitBox.getIndexPressed()==1);
			}
			break;
		}
	}

	public static void draw(Graphics _g, int _iState) {
		_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
		switch(_iState){
		case Define.ST_GAME_INIT:
			break;
			
		case Define.ST_GAME_RUN:
			gameManager.draw(_g);
			btnPause.draw(_g, 0, 0);
			drawDebugButton(_g);
			break;
			
		case Define.ST_GAME_PAUSE:
			gameManager.draw(_g);
			pauseBox.draw(_g, GfxManager.imgBlackBG);
			break;
		case Define.ST_GAME_CONFIRMATION_QUIT:
			gameManager.draw(_g);
			confirmationQuitBox.draw(_g, GfxManager.imgBlackBG);
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
