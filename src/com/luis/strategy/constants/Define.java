package com.luis.strategy.constants;

import com.luis.lgameengine.gameutils.Settings;

/**
*
* @author Luis Valdes Frances
*/
public class Define {
	
	public static final int SIZEX = (int) Settings.getInstance().getScreenWidth();
	public static final int SIZEY = (int) Settings.getInstance().getScreenHeight();

	public static final int SIZEX2 = SIZEX >> 1;
	public static final int SIZEY2 = SIZEY >> 1;
	public static final int SIZEX4 = SIZEX >> 2;
	public static final int SIZEY4 = SIZEY >> 2;
	public static final int SIZEX8 = SIZEX >> 3;
	public static final int SIZEY8 = SIZEY >> 3;
	public static final int SIZEX12 = SIZEX / 12;
	public static final int SIZEY12 = SIZEY / 12;
	public static final int SIZEX16 = SIZEX >> 4;
	public static final int SIZEY16 = SIZEY >> 4;
	public static final int SIZEX24 = SIZEX / 24;
	public static final int SIZEY24 = SIZEY / 24;
	public static final int SIZEX32 = SIZEX >> 5;
	public static final int SIZEY32 = SIZEY >> 5;
	public static final int SIZEX64 = SIZEX >> 6;
	public static final int SIZEY64 = SIZEY >> 6;

	public static final int SCR_MIDLE = (SIZEX + SIZEY) / 2;

	public static final int FX_POINT = 8;

	// Menu States:
	public static final int ST_MENU_LOGO = 0;
	public static final int ST_MENU_ASK_LANGUAGE = 1;
	public static final int ST_MENU_ASK_SOUND = 2;

	public static final int ST_MENU_MAIN = 3;
	public static final int ST_MENU_OPTIONS = 4;
	public static final int ST_MENU_MORE = 5;
	public static final int ST_MENU_EXIT = 6;
	public static final int ST_MENU_HELP = 7;
	public static final int ST_MENU_ABOUT = 8;
	public static final int ST_MENU_SELECT_GAME = 9;
	public static final int ST_MENU_SELECT_MAP = 10;
	public static final int ST_MENU_CONFIG_MAP = 11;
	public static final int ST_MENU_CAMPAING = 12;
	
	public static final int ST_MENU_ON_LINE_START = 13;
	public static final int ST_MENU_ON_LINE_CREATE_USER = 14;
	public static final int ST_MENU_ON_LINE_LOGIN = 15;
	public static final int ST_MENU_ON_LINE_LIST_ALL_GAME = 16;
	public static final int ST_MENU_ON_LINE_LIST_WAIT_GAME = 17;
	public static final int ST_MENU_ON_LINE_CREATE_SCENE = 18;
	
	public static final int ST_TEST = 50;

	// Game states:
	public static final int ST_GAME_INIT = 100;
	public static final int ST_GAME_CONTINUE = 101;
	public static final int ST_GAME_RUN = 102;
	public static final int ST_GAME_PAUSE = 103;
	public static final int ST_GAME_CONFIRMATION_QUIT = 104;
	
	
	//public static final String SERVER_URL = "http://172.104.228.65:8080/KingServer/";//Online
	public static final String SERVER_URL = "http://192.168.1.110:8080/KingServer/";//Local
	   
	// Nombre del fichero donde se guardaran y cargaran los datos:
	public static final String DATA_USER = "strategyDataUser";
	public static final String DATA_PASS_AND_PLAY = "strategyDataPassAndPlay";
	public static final String DATA_CAMPAING = "strategyDataCampaing";
	public static final int MAX_NAME_CHAR = 14;
	public static final int MAX_PASSWORD_CHAR = 20;
	public static final int MIN_NAME_CHAR = 4;
	public static final int MIN_PASSWORD_CHAR = 8;
	  
	 
	

}
