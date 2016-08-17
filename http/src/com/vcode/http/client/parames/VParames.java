package com.vcode.http.client.parames;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author 默非默
 *
 */
public class VParames {
	
	private Map<String,String> parames = new HashMap<String, String>();

	public Map<String, String> getParames() {
		return parames;
	}

	public void setParames(Map<String, String> parames) {
		this.parames = parames;
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, String value){
		parames.put(key, value);
	}
	
	/**
	 * 
	 */
	public VParames(){};
	
	/**
	 * 
	 * @param map
	 */
	public VParames(Map<String, String> map){
		this.parames = map;
	}

}
