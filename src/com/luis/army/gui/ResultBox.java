package com.luis.army.gui;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.menu.Button;
import com.luis.lgameengine.menu.MenuBox;
import com.luis.strategy.GfxManager;
import com.luis.strategy.constants.Define;

public class ResultBox extends MenuBox{
	
	private int type;
	
	public static final int TYPE_DEFEAT = 0;
	public static final int TYPE_VICTORY = 1;
	public static final int TYPE_MASSACRE = 2;
	public static final int TYPE_CONQUEST = 3;
	
	public ResultBox() {
		
		super(Define.SIZEX, Define.SIZEY, GfxManager.imgSmallBox, null, null, 
				"RESULT",
				null, Font.FONT_MEDIUM, Font.FONT_SMALL);
		
		btnList.add(new Button(
				GfxManager.imgButtonOkRelease, 
				GfxManager.imgButtonOkFocus, 
				screenWidth/2, 
				screenHeight/2 + GfxManager.imgSmallBox.getHeight()/2, 
				null, 
				-1){});
	}
	
	
	public void start(int type){
		this.type = type;
		start();
	}
	
	@Override
	public void draw(Graphics g){
		super.draw(g);
		if(state != STATE_UNACTIVE){
			String text="";
			
			switch(type){
			case TYPE_DEFEAT:
				text="DEFEAT";
				break;
			case TYPE_VICTORY:
				text="VICTORY";
				break;
			case TYPE_CONQUEST:
				text="CONQUEST";
				break;
			case TYPE_MASSACRE:
				text="MASSACRE";
				break;
			}
			
			TextManager.drawSimpleText(g, 
					Font.FONT_SMALL,
					text,
					x+(int)modPosX, y - Font.getFontHeight(Font.FONT_SMALL),
					Graphics.VCENTER|Graphics.HCENTER);
		}
	}

}

