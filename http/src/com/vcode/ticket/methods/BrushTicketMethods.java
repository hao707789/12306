package com.vcode.ticket.methods;

import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vcode.http.client.VHttpResponse;
import com.vcode.http.client.methods.VHttpGet;
import com.vcode.http.utils.VBrowser;
import com.vcode.http.utils.VHttpUtils;
import com.vcode.ticket.ui.Home_Page;

public class BrushTicketMethods extends Thread{
	
	private Home_Page home_page;

	public BrushTicketMethods(Home_Page home_page) {
		this.home_page = home_page;
	}

	@Override
	public void run() {
		int num = 0;
		Thread.currentThread().setName("3");
		while (home_page.result) {
			num+=1;
			if (home_page.ticket_type==1) {
				home_page.printLog("线程"+Thread.currentThread().getName()+"正在开始第"+num+"次查询");
			}
			brushVotes();
			try {
				Thread.sleep(Integer.parseInt(home_page.spinner.getValue().toString()));
			} catch (InterruptedException e) {
				Thread.interrupted();
				e.printStackTrace();
			};
		}
	}
	
	/**
	 * 刷票，点击查票按钮后，开始查询车票信息，并填入到表格中
	 * 验证出发地、目的地、时间等是否填写，没有则不刷票
	 * 
	 */
	private void brushVotes() {
		// 开始刷票
		VHttpGet get = new VHttpGet(
				"https://kyfw.12306.cn/otn/leftTicket/queryX?" + home_page.sb);
		VHttpResponse res = VBrowser.execute(get);
		String body = VHttpUtils.outHtml(res.getBody());
		try {
			disposeTicketInfo(body,home_page.seatOthers);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 处理刷票返回信息
	 */
	private boolean disposeTicketInfo(String body,String[] seatOther) throws JSONException {
		boolean Brush = true;
		JSONObject json = new JSONObject(body);
		JSONArray jsonArr = new JSONArray(json.get("data").toString());
		home_page.table.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
				"\u8F66\u6B21", "\u51FA\u53D1\u5730", "\u76EE\u7684\u5730",
				"\u5386\u65F6", "\u53D1\u8F66\u65F6\u95F4",
				"\u5230\u8FBE\u65F6\u95F4", "\u5546\u52A1", "\u7279\u7B49",
				"\u4E00\u7B49", "\u4E8C\u7B49", "\u9AD8\u8F6F", "\u8F6F\u5367",
				"\u786C\u5367", "\u8F6F\u5EA7", "\u786C\u5EA7", "\u65E0\u5EA7",
				"\u5176\u5B83", "\u5907\u6CE8" }) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		home_page.datalist.clear();
		for (int i = 0; i < jsonArr.length(); i++) {
			JSONObject obj = (JSONObject) jsonArr.get(i);
			JSONObject obj2 = new JSONObject(obj.get("queryLeftNewDTO")
					.toString());
			obj2.put("secretStr", obj.get("secretStr").toString());
			
			flag:
			if (home_page.ticket_type==1 && Brush) {
				for (int j=0;j<home_page.model_train.getSize();j++) {
					String train_no = home_page.model_train.get(j).toString();
					if (obj2.get("station_train_code").toString().trim().equalsIgnoreCase(train_no.trim())) {
						for (String seat : seatOther) {
							if (!"--".equals(obj2.get(seat).toString().trim().toUpperCase())) {
								Brush = false;		//结束刷票结果判定
								home_page.result = false;		//结束循环刷票判定
								home_page.isRun = false;		//结束运行判断
								home_page.btnNewButton.setText("自动刷票");
								new HomeMethods(home_page.window,obj2).start();
								break flag;
							}
						}
					}
				}
				
			}
			if (home_page.ticket_type==0) {
				home_page.result = false;
			}
			home_page.datalist.add(obj2);
			home_page.addRow(obj2);
		}
		home_page.setTableSize();
		return home_page.result;
	}

}
