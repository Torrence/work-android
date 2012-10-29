package com.led.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class SpinnerDemo extends Activity {
	private static final String[] colors = { "red", "black", "white", "blue",
			"green" };
	private Spinner _spinner1;
	private TextView _textview1;
	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spinner);

		_spinner1 = (Spinner) findViewById(R.id.spinner1);
		_textview1 = (TextView) findViewById(R.id.textView3);

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, colors);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		_spinner1.setAdapter(adapter);
		_spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				_textview1.setText("你选择的颜色是：" + colors[position]);
				parent.setVisibility(View.VISIBLE);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
	}
}