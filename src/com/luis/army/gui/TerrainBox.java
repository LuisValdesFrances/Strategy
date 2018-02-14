package com.luis.army.gui;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.gui.MenuBox;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;
import com.luis.strategy.GfxManager;
import com.luis.strategy.constants.Define;
import com.luis.strategy.constants.GameParams;
import com.luis.strategy.game.Player;
import com.luis.strategy.map.Kingdom;

public class TerrainBox extends MenuBox{

	private Button buttonInfo;
	private Button buttonNewArmy;
	private boolean recruited;
	
	public TerrainBox() {
		super(Define.SIZEX, Define.SIZEY, GfxManager.imgMediumBox, null, null,
				Define.SIZEX2, Define.SIZEY2-GfxManager.imgGameHud.getHeight()/2,
				null,
				null, Font.FONT_MEDIUM, Font.FONT_SMALL);
		
		btnList.add(new Button(
				GfxManager.imgButtonCancelRelease,
				GfxManager.imgButtonCancelFocus,
				getX() - GfxManager.imgMediumBox.getWidth()/2, 
				getY() - GfxManager.imgMediumBox.getHeight()/2, 
				null, 
				-1){});
		}
	
	private int headY;
	private int bodyY;
	private int imageY;
	private Kingdom kingdom;
	private int terrainIndex;
	
	private String textHeader;
	private String textBody;
	
	public void start(Player player, Kingdom kingdom, boolean clear, int terrainIndex){
		super.start();
		
		this.kingdom = kingdom;
		this.terrainIndex = terrainIndex;
		this.recruited = false;
		
		String text[] ={"Plain", "Forest", "Montain", "Small city", "Medium city", "Big city"};
		textHeader = text[terrainIndex];
		textBody = "Defense: " + GameParams.TERRAIN_DEFENSE[terrainIndex];
		
		int sep = GfxManager.imgButtonInfoRelease.getHeight()/8 ;
		int totalHeight = 
				Font.getFontHeight(Font.FONT_BIG) + 
				Font.getFontHeight(Font.FONT_MEDIUM) + 
				GfxManager.imgTerrainBox.get(0).getHeight() + 
				GfxManager.imgButtonInfoRelease.getHeight()
				+ sep*5;
		int initY = getY() - totalHeight/2;
		
		headY = initY + Font.getFontHeight(Font.FONT_BIG)/2 + sep;
		bodyY = initY + Font.getFontHeight(Font.FONT_BIG) + Font.getFontHeight(Font.FONT_MEDIUM)/2 + sep*2;
		imageY = initY + Font.getFontHeight(Font.FONT_BIG) + Font.getFontHeight(Font.FONT_MEDIUM) + 
				GfxManager.imgTerrainBox.get(0).getHeight()/2 + sep*3;
		
		buttonInfo = new Button(
				GfxManager.imgButtonInfoRelease, GfxManager.imgButtonInfoFocus, 
				getX()-GfxManager.imgTerrainBox.get(0).getWidth()/2 + GfxManager.imgButtonInfoRelease.getWidth()/2, 
				initY + 
				Font.getFontHeight(Font.FONT_BIG) + Font.getFontHeight(Font.FONT_MEDIUM) + GfxManager.imgTerrainBox.get(0).getHeight() +
				GfxManager.imgButtonInfoRelease.getHeight()/2 + sep*4, 
				null, -1){
			public void onButtonPressUp() {
			};
		};
		if(clear){
			buttonNewArmy = new Button(
					GfxManager.imgButtonNewArmyRelease, GfxManager.imgButtonNewArmyFocus, 
					getX()+GfxManager.imgTerrainBox.get(0).getWidth()/2 - GfxManager.imgButtonNewArmyRelease.getWidth()/2, 
					buttonInfo.getY(), 
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
	
	@Override
	public void draw(Graphics g, boolean drawBG){
		super.draw(g, drawBG);
		if(state != STATE_UNACTIVE){
			
			TextManager.drawSimpleText(g, Font.FONT_BIG, textHeader, 
					getX()+(int)modPosX, headY, Graphics.VCENTER | Graphics.HCENTER);
			
			TextManager.drawSimpleText(g, Font.FONT_MEDIUM, textBody, 
					getX()+(int)modPosX, bodyY, Graphics.VCENTER | Graphics.HCENTER);
			
			g.drawImage(GfxManager.imgTerrainBox.get(terrainIndex), 
					getX()+(int)modPosX, imageY, Graphics.VCENTER | Graphics.HCENTER);
		
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
