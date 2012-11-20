package com.example.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

	private static final String TAG = "MyService";
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.i(TAG, "onBind called.");
		return new MyBinder();
	}
	
	/**
	 * 绑定对象
	 * @author user
	 *
	 */
	public class MyBinder extends Binder {
		
		/**
		 * 问候
		 * @param name
		 */
		public void greet(String name) {
			Log.i(TAG, "hello, " + name);
		}
	}
}
