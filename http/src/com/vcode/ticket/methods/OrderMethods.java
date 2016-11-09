package com.vcode.ticket.methods;

import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vcode.http.client.VHttpResponse;
import com.vcode.http.client.methods.VHttpPost;
import com.vcode.http.client.parames.VParames;
import com.vcode.http.utils.VBrowser;
import com.vcode.http.utils.VHttpUtils;
import com.vcode.ticket.ui.Home_Page;

public class OrderMethods {

	/**
	 * 查询订单列表
	 */
	public static void getOrderList(JButton btnNewButton_1,Home_Page home_page) {

		try {
			VHttpPost post = new VHttpPost(
					"https://kyfw.12306.cn/otn/queryOrder/queryMyOrderNoComplete");
			VParames parames = new VParames();
			parames.clear();
			parames.put("_json_att", "");
			post.setParames(parames);
			VHttpResponse res = VBrowser.execute(post);
			String body = VHttpUtils.outHtml(res.getBody());
			JSONObject res_obj = new JSONObject(body);
			disposeOrder(res_obj, btnNewButton_1,home_page);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 处理订单列表
	 */
	public static void disposeOrder(JSONObject res_obj, JButton btnNewButton_1,Home_Page home_page) {
		try {
			home_page.table_1.setModel(new DefaultTableModel(new Object[][] {},
					new String[] { "\u8F66\u6B21", "\u8BA2\u5355\u53F7",
							"\u4E58\u5BA2\u59D3\u540D",
							"\u53D1\u8F66\u65F6\u95F4", "\u51FA\u53D1\u5730",
							"\u76EE\u7684\u5730", "\u7968\u79CD",
							"\u5E2D\u522B", "\u8F66\u53A2", "\u5EA7\u4F4D",
							"\u7968\u4EF7", "\u72B6\u6001" }) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			});
			home_page.table_1.getColumnModel().getColumn(3).setPreferredWidth(124);
			DefaultTableModel model = (DefaultTableModel) home_page.table_1.getModel();
			if (btnNewButton_1 != null) {
				btnNewButton_1.setEnabled(true);
			}
			if (res_obj.isNull("data")) {
				model.setRowCount(0);
				return;
			}
			JSONObject userListObj = (JSONObject) res_obj.get("data");

			JSONArray jsonArr = ((JSONArray) userListObj.get("orderDBList"));

			for (int i = 0; i < jsonArr.length(); i++) {
				JSONObject obj = jsonArr.getJSONObject(i);
				JSONArray jsonArr2 = ((JSONArray) obj.get("tickets"));
				JSONObject tickets = jsonArr2.getJSONObject(0);
				JSONObject stationTrainDTO = (JSONObject) tickets
						.get("stationTrainDTO");
				JSONObject passengerDTO = (JSONObject) tickets
						.get("passengerDTO");

				Vector<String> vector = new Vector<String>();
				vector.add(obj.get("train_code_page").toString());
				vector.add(obj.get("sequence_no").toString());
				vector.add(passengerDTO.get("passenger_name").toString());
				vector.add(obj.get("start_train_date_page").toString());
				vector.add(stationTrainDTO.get("from_station_name").toString());
				vector.add(stationTrainDTO.get("to_station_name").toString());
				vector.add(tickets.get("ticket_type_name").toString());
				vector.add(tickets.get("seat_type_name").toString());
				vector.add(tickets.get("coach_name").toString());
				vector.add(tickets.get("seat_name").toString());
				vector.add(tickets.get("str_ticket_price_page").toString());
				vector.add(tickets.get("ticket_status_name").toString());
				model.addRow(vector);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 取消订单
	 * @param orderId
	 * @param button_3
	 */
	public static void cancelOrder(String orderId, JButton button_3,Home_Page home_page) {
		try {
			VHttpPost post = new VHttpPost(
					"https://kyfw.12306.cn/otn/queryOrder/cancelNoCompleteMyOrder");
			VParames parames = new VParames();
			parames.clear();
			parames.put("_json_att", "");
			parames.put("cancel_flag", "cancel_order");
			parames.put("sequence_no", orderId);
			post.setParames(parames);
			VHttpResponse res = VBrowser.execute(post);
			String body = VHttpUtils.outHtml(res.getBody());
			JSONObject res_obj = new JSONObject(body);
			JSONObject data_obj = (JSONObject) res_obj.get("data");
			if ("N".equals(data_obj.get("existError"))) {
				home_page.textArea.append(home_page.format.format(new Date()) + "：订单取消成功\r\n");
			}
			if (button_3 != null) {
				button_3.setEnabled(true);
			}
			Thread.sleep(2000);
			getOrderList(null,home_page);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
