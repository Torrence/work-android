package com.led.auction;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends Activity {
	//登录框
	private LoginView loginView;
	//进度提示框
	protected ProgressDialog pgsDialog;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		loginView = new LoginView(this);
		//实现登录框的登录按钮监听
		loginView.setLoginOnClickListener(new Button.OnClickListener() {
			public void onClick(final View v) {
				//显示进度框
				pgsDialog = ProgressDialog.show(v.getContext(), "请稍等...",
						"正在为您登录...", true);
				//同时开始登录验证线程
				new Thread() {
					@Override
					public void run() {
						//调用登录验证方法
						loginView.loginPost(v);
						//进度框结束
						pgsDialog.dismiss();
					}
				}.start();
			}
		});
		//实现登录框的取消按钮监听
		loginView.setCancelOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		//设置当前activity的layout
		setContentView(loginView);
	}
}