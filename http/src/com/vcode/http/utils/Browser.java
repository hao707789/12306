package com.vcode.http.utils;

import com.vcode.http.client.VHttpClient;
import com.vcode.http.client.VHttpResponse;
import com.vcode.http.client.methods.VHttpMethods;
import com.vcode.http.impl.VHttpClientImpl;

/**
 * 浏览器类
 * @author Administrator
 *
 */
public class Browser {
	
	private static VHttpClient client = new VHttpClientImpl();
	
	private static Browser browser = null;
	
	private Browser(){}
	
	public static Browser getInstance(){
		if (browser==null) {
			return new Browser();
		}
		return browser;
	}

	public static VHttpResponse execute(VHttpMethods methods){
		return client.execute(methods);
	}
}
