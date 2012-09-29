package com.led.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置layout
		setContentView(R.layout.main);
		//实现精确查询按钮监听
		Button btnSearchOne=(Button) findViewById(R.id.goSearchOne);
		btnSearchOne.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//开始新的activity
				Intent intent=new Intent();
        		intent.setClass(MainActivity.this, SearchOne.class);
        		startActivity(intent);
        		finish();
			}
		});
		//实现模糊查询按钮监听
		Button btnSearchAll=(Button) findViewById(R.id.goSearchAll);
		btnSearchAll.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//开始新的activity
				Intent intent=new Intent();
        		intent.setClass(MainActivity.this, SearchAll.class);
        		startActivity(intent);
        		finish();
			}
		});
	}
}
