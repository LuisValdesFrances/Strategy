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
import com.luis.strategy.army.Troop;
import com.luis.strategy.constants.Define;
import com.luis.strategy.constants.GameParams;


public class ArmyBox extends MenuBox{
	
	private ArmyBuyBox armyBuyBox;
	
	private Army army;
	private boolean isCurrentPlayer;
	
	private List<Button>deleteButtonList;
	private Button crossButton;
	
	private boolean discardMode;
	
	private boolean enableCrossButton;
	
	public ArmyBox() {
		super(Define.SIZEX, Define.SIZEY, GfxManager.imgBigBox, null, null,
				Define.SIZEX2, Define.SIZEY2-GfxManager.imgGameHud.getHeight()/2,
				null,
				null, Font.FONT_MEDIUM, Font.FONT_SMALL);
		
		btnList.add(new Button(
				GfxManager.imgButtonCancelRelease,
				GfxManager.imgButtonCancelFocus,
				getX() - GfxManager.imgBigBox.getWidth()/2, 
				getY() - GfxManager.imgBigBox.getHeight()/2, 
				null, 
				-1){});
		
		armyBuyBox = new ArmyBuyBox(){
			@Override
			public void onBuy() {
				if(army.getPlayer().getGold() >= GameParams.TROOP_COST[this.getIndex()]){
					army.getPlayer().setGold(army.getPlayer().getGold()-GameParams.TROOP_COST[this.getIndex()]);
					army.getTroopList().add(new Troop(this.getIndex(), false));
					updateTroops();
				}
			}
		};
	}
	
	private int fileWidth;
	private int columnHeight;
	private int totalColums;
	private int totalFiles;
	private int marginW;
	private int marginH;
	
	public void start(Army a, boolean isCurrentPlayer, boolean discardMode){
		super.start();
		this.army = a;
		this.isCurrentPlayer = isCurrentPlayer;
		this.discardMode = discardMode;
		
		int imageW = GfxManager.imgSmallTroop.get(0).getWidth();
		int imageH = GfxManager.imgSmallTroop.get(0).getHeight();
		marginW = imageW/10;
		marginH = imageH/10;
		
		totalColums = GfxManager.imgBigBox.getWidth() / (imageW+marginW);
		
		totalFiles = GfxManager.imgBigBox.getHeight() / (imageH+marginH);
		fileWidth = imageW * totalColums + (marginW * (totalColums-1));
		columnHeight = imageH * totalFiles + (marginH * (totalFiles-1));
		
		updateTroops();
		
		if(isCurrentPlayer){
			crossButton = new Button(
					GfxManager.imgButtonCrossBigRelease, 
					GfxManager.imgButtonCrossBigFocus, 
					getX()+GfxManager.imgBigBox.getWidth()/2-GfxManager.imgButtonCrossBigRelease.getWidth()/4, 
					getY()+GfxManager.imgBigBox.getHeight()/2-GfxManager.imgButtonCrossBigRelease.getHeight()/4, 
					null, -1){
				@Override
				public void onButtonPressUp() {
					reset();
					armyBuyBox.start(army);
				}
			};
			
			boolean isInCity = 
					(army.getKingdom().hasTerrain(GameParams.SMALL_CITY)) ||
					(army.getKingdom().hasTerrain(GameParams.MEDIUM_CITY)) ||
					(army.getKingdom().hasTerrain(GameParams.BIG_CITY)) ||
					(army.getKingdom().hasTerrain(GameParams.CASTLE));
			
			enableCrossButton = !discardMode && isInCity && army.getPlayer().hasKingom(army.getKingdom());
			crossButton.setDisabled(!enableCrossButton);
		}else{
			crossButton = null;
		}
		
	}
	
	public void updateTroops(){
		
		int initX = getX() - fileWidth/2 + GfxManager.imgSmallTroop.get(0).getWidth()/2;
		int initY = getY() - columnHeight/2 + GfxManager.imgSmallTroop.get(0).getHeight()/2;
		
		int countColumns = 0;
		int countFiles = 0;
		
		deleteButtonList = new ArrayList<Button>();
		for(int i = 0; i < army.getTroopList().size(); i++){
				
			if(countColumns == totalColums){
				countColumns=0;
				countFiles++;
			}
				
			if(isCurrentPlayer){
				Button discard = null;
				if(!army.getTroopList().get(i).isSubject()){
					discard = new Button(
						GfxManager.imgButtonDeleteRelease,
						GfxManager.imgButtonDeleteFocus,
							
						initX + 
						(GfxManager.imgSmallTroop.get(0).getWidth()*countColumns + marginW*countColumns)-
						GfxManager.imgSmallTroop.get(0).getWidth()/2+GfxManager.imgButtonDeleteRelease.getWidth()/2
						-GfxManager.imgButtonDeleteRelease.getWidth()/4,
							
						initY + 
						(GfxManager.imgSmallTroop.get(0).getHeight()*countFiles + marginH*countFiles) -
						GfxManager.imgSmallTroop.get(0).getHeight()/2+GfxManager.imgButtonDeleteRelease.getHeight()/2
						-GfxManager.imgButtonDeleteRelease.getHeight()/4,
							
						null, 0){
						
						@Override
						public void onButtonPressDown(){}
							
						@Override
						public void onButtonPressUp(){
							reset();
						}
					};
				}
				deleteButtonList.add(discard);
				countColumns++;
			}
		}
	}
	
	@Override
	public boolean update(MultiTouchHandler touchHandler, float delta){
		if(!armyBuyBox.update(touchHandler, delta)){
			if(isCurrentPlayer && state == STATE_ACTIVE){
				for(int i = 0; i < army.getTroopList().size(); i++){
					if(!army.getTroopList().get(i).isSubject()){
						if(deleteButtonList.get(i).update(touchHandler)){
							Log.i("Debug", "Descartado tropa: " + army.getTroopList().get(i).getId() + " - " + army.getTroopList().get(i).getType());
							army.getPlayer().setGold(army.getPlayer().getGold() + 
									(discardMode?
											GameParams.TROOP_COST[army.getTroopList().get(i).getType()]:
											GameParams.TROOP_COST[army.getTroopList().get(i).getType()]/2));
							army.getTroopList().remove(army.getTroopList().get(i));
							check();
							break;
						}
					}
				}
				
				if(crossButton != null & enableCrossButton)
					crossButton.update(touchHandler);
			}
			return super.update(touchHandler, delta);
			}
		return true;
	}
	
	@Override
	public void draw(Graphics g, boolean drawBG){
		super.draw(g, drawBG);
		if(state != STATE_UNACTIVE){
			
			int countColumns = 0;
			int countFiles = 0;
			int initX = getX() - fileWidth/2 + GfxManager.imgSmallTroop.get(0).getWidth()/2;
			int initY = getY() - columnHeight/2 + GfxManager.imgSmallTroop.get(0).getHeight()/2;
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
				if(army.getTroopList().get(i).isSubject())
					g.setAlpha(120);
				
				g.drawImage(GfxManager.imgSmallTroop.get(army.getTroopList().get(i).getType()), pX, pY, 
						Graphics.VCENTER | Graphics.HCENTER);
				
				g.setAlpha(255);
				
			}
			
			for(int i = 0; i < army.getTroopList().size(); i++){
				if(isCurrentPlayer && !army.getTroopList().get(i).isSubject()){
					deleteButtonList.get(i).draw(g, (int)modPosX, 0);
				}
			}
			
			if(crossButton != null)
				crossButton.draw(g, (int)modPosX, 0);
			
			armyBuyBox.draw(g);
		}
	}
	
	public void check(){}
	
	

}
