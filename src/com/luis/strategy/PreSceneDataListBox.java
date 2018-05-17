package com.luis.strategy;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gui.ListBox;
import com.luis.strategy.constants.Define;
import com.luis.strategy.datapackage.scene.PreSceneData;
import com.luis.strategy.datapackage.scene.PreSceneListData;

public class PreSceneDataListBox extends ListBox{
	
	private PreSceneListData preSceneListData;

	public PreSceneDataListBox(PreSceneListData preSceneListData, String[] textOptions) {
		
		super(Define.SIZEX, Define.SIZEY, null, 
				GfxManager.imgNotificationBox, GfxManager.imgNotificationBox, 
				Define.SIZEX2, Define.SIZEY2, 
				RscManager.allText[RscManager.TXT_SELECT_MAP], 
				textOptions,
				Font.FONT_BIG, Font.FONT_SMALL);
		
		this.preSceneListData = preSceneListData;
	}
	
	public PreSceneData getPreSceneData(){
		return preSceneListData.getPreSceneDataList().get(getIndexPressed());
	}
	

}
