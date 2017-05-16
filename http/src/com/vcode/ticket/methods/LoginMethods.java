package com.vcode.ticket.methods;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import org.json.JSONException;
import org.json.JSONObject;

import com.vcode.http.client.VHttpResponse;
import com.vcode.http.client.methods.VHttpGet;
import com.vcode.http.client.methods.VHttpPost;
import com.vcode.http.client.parames.VParames;
import com.vcode.http.utils.VBrowser;
import com.vcode.http.utils.VHttpUtils;
import com.vcode.ticket.ui.HomePage;
import com.vcode.ticket.ui.LoginPage;
import com.vcode.ticket.utils.ConfigUtils;

/**
 * 登录界面的逻辑和方法
 * @author Administrator
 *
 */
public class LoginMethods {
	
	private LoginPage login_page;
	
	private String newCode = "";
	
	public LoginMethods(LoginPage login_page){
		this.login_page = login_page;
	}

	/**
	 * 登录界面，用于获取cookie
	 */
	public static void ticket_init(){
		VHttpGet get = new VHttpGet("https://kyfw.12306.cn/otn/login/init");
		VHttpResponse res = VBrowser.execute(get);	
		res.getEntity().disconnect();							
	}
	
	/**
	 * 获取登录界面验证码
	 * @return
	 */
	public static byte[] getLoginCode(){
		VHttpGet get = new VHttpGet("https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew?module=login&rand=sjrand&"+Math.random());
		VHttpResponse res = VBrowser.execute(get);								//获取验证码
		return VHttpUtils.InputStreamToByte(res.getBody());		
	}
	
	/**
	 * 表单校验
	 * @return
	 */
	private boolean IsChoiceCode(){
		if ("".equals(login_page.txtHao.getText())) {
			login_page.lblNewLabel_2.setText("提示：请填写用户名");
			login_page.lblNewLabel_2.setForeground(Color.red);
			return false;
		}
		if (login_page.passwordField.getPassword().length==0) {
			login_page.lblNewLabel_2.setText("提示：请填写密码");
			login_page.lblNewLabel_2.setForeground(Color.red);
			return false;
		}
		JComponent p3 = (JComponent)login_page.frame.getLayeredPane();
		if (p3.getComponents().length<=0) {
			login_page.lblNewLabel_2.setText("提示：请选择验证码");
			login_page.lblNewLabel_2.setForeground(Color.red);
			return false;
		}
		return true;
	}
	
	/**
	 * 验证验证码是否正确
	 */
	public void CheckCode(){
		
		if (!IsChoiceCode()) {
			return;
		}
		JComponent p3 = (JComponent)login_page.frame.getLayeredPane();
		Component[] cons = p3.getComponents();
		for (int i=0;i<cons.length;i++) {
			if (cons[i] instanceof JLabel) {
				JLabel lb = (JLabel)cons[i];
				newCode += lb.getX()-64+(lb.getIcon().getIconWidth()/2) + "," ;
				newCode += lb.getY()-179+(lb.getIcon().getIconHeight()/2) + "";
				if (i<cons.length-1) {
					newCode += ",";
				}
			}
		}
		login_page.lblNewLabel_2.setText("当前验证码是："+newCode);
		
		VHttpPost post = new VHttpPost("https://kyfw.12306.cn/otn/passcodeNew/checkRandCodeAnsyn");
		VParames parames = new VParames();
		parames.put("rand", "sjrand");
		parames.put("randCode", newCode);
		post.setParames(parames);							//装配提交的Form
		VHttpResponse res = VBrowser.execute(post);
		String body = VHttpUtils.outHtml(res.getBody());		//将网页内容转为文本
		try {
			JSONObject json = new JSONObject(body);
			JSONObject json2 = new JSONObject(json.get("data").toString());
			if ("1".equals(json2.get("result"))) {
				login_page.lblNewLabel_2.setText("验证码正确，开始提交表单");
				res.getEntity().disconnect();
				login();
			}else {
				login_page.lblNewLabel_2.setText("提示：验证码错误");
				login_page.lblNewLabel_2.setForeground(Color.red);
				res.getEntity().disconnect();
				login_page.lblNewLabel_1.setIcon(new ImageIcon(getLoginCode()));
				clearCode();
			}
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
	}
	
	private void login(){
		//开始提交登录信息
		VHttpPost post = new VHttpPost("https://kyfw.12306.cn/otn/login/loginAysnSuggest");
		VParames parames = new VParames();
		parames.put("loginUserDTO.user_name", login_page.txtHao.getText());
		parames.put("randCode", newCode);
		parames.put("userDTO.password", new String(login_page.passwordField.getPassword()));
		
		post.setParames(parames);
		VHttpResponse res = VBrowser.execute(post);
		String body = VHttpUtils.outHtml(res.getBody());			//将网页内容转为文本
		try {
			JSONObject json = new JSONObject(body);
			if ("true".equals(json.get("status").toString())) {
				JSONObject json2 = new JSONObject(json.get("data").toString());
				if (json2.length()>1 && "Y".equals(json2.get("loginCheck").toString())) {
					login_page.lblNewLabel_2.setText("登录成功，正在跳转到主页");
				}else {
					System.out.println(json.get("messages"));
					System.exit(0);
				}
			}else {
				System.out.println(json.get("messages"));
				System.exit(0);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		res.getEntity().disconnect();
		
		//开始第二次登录
		post = new VHttpPost("https://kyfw.12306.cn/otn/login/userLogin");
		VParames parames2 = new VParames();
		parames2.put("_json_att", "");
		post.setParames(parames2);								//装配参数
		res = VBrowser.execute(post);								//提交登录
		if (res.getEntity().getStaus()==200){
			if (VHttpUtils.outHtml(res.getBody()).contains("欢迎您登录中国铁路客户服务中心网站")){		//验证是否登录成功
				login_page.lblNewLabel_2.setText("登录成功");
				
				//处理记住密码
				boolean check = login_page.checkBox.isSelected();
				String[] str = new String[]{login_page.txtHao.getText(),new String(login_page.passwordField.getPassword())};
				if (check){
					try {
						ConfigUtils.getInstace().rememberPass(str);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else {
					try {
						ConfigUtils.getInstace().removePass(str);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				HomePage window = new HomePage();
				window.printLog("登录成功,欢迎使用V代码抢票工具");
				window.printLog("双击车次即可提交订单哦,右击可将车次加入自动刷票的预选车次中,更多隐藏功能等你发现！");
				login_page.frame.dispose();
				window.show(window);
			}else {
				login_page.lblNewLabel_2.setText("提示：登录失败");
				login_page.lblNewLabel_2.setForeground(Color.red);
			}
			res.getEntity().disconnect();
		}
	}
	
	/**
	 * 
	 * 清空验证码
	 * 
	 */
	public void clearCode(){
		newCode = "";
		JComponent p3 = (JComponent)login_page.frame.getLayeredPane();
		Component[] cons1 = p3.getComponents();
		for (Component con : cons1) {
			if (con instanceof JLabel) {
				p3.remove(con);
			}
		}
		login_page.frame.repaint(); 
	}
}
