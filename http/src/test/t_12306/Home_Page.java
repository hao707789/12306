package test.t_12306;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextArea;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JList;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Calendar;

public class Home_Page {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTable table;
	private JTextField textField_1;
	private JTextField textField_4;

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

	/**
	 * Create the application.
	 */
	public Home_Page() {
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
		
		textField = new JTextField();
		textField.setBounds(84, 51, 60, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel label = new JLabel("出发地");
		label.setBounds(43, 52, 41, 18);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("目的地");
		label_1.setBounds(154, 52, 41, 18);
		frame.getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("日  期");
		label_2.setBounds(268, 51, 41, 21);
		frame.getContentPane().add(label_2);
		
		textField_2 = new JTextField();
		textField_2.setBounds(308, 51, 97, 21);
		textField_2.setColumns(10);
		frame.getContentPane().add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setBounds(198, 51, 60, 21);
		textField_3.setColumns(10);
		frame.getContentPane().add(textField_3);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("更多日期");
		chckbxNewCheckBox.setBounds(411, 50, 80, 23);
		frame.getContentPane().add(chckbxNewCheckBox);
		
		JLabel label_3 = new JLabel("发车时间");
		label_3.setBounds(497, 52, 60, 18);
		frame.getContentPane().add(label_3);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(554, 51, 97, 21);
		frame.getContentPane().add(comboBox);
		
		JRadioButton radioButton = new JRadioButton("成人");
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
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("刷票模式");
		chckbxNewCheckBox_1.setBounds(886, 52, 75, 23);
		frame.getContentPane().add(chckbxNewCheckBox_1);
		
		JButton btnNewButton = new JButton("手动查票");
		btnNewButton.setBounds(964, 41, 80, 67);
		frame.getContentPane().add(btnNewButton);
		
		JLabel label_4 = new JLabel("车  型");
		label_4.setBounds(43, 90, 41, 18);
		frame.getContentPane().add(label_4);
		
		JCheckBox checkBox = new JCheckBox("高铁");
		checkBox.setBounds(93, 88, 49, 23);
		frame.getContentPane().add(checkBox);
		
		JCheckBox checkBox_1 = new JCheckBox("城铁");
		checkBox_1.setBounds(160, 88, 49, 23);
		frame.getContentPane().add(checkBox_1);
		
		JCheckBox checkBox_2 = new JCheckBox("动车");
		checkBox_2.setBounds(227, 88, 49, 23);
		frame.getContentPane().add(checkBox_2);
		
		JCheckBox checkBox_3 = new JCheckBox("特快");
		checkBox_3.setBounds(294, 88, 49, 23);
		frame.getContentPane().add(checkBox_3);
		
		JCheckBox checkBox_4 = new JCheckBox("直达");
		checkBox_4.setBounds(361, 88, 49, 23);
		frame.getContentPane().add(checkBox_4);
		
		JCheckBox checkBox_5 = new JCheckBox("快车");
		checkBox_5.setBounds(428, 88, 49, 23);
		frame.getContentPane().add(checkBox_5);
		
		JCheckBox checkBox_6 = new JCheckBox("普客");
		checkBox_6.setBounds(495, 88, 49, 23);
		frame.getContentPane().add(checkBox_6);
		
		JCheckBox checkBox_7 = new JCheckBox("临客");
		checkBox_7.setBounds(562, 88, 49, 23);
		frame.getContentPane().add(checkBox_7);
		
		JCheckBox checkBox_9 = new JCheckBox("其它");
		checkBox_9.setBounds(629, 88, 49, 23);
		frame.getContentPane().add(checkBox_9);
		
		JLabel label_5 = new JLabel("席  别");
		label_5.setBounds(43, 129, 41, 18);
		frame.getContentPane().add(label_5);
		
		JCheckBox checkBox_8 = new JCheckBox("特等");
		checkBox_8.setBounds(160, 127, 49, 23);
		frame.getContentPane().add(checkBox_8);
		
		JCheckBox checkBox_10 = new JCheckBox("商务");
		checkBox_10.setBounds(93, 127, 49, 23);
		frame.getContentPane().add(checkBox_10);
		
		JCheckBox checkBox_11 = new JCheckBox("一等");
		checkBox_11.setBounds(227, 127, 49, 23);
		frame.getContentPane().add(checkBox_11);
		
		JCheckBox checkBox_12 = new JCheckBox("二等");
		checkBox_12.setBounds(294, 127, 49, 23);
		frame.getContentPane().add(checkBox_12);
		
		JCheckBox checkBox_13 = new JCheckBox("高软");
		checkBox_13.setBounds(361, 127, 49, 23);
		frame.getContentPane().add(checkBox_13);
		
		JCheckBox checkBox_15 = new JCheckBox("软卧");
		checkBox_15.setBounds(428, 127, 49, 23);
		frame.getContentPane().add(checkBox_15);
		
		JCheckBox checkBox_16 = new JCheckBox("无座");
		checkBox_16.setBounds(696, 127, 49, 23);
		frame.getContentPane().add(checkBox_16);
		
		JCheckBox checkBox_17 = new JCheckBox("硬座");
		checkBox_17.setBounds(629, 127, 49, 23);
		frame.getContentPane().add(checkBox_17);
		
		JCheckBox checkBox_18 = new JCheckBox("软座");
		checkBox_18.setBounds(562, 127, 49, 23);
		frame.getContentPane().add(checkBox_18);
		
		JCheckBox checkBox_19 = new JCheckBox("硬卧");
		checkBox_19.setBounds(495, 127, 49, 23);
		frame.getContentPane().add(checkBox_19);
		
		JCheckBox checkBox_14 = new JCheckBox("其它");
		checkBox_14.setBounds(764, 127, 49, 23);
		frame.getContentPane().add(checkBox_14);
		
		String tableRows[] = {"编号", "名称", "规格", "数量", "原价", "时间"};//表头
		int ROW_MAX = 100; //表格最大行数
		String tableColunms[][] = new String[ROW_MAX][tableRows.length];
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(43, 157, 1001, 237);
		frame.getContentPane().add(scrollPane);
		table = new JTable(tableColunms,tableRows);
		table.setFillsViewportHeight(true);
		table.setSurrendersFocusOnKeystroke(true);
		table.setFont(new Font("微软雅黑", Font.BOLD, 13));
		scrollPane.setViewportView(table);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
			},
			new String[] {
				"\u8F66\u6B21", "\u51FA\u53D1\u5730", "\u76EE\u7684\u5730", "\u5386\u65F6", "\u53D1\u8F66\u65F6\u95F4", "\u5230\u8FBE\u65F6\u95F4", "\u5546\u52A1", "\u7279\u7B49", "\u4E00\u7B49", "\u4E8C\u7B49", "\u9AD8\u8F6F", "\u8F6F\u5367", "\u786C\u5367", "\u8F6F\u5EA7", "\u786C\u5EA7", "\u65E0\u5EA7", "\u5176\u5B83"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.setToolTipText("");
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		JPanel p2 = new JPanel();
		JPanel p4 = new JPanel();
		JPanel p1 = new JPanel();
		tabbedPane.add(p1,"刷票界面");
		p1.setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 10, 394, 185);
		p1.add(textArea);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(1000), null, null, new Integer(100)));
		spinner.setBounds(497, 75, 53, 22);
		p1.add(spinner);
		
		JLabel label_6 = new JLabel("刷票频率：");
		label_6.setBounds(436, 79, 62, 15);
		p1.add(label_6);
		
		JLabel label_7 = new JLabel("乘    客：");
		label_7.setBounds(436, 171, 62, 15);
		p1.add(label_7);
		
		textField_1 = new JTextField();
		textField_1.setBounds(497, 163, 404, 32);
		p1.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel label_8 = new JLabel("优先级别：");
		label_8.setBounds(435, 121, 62, 15);
		p1.add(label_8);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(496, 113, 404, 32);
		p1.add(textField_4);
		
		JList list = new JList();
		list.setBounds(950, 69, 25, -19);
		p1.add(list);
		
		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("部分提交");
		chckbxNewCheckBox_2.setBounds(573, 74, 81, 23);
		p1.add(chckbxNewCheckBox_2);
		
		JCheckBox checkBox_20 = new JCheckBox("无座不提交");
		checkBox_20.setBounds(663, 74, 91, 23);
		p1.add(checkBox_20);
		
		JCheckBox checkBox_21 = new JCheckBox("定时启动");
		checkBox_21.setBounds(764, 74, 81, 23);
		p1.add(checkBox_21);
		
		JButton btnNewButton_1 = new JButton("乘车人");
		btnNewButton_1.setBounds(911, 162, 69, 32);
		p1.add(btnNewButton_1);
		
		JButton button = new JButton("席  别");
		button.setBounds(910, 113, 69, 32);
		p1.add(button);
		
		JLabel label_9 = new JLabel("乘车信息：");
		label_9.setBounds(436, 35, 69, 15);
		p1.add(label_9);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerDateModel(new Date(1477411200000L), null, null, Calendar.MINUTE));
		spinner_1.setBounds(845, 75, 130, 22);
		p1.add(spinner_1);
		JPanel p3 = new JPanel();
		tabbedPane.add(p3,"消息提醒");
		tabbedPane.add(p2,"订单界面");
		tabbedPane.add(p4,"其它功能");
		tabbedPane.setBounds(43, 404, 1001, 234);
		frame.getContentPane().add(tabbedPane);
	}
}
