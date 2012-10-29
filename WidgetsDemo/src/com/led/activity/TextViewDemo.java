package com.led.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class TextViewDemo extends Activity {
	private TextView textview;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.textview);

		textview = (TextView) findViewById(R.id.textView1);
		String string="你好！欢迎使用文本示例！";
		textview.setText(string);
		textview.setTextColor(Color.RED);
		textview.setTextSize(20);
		textview.setBackgroundColor(Color.BLUE);
	}
}