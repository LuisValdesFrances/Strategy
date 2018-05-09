package com.luis.strategy.datapackage;

import java.io.Serializable;
import java.util.List;

public class DataPackage implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int map;
	private int playerIndex;
	private int turnCount;
	List<PlayerData> playerDataList;
	
	public int getMap() {
		return map;
	}
	public void setMap(int map) {
		this.map = map;
	}
	public int getPlayerIndex() {
		return playerIndex;
	}
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	public int getTurnCount() {
		return turnCount;
	}
	public void setTurnCount(int turnCount) {
		this.turnCount = turnCount;
	}
	public List<PlayerData> getPlayerDataList() {
		return playerDataList;
	}
	public void setPlayerDataList(List<PlayerData> playerDataList) {
		this.playerDataList = playerDataList;
	}
	
	
}
