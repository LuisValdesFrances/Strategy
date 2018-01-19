package com.luis.army.gui;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.gui.MenuElement;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;
import com.luis.strategy.GfxManager;
import com.luis.strategy.constants.Define;

public class BuyBox {
	
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
	
	private List<String>descTroopList;
	
	private int modPosX;
	private int modPosY;
	
	public BuyBox(){
		
		
		int imageW = 
				GfxManager.imgBigTroop.get(0).getWidth() + 
				GfxManager.imgButtonCancelRelease.getWidth()/2 + 
				GfxManager.imgButtonCrossBigRelease.getWidth()/2 -GfxManager.imgButtonCrossBigRelease.getWidth()/4;
		int boxW =	GfxManager.imgTextBox.getWidth();
		
		troopX = Define.SIZEX2- (boxW-imageW)/2 - imageW / 2;
		textX = Define.SIZEX2- (boxW-imageW)/2 + boxW / 2;
		
		troopY = Define.SIZEY2;
		textY = Define.SIZEY2;
		
		
		descTroopList = new ArrayList<String>();
		
		for(int i = 0; i < GfxManager.imgBigTroop.size(); i++){
			descTroopList.add("DESCRIPTION TEXT LARGE EXPLAIN COMBAT HABILITIES AND SKILLS FOR TROOP WITH INDEX " + i);
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
				Log.i("Debug", "Comprado: " + index);
				onBuy();
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
			};
		};
	}
	
	public void start(){
		state = STATE_START;
		modPosY = -Define.SIZEY2-GfxManager.imgBigTroop.get(0).getHeight()/2;
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
			
			int modAlpha = (int) ((Math.abs(modPosY) * (MenuElement.bgAlpha*2)) / 
					(Define.SIZEY2+GfxManager.imgBigTroop.get(0).getHeight()/2));
			g.setAlpha(MenuElement.bgAlpha*2-modAlpha);
			g.drawImage(MenuElement.imgBG, Define.SIZEX2, Define.SIZEY2, Graphics.VCENTER | Graphics.HCENTER);
			g.setAlpha(255);
			
			
			
			int modY = state==STATE_END?modPosY*-1:modPosY;
			
			g.drawImage(GfxManager.imgBigTroop.get(index), 
					troopX + modPosX, 
					troopY + modY, 
					Graphics.VCENTER | Graphics.HCENTER);
			
			g.drawImage(GfxManager.imgTextBox, 
					textX + modPosX, 
					textY + modY, 
					Graphics.VCENTER | Graphics.HCENTER);
			
			TextManager.draw(g, Font.FONT_SMALL, descTroopList.get(index), 
					textX + modPosX, 
					textY + modY, 
					GfxManager.imgTextBox.getWidth()-GfxManager.imgTextBox.getWidth()/6, 
					TextManager.ALING_CENTER, -1);
			
			buttonLeft.draw(g, 0, 0);
			buttonRight.draw(g, 0, 0);
			buttonBuy.draw(g, 0, modY);
			buttonCancel.draw(g, 0, modY);
		}
	}
	
	public void onBuy(){}

}
