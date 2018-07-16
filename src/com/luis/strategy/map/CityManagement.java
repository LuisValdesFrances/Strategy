package com.luis.strategy.map;

import java.util.ArrayList;
import java.util.List;

import com.luis.strategy.constants.GameParams;

public class CityManagement {

	/*
	 * Militar: Torre -> Muralla -> Fortaleza (Aumentan defensa)
	 * 
	 * Economico -> Taller -> Gremios -> Mercado (Aumentan recaudaci�n)
	 * 
	 * Religioso -> Capilla -> Iglesia -> Catedral (Cabeza de la Iglesia->
	 * �blindarse?)
	 */

	/*
	 * Cabeza de la Iglesia: este seria un jugador que podr�a blindarse y no
	 * recibir ataques �?
	 */

	// Ofrecer la posibilidad de arrasar ciudades??
	// 1-La elimino del mapa -> Guardar en Scene lista de territorios arrasados
	// 2-Le quito todas las mejoras

	private List<Building> buildingList;

	public CityManagement() {
		buildingList = new ArrayList<Building>();
		buildingList.add(new Building(0, 0, -1));
		buildingList.add(new Building(1, 0, -1));
		buildingList.add(new Building(2, 0, -1));
	}

	public void update() {
		for (Building b : buildingList) {
			if(b.level > -1 && b.state < GameParams.BUILDING_STATE[b.type][b.level]){
				b.state++;
			}
		}
	}
	
	public List<Building> getBuildingList() {
		return buildingList;
	}

	public void setBuildingList(List<Building> buildingList) {
		this.buildingList = buildingList;
	}

	public void build(int type) {
		if(buildingList.get(type).level < 2){
			buildingList.get(type).level++;
		}
	}

	public class Building {
		int type;
		int state;
		int level;// 0, 1, 2 //Ejemplo: torre -> muralla -> fortaleza
		
		
		
		public Building(int type, int state, int level) {
			super();
			this.type = type;
			this.state = state;
			this.level = level;
		}
		public boolean isBuilding(){
			if(level < 0)
				return false;
			else
				return state < GameParams.BUILDING_STATE[type][level];
		}
		public int getType() {
			return type;
		}
		
		public int getState() {
			return state;
		}
		
		public int getLevel() {
			return level;
		}
		
		
		
	}

}
