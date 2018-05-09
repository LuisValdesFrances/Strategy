package com.luis.strategy.data;

import java.util.ArrayList;
import java.util.List;

import com.luis.lgameengine.gameutils.gameworld.GameCamera;
import com.luis.lgameengine.gameutils.gameworld.WorldConver;
import com.luis.strategy.GfxManager;
import com.luis.strategy.constants.GameParams;
import com.luis.strategy.map.Kingdom;
import com.luis.strategy.map.MapObject;
import com.luis.strategy.map.Terrain;

public class DataKingdom {
	
	/**Establece los dos reinos con los que se empieza por defecto
	 * Dimension 0: mapa
	 * Dimension 1: jugador
	 * Dimension 2. kingdoms
	*/
	public static final int[][][] INIT_MAP_DATA =
		{
		{{1,2}, {7,8}},
		{{1,2}, {14,15}, {19,21}, {25,26}, {36,37}, {51,53}}
		};
	
	public static final int[][] MAP_PARTS = {{4,2}, {4,4}};
	
	public static List<Kingdom> getGenterex(WorldConver worldConver, GameCamera gameCamera, MapObject map){
		
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
			(970f/(float)map.getWidth()*120f), (250f/(float)map.getHeight()*120f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k4.setId(4);
		k4.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(800f/(float)map.getWidth()*120f), (235f/(float)map.getHeight()*120f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(860f/(float)map.getWidth()*120f), (235f/(float)map.getHeight()*120f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(920f/(float)map.getWidth()*120f), (235f/(float)map.getHeight()*120f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
		k4.setTerrainList(terrainList);
		
		Kingdom k5 = new Kingdom(worldConver, gameCamera, map,
			(660f/(float)map.getWidth()*120f), (290f/(float)map.getHeight()*120f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k5.setId(5);
		k5.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(608f/(float)map.getWidth()*120f), (370f/(float)map.getHeight()*120f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(668f/(float)map.getWidth()*120f), (370f/(float)map.getHeight()*120f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
		k5.setTerrainList(terrainList);
		
		Kingdom k6 = new Kingdom(worldConver, gameCamera, map,
			(770f/(float)map.getWidth()*120f), (270f/(float)map.getHeight()*120f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k6.setId(6);
		k6.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(764f/(float)map.getWidth()*120f), (340f/(float)map.getHeight()*120f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(821f/(float)map.getWidth()*120f), (340f/(float)map.getHeight()*120f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
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
			(720f/(float)map.getWidth()*120f), (595f/(float)map.getHeight()*120f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(770f/(float)map.getWidth()*120f), (595f/(float)map.getHeight()*120f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(820f/(float)map.getWidth()*120f), (595f/(float)map.getHeight()*120f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
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
	
public static List<Kingdom> getCrom(WorldConver worldConver, GameCamera gameCamera, MapObject map){
		
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
			(730f/(float)map.getWidth()*100f), (530f/(float)map.getHeight()*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
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
			(660f/(float)map.getWidth()*100f), (570f/(float)map.getHeight()*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
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
			(640f/(float)map.getWidth()*100f), (840f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(710f/(float)map.getWidth()*100f), (840f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(780f/(float)map.getWidth()*100f), (840f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(650f/(float)map.getWidth()*100f), (750f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.BIG_CITY).getWidth(), GfxManager.imgTerrain.get(GameParams.BIG_CITY).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MEDIUM_CITY, false));
		k7.setTerrainList(terrainList);
		
		Kingdom k8 = new Kingdom(worldConver, gameCamera, map,
			(830f/(float)map.getWidth()*100f), (940f/(float)map.getHeight()*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k8.setId(8);
		k8.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(620f/(float)map.getWidth()*100f), (980f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(690f/(float)map.getWidth()*100f), (980f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(760f/(float)map.getWidth()*100f), (980f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
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
			(695f/(float)map.getWidth()*100f), (400f/(float)map.getHeight()*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k12.setId(12);
		k12.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(635f/(float)map.getWidth()*100f), (375f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.FOREST).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		k12.setTerrainList(terrainList);
		
		Kingdom k13 = new Kingdom(worldConver, gameCamera, map,
			(780f/(float)map.getWidth()*100f), (410f/(float)map.getHeight()*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k13.setId(13);
		k13.setName("Daergonais");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(730f/(float)map.getWidth()*100f), (330f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.FOREST).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(800f/(float)map.getWidth()*100f), (330f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.FOREST).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
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
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(340f/(float)map.getWidth()*100f), (1025/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
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
			(490f/(float)map.getWidth()*100f), (1380f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
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
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(995f/(float)map.getWidth()*100f), (360/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
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
			(1440/(float)map.getWidth()*100f), (160f/(float)map.getHeight()*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k21.setId(21);
		k21.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1150f/(float)map.getWidth()*100f), (200f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1220f/(float)map.getWidth()*100f), (200f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1290f/(float)map.getWidth()*100f), (200f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1250f/(float)map.getWidth()*100f), (140f/(float)map.getHeight()*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.SMALL_CITY, false));
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
		
		//Crom
		Kingdom k25 = new Kingdom(worldConver, gameCamera, map,
			(1575f/map.getWidth()*100), (660f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k25.setId(25);
		k25.setName("Crom");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1410f/map.getWidth()*100), (760f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1480f/map.getWidth()*100), (760f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1550f/map.getWidth()*100), (760f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.FOREST).getWidth(), GfxManager.imgTerrain.get(GameParams.FOREST).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1450f/map.getWidth()*100), (620f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.BIG_CITY).getWidth(), GfxManager.imgTerrain.get(GameParams.BIG_CITY).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.BIG_CITY, false));
		k25.setTerrainList(terrainList);
		
		Kingdom k26 = new Kingdom(worldConver, gameCamera, map,
			(1300f/map.getWidth()*100), (450f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k26.setId(26);
		k26.setName("Cromgast");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1020f/map.getWidth()*100), (470f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1080f/map.getWidth()*100), (470f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1190f/map.getWidth()*100), (440f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.FOREST).getWidth(), GfxManager.imgTerrain.get(GameParams.FOREST).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MEDIUM_CITY, false));
		k26.setTerrainList(terrainList);
		
		Kingdom k27 = new Kingdom(worldConver, gameCamera, map,
			(1410f/map.getWidth()*100), (460f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k27.setId(27);
		k27.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1580f/map.getWidth()*90), (380f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1650f/map.getWidth()*90), (380f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
		k27.setTerrainList(terrainList);
		
		Kingdom k28 = new Kingdom(worldConver, gameCamera, map,
			(1580f/map.getWidth()*100), (500f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k28.setId(28);
		k28.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1510f/map.getWidth()*100), (480f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
		k28.setTerrainList(terrainList);
		
		Kingdom k29 = new Kingdom(worldConver, gameCamera, map,
			(1055f/map.getWidth()*100), (580f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k29.setId(29);
		k29.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(910f/map.getWidth()*100), (675f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(980f/map.getWidth()*100), (675f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
		k29.setTerrainList(terrainList);
		
		Kingdom k30 = new Kingdom(worldConver, gameCamera, map,
			(1195f/map.getWidth()*100), (600f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k30.setId(30);
		k30.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1280f/map.getWidth()*100), (700f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		k30.setTerrainList(terrainList);
		
		Kingdom k31 = new Kingdom(worldConver, gameCamera, map,
			(1680f/map.getWidth()*100), (650f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k31.setId(31);
		k31.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1745f/map.getWidth()*100), (675f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1815f/map.getWidth()*100), (675f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		k31.setTerrainList(terrainList);
		
		Kingdom k32 = new Kingdom(worldConver, gameCamera, map,
			(1070f/map.getWidth()*100), (780f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k32.setId(32);
		k32.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1050f/map.getWidth()*100), (920f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1120f/map.getWidth()*100), (920f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(985f/map.getWidth()*100), (800f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MEDIUM_CITY, false));
		k32.setTerrainList(terrainList);
		
		Kingdom k33 = new Kingdom(worldConver, gameCamera, map,
			(1210f/map.getWidth()*100), (795f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k33.setId(33);
		k33.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1210f/map.getWidth()*100), (890f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1280f/map.getWidth()*100), (890f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1350f/map.getWidth()*100), (820f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.SMALL_CITY, false));
		k33.setTerrainList(terrainList);
		
		Kingdom k34 = new Kingdom(worldConver, gameCamera, map,
			(1550f/map.getWidth()*100), (1030f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k34.setId(34);
		k34.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1620f/map.getWidth()*100), (1040f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1690f/map.getWidth()*100), (1040f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1750f/map.getWidth()*100), (1040f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1530f/map.getWidth()*100), (930f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MEDIUM_CITY, false));
		k34.setTerrainList(terrainList);
		
		Kingdom k35 = new Kingdom(worldConver, gameCamera, map,
			(1650f/map.getWidth()*100), (795f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k35.setId(35);
		k35.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1700f/map.getWidth()*100), (890f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		k35.setTerrainList(terrainList);
		
		//Gapeangue
		Kingdom k36 = new Kingdom(worldConver, gameCamera, map,
			(1285f/map.getWidth()*100), (1030f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k36.setId(36);
		k36.setName("Gapeangue");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1260f/map.getWidth()*100), (1140f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1330f/map.getWidth()*100), (1140f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1400f/map.getWidth()*100), (1140f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.FOREST).getWidth(), GfxManager.imgTerrain.get(GameParams.FOREST).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1145f/map.getWidth()*100), (1085f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.BIG_CITY).getWidth(), GfxManager.imgTerrain.get(GameParams.BIG_CITY).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MEDIUM_CITY, false));
		k36.setTerrainList(terrainList);
		
		Kingdom k37 = new Kingdom(worldConver, gameCamera, map,
			(1015f/map.getWidth()*100), (1065f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k37.setId(37);
		k37.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(930f/map.getWidth()*100), (1010f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		k37.setTerrainList(terrainList);
		
		Kingdom k38 = new Kingdom(worldConver, gameCamera, map,
			(1045f/map.getWidth()*100), (1200f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k38.setId(38);
		k38.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(900f/map.getWidth()*100), (1165f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(970f/map.getWidth()*100), (1165f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(830f/map.getWidth()*100), (1150f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.SMALL_CITY, false));
		k38.setTerrainList(terrainList);
		
		Kingdom k39 = new Kingdom(worldConver, gameCamera, map,
			(815f/map.getWidth()*100), (1275f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k39.setId(39);
		k39.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(750f/map.getWidth()*100), (1250f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		k39.setTerrainList(terrainList);
		
		Kingdom k40 = new Kingdom(worldConver, gameCamera, map,
			(1355f/map.getWidth()*100), (1310f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k40.setId(40);
		k40.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1180f/map.getWidth()*100), (1260f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1140f/map.getWidth()*100), (1320f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.SMALL_CITY, false));
		k40.setTerrainList(terrainList);
		
		Kingdom k41 = new Kingdom(worldConver, gameCamera, map,
			(1440f/map.getWidth()*100), (1360f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k41.setId(41);
		k41.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1370f/map.getWidth()*100), (1270f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1440f/map.getWidth()*100), (1270f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		k41.setTerrainList(terrainList);
		
		Kingdom k42 = new Kingdom(worldConver, gameCamera, map,
			(735f/map.getWidth()*100), (1085f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k42.setId(42);
		k42.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(600f/map.getWidth()*100), (1160f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(670f/map.getWidth()*100), (1160f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		k42.setTerrainList(terrainList);
		
		//Myr
		Kingdom k43 = new Kingdom(worldConver, gameCamera, map,
			(1600f/map.getWidth()*100), (1235f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k43.setId(43);
		k43.setName("Myr");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1750f/map.getWidth()*100), (1240f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1820f/map.getWidth()*100), (1240f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1660f/map.getWidth()*100), (1160f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.SMALL_CITY, false));
		k43.setTerrainList(terrainList);
		
		
		Kingdom k44 = new Kingdom(worldConver, gameCamera, map,
			(1830f/map.getWidth()*100), (1370f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k44.setId(44);
		k44.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1720f/map.getWidth()*100), (1375f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		k44.setTerrainList(terrainList);
		
		Kingdom k45 = new Kingdom(worldConver, gameCamera, map,
			(2055f/map.getWidth()*100), (1315f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k45.setId(45);
		k45.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1980f/map.getWidth()*100), (1315f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		k45.setTerrainList(terrainList);
		
		//Montag
		Kingdom k46 = new Kingdom(worldConver, gameCamera, map,
			(1920f/map.getWidth()*100), (450f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k46.setId(46);
		k46.setName("Montag");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1955f/map.getWidth()*100), (555f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(2025f/map.getWidth()*100), (555f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(2005f/map.getWidth()*100), (440f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.SMALL_CITY, false));
		k46.setTerrainList(terrainList);
		
		Kingdom k47 = new Kingdom(worldConver, gameCamera, map,
			(1730f/map.getWidth()*100), (500f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k47.setId(47);
		k47.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1610f/map.getWidth()*100), (370f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1680f/map.getWidth()*100), (370f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1750f/map.getWidth()*100), (370f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		k47.setTerrainList(terrainList);
		
		Kingdom k48 = new Kingdom(worldConver, gameCamera, map,
			(2120f/map.getWidth()*100), (370f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k48.setId(48);
		k48.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(2210f/map.getWidth()*100), (430f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(2280f/map.getWidth()*100), (430f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		k48.setTerrainList(terrainList);
		
		Kingdom k49 = new Kingdom(worldConver, gameCamera, map,
			(2130f/map.getWidth()*100), (550f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k49.setId(49);
		k49.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(2155f/map.getWidth()*100), (640f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
		k49.setTerrainList(terrainList);
		
		Kingdom k50 = new Kingdom(worldConver, gameCamera, map,
			(2280f/map.getWidth()*100), (525f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k50.setId(50);
		k50.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(2260f/map.getWidth()*100), (635f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(2210f/map.getWidth()*100), (570f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.SMALL_CITY, false));
		k50.setTerrainList(terrainList);
		
		
		//Levantia
		Kingdom k51 = new Kingdom(worldConver, gameCamera, map,
			(2015f/map.getWidth()*100), (950f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k51.setId(51);
		k51.setName("Levantia");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(2020f/map.getWidth()*100), (1050f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(2090f/map.getWidth()*100), (1050f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(2160f/map.getWidth()*100), (1050f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(2140f/map.getWidth()*100), (950f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MEDIUM_CITY, false));
		k51.setTerrainList(terrainList);
		
		Kingdom k52 = new Kingdom(worldConver, gameCamera, map,
			(2170f/map.getWidth()*100), (750f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k52.setId(52);
		k52.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(2240f/map.getWidth()*100), (760f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(2310f/map.getWidth()*100), (760f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		k52.setTerrainList(terrainList);
		
		Kingdom k53 = new Kingdom(worldConver, gameCamera, map,
			(1935f/map.getWidth()*100), (660f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k53.setId(53);
		k53.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1920f/map.getWidth()*100), (765f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1975f/map.getWidth()*100), (830f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.SMALL_CITY, false));
		k53.setTerrainList(terrainList);
		
		Kingdom k54 = new Kingdom(worldConver, gameCamera, map,
			(1830f/map.getWidth()*100), (760f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k54.setId(54);
		k54.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1755f/map.getWidth()*100), (785f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		k54.setTerrainList(terrainList);
		
		Kingdom k55 = new Kingdom(worldConver, gameCamera, map,
			(1875f/map.getWidth()*100), (960f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k55.setId(55);
		k55.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1810f/map.getWidth()*100), (880f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1880f/map.getWidth()*100), (880f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
		k55.setTerrainList(terrainList);
		
		Kingdom k56 = new Kingdom(worldConver, gameCamera, map,
			(1885f/map.getWidth()*100), (1110f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k56.setId(56);
		k56.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1970f/map.getWidth()*100), (1140f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(1965f/map.getWidth()*100), (1190f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.SMALL_CITY, false));
		k56.setTerrainList(terrainList);
		
		Kingdom k57 = new Kingdom(worldConver, gameCamera, map,
			(2145f/map.getWidth()*100), (1180f/map.getHeight()*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
		k57.setId(57);
		k57.setName("");
		//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
		terrainList = new ArrayList<Terrain>();
		terrainList.add(new Terrain(worldConver, gameCamera, map,
			(2070f/map.getWidth()*100), (1175f/map.getHeight()*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
		k57.setTerrainList(terrainList);
		
		
		
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
		k4.getBorderList().add(k26);
		k4.getBorderList().add(k29);
		
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
		k7.getBorderList().add(k32);
		
		k8.getBorderList().add(k7);
		k8.getBorderList().add(k3);
		k8.getBorderList().add(k16);
		k8.getBorderList().add(k42);
		
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
		
		k12.getBorderList().add(k10);
		k12.getBorderList().add(k1);
		k12.getBorderList().add(k4);
		k12.getBorderList().add(k13);
		
		k13.getBorderList().add(k12);
		k13.getBorderList().add(k20);
		k13.getBorderList().add(k19);
		k13.getBorderList().add(k4);
		k13.getBorderList().add(k26);
		
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
		k16.getBorderList().add(k42);
		
		k17.getBorderList().add(k15);
		k17.getBorderList().add(k14);
		k17.getBorderList().add(k16);
		k17.getBorderList().add(k18);
		k17.getBorderList().add(k42);
		
		k18.getBorderList().add(k17);
		
		//Lye
		k19.getBorderList().add(k13);
		k19.getBorderList().add(k20);
		k19.getBorderList().add(k21);
		k19.getBorderList().add(k23);
		k19.getBorderList().add(k26);
		
		k20.getBorderList().add(k13);
		k20.getBorderList().add(k19);
		
		k21.getBorderList().add(k19);
		k21.getBorderList().add(k22);
		k21.getBorderList().add(k23);
		k21.getBorderList().add(k24);
		
		k22.getBorderList().add(k21);
		k22.getBorderList().add(k24);
		k22.getBorderList().add(k47);
		
		k23.getBorderList().add(k19);
		k23.getBorderList().add(k21);
		k23.getBorderList().add(k24);
		k23.getBorderList().add(k27);
		k23.getBorderList().add(k26);
		
		k24.getBorderList().add(k21);
		k24.getBorderList().add(k22);
		k24.getBorderList().add(k23);
		k24.getBorderList().add(k27);
		k24.getBorderList().add(k47);
		
		//Crom
		k25.getBorderList().add(k26);
		k25.getBorderList().add(k27);
		k25.getBorderList().add(k28);
		k25.getBorderList().add(k31);
		k25.getBorderList().add(k35);
		k25.getBorderList().add(k34);
		k25.getBorderList().add(k33);
		k25.getBorderList().add(k30);
		
		k26.getBorderList().add(k30);
		k26.getBorderList().add(k29);
		k26.getBorderList().add(k4);
		k26.getBorderList().add(k13);
		k26.getBorderList().add(k19);
		k26.getBorderList().add(k23);
		k26.getBorderList().add(k27);
		k26.getBorderList().add(k25);
		
		k27.getBorderList().add(k28);
		k27.getBorderList().add(k25);
		k27.getBorderList().add(k26);
		k27.getBorderList().add(k23);
		k27.getBorderList().add(k24);
		k27.getBorderList().add(k47);
		
		k28.getBorderList().add(k31);
		k28.getBorderList().add(k25);
		k28.getBorderList().add(k27);
		k28.getBorderList().add(k47);
		
		k29.getBorderList().add(k4);
		k29.getBorderList().add(k26);
		k29.getBorderList().add(k30);
		k29.getBorderList().add(k32);
		k29.getBorderList().add(k7);
		
		k30.getBorderList().add(k33);
		k30.getBorderList().add(k32);
		k30.getBorderList().add(k29);
		k30.getBorderList().add(k26);
		k30.getBorderList().add(k25);
		
		k31.getBorderList().add(k35);
		k31.getBorderList().add(k25);
		k31.getBorderList().add(k28);
		k31.getBorderList().add(k47);
		k31.getBorderList().add(k53);
		k31.getBorderList().add(k54);
		
		k32.getBorderList().add(k7);
		k32.getBorderList().add(k29);
		k32.getBorderList().add(k30);
		k32.getBorderList().add(k33);
		
		k33.getBorderList().add(k32);
		k33.getBorderList().add(k30);
		k33.getBorderList().add(k25);
		k33.getBorderList().add(k34);
		k33.getBorderList().add(k36);
		
		k34.getBorderList().add(k33);
		k34.getBorderList().add(k25);
		k34.getBorderList().add(k35);
		k34.getBorderList().add(k36);
		k34.getBorderList().add(k43);
		k34.getBorderList().add(k55);
		
		k35.getBorderList().add(k34);
		k35.getBorderList().add(k25);
		k35.getBorderList().add(k31);
		k35.getBorderList().add(k54);
		k35.getBorderList().add(k55);
		
		//Gapeangue
		k36.getBorderList().add(k37);
		k36.getBorderList().add(k32);
		k36.getBorderList().add(k33);
		k36.getBorderList().add(k34);
		k36.getBorderList().add(k41);
		k36.getBorderList().add(k40);
		k36.getBorderList().add(k38);
		k36.getBorderList().add(k43);
		
		k37.getBorderList().add(k32);
		k37.getBorderList().add(k36);
		k37.getBorderList().add(k38);
		
		k38.getBorderList().add(k39);
		k38.getBorderList().add(k42);
		k38.getBorderList().add(k37);
		k38.getBorderList().add(k36);
		k38.getBorderList().add(k40);
		k38.getBorderList().add(k37);
		k38.getBorderList().add(k36);
		
		k39.getBorderList().add(k18);
		k39.getBorderList().add(k38);
		
		k40.getBorderList().add(k38);
		k40.getBorderList().add(k36);
		k40.getBorderList().add(k41);
		
		k41.getBorderList().add(k40);
		k41.getBorderList().add(k36);
		k41.getBorderList().add(k43);
		k41.getBorderList().add(k44);
		
		k42.getBorderList().add(k17);
		k42.getBorderList().add(k16);
		k42.getBorderList().add(k8);
		k42.getBorderList().add(k38);
		
		//Myr
		k43.getBorderList().add(k41);
		k43.getBorderList().add(k36);
		k43.getBorderList().add(k34);
		k43.getBorderList().add(k44);
		k43.getBorderList().add(k45);
		k43.getBorderList().add(k55);
		k43.getBorderList().add(k56);
		
		k44.getBorderList().add(k41);
		k44.getBorderList().add(k43);
		k44.getBorderList().add(k45);
		
		k45.getBorderList().add(k44);
		k45.getBorderList().add(k43);
		k45.getBorderList().add(k56);
		k45.getBorderList().add(k57);
		
		//Montag
		k46.getBorderList().add(k47);
		k46.getBorderList().add(k48);
		k46.getBorderList().add(k49);
		k46.getBorderList().add(k53);
		
		k47.getBorderList().add(k22);
		k47.getBorderList().add(k46);
		k47.getBorderList().add(k31);
		k47.getBorderList().add(k28);
		k47.getBorderList().add(k27);
		k47.getBorderList().add(k24);
		k47.getBorderList().add(k53);
		
		k48.getBorderList().add(k46);
		k48.getBorderList().add(k49);
		k48.getBorderList().add(k50);
		
		k49.getBorderList().add(k46);
		k49.getBorderList().add(k48);
		k49.getBorderList().add(k50);
		k49.getBorderList().add(k52);
		
		k50.getBorderList().add(k49);
		k50.getBorderList().add(k48);
		k50.getBorderList().add(k52);
		
		//Levantia
		k51.getBorderList().add(k57);
		k51.getBorderList().add(k56);
		k51.getBorderList().add(k55);
		k51.getBorderList().add(k53);
		
		k52.getBorderList().add(k50);
		k52.getBorderList().add(k49);
		k52.getBorderList().add(k53);
		
		k53.getBorderList().add(k52);
		k53.getBorderList().add(k49);
		k53.getBorderList().add(k46);
		k53.getBorderList().add(k47);
		k53.getBorderList().add(k31);
		k53.getBorderList().add(k54);
		k53.getBorderList().add(k55);
		k53.getBorderList().add(k51);
		
		k54.getBorderList().add(k53);
		k54.getBorderList().add(k31);
		k54.getBorderList().add(k35);
		k54.getBorderList().add(k55);
		
		k55.getBorderList().add(k53);
		k55.getBorderList().add(k54);
		k55.getBorderList().add(k35);
		k55.getBorderList().add(k34);
		k55.getBorderList().add(k43);
		k55.getBorderList().add(k56);
		k55.getBorderList().add(k51);
		
		k56.getBorderList().add(k51);
		k56.getBorderList().add(k55);
		k56.getBorderList().add(k43);
		k56.getBorderList().add(k45);
		k56.getBorderList().add(k57);
		
		k57.getBorderList().add(k51);
		k57.getBorderList().add(k56);
		k57.getBorderList().add(k45);
		
		
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
		
		kingdomList.add(k25);
		kingdomList.add(k26);
		kingdomList.add(k27);
		kingdomList.add(k28);
		kingdomList.add(k29);
		kingdomList.add(k30);
		kingdomList.add(k31);
		kingdomList.add(k32);
		kingdomList.add(k33);
		kingdomList.add(k34);
		kingdomList.add(k35);
		
		kingdomList.add(k36);
		kingdomList.add(k37);
		kingdomList.add(k38);
		kingdomList.add(k39);
		kingdomList.add(k40);
		kingdomList.add(k41);
		kingdomList.add(k42);
		
		kingdomList.add(k43);
		kingdomList.add(k44);
		kingdomList.add(k45);
		
		kingdomList.add(k46);
		kingdomList.add(k47);
		kingdomList.add(k48);
		kingdomList.add(k49);
		kingdomList.add(k50);
		
		kingdomList.add(k51);
		kingdomList.add(k52);
		kingdomList.add(k53);
		kingdomList.add(k54);
		kingdomList.add(k55);
		kingdomList.add(k56);
		kingdomList.add(k57);
		
		return kingdomList;
	}

}
