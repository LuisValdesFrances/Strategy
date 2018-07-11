package com.luis.strategy.map;

import java.util.List;

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

	public static final int[][] BUILDING_COST = { 
			{ 200, 400, 800 },
			{ 300, 600, 1200 }, 
			{ 400, 800, 1600 } };

	public static final int[][] BUILDING_STATE = { 
			{ 3, 5, 7 }, 
			{ 4, 7, 10 },
			{ 5, 9, 13 } };

	private List<Building> buildingList;

	public CityManagement() {
		buildingList.add(null);
		buildingList.add(null);
		buildingList.add(null);
	}

	public void update() {
		for (Building b : buildingList) {
			if(b.state > BUILDING_STATE[b.type][b.state]){
				b.state++;
			}
		}
	}
	
	

	public void build(int type) {
		if(buildingList.get(type).state < BUILDING_STATE[type][2]){
			buildingList.get(type).state++;
		}
	}

	class Building {
		int type;
		int state;
		int level;// 0, 1, 2 //Ejemplo: torre -> muralla -> fortaleza
		public boolean isBuilding(){
			return state < BUILDING_STATE[type][state];
		}
	}

}
