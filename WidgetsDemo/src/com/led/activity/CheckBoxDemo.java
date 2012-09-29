package com.led.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class CheckBoxDemo extends Activity {
	List<String> _checkedIds;
	Button _btnOk;
	TextView _lblResult;
	CheckBox _chk1;
	CheckBox _chk2;
	CheckBox _chk3;
	CheckBox _chk4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkbox);

		_btnOk = (Button) findViewById(R.id.checkboxlayout_btnOk);
		_btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (_checkedIds == null || _checkedIds.isEmpty()) {
					_lblResult.setText("你没有选择任何项");
				} else {
					StringBuffer sb = new StringBuffer();
					for (String id : _checkedIds) {
						sb.append(id);
						sb.append(",");
						sb.append(((CheckBox) findViewById(Integer.parseInt(id)))
								.getText());
						sb.append(",");
					}
					sb.deleteCharAt(sb.lastIndexOf(","));
					_lblResult.setText(sb.toString());
				}
			}

		});
		_chk1 = (CheckBox) findViewById(R.id.checkboxlayout_chk1);
		_chk1.setOnCheckedChangeListener(checkboxChecked());
		_chk2 = (CheckBox) findViewById(R.id.checkboxlayout_chk2);
		_chk2.setOnCheckedChangeListener(checkboxChecked());
		_chk3 = (CheckBox) findViewById(R.id.checkboxlayout_chk3);
		_chk3.setOnCheckedChangeListener(checkboxChecked());
		_chk4 = (CheckBox) findViewById(R.id.checkboxlayout_chk4);
		_chk4.setOnCheckedChangeListener(checkboxChecked());
		_lblResult = (TextView) findViewById(R.id.checkboxlayout_lbleResult);

	}

	private OnCheckedChangeListener checkboxChecked() {
		return new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					if (_checkedIds == null)
						_checkedIds = new ArrayList<String>();
					_checkedIds.add(String.valueOf(buttonView.getId()));
				} else {
					if (_checkedIds != null
							&& _checkedIds.isEmpty() == false
							&& _checkedIds.contains(String.valueOf(buttonView
									.getId())))
						_checkedIds.remove(String.valueOf(buttonView.getId()));
				}
			}
		};
	}
}
