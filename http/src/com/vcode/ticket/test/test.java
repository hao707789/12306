package com.vcode.ticket.test;

import com.vcode.ticket.utils.HttpUtils;


public class test {

	public static void main(String[] args) throws Exception {
		String data = HttpUtils.getCountByJs("1002453456401085000810024506723007050034", "3");
		System.out.println(data);
	}
}
