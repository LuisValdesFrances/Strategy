package com.luis.strategy.data;

import java.util.ArrayList;
import java.util.List;

import com.luis.lgameengine.gameutils.gameworld.GameCamera;
import com.luis.lgameengine.gameutils.gameworld.WorldConver;
import com.luis.strategy.GfxManager;
import com.luis.strategy.constants.GameParams;
import com.luis.strategy.map.Kingdom;
import com.luis.strategy.map.Map;
import com.luis.strategy.map.Terrain;

public class DataKingdom {
	
	public static List<Kingdom> getGenterex(WorldConver worldConver, GameCamera gameCamera, Map map){
		
		List<Kingdom> kingdomList = new ArrayList<Kingdom>();
		
		Kingdom k1 = new Kingdom(worldConver, gameCamera, map,
			(690f/map.getWidth()*120), (174f/map.getHeight()*120), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k1.setId(1);
		k1.setName("Genterex");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		List<Terrain> terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(560f/map.getWidth()*120), (210f/map.getHeight()*120), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(620f/map.getWidth()*120), (210f/map.getHeight()*120), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(560f/map.getWidth()*120), (124f/map.getHeight()*120), GfxManager.imgTerrain.get(GameParams.BIG_CITY).getWidth(), GfxManager.imgTerrain.get(GameParams.BIG_CITY).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.BIG_CITY, false));
		k1.setTerrainList(terrainList);
		
		Kingdom k2 = new Kingdom(worldConver, gameCamera, map,
				(540f/(float)map.getWidth()*120f), (290f/(float)map.getHeight()*120f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k2.setId(2);
		k2.setName("Surett");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(460f/(float)map.getWidth()*120f), (350f/(float)map.getHeight()*120f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(520f/(float)map.getWidth()*120f), (350f/(float)map.getHeight()*120f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(440f/(float)map.getWidth()*120f), (270f/(float)map.getHeight()*120f), GfxManager.imgTerrain.get(GameParams.MEDIUM_CITY).getWidth(), GfxManager.imgTerrain.get(GameParams.MEDIUM_CITY).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MEDIUM_CITY, false));
		k2.setTerrainList(terrainList);
		
		Kingdom k3 = new Kingdom(worldConver, gameCamera, map,
			(520f/(float)map.getWidth()*120f), (430f/(float)map.getHeight()*120f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k3.setId(3);
		k3.setName("Lyecee");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(530f/(float)map.getWidth()*120f), (580f/(float)map.getHeight()*120f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(590f/(float)map.getWidth()*120f), (580f/(float)map.getHeight()*120f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(520f/(float)map.getWidth()*120f), (515f/(float)map.getHeight()*120f), GfxManager.imgTerrain.get(GameParams.MEDIUM_CITY).getWidth(), GfxManager.imgTerrain.get(GameParams.MEDIUM_CITY).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MEDIUM_CITY, false));
		k3.setTerrainList(terrainList);
		
		Kingdom k4 = new Kingdom(worldConver, gameCamera, map,
			(960f/(float)map.getWidth()*120f), (260f/(float)map.getHeight()*120f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k4.setId(4);
		k4.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(850f/(float)map.getWidth()*120f), (235f/(float)map.getHeight()*120f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(910f/(float)map.getWidth()*120f), (235f/(float)map.getHeight()*120f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
		k4.setTerrainList(terrainList);
		
		Kingdom k5 = new Kingdom(worldConver, gameCamera, map,
			(660f/(float)map.getWidth()*120f), (305f/(float)map.getHeight()*120f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k5.setId(5);
		k5.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(608f/(float)map.getWidth()*120f), (380f/(float)map.getHeight()*120f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(668f/(float)map.getWidth()*120f), (380f/(float)map.getHeight()*120f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		k5.setTerrainList(terrainList);
		
		Kingdom k6 = new Kingdom(worldConver, gameCamera, map,
			(785f/(float)map.getWidth()*120f), (280f/(float)map.getHeight()*120f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k6.setId(6);
		k6.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(764f/(float)map.getWidth()*120f), (340f/(float)map.getHeight()*120f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(821f/(float)map.getWidth()*120f), (340f/(float)map.getHeight()*120f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		k6.setTerrainList(terrainList);
		
		Kingdom k7 = new Kingdom(worldConver, gameCamera, map,
			(705f/(float)map.getWidth()*120f), (510f/(float)map.getHeight()*120f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k7.setId(7);
		k7.setName("Tiraslye");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(827f/(float)map.getWidth()*120f), (485f/(float)map.getHeight()*120f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(882f/(float)map.getWidth()*120f), (485f/(float)map.getHeight()*120f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(745f/(float)map.getWidth()*120f), (430f/(float)map.getHeight()*120f), GfxManager.imgTerrain.get(GameParams.BIG_CITY).getWidth(), GfxManager.imgTerrain.get(GameParams.BIG_CITY).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.BIG_CITY, false));
		k7.setTerrainList(terrainList);
		
		Kingdom k8 = new Kingdom(worldConver, gameCamera, map,
			(930f/(float)map.getWidth()*120f), (565f/(float)map.getHeight()*120f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k8.setId(8);
		k8.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(750f/(float)map.getWidth()*120f), (595f/(float)map.getHeight()*120f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(810f/(float)map.getWidth()*120f), (595f/(float)map.getHeight()*120f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
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
	
public static List<Kingdom> getCrom(WorldConver worldConver, GameCamera gameCamera, Map map){
		
		List<Kingdom> kingdomList = new ArrayList<Kingdom>();
		
		Kingdom k1 = new Kingdom(worldConver, gameCamera, map,
			(530f/map.getWidth()*100), (410f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k1.setId(1);
		k1.setName("Genterex");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		List<Terrain> terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(430f/map.getWidth()*100), (510f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(500f/map.getWidth()*100), (510f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(570f/map.getWidth()*100), (510f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.FOREST).getWidth(), GfxManager.imgTerrain.get(GameParams.FOREST).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(405f/map.getWidth()*100), (400f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.BIG_CITY).getWidth(), GfxManager.imgTerrain.get(GameParams.BIG_CITY).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.BIG_CITY, false));
		k1.setTerrainList(terrainList);
		
		Kingdom k2 = new Kingdom(worldConver, gameCamera, map,
				(360f/(float)map.getWidth()*100f), (595f/(float)map.getHeight()*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k2.setId(2);
		k2.setName("Surett");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(290f/(float)map.getWidth()*100f), (680f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(360f/(float)map.getWidth()*100f), (680f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(260f/(float)map.getWidth()*100f), (592f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.MEDIUM_CITY).getWidth(), GfxManager.imgTerrain.get(GameParams.MEDIUM_CITY).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MEDIUM_CITY, false));
		k2.setTerrainList(terrainList);
		
		Kingdom k3 = new Kingdom(worldConver, gameCamera, map,
			(460f/(float)map.getWidth()*100f), (790f/(float)map.getHeight()*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k3.setId(3);
		k3.setName("Lyecee");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(382f/(float)map.getWidth()*100f), (940f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(452f/(float)map.getWidth()*100f), (940f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(372f/(float)map.getWidth()*100f), (852f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.MEDIUM_CITY).getWidth(), GfxManager.imgTerrain.get(GameParams.MEDIUM_CITY).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MEDIUM_CITY, false));
		k3.setTerrainList(terrainList);
		
		Kingdom k4 = new Kingdom(worldConver, gameCamera, map,
			(710f/(float)map.getWidth()*100f), (510f/(float)map.getHeight()*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k4.setId(4);
		k4.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(790f/(float)map.getWidth()*100f), (575f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(860f/(float)map.getWidth()*100f), (575f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(930f/(float)map.getWidth()*100f), (575f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
		k4.setTerrainList(terrainList);
		
		Kingdom k5 = new Kingdom(worldConver, gameCamera, map,
			(500f/(float)map.getWidth()*100f), (610f/(float)map.getHeight()*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k5.setId(5);
		k5.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(460f/(float)map.getWidth()*100f), (710f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(530f/(float)map.getWidth()*100f), (710f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
		k5.setTerrainList(terrainList);
		
		Kingdom k6 = new Kingdom(worldConver, gameCamera, map,
			(690f/(float)map.getWidth()*100f), (575f/(float)map.getHeight()*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k6.setId(6);
		k6.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(650f/(float)map.getWidth()*100f), (660f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(720f/(float)map.getWidth()*100f), (660f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
		k6.setTerrainList(terrainList);
		
		Kingdom k7 = new Kingdom(worldConver, gameCamera, map,
			(580f/(float)map.getWidth()*100f), (840f/(float)map.getHeight()*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k7.setId(7);
		k7.setName("Tiraslye");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(710f/(float)map.getWidth()*100f), (830f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(780f/(float)map.getWidth()*100f), (830f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(650f/(float)map.getWidth()*100f), (760f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.BIG_CITY).getWidth(), GfxManager.imgTerrain.get(GameParams.BIG_CITY).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MEDIUM_CITY, false));
		k7.setTerrainList(terrainList);
		
		Kingdom k8 = new Kingdom(worldConver, gameCamera, map,
			(820f/(float)map.getWidth()*100f), (940f/(float)map.getHeight()*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k8.setId(8);
		k8.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(620f/(float)map.getWidth()*100f), (980f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(690f/(float)map.getWidth()*100f), (980f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		k8.setTerrainList(terrainList);
		
		
		//Daergonais
		Kingdom k9 = new Kingdom(worldConver, gameCamera, map,
			(340f/(float)map.getWidth()*100f), (80f/(float)map.getHeight()*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k9.setId(9);
		k9.setName("Krull");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(245f/(float)map.getWidth()*100f), (95f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		k9.setTerrainList(terrainList);
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(150f/(float)map.getWidth()*100f), (95f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.SMALL_CITY, false));
		k9.setTerrainList(terrainList);
		
		Kingdom k10 = new Kingdom(worldConver, gameCamera, map,
			(485f/(float)map.getWidth()*100f), (275f/(float)map.getHeight()*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k10.setId(10);
		k10.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(350f/(float)map.getWidth()*100f), (210f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.FOREST).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		k10.setTerrainList(terrainList);
		
		Kingdom k11 = new Kingdom(worldConver, gameCamera, map,
			(305f/(float)map.getWidth()*100f), (250f/(float)map.getHeight()*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k11.setId(11);
		k11.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(220f/(float)map.getWidth()*100f), (240f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.FOREST).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		k11.setTerrainList(terrainList);
		
		Kingdom k12 = new Kingdom(worldConver, gameCamera, map,
			(695f/(float)map.getWidth()*100f), (420f/(float)map.getHeight()*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k12.setId(12);
		k12.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(620f/(float)map.getWidth()*100f), (390f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.FOREST).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		k12.setTerrainList(terrainList);
		
		Kingdom k13 = new Kingdom(worldConver, gameCamera, map,
			(780f/(float)map.getWidth()*100f), (410f/(float)map.getHeight()*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k13.setId(13);
		k13.setName("Daergonais");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(730f/(float)map.getWidth()*100f), (330f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.FOREST).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		k13.setTerrainList(terrainList);
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(800f/(float)map.getWidth()*100f), (330f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.FOREST).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
		k13.setTerrainList(terrainList);
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(870f/(float)map.getWidth()*100f), (430f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.FOREST).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.SMALL_CITY, false));
		k13.setTerrainList(terrainList);
		
		
		//Quaca
		Kingdom k14 = new Kingdom(worldConver, gameCamera, map,
		(400/(float)map.getWidth()*100f), (1070f/(float)map.getHeight()*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k14.setId(14);
		k14.setName("Quaca");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(270f/(float)map.getWidth()*100f), (1025f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		k14.setTerrainList(terrainList);
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(340f/(float)map.getWidth()*100f), (1025/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
		k14.setTerrainList(terrainList);
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(300f/(float)map.getWidth()*100f), (1135f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MEDIUM_CITY, false));
		k14.setTerrainList(terrainList);
		
		Kingdom k15 = new Kingdom(worldConver, gameCamera, map,
			(125/(float)map.getWidth()*100f), (1240f/(float)map.getHeight()*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k15.setId(15);
		k15.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(200f/(float)map.getWidth()*100f), (1210f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		k15.setTerrainList(terrainList);
		
		Kingdom k16 = new Kingdom(worldConver, gameCamera, map,
			(470/(float)map.getWidth()*100f), (1130f/(float)map.getHeight()*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k16.setId(16);
		k16.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(485f/(float)map.getWidth()*100f), (1060f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		k16.setTerrainList(terrainList);
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(555f/(float)map.getWidth()*100f), (1060f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
		k16.setTerrainList(terrainList);
		
		Kingdom k17 = new Kingdom(worldConver, gameCamera, map,
			(480/(float)map.getWidth()*100f), (1235f/(float)map.getHeight()*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k17.setId(17);
		k17.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(380f/(float)map.getWidth()*100f), (1240f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
		k17.setTerrainList(terrainList);
		
		Kingdom k18 = new Kingdom(worldConver, gameCamera, map,
			(575/(float)map.getWidth()*100f), (1340f/(float)map.getHeight()*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k18.setId(18);
		k18.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(510f/(float)map.getWidth()*100f), (1380f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		k18.setTerrainList(terrainList);
		
		//Lye
		Kingdom k19 = new Kingdom(worldConver, gameCamera, map,
		(1030/(float)map.getWidth()*100f), (250f/(float)map.getHeight()*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k19.setId(19);
		k19.setName("Lye");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(925f/(float)map.getWidth()*100f), (360f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		k19.setTerrainList(terrainList);
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(995f/(float)map.getWidth()*100f), (360/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		k19.setTerrainList(terrainList);
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(910f/(float)map.getWidth()*100f), (250f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MEDIUM_CITY, false));
		k19.setTerrainList(terrainList);
		
		Kingdom k20 = new Kingdom(worldConver, gameCamera, map,
			(900/(float)map.getWidth()*100f), (70f/(float)map.getHeight()*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k20.setId(20);
		k20.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(800f/(float)map.getWidth()*100f), (90f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		k20.setTerrainList(terrainList);
		
		Kingdom k21 = new Kingdom(worldConver, gameCamera, map,
			(1445/(float)map.getWidth()*100f), (160f/(float)map.getHeight()*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k21.setId(21);
		k21.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1150f/(float)map.getWidth()*100f), (225f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1220f/(float)map.getWidth()*100f), (225f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1285f/(float)map.getWidth()*100f), (160f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.SMALL_CITY, false));
		k21.setTerrainList(terrainList);
		
		Kingdom k22 = new Kingdom(worldConver, gameCamera, map,
			(1580/(float)map.getWidth()*100f), (180f/(float)map.getHeight()*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k22.setId(22);
		k22.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1690f/(float)map.getWidth()*100f), (255f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1760f/(float)map.getWidth()*100f), (225f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.SMALL_CITY).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.SMALL_CITY, false));
		k22.setTerrainList(terrainList);
		
		Kingdom k23 = new Kingdom(worldConver, gameCamera, map,
			(1270/(float)map.getWidth()*100f), (315f/(float)map.getHeight()*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k23.setId(23);
		k23.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1110f/(float)map.getWidth()*100f), (340f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1180f/(float)map.getWidth()*100f), (340f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
		k23.setTerrainList(terrainList);
		
		Kingdom k24 = new Kingdom(worldConver, gameCamera, map,
			(1520/(float)map.getWidth()*100f), (260f/(float)map.getHeight()*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k24.setId(24);
		k24.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1360f/(float)map.getWidth()*100f), (300f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1430f/(float)map.getWidth()*100f), (300f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		k24.setTerrainList(terrainList);
		
		
		//Genterex
		k1.getBorderList().add(k2);
		k1.getBorderList().add(k4);
		k1.getBorderList().add(k5);
		k1.getBorderList().add(k6);
		k1.getBorderList().add(k6);
		k1.getBorderList().add(k6);
		k1.getBorderList().add(k10);
		k1.getBorderList().add(k11);
		k1.getBorderList().add(k12);
		
		k2.getBorderList().add(k1);
		k2.getBorderList().add(k3);
		k2.getBorderList().add(k5);
		
		k3.getBorderList().add(k2);
		k3.getBorderList().add(k5);
		k3.getBorderList().add(k7);
		k3.getBorderList().add(k8);
		k3.getBorderList().add(k14);
		k3.getBorderList().add(k16);
		
		k4.getBorderList().add(k1);
		k4.getBorderList().add(k6);
		k4.getBorderList().add(k7);
		k4.getBorderList().add(k12);
		k4.getBorderList().add(k13);
		
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
		k8.getBorderList().add(k16);
		
		//Daergonais
		k9.getBorderList().add(k10);
		k9.getBorderList().add(k11);
		
		k10.getBorderList().add(k9);
		k10.getBorderList().add(k11);
		k10.getBorderList().add(k1);
		k10.getBorderList().add(k12);
		
		k11.getBorderList().add(k9);
		k11.getBorderList().add(k10);
		k11.getBorderList().add(k1);
		k11.getBorderList().add(k12);
		
		k12.getBorderList().add(k10);
		k12.getBorderList().add(k1);
		k12.getBorderList().add(k4);
		k12.getBorderList().add(k13);
		
		k13.getBorderList().add(k13);
		k13.getBorderList().add(k12);
		k13.getBorderList().add(k4);
		k13.getBorderList().add(k19);
		
		//Quaca
		k14.getBorderList().add(k3);
		k14.getBorderList().add(k16);
		k14.getBorderList().add(k17);
		k14.getBorderList().add(k15);
		
		k15.getBorderList().add(k14);
		k15.getBorderList().add(k17);
		
		k16.getBorderList().add(k14);
		k16.getBorderList().add(k3);
		k16.getBorderList().add(k8);
		k16.getBorderList().add(k17);
		
		k17.getBorderList().add(k15);
		k17.getBorderList().add(k14);
		k17.getBorderList().add(k16);
		k17.getBorderList().add(k18);
		
		k18.getBorderList().add(k17);
		
		//Lye
		k19.getBorderList().add(k13);
		k19.getBorderList().add(k20);
		k19.getBorderList().add(k21);
		k19.getBorderList().add(k23);
		
		k20.getBorderList().add(k13);
		k20.getBorderList().add(k19);
		
		k21.getBorderList().add(k19);
		k21.getBorderList().add(k22);
		k21.getBorderList().add(k23);
		k21.getBorderList().add(k24);
		
		k22.getBorderList().add(k21);
		k22.getBorderList().add(k24);
		
		k23.getBorderList().add(k19);
		k23.getBorderList().add(k21);
		k23.getBorderList().add(k24);
		
		k24.getBorderList().add(k21);
		k24.getBorderList().add(k22);
		k24.getBorderList().add(k24);
		
		
		kingdomList.add(k1);
		kingdomList.add(k2);
		kingdomList.add(k3);
		kingdomList.add(k4);
		kingdomList.add(k5);
		kingdomList.add(k6);
		kingdomList.add(k7);
		kingdomList.add(k8);
		
		kingdomList.add(k9);
		kingdomList.add(k10);
		kingdomList.add(k11);
		kingdomList.add(k12);
		kingdomList.add(k13);
		
		kingdomList.add(k14);
		kingdomList.add(k15);
		kingdomList.add(k16);
		kingdomList.add(k17);
		kingdomList.add(k18);
		
		kingdomList.add(k19);
		kingdomList.add(k20);
		kingdomList.add(k21);
		kingdomList.add(k22);
		kingdomList.add(k23);
		kingdomList.add(k24);
		
		return kingdomList;
	}

}
