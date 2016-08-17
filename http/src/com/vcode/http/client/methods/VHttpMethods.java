package com.vcode.http.client.methods;

import com.vcode.http.Header.VHeader;
import com.vcode.http.client.parames.VParames;

/**
 * 
 * @author 默非默
 *
 */
public interface VHttpMethods {
	
	public abstract String getUrl();
	
	public abstract VHeader getHeader();
	
	public abstract VParames getParames();
	
	public abstract String getType();

}
