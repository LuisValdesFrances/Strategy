package com.luis.data;

import java.util.ArrayList;
import java.util.List;

import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.map.Kingdom;
import com.luis.map.Terrain;
import com.luis.strategy.GfxManager;
import com.luis.strategy.constants.GameParams;

public class DataKingdom {
	
	public static List<Kingdom> getGenterex(int mapX, int mapY, Image map){
		
		List<Kingdom> kingdomList = new ArrayList<Kingdom>();
		
		Kingdom k1 = new Kingdom((int)(360f/map.getWidth()*100), (int)(174f/map.getHeight()*100), mapX, mapY, map.getWidth(), map.getHeight());
		k1.setId(1);
		k1.setName("Genterex");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		List<Terrain> terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain((int)(238f/map.getWidth()*100), (int)(210f/map.getHeight()*100), GfxManager.imgPlain.getWidth(), GfxManager.imgPlain.getHeight(), mapX, mapY, map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain((int)(238f/map.getWidth()*100), (int)(124f/map.getHeight()*100), GfxManager.imgBigCity.getWidth(), GfxManager.imgBigCity.getHeight(), mapX, mapY, map.getWidth(), map.getHeight(), GameParams.BIG_CITY, false));
		k1.setTerrainList(terrainList);
		
		Kingdom k2 = new Kingdom((int)(210f/(float)map.getWidth()*100f), (int)(290f/(float)map.getHeight()*100f), mapX, mapY, map.getWidth(), map.getHeight());
		k2.setId(2);
		k2.setName("Surett");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain((int)(165f/(float)map.getWidth()*100f), (int)(355f/(float)map.getHeight()*100f), GfxManager.imgPlain.getWidth(), GfxManager.imgPlain.getHeight(), mapX, mapY, map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		terrainList.add(new Terrain((int)(110f/(float)map.getWidth()*100f), (int)(270f/(float)map.getHeight()*100f), GfxManager.imgMediumCity.getWidth(), GfxManager.imgMediumCity.getHeight(), mapX, mapY, map.getWidth(), map.getHeight(), GameParams.MEDIUM_CITY, false));
		k2.setTerrainList(terrainList);
		
		Kingdom k3 = new Kingdom((int)(275f/(float)map.getWidth()*100f), (int)(580f/(float)map.getHeight()*100f), mapX, mapY, map.getWidth(), map.getHeight());
		k3.setId(3);
		k3.setName("Lyecee");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain((int)(220f/(float)map.getWidth()*100f), (int)(452f/(float)map.getHeight()*100f), GfxManager.imgPlain.getWidth(), GfxManager.imgPlain.getHeight(), mapX, mapY, map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain((int)(190f/(float)map.getWidth()*100f), (int)(515f/(float)map.getHeight()*100f), GfxManager.imgMediumCity.getWidth(), GfxManager.imgMediumCity.getHeight(), mapX, mapY, map.getWidth(), map.getHeight(), GameParams.MEDIUM_CITY, false));
		k3.setTerrainList(terrainList);
		
		Kingdom k4 = new Kingdom((int)(630f/(float)map.getWidth()*100f), (int)(260f/(float)map.getHeight()*100f), mapX, mapY, map.getWidth(), map.getHeight());
		k4.setId(4);
		k4.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain((int)(545f/(float)map.getWidth()*100f), (int)(235f/(float)map.getHeight()*100f), GfxManager.imgPlain.getWidth(), GfxManager.imgPlain.getHeight(), mapX, mapY, map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
		k4.setTerrainList(terrainList);
		
		Kingdom k5 = new Kingdom((int)(330f/(float)map.getWidth()*100f), (int)(305f/(float)map.getHeight()*100f), mapX, mapY, map.getWidth(), map.getHeight());
		k5.setId(5);
		k5.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain((int)(278f/(float)map.getWidth()*100f), (int)(380f/(float)map.getHeight()*100f), GfxManager.imgPlain.getWidth(), GfxManager.imgPlain.getHeight(), mapX, mapY, map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
		terrainList.add(new Terrain((int)(338f/(float)map.getWidth()*100f), (int)(380f/(float)map.getHeight()*100f), GfxManager.imgPlain.getWidth(), GfxManager.imgPlain.getHeight(), mapX, mapY, map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		k5.setTerrainList(terrainList);
		
		Kingdom k6 = new Kingdom((int)(455f/(float)map.getWidth()*100f), (int)(280f/(float)map.getHeight()*100f), mapX, mapY, map.getWidth(), map.getHeight());
		k6.setId(6);
		k6.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain((int)(434f/(float)map.getWidth()*100f), (int)(340f/(float)map.getHeight()*100f), GfxManager.imgPlain.getWidth(), GfxManager.imgPlain.getHeight(), mapX, mapY, map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
		terrainList.add(new Terrain((int)(491f/(float)map.getWidth()*100f), (int)(340f/(float)map.getHeight()*100f), GfxManager.imgPlain.getWidth(), GfxManager.imgPlain.getHeight(), mapX, mapY, map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		k6.setTerrainList(terrainList);
		
		Kingdom k7 = new Kingdom((int)(375f/(float)map.getWidth()*100f), (int)(510f/(float)map.getHeight()*100f), mapX, mapY, map.getWidth(), map.getHeight());
		k7.setId(7);
		k7.setName("Tiraslye");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain((int)(497f/(float)map.getWidth()*100f), (int)(485f/(float)map.getHeight()*100f), GfxManager.imgPlain.getWidth(), GfxManager.imgPlain.getHeight(), mapX, mapY, map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		terrainList.add(new Terrain((int)(562f/(float)map.getWidth()*100f), (int)(485f/(float)map.getHeight()*100f), GfxManager.imgPlain.getWidth(), GfxManager.imgPlain.getHeight(), mapX, mapY, map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain((int)(425f/(float)map.getWidth()*100f), (int)(415f/(float)map.getHeight()*100f), GfxManager.imgPlain.getWidth(), GfxManager.imgPlain.getHeight(), mapX, mapY, map.getWidth(), map.getHeight(), GameParams.MEDIUM_CITY, false));
		k7.setTerrainList(terrainList);
		
		Kingdom k8 = new Kingdom((int)(600f/(float)map.getWidth()*100f), (int)(565f/(float)map.getHeight()*100f), mapX, mapY, map.getWidth(), map.getHeight());
		k8.setId(8);
		k8.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain((int)(460f/(float)map.getWidth()*100f), (int)(595f/(float)map.getHeight()*100f), GfxManager.imgPlain.getWidth(), GfxManager.imgPlain.getHeight(), mapX, mapY, map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		k8.setTerrainList(terrainList);
		
		k1.getBorderList().add(k2);
		k1.getBorderList().add(k4);
		k1.getBorderList().add(k5);
		k1.getBorderList().add(k6);
		
		k2.getBorderList().add(k1);
		k2.getBorderList().add(k3);
		k2.getBorderList().add(k5);
		
		k3.getBorderList().add(k2);
		k3.getBorderList().add(k5);
		k3.getBorderList().add(k7);
		k3.getBorderList().add(k8);
		
		k4.getBorderList().add(k1);
		k4.getBorderList().add(k6);
		k4.getBorderList().add(k7);
		
		k5.getBorderList().add(k1);
		k5.getBorderList().add(k2);
		k5.getBorderList().add(k3);
		k5.getBorderList().add(k6);
		k5.getBorderList().add(k7);
		
		k6.getBorderList().add(k1);
		k6.getBorderList().add(k4);
		k6.getBorderList().add(k5);
		k6.getBorderList().add(k7);
		
		k7.getBorderList().add(k3);
		k7.getBorderList().add(k4);
		k7.getBorderList().add(k5);
		k7.getBorderList().add(k6);
		k7.getBorderList().add(k8);
		
		k8.getBorderList().add(k7);
		k8.getBorderList().add(k3);
		
		
		kingdomList.add(k1);
		kingdomList.add(k2);
		kingdomList.add(k3);
		kingdomList.add(k4);
		kingdomList.add(k5);
		kingdomList.add(k6);
		kingdomList.add(k7);
		kingdomList.add(k8);
		
		return kingdomList;
	}

}
