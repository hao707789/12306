package test.t_12306;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
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
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import com.vcode.http.utils.Chooser;

public class Home_Page {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTable table;
	private JTextField textField_1;
	private JTextField textField_4;
	private int[] mouse_gis = new int[2];
	private JLabel label;
	private static JFrame jf = new JFrame("日期选择");
	private JTextField textField_5;

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
		
		
		
		/**
		 * ComBoTextField 带下拉功能的textField
		 * 使用方法：ComBoTextField.setupAutoComplete(普通输入框, 下拉数据);
		 * textField.setColumns(number);
		 */
		textField = new JTextField();
		ArrayList<String> items = new ArrayList<String>();
        String[] str = new String[]{"aaaaa","bbbbb"};
        for (int i = 0; i < str.length; i++) {
            String item = str[i];
            items.add(item);
        }
        ComBoTextField.setupAutoComplete(textField, items);
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
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"00:00—08:00", "08:00—12:00", "12:00—20:00", "20:00—00:00"}));
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
		JPanel p1 = new JPanel();
		tabbedPane.add(p1,"刷票界面");
		p1.setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 10, 394, 185);
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
		
		textField_1 = new JTextField();
		textField_1.setBounds(497, 162, 404, 32);
		p1.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel label_8 = new JLabel("优先级别：");
		label_8.setBounds(436, 134, 62, 15);
		p1.add(label_8);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(497, 125, 404, 32);
		p1.add(textField_4);
		
		JList list = new JList();
		list.setBounds(950, 69, 25, -19);
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
		label_9.setBounds(436, 28, 69, 15);
		p1.add(label_9);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerDateModel(new Date(1477411200000L), null, null, Calendar.MINUTE));
		spinner_1.setBounds(844, 53, 130, 22);
		p1.add(spinner_1);
		
		JLabel label_10 = new JLabel("深圳——咸宁，2016-10-26，成人票");
		label_10.setBounds(496, 28, 198, 15);
		p1.add(label_10);
		
		JLabel label_11 = new JLabel("车    次：");
		label_11.setBounds(435, 94, 62, 15);
		p1.add(label_11);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(496, 85, 404, 32);
		p1.add(textField_5);
		
		JButton btnNewButton_2 = new JButton("删除");
		btnNewButton_2.setBounds(911, 90, 73, 23);
		p1.add(btnNewButton_2);
		
		JButton button = new JButton("席别");
		button.setBounds(911, 130, 73, 23);
		p1.add(button);
		
		JButton button_1 = new JButton("乘车人");
		button_1.setBounds(911, 167, 73, 23);
		p1.add(button_1);
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
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
