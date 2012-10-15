package com.ds.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */

	private Button startServerBtn;
	private Button startClientBtn;
	private Button startBluetoothBtn;
	private ButtonClickListener btnClickListener = new ButtonClickListener();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		startServerBtn = (Button) findViewById(R.id.startServerBtn);
		startClientBtn = (Button) findViewById(R.id.startClientBtn);
		startBluetoothBtn = (Button) findViewById(R.id.startBluetooth);

		startServerBtn.setOnClickListener(btnClickListener);
		startClientBtn.setOnClickListener(btnClickListener);
		startBluetoothBtn.setOnClickListener(btnClickListener);
	}

	class ButtonClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {

			case R.id.startBluetooth:
				// 打开蓝牙设备
				BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
				if (!adapter.isEnabled()) {
					Intent intent = new Intent(
							BluetoothAdapter.ACTION_REQUEST_ENABLE);
					startActivityForResult(intent, 0x1);
				}
				break;

			case R.id.startServerBtn:
				// 打开服务器
				Intent serverIntent = new Intent(MainActivity.this,
						ServerActivity.class);
				serverIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(serverIntent);
				break;
				
			case R.id.startClientBtn:
				// 打开客户端
				Intent clientIntent = new Intent(MainActivity.this,
						ClientActivity.class);
				clientIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(clientIntent);
				break;

			}
		}

	}

}