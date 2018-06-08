package com.luis.strategy.connection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.luis.strategy.datapackage.scene.NotificationListData;
import com.luis.strategy.datapackage.scene.PreSceneListData;
import com.luis.strategy.datapackage.scene.SceneData;
import com.luis.strategy.datapackage.scene.SceneListData;

public class OnlineInputOutput {
	
	public static final String GAME_VERSION = "0.1";
	public static final String SERVER_URL = "http://172.104.228.65:8080/KingServer/";//Online
	//public static final String SERVER_URL = "http://192.168.1.110:8080/KingServer/";//Local
	//public static final String SERVER_URL = "http://192.168.26.155:8080/KingServer/";//Local2
	
	private static OnlineInputOutput instance;
	
	public static final String URL_CREATE_INSCRIPTION = "createInscriptionServlet";
	public static final String URL_CREATE_PRE_SCENE = "createPreSceneServlet";
	public static final String URL_CREATE_NOTIFICATION = "createNotificationServlet";
	
	public static final String URL_GET_GAME_VERSION = "getGameVersionServlet";
	public static final String URL_GET_PRE_SCENE_LIST = "getPreSceneListServlet";
	public static final String URL_GET_SCENE_LIST = "getSceneListServlet";
	public static final String URL_GET_START_SCENE = "getStartSceneServlet";
	public static final String URL_GET_SCENE = "getSceneController";
	public static final String URL_GET_NOTIFICATION_LIST = "getNotificationListServlet";
	
	public static final String URL_UPDATE_NOTIFICATION = "updateNotificationSceneServlet";
	public static final String URL_UPDATE_SCENE = "updateSceneServlet";
	
	public static final String MSG_NO_CONNECTION = "No connection";
	
	//Notifications code
	public static final int CODE_NOTIFICATION_YOU_LOST_GAME= 0;
	public static final int CODE_NOTIFICATION_LOST_GAME= CODE_NOTIFICATION_YOU_LOST_GAME+1;
	public static final int CODE_NOTIFICATION_YOUR_ARMY_DEFEATED= CODE_NOTIFICATION_LOST_GAME+1;
	public static final int CODE_NOTIFICATION_YOUR_ARMY_WON= CODE_NOTIFICATION_YOUR_ARMY_DEFEATED+1;
	public static final int CODE_NOTIFICATION_YOUR_ARMY_DESTROYED= CODE_NOTIFICATION_YOUR_ARMY_WON+1;
	public static final int CODE_NOTIFICATION_YOUR_ARMY_DESTROYED_ENEMY= CODE_NOTIFICATION_YOUR_ARMY_DESTROYED+1;
	public static final int CODE_NOTIFICATION_CHANGE_CAPITAL= CODE_NOTIFICATION_YOUR_ARMY_DESTROYED_ENEMY+1;
	
	public static OnlineInputOutput getInstance(){
		if(instance == null){
			instance = new OnlineInputOutput();
		}
		return instance;
	}
	
	
	public boolean isOnline(Context context) {
	    ConnectivityManager cm =
	        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    return netInfo != null && netInfo.isConnectedOrConnecting();
	}
	
	public String checkGameVersion(Context context){
		
		if(!isOnline(context)){
			return MSG_NO_CONNECTION;
		}
		
		HttpURLConnection connection = null;
		String result = "";
		
		try {
			// open URL connection
			URL url = new URL(SERVER_URL + URL_GET_GAME_VERSION);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Content-Type", "application/octet-stream");
			connection.setRequestMethod("GET");
			connection.setRequestProperty("version", GAME_VERSION);
			connection.setDoInput(true);
			connection.setDoOutput(false);
			connection.setUseCaches(false);

			BufferedReader in = 
					new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String str = "";
			while ((str = in.readLine()) != null) {
				result += str;// + "\n";
			}
			in.close();

			System.out.println(result);
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}
		return result;
	}

	public String sendNotification(Context context, String scene, String from, String to, String message, String type){
		
		if(!isOnline(context)){
			return MSG_NO_CONNECTION;
		}
		
		HttpURLConnection connection = null;
		String result = "";
		
		try {
			// open URL connection
			URL url = new URL(SERVER_URL + URL_CREATE_NOTIFICATION);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Content-Type", "application/octet-stream");
			connection.setRequestMethod("POST");
			connection.setRequestProperty("scene", scene);
			connection.setRequestProperty("from", from);
			connection.setRequestProperty("to", to);
			connection.setRequestProperty("message", message);
			connection.setRequestProperty("type", type);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);

			BufferedReader in = 
					new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String str = "";
			while ((str = in.readLine()) != null) {
				result += str;// + "\n";
			}
			in.close();

			System.out.println(result);
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}
		return result;
	}
	
	public NotificationListData reviceNotificationListData(Context context, String scene, String to, String type){
		
		if(!isOnline(context)){
			return null;
		}
		
		NotificationListData notificationListData = null;
		HttpURLConnection connection = null;
		try {
			// open URL connection
			String encodeUrl = SERVER_URL + URL_GET_NOTIFICATION_LIST;
			URL url = new URL(encodeUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Content-Type", "application/octet-stream");
			connection.setRequestMethod("GET");
			connection.setRequestProperty("scene", scene);
			connection.setRequestProperty("to", to);
			connection.setRequestProperty("type", type);
			connection.setDoInput(true);
			connection.setDoOutput(false);
			connection.setUseCaches(false);
			
			ObjectInputStream objIn = new ObjectInputStream(connection.getInputStream());
			notificationListData = (NotificationListData) objIn.readObject();
			objIn.close();
			connection.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return notificationListData;
	}
	
	public String sendUser(Context context, String URL, String name, String password){
		
		if(!isOnline(context)){
			return MSG_NO_CONNECTION;
		}
		
		HttpURLConnection connection = null;
		String result = "";
		try {
			// open URL connection
			URL url = new URL(SERVER_URL + URL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Content-Type", "application/octet-stream");
			connection.setRequestMethod("POST");
			connection.setRequestProperty("name", name);
			connection.setRequestProperty("password", password);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);

			BufferedReader in = 
					new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String str = "";
			while ((str = in.readLine()) != null) {
				result += str;// + "\n";
			}
			in.close();

			System.out.println(result);
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}
		return result;
	}
	
	public String sendPreScene(Context context, String URL, String map, String user, String name){
		if(!isOnline(context)){
			return MSG_NO_CONNECTION;
		}
		HttpURLConnection connection = null;
		String result = "";
		try {
			// open URL connection
			URL url = new URL(SERVER_URL + URL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Content-Type", "application/octet-stream");
			connection.setRequestMethod("POST");
			connection.setRequestProperty("map", map);
			connection.setRequestProperty("user", user);
			connection.setRequestProperty("name", name);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);

			BufferedReader in = 
					new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String str = "";
			while ((str = in.readLine()) != null) {
				result += str;// + "\n";
			}
			in.close();

			System.out.println(result);
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}
		return result;
	}
	
	public String sendInscription(Context context, String scene, String user, String create){
		if(!isOnline(context)){
			return MSG_NO_CONNECTION;
		}
		HttpURLConnection connection = null;
		String result = "";
		try {
			// open URL connection
			URL url = new URL(SERVER_URL + URL_CREATE_INSCRIPTION);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Content-Type", "application/octet-stream");
			connection.setRequestMethod("POST");
			connection.setRequestProperty("scene", scene);
			connection.setRequestProperty("user", user);
			connection.setRequestProperty("create", create);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);

			BufferedReader in = 
					new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String str = "";
			while ((str = in.readLine()) != null) {
				result += str;// + "\n";
			}
			in.close();

			System.out.println(result);
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}
		return result;
	}
	
	public String sendDataPackage(Context context, String URL, Serializable dataPackage){
		if(!isOnline(context)){
			return MSG_NO_CONNECTION;
		}
		HttpURLConnection connection = null;
		String result = "";
		try {
			// open URL connection
			URL url = new URL(SERVER_URL + URL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Content-Type", "application/octet-stream");
			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);

			// send object
			ObjectOutputStream objOut = new ObjectOutputStream(
					connection.getOutputStream());
			objOut.writeObject(dataPackage);
			objOut.flush();
			objOut.close();

			BufferedReader in = 
					new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String str = "";
			while ((str = in.readLine()) != null) {
				result += str;// + "\n";
			}
			in.close();

			System.out.println(result);
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}
		return result;
	}
	
	public PreSceneListData revicePreSceneListData(Context context, String URL, String user){
		if(!isOnline(context)){
			return null;
		}
		PreSceneListData preSceneListData = null;
		HttpURLConnection connection = null;
		try {
			// open URL connection
			//String encodeUrl = SERVER_URL + URL + URLEncoder.encode("?user=" + user);
			String encodeUrl = SERVER_URL + URL;
			URL url = new URL(encodeUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Content-Type", "application/octet-stream");
			connection.setRequestMethod("GET");
			connection.setRequestProperty("user", user);
			connection.setDoInput(true);
			connection.setDoOutput(false);
			connection.setUseCaches(false);
			
			ObjectInputStream objIn = new ObjectInputStream(connection.getInputStream());
			preSceneListData = (PreSceneListData) objIn.readObject();
			objIn.close();
			connection.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return preSceneListData;
	}
	
	public SceneListData reviceSceneListData(Context context, String user){
		if(!isOnline(context)){
			return null;
		}
		SceneListData sceneListData = null;
		HttpURLConnection connection = null;
		try {
			// open URL connection
			//String encodeUrl = Define.SERVER_URL + URL + URLEncoder.encode("?user=" + user);
			String encodeUrl = SERVER_URL + URL_GET_SCENE_LIST;
			URL url = new URL(encodeUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Content-Type", "application/octet-stream");
			connection.setRequestMethod("GET");
			connection.setRequestProperty("user", user);
			connection.setDoInput(true);
			connection.setDoOutput(false);
			connection.setUseCaches(false);
			
			ObjectInputStream objIn = new ObjectInputStream(connection.getInputStream());
			sceneListData = (SceneListData) objIn.readObject();
			objIn.close();
			connection.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sceneListData;
	}
	
	
	public SceneData reviceSceneData(Context context, String URL, String scene){
		if(!isOnline(context)){
			return null;
		}
		SceneData sceneData = null;
		HttpURLConnection connection = null;
		try {
			// open URL connection
			//String encodeUrl = Define.SERVER_URL + URL + URLEncoder.encode("?user=" + user);
			String encodeUrl = SERVER_URL + URL;
			URL url = new URL(encodeUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Content-Type", "application/octet-stream");
			connection.setRequestMethod("GET");
			connection.setRequestProperty("scene", scene);
			connection.setDoInput(true);
			connection.setDoOutput(false);
			connection.setUseCaches(false);
			
			ObjectInputStream objIn = new ObjectInputStream(connection.getInputStream());
			sceneData = (SceneData) objIn.readObject();
			objIn.close();
			connection.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sceneData;
	}

}
