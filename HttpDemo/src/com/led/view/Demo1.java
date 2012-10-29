package com.led.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Demo1 extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo1);

		TextView mTextView = (TextView) this.findViewById(R.id.result);
		// http 地址
		String httpUrl = "http://www.baidu.com";
		// 获得的数据
		String resultData = "";
		URL url = null;
		try {
			// 构造一个 URL 对象
			url = new URL(httpUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if (url != null) {
			try {
				// 使用 HttpURLConnection 打开连接
				HttpURLConnection urlConn = (HttpURLConnection) url
						.openConnection();
				// 得到读取的内容 ( 流 )
				InputStreamReader in = new InputStreamReader(
						urlConn.getInputStream());
				// 为输出创建 BufferedReader
				BufferedReader buffer = new BufferedReader(in);
				String inputLine = null;
				// 使用循环来读取获得的数据
				while (((inputLine = buffer.readLine()) != null)) {
					// 我们在每一行后面加上一个 "\n" 来换行
					resultData += inputLine + "\n";
				}
				// 关闭 InputStreamReader
				in.close();
				// 关闭 http 连接
				urlConn.disconnect();
				// 设置显示取得的内容
				if (!"".equals(resultData)) {
					mTextView.setText(resultData);
				} else {
					mTextView.setText("读取的内容为 NULL");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Url NULL");
		}
	}
}