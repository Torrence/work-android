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

// �� Get ��ʽ�ϴ�����
public class Demo2 extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo2);
		TextView mTextView = (TextView) this.findViewById(R.id.resultGet);
		// http ��ַ "?par=abcdefg" �������ϴ��Ĳ���
		String httpUrl = "http://192.168.1.150:8080/android-test-webpage/httpget.jsp?par=abcdefg";
		// ��õ����
		String resultData = "";
		URL url = null;
		try {
			// ����һ�� URL ����
			url = new URL(httpUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if (url != null) {
			try {
				// ʹ�� HttpURLConnection ������
				HttpURLConnection urlConn = (HttpURLConnection) url
						.openConnection();
				// �õ���ȡ������ ( �� )
				InputStreamReader in = new InputStreamReader(
						urlConn.getInputStream());
				// Ϊ������� BufferedReader
				BufferedReader buffer = new BufferedReader(in);
				String inputLine = null;
				// ʹ��ѭ������ȡ��õ����
				while (((inputLine = buffer.readLine()) != null)) {
					// ������ÿһ�к������һ�� "\n" ������
					resultData += inputLine + "\n";
				}
				// �ر� InputStreamReader
				in.close();
				// �ر� http ����
				urlConn.disconnect();
				// ������ʾȡ�õ�����
				if (!"".equals(resultData)) {
					mTextView.setText(resultData);
				} else {
					mTextView.setText(" ��ȡ������Ϊ NULL");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Url NULL");
		}
	}
}
