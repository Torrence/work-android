package com.android.fzmap.map;
/**
 * @author rongfzh
 * @version 1.0.0  
 */
import java.util.List;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * @author why
 */
public class FzLocationManager {
	private final String TAG = "FzLocationManager";
	private static Context mContext;
	private LocationManager gpsLocationManager;
	private LocationManager networkLocationManager;
	private static final int MINTIME = 2000;
	private static final int MININSTANCE = 2;
	private static FzLocationManager instance;
	private Location lastLocation = null;
	private static LocationCallBack mCallback;
	
	public static void init(Context c , LocationCallBack callback) {
		mContext = c;
		mCallback = callback;
	}

	
	private FzLocationManager() {
		LocationManager locationmanager = (LocationManager) mContext
				.getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(false);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		
		String bestprovider = locationmanager.getBestProvider(criteria, true);
//		Location locat = null;
//		while (true) {
//			List<String> privatelist = locationmanager.getAllProviders();
//			for (String privates : privatelist) {
//				locat = locationmanager.getLastKnownLocation(privates);
//				if (locat != null) {
//					bestprovider = privates;
//					Log.d("lm", locat.toString());
//					break;
//				}
//			}
//			if (locat != null) {
//				break;
//			}
//			Log.d("lm", "provider = " + bestprovider);
//		}
		
		// Gps 定位
		gpsLocationManager = locationmanager;
		if (gpsLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Log.d("lm", "gps avaible");
			Location gpsLocation = gpsLocationManager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			gpsLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
					MINTIME, MININSTANCE, locationListener);
		} else {
			Log.d("lm", "gps unavaible");
		}
        // 基站定位
		networkLocationManager = locationmanager;
		if (networkLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			Log.d("lm", "newwork avaible");
			Location networkLocation = networkLocationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			networkLocationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, MINTIME, MININSTANCE,
					locationListener);
		} else {
			Log.d("lm", "network unavaible");
		}
	}

	public static FzLocationManager getInstance() {
		if (null == instance) {
			instance = new FzLocationManager();
		}
		return instance;
	}

	private void updateLocation(Location location) {
		lastLocation = location;
		mCallback.onCurrentLocation(location);
	}

	
	private final LocationListener locationListener = new LocationListener() {
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onLocationChanged(Location location) {
			Log.d(TAG, "onLocationChanged");
			updateLocation(location);
		}
	};

	public Location getMyLocation() {
		return lastLocation;
	}
	
    private static int ENOUGH_LONG = 1000 * 60;	 
	
	public interface LocationCallBack{
		/**
		 * 当前位置
		 * @param location 
		 */
		void onCurrentLocation(Location location);
	}
	
	
	public void destoryLocationManager(){
		Log.d(TAG, "destoryLocationManager");
		gpsLocationManager.removeUpdates(locationListener);
		networkLocationManager.removeUpdates(locationListener);
	}
}
