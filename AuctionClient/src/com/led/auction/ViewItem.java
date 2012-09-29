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

import com.led.model.Item;
import com.led.tools.HttpTool;
import com.led.tools.ItemHandler;
import com.led.tools.ValidateTool;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ViewItem extends ListActivity {

	// 品种列表
	private List<String> item;
	// 列表控件适配器
	private ArrayAdapter<String> stringItemAdapter;
	// 竞拍价控件
	private EditText edtBidPrice;
	// 竞拍按钮
	private Button btnSubmit;
	// 竞拍物品ID
	private String itemId;
	// 用户ID
	private String userId;
	// 竞拍价
	private String bidPrice;
	// 最高竞拍价
	private double maxPrice;
	// 传递数据对象
	private Bundle bundle;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置layout
		setContentView(R.layout.viewitem);

		item = new ArrayList<String>();
		stringItemAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, item);
		setListAdapter(stringItemAdapter);

		// 获得竞拍价控件
		edtBidPrice = (EditText) findViewById(R.id.bidPrice);

		// 获得当前intent
		Intent intent = this.getIntent();
		// 通过intent获得传递过来的bundle数据对象
		bundle = intent.getExtras();
		// 获得物品详细信息并添加到列表内容中
		userId = bundle.getString("userId");
		itemId = bundle.getString("id");

		// 更新列表内容
		updateList();

		// 实现提交按钮监听
		btnSubmit = (Button) findViewById(R.id.submit);
		btnSubmit.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 取得用户竞拍价
				bidPrice = edtBidPrice.getText().toString().trim();
				// 竞拍价为空容错
				if ("".equals(bidPrice)) {
					Toast
							.makeText(v.getContext(), "竞拍价不能为空",
									Toast.LENGTH_LONG).show();
					return;
				}
				// 竞拍价数字容错处理
				if (!ValidateTool.isNumber(bidPrice)) {
					Toast
							.makeText(v.getContext(), "竞拍价格式错误",
									Toast.LENGTH_LONG).show();
					return;
				}
				// 竞拍价未高于最高价容错
				if ((Double.parseDouble(bidPrice)) <= maxPrice) {
					Toast.makeText(v.getContext(), "竞拍价必须高于最高价",
							Toast.LENGTH_LONG).show();
					return;
				}
				// http请求地址
				String url = getResources().getString(R.string.baseUrl)
						+ "androidBid.action";
				// http传输数据
				List<NameValuePair> datas = new ArrayList<NameValuePair>();
				// 添加数据
				datas.add(new BasicNameValuePair("userid", userId));
				datas.add(new BasicNameValuePair("itemId", itemId));
				datas.add(new BasicNameValuePair("bidPrice", bidPrice));
				// 发送http请求，获得服务器端返回数据
				InputStream is = HttpTool.sendDataByPost(url, datas);
				String result = HttpTool.convertStreamToString(is);
				if ("success".equals(result)) {
					Toast.makeText(v.getContext(), "竞价成功", Toast.LENGTH_LONG)
							.show();
				} else {
					Toast.makeText(v.getContext(), "竞价失败", Toast.LENGTH_LONG)
							.show();
				}

				// 更新列表内容
				updateList();
			}
		});
	}

	public void updateList() {
		// http请求地址
		String url = getResources().getString(R.string.baseUrl)
				+ "androidViewOne.action";
		// http传输数据
		List<NameValuePair> datas = new ArrayList<NameValuePair>();
		// 添加数据
		datas.add(new BasicNameValuePair("itemId", itemId));
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

			// 清空列表
			item.clear();
			for (Item i : items) {
				item.add("物品名:" + i.getItemName());
				item.add("物品备注:" + i.getItemDesc());
				item.add("物品描述:" + i.getItemDesc());
				item.add("物品种类:" + i.getKindName());
				item.add("物品所有者:" + i.getOwnerName());
				item.add("物品起拍价:" + i.getInitPrice());
				maxPrice = i.getMaxPrice();
				item.add("物品最高价:" + maxPrice);
				item.add("物品起拍时间:" + i.getAddtime());
				item.add("物品结束时间:" + i.getEndtime());
			}
			// 如果列表内容有变化则提醒适配器更新UI控件
			stringItemAdapter.notifyDataSetChanged();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
