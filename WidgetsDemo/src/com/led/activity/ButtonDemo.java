package com.led.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ButtonDemo extends Activity {
	private Button _button1;
	private Button _button2;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.button);
		Toast toast = Toast.makeText(ButtonDemo.this, "欢迎使用按钮示例！",
				Toast.LENGTH_LONG);
		toast.show();

		_button1 = (Button) findViewById(R.id.button1);
		_button2 = (Button) findViewById(R.id.button2);
		_button1.setText("确定");
		_button2.setText("返回");
		_button1.setWidth(200);
		_button2.setWidth(100);
		_button1.setTextColor(Color.RED);
		_button2.setTextColor(Color.BLUE);
		_button1.setTextSize(40);
		_button2.setTextSize(20);
		_button1.setBackgroundColor(Color.GRAY);

		_button1.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast toast = Toast.makeText(ButtonDemo.this,
						"您点击了" + _button1.getText() + "按钮！", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.TOP, 0, 200);
				toast.show();
			}
		});

		_button2.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}