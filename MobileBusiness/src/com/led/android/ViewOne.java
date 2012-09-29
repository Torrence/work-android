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
	//商品详细信息列表
	private ListView listView;
	//列表控件适配器
	private ArrayAdapter<String> stringItemAdapter;
	//列表内容
	private List<String> product;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//加载layout
		this.setContentView(R.layout.viewone);
		//初始化列表内容
		product = new ArrayList<String>();
		//初始化适配器
		stringItemAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, product);
		//获得画面列表控件
		listView = (ListView) findViewById(R.id.listProduct);
		//为控件分配相应适配器
		listView.setAdapter(stringItemAdapter);
		//获得当前intent
		Intent intent = this.getIntent();
		//通过intent获得传递过来的bundle数据对象
		Bundle bundle = intent.getExtras();
		//获得商品详细信息并添加到列表内容中
		product.add("商品编号:" + bundle.getString("id"));
		product.add("商品名称:" + bundle.getString("name"));
		product.add("商品名称:" + bundle.getString("desc"));
		product.add("商品价格:" + bundle.getString("price"));
		product.add("商品产地:" + bundle.getString("area"));
		product.add("商品类型:" + bundle.getString("type"));
		//如果列表内容有变化则提醒适配器更新UI控件
		stringItemAdapter.notifyDataSetChanged();
		//实现返回按钮监听
		Button btn = (Button) findViewById(R.id.back02);
		btn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				//结束当前activity
				finish();
			}
		});
	}
}
