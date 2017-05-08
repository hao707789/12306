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
		String[] ss = "aGFvNzA3Nzg5|bG92ZVhJQU83MDc3ODk=".split("\\|");
		System.out.println(ss[0]);
	}
}
