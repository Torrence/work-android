package com.led.tools;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import com.led.model.Kind;

public class KindHandler extends DefaultHandler {

	//���ڴ�Ž���������Ʒ����Ϣ
	private List<Kind> kinds;

	//��ȡƷ����
	public List<Kind> getKinds() {
		return kinds;
	}
	
	//��ʼ������
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
		kinds = new ArrayList<Kind>();
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
		if (localName.equals("kind")) {
			Kind kind = new Kind();
			kind.setId(Integer.parseInt(attributes.getValue("id")));
			kind.setKindName(attributes.getValue("kindName"));
			kind.setKindDesc(attributes.getValue("kindDesc"));
			kinds.add(kind);
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
