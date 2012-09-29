package com.led.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class WeatherHandler extends DefaultHandler {

	//用于存放解析出来的天气信息
	Map<String, Object> result = new HashMap<String, Object>();

	//获取天气信息
	public Map<String, Object> getWeather() {
		return result;
	}
	
	//初始化工作
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
	}
	
	//收尾的处理工作
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}

	//初始判断
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
	
	//获取节点的字符数据
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
	}

	//将数据写入数据结构
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
	}
}
