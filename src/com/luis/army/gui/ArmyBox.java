package com.luis.army.gui;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;
import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.gui.MenuBox;
import com.luis.strategy.GfxManager;
import com.luis.strategy.army.Army;
import com.luis.strategy.constants.Define;


public class ArmyBox extends MenuBox{
	
	private Army army;
	private boolean isCurrentPlayer;
	
	private List<Button>discardButtonList;
	
	public ArmyBox() {
		super(Define.SIZEX, Define.SIZEY, GfxManager.imgBigBox, null, null, 
				"ARMY",
				null, Font.FONT_MEDIUM, Font.FONT_SMALL);
		
		btnList.add(new Button(
				GfxManager.imgButtonOkRelease,
				GfxManager.imgButtonOkFocus,
				screenWidth/2, 
				screenHeight/2 + GfxManager.imgBigBox.getHeight()/2, 
				null, 
				-1){});
	}
	
	private int fileWidth;
	private int columnHeight;
	private int totalColums;
	private int totalFiles;
	private int marginW;
	private int marginH;
	
	public void start(Army a, boolean isCurrentPlayer){
		super.start();
		this.army = a;
		this.isCurrentPlayer = isCurrentPlayer;
		
		int imageW = GfxManager.imgSmallTroop.get(0).getWidth();
		int imageH = GfxManager.imgSmallTroop.get(0).getHeight();
		marginW = imageW/10;
		marginH = imageH/10;
		
		totalColums = GfxManager.imgBigBox.getWidth() / (imageW+marginW);
		
		totalFiles = GfxManager.imgBigBox.getHeight() / (imageH+marginH);
		fileWidth = imageW * totalColums + (marginW * (totalColums-1));
		columnHeight = imageH * totalFiles + (marginH * (totalFiles-1));
		
		discardButtonList = new ArrayList<Button>();
		int initX = x - fileWidth/2 + imageW/2;
		int initY = y - columnHeight/2 + imageH/2;
		
		int countColumns = 0;
		int countFiles = 0;
		
		for(int i = 0; i < army.getTroopList().size(); i++){
				
			if(countColumns == totalColums){
				countColumns=0;
				countFiles++;
			}
				
			if(isCurrentPlayer){
				Button discard = new Button(
					GfxManager.imgButtonCancelRelease, 
					GfxManager.imgButtonCancelFocus,
						
					initX + 
					(GfxManager.imgSmallTroop.get(0).getWidth()*countColumns + marginW*countColumns)-
					GfxManager.imgSmallTroop.get(0).getWidth()/2+GfxManager.imgButtonCancelRelease.getWidth()/2
					-GfxManager.imgButtonCancelRelease.getWidth()/4,
						
					initY + 
					(GfxManager.imgSmallTroop.get(0).getHeight()*countFiles + marginH*countFiles) -
					GfxManager.imgSmallTroop.get(0).getHeight()/2+GfxManager.imgButtonCancelRelease.getHeight()/2
					-GfxManager.imgButtonCancelRelease.getHeight()/4,
						
					null, 0){
					
				@Override
				public void onButtonPressDown(){}
					
				@Override
				public void onButtonPressUp(){
					reset();
				}
				};
				discardButtonList.add(discard);
				countColumns++;
			}
		}
	}
	
	@Override
	public boolean update(MultiTouchHandler touchHandler, float delta){
		if(isCurrentPlayer && state == STATE_ACTIVE){
			for(int i = 0; i < army.getTroopList().size(); i++){
				if(discardButtonList.get(i).update(touchHandler)){
					Log.i("Debug", "Descartado tropa: " + army.getTroopList().get(i).getId() + " - " + army.getTroopList().get(i).getType());
					army.getTroopList().remove(army.getTroopList().get(i));
					check();
					break;
				}
			}
		}
		return super.update(touchHandler, delta);
	}
	
	@Override
	public void draw(Graphics g, boolean drawBG){
		super.draw(g, drawBG);
		if(state != STATE_UNACTIVE){
			
			int countColumns = 0;
			int countFiles = 0;
			int initX = x - fileWidth/2 + GfxManager.imgSmallTroop.get(0).getWidth()/2;
			int initY = y - columnHeight/2 + GfxManager.imgSmallTroop.get(0).getHeight()/2;
			g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			for(int i = 0; i < army.getTroopList().size(); i++){
				
				if(countColumns == totalColums){
					countColumns=0;
					countFiles++;
				}
				
				int pX = initX + 
						(GfxManager.imgSmallTroop.get(0).getWidth()*countColumns + marginW*countColumns)+(int)modPosX;
				int pY = initY + 
						(GfxManager.imgSmallTroop.get(0).getHeight()*countFiles + marginH*countFiles);
				
				countColumns++;
				g.drawImage(GfxManager.imgSmallTroop.get(army.getTroopList().get(i).getType()), pX, pY, Graphics.VCENTER | Graphics.HCENTER);
				
			}
			
			for(int i = 0; i < army.getTroopList().size(); i++){
				if(isCurrentPlayer){
					discardButtonList.get(i).draw(g, (int)modPosX, 0);
				}
			}
		}
	}
	
	public void check(){};

}
