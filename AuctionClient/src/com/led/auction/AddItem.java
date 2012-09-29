package com.led.auction;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import com.led.model.Kind;
import com.led.tools.HttpTool;
import com.led.tools.KindHandler;
import com.led.tools.ValidateTool;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddItem extends Activity {

	private String[] items = { "一天", "二天", "三天", "四天", "五天", "一个星期", "一个月",
			"一年" };
	private Spinner timeSpinner;
	private Spinner kindSpinner;
	private ArrayAdapter<String> adapter;
	// 品种列表
	private List<String> kindTitles;
	private EditText edtItemName;
	private EditText edtItemDesc;
	private EditText edtItemRemark;
	private EditText edtItemPrice;
	// 传递数据对象
	private Bundle bundle;
	// 用户ID
	private String userId;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置layout
		setContentView(R.layout.additem);

		// 获得竞拍价控件
		edtItemName = (EditText) findViewById(R.id.itemName);
		edtItemDesc = (EditText) findViewById(R.id.itemDesc);
		edtItemRemark = (EditText) findViewById(R.id.itemRemark);
		edtItemPrice = (EditText) findViewById(R.id.itemPrice);

		// 获得当前intent
		Intent intent = this.getIntent();
		// 通过intent获得传递过来的bundle数据对象
		bundle = intent.getExtras();
		userId = bundle.getString("userId");

		// 初始化品种变量
		kindTitles = new ArrayList<String>();
		// http请求地址
		String url = getResources().getString(R.string.baseUrl)
				+ "androidViewKinds.action";
		// http传输数据
		List<NameValuePair> datas = new ArrayList<NameValuePair>();
		// 发送http请求，获得服务器端返回数据
		InputStream xmlStream = HttpTool.sendDataByPost(url, datas);
		try {
			// 获取SAX解析工厂
			SAXParserFactory factory = SAXParserFactory.newInstance();
			// 获取SAX解析器
			SAXParser parser = factory.newSAXParser();
			// 获取XML读取器对象
			XMLReader xmlreader = parser.getXMLReader();
			// 获取品种专用处理器
			KindHandler handler = new KindHandler();
			// 设置为品种信息专用的XML读取器
			xmlreader.setContentHandler(handler);
			// 获取待解析的XML格式内容
			InputSource source = new InputSource(xmlStream);
			// 开始解析XML
			xmlreader.parse(source);
			// 获得解析后的商品列
			List<Kind> kinds = handler.getKinds();

			for (Kind k : kinds) {
				// 取得品种
				kindTitles.add(k.getId() + "." + k.getKindName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		timeSpinner = (Spinner) findViewById(R.id.spinnerTime);
		// 将可选内容与ArrayAdapter连接起来
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, items);
		// 设置下拉列表的风格
		adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		timeSpinner.setAdapter(adapter);
		/*
		 * typeSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
		 * public void onItemSelected(AdapterView<?> adapterView, View view, int
		 * position, long id) { String time=(String)
		 * adapterView.getItemAtPosition(position); } public void
		 * onNothingSelected(AdapterView<?> arg0) { // TODO Auto-generated
		 * method stub } });
		 */

		kindSpinner = (Spinner) findViewById(R.id.spinnerKind);
		// 将可选内容与ArrayAdapter连接起来
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, kindTitles);
		// 设置下拉列表的风格
		adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		kindSpinner.setAdapter(adapter);
		// 添加事件Spinner事件监听
		/*
		 * typeSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
		 * public void onItemSelected(AdapterView<?> adapterView, View view, int
		 * position, long id) { String type=(String)
		 * adapterView.getItemAtPosition(position); } public void
		 * onNothingSelected(AdapterView<?> arg0) { // TODO Auto-generated
		 * method stub
		 * 
		 * } });
		 */

		// 实现竞拍物品按钮监听
		Button btnAddItem = (Button) findViewById(R.id.addItem);
		btnAddItem.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// 取得物品名
				String name = edtItemName.getText().toString().trim();
				// 取得物品描述
				String desc = edtItemDesc.getText().toString().trim();
				// 取得物品备注
				String remark = edtItemRemark.getText().toString().trim();
				// 取得起拍价格
				String price = edtItemPrice.getText().toString().trim();
				// 非空容错
				if ("".equals(name) || "".equals(desc) || "".equals(remark)
						|| "".equals(price)) {
					Toast.makeText(v.getContext(), "请将信息填写完整",
							Toast.LENGTH_LONG).show();
					return;
				}
				// 价格数字容错处理
				if (!ValidateTool.isNumber(price)) {
					Toast
							.makeText(v.getContext(), "起拍价格式错误",
									Toast.LENGTH_LONG).show();
					return;
				}
				// 取得有效时间
				int index = timeSpinner.getSelectedItemPosition() + 1;
				String time = String.valueOf(index);
				switch (index) {
				case 6:
					time = "7";
					break;
				case 7:
					time = "30";
					break;
				case 8:
					time = "256";
					break;
				default:
					break;
				}
				// 取得物品种类
				String kind = (String) kindSpinner.getSelectedItem();
				String kindId = kind.substring(0, kind.indexOf("."));

				// http请求地址
				String url = getResources().getString(R.string.baseUrl)
						+ "androidAddItem.action";
				// http传输数据
				List<NameValuePair> datas = new ArrayList<NameValuePair>();
				// 添加数据
				datas.add(new BasicNameValuePair("itemName", name));
				datas.add(new BasicNameValuePair("itemDesc", desc));
				datas.add(new BasicNameValuePair("itemRemark", remark));
				datas.add(new BasicNameValuePair("itemPrice", price));
				datas.add(new BasicNameValuePair("itemTime", time));
				datas.add(new BasicNameValuePair("itemKind", kindId));
				datas.add(new BasicNameValuePair("userid", userId));
				// 发送http请求，获得服务器端返回 数据
				InputStream is = HttpTool.sendDataByPost(url, datas);
				String result = HttpTool.convertStreamToString(is);
				if ("success".equals(result)) {
					Toast.makeText(v.getContext(), "起拍成功", Toast.LENGTH_LONG)
							.show();
				}else{
					Toast.makeText(v.getContext(), "起拍失败", Toast.LENGTH_LONG)
					.show();
				}
			}
		});
	}
}
