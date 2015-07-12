package com.xist.pluggin;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.bluetooth.BluetoothAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;

public class Toggler extends CordovaPlugin {
	public static final String WIFI= "wifi";
	public static final String BLUETOOTH = "bluetooth";
	public static final String DataPack = "datapack";
	public static final String SILENTMODE = "silentmode";
	public static final String BRIGHTNESS = "brightness";
	public static final String AIRPLANE = "airplane";
	public static final String SCREEN_ROTATION="screenrotation";
	
	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		try {
			/*
			 * Wifi
			 */
			if (WIFI.equals(action)) {
			//	JSONObject arg_object = args.getJSONObject(0);
				if(isWifiEnabled()){
					toggleWiFi(false);
					callbackContext.error("wifi");
				}
				else{
					toggleWiFi(true);
					callbackContext.success("wifi");
				}
				 
				 
				return true;
			}
			/*
			 * Data Pack Toggle
			 */
			if(DataPack.equals(action)){
				if(isDataPackEnabled()){
					setMobileDataEnabled(this.cordova.getActivity(),false);
					callbackContext.error("dp");
				}else{
					setMobileDataEnabled(this.cordova.getActivity(),true);
					callbackContext.success("dp");
				}
			}
			
			/*
			 * Data Pack Toggle
			 */
			if(SILENTMODE.equals(action)){
				AudioManager audioManager = (AudioManager)this.cordova.getActivity().getSystemService(Activity.AUDIO_SERVICE);
				if(isPhoneSilent(audioManager)){
					audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
					setMobileDataEnabled(this.cordova.getActivity(),false);
					callbackContext.error("silentmode");
				}else{
					audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
					callbackContext.success("silentmode");
				}
			}
			/*
			 * Brightness Toggle
			 */
			if(BRIGHTNESS.equals(action)){
				if(isNightModeEnabled()){
					android.provider.Settings.System.putInt(this.cordova.getActivity().getContentResolver(),
						      android.provider.Settings.System.SCREEN_BRIGHTNESS,
						      175);
					callbackContext.error("brightness");
				}else{
					android.provider.Settings.System.putInt(this.cordova.getActivity().getContentResolver(),
						      android.provider.Settings.System.SCREEN_BRIGHTNESS,
						      60);
					callbackContext.success("brightness");
				}
			}
			/*
			 * Airplane Mode
			 */
			if(AIRPLANE.equals(action)){
				if(isAirplaneModeEnabled()){
					 Intent intent = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
		               intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		               this.cordova.getActivity().startActivity(intent);
		               callbackContext.success("airplane");
					
				}else{
					 Intent intent = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
		               intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		               this.cordova.getActivity().startActivity(intent);
		               callbackContext.error("airplane");
				}
			}
		 /*
		  * Bluetooth
		  */
			if (BLUETOOTH.equals(action)) {
				BluetoothAdapter bluetooth=BluetoothAdapter.getDefaultAdapter();
				JSONObject arg_object = args.getJSONObject(0);
				if(bluetooth.isEnabled()){
					bluetooth.disable();
					callbackContext.error("bluetooth");
				}else{
					Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		               this.cordova.getActivity().startActivityForResult(enableBtIntent, 0);
		               callbackContext.success("bluetooth");
				}
				 
			}
			/*
			 * Screen Rotation
			 */
			if(SCREEN_ROTATION.equals("screenrotation")){
				
			}
			
			callbackContext.error("Invalid action");
			return false;
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
			callbackContext.error(e.getMessage());
			return false;
		}

	}

	private void toggleWiFi(boolean status) {
		WifiManager wifiManager = (WifiManager) this.cordova.getActivity()
				.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
		if (status == true && !wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
		} else if (status == false && wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(false);
		}
	}
	
	@SuppressWarnings("unused")
	private Boolean isNightModeEnabled() throws SettingNotFoundException{
		Float curBrightnessValue = (float) android.provider.Settings.System.getInt(
				this.cordova.getActivity().getContentResolver(),
			     android.provider.Settings.System.SCREEN_BRIGHTNESS);
		if(curBrightnessValue>100){
			return false;
		}else{
			return true;
		}
		 
	}
	
	private Boolean isWifiEnabled(){
		WifiManager wifiManager = (WifiManager)  this.cordova.getActivity().getSystemService(this.cordova.getActivity().WIFI_SERVICE);
		boolean wifiEnabled = wifiManager.isWifiEnabled();
		
		return wifiEnabled;

	}
	
	private Boolean isAirplaneModeEnabled(){
		if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN){
			boolean isEnabled = Settings.System.getInt(this.cordova.getActivity().getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) == 1;
			return isEnabled;
		}else{
			boolean isEnabled = Settings.System.getInt(this.cordova.getActivity().getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) == 1;
			return isEnabled;
		}
		
	}
	
	private Boolean isDataPackEnabled(){
		ConnectivityManager connectivityManager =
			    (ConnectivityManager) this.cordova.getActivity().getSystemService(this.cordova.getActivity().CONNECTIVITY_SERVICE);
		NetworkInfo mobileInfo =
			    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			boolean mobileConnected = mobileInfo.getState() == NetworkInfo.State.CONNECTED;
		return mobileConnected;
	}
	
	private Boolean isPhoneSilent(AudioManager audioManager){
		
		if(audioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT){
			return true;
		}else{
			return false;
		}
	}
	
	@SuppressWarnings("unused")
	private void setMobileDataEnabled(Context context, boolean enabled) {
		  final ConnectivityManager conman =
		      (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		  try {
		    final Class conmanClass = Class.forName(conman.getClass().getName());
		    final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
		    iConnectivityManagerField.setAccessible(true);
		    final Object iConnectivityManager = iConnectivityManagerField.get(conman);
		    final Class iConnectivityManagerClass = Class.forName(
		        iConnectivityManager.getClass().getName());
		    final Method setMobileDataEnabledMethod = iConnectivityManagerClass
		        .getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
		    setMobileDataEnabledMethod.setAccessible(true);

		    setMobileDataEnabledMethod.invoke(iConnectivityManager, enabled);
		    
		  } catch (ClassNotFoundException e) {
		    e.printStackTrace();
		  } catch (InvocationTargetException e) {
		    e.printStackTrace();
		  } catch (NoSuchMethodException e) {
		    e.printStackTrace();
		  } catch (IllegalAccessException e) {
		    e.printStackTrace();
		  } catch (NoSuchFieldException e) {
		    e.printStackTrace();
		  }
		}

}
