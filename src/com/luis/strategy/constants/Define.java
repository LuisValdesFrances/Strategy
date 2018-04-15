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

	// Game states:
	public static final int ST_GAME_INIT = 100;
	public static final int ST_GAME_RUN = 101;
	public static final int ST_GAME_PAUSE = 102;
	public static final int ST_GAME_CONFIRMATION_QUIT = 103;
	   
	  
	 
	

}
