package kr.picture.poketme.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import kr.picture.poketme.MyApp;


public class NetworkHelper {
	private BroadcastReceiver mConnReceiver	= null;
	private ConnectivityManager mConnectivityManager= null;
	private boolean mIsWifi = false;
	private boolean mIs3G = false;
	private final android.app.Application mApp;

	private static NetworkHelper mINSTANCE = new NetworkHelper();

	public static NetworkHelper getInstance(){
		return mINSTANCE;
	}

	private NetworkHelper(){
		mApp = MyApp.getApplication();
		mConnectivityManager = (ConnectivityManager) mApp.getSystemService(Context.CONNECTIVITY_SERVICE);
		init();
		loadNetStatus();
	}

	public void init(){
		if( mConnReceiver == null ){
			mConnReceiver = new ConnectionReceiver();
			mApp.registerReceiver(mConnReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
		}
	}

	public boolean isWIFIConneced(){
		return mIsWifi;
	}

	public boolean is3GConnected(){
		return mIs3G;
	}

	public boolean isAvailable(){
		return isWIFIConneced() || is3GConnected();
	}

	public void close(){
		mApp.unregisterReceiver(mConnReceiver);
		//mINSTANCE = null;
	}

	public void loadNetStatus(){
		try{
			if( null != mApp && mConnectivityManager == null)
				mConnectivityManager = (ConnectivityManager) mApp.getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo wifi = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			NetworkInfo mobile = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			
			if( wifi!= null && wifi.isAvailable() && wifi.isConnected()){
				NetworkHelper.this.mIsWifi = true;
				NetworkHelper.this.mIs3G = false;
			}else if(mobile != null && mobile.isAvailable() && mobile.isConnected()){
				NetworkHelper.this.mIsWifi = false;
				NetworkHelper.this.mIs3G = true;
			}else{
				NetworkHelper.this.mIsWifi = false;
				NetworkHelper.this.mIs3G = false;
			}
		}catch (NullPointerException e) {
		}
	}

	/**
	 * BroadcastReceiver
	 * @author jmlee
	 *
	 */
	public class ConnectionReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if( ConnectivityManager.CONNECTIVITY_ACTION.equals(action) )
				loadNetStatus();
		}
	}

	public static String getMacAddress_v6() {
		try {
			List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface nif : all) {
				if (!nif.getName().equalsIgnoreCase("wlan0")) continue;
				byte[] macBytes = nif.getHardwareAddress();
				if (macBytes == null) {
					return "";
				}
				StringBuilder res1 = new StringBuilder();
				for (byte b : macBytes) {
					res1.append(Integer.toHexString(b & 0xFF) + ":");
				}
				if (res1.length() > 0) {
					res1.deleteCharAt(res1.length() - 1);
				}
				return res1.toString();
			}
		} catch (NullPointerException e) {
		} catch (Exception e) {
		}
		return "02:00:00:00:00:00";
	}

	public static String getMACAddress(String interfaceName) {
		try {
			List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface intf : interfaces) {
				if (interfaceName != null) {
					if (!intf.getName().equalsIgnoreCase(interfaceName)) continue;
				}
				byte[] mac = intf.getHardwareAddress();
				if (mac==null) return "";
				StringBuilder buf = new StringBuilder();
				for (int idx=0; idx<mac.length; idx++)
					buf.append(String.format("%02X:", mac[idx]));
				if (buf.length()>0) buf.deleteCharAt(buf.length()-1);
				return buf.toString();
			}
		} catch (NullPointerException e) {
		} catch (Exception e) {
		}
		return "";
	}
}
