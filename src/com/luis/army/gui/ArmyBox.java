package com.luis.army.gui;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.menu.Button;
import com.luis.lgameengine.menu.MenuBox;
import com.luis.strategy.GfxManager;
import com.luis.strategy.army.Army;
import com.luis.strategy.constants.Define;


public class ArmyBox extends MenuBox{
	
	private Army army;
	private boolean isCurrentPlayer;
	
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
	
	public void start(Army army, boolean isCurrentPlayer){
		this.army = army;
		this.isCurrentPlayer = isCurrentPlayer;
		start();
	}
	
	@Override
	public void draw(Graphics g){
		super.draw(g);
		if(state != STATE_UNACTIVE){
			int imageW = GfxManager.imgSmallTroop.get(0).getWidth();
			int imageH = GfxManager.imgSmallTroop.get(0).getHeight();
			int marginW = imageW/10;
			int marginH = imageH/10;
			
			int totalColums = GfxManager.imgBigBox.getWidth() / (imageW+marginW);
			int totalFiles = GfxManager.imgBigBox.getHeight() / (imageH+marginH);
			
			int fileWidth = imageW * totalColums + (marginW * (totalColums-1));
			int columnHeight = imageH * totalFiles + (marginH * (totalFiles-1));
			
			int initX = getX() - fileWidth/2 + imageW/2;
			int initY = getY() - columnHeight/2 + imageH/2;
			
			int countColumns = 0;
			int countFiles = 0;
			g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			for(int i = 0; i < army.getTroopList().size(); i++){
				if(countColumns == totalColums){
					countColumns=0;
					countFiles++;
				}
				
				int pX = initX + (imageW*countColumns + marginW*countColumns)+(int)modPosX;
				int pY = initY + (imageH*countFiles + marginH*countFiles);
				
				countColumns++;
				g.drawImage(GfxManager.imgSmallTroop.get(army.getTroopList().get(i).getType()), pX, pY, Graphics.VCENTER | Graphics.HCENTER);
				
				
				
				
				
			}
			
		}
	}

}
