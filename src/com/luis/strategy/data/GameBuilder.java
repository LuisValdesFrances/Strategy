package com.luis.strategy.data;

import java.util.ArrayList;
import java.util.List;

import com.luis.strategy.GameState;
import com.luis.strategy.datapackage.scene.ArmyData;
import com.luis.strategy.datapackage.scene.SceneData;
import com.luis.strategy.datapackage.scene.KingdomData;
import com.luis.strategy.datapackage.scene.PlayerData;
import com.luis.strategy.datapackage.scene.TroopData;
import com.luis.strategy.game.ActionIA;
import com.luis.strategy.map.Army;
import com.luis.strategy.map.GameScene;
import com.luis.strategy.map.Kingdom;
import com.luis.strategy.map.Player;
import com.luis.strategy.map.Troop;

public class GameBuilder {
	
	private static GameBuilder instance;
	
	public static GameBuilder getInstance(){
		if(instance == null){
			instance = new GameBuilder();
		}
		return instance;
	}
	
	public GameScene buildPassAndPlay(){
		
		GameScene gameScene = new GameScene(GameState.getInstance()
				.getMap(),
				0,// GfxManager.imgMap.getWidth()/2,
				0,// GfxManager.imgMap.getHeight()/2,
				DataKingdom.MAP_PARTS[GameState.getInstance().getMap()][0],
				DataKingdom.MAP_PARTS[GameState.getInstance().getMap()][1]);

		switch (GameState.getInstance().getMap()) {
		case 0:
			gameScene.setKingdomList(DataKingdom.getGenterex(gameScene.getMap()));
			break;
		case 1:
			gameScene.setKingdomList(DataKingdom.getCrom(gameScene.getMap()));
			break;
		}
		
		List<Player> playerList = new ArrayList<Player>();
		GameState.PlayerConf[] playerConfList = GameState.getInstance().getPlayerConfList();
		
		for (int i = 0; i < playerConfList.length; i++) {
			
			int k1 = DataKingdom.INIT_MAP_DATA[GameState.getInstance().getMap()][i][0];
			int k2 = DataKingdom.INIT_MAP_DATA[GameState.getInstance().getMap()][i][1];
			
			Player player = new Player(
					GameState.getInstance().getPlayerConfList()[i].name, 
					GameState.getInstance().getPlayerConfList()[i].IA?new ActionIA():null, 
							GameState.getInstance().getPlayerConfList()[i].flag, 
							k1);
			
			player.setGold(10);
			player.getKingdomList().add(gameScene.getKingdom(k1));
			player.getKingdomList().add(gameScene.getKingdom(k2));
			
			Army army = new Army(
					gameScene.getMap(), player, gameScene.getKingdom(k1), player.getFlag(), 
					gameScene.getMap().getX(), gameScene.getMap().getY(), gameScene.getMap().getWidth(), gameScene.getMap().getHeight());
			army.initTroops();
			player.getArmyList().add(army);
			
			playerList.add(player);
		}
		
		gameScene.setPlayerList(playerList);
		return gameScene;
	}
	
	public GameScene buildGameScene(){
		
		GameScene gameScene = new GameScene(
				GameState.getInstance().getMap(),
				0,//GfxManager.imgMap.getWidth()/2, 
				0,//GfxManager.imgMap.getHeight()/2,
				DataKingdom.MAP_PARTS[GameState.getInstance().getMap()][0],
				DataKingdom.MAP_PARTS[GameState.getInstance().getMap()][1]
				);
		gameScene.setTurnCount(GameState.getInstance().getDataPackage().getTurnCount());
		gameScene.setPlayerIndex(GameState.getInstance().getDataPackage().getPlayerIndex());
		
		List<Player>playerList = new ArrayList<Player>();
		
		switch(GameState.getInstance().getMap()){
    	case 0:
    		gameScene.setKingdomList(DataKingdom.getGenterex(gameScene.getMap()));
            break;
    	case 1:
    		gameScene.setKingdomList(DataKingdom.getCrom(gameScene.getMap()));
    		break;
    	}
		
		for(PlayerData playerData: GameState.getInstance().getDataPackage().getPlayerDataList()){
			
			String name = playerData.getName();
			boolean isIA = playerData.isIA();
			int flag = playerData.getFlag();
			int capitalkingdom = playerData.getCapitalKingdom();
			int gold = playerData.getGold();
			
			ActionIA actionIA = isIA?new ActionIA():null;
			Player player = new Player(name, actionIA, flag, capitalkingdom);
			player.setGold(gold);
			
			for(KingdomData kingdomData : playerData.getKingdomList()){
				Kingdom k = gameScene.getKingdom(kingdomData.getId());
				k.setState(kingdomData.getState());
				player.getKingdomList().add(k);
			}
			
			for(ArmyData armyData : playerData.getArmyList()){
				
				Kingdom k = gameScene.getKingdom(armyData.getKingdom().getId());
				k.setState(armyData.getKingdom().getState());
				
				Army army = new Army(
						gameScene.getMap(), player, k, player.getFlag(), 
						gameScene.getMap().getX(), gameScene.getMap().getY(), gameScene.getMap().getWidth(), gameScene.getMap().getHeight());
				for(TroopData td : armyData.getTroopList()){
					Troop troop = new Troop(td.getType(), td.isSubject());
					army.getTroopList().add(troop);
				}
				
				player.getArmyList().add(army);
			}
			playerList.add(player);
		}
		gameScene.setPlayerList(playerList);
		
		return gameScene;
	}
	
	public SceneData buildSceneData(){
		SceneData dataPackage = new SceneData();
		dataPackage.setMap(GameState.getInstance().getGameScene().getId());
		dataPackage.setPlayerIndex(GameState.getInstance().getGameScene().getPlayerIndex());
		dataPackage.setTurnCount(GameState.getInstance().getGameScene().getTurnCount());
		
		List<PlayerData> playerDataList = new ArrayList<PlayerData>();
		for(Player p : GameState.getInstance().getGameScene().getPlayerList()){
			PlayerData pd = new PlayerData();
			pd.setId(p.getId());
			pd.setName(p.getName());
			pd.setGold(p.getGold());
			pd.setCapitalKingdom(p.getCapitalkingdom().getId());
			pd.setFlag(p.getFlag());
			pd.setIA(p.getActionIA() != null);
			
			//Add army list to player object
			List<ArmyData> adList = new ArrayList<ArmyData>();
			for(Army a : p.getArmyList()){
				ArmyData ad = new ArmyData();
				KingdomData kingdomData = new KingdomData();
				kingdomData.setId(a.getKingdom().getId());
				kingdomData.setState(a.getKingdom().getState());
				ad.setKingdom(kingdomData);
				//Añado las tropas
				List<TroopData> troopList = new ArrayList<TroopData>();
				for(Troop t : a.getTroopList()){
					TroopData troopData = new TroopData();
					troopData.setType(t.getType());
					troopData.setSubject(t.isSubject());
					troopList.add(troopData);
				}
				ad.setTroopList(troopList);
				adList.add(ad);
			}
			pd.setArmyList(adList);
			
			//Add kingdom list to player object
			List<KingdomData> kdList = new ArrayList<KingdomData>();
			for(Kingdom k : p.getKingdomList()){
				KingdomData kd = new KingdomData();
				kd.setId(k.getId());
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
