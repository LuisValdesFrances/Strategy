package com.luis.strategy.gui;

import java.util.List;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.gameworld.WorldConver;
import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.gui.MenuBox;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.strategy.GfxManager;
import com.luis.strategy.constants.Define;
import com.luis.strategy.map.Player;

public class MapBox extends MenuBox{
	
	private List<Player>playerList;
	private Image imgMap;
	
	private int numberPartsW;
	private int numberPartsH;

	public MapBox(WorldConver worldConver, int numberPartsW, int numberPartsH) {
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
						-1){
					@Override
					public void  onButtonPressUp(){
						cancel();
					}
		});
		
		this.numberPartsW = numberPartsW;
		this.numberPartsH = numberPartsH;
		this.imgMap = new Image((int)worldConver.getWorldWidth(), (int)worldConver.getWorldHeight());
	}
	
	public void start(List<Player>playerList){
		super.start();
		this.playerList = playerList;
	}
	
	public void draw(Graphics g){
		super.draw(g, GfxManager.imgBlackBG);
		if(state != STATE_UNACTIVE){
			Graphics _g = imgMap.getGraphics();
			_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			int pW = GfxManager.imgMapList.get(0).getWidth();
			int pH = GfxManager.imgMapList.get(0).getHeight();
			
			int i = 0;
			for(int y = 0; y < numberPartsH; y++){
				for(int x = 0; x < numberPartsW; x++){
					_g.drawImage(GfxManager.imgMapList.get(i),x*pW,y*pH,Graphics.TOP | Graphics.LEFT);
				i++;
				}
			}
			
			float mapAverage = (imgMap.getWidth() + imgMap.getHeight()) / 2f;
			float boxAverage = (GfxManager.imgMediumBox.getWidth() + GfxManager.imgMediumBox.getHeight()) / 2f;
			
			//Relativizo la escala al tamaño de mapa (escalado cuando mas grande)
			float scale = boxAverage / mapAverage;
			
			g.setImageSize(scale, scale);
			g.drawImage(imgMap, 
					getX()+(int)modPosX,// + (int)(imgMap.getWidth()-(imgMap.getWidth()*scale))/2, 
					getY()+(int)modPosY,// + (int)(imgMap.getHeight()-(imgMap.getHeight()*scale))/2, 
					Graphics.VCENTER | Graphics.HCENTER);
			g.setImageSize(1f, 1f);
			
			
		
		}
	
	}

}
