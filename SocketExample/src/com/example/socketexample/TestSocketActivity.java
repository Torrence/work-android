package com.example.socketexample;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

@SuppressLint("NewApi")
public class TestSocketActivity extends Activity {
	private TextView myTextView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		int version = android.os.Build.VERSION.SDK_INT;
		if (version > 10) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectDiskReads().detectDiskWrites().detectNetwork() // or
																			// .detectAll()
																			// for
																			// all
																			// detectable
																			// problems
					.penaltyLog().build());

			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
					.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
					.penaltyLog().penaltyDeath().build());
		}

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_socket);
		myTextView = (TextView) findViewById(R.id.msgTextView01);
		String msg = "";
		try {
			while (true) {//1次连接不一定成功，循环连接直到成功获取所发送的内容
				Socket socket = new Socket("192.168.1.150", 8888);// 此处的IP地址为执行server程序的机器的IP地址
				InputStream in = socket.getInputStream();
				byte[] buffer = new byte[in.available()];
				in.read(buffer);
				msg = new String(buffer);
				if (msg.contains("Hello Android"))
					break;
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		myTextView.setText(msg);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_test_socket, menu);
		return true;
	}
}
