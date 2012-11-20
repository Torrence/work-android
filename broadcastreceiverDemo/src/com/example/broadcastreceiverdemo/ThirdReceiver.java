package com.example.broadcastreceiverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ThirdReceiver extends BroadcastReceiver {
	
	private static final String TAG = "OrderedBroadcast";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String msg = getResultExtras(true).getString("msg");
		Log.i(TAG, "ThirdReceiver: " + msg);
		
		Bundle bundle = new Bundle();
		bundle.putString("msg", msg + "@ThirdReceiver");
		setResultExtras(bundle);
	}

}