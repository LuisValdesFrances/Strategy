package com.luis.strategy.gui;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.gui.MenuBox;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;
import com.luis.lgameengine.implementation.sound.SndManager;
import com.luis.strategy.GfxManager;
import com.luis.strategy.Main;
import com.luis.strategy.RscManager;
import com.luis.strategy.constants.Define;
import com.luis.strategy.constants.GameParams;
import com.luis.strategy.map.Kingdom;
import com.luis.strategy.map.Player;

public class CityBox extends MenuBox{

	private Button buttonInfo;
	private Button buttonNewArmy;
	private boolean recruited;
	
	public CityBox() {
		super(
				Define.SIZEX, Define.SIZEY, GfxManager.imgMediumBox, null, null,
				Define.SIZEX2, Define.SIZEY2-GfxManager.imgGameHud.getHeight()/2,
				null,
				null, Font.FONT_MEDIUM, Font.FONT_SMALL, -1, Main.FX_BACK);
		
		btnList.add(new Button(
				GfxManager.imgButtonCancelRelease,
				GfxManager.imgButtonCancelFocus,
				getX() - GfxManager.imgMediumBox.getWidth()/2, 
				getY() - GfxManager.imgMediumBox.getHeight()/2, 
				null, 
				-1){
			@Override
			public void onButtonPressDown() {}
			
			@Override
			public void onButtonPressUp() {
				super.onButtonPressUp();
				SndManager.getInstance().playFX(Main.FX_BACK, 0);
			}
		});
	}
	
	private int headY;
	private Kingdom kingdom;
	
	private String textHeader;
	
	public void start(Player player, Kingdom kingdom, boolean clear, int terrainIndex){
		super.start();
		
		this.kingdom = kingdom;
		this.recruited = false;
		
		textHeader = RscManager.allText[RscManager.TXT_GAME_DEFENSE] + " " + GameParams.TERRAIN_DEFENSE[terrainIndex];
		textHeader += " - " + RscManager.allText[RscManager.TXT_GAME_GOLD]  + " " + + GameParams.TERRAIN_DEFENSE[terrainIndex];
		textHeader += " - " + RscManager.allText[RscManager.TXT_GAME_FAITH]  + " " + + GameParams.TERRAIN_DEFENSE[terrainIndex];
		
		int sep = GfxManager.imgButtonInfoRelease.getHeight()/6 ;
		int totalHeight = 
				Font.getFontHeight(Font.FONT_SMALL) + 
				GfxManager.imgTowerList.get(2).getHeight()*3 + sep*5;
		int initY = getY() - totalHeight/2;
		
		headY = initY + Font.getFontHeight(Font.FONT_SMALL)/2 + sep;
		
		buttonInfo = new Button(
				GfxManager.imgButtonInfoRelease, GfxManager.imgButtonInfoFocus, 
				getX() - GfxManager.imgMediumBox.getWidth()/2, 
				getY() + GfxManager.imgMediumBox.getHeight()/2, 
				null, -1){
			public void onButtonPressUp() {
			};
		};
		if(clear){
			buttonNewArmy = new Button(
					GfxManager.imgButtonNewArmyRelease, GfxManager.imgButtonNewArmyFocus, 
					getX() + GfxManager.imgMediumBox.getWidth()/2, 
					getY() + GfxManager.imgMediumBox.getHeight()/2, 
					null, -1){
				public void onButtonPressUp() {
					onBuy();
				};
			};
			buttonNewArmy.setDisabled(player.getGold() < GameParams.ARMY_COST);
		}else{
			buttonNewArmy = null;
		}
	}
	
	@Override
	public boolean update(MultiTouchHandler touchHandler, float delta) {
		if(state == STATE_ACTIVE){
			buttonInfo.update(touchHandler);
			if(buttonNewArmy != null && !buttonNewArmy.isDisabled()){
				buttonNewArmy.update(touchHandler);
			}
		}
		return super.update(touchHandler, delta);
	}
	
	public void draw(Graphics g){
		super.draw(g, GfxManager.imgBlackBG);
		if(state != STATE_UNACTIVE){
			
			g.setAlpha(110);
			g.drawImage(GfxManager.imgTerrainBox.get(3), 
					getX()+(int)modPosX, getY(), Graphics.VCENTER | Graphics.HCENTER);
			g.setAlpha(255);
			
			TextManager.drawSimpleText(g, Font.FONT_SMALL, textHeader, 
					getX()+(int)modPosX, headY, Graphics.VCENTER | Graphics.HCENTER);
			
			buttonInfo.draw(g, (int)modPosX, 0);
			if(buttonNewArmy != null){
				buttonNewArmy.draw(g, (int)modPosX, 0);
			}
		}
	}
	
	public void onBuy(){
		recruited = true;
	}
	
	public Kingdom getKingdom() {
		return kingdom;
	}

	public boolean isRecruited() {
		return recruited;
	}

	public void setRecruited(boolean recruited) {
		this.recruited = recruited;
	}
	
	

}
