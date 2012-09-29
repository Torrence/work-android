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

	// Ʒ���б�
	private List<String> kindTitles;
	// �б�ؼ�������
	private ArrayAdapter<String> stringItemAdapter;
	// �������ݶ���
	private Bundle bundle;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ����layout
		setContentView(R.layout.kindlist);

		// ��õ�ǰintent
		Intent intent = this.getIntent();
		// ͨ��intent��ô��ݹ�����bundle���ݶ���
		bundle = intent.getExtras();

		kindTitles = new ArrayList<String>();
		stringItemAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, kindTitles);
		setListAdapter(stringItemAdapter);

		// http�����ַ
		String url = getResources().getString(R.string.baseUrl)
				+ "androidViewKinds.action";
		// http��������
		List<NameValuePair> datas = new ArrayList<NameValuePair>();
		// ����http���󣬻�÷������˷�������
		InputStream xmlStream = HttpTool.sendDataByPost(url, datas);
		try {
			// ��ȡSAX��������
			SAXParserFactory factory = SAXParserFactory.newInstance();
			// ��ȡSAX������
			SAXParser parser = factory.newSAXParser();
			// ��ȡXML��ȡ������
			XMLReader xmlreader = parser.getXMLReader();
			// ��ȡƷ��ר�ô�����
			KindHandler handler = new KindHandler();
			// ����ΪƷ����Ϣר�õ�XML��ȡ��
			xmlreader.setContentHandler(handler);
			// ��ȡ��������XML��ʽ����
			InputSource source = new InputSource(xmlStream);
			// ��ʼ����XML
			xmlreader.parse(source);
			// ��ý��������Ʒ��
			List<Kind> kinds = handler.getKinds();

			for (Kind k : kinds) {
				kindTitles.add(k.getId() + "." + k.getKindName());
			}
			// ����б������б仯����������������UI�ؼ�
			stringItemAdapter.notifyDataSetChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ��ѡ���б���ʱ��ʾ���Ĳ�Ʒ
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent();
		intent.setClass(this, ItemList.class);
		// ��ȡ���Ʒ��
		String kindTitle = (String) kindTitles.get(position);
		String kindId = kindTitle.substring(0, kindTitle.indexOf("."));
		// ��װ��bundle����intent������ݴ���
		bundle.putString("kindId", kindId);
		// ��bundle����intent
		intent.putExtras(bundle);
		// ��ʼ��һ��activity
		startActivity(intent);
	}
}
