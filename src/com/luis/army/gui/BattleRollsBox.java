package com.luis.army.gui;

import java.util.List;

import android.util.Log;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.gui.MenuElement;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;
import com.luis.strategy.GfxManager;
import com.luis.strategy.Main;
import com.luis.strategy.army.Army;
import com.luis.strategy.constants.Define;
import com.luis.strategy.constants.GameParams;
import com.luis.strategy.map.Terrain;

public class BattleRollsBox {
	
	private Terrain terrain;
	private Army armyAtack;
	private Army armyDefense;
	
	private int state;
	private int stateCombat = 0;
	public static final int STATE_UNACTIVE = 0;
	public static final int STATE_START = 1;
	public static final int STATE_COMBAT_1 = 2;
	public static final int STATE_COMBAT_2 = 3;
	public static final int STATE_COMBAT_3 = 4;
	public static final int STATE_END = 5;
	
	
	private Button buttonCombat;
	
	private int shieldX;
	private int shieldY;
	private int parchmentX;
	private int parchmentY;
	private int shieldIconX;
	private int[] shieldIconY;
	
	private int modPosY;
	
	private int modPosRoll;
	
	private int rollValue;
	private int rollDifficult;
	private boolean[] result;

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
				if(state >= STATE_COMBAT_1 && state <= STATE_COMBAT_3){
					modPosRoll = -Define.SIZEX;
					rollValue = Main.getRandom(1, GameParams.ROLL_SYSTEM);
					result[stateCombat] = rollValue >= rollDifficult;
					stateCombat++;
				}
				Log.i("debug", "rollValue: " + rollValue);
				for(int i = 0; i < result.length; i++){
					Log.i("Debug", "result " + i + " " + result[i]);
				}
			};
		};
		
		result = new boolean[3];
	}
	
	public void start(Terrain terrain, Army armyAtack, Army armyDefense){
		this.terrain = terrain;
		this.armyAtack = armyAtack;
		this.armyDefense = armyDefense;
		this.state = STATE_START;
		this.modPosY = -Define.SIZEY;
		this.modPosRoll = -Define.SIZEX;
		this.rollDifficult = calculateDifficult();
		this.stateCombat = 0;
		
		this.rollValue = Main.getRandom(1, GameParams.ROLL_SYSTEM);
		result[stateCombat] = rollValue >= rollDifficult;
		stateCombat++;
		
		Log.i("debug", "rollValue: " + rollValue);
		for(int i = 0; i < result.length; i++){
			Log.i("Debug", "result " + i + " " + result[i]);
		}
	}
	
	public boolean update(MultiTouchHandler touchHandler, float delta){
		if(state != STATE_UNACTIVE){
			switch(state){
			case STATE_START:
				modPosY -= (modPosY*8f)*delta - 1f;
				if(modPosY >= 0){
					modPosY = 0;
					state = STATE_COMBAT_1;
				}
				break;
			case STATE_COMBAT_1:
			case STATE_COMBAT_2:
			case STATE_COMBAT_3:
				if(modPosRoll < 0){
					modPosRoll -= (modPosRoll*8f)*delta - 1f;
				}else{
					buttonCombat.update(touchHandler);
				}
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
			
			TextManager.drawSimpleText(g, Font.FONT_BIG, "DIFFICULT: "+ rollDifficult, 
					parchmentX, parchmentY + modY, Graphics.VCENTER | Graphics.HCENTER);
			g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			
			g.drawImage(GfxManager.imgShield, 
					shieldX, 
					shieldY + modY, 
					Graphics.VCENTER | Graphics.HCENTER);
			
			g.drawImage(GfxManager.imgRollList.get(rollValue-1), 
					shieldX + modPosRoll, 
					shieldY + modY, 
					Graphics.VCENTER | Graphics.HCENTER);
			
			for(int i = 0;i < shieldIconY.length-1; i++){
				g.drawImage(GfxManager.imgShieldIcon, 
						shieldIconX, 
						shieldIconY[i] + modY, 
						Graphics.VCENTER | Graphics.HCENTER);
				
				if(state >= STATE_COMBAT_1){
					if(stateCombat > i){
						g.drawImage(
							result[i]?GfxManager.imgOkIcon:GfxManager.imgCrossIcon, 
							shieldIconX, 
							shieldIconY[i] + modY, 
							Graphics.VCENTER | Graphics.HCENTER);
					}
				}
			}
			
			buttonCombat.draw(g, 0, modY);
		}
	}
	
	
	public void onCombat(){}
	
	public void onResult(){}
	
	public int calculateDifficult(){
		int value=0;
		
		int pAtack = armyAtack.getPower(terrain);
		int pDefense = armyDefense != null ? armyDefense.getPower(terrain):GameParams.TERRAIN_DEFENSE[terrain.getType()];
		
		
		value = GameParams.ROLL_SYSTEM-((armyAtack.getPower(terrain) * GameParams.ROLL_SYSTEM)/(pAtack+pDefense));
		
		return value;
	}
	
	
	

}
