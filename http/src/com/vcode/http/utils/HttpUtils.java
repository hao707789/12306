package com.vcode.http.utils;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
import javax.swing.JPanel;

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
	 * 针对12306验证码
	 * @param in	验证码的流
	 * @param x		展示验证码的窗口的长
	 * @param y		展示验证码的窗口的宽
	 * @return		验证码
	 */
	public static String outCodeBy12306(InputStream in, int x,int y) {
		final StringBuffer sb = new StringBuffer("true");
		File imageFile = new File("D:/YZM.jpg");
		if (imageFile.exists()) imageFile.delete();
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
		frame.setLayout(new FlowLayout());

		final StringBuffer incode = new StringBuffer();
		ImageIcon icon = new ImageIcon(data);
		frame.add(new JLabel(icon));
		
		final JLabel lable = new JLabel("鼠标当前点击位置的坐标是：");
		JButton bt = new JButton("提交");
		frame.add(lable);
		frame.add(bt);
		frame.setVisible(true);
		frame.addMouseListener(new MouseAdapter(){  //匿名内部类，鼠标事件
            public void mouseClicked(MouseEvent e){   //鼠标完成点击事件
                if(e.getButton() == MouseEvent.BUTTON1){ //e.getButton就会返回点鼠标的那个键，左键还是右健，3代表右键
                    int x = e.getX();  //得到鼠标x坐标
                    int y = e.getY();  //得到鼠标y坐标
                    String banner = x + "," + (y-65) + ",";
                    incode.append(banner);
                    lable.setText("鼠标当前点击位置的坐标是" + x + "," + (y-65));
                }
            }
        });
		bt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sb.append("2");
				frame.dispose();
			}
		});
		
		while ("true".equals(sb.toString())) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		String rs = incode.toString();
		rs = rs.substring(0,rs.length()-1);
		System.out.println("验证码 : " + rs);
		return incode.toString();
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
		frame.setLayout(new FlowLayout());

		final StringBuffer incode = new StringBuffer();
		ImageIcon icon = new ImageIcon(data);
		frame.add(new JLabel(icon));
		frame.setVisible(true);
		

		System.out.print("输入你看到的验证码：");
		Scanner scr = new Scanner(System.in);

		String code = scr.nextLine();
		System.out.println("验证码 : " + code);
		return code;
	}
	
	
	/**
	 * 12306专用验证码
	 * @param in
	 * @return
	 */
	public static String outCodeBy12306(InputStream in){
		return outCodeBy12306(in, 0, 0);
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
}
