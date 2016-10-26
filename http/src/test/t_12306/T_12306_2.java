package test.t_12306;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
 * 非自动提交流程
 * Date 2016-08-17 15:15:51
 * @author 默非默
 *
 */
public class T_12306_2 {
	
	private static String username = "华浩";							//昵称，用来验证是否登录成功
	
	private static String loginName = "hao707789";					//12306登录账号
	
	private static String password = "loveXIAO707789";				//12306登录密码
	
	private static Map<String, String> citiMap = HttpUtils.getCitiInfo();	//城市列表

	public static void main(String[] args) throws Exception{
		VHttpClient client = new VHttpClientImpl();
		SimpleDateFormat format = new SimpleDateFormat("yyyyDDmm");
		String inCode = "";
		String newCode = "";
		
		VHttpGet get = new VHttpGet("https://kyfw.12306.cn/otn/login/init");
		VHttpResponse res = client.execute(get);				//获取Cookie
		System.out.println(res.getEntity().getStaus());			//打印状态码
		res.getEntity().disconnect();							//耗尽资源
		while (true){
			get = new VHttpGet("https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew?module=login&rand=sjrand&"+Math.random());
			res = client.execute(get);								//获取验证码
			System.out.println(res.getEntity().getStaus());			//打印状态码
			HttpUtils.PrintCookies(res);							//打印Cookies
			newCode = HttpUtils.outCodeBy12306(res.getBody());	//定义窗口大小
			res.getEntity().disconnect();							//耗尽资源
			
			//开始校验验证码是否正确
			VHttpPost post = new VHttpPost("https://kyfw.12306.cn/otn/passcodeNew/checkRandCodeAnsyn");
			VParames parames = new VParames();
			parames.put("rand", "sjrand");
			parames.put("randCode", newCode);
			
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
		parames.put("randCode", newCode);
		parames.put("userDTO.password", password);
		
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
		System.out.println("请输入出发地、目的地、时间，用逗号间隔(例：深圳,咸宁,2016-10-17)：");
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
		get = new VHttpGet("https://kyfw.12306.cn/otn/leftTicket/queryX?"+sb.toString());
		res = client.execute(get);
		body = HttpUtils.outHtml(res.getBody());
		json = new JSONObject(body);
		JSONArray jsonArr = new JSONArray(json.get("data").toString());
		JSONObject obj = null;						//座位信息1
		JSONObject obj2 = null; 					//座位信息2
		for (int i=0;i<jsonArr.length();i++) {
			obj = (JSONObject)jsonArr.get(i);
			obj2 = new JSONObject(obj.get("queryLeftNewDTO").toString());
			System.out.println(obj2.get("station_train_code")+"："+
								"商务座："+obj2.get("swz_num")+","+
								"特等座："+obj2.get("tz_num")+","+
								"一等座："+obj2.get("zy_num")+","+
								"二等座："+obj2.get("ze_num")+","+
								"高级软卧："+obj2.get("gr_num")+","+
								"软卧："+obj2.get("rw_num")+","+
								"硬卧："+obj2.get("yw_num")+","+
								"特等座："+obj2.get("tz_num")+","+
								"软座："+obj2.get("rz_num")+","+
								"硬座："+obj2.get("yz_num")+","+
								"无座："+obj2.get("wz_num")+","+
								"其它座："+obj2.get("qt_num"));
			if (!"--".equals(obj2.get("yz_num")) && "Y".equals(obj2.get("canWebBuy")) 
					&& !"无".equals(obj2.get("yz_num"))){
				System.out.println(obj2.get("station_train_code")+"次列车，"
						+obj2.get("start_station_name")+"到"+obj2.get("end_station_name")+"硬座票========"+obj2.get("yz_num"));
				System.out.println("开始提交订单");
				break;
			}
		}
		res.getEntity().disconnect();
		
		
		//验证用户是否登录
		post = new VHttpPost("https://kyfw.12306.cn/otn/login/checkUser");
		parames.clear();
		parames.put("_json_att", "");
		post.setParames(parames);
		res = client.execute(post);
		body = HttpUtils.outHtml(res.getBody());
		JSONObject res_obj = new JSONObject(body);
		JSONObject dataobj = new JSONObject(res_obj.get("data").toString());
		if ("true".equals(res_obj.get("status").toString()) && "true".equals(dataobj.get("flag").toString())) {
			System.out.println("当前用户已登录");
		}else {
			System.out.println("检测用户登录信息过期，请重新登录"+body);
		}
		res.getEntity().disconnect();
		
		//预订车票
		post = new VHttpPost("https://kyfw.12306.cn/otn/leftTicket/submitOrderRequest");
		parames.clear();
		parames.put("secretStr", obj.getString("secretStr"));
		parames.put("train_date", citi.split(",")[2]);
		parames.put("back_train_date", format.format(new Date()));
		parames.put("tour_flag", "dc");
		parames.put("purpose_codes", "ADULT");
		parames.put("query_from_station_name", obj2.getString("from_station_name"));
		parames.put("query_to_station_name", obj2.getString("to_station_name"));
		parames.put("undefined", "");
		post.setParames(parames);
		res = client.execute(post);
		body = HttpUtils.outHtml(res.getBody());
		res_obj = new JSONObject(body);
		if ("true".equals(res_obj.get("status").toString())) {
			System.out.println("车票预定成功");
		}else {
			System.out.println(res_obj.get("messages").toString());
			System.exit(0);
		}
		res.getEntity().disconnect();
		
		//确定预订界面
		post = new VHttpPost("https://kyfw.12306.cn/otn/confirmPassenger/initDc");
		parames.clear();
		parames.put("_json_att", "");
		post.setParames(parames);
		post.setParames(parames);
		res = client.execute(post);
		body = HttpUtils.outHtml(res.getBody());
		
		Pattern pattern = Pattern.compile("var globalRepeatSubmitToken = '[0-9 | a-z]{32}'");
		Pattern pattern2 = Pattern.compile("'key_check_isChange':'[0-9 | A-Z]{56}'");
		Matcher matcher = pattern.matcher(body);
		Matcher matcher2 = pattern2.matcher(body);
		String REPEAT_SUBMIT_TOKEN = "";
		String key_check_isChange = "";
		while (matcher.find()) {
			REPEAT_SUBMIT_TOKEN = matcher.group(0).replace("var globalRepeatSubmitToken = '", "").replace("'", "");
		}
		while (matcher2.find()) {
			key_check_isChange = matcher2.group(0).replace("'key_check_isChange':'", "").replace("'", "");
		}
		res.getEntity().disconnect();
		
		
		//获取订单人员列表
		post = new VHttpPost("https://kyfw.12306.cn/otn/confirmPassenger/getPassengerDTOs");
		parames.clear();
		parames.put("REPEAT_SUBMIT_TOKEN", REPEAT_SUBMIT_TOKEN);
		parames.put("_json_att", "");
		post.setParames(parames);
		res = client.execute(post);
		body = HttpUtils.outHtml(res.getBody());
		JSONObject userListObj = (JSONObject)new JSONObject(body).get("data");
		if (userListObj.length()<1) {
			System.out.println(new JSONObject(body).get("messages"));
			System.exit(0);
		}
		JSONArray userArr = (JSONArray)(userListObj.get("normal_passengers"));
		System.out.println("=================乘客人员列表===================");
		for (int i=0;i<userArr.length();i++){
			JSONObject user_obj = (JSONObject)userArr.get(i);
			System.out.print(i+": "+user_obj.get("passenger_name"));
			if (i<userArr.length()-1){
				System.out.print(",");
			}
		}
		System.out.println("请输入乘客人员的ID（单个）：");
		Scanner userin = new Scanner(System.in);
		String userCode = userin.nextLine();		//乘车人序号
		JSONObject userObj = new JSONObject();		//乘车人Json对象
		for (int i=0;i<userArr.length();i++){
			JSONObject user_obj = (JSONObject)userArr.get(i);
			if ((i+"").equals(userCode)){
				System.out.println("你选择了："+user_obj.getString("passenger_name"));
				userObj = user_obj;
				break;
			}
		}
		
		
		while (true){
			//拉取验证码
			get = new VHttpGet("https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew?module=passenger&rand=randp&"+Math.random());
			res = client.execute(get);								//获取验证码
			newCode = HttpUtils.outCodeBy12306(res.getBody());	//定义窗口大小
			res.getEntity().disconnect();							//耗尽资源
			
			post = new VHttpPost("https://kyfw.12306.cn/otn/passcodeNew/checkRandCodeAnsyn");
			VParames parames5 = new VParames();
			parames5.put("randCode", newCode);
			parames5.put("rand", "randp");
			parames5.put("_json_att", "");
			parames5.put("REPEAT_SUBMIT_TOKEN", REPEAT_SUBMIT_TOKEN);
			
			post.setParames(parames5);
			res = client.execute(post); 
			body = HttpUtils.outHtml(res.getBody());
			System.out.println(body);					//打印结果
			res_obj = new JSONObject(body);
			JSONObject dataObj = (JSONObject)res_obj.get("data");
			if ("1".equals(dataObj.get("result").toString())) {
				System.out.println("验证码正确，开始确认提交订单");
				break;
			}else {
				System.out.println("验证码错误，重新拉取");
				System.out.println(res_obj.get("validateMessages").toString());
				continue;
			}
		}
		res.getEntity().disconnect();
		
		//确认用户是否可以提交订单
		post = new VHttpPost("https://kyfw.12306.cn/otn/confirmPassenger/checkOrderInfo");
		parames.clear();
		parames.put("cancel_flag", "2");
		parames.put("bed_level_order_num", "000000000000000000000000000000");
		parames.put("passengerTicketStr", "1,0,1,"+userObj.getString("passenger_name")+",1,"+userObj.getString("passenger_id_no")+","+userObj.getString("mobile_no")+",N");
		parames.put("oldPassengerStr", userObj.getString("passenger_name")+",1,"+userObj.getString("passenger_id_no")+",1_");
		parames.put("tour_flag", "dc");
		parames.put("randCode", newCode);
		parames.put("_json_att", "");
		parames.put("REPEAT_SUBMIT_TOKEN", REPEAT_SUBMIT_TOKEN);
		post.setParames(parames);
		res = client.execute(post);
		body = HttpUtils.outHtml(res.getBody());
		res_obj = new JSONObject(body);
		dataobj = new JSONObject(res_obj.get("data").toString());
		if ("true".equals(dataobj.get("submitStatus").toString())) {
			System.out.println("当前用户可以提交订单");
		}else {
			System.out.println(res_obj.get("messages").toString());
			System.exit(0);
		}
		res.getEntity().disconnect();
		
		
		//可以提交订单后，开始获取剩余票数
		post = new VHttpPost("https://kyfw.12306.cn/otn/confirmPassenger/getQueueCount");
		VParames parames4 = new VParames();
		parames4.put("train_date", format.parse(citi.split(",")[2])+"");
		parames4.put("train_no", obj2.get("train_no").toString());
		parames4.put("stationTrainCode", obj2.get("station_train_code").toString());
		parames4.put("seatType", "3");
		parames4.put("fromStationTelecode", obj2.get("from_station_telecode").toString());
		parames4.put("toStationTelecode", obj2.get("to_station_telecode").toString());
		parames4.put("leftTicket", obj2.getString("yp_info"));
		parames4.put("purpose_codes", "00");
		parames4.put("train_location", obj2.getString("location_code"));
		parames4.put("_json_att", "");
		parames4.put("REPEAT_SUBMIT_TOKEN", REPEAT_SUBMIT_TOKEN);
		post.setParames(parames4);
		res = client.execute(post);
		body = HttpUtils.outHtml(res.getBody());
		System.out.println(body);					//打印结果
		JSONObject jsonBody = new JSONObject(body);
		if ("true".equals(jsonBody.get("status").toString())){
			JSONObject dataObj = (JSONObject)jsonBody.get("data");
			String[] counts = HttpUtils.getCountByJs(dataObj.get("ticket").toString(), "1").split(",");
			
			if (Integer.parseInt(counts[0]) > 0) {
				System.out.println(obj2.get("station_train_code")+"：硬座剩余:"+counts[0]+"张");
			}else if (Integer.parseInt(counts[1]) > 0){
				System.out.println(obj2.get("station_train_code")+"：硬座剩余:"+counts[1]+"张");
			}
			System.out.println("开始确认提交订单");
		}else {
			System.out.println(jsonBody.get("messages").toString());
			System.exit(0);
		}
		res.getEntity().disconnect();
		
		
		//确定提交订单
		post = new VHttpPost("https://kyfw.12306.cn/otn/confirmPassenger/confirmSingleForQueue");
		parames.clear();
		parames.put("passengerTicketStr", "1,0,1,"+userObj.getString("passenger_name")+",1,"+userObj.getString("passenger_id_no")+","+userObj.getString("mobile_no")+",N");
		parames.put("oldPassengerStr", userObj.getString("passenger_name")+",1,"+userObj.getString("passenger_id_no")+",1_");
		parames.put("randCode", newCode);
		parames.put("purpose_codes", "00");
		parames.put("key_check_isChange", key_check_isChange);
		parames.put("leftTicketStr", obj2.getString("yp_info"));
		parames.put("train_location", obj2.getString("location_code"));
		parames.put("roomType", "00");
		parames.put("dwAll", "N");
		parames.put("_json_att", "");
		parames.put("REPEAT_SUBMIT_TOKEN", REPEAT_SUBMIT_TOKEN);
		post.setParames(parames);
		res = client.execute(post);
		body = HttpUtils.outHtml(res.getBody());
		res_obj = new JSONObject(body);
		dataobj = new JSONObject(res_obj.get("data").toString());
		if ("true".equals(dataobj.get("submitStatus").toString())) {
			System.out.println("提交成功，正在查询订票结果");
		}else {
			System.out.println("出票失败"+body);
			System.exit(0);
		}
		res.getEntity().disconnect();
		
		//开始查询订单
		boolean order = true;
		String orderId = "";
		while (order) {
			Random ne=new Random();
	        int x=ne.nextInt(9999-1000+1)+1000;
			String query_url = "https://kyfw.12306.cn/otn/confirmPassenger/queryOrderWaitTime?";
			query_url = query_url + "random=14772940"+x+"&tourFlag=dc&_json_att=&REPEAT_SUBMIT_TOKEN="+REPEAT_SUBMIT_TOKEN;
			get = new VHttpGet(query_url);
			res = client.execute(get);
			body = HttpUtils.outHtml(res.getBody());
			res_obj = new JSONObject(body);
			dataobj = new JSONObject(res_obj.get("data").toString());
			if (!"null".equals(dataobj.get("orderId").toString())) {
				order = false;
				orderId = dataobj.get("orderId").toString();
			}
		}
		System.out.println("恭喜你，成功订到一张"+obj2.getString("from_station_name")+"至"+obj2.getString("end_station_name")+"的硬座，订单号为："+orderId+"，请尽快付款，以免耽误行程");
	}

}
