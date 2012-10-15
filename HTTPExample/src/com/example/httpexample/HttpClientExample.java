package com.example.httpexample;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientExample {
	String urlStr = "http://www.baidu.com";
	
	public String HttpGetMethod() {
		String result = "";
		try {
			HttpGet httpRequest = new HttpGet(urlStr);
//			httpRequest.setURI(new URI("http://www.baidu.com"));
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(httpResponse.getEntity());
			}
			else {
				result = "null";
			}
			return result;
		} catch (Exception e) {
			return null;
		}
	}
	
	public String HttpPostMethod(String key, String value) {
		String result = "";
		try {
			//HttpPost连接对象
			HttpPost httpRequest = new HttpPost(urlStr);
			//使用NameValuePair来保存要传递的Post参数
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			//添加要传递的参数
			params.add(new BasicNameValuePair(key, value));
			//设置字符集
			HttpEntity httpentity = new UrlEncodedFormEntity(params, "utf-8");
			//请求httpRequest
			httpRequest.setEntity(httpentity);
			//取得默认的HttpClient
			HttpClient httpclient = new DefaultHttpClient();
			//取得HttpResponse
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			//HttpStatus.SC_OK表示连接成功
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				//取得返回的字符串
				result = EntityUtils.toString(httpResponse.getEntity());
				return result;
			}
			else {
				return "null";
			}
		} catch (Exception e) {
			return null;
		}
	}
}
