package com.luis.strategy;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gui.ListBox;
import com.luis.strategy.constants.Define;
import com.luis.strategy.datapackage.scene.SceneData;
import com.luis.strategy.datapackage.scene.SceneListData;

public class SceneDataListBox extends ListBox{
	
	private SceneListData sceneListData;

	public SceneDataListBox(SceneListData sceneListData, String[] textOptions) {
		
		super(Define.SIZEX, Define.SIZEY, null, 
				GfxManager.imgNotificationBox, GfxManager.imgNotificationBox, 
				Define.SIZEX2, Define.SIZEY2, 
				RscManager.allText[RscManager.TXT_SELECT_MAP], 
				textOptions,
				Font.FONT_BIG, Font.FONT_SMALL);
		
		this.sceneListData = sceneListData;
	}
	
	public SceneData getSceneData(){
		return sceneListData.getSceneDataList().get(getIndexPressed());
	}
	

}
