package com.luis.strategy.datapackage;

import java.io.Serializable;

public class ArmyData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int kingdom;

	public int getKingdom() {
		return kingdom;
	}

	public void setKingdom(int kingdom) {
		this.kingdom = kingdom;
	}
}
