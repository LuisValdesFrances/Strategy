package com.luis.army.gui;

import java.util.ArrayList;
import java.util.List;



import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.gui.ListBox;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;
import com.luis.strategy.GfxManager;
import com.luis.strategy.RscManager;
import com.luis.strategy.GameState.PlayerConf;
import com.luis.strategy.constants.Define;

public class ConfigMapBox extends ListBox{
	

	private List<String> playerList;
	private List<Button> playerFlagBtnList;
	private List<Boolean> flagAvailableList;
	
	private PlayerConf[] playerConfList;
	
	public ConfigMapBox(PlayerConf[] pcList){
		super(Define.SIZEX, Define.SIZEY, null,
			GfxManager.imgButtonMenuMediumRelease, GfxManager.imgButtonMenuMediumFocus,
			Define.SIZEX2, Define.SIZEY2, 
			RscManager.allText[RscManager.TXT_CONFIG_MAP],
			new String[pcList.length], 
			Font.FONT_BIG, Font.FONT_MEDIUM);
		
		this.playerConfList = pcList;
		playerList = new ArrayList<String>();
		playerFlagBtnList = new ArrayList<Button>();
		flagAvailableList = new ArrayList<Boolean>();
		
		
		for(int i = 0; i < btnList.size(); i++){
			String playerName = "Player " + (i+1);
			playerList.add(playerName);
			
			playerFlagBtnList.add(
					new Button(
				GfxManager.imgButtonMenuSmallRelease, GfxManager.imgButtonMenuSmallFocus, 
				Define.SIZEX - Define.SIZEX32 - GfxManager.imgButtonMenuMediumRelease.getWidth()/2,
				btnList.get(i).getY(), 
				null, -1));
			
			//ojo
			flagAvailableList.add(true);
		}
	}
	
	@Override
	public boolean update(MultiTouchHandler touchHandler, float delta){
		
		
		if(state == STATE_ACTIVE){
			for(int i = 0; i < btnList.size(); i++){
				if(btnList.get(i).update(touchHandler)){
					playerConfList[i].IA = !playerConfList[i].IA;
					btnList.get(i).reset();
					break;
				}
			}
			
			for(int i = 0; i < playerFlagBtnList.size(); i++){
				if(playerFlagBtnList.get(i).update(touchHandler)){
					playerConfList[i].flag = (playerConfList[i].flag + 1)%(GfxManager.imgFlagList.size()-1);
					playerFlagBtnList.get(i).reset();
					break;
				}
			}
		}
		if(scroll){
			for(int i = 0; i < initOptionY.length; i++){
				playerFlagBtnList.get(i).setY(initOptionY[i] + (int)modPosY);
			}
		}
		
		return super.update(touchHandler, delta);
	}
	
	@Override
	public void draw(Graphics g, boolean drawBG){
		super.draw(g, drawBG);
		
		for(int i = 0; i < btnList.size(); i++){
			TextManager.drawSimpleText(g, Font.FONT_BIG, 
					playerList.get(i), 
					Define.SIZEX32 + 
					(Font.getFontWidth(Font.FONT_BIG)*playerList.get(i).length())/2
					+ (int)modPosX, 
					btnList.get(i).getY(), 
					Graphics.VCENTER | Graphics.HCENTER);
			
			TextManager.drawSimpleText(g, Font.FONT_MEDIUM, 
					!playerConfList[i].IA ? RscManager.allText[RscManager.TXT_MAP_CONFIG_HUMAN]:RscManager.allText[RscManager.TXT_MAP_CONFIG_IA], 
					btnList.get(i).getX()+ (int)modPosX, btnList.get(i).getY(), 
					Graphics.VCENTER | Graphics.HCENTER);
			
			//playerFlagBtnList.get(i).draw(g, (int)modPosX, 0);
			g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			g.setImageSize(0.9f, 0.85f);
			g.drawImage(
					GfxManager.imgFlagList.get(playerConfList[i].flag), 
					playerFlagBtnList.get(i).getX()+ (int)modPosX, 
					playerFlagBtnList.get(i).getY(), Graphics.VCENTER | Graphics.HCENTER);
			g.setImageSize(1f, 1f);
		}
	}
	

	
}
