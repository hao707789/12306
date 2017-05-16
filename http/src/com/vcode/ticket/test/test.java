package com.vcode.ticket.test;

import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;

import javax.swing.JFrame;


public class test {

	public static void main(String[] args) throws Exception {
		/*JFrame frame = new JFrame();
		frame.setSize(300, 200);
		frame.setLocationRelativeTo(null);
		
		SystemTray tray = SystemTray.getSystemTray();
		Image image=Toolkit.getDefaultToolkit().getImage(test.class.getResource("t.jpg"));
		TrayIcon trayIcon = new TrayIcon(image,"V代码抢票工具",null);
		trayIcon.displayMessage("提示", "欢迎使用V代码抢票工具", MessageType.INFO);
		frame.setVisible(true);*/
		String[] ss = ("m3%2BI6H52cSM7ysxtK9gTvWmCpAdqTqBTp0BgIfIpnESg3NhXj178O%2F5U5L4r%2FRWttSuVBLF83NJF%0AQPOd2%2BF1rKZ1r%2FW%2BCaQ7kbo9I15o7YL2UUpietR6X6xlBJooHKBil6JHs5vuQBbOHf8LBVhIbWH%2F%0ADiUpGXgvyODp22uVriuHqME3IGa4iTJr8G5lh2kGYd23YuiTqocEvc0NB%2FtVCBTVstNraverraTF%0AHFDy6VjILQ0P|预订|6i000G103206|G1032|IOQ|HAN|IOQ|XRN|12:39|17:16|04:37|Y|%2Fd75N3DS7BOQ4mBDFRQYcwNRW1qiprspBzw3m9DVNP6lmGb3|20170531|3|Q6|01|11|1|0|||||||||||有|有|8|O0M090|OM9"+
	            "|预订|6i0000G55403|G554|IOQ|LBN|IOQ|XRN|13:29|18:28|04:59|N|kpLkLhxelQLn8IwBBIciE723u9duhBq9R9O1anxR1YE7QM3x|20170531|3|Q9|01|10|1|0|||||||||||无|无|无|O0M090|OM9").split("\\|");
		for (String s : ss){
			if (!"".equals(s.trim())){
				System.out.println(s);
			}
		}
	}
}
