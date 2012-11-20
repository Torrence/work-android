package com.example.broadcastreceiverdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /*
        //动态注册广播代码
        MyReceiver receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.MY_BROADCAST");
        registerReceiver(receiver, filter);
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		/*
		 * 动态注册广播，解除注册
		unregisterReceiver(receiver);
		*/
	}
    
    public void send(View view) {
    	Intent intent = new Intent("android.intent.action.MY_BROADCAST");
    	intent.putExtra("msg", "hello receiver");
    	sendOrderedBroadcast(intent, "scott.permission.MY_BROADCAST_PERMISSION");
    }
}
