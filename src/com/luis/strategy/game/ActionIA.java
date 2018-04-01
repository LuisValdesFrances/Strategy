package com.luis.strategy.game;

import java.util.List;

import android.util.Log;

import com.luis.lgameengine.gameutils.gameworld.GameCamera;
import com.luis.lgameengine.gameutils.gameworld.WorldConver;
import com.luis.lgameengine.gui.NotificationBox;
import com.luis.strategy.GfxManager;
import com.luis.strategy.Main;
import com.luis.strategy.RscManager;
import com.luis.strategy.army.Army;
import com.luis.strategy.army.Troop;
import com.luis.strategy.constants.GameParams;
import com.luis.strategy.map.Kingdom;
import com.luis.strategy.map.Map;
import com.luis.strategy.map.Terrain;

public class ActionIA {
	
	private Player player;
	
	public static final int DECISION_NONE = 0;//No hace nada
	public static final int DECISION_ATACK = 1;//Continua con la conquista
	public static final int DECISION_MOVE = 2;//Mueve solamente
	public static final int DECISION_MOVE_AND_ATACK = 3;//Mueve y ataca
	
	
	/**
	 * Crea ejercitos nuevos y compra tropas a los que se encuentren en ciudades
	 */
	public void management(WorldConver worldConver, GameCamera gameCamera, Map map, List<Player> playerList){
		/*
		int cost = player.getCost(false);
		int salary = player.getTaxes();
		int gold = player.getGold();
		*/
		boolean canBuy;//Controla que haya alguna ciudad libre (Generar ejercito )o haya alg�n ejercito en una ciudad (Aquirir tropas)
		
		//Presupuestos
		int armyBudget = (int)(player.getGold()*0.30f);
		int troopBudget = (int)(player.getGold()*0.30f);
		
		do{
			canBuy = false;
			for(Kingdom k : player.getKingdomList()){
				//Busco si la ciudad esta libre
				boolean isFree = true;
				for(Player player : playerList){
					for(Army army : player.getArmyList()){
						if(army.getKingdom().getId() == k.getId()){
							isFree = false;
						}
					}
				}
				if(isFree && k.isACity()){
					canBuy = true;
					
					if(armyBudget >= GameParams.ARMY_COST){
						String text = player.getName() +  " has recruited a new army";
						NotificationBox.getInstance().addMessage(text);
						
						armyBudget -= GameParams.ARMY_COST;
						player.setGold(player.getGold()-GameParams.ARMY_COST);
						
						Army army = new Army(
								worldConver, 
								gameCamera, 
								map, 
								player,
								k,
								player.getFlag(), 
								map.getX(), map.getY(), GfxManager.imgMap.getWidth(), GfxManager.imgMap.getHeight());
						army.setState(Army.STATE_OFF);
						player.getArmyList().add(army);
					}
				}
			}
		}while(armyBudget >= GameParams.ARMY_COST && canBuy);
		
		
		do{
			canBuy = false;
			for(Army army : player.getArmyList()){
				if(player.hasKingom(army.getKingdom()) && army.getKingdom().isACity()){
					canBuy = true;
					int troop = Main.getRandom(0, GameParams.SIEGE);
					//Si sigo teniendo presupuesto, compro:
					if(troopBudget >= GameParams.TROOP_COST[troop]){
						String text = player.getName() +  " has been acquired a new troop";
						NotificationBox.getInstance().addMessage(text);
						troopBudget-= GameParams.TROOP_COST[troop];
						player.setGold(player.getGold()-GameParams.TROOP_COST[troop]);
						army.getTroopList().add(new Troop(troop, false));
					}
				}
			}
		}while(troopBudget >= GameParams.TROOP_COST[GameParams.HARASSERES] && canBuy);
	}
	
	public void discard(){
		do{
			for(Army army: player.getArmyList()){
				Troop troop = army.getTroopList().get(army.getTroopList().size()-1);
				if(!troop.isSubject()){
					Log.i("Debug", "Descartado tropa: " + troop.getId() + " - " + troop.getType());
					
					String text = player.getName() +  " descarta tropa: " + RscManager.allText[RscManager.TXT_GAME_INFANTRY + troop.getType()];
					NotificationBox.getInstance().addMessage(text);
					
					player.setGold(army.getPlayer().getGold() + GameParams.TROOP_COST[troop.getType()]);
					
					army.getTroopList().remove(army.getTroopList().size()-1);
				}
			}
		}while(player.getGold() < 0);
	}
	
	private void buildDecision(List<Player> playerList, Army army) {
		
		if(army != null){
			// Orden de prioridades
	
			// Sigo con las conquistas a medias(Si procede)
			if(army.getKingdom().getState() > 0 && !player.hasKingom(army.getKingdom())){
				army.getIaDecision().setDecision(DECISION_ATACK);
				return;
			}
	
			// Muevo y ataco al ejercito enemigo mas debil(Si procede)
			for (Kingdom k : army.getKingdom().getBorderList()) {
				Army enemy = getArmyAtKingdom(playerList, k);
				if(enemy != null && enemy.getPlayer().getId() != player.getId()){
					// Comparo fuerzas
					if (army.getPower(k.getTerrainList().get(0)) > enemy.getPower(k.getTerrainList().get(0))) {
						army.getIaDecision().setDecision(DECISION_MOVE_AND_ATACK);
						army.getIaDecision().setKingdomDecision(k.getId());
						return;
					} else if (army.getPower(k.getTerrainList().get(0)) == enemy.getPower(k.getTerrainList().get(0))) {
						if (Main.getRandom(0, 100) >= 50) {
							army.getIaDecision().setDecision(DECISION_MOVE_AND_ATACK);
							army.getIaDecision().setKingdomDecision(k.getId());
							return;
						}
					}
				}
			}
			
	
			// Inicio una conquista si el territorio es mas debil(Si procede)
			if(!player.hasKingom(army.getKingdom()) && comparePower(army, army.getKingdom())){
				army.getIaDecision().setDecision(DECISION_ATACK);
				return;
			}
	
			// Muevo y ataco a un territorio VACIO mas debil(Si procede)
			for (Kingdom k : army.getKingdom().getBorderList()) {
				if(getArmyAtKingdom(playerList, k) == null && !player.hasKingom(k)){
					if(comparePower(army, k)){
						army.getIaDecision().setDecision(DECISION_MOVE_AND_ATACK);
						army.getIaDecision().setKingdomDecision(k.getId());
						return;
					}
				}
			}
			
			// Muevo a una de mis ciudades libres(Si procede)
			for (Kingdom k : army.getKingdom().getBorderList()) {
				if(getArmyAtKingdom(playerList, k) == null && player.hasKingom(k) && k.isACity()){
					army.getIaDecision().setDecision(DECISION_MOVE);
					army.getIaDecision().setKingdomDecision(k.getId());
					return;
				}
			}
			
			// Muevo a uno de mis territorios(Si procede)
			for (Kingdom k : army.getKingdom().getBorderList()) {
				if(getArmyAtKingdom(playerList, k) == null && player.hasKingom(k)){
					army.getIaDecision().setDecision(DECISION_MOVE);
					army.getIaDecision().setKingdomDecision(k.getId());
					return;
				}
			}
			
			// Random
			int r = Main.getRandom(0, army.getKingdom().getBorderList().size()-1);
			army.getIaDecision().setDecision(-1);
			army.getIaDecision().setKingdomDecision(army.getKingdom().getBorderList().get(r).getId());
			return;
		}
	}
	
	public Army getActiveArmy(List<Player> playerList){
		Army activeArmy = null;
		for(int i = 0; i < player.getArmyList().size() && activeArmy == null; i++){
			if(player.getArmyList().get(i).getState() == Army.STATE_ON){
				activeArmy = player.getArmyList().get(i);
			}
		}
		
		buildDecision(playerList, activeArmy);
		
		if(activeArmy != null && activeArmy.getIaDecision().getDecision() == DECISION_NONE){
			return null;
		}else{
			return activeArmy;
		}
	}
	
	public boolean isKingdomToMove(Map map, Kingdom kingdom){
		
		switch(getSelectedArmy().getIaDecision().getDecision()){
			case DECISION_ATACK:
				return getSelectedArmy().getKingdom().getId() == kingdom.getId();
			
			case DECISION_MOVE:
			case DECISION_MOVE_AND_ATACK:
				return getSelectedArmy().getIaDecision().getKingdomDecision() == kingdom.getId();
			default:
				int r = Main.getRandom(0, getSelectedArmy().getKingdom().getBorderList().size()-1);
				return getSelectedArmy().getKingdom().getBorderList().get(r).getId() == kingdom.getId();
		}
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	private Army getSelectedArmy(){
		Army selected = null;
		for(Army army : player.getArmyList()){
			if(army.isSelected()){
				selected = army;
				break;
			}
		}
		return selected;
	}
	
	/**
	 * Compara si el poder del ejercito es superior al del kingdom
	 * @param army
	 * @param kingdom
	 * @return
	 */
	private boolean comparePower(Army army, Kingdom kingdom){
		boolean higherPower = true;
		
		for(Terrain terrain : kingdom.getTerrainList()){
			if(army.getPower(terrain) < GameParams.TERRAIN_DEFENSE[terrain.getType()]){
				higherPower = false;
			}
		}
		return higherPower;
	}
	
	/**
	 * Devuelve el ejercito del reino
	 * @param playerList
	 * @param kingdom
	 * @return
	 */
	private Army getArmyAtKingdom(List<Player> playerList, Kingdom kingdom){
		Army army = null;
		for(Player player : playerList){
			army = player.getArmy(kingdom);
			if(army != null)
				break;
		}
		return army;
	}
	
	

}
