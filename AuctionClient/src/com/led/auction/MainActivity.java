package com.led.auction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	// �������ݶ���
	private Bundle bundle;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ����layout
		setContentView(R.layout.main);
		
		// ��õ�ǰintent
		Intent intent = this.getIntent();
		// ͨ��intent��ô��ݹ�����bundle���ݶ���
		bundle = intent.getExtras();
		//ʵ��������Ʒ��ť����
		Button btnSearchOne=(Button) findViewById(R.id.goAdd);
		btnSearchOne.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//��ʼ�µ�activity
				Intent intent=new Intent();
        		intent.setClass(MainActivity.this, AddItem.class);
        		// ��bundle����intent
        		intent.putExtras(bundle);
        		startActivity(intent);
			}
		});
		//ʵ�־�����Ʒ��ť����
		Button btnSearchAll=(Button) findViewById(R.id.goBid);
		btnSearchAll.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//��ʼ�µ�activity
				Intent intent=new Intent();
        		intent.setClass(MainActivity.this, KindList.class);
        		// ��bundle����intent
        		intent.putExtras(bundle);
        		startActivity(intent);
			}
		});
	}
}
