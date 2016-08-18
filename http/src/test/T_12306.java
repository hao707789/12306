package test;

import java.util.Map;
import java.util.Scanner;

import org.json.JSONArray;
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
	
	private static String username = "XXXXXXXX";							//昵称，用来验证是否登录成功
	
	private static String loginName = "XXXXXXXXX";					//12306登录账号
	
	private static String password = "XXXXXXXXXXX";				//12306登录密码
	
	private static Map<String, String> citiMap = HttpUtils.getCitiInfo();	//城市列表

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
		parames.put("loginUserDTO.user_name", loginName);
		parames.put("randCode", inCode);
		parames.put("userDTO.password", password);
		
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
		
		
		//开始刷票
		System.out.println("请输入出发地、目的地、时间，用逗号间隔(例：深圳,咸宁,2016-08-18)：");
		Scanner in = new Scanner(System.in);
		String citi = in.nextLine();
		StringBuffer sb = new StringBuffer();
		sb.append("leftTicketDTO.train_date="+citi.split(",")[2]+"&");
		sb.append("leftTicketDTO.from_station="+citiMap.get(citi.split(",")[0])+"&");
		sb.append("leftTicketDTO.to_station="+citiMap.get(citi.split(",")[1])+"&");
		sb.append("purpose_codes=ADULT");
		
		//第一次刷票，不知道干嘛的，没有返回内容
		get = new VHttpGet("https://kyfw.12306.cn/otn/leftTicket/log?"+sb.toString());
		res = client.execute(get);
		res.getEntity().disconnect();
		
		//第二次，真正返回刷票结果，此段代码因命令行模拟无法提供很好的交互，所以写死了订无座票，后期改
		get = new VHttpGet("https://kyfw.12306.cn/otn/leftTicket/queryT?"+sb.toString());
		res = client.execute(get);
		body = HttpUtils.outHtml(res.getBody());
		json = new JSONObject(body);
		JSONArray jsonArr = new JSONArray(json.get("data").toString());
		for (int i=0;i<jsonArr.length();i++) {
			JSONObject obj = (JSONObject)jsonArr.get(i);
			JSONObject obj2 = new JSONObject(obj.get("queryLeftNewDTO").toString());
			if (!"--".equals(obj2.get("wz_num")) && "Y".equals(obj2.get("canWebBuy"))){
				System.out.println(obj2.get("station_train_code")+"次列车，"
						+obj2.get("start_station_name")+"到"+obj2.get("end_station_name")+"无座票========有票");
				System.out.println("开始提交订单");
				break;
			}
		}
		//开始提交订单
		System.out.println("提交订单逻辑没写");
	}

}
