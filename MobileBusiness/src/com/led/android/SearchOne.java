package com.led.android;

import java.io.InputStream;
import java.util.ArrayList;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchOne extends Activity {
	//商品编号显示框
	private EditText pid;
	//商品名称显示框
	private EditText name;
	//商品描述显示框
	private EditText desc;
	//商品价格显示框
	private EditText price;
	//商品产地显示框
	private EditText area;
	//商品类别显示框
	private EditText type;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//加载activity的layout
		setContentView(R.layout.searchone);
		//实现查询按钮监听
		Button btnSearch = (Button) findViewById(R.id.searchone);
		btnSearch.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//获取画面商品编号控件
				pid = (EditText) findViewById(R.id.productid);
				//获取画面商品名称控件
				name = (EditText) findViewById(R.id.productname);
				//获取画面商品描述控件
				desc = (EditText) findViewById(R.id.description);
				//获取画面商品价格控件
				price = (EditText) findViewById(R.id.price);
				//获取画面商品产地控件
				area = (EditText) findViewById(R.id.productarea);
				//获取画面商品类型控件
				type = (EditText) findViewById(R.id.type);
				//获取用户输入商品编号
				String productid = pid.getText().toString();
				//商品编号未输入容错处理
				if("".equals(productid)){
					//显示message
					Toast.makeText(v.getContext(), "please enter id",
							Toast.LENGTH_LONG).show();
					return;
				}
				//商品编号数字容错处理
				for (int i = 0; i < productid.length(); i++) {
					if (!Character.isDigit(productid.charAt(i))) {
						//显示message
						Toast.makeText(v.getContext(), "only number permit",
								Toast.LENGTH_LONG).show();
						return;
					}
				}
				//http请求地址
				String url = "http://192.168.1.150:8080/AndroidHost/searchone";
				//http传输数据
				List<NameValuePair> datas = new ArrayList<NameValuePair>();
				//添加数据
				datas.add(new BasicNameValuePair("productid", productid));
				//发送http请求，获得服务器端返回数据
				InputStream xmlStream = HttpTool.sendDataByPost(url, datas);

				try {
					//获取SAX解析工厂
					SAXParserFactory factory = SAXParserFactory.newInstance();
					//获取SAX解析器
					SAXParser parser = factory.newSAXParser();
					//获取XML读取器对象
					XMLReader xmlreader = parser.getXMLReader();
					//获取商品专用处理器
					ProductHandler handler = new ProductHandler();
					//设置为商品信息专用的XML读取器
					xmlreader.setContentHandler(handler);
					//获取待解析的XML格式内容
					InputSource source = new InputSource(xmlStream);
					//开始解析XML
					xmlreader.parse(source);
					//获得解析后的商品列
					List<Product> products = handler.getProducts();
					//循环添加商品信息
					for (int i = 0; i < products.size(); i++) {
						Product product = (Product) products.get(i);
						//画面内容显示
						name.setText(product.getName());
						desc.setText(product.getDescription());
						price.setText(product.getPrice());
						area.setText(product.getArea());
						type.setText(product.getType());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		//实现返回按钮监听
		Button btnBack = (Button) findViewById(R.id.back01);
		btnBack.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//开始新的activity
				Intent intent = new Intent();
				intent.setClass(SearchOne.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		//实现注销按钮监听
		Button btnLogout = (Button) findViewById(R.id.logout);
		btnLogout.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//开始新的activity
				Intent intent = new Intent();
				intent.setClass(SearchOne.this, Login.class);
				startActivity(intent);
				finish();
			}
		});
	}
}
