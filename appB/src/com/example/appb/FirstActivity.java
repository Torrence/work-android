package com.example.appb;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FirstActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(this.toString() + "\n" + "task id: " + getTaskId());
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent("android.intent.action.APP_A_SECOND_ACTIVITY");
//				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				
//				Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
//				startActivity(intent);
				
//				Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com.hk"));
//				startActivity(viewIntent);
				
//				Intent intent = new Intent("android.intent.action.APP_A_SECOND_ACTIVITY");
//				startActivity(intent);
				
				Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
				startActivity(intent);
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.first, menu);
        return true;
    }
}
