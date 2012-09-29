package com.led.tools;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import com.led.model.Product;

public class ProductHandler extends DefaultHandler {

	//用于存放解析出来的商品信息
	private List<Product> products;

	//获取商品列
	public List<Product> getProducts() {
		return products;
	}
	
	//初始化工作
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
		products = new ArrayList<Product>();
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
