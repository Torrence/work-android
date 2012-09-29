package com.led.tools;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import com.led.model.Product;

public class ProductHandler extends DefaultHandler {

	//���ڴ�Ž�����������Ʒ��Ϣ
	private List<Product> products;

	//��ȡ��Ʒ��
	public List<Product> getProducts() {
		return products;
	}
	
	//��ʼ������
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
		products = new ArrayList<Product>();
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
		if (localName.equals("product")) {
			Product product = new Product();
			String sid = attributes.getValue("id");
			product.setId(Integer.parseInt(sid));
			product.setName(attributes.getValue("name"));
			product.setDescription(attributes.getValue("description"));
			product.setPrice(attributes.getValue("price"));
			product.setArea(attributes.getValue("area"));
			product.setType(attributes.getValue("type"));
			products.add(product);
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
