package com.example.httpexample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class HttpURLConnectionExample {
	public HttpURLConnection urlconn = null;
	private String urlStr = "";
	
	private void Init() throws IOException {
    	if (urlStr == "") {
    		urlStr = "http://www.baidu.com";
    	}
    	URL url = new URL(urlStr);
    	//打开一个URL所指向的Connection对象
    	if (urlconn == null) {
    		urlconn = (HttpURLConnection) url.openConnection();
    	}
    }
    
    private String StreamDeal(InputStream is) {
    	String result = "";
    	InputStreamReader in = new InputStreamReader(is);
    	BufferedReader buffer = new BufferedReader(in);
    	String inputLine = null;
    	try {
			while (((inputLine = buffer.readLine()) != null)) {
				result += inputLine + "\n";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	return result;
    }
	
	public String HttpGetMethod() throws IOException {
    	if (urlconn == null) {
    		Init();
    	}
    	String result = StreamDeal(urlconn.getInputStream());
    	urlconn.disconnect();
    	return result;
    }
    
    public String HttpPostMethod(String key, String value) throws IOException {
    	if (urlconn == null) {
    		Init();
    	}
    	//设置该URLConnection可读
    	urlconn.setDoInput(true);
    	//设置该URLConnection可写
    	urlconn.setDoOutput(true);
    	//使用POST方式来提交数据
    	urlconn.setRequestMethod("POST");
    	//不运行缓存
    	urlconn.setUseCaches(false);
    	urlconn.connect();
    	//使用POST方式时，我们需要自己构造部分Http请求的内容，因此我们需要使用OutputStream来进行数据写和操作
    	OutputStreamWriter writer = new OutputStreamWriter(urlconn.getOutputStream());
    	String urlQueryStr = key + "=" + URLEncoder.encode(value, "utf-8");
    	writer.write(urlQueryStr);
    	writer.flush();
    	writer.close();
    	//获取返回的内容
    	String result = StreamDeal(urlconn.getInputStream());
    	urlconn.disconnect();
    	return result;
    }
}
