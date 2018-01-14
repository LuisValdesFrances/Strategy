package com.luis.army.gui;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.gui.MenuBox;
import com.luis.strategy.GfxManager;
import com.luis.strategy.constants.Define;

public class BattleBox extends MenuBox{
	
	public static final int STATE_PRESENTATION = 0;
	public static final int STATE_COMBAT = 1;
	
	private int type;
	public static final int TYPE_BATTLE_PLAIN = 0;
	public static final int TYPE_BATTLE_FOREST = 1;
	public static final int TYPE_BATTLE_MONTAIN = 2;
	public static final int TYPE_BATTLE_SMALL_CITY = 3;
	public static final int TYPE_BATTLE_MEDIUM_CITY = 4;
	public static final int TYPE_BATTLE_BIG_CITY = 5;
	public static final int TYPE_BATTLE_ARMY = 6;
	
	public BattleBox() {
		
		super(Define.SIZEX, Define.SIZEY, GfxManager.imgMediumBox, null, null, 
				"COMBAT",
				null, Font.FONT_MEDIUM, Font.FONT_SMALL);
		
		btnList.add(new Button(
				GfxManager.imgButtonCombatRelease, 
				GfxManager.imgButtonCombatFocus, 
				screenWidth/2 + GfxManager.imgMediumBox.getWidth()/2, 
				screenHeight/2 + GfxManager.imgMediumBox.getHeight()/2, 
				null, 
				-1){});
		
		btnList.add(new Button(
				GfxManager.imgButtonCardRelease, 
				GfxManager.imgButtonCardFocus, 
				screenWidth/2 - GfxManager.imgMediumBox.getWidth()/2, 
				screenHeight/2 + GfxManager.imgMediumBox.getHeight()/2, 
				null, 
				-1){});
	}
	
	
	public void start(int type){
		this.type = type;
		start();
	}
	
	@Override
	public void draw(Graphics g, boolean drawBG){
		super.draw(g, drawBG);
		if(state != STATE_UNACTIVE){
			String text="";
			switch(type){
			case TYPE_BATTLE_PLAIN:
				text = "BATTLE PLAIN";
				break;
			case TYPE_BATTLE_FOREST:
				text = "BATTLE FOREST";
				break;
			case TYPE_BATTLE_MONTAIN:
				text = "BATTLE MONTAIN";
				break;
			case TYPE_BATTLE_SMALL_CITY:
				text = "BATTLE SMALL CITY";
				break;
			case TYPE_BATTLE_MEDIUM_CITY:
				text = "BATTLE MEDIUM CITY";
				break;
			case TYPE_BATTLE_BIG_CITY:
				text = "BATTLE BIG CITY";
				break;
			case TYPE_BATTLE_ARMY:
				text = "BATTLE ARMY";
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
