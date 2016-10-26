package test.t_12306;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class login {

	private JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login window = new login();
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
	public login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 459, 450);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(123, 23, 230, 33);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("用户名");
		lblNewLabel.setBounds(61, 32, 54, 15);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel label = new JLabel("密  码");
		label.setBounds(61, 76, 54, 15);
		frame.getContentPane().add(label);
		
		JButton btnNewButton = new JButton("登录");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("登录事件");
			}
		});
		btnNewButton.setBounds(100, 368, 210, 23);
		frame.getContentPane().add(btnNewButton);
		
		JLabel label_1 = new JLabel("验证码");
		label_1.setBounds(61, 114, 54, 15);
		frame.getContentPane().add(label_1);
		
		JButton button = new JButton("刷新");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("刷新事件");
			}
		});
		button.setBounds(123, 110, 104, 23);
		frame.getContentPane().add(button);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(123, 67, 230, 33);
		frame.getContentPane().add(passwordField);
	}
}
