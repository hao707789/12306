package test.t_12306;

import java.text.SimpleDateFormat;
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
 * 自动提交流程
 * Date 2016-08-17 15:15:51
 * @author 默非默
 *
 */
public class T_12306 {
	
	private static String username = "华浩";							//昵称，用来验证是否登录成功
	
	private static String loginName = "hao707789";					//12306登录账号
	
	private static String password = "loveXIAO707789";				//12306登录密码
	
	private static Map<String, String[]> citiMap = HttpUtils.getCitiInfo();	//城市列表

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
			inCode = HttpUtils.outCode(res.getBody(), 300, 220);	//定义窗口大小
			res.getEntity().disconnect();							//耗尽资源
			
			String[] codeArr = inCode.split(",");
			for (int i=0;i<codeArr.length;i++) {
				if ("1".equals(codeArr[i])) {
					newCode+="40,40";
				}else if ("2".equals(codeArr[i])) {
					newCode+="110,40";
				}else if ("3".equals(codeArr[i])) {
					newCode+="180,40";
				}else if ("4".equals(codeArr[i])){
					newCode+="250,40";
				}else if ("5".equals(codeArr[i])) {
					newCode+="40,110";
				}else if ("6".equals(codeArr[i])) {
					newCode+="110,110";
				}else if ("7".equals(codeArr[i])) {
					newCode+="180,110";
				}else if ("8".equals(codeArr[i])) {
					newCode+="250,110";
				}
				if (i!=codeArr.length-1) newCode+=",";
			}
			
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
		
		//获取订单人员列表
		post = new VHttpPost("https://kyfw.12306.cn/otn/confirmPassenger/getPassengerDTOs");
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
			JSONObject obj = (JSONObject)userArr.get(i);
			System.out.print(i+": "+obj.get("passenger_name"));
			if (i<userArr.length()-1){
				System.out.print(",");
			}
		}
		System.out.println("请输入乘客人员的ID（单个）：");
		Scanner userin = new Scanner(System.in);
		String userCode = userin.nextLine();		//乘车人序号
		JSONObject userObj = new JSONObject();		//乘车人Json对象
		for (int i=0;i<userArr.length();i++){
			JSONObject obj = (JSONObject)userArr.get(i);
			if ((i+"").equals(userCode)){
				System.out.println("你选择了："+obj.getString("passenger_name"));
				userObj = obj;
				break;
			}
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
		get = new VHttpGet("https://kyfw.12306.cn/otn/leftTicket/queryT?"+sb.toString());
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
		
		//======================开始提交订单======================
		
		//提交订票信息，并返回相关令牌
		post = new VHttpPost("https://kyfw.12306.cn/otn/confirmPassenger/autoSubmitOrderRequest");
		VParames parames3 = new VParames();
		parames3.put("bed_level_order_num", "000000000000000000000000000000");
		parames3.put("cancel_flag", "2");
		parames3.put("oldPassengerStr", userObj.getString("passenger_name")+",1,"+userObj.getString("passenger_id_no")+",1_");
		parames3.put("passengerTicketStr", "1,0,1,"+userObj.getString("passenger_name")+",1,"+userObj.getString("passenger_id_no")+","+userObj.getString("mobile_no")+",N");
		parames3.put("purpose_codes", "ADULT");				//adult: "1" 成人, child: "2" 孩子,student: "3" 学生,disability: "4"  残疾
		parames3.put("query_from_station_name", obj2.getString("from_station_name"));
		parames3.put("query_to_station_name", obj2.getString("to_station_name"));
		parames3.put("secretStr", obj.getString("secretStr"));
		parames3.put("tour_flag", "dc");
		parames3.put("train_date", citi.split(",")[2]);
		
		post.setParames(parames3);
		res = client.execute(post);
		body = HttpUtils.outHtml(res.getBody());
		System.out.println(body);					//打印结果
		JSONObject orderObj = new JSONObject(body);
		JSONObject ParamesObj = new JSONObject();					//用于后面的参数
		if ("true".equals(orderObj.get("status").toString()) && "true".equals(((JSONObject)orderObj.get("data")).get("submitStatus").toString())) {			
			System.out.println("校验成功，当前用户可以提交订单，开始获取票数");
			JSONObject obj3 = (JSONObject)orderObj.get("data");
			String paStr = obj3.getString("result");
			ParamesObj.put("train_location", paStr.split("#")[0]);
			ParamesObj.put("key_check_isChange", paStr.split("#")[1]);
			ParamesObj.put("leftTicketStr", paStr.split("#")[2]);
		}else {
			System.out.println(orderObj.get("messages"));
		}
		res.getEntity().disconnect();
		
		
		//获取剩余票数
		post = new VHttpPost("https://kyfw.12306.cn/otn/confirmPassenger/getQueueCountAsync");
		VParames parames4 = new VParames();
		parames4.put("_json_att", "");
		parames4.put("fromStationTelecode", obj2.get("from_station_telecode").toString());
		parames4.put("leftTicket", ParamesObj.get("leftTicketStr").toString());
		parames4.put("purpose_codes", "ADULT");
		parames4.put("seatType", "1");
		parames4.put("stationTrainCode", obj2.get("station_train_code").toString());
		parames4.put("toStationTelecode", obj2.get("to_station_telecode").toString());
		parames4.put("train_date", format.parse(citi.split(",")[2])+"");
		parames4.put("train_no", obj2.get("train_no").toString());
		
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
			System.out.println("开始拉取验证码");
		}else {
			System.out.println(jsonBody.get("messages").toString());
			System.exit(0);
		}
		res.getEntity().disconnect();
		
		//拉取验证码
		
		get = new VHttpGet("https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew?module=passenger&rand=randp&"+Math.random());
		res = client.execute(get);								//获取验证码
		inCode = HttpUtils.outCode(res.getBody(), 300, 220);	//定义窗口大小
		res.getEntity().disconnect();							//耗尽资源
		
		//验证验证码
		String[] codeArr = inCode.split(",");
		newCode = "";
		for (int i=0;i<codeArr.length;i++) {
			if ("1".equals(codeArr[i])) {
				newCode+="40,40";
			}else if ("2".equals(codeArr[i])) {
				newCode+="110,40";
			}else if ("3".equals(codeArr[i])) {
				newCode+="180,40";
			}else if ("4".equals(codeArr[i])){
				newCode+="250,40";
			}else if ("5".equals(codeArr[i])) {
				newCode+="40,110";
			}else if ("6".equals(codeArr[i])) {
				newCode+="110,110";
			}else if ("7".equals(codeArr[i])) {
				newCode+="180,110";
			}else if ("8".equals(codeArr[i])) {
				newCode+="250,110";
			}
			if (i!=codeArr.length-1) newCode+=",";
		}
		
		post = new VHttpPost("https://kyfw.12306.cn/otn/passcodeNew/checkRandCodeAnsyn");
		VParames parames5 = new VParames();
		parames5.put("_json_att", "");
		parames5.put("rand", "randp");
		parames5.put("randCode", newCode);
		
		post.setParames(parames5);
		res = client.execute(post); 
		body = HttpUtils.outHtml(res.getBody());
		System.out.println(body);					//打印结果
		jsonBody = new JSONObject(body);
		JSONObject dataObj = (JSONObject)jsonBody.get("data");
		if ("1".equals(dataObj.get("result").toString())) {
			System.out.println("验证码正确，开始确认提交订单");
		}else {
			System.out.println(jsonBody.get("validateMessages").toString());
			System.exit(0);
		}
		res.getEntity().disconnect();
		
		//确认提交订单
		post = new VHttpPost("https://kyfw.12306.cn/otn/confirmPassenger/confirmSingleForQueueAsys");
		VParames parames6 = new VParames();
		parames6.put("_json_att", "");
		parames6.put("key_check_isChange", ParamesObj.get("key_check_isChange").toString());
		parames6.put("leftTicketStr", ParamesObj.get("leftTicketStr").toString());
		parames6.put("oldPassengerStr", userObj.getString("passenger_name")+",1,"+userObj.getString("passenger_id_no")+",1_");
		parames6.put("passengerTicketStr", "1,0,1,"+userObj.getString("passenger_name")+",1,"+userObj.getString("passenger_id_no")+","+userObj.getString("mobile_no")+",N");
		parames6.put("purpose_codes", "ADULT");
		parames6.put("randCode", newCode);
		parames6.put("train_location", ParamesObj.get("train_location").toString());
		
		VHeader header1 = new VHeader();
		header1.put("Host", "kyfw.12306.cn");
		header1.put("Referer", "https://kyfw.12306.cn/otn/leftTicket/init");
		header1.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0");
		header1.put("X-Requested-With", "XMLHttpRequest");
		header1.put("Connection", "keep-alive");
		header1.put("Accept", "*/*");
		header1.put("Content-Length", "376");
		
		System.out.println(parames6.getParames().toString());
		post.setParames(parames6);
		post.setHeader(header1);
		res = client.execute(post);
		body = HttpUtils.outHtml(res.getBody());
		System.out.println(body);					//打印结果
		jsonBody = new JSONObject(body);
		dataObj = (JSONObject)jsonBody.get("data");
		if ("true".equals(jsonBody.get("status").toString()) &&
				"true".equals(dataObj.get("submitStatus").toString())){
			System.out.println("订单提交成功...开始查询订单结果");
		}else {
			System.out.println(dataObj.get("errMsg").toString());
			System.exit(0);
		}
		res.getEntity().disconnect();
		
		//查询订单结果
		while (true){
			get = new VHttpGet("https://kyfw.12306.cn/otn/confirmPassenger/queryOrderWaitTime?random="+Math.random()+"&tourFlag=dc&_json_att=");
			res = client.execute(get);								
			body = HttpUtils.outHtml(res.getBody());
			jsonBody = new JSONObject(body);
			dataObj = (JSONObject)jsonBody.get("data");
			if ("-1".equals(dataObj.get("waitTime").toString())){
				System.out.println("恭喜"+obj.getString("passenger_name")+"，成功订到"+obj2.get("station_train_code")+"次列车，"
						+obj2.get("start_station_name")+"到"+obj2.get("end_station_name")+"硬座票,订单号为："+dataObj.get("orderId").toString());
				res.getEntity().disconnect();
				break;
			}else {
				System.out.println("正在排队中，预计需要"+dataObj.get("waitTime").toString()+"秒");
				Thread.sleep(1000);
			}
		}
	}

}
