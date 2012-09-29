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

	private String[] items = { "һ��", "����", "����", "����", "����", "һ������", "һ����",
			"һ��" };
	private Spinner timeSpinner;
	private Spinner kindSpinner;
	private ArrayAdapter<String> adapter;
	// Ʒ���б�
	private List<String> kindTitles;
	private EditText edtItemName;
	private EditText edtItemDesc;
	private EditText edtItemRemark;
	private EditText edtItemPrice;
	// �������ݶ���
	private Bundle bundle;
	// �û�ID
	private String userId;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ����layout
		setContentView(R.layout.additem);

		// ��þ��ļۿؼ�
		edtItemName = (EditText) findViewById(R.id.itemName);
		edtItemDesc = (EditText) findViewById(R.id.itemDesc);
		edtItemRemark = (EditText) findViewById(R.id.itemRemark);
		edtItemPrice = (EditText) findViewById(R.id.itemPrice);

		// ��õ�ǰintent
		Intent intent = this.getIntent();
		// ͨ��intent��ô��ݹ�����bundle���ݶ���
		bundle = intent.getExtras();
		userId = bundle.getString("userId");

		// ��ʼ��Ʒ�ֱ���
		kindTitles = new ArrayList<String>();
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
				// ȡ��Ʒ��
				kindTitles.add(k.getId() + "." + k.getKindName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		timeSpinner = (Spinner) findViewById(R.id.spinnerTime);
		// ����ѡ������ArrayAdapter��������
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, items);
		// ���������б�ķ��
		adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
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
		// ����ѡ������ArrayAdapter��������
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, kindTitles);
		// ���������б�ķ��
		adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		kindSpinner.setAdapter(adapter);
		// ����¼�Spinner�¼�����
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

		// ʵ�־�����Ʒ��ť����
		Button btnAddItem = (Button) findViewById(R.id.addItem);
		btnAddItem.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// ȡ����Ʒ��
				String name = edtItemName.getText().toString().trim();
				// ȡ����Ʒ����
				String desc = edtItemDesc.getText().toString().trim();
				// ȡ����Ʒ��ע
				String remark = edtItemRemark.getText().toString().trim();
				// ȡ�����ļ۸�
				String price = edtItemPrice.getText().toString().trim();
				// �ǿ��ݴ�
				if ("".equals(name) || "".equals(desc) || "".equals(remark)
						|| "".equals(price)) {
					Toast.makeText(v.getContext(), "�뽫��Ϣ��д����",
							Toast.LENGTH_LONG).show();
					return;
				}
				// �۸������ݴ���
				if (!ValidateTool.isNumber(price)) {
					Toast
							.makeText(v.getContext(), "���ļ۸�ʽ����",
									Toast.LENGTH_LONG).show();
					return;
				}
				// ȡ����Чʱ��
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
				// ȡ����Ʒ����
				String kind = (String) kindSpinner.getSelectedItem();
				String kindId = kind.substring(0, kind.indexOf("."));

				// http�����ַ
				String url = getResources().getString(R.string.baseUrl)
						+ "androidAddItem.action";
				// http��������
				List<NameValuePair> datas = new ArrayList<NameValuePair>();
				// �������
				datas.add(new BasicNameValuePair("itemName", name));
				datas.add(new BasicNameValuePair("itemDesc", desc));
				datas.add(new BasicNameValuePair("itemRemark", remark));
				datas.add(new BasicNameValuePair("itemPrice", price));
				datas.add(new BasicNameValuePair("itemTime", time));
				datas.add(new BasicNameValuePair("itemKind", kindId));
				datas.add(new BasicNameValuePair("userid", userId));
				// ����http���󣬻�÷������˷��� ����
				InputStream is = HttpTool.sendDataByPost(url, datas);
				String result = HttpTool.convertStreamToString(is);
				if ("success".equals(result)) {
					Toast.makeText(v.getContext(), "���ĳɹ�", Toast.LENGTH_LONG)
							.show();
				}else{
					Toast.makeText(v.getContext(), "����ʧ��", Toast.LENGTH_LONG)
					.show();
				}
			}
		});
	}
}
