package com.luis.strategy.game;

import com.luis.strategy.constants.GameParams;
import com.luis.strategy.map.Army;
import com.luis.strategy.map.Terrain;

public class GameUtils {
	
	private static GameUtils instance;
	
	public static GameUtils getInstance(){
		if(instance == null){
			instance = new GameUtils();
		}
		return instance;
	}
	
	public int calculateDifficult(Terrain terrain, Army armyAtack, Army armyDefense){
		int value=0;
		
		int pAtack = armyAtack.getPower(terrain);
		int pDefense = armyDefense != null ? armyDefense.getPower(terrain):GameParams.TERRAIN_DEFENSE[terrain.getType()];
		
		value = GameParams.ROLL_SYSTEM-((armyAtack.getPower(terrain) * GameParams.ROLL_SYSTEM)/(pAtack+pDefense));
		
		return value;
	}

}
