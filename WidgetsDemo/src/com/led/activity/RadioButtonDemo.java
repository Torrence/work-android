package com.led.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RadioButtonDemo extends Activity {
	private RadioGroup _radiogroup;
	private RadioButton _radiobutton1;
	private RadioButton _radiobutton2;
	private RadioButton _radiobutton3;
	private RadioButton _radiobutton4;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.radiobutton);

		_radiogroup = (RadioGroup) findViewById(R.id.radiogroup01);
		_radiobutton1 = (RadioButton) findViewById(R.id.radioButton1);
		_radiobutton2 = (RadioButton) findViewById(R.id.radioButton2);
		_radiobutton3 = (RadioButton) findViewById(R.id.radioButton3);
		_radiobutton4 = (RadioButton) findViewById(R.id.radioButton4);

		_radiogroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						if (checkedId == _radiobutton1.getId()) {
							displayToast(_radiobutton1.getText().toString());
						}
						if (checkedId == _radiobutton2.getId()) {
							displayToast(_radiobutton2.getText().toString());
						}
						if (checkedId == _radiobutton3.getId()) {
							displayToast(_radiobutton3.getText().toString());
						}
						if (checkedId == _radiobutton4.getId()) {
							displayToast(_radiobutton4.getText().toString());
						}
					}
				});
	}

	public void displayToast(String str) {
		Toast toast = Toast.makeText(this, "您选择了" + str, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.TOP, 0, 300);
		toast.show();
	}
}