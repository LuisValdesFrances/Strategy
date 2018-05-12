package com.luis.strategy;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

import com.luis.lgameengine.gameutils.Settings;
import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.gui.ListBox;
import com.luis.lgameengine.gui.MenuElement;
import com.luis.lgameengine.gui.NotificationBox;
import com.luis.lgameengine.implementation.fileio.FileIO;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.strategy.GameState.PlayerConf;
import com.luis.strategy.constants.Define;
import com.luis.strategy.constants.GameParams;
import com.luis.strategy.data.DataKingdom;
import com.luis.strategy.data.GameBuilder;
import com.luis.strategy.datapackage.scene.SceneData;
import com.luis.strategy.datapackage.user.UserData;
import com.luis.strategy.gui.ConfigMapBox;
import com.luis.strategy.gui.AccountBox;

public class ModeMenu {
	
	public static final int NUMBER_OPTS_LANGUAGE = 3;
	public static final int NUMBER_OPTS_SOUND = 2;
	public static final int NUMBER_OPTS_MAIN_MENU = 4;
	public static final int NUMBER_OPTS_MORE_MENU = 2;
	
	public static int iLanguageSelect;
	public static int iSoundSelect;
	
	public static Image vImgPlanet;
	public static Image vImgAstheroid;
	public static Image vImgAstheroid2;
	
	private static Button btnBack;
	private static Button btnNext;
	private static Button btnCampaign;
	private static Button btnMultiPlayer;
	private static Button btnOnLine;
	private static Button btnPassAndPlay;
	private static Button btnContinue;
	private static Button btnStart;
	
	private static Button btnNewAccount;
	private static Button btnLogin;
	
	private static ListBox selectMapBox;
	private static ConfigMapBox configMapBox;
	private static AccountBox accountBox;
	
	
	public static void init(int _iMenuState){
		Log.i("Info", "Init State: "+ _iMenuState);
		switch (_iMenuState) {
        case Define.ST_MENU_LOGO:
        	
        	MenuElement.bgAlpha = (int)(GameParams.BG_BLACK_ALPHA*0.5);
			
    		iStateLogo = ST_LOGO_1;
			alpha = 0;
			logoAlpha = 255;
			startTime = System.currentTimeMillis();
			Font.init(GfxManager.vImgFontSmall, GfxManager.vImgFontMedium, GfxManager.vImgFontBig);
			RscManager.loadLanguage(Main.iLanguage);
			
			btnBack = new Button(GfxManager.imgButtonArrowBackRelease, GfxManager.imgButtonArrowBackFocus,
					Define.SIZEX32+GfxManager.imgButtonArrowBackRelease.getWidth()/2,
					Define.SIZEY32+GfxManager.imgButtonArrowBackRelease.getHeight()/2,
					null, -1){
				@Override
				public void onButtonPressUp() {
					switch(Main.state){
					case Define.ST_MENU_SELECT_GAME:
					case Define.ST_MENU_CAMPAING:
					case Define.ST_MENU_ON_LINE_START:
						Main.changeState(Define.ST_MENU_MAIN, false);
						break;
					case Define.ST_MENU_SELECT_MAP:
						selectMapBox.cancel();
						break;
					case Define.ST_MENU_CONFIG_MAP:
						configMapBox.cancel();
						break;
					case Define.ST_MENU_ON_LINE_NEW_ACCOUNT:
						accountBox.cancel();
						break;
					}
				};
			};
			
			btnNext = new Button(GfxManager.imgButtonArrowNextRelease, GfxManager.imgButtonArrowNextFocus,
					Define.SIZEX-Define.SIZEX32-GfxManager.imgButtonArrowBackRelease.getWidth()/2,
					Define.SIZEY-Define.SIZEY32-GfxManager.imgButtonArrowBackRelease.getHeight()/2,
					null, -1){
				@Override
				public void onButtonPressUp() {
					switch(Main.state){
					case Define.ST_MENU_CONFIG_MAP:
						configMapBox.setIndexPressed(0);
						configMapBox.cancel();
						break;
					}
				};
			};
			
			NotificationBox.getInstance().init(
					Define.SIZEX, Define.SIZEY, 
					null, NotificationBox.DURATION_LONG);

			break;
		case Define.ST_MENU_ASK_SOUND:
		case Define.ST_MENU_ASK_LANGUAGE:
			
			break;
		case Define.ST_MENU_MAIN:
			
			if(Main.lastState < Define.ST_MENU_MAIN){
				alpha = 255;
				startTime = System.currentTimeMillis();
				
				cloudFarBGX = Define.SIZEX2;
				cloudNearBGX = Define.SIZEX;
				cloudNear2BGX = Define.SIZEX+Define.SIZEX2;
				
				cloudFarBGY = Define.SIZEY2;
				cloudNearBGY = Define.SIZEY2+Define.SIZEY4;
				cloudNear2BGY = Define.SIZEY;
				
				btnCampaign = new Button(
						GfxManager.imgButtonMenuBigRelease, 
						GfxManager.imgButtonMenuBigFocus, 
						Define.SIZEX-(int)(GfxManager.imgButtonMenuBigRelease.getWidth()/2)-Define.SIZEY64, 
						Define.SIZEY-(int)(GfxManager.imgButtonMenuBigRelease.getHeight()*1.5)-Define.SIZEY64,
						RscManager.allText[RscManager.TXT_CAMPAING], Font.FONT_MEDIUM){
					@Override
					public void onButtonPressDown(){}
					
					@Override
					public void onButtonPressUp(){
						Main.changeState(Define.ST_MENU_CAMPAING, false);
						reset();
					}
				};
				btnMultiPlayer = new Button(
						GfxManager.imgButtonMenuBigRelease, 
						GfxManager.imgButtonMenuBigFocus, 
						Define.SIZEX-(int)(GfxManager.imgButtonMenuBigRelease.getWidth()/2)-Define.SIZEY64, 
						Define.SIZEY-(int)(GfxManager.imgButtonMenuBigRelease.getHeight()/2)-Define.SIZEY64,
						RscManager.allText[RscManager.TXT_MULTI_PLAYER], Font.FONT_MEDIUM){
					@Override
					public void onButtonPressDown(){}
					
					@Override
					public void onButtonPressUp(){
						Main.changeState(Define.ST_MENU_SELECT_GAME, true);
						reset();
					}
				};
				
				btnCampaign.setDisabled(true);
			}
			break;
		case Define. ST_MENU_OPTIONS:
			break;
		case Define. ST_MENU_MORE:
		case Define. ST_MENU_EXIT:
		case Define. ST_MENU_HELP:
		case Define. ST_MENU_ABOUT:
			break;
		
		case Define. ST_MENU_CAMPAING:
			
			btnContinue = new Button(
					GfxManager.imgButtonMenuBigRelease, 
					GfxManager.imgButtonMenuBigFocus, 
					Define.SIZEX-(int)(GfxManager.imgButtonMenuBigRelease.getWidth()/2)-Define.SIZEY64, 
					Define.SIZEY-(int)(GfxManager.imgButtonMenuBigRelease.getHeight()*1.5)-Define.SIZEY64,
					RscManager.allText[RscManager.TXT_CONTINUE], Font.FONT_MEDIUM){
				@Override
				public void onButtonPressDown(){}
				
				@Override
				public void onButtonPressUp(){
					
					SceneData dataPackage = null;
					/*
					//Lectura local
					try{
						FileInputStream fis = Main.context.openFileInput("Test");
						ObjectInputStream is = new ObjectInputStream(fis);
						dataPackage = (DataPackage) is.readObject();
						is.close();
						fis.close();
					}catch(Exception e){
						e.printStackTrace();
					}
					*/
					
					//Lectura onLine
					HttpURLConnection connection = null;
					try {

						// open URL connection
						//URL url = new URL("http://192.168.1.110:8080/KingServer/mapTestServlet2");//local
						URL url = new URL("http://172.104.228.65:8080/KingServer/mapTestServlet2");//online
						connection = (HttpURLConnection) url.openConnection();
						connection.setRequestProperty("Content-Type",
								"application/octet-stream");
						connection.setRequestMethod("GET");
						connection.setDoInput(true);
						connection.setDoOutput(false);
						connection.setUseCaches(false);

						ObjectInputStream objIn = new ObjectInputStream(
								connection.getInputStream());
						dataPackage = (SceneData) objIn.readObject();
						objIn.close();

					} catch (Exception e) {
						e.printStackTrace();
					}
					
					
					
					GameState.getInstance().init(dataPackage);
					
					Main.changeState(Define.ST_GAME_CONTINUE, true);
					reset();
				}
			};
			btnStart = new Button(
					GfxManager.imgButtonMenuBigRelease, 
					GfxManager.imgButtonMenuBigFocus, 
					Define.SIZEX-(int)(GfxManager.imgButtonMenuBigRelease.getWidth()/2)-Define.SIZEY64, 
					Define.SIZEY-(int)(GfxManager.imgButtonMenuBigRelease.getHeight()/2)-Define.SIZEY64,
					RscManager.allText[RscManager.TXT_START], Font.FONT_MEDIUM){
				@Override
				public void onButtonPressDown(){}
				
				@Override
				public void onButtonPressUp(){
					
					reset();
				}
			};
			break;
			
		case Define. ST_MENU_SELECT_GAME:
			btnOnLine = new Button(
					GfxManager.imgButtonMenuBigRelease, 
					GfxManager.imgButtonMenuBigFocus, 
					Define.SIZEX-(int)(GfxManager.imgButtonMenuBigRelease.getWidth()/2)-Define.SIZEY64, 
					Define.SIZEY-(int)(GfxManager.imgButtonMenuBigRelease.getHeight()*1.5)-Define.SIZEY64,
					RscManager.allText[RscManager.TXT_ON_LINE], Font.FONT_MEDIUM){
				@Override
				public void onButtonPressDown(){}
				
				@Override
				public void onButtonPressUp(){
					Main.changeState(Define.ST_MENU_ON_LINE_START, false);
					reset();
				}
			};
			btnPassAndPlay = new Button(
					GfxManager.imgButtonMenuBigRelease, 
					GfxManager.imgButtonMenuBigFocus, 
					Define.SIZEX-(int)(GfxManager.imgButtonMenuBigRelease.getWidth()/2)-Define.SIZEY64, 
					Define.SIZEY-(int)(GfxManager.imgButtonMenuBigRelease.getHeight()/2)-Define.SIZEY64,
					RscManager.allText[RscManager.TXT_PASS_AND_PLAY], Font.FONT_MEDIUM){
				@Override
				public void onButtonPressDown(){}
				
				@Override
				public void onButtonPressUp(){
					Main.changeState(Define.ST_MENU_SELECT_MAP, false);
					reset();
				}
			};
			break;
		case Define.ST_MENU_SELECT_MAP:
			selectMapBox = new ListBox(
					Define.SIZEX, Define.SIZEY, 
					null, GfxManager.imgNotificationBox, GfxManager.imgNotificationBox, 
					Define.SIZEX2, Define.SIZEY2, 
					RscManager.allText[RscManager.TXT_SELECT_MAP],
					new String[]{
							"OCCITANE     (2-Player) Small", 
							"SIX KINGDOMS (6-Player) Big",
							"MAP 3        (3-Player) Med",
							"MAP 4        (3-Player) Med",
							"MAP 5        (3-Player) Med",
							"MAP 6        (3-Player) Med",
							"MAP 7        (3-Player) Med",
							"MAP 8        (3-Player) Med",
							"MAP 9        (3-Player) Med",
							}, 
					Font.FONT_BIG, Font.FONT_SMALL){
				
				@Override
				public void onFinish(){
					
					if(getIndexPressed() != -1){
						GameState.getInstance().init(
								getIndexPressed(), DataKingdom.INIT_MAP_DATA[getIndexPressed()].length);
						
						int i = 0;
						for(PlayerConf pc : GameState.getInstance().getPlayerConfList()){
							pc.name="Player " + (i+1);
							pc.flag=i;
							pc.IA = false;
							i++;
						}
						Main.changeState(Define.ST_MENU_CONFIG_MAP, false);
					}else{
						Main.changeState(Define.ST_MENU_SELECT_GAME, false);
					}
				}
			};
			selectMapBox.start();
			break;
		case Define.ST_MENU_CONFIG_MAP:
			configMapBox = new ConfigMapBox(GameState.getInstance().getPlayerConfList()){
				@Override
				public void onFinish(){
					if(getIndexPressed() != -1){
						Main.changeState(Define.ST_GAME_INIT, true);
					}else{
						Main.changeState(Define.ST_MENU_SELECT_MAP, false);
					}
				}
			};
			configMapBox.start();
			break;
			
			
			
		case Define.ST_MENU_ON_LINE_START:
			String data = 
				FileIO.getInstance().loadData(Define.DATA_USER, 
						Settings.getInstance().getActiviy().getApplicationContext());

			if(false){//if (data != null && data.length() > 0) {
				Log.i("Debug", "Datos cargado: " + data);
				String[] d = data.split("\n");
				GameState.getInstance().setName(d[0]);
				GameState.getInstance().setPassword(d[1]);
				Main.changeState(Define.ST_MENU_ON_LINE_LIST, false);
				
			} 
			else {
				Log.i("Debug", "No existen datos guardados");
				/*
				String d = "Test1@123pepe";
				FileIO.getInstance().saveData(d, Define.DATA_USER, 
						Settings.getInstance().getActiviy().getApplicationContext());
				*/
				btnNewAccount = new Button(
						GfxManager.imgButtonMenuBigRelease, 
						GfxManager.imgButtonMenuBigFocus, 
						Define.SIZEX-(int)(GfxManager.imgButtonMenuBigRelease.getWidth()/2)-Define.SIZEY64, 
						Define.SIZEY-(int)(GfxManager.imgButtonMenuBigRelease.getHeight()*1.5)-Define.SIZEY64,
						RscManager.allText[RscManager.TXT_NEW_ACOUNT], Font.FONT_MEDIUM){
					@Override
					public void onButtonPressDown(){}
					
					@Override
					public void onButtonPressUp(){
						Main.changeState(Define.ST_MENU_ON_LINE_NEW_ACCOUNT, false);
						reset();
					}
				};
				btnLogin = new Button(
						GfxManager.imgButtonMenuBigRelease, 
						GfxManager.imgButtonMenuBigFocus, 
						Define.SIZEX-(int)(GfxManager.imgButtonMenuBigRelease.getWidth()/2)-Define.SIZEY64, 
						Define.SIZEY-(int)(GfxManager.imgButtonMenuBigRelease.getHeight()/2)-Define.SIZEY64,
						RscManager.allText[RscManager.TXT_LOGIN], Font.FONT_MEDIUM){
					@Override
					public void onButtonPressDown(){}
					
					@Override
					public void onButtonPressUp(){
						Main.changeState(Define.ST_MENU_ON_LINE_LOGIN, false);
						reset();
					}
				};
				
				Main.changeState(Define.ST_MENU_ON_LINE_LIST, false);
			}
			
			break;
		 case Define.ST_MENU_ON_LINE_NEW_ACCOUNT:
			 accountBox = new AccountBox(){
				 @Override
				 public void onSendForm() {
					 super.onSendForm();
					 
					 String p1 = getTextPassword();
					 String p2 = getTextRepPassword();
					 if(!getTextPassword().equals(getTextRepPassword())){
						 NotificationBox.getInstance().addMessage(RscManager.allText[RscManager.TXT_PASS_NO_MATCH]);
						 
					 }else{
						 
						 String result = "";
						 String msg = "";
						 //Escrutura online
						 UserData userData = new UserData();
						 userData.setName(getTextName());
						 userData.setPassword(getTextPassword());
						 HttpURLConnection connection = null;
							try {
								URL url = new URL(Define.SERVER_URL + "createUserServlet");//online
								connection = (HttpURLConnection) url.openConnection();
								connection.setRequestProperty("Content-Type", "application/octet-stream");
								connection.setRequestMethod("POST");
								connection.setDoInput(true);
								connection.setDoOutput(true);
								connection.setUseCaches(false);

								// send object
								ObjectOutputStream objOut = new ObjectOutputStream(
										connection.getOutputStream());
								objOut.writeObject(userData);
								objOut.flush();
								objOut.close();

								BufferedReader in = new BufferedReader(new InputStreamReader(
										connection.getInputStream()));

								String str = "";
								while ((str = in.readLine()) != null) {
									result += str + "\n";
								}
								in.close();

								System.out.println(result);
								connection.disconnect();

							} catch (Exception ex) {
								ex.printStackTrace();
								result = "Connection error";
							}	
						 
						if(result.equals("Connection error")){
							msg = RscManager.allText[RscManager.TXT_CONNECTION_ERROR];
						}
						else if(result.equals("Server error")){
							msg = RscManager.allText[RscManager.TXT_SERVER_ERROR];
						}
						else if(result.equals("User error")){
							msg = RscManager.allText[RscManager.TXT_INCORRECT_USER_NAME];
						}
						else if(result.equals("Succes")){
							msg = RscManager.allText[RscManager.TXT_ACCOUNT_CREATED];
							 //Guardo los datos en la memoria local
							
							//Cambio de estado
						}
						
						NotificationBox.getInstance().addMessage(msg);
					 }
					 
				 }
				 
				 @Override
				public void onFinish(){
					 if(accountBox.isSucces()){
						 Main.changeState(Define.ST_MENU_ON_LINE_LIST, false);
					 }else{
						 Main.changeState(Define.ST_MENU_ON_LINE_START, false);
					 }
				}
			 };
			 accountBox.start();
			 break;
		 case Define.ST_MENU_ON_LINE_LOGIN:
			 break;
		 case Define.ST_MENU_ON_LINE_LIST:
			 break;
		 case Define.ST_MENU_ON_LINE_HOST:
			 break;
		
		case Define.ST_TEST:
			break;
		}
	}
	
	public static void update(){
		
		switch (Main.state) {
		case Define.ST_MENU_LOGO:
			runLogo();
			break;
		case Define.ST_MENU_ASK_LANGUAGE:
			break;
		
		case Define.ST_MENU_ASK_SOUND:
			break;
		
		case Define.ST_MENU_MAIN:
			runMenuBG(Main.getDeltaSec());
			btnCampaign.update(UserInput.getInstance().getMultiTouchHandler());
			btnMultiPlayer.update(UserInput.getInstance().getMultiTouchHandler());
			break;
			
		case Define.ST_MENU_MORE:
			break;
			
		case Define.ST_MENU_OPTIONS:
			break;
		case Define.ST_MENU_HELP:
		case Define.ST_MENU_ABOUT:
			break;
			
		case Define.ST_MENU_EXIT:
        	break;
		case Define.ST_MENU_CAMPAING:
	        	runMenuBG(Main.getDeltaSec());
	        	btnBack.update(UserInput.getInstance().getMultiTouchHandler());
				btnContinue.update(UserInput.getInstance().getMultiTouchHandler());
				btnStart.update(UserInput.getInstance().getMultiTouchHandler());
				break;
        case Define.ST_MENU_SELECT_GAME:
        	runMenuBG(Main.getDeltaSec());
        	btnBack.update(UserInput.getInstance().getMultiTouchHandler());
			btnOnLine.update(UserInput.getInstance().getMultiTouchHandler());
			btnPassAndPlay.update(UserInput.getInstance().getMultiTouchHandler());
			break;
        case Define.ST_MENU_SELECT_MAP:
        	runMenuBG(Main.getDeltaSec());
        	btnBack.update(UserInput.getInstance().getMultiTouchHandler());
        	selectMapBox.update(UserInput.getInstance().getMultiTouchHandler(), Main.getDeltaSec());
			break;
        case Define.ST_MENU_CONFIG_MAP:
			runMenuBG(Main.getDeltaSec());
			btnBack.update(UserInput.getInstance().getMultiTouchHandler());
			btnNext.update(UserInput.getInstance().getMultiTouchHandler());
			configMapBox.update(UserInput.getInstance().getMultiTouchHandler(), Main.getDeltaSec());
			break;
			
        case Define.ST_MENU_ON_LINE_START:
        	 runMenuBG(Main.getDeltaSec());
        	 btnBack.update(UserInput.getInstance().getMultiTouchHandler());
        	 btnNewAccount.update(UserInput.getInstance().getMultiTouchHandler());
        	 btnLogin.update(UserInput.getInstance().getMultiTouchHandler());
			 break;
		 case Define.ST_MENU_ON_LINE_NEW_ACCOUNT:
			 runMenuBG(Main.getDeltaSec());
			 btnBack.update(UserInput.getInstance().getMultiTouchHandler());
			 accountBox.update(UserInput.getInstance().getMultiTouchHandler(), Main.getDeltaSec());
		 case Define.ST_MENU_ON_LINE_LOGIN:
			 runMenuBG(Main.getDeltaSec());
			 btnBack.update(UserInput.getInstance().getMultiTouchHandler());
			 break;
		 case Define.ST_MENU_ON_LINE_LIST:
			 runMenuBG(Main.getDeltaSec());
			 btnBack.update(UserInput.getInstance().getMultiTouchHandler());
			 break;
		 case Define.ST_MENU_ON_LINE_HOST:
			 runMenuBG(Main.getDeltaSec());
			 btnBack.update(UserInput.getInstance().getMultiTouchHandler());
			 break;
			
        case Define.ST_TEST:
			break;
		}
		
		NotificationBox.getInstance().update(Main.getDeltaSec());
	}
	
	public static void draw(Graphics _g){
		
		switch (Main.state) {
		case Define.ST_MENU_LOGO:
			_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			_g.setColor(Main.COLOR_BLACK);
			_g.fillRect(0, 0, Define.SIZEX, Define.SIZEY);
			_g.setAlpha(alpha);
			_g.drawImage(GfxManager.vImgLogo, Define.SIZEX2, Define.SIZEY2, Graphics.VCENTER|Graphics.HCENTER);
			_g.setAlpha(255);
             
			break;
		case Define.ST_MENU_ASK_LANGUAGE:
			_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			break;
			
		case Define.ST_MENU_ASK_SOUND:
			_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			break;
		
		case Define.ST_MENU_MAIN:
			drawMenuBG(_g);
			btnCampaign.draw(_g, 0, 0);
			btnMultiPlayer.draw(_g, 0, 0);
			if(alpha > 0){
				_g.setAlpha(alpha);
				_g.drawImage(GfxManager.imgBlackBG, 0, 0, Graphics.TOP | Graphics.LEFT);
				_g.setAlpha(255);
			}
			break;
			
		case Define.ST_MENU_OPTIONS:
			_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			break;
			
		case Define.ST_MENU_MORE:
			_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			break;
			
		case Define.ST_MENU_HELP:
		case Define.ST_MENU_ABOUT:
			_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			break;
			
		case Define. ST_MENU_CAMPAING:
			drawMenuBG(_g);
			btnBack.draw(_g, 0, 0);
			btnContinue.draw(_g, 0, 0);
			btnStart.draw(_g, 0, 0);
			break;
			
		case Define.ST_MENU_SELECT_GAME:
			drawMenuBG(_g);
			btnBack.draw(_g, 0, 0);
			btnOnLine.draw(_g, 0, 0);
			btnPassAndPlay.draw(_g, 0, 0);
			break;
		case Define.ST_MENU_SELECT_MAP:
			drawMenuBG(_g);
			btnBack.draw(_g, 0, 0);
			selectMapBox.draw(_g, GfxManager.imgBlackBG);
			break;
		
		case Define.ST_MENU_CONFIG_MAP:
			drawMenuBG(_g);
			btnBack.draw(_g, 0, 0);
			btnNext.draw(_g, 0, 0);
			configMapBox.draw(_g);
			break;
			
		 case Define.ST_MENU_ON_LINE_START:
			 drawMenuBG(_g);
			 btnBack.draw(_g, 0, 0);
			 btnNewAccount.draw(_g, 0, 0);
			 btnLogin.draw(_g, 0, 0);
			 break;
		 case Define.ST_MENU_ON_LINE_NEW_ACCOUNT:
			 drawMenuBG(_g);
			 btnBack.draw(_g, 0, 0);
			 accountBox.draw(_g);
			 break;
		 case Define.ST_MENU_ON_LINE_LOGIN:
			 drawMenuBG(_g);
			 btnBack.draw(_g, 0, 0);
			 break;
		 case Define.ST_MENU_ON_LINE_LIST:
			 drawMenuBG(_g);
			 btnBack.draw(_g, 0, 0);
			 break;
		 case Define.ST_MENU_ON_LINE_HOST:
			 drawMenuBG(_g);
			 btnBack.draw(_g, 0, 0);
			 break;
			
		case Define.ST_MENU_EXIT:
			_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			break;
			
		case Define.ST_TEST:
			
			_g.setColor(0x000000);
			_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			_g.fillRect(0, 0, Define.SIZEX, Define.SIZEY);
			
			int distW = GfxManager.imgGreenBox.getWidth()/4;
			int distH = GfxManager.imgGreenBox.getHeight()/4;
			int totalW = GfxManager.imgGreenBox.getWidth() + distH*2;
			int totalH = GfxManager.imgGreenBox.getHeight() + distH;
			
			_g.drawDistorisionImage(
					 GfxManager.imgGreenBox, 
					 
					 Define.SIZEX2- GfxManager.imgGreenBox.getWidth()/2, 10, 	//Point corner top-left 
					 Define.SIZEX2+ GfxManager.imgGreenBox.getWidth()/2, 10,  //Point corner top-right
					 Define.SIZEX2 - totalW/2, 10 + totalH, //Point corner button-left
					 Define.SIZEX2 + totalW/2, 10 + totalH	//Point corner button-right
					 );
			
			_g.drawImage(GfxManager.imgRedBox, Define.SIZEX2, 10, Graphics.TOP | Graphics.HCENTER);
			break;
		}
		
		NotificationBox.getInstance().draw(_g);
	}
	
	public static void drawBackground(Graphics _g, Image _vGB) {
        if (_vGB != null) {
            _g.drawImage(_vGB, 0, 0, 0);
        } else {
            gradientBackground(_g, Main.COLOR_BLUE_BG, Main.COLOR_BLACK, 0, 0, Define.SIZEX, Define.SIZEY, 32);
        }
    }
	
	public static int[] iColor = new int[3];
    public static boolean isVibrate;

    public static void gradientBackground(Graphics g, int color1, int color2, int x, int y, int width, int height, int steps) {

        int stepSize = height / steps;

        int color1RGB[] = new int[]{(color1 >> 16) & 0xff, (color1 >> 8) & 0xff, color1 & 0xff};
        int color2RGB[] = new int[]{(color2 >> 16) & 0xff, (color2 >> 8) & 0xff, color2 & 0xff};

        int colorCalc[] = new int[]{
            ((color2RGB[0] - color1RGB[0]) << 16) / steps,
            ((color2RGB[1] - color1RGB[1]) << 16) / steps,
            ((color2RGB[2] - color1RGB[2]) << 16) / steps
        };

        for (int i = 0; i < steps; i++) {
            g.setColor(color1RGB[0] + ((i * colorCalc[0] >> 16)) << 16
                    | color1RGB[1] + ((i * colorCalc[1] >> 16)) << 8
                    | color1RGB[2] + ((i * colorCalc[2] >> 16)));
            if (i != steps - 1) {
                g.fillRect(x, y + i * stepSize, width, stepSize);
            } else {
                g.fillRect(x, y + i * stepSize, width, stepSize + 30); //+20 corrects presicion los due to divisions
            }
        }
    }
	
	public static long startTime;
	public static final long ST_TIME_LOGO_1 = 1200;
	public static final long ST_TIME_LOGO_2 = 800;
	public static final long ST_TIME_LOGO_3 = 1200;
	
	public static final long ST_TIME_MAIN = 1200;
	
	public static int iStateLogo;
	public static final int ST_LOGO_1 = 0;
	public static final int ST_LOGO_2 = 2;
	public static final int ST_LOGO_3 = 3;
	public static int alpha;
	
	public static void runLogo(){
		switch(iStateLogo){
		case ST_LOGO_1:
			alpha = (int)(((System.currentTimeMillis() - startTime)*255)/ST_TIME_LOGO_1);
			if(alpha >= 255){
				alpha = 255;
				iStateLogo = ST_LOGO_2;
				startTime = System.currentTimeMillis();
			}
			break;
		case ST_LOGO_2:
			if(System.currentTimeMillis()>startTime+ST_TIME_LOGO_2){
				iStateLogo = ST_LOGO_3;
				startTime = System.currentTimeMillis();
				
			}
			break;
		case ST_LOGO_3:
			alpha = 255- (int)(((System.currentTimeMillis() - startTime)*255)/ST_TIME_LOGO_3);
			if(alpha <= 0){
				alpha = 0;
				Main.changeState(Define.ST_MENU_MAIN, true);
			}
			
			break;
		}
		//Log.i("Info", "iLevelAlpha: "+iLevelAlpha);
	}
	
	public static float cloudFarBGX;
	public static float cloudFarBGY;
	public static float cloudNearBGX;
	public static float cloudNearBGY;
	public static float cloudNear2BGX;
	public static float cloudNear2BGY;
	public static int logoAlpha;
	public static void runMenuBG(float delta){
		
		alpha = 255-((int)(((System.currentTimeMillis() - startTime)*255)/ST_TIME_MAIN));
		if(alpha <= 0){
			alpha = 0;
		}
		
		
		if(cloudFarBGX < -GfxManager.imgCloudBG.getWidth()/2){
			cloudFarBGX = Define.SIZEX + (GfxManager.imgCloudBG.getWidth()/2);
		}
		if(cloudNearBGX < -(GfxManager.imgCloudBG.getWidth()*1.2f)/2){
			cloudNearBGX = Define.SIZEX + ((GfxManager.imgCloudBG.getWidth()*1.2f)/2);
		}
		if(cloudNear2BGX < -(GfxManager.imgCloudBG.getWidth()*1.4f)/2){
			cloudNear2BGX = Define.SIZEX + ((GfxManager.imgCloudBG.getWidth()*1.4f)/2);
		}
		cloudFarBGX-=10f*delta;
		cloudNearBGX-=20f*delta;
		cloudNear2BGX-=40f*delta;
		
		if(
				Main.state == Define.ST_MENU_MAIN || 
				Main.state == Define.ST_MENU_SELECT_GAME || 
				Main.state == Define.ST_MENU_ON_LINE_START || 
				Main.state == Define.ST_MENU_ON_LINE_LOGIN
				
		){
			logoAlpha +=delta*255;
			logoAlpha = Math.min(255, logoAlpha);
		}else{
			logoAlpha -=delta*(255);
			logoAlpha = Math.max(80, logoAlpha);
		}
	}
	
	private static void drawMenuBG(Graphics g){
		g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
		g.drawImage(GfxManager.imgMainBG, 0, 0, Graphics.TOP | Graphics.LEFT);
		
		g.setAlpha(logoAlpha);
		g.drawImage(GfxManager.imgTitle, 
				Define.SIZEX-(int)(GfxManager.imgButtonMenuBigRelease.getWidth()/2)-Define.SIZEY64, 
				GfxManager.imgTitle.getHeight()/2 + Define.SIZEY32, 
				Graphics.VCENTER | Graphics.HCENTER);
		
		g.setAlpha(255);;
		
		g.drawImage(GfxManager.imgCloudBG, (int)cloudFarBGX, (int)cloudFarBGY, Graphics.VCENTER | Graphics.HCENTER);
		g.setImageSize(1.2f, 1.2f);
		g.drawImage(GfxManager.imgCloudBG, (int)cloudNearBGX, (int)cloudNearBGY, Graphics.VCENTER | Graphics.HCENTER);
		g.setImageSize(1f, 1f);
		g.drawImage(GfxManager.imgSwordBG, 0, Define.SIZEY, Graphics.BOTTOM | Graphics.LEFT);
		g.setImageSize(1.4f, 1.4f);
		g.drawImage(GfxManager.imgCloudBG, (int)cloudNear2BGX, (int)cloudNear2BGY, Graphics.VCENTER | Graphics.HCENTER);
		g.setImageSize(1f, 1f);
	}
}
