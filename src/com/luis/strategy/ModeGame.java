package com.luis.strategy;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
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
import com.luis.strategy.constants.Define;
import com.luis.strategy.data.DataKingdom;
import com.luis.strategy.data.GameBuilder;
import com.luis.strategy.game.GameManager;
import com.luis.strategy.map.GameScene;

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
	
	/**
	 * Interface
	 */
	private static Button btnPause;
	
	private static MenuBox confirmationQuitBox;
	private static MenuBox pauseBox;
	
	public static void init(int _iState) {
		
		switch(_iState){
		case Define.ST_GAME_INIT:
		case Define.ST_GAME_CONTINUE_ON_LINE:
			
			btnPause = new Button(
					GfxManager.imgButtonPauseRelease, 
					GfxManager.imgButtonPauseFocus, 
					Define.SIZEX - Define.SIZEX64 - GfxManager.imgButtonPauseRelease.getWidth()/2, 
					Define.SIZEY8,
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
			
			if(Main.state == Define.ST_GAME_INIT){
				
				GameScene gameScene = GameBuilder.getInstance().buildPassAndPlay();
				GameState.getInstance().setGameScene(gameScene);
			}
			else if(Main.state == Define.ST_GAME_CONTINUE_ON_LINE){
				GameScene gameScene;
				if(GameState.getInstance().getSceneData().getState() == 0){
					gameScene = GameBuilder.getInstance().buildStartGameScene();
				}else{
					gameScene = GameBuilder.getInstance().buildGameScene();
				}
				GameState.getInstance().setGameScene(gameScene);
			}
			
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
			gameManager = new GameManager(worldConver, gameCamera, GameState.getInstance().getGameScene());
			
			break;
		
		case Define.ST_GAME_RUN:
			
			break;
			
		case Define.ST_GAME_PAUSE:
			
			
			/*
			//Escrutura online
			HttpURLConnection connection = null;
			try {
				// open URL connection
				URL url = new URL(Define.SERVER_URL + "mapTestServlet2");//online
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestProperty("Content-Type", "application/octet-stream");
				connection.setRequestMethod("POST");
				connection.setDoInput(true);
				connection.setDoOutput(true);
				connection.setUseCaches(false);

				// send object
				ObjectOutputStream objOut = new ObjectOutputStream(
						connection.getOutputStream());
				objOut.writeObject(GameBuilder.getInstance().buildSceneData());
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
			*/
			
			
			/*
			 * Escritura local
			try {
				FileOutputStream fos = Main. context.openFileOutput(Define.DATA_GAME_SAVE, Main.context.MODE_PRIVATE);
				ObjectOutputStream os = new ObjectOutputStream(fos);
				os.writeObject(GameBuilder.getInstance().
						build(GameState.getInstance().getGameScene()));
				os.close();
				fos.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}	
			*/
			
			
			
			pauseBox = new MenuBox(
					Define.SIZEX, Define.SIZEY, 
					GfxManager.imgBigBox, 
					GfxManager.imgButtonMenuBigRelease, GfxManager.imgButtonMenuBigFocus, 
					Define.SIZEX2, Define.SIZEY2,
					null,
					new String[]{
							RscManager.allText[RscManager.TXT_CONTINUE_GAME], 
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
		case Define.ST_GAME_CONTINUE_ON_LINE:
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
				if(confirmationQuitBox.getIndexPressed()==1){
					GameState.getInstance().setGameScene(null);
					System.gc();
				}
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
		case Define.ST_GAME_CONTINUE_ON_LINE:
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
	public static final int DEBUG_BUTTON_Y = Define.SIZEY4 - DEBUG_BUTTON_H/2;
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
