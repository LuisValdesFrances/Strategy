package com.luis.strategy.connection;

import java.util.ArrayList;
import java.util.List;

import com.luis.strategy.datapackage.ArmyData;
import com.luis.strategy.datapackage.DataPackage;
import com.luis.strategy.datapackage.KingdomData;
import com.luis.strategy.datapackage.PlayerData;
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
	
	public GameScene build(DataPackage dataPackage){
		
		GameScene map = null;
		List<Player>playerList = new ArrayList<Player>();
		
		return map;
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
