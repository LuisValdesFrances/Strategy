package com.luis.strategy.gui;

import java.util.ArrayList;
import java.util.List;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.gui.MenuElement;
import com.luis.lgameengine.gui.NotificationBox;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;
import com.luis.strategy.GfxManager;
import com.luis.strategy.RscManager;
import com.luis.strategy.constants.Define;
import com.luis.strategy.constants.GameParams;
import com.luis.strategy.map.Army;

public class ArmyBuyBox {
	
	private Army army;
	
	public static final int STATE_UNACTIVE = 0;
	public static final int STATE_START = 1;
	public static final int STATE_SHOW = 2;
	public static final int STATE_NEXT = 3;
	public static final int STATE_LAST = 4;
	public static final int STATE_END = 5;
	
	private Button buttonCancel;
	private Button buttonBuy;
	private Button buttonLeft;
	private Button buttonRight;
	private int state;
	private int index;
	
	private int troopX;
	private int troopY;
	private int textX;
	private int textY;
	
	private List<String>nameTroopList;
	private List<String>descTroopList;
	
	private int modPosY;
	
	public ArmyBuyBox(){
		
		int imageW = 
				GfxManager.imgBigTroop.get(0).getWidth() + 
				GfxManager.imgButtonCancelRelease.getWidth()/2 + 
				GfxManager.imgButtonCrossBigRelease.getWidth()/2 -GfxManager.imgButtonCrossBigRelease.getWidth()/4;
		int boxW =	GfxManager.imgTextBox.getWidth();
		
		troopX = Define.SIZEX2- (boxW-imageW)/2 - imageW / 2;
		textX = Define.SIZEX2- (boxW-imageW)/2 + boxW / 2;
		
		troopY =Define.SIZEY2-GfxManager.imgGameHud.getHeight()/2;
		textY = Define.SIZEY2-GfxManager.imgGameHud.getHeight()/2;
		
		
		descTroopList = new ArrayList<String>();
		
		for(int i = 0; i < GfxManager.imgBigTroop.size(); i++){
			descTroopList.add("Description text large explain combat habilities and skills for troop with index " + i);
		}
		
		nameTroopList = new ArrayList<String>();
		
		for(int i = 0; i < GfxManager.imgBigTroop.size(); i++){
			nameTroopList.add(RscManager.allText[RscManager.TXT_GAME_INFANTRY + i]);
		}
		
		buttonCancel = new Button(
				GfxManager.imgButtonCancelRelease, GfxManager.imgButtonCancelFocus, 
				troopX-GfxManager.imgBigTroop.get(0).getWidth()/2, 
				troopY-GfxManager.imgBigTroop.get(0).getHeight()/2, null, -1){
			public void onButtonPressUp() {
				if(state==STATE_SHOW){
					state = STATE_END;
				}
			};
		};
		buttonBuy = new Button(
				GfxManager.imgButtonCrossBigRelease, GfxManager.imgButtonCrossBigFocus, 
				troopX+GfxManager.imgBigTroop.get(0).getWidth()/2-GfxManager.imgButtonCrossBigRelease.getWidth()/4, 
				troopY+GfxManager.imgBigTroop.get(0).getHeight()/2-GfxManager.imgButtonCrossBigRelease.getHeight()/4, 
				null, -1){
			public void onButtonPressUp() {
				String text = "New troop has been acquired";
				NotificationBox.getInstance().addMessage(text);
				onBuy();
				buttonBuy.setDisabled(army.getPlayer().getGold() < GameParams.TROOP_COST[index]);
				buttonBuy.reset();
			};
		};
		
		buttonLeft = new Button(
				GfxManager.imgPadWest, GfxManager.imgPadWest, 
				troopX-GfxManager.imgBigTroop.get(0).getWidth()/2-GfxManager.imgPadWest.getWidth(), 
				troopY, null, -1){
			public void onButtonPressUp() {
				reset();
				index = index+1%(descTroopList.size()-1);
				index = index > descTroopList.size()-1 ? 0 : index;
				buttonBuy.setDisabled(army.getPlayer().getGold() < GameParams.TROOP_COST[index]);
			};
		};
		
		buttonRight = new Button(
				GfxManager.imgPadEast, GfxManager.imgPadEast, 
				troopX+GfxManager.imgBigTroop.get(0).getWidth()/2+GfxManager.imgPadWest.getWidth(), 
				troopY, null, -1){
			public void onButtonPressUp() {
				reset();
				index = index-1;
				index = index < 0 ? descTroopList.size()-1 : index;
				buttonBuy.setDisabled(army.getPlayer().getGold() < GameParams.TROOP_COST[index]);
			};
		};
	}
	
	public void start(Army army){
		this.army = army;
		this.state = STATE_START;
		this.modPosY = -Define.SIZEY2-GfxManager.imgBigTroop.get(0).getHeight()/2;
		buttonBuy.setDisabled(army.getPlayer().getGold() < GameParams.TROOP_COST[index]);
	}
	
	public boolean update(MultiTouchHandler touchHandler, float delta){
		if(state != STATE_UNACTIVE){
			switch(state){
			case STATE_START:
				modPosY -= (modPosY*8f)*delta - 1f;
				if(modPosY >= 0){
					modPosY = 0;
					state = STATE_SHOW;
				}
				break;
			case STATE_SHOW:
				
				buttonLeft.update(touchHandler);
				buttonRight.update(touchHandler);
				buttonBuy.update(touchHandler);
				buttonCancel.update(touchHandler);
				
				break;
			case STATE_NEXT:
				break;
			case STATE_LAST:
				break;
			case STATE_END:
				modPosY += (modPosY*16f)*delta + 1f;
				if(modPosY >= Define.SIZEY2+GfxManager.imgBigTroop.get(0).getHeight()/2){
					state = STATE_UNACTIVE;
				}
				break;
			}
			return true;
		}else{
			return false;
		}
	}
	
	public void draw(Graphics g){
		if(state != STATE_UNACTIVE){
			g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			int menuHeight = GfxManager.imgTextBox.getHeight();
			int modAlpha = (int) ((Math.abs(modPosY) * MenuElement.bgAlpha) / 
					(Define.SIZEY2+menuHeight/2));
			g.setAlpha(MenuElement.bgAlpha-modAlpha);
			g.drawImage(GfxManager.imgBlackBG, Define.SIZEX2, Define.SIZEY2, Graphics.VCENTER | Graphics.HCENTER);
			g.setAlpha(255);
			
			int modY = state==STATE_END?modPosY*-1:modPosY;
			
			g.drawImage(GfxManager.imgBigTroop.get(index), 
					troopX, 
					troopY + modY, 
					Graphics.VCENTER | Graphics.HCENTER);
			
			g.drawImage(GfxManager.imgTextBox, 
					textX, 
					textY + modY, 
					Graphics.VCENTER | Graphics.HCENTER);
			
			TextManager.drawSimpleText(g, Font.FONT_MEDIUM, nameTroopList.get(index), 
					textX, 
					textY + modY - GfxManager.imgTextBox.getHeight()/8,
					Graphics.VCENTER | Graphics.HCENTER);
			TextManager.draw(g, Font.FONT_SMALL, descTroopList.get(index),
					textX, 
					textY + modY + GfxManager.imgTextBox.getHeight()/8, 
					GfxManager.imgTextBox.getWidth()-GfxManager.imgTextBox.getWidth()/4, 
					TextManager.ALING_CENTER, -1);
			
			String cost = ""+GameParams.TROOP_COST[index];
			
			g.drawImage(GfxManager.imgCoin, 
					troopX + GfxManager.imgBigTroop.get(index).getWidth()/2 - (Font.getFontWidth(Font.FONT_BIG)*cost.length())/2-
					Font.getFontWidth(Font.FONT_BIG)/2-GfxManager.imgCoin.getWidth(), 
					troopY - GfxManager.imgBigTroop.get(index).getHeight()/2 + GfxManager.imgCoin.getHeight()/2+
					Font.getFontWidth(Font.FONT_BIG)/2 + modY,
					Graphics.VCENTER | Graphics.HCENTER);
			TextManager.drawSimpleText(g, Font.FONT_BIG, cost, 
					troopX + GfxManager.imgBigTroop.get(index).getWidth()/2 - (Font.getFontWidth(Font.FONT_BIG)*cost.length())/2-
					Font.getFontWidth(Font.FONT_BIG)/2, 
					troopY - GfxManager.imgBigTroop.get(index).getHeight()/2 + GfxManager.imgCoin.getHeight()/2+
					Font.getFontWidth(Font.FONT_BIG)/2 + modY,
					Graphics.VCENTER | Graphics.HCENTER);
			buttonLeft.draw(g, 0, modY);
			buttonRight.draw(g, 0, modY);
			buttonBuy.draw(g, 0, modY);
			buttonCancel.draw(g, 0, modY);
		}
	}
	
	public void onBuy(){}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	
	
	

}