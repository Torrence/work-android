package com.led.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class NextActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next);
        
        String extra = getIntent().getStringExtra("extra");
		TextView tv = (TextView) findViewById(R.id.txtMsg);
		tv.setText(extra);
    }
}