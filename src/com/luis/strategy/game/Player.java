package com.luis.strategy.game;

import java.util.ArrayList;
import java.util.List;

import com.luis.army.Army;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.map.Kingdom;

public class Player {
	
	private String name;
	
	private String gold;
	
	private List<Army> armyList;
	private List<Kingdom> kingdomList;
	
	private int flag;
	
	public Player(int flag){
		this.flag = flag;
		this.armyList = new ArrayList<Army>();
		this.kingdomList = new ArrayList<Kingdom>();
	}
	
	
	public void updateAnimations(float delta){
		for(Army army : armyList){
			army.update(delta);
		}
	}
	
	public void draw(Graphics g){
		for(Army army : armyList){
			army.draw(g);
		}
	}
	
	public boolean hasKingom(Kingdom kingdom){
		boolean k = false;
		for(int i = 0; !k && i < kingdomList.size(); i++){
			if(kingdomList.get(i).getId() == kingdom.getId())
				k= true;
		}
		return k;
	}
	
	public Army getArmy(Kingdom kingdom){
		Army army = null;
		for(int i = 0; army == null && i < armyList.size(); i++){
			if(armyList.get(i).getKingdom().getId() == kingdom.getId()){
				army = armyList.get(i);
			}
		}
		return army;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGold() {
		return gold;
	}

	public void setGold(String gold) {
		this.gold = gold;
	}

	public List<Army> getArmyList() {
		return armyList;
	}

	public void setArmyList(List<Army> armyList) {
		this.armyList = armyList;
	}

	public List<Kingdom> getKingdomList() {
		return kingdomList;
	}

	public void setKingdomList(List<Kingdom> kingdomList) {
		this.kingdomList = kingdomList;
	}


	public int getFlag() {
		return flag;
	}


	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	
	
	

}
