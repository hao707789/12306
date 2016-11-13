package com.vcode.ticket.ui;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vcode.http.client.VHttpResponse;
import com.vcode.http.client.methods.VHttpPost;
import com.vcode.http.utils.VBrowser;
import com.vcode.http.utils.VHttpUtils;
import com.vcode.ticket.methods.BrushTicketMethods;
import com.vcode.ticket.methods.HomeMethods;
import com.vcode.ticket.methods.OrderMethods;
import com.vcode.ticket.utils.Chooser;
import com.vcode.ticket.utils.ComBoTextField;
import com.vcode.ticket.utils.HttpUtils;
import com.vcode.ticket.utils.JTextAreaExt;
import com.vcode.ticket.utils.PopList;

/**
 * 刷票界面
 * 
 * 座位编号： 硬座：1 软座：2 硬卧：3 软卧：4 一等座：M 二等座：O 商务座：9
 * 
 * 
 * @author huahao
 *
 */
public class Home_Page {

	public Home_Page window;
	private JFrame frame;
	public JTextField textField;
	public JTextField textField_2;
	public JTextField textField_3;
	public JTable table;
	private int[] mouse_gis = new int[2];
	public JLabel label,label_10;
	public Map<String, String[]> map = (Map<String, String[]>) HttpUtils
			.getCitiInfo();
	public Map<String, JSONObject> userMap = new HashMap<String, JSONObject>();
	public List<JSONObject> datalist = new ArrayList<JSONObject>();
	public JTextAreaExt textArea;
	public SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	public SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
	public JList<Object> list_1;
	public JList<Object> list_2;
	public JList<Object> list_3;
	public String REPEAT_SUBMIT_TOKEN = "";
	public String key_check_isChange = "";
	public DefaultListModel<Object> model_train = new DefaultListModel<Object>();
	public JTable table_1;
	public int ticket_type = 0;
	public boolean result = true;
	public JSpinner spinner;
	public String sb;
	public String[] seatOthers;
	public JPanel panel_1;
	public JPanel panel_2;
	public boolean isRun = false;
	public JButton btnNewButton;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home_Page window = new Home_Page();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void show(Home_Page window) {
		this.window = window;
		window.frame.setVisible(true);
	}

	/**
	 * Create the application.
	 */
	public Home_Page() {
		VBrowser.getInstance();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		frame = new JFrame();
		frame.setBounds(100, 100, 1110, 720);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/**
		 * ComBoTextField 带下拉功能的textField
		 * 使用方法：ComBoTextField.setupAutoComplete(普通输入框, 下拉数据);
		 * textField.setColumns(number);
		 */
		textField = new JTextField();
		ArrayList<String> items = new ArrayList<String>();
		Set<String> set = map.keySet();
		for (String s : set) {
			items.add(s);
		}
		ComBoTextField.setupAutoComplete2(textField, items, map);
		textField.setColumns(30);
		textField.setColumns(10);

		label = new JLabel("出发地");

		JLabel label_1 = new JLabel("目的地");

		JLabel label_2 = new JLabel("日  期");
		frame.getContentPane().setLayout(null);
		textField_2 = new JTextField();

		Chooser ser2 = Chooser.getInstance("yyyy-MM-dd");
		ser2.register(textField_2);
		textField_2.setColumns(10);

		textField_3 = new JTextField();
		ComBoTextField.setupAutoComplete2(textField_3, items, map);
		textField_3.setColumns(10);

		JCheckBox chckbxNewCheckBox = new JCheckBox("更多日期");
		Chooser ser3 = Chooser.getInstance("yyyy-MM-dd");
		ser3.register(chckbxNewCheckBox);

		JLabel label_3 = new JLabel("发车时间");

		final JComboBox<Object> comboBox = new JComboBox<Object>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.setRowCount(0);
				String [] time_arr = comboBox.getSelectedItem().toString().split("—");
				for (int i = 0; i < datalist.size(); i++) {
					JSONObject obj = datalist.get(i);
					String start_time;
					try {
						start_time = obj.get("start_time").toString();
						DateFormat sdf = new SimpleDateFormat("HH:mm"); 
						Date first_date = sdf.parse(time_arr[0]);
						Date last_date = sdf.parse(time_arr[1]);
						Date start_date = sdf.parse(start_time);
						if (first_date.getTime()<=start_date.getTime()&&start_date.getTime()<last_date.getTime()) {
							addRow(obj);
						}
					} catch (JSONException e1) {
						e1.printStackTrace();
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		comboBox.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		comboBox.setModel(new DefaultComboBoxModel<Object>(new String[] {
				"00:00—24:00", "00:00—08:00", "08:00—12:00", "12:00—20:00",
				"20:00—24:00" }));

		/**
		 * adult: "1", child: "2", student: "3", disability: "4"
		 */
		JRadioButton radioButton = new JRadioButton("成人");
		radioButton.setSelected(true);

		JRadioButton radioButton_1 = new JRadioButton("学生");

		JRadioButton radioButton_2 = new JRadioButton("儿童");

		JRadioButton radioButton_3 = new JRadioButton("军残");

		ButtonGroup btgroup = new ButtonGroup();
		btgroup.add(radioButton);
		btgroup.add(radioButton_1);
		btgroup.add(radioButton_2);
		btgroup.add(radioButton_3);

		btnNewButton = new JButton("手动查票");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (ticket_type==1) {
					if (isRun) {
						isRun = false;
						result = false;
						printLog("已停止刷票");
						btnNewButton.setText("自动刷票");
					}else {
						isRun = true;
						btnNewButton.setText("停止刷票");
						checkAllColRow();
						checkbrushVotesInfo();
					}
				}else {
					checkbrushVotesInfo();
				}
			}
		});
		btnNewButton.setBounds(964, 41, 80, 67);
		
		final JCheckBox chckbxNewCheckBox_1 = new JCheckBox("刷票模式");
		chckbxNewCheckBox_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (chckbxNewCheckBox_1.isSelected()) {
					btnNewButton.setText("自动刷票");
					ticket_type = 1;
				}else {
					if (!isRun) {
						btnNewButton.setText("手动查票");
						ticket_type = 0;
					}else {
						printLog("请先停止刷票");
						chckbxNewCheckBox_1.setSelected(true);
						return;
					}
				}
			}
		});
		chckbxNewCheckBox_1.setBounds(886, 52, 75, 23);
		frame.getContentPane().add(chckbxNewCheckBox_1);
		frame.getContentPane().add(btnNewButton);

		JLabel label_4 = new JLabel("车  型");
		JLabel label_5 = new JLabel("席  别");

		panel_1 = new JPanel();
		panel_2 = new JPanel();

		JScrollPane scrollPane = new JScrollPane();
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == e.BUTTON3) {
					int[] rows = table.getSelectedRows();
					for (int row : rows) {
						if (!model_train.contains(table.getValueAt(row, 0))) {
							model_train.addElement(table.getValueAt(row, 0));
						}
					}
				}
				if (e.getClickCount() == 2) {
					new HomeMethods(window).start();
				}
			}
		});
		table.setFillsViewportHeight(true);
		table.setSurrendersFocusOnKeystroke(true);
		table.setFont(new Font("宋体", Font.PLAIN, 12));
		scrollPane.setViewportView(table);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\u8F66\u6B21", "\u51FA\u53D1\u5730", "\u76EE\u7684\u5730", "\u5386\u65F6", "\u53D1\u8F66\u65F6\u95F4", "\u5230\u8FBE\u65F6\u95F4", "\u5546\u52A1", "\u7279\u7B49", "\u4E00\u7B49", "\u4E8C\u7B49", "\u9AD8\u8F6F", "\u8F6F\u5367", "\u786C\u5367", "\u8F6F\u5EA7", "\u786C\u5EA7", "\u65E0\u5EA7", "\u5176\u5B83", "\u5907\u6CE8"
			}
		));
		table.getColumnModel().getColumn(3).setPreferredWidth(68);
		table.getColumnModel().getColumn(4).setPreferredWidth(90);
		table.getColumnModel().getColumn(5).setPreferredWidth(90);
		table.getColumnModel().getColumn(6).setPreferredWidth(65);
		table.getColumnModel().getColumn(7).setPreferredWidth(65);
		table.getColumnModel().getColumn(8).setPreferredWidth(65);
		table.getColumnModel().getColumn(9).setPreferredWidth(65);
		table.getColumnModel().getColumn(10).setPreferredWidth(65);
		table.getColumnModel().getColumn(11).setPreferredWidth(65);
		table.getColumnModel().getColumn(12).setPreferredWidth(65);
		table.getColumnModel().getColumn(13).setPreferredWidth(65);
		table.getColumnModel().getColumn(14).setPreferredWidth(65);
		table.getColumnModel().getColumn(15).setPreferredWidth(65);
		table.getColumnModel().getColumn(16).setPreferredWidth(65);
		setTableSize();

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		JPanel p1 = new JPanel();
		tabbedPane.add(p1, "刷票界面");
		p1.setLayout(null);

		textArea = new JTextAreaExt();
		textArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==e.BUTTON3) {
					textArea.setText("");
					printLog("信息输出区清空完毕");
				}
			}
		});
		textArea.setEnabled(false);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		scroll.setBounds(0, 0, 368, 205);
		textArea.setBorder(BorderFactory.createTitledBorder("信息输出: "));
		p1.add(scroll);

		spinner = new JSpinner();
		spinner.setBounds(496, 53, 53, 22);
		spinner.setModel(new SpinnerNumberModel(new Integer(1000), null, null,
				new Integer(100)));
		p1.add(spinner);

		JLabel label_6 = new JLabel("刷票频率：");
		label_6.setBounds(435, 57, 62, 15);
		p1.add(label_6);

		JLabel label_7 = new JLabel("乘    客：");
		label_7.setBounds(436, 171, 62, 15);
		p1.add(label_7);

		JLabel label_8 = new JLabel("优先席别：");
		label_8.setBounds(436, 134, 62, 15);
		p1.add(label_8);

		JList<Object> list = new JList<Object>();
		list.setBounds(950, 73, 25, -19);
		p1.add(list);

		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("部分提交");
		chckbxNewCheckBox_2.setBounds(572, 53, 81, 23);
		p1.add(chckbxNewCheckBox_2);

		JCheckBox checkBox_20 = new JCheckBox("无座不提交");
		checkBox_20.setBounds(662, 53, 91, 23);
		checkBox_20.setSelected(true);
		p1.add(checkBox_20);

		JCheckBox checkBox_21 = new JCheckBox("定时启动");
		checkBox_21.setBounds(763, 53, 81, 23);
		p1.add(checkBox_21);

		JLabel label_9 = new JLabel("乘车信息：");
		label_9.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				
			}
		});
		label_9.setBounds(435, 21, 69, 15);
		p1.add(label_9);

		JSpinner spinner_1 = new JSpinner();
		spinner_1.setBounds(844, 53, 130, 22);
		spinner_1.setModel(new SpinnerDateModel(new Date(1477411200000L), null,
				null, Calendar.MINUTE));
		p1.add(spinner_1);

		label_10 = new JLabel();
		label_10.setBounds(495, 21, 453, 15);
		p1.add(label_10);

		JLabel label_11 = new JLabel("车    次：");
		label_11.setBounds(436, 96, 62, 15);
		p1.add(label_11);

		JButton button = new JButton("席别");
		button.setBounds(911, 130, 73, 23);
		p1.add(button);

		DefaultListModel<Object> pupModel = new DefaultListModel<Object>();
		pupModel.addElement("商务座");
		pupModel.addElement("特等座");
		pupModel.addElement("一等座");
		pupModel.addElement("二等座");
		pupModel.addElement("高级软卧");
		pupModel.addElement("软卧");
		pupModel.addElement("硬卧");
		pupModel.addElement("软座");
		pupModel.addElement("硬座");
		pupModel.addElement("无座");
		pupModel.addElement("其它");
		DefaultListModel<Object> pupModel2 = getPassengerDTOs();

		JButton button_1 = new JButton("乘车人");
		button_1.setBounds(911, 167, 73, 23);
		p1.add(button_1);

		list_1 = new JList<Object>(model_train);
		list_1.setBounds(496, 87, 404, 32);
		list_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (list_1.getSelectedIndex() > -1) {
					model_train.remove(list_1.getSelectedIndex());
				}
			}
		});
		list_1.setVisibleRowCount(2);
		list_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_1.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		p1.add(list_1);

		list_2 = new JList<Object>();
		list_2.setBounds(496, 125, 404, 32);
		list_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount()>=2) {
					if (list_2.getSelectedIndex() > -1) {
						DefaultListModel<Object> model_Seats = new DefaultListModel<Object>();
						for (int i = 0; i < list_2.getModel().getSize(); i++) {
							if (i != list_2.getSelectedIndex()) {
								model_Seats.addElement(list_2.getModel()
										.getElementAt(i));
							}
						}
						list_2.setModel(model_Seats);
					}
				}
			}
		});
		list_2.setVisibleRowCount(2);
		list_2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_2.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		p1.add(list_2);

		list_3 = new JList<Object>();
		list_3.setBounds(496, 162, 404, 32);
		list_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount()>=2) {
					DefaultListModel<Object> model_Passenger = new DefaultListModel<Object>();
					if (list_3.getSelectedIndex() > -1) {
						for (int i = 0; i < list_3.getModel().getSize(); i++) {
							if (i != list_3.getSelectedIndex()) {
								model_Passenger.addElement(list_3.getModel()
										.getElementAt(i));
							}
						}
						list_3.setModel(model_Passenger);
					}
				}
			}
		});
		list_3.setVisibleRowCount(2);
		list_3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_3.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		p1.add(list_3);

		PopList.initPopup(button, list_2, pupModel);
		PopList.initPopup(button_1, list_3, pupModel2);

		JButton button_2 = new JButton("清空");
		button_2.setBounds(911, 92, 73, 23);
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				model_train.removeAllElements();
			}
		});
		p1.add(button_2);
		JPanel p2 = new JPanel();
		tabbedPane.add(p2, "订单界面");
		p2.setLayout(null);

		final JButton btnNewButton_1 = new JButton("刷新订单列表");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNewButton_1.setEnabled(false);
				//启动线程，不影响UI
				new Thread(new Runnable() {
					@Override
					public void run() {
						OrderMethods.getOrderList(btnNewButton_1, window);
					}
				}).start();
			}
		});
		btnNewButton_1.setBounds(20, 19, 105, 32);
		p2.add(btnNewButton_1);

		table_1 = new JTable();
		table_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_1.setFillsViewportHeight(true);
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\u8F66\u6B21", "\u8BA2\u5355\u53F7", "\u4E58\u5BA2\u59D3\u540D", "\u53D1\u8F66\u65F6\u95F4", "\u51FA\u53D1\u5730", "\u76EE\u7684\u5730", "\u7968\u79CD", "\u5E2D\u522B", "\u8F66\u53A2", "\u5EA7\u4F4D", "\u7968\u4EF7", "\u72B6\u6001"
			}
		));
		table_1.getColumnModel().getColumn(3).setPreferredWidth(124);

		JScrollPane scrollPane_1 = new JScrollPane(table_1);
		scrollPane_1.setBounds(10, 62, 976, 140);
		p2.add(scrollPane_1);

		JPanel panel = new JPanel();
		panel.setBounds(10, 0, 465, 60);
		panel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "订单操作: ",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				SystemColor.controlText));
		p2.add(panel);
		panel.setLayout(null);

		JButton button_4 = new JButton("继续支付");
		button_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("继续支付");
			}
		});
		button_4.setBounds(205, 19, 81, 32);
		panel.add(button_4);

		final JButton button_3 = new JButton("取消订单");
		button_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (table_1.getSelectedRows().length > 0) {
					if (table_1.getValueAt(table_1.getSelectedRow(), 1) != null) {
						int firm_cancle = JOptionPane.showConfirmDialog(null, "是否取消订单?", "确认框",JOptionPane.YES_NO_OPTION);
						if(firm_cancle==0){
							button_3.setEnabled(false);
							final String OrderId = table_1.getValueAt(
								table_1.getSelectedRow(), 1).toString();
							new Thread(new Runnable() {
								public void run() {
									OrderMethods.cancelOrder(OrderId, button_3, window);
								}
							}).start();
						}
					}
				}
			}
		});
		button_3.setBounds(120, 19, 81, 32);
		panel.add(button_3);

		JButton button_5 = new JButton("改签");
		button_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("改签");
			}
		});
		button_5.setBounds(290, 19, 81, 32);
		panel.add(button_5);

		JButton button_6 = new JButton("退票");
		button_6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("退票");
			}
		});
		button_6.setBounds(375, 19, 81, 32);
		panel.add(button_6);

		JPanel p3 = new JPanel();
		tabbedPane.add(p3, "消息提醒");
		JPanel p4 = new JPanel();
		tabbedPane.add(p4, "其它功能");
		GroupLayout gl_p4 = new GroupLayout(p4);
		gl_p4.setHorizontalGroup(gl_p4.createParallelGroup(Alignment.LEADING)
				.addGap(0, 996, Short.MAX_VALUE));
		gl_p4.setVerticalGroup(gl_p4.createParallelGroup(Alignment.LEADING)
				.addGap(0, 205, Short.MAX_VALUE));
		p4.setLayout(gl_p4);
		panel_1.setLayout(null);

		final JCheckBox checkBox_22 = new JCheckBox("全部车次");
		checkBox_22.setBounds(15, 5, 73, 23);
		panel_1.add(checkBox_22);
		checkBox_22.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (checkBox_22.isSelected()) {
					Component[] comps = panel_1.getComponents();
					for (int i = 0; i < comps.length; i++) {
						Component comp = comps[i];
						if (comp instanceof JCheckBox) {
							JCheckBox box = (JCheckBox) comp;
							box.setSelected(true);
						}
					}
					
					//显示全部车次信息
					for (int i=0;i<datalist.size();i++) {
						JSONObject obj = datalist.get(i);
						addRow(obj);
					}
				} else {
					Component[] comps = panel_1.getComponents();
					for (int i = 0; i < comps.length; i++) {
						Component comp = comps[i];
						if (comp instanceof JCheckBox) {
							JCheckBox box = (JCheckBox) comp;
							box.setSelected(false);
						}
					}
					//隐藏全部车次信息
					DefaultTableModel model =  (DefaultTableModel) table.getModel();
					model.setRowCount(0);
				}
			}
		});
		checkBox_22.setSelected(true);

		final JCheckBox chckbxg = new JCheckBox("高铁-G");
		chckbxg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//
				CheckMethods2(chckbxg,panel_1,checkBox_22,"G");
				
			}
		});
		chckbxg.setBounds(90, 5, 61, 23);
		panel_1.add(chckbxg);
		chckbxg.setSelected(true);

		final JCheckBox chckbxc = new JCheckBox("城铁-C");
		chckbxc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CheckMethods2(chckbxc,panel_1,checkBox_22,"C");
			}
		});
				
		chckbxc.setBounds(153, 5, 61, 23);
		panel_1.add(chckbxc);
		chckbxc.setSelected(true);

		final JCheckBox chckbxd = new JCheckBox("动车-D");
		chckbxd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CheckMethods2(chckbxd,panel_1,checkBox_22,"D");
			}
		});
		
		chckbxd.setBounds(216, 5, 61, 23);
		panel_1.add(chckbxd);
		chckbxd.setSelected(true);

		final JCheckBox chckbxt = new JCheckBox("特快-T");
		chckbxt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CheckMethods2(chckbxt,panel_1,checkBox_22,"T");
			}
		});
		chckbxt.setBounds(279, 5, 61, 23);
		panel_1.add(chckbxt);
		chckbxt.setSelected(true);

		final JCheckBox chckbxz = new JCheckBox("直达-Z");
		chckbxz.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CheckMethods2(chckbxz,panel_1,checkBox_22,"Z");
			}
		});
		chckbxz.setBounds(342, 5, 61, 23);
		panel_1.add(chckbxz);
		chckbxz.setSelected(true);

		final JCheckBox chckbxk = new JCheckBox("快车-K");
		chckbxk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CheckMethods2(chckbxk,panel_1,checkBox_22,"K");
			}
		});
		chckbxk.setBounds(405, 5, 61, 23);
		panel_1.add(chckbxk);
		chckbxk.setSelected(true);

		final JCheckBox checkBox_6 = new JCheckBox("普客");
		checkBox_6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CheckMethods2(checkBox_6,panel_1,checkBox_22,"*");
			}
		});
		checkBox_6.setBounds(468, 5, 49, 23);
		panel_1.add(checkBox_6);
		checkBox_6.setSelected(true);

		final JCheckBox checkBox_7 = new JCheckBox("临客");
		checkBox_7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CheckMethods2(checkBox_7,panel_1,checkBox_22,"*");
			}
		});
		checkBox_7.setBounds(519, 5, 49, 23);
		panel_1.add(checkBox_7);
		checkBox_7.setSelected(true);

		final JCheckBox checkBox_9 = new JCheckBox("其它");
		checkBox_9.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CheckMethods2(checkBox_9,panel_1,checkBox_22,"*");
			}
		});
		checkBox_9.setBounds(570, 5, 49, 23);
		panel_1.add(checkBox_9);
		checkBox_9.setSelected(true);
		panel_2.setLayout(null);

		final JCheckBox checkBox_23 = new JCheckBox("全部席别");
		checkBox_23.setBounds(15, 4, 73, 23);
		panel_2.add(checkBox_23);
		checkBox_23.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Component[] comps = panel_2.getComponents();
				for (int i = 0; i < comps.length; i++) {
					Component comp = comps[i];
					if (comp instanceof JCheckBox) {
						JCheckBox box = (JCheckBox) comp;
						if (checkBox_23.isSelected()){
							box.setSelected(true);
						}else {
							box.setSelected(false);
						}
					}
				}
				for (int i = 0; i < comps.length; i++) {
					Component comp = comps[i];
					if (comp instanceof JCheckBox) {
						JCheckBox box = (JCheckBox) comp;
						if (!"全部席别".equals(box.getText())) {
							int col_size = table.getColumnModel().getColumnCount();
							int col_num = 0;
							for (int j=0;j<col_size;j++) {
								if (box.getText().equals(table.getColumnModel().getColumn(j).getHeaderValue())) {
									col_num = j;
								}
							}
							CheckMethods(box, col_num, box.getWidth(), panel_2, checkBox_23);
						}
					}
				}
			}
		});
		checkBox_23.setSelected(true);

		final JCheckBox checkBox_10 = new JCheckBox("商务");
		checkBox_10.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CheckMethods(checkBox_10, 6, 65, panel_2, checkBox_23);
			}
		});
		checkBox_10.setBounds(90, 4, 49, 23);
		panel_2.add(checkBox_10);
		checkBox_10.setSelected(true);

		final JCheckBox checkBox_8 = new JCheckBox("特等");
		checkBox_8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CheckMethods(checkBox_8, 7, 65, panel_2, checkBox_23);
			}
		});
		checkBox_8.setBounds(153, 4, 49, 23);
		panel_2.add(checkBox_8);
		checkBox_8.setSelected(true);

		final JCheckBox checkBox_11 = new JCheckBox("一等");
		checkBox_11.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CheckMethods(checkBox_11, 8, 65, panel_2, checkBox_23);
			}
		});
		checkBox_11.setBounds(216, 4, 49, 23);
		panel_2.add(checkBox_11);
		checkBox_11.setSelected(true);

		final JCheckBox checkBox_12 = new JCheckBox("二等");
		checkBox_12.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CheckMethods(checkBox_12, 9, 65, panel_2, checkBox_23);
			}
		});
		checkBox_12.setBounds(279, 4, 49, 23);
		panel_2.add(checkBox_12);
		checkBox_12.setSelected(true);

		final JCheckBox checkBox_13 = new JCheckBox("高软");
		checkBox_13.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CheckMethods(checkBox_13, 10, 65, panel_2, checkBox_23);
			}
		});
		checkBox_13.setBounds(342, 4, 49, 23);
		panel_2.add(checkBox_13);
		checkBox_13.setSelected(true);

		final JCheckBox checkBox_19 = new JCheckBox("软卧");
		checkBox_19.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CheckMethods(checkBox_19, 11, 65, panel_2, checkBox_23);
			}
		});
		checkBox_19.setBounds(405, 4, 49, 23);
		panel_2.add(checkBox_19);
		checkBox_19.setSelected(true);

		final JCheckBox checkBox_15 = new JCheckBox("硬卧");
		checkBox_15.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CheckMethods(checkBox_15, 12, 65, panel_2, checkBox_23);
			}
		});
		checkBox_15.setBounds(468, 4, 49, 23);
		panel_2.add(checkBox_15);
		checkBox_15.setSelected(true);

		final JCheckBox checkBox_18 = new JCheckBox("软座");
		checkBox_18.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CheckMethods(checkBox_18, 13, 65, panel_2, checkBox_23);
			}
		});
		checkBox_18.setBounds(519, 4, 49, 23);
		panel_2.add(checkBox_18);
		checkBox_18.setSelected(true);

		final JCheckBox checkBox_17 = new JCheckBox("硬座");
		checkBox_17.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CheckMethods(checkBox_17, 14, 65, panel_2, checkBox_23);
			}
		});
		checkBox_17.setBounds(570, 4, 49, 23);
		panel_2.add(checkBox_17);
		checkBox_17.setSelected(true);

		final JCheckBox checkBox_16 = new JCheckBox("无座");
		checkBox_16.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CheckMethods(checkBox_16, 15, 65, panel_2, checkBox_23);
			}
		});
		checkBox_16.setBounds(628, 4, 49, 23);
		panel_2.add(checkBox_16);
		checkBox_16.setSelected(true);

		final JCheckBox checkBox_14 = new JCheckBox("其它");
		checkBox_14.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CheckMethods(checkBox_14, 16, 65, panel_2, checkBox_23);
			}
		});
		checkBox_14.setBounds(682, 4, 49, 23);
		panel_2.add(checkBox_14);
		checkBox_14.setSelected(true);

		JLabel lblNewLabel = new JLabel(new ImageIcon("arrow.png"));
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String one = textField.getText();
				String two = textField_3.getText();
				textField.setText(two);
				textField_3.setText(one);
			}
		});
		lblNewLabel.setText("◄►");
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(43)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(label, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
									.addGap(7)
									.addComponent(lblNewLabel)
									.addGap(7)
									.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
									.addGap(3)
									.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
									.addGap(4)
									.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
									.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
									.addGap(1)
									.addComponent(chckbxNewCheckBox, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
									.addGap(2)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(57)
											.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
									.addGap(4)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(57)
											.addComponent(radioButton_1, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
										.addComponent(radioButton, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(171)
											.addComponent(radioButton_3, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(113)
											.addComponent(radioButton_2, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))))
								.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 656, GroupLayout.PREFERRED_SIZE))
							.addComponent(chckbxNewCheckBox_1, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
							.addGap(3)
							.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(label_5, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 786, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 1001, GroupLayout.PREFERRED_SIZE)
						.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(41)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(11)
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
							.addGap(19)
							.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(9)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(1)
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(4)
									.addComponent(lblNewLabel))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(2)
									.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(1)
									.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(1)
									.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(1)
									.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(chckbxNewCheckBox)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(1)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(1)
											.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(1)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(radioButton_1)
										.addComponent(radioButton)
										.addComponent(radioButton_3)
										.addComponent(radioButton_2))))
							.addGap(8)
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(11)
							.addComponent(chckbxNewCheckBox_1))
						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
					.addGap(4)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(label_5, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
						.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
					.addGap(9)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 237, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		frame.getContentPane().setLayout(groupLayout);

		frame.addMouseListener(new MouseAdapter() { // 匿名内部类，鼠标事件
			public void mouseClicked(MouseEvent e) { // 鼠标完成点击事件
				if (e.getButton() == MouseEvent.BUTTON1) { // e.getButton就会返回点鼠标的那个键，左键还是右健，3代表右键
					int x = e.getX(); // 得到鼠标x坐标
					int y = e.getY(); // 得到鼠标y坐标
					mouse_gis[0] = x;
					mouse_gis[1] = y;
				}
			}
		});
	}
	
	
	/**
	 * 
	 * 校验刷票信息是否填写完毕
	 * 
	 */
	private void checkbrushVotesInfo() {
		// 验证出发地、目的地、时间等是否填写，没有则不刷票
		if ("".equals(textField.getText())) {
			textArea.append(format.format(new Date()) + "：请输入出发地\r\n");
			label_10.setText(null);
			return;
		}
		if ("".equals(textField_3.getText())) {
			textArea.append(format.format(new Date()) + "：请输入目的地\r\n");
			label_10.setText(null);
			return;
		}
		if ("".equals(textField_2.getText())) {
			textArea.append(format.format(new Date()) + "：请输入日期\r\n");
			label_10.setText(null);
			return;
		}
		if (list_3.getModel().getSize() <= 0) {
			textArea.append(format.format(new Date()) + "：请选择乘车人\r\n");
			label_10.setText(null);
			return;
		}
		if (list_2.getModel().getSize() <= 0) {
			textArea.append(format.format(new Date()) + "：请先选择席别\r\n");
			return;
		}
		if (ticket_type==1) {
			if (list_1.getModel().getSize() <= 0) {
				textArea.append(format.format(new Date()) + "：请先切换到手动查票模式下，查询出车次后，并右键添加到车次列表后再切为自动刷票模式\r\n");
				return;
			}
		}
		
		ListModel<Object> model = list_3.getModel();
		StringBuffer sb2 = new StringBuffer();
		for (int i =0;i<model.getSize();i++) {
			sb2.append(model.getElementAt(i).toString());
			if (i!=model.getSize()-1) {
				sb2.append(",");
			}
		}
		String content="    "+sb2.toString()+"     "+textField.getText() +"  →  "+textField_3.getText()+"     "+textField_2.getText();
		label_10.setText(content);
		
		sb = packagingBrushTicketInfo();
		seatOthers = packagingseatOther();
		new BrushTicketMethods(window).start();//刷票
	}
	
	/**
	 * 席别选择框方法
	 * 当选中该席别时，列表中对应的列显示，否则隐藏
	 * 当有隐藏列时，全部席别选择框设为未选中，否则选中
	 * @param checkBox	点击的复选框
	 * @param col_num	对应在列表是第几列
	 * @param width		对应在列表的列原本的宽度
	 * @param panel		该组复选框的父类容器
	 * @param checkBox2	全部席别复选框
	 */
	private void CheckMethods(JCheckBox checkBox,int col_num,int width,JPanel panel,JCheckBox checkBox2){
		if (!checkBox.isSelected()) {
			table.getColumnModel().getColumn(col_num).setMinWidth(0);
			table.getColumnModel().getColumn(col_num).setMaxWidth(0);
			table.getColumnModel().getColumn(col_num).setPreferredWidth(0);
		}else {
			table.getColumnModel().getColumn(col_num).setMinWidth(width);
			table.getColumnModel().getColumn(col_num).setMaxWidth(width);
			table.getColumnModel().getColumn(col_num).setPreferredWidth(width);
		}
		boolean check = true;
		Component[] comps = panel.getComponents();
		for (int i = 0; i < comps.length; i++) {
			Component comp = comps[i];
			if (comp instanceof JCheckBox) {
				JCheckBox box = (JCheckBox) comp;
				if (!"全部席别".equals(box.getText())) {
					if (!box.isSelected()) {
						check = false;
					}
				}
			}
		}
		if (check) {
			checkBox2.setSelected(true);
		}else {
			checkBox2.setSelected(false);
		}
	}
	
	/**
	 * 车次复选框功能
	 * @param checkBox		复选框
	 * @param panel_1		车次复选框的父类容器
	 * @param checkBox_22	全部车次按钮
	 * @param num			车次编号的第一个字母
	 */
	private void CheckMethods2(JCheckBox checkBox, JPanel panel_1,
			JCheckBox checkBox_22, String num) {
		if (checkBox.isSelected()) {
			// 遍历其它车次的值，全部都是true，则勾选全部车次，否则全部车次不勾选
			Component[] comps = panel_1.getComponents();
			boolean check = true;
			for (int i = 0; i < comps.length; i++) {
				Component comp = comps[i];
				if (comp instanceof JCheckBox) {
					JCheckBox box = (JCheckBox) comp;
					if (!"全部车次".equals(box.getText())) {
						if (!box.isSelected()) {
							check = false;
							break;
						}
					}
				}
			}
			if (check) {
				checkBox_22.setSelected(true);
			} else {
				checkBox_22.setSelected(false);
			}
			// 显示行
			for (int i = 0; i < datalist.size(); i++) {
				JSONObject obj = datalist.get(i);
				String train_code;
				try {
					train_code = obj.get("station_train_code").toString();
					if (train_code.toUpperCase().startsWith(num)) {
						addRow(obj);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} else {
			// 全部车次的值改为false
			checkBox_22.setSelected(false);
			//隐藏行
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			int row_num = table.getRowCount();
			for (int i = row_num - 1; i >= 0; i--) {
				String train_code = table.getValueAt(i, 0).toString();
				if (train_code.toUpperCase().startsWith(num)) {
					model.removeRow(i);
				}
			}
		}
	}
	
	/**
	 * 选中所有行和列
	 */
	public void checkAllColRow(){
		//处理行
		Component[] comps = panel_1.getComponents();
		for (int i = 0; i < comps.length; i++) {
			Component comp = comps[i];
			if (comp instanceof JCheckBox) {
				JCheckBox box = (JCheckBox) comp;
				box.setSelected(true);
			}
		}
		for (int i=0;i<datalist.size();i++) {
			JSONObject obj = datalist.get(i);
			addRow(obj);
		}
		
		//处理列
		Component[] comps2 = panel_2.getComponents();
		for (int i = 0; i < comps2.length; i++) {
			Component comp = comps2[i];
			if (comp instanceof JCheckBox) {
				JCheckBox box = (JCheckBox) comp;
				box.setSelected(true);
			}
		}
		for (int i = 0; i < comps2.length; i++) {
			Component comp = comps2[i];
			if (comp instanceof JCheckBox) {
				JCheckBox box = (JCheckBox) comp;
				if (!"全部席别".equals(box.getText())) {
					int col_size = table.getColumnModel().getColumnCount();
					int col_num = 0;
					for (int j=0;j<col_size;j++) {
						if (box.getText().equals(table.getColumnModel().getColumn(j).getHeaderValue())) {
							col_num = j;
						}
					}
					CheckMethods(box, col_num, box.getWidth(), panel_2, (JCheckBox)comps2[0]);
				}
			}
		}
	}
	
	
	/**
	 * 打印信息
	 * @param data
	 */
	public void printLog(String data){
		textArea.append(format.format(new Date()) + "："+data+"\r\n");
	}
	
	/**
	 * 
	 * 组装刷票信息
	 * 
	 */
	private String packagingBrushTicketInfo(){
		StringBuffer sb = new StringBuffer();
		sb.append("leftTicketDTO.train_date=");
		sb.append(textField_2.getText() + "&");
		sb.append("leftTicketDTO.from_station=");
		sb.append(map.get(textField.getText())[2] + "&");
		sb.append("leftTicketDTO.to_station=");
		sb.append(map.get(textField_3.getText())[2] + "&");
		sb.append("purpose_codes=ADULT");
		return sb.toString();
	}
	
	/**
	 * 
	 * 组装票类型参数，用于自动刷票使用
	 * 
	 */
	public String[] packagingseatOther(){
		//组装自动刷票判断
		ListModel<Object> model2 = list_2.getModel();
		String[] seatOther = new String[model2.getSize()];
		result = true;
		if (ticket_type==1) {
			for (int i=0;i<model2.getSize();i++) {
				if ("商务座".equalsIgnoreCase(model2.getElementAt(i).toString().trim())) {
					seatOther[i] = "swz_num";
				}else if ("特等座".equalsIgnoreCase(model2.getElementAt(i).toString().trim())) {
					seatOther[i] = "tz_num";
				}else if ("一等座".equalsIgnoreCase(model2.getElementAt(i).toString().trim())){
					seatOther[i] = "zy_num";
				}else if ("二等座".equalsIgnoreCase(model2.getElementAt(i).toString().trim())){
					seatOther[i] = "ze_num";
				}else if ("高级软卧".equalsIgnoreCase(model2.getElementAt(i).toString().trim())){
					seatOther[i] = "gr_num";
				}else if ("软卧".equalsIgnoreCase(model2.getElementAt(i).toString().trim())){
					seatOther[i] = "rw_num";
				}else if ("硬卧".equalsIgnoreCase(model2.getElementAt(i).toString().trim())){
					seatOther[i] = "yw_num";
				}else if ("软座".equalsIgnoreCase(model2.getElementAt(i).toString().trim())){
					seatOther[i] = "rz_num";
				}else if ("硬座".equalsIgnoreCase(model2.getElementAt(i).toString().trim())){
					seatOther[i] = "yz_num";
				}else if ("无座".equalsIgnoreCase(model2.getElementAt(i).toString().trim())){
					seatOther[i] = "wz_num";
				}else if ("其它".equalsIgnoreCase(model2.getElementAt(i).toString().trim())){
					seatOther[i] = "qt_num";
				}
			}
		}
		return seatOther;
	}
	
	/**
	 * 设置表格样式
	 */
	public void setTableSize() {
		table.setToolTipText("");
	}
	
	/**
	 * 获取乘客列表
	 * 
	 * @return
	 */
	private DefaultListModel<Object> getPassengerDTOs() {
		DefaultListModel<Object> model_Seats = new DefaultListModel<Object>();
		VHttpPost post = new VHttpPost(
				"https://kyfw.12306.cn/otn/confirmPassenger/getPassengerDTOs");
		VHttpResponse res = VBrowser.execute(post);
		String body = VHttpUtils.outHtml(res.getBody());
		try {
			JSONObject res_obj = new JSONObject(body);
			JSONObject userListObj = (JSONObject) res_obj.get("data");
			if (userListObj.length() < 1) {
				textArea.append(format.format(new Date()) + "："
						+ new JSONObject(body).get("messages") + "\r\n");
			} else {
				JSONArray userArr = (JSONArray) (userListObj
						.get("normal_passengers"));
				for (int i = 0; i < userArr.length(); i++) {
					JSONObject obj = (JSONObject) userArr.get(i);
					model_Seats.addElement(obj.get("passenger_name"));
					userMap.put(obj.get("passenger_name").toString(), obj);
				}
			}
		} catch (JSONException e) {
			textArea.append(format.format(new Date())
					+ "：获取乘客列表失败，请联系作者QQ：3094759846\r\n");
		}
		return model_Seats;
	}
	
	/**
	 * 添加一行数据
	 * @param obj
	 */
	public void addRow(JSONObject obj) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		Vector<String> vector = new Vector<String>();
		try {
			vector.add(obj.get("station_train_code").toString());
			vector.add(obj.get("start_station_name").toString());
			vector.add(obj.get("end_station_name").toString());
			vector.add(obj.get("lishi").toString());
			vector.add(obj.get("start_time").toString());
			vector.add(obj.get("arrive_time").toString());
			vector.add(obj.get("swz_num").toString());
			vector.add(obj.get("tz_num").toString());
			vector.add(obj.get("zy_num").toString());
			vector.add(obj.get("ze_num").toString());
			vector.add(obj.get("gr_num").toString());
			vector.add(obj.get("rw_num").toString());
			vector.add(obj.get("yw_num").toString());
			vector.add(obj.get("rz_num").toString());
			vector.add(obj.get("yz_num").toString());
			vector.add(obj.get("wz_num").toString());
			vector.add(obj.get("qt_num").toString());
			if ("Y".equalsIgnoreCase(obj.get("canWebBuy").toString())) {
				vector.add("可预订");
			} else {
				vector.add("不可预订");
			}
			model.addRow(vector);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
