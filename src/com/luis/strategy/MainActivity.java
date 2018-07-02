package com.luis.strategy;


import com.luis.lgameengine.gameutils.Settings;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

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
		Settings.getInstance().init(
				this, new boolean[]{
									false,
									false,
									true,
									true
		}
		);
		
		
		// Rescale surface view to layout size:
		/*
		RelativeLayout layout = new RelativeLayout(this);
		layout.addView(main, Settings.getInstance().getRealWidth(), Settings.getInstance().getRealHeight());
		setContentView(this);
		*/
		
		//main.setLayoutParams(new ActionBar.LayoutParams(Settings.getInstance().getRealWidth(), Settings.getInstance().getRealHeight()));
		//setContentView(main);
		
		main = new Main(this);
		setContentView(main);
		
		Log.i("Debug", "Game loop START");
		gameThread = new Thread(main);
		gameThread.start();
		
		
		//Screen no sleep
		
		powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
		
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
	public void onPause(){
		super.onPause();
		
		if(wakeLock != null){
			wakeLock.release();
		}
		main.pause();
	}
	
	@Override
	public void onStop(){
		super.onStop();
		main.saveAndSend();
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
	
	
	//Notificaciones
	public void sendNotification(int notificationId, String title, String content) {
		
		//Intent intent = new Intent(this, MainActivity.class);
		Intent intent = getIntent();
		//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
		
		long[] vibrate = {500};
		
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentTitle(title)
        .setContentText(content)
        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        .setVibrate(vibrate)
        .setLights(Color.GREEN, 1000, 500)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true);
		
		NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

		// notificationId is a unique int for each notification that you must define
		notificationManager.notify(notificationId, mBuilder.build());
	}
	
	}


