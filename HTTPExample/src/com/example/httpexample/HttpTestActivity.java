package com.example.httpexample;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("NewApi")
public class HttpTestActivity extends Activity implements OnClickListener {
	HttpURLConnectionExample httpurlconnection;
	HttpClientExample httpclient;
	Button btnget1, btnpost1, btnget2, btnpost2;
	TextView text;
	String result = null;

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
        setContentView(R.layout.activity_http_test);
        
        btnget1 = (Button) findViewById(R.id.btnget1);
        btnpost1 = (Button) findViewById(R.id.btnpost1);
        btnget2 = (Button) findViewById(R.id.btnget2);
        btnpost2 = (Button) findViewById(R.id.btnpost2);
        text = (TextView) findViewById(R.id.text);
        
        btnget1.setOnClickListener(this);
        btnpost1.setOnClickListener(this);
        btnget2.setOnClickListener(this);
        btnpost2.setOnClickListener(this);
        
        httpurlconnection = new HttpURLConnectionExample();
        httpclient = new HttpClientExample();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_http_test, menu);
        return true;
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.btnget1:
			try {
				result = httpurlconnection.HttpGetMethod();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.btnpost1:
			try {
				result = httpurlconnection.HttpPostMethod("user", "test");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.btnget2:
			result = httpclient.HttpGetMethod();
			break;
		case R.id.btnpost2:
			result = httpclient.HttpPostMethod("user", "test");
			break;
		}
		text.setText(result);
	}
}
