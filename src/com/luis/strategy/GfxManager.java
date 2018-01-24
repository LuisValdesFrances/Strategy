package com.luis.strategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.strategy.constants.Define;

public class GfxManager {
	
	   public static Image vImgFontSmall;
	   public static Image vImgFontMedium;
	   public static Image vImgFontBig;
	   
	   public static Image vImgLogo;
	   //public static Image vImgBackground;
	   public static Image vImgMenuButtons;
	   public static Image vImgMenuArrows;
	   public static Image vImgSoftkeys;
	   public static Image vImgPresent;
	   public static Image vImageArrowsLR;
	   public static Image vImageArrowsUD;
	   public static Image vImagePatch;
	   public static Image vImagePatch2;
	   public static Image vImagePlanetLocked;
	   
	   public static Image imgMenuBox;
	   public static Image imgButtonRelease;
	   public static Image imgButtonFocus;
	   
	   //Game images:
	   public static Image imgGameBG;
	   public static Image imgBlackBG;
	   public static Image imgTextBG;
	   
	   public static Image imgPadWest;
	   public static Image imgPadEast;
	   /*
	   public static Image imgPadNorth;
	   public static Image imgPadSouth;
	   public static Image imgPadAux;
	   */
	   public static Image imgGameHud;
	   public static Image imgChest;
	   public static Image imgCoin;
	   public static Image imgButtonPauseRelease;
	   public static Image imgButtonPauseFocus;
	   public static Image imgButtonNextRelease;
	   public static Image imgButtonNextFocus;
	   public static Image imgButtonCancelRelease;
	   public static Image imgButtonCancelFocus;
	   public static Image imgButtonDeleteRelease;
	   public static Image imgButtonDeleteFocus;
	   
	   public static Image imgTextBox;
	   public static Image imgBigBox;
	   public static Image imgMediumBox;
	   public static Image imgSmallBox;
	   public static Image imgNotificationBox;
	   public static Image imgButtonCombatRelease;
	   public static Image imgButtonCombatFocus;
	   public static Image imgButtonCardRelease;
	   public static Image imgButtonCardFocus;
	   public static Image imgButtonOkRelease;
	   public static Image imgButtonOkFocus;
	   public static Image imgButtonCrossRelease;
	   public static Image imgButtonCrossFocus;
	   public static Image imgButtonCrossBigRelease;
	   public static Image imgButtonCrossBigFocus;
	   public static Image imgButtonFlagHelmetRelease;
	   public static Image imgButtonFlagHelmetFocus;
	   public static Image imgButtonFlagCastleRelease;
	   public static Image imgButtonFlagCastleFocus;
	   
	   public static Image imgMap;
	   public static Image imgTerrainOk;
	   public static Image imgPlain;
	   public static Image imgForest;
	   public static Image imgMontain;
	   public static Image imgBigCity;
	   public static Image imgMediumCity;
	   public static Image imgSmallCity;
	   public static Image imgTargetDomain;
	   public static Image imgTargetBattle;
	   public static Image imgTargetAggregation;
	   public static Image imgMapSelectGreen;
	   public static Image imgMapSelectRed;
	   
	   
	   
	   public static List<Image> imgBigTroop;
	   public static List<Image> imgSmallTroop;
	   
	   public static Image imgArmyIdle;
	   public static Image imgArmyRun;
	   public static Image imgArmyOff;
	   
	   public static List<Image> imgFlagList;
	   public static List<Image> imgFlagSmallList;
	   
	  

	   public static void loadGFX(int _iNewState) {

	      switch (_iNewState) {
	          case Define.ST_MENU_LOGO:
	            try {
	            	//Load fonts
		            vImgFontSmall = Image.createImage("/font_small.png");
		            vImgFontMedium = Image.createImage("/font_medium.png");
		            vImgFontBig = Image.createImage("/font_big.png");
		               
		            vImgLogo = Image.createImage("/4away.png");
		            
		            imgMenuBox = Image.createImage("/menu_box.png");
		            imgButtonRelease = Image.createImage("/button_release.png");
		            imgButtonFocus = Image.createImage("/button_focus.png");
		            
	            } catch (IOException ex) {
	               ex.printStackTrace();
	            }
	            break;
	         case Define.ST_MENU_ASK_SOUND:
	         case Define.ST_MENU_ASK_LANGUAGE:
		        try {
		        	vImgMenuButtons = Image.createImage("/menu_buttons.png");
		            vImgMenuArrows = Image.createImage("/menu_arrows.png");
		            vImgSoftkeys = Image.createImage("/softkeys.png");
		            	try{
		                //vImgBackground = Image.createImage("/bg_generic.png");
		            }catch(Exception e){}
		        } catch (IOException ex) {
		            ex.printStackTrace();
		        }
		        
		        break;
		       
		     //Test
	         case Define.ST_MENU_SELECT_GAME:
	        	 try {
			        	vImgMenuButtons = Image.createImage("/menu_buttons.png");
			            vImgMenuArrows = Image.createImage("/menu_arrows.png");
			            vImgSoftkeys = Image.createImage("/softkeys.png");
			            try{
			                //vImgBackground = Image.createImage("/bg_generic.png");
			            }catch(Exception e){}
			        } catch (IOException ex) {
			            ex.printStackTrace();
			        }
	        	 break;
	        	 
		        
	         case Define.ST_GAME_INIT:
	            try {
	            	switch(GameState.getInstance().getLevel()){
	            	case 0:
	            		imgMap = Image.createImage("/crom.png");
			            
	            		break;
	            	case 1:
	            		imgMap = Image.createImage("/genterex.png");
	            		break;
	            	}
	            	
	            	imgTextBG = Image.createImage("/text_bg.png");
	            	imgBlackBG = Image.createImage("/black_bg.png");
	            	
	            	imgPadWest = Image.createImage("/pad_left.png");
	            	imgPadEast = Image.createImage("/pad_right.png");
	            	/*
	            	imgPadNorth = Image.createImage("/pad_up.png");
	            	imgPadSouth = Image.createImage("/pad_down.png");
	            	imgPadAux = Image.createImage("/pad_aux.png");
	            	*/
	            	imgTerrainOk = Image.createImage("/terrain_ok.png");
	            	imgPlain = Image.createImage("/plain.png");
	            	imgForest = Image.createImage("/forest.png");
	            	imgMontain = Image.createImage("/montain.png");
	            	imgBigCity = Image.createImage("/big_city.png");
	            	imgMediumCity = Image.createImage("/medium_city.png");
	            	imgSmallCity = Image.createImage("/small_city.png");
	            	imgTargetDomain = Image.createImage("/target_domain.png");
	            	imgTargetAggregation = Image.createImage("/target_aggregation.png");
	            	imgTargetBattle = Image.createImage("/target_battle.png");
	            	
	            	imgMapSelectGreen = Image.createImage("/map_select_green.png");
	            	imgMapSelectRed = Image.createImage("/map_select_red.png");
	            	
	            	imgArmyIdle = Image.createImage("/army_idle.png");
	            	imgArmyRun = Image.createImage("/army_run.png");
	            	imgArmyOff = Image.createImage("/army_off.png");
	            	
	            	imgGameHud = Image.createImage("/game_hud.png");
	            	imgChest = Image.createImage("/chest.png");
	            	imgCoin = Image.createImage("/coin.png");
	            	imgButtonPauseRelease = Image.createImage("/pause_release.png");
	            	imgButtonPauseFocus = Image.createImage("/pause_focus.png");
	            	imgButtonCancelRelease = Image.createImage("/cancel_release.png");
	            	imgButtonCancelFocus = Image.createImage("/cancel_focus.png");
	            	imgButtonNextRelease = Image.createImage("/next_release.png");
	            	imgButtonNextFocus = Image.createImage("/next_focus.png");
	            	imgButtonOkRelease = Image.createImage("/ok_release.png");
	            	imgButtonOkFocus = Image.createImage("/ok_focus.png");
	            	imgButtonCrossRelease = Image.createImage("/cross_release.png");
	            	imgButtonCrossFocus = Image.createImage("/cross_focus.png");
	            	imgButtonCrossBigRelease = Image.createImage("/cross_big_release.png");
	            	imgButtonCrossBigFocus = Image.createImage("/cross_big_focus.png");
	            	
	            	imgButtonDeleteFocus = Image.createImage("/delete_focus.png");
	            	imgButtonDeleteRelease = Image.createImage("/delete_release.png");
	            	
	            	imgTextBox = Image.createImage("/text_box.png");
	            	imgBigBox = Image.createImage("/big_box.png");
	            	imgMediumBox =  Image.createImage("/medium_box.png");
	            	imgSmallBox =  Image.createImage("/small_box.png");
	            	imgNotificationBox =  Image.createImage("/game_notification_box.png");
	            	imgButtonCombatRelease = Image.createImage("/combat_release.png");
	            	imgButtonCombatFocus = Image.createImage("/combat_focus.png");
	            	imgButtonCardRelease = Image.createImage("/card_release.png");
	            	imgButtonCardFocus = Image.createImage("/card_focus.png");
	            	imgButtonFlagHelmetRelease = Image.createImage("/flag_helmet_release.png");
	            	imgButtonFlagHelmetFocus = Image.createImage("/flag_helmet_focus.png");
	            	imgButtonFlagCastleRelease = Image.createImage("/flag_castle_release.png");
	            	imgButtonFlagCastleFocus = Image.createImage("/flag_castle_focus.png");
	            	
	            	imgFlagList = new ArrayList<Image>();
	            	imgFlagSmallList = new ArrayList<Image>();
	            	
	            	imgFlagList.add(Image.createImage("/flag1.png"));
	            	imgFlagList.add(Image.createImage("/flag2.png"));
	            	imgFlagList.add(Image.createImage("/flag3.png"));
	            	imgFlagList.add(Image.createImage("/flag4.png"));
	            	imgFlagList.add(Image.createImage("/flag5.png"));
	            	imgFlagList.add(Image.createImage("/flag6.png"));
	            	
	            	imgFlagSmallList.add(Image.createImage("/flag_small1.png"));
	            	imgFlagSmallList.add(Image.createImage("/flag_small2.png"));
	            	imgFlagSmallList.add(Image.createImage("/flag_small3.png"));
	            	imgFlagSmallList.add(Image.createImage("/flag_small4.png"));
	            	imgFlagSmallList.add(Image.createImage("/flag_small5.png"));
	            	imgFlagSmallList.add(Image.createImage("/flag_small6.png"));
	            	
	            	imgBigTroop = new ArrayList<Image>();
	            	imgSmallTroop = new ArrayList<Image>();
	            	imgBigTroop.add(Image.createImage("/infantry_big.png"));
	            	imgBigTroop.add(Image.createImage("/knigths_big.png"));
	            	imgBigTroop.add(Image.createImage("/harassers_big.png"));
	            	imgSmallTroop.add(Image.createImage("/infantry_small.png"));
	            	imgSmallTroop.add(Image.createImage("/knigths_small.png"));
	            	imgSmallTroop.add(Image.createImage("/harassers_small.png"));
	            	
	               
	            } catch (IOException ex) {
	               ex.printStackTrace();
	            }
	            break;
	        }
	   }

	    public static void deleteGFX() {
	    	//vImgLogo = null;
	        //vImgBackground = null;
	        //Game images:
	        System.gc();
	    }
    
   
    
    

}
