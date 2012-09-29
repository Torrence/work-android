package com.led.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends Activity {
	//��¼��
	private LoginView loginView;
	//������ʾ��
	protected ProgressDialog pgsDialog;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		loginView = new LoginView(this);
		//ʵ�ֵ�¼��ĵ�¼��ť����
		loginView.setLoginOnClickListener(new Button.OnClickListener() {
			public void onClick(final View v) {
				//��ʾ���ȿ�
				pgsDialog = ProgressDialog.show(v.getContext(), "waiting...",
						"loading...", true);
				//ͬʱ��ʼ��¼��֤�߳�
				new Thread() {
					@Override
					public void run() {
						//���õ�¼��֤����
						if (loginView.loginPost(v)) {
							finish();
						}
						//���ȿ����
						pgsDialog.dismiss();
					}
				}.start();
			}
		});
		//ʵ�ֵ�¼���ȡ����ť����
		loginView.setCancelOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		//���õ�ǰactivity��layout
		setContentView(loginView);
	}
}