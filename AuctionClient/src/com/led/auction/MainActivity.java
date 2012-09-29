package com.led.auction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	// 传递数据对象
	private Bundle bundle;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置layout
		setContentView(R.layout.main);
		
		// 获得当前intent
		Intent intent = this.getIntent();
		// 通过intent获得传递过来的bundle数据对象
		bundle = intent.getExtras();
		//实现拍卖物品按钮监听
		Button btnSearchOne=(Button) findViewById(R.id.goAdd);
		btnSearchOne.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//开始新的activity
				Intent intent=new Intent();
        		intent.setClass(MainActivity.this, AddItem.class);
        		// 将bundle存入intent
        		intent.putExtras(bundle);
        		startActivity(intent);
			}
		});
		//实现竞拍物品按钮监听
		Button btnSearchAll=(Button) findViewById(R.id.goBid);
		btnSearchAll.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//开始新的activity
				Intent intent=new Intent();
        		intent.setClass(MainActivity.this, KindList.class);
        		// 将bundle存入intent
        		intent.putExtras(bundle);
        		startActivity(intent);
			}
		});
	}
}
