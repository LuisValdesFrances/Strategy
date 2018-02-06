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
import com.luis.strategy.army.Troop;
import com.luis.strategy.constants.Define;
import com.luis.strategy.constants.GameParams;

public class BattleBox extends MenuBox{
	
	private BattleRollsBox battleRollsBox;
	
	private Army armyAtack;
	private Army armyDefense;
	
	private int terrain;
	
	private int troopY;
	private int centerY;
	private int separation;
	public BattleBox(){
		
		super(Define.SIZEX, Define.SIZEY, GfxManager.imgBigBox, null, null,
				Define.SIZEX2, Define.SIZEY2-GfxManager.imgGameHud.getHeight()/2,
				"BALANCE OF POWER",
				null, Font.FONT_MEDIUM, Font.FONT_SMALL);
		
		
		btnList.add(new Button(
				GfxManager.imgButtonCombatRelease,
				GfxManager.imgButtonCombatFocus,
				getX(), 
				getY() + GfxManager.imgBigBox.getHeight()/2, 
				null, 
				-1){
			@Override
			public void onButtonPressUp(){
				reset();
				battleRollsBox.start(armyAtack, armyDefense);
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
		
		battleRollsBox = new BattleRollsBox(){
			@Override
			public void onResult() {
				
			}
		};
	}
	
	public void start(int type, Army armyAtack, Army armyDefense){
		super.start();
		this.terrain = type;
		this.armyAtack = armyAtack;
		this.armyDefense = armyDefense;
		start();
	}
	
	@Override
	public boolean update(MultiTouchHandler touchHandler, float delta){
		if(!battleRollsBox.update(touchHandler, delta)){
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
						((GfxManager.imgIconVillagers.getHeight() + Font.getFontHeight(Font.FONT_MEDIUM)+ separation)/2);
				String text = "X1";
				
				TextManager.drawSimpleText(g, 
						Font.FONT_MEDIUM,
						text,
						getX()+GfxManager.imgBigBox.getWidth()/2 - 
						separation*2 - GfxManager.imgIconVillagers.getWidth()/2 +
						(int)modPosX,
						initY + Font.getFontHeight(Font.FONT_MEDIUM)/2,
						Graphics.VCENTER|Graphics.HCENTER);
				
				g.drawImage(
						GfxManager.imgIconVillagers,
						getX()+GfxManager.imgBigBox.getWidth()/2 - 
						separation*2 - GfxManager.imgIconVillagers.getWidth()/2 +
						(int)modPosX,
						initY + Font.getFontHeight(Font.FONT_MEDIUM)/2 + separation +
						GfxManager.imgIconVillagers.getHeight()/2,
						Graphics.VCENTER | Graphics.HCENTER);
			}
					
			int relativeFlagX = GfxManager.imgTerrainBox.get(terrain).getWidth()/3;
			
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
					getX()-GfxManager.imgTerrainBox.get(terrain).getWidth()/3 +
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
						armyDefense.getPower(terrain):GameParams.TERRAIN_DEFENSE[terrain]),
						getX()+GfxManager.imgTerrainBox.get(terrain).getWidth()/3 +
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
			g.drawImage(GfxManager.imgTerrainBox.get(terrain), 
					getX()+(int)modPosX, 
					centerY +
					Font.getFontHeight(Font.FONT_BIG)/2 + 
					separation +
					(armyDefense != null?
						GfxManager.imgFlagBigList.get(armyDefense.getPlayer().getFlag()).getHeight() :
						GfxManager.imgFlagBigList.get(GfxManager.imgFlagBigList.size()-1).getHeight())+
					GfxManager.imgTerrainBox.get(terrain).getHeight()/2, 
					Graphics.VCENTER|Graphics.HCENTER);
			
			String terrainText="";
			switch(terrain){
				case GameParams.PLAIN: terrainText = "BATTLE PLAIN"; break;
				case GameParams.FOREST: terrainText = "BATTLE FOREST"; break;
				case GameParams.MONTAIN: terrainText = "BATTLE MONTAIN"; break;
				case GameParams.SMALL_CITY: terrainText = "BATTLE SMALL CITY"; break;
				case GameParams.MEDIUM_CITY: terrainText = "BATTLE MEDIUM CITY"; break;
				case GameParams.BIG_CITY: terrainText = "BATTLE BIG CITY"; break;
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
							GfxManager.imgTerrainBox.get(terrain).getHeight(),
					Graphics.VCENTER|Graphics.HCENTER);
			
			//Equilibrio de poder
			
			//Distancia total
			int barWidth = 
			GfxManager.imgTerrainBox.get(terrain).getWidth() -
			relativeFlagX - 
			GfxManager.imgFlagBigList.get(GfxManager.imgFlagBigList.size()-1).getWidth();
			int barHeight = GfxManager.imgFlagBigList.get(armyAtack.getPlayer().getFlag()).getHeight()/10;
			
			int atackForces = armyAtack.getPower(terrain);
			int defenseForces = armyDefense != null? armyDefense.getPower(terrain):GameParams.TERRAIN_DEFENSE[terrain];
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
			
			battleRollsBox.draw(g);
		}
	}

}
