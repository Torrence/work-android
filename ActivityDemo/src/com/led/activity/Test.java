package com.led.activity;

import com.led.activity.MainActivity.MyApp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

class Blah extends Activity {

	@Override
	public void onCreate(Bundle b) {
		
		MyApp appState = ((MyApp) getApplicationContext());
		String state = appState.getState();
		
	}
}
