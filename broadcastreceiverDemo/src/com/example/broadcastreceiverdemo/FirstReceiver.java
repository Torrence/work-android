package com.example.broadcastreceiverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class FirstReceiver extends BroadcastReceiver {
	
	private static final String TAG = "OrderedBroadcast";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String msg = intent.getStringExtra("msg");
		Log.i(TAG, "FirstReceiver: " + msg);
		
		Bundle bundle = new Bundle();
		bundle.putString("msg", msg + "@FirstReceiver");
		setResultExtras(bundle);
		abortBroadcast();
	}

}
