package com.luis.strategy;

public class RscManager {
	
	public static String[] allText;
	
	public static final byte ENGLISH = 0;
	public static final byte SPANISH = 1;
	public static final byte CATALA = 2;
	//Language text
	public static final int TXT_BLANK = 0;
	public static final int TXT_ENGLISH = TXT_BLANK+1;
	public static final int TXT_SPANISH = TXT_ENGLISH+1;
	public static final int TXT_CATALA = TXT_SPANISH+1;
	
	public static final int TXT_CAMPAING = TXT_CATALA +1;
	public static final int TXT_MULTI_PLAYER = TXT_CAMPAING +1;
	public static final int TXT_ON_LINE = TXT_MULTI_PLAYER +1;
	public static final int TXT_PASS_AND_PLAY = TXT_ON_LINE +1;
	
	public static final int TXT_PLAY = TXT_PASS_AND_PLAY +1;
	public static final int TXT_OPTIONS = TXT_PLAY +1;
	public static final int TXT_INFO=  TXT_OPTIONS+1;
	public static final int TXT_EXIT=  TXT_INFO+1;
	public static final int TXT_SOUND_ON= TXT_EXIT+1;
	public static final int TXT_SOUND_OFF= TXT_SOUND_ON+1;
	public static final int TXT_VIBRATION_ON= TXT_SOUND_OFF+1;
	public static final int TXT_VIBRATION_OFF= TXT_VIBRATION_ON+1;
	public static final int TXT_WANT_EXIT_GAME= TXT_VIBRATION_OFF+1;
	public static final int TXT_NO= TXT_WANT_EXIT_GAME+1;
	public static final int TXT_YES= TXT_NO+1;
	public static final int TXT_HELP= TXT_YES+1;
	public static final int TXT_ABOUT= TXT_HELP+1;
	public static final int TXT_HELP_DESCRIP= TXT_ABOUT+1;
	public static final int TXT_ABOUT_DESCRIP= TXT_HELP_DESCRIP+1;
	public static final int TXT_POINTS= TXT_ABOUT_DESCRIP+1;
	public static final int TXT_RECORD= TXT_POINTS+1;
	public static final int TXT_CONTINUE= TXT_RECORD+1;
	public static final int TXT_LEAVE= TXT_CONTINUE+1;
	public static final int TXT_NEW_RECORD= TXT_LEAVE+1;
	public static final int TXT_RETURN_MENU= TXT_NEW_RECORD+1;
	
	public static final int TXT_GAME_PLAIN= TXT_RETURN_MENU+1;
	public static final int TXT_GAME_FOREST= TXT_GAME_PLAIN+1;
	public static final int TXT_GAME_MONTAIN= TXT_GAME_FOREST+1;
	public static final int TXT_GAME_SMALL_CITY= TXT_GAME_MONTAIN+1;
	public static final int TXT_GAME_MEDIUM_CITY= TXT_GAME_SMALL_CITY+1;
	public static final int TXT_GAME_BIG_CITY= TXT_GAME_MEDIUM_CITY+1;
	public static final int TXT_GAME_CASTLE= TXT_GAME_BIG_CITY+1;
	public static final int TXT_GAME_INFANTRY= TXT_GAME_CASTLE+1;
	public static final int TXT_GAME_KNIGHTS= TXT_GAME_INFANTRY+1;
	public static final int TXT_GAME_HARASSERS= TXT_GAME_KNIGHTS+1;
	public static final int TXT_GAME_SIEGE= TXT_GAME_HARASSERS+1;
	
	public static final int TXT_GAME_RESULT= TXT_GAME_SIEGE+1;
	public static final int TXT_GAME_PLAYER= TXT_GAME_RESULT+1;
	public static final int TXT_GAME_BIG_DEFEAT= TXT_GAME_PLAYER+1;
	public static final int TXT_GAME_DEFEAT= TXT_GAME_BIG_DEFEAT+1;
	public static final int TXT_GAME_VICTORY= TXT_GAME_DEFEAT+1;
	public static final int TXT_GAME_BIG_VICTORY= TXT_GAME_VICTORY+1;
	public static final int TXT_GAME_THE_ARMY_FROM_PLAYER= TXT_GAME_BIG_VICTORY+1;
	public static final int TXT_GAME_HAS_BEEN_DESTROYED= TXT_GAME_THE_ARMY_FROM_PLAYER+1;
	public static final int TXT_GAME_ATTACKER_LOST= TXT_GAME_HAS_BEEN_DESTROYED+1;
	public static final int TXT_GAME_DEFENSER_LOST= TXT_GAME_ATTACKER_LOST+1;
	public static final int TXT_GAME_LOSSES= TXT_GAME_DEFENSER_LOST+1;
	public static final int TXT_GAME_NEW_ARMY= TXT_GAME_LOSSES+1;
	public static final int TXT_GAME_ECONOMY= TXT_GAME_NEW_ARMY+1;
	public static final int TXT_GAME_EARNING= TXT_GAME_ECONOMY+1;
	public static final int TXT_GAME_SALARY= TXT_GAME_EARNING+1;
	public static final int TXT_GAME_COST_OF_TROOPS= TXT_GAME_SALARY+1;
	public static final int TXT_GAME_COST_PLAYER= TXT_GAME_COST_OF_TROOPS+1;
	public static final int TXT_GAME_IS_WINNER= TXT_GAME_COST_PLAYER+1;
	
	
	public static final int TOTAL_LINES_TXT = TXT_GAME_IS_WINNER;
	
	
	public static void loadLanguage(int language) {
		String lan = "";
		switch (language) {
		case ENGLISH:
			lan = "/texts/english.txt";
			break;
		case SPANISH:
			lan = "/texts/spanish.txt";
			break;
		case CATALA:
			lan = "/texts/catala.txt";
			break;
			}
		try {
			StreamReader reader;
			reader = new StreamReader(lan, TOTAL_LINES_TXT + 1);
			allText = reader.read();
//			for (int i = 0; i < ms_sAllTexts.length; i++) {
//				System.out.println("Cargado " + ms_sAllTexts[i]);
//			 }
		} catch (Exception e) {
			System.out.println("No se han podido cargar los textos");
		}
	}
	
	
	
	
	

}
