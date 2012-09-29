package com.led.view;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import com.led.tool.DomParser;
import com.led.tool.WeatherHandler;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {

	private EditText edittext;
	private Button btnDom;
	private Button btnSax;
	private InputStream is;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		edittext = (EditText) findViewById(R.id.editText1);
		btnDom = (Button) findViewById(R.id.button1);
		btnSax = (Button) findViewById(R.id.button2);

		btnDom.setOnClickListener(this);
		btnSax.setOnClickListener(this);

		is = this.getClassLoader().getResourceAsStream("weather.xml");
		byte[] buf = new byte[1024];
		StringBuffer sb = new StringBuffer();
		try {
			while ((is.read(buf)) != -1) {
				sb.append(new String(buf));
				buf = new byte[1024];// �������ɣ�������ϴζ�ȡ�������ظ�
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		edittext.setText(sb.toString());

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:
			try {
				is = this.getClassLoader().getResourceAsStream("weather.xml");
				Map<String, Object> weather = DomParser.ReadXmlByPull(is);
				new AlertDialog.Builder(this)
						.setTitle("ʵʱ������DOM��")
						.setItems(
								new String[] {
										weather.get("condition").toString(),
										weather.get("temp_c").toString(),
										weather.get("humidity").toString(),
										weather.get("wind_condition")
												.toString() }, null)
						.setNegativeButton("ȷ��", null).show();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.button2:
			try {
				// ��ȡSAX��������
				SAXParserFactory factory = SAXParserFactory.newInstance();
				// ��ȡSAX������
				SAXParser parser = factory.newSAXParser();
				// ��ȡXML��ȡ������
				XMLReader xmlreader = parser.getXMLReader();
				// ��ȡ����ר�ô�����
				WeatherHandler handler = new WeatherHandler();
				// ����Ϊ������Ϣר�õ�XML��ȡ��
				xmlreader.setContentHandler(handler);

				is = this.getClassLoader().getResourceAsStream("weather.xml");
				// ��ȡ��������XML��ʽ����
				InputSource source = new InputSource(is);
				// ��ʼ����XML
				xmlreader.parse(source);
				// ��ý��������Ʒ��
				Map<String, Object> weather = handler.getWeather();
				new AlertDialog.Builder(this)
						.setTitle("ʵʱ������SAX��")
						.setItems(
								new String[] {
										weather.get("condition").toString(),
										weather.get("temp_c").toString(),
										weather.get("humidity").toString(),
										weather.get("wind_condition")
												.toString() }, null)
						.setNegativeButton("ȷ��", null).show();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}

}