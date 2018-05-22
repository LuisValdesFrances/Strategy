package com.luis.strategy;


import com.luis.lgameengine.gameutils.Settings;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
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
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		int currentApiVersion = android.os.Build.VERSION.SDK_INT;

        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        
        // This work only for android 4.4+
        if(currentApiVersion >= Build.VERSION_CODES.KITKAT){

            getWindow().getDecorView().setSystemUiVisibility(flags);

            // Code below is to handle presses of Volume up or Volume down.
            // Without this, after pressing volume buttons, the navigation bar will
            // show up and won't hide
            final View decorView = getWindow().getDecorView();
            decorView
                    .setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener()
                    {
                        @Override
                        public void onSystemUiVisibilityChange(int visibility)
                        {
                            if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0)
                            {
                                decorView.setSystemUiVisibility(flags);
                            }
                        }
                    });
        }
		
		
		Log.i("Debug", "lGameEngine INIT");
		Settings.getInstance().init(this);
		
		main = new Main(this);
		
		// Rescale surface view to layout size:
		RelativeLayout layout = new RelativeLayout(this);
		layout.addView(main, Settings.getInstance().getRealWidth(), Settings.getInstance().getRealHeight());
		setContentView(layout);
		
		
		//main.setLayoutParams(new ActionBar.LayoutParams(Settings.getInstance().getRealWidth(), Settings.getInstance().getRealHeight()));
		//setContentView(main);
		
		Log.i("Debug", "lGameEngine START");
		gameThread = new Thread(main);
		gameThread.start();
		
		//this.finish();
		
	}
	
	@Override
	public void onResume(){
		super.onResume();
		main.unPause();
	}
	
	@Override
	public void onRestart(){
		super.onRestart();
	}
	
	@Override
	public void onPause(){
		super.onPause();
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


