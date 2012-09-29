package com.led.android;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.led.tools.HttpTool;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

public class LoginView extends FrameLayout {
	//��¼��ť
	protected Button btnLogin;
	//ȡ����ť
	protected Button btnCancel;
	//�û��������
	protected EditText edtUsername;
	//���������
	protected EditText edtPassword;
	//���ڽ������߳���Ϣ��������̸߳���UI
	protected Handler handler;

	public LoginView(Context context) {
		super(context);
		//��ʼ��
		initViews();
	}

	private void initViews() {
		//����layout
		inflate(getContext(), R.layout.login, this);
		//��ʼ���ؼ�
		btnLogin = (Button) findViewById(R.id.login);
		btnCancel = (Button) findViewById(R.id.cancel);
		edtUsername = (EditText) findViewById(R.id.username);
		edtPassword = (EditText) findViewById(R.id.password);
		handler = new Handler();
	}

	//ע���¼����
	public void setLoginOnClickListener(OnClickListener l) {
		btnLogin.setOnClickListener(l);
	}

	//ע��ȡ������
	public void setCancelOnClickListener(OnClickListener l) {
		btnCancel.setOnClickListener(l);
	}

	public boolean loginPost(final View v) {
		try {
			//ȡ���û������û���
			String username = edtUsername.getText().toString();
			//ȡ���û���������
			String password = edtPassword.getText().toString();
			//http�����ַ
			String url = "http://192.168.1.150:8080/AndroidHost/login";
			//http��������
			List<NameValuePair> datas = new ArrayList<NameValuePair>();
			//�������
			datas.add(new BasicNameValuePair("username", username));
			datas.add(new BasicNameValuePair("password", password));
			//����http���󣬻�÷������˷�������
			InputStream is = HttpTool.sendDataByPost(url, datas);
			if (is != null) {
				//�����֤ͨ��
				if ("success".equals(HttpTool.convertStreamToString(is))) {
					Context context = v.getContext();
					//Ǩ�Ƶ�������
					context.startActivity(new Intent(context, MainActivity.class));
					//��ʾmessage
					handler.post(new Runnable() {
						public void run() {
							Toast.makeText(v.getContext(), "login success",
									Toast.LENGTH_LONG).show();
						}
					});
					return true;
				} else {
					//��֤ʧ�ܣ���ʾmessage
					handler.post(new Runnable() {
						public void run() {
							Toast.makeText(v.getContext(), "user wrong",
									Toast.LENGTH_LONG).show();
						}
					});
					return false;
				}
			} else {
				//��������ʧ�ܣ���ʾmessage
				handler.post(new Runnable() {
					public void run() {
						Toast.makeText(v.getContext(), "connect error",
								Toast.LENGTH_LONG).show();
					}
				});
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
