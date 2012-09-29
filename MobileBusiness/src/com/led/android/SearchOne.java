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
	//��Ʒ�����ʾ��
	private EditText pid;
	//��Ʒ������ʾ��
	private EditText name;
	//��Ʒ������ʾ��
	private EditText desc;
	//��Ʒ�۸���ʾ��
	private EditText price;
	//��Ʒ������ʾ��
	private EditText area;
	//��Ʒ�����ʾ��
	private EditText type;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//����activity��layout
		setContentView(R.layout.searchone);
		//ʵ�ֲ�ѯ��ť����
		Button btnSearch = (Button) findViewById(R.id.searchone);
		btnSearch.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//��ȡ������Ʒ��ſؼ�
				pid = (EditText) findViewById(R.id.productid);
				//��ȡ������Ʒ���ƿؼ�
				name = (EditText) findViewById(R.id.productname);
				//��ȡ������Ʒ�����ؼ�
				desc = (EditText) findViewById(R.id.description);
				//��ȡ������Ʒ�۸�ؼ�
				price = (EditText) findViewById(R.id.price);
				//��ȡ������Ʒ���ؿؼ�
				area = (EditText) findViewById(R.id.productarea);
				//��ȡ������Ʒ���Ϳؼ�
				type = (EditText) findViewById(R.id.type);
				//��ȡ�û�������Ʒ���
				String productid = pid.getText().toString();
				//��Ʒ���δ�����ݴ���
				if("".equals(productid)){
					//��ʾmessage
					Toast.makeText(v.getContext(), "please enter id",
							Toast.LENGTH_LONG).show();
					return;
				}
				//��Ʒ��������ݴ���
				for (int i = 0; i < productid.length(); i++) {
					if (!Character.isDigit(productid.charAt(i))) {
						//��ʾmessage
						Toast.makeText(v.getContext(), "only number permit",
								Toast.LENGTH_LONG).show();
						return;
					}
				}
				//http�����ַ
				String url = "http://192.168.1.150:8080/AndroidHost/searchone";
				//http��������
				List<NameValuePair> datas = new ArrayList<NameValuePair>();
				//�������
				datas.add(new BasicNameValuePair("productid", productid));
				//����http���󣬻�÷������˷�������
				InputStream xmlStream = HttpTool.sendDataByPost(url, datas);

				try {
					//��ȡSAX��������
					SAXParserFactory factory = SAXParserFactory.newInstance();
					//��ȡSAX������
					SAXParser parser = factory.newSAXParser();
					//��ȡXML��ȡ������
					XMLReader xmlreader = parser.getXMLReader();
					//��ȡ��Ʒר�ô�����
					ProductHandler handler = new ProductHandler();
					//����Ϊ��Ʒ��Ϣר�õ�XML��ȡ��
					xmlreader.setContentHandler(handler);
					//��ȡ��������XML��ʽ����
					InputSource source = new InputSource(xmlStream);
					//��ʼ����XML
					xmlreader.parse(source);
					//��ý��������Ʒ��
					List<Product> products = handler.getProducts();
					//ѭ�������Ʒ��Ϣ
					for (int i = 0; i < products.size(); i++) {
						Product product = (Product) products.get(i);
						//����������ʾ
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
		//ʵ�ַ��ذ�ť����
		Button btnBack = (Button) findViewById(R.id.back01);
		btnBack.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//��ʼ�µ�activity
				Intent intent = new Intent();
				intent.setClass(SearchOne.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		//ʵ��ע����ť����
		Button btnLogout = (Button) findViewById(R.id.logout);
		btnLogout.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//��ʼ�µ�activity
				Intent intent = new Intent();
				intent.setClass(SearchOne.this, Login.class);
				startActivity(intent);
				finish();
			}
		});
	}
}
