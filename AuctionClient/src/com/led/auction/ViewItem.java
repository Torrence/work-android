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

	// Ʒ���б�
	private List<String> item;
	// �б�ؼ�������
	private ArrayAdapter<String> stringItemAdapter;
	// ���ļۿؼ�
	private EditText edtBidPrice;
	// ���İ�ť
	private Button btnSubmit;
	// ������ƷID
	private String itemId;
	// �û�ID
	private String userId;
	// ���ļ�
	private String bidPrice;
	// ��߾��ļ�
	private double maxPrice;
	// �������ݶ���
	private Bundle bundle;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ����layout
		setContentView(R.layout.viewitem);

		item = new ArrayList<String>();
		stringItemAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, item);
		setListAdapter(stringItemAdapter);

		// ��þ��ļۿؼ�
		edtBidPrice = (EditText) findViewById(R.id.bidPrice);

		// ��õ�ǰintent
		Intent intent = this.getIntent();
		// ͨ��intent��ô��ݹ�����bundle���ݶ���
		bundle = intent.getExtras();
		// �����Ʒ��ϸ��Ϣ����ӵ��б�������
		userId = bundle.getString("userId");
		itemId = bundle.getString("id");

		// �����б�����
		updateList();

		// ʵ���ύ��ť����
		btnSubmit = (Button) findViewById(R.id.submit);
		btnSubmit.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// ȡ���û����ļ�
				bidPrice = edtBidPrice.getText().toString().trim();
				// ���ļ�Ϊ���ݴ�
				if ("".equals(bidPrice)) {
					Toast
							.makeText(v.getContext(), "���ļ۲���Ϊ��",
									Toast.LENGTH_LONG).show();
					return;
				}
				// ���ļ������ݴ���
				if (!ValidateTool.isNumber(bidPrice)) {
					Toast
							.makeText(v.getContext(), "���ļ۸�ʽ����",
									Toast.LENGTH_LONG).show();
					return;
				}
				// ���ļ�δ������߼��ݴ�
				if ((Double.parseDouble(bidPrice)) <= maxPrice) {
					Toast.makeText(v.getContext(), "���ļ۱��������߼�",
							Toast.LENGTH_LONG).show();
					return;
				}
				// http�����ַ
				String url = getResources().getString(R.string.baseUrl)
						+ "androidBid.action";
				// http��������
				List<NameValuePair> datas = new ArrayList<NameValuePair>();
				// �������
				datas.add(new BasicNameValuePair("userid", userId));
				datas.add(new BasicNameValuePair("itemId", itemId));
				datas.add(new BasicNameValuePair("bidPrice", bidPrice));
				// ����http���󣬻�÷������˷�������
				InputStream is = HttpTool.sendDataByPost(url, datas);
				String result = HttpTool.convertStreamToString(is);
				if ("success".equals(result)) {
					Toast.makeText(v.getContext(), "���۳ɹ�", Toast.LENGTH_LONG)
							.show();
				} else {
					Toast.makeText(v.getContext(), "����ʧ��", Toast.LENGTH_LONG)
							.show();
				}

				// �����б�����
				updateList();
			}
		});
	}

	public void updateList() {
		// http�����ַ
		String url = getResources().getString(R.string.baseUrl)
				+ "androidViewOne.action";
		// http��������
		List<NameValuePair> datas = new ArrayList<NameValuePair>();
		// �������
		datas.add(new BasicNameValuePair("itemId", itemId));
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

			// ����б�
			item.clear();
			for (Item i : items) {
				item.add("��Ʒ��:" + i.getItemName());
				item.add("��Ʒ��ע:" + i.getItemDesc());
				item.add("��Ʒ����:" + i.getItemDesc());
				item.add("��Ʒ����:" + i.getKindName());
				item.add("��Ʒ������:" + i.getOwnerName());
				item.add("��Ʒ���ļ�:" + i.getInitPrice());
				maxPrice = i.getMaxPrice();
				item.add("��Ʒ��߼�:" + maxPrice);
				item.add("��Ʒ����ʱ��:" + i.getAddtime());
				item.add("��Ʒ����ʱ��:" + i.getEndtime());
			}
			// ����б������б仯����������������UI�ؼ�
			stringItemAdapter.notifyDataSetChanged();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
