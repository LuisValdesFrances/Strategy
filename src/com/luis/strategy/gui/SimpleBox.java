package com.luis.strategy.gui;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.gui.MenuBox;
import com.luis.strategy.GfxManager;
import com.luis.strategy.constants.Define;

public class SimpleBox extends MenuBox{
	
	private String textBody;
	
	public SimpleBox(Image imgBox, boolean includeButton, boolean includeCancelButton) {
		
		super(
			Define.SIZEX, Define.SIZEY, imgBox, null, null, 
			Define.SIZEX2, Define.SIZEY2,
			null,null, Font.FONT_MEDIUM, Font.FONT_SMALL);
		
		if(includeButton)
			btnList.add(new Button(
					GfxManager.imgButtonOkRelease, 
					GfxManager.imgButtonOkFocus, 
					screenWidth/2, 
					screenHeight/2 + GfxManager.imgSmallBox.getHeight()/2, 
					null, 
					-1){});
		
		if(includeCancelButton)
			btnList.add(new Button(
					GfxManager.imgButtonDeleteRelease, 
					GfxManager.imgButtonDeleteFocus, 
					screenWidth/2-GfxManager.imgSmallBox.getWidth()/2, 
					screenHeight/2 - GfxManager.imgSmallBox.getHeight()/2, 
					null, 
					-1){});
	}
	
	
	public void start(String textHeader, String textBody){
		this.textHeader = textHeader;
		this.textBody = textBody;
		start();
	}
	
	public void draw(Graphics g, Image imgBG){
		super.draw(g, imgBG);
		if(state != STATE_UNACTIVE){
			TextManager.draw(g, Font.FONT_SMALL, textBody, 
					getX() + (int)modPosX, 
					getY() + (textHeader != null ?Font.getFontHeight(Font.FONT_MEDIUM)/2:0), 
					imgBox.getWidth()-imgBox.getWidth()/16,
					TextManager.ALING_CENTER, -1);
		}
	}


	public String getTextBody() {
		return textBody;
	}


	public void setTextBody(String textBody) {
		this.textBody = textBody;
	}
	
	

}

