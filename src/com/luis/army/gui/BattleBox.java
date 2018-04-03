package com.luis.army.gui;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;
import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.gui.MenuBox;
import com.luis.strategy.GfxManager;
import com.luis.strategy.Main;
import com.luis.strategy.army.Army;
import com.luis.strategy.constants.Define;
import com.luis.strategy.constants.GameParams;
import com.luis.strategy.map.Terrain;

public class BattleBox extends MenuBox{
	
	private BattleDiceBox battleDiceBox;
	
	private Army armyAtack;
	private Army armyDefense;
	
	private Terrain terrain;
	
	private int troopY;
	private int centerY;
	private int separation;
	
	private Button cancelButton;
	
	private boolean scape;
	
	public BattleBox(){
		
		super(Define.SIZEX, Define.SIZEY, GfxManager.imgBigBox, null, null,
				Define.SIZEX2, Define.SIZEY2-GfxManager.imgGameHud.getHeight()/2,
				"Balance of power",
				null, Font.FONT_MEDIUM, Font.FONT_SMALL);
		
		
		btnList.add(new Button(
				GfxManager.imgButtonCombatRelease,
				GfxManager.imgButtonCombatFocus,
				getX() + GfxManager.imgBigBox.getWidth()/2, 
				getY() + GfxManager.imgBigBox.getHeight()/2, 
				null, 
				-1){
			@Override
			public void onButtonPressUp(){
				reset();
				battleDiceBox.start(terrain, armyAtack, armyDefense);
			}
		});
		
		separation = GfxManager.imgIconTroop.get(0).getHeight()/4;
		
		int totalHeight = 
				GfxManager.imgIconTroop.get(0).getHeight()*GfxManager.imgIconTroop.size() +
				(separation*(GfxManager.imgIconTroop.size()-1));
		troopY = getY() - totalHeight/2;
		
		totalHeight = 
				Font.getFontHeight(Font.FONT_SMALL) +
				GfxManager.imgFlagBigList.get(0).getHeight() +
				GfxManager.imgTerrainBox.get(0).getHeight() +
				separation*2 +
				Font.getFontHeight(Font.FONT_MEDIUM);
		centerY = getY() - totalHeight/2;
		
		battleDiceBox = new BattleDiceBox(){
			@Override
			public void onResult() {
				
			}
		};
	}
	
	public void start(Terrain terrain, Army armyAtack, Army armyDefense, boolean isIA, boolean scape){
		super.start();
		this.terrain = terrain;
		this.armyAtack = armyAtack;
		this.armyDefense = armyDefense;
		this.scape = scape;
		
		if((armyDefense == null && !isIA) || scape){
			cancelButton =  new Button(
					GfxManager.imgButtonCancelRelease,
					GfxManager.imgButtonCancelFocus,
					getX() - GfxManager.imgBigBox.getWidth()/2, 
					getY() + GfxManager.imgBigBox.getHeight()/2, 
					null, 
					-1){
				@Override
				public void onButtonPressUp(){
					indexPressed = 1;//Al controlador de juego le interesa saber que he cancelado el combate
					reset();
					cancel();
				}
			};
		}else{
			cancelButton = null;
		}
		
		start();
	}
	
	@Override
	public boolean update(MultiTouchHandler touchHandler, float delta){
		if(!battleDiceBox.update(touchHandler, delta)){
			
			if(cancelButton != null)
				cancelButton.update(touchHandler);
			
			return super.update(touchHandler, delta);
		}
		return true;
	}
	
	@Override
	public void draw(Graphics g, boolean drawBG){
		super.draw(g, drawBG);
		if(state != STATE_UNACTIVE){
			for(int i = 0; i < GfxManager.imgIconTroop.size(); i++){
				g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
				
				if(cancelButton != null)
					cancelButton.draw(g, (int)modPosX, 0);
				
				//Left
				g.drawImage(
						GfxManager.imgIconTroop.get(i),
						getX()-GfxManager.imgBigBox.getWidth()/2 +
						separation*2 + GfxManager.imgIconTroop.get(i).getWidth()/2 +
						(int)modPosX, 
						troopY + GfxManager.imgIconTroop.get(i).getHeight()/2 +
						GfxManager.imgIconTroop.get(i).getHeight()*i + 
						separation * i, 
						Graphics.VCENTER | Graphics.HCENTER);
				
				String text = "X" + armyAtack.getNumberTroops(i);
				TextManager.drawSimpleText(g, 
						Font.FONT_MEDIUM,
						text,
						getX()-GfxManager.imgBigBox.getWidth()/2 +
						separation*3 + GfxManager.imgIconTroop.get(i).getWidth()+
						(Font.getFontWidth(Font.FONT_MEDIUM)*text.length())/2 +
						(int)modPosX, 
						troopY + GfxManager.imgIconTroop.get(i).getHeight()/2+
						GfxManager.imgIconTroop.get(i).getHeight()*i + 
						separation * i,
						Graphics.VCENTER|Graphics.HCENTER);
				
				
				//Right
				if(armyDefense != null){
					g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
					g.drawImage(
							GfxManager.imgIconTroop.get(i),
							getX()+GfxManager.imgBigBox.getWidth()/2 - 
							separation*2 - GfxManager.imgIconTroop.get(i).getWidth()/2 +
							(int)modPosX, 
							troopY + GfxManager.imgIconTroop.get(i).getHeight()/2 +
							GfxManager.imgIconTroop.get(i).getHeight()*i + 
							separation * i, 
							Graphics.VCENTER | Graphics.HCENTER);
					
					text = "X" + armyDefense.getNumberTroops(i);
					TextManager.drawSimpleText(g, 
							Font.FONT_MEDIUM,
							text,
							getX()+GfxManager.imgBigBox.getWidth()/2 -
							separation*3 - GfxManager.imgIconTroop.get(i).getWidth()-
							(Font.getFontWidth(Font.FONT_MEDIUM)*text.length())/2 +
							(int)modPosX,
							troopY + GfxManager.imgIconTroop.get(i).getHeight()/2 +
							GfxManager.imgIconTroop.get(i).getHeight()*i + 
							separation * i,
							Graphics.VCENTER|Graphics.HCENTER);
				}
			}
			
			if(armyDefense == null){
				g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
				int initY = getY() -
						((GfxManager.imgVillagers.getHeight() + Font.getFontHeight(Font.FONT_MEDIUM)+ separation)/2);
				String text = "X1";
				
				TextManager.drawSimpleText(g, 
						Font.FONT_MEDIUM,
						text,
						getX()+GfxManager.imgBigBox.getWidth()/2 - 
						separation*2 - GfxManager.imgVillagers.getWidth()/2 +
						(int)modPosX,
						initY + Font.getFontHeight(Font.FONT_MEDIUM)/2,
						Graphics.VCENTER|Graphics.HCENTER);
				
				g.drawImage(
						GfxManager.imgVillagers,
						getX()+GfxManager.imgBigBox.getWidth()/2 - 
						separation*2 - GfxManager.imgVillagers.getWidth()/2 +
						(int)modPosX,
						initY + Font.getFontHeight(Font.FONT_MEDIUM)/2 + separation +
						GfxManager.imgVillagers.getHeight()/2,
						Graphics.VCENTER | Graphics.HCENTER);
			}
					
			int relativeFlagX = GfxManager.imgTerrainBox.get(terrain.getType()).getWidth()/3;
			
			//Left
			TextManager.drawSimpleText(g, 
					Font.FONT_SMALL,
					""+armyAtack.getPower(terrain),
					getX()-relativeFlagX +
					(int)modPosX, 
					centerY +
					Font.getFontHeight(Font.FONT_SMALL)/2,
					Graphics.VCENTER|Graphics.HCENTER);
			
			g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			g.drawImage(GfxManager.imgFlagBigList.get(armyAtack.getPlayer().getFlag()), 
					getX()-GfxManager.imgTerrainBox.get(terrain.getType()).getWidth()/3 +
					(int)modPosX, 
					centerY +
					Font.getFontHeight(Font.FONT_BIG)/2 + 
					separation +
					GfxManager.imgFlagBigList.get(armyAtack.getPlayer().getFlag()).getHeight()/2,
					Graphics.VCENTER|Graphics.HCENTER);
			
			//Right
			TextManager.drawSimpleText(g,
					Font.FONT_SMALL,
					""+
					(armyDefense != null?
						armyDefense.getPower(terrain):GameParams.TERRAIN_DEFENSE[terrain.getType()]),
						getX()+GfxManager.imgTerrainBox.get(terrain.getType()).getWidth()/3 +
					(int)modPosX, 
					centerY +
					Font.getFontHeight(Font.FONT_SMALL)/2,
					Graphics.VCENTER|Graphics.HCENTER);
				
			g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			g.drawImage(
					(armyDefense != null?
					GfxManager.imgFlagBigList.get(armyDefense.getPlayer().getFlag()):
					GfxManager.imgFlagBigList.get(GfxManager.imgFlagBigList.size()-1)), 
					getX()+relativeFlagX +
					(int)modPosX, 
					centerY +
					Font.getFontHeight(Font.FONT_BIG)/2 + 
					separation +
					(armyDefense != null?
						GfxManager.imgFlagBigList.get(armyDefense.getPlayer().getFlag()).getHeight()/2:
						GfxManager.imgFlagBigList.get(GfxManager.imgFlagBigList.size()-1).getHeight()/2),
					Graphics.VCENTER|Graphics.HCENTER);
			
			
			g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			g.drawImage(GfxManager.imgTerrainBox.get(terrain.getType()), 
					getX()+(int)modPosX, 
					centerY +
					Font.getFontHeight(Font.FONT_BIG)/2 + 
					separation +
					(armyDefense != null?
						GfxManager.imgFlagBigList.get(armyDefense.getPlayer().getFlag()).getHeight() :
						GfxManager.imgFlagBigList.get(GfxManager.imgFlagBigList.size()-1).getHeight())+
					GfxManager.imgTerrainBox.get(terrain.getType()).getHeight()/2, 
					Graphics.VCENTER|Graphics.HCENTER);
			
			String terrainText="";
			switch(terrain.getType()){
				case GameParams.PLAIN: terrainText = "Battle plain"; break;
				case GameParams.FOREST: terrainText = "Battle forest"; break;
				case GameParams.MONTAIN: terrainText = "Battle montain"; break;
				case GameParams.SMALL_CITY: terrainText = "battle small city"; break;
				case GameParams.MEDIUM_CITY: terrainText = "battle medium city"; break;
				case GameParams.BIG_CITY: terrainText = "battle big city"; break;
			}
			
			TextManager.drawSimpleText(g, 
					Font.FONT_SMALL,
					terrainText,
					getX()+(int)modPosX, 
					centerY +
					Font.getFontHeight(Font.FONT_BIG) + 
					separation*2 +
					(armyDefense != null ?
							GfxManager.imgFlagBigList.get(armyDefense.getPlayer().getFlag()).getHeight():
							GfxManager.imgFlagBigList.get(GfxManager.imgFlagBigList.size()-1).getHeight()) +
							GfxManager.imgTerrainBox.get(terrain.getType()).getHeight(),
					Graphics.VCENTER|Graphics.HCENTER);
			
			//Equilibrio de poder
			
			//Distancia total
			int barWidth = 
			GfxManager.imgTerrainBox.get(terrain.getType()).getWidth() -
			relativeFlagX - 
			GfxManager.imgFlagBigList.get(GfxManager.imgFlagBigList.size()-1).getWidth();
			int barHeight = GfxManager.imgFlagBigList.get(armyAtack.getPlayer().getFlag()).getHeight()/10;
			
			int atackForces = armyAtack.getPower(terrain);
			int defenseForces = armyDefense != null? armyDefense.getPower(terrain):GameParams.TERRAIN_DEFENSE[terrain.getType()];
			int totalForces = atackForces+defenseForces;
			int atackWidth = (atackForces*barWidth)/totalForces;
			int defenseWidth = (defenseForces*barWidth)/totalForces;
			
			g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			g.setColor(Main.COLOR_GREEN);
			g.fillRect(getX()-barWidth/2 +(int)modPosX,
					centerY +
					Font.getFontHeight(Font.FONT_BIG)/2 + 
					separation +
					GfxManager.imgFlagBigList.get(armyAtack.getPlayer().getFlag()).getHeight()-
					separation-barHeight,
					atackWidth, barHeight);
			
			g.setColor(Main.COLOR_RED);
			g.fillRect(getX()+barWidth/2 - defenseWidth +(int)modPosX,
					centerY +
					Font.getFontHeight(Font.FONT_BIG)/2 + 
					separation +
					GfxManager.imgFlagBigList.get(armyAtack.getPlayer().getFlag()).getHeight()-
					separation-barHeight,
					defenseWidth, barHeight);
			
			battleDiceBox.draw(g);
		}
	}

	public int getResult() {
		return battleDiceBox.getResult();
	}

	public boolean isScape() {
		return scape;
	}

	public void setScape(boolean scape) {
		this.scape = scape;
	}
	
	

}
