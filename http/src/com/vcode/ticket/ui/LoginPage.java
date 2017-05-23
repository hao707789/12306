package com.vcode.ticket.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.vcode.http.utils.VBrowser;
import com.vcode.ticket.methods.LoginMethods;
import com.vcode.ticket.utils.ComBoTextField;
import com.vcode.ticket.utils.ConfigUtils;
import javax.swing.JCheckBox;

/**
 * 登录界面
 * @author Administrator
 *
 */
public class LoginPage {

	public JFrame frame;
	public JTextField txtHao;
	public JPasswordField passwordField;
	public JLabel lblNewLabel_1;
	public JLabel lblNewLabel_2;
	public JCheckBox checkBox;
	public SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	private static LoginMethods loginMethods;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage window = new LoginPage();
					loginMethods = new LoginMethods(window);
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
	public LoginPage() {
		VBrowser.getInstance();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		frame = new JFrame("欢迎使用V代码抢票工具——Login");
		frame.setBounds(100, 100, 440, 465);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtHao = new JTextField();
		txtHao.setBounds(123, 23, 230, 33);
		frame.getContentPane().add(txtHao);
		txtHao.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("用户名");
		lblNewLabel.setBounds(61, 32, 54, 15);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel label = new JLabel("密  码");
		label.setBounds(61, 76, 54, 15);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("验证码");
		label_1.setBounds(61, 114, 54, 15);
		frame.getContentPane().add(label_1);
		
		final JComponent p3 = (JComponent) frame.getLayeredPane();
		
		lblNewLabel_1 = new JLabel(new ImageIcon(LoginMethods.getLoginCode()));
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==e.BUTTON1) {
					ImageIcon icon = new ImageIcon(LoginPage.class.getResource("../image/12306.jpg"));
					final JLabel jb3 = new JLabel(icon);
					jb3.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							p3.remove(jb3);
							p3.repaint();
						}
					});
					p3.add(jb3, new Integer(-3)); // 将按钮jb3，放置在内容面板之下
					jb3.setSize(icon.getIconWidth(), icon.getIconHeight());
					jb3.setLocation(e.getX()+60-(icon.getIconWidth()/2), e.getY()+139-(icon.getIconHeight()/2));
				}else if(e.getButton() == MouseEvent.BUTTON3){
                	loginMethods.CheckCode();
                }
			}
		});
		lblNewLabel_1.setBounds(60, 139, 293, 191);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton button = new JButton("刷新");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNewLabel_1.setIcon(new ImageIcon(LoginMethods.getLoginCode()));
				JComponent p3 = (JComponent)frame.getLayeredPane();
				Component[] cons = p3.getComponents();
				for (Component con : cons) {
					if (con instanceof JLabel) {
						p3.remove(con);
					}
				}
				frame.repaint(); 
			}
		});
		button.setBounds(123, 110, 104, 23);
		frame.getContentPane().add(button);
		
		passwordField = new JPasswordField("");
		passwordField.setEchoChar('♫');
		passwordField.setToolTipText("");
		passwordField.setBounds(123, 67, 230, 33);
		frame.getContentPane().add(passwordField);
		
		lblNewLabel_2 = new JLabel("提示：选择完验证码后，右击同样可以提交哦");
		lblNewLabel_2.setBounds(61, 340, 337, 29);
		lblNewLabel_2.setForeground(Color.blue);
		frame.getContentPane().add(lblNewLabel_2);
		frame.addMouseListener(new MouseAdapter(){  
            public void mouseClicked(MouseEvent e){
                if(e.getButton() == MouseEvent.BUTTON3){
                	loginMethods.CheckCode();
                }
            }
        });
		
		JButton btnNewButton = new JButton("登录");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginMethods.CheckCode();
			}
		});
		btnNewButton.setBounds(61, 366, 303, 51);
		frame.getContentPane().add(btnNewButton);
		
		//记住用户
		List<String> items = new ArrayList<String>();
		Map<String, String> map = null;
		try {
			map = ConfigUtils.getInstace().map;
		} catch (Exception e1) {
			lblNewLabel_2.setText("配置读取文件失败");
			lblNewLabel_2.setForeground(Color.red);
			e1.printStackTrace();
		}
		Set<String> set = map.keySet();
		Iterator<String> it = set.iterator();
		while(it.hasNext()){
			items.add(it.next());
		}
        ComBoTextField.setupAutoComplete(txtHao,passwordField,items);
        
        checkBox = new JCheckBox("记住");
        checkBox.setSelected(true);
        checkBox.setBounds(354, 29, 116, 21);
        frame.getContentPane().add(checkBox);
		LoginMethods.ticket_init();
	}
}
