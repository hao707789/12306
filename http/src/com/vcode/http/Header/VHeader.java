package com.vcode.http.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author 默非默
 *
 */
public class VHeader {
	
	private Map<String, List<String>> headerMap = new HashMap<String, List<String>>();

	public Map<String, List<String>> getHeaderMap() {
		return headerMap;
	}

	public void setHeaderMap(Map<String, List<String>> headerMap) {
		this.headerMap = headerMap;
	}
	
	/**
	 * 无参构造方法
	 */
	public VHeader(){}
	
	/**
	 * 有参构造方法
	 * @param map
	 */
	public VHeader(Map<String, List<String>> map){
		setHeaderMap(map);
	}

	/**
	 * 获取Value的值
	 * @param name
	 * @return
	 */
	public String getValue(String key){
		return headerMap.get(key).toString();
	}
	
	/**
	 * 获取name集合
	 * @return
	 */
	public String[] getKeys(){
		List<String> list = new ArrayList<String>();
		Set<String> set = headerMap.keySet();
		for (String s : set) {
			list.add(s);
		}
		return list.toArray(new String[]{});
	}
	
	/**
	 * 存值
	 * @param key
	 * @param value
	 */
	public void put(String key, String value){
		List<String> list = new ArrayList<String>();
		list.add(value);
		headerMap.put(key, list);
	}
	
	public void PrintlnAllHeaderInfo(){
		Set<String> set = headerMap.keySet();
		System.out.println("===================开始打印头信息=================");
		for (String s : set){
			System.out.println(s+"=="+headerMap.get(s));
		}
		System.out.println("===================打印头信息结束=================");
	}
}
