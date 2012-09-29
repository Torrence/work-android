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
		//����layout
		setContentView(R.layout.main);
		//ʵ�־�ȷ��ѯ��ť����
		Button btnSearchOne=(Button) findViewById(R.id.goSearchOne);
		btnSearchOne.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//��ʼ�µ�activity
				Intent intent=new Intent();
        		intent.setClass(MainActivity.this, SearchOne.class);
        		startActivity(intent);
        		finish();
			}
		});
		//ʵ��ģ����ѯ��ť����
		Button btnSearchAll=(Button) findViewById(R.id.goSearchAll);
		btnSearchAll.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//��ʼ�µ�activity
				Intent intent=new Intent();
        		intent.setClass(MainActivity.this, SearchAll.class);
        		startActivity(intent);
        		finish();
			}
		});
	}
}
