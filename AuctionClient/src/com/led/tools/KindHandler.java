package com.led.tools;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import com.led.model.Kind;

public class KindHandler extends DefaultHandler {

	//用于存放解析出来的品种信息
	private List<Kind> kinds;

	//获取品种列
	public List<Kind> getKinds() {
		return kinds;
	}
	
	//初始化工作
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
		kinds = new ArrayList<Kind>();
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
		if (localName.equals("kind")) {
			Kind kind = new Kind();
			kind.setId(Integer.parseInt(attributes.getValue("id")));
			kind.setKindName(attributes.getValue("kindName"));
			kind.setKindDesc(attributes.getValue("kindDesc"));
			kinds.add(kind);
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
