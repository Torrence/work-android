package com.led.auction;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.http.NameValuePair;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import com.led.model.Kind;
import com.led.tools.HttpTool;
import com.led.tools.KindHandler;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class KindList extends ListActivity {

	// 品种列表
	private List<String> kindTitles;
	// 列表控件适配器
	private ArrayAdapter<String> stringItemAdapter;
	// 传递数据对象
	private Bundle bundle;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置layout
		setContentView(R.layout.kindlist);

		// 获得当前intent
		Intent intent = this.getIntent();
		// 通过intent获得传递过来的bundle数据对象
		bundle = intent.getExtras();

		kindTitles = new ArrayList<String>();
		stringItemAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, kindTitles);
		setListAdapter(stringItemAdapter);

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
				kindTitles.add(k.getId() + "." + k.getKindName());
			}
			// 如果列表内容有变化则提醒适配器更新UI控件
			stringItemAdapter.notifyDataSetChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 当选择列表项时显示竞拍产品
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent();
		intent.setClass(this, ItemList.class);
		// 获取点击品种
		String kindTitle = (String) kindTitles.get(position);
		String kindId = kindTitle.substring(0, kindTitle.indexOf("."));
		// 封装进bundle用于intent间的数据传输
		bundle.putString("kindId", kindId);
		// 将bundle存入intent
		intent.putExtras(bundle);
		// 开始另一个activity
		startActivity(intent);
	}
}
