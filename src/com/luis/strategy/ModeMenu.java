package com.luis.strategy;

import android.util.Log;

import com.luis.lgameengine.gameutils.Settings;
import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.gui.ListBox;
import com.luis.lgameengine.gui.MenuElement;
import com.luis.lgameengine.gui.NotificationBox;
import com.luis.lgameengine.implementation.fileio.FileIO;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.lgameengine.implementation.sound.SndManager;
import com.luis.strategy.GameState.PlayerConf;
import com.luis.strategy.connection.OnlineInputOutput;
import com.luis.strategy.constants.Define;
import com.luis.strategy.constants.GameParams;
import com.luis.strategy.data.DataKingdom;
import com.luis.strategy.datapackage.scene.NotificationListData;
import com.luis.strategy.datapackage.scene.PreSceneData;
import com.luis.strategy.datapackage.scene.PreSceneListData;
import com.luis.strategy.datapackage.scene.SceneData;
import com.luis.strategy.datapackage.scene.SceneListData;
import com.luis.strategy.gui.ConfigMapBox;
import com.luis.strategy.gui.CreateUserBox;
import com.luis.strategy.gui.LoginBox;
import com.luis.strategy.gui.SceneDataListBox;

public class ModeMenu {
	
	public static final int NUMBER_OPTS_LANGUAGE = 3;
	public static final int NUMBER_OPTS_SOUND = 2;
	public static final int NUMBER_OPTS_MAIN_MENU = 4;
	public static final int NUMBER_OPTS_MORE_MENU = 2;
	
	public static int cita;
	public static int author;
	
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
	private static Button btnContinueCampaing;
	private static Button btnContinuePassAndPlay;
	private static Button btnStart;
	
	private static Button btnNewAccount;
	private static Button btnLogin;
	private static Button btnSearchGame;
	private static Button btnCreateScene;
	private static Button btnCancel;
	
	private static ListBox selectMapBox;
	private static ListBox selectPreSceneBox;
	private static ListBox selectSceneBox;
	private static ListBox notificationBox;
	private static ConfigMapBox configMapBox;
	private static CreateUserBox createUserBox;
	private static LoginBox loginBox;
	
	private static boolean createUsserSucces;
	private static boolean loginUsserSucces;
	
	public static void init(int _iMenuState){
		Log.i("Info", "Init State: "+ _iMenuState);
		switch (_iMenuState) {
		
		case Define.ST_MENU_START:
			Font.init(GfxManager.vImgFontSmall, GfxManager.vImgFontMedium, GfxManager.vImgFontBig);
			RscManager.loadLanguage(Main.iLanguage);
			MenuElement.bgAlpha = (int)(GameParams.BG_BLACK_ALPHA*0.5);
			
			alpha = 255;
        	startTime = System.currentTimeMillis();
        	cita = Main.getRandom(RscManager.TXT_CITA_1, RscManager.TXT_CITA_4);
			author = RscManager.TXT_CITA_4+cita;
			
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
					case Define.ST_MENU_ON_LINE_CREATE_SCENE:
						selectMapBox.cancel();
						break;
					case Define.ST_MENU_ON_LINE_LIST_ALL_GAME:
						selectSceneBox.cancel();
						break;
					case Define.ST_MENU_ON_LINE_LIST_JOIN_GAME:
						selectPreSceneBox.cancel();
						break;
					case Define.ST_MENU_CONFIG_MAP:
						configMapBox.cancel();
						break;
					case Define.ST_MENU_ON_LINE_CREATE_USER:
						createUserBox.cancel();
						break;
					case Define.ST_MENU_ON_LINE_LOGIN:
						loginBox.cancel();
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
        case Define.ST_MENU_LOGO:
        	
			
        	startTime = System.currentTimeMillis();
    		statePresentation = ST_PRESENTATION_1;
			alpha = 255;
			logoAlpha = 255;
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
						reset();
						Main.changeState(Define.ST_MENU_SELECT_GAME, false);
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
			
			btnContinueCampaing = new Button(
					GfxManager.imgButtonMenuBigRelease, 
					GfxManager.imgButtonMenuBigFocus, 
					Define.SIZEX-(int)(GfxManager.imgButtonMenuBigRelease.getWidth()/2)-Define.SIZEY64, 
					Define.SIZEY-(int)(GfxManager.imgButtonMenuBigRelease.getHeight()*1.5)-Define.SIZEY64,
					RscManager.allText[RscManager.TXT_CONTINUE], Font.FONT_MEDIUM){
				@Override
				public void onButtonPressDown(){}
				
				@Override
				public void onButtonPressUp(){
					
					//SceneData dataPackage = null;
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
					
					//GameState.getInstance().init(GameState.GAME_MODE_CAMPAING, dataPackage);
					//Main.changeState(Define.ST_GAME_CONTINUE, true);
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
			
		case Define. ST_MENU_SELECT_GAME://porque carga
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
					reset();
					String data = FileIO.getInstance().loadData(Define.DATA_USER, 
							Settings.getInstance().getActiviy().getApplicationContext());

					if (data != null && data.length() > 0) {
						String[] d = data.split("\n");
						GameState.getInstance().setName(d[0]);
						GameState.getInstance().setPassword(d[1]);
						NotificationBox.getInstance().addMessage(RscManager.allText[RscManager.TXT_CONNECTED_BY] + " " + d[0]);
						Main.changeState(Define.ST_MENU_ON_LINE_LIST_ALL_GAME, false);
					} else{
						Main.changeState(Define.ST_MENU_ON_LINE_START, false);
					}
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
					DataKingdom.SCENARY_LIST,
					Font.FONT_BIG, Font.FONT_SMALL){
				
				@Override
				public void onFinish(){
					
					if(getIndexPressed() != -1){
						GameState.getInstance().init(
								GameState.GAME_MODE_PLAY_AND_PASS,
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
						Main.changeState(Define.ST_GAME_INIT_PASS_AND_PLAY, true);
					}else{
						Main.changeState(Define.ST_MENU_SELECT_MAP, false);
					}
				}
			};
			configMapBox.start();
			break;
			
			
			
		case Define.ST_MENU_ON_LINE_START:
			
			btnNewAccount = new Button(GfxManager.imgButtonMenuBigRelease,
					GfxManager.imgButtonMenuBigFocus,
					Define.SIZEX - (int) (GfxManager.imgButtonMenuBigRelease.getWidth() / 2) - Define.SIZEY64,
					Define.SIZEY - (int) (GfxManager.imgButtonMenuBigRelease.getHeight() * 1.5) - Define.SIZEY64,
					RscManager.allText[RscManager.TXT_NEW_ACOUNT], Font.FONT_MEDIUM) {
				@Override
				public void onButtonPressDown() {
				}

				@Override
				public void onButtonPressUp() {
					Main.changeState(Define.ST_MENU_ON_LINE_CREATE_USER, false);
					reset();
				}
			};
			btnLogin = new Button(GfxManager.imgButtonMenuBigRelease,
					GfxManager.imgButtonMenuBigFocus,
					Define.SIZEX - (int) (GfxManager.imgButtonMenuBigRelease.getWidth() / 2) - Define.SIZEY64,
					Define.SIZEY - (int) (GfxManager.imgButtonMenuBigRelease.getHeight() / 2) - Define.SIZEY64,
					RscManager.allText[RscManager.TXT_LOGIN], Font.FONT_MEDIUM) {
				@Override
				public void onButtonPressDown() {
				}

				@Override
				public void onButtonPressUp() {
					Main.changeState(Define.ST_MENU_ON_LINE_LOGIN, false);
					reset();
				}
			};

			Main.changeState(Define.ST_MENU_ON_LINE_LIST_ALL_GAME, false);
			break;
			
		 case Define.ST_MENU_ON_LINE_CREATE_USER:
			 createUsserSucces = false;
			 createUserBox = new CreateUserBox(){
				 @Override
				 public void onSendForm() {
					 super.onSendForm();
					 
					  if(!getTextPassword().equals(getTextRepPassword())){
						 NotificationBox.getInstance().addMessage(RscManager.allText[RscManager.TXT_PASS_NO_MATCH]);
					  }
					  else{
						 //Escrutura online
						 String msg = "";
						 Main.getInstance().startClock(Main.TYPE_EARTH);
						 String result = OnlineInputOutput.getInstance().sendUser("createUserServlet", getTextName(), getTextPassword());
						 Main.getInstance().stopClock();
						 
						 if(result.equals("Server error")){
							msg = RscManager.allText[RscManager.TXT_SERVER_ERROR];
						 }
						 else if(result.equals("Query error")){
							msg = RscManager.allText[RscManager.TXT_TRY_ANOTHER_NAME];
						 }
						 else if(result.equals("Succes")){
							msg = RscManager.allText[RscManager.TXT_ACCOUNT_CREATED];
							 
							GameState.getInstance().setName(getTextName());
							GameState.getInstance().setPassword(getTextPassword());
							
							//Guardo los datos en la memoria local
							String d = getTextName() + "\n" + getTextPassword();
							FileIO.getInstance().saveData(d, Define.DATA_USER, 
									Settings.getInstance().getActiviy().getApplicationContext());
							
							createUsserSucces = true;
							cancel();
						}else{
							msg = RscManager.allText[RscManager.TXT_CONNECTION_ERROR];
						}
						
						NotificationBox.getInstance().addMessage(msg);
					 }
				 }
				 
				 @Override
				 public void onFinish(){
					 if(createUsserSucces){
						 Main.changeState(Define.ST_MENU_ON_LINE_LIST_ALL_GAME, false);
						 
						 //Valido los datos guardados contra el servidor(De momento no es necesario porque si el nombre es incorrecto)
						 
					 }else{
						 Main.changeState(Define.ST_MENU_ON_LINE_START, false);
					 }
				}
			 };
			 createUserBox.start();
			 break;
		 case Define.ST_MENU_ON_LINE_LOGIN:
			 loginUsserSucces = false;
			 loginBox = new LoginBox(){
				 @Override
				 public void onSendForm() {
					 super.onSendForm();
					 String msg = "";
					 Main.getInstance().startClock(Main.TYPE_EARTH);
					 String result = OnlineInputOutput.getInstance().sendUser("loginUserServlet", getTextName(), getTextPassword());
					 Main.getInstance().stopClock();
					 
					if(result.equals("Server error")){
						 msg = RscManager.allText[RscManager.TXT_SERVER_ERROR];
					 }
					 else if(result.equals("Query error")){
						 msg = RscManager.allText[RscManager.TXT_INCORRECT_USER_NAME];
					 }
					 else if(result.equals("Succes")){
						 msg = RscManager.allText[RscManager.TXT_CONNECTED_BY] + " " + getTextName();
						
						 GameState.getInstance().setName(getTextName());
						 GameState.getInstance().setPassword(getTextPassword());
						
						 //Guardo los datos en la memoria local
						 String d = getTextName() + "\n" + getTextPassword();
						 FileIO.getInstance().saveData(d, Define.DATA_USER, 
								Settings.getInstance().getActiviy().getApplicationContext());
						
						 loginUsserSucces = true;
						 cancel();
					}else{
						msg = RscManager.allText[RscManager.TXT_CONNECTION_ERROR];
					}
					NotificationBox.getInstance().addMessage(msg);
				 }
				 
				 @Override
				 public void onFinish(){
					 if(loginUsserSucces){
						 Main.changeState(Define.ST_MENU_ON_LINE_LIST_ALL_GAME, false);
					 }else{
						 Main.changeState(Define.ST_MENU_ON_LINE_START, false);
					 }
				}
			 };
			 loginBox.start();
			 break;
		 case Define.ST_MENU_ON_LINE_LIST_ALL_GAME:
			 
			 //Notificaciones
			 Main.getInstance().startClock(Main.TYPE_EARTH);
			 NotificationListData notificationListData = 
			 	OnlineInputOutput.getInstance().reviceNotificationListData(GameState.getInstance().getName());
			 Main.getInstance().stopClock();
			 if(notificationListData.getNotificationDataList().size() > 0){
				 
				 //Si vengo del juego, no muestro notificaciones
				 if(Main.lastState < Define.ST_GAME_INIT_PASS_AND_PLAY){
					 String[] notificationList = new String[notificationListData.getNotificationDataList().size()];
					 for(int i = 0; i < notificationListData.getNotificationDataList().size(); i++){
						 notificationList[i] = notificationListData.getNotificationDataList().get(i).getMessage();
					 }
					 notificationBox = new ListBox(
								Define.SIZEX, Define.SIZEY, 
								GfxManager.imgMediumBox, 
								GfxManager.imgButtonInvisible, GfxManager.imgButtonInvisible, 
								Define.SIZEX2, Define.SIZEY2, 
								RscManager.allText[RscManager.TXT_NOTIFICATIONS],
								notificationList,
								Font.FONT_MEDIUM, Font.FONT_SMALL);
					 notificationBox.setDisabledList();
					 for(Button button : notificationBox.getBtnList()){
						 button.setIgnoreAlpha(true);
					 }
					 notificationBox.start();
					 
					 btnCancel = new Button(
							 GfxManager.imgButtonCancelRelease, 
							 GfxManager.imgButtonCancelRelease, 
							 notificationBox.getX()-notificationBox.getWidth()/2, 
							 notificationBox.getY()-notificationBox.getHeight()/2, 
							 null, -1){
						 @Override
						 public void onButtonPressUp(){
							 setDisabled(true);
							 if(Main.state == Define.ST_MENU_ON_LINE_LIST_ALL_GAME){
								 notificationBox.cancel();
							 }
						 }
					 };
				 }
				 
				 //Enviar notificaciones leidas
				 updateNotifications(notificationListData);
			 }
			 btnSearchGame = new Button(
						GfxManager.imgButtonSearchBigRelease, 
						GfxManager.imgButtonSearchBigFocus, 
						Define.SIZEX - (GfxManager.imgButtonSearchBigRelease.getWidth()/2) - Define.SIZEY64, 
						(GfxManager.imgButtonSearchBigRelease.getHeight()/2) + Define.SIZEY64,
						null, -1){
					@Override
					public void onButtonPressDown(){}
					
					@Override
					public void onButtonPressUp(){
						reset();
						Main.changeState(Define.ST_MENU_ON_LINE_LIST_JOIN_GAME, false);
					}
				};
				
				btnCreateScene = new Button(
						GfxManager.imgButtonCrossBigRelease, 
						GfxManager.imgButtonCrossBigFocus, 
						Define.SIZEX - (GfxManager.imgButtonCrossBigRelease.getWidth()/2)-Define.SIZEY64, 
						Define.SIZEY - (GfxManager.imgButtonCrossBigRelease.getHeight()/2)-Define.SIZEY64,
						null, -1){
					@Override
					public void onButtonPressDown(){}
					
					@Override
					public void onButtonPressUp(){
						reset();
						Main.changeState(Define.ST_MENU_ON_LINE_CREATE_SCENE, false);
					}
				};
				
				
			Main.getInstance().startClock(Main.TYPE_EARTH);
			SceneListData sceneListData = OnlineInputOutput.getInstance().reviceSceneListData(GameState.getInstance().getName());
			Main.getInstance().stopClock();

			if (sceneListData != null) {

				String[] textList = new String[sceneListData.getSceneDataList().size()];
				
				boolean[] disableList = new boolean[sceneListData.getSceneDataList().size()];
				for (int i = 0; i < sceneListData.getSceneDataList().size(); i++) {
					disableList[i] = 
							!sceneListData.getSceneDataList().get(i).getNextPlayer().equals(GameState.getInstance().getName());
				}
				
				for (int i = 0; i < sceneListData.getSceneDataList().size(); i++) {
					textList[i] = ""+
							sceneListData.getSceneDataList().get(i).getId() + " - " +
							DataKingdom.SCENARY_NAME_LIST[sceneListData.getSceneDataList().get(i).getMap()] +
							" - ";
					if(disableList[i]){
						textList[i] += "NEXT " + sceneListData.getSceneDataList().get(i).getNextPlayer();
					}else{
						textList[i] += "YOUR TURN";
					}
				}
                
				selectSceneBox = new SceneDataListBox(sceneListData, textList) {
					@Override
					public void onWaitFinish() {

						
						if (getIndexPressed() != -1) {
							
							SceneListData sceneListData = (SceneListData)getSceneListData();
							SceneData sd = sceneListData.getSceneDataList().get(getIndexPressed());
							
							String msg = "";
							Main.getInstance().startClock(Main.TYPE_EARTH);
							
							//El escenario es nuevo
							SceneData sceneData = null;
							if(sd.getState() == 0){
								sceneData = 
										OnlineInputOutput.getInstance().reviceSceneData(OnlineInputOutput.URL_GET_START_SCENE, ""+ sd.getId());
							}
							//El escenario NO es nuevo
							else{
								sceneData = 
										OnlineInputOutput.getInstance().reviceSceneData(OnlineInputOutput.URL_GET_SCENE, ""+ sd.getId());
							}
							
							Main.getInstance().stopClock();
							
							if(sceneData != null){
								
								GameState.getInstance().init(GameState.GAME_MODE_ONLINE, sceneData);
								msg = RscManager.allText[RscManager.TXT_GAME_LOADED];
								NotificationBox.getInstance().addMessage(msg);
								Main.changeState(Define.ST_GAME_INIT_ON_LINE, true);
							}else{
								msg = RscManager.allText[RscManager.TXT_SERVER_ERROR];
								NotificationBox.getInstance().addMessage(msg);
								Main.changeState(Define.ST_MENU_SELECT_GAME, false);
							}
							
						}else{
							Main.changeState(Define.ST_MENU_SELECT_GAME, false);
						}
					}
				};
				selectSceneBox.setDisabledList(disableList);
				selectSceneBox.start();
				
				updateScenes();
			
			} else {
				NotificationBox.getInstance().
					addMessage(RscManager.allText[RscManager.TXT_CONNECTION_ERROR]);
				Main.changeState(Define.ST_MENU_ON_LINE_START, false);
			}
			
			 break;
		 
		 case Define.ST_MENU_ON_LINE_LIST_JOIN_GAME:
			 Main.getInstance().startClock(Main.TYPE_EARTH);
			 PreSceneListData preSceneListData =  
					 OnlineInputOutput.getInstance().revicePreSceneListData(OnlineInputOutput.URL_GET_PRE_SCENE_LIST, GameState.getInstance().getName());
			 Main.getInstance().stopClock();
			
			 if (preSceneListData != null) {
				 
				 
				String[] textList = new String[preSceneListData.getPreSceneDataList().size()];
				for(int i = 0; i < preSceneListData.getPreSceneDataList().size(); i++){
					
					int numPlayer = DataKingdom.INIT_MAP_DATA[preSceneListData.getPreSceneDataList().get(i).getMap()].length;
					
					textList[i] = ""+
							preSceneListData.getPreSceneDataList().get(i).getId() + "-" +
							preSceneListData.getPreSceneDataList().get(i).getHost() + " " +
							DataKingdom.SCENARY_NAME_LIST[preSceneListData.getPreSceneDataList().get(i).getMap()] +
							" - " +
							preSceneListData.getPreSceneDataList().get(i).getPlayerCount() + 
							"/" + numPlayer;
				}
				
				selectPreSceneBox = new SceneDataListBox(preSceneListData, textList){
					@Override
					public void onWaitFinish(){
						
						if(getIndexPressed() != -1){
							PreSceneListData preSceneListData = (PreSceneListData)getSceneListData();
							PreSceneData sceneData = preSceneListData.getPreSceneDataList().get(getIndexPressed());
							
							String scene = "" + sceneData.getId();
							String user = GameState.getInstance().getName();
							
							String create = null;
							
							int insCount = (sceneData.getPlayerCount()+1);
							if(insCount ==  DataKingdom.INIT_MAP_DATA[sceneData.getMap()].length){
								create = "create";
								Log.i("Debug", "Scene " + sceneData.getId() + " ya contiene el total de jugadores");
							}
							
							String msg = "";
							Main.getInstance().startClock(Main.TYPE_EARTH);
							String result = 
									OnlineInputOutput.getInstance().sendInscription(OnlineInputOutput.URL_CREATE_INSCRIPTION, scene, user, create);
							Main.getInstance().stopClock();
							 
							if(result.equals("Server error")){
								msg = RscManager.allText[RscManager.TXT_SERVER_ERROR];
							}
							else if(result.equals("Query error")){
								msg = RscManager.allText[RscManager.TXT_INCORRECT_USER_NAME];
							}
							else if(result.equals("Succes")){
								msg = RscManager.allText[RscManager.TXT_HAVE_JOINED];
							}
							else{
								msg = RscManager.allText[RscManager.TXT_CONNECTION_ERROR];
							}
							NotificationBox.getInstance().addMessage(msg);
						}
						Main.changeState(Define.ST_MENU_ON_LINE_LIST_ALL_GAME, false);
					}
				};
				selectPreSceneBox.start();
				updatePreScenes();
				
				
			}else{
				NotificationBox.getInstance().addMessage(RscManager.allText[RscManager.TXT_CONNECTION_ERROR]);
				Main.changeState(Define.ST_MENU_ON_LINE_START, false);
			}
			
			break;
		 case Define.ST_MENU_ON_LINE_CREATE_SCENE:
			 selectMapBox = new ListBox(
						Define.SIZEX, Define.SIZEY, 
						null, GfxManager.imgNotificationBox, GfxManager.imgNotificationBox, 
						Define.SIZEX2, Define.SIZEY2, 
						RscManager.allText[RscManager.TXT_SELECT_MAP],
						DataKingdom.SCENARY_LIST,
						Font.FONT_BIG, Font.FONT_SMALL){
					
					@Override
					public void onWaitFinish(){
						
						if(getIndexPressed() != -1){
							
							String map = "" + getIndexPressed();
							String host = GameState.getInstance().getName();
							String name = "TEST";
							
							
							 String msg = null;
							 Main.getInstance().startClock(Main.TYPE_EARTH);
							 String result =  
									 OnlineInputOutput.getInstance().sendPreScene(OnlineInputOutput.URL_CREATE_PRE_SCENE, map, host, name);
							 Main.getInstance().stopClock();
							 if(result.equals("Server error")){
								msg = RscManager.allText[RscManager.TXT_SERVER_ERROR];
							 }
							 else if(result.equals("Query error")){
								msg = RscManager.allText[RscManager.TXT_SERVER_ERROR];
							 }
							 else if(result.equals("Succes")){
								msg = RscManager.allText[RscManager.TXT_GAME_CREATED];
							 }
							 else{
								 msg = RscManager.allText[RscManager.TXT_CONNECTION_ERROR];
							 }
							 if(msg != null){
								NotificationBox.getInstance().addMessage(msg);
							 }
						}
						Main.changeState(Define.ST_MENU_ON_LINE_LIST_ALL_GAME, false);
					};
				};
				selectMapBox.start();
			 break;
		 
		 
		 case Define.ST_TEST:
			break;
		}
	}
	
	public static void update(){
		
		switch (Main.state) {
		case Define.ST_MENU_START:
			if(!runPresentation(ST_TIME_CITA_1, ST_TIME_CITA_2, ST_TIME_CITA_3) || Main.debug){
				Main.changeState(Define.ST_MENU_LOGO, false);
			}
			break;
		case Define.ST_MENU_LOGO:
			if(!runPresentation(ST_TIME_LOGO_1, ST_TIME_LOGO_2, ST_TIME_LOGO_3) || Main.debug){
				Main.changeState(Define.ST_MENU_MAIN, false);
			}
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
				btnContinueCampaing.update(UserInput.getInstance().getMultiTouchHandler());
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
		 case Define.ST_MENU_ON_LINE_CREATE_USER:
			 runMenuBG(Main.getDeltaSec());
			 btnBack.update(UserInput.getInstance().getMultiTouchHandler());
			 createUserBox.update(UserInput.getInstance().getMultiTouchHandler(), Main.getDeltaSec());
			 break;
		 case Define.ST_MENU_ON_LINE_LOGIN:
			 runMenuBG(Main.getDeltaSec());
			 btnBack.update(UserInput.getInstance().getMultiTouchHandler());
			 loginBox.update(UserInput.getInstance().getMultiTouchHandler(), Main.getDeltaSec());
			 break;
		 case Define.ST_MENU_ON_LINE_LIST_ALL_GAME:
			 runMenuBG(Main.getDeltaSec());
			 btnBack.update(UserInput.getInstance().getMultiTouchHandler());
			 btnSearchGame.update(UserInput.getInstance().getMultiTouchHandler());
			 btnCreateScene.update(UserInput.getInstance().getMultiTouchHandler());
			 if(notificationBox != null){
				 notificationBox.update(UserInput.getInstance().getMultiTouchHandler(), Main.getDeltaSec());
				 btnCancel.update(UserInput.getInstance().getMultiTouchHandler());
				 if(!notificationBox.isActive()){
					 if(selectSceneBox != null)
						 selectSceneBox .update(UserInput.getInstance().getMultiTouchHandler(), Main.getDeltaSec());
				 }
			 }else{
				 if(selectSceneBox != null)
					 selectSceneBox .update(UserInput.getInstance().getMultiTouchHandler(), Main.getDeltaSec());
			 }
			 break;
		 case Define.ST_MENU_ON_LINE_LIST_JOIN_GAME:
			 runMenuBG(Main.getDeltaSec());
			 btnBack.update(UserInput.getInstance().getMultiTouchHandler());
			 if(selectPreSceneBox != null){
				 selectPreSceneBox.update(UserInput.getInstance().getMultiTouchHandler(), Main.getDeltaSec());
			 }
			 break;
		 case Define.ST_MENU_ON_LINE_CREATE_SCENE:
			 runMenuBG(Main.getDeltaSec());
			 btnBack.update(UserInput.getInstance().getMultiTouchHandler());
			 if(selectMapBox != null)
				 selectMapBox.update(UserInput.getInstance().getMultiTouchHandler(), Main.getDeltaSec());
			 break;
		
		 
		 case Define.ST_TEST:
			break;
		}
		
		NotificationBox.getInstance().update(Main.getDeltaSec());
	}
	
	public static void draw(Graphics _g){
		
		switch (Main.state) {
		case Define.ST_MENU_START:
			_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			_g.setColor(Main.COLOR_BLACK);
			_g.fillRect(0, 0, Define.SIZEX, Define.SIZEY);
			
			TextManager.draw(_g, Font.FONT_SMALL, RscManager.allText[cita], 
					Define.SIZEX2, Define.SIZEY2-Define.SIZEY8, 
					(int)(Define.SIZEX*0.75), TextManager.ALING_CENTER, -1);
			
			TextManager.draw(_g, Font.FONT_SMALL, RscManager.allText[author], 
					Define.SIZEX2, Define.SIZEY2+Define.SIZEY8, 
					Define.SIZEX, TextManager.ALING_CENTER, -1);
			
			_g.setAlpha(alpha);
			_g.drawImage(GfxManager.imgBlackBG, 0, 0, Graphics.TOP | Graphics.LEFT);
			_g.setAlpha(255);
			break;
		case Define.ST_MENU_LOGO:
			_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			_g.setColor(Main.COLOR_BLACK);
			_g.fillRect(0, 0, Define.SIZEX, Define.SIZEY);
			
			_g.drawImage(GfxManager.vImgLogo, Define.SIZEX2, Define.SIZEY2, Graphics.VCENTER|Graphics.HCENTER);
			
			_g.setAlpha(alpha);
			_g.drawImage(GfxManager.imgBlackBG, 0, 0, Graphics.TOP | Graphics.LEFT);
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
			_g.setAlpha(alpha);
			_g.drawImage(GfxManager.imgBlackBG, 0, 0, Graphics.TOP | Graphics.LEFT);
			_g.setAlpha(255);
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
			btnContinueCampaing.draw(_g, 0, 0);
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
		 case Define.ST_MENU_ON_LINE_CREATE_USER:
			 drawMenuBG(_g);
			 btnBack.draw(_g, 0, 0);
			 createUserBox.draw(_g);
			 break;
		 case Define.ST_MENU_ON_LINE_LOGIN:
			 drawMenuBG(_g);
			 btnBack.draw(_g, 0, 0);
			 loginBox.draw(_g);
			 break;
		 case Define.ST_MENU_ON_LINE_LIST_ALL_GAME:
			 drawMenuBG(_g);
			 btnBack.draw(_g, 0, 0);
			 TextManager.drawSimpleText(
					 _g, Font.FONT_SMALL, 
					 RscManager.allText[RscManager.TXT_GAME_PLAYER] + " " + GameState.getInstance().getName(), 
					 Define.SIZEX64, 
					 Define.SIZEY-Define.SIZEY64, Graphics.BOTTOM | Graphics.LEFT);
			 btnSearchGame.draw(_g, 0, 0);
			 btnCreateScene.draw(_g,0, 0);
			 if(selectSceneBox != null){
				 selectSceneBox.draw(_g, GfxManager.imgBlackBG);
			 }
			 if(notificationBox != null){
				 notificationBox.draw(_g, GfxManager.imgBlackBG);
				 btnCancel.draw(_g, (int)notificationBox.getModPosX(),0);
			 }
			 break;
		 case Define.ST_MENU_ON_LINE_LIST_JOIN_GAME:
			 drawMenuBG(_g);
			 btnBack.draw(_g, 0, 0);
			 TextManager.drawSimpleText(
					 _g, Font.FONT_SMALL, 
					 RscManager.allText[RscManager.TXT_GAME_PLAYER] + " " + GameState.getInstance().getName(), 
					 Define.SIZEX64, 
					 Define.SIZEY-Define.SIZEY64, Graphics.BOTTOM | Graphics.LEFT);
			 if(selectPreSceneBox != null){
				 selectPreSceneBox.draw(_g, GfxManager.imgBlackBG);
			 }
			 break;
		 case Define.ST_MENU_ON_LINE_CREATE_SCENE:
			 drawMenuBG(_g);
			 btnBack.draw(_g, 0, 0);
			 if(selectMapBox != null){
				 selectMapBox.draw(_g, GfxManager.imgBlackBG);
			 }
			 break;
		
		 
		 case Define.ST_MENU_EXIT:
			_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			break;
			
		case Define.ST_TEST:
			
			_g.setColor(0x000000);
			_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			_g.fillRect(0, 0, Define.SIZEX, Define.SIZEY);
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
	public static final long ST_TIME_CITA_1 = 1000;
	public static final long ST_TIME_CITA_2 = 4000;
	public static final long ST_TIME_CITA_3 = 2000;
	
	
	public static final long ST_TIME_LOGO_1 = 1000;
	public static final long ST_TIME_LOGO_2 = 1000;
	public static final long ST_TIME_LOGO_3 = 1000;
	
	public static final long ST_TIME_MAIN = 1200;
	
	public static int statePresentation;
	public static final int ST_PRESENTATION_1 = 0;
	public static final int ST_PRESENTATION_2 = 2;
	public static final int ST_PRESENTATION_3 = 3;
	public static int alpha;
	
	public static boolean runPresentation(long time1, long time2, long time3){
		switch(statePresentation){
		case ST_PRESENTATION_1:
			alpha = 255-(int)(((System.currentTimeMillis() - startTime)*255)/time1);
			if(alpha <= 0){
				alpha = 0;
				statePresentation = ST_PRESENTATION_2;
				startTime = System.currentTimeMillis();
			}
			break;
		case ST_PRESENTATION_2:
			if(System.currentTimeMillis()>startTime+time2){
				statePresentation = ST_PRESENTATION_3;
				startTime = System.currentTimeMillis();
				
				if(Main.state == Define.ST_MENU_START){
					SndManager.getInstance().playMusic(Main.MUSIC_INTRO, false);
				}
				
			}
			break;
		case ST_PRESENTATION_3:
			alpha = (int)(((System.currentTimeMillis() - startTime)*255)/time3);
			if(alpha >= 255){
				alpha = 255;
				return false;
			}
		}
		return true;
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
		if(alpha < 0){
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
		
		g.setAlpha(255);
		
		g.drawImage(GfxManager.imgCloudBG, (int)cloudFarBGX, (int)cloudFarBGY, Graphics.VCENTER | Graphics.HCENTER);
		g.setImageSize(1.2f, 1.2f);
		g.drawImage(GfxManager.imgCloudBG, (int)cloudNearBGX, (int)cloudNearBGY, Graphics.VCENTER | Graphics.HCENTER);
		g.setImageSize(1f, 1f);
		g.drawImage(GfxManager.imgSwordBG, 0, Define.SIZEY, Graphics.BOTTOM | Graphics.LEFT);
		g.setImageSize(1.4f, 1.4f);
		g.drawImage(GfxManager.imgCloudBG, (int)cloudNear2BGX, (int)cloudNear2BGY, Graphics.VCENTER | Graphics.HCENTER);
		g.setImageSize(1f, 1f);
	}
	
	private static Thread sceneUpdate;
	private static Thread preSceneUpdate;

	private static void updateScenes() {
		sceneUpdate = new Thread() {
			@Override
			public void run() {
				super.run();
				
				while(Main.state == Define.ST_MENU_ON_LINE_LIST_ALL_GAME){
					
					try {
						Thread.sleep(5000);
						//Log.i("Debug", "Actualizando lista de scenes...");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					SceneListData sceneListData = 
							OnlineInputOutput.getInstance().reviceSceneListData(GameState.getInstance().getName());
					if (sceneListData != null &&
							Main.state == Define.ST_MENU_ON_LINE_LIST_ALL_GAME && selectSceneBox != null) {
						Log.i("Debug", "Actualizando selectSceneBox " + Main.iFrame);
						String[] textList = new String[sceneListData.getSceneDataList().size()];
						
						boolean[] disableList = new boolean[sceneListData.getSceneDataList().size()];
						for (int i = 0; i < sceneListData.getSceneDataList().size(); i++) {
							disableList[i] = 
									!sceneListData.getSceneDataList().get(i).getNextPlayer().equals(GameState.getInstance().getName());
						}
						
						for (int i = 0; i < sceneListData.getSceneDataList().size(); i++) {
							textList[i] = ""+
									sceneListData.getSceneDataList().get(i).getId() + " - " +
									DataKingdom.SCENARY_NAME_LIST[sceneListData.getSceneDataList().get(i).getMap()] +
									" - ";
							if(disableList[i]){
								textList[i] += "NEXT " + sceneListData.getSceneDataList().get(i).getNextPlayer();
							}else{
								textList[i] += "YOUR TURN";
							}
						}
					selectSceneBox.refresh(sceneListData, RscManager.allText[RscManager.TXT_SELECT_MAP], textList);
					selectSceneBox.setDisabledList(disableList);
					}
				}
			}
		};
		sceneUpdate.start();
	}
	
	private static PreSceneListData preSceneListData;
	private static void updatePreScenes() {
		preSceneUpdate = new Thread() {
			@Override
			public void run() {
				super.run();
					
				while(Main.state == Define.ST_MENU_ON_LINE_LIST_JOIN_GAME){
					
					try {
						Thread.sleep(5000);
						//Log.i("Debug", "Actualizando lista de scenes...");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					preSceneListData =  
							OnlineInputOutput.getInstance().revicePreSceneListData(OnlineInputOutput.URL_GET_PRE_SCENE_LIST, GameState.getInstance().getName());
					if (preSceneListData != null &&
							Main.state == Define.ST_MENU_ON_LINE_LIST_JOIN_GAME && selectPreSceneBox != null) {
						Log.i("Debug", "Actualizando selectPreSceneBox " + Main.iFrame);
						String[] textList = new String[preSceneListData.getPreSceneDataList().size()];
						for(int i = 0; i < preSceneListData.getPreSceneDataList().size(); i++){
							int numPlayer = DataKingdom.INIT_MAP_DATA[preSceneListData.getPreSceneDataList().get(i).getMap()].length;
							textList[i] = ""+
									preSceneListData.getPreSceneDataList().get(i).getId() + "-" +
									preSceneListData.getPreSceneDataList().get(i).getHost() + " " +
									DataKingdom.SCENARY_NAME_LIST[preSceneListData.getPreSceneDataList().get(i).getMap()] +
									" - " +
									preSceneListData.getPreSceneDataList().get(i).getPlayerCount() + 
									"/" + numPlayer;
							}
						selectPreSceneBox.refresh(preSceneListData, RscManager.allText[RscManager.TXT_SELECT_MAP], textList);
						}
				}
			}
		};
		preSceneUpdate.start();
	}
	
	public static void updateNotifications(final NotificationListData notificationListData){
		Thread t = new Thread(){
			 @Override
			 public void run(){
				 OnlineInputOutput.getInstance().sendDataPackage(OnlineInputOutput.URL_UPDATE_NOTIFICATION, notificationListData);
			 }
		 };
		 t.start();
	}
}
