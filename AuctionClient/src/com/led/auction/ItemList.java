package com.led.auction;

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
import com.led.model.Item;
import com.led.tools.HttpTool;
import com.led.tools.ItemHandler;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ItemList extends ListActivity {

	// 物品列表
	private ArrayList<HashMap<String, Object>> itemList;
	// 列表控件适配器
	private SimpleAdapter listItemAdapter;
	// 传递数据对象
	private Bundle bundle;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置layout
		setContentView(R.layout.itemlist);

		itemList = new ArrayList<HashMap<String, Object>>();
		// 初始化列表适配器
		listItemAdapter = new SimpleAdapter(this, itemList, R.layout.item,
				new String[] { "title" }, new int[] { R.id.title });
		setListAdapter(listItemAdapter);

		// 获得当前intent
		Intent intent = this.getIntent();
		// 通过intent获得传递过来的bundle数据对象
		bundle = intent.getExtras();

		// http请求地址
		String url = getResources().getString(R.string.baseUrl)
				+ "androidViewItems.action";
		// http传输数据
		List<NameValuePair> datas = new ArrayList<NameValuePair>();
		// 添加数据
		datas.add(new BasicNameValuePair("kindId", bundle.getString("kindId")));
		// 发送http请求，获得服务器端返回数据
		InputStream xmlStream = HttpTool.sendDataByPost(url, datas);
		try {
			// 获取SAX解析工厂
			SAXParserFactory factory = SAXParserFactory.newInstance();
			// 获取SAX解析器
			SAXParser parser = factory.newSAXParser();
			// 获取XML读取器对象
			XMLReader xmlreader = parser.getXMLReader();
			// 获取物品专用处理器
			ItemHandler handler = new ItemHandler();
			// 设置为物品信息专用的XML读取器
			xmlreader.setContentHandler(handler);
			// 获取待解析的XML格式内容
			InputSource source = new InputSource(xmlStream);
			// 开始解析XML
			xmlreader.parse(source);
			// 获得解析后的商品列
			List<Item> items = handler.getItems();

			if (items.isEmpty()) {
				Toast.makeText(this, "该种类下没有拍卖物品", Toast.LENGTH_LONG).show();
			}

			for (Item item : items) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				// 物品显示标题
				map.put("title", item.getId() + "." + item.getItemName());
				// 商品详细信息
				map.put("item", item);
				// 添加到列表内容中
				itemList.add(map);
			}
			// 如果列表内容有变化则提醒适配器更新UI控件
			listItemAdapter.notifyDataSetChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 当选择列表项时显示物品明细
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent();
		intent.setClass(this, ViewItem.class);
		// 获取点击项
		HashMap<String, Object> map = (HashMap<String, Object>) itemList
				.get(position);
		// 获取该物品信息
		Item item = (Item) map.get("item");
		// 封装进bundle用于intent间的数据传输
		bundle.putString("id", String.valueOf(item.getId()));
		// 将bundle存入intent
		intent.putExtras(bundle);
		// 开始另一个activity
		startActivity(intent);
	}
}
