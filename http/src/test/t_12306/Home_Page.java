package test.t_12306;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vcode.http.client.VHttpResponse;
import com.vcode.http.client.methods.VHttpGet;
import com.vcode.http.client.methods.VHttpPost;
import com.vcode.http.client.parames.VParames;
import com.vcode.http.utils.Browser;
import com.vcode.http.utils.Chooser;
import com.vcode.http.utils.HttpUtils;
import com.vcode.http.utils.PopList;

public class Home_Page {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTable table;
	private int[] mouse_gis = new int[2];
	private JLabel label;
	private Map<String, String> map = (Map<String, String>)HttpUtils.getCitiInfo();
	private List<JSONObject> datalist = new ArrayList<JSONObject>();
	private JTextArea textArea;
	private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	private JList<Object> list_1;
	private JList<Object> list_2;
	private JList<Object> list_3;
	private DefaultListModel<Object> model_train = new DefaultListModel<Object>();

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
	
	public void show(Home_Page window){
		window.frame.setVisible(true);
	}

	/**
	 * Create the application.
	 */
	public Home_Page() {
		Browser.getInstance();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		frame = new JFrame();
		frame.setBounds(100, 100, 1110, 720);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		
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
        ComBoTextField.setupAutoComplete2(textField, items,map);
        textField.setColumns(30);
		textField.setBounds(84, 51, 60, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		label = new JLabel("出发地");
		label.setBounds(43, 52, 41, 18);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("目的地");
		label_1.setBounds(185, 52, 41, 18);
		frame.getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("日  期");
		label_2.setBounds(293, 51, 41, 21);
		frame.getContentPane().add(label_2);
		frame.getContentPane().setLayout(null);
		textField_2 = new JTextField();
		
		Chooser ser2 = Chooser.getInstance("yyyy-MM-dd");
        ser2.register(textField_2);
		
		textField_2.setBounds(334, 51, 86, 21);
		textField_2.setColumns(10);
		frame.getContentPane().add(textField_2);
		
		textField_3 = new JTextField();
        ComBoTextField.setupAutoComplete2(textField_3, items,map);
		
		textField_3.setBounds(229, 51, 60, 21);
		textField_3.setColumns(10);
		frame.getContentPane().add(textField_3);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("更多日期");
		Chooser ser3 = Chooser.getInstance("yyyy-MM-dd");
		ser3.register(chckbxNewCheckBox);
		
		chckbxNewCheckBox.setBounds(421, 50, 74, 23);
		frame.getContentPane().add(chckbxNewCheckBox);
		
		JLabel label_3 = new JLabel("发车时间");
		label_3.setBounds(497, 52, 60, 18);
		frame.getContentPane().add(label_3);
		
		JComboBox<Object> comboBox = new JComboBox<Object>();
		comboBox.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		comboBox.setModel(new DefaultComboBoxModel<Object>(new String[] {"00:00—24:00", "00:00—08:00", "08:00—12:00", "12:00—20:00", "20:00—24:00"}));
		comboBox.setBounds(554, 51, 103, 21);
		frame.getContentPane().add(comboBox);
		
		JRadioButton radioButton = new JRadioButton("成人");
		radioButton.setSelected(true);
		radioButton.setBounds(661, 51, 61, 23);
		frame.getContentPane().add(radioButton);
		
		JRadioButton radioButton_1 = new JRadioButton("学生");
		radioButton_1.setBounds(718, 51, 60, 23);
		frame.getContentPane().add(radioButton_1);
		
		JRadioButton radioButton_2 = new JRadioButton("儿童");
		radioButton_2.setBounds(774, 51, 60, 23);
		frame.getContentPane().add(radioButton_2);
		
		JRadioButton radioButton_3 = new JRadioButton("军残");
		radioButton_3.setBounds(832, 51, 54, 23);
		frame.getContentPane().add(radioButton_3);
		
		ButtonGroup btgroup = new ButtonGroup();
		btgroup.add(radioButton);
		btgroup.add(radioButton_1);
		btgroup.add(radioButton_2);
		btgroup.add(radioButton_3);
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("刷票模式");
		chckbxNewCheckBox_1.setBounds(886, 52, 75, 23);
		frame.getContentPane().add(chckbxNewCheckBox_1);
		
		JButton btnNewButton = new JButton("手动查票");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				brushVotes();
			}
		});
		btnNewButton.setBounds(964, 41, 80, 67);
		frame.getContentPane().add(btnNewButton);
		
		JLabel label_4 = new JLabel("车  型");
		label_4.setBounds(43, 90, 41, 18);
		frame.getContentPane().add(label_4);
		
		JCheckBox checkBox = new JCheckBox("高铁");
		checkBox.setSelected(true);
		checkBox.setBounds(193, 88, 49, 23);
		frame.getContentPane().add(checkBox);
		
		JCheckBox checkBox_1 = new JCheckBox("城铁");
		checkBox_1.setSelected(true);
		checkBox_1.setBounds(260, 88, 49, 23);
		frame.getContentPane().add(checkBox_1);
		
		JCheckBox checkBox_2 = new JCheckBox("动车");
		checkBox_2.setSelected(true);
		checkBox_2.setBounds(327, 88, 49, 23);
		frame.getContentPane().add(checkBox_2);
		
		JCheckBox checkBox_3 = new JCheckBox("特快");
		checkBox_3.setSelected(true);
		checkBox_3.setBounds(394, 88, 49, 23);
		frame.getContentPane().add(checkBox_3);
		
		JCheckBox checkBox_4 = new JCheckBox("直达");
		checkBox_4.setSelected(true);
		checkBox_4.setBounds(461, 88, 49, 23);
		frame.getContentPane().add(checkBox_4);
		
		JCheckBox checkBox_5 = new JCheckBox("快车");
		checkBox_5.setSelected(true);
		checkBox_5.setBounds(528, 88, 49, 23);
		frame.getContentPane().add(checkBox_5);
		
		JCheckBox checkBox_6 = new JCheckBox("普客");
		checkBox_6.setSelected(true);
		checkBox_6.setBounds(595, 88, 49, 23);
		frame.getContentPane().add(checkBox_6);
		
		JCheckBox checkBox_7 = new JCheckBox("临客");
		checkBox_7.setSelected(true);
		checkBox_7.setBounds(662, 88, 49, 23);
		frame.getContentPane().add(checkBox_7);
		
		JCheckBox checkBox_9 = new JCheckBox("其它");
		checkBox_9.setSelected(true);
		checkBox_9.setBounds(729, 88, 49, 23);
		frame.getContentPane().add(checkBox_9);
		
		JLabel label_5 = new JLabel("席  别");
		label_5.setBounds(43, 122, 41, 18);
		frame.getContentPane().add(label_5);
		
		JCheckBox checkBox_8 = new JCheckBox("特等");
		checkBox_8.setSelected(true);
		checkBox_8.setBounds(260, 120, 49, 23);
		frame.getContentPane().add(checkBox_8);
		
		JCheckBox checkBox_10 = new JCheckBox("商务");
		checkBox_10.setSelected(true);
		checkBox_10.setBounds(193, 120, 49, 23);
		frame.getContentPane().add(checkBox_10);
		
		JCheckBox checkBox_11 = new JCheckBox("一等");
		checkBox_11.setSelected(true);
		checkBox_11.setBounds(327, 120, 49, 23);
		frame.getContentPane().add(checkBox_11);
		
		JCheckBox checkBox_12 = new JCheckBox("二等");
		checkBox_12.setSelected(true);
		checkBox_12.setBounds(394, 120, 49, 23);
		frame.getContentPane().add(checkBox_12);
		
		JCheckBox checkBox_13 = new JCheckBox("高软");
		checkBox_13.setSelected(true);
		checkBox_13.setBounds(461, 120, 49, 23);
		frame.getContentPane().add(checkBox_13);
		
		JCheckBox checkBox_15 = new JCheckBox("软卧");
		checkBox_15.setSelected(true);
		checkBox_15.setBounds(528, 120, 49, 23);
		frame.getContentPane().add(checkBox_15);
		
		JCheckBox checkBox_16 = new JCheckBox("无座");
		checkBox_16.setSelected(true);
		checkBox_16.setBounds(796, 120, 49, 23);
		frame.getContentPane().add(checkBox_16);
		
		JCheckBox checkBox_17 = new JCheckBox("硬座");
		checkBox_17.setSelected(true);
		checkBox_17.setBounds(729, 120, 49, 23);
		frame.getContentPane().add(checkBox_17);
		
		JCheckBox checkBox_18 = new JCheckBox("软座");
		checkBox_18.setSelected(true);
		checkBox_18.setBounds(662, 120, 49, 23);
		frame.getContentPane().add(checkBox_18);
		
		JCheckBox checkBox_19 = new JCheckBox("硬卧");
		checkBox_19.setSelected(true);
		checkBox_19.setBounds(595, 120, 49, 23);
		frame.getContentPane().add(checkBox_19);
		
		JCheckBox checkBox_14 = new JCheckBox("其它");
		checkBox_14.setSelected(true);
		checkBox_14.setBounds(864, 120, 49, 23);
		frame.getContentPane().add(checkBox_14);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(43, 157, 1001, 237);
		frame.getContentPane().add(scrollPane);
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==e.BUTTON3) {
					model_train.addElement(table.getValueAt(table.getSelectedRow(), 0));
				}
				
				if(e.getClickCount()==2){
					SubmitOrder();
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
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
			},
			new String[] {
				"\u8F66\u6B21", "\u51FA\u53D1\u5730", "\u76EE\u7684\u5730", "\u5386\u65F6", "\u53D1\u8F66\u65F6\u95F4", "\u5230\u8FBE\u65F6\u95F4", "\u5546\u52A1", "\u7279\u7B49", "\u4E00\u7B49", "\u4E8C\u7B49", "\u9AD8\u8F6F", "\u8F6F\u5367", "\u786C\u5367", "\u8F6F\u5EA7", "\u786C\u5EA7", "\u65E0\u5EA7", "\u5176\u5B83", "\u5907\u6CE8"
			}
		));
		setTableSize();
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		JPanel p1 = new JPanel();
		tabbedPane.add(p1,"刷票界面");
		p1.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setBounds(10, 10, 394, 185);
		textArea.setBorder(BorderFactory.createTitledBorder("信息输出: "));
		p1.add(textArea);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(1000), null, null, new Integer(100)));
		spinner.setBounds(496, 53, 53, 22);
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
		checkBox_20.setSelected(true);
		checkBox_20.setBounds(662, 53, 91, 23);
		p1.add(checkBox_20);
		
		JCheckBox checkBox_21 = new JCheckBox("定时启动");
		checkBox_21.setBounds(763, 53, 81, 23);
		p1.add(checkBox_21);
		
		JLabel label_9 = new JLabel("乘车信息：");
		label_9.setBounds(435, 21, 69, 15);
		p1.add(label_9);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerDateModel(new Date(1477411200000L), null, null, Calendar.MINUTE));
		spinner_1.setBounds(844, 53, 130, 22);
		p1.add(spinner_1);
		
		JLabel label_10 = new JLabel("深圳——咸宁，2016-10-26，成人票");
		label_10.setBounds(495, 21, 198, 15);
		p1.add(label_10);
		
		JLabel label_11 = new JLabel("车    次：");
		label_11.setBounds(436, 96, 62, 15);
		p1.add(label_11);
		
		JButton button = new JButton("席别");
		button.setBounds(911, 96, 73, 40);
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
		
		JButton button_1 = new JButton("乘车人");
		button_1.setBounds(911, 155, 73, 40);
		p1.add(button_1);
		
		list_1 = new JList<Object>(model_train);
		list_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (list_1.getSelectedIndex()>-1) {
					model_train.remove(list_1.getSelectedIndex());
				}
			}
		});
		list_1.setVisibleRowCount(2);
		list_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_1.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list_1.setBounds(496, 87, 404, 32);
		p1.add(list_1);
		
		
		list_2 = new JList<Object>();
		list_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (list_2.getSelectedIndex()>-1) {
					DefaultListModel<Object> model_Seats = new DefaultListModel<Object>();
					for (int i =0;i<list_2.getModel().getSize();i++) {
						if (i!=list_2.getSelectedIndex()) {
							model_Seats.addElement(list_2.getModel().getElementAt(i));
						}
					}
					list_2.setModel(model_Seats);
				}
			}
		});
		list_2.setVisibleRowCount(2);
		list_2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_2.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list_2.setBounds(496, 125, 404, 32);
		p1.add(list_2);
		
		list_3 = new JList<Object>();
		list_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (list_3.getSelectedIndex()>-1) {
					DefaultListModel<Object> model_Passenger = new DefaultListModel<Object>();
					for (int i =0;i<list_3.getModel().getSize();i++) {
						if (i!=list_3.getSelectedIndex()) {
							model_Passenger.addElement(list_3.getModel().getElementAt(i));
						}
					}
					list_3.setModel(model_Passenger);
				}
			}
		});
		list_3.setVisibleRowCount(2);
		list_3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_3.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list_3.setBounds(496, 162, 404, 32);
		p1.add(list_3);
		
		PopList.initPopup(button,list_2, pupModel);
		PopList.initPopup(button_1,list_3, pupModel);
		
		JPanel p3 = new JPanel();
		tabbedPane.add(p3,"消息提醒");
		JPanel p2 = new JPanel();
		tabbedPane.add(p2,"订单界面");
		JPanel p4 = new JPanel();
		tabbedPane.add(p4,"其它功能");
		GroupLayout gl_p4 = new GroupLayout(p4);
		gl_p4.setHorizontalGroup(
			gl_p4.createParallelGroup(Alignment.LEADING)
				.addGap(0, 996, Short.MAX_VALUE)
		);
		gl_p4.setVerticalGroup(
			gl_p4.createParallelGroup(Alignment.LEADING)
				.addGap(0, 205, Short.MAX_VALUE)
		);
		p4.setLayout(gl_p4);
		tabbedPane.setBounds(43, 404, 1001, 234);
		frame.getContentPane().add(tabbedPane);
		
		JCheckBox checkBox_22 = new JCheckBox("全部车次");
		checkBox_22.setSelected(true);
		checkBox_22.setBounds(103, 88, 85, 23);
		frame.getContentPane().add(checkBox_22);
		
		JCheckBox checkBox_23 = new JCheckBox("全部席别");
		checkBox_23.setSelected(true);
		checkBox_23.setBounds(103, 120, 85, 23);
		frame.getContentPane().add(checkBox_23);
		
		JLabel lblNewLabel = new JLabel(new ImageIcon("arrow.png"));
		lblNewLabel.setText("◄►");
		lblNewLabel.setBounds(151, 54, 27, 15);
		frame.getContentPane().add(lblNewLabel);
		
		frame.addMouseListener(new MouseAdapter(){  //匿名内部类，鼠标事件
            public void mouseClicked(MouseEvent e){   //鼠标完成点击事件
                if(e.getButton() == MouseEvent.BUTTON1){ //e.getButton就会返回点鼠标的那个键，左键还是右健，3代表右键
                    int x = e.getX();  //得到鼠标x坐标
                    int y = e.getY();  //得到鼠标y坐标
                    mouse_gis[0] = x;
                    mouse_gis[1] = y;
                }
            }
        });
	}
	
	/**
	 * 刷票，点击查票按钮后，开始查询车票信息，并填入到表格中
	 */
	private void brushVotes(){
		// 验证出发地、目的地、时间等是否填写，没有则不刷票
		if ("".equals(textField)) {
			System.out.println("请输入出发地");
			return;
		}
		if ("".equals(textField_3)) {
			System.out.println("请输入目的地");
			return;
		}
		if ("".equals(textField_2)) {
			System.out.println("请输入日期");
			return;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("leftTicketDTO.train_date=");
		sb.append(textField_2.getText()+"&");
		sb.append("leftTicketDTO.from_station=");
		sb.append(map.get(textField.getText())+"&");
		sb.append("leftTicketDTO.to_station=");
		sb.append(map.get(textField_3.getText())+"&");
		sb.append("purpose_codes=ADULT");
		
		//开始刷票
		VHttpGet get = new VHttpGet("https://kyfw.12306.cn/otn/leftTicket/queryX?"+sb.toString());
		VHttpResponse res = Browser.execute(get);
		String body = HttpUtils.outHtml(res.getBody());
		try {
			disposeTicketInfo(body);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * 处理刷票返回信息
	 */
	private void disposeTicketInfo(String body)throws JSONException{
		JSONObject json = new JSONObject(body);
		JSONArray jsonArr = new JSONArray(json.get("data").toString());
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
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
		for (int i=0;i<jsonArr.length();i++) {
			JSONObject obj = (JSONObject)jsonArr.get(i);
			JSONObject obj2 = new JSONObject(obj.get("queryLeftNewDTO").toString());
			obj2.put("secretStr", obj.get("secretStr").toString());
			datalist.add(obj2);
			addRow(obj2);
		}
		setTableSize();
	}
	
	/**
	 * 添加一行数据
	 * @param obj
	 */
	private void addRow(JSONObject obj){
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
			}else {
				vector.add("不可预订");
			}
			model.addRow(vector);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置表格样式
	 */
	private void setTableSize(){
		//定义表格列宽
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
		table.setToolTipText("");
	}
	
	/**
	 * 提交订单
	 */
	private void SubmitOrder(){
		JSONObject obj = datalist.get(table.getSelectedRow());
		try {
			//预订车票
			textArea.append(format.format(new Date())+"：开始提交订票信息\r\n");
			VHttpPost post = new VHttpPost("https://kyfw.12306.cn/otn/leftTicket/submitOrderRequest");
			VParames parames = new VParames();
			parames.clear();
			parames.put("secretStr", obj.get("secretStr").toString());
			parames.put("train_date", textField_2.getText());
			parames.put("back_train_date", textField_2.getText());
			parames.put("tour_flag", "dc");
			parames.put("purpose_codes", "ADULT");
			parames.put("query_from_station_name", obj.get("from_station_name").toString());
			parames.put("query_to_station_name", obj.get("to_station_name").toString());
			parames.put("undefined", "");
			post.setParames(parames);
			VHttpResponse res = Browser.execute(post);
			String body = HttpUtils.outHtml(res.getBody());
			JSONObject res_obj = new JSONObject(body);
			if ("true".equals(res_obj.get("status").toString())) {
				textArea.append(format.format(new Date())+"：订票信息提交成功\r\n");
			}else {
				textArea.append(format.format(new Date())+res_obj.get("messages").toString()+"\r\n");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
}
