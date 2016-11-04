package com.vcode.ticket.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONObject;

import com.vcode.http.client.VHttpClient;
import com.vcode.http.client.VHttpResponse;
import com.vcode.http.client.methods.VHttpGet;
import com.vcode.http.client.methods.VHttpPost;
import com.vcode.http.client.parames.VParames;
import com.vcode.http.impl.VHttpClientImpl;
import com.vcode.http.utils.VHttpUtils;

public class T_douban {

	/**豆瓣测试类
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) {
        try {
        	//取验证码地址和Cookie
        	VHttpClient client = new VHttpClientImpl();
    		VHttpGet get = new VHttpGet("https://www.douban.com/j/misc/captcha");
    		VHttpResponse res = client.execute(get);
    		
//    		res.getAllHeader().PrintlnAllHeaderInfo();	//打印头信息
    		System.out.println(res.getEntity().getStaus());	//打印返回的状态码
    		String result = VHttpUtils.outHtml(res.getBody());
        	
			JSONObject json = new JSONObject(result);
			System.out.println(json.get("url"));
			
			//获取验证码
			get = new VHttpGet("https:"+json.get("url").toString());
			VHttpResponse rs = client.execute(get);
//			rs.getAllHeader().PrintlnAllHeaderInfo();	//打印头信息
			System.out.println(rs.getEntity().getStaus());	//打印返回的状态码
			String code = VHttpUtils.outCode(rs.getBody(),0,0);	//普通验证码获取
			
			//设置参数
	        Map<String, String> map = new HashMap<String, String>();
	        map.put("source", "None");
			map.put("redir", "https://www.douban.com/");
			map.put("form_email", "3094759846@qq.com");
			map.put("form_password", "520xiao707789");
			map.put("captcha-solution", code);
			map.put("captcha-id", json.getString("token"));
			map.put("login", "登录");
			
			VHttpPost post = new VHttpPost("https://accounts.douban.com/login");
			VParames parames = new VParames();
			parames.setParames(map);
			post.setParames(parames);
			VHttpResponse loginrs = client.execute(post);
//			loginrs.getAllHeader().PrintlnAllHeaderInfo();	//打印头信息
			System.out.println(loginrs.getEntity().getStaus());	//打印返回的状态码
			if (loginrs.getEntity().getStaus()==200) {
				String body = VHttpUtils.outHtml(loginrs.getBody());
				if (body.contains("默非默")) {
					System.out.println("恭喜你，登录成功");
				}
			}
			if (loginrs.getEntity().getStaus()==302){
				String url = loginrs.getLocation();
				get.setUrl(url);
				VHttpResponse rs2 = client.execute(get);
//				rs2.getAllHeader().PrintlnAllHeaderInfo();	//打印头信息
				System.out.println(rs2.getEntity().getStaus());	//打印返回的状态码
				VHttpUtils.PrintHtmlByLine(rs2.getBody());
				rs2.getEntity().disconnect();
			}
			loginrs.getEntity().disconnect();
			
			while (true){
				System.out.println("请输入要发表的说说：");
				Scanner scan = new Scanner(System.in);
				String coment = scan.nextLine();
				
				//发表说说
				post = new VHttpPost("https://www.douban.com/");
				VParames parames2 = new VParames();
				parames2.put("ck", "fGh2");
				parames2.put("comment", coment);
				post.setParames(parames2);
				VHttpResponse res2 = client.execute(post);
				System.out.println(res2.getEntity().getStaus());
				String body = VHttpUtils.outHtmlByLine(res2.getBody());
				if (body.contains(coment)) {
					System.out.println(coment+"	发表成功");
				}else {
					res2.getAllHeader().PrintlnAllHeaderInfo();
					System.out.println("发表失败");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
