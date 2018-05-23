package com.luis.strategy;


import com.luis.lgameengine.gameutils.Settings;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

/**
 * 
 * @author Luis Valdes Frances
 */

public class MainActivity extends Activity{
	
	private Thread gameThread;
	private Main main;
	
	private PowerManager powerManager;
	private PowerManager.WakeLock wakeLock;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		Log.i("Debug", "lGameEngine INIT");
		Settings.getInstance().init(this);
		
		main = new Main(this);
		
		// Rescale surface view to layout size:
		RelativeLayout layout = new RelativeLayout(this);
		layout.addView(main, Settings.getInstance().getRealWidth(), Settings.getInstance().getRealHeight());
		setContentView(layout);
		
		
		//main.setLayoutParams(new ActionBar.LayoutParams(Settings.getInstance().getRealWidth(), Settings.getInstance().getRealHeight()));
		//setContentView(main);
		
		Log.i("Debug", "Game loop START");
		gameThread = new Thread(main);
		gameThread.start();
		
		
		//Screen no sleep
		powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
		
		//this.finish();
		
	}
	
	@Override
	public void onResume(){
		super.onResume();
		if(wakeLock != null){
			wakeLock.acquire();
		}
		main.unPause();
	}
	
	@Override
	public void onRestart(){
		super.onRestart();
	}
	
	@Override
	public void onPause(){
		super.onPause();
		if(wakeLock != null){
			wakeLock.acquire();
		}
		main.pause();
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Log.i("Debug", "Thread stoped with exit!");
	}
	
	
	
	//Dialogo de confirmación que captura el boton "atrás":
//		public boolean onKeyDown(int keyCode, KeyEvent event) {
//			return true;
//		}
	}


