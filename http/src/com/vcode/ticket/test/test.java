package com.vcode.ticket.test;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class test {

	public static void main(String[] args) throws Exception {
		JFrame frame = new JFrame();
		ImageIcon bg = new ImageIcon("d:/Desert.jpg");
		JLabel label = new JLabel(bg);
		label.setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());
		frame.getLayeredPane().add(label);
		frame.setSize(new Dimension(bg.getIconWidth(), bg.getIconHeight()));
		
		JPanel jp = (JPanel) frame.getContentPane();
		jp.setOpaque(true);
		frame.getContentPane().setLayout(null);
		JLabel label_1 = new JLabel("1111");
		label_1.setBounds(279, 196, 100, 100);
		jp.add(label_1);
		frame.setVisible(true);
	}
}
