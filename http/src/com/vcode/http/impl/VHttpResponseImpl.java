package com.vcode.http.impl;

import java.io.InputStream;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.util.List;

import com.vcode.http.Header.VHeader;
import com.vcode.http.client.VHttpResponse;
import com.vcode.http.client.entity.VHttpEntity;

/**
 * 
 * @author 默非默
 *
 */
public class VHttpResponseImpl implements VHttpResponse {
	
	private VHttpEntity entity;

	public VHttpEntity getEntity() {
		return entity;
	}

	public void setEntity(VHttpEntity entity) {
		this.entity = entity;
	}

	@Override
	public VHeader getAllHeader() {
		return entity.getHeaders();
	}

	@Override
	public InputStream getBody() {
		return entity.getInput();
	}

	@Override
	public List<HttpCookie> getCookies() {
		CookieManager manager = VHttpClientImpl.manager;
		CookieStore cookirStore = manager.getCookieStore();
		return cookirStore.getCookies();
	}

	@Override
	public String getHeader(String key) {
		VHeader header = entity.getHeaders();
		return header.getValue(key);
	}

	@Override
	public String getLocation() {
		VHeader header = entity.getHeaders();
		String Location = header.getValue("Location");
		if (Location!=null && Location.length()>0) {
			Location = Location.substring(1, Location.length()-1);
		}else {
			Location = "";
		}
		return Location;
	}

}
