package com.luis.strategy.map;

public class CityManagement {
	
	/*
	 * Mititar:
	 * Torre -> Muralla -> Fortaleza
	 * 
	 * Economico -> Taller -> Gremios -> Mercado
	 * 
	 * Religioso -> Capilla -> Iglesia -> Catedral
	 */
	
	//Ofrecer la posibilidad de arrasar ciudades??
	//1-La elimino del mapa -> Guardar en Scene lista de territorios arrasados
	//2-Le quito todas las mejoras 
	
	class Building{
		int type;
		int state;
		int level;//0, 1, 2 //Ejemplo: torre -> muralla -> fortaleza
	}

}
