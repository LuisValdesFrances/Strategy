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
	   public static Image imgButtonPauseRelease;
	   public static Image imgButtonPauseFocus;
	   public static Image imgButtonNextRelease;
	   public static Image imgButtonNextFocus;
	   public static Image imgButtonCancelRelease;
	   public static Image imgButtonCancelFocus;
	   
	   public static Image imgMap;
	   public static Image imgPlain;
	   public static Image imgForest;
	   public static Image imgMontain;
	   public static Image imgBigCity;
	   public static Image imgMediumCity;
	   public static Image imgSmallCity;
	   public static Image imgTargetDomain;
	   public static Image imgTargetBattle;
	   public static Image imgTargetAggregation;
	   
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
	            	
	            	imgPlain = Image.createImage("/plain.png");
	            	imgForest = Image.createImage("/forest.png");
	            	imgMontain = Image.createImage("/montain.png");
	            	imgBigCity = Image.createImage("/big_city.png");
	            	imgMediumCity = Image.createImage("/medium_city.png");
	            	imgSmallCity = Image.createImage("/small_city.png");
	            	imgTargetDomain = Image.createImage("/target_domain.png");
	            	imgTargetAggregation = Image.createImage("/target_aggregation.png");
	            	imgTargetBattle = Image.createImage("/target_battle.png");
	            	
	            	imgArmyIdle = Image.createImage("/army_idle.png");
	            	imgArmyRun = Image.createImage("/army_run.png");
	            	imgArmyOff = Image.createImage("/army_off.png");
	            	
	            	imgButtonPauseRelease = Image.createImage("/pause_release.png");
	            	imgButtonPauseFocus = Image.createImage("/pause_focus.png");
	            	imgButtonCancelRelease = Image.createImage("/cancel_release.png");
	            	imgButtonCancelFocus = Image.createImage("/cancel_focus.png");
	            	imgButtonNextRelease = Image.createImage("/next_release.png");
	            	imgButtonNextFocus = Image.createImage("/next_focus.png");
	            	
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
