package com.led.activity;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

//ʾ��������������ʶ�����ͼ��API. 

public class MainActivity extends Activity implements OnClickListener {

	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
	private ListView mList;

	/**
	 * 
	 * Called with the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// �Ǵ�����XML�ĳ���UI���õ�����
		setContentView(R.layout.main);
		// Ϊ�Ժ�õ���չʾ��Ʒ���໥����
		Button speakButton = (Button) findViewById(R.id.btn_speak);
		mList = (ListView) findViewById(R.id.list);

		// ��鿴���Ƿ���ʶ�
		PackageManager pm = getPackageManager();
		List activities = pm.queryIntentActivities(

		new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities.size() != 0) {
			speakButton.setOnClickListener(this);
		} else {
			speakButton.setEnabled(false);
			speakButton.setText("Recognizer not present");
		}
	}

	/**
	 * 
	 * �����ʼ����ʶ��ť��
	 */
	public void onClick(View v) {
		if (v.getId() == R.id.btn_speak) {
			startVoiceRecognitionActivity();
		}
	}

	/**
	 * 
	 * ��ʼ����ʶ��Ļ��
	 */
	private void startVoiceRecognitionActivity() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
				"Speech recognition demo");
		startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
	}

	/**
	 * 
	 * ��������ʶ��
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE
				&& resultCode == RESULT_OK) {
			// �����б���ͼ���ַ����ı�����Ϊ��������
			ArrayList matches = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			mList.setAdapter(new ArrayAdapter(this,
					android.R.layout.simple_list_item_1, matches));
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
