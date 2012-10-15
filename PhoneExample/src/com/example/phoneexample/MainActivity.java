package com.example.phoneexample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	Button btnPhone;
	Button btnSms;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btnPhone = (Button) findViewById(R.id.phone);
        btnSms = (Button) findViewById(R.id.sms);
        
        btnPhone.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				ITelephone tpCallModule = (ITelephone) ITelephone.Stub.asInterface();
				Intent intent = new Intent();
//				intent.setAction("android.intent.action.CALL");//直接拨打电话
				intent.setAction("android.intent.action.DIAL");//跳转至拨号界面
				intent.setData(Uri.parse("tel:10086"));
				startActivity(intent);
			}
		});
        
        btnSms.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SmsManager SMS = SmsManager.getDefault();
				SMS.sendTextMessage("10086", null, "this is a test message", null, null);
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
