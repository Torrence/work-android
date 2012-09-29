package com.led.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class WeatherHandler extends DefaultHandler {

	//���ڴ�Ž���������������Ϣ
	Map<String, Object> result = new HashMap<String, Object>();

	//��ȡ������Ϣ
	public Map<String, Object> getWeather() {
		return result;
	}
	
	//��ʼ������
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
	}
	
	//��β�Ĵ�����
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}

	//��ʼ�ж�
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		if (localName.equals("condition")) {
			result.put("condition", attributes.getValue("data"));
		}
		if (localName.equals("temp_c")) {
			result.put("temp_c", attributes.getValue("data"));
		}
		if (localName.equals("humidity")) {
			result.put("humidity", attributes.getValue("data"));
		}
		if (localName.equals("wind_condition")) {
			result.put("wind_condition", attributes.getValue("data"));
		}
	}
	
	//��ȡ�ڵ���ַ�����
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
	}

	//������д�����ݽṹ
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
	}
}
