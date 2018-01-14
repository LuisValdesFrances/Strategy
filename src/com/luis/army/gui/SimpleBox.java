package com.luis.army.gui;

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
	
	public SimpleBox(Image imgBox, boolean includeButton) {
		
		super(Define.SIZEX, Define.SIZEY, imgBox, null, null, null,null, Font.FONT_MEDIUM, Font.FONT_SMALL);
		
		if(includeButton)
			btnList.add(new Button(
				GfxManager.imgButtonOkRelease, 
				GfxManager.imgButtonOkFocus, 
				screenWidth/2, 
				screenHeight/2 + GfxManager.imgSmallBox.getHeight()/2, 
				null, 
				-1){});
	}
	
	
	public void start(String textHeader, String textBody){
		this.textHeader = textHeader;
		this.textBody = textBody;
		start();
	}
	
	@Override
	public void draw(Graphics g, boolean drawBG){
		super.draw(g, drawBG);
		if(state != STATE_UNACTIVE){
			TextManager.draw(g, Font.FONT_SMALL, textBody, 
					x+(int)modPosX, 
					y + (textHeader != null ?Font.getFontHeight(Font.FONT_MEDIUM)/2:0), 
					imgBox.getWidth()-imgBox.getWidth()/8,
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

