package com.luis.strategy;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.gui.ListBox;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.strategy.constants.Define;
import com.luis.strategy.data.DataKingdom;
import com.luis.strategy.datapackage.scene.SceneData;
import com.luis.strategy.datapackage.scene.SceneListData;

public class SceneDataListBox extends ListBox{
	
	private SceneListData sceneListData;

	public SceneDataListBox(SceneListData sceneListData) {
		
		super(Define.SIZEX, Define.SIZEY, null, 
				GfxManager.imgNotificationBox, GfxManager.imgNotificationBox, 
				Define.SIZEX2, Define.SIZEY2, 
				RscManager.allText[RscManager.TXT_SELECT_MAP], 
				sceneListData.getSceneDataList().size(),
				Font.FONT_BIG, Font.FONT_SMALL);
		
		this.sceneListData = sceneListData;
	}
	
	public SceneData getSceneData(){
		return sceneListData.getSceneDataList().get(getIndexPressed());
	}
	
	@Override
	public void draw(Graphics g, Image imgBG){
		super.draw(g, imgBG);
		if(state != STATE_UNACTIVE){
			for(int i = 0; i < btnList.size(); i++){
				
				SceneData sd = sceneListData.getSceneDataList().get(i);
				
				String text = DataKingdom.SCENARY_NAME_LIST[sd.getMap()];
				text += "  " + sd.getPlayerCount() + "/" + sd.getNumPlayer();
				g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
				TextManager.drawSimpleText(g, Font.FONT_SMALL, text, 
						btnList.get(i).getX() + (int)modPosX, 
						btnList.get(i).getY(), Graphics.VCENTER | Graphics.HCENTER);
			}
		}
		
	}

}
