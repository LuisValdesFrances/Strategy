package com.luis.strategy;

import com.luis.strategy.datapackage.scene.SceneData;
import com.luis.strategy.map.GameScene;

public class GameState {

	private static GameState instance;
	
	//Game configuration menu
	private int map;
	private PlayerConf[] playerConfList;
	
	//Game serial data
	private GameScene gameScene;
	private SceneData dataPackage;
	
	//User configuration
	private String name;
	private String password;
	private int gameMode;
	
	public static final int GAME_MODE_CAMPAING = 0;
	public static final int GAME_MODE_PLAY_AND_PASS = 1;
	public static final int GAME_MODE_ONLINE = 2;
	
	public void init(int map, int numPlayer){
		this.map = map;
		playerConfList = new PlayerConf[numPlayer];
		for (int i = 0; i < playerConfList.length; i++) {
			playerConfList[i] = new PlayerConf();
		}
	}
	
	public void init(SceneData dataPackage){
		this.dataPackage = dataPackage;
		this.map = dataPackage.getMap();
		playerConfList = new PlayerConf[dataPackage.getPlayerDataList().size()];
		for (int i = 0; i < playerConfList.length; i++) {
			playerConfList[i] = new PlayerConf();
		}
	}

	public static GameState getInstance(){
		if(instance == null){
			instance = new GameState();
		}
		return instance;
	}
	
	
	public GameScene getGameScene() {
		return gameScene;
	}

	public void setGameScene(GameScene gameScene) {
		this.gameScene = gameScene;
	}

	
	public SceneData getDataPackage() {
		return dataPackage;
	}

	public void setDataPackage(SceneData dataPackage) {
		this.dataPackage = dataPackage;
	}

	public static GameState getGameState() {
		return instance;
	}

	public static void setGameState(GameState gameState) {
		GameState.instance = gameState;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getGameMode() {
		return gameMode;
	}

	public void setGameMode(int gameMode) {
		this.gameMode = gameMode;
	}



	public class PlayerConf{
		public String name; 
		public int flag; 
		public boolean IA;
		
	}
}
