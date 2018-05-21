package com.luis.strategy.game;

import java.util.ArrayList;
import java.util.List;

import com.luis.lgameengine.gui.NotificationBox;
import com.luis.strategy.GameState;
import com.luis.strategy.Main;
import com.luis.strategy.RscManager;
import com.luis.strategy.connection.OnlineInputOutput;
import com.luis.strategy.data.DataKingdom;
import com.luis.strategy.data.GameBuilder;
import com.luis.strategy.datapackage.scene.SceneData;
import com.luis.strategy.map.GameScene;

public class DataSender{

	private List<Notification> notificationList;
	
	public DataSender(){
		notificationList = new ArrayList<Notification>();
	}
	
	public void addNotification(String user, String message){
		notificationList.add(new Notification(user, message));
	}
	
	public void sendGameScene(GameScene gameScene, int state) {
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
	
	/*
	public void sendNotification(final String user, final String message){
		if(GameState.getInstance().getGameMode() == GameState.GAME_MODE_ONLINE){
			Thread thread = new Thread(){
				@Override
				public void run(){
					OnlineInputOutput.getInstance().sendNotifiation(
							OnlineInputOutput.URL_CREATE_NOTIFICATION, 
							""+GameState.getInstance().getSceneData().getId(), 
							user, 
							message);
				}
			};
			thread.start();
		}
	}
	*/
	
	
	public void sendGameNotifications(){
		for(Notification n : notificationList){
			
			String message = 
					GameState.getInstance().getSceneData().getId() + "-" +
					DataKingdom.SCENARY_NAME_LIST[GameState.getInstance().getMap()] + " " + n.message;
			
			OnlineInputOutput.getInstance().sendNotifiation(
					OnlineInputOutput.URL_CREATE_NOTIFICATION, 
					""+GameState.getInstance().getSceneData().getId(), 
					n.user, 
					message);
		}
		/*
		Thread thread = new Thread(){
			@Override
			public void run(){
				for(Notification n : notificationList){
				OnlineInputOutput.getInstance().sendNotifiation(
						OnlineInputOutput.URL_CREATE_NOTIFICATION, 
						""+GameState.getInstance().getSceneData().getId(), 
						n.user, 
						n.message);
			}
			}
		};
		thread.start();
		
		*/
	}
	
	class Notification{
		
		public Notification(String user, String message) {
			super();
			this.user = user;
			this.message = message;
		}
		String user;
		String message;
	}
}
