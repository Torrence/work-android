package com.led.android;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import com.led.model.Product;
import com.led.tools.HttpTool;
import com.led.tools.ProductHandler;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SearchAll extends Activity {
	// 商品列表
	private ListView listview;
	// 列表控件适配器
	private SimpleAdapter listItemAdapter;
	// 列表内容
	private ArrayList<HashMap<String, Object>> listItem;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设定activity的layout
		setContentView(R.layout.searchall);
		// 初始化列表内容
		listItem = new ArrayList<HashMap<String, Object>>();
		// 初始化列表适配器
		listItemAdapter = new SimpleAdapter(this, listItem, R.layout.item,
				new String[] { "title" }, new int[] { R.id.title });
		// 获取画面上的商品列表控件
		listview = (ListView) findViewById(R.id.listProducts);
		// 设置控件适配器
		listview.setAdapter(listItemAdapter);
		// 实现列表控件的单击事件监听
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent();
				intent.setClass(SearchAll.this, ViewOne.class);
				// 获取点击项
				HashMap<String, Object> map = (HashMap<String, Object>) listItem
						.get(arg2);
				// 获取该商品数据
				Product product = (Product) map.get("product");
				// 封装进bundle用于intent间的数据传输
				Bundle bundle = new Bundle();
				bundle.putString("id", String.valueOf(product.getId()));
				bundle.putString("name", product.getName());
				bundle.putString("desc", product.getDescription());
				bundle.putString("price", product.getPrice());
				bundle.putString("area", product.getArea());
				bundle.putString("type", product.getType());
				// 将bundle存入intent
				intent.putExtras(bundle);
				// 开始另一个activity
				startActivity(intent);
			}
		});
		// 实现查询按钮监听
		Button btnSearch = (Button) findViewById(R.id.searchall);
		btnSearch.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// 取得画面输入框对象
				EditText key = (EditText) findViewById(R.id.keyword);
				// 取得输入框内容
				String keyword = key.getText().toString();
				// http请求地址
				String url = "http://192.168.1.150:8080/AndroidHost/searchall";
				// http传输数据
				List<NameValuePair> datas = new ArrayList<NameValuePair>();
				// 添加数据
				datas.add(new BasicNameValuePair("keyword", keyword));
				// 发送http请求，获得服务器端返回数据
				InputStream xmlStream = HttpTool.sendDataByPost(url, datas);

				try {
					// 获取SAX解析工厂
					SAXParserFactory factory = SAXParserFactory.newInstance();
					// 获取SAX解析器
					SAXParser parser = factory.newSAXParser();
					// 获取XML读取器对象
					XMLReader xmlreader = parser.getXMLReader();
					// 获取商品专用处理器
					ProductHandler handler = new ProductHandler();
					// 设置为商品信息专用的XML读取器
					xmlreader.setContentHandler(handler);
					// 获取待解析的XML格式内容
					InputSource source = new InputSource(xmlStream);
					// 开始解析XML
					xmlreader.parse(source);
					// 获得解析后的商品列
					List<Product> products = handler.getProducts();
					// 清空列表内容
					listItem.clear();
					// 循环添加商品信息
					for (int i = 0; i < products.size(); i++) {
						Product product = (Product) products.get(i);
						HashMap<String, Object> map = new HashMap<String, Object>();
						// 商品显示标题
						map.put("title", String.valueOf(i + 1) + "."
								+ product.getName());
						// 商品详细信息
						map.put("product", product);
						// 添加到列表内容中
						listItem.add(map);
					}
					// 如果列表内容有变化则提醒适配器更新UI控件
					listItemAdapter.notifyDataSetChanged();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		// 实现返回按钮监听
		Button btnBack = (Button) findViewById(R.id.back03);
		btnBack.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// 开始新的activity
				Intent intent = new Intent();
				intent.setClass(SearchAll.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
}
