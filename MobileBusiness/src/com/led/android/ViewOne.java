package com.led.android;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ViewOne extends Activity {
	//��Ʒ��ϸ��Ϣ�б�
	private ListView listView;
	//�б�ؼ�������
	private ArrayAdapter<String> stringItemAdapter;
	//�б�����
	private List<String> product;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//����layout
		this.setContentView(R.layout.viewone);
		//��ʼ���б�����
		product = new ArrayList<String>();
		//��ʼ��������
		stringItemAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, product);
		//��û����б�ؼ�
		listView = (ListView) findViewById(R.id.listProduct);
		//Ϊ�ؼ�������Ӧ������
		listView.setAdapter(stringItemAdapter);
		//��õ�ǰintent
		Intent intent = this.getIntent();
		//ͨ��intent��ô��ݹ�����bundle���ݶ���
		Bundle bundle = intent.getExtras();
		//�����Ʒ��ϸ��Ϣ����ӵ��б�������
		product.add("��Ʒ���:" + bundle.getString("id"));
		product.add("��Ʒ����:" + bundle.getString("name"));
		product.add("��Ʒ����:" + bundle.getString("desc"));
		product.add("��Ʒ�۸�:" + bundle.getString("price"));
		product.add("��Ʒ����:" + bundle.getString("area"));
		product.add("��Ʒ����:" + bundle.getString("type"));
		//����б������б仯����������������UI�ؼ�
		stringItemAdapter.notifyDataSetChanged();
		//ʵ�ַ��ذ�ť����
		Button btn = (Button) findViewById(R.id.back02);
		btn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				//������ǰactivity
				finish();
			}
		});
	}
}
