package com.luis.army.gui;

import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;
import com.luis.lgameengine.menu.Button;
import com.luis.strategy.constants.Define;

public class FlagButton extends Button{
	
	private int state;
	public static final int STATE_UNACTIVE = 0;
	public static final int STATE_INCOME = 1;
	public static final int STATE_ACTIVE = 2;
	public static final int STATE_END = 3;
	
	private float modPosY;
	

	public FlagButton(Image imgRelease, Image imgFocus, int x, int y) {
		super(imgRelease, imgFocus, x, y, null, -1);
		// TODO Auto-generated constructor stub
	}
	
	public void start(){
		modPosY = +height + height/2;
		state = STATE_INCOME;
	}
	
	public void update(MultiTouchHandler touchHandler, Float delta){
		switch(state){
		case STATE_INCOME:
			modPosY -= (modPosY*4f)*delta + 1f;
			if(modPosY <= 0){
				modPosY = 0;
				state = STATE_ACTIVE;
			}
			break;
		case STATE_ACTIVE:
			super.update(touchHandler);
			break;
		case STATE_END:
			modPosY += (modPosY*8f)*delta + 1f;
			if(modPosY >= height+getWidth()/2){
				state = STATE_UNACTIVE;
				onButtonPressUp();
			}
			break;
		}
	}
	
	public void hide(){
		if(state == STATE_ACTIVE)
			state = STATE_END;
	}
	
	public void draw(Graphics g){
		g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
		if(state != STATE_UNACTIVE)
			g.drawImage(isTouching() || isDisabled() ? getImgFocus():getImgRelese(), 
					x, y+(int)modPosY, Graphics.VCENTER | Graphics.HCENTER);
		
	}

}
