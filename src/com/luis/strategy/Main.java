package com.luis.strategy;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;

import android.app.Activity;
import android.util.Log;

import com.luis.lgameengine.gameutils.GamePerformance;
import com.luis.lgameengine.gameutils.Settings;
import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.lgameengine.implementation.graphics.MyCanvas;
import com.luis.lgameengine.implementation.input.KeyboardHandler;
import com.luis.lgameengine.implementation.sound.SndManager;
import com.luis.strategy.constants.Define;
import com.luis.strategy.datapackage.scene.SceneData;
import com.luis.strategy.datapackage.scene.PreSceneListData;

/**
 * @author Luis Valdes Frances
 */
public class Main extends MyCanvas implements Runnable {

	public static Main instance;
	public static Main getInstance(){
		return instance;
	}
	public static boolean isTouchDevice = true;
	
	public static final boolean IS_MOVE_SOFT_BANNER = false;

	public static int targetFPS;
	public static int iFrame;
	public static long lInitTime;
	private static int iAcumulativeTicks;
	private static long lAcumulativeTime;
	public static int iFramesXSecond;

	private static long deltaTime;
	public static int getDeltaMilis(){
		return (int)deltaTime;
	}
	public static float getDeltaSec(){
		//return 0.03f;//Para debug
		return Math.min(((float)deltaTime / 1000f), 0.1f);
	}
	public static long lastTime;

	private static long minDurationFrame;
	public static boolean isGameRun;

	private KeyboardHandler vKeyData;

	public static int state;
	public static int lastState;

	public static final int COLOR_BLACK = 0x00000000;
	public static final int COLOR_GREEN    = 0xff0ee50e;
	public static final int COLOR_RED      = 0xffA02020;
	public static final int COLOR_ORANGE   = 0xffff7800;
	public static final int COLOR_YELLOW   = 0xfffcff00;
	public static final int COLOR_PURPLE   = 0xffcc00ff;
	public static final int COLOR_PURPLE_GALAXY = 0xff2f0947;
    public static final int COLOR_BLUE     = 0xff202080;
	public static final int COLOR_WHITE    = 0xffffffff;
	
	public static final int COLOR_LILA_BG = 0xffe48bfe;
	public static final int COLOR_BLUE_BG = 0xff88c7fc;
	public static final int COLOR_GREEN_BG = 0xff8bfc88;
	public static final int COLOR_YELOW_BG = 0xfffcf659;

	public static final boolean IS_FPS = true;
	public static final boolean IS_DEBUG = false;
	public static final boolean IS_TOUCH_INPUT_DEBUG = false;
	public static final boolean IS_KEY_INPUT_DEBUG = false;
	public static final boolean IS_GAME_DEBUG = false;

	public static final int INDEX_DATA_LANGUAGE = 0;
	public static final int INDEX_DATA_RECORD = 1;

	public static int iLanguage;
	
	public Main(Activity activity) {
		super(activity, Define.SIZEX, Define.SIZEY);
		instance = this;
		// if(Integer.parseInt(VERSION.SDK) < 5)
		// touchHandler = new SingleTouchHandler(view, scaleX, scaleY);
		// else
		// touchHandler = new MultiTouchHandler(view, scaleX, scaleY);
		
		UserInput.getInstance().init(multiTouchHandler, keyboardHandler);
		SndManager.inicialize(activity);
		isGameRun = true;
	}

	private void initGame() {
		Log.i("INFO", "initMain run");
		targetFPS = GamePerformance.getInstance().getOptimalFrames();//30;
		minDurationFrame = 1000 / targetFPS;
		changeState(Define.ST_MENU_LOGO,true);
	}

	
	
	@Override
	public void run() {

		Log.i("Debug", "Game thread start");
		initGame();

		while (isGameRun) {
			deltaTime = System.currentTimeMillis() - lastTime;
			lastTime = System.currentTimeMillis();
			
			if(orderToChangeState){
				updateChangeState();
			}else{
				switch (state) {
					case Define.ST_MENU_LOGO:
					case Define.ST_MENU_ASK_LANGUAGE:
					case Define.ST_MENU_ASK_SOUND:
					case Define.ST_MENU_MAIN:
					case Define.ST_MENU_OPTIONS:
					case Define.ST_MENU_MORE:
					case Define.ST_MENU_EXIT:
					case Define.ST_MENU_HELP:
					case Define.ST_MENU_ABOUT:
					case Define.ST_MENU_SELECT_GAME:
					case Define.ST_MENU_SELECT_MAP:
					case Define.ST_MENU_CONFIG_MAP:
					case Define.ST_MENU_CAMPAING:
						
					case Define.ST_MENU_ON_LINE_START:
					case Define.ST_MENU_ON_LINE_CREATE_USER:
					case Define.ST_MENU_ON_LINE_LOGIN:
					case Define.ST_MENU_ON_LINE_LIST_ALL_GAME:
					case Define.ST_MENU_ON_LINE_LIST_JOIN_GAME:
					case Define.ST_MENU_ON_LINE_CREATE_SCENE:
					
					case Define.ST_TEST:
					if (!isLoading) {
						ModeMenu.update();
					}
					break;

				case Define.ST_GAME_INIT:
				case Define.ST_GAME_CONTINUE_ON_LINE:
				case Define.ST_GAME_RUN:
				case Define.ST_GAME_PAUSE:
				case Define.ST_GAME_CONFIRMATION_QUIT:
					if (!isLoading) {
						ModeGame.update(state);
					}
					break;
				}
			}
			repaint();

			multiTouchHandler.update();
			keyboardHandler.update();

			while (System.currentTimeMillis() - lInitTime < minDurationFrame)
				Thread.yield();

			// New loop:
			lAcumulativeTime += (System.currentTimeMillis() - lInitTime);
			if (lAcumulativeTime < 1000) {
				iAcumulativeTicks++;
			} else {
				iFramesXSecond = iAcumulativeTicks;
				lAcumulativeTime = 0;
				iAcumulativeTicks = 0;
			}

			lInitTime = System.currentTimeMillis();
			iFrame++;

			/*
			 * if(!vHolder.getSurface().isValid()) continue; paint(vGraphics);
			 * Canvas canvas = vHolder.lockCanvas();
			 * canvas.getClipBounds(vDstRect);//Obtiene la totalidad de la
			 * pantalla. canvas.drawBitmap(vGraphics.mFrameBuffer, null,
			 * vDstRect, null); vHolder.unlockCanvasAndPost(canvas);
			 */

		}
		stop();
	}

	@Override
	protected void paint(Graphics _g) {
		if (!isClock) {
			switch (state) {
				 case Define.ST_MENU_LOGO:
		         case Define.ST_MENU_ASK_LANGUAGE:
		         case Define.ST_MENU_ASK_SOUND:
		         case Define.ST_MENU_MAIN:
		         case Define.ST_MENU_OPTIONS:
		         case Define.ST_MENU_MORE:
		         case Define.ST_MENU_EXIT:
		         case Define.ST_MENU_HELP:
		         case Define.ST_MENU_ABOUT:
		         case Define.ST_MENU_SELECT_GAME:
		         case Define.ST_MENU_SELECT_MAP:
		         case Define.ST_MENU_CONFIG_MAP:
		         case Define.ST_MENU_CAMPAING:
		        	 
		         case Define.ST_MENU_ON_LINE_START:
				 case Define.ST_MENU_ON_LINE_CREATE_USER:
				 case Define.ST_MENU_ON_LINE_LOGIN:
				 case Define.ST_MENU_ON_LINE_LIST_ALL_GAME:
				 case Define.ST_MENU_ON_LINE_LIST_JOIN_GAME:
				 case Define.ST_MENU_ON_LINE_CREATE_SCENE:
		        	 
		         case Define.ST_TEST:
					ModeMenu.draw(_g);
					break;
		         case Define.ST_GAME_INIT:
		         case Define.ST_GAME_CONTINUE_ON_LINE:
		         case Define.ST_GAME_RUN:
		         case Define.ST_GAME_PAUSE:
		         case Define.ST_GAME_CONFIRMATION_QUIT:
		        	 ModeGame.draw(_g, state);
					break;
			}
			
			if (Main.IS_FPS) {
				_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
				_g.setTextSize(Font.SYSTEM_SIZE[Settings.getInstance().getNativeResolution()]);
				_g.drawText("" + iFramesXSecond + "/" + targetFPS,  0, _g.getTextHeight(), COLOR_RED);
			}
			else if (Main.IS_DEBUG) {
				_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
				_g.setTextSize(Font.SYSTEM_SIZE[Settings.getInstance().getNativeResolution()]);
				_g.setAlpha(160);
				_g.setColor(0x88000000);
				_g.fillRect(0, 0, Define.SIZEX, _g.getTextHeight() * 4);
				_g.setAlpha(255);
				_g.drawText("LGameEngine v.: " + Settings.LGAME_ENGINE_VERSION, 0, _g.getTextHeight(), COLOR_WHITE);
				_g.drawText("FPS: " + iFramesXSecond + "/" + targetFPS, 0, _g.getTextHeight() * 2, COLOR_WHITE);
				_g.drawText("DT Real/Trans: " + ((float)deltaTime / 1000f) + "/" + getDeltaSec(), Define.SIZEX4, _g.getTextHeight() * 2, COLOR_WHITE);
				_g.drawText("SizeX: " + Define.SIZEX, 0, _g.getTextHeight() * 3,COLOR_WHITE);
				_g.drawText("SizeY: " + Define.SIZEY, Define.SIZEX2, _g.getTextHeight() * 3, COLOR_WHITE);
				_g.drawText("RealW: " + Settings.getInstance().getRealWidth(), 0, _g.getTextHeight() * 4, COLOR_WHITE);
				_g.drawText("RealH: " + Settings.getInstance().getRealHeight(), Define.SIZEX2, _g.getTextHeight() * 4, COLOR_WHITE);
				
				_g.setColor(Main.COLOR_GREEN);
				_g.fillRect(0, 0, Define.SCR_MIDLE/64, Define.SCR_MIDLE/64);
				_g.fillRect(0, Define.SIZEY-Define.SCR_MIDLE/64, Define.SCR_MIDLE/64, Define.SCR_MIDLE/64);
				_g.fillRect(Define.SIZEX-Define.SCR_MIDLE/64, 0, Define.SCR_MIDLE/64, Define.SCR_MIDLE/64);
				_g.fillRect(Define.SIZEX-Define.SCR_MIDLE/64, Define.SIZEY-Define.SCR_MIDLE/64, Define.SCR_MIDLE/64, Define.SCR_MIDLE/64);
			
			}else if (Main.IS_TOUCH_INPUT_DEBUG){
				_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
				_g.setTextSize(Font.SYSTEM_SIZE[Settings.getInstance().getNativeResolution()]);
				_g.setAlpha(160);
				_g.setColor(0x88000000);
				_g.fillRect(0, 0, Define.SIZEX, _g.getTextHeight() * 7);
				_g.setAlpha(255);
				_g.drawText("TouchAction: " + 
				multiTouchHandler.getTouchAction(0), 0, _g.getTextHeight(),COLOR_WHITE);
				_g.drawText("TouchFrame: " + 
				multiTouchHandler.getTouchFrames(0), Define.SIZEX2-Define.SIZEX4, _g.getTextHeight(), COLOR_WHITE);
				/*
				_g.drawText("Buffer size: " + 
						UserInput.getInstance().getMultiTouchHandler().getBufferSize(), Define.SIZEX2+Define.SIZEX4,_g.getTextHeight() * 5, 
						UserInput.getInstance().getMultiTouchHandler().getBufferSize() <= MultiTouchHandler.BUFFER_SIZE ? COLOR_WHITE : COLOR_RED);
				*/
				_g.drawText("Orin_X: " + 
				multiTouchHandler.getTouchOriginX(0), 0, _g.getTextHeight()*2,COLOR_WHITE);
				_g.drawText("Orin_Y: " + 
				multiTouchHandler.getTouchOriginY(0), Define.SIZEX2-Define.SIZEX4,_g.getTextHeight()*2, COLOR_WHITE);
				_g.drawText("Current_X: " + 
					UserInput.getInstance().getMultiTouchHandler().getTouchX(0), 0, _g.getTextHeight() * 3, COLOR_WHITE);
				_g.drawText("Current_Y: " +
					UserInput.getInstance().getMultiTouchHandler().getTouchY(0), Define.SIZEX2-Define.SIZEX4,_g.getTextHeight() * 3, COLOR_WHITE);
				_g.drawText("Dist_X: " + 
					UserInput.getInstance().getMultiTouchHandler().getTouchDistanceX(0), 0, _g.getTextHeight() * 4, COLOR_WHITE);
				_g.drawText("Dist_Y: " + 
					UserInput.getInstance().getMultiTouchHandler().getTouchDistanceY(0), Define.SIZEX2-Define.SIZEX4,_g.getTextHeight() * 4, COLOR_WHITE);
				
				_g.drawText("Pointer 2: " +
					UserInput.getInstance().getMultiTouchHandler().getTouchAction(1), 0,_g.getTextHeight() * 6, COLOR_WHITE);
				_g.drawText("Pointer 3: " +
					UserInput.getInstance().getMultiTouchHandler().getTouchAction(2), Define.SIZEX2-Define.SIZEX4, _g.getTextHeight() * 6, COLOR_WHITE);
				_g.drawText("Pointer 4: " +
					UserInput.getInstance().getMultiTouchHandler().getTouchAction(3), 0,_g.getTextHeight() * 7, COLOR_WHITE);
				_g.drawText("Pointer 5: " + 
					UserInput.getInstance().getMultiTouchHandler().getTouchAction(4), Define.SIZEX2-Define.SIZEX4, _g.getTextHeight() * 7, COLOR_WHITE);

			}else if (Main.IS_KEY_INPUT_DEBUG){
				_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
				_g.setTextSize(Font.SYSTEM_SIZE[Settings.getInstance().getNativeResolution()]);
				_g.setAlpha(160);
				_g.setColor(0x88000000);
				_g.fillRect(0, 0, Define.SIZEX, _g.getTextHeight() * 3);
				_g.setAlpha(255);
				_g.drawText("Key UP: " + 
					(UserInput.getInstance().getKeyboardHandler().getPressedKeys(UserInput.KEYCODE_UP).getAction()), 0, _g.getTextHeight(),COLOR_WHITE);
				_g.drawText("Key DOWN: " + 
					(UserInput.getInstance().getKeyboardHandler().getPressedKeys(UserInput.KEYCODE_DOWN).getAction()), Define.SIZEX2,_g.getTextHeight(), COLOR_WHITE);
				_g.drawText("Key LEFT: " + 
					(UserInput.getInstance().getKeyboardHandler().getPressedKeys(UserInput.KEYCODE_LEFT).getAction()), 0, _g.getTextHeight() * 2, COLOR_WHITE);
				_g.drawText("Key RIGHT: " + 
					(UserInput.getInstance().getKeyboardHandler().getPressedKeys(UserInput.KEYCODE_RIGHT).getAction()), Define.SIZEX2,_g.getTextHeight() * 2, COLOR_WHITE);
				_g.drawText("Key A: " + 
					(UserInput.getInstance().getKeyboardHandler().getPressedKeys(UserInput.KEYCODE_SHIELD_A).getAction()), 0, _g.getTextHeight() * 3, COLOR_WHITE);
				_g.drawText("Key B: " + 
					(UserInput.getInstance().getKeyboardHandler().getPressedKeys(UserInput.KEYCODE_SHIELD_B).getAction()), Define.SIZEX2,_g.getTextHeight() * 3, COLOR_WHITE);
				

			}
			_g.setAlpha(255);
		} else {
			drawClock(_g);
		}
	}
	
	//Resources:
    private static Random vRandom;// = new Random(0);
    //Obtiene un randon entre el primer parametro(Numero menor) y el segundo parametro(numero mayor). 
    //Ambos incluidos.
    public static int getRandom(int _i0, int _i1) {
        if(vRandom == null) vRandom = new Random();
        return _i0 + Math.abs(vRandom.nextInt() % (1 + _i1 - _i0));
    }
    
    public static int getRandom(int _iNumber) {
        if(vRandom == null) vRandom = new Random();
        if (_iNumber < 0) {
            return (vRandom.nextInt() % -_iNumber);
        }
        try {
            return Math.abs(vRandom.nextInt()) % _iNumber;
        } catch (Exception e) {
            e.printStackTrace();
           return 0;
        }
    }
    
    public static boolean isModule(int number) {
        if ((number % 2) == 1) {
            return false;//impar
        } else {
            return true;//par
        }
    }
    
    public static boolean isIntervalTwo() {
        if (
        		iFrame % (50 * GamePerformance.getInstance().getFrameMult(targetFPS)) < 5 || 
        		(
        		iFrame % (50 * GamePerformance.getInstance().getFrameMult(targetFPS)) > 10
                && 
                iFrame % (50 * GamePerformance.getInstance().getFrameMult(targetFPS)) < 15)
                ) {
            return false;
        } else {
            return true;
        }
    }
    
    public static boolean isDispareCount(float deltaTime, float currentCount, float totalCount){
        return (currentCount%totalCount < totalCount) && ((currentCount + deltaTime)%totalCount < currentCount%totalCount);
        
    }

	public void pause() {
		//MyCanvas.isPause = true;
//		if (iState >= Define.ST_GAME_INIT)
//			Main.changeState(Define.ST_GAME_PAUSE,false);

		SndManager.pauseMusic();
		Log.i("INFO", "Llamada a pause()");
	}

	public void unPause() {
		//if (MyCanvas.isPause) 
		{
			//MyCanvas.isPause = false;
			SndManager.unpauseMusic();
			Log.i("INFO", "Llamada a unPause()");
		}
	}

	public void stop() {
		SndManager.stopMusic();
		SndManager.flushSndManager();
	}

	private static boolean isLoading;
	private static int tripleBufferWait;
	private static boolean orderToChangeState;
	private static int newState;
	private static boolean isLoadGraphics;
	
	public static void changeState(int _iNewState, boolean _isLoadGraphics) {
		if(!orderToChangeState){
			tripleBufferWait = 0;
			orderToChangeState = true;
			newState = _iNewState;
			isLoadGraphics = _isLoadGraphics;
		}
	}
	private static void updateChangeState(){
		if(tripleBufferWait > 3 && orderToChangeState){
			executeChageState(newState, isLoadGraphics);
			tripleBufferWait = 0;
			orderToChangeState = false;
		}else{
			tripleBufferWait++;
		}
	}
	private static void executeChageState(int _iNewState, boolean _isLoadGraphics){
		
		isLoading = true;
		
		UserInput.getInstance().getMultiTouchHandler().resetTouch();
		UserInput.getInstance().getKeyboardHandler().resetKeys();

		lastState = state;
		state = _iNewState;
		///*
		switch(state){
			case Define.ST_GAME_INIT:
			case Define.ST_GAME_CONTINUE_ON_LINE:
				GfxManager.deleteMenuGFX();
				break;
			case Define.ST_MENU_MAIN:
				if(lastState >= Define.ST_GAME_INIT ){
					GfxManager.deleteGameGFX();
				}
				break;
		}
		//*/
		if(_isLoadGraphics){
			instance.startClock();
			GfxManager.loadGFX(_iNewState);
		}

		Log.i("INFO", "Estado cambiado a: " + _iNewState);
		
		
		if (_iNewState < Define.ST_GAME_INIT)
			ModeMenu.init(state);
		else
			ModeGame.init(state);
		
		instance.stopClock();
		isLoading = false;
	}
	

	/*
	 * @Override public boolean onTouch(View _v, MotionEvent _event) { // switch
	 * (_event.getAction()) { // case MotionEvent.ACTION_DOWN: // case
	 * MotionEvent.ACTION_MOVE: // iTouchX = (int)(_event.getX() *
	 * Settings.getScaleX()); // iTouchY = (int)(_event.getX() *
	 * Settings.getScaleY()); // break; // case MotionEvent.ACTION_UP: //
	 * iTouchX = 0; // iTouchY = 0; // break; // } iTouchX =
	 * (int)(_event.getX()); iTouchY = (int)(_event.getX()); return true; }
	 */
	
	
	// Show clock variables:
    private static final int SPEED_TIME_ANIMATION = 200;
	private static final int FRAMES = 4;
	private static long lClCurrentTime;
	private static long lClLastCurrentTime;
	public static int iFrameClock;
	public static boolean isClock = false;
	public static Image imgClock;
	private static Thread tClockThread;
	private static final String TEXT = "Loading...";

	public void startClock() {

		System.out.println("Start clock run");
		isClock = true;
		iFrameClock = 0;
		lClCurrentTime = 0;
		tClockThread = new Thread() {

			public void run() {
				while (isClock) {
					lClLastCurrentTime = System.currentTimeMillis();
					repaint();
					updateClock();
				}
			}
		};
		tClockThread.start();
	}

	private void updateClock() {
		long time = System.currentTimeMillis();
		lClCurrentTime += time - lClLastCurrentTime;
		lClLastCurrentTime = time;

		if (lClCurrentTime > SPEED_TIME_ANIMATION) {
			lClCurrentTime = 0;
			iFrameClock = iFrameClock + 1 == FRAMES ?0:iFrameClock + 1;
			repaint();
		}
	}

	public void stopClock() {
		System.out.println("Stop clock run");
		if (isClock) {
			isClock = false;
			tClockThread = null;
		}
	}

	private void drawClock(Graphics _g) {

		if (isClock) {
			if (imgClock == null){
				try{
				imgClock = Image.createImage("/clock.png");
				}catch(Exception e){
					Log.e("error", "No se encuentra la imagen del reloj de carga");
				}
			}
			if(imgClock != null){
			_g.setColor(COLOR_BLACK);
			_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			_g.fillRect(0, Define.SIZEY2 - imgClock.getHeight(), Define.SIZEX, imgClock.getHeight());
			_g.setClip(
					Define.SIZEX2 - ((imgClock.getWidth() / FRAMES) >> 1),
					Define.SIZEY2 - imgClock.getHeight(),
					imgClock.getWidth() / FRAMES,
					imgClock.getHeight());
			_g.drawImage(
					imgClock,
					Define.SIZEX2 - ((imgClock.getWidth() / FRAMES) >> 1)
							- ((imgClock.getWidth() / FRAMES) * iFrameClock),
					Define.SIZEY2 - imgClock.getHeight(), 0);

			_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
		}
		}else{
			Log.e("error", "No se existe la imagen del reloj de carga");
		}
	}
	
	
	public String sendUser(String URL, String name, String password){
		HttpURLConnection connection = null;
		String result = "";
		try {
			// open URL connection
			URL url = new URL(Define.SERVER_URL + URL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Content-Type", "application/octet-stream");
			connection.setRequestMethod("POST");
			connection.setRequestProperty("name", name);
			connection.setRequestProperty("password", password);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);

			BufferedReader in = 
					new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String str = "";
			while ((str = in.readLine()) != null) {
				result += str;// + "\n";
			}
			in.close();

			System.out.println(result);
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}
		return result;
	}
	
	public String sendScene(String URL, String map, String host, String name){
		HttpURLConnection connection = null;
		String result = "";
		try {
			// open URL connection
			URL url = new URL(Define.SERVER_URL + URL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Content-Type", "application/octet-stream");
			connection.setRequestMethod("POST");
			connection.setRequestProperty("map", map);
			connection.setRequestProperty("host", host);
			connection.setRequestProperty("name", name);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);

			BufferedReader in = 
					new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String str = "";
			while ((str = in.readLine()) != null) {
				result += str;// + "\n";
			}
			in.close();

			System.out.println(result);
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}
		return result;
	}
	
	public String sendInscription(String URL, String scene, String user, boolean create){
		HttpURLConnection connection = null;
		String result = "";
		try {
			// open URL connection
			URL url = new URL(Define.SERVER_URL + URL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Content-Type", "application/octet-stream");
			connection.setRequestMethod("POST");
			connection.setRequestProperty("scene", scene);
			connection.setRequestProperty("user", user);
			connection.setRequestProperty("scene", scene);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);

			BufferedReader in = 
					new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String str = "";
			while ((str = in.readLine()) != null) {
				result += str;// + "\n";
			}
			in.close();

			System.out.println(result);
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}
		return result;
	}
	
	public String sendDataPackage(Serializable dataPackage, String URL){
		HttpURLConnection connection = null;
		String result = "";
		try {
			// open URL connection
			URL url = new URL(Define.SERVER_URL + URL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Content-Type", "application/octet-stream");
			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);

			// send object
			ObjectOutputStream objOut = new ObjectOutputStream(
					connection.getOutputStream());
			objOut.writeObject(dataPackage);
			objOut.flush();
			objOut.close();

			BufferedReader in = 
					new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String str = "";
			while ((str = in.readLine()) != null) {
				result += str;// + "\n";
			}
			in.close();

			System.out.println(result);
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}
		return result;
	}
	
	public PreSceneListData revicePreSceneListData(String URL, String user){
		PreSceneListData preSceneListData = null;
		HttpURLConnection connection = null;
		try {
			// open URL connection
			//String encodeUrl = Define.SERVER_URL + URL + URLEncoder.encode("?user=" + user);
			String encodeUrl = Define.SERVER_URL + URL;
			URL url = new URL(encodeUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Content-Type", "application/octet-stream");
			connection.setRequestMethod("GET");
			connection.setRequestProperty("user", user);
			connection.setDoInput(true);
			connection.setDoOutput(false);
			connection.setUseCaches(false);
			
			ObjectInputStream objIn = new ObjectInputStream(connection.getInputStream());
			preSceneListData = (PreSceneListData) objIn.readObject();
			objIn.close();
			connection.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return preSceneListData;
	}
	
	/*
	public SceneData reviceSceneData(String URL, String scene){
		SceneData sceneData = null;
		HttpURLConnection connection = null;
		try {
			// open URL connection
			//String encodeUrl = Define.SERVER_URL + URL + URLEncoder.encode("?user=" + user);
			String encodeUrl = Define.SERVER_URL + URL;
			URL url = new URL(encodeUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Content-Type", "application/octet-stream");
			connection.setRequestMethod("GET");
			connection.setRequestProperty("scene", scene);
			connection.setDoInput(true);
			connection.setDoOutput(false);
			connection.setUseCaches(false);
			
			ObjectInputStream objIn = new ObjectInputStream(connection.getInputStream());
			sceneData = (SceneData) objIn.readObject();
			objIn.close();
			connection.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sceneData;
	}
	*/
}
