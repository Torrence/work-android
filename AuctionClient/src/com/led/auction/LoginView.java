package com.led.auction;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.led.tools.HttpTool;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

public class LoginView extends FrameLayout {
	// 登录按钮
	protected Button btnLogin;
	// 取消按钮
	protected Button btnCancel;
	// 用户名输入框
	protected EditText edtUsername;
	// 密码输入框
	protected EditText edtPassword;
	// 用于接收子线程消息，配合主线程更新UI
	protected Handler handler;

	public LoginView(Context context) {
		super(context);
		// 初始化
		initViews();
	}

	private void initViews() {
		// 加载layout
		inflate(getContext(), R.layout.login, this);
		// 初始化控件
		btnLogin = (Button) findViewById(R.id.login);
		btnCancel = (Button) findViewById(R.id.cancel);
		edtUsername = (EditText) findViewById(R.id.username);
		edtPassword = (EditText) findViewById(R.id.password);
		handler = new Handler();
	}

	// 注册登录监听
	public void setLoginOnClickListener(OnClickListener l) {
		btnLogin.setOnClickListener(l);
	}

	// 注册取消监听
	public void setCancelOnClickListener(OnClickListener l) {
		btnCancel.setOnClickListener(l);
	}

	public boolean loginPost(final View v) {
		try {
			// 取得用户输入用户名
			String username = edtUsername.getText().toString();
			// 取得用户输入密码
			String password = edtPassword.getText().toString();
			// http请求地址
			String url = getResources().getString(R.string.baseUrl)
					+ "androidLogin.action";
			// http传输数据
			List<NameValuePair> datas = new ArrayList<NameValuePair>();
			// 添加数据
			datas.add(new BasicNameValuePair("username", username));
			datas.add(new BasicNameValuePair("password", password));
			// 发送http请求，获得服务器端返回数据
			InputStream is = HttpTool.sendDataByPost(url, datas);
			if (is != null) {
				String userId = HttpTool.convertStreamToString(is);
				// 身份验证通过
				if ("null".equals(userId)) {
					// 验证失败，显示message
					handler.post(new Runnable() {
						public void run() {
							Toast.makeText(v.getContext(), "用户名或密码错误",
									Toast.LENGTH_LONG).show();
						}
					});
					return false;
				} else {
					// 取得用户id
					Context context = v.getContext();
					Intent intent = new Intent(context, MainActivity.class);
					// 用于存储用户id
					Bundle bundle = new Bundle();
					bundle.putString("userId", userId);
					// 将bundle存入intent
					intent.putExtras(bundle);
					// 迁移画面
					context.startActivity(intent);
					// 显示message
					handler.post(new Runnable() {
						public void run() {
							Toast.makeText(v.getContext(), "登录成功",
									Toast.LENGTH_LONG).show();
						}
					});
					return true;
				}
			} else {
				// 网络连接失败，显示message
				handler.post(new Runnable() {
					public void run() {
						Toast.makeText(v.getContext(), "网络连接失败",
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
