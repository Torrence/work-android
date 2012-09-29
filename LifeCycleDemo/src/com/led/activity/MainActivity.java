package com.led.activity;

import com.led.model.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private static final String TAG = "LifeCycleDemo";
	private Button btnNext;
	private String name = "led";
	private User user1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Log.e(TAG, "start onCreate~~~");

		btnNext = (Button) findViewById(R.id.btnNext);
		btnNext.setOnClickListener(new MyClickListener());
	}

	class MyClickListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {

			// User mUser = new User("led", "123456");
			// Intent intent = new Intent();
			// intent.setClass(MainActivity.this, NextActivity.class);
			// intent.putExtra("user", mUser);
			//
			// intent.putExtra("name", name);
			// startActivity(intent);

			// user1 = new User("led", "123456");
			// Intent intent = new Intent();
			// intent.setClass(MainActivity.this, NextActivity.class);
			// intent.putExtra("user", user1);
			// startActivityForResult(intent, 1);

			String username = "led";
			String password = "123456";

			Intent intent = new Intent();
			intent.setClass(MainActivity.this, NextActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("name", username);
			bundle.putString("pwd", password);
			intent.putExtras(bundle);
			startActivity(intent);

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 1 || data.getSerializableExtra("returnUser") != null) {
			user1 = (User) getIntent().getSerializableExtra("returnUser");
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.e(TAG, "start onDestroy~~~");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.e(TAG, "start onPause~~~");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.e(TAG, "start onRestart~~~");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.e(TAG, "start onResume~~~");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.e(TAG, "start onStart~~~");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.e(TAG, "start onStop~~~");
	}
}