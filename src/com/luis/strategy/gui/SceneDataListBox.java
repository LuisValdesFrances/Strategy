package com.luis.strategy.gui;

import java.io.Serializable;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gui.ListBox;
import com.luis.strategy.GfxManager;
import com.luis.strategy.RscManager;
import com.luis.strategy.constants.Define;

public class SceneDataListBox extends ListBox{
	
	private Serializable sceneListData;

	public SceneDataListBox(Serializable sceneListData, String[] textOptions) {
		
		super(Define.SIZEX, Define.SIZEY, null, 
				GfxManager.imgNotificationBox, GfxManager.imgNotificationBox, 
				Define.SIZEX2, Define.SIZEY2, 
				RscManager.allText[RscManager.TXT_SELECT_MAP], 
				textOptions,
				Font.FONT_BIG, Font.FONT_SMALL);
		
		this.sceneListData = sceneListData;
	}
	
	public void refresh(String[] textOptions){
		super.refresh(
				GfxManager.imgNotificationBox,
				GfxManager.imgNotificationBox, 
				textOptions, 
				Font.FONT_SMALL);
	}
	
	public Serializable getSceneListData() {
		return sceneListData;
	}

	public void setSceneListData(Serializable sceneListData) {
		this.sceneListData = sceneListData;
	}
	
	
}
