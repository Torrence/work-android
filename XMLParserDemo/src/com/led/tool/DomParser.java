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
		// android�������ṩ��xml �����õ�xmlpull������
		XmlPullParser xmlpull = Xml.newPullParser();
		// ������������ �趨���뷽ʽ
		xmlpull.setInput(temp, "utf-8");
		// pull����xml�� �������� ��ȡ��xml��������������0 START_DOCUMENT; ��ȡ��xml�Ľ�����������1
		// END_DOCUMENT ; ��ȡ��xml�Ŀ�ʼ��ǩ��������2 START_TAG ��ȡ��xml�Ľ�����ǩ��������3 END_TAG
		// ��ȡ��xml���ı���������4 TEXT
		int eventCode = xmlpull.getEventType();
		// ֻҪ����¼����صĲ���1 ���Ǿ�һֱ��ȡxml�ļ�
		while (eventCode != XmlPullParser.END_DOCUMENT) {
			switch (eventCode) {

			case XmlPullParser.START_DOCUMENT: {
				// ��ʼ�ĵ�
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
				// ������ǩ
				break;
			}
			}

			// û�н���xml�ļ����Ƶ��¸����н���
			eventCode = xmlpull.next();
		}
		return result;
	}
}
