package test;

import java.util.Random;

import org.json.JSONObject;

import com.vcode.http.Header.VHeader;
import com.vcode.http.client.VHttpClient;
import com.vcode.http.client.VHttpResponse;
import com.vcode.http.client.methods.VHttpGet;
import com.vcode.http.client.methods.VHttpPost;
import com.vcode.http.client.parames.VParames;
import com.vcode.http.impl.VHttpClientImpl;
import com.vcode.http.utils.HttpUtils;

/**
 * Date 2016-08-17 15:15:51
 * @author 默非默
 *
 */
public class T_12306 {
	
	private static String username = "华浩";

	public static void main(String[] args) throws Exception{
		VHttpClient client = new VHttpClientImpl();
		String inCode = "";
		VHttpGet get = new VHttpGet("https://kyfw.12306.cn/otn/login/init");
		VHttpResponse res = client.execute(get);				//获取Cookie
		System.out.println(res.getEntity().getStaus());			//打印状态码
		res.getEntity().disconnect();							//耗尽资源
		
		while (true){
			get = new VHttpGet("https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew?module=login&rand=sjrand&"+Math.random());
			res = client.execute(get);								//获取验证码
			System.out.println(res.getEntity().getStaus());			//打印状态码
			HttpUtils.PrintCookies(res);							//打印Cookies
			inCode = HttpUtils.outCode(res.getBody(), 300, 220);	//定义窗口大小
			res.getEntity().disconnect();							//耗尽资源
			
			//开始校验验证码是否正确
			VHttpPost post = new VHttpPost("https://kyfw.12306.cn/otn/passcodeNew/checkRandCodeAnsyn");
			VParames parames = new VParames();
			parames.put("rand", "sjrand");
			parames.put("randCode", inCode);
			
			post.setParames(parames);							//装配提交的Form
			res = client.execute(post);
			System.out.println(res.getEntity().getStaus());		//打印状态码
			HttpUtils.PrintCookies(res);							//打印Cookies
			String body = HttpUtils.outHtml(res.getBody());		//将网页内容转为文本
			JSONObject json = new JSONObject(body);
			JSONObject json2 = new JSONObject(json.get("data").toString());
			if ("1".equals(json2.get("result"))) {
				System.out.println("验证码正确，开始提交表单");
				res.getEntity().disconnect();
				break;											//正确的话开始执行下一步动作
			}else {
				System.out.println("验证码错误");
				res.getEntity().disconnect();
				continue;										//否则刷新验证码重来
			}
		}
		
		//开始提交登录信息
		VHttpPost post = new VHttpPost("https://kyfw.12306.cn/otn/login/loginAysnSuggest");
		VParames parames = new VParames();
		parames.put("loginUserDTO.user_name", "hao707789");
		parames.put("randCode", inCode);
		parames.put("userDTO.password", "Hh707789");
		
		VHeader header = new VHeader();
		header.put("Host", "kyfw.12306.cn");
		header.put("Referer", "https://kyfw.12306.cn/otn/login/init");
		header.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0");
		header.put("X-Requested-With", "XMLHttpRequest");
		header.put("Connection", "keep-alive");
		header.put("Accept", "*/*");
		header.put("Content-Length", "99");
		
		post.setHeader(header);
		post.setParames(parames);
		res = client.execute(post);
		System.out.println(res.getEntity().getStaus());			//打印状态码
		HttpUtils.PrintCookies(res);							//打印Cookies
		String body = HttpUtils.outHtml(res.getBody());			//将网页内容转为文本
		JSONObject json = new JSONObject(body);
		JSONObject json2 = new JSONObject(json.get("data").toString());
		if (json2.length()>1 && "Y".equals(json2.get("loginCheck"))) {
			System.out.println("恭喜"+username+"登录成功，正在跳转到主页");
		}else {
			System.out.println(json.get("messages"));
			System.exit(0);
		}
		res.getEntity().disconnect();
		
		//开始第二次登录
		post = new VHttpPost("https://kyfw.12306.cn/otn/login/userLogin");
		VParames parames2 = new VParames();
		parames2.put("_json_att", "");
		post.setParames(parames2);								//装配参数
		res = client.execute(post);								//提交登录
		System.out.println(res.getEntity().getStaus());			//打印状态码，200、302......
		HttpUtils.PrintCookies(res);							//打印Cookies
		if (res.getEntity().getStaus()==302){
			get = new VHttpGet(res.getLocation());				//获取302的跳转链接并访问
			res.getEntity().disconnect();
			res = client.execute(get);
			if (HttpUtils.outHtml(res.getBody()).contains(username)){		//验证是否登录成功
				System.out.println(username+"你好，欢迎您登录中国铁路客户服务中心网站。");
			}else {
				System.out.println("登录失败");
			}
			res.getEntity().disconnect();
		}else if (res.getEntity().getStaus()==200){
			if (HttpUtils.outHtml(res.getBody()).contains(username)){		//验证是否登录成功
				System.out.println(username+"你好，欢迎您登录中国铁路客户服务中心网站。");
			}else {
				System.out.println("登录失败");
			}
			res.getEntity().disconnect();
		}
	}

}
