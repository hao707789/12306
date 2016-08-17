package com.vcode.http.client.methods;

import com.vcode.http.Header.VHeader;
import com.vcode.http.client.parames.VParames;
import com.vcode.http.utils.Constant;

/**
 * 
 * @author 默非默
 *
 */
public class VHttpPost implements VHttpMethods{

	private final String type = Constant.HTTPPOST;

	private String url;
	
	private VHeader header;
	
	private VParames parames;
	
	/**
	 * 
	 */
	public VHttpPost(){};
	
	/**
	 * 
	 * @param url
	 */
	public VHttpPost(String url){
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public VHeader getHeader() {
		return header;
	}

	public void setHeader(VHeader header) {
		this.header = header;
	}

	public VParames getParames() {
		return parames;
	}

	public void setParames(VParames parames) {
		this.parames = parames;
	}
	
}
