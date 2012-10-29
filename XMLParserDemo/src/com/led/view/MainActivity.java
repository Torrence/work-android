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
				buf = new byte[1024];// 重新生成，避免和上次读取的数据重复
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
						.setTitle("实时天气（DOM）")
						.setItems(
								new String[] {
										weather.get("condition").toString(),
										weather.get("temp_c").toString(),
										weather.get("humidity").toString(),
										weather.get("wind_condition")
												.toString() }, null)
						.setNegativeButton("确定", null).show();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.button2:
			try {
				// 获取SAX解析工厂
				SAXParserFactory factory = SAXParserFactory.newInstance();
				// 获取SAX解析器
				SAXParser parser = factory.newSAXParser();
				// 获取XML读取器对象
				XMLReader xmlreader = parser.getXMLReader();
				// 获取天气专用处理器
				WeatherHandler handler = new WeatherHandler();
				// 设置为天气信息专用的XML读取器
				xmlreader.setContentHandler(handler);

				is = this.getClassLoader().getResourceAsStream("weather.xml");
				// 获取待解析的XML格式内容
				InputSource source = new InputSource(is);
				// 开始解析XML
				xmlreader.parse(source);
				// 获得解析后的商品列
				Map<String, Object> weather = handler.getWeather();
				new AlertDialog.Builder(this)
						.setTitle("实时天气（SAX）")
						.setItems(
								new String[] {
										weather.get("condition").toString(),
										weather.get("temp_c").toString(),
										weather.get("humidity").toString(),
										weather.get("wind_condition")
												.toString() }, null)
						.setNegativeButton("确定", null).show();
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