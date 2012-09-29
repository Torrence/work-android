package com.led.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import com.led.model.Item;

public class ItemHandler extends DefaultHandler {

	// 用于存放解析出来的品种信息
	private List<Item> items;

	// 获取品种列
	public List<Item> getItems() {
		return items;
	}

	// 初始化工作
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
		items = new ArrayList<Item>();
	}

	// 收尾的处理工作
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}

	// 初始判断
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		if (localName.equals("item")) {
			Item item = new Item();
			item.setId(Integer.parseInt(attributes.getValue("id")));
			item.setItemName(attributes.getValue("itemName"));
			item.setItemRemark(attributes.getValue("itemRemark"));
			item.setItemDesc(attributes.getValue("itemDesc"));
			item.setKindName(attributes.getValue("kindName"));
			item.setOwnerName(attributes.getValue("ownerName"));
			if (attributes.getValue("initPrice") != null) {
				item.setInitPrice(Double.parseDouble(attributes
						.getValue("initPrice")));
			}
			if (attributes.getValue("maxPrice") != null) {
				item.setMaxPrice(Double.parseDouble(attributes
						.getValue("maxPrice")));
			}
			if (attributes.getValue("addtime") != null
					&& attributes.getValue("endtime") != null) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				try {
					item.setAddtime(formatter.parse(attributes
							.getValue("addtime")));
					item.setEndtime(formatter.parse(attributes
							.getValue("endtime")));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			items.add(item);
		}
	}

	// 获取节点的字符数据
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
	}

	// 将数据写入数据结构
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
	}

}
