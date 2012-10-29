package com.example.debugdemo;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	private String TAG = "DebugDemo";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Log.v(TAG, "output log with Log.v");
        Log.d(TAG, "output log with Log.d");
        Log.i(TAG, "output log with Log.i");
        Log.w(TAG, "output log with Log.w");
        Log.e(TAG, "output log with Log.e");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
