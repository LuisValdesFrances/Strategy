package com.luis.strategy.army;

public class Troop {
	
	private static int idCount;
	private int id;
	private int type;
	
	public Troop(int type){
		this.type = type;
		this.id = idCount++;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

}
