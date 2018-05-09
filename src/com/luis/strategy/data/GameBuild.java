package com.luis.strategy.data;

import java.util.ArrayList;
import java.util.List;

import com.luis.lgameengine.gameutils.gameworld.GameCamera;
import com.luis.lgameengine.gameutils.gameworld.WorldConver;
import com.luis.strategy.GameState;
import com.luis.strategy.game.ActionIA;
import com.luis.strategy.map.Army;
import com.luis.strategy.map.GameScene;
import com.luis.strategy.map.Player;

public class GameBuild {
	
	private static GameBuild instance;
	
	public static GameBuild getInstance(){
		if(instance == null){
			instance = new GameBuild();
		}
		return instance;
	}
	
	public List<Player> build(
			GameState gameState,
			GameScene gameScene, WorldConver worldConver, GameCamera gameCamera){
		
		List<Player> playerList = new ArrayList<Player>();
		GameState.PlayerConf[] playerConfList = gameState.getPlayerConfList();
		
		for (int i = 0; i < playerConfList.length; i++) {
			
			int k1 = DataKingdom.INIT_MAP_DATA[gameState.getMap()][i][0];
			int k2 = DataKingdom.INIT_MAP_DATA[gameState.getMap()][i][1];
			
			Player player = new Player(
					gameState.getPlayerConfList()[i].name, 
					gameState.getPlayerConfList()[i].IA?new ActionIA():null, 
					gameState.getPlayerConfList()[i].flag, 
					k1);
			
			player.setGold(10);
			player.getKingdomList().add(gameScene.getKingdom(k1));
			player.getKingdomList().add(gameScene.getKingdom(k2));
			
			Army army = new Army(worldConver, gameCamera, 
					gameScene.getMap(), player, gameScene.getKingdom(k1), player.getFlag(), 
					gameScene.getMap().getX(), gameScene.getMap().getY(), gameScene.getMap().getWidth(), gameScene.getMap().getHeight());
			player.getArmyList().add(army);
			
			playerList.add(player);
		}
		return playerList;
	}

}
