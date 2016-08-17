package com.vcode.http.client;

import com.vcode.http.client.methods.VHttpMethods;

/**
 * 
 * @author 默非默
 *
 */
public interface VHttpClient {

	public abstract VHttpResponse execute(VHttpMethods methods);
}
