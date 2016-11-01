package com.vcode.http.utils;

import javax.swing.JTextArea;


public class JTextAreaExt extends JTextArea {

	@Override
	public void append(String str) {
		super.append(str);
		super.paintImmediately(super.getBounds());
	}

	
}
