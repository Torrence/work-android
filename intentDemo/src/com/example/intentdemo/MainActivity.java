package com.example.intentdemo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    public void gotoTargetActivity(View view) {
    	Intent intent = new Intent("com.scott.intent.action.TARGET");
    	intent.setData(Uri.parse("scott://com.scott.intent.data:7788/target"));
    	Bundle bundle = new Bundle();
    	bundle.putInt("id", 0);
    	bundle.putString("name", "scott");
    	intent.putExtras(bundle);
    	startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
