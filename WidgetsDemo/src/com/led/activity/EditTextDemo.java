package com.led.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditTextDemo extends Activity {
    private EditText _edittext;
    private TextView _textview;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edittext);
        
        _edittext=(EditText)findViewById(R.id.EditText01);
        _textview=(TextView)findViewById(R.id.TextView01);
        _edittext.setHint("�������û���");
        _textview.setTextSize(20);
        
        _edittext.setOnKeyListener(new EditText.OnKeyListener(){
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				_textview.setText("�༭����������ǣ�\n"+_edittext.getText().toString());
				return false;
			}
        });
    }
}