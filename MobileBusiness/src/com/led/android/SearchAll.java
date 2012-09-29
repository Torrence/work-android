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
	// ��Ʒ�б�
	private ListView listview;
	// �б�ؼ�������
	private SimpleAdapter listItemAdapter;
	// �б�����
	private ArrayList<HashMap<String, Object>> listItem;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// �趨activity��layout
		setContentView(R.layout.searchall);
		// ��ʼ���б�����
		listItem = new ArrayList<HashMap<String, Object>>();
		// ��ʼ���б�������
		listItemAdapter = new SimpleAdapter(this, listItem, R.layout.item,
				new String[] { "title" }, new int[] { R.id.title });
		// ��ȡ�����ϵ���Ʒ�б�ؼ�
		listview = (ListView) findViewById(R.id.listProducts);
		// ���ÿؼ�������
		listview.setAdapter(listItemAdapter);
		// ʵ���б�ؼ��ĵ����¼�����
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent();
				intent.setClass(SearchAll.this, ViewOne.class);
				// ��ȡ�����
				HashMap<String, Object> map = (HashMap<String, Object>) listItem
						.get(arg2);
				// ��ȡ����Ʒ����
				Product product = (Product) map.get("product");
				// ��װ��bundle����intent������ݴ���
				Bundle bundle = new Bundle();
				bundle.putString("id", String.valueOf(product.getId()));
				bundle.putString("name", product.getName());
				bundle.putString("desc", product.getDescription());
				bundle.putString("price", product.getPrice());
				bundle.putString("area", product.getArea());
				bundle.putString("type", product.getType());
				// ��bundle����intent
				intent.putExtras(bundle);
				// ��ʼ��һ��activity
				startActivity(intent);
			}
		});
		// ʵ�ֲ�ѯ��ť����
		Button btnSearch = (Button) findViewById(R.id.searchall);
		btnSearch.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// ȡ�û�����������
				EditText key = (EditText) findViewById(R.id.keyword);
				// ȡ�����������
				String keyword = key.getText().toString();
				// http�����ַ
				String url = "http://192.168.1.150:8080/AndroidHost/searchall";
				// http��������
				List<NameValuePair> datas = new ArrayList<NameValuePair>();
				// �������
				datas.add(new BasicNameValuePair("keyword", keyword));
				// ����http���󣬻�÷������˷�������
				InputStream xmlStream = HttpTool.sendDataByPost(url, datas);

				try {
					// ��ȡSAX��������
					SAXParserFactory factory = SAXParserFactory.newInstance();
					// ��ȡSAX������
					SAXParser parser = factory.newSAXParser();
					// ��ȡXML��ȡ������
					XMLReader xmlreader = parser.getXMLReader();
					// ��ȡ��Ʒר�ô�����
					ProductHandler handler = new ProductHandler();
					// ����Ϊ��Ʒ��Ϣר�õ�XML��ȡ��
					xmlreader.setContentHandler(handler);
					// ��ȡ��������XML��ʽ����
					InputSource source = new InputSource(xmlStream);
					// ��ʼ����XML
					xmlreader.parse(source);
					// ��ý��������Ʒ��
					List<Product> products = handler.getProducts();
					// ����б�����
					listItem.clear();
					// ѭ�������Ʒ��Ϣ
					for (int i = 0; i < products.size(); i++) {
						Product product = (Product) products.get(i);
						HashMap<String, Object> map = new HashMap<String, Object>();
						// ��Ʒ��ʾ����
						map.put("title", String.valueOf(i + 1) + "."
								+ product.getName());
						// ��Ʒ��ϸ��Ϣ
						map.put("product", product);
						// ��ӵ��б�������
						listItem.add(map);
					}
					// ����б������б仯����������������UI�ؼ�
					listItemAdapter.notifyDataSetChanged();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		// ʵ�ַ��ذ�ť����
		Button btnBack = (Button) findViewById(R.id.back03);
		btnBack.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// ��ʼ�µ�activity
				Intent intent = new Intent();
				intent.setClass(SearchAll.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
}
