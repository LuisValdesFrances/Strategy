package com.luis.strategy;

/*
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
*/
import com.luis.lgameengine.gameutils.Settings;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * 
 * @author Luis Valdes Frances
 */

public class MainActivity extends Activity{

	private Thread gameThread;
	private Main main;

	//private InterstitialAd mInterstitialAd;

	public static final String ADMOB_ID = "ca-app-pub-9871578065265688~4052408776";
	public static final String INTERSTITIAL_ID = "ca-app-pub-9871578065265688/6188968599";
	
	private PowerManager powerManager;
	private PowerManager.WakeLock wakeLock;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    Log.i("Debug", "onCreate is called");

	    //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

	    /*
		MobileAds.initialize(this, ADMOB_ID);
        mInterstitialAd = new InterstitialAd(this);
		mInterstitialAd.setAdUnitId(INTERSTITIAL_ID);
		//mInterstitialAd.loadAd(new AdRequest.Builder().build());

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( new OnSuccessListener<InstanceIdResult>() {
			@Override
			public void onSuccess(InstanceIdResult instanceIdResult) {
				// Do whatever you want with your token now
				// i.e. store it on SharedPreferences or DB
				// or directly send it to server
                try {
                    firebaseDeviceToken = instanceIdResult.getToken();
                }catch(Exception e){
                    e.printStackTrace();
                }
			}
		});
		*/

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        startGame();
        //Screen no sleep
        powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
	}

	@Override
    public void onStart(){
	    super.onStart();
        Log.i("Debug", "onStart is called");
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

		try{
			main.saveAndSend();
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	@Override
	public void onDestroy(){
		super.onDestroy();
        try{
			if(gameThread.isAlive()){
				main.finishGame();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		Log.i("Debug", "Thread stoped with exit!");

	}

	private void startGame(){
        try {


            boolean notification = false;
            if (getIntent().getExtras() != null) {
                if(getIntent().getExtras().keySet().size() > 1){
                    notification = true;
                }
                //Toast.makeText(getApplicationContext(), (""+getIntent().getExtras().keySet().size()), Toast.LENGTH_SHORT).show();
                /*
                for (String key : getIntent().getExtras().keySet()) {
                    String value = String.valueOf(getIntent().getExtras().get(key));
                    Log.d("Debug", "Key: " + key + " Value: " + value);
                    //
                    //Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
                    if(key.equals("collapse_key_value") && value.equals("com.luis.strategy")){
                        notification = true;
                    }
                }
                */
            }





            Settings.getInstance().init(
                    this,
                    new boolean[]{
                            false,
                            true,
                            true,
                            true
                    }, Settings.ORIENTATION_LANDSCAPE);

            //Metodo 1
            // Rescale surface view to layout size:
            /*
            main = new Main(this);
            RelativeLayout layout = new RelativeLayout(this);
            layout.addView(main, Settings.getInstance().getRealWidth(), Settings.getInstance().getRealHeight());
            setContentView(layout);
            */

                //Metodo 2
            /*
            main = new Main(this);
            main.setLayoutParams(new ActionBar.LayoutParams(Settings.getInstance().getRealWidth(), Settings.getInstance().getRealHeight()));
            setContentView(main);
            */

            //Metodo 3
            main = new Main(this, Settings.getInstance(), notification);
            setContentView(main);

            Log.i("Debug", "View load");
            gameThread = new Thread(main);
            gameThread.start();
            Log.i("Debug", "Game loop START");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

	public void loadInterstitial(){
		/*
		if(!Main.IS_GAME_DEBUG) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					mInterstitialAd.loadAd(new AdRequest.Builder().build());
				}
			});
		}
		*/
    }

	public void requestInterstitial() {
		/*
		if(!Main.IS_GAME_DEBUG) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (mInterstitialAd.isLoaded()) {
						mInterstitialAd.show();
					}
				}
			});
		}
		*/
	}

	//Firebase
    private String firebaseDeviceToken;
    public String getFirebaseDeviceToken(){
        return firebaseDeviceToken;
    }

    //Notificaciones
	/*
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
	*/
	
	}


