package com.led.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	private Button _btnFrame;
	private Button _btnLinear;
	private Button _btnAbsolute;
	private Button _btnRelative;
	private Button _btnTable;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		_btnFrame = (Button) findViewById(R.id.button1);
		_btnLinear = (Button) findViewById(R.id.button2);
		_btnAbsolute = (Button) findViewById(R.id.button3);
		_btnRelative = (Button) findViewById(R.id.button4);
		_btnTable = (Button) findViewById(R.id.button5);

		_btnFrame.setOnClickListener(this);
		_btnLinear.setOnClickListener(this);
		_btnAbsolute.setOnClickListener(this);
		_btnRelative.setOnClickListener(this);
		_btnTable.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.button1:
			intent.setClass(MainActivity.this, Frame.class);
			startActivity(intent);
			break;
		case R.id.button2:
			intent.setClass(MainActivity.this, Linear.class);
			startActivity(intent);
			break;
		case R.id.button3:
			intent.setClass(MainActivity.this, Absolute.class);
			startActivity(intent);
			break;
		case R.id.button4:
			intent.setClass(MainActivity.this, Relative.class);
			startActivity(intent);
			break;
		case R.id.button5:
			intent.setClass(MainActivity.this, Table.class);
			startActivity(intent);
			break;
		}
	}
}