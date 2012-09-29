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

	// ��Ʒ�б�
	private ArrayList<HashMap<String, Object>> itemList;
	// �б�ؼ�������
	private SimpleAdapter listItemAdapter;
	// �������ݶ���
	private Bundle bundle;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ����layout
		setContentView(R.layout.itemlist);

		itemList = new ArrayList<HashMap<String, Object>>();
		// ��ʼ���б�������
		listItemAdapter = new SimpleAdapter(this, itemList, R.layout.item,
				new String[] { "title" }, new int[] { R.id.title });
		setListAdapter(listItemAdapter);

		// ��õ�ǰintent
		Intent intent = this.getIntent();
		// ͨ��intent��ô��ݹ�����bundle���ݶ���
		bundle = intent.getExtras();

		// http�����ַ
		String url = getResources().getString(R.string.baseUrl)
				+ "androidViewItems.action";
		// http��������
		List<NameValuePair> datas = new ArrayList<NameValuePair>();
		// �������
		datas.add(new BasicNameValuePair("kindId", bundle.getString("kindId")));
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
			ItemHandler handler = new ItemHandler();
			// ����Ϊ��Ʒ��Ϣר�õ�XML��ȡ��
			xmlreader.setContentHandler(handler);
			// ��ȡ��������XML��ʽ����
			InputSource source = new InputSource(xmlStream);
			// ��ʼ����XML
			xmlreader.parse(source);
			// ��ý��������Ʒ��
			List<Item> items = handler.getItems();

			if (items.isEmpty()) {
				Toast.makeText(this, "��������û��������Ʒ", Toast.LENGTH_LONG).show();
			}

			for (Item item : items) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				// ��Ʒ��ʾ����
				map.put("title", item.getId() + "." + item.getItemName());
				// ��Ʒ��ϸ��Ϣ
				map.put("item", item);
				// ��ӵ��б�������
				itemList.add(map);
			}
			// ����б������б仯����������������UI�ؼ�
			listItemAdapter.notifyDataSetChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ��ѡ���б���ʱ��ʾ��Ʒ��ϸ
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent();
		intent.setClass(this, ViewItem.class);
		// ��ȡ�����
		HashMap<String, Object> map = (HashMap<String, Object>) itemList
				.get(position);
		// ��ȡ����Ʒ��Ϣ
		Item item = (Item) map.get("item");
		// ��װ��bundle����intent������ݴ���
		bundle.putString("id", String.valueOf(item.getId()));
		// ��bundle����intent
		intent.putExtras(bundle);
		// ��ʼ��һ��activity
		startActivity(intent);
	}
}
