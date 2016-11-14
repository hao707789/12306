package com.vcode.ticket.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileReader;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.vcode.http.client.VHttpClient;
import com.vcode.http.client.VHttpResponse;
import com.vcode.http.client.methods.VHttpGet;
import com.vcode.http.impl.VHttpClientImpl;
import com.vcode.http.utils.VBrowser;
import com.vcode.http.utils.VHttpUtils;
import com.vcode.ticket.methods.HomeMethods;
import com.vcode.ticket.ui.Login_Page;

/**
 * 
 * @author 默非默
 *
 */
public class HttpUtils {
	
	/**
	 * 12306专用验证码(提交订单用)
	 * @param in
	 * @param x
	 * @param y
	 */
	public static void getSubmitCodeBy12306(InputStream in,final HomeMethods order_oage) {
		byte[] data = VHttpUtils.InputStreamToByte(in);
		
		final JFrame frame2 = new JFrame("验证码");
		frame2.setSize(new Dimension(387, 455));
		frame2.setLocationRelativeTo(null);
		frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame2.getContentPane().setLayout(null);

		final JLabel label_1 = new JLabel("当前点击坐标是：");
		label_1.setBounds(43, 266, 284, 23);
		frame2.getContentPane().add(label_1);
		
		final JLabel label = new JLabel("");
		label.setBounds(43, 68, 284, 196);
		label.setIcon(new ImageIcon(data));
		
		final JComponent p3 = (JComponent) frame2.getLayeredPane();
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==e.BUTTON1) {
					ImageIcon icon = new ImageIcon(Login_Page.class.getResource("../image/12306.jpg"));
					final JLabel jb3 = new JLabel(icon);
					jb3.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							p3.remove(jb3);
							p3.repaint();
						}
					});
					p3.add(jb3, new Integer(-3)); // 将按钮jb3，放置在内容面板之下
					jb3.setSize(icon.getIconWidth(), icon.getIconHeight());
					jb3.setLocation(e.getX()+43-(icon.getIconWidth()/2), e.getY()+86-(icon.getIconHeight()));
				}else if(e.getButton() == MouseEvent.BUTTON3){
					Component[] coms = p3.getComponents();
					if (coms.length<=0) {
						label_1.setText("未选择任何验证码，不进行提交");
						label_1.setForeground(Color.red);
					} else {
						frame2.dispose();
						String code = "";
						for (int i=0;i<coms.length;i++) {
							if (coms[i] instanceof JLabel) {
								JLabel lb = (JLabel)coms[i];
								code += lb.getX()-64+(lb.getIcon().getIconWidth()/2) + "," ;
								code += lb.getY()-179+(lb.getIcon().getIconHeight()/2) + "";
								if (i<coms.length-1) {
									code += ",";
								}
							}
						}
						order_oage.checkSubmitCode(code);
					}
                }
			}
		});
		
		frame2.getContentPane().add(label);

		JButton button = new JButton("提交");
		button.setBounds(43, 354, 284, 53);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 回调方法
				Component[] coms = p3.getComponents();
				if (coms.length<=0) {
					label_1.setText("未选择任何验证码，不进行提交");
					label_1.setForeground(Color.red);
				} else {
					frame2.dispose();
					String code = "";
					for (int i=0;i<coms.length;i++) {
						if (coms[i] instanceof JLabel) {
							JLabel lb = (JLabel)coms[i];
							code += lb.getX()-48+(lb.getIcon().getIconWidth()/2) + "," ;
							code += lb.getY()-113+(lb.getIcon().getIconHeight()/2) + "";
							if (i<coms.length-1) {
								code += ",";
							}
						}
					}
					order_oage.checkSubmitCode(code);
				}
			}
		});
		frame2.getContentPane().add(button);

		JButton button_1 = new JButton("眼瞎了...看不清");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String url = "https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew?module=passenger&rand=randp&"
						+ Math.random();
				VHttpGet get = new VHttpGet(url);
				VHttpResponse res = VBrowser.execute(get); // 获取验证码
				byte[] data = VHttpUtils.InputStreamToByte(res.getBody());
				label.setIcon(new ImageIcon(data));
			}
		});
		button_1.setBounds(43, 316, 284, 35);
		frame2.getContentPane().add(button_1);

		JLabel lblt = new JLabel("车票信息：");
		lblt.setBounds(43, 10, 88, 23);
		lblt.setFont(new Font("微软雅黑", Font.BOLD, 15));
		frame2.getContentPane().add(lblt);

		String userName = order_oage.home_page.list_3.getModel().getElementAt(0).toString();
		JLabel order_name = new JLabel(userName);
		order_name.setBounds(43, 43, 50, 15);
		order_name.setFont(new Font("微软雅黑", Font.BOLD, 12));
		frame2.getContentPane().add(order_name);

		JLabel order_train_no = null;
		try {
			order_train_no = new JLabel(order_oage.obj2.get("station_train_code").toString());
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		order_train_no.setBounds(108, 43, 50, 15);
		order_train_no.setFont(new Font("微软雅黑", Font.BOLD, 12));
		frame2.getContentPane().add(order_train_no);

		JLabel order_citi = new JLabel(order_oage.home_page.textField.getText()+"—>"+order_oage.home_page.textField_3.getText());
		order_citi.setBounds(168, 43, 88, 15);
		order_citi.setFont(new Font("微软雅黑", Font.BOLD, 12));
		frame2.getContentPane().add(order_citi);

		String seatType = order_oage.home_page.list_2.getModel().getElementAt(0).toString();
		JLabel order_seat = new JLabel(seatType);
		order_seat.setBounds(260, 43, 50, 15);
		order_seat.setFont(new Font("微软雅黑", Font.BOLD, 12));
		frame2.getContentPane().add(order_seat);

		JLabel label_5 = new JLabel("选择完验证码后试试右键即可完成提交");
		label_5.setBounds(43, 290, 284, 23);
		label_5.setForeground(Color.RED);
		frame2.getContentPane().add(label_5);
		frame2.setVisible(true);
	}
	
	/**
	 * 获取城市信息
	 * @return
	 */
	public static Map<String, String[]> getCitiInfo() {
		Map<String, String[]> map = new LinkedHashMap<String, String[]>();
		VHttpClient client = new VHttpClientImpl();
		VHttpGet get = new VHttpGet("https://kyfw.12306.cn/otn/resources/js/framework/station_name.js");
		VHttpResponse res = client.execute(get);
		String data = VHttpUtils.outHtml(res.getBody()).split("=")[1];
		res.getEntity().disconnect();
		String[] citiArr = data.replace("'", "").split("@");
		for (String s : citiArr){
			String[] citiInfo = s.split("\\|");
			if (citiInfo.length>1){
				map.put(citiInfo[1], citiInfo);
			}
		}
		return map;
	}
	
	
	/**
	 * 获取热门城市信息
	 * @return
	 */
	public static Map<String, String[]> getHotCitiInfo() {
		Map<String, String[]> map = new LinkedHashMap<String, String[]>();
		VHttpClient client = new VHttpClientImpl();
		VHttpGet get = new VHttpGet("https://kyfw.12306.cn/otn/resources/js/framework/favorite_name.js");
		VHttpResponse res = client.execute(get);
		String data = VHttpUtils.outHtml(res.getBody()).split("=")[1];
		res.getEntity().disconnect();
		String[] citiArr = data.replace("'", "").split("@");
		for (String s : citiArr){
			String[] citiInfo = s.split("\\|");
			if (citiInfo.length>1){
				map.put(citiInfo[1], citiInfo);
			}
		}
		return map;
	}
	
	
	/**
	 * 得到余票数量
	 * @param ticket
	 * @param seatNum
	 * @return
	 */
	public static String getCountByJs(String ticket, String seatNum){
		ScriptEngineManager manager = new ScriptEngineManager();   
		ScriptEngine engine = manager.getEngineByName("javascript");
		String jsFileName = HttpUtils.class.getResource(".").getFile().toString()+"\\getCount.js";   // 读取js文件 
		String rs = "";
		try {
			FileReader reader = new FileReader(jsFileName);   // 执行指定脚本   
			engine.eval(reader);
			if(engine instanceof Invocable) {    
				Invocable invoke = (Invocable)engine;    // 调用merge方法，并传入两个参数    
			rs = (String)invoke.invokeFunction("L", ticket, seatNum);
		}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}    
		return rs;
  	}
	
	/**
	 * 获取座位编号
	 * @param userObj
	 * @param seatTypes
	 * @param station_train_code
	 * @return
	 */
	public static String getPassengerTicketStr(JSONObject userObj,String[] seatTypes,String station_train_code){
		String seatTypeCode = "";
		String passengerTicketStr = "";
		try {
			if (station_train_code.toUpperCase().startsWith("G")) {
				for (String seatType : seatTypes) {
					if (seatType.contains("商务座")) {
						seatTypeCode = "9";
					}else if (seatType.contains("一等座")){
						seatTypeCode = "M";
					}else if (seatType.contains("二等座")){
						seatTypeCode = "o";
					}
					if (!"".equals(seatType)) {
						break;
					}
				}
			}else if (station_train_code.toUpperCase().startsWith("C") || station_train_code.toUpperCase().startsWith("D")){
				for (String seatType : seatTypes) {
					if (seatType.contains("一等座")){
						seatTypeCode = "M";
					}else if (seatType.contains("二等座")){
						seatTypeCode = "O";
					}
					if (!"".equals(seatType)) {
						break;
					}
				}
			}else if (station_train_code.toUpperCase().startsWith("Z") || station_train_code.toUpperCase().startsWith("T") 
						|| station_train_code.toUpperCase().startsWith("K")) {
				for (String seatType : seatTypes) {
					if (seatType.contains("软卧")){
						seatTypeCode = "4";
					}else if (seatType.contains("硬卧")){
						seatTypeCode = "3";
					}else if (seatType.contains("硬座")){
						seatTypeCode = "1";
					}
					if (!"".equals(seatType)) {
						break;
					}
				}
			}
			passengerTicketStr = seatTypeCode+",0,1," + userObj.getString("passenger_name") + ",1,"
					+ userObj.getString("passenger_id_no") + ","
					+ userObj.getString("mobile_no") + ",N";
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return passengerTicketStr;
	}
	
	/**
	 * 转换座位编码
	 * @param seatNum
	 * @return
	 */
	public static String seatNumToseatType(String seatNum){
		String seatType = "";
		if ("1".equals(seatNum.toUpperCase())) {
			seatType = "硬座";
		}else if ("3".equals(seatNum.toUpperCase())) {
			seatType =  "硬卧";
		}else if ("4".equals(seatNum.toUpperCase())) {
			seatType =  "软卧";
		}else if ("O".equals(seatNum.toUpperCase())) {
			seatType =  "二等座";
		}else if ("M".equals(seatNum.toUpperCase())) {
			seatType =  "一等座";
		}else if ("9".equals(seatNum.toUpperCase())) {
			seatType =  "商务座";
		}
		return seatType;
	}
	
	/**
	 * 读取conf文件
	 * @param note
	 */
	public static void readConf(String note){
		
	}
	
	/**
	 * 删除某节点
	 * @param note
	 */
	public static void delConfNote(String note){
		
	}
	
	/**
	 * 修改节点内容
	 * @param note
	 */
	public static void updateNote(String note){
		
	}
	
	/**
	 * 增加节点内容
	 * @param note
	 */
	public static void addConfNote(String note){
		
	}
	
	public static void main(String[] args) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(HttpUtils.class.getResource("../conf/xconf.xml").toString());
		Element root = document.getDocumentElement();
		NodeList nodelist = root.getElementsByTagName("citys");
		for (int i=0;i<nodelist.getLength();i++) {
			Node node = nodelist.item(i);
			NodeList citylist = node.getChildNodes();
			for (int j=0;j<citylist.getLength();j++) {
				Node cityNode = citylist.item(j);
				System.out.println(cityNode.getNodeName());
				System.out.println(cityNode.getTextContent());
			}
		}
		System.out.println(root.getTagName());
	}
}
