package com.luis.strategy;

import android.util.Log;

import com.luis.army.gui.ConfigMapBox;
import com.luis.lgameengine.gameutils.Settings;
import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.gui.ListBox;
import com.luis.lgameengine.gui.MenuElement;
import com.luis.lgameengine.implementation.fileio.FileIO;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.strategy.GameState.PlayerConf;
import com.luis.strategy.constants.Define;
import com.luis.strategy.constants.GameParams;
import com.luis.strategy.data.DataKingdom;

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
	
	private static ListBox selectMapBox;
	private static ConfigMapBox configMapBox;
	
	
	public static void init(int _iMenuState){
		Log.i("Info", "Init State: "+ _iMenuState);
		switch (_iMenuState) {
        case Define.ST_MENU_LOGO:
        	
        	MenuElement.imgBG = GfxManager.imgBlackBG;
    		MenuElement.bgAlpha = (int)(GameParams.BG_BLACK_ALPHA*0.5);
			
    		iStateLogo = ST_LOGO_1;
			alpha = 255;
			logoAlpha = 255;
			startTime = System.currentTimeMillis();
			Font.init(GfxManager.vImgFontSmall, GfxManager.vImgFontMedium, GfxManager.vImgFontBig);
//			if(FileIO.isData()){
//				
//			}else{
//				
//			}
			RscManager.loadLanguage(Main.iLanguage);
			
			btnBack = new Button(GfxManager.imgButtonArrowBackRelease, GfxManager.imgButtonArrowBackFocus,
					Define.SIZEX32+GfxManager.imgButtonArrowBackRelease.getWidth()/2,
					Define.SIZEY32+GfxManager.imgButtonArrowBackRelease.getHeight()/2,
					null, -1){
				@Override
				public void onButtonPressUp() {
					switch(Main.state){
					case Define.ST_MENU_SELECT_GAME:
						Main.changeState(Define.ST_MENU_MAIN, false);
						break;
					case Define.ST_MENU_SELECT_MAP:
						selectMapBox.cancel();
						break;
					case Define.ST_MENU_CONFIG_MAP:
						configMapBox.cancel();
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
					public void onButtonPressUp(){}
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
			}
			break;
		case Define. ST_MENU_OPTIONS:
			break;
		case Define. ST_MENU_MORE:
		case Define. ST_MENU_EXIT:
		case Define. ST_MENU_HELP:
		case Define. ST_MENU_ABOUT:
			break;
			
		case Define. ST_MENU_SELECT_GAME:
			btnOnLine = new Button(
					GfxManager.imgButtonMenuBigRelease, 
					GfxManager.imgButtonMenuBigFocus, 
					Define.SIZEX-(int)(GfxManager.imgButtonMenuBigRelease.getWidth()/2)-Define.SIZEY64, 
					Define.SIZEY-(int)(GfxManager.imgButtonMenuBigRelease.getHeight()/2)-Define.SIZEY64,
					RscManager.allText[RscManager.TXT_ON_LINE], Font.FONT_MEDIUM){
				@Override
				public void onButtonPressDown(){}
				
				@Override
				public void onButtonPressUp(){}
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
		}
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
			
		case Define.ST_MENU_SELECT_GAME:
			drawMenuBG(_g);
			btnBack.draw(_g, 0, 0);
			btnOnLine.draw(_g, 0, 0);
			btnPassAndPlay.draw(_g, 0, 0);
			break;
		case Define.ST_MENU_SELECT_MAP:
			drawMenuBG(_g);
			btnBack.draw(_g, 0, 0);
			selectMapBox.draw(_g, true);
			break;
		
		case Define.ST_MENU_CONFIG_MAP:
			drawMenuBG(_g);
			btnBack.draw(_g, 0, 0);
			btnNext.draw(_g, 0, 0);
			configMapBox.draw(_g, true);
			break;
			
		case Define.ST_MENU_EXIT:
			_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			break;
		}
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
		
		if(Main.state == Define.ST_MENU_MAIN || Main.state == Define.ST_MENU_SELECT_GAME){
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
	
	public static void saveSystemData(){
		Main.iDataList[Main.INDEX_DATA_LANGUAGE] = Main.iLanguage;
		Log.i("LOGCAT", "Save: "+ Main.iDataList[Main.INDEX_DATA_LANGUAGE]);
		FileIO.saveData(Main.iDataList, Main.DATA_NAME, Settings.getInstance().getActiviy());
		Log.i("LOGCAT", "Datos salvados");
	}
	}
