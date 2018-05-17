package com.luis.strategy.datapackage.scene;

import java.io.Serializable;
import java.util.List;

public class SceneData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;//Fijo
	
	private String host;//Fijo (Eliminar -> es inutil)
	private int map;//Fijo
	private int numPlayer;//Fijo (Eliminar -> se puede obtener a partur del mapa)
	
	private String nextPlayer;//Variable
	private int playerCount;//Variable
	private int playerIndex;//Variable
	private int turnCount;//Variable
	private int state;
	
	List<PlayerData> playerDataList;//Variable
	
	
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
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
}
