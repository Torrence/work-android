package com.example.bluetoothexample;

import java.io.IOException;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class ClientSocketActivity  extends Activity
{
	private static final String TAG = "lm";
	private static final int REQUEST_DISCOVERY = 0x1;;
	private Handler _handler = new Handler();
	private BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter();
	
	private TextView mText;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
		WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		setContentView(R.layout.client_socket);
		
		mText = (TextView) findViewById(R.id.info_message);
		
		if (!_bluetooth.isEnabled()) {
			finish();
			return;
		}
		Intent intent = new Intent(this, DiscoveryActivity.class);
		/* 提示选择一个要连接的服务器 */
		Toast.makeText(this, "select device to connect", Toast.LENGTH_SHORT).show();
		/* 跳转到搜索的蓝牙设备列表区，进行选择 */
		startActivityForResult(intent, REQUEST_DISCOVERY);
	}
	/* 选择了服务器之后进行连接 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode != REQUEST_DISCOVERY) {
			return;
		}
		if (resultCode != RESULT_OK) {
			return;
		}
		final BluetoothDevice device = data.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		Log.d("lm", device.toString());
		
		mText.setText(getResources().getString(R.string.connecting) + device.getName());
		
		new Thread() {
			public void run() {
				/* 连接 */
				connect(device);
			};
		}.start();
	}
	protected void connect(BluetoothDevice device) {
		BluetoothSocket socket = null;
		try {
			//创建一个Socket连接：只需要服务器在注册时的UUID号
			// socket = device.createRfcommSocketToServiceRecord(BluetoothProtocols.OBEX_OBJECT_PUSH_PROTOCOL_UUID);
			socket = device.createRfcommSocketToServiceRecord(UUID.fromString("a60f35f0-b93a-11de-8a39-08002009c666"));
			//连接
			socket.connect();
			
//			Method m;
//			try {
//				m = device.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
//				try {
//					socket = (BluetoothSocket) m.invoke(device, 1);
//					_bluetooth.cancelDiscovery();
//					try {
//						socket.connect();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				} catch (IllegalArgumentException e) {
//					e.printStackTrace();
//				} catch (IllegalAccessException e) {
//					e.printStackTrace();
//				} catch (InvocationTargetException e) {
//					e.printStackTrace();
//				}
//			} catch (SecurityException e) {
//				e.printStackTrace();
//			} catch (NoSuchMethodException e) {
//				e.printStackTrace();
//			}
			
		} catch (Exception e) {
			Log.e(TAG, "", e);
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					Log.e(TAG, "", e);
				}
			}
		}
	}
}