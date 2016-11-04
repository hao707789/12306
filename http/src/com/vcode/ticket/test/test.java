package com.vcode.ticket.test;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JCheckBox;
import javax.swing.JFrame;

public class test {

	public test() {
	}

	public static void main(String[] args) throws Exception {
		final StringBuffer incode = new StringBuffer();
		InputStream in = new FileInputStream(new File("d:/123.png"));
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
		frame2.setSize(new Dimension(387, 455));
		frame2.setLocationRelativeTo(null);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.getContentPane().setLayout(null);
		
		
		final JCheckBox checkBox = new JCheckBox("车二");
		checkBox.setBounds(23, 64, 103, 23);
		frame2.getContentPane().add(checkBox);
		
		final JCheckBox checkBox_1 = new JCheckBox("车三");
		checkBox_1.setBounds(23, 89, 103, 23);
		frame2.getContentPane().add(checkBox_1);
		
		final JCheckBox checkBox_2 = new JCheckBox("车四");
		checkBox_2.setBounds(23, 114, 103, 23);
		frame2.getContentPane().add(checkBox_2);
		
		final JCheckBox chckbxNewCheckBox = new JCheckBox("全部");
		chckbxNewCheckBox.setBounds(23, 39, 103, 23);
		frame2.getContentPane().add(chckbxNewCheckBox);
		chckbxNewCheckBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (chckbxNewCheckBox.isSelected()) {
					Component[] comps = frame2.getContentPane().getComponents();
					for (int i=0;i<comps.length;i++) {
						Component comp = comps[i];
						if(comp instanceof JCheckBox){
						    JCheckBox box = (JCheckBox)comp;
						    box.setSelected(true);
						}
					}
				}else {
					Component[] comps = frame2.getContentPane().getComponents();
					for (int i=0;i<comps.length;i++) {
						Component comp = comps[i];
						if(comp instanceof JCheckBox){
						    JCheckBox box = (JCheckBox)comp;
						    box.setSelected(false);
						}
					}
				}
			}
		});
		frame2.setVisible(true);
	}
}
