package com.led.tool;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import android.util.Xml;

public class DomParser {

	public static Map<String, Object> ReadXmlByPull(InputStream inputStream)
			throws Exception {
		InputStream temp = inputStream;
		Map<String, Object> result = new HashMap<String, Object>();
		// android给我们提供了xml 用来得到xmlpull解析器
		XmlPullParser xmlpull = Xml.newPullParser();
		// 将输入流传入 设定编码方式
		xmlpull.setInput(temp, "utf-8");
		// pull读到xml后 返回数字 读取到xml的声明返回数字0 START_DOCUMENT; 读取到xml的结束返回数字1
		// END_DOCUMENT ; 读取到xml的开始标签返回数字2 START_TAG 读取到xml的结束标签返回数字3 END_TAG
		// 读取到xml的文本返回数字4 TEXT
		int eventCode = xmlpull.getEventType();
		// 只要这个事件返回的不是1 我们就一直读取xml文件
		while (eventCode != XmlPullParser.END_DOCUMENT) {
			switch (eventCode) {

			case XmlPullParser.START_DOCUMENT: {
				// 开始文档
				break;
			}

			case XmlPullParser.START_TAG: {
				if ("condition".equals(xmlpull.getName())) {
					result.put("condition", xmlpull.getAttributeValue(0));
				}
				if ("temp_c".equals(xmlpull.getName())) {
					result.put("temp_c", xmlpull.getAttributeValue(0));
				}
				if ("humidity".equals(xmlpull.getName())) {
					result.put("humidity", xmlpull.getAttributeValue(0));
				}
				if ("wind_condition".equals(xmlpull.getName())) {
					result.put("wind_condition", xmlpull.getAttributeValue(0));
				}
				break;
			}

			case XmlPullParser.END_TAG: {
				// 结束标签
				break;
			}
			}

			// 没有结束xml文件就推到下个进行解析
			eventCode = xmlpull.next();
		}
		return result;
	}
}