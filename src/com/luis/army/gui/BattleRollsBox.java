package com.luis.army.gui;

import java.util.List;

import android.util.Log;

import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.gui.MenuElement;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;
import com.luis.strategy.GfxManager;
import com.luis.strategy.army.Army;
import com.luis.strategy.constants.Define;
import com.luis.strategy.constants.GameParams;

public class BattleRollsBox {
	
	private Army armyAtack;
	private Army armyDefense;
	
	private int state;
	public static final int STATE_UNACTIVE = 0;
	public static final int STATE_START = 1;
	public static final int STATE_SHOW_1 = 2;
	public static final int STATE_SHOW_2 = 3;
	public static final int STATE_SHOW_3 = 4;
	public static final int STATE_END = 5;
	
	private Button buttonCombat;
	
	private int shieldX;
	private int shieldY;
	private int parchmentX;
	private int parchmentY;
	private int shieldIconX;
	private int[] shieldIconY;
	
	private int modPosY;

	public BattleRollsBox() {
		
		int shieldSep = GfxManager.imgShieldIcon.getWidth()/2;
		int parchmentSep = -GfxManager.imgNotificationBox.getHeight()/3;
		int totalWidth = GfxManager.imgNotificationBox.getWidth() 
				+ GfxManager.imgShieldIcon.getWidth() + shieldSep;
		int totalHeight = GfxManager.imgShield.getHeight() + 
				GfxManager.imgNotificationBox.getHeight() + parchmentSep;
		
		shieldX = Define.SIZEX2-totalWidth/2+GfxManager.imgNotificationBox.getWidth()/2;
		shieldY = (Define.SIZEY2-GfxManager.imgGameHud.getHeight()/2) - totalHeight/2 + GfxManager.imgShield.getHeight()/2;
		parchmentX = shieldX;
		parchmentY = shieldY +GfxManager.imgShield.getHeight()/2 + parchmentSep + GfxManager.imgNotificationBox.getHeight()/2;
		
		shieldIconY = new int[4];
		
		int iconSep = (totalHeight - GfxManager.imgShieldIcon.getHeight()*4)/5;
		shieldIconX = parchmentX + GfxManager.imgNotificationBox.getWidth()/2 + shieldSep + GfxManager.imgShieldIcon.getWidth()/2;
		for(int i = 0; i < shieldIconY.length; i++){
			shieldIconY[i] = iconSep*(i+1) + GfxManager.imgShieldIcon.getHeight()*i + GfxManager.imgShieldIcon.getHeight()/2;
		}
		
		buttonCombat = new Button(
				GfxManager.imgButtonCombatRelease, GfxManager.imgButtonCombatFocus, 
				shieldIconX, 
				iconSep*shieldIconY.length + GfxManager.imgShieldIcon.getHeight()*(shieldIconY.length-1) + GfxManager.imgShieldIcon.getHeight()/2 , 
				null, -1){
			public void onButtonPressUp() {
				state++;
				onCombat();
				buttonCombat.reset();
				Log.i("debug", "state " + state);
			};
		};
	}
	
	public void start(Army armyAtack, Army armyDefense){
		this.armyAtack = armyAtack;
		this.armyDefense = armyDefense;
		this.state = STATE_START;
		this.modPosY = -Define.SIZEY;
	}
	
	public boolean update(MultiTouchHandler touchHandler, float delta){
		if(state != STATE_UNACTIVE){
			switch(state){
			case STATE_START:
				modPosY -= (modPosY*8f)*delta - 1f;
				if(modPosY >= 0){
					modPosY = 0;
					state = STATE_SHOW_1;
				}
				break;
			case STATE_SHOW_1:
				buttonCombat.update(touchHandler);
				break;
			case STATE_SHOW_2:
				buttonCombat.update(touchHandler);
				break;
			case STATE_SHOW_3:
				buttonCombat.update(touchHandler);
				break;
			case STATE_END:
				modPosY += (modPosY*16f)*delta + 1f;
				if(modPosY >= Define.SIZEY){
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
			
			g.drawImage(GfxManager.imgNotificationBox, 
					parchmentX, 
					parchmentY + modY, 
					Graphics.VCENTER | Graphics.HCENTER);
			
			g.drawImage(GfxManager.imgShield, 
					shieldX, 
					shieldY + modY, 
					Graphics.VCENTER | Graphics.HCENTER);
			
			for(int i = 0;i < shieldIconY.length-1; i++){
				g.drawImage(GfxManager.imgShieldIcon, 
						shieldIconX, 
						shieldIconY[i] + modY, 
						Graphics.VCENTER | Graphics.HCENTER);
			}
			
			buttonCombat.draw(g, 0, modY);
		}
	}
	
	
	public void onCombat(){}
	
	public void onResult(){}
	
	
	

}
