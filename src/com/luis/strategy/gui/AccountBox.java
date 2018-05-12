package com.luis.strategy.gui;

import android.util.Log;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.gui.Keyboard;
import com.luis.lgameengine.gui.MenuBox;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;
import com.luis.strategy.GfxManager;
import com.luis.strategy.constants.Define;



public class AccountBox extends MenuBox{
	
	private Keyboard keyboard;
	
	private String textName;
	private String textPassword;
	private String textConfPassword;

	public AccountBox() {
		super(
				Define.SIZEX, Define.SIZEY, 
				null,
				null, 
				null,
				Define.SIZEX2, Define.SIZEY2,
				null, null,
				-1, -1);
		keyboard = new Keyboard(
				Define.SIZEX2, 
				Define.SIZEY-GfxManager.imgButtonKeyboardRelease.getHeight()*2, 
				GfxManager.imgButtonKeyboardRelease, GfxManager.imgButtonKeyboardFocus, 
				GfxManager.imgButtonKeyboardReleaseSp, GfxManager.imgButtonKeyboardFocusSp,
				Font.FONT_BIG, Font.FONT_SMALL){
			@Override
			public void onButtonPressUp() {}
		};
		textName = new String("");
		textPassword = "";
		textConfPassword = "";
		
		keyboard.setTextChain(textName);
	}
	
	/*
	public void onFinish() {
		for(PlayerConf pc : playerConfList){
			
		}
	};
	*/
	
	public boolean update(MultiTouchHandler touchHandler, float delta){
		super.update(touchHandler, delta);
		keyboard.update(touchHandler);
		return true;
	}
	
	public void draw(Graphics g){
		g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
		TextManager.drawSimpleText(g, Font.FONT_BIG, keyboard.getTextChain(), Define.SIZEX2, Define.SIZEY4, Graphics.VCENTER | Graphics.HCENTER);
		keyboard.draw(g);
	}

}
