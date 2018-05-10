package com.luis.strategy.data;

import java.util.ArrayList;
import java.util.List;

import com.luis.strategy.GameState;
import com.luis.strategy.datapackage.ArmyData;
import com.luis.strategy.datapackage.DataPackage;
import com.luis.strategy.datapackage.KingdomData;
import com.luis.strategy.datapackage.PlayerData;
import com.luis.strategy.game.ActionIA;
import com.luis.strategy.map.Army;
import com.luis.strategy.map.GameScene;
import com.luis.strategy.map.Kingdom;
import com.luis.strategy.map.Player;

public class GameBuilder {
	
	private static GameBuilder instance;
	
	public static GameBuilder getInstance(){
		if(instance == null){
			instance = new GameBuilder();
		}
		return instance;
	}
	
	public List<Player> build(
			GameState gameState,
			GameScene gameScene){
		
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
			
			Army army = new Army(
					gameScene.getMap(), player, gameScene.getKingdom(k1), player.getFlag(), 
					gameScene.getMap().getX(), gameScene.getMap().getY(), gameScene.getMap().getWidth(), gameScene.getMap().getHeight());
			player.getArmyList().add(army);
			
			playerList.add(player);
		}
		return playerList;
	}
	
	public GameScene build(DataPackage dataPackage){
		
		GameState.getInstance().init(dataPackage.getMap(), dataPackage.getPlayerDataList().size());
		GameScene gameScene= null;
		
		List<Player>playerList = new ArrayList<Player>();
		List<Kingdom> kingdomList = null;
		
		switch(GameState.getInstance().getMap()){
    	case 0:
    		kingdomList = DataKingdom.getGenterex(null);
            break;
    	case 1:
    	case 2:
    		kingdomList = DataKingdom.getCrom(null);
    		break;
    	}
		
		for(PlayerData playerData: dataPackage.getPlayerDataList()){
			
			String name = playerData.getName();
			boolean isIA = playerData.isIA();
			int flag = playerData.getFlag();
			int capitalkingdom = playerData.getCapitalKingdom();
			
			ActionIA actionIA = isIA?new ActionIA():null;
			Player player = new Player(name, actionIA, flag, capitalkingdom);
			
			for(KingdomData kingdomData : playerData.getKingdomList()){
				Kingdom k = gameScene.getKingdom(kingdomData.getId());
				k.setState(kingdomData.getState());
				player.getKingdomList().add(k);
			}
			
		}
		
		return gameScene;
	}
	
	
	public DataPackage build(GameScene gameScene){
		DataPackage dataPackage = new DataPackage();
		dataPackage.setMap(gameScene.getId());
		dataPackage.setPlayerIndex(gameScene.getPlayerIndex());
		dataPackage.setTurnCount(gameScene.getTurnCount());
		
		List<PlayerData> playerDataList = new ArrayList<PlayerData>();
		for(Player p : gameScene.getPlayerList()){
			PlayerData pd = new PlayerData();
			pd.setId(p.getId());
			pd.setGold(p.getGold());
			pd.setCapitalKingdom(p.getCapitalkingdom().getId());
			pd.setFlag(p.getFlag());
			pd.setIA(p.getActionIA() != null);
			
			//Add army list to player object
			List<ArmyData> adList = new ArrayList<ArmyData>();
			for(Army a : p.getArmyList()){
				ArmyData ad = new ArmyData();
				ad.setKingdom(a.getKingdom().getId());
				adList.add(ad);
			}
			pd.setArmyList(adList);
			
			//Add kingdom list to player object
			List<KingdomData> kdList = new ArrayList<KingdomData>();
			for(Kingdom k : p.getKingdomList()){
				KingdomData kd = new KingdomData();
				kd.setId(p.getId());
				kd.setState(k.getState());
				kdList.add(kd);
			}
			pd.setKingdomList(kdList);
			
			
			playerDataList.add(pd);
		}
		
		//Añado las lista de jugadores
		dataPackage.setPlayerDataList(playerDataList);
		
		return dataPackage;
	}

}
