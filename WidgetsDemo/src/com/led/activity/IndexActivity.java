package com.led.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class IndexActivity extends Activity implements OnClickListener{
	
	private Button _btnTextView;
	private Button _btnButton;
	private Button _btnEditText;
	private Button _btnRadioButton;
	private Button _btnCheckBox;
	private Button _btnSpinner;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        _btnTextView=(Button)findViewById(R.id.btnTextView);
        _btnButton=(Button)findViewById(R.id.btnButton);
        _btnEditText=(Button)findViewById(R.id.btnEditText);
        _btnRadioButton=(Button)findViewById(R.id.btnRadioButton);
        _btnCheckBox=(Button)findViewById(R.id.btnCheckBox);
        _btnSpinner=(Button)findViewById(R.id.btnSpinner);
        
        _btnTextView.setOnClickListener(this);
        _btnButton.setOnClickListener(this);
        _btnEditText.setOnClickListener(this);
        _btnRadioButton.setOnClickListener(this);
        _btnCheckBox.setOnClickListener(this);
        _btnSpinner.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		switch (v.getId()) {
		case R.id.btnTextView:
			intent.setClass(IndexActivity.this,TextViewDemo.class);
			startActivity(intent);
			break;
		case R.id.btnButton:
			intent.setClass(IndexActivity.this,ButtonDemo.class);
			startActivity(intent);
			break;
		case R.id.btnEditText:
			intent.setClass(IndexActivity.this,EditTextDemo.class);
			startActivity(intent);
			break;
		case R.id.btnRadioButton:
			intent.setClass(IndexActivity.this,RadioButtonDemo.class);
			startActivity(intent);
			break;
		case R.id.btnCheckBox:
			intent.setClass(IndexActivity.this,CheckBoxDemo.class);
			startActivity(intent);
			break;
		case R.id.btnSpinner:
			intent.setClass(IndexActivity.this,SpinnerDemo.class);
			startActivity(intent);
			break;
		}
	}
}