package com.luis.strategy.game;

import com.luis.lgameengine.gui.NotificationBox;
import com.luis.strategy.GameState;
import com.luis.strategy.Main;
import com.luis.strategy.RscManager;
import com.luis.strategy.connection.OnlineInputOutput;
import com.luis.strategy.data.GameBuilder;
import com.luis.strategy.datapackage.scene.SceneData;
import com.luis.strategy.map.GameScene;

public class DataSender{

	public DataSender(){
	}
	
	public void send(GameScene gameScene, int state) {
		String msg = null;
		GameState.getInstance().setGameScene(gameScene);
		SceneData sceneData = GameBuilder.getInstance().buildSceneData(state);
		
		Main.getInstance().startClock(Main.TYPE_EARTH);
		String result = 
				OnlineInputOutput.getInstance().sendDataPackage(sceneData, "updateSceneServlet");
		Main.getInstance().stopClock();
		
		if(result.equals("Connection error")){
			msg = RscManager.allText[RscManager.TXT_CONNECTION_ERROR];
		 }
		 else if(result.equals("Server error")){
			msg = RscManager.allText[RscManager.TXT_SERVER_ERROR];
		 }
		 else if(result.equals("Query error")){
			msg = RscManager.allText[RscManager.TXT_SERVER_ERROR];
		 }
		 else if(result.equals("Succes")){
			msg = RscManager.allText[RscManager.TXT_SEND_DATA];
		}
		if(msg != null){
			NotificationBox.getInstance().addMessage(msg);
		}
		
	}

}
