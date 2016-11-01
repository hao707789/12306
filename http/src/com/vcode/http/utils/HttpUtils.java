package com.vcode.http.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import test.t_12306.Home_Page;

import com.vcode.http.client.VHttpClient;
import com.vcode.http.client.VHttpResponse;
import com.vcode.http.client.methods.VHttpGet;
import com.vcode.http.impl.VHttpClientImpl;

/**
 * 
 * @author 默非默
 *
 */
public class HttpUtils {
	
	public static StringBuffer incode = new StringBuffer();
	
	/**
	 * 将网页返回的结果封装成字符串
	 * @param in
	 * @return
	 */
	public static String outHtml(InputStream in){
		StringBuffer result = new StringBuffer() ;
		try  {
			InputStreamReader inr = new InputStreamReader(in);
			BufferedReader br = new  BufferedReader(inr);  
	        String line ;  
	        while ( (line =br.readLine()) !=  null  ){
	        	result.append(line);
	        }  
	        br.close();  
		}catch(IOException e){
			e.printStackTrace();
		}
		return result.toString();
	}
	
	/**
	 * 将网页返回的结果封装成字符串
	 * @param in
	 * @return
	 */
	public static String outHtmlByLine(InputStream in){
		StringBuffer result = new StringBuffer() ;
		try  {
			InputStreamReader inr = new InputStreamReader(in);
			BufferedReader br = new  BufferedReader(inr);  
	        String line ;  
	        while ( (line =br.readLine()) !=  null  ){
	        	result.append(line+"/r/n");
	        }  
	        br.close();  
		}catch(IOException e){
			e.printStackTrace();
		}
		return result.toString();
	}
	
	/**
	 * 打印网页响应内容并返回字符串结果
	 * @param in
	 * @return
	 */
	public static String outHtmlAndPrint(InputStream in){
		StringBuffer result = new StringBuffer() ;
		try  {
			InputStreamReader inr = new InputStreamReader(in);
			BufferedReader br = new  BufferedReader(inr);  
	        String line ;  
	        while ( (line =br.readLine()) !=  null  ){
	        	result.append(line);
	        }  
	        br.close();  
		}catch(IOException e){
			e.printStackTrace();
		}
		return result.toString();
	}
	
	/**
	 * 打印网页内容
	 * @param in
	 */
	public static void PrintHtml(InputStream in){
		try  {
			InputStreamReader inr = new InputStreamReader(in);
			BufferedReader br = new  BufferedReader(inr);  
	        String line ;  
	        while ( (line =br.readLine()) !=  null  ){
	        	System.out.println(line);
	        }  
	        br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 打印网页内容（分行）
	 * @param in
	 */
	public static void PrintHtmlByLine(InputStream in){
		try  {
			InputStreamReader inr = new InputStreamReader(in);
			BufferedReader br = new  BufferedReader(inr);  
	        String line ;  
	        while ( (line =br.readLine()) !=  null  ){
	        	System.out.println(line+"/r/n");
	        }  
	        br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 12306专用验证码(登录用)
	 * @param in
	 * @return
	 */
	public static String outCodeBy12306(InputStream in){
		return getCodeBy12306(in, 0, 0);
	}
	
	/**
	 * 12306专用验证码(提交订单用)
	 * @param in
	 * @param x
	 * @param y
	 */
	public static void getSubmitCodeBy12306(InputStream in,final Home_Page home) {
		incode.setLength(0);
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		byte[] data = null;
		try {
			while ((len = in.read(buffer)) != -1) {
				// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
				outStream.write(buffer, 0, len);
			}
			in.close();
			data = outStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		final JFrame frame2 = new JFrame("验证码");
		frame2.setSize(new Dimension(302, 268));
		frame2.setLocationRelativeTo(null);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(data));
		label.setSize(new Dimension(284, 196));
		frame2.getContentPane().add(label);
		
		final JLabel label_1 = new JLabel("当前点击坐标是：");
		label_1.setBounds(10, 195, 184, 35);
		frame2.getContentPane().add(label_1);
		
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1){ //e.getButton就会返回点鼠标的那个键，左键还是右健，3代表右键
                  int x = e.getX();  //得到鼠标x坐标
                  int y = e.getY();  //得到鼠标y坐标
                  String banner = x + "," + (y-33) + ",";
                  incode.append(banner);
                  label_1.setText("当前点击坐标是：" + x + "," + (y-33));
                  label_1.setForeground(Color.black);
              }
			}
		});
		
		JButton button = new JButton("提交");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//回调方法
				if ("".equals(incode.toString())) {
					label_1.setText("未选择任何验证码，不进行提交");
					label_1.setForeground(Color.red);
				}else {
					frame2.dispose();
					home.checkSubmitCode();
				}
			}
		});
		button.setBounds(214, 200, 62, 25);
		frame2.getContentPane().add(button);
		frame2.setVisible(true);
	}
	
	/**
	 * 12306登录使用
	 * @param in
	 * @param x
	 * @param y
	 * @return
	 */
	public static String getCodeBy12306(InputStream in, int x,int y) {
		StringBuffer sb = new StringBuffer("true");
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		byte[] data = null;
		try {
			while ((len = in.read(buffer)) != -1) {
				// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
				outStream.write(buffer, 0, len);
			}
			in.close();
			data = outStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		final JFrame frame2 = new JFrame("验证码");
		frame2.setSize(new Dimension(302, 268));
		frame2.setLocationRelativeTo(null);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("d:/123.png"));
		label.setSize(new Dimension(284, 196));
		frame2.getContentPane().add(label);
		
		final JLabel label_1 = new JLabel("当前点击坐标是：");
		label_1.setBounds(10, 195, 184, 35);
		frame2.getContentPane().add(label_1);
		
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1){ //e.getButton就会返回点鼠标的那个键，左键还是右健，3代表右键
                  int x = e.getX();  //得到鼠标x坐标
                  int y = e.getY();  //得到鼠标y坐标
                  String banner = x + "," + (y-65) + ",";
                  incode.append(banner);
                  label_1.setText("当前点击坐标是：" + x + "," + (y-65));
              }
			}
		});
		
		JButton button = new JButton("提交");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//回调方法
				
				frame2.dispose();
			}
		});
		button.setBounds(214, 200, 62, 25);
		frame2.getContentPane().add(button);
		frame2.setVisible(true);
		
		while ("true".equals(sb.toString())){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		return sb.substring(0, sb.length()-1);
	}
	
	/**
	 * 返回验证码字节数组
	 * @param in
	 * @return
	 */
	public static byte[] outCode(InputStream in){
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		byte[] data = null;
		try {
			while ((len = in.read(buffer)) != -1) {
				// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
				outStream.write(buffer, 0, len);
			}
			in.close();
			data = outStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	
	/**
	 * 针对普通验证码方式
	 * @param in	验证码的流
	 * @param x		展示验证码的窗口的长
	 * @param y		展示验证码的窗口的宽
	 * @return		验证码
	 * @wbp.parser.entryPoint
	 */
	public static String outCode(InputStream in, int x,int y) {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		byte[] data = null;
		try {
			while ((len = in.read(buffer)) != -1) {
				// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
				outStream.write(buffer, 0, len);
			}
			in.close();
			data = outStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		final JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(false);
		frame.setBounds(x==0?307:x, y==0?266:y,x==0?307:x, y==0?266:y);
		frame.getContentPane().setLayout(new FlowLayout());

		final StringBuffer incode = new StringBuffer();
		ImageIcon icon = new ImageIcon(data);
		frame.getContentPane().add(new JLabel(icon));
		frame.setVisible(true);
		

		System.out.print("输入你看到的验证码：");
		Scanner scr = new Scanner(System.in);

		String code = scr.nextLine();
		System.out.println("验证码 : " + code);
		return code;
	}

	/**
	 * 打印Cookie
	 * @param res
	 */
	public static void PrintCookies(VHttpResponse res){
		List<HttpCookie> cookies = res.getCookies();
		System.out.println("==================Cookies打印开始=========================");
		for (HttpCookie cookie : cookies){
			System.out.println(cookie.getName()+"==="+cookie.getValue());
		}
		System.out.println("==================Cookies打印结束=========================");
	}
	
	/**
	 * 获取城市信息
	 * @return
	 */
	public static Map<String, String> getCitiInfo() {
		Map<String, String> map = new HashMap<String, String>();
		VHttpClient client = new VHttpClientImpl();
		VHttpGet get = new VHttpGet("https://kyfw.12306.cn/otn/resources/js/framework/station_name.js");
		VHttpResponse res = client.execute(get);
		String data = outHtml(res.getBody()).split("=")[1];
		res.getEntity().disconnect();
		String[] citiArr = data.replace("'", "").split("@");
		for (String s : citiArr){
			String[] citiInfo = s.split("\\|");
			if (citiInfo.length>1){
				map.put(citiInfo[1], citiInfo[2]);
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
	
	public static void main(String[] args) throws FileNotFoundException {
		outCodeBy12306(new FileInputStream(new File("d:/123.png")));
	}
}
