package com.luis.strategy;

import android.util.Log;

import com.luis.lgameengine.gameutils.Settings;
import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.implementation.fileio.FileIO;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;
import com.luis.lgameengine.implementation.sound.SndManager;
import com.luis.strategy.constants.Define;

public class ModeMenu {
	
	public static final int NUMBER_OPTS_LANGUAGE = 3;
	public static final int NUMBER_OPTS_SOUND = 2;
	public static final int NUMBER_OPTS_MAIN_MENU = 4;
	public static final int NUMBER_OPTS_MORE_MENU = 2;
	
	public static int optionSelect;
	public static int iLanguageSelect;
	public static int iSoundSelect;
	
	public static Image vImgPlanet;
	public static Image vImgAstheroid;
	public static Image vImgAstheroid2;
	
	private static Button btnStart;
	
	
	public static void init(int _iMenuState){
		Log.i("Info", "Init State: "+ _iMenuState);
		optionSelect = 0;
		switch (_iMenuState) {
        case Define.ST_MENU_LOGO:
			iStateLogo = ST_LOGO_1;
			iLevelAlpha = 255;
			lInitialLogoTime = System.currentTimeMillis();
			Font.init(GfxManager.vImgFontSmall, GfxManager.vImgFontMedium, GfxManager.vImgFontBig);
//			if(FileIO.isData()){
//				
//			}else{
//				
//			}
			RscManager.loadLanguage(Main.iLanguage);
			break;
		case Define.ST_MENU_ASK_SOUND:
		case Define.ST_MENU_ASK_LANGUAGE:
			
			break;
		case Define.ST_MENU_MAIN:
			cloudFarBGX = Define.SIZEX2;
			cloudNearBGX = Define.SIZEX;
			cloudNear2BGX = Define.SIZEX+Define.SIZEX2;
			
			cloudFarBGY = Define.SIZEY2;
			cloudNearBGY = Define.SIZEY2+Define.SIZEY4;
			cloudNear2BGY = Define.SIZEY;
			
			btnStart = new Button(
					GfxManager.imgButtonNextRelease, 
					GfxManager.imgButtonNextFocus, 
					Define.SIZEX-(int)(GfxManager.imgButtonNextRelease.getWidth()*0.75f), 
					Define.SIZEY-(int)(GfxManager.imgButtonNextRelease.getHeight()*0.75f),
					null, 0){
				@Override
				public void onButtonPressDown(){}
				
				@Override
				public void onButtonPressUp(){
					GameState.getInstance().setLevel(0);
					Main.changeState(Define.ST_GAME_INIT, true);
					reset();
				}
			};
			break;
		case Define. ST_MENU_OPTIONS:
			break;
		case Define. ST_MENU_MORE:
		case Define. ST_MENU_EXIT:
		case Define. ST_MENU_HELP:
		case Define. ST_MENU_ABOUT:
			break;
			
		case Define. ST_MENU_SELECT_GAME:
			
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
			btnStart.update(UserInput.getInstance().getMultiTouchHandler());
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
			
			break;
		}
	}
	
	public static void draw(Graphics _g){
		switch (Main.state) {
		case Define.ST_MENU_LOGO:
			_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			_g.setColor(Main.COLOR_BLACK);
			_g.fillRect(0, 0, Define.SIZEX, Define.SIZEY);
			_g.setAlpha(iLevelAlpha);
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
			_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			_g.drawImage(GfxManager.imgMainBG, 0, 0, Graphics.TOP | Graphics.LEFT);
			_g.drawImage(GfxManager.imgCloudBG, (int)cloudFarBGX, (int)cloudFarBGY, Graphics.VCENTER | Graphics.HCENTER);
			_g.setImageSize(1.2f, 1.2f);
			_g.drawImage(GfxManager.imgCloudBG, (int)cloudNearBGX, (int)cloudNearBGY, Graphics.VCENTER | Graphics.HCENTER);
			_g.setImageSize(1f, 1f);
			_g.drawImage(GfxManager.imgSwordBG, 0, Define.SIZEY, Graphics.BOTTOM | Graphics.LEFT);
			_g.setImageSize(1.4f, 1.4f);
			_g.drawImage(GfxManager.imgCloudBG, (int)cloudNear2BGX, (int)cloudNear2BGY, Graphics.VCENTER | Graphics.HCENTER);
			_g.setImageSize(1f, 1f);
			
			btnStart.draw(_g, 0, 0);
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
			_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
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
	
	public static long lInitialLogoTime;
	public static final long ST_TIME_LOGO_1 = 1000;
	public static final long ST_TIME_LOGO_2 = 500;
	public static final long ST_TIME_LOGO_3 = 1000;
	public static int iStateLogo;
	public static final int ST_LOGO_1 = 0;
	public static final int ST_LOGO_2 = 2;
	public static final int ST_LOGO_3 = 3;
	public static int iLevelAlpha;
	
	public static void runLogo(){
		switch(iStateLogo){
		case ST_LOGO_1:
			iLevelAlpha = (int)(((System.currentTimeMillis() - lInitialLogoTime)*255)/ST_TIME_LOGO_1);
			if(iLevelAlpha >= 255){
				iLevelAlpha = 255;
				iStateLogo = ST_LOGO_2;
				lInitialLogoTime = System.currentTimeMillis();
			}
			break;
		case ST_LOGO_2:
			if(System.currentTimeMillis()>lInitialLogoTime+ST_TIME_LOGO_2){
				iStateLogo = ST_LOGO_3;
				lInitialLogoTime = System.currentTimeMillis();
				
			}
			break;
		case ST_LOGO_3:
			iLevelAlpha = 255- (int)(((System.currentTimeMillis() - lInitialLogoTime)*255)/ST_TIME_LOGO_3);
			if(iLevelAlpha <= 0){
				iLevelAlpha = 255;
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
	public static void runMenuBG(float delta){
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
	}
	
	public static void saveSystemData(){
		Main.iDataList[Main.INDEX_DATA_LANGUAGE] = Main.iLanguage;
		Log.i("LOGCAT", "Save: "+ Main.iDataList[Main.INDEX_DATA_LANGUAGE]);
		FileIO.saveData(Main.iDataList, Main.DATA_NAME, Settings.getInstance().getActiviy());
		Log.i("LOGCAT", "Datos salvados");
	}
	}
