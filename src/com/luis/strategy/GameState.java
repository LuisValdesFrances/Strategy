package com.luis.strategy;

import com.luis.strategy.map.GameScene;

public class GameState {

	private static GameState gameState;
	private int map;
	private PlayerConf[] playerConfList; 
	
	
	private GameScene gameScene;
	
	public void init(int map, int numPlayer){
		this.map = map;
		playerConfList = new PlayerConf[numPlayer];
		for (int i = 0; i < playerConfList.length; i++) {
			playerConfList[i] = new PlayerConf();
		}
		
	}

	public static GameState getInstance(){
		if(gameState == null){
			gameState = new GameState();
		}
		return gameState;
	}
	
	
	public GameScene getGameScene() {
		return gameScene;
	}

	public void setGameScene(GameScene gameScene) {
		this.gameScene = gameScene;
	}

	public static GameState getGameState() {
		return gameState;
	}

	public static void setGameState(GameState gameState) {
		GameState.gameState = gameState;
	}

	public int getMap() {
		return map;
	}

	public void setMap(int map) {
		this.map = map;
	}

	
	public PlayerConf[] getPlayerConfList() {
		return playerConfList;
	}

	public void setPlayerConfList(PlayerConf[] playerConfList) {
		this.playerConfList = playerConfList;
	}


	public class PlayerConf{
		
		public String name; 
		public int flag; 
		public boolean IA;
		
	}
}
