package com.example.servicedemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	
	/**
	 * 绑定对象实例
	 */
	private MyService.MyBinder binder;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
	
	private ServiceConnection conn = new ServiceConnection() {
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			binder = (MyService.MyBinder) service;	//获取其实例
			binder.greet("scott");					//调用其方法
		}
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
		}
	};
	
	/**
	 * 绑定服务
	 * @param view
	 */
	public void bind(View view) {
		Intent intent = new Intent(this, MyService.class);
		bindService(intent, conn, Context.BIND_AUTO_CREATE);
	}
	
	/**
	 * 解除绑定
	 * @param view
	 */
	public void unbind(View view) {
		unbindService(conn);
	}
}
