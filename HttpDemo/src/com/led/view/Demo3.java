package com.led.view;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

//�� post ��ʽ�ϴ�����
public class Demo3 extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo3);
		TextView mTextView = (TextView) this.findViewById(R.id.resultPost);
		// http ��ַ "?par=abcdefg" �������ϴ��Ĳ���
		String httpUrl = "http://192.168.1.150:8080/android-test-webpage/httpget.jsp";
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
				// ��Ϊ����� post ���� , ������Ҫ����Ϊ true
				urlConn.setDoOutput(true);
				urlConn.setDoInput(true);
				// ������ POST ��ʽ
				urlConn.setRequestMethod("POST");
				// Post ������ʹ�û���
				urlConn.setUseCaches(false);
				urlConn.setInstanceFollowRedirects(true);
				// ���ñ������ӵ� Content-type ������Ϊ application/x-www-form-urlencoded ��
				urlConn.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
				// ���ӣ��� postUrl.openConnection() ���˵����ñ���Ҫ�� connect ֮ǰ��ɣ�
				// Ҫע����� connection.getOutputStream �������Ľ��� connect ��
				urlConn.connect();
				// DataOutputStream ��
				DataOutputStream out = new DataOutputStream(
						urlConn.getOutputStream());
				// Ҫ�ϴ��Ĳ���
				String content = "par="
						+ URLEncoder.encode("ABCDEFG", "gb2312");
				// ��Ҫ�ϴ�������д������
				out.writeBytes(content);
				// ˢ�¡��ر�
				out.flush();
				out.close();
				// ��ȡ���
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(urlConn.getInputStream()));
				String inputLine = null;
				// ʹ��ѭ������ȡ��õ����
				while (((inputLine = reader.readLine()) != null)) {
					// ������ÿһ�к������һ�� "\n" ������
					resultData += inputLine + "\n";
				}
				reader.close();
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