package com.led.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button btnNext = (Button) findViewById(R.id.btnNext);
		btnNext.setOnClickListener(new MyClickListener());
	}

	class MyClickListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			// //生成Intent对象（包含了ctivity间传的Data，param）;相当于一个请求
			// Intent intent=new Intent();
			// //键值对
			// intent.putExtra("extra", "HelloAndroid!This is ActivityDemo!");
			// //从此activity传到另一Activity
			// intent.setClass(MainActivity.this, NextActivity.class);
			// //启动另一个Activity
			// startActivity(intent);
			// 启动Android发短信的Activity
			// Intent在两Activity传递，Activity可不在同一应用程序下
			Uri uri = Uri.parse("smsto:1580046882*");
			Intent intent = new Intent(Intent.ACTION_SENDTO, uri);

			intent.putExtra("sms", "sms Content");
			startActivity(intent);
		}
	}

	class MyApp extends Application {
		private String myState;

		public String getState() {
			return myState;
		}

		public void setState(String s) {
			myState = s;
		}
	}
}