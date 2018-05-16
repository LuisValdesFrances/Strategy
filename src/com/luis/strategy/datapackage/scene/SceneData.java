package com.luis.strategy.datapackage.scene;

import java.io.Serializable;
import java.util.List;

public class SceneData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String host;
	private int map;
	
	private String nextPlayer;
	private int numPlayer;
	private int playerCount;
	
	private int playerIndex;
	private int turnCount;
	
	List<PlayerData> playerDataList;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
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
	public String getNextPlayer() {
		return nextPlayer;
	}
	public void setNextPlayer(String nextPlayer) {
		this.nextPlayer = nextPlayer;
	}
	public int getNumPlayer() {
		return numPlayer;
	}
	public void setNumPlayer(int numPlayer) {
		this.numPlayer = numPlayer;
	}
	public int getPlayerCount() {
		return playerCount;
	}
	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}
	
}
