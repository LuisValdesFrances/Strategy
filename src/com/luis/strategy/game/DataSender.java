package com.luis.strategy.game;

import java.util.ArrayList;
import java.util.List;

import com.luis.strategy.GameState;
import com.luis.strategy.Main;
import com.luis.strategy.connection.OnlineInputOutput;
import com.luis.strategy.data.GameBuilder;
import com.luis.strategy.datapackage.scene.SceneData;
import com.luis.strategy.map.GameScene;

public class DataSender{

	private List<Notification> notificationList;
	
	public DataSender(){
		notificationList = new ArrayList<Notification>();
	}
	
	public void addNotification(String user, String message, int type){
		notificationList.add(new Notification(user, message, type));
	}
	
	public String sendGameScene(GameScene gameScene, int state) {
		GameState.getInstance().setGameScene(gameScene);
		SceneData sceneData = GameBuilder.getInstance().buildSceneData(state);
		
		Main.getInstance().startClock(Main.TYPE_EARTH);
		String result = 
				OnlineInputOutput.getInstance().sendDataPackage(
						Main.getInstance().getActivity(),
						OnlineInputOutput.URL_UPDATE_SCENE, sceneData);
		Main.getInstance().stopClock();
		return result;
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
	
	
	public void sendGameNotifications(String user){
		for(Notification n : notificationList){
			
			String message = user + " - " + n.message;
			String type = "" + n.type;
			
			OnlineInputOutput.getInstance().sendNotification(
					Main.getInstance().getActivity(),
					"" + GameState.getInstance().getSceneData().getId(), 
					n.user, message, type);
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
		
		public Notification(String user, String message, int type) {
			super();
			this.user = user;
			this.message = message;
			this.type = type;
		}
		String user;
		String message;
		int type;
	}
}
