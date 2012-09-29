package com.led.activity;

import com.led.model.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

public class NextActivity extends Activity {

	private static final String TAG = "LifeCycleDemo";
	private User user1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.next);
		Log.e(TAG, "NextActivity->start onCreate~~~");

		// String username = getIntent().getStringExtra("name");
		TextView tv = (TextView) findViewById(R.id.txtMsg);

		user1 = (User) getIntent().getSerializableExtra("user");

		Bundle bundle = getIntent().getExtras();
		String username = bundle.getString("name");
		String password = bundle.getString("pwd");
		
		tv.setText(bundle.getString("name"));
		// tv.setText(user1.getUsername() + " " + user1.getPassword());
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			final Intent intent = new Intent();
			intent.setClass(NextActivity.this, MainActivity.class);
			intent.putExtra("returnUser", user1);
			this.setResult(1);
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.e(TAG, "NextActivity->start onDestroy~~~");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.e(TAG, "NextActivity->start onPause~~~");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.e(TAG, "NextActivity->start onRestart~~~");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.e(TAG, "NextActivity->start onResume~~~");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.e(TAG, "NextActivity->start onStart~~~");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.e(TAG, "NextActivity->start onStop~~~");
	}
}