package com.luis.strategy.gui;

import java.util.ArrayList;
import java.util.List;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.gui.MenuBox;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;
import com.luis.lgameengine.implementation.sound.SndManager;
import com.luis.strategy.GfxManager;
import com.luis.strategy.Main;
import com.luis.strategy.RscManager;
import com.luis.strategy.constants.Define;
import com.luis.strategy.constants.GameParams;
import com.luis.strategy.map.CityManagement;
import com.luis.strategy.map.Kingdom;
import com.luis.strategy.map.Player;

public class CityBox extends MenuBox{

	private Button buttonInfo;
	private Button buttonNewArmy;
	private boolean recruited;
	
	private BuildingImage[][] buildingImageList;
	
	class BuildingImage{
		Image image;
		int x;
		int y;
		
		public BuildingImage(Image image){
			this.image = image; 
		}
	}
	
	private List<Button> levelUpButtonList;
	
	public CityBox() {
		super(
				Define.SIZEX, Define.SIZEY, GfxManager.imgMediumBox, null, null,
				Define.SIZEX2, Define.SIZEY2-GfxManager.imgGameHud.getHeight()/2,
				null,
				null, Font.FONT_MEDIUM, Font.FONT_SMALL, -1, Main.FX_BACK);
		
		btnList.add(new Button(
				GfxManager.imgButtonCancelRelease,
				GfxManager.imgButtonCancelFocus,
				getX() - GfxManager.imgMediumBox.getWidth()/2, 
				getY() - GfxManager.imgMediumBox.getHeight()/2, 
				null, 
				-1){
			@Override
			public void onButtonPressDown() {}
			
			@Override
			public void onButtonPressUp() {
				super.onButtonPressUp();
				SndManager.getInstance().playFX(Main.FX_BACK, 0);
			}
		});
	}
	
	private int headY;
	private Kingdom kingdom;
	
	private String textHeader;
	
	public void start(
			Player player, 
			Kingdom k, 
			boolean clear, int terrainIndex){
		super.start();
		
		this.kingdom = k;
		this.recruited = false;
		
		textHeader = RscManager.allText[RscManager.TXT_GAME_DEFENSE] + " " + GameParams.TERRAIN_DEFENSE[terrainIndex];
		textHeader += " - " + RscManager.allText[RscManager.TXT_GAME_GOLD]  + " " + + GameParams.TERRAIN_DEFENSE[terrainIndex];
		textHeader += " - " + RscManager.allText[RscManager.TXT_GAME_FAITH]  + " " + + GameParams.TERRAIN_DEFENSE[terrainIndex];
		
		int sepH = GfxManager.imgTowerList.get(2).getHeight()/4;
		int sepW = GfxManager.imgTowerList.get(2).getWidth();
		int totalHeight = 
				Font.getFontHeight(Font.FONT_SMALL) + 
				GfxManager.imgTowerList.get(2).getHeight()*3 + sepH*5;
		int totalWidth = 
				GfxManager.imgTowerList.get(2).getWidth()*3 + 
				GfxManager.imgLevelUpRelease.getWidth() + 
				sepW*5;
		
		int initY = getY() - totalHeight/2;
		int initW = getX() - totalWidth/2;
		
		headY = initY + Font.getFontHeight(Font.FONT_SMALL)/2 + sepH;
		
		buttonInfo = new Button(
				GfxManager.imgButtonInfoRelease, GfxManager.imgButtonInfoFocus, 
				getX() - GfxManager.imgMediumBox.getWidth()/2, 
				getY() + GfxManager.imgMediumBox.getHeight()/2, 
				null, -1){
			public void onButtonPressUp() {
			};
		};
		
		//Building
		buildingImageList = new BuildingImage[3][3];
		
		//Recorro los tres niveles
		for(int i = 0; i < kingdom.getCityManagement().getBuildingList().size(); i++){
			
			if(kingdom.getCityManagement().getBuildingList().get(0).getLevel() >= i){
				buildingImageList[0][i] = (new BuildingImage(GfxManager.imgTowerList.get(i)));
			}else{
				buildingImageList[0][i] = (new BuildingImage(GfxManager.imgTowerBNList.get(i)));
			}
			
			if(kingdom.getCityManagement().getBuildingList().get(1).getLevel() >= i){
				buildingImageList[1][i] = (new BuildingImage(GfxManager.imgMarketList.get(i)));
			}else{
				buildingImageList[1][i] = (new BuildingImage(GfxManager.imgMarketBNList.get(i)));
			}
			
			if(kingdom.getCityManagement().getBuildingList().get(2).getLevel() >= i){
				buildingImageList[2][i] = (new BuildingImage(GfxManager.imgChurchList.get(i)));
			}else{
				buildingImageList[2][i] = (new BuildingImage(GfxManager.imgChurchBNList.get(i)));
			}
		}
		
		//Recorro los tres edificios
		if(player.hasKingom(kingdom)){
			levelUpButtonList = new ArrayList<Button>();
			
			
			addButton(player, 0);
			addButton(player, 1);
			addButton(player, 2);
		}
		
		//Posiciones
		int w = buildingImageList[0][2].image.getWidth();
		int h = buildingImageList[0][2].image.getHeight();
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				buildingImageList[i][j].x = 
						initW + sepW*(j+1) + w*(j+1) - w/2;
				buildingImageList[i][j].y = 
						initY + Font.getFontHeight(Font.FONT_SMALL) + sepH*(i+2) + h*(i+1) - h/2;
			}
			
			//Botones
			if(player.hasKingom(kingdom)){
				levelUpButtonList.get(i).setX(
						getX() + totalWidth/2 - sepW - GfxManager.imgLevelUpRelease.getWidth()/2);
				levelUpButtonList.get(i).setY(
						initY + Font.getFontHeight(Font.FONT_SMALL) + sepH*(i+2) + h*(i+1) - 
						GfxManager.imgLevelUpRelease.getHeight()/2);
			}else{
				levelUpButtonList = null;
			}
		}
		
		
		if(clear){
			buttonNewArmy = new Button(
					GfxManager.imgButtonNewArmyRelease, GfxManager.imgButtonNewArmyFocus, 
					getX() + GfxManager.imgMediumBox.getWidth()/2, 
					getY() + GfxManager.imgMediumBox.getHeight()/2, 
					null, -1){
				public void onButtonPressUp() {
					onBuy();
				};
			};
			buttonNewArmy.setDisabled(player.getGold() < GameParams.ARMY_COST);
		}else{
			buttonNewArmy = null;
		}
	}
	
	private void initLevelUpButtons(Player player, int type) {
		if(kingdom.getCityManagement().getBuildingList().get(type).isBuilding()){
			levelUpButtonList.get(type).setImgRelese(GfxManager.imgLevelUpFocus);
			levelUpButtonList.get(type).setImgFocus(GfxManager.imgLevelUpFocus);
			levelUpButtonList.get(type).setDisabled(true);
		}else{
			int level = kingdom.getCityManagement().getBuildingList().get(type).getLevel()+1;
			if(
					level < 2 &&
					player.getGold() >= CityManagement.BUILDING_COST[type][level]){
				levelUpButtonList.get(type).setImgRelese(GfxManager.imgLevelUpRelease);
				levelUpButtonList.get(type).setImgFocus(GfxManager.imgLevelUpFocus);
				levelUpButtonList.get(type).setDisabled(false);
				//Add callback
			}else{
				levelUpButtonList.get(type).setImgRelese(GfxManager.imgLevelUpDisabled);
				levelUpButtonList.get(type).setImgFocus(GfxManager.imgLevelUpDisabled);
				levelUpButtonList.get(type).setDisabled(true);
			}
		}
		
	}

	private void addButton(final Player player, final int type) {
		
		levelUpButtonList.add(new Button(
				GfxManager.imgLevelUpRelease.getWidth(), 
				GfxManager.imgLevelUpRelease.getHeight(),
				0, 0){
			@Override
			public void onButtonPressUp() {
				reset();
				int level = kingdom.getCityManagement().getBuildingList().get(type).getLevel()+1;
				
				int cost = CityManagement.BUILDING_COST[type][level];
				player.setGold(player.getGold() - cost);
				kingdom.getCityManagement().build(type);
				
				//Habilitar botones
				for(int i = 0; i < levelUpButtonList.size();i++){
					initLevelUpButtons(player, i);
				}
			}});
		
		initLevelUpButtons(player, type);
		
	}

	@Override
	public boolean update(MultiTouchHandler touchHandler, float delta) {
		if(state == STATE_ACTIVE){
			buttonInfo.update(touchHandler);
			if(levelUpButtonList != null){
				for(Button b : levelUpButtonList){
					b.update(touchHandler);
				}
			}
			if(buttonNewArmy != null && !buttonNewArmy.isDisabled()){
				buttonNewArmy.update(touchHandler);
			}
		}
		return super.update(touchHandler, delta);
	}
	
	public void draw(Graphics g){
		super.draw(g, GfxManager.imgBlackBG);
		if(state != STATE_UNACTIVE){
			
			TextManager.drawSimpleText(g, Font.FONT_SMALL, textHeader, 
					getX()+(int)modPosX, headY, Graphics.VCENTER | Graphics.HCENTER);
			
			for(int i = 0; i < 3; i++){
				for(int j = 0; j < 3; j++){
					g.drawImage(buildingImageList[i][j].image, 
							buildingImageList[i][j].x+(int)modPosX, 
							buildingImageList[i][j].y, 
							Graphics.VCENTER | Graphics.HCENTER);
					
					if(
							kingdom.getCityManagement().getBuildingList().get(i).isBuilding() &&
							kingdom.getCityManagement().getBuildingList().get(i).getLevel() == j){
						TextManager.drawSimpleText(g, Font.FONT_MEDIUM, 
								"" +
								kingdom.getCityManagement().getBuildingList().get(i).getState() + "/" +
								CityManagement.BUILDING_STATE[i][kingdom.getCityManagement().getBuildingList().get(i).getType()], 
								buildingImageList[i][j].x+(int)modPosX, 
								buildingImageList[i][j].y, 
								Graphics.VCENTER | Graphics.HCENTER);
						g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
					}
					else if(kingdom.getCityManagement().getBuildingList().get(i).getLevel() < j){
						TextManager.drawSimpleText(g, Font.FONT_MEDIUM, 
								"" +
								CityManagement.BUILDING_COST[i][j], 
								buildingImageList[i][j].x+(int)modPosX, 
								buildingImageList[i][j].y, 
								Graphics.VCENTER | Graphics.HCENTER);
						g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
					}
				}
				if(levelUpButtonList != null){
					levelUpButtonList.get(i).draw(g, (int)modPosX, 0);
				}
			}
			
			buttonInfo.draw(g, (int)modPosX, 0);
			if(buttonNewArmy != null){
				buttonNewArmy.draw(g, (int)modPosX, 0);
			}
		}
	}
	
	public void onBuy(){
		recruited = true;
	}
	
	public Kingdom getKingdom() {
		return kingdom;
	}

	public boolean isRecruited() {
		return recruited;
	}

	public void setRecruited(boolean recruited) {
		this.recruited = recruited;
	}
	
	

}
