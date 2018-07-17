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
	 * 
	 * Se blinda el reino con la catedral ->
	 * nivel 1 = 12% de blindaje
	 * nivel 2 = 25% de blindaje
	 * nivel 3 = 50% de blindaje
	 * 
	 * Puntos de Fe -> Repetir tiradas fallidas de forma global
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
	
	public void build(int type) {
		if (buildingList.get(type).level < 2) {
			buildingList.get(type).level++;
		}
	}

	public void update() {
		for (Building b : buildingList) {
			if (b.level > -1
					&& b.state < GameParams.BUILDING_STATE[b.type][b.level]) {
				b.state++;
			}
		}
	}
	
	public int getSizeByActiveLevel(){
		int total = GameParams.BUILDING_STATE.length * GameParams.BUILDING_STATE[0].length;
		
		int count = 0;
		for (Building b : buildingList) {
			count += (b.getActiveLevel()+1);
		}
		
		if(count == total){
			return 2;
		}
		else if (count >= total/2){
			return 1;
		}
		else{
			return 0;
		}
	}

	public List<Building> getBuildingList() {
		return buildingList;
	}

	public void setBuildingList(List<Building> buildingList) {
		this.buildingList = buildingList;
	}


	

}
