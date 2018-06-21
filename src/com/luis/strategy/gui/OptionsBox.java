package com.luis.strategy.gui;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.gui.MenuBox;
import com.luis.lgameengine.implementation.fileio.FileIO;
import com.luis.lgameengine.implementation.sound.SndManager;
import com.luis.strategy.GfxManager;
import com.luis.strategy.Main;
import com.luis.strategy.RscManager;
import com.luis.strategy.constants.Define;

public class OptionsBox extends MenuBox{
	
	private int languageY;
	private int soundY;
	private int enableAlertsY;
	private int game3DY;
	
	private int optLabelX;//center | right
	private int optValueX;
	
	private Button languageButton;
	private Button soundButton;
	private Button alertsButton;
	private Button game3DButton;
	
	private int language;
	private boolean sound;
	private boolean enableAlerts;
	private boolean game3D;

	public OptionsBox() {
		super(Define.SIZEX, Define.SIZEY, GfxManager.imgBigBox, null, null,
				Define.SIZEX2, Define.SIZEY2-GfxManager.imgGameHud.getHeight()/2,
				RscManager.allText[RscManager.TXT_OPTIONS],
				null, Font.FONT_MEDIUM, Font.FONT_SMALL, -1, Main.FX_NEXT);
		
		
		btnList.add(new Button(
				GfxManager.imgButtonOkRelease, 
				GfxManager.imgButtonOkFocus, 
				screenWidth/2, 
				screenHeight/2 + imgBox.getHeight()/2, 
				null, 
				-1){
			@Override
			public void onButtonPressDown() {
				super.onButtonPressDown();
			}
			@Override
			public void onButtonPressUp() {
				SndManager.getInstance().playFX(Main.FX_NEXT, 0);
			};
		});
		
		int numOptions = 4;//Language, sound, enable alerts, game3D
		int sepY = Define.SIZEY32;
		int sepX = Define.SIZEX16;
		
		int optionsHeight = Font.getFontHeight(Font.FONT_SMALL)*numOptions + (sepY * (numOptions-1));
		languageY = Define.SIZEY2-(optionsHeight/2) + Font.getFontHeight(Font.FONT_SMALL)/2;
		soundY = Define.SIZEY2-(optionsHeight/2) + (int)(Font.getFontHeight(Font.FONT_SMALL)*1.5f)*sepY;
		enableAlertsY = Define.SIZEY2-(optionsHeight/2) + (int)(Font.getFontHeight(Font.FONT_SMALL)*2.5f)*sepY*2;
		game3DY = Define.SIZEY2-(optionsHeight/2) + (int)(Font.getFontHeight(Font.FONT_SMALL)*3.5f)*sepY*3;
		
		
		//Cargo las opciones por defecto
		String dataConfig = FileIO.getInstance().loadData(Define.DATA_CONFIG, Main.getInstance().getActivity());
		language = Integer.parseInt(dataConfig.split("\n")[0]);
		sound = dataConfig.split("\n")[1].equals("true");
		enableAlerts = dataConfig.split("\n")[2].equals("true");
		game3D = dataConfig.split("\n")[3].equals("true");
		
		optValueX = Define.SIZEX2- sepX;
		
		languageButton = new Button(null, null, optValueX, languageY, null, -1);
		
	}
	
	private void updateLanguageButton(int language){
		/*
		if(language == 0){
			languageButton.setImgRelese(GfxManager.imgEnglishRelease);
			languageButton.setImgFocus(GfxManager.imgEnglishFocus);
		}else if(language == 1){
			languageButton.setImgRelese(GfxManager.imgSpanishRelease);
			languageButton.setImgFocus(GfxManager.imgSpanishFocus);
		}else{
			languageButton.setImgRelese(GfxManager.imgCatalaRelease);
			languageButton.setImgFocus(GfxManager.imgCatalaFocus);
		}
		*/
	}

}
