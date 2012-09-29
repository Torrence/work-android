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
			// //����Intent���󣨰�����ctivity�䴫��Data��param��;�൱��һ������
			// Intent intent=new Intent();
			// //��ֵ��
			// intent.putExtra("extra", "HelloAndroid!This is ActivityDemo!");
			// //�Ӵ�activity������һActivity
			// intent.setClass(MainActivity.this, NextActivity.class);
			// //������һ��Activity
			// startActivity(intent);
			// ����Android�����ŵ�Activity
			// Intent����Activity���ݣ�Activity�ɲ���ͬһӦ�ó�����
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