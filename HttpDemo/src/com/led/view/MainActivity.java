package com.led.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{
	private Button _btnDemo1;
	private Button _btnDemo2;
	private Button _btnDemo3;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        _btnDemo1=(Button)findViewById(R.id.button1);
        _btnDemo2=(Button)findViewById(R.id.button2);
        _btnDemo3=(Button)findViewById(R.id.button3);
        
        _btnDemo1.setOnClickListener(this);
        _btnDemo2.setOnClickListener(this);
        _btnDemo3.setOnClickListener(this);
    }
    
    @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		switch (v.getId()) {
		case R.id.button1:
			intent.setClass(MainActivity.this,Demo1.class);
			startActivity(intent);
			break;
		case R.id.button2:
			intent.setClass(MainActivity.this,Demo2.class);
			startActivity(intent);
			break;
		case R.id.button3:
			intent.setClass(MainActivity.this,Demo3.class);
			startActivity(intent);
			break;
		}
	}
}