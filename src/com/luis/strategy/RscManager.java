package com.luis.strategy;

public class RscManager {
	
	public static String[] allText;
	
	
	public static final byte ENGLISH = 0;
	public static final byte SPANISH = 1;
	public static final byte CATALA = 2;
	
	
	public static final int TXT_BLANK = 0;
	public static final byte TXT_CITA_1 = TXT_BLANK+1;
	public static final byte TXT_CITA_2 = TXT_CITA_1+1;
	public static final byte TXT_CITA_3 = TXT_CITA_2+1;
	public static final byte TXT_CITA_4 = TXT_CITA_3+1;
	public static final byte TXT_CITA_5 = TXT_CITA_4+1;
	public static final byte TXT_CITA_6 = TXT_CITA_5+1;
	
	public static final byte TXT_AUTOR_1 = TXT_CITA_6+1;
	public static final byte TXT_AUTOR_2 = TXT_AUTOR_1+1;
	public static final byte TXT_AUTOR_3 = TXT_AUTOR_2+1;
	public static final byte TXT_AUTOR_4 = TXT_AUTOR_3+1;
	public static final byte TXT_AUTOR_5 = TXT_AUTOR_4+1;
	public static final byte TXT_AUTOR_6 = TXT_AUTOR_5+1;
	
	//Language text
	public static final int TXT_ENGLISH = TXT_AUTOR_6+1;
	public static final int TXT_SPANISH = TXT_ENGLISH+1;
	public static final int TXT_CATALA = TXT_SPANISH+1;
	
	public static final int TXT_CAMPAING = TXT_CATALA +1;
	public static final int TXT_CONTINUE = TXT_CAMPAING +1;
	public static final int TXT_START = TXT_CONTINUE +1;
	public static final int TXT_MULTI_PLAYER = TXT_START +1;
	public static final int TXT_ON_LINE = TXT_MULTI_PLAYER +1;
	public static final int TXT_PASS_AND_PLAY = TXT_ON_LINE +1;
	public static final int TXT_NEW_ACOUNT = TXT_PASS_AND_PLAY +1;
	public static final int TXT_LOGIN = TXT_NEW_ACOUNT +1;
	
	public static final int TXT_PLAY = TXT_LOGIN +1;
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
	public static final int TXT_SELECT_MAP= TXT_ABOUT_DESCRIP+1;
	public static final int TXT_CONFIG_MAP= TXT_SELECT_MAP+1;
	public static final int TXT_MAP_CONFIG_HUMAN = TXT_CONFIG_MAP+1;
	public static final int TXT_MAP_CONFIG_IA = TXT_MAP_CONFIG_HUMAN+1;
	public static final int TXT_POINTS= TXT_MAP_CONFIG_IA+1;
	public static final int TXT_RECORD= TXT_POINTS+1;
	public static final int TXT_CONTINUE_GAME= TXT_RECORD+1;
	public static final int TXT_QUIT= TXT_CONTINUE_GAME+1;
	public static final int TXT_NEW_RECORD= TXT_QUIT+1;
	public static final int TXT_RETURN_MENU= TXT_NEW_RECORD+1;
	public static final int TXT_NOTIFICATIONS= TXT_RETURN_MENU+1;
	
	public static final int TXT_NAME= TXT_NOTIFICATIONS+1;
	public static final int TXT_PASS= TXT_NAME+1;
	public static final int TXT_REPEAT_PASS= TXT_PASS+1;
	public static final int TXT_PASS_NO_MATCH= TXT_REPEAT_PASS+1;
	public static final int TXT_TRY_ANOTHER_NAME= TXT_PASS_NO_MATCH+1;
	public static final int TXT_INCORRECT_USER_NAME= TXT_TRY_ANOTHER_NAME+1;
	public static final int TXT_CONNECTION_ERROR= TXT_INCORRECT_USER_NAME+1;
	public static final int TXT_SERVER_ERROR= TXT_CONNECTION_ERROR+1;
	public static final int TXT_NO_CONNECTION= TXT_SERVER_ERROR+1;
	
	public static final int TXT_ACCOUNT_CREATED= TXT_NO_CONNECTION+1;
	public static final int TXT_GAME_CREATED= TXT_ACCOUNT_CREATED+1;
	public static final int TXT_CONNECTED_BY= TXT_GAME_CREATED+1;
	public static final int TXT_HAVE_JOINED= TXT_CONNECTED_BY+1;
	public static final int TXT_GAME_LOADED= TXT_HAVE_JOINED+1;
	public static final int TXT_SEND_DATA= TXT_GAME_LOADED+1;
	
	public static final int TXT_GAME_TURN= TXT_SEND_DATA+1;
	public static final int TXT_GAME_PLAIN= TXT_GAME_TURN+1;
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
	public static final int TXT_GAME_YOU_LOST_GAME= TXT_GAME_BIG_VICTORY+1;
	public static final int TXT_GAME_LOST_GAME= TXT_GAME_YOU_LOST_GAME+1;
	
	public static final int TXT_GAME_ATTACKER_WINS= TXT_GAME_LOST_GAME+1;
	public static final int TXT_GAME_ATTACKER_DEFEAT= TXT_GAME_ATTACKER_WINS+1;
	public static final int TXT_GAME_ATTACKER_HAS_DESTROYED= TXT_GAME_ATTACKER_DEFEAT+1;
	public static final int TXT_GAME_ATTACKER_HAS_BEEN_DESTROYED= TXT_GAME_ATTACKER_HAS_DESTROYED+1;
	
	public static final int TXT_GAME_ATTACKER_LOST= TXT_GAME_ATTACKER_HAS_BEEN_DESTROYED+1;
	public static final int TXT_GAME_DEFENSER_LOST= TXT_GAME_ATTACKER_LOST+1;
	public static final int TXT_GAME_LOSSES= TXT_GAME_DEFENSER_LOST+1;
	public static final int TXT_GAME_NEW_ARMY= TXT_GAME_LOSSES+1;
	public static final int TXT_GAME_ARMY_JOIN= TXT_GAME_NEW_ARMY+1;
	public static final int TXT_GAME_ECONOMY= TXT_GAME_ARMY_JOIN+1;
	public static final int TXT_GAME_EARNING= TXT_GAME_ECONOMY+1;
	public static final int TXT_GAME_SALARY= TXT_GAME_EARNING+1;
	public static final int TXT_GAME_COST_OF_TROOPS= TXT_GAME_SALARY+1;
	public static final int TXT_GAME_EXCEED_TROOPS= TXT_GAME_COST_OF_TROOPS+1;
	public static final int TXT_GAME_IS_WINNER= TXT_GAME_EXCEED_TROOPS+1;
	public static final int TXT_GAME_CHANGE_HIS_CAPITAL = TXT_GAME_IS_WINNER+1;
	public static final int TXT_GAME_CHANGE_YOUR_CAPITAL = TXT_GAME_CHANGE_HIS_CAPITAL+1;
	
	public static final int TXT_GAME_DO_YOU_WANT_DISCARD= TXT_GAME_CHANGE_YOUR_CAPITAL+1;
	public static final int TXT_GAME_FOR= TXT_GAME_DO_YOU_WANT_DISCARD+1;
	public static final int TXT_GAME_COINS= TXT_GAME_FOR+1;
	public static final int TXT_GAME_INTERROGATION_ICON= TXT_GAME_COINS+1;
	
	public static final int TOTAL_LINES_TXT = TXT_GAME_INTERROGATION_ICON;
	
	
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
			e.printStackTrace();
		}
	}
	
	
	
	
	

}
