package com.example.intentdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TargetActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.target);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		int id = bundle.getInt("id");
		String name = bundle.getString("name");
		
		TextView textview = (TextView) findViewById(R.id.textview);
		textview.setText("The data get from MainActivity: id = " + id + ", name = " + name);
	}

}
