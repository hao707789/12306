package com.vcode.http.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import com.vcode.http.Header.VHeader;
import com.vcode.http.MyX509TrustManager.MyX509TrustManager;
import com.vcode.http.client.VHttpClient;
import com.vcode.http.client.VHttpResponse;
import com.vcode.http.client.entity.VHttpEntity;
import com.vcode.http.client.methods.VHttpMethods;
import com.vcode.http.client.parames.VParames;
import com.vcode.http.utils.Constant;

/**
 * 
 * @author 默非默
 *
 */
public class VHttpClientImpl implements VHttpClient {
	
	public static CookieManager manager = new CookieManager();
	
	static {
		CookieHandler.setDefault(manager);
	}

	@Override
	public VHttpResponse execute(VHttpMethods methods) {
		VHttpResponseImpl res = new VHttpResponseImpl();
		VHttpEntity entity = new VHttpEntity();
		String newurl = methods.getUrl();
		if (Constant.HTTPGET.equals(methods.getType())) {
			String parameStr = prepareParam(methods.getParames());
			if (Constant.QUESTIONMARK.equals(newurl.substring(newurl.length()-1))) {
				newurl = newurl+parameStr;
			}else {
				if (!"".equals(parameStr)) {
					newurl = newurl+Constant.QUESTIONMARK+parameStr;
				}
			}
		}
		
		try{
			if (methods.getUrl().contains(Constant.HTTPS)) {
				URL url = new URL(newurl);
				HttpsURLConnection httpsConn = (HttpsURLConnection)url.openConnection();
				https(httpsConn, methods);
				entity.setInput(httpsConn.getInputStream());
				entity.setHeaders(new VHeader(httpsConn.getHeaderFields()));
				entity.setStaus(httpsConn.getResponseCode());
				entity.setConn(httpsConn);
				res.setEntity(entity);
			}else {
				URL url = new URL(newurl);
				HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
				http(conn, methods);
				entity.setInput(conn.getInputStream());
				entity.setHeaders(new VHeader(conn.getHeaderFields()));
				entity.setStaus(conn.getResponseCode());
				entity.setConn(conn);
				res.setEntity(entity);
			}
		}catch(IOException e){
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * http请求
	 * @param conn
	 * @param methods
	 * @throws IOException
	 */
	private void http(HttpURLConnection conn, VHttpMethods methods) throws IOException{
		conn.setInstanceFollowRedirects(false);
		conn.setRequestMethod(methods.getType());
		String paramStr = prepareParam(methods.getParames());
		
		if (methods.getType().equals(Constant.HTTPGET)) {
			conn.connect();
		}else {
	        conn.setDoInput(true);  
	        conn.setDoOutput(true);
	        OutputStream os = conn.getOutputStream();
	        os.write(paramStr.toString().getBytes("utf-8" ));       
	        os.close(); 
		}
	}
	
	/**
	 * https请求
	 * @param conn
	 * @param methods
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	private void https(HttpsURLConnection conn, VHttpMethods methods) throws IOException, 
										NoSuchAlgorithmException, KeyManagementException{
		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(null, new TrustManager[]{new MyX509TrustManager()}, null);
		conn.setSSLSocketFactory(sslContext.getSocketFactory());
		
		conn.setRequestMethod(methods.getType());
		String paramStr = prepareParam(methods.getParames());
		
        
		if (methods.getType().equals(Constant.HTTPGET)) {
			conn.connect();
		}else {
	        conn.setDoInput(true);  
	        conn.setDoOutput(true);  
	        OutputStream os = conn.getOutputStream();
	        os.write(paramStr.toString().getBytes("utf-8" ));       
	        os.close(); 
		}
	}
	
	/**
	 * 装配参数
	 * @param parames
	 * @return
	 */
	private String prepareParam(VParames parames){
		if (parames==null || "".equals(parames.getParames())) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		Map<String,String> map = parames.getParames();
		Set<String> set = map.keySet();
		for (String s : set) {
			sb.append(s+"=");
			sb.append(map.get(s)+"&");
		}
		sb.append("1=1");
		return sb.toString();
	}
}
