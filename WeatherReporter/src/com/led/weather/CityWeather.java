package com.led.weather;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class CityWeather extends Activity {
	private static final int NO_NETWORK = 0;
	
	Handler handler = null;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		handler = new Handler() {
			public void handleMessage (Message msg) {
				switch (msg.what) {
				case NO_NETWORK:
					Toast.makeText(getApplicationContext(), "���粻���ã�ʹ����������", Toast.LENGTH_LONG).show();
					break;
				}
			}
		};
		
		init();
	}

	private void init() {
		Spinner city_spr = (Spinner) findViewById(R.id.Spinner01);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, ConstData.city);
		adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city_spr.setAdapter(adapter);

		Button submit = (Button) findViewById(R.id.Button01);
		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Spinner spr = (Spinner) findViewById(R.id.Spinner01);
				Long l = spr.getSelectedItemId();
				int index = l.intValue();
				String cityParamString = ConstData.cityCode[index];

				try {
					URL url = new URL(ConstData.queryString + cityParamString);
					getCityWeather(url);
				} catch (Exception e) {
					Log.e("CityWeather", e.toString());
				}

			}
		});

	}

	// ������ʾʵʱ������Ϣ
	private void updateWeatherInfoView(int aResourceID,
			WeatherCurrentCondition aWCC) throws MalformedURLException {

		URL imgURL = new URL("http://www.google.com/" + aWCC.getIcon());
		((SingleWeatherInfoView) findViewById(aResourceID))
				.setWeatherIcon(imgURL);
		((SingleWeatherInfoView) findViewById(aResourceID))
				.setWeatherString(aWCC.toString());
	}

	// ������ʾ����Ԥ��
	private void updateWeatherInfoView(int aResourceID,
			WeatherForecastCondition aWFC) throws MalformedURLException {

		URL imgURL = new URL("http://www.google.com/" + aWFC.getIcon());
		((SingleWeatherInfoView) findViewById(aResourceID))
				.setWeatherIcon(imgURL);
		((SingleWeatherInfoView) findViewById(aResourceID))
				.setWeatherString(aWFC.toString());
	}

	// ��ȡ������Ϣ
	// ͨ�������ȡ����
	// ���ݸ�XMLReader����
	public void getCityWeather(URL url) {
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();

			XMLReader xr = sp.getXMLReader();

			GoogleWeatherHandler gwh = new GoogleWeatherHandler();
			xr.setContentHandler(gwh);

			InputStreamReader isr = null;
			try {
				isr = new InputStreamReader(url.openStream(), "GBK");
			} catch (FileNotFoundException e) {
				handler.sendEmptyMessage(NO_NETWORK);
				InputStream in = getResources().getAssets().open("default-weather.xml");
				isr = new InputStreamReader(in, "utf-8");
			}
			
			
			InputSource is = new InputSource(isr);

			xr.parse(is);

			WeatherSet ws = gwh.getMyWeatherSet();

			updateWeatherInfoView(R.id.weather_0, ws.getMyCurrentCondition());
			updateWeatherInfoView(R.id.weather_1, ws.getMyForecastConditions()
					.get(0));
			updateWeatherInfoView(R.id.weather_2, ws.getMyForecastConditions()
					.get(1));
			updateWeatherInfoView(R.id.weather_3, ws.getMyForecastConditions()
					.get(2));
			updateWeatherInfoView(R.id.weather_4, ws.getMyForecastConditions()
					.get(3));
		} catch (Exception e) {
			Log.e("CityWeather", e.toString());
		}
	}
}
