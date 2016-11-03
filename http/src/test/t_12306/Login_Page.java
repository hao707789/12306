package test.t_12306;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.json.JSONException;
import org.json.JSONObject;

import com.vcode.http.client.VHttpResponse;
import com.vcode.http.client.methods.VHttpGet;
import com.vcode.http.client.methods.VHttpPost;
import com.vcode.http.client.parames.VParames;
import com.vcode.http.utils.Browser;
import com.vcode.http.utils.HttpUtils;

public class Login_Page {

	private JFrame frame;
	private JTextField txtHao;
	private JPasswordField passwordField;
	private StringBuffer code = new StringBuffer();
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login_Page window = new Login_Page();
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
	public Login_Page() {
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
		frame.setBounds(100, 100, 440, 465);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtHao = new JTextField();
		txtHao.setText("hao707789");
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
		
		lblNewLabel_1 = new JLabel(new ImageIcon(getLoginCode()));
		lblNewLabel_1.setBounds(60, 139, 293, 191);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton button = new JButton("刷新");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				code.setLength(0);
				lblNewLabel_1.setIcon(new ImageIcon(getLoginCode()));
			}
		});
		button.setBounds(123, 110, 104, 23);
		frame.getContentPane().add(button);
		
		passwordField = new JPasswordField("loveXIAO707789");
		passwordField.setEchoChar('♫');
		passwordField.setToolTipText("");
		passwordField.setBounds(123, 67, 230, 33);
		frame.getContentPane().add(passwordField);
		
		lblNewLabel_2 = new JLabel("鼠标当前点击位置的坐标是：");
		lblNewLabel_2.setBounds(61, 340, 293, 29);
		frame.getContentPane().add(lblNewLabel_2);
		frame.addMouseListener(new MouseAdapter(){  
            public void mouseClicked(MouseEvent e){
                if(e.getButton() == MouseEvent.BUTTON1){ //e.getButton就会返回点鼠标的那个键，左键还是右健，3代表右键
                    int x = e.getX()-69;  
                    int y = e.getY()-199;  
                    String banner = x + "," + y + ",";
                    code.append(banner);
                    lblNewLabel_2.setText("鼠标当前点击位置的坐标是" + x + "," + y);
                    lblNewLabel_2.setForeground(Color.black);
                }
                if(e.getButton() == MouseEvent.BUTTON3){
                	CheckCode();
                }
            }
        });
		
		JButton btnNewButton = new JButton("登录");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CheckCode();
			}
		});
		btnNewButton.setBounds(61, 366, 303, 51);
		frame.getContentPane().add(btnNewButton);
		ticket_init();
	}
	
	/**
	 * 登录界面，用于获取cookie
	 */
	private void ticket_init(){
		VHttpGet get = new VHttpGet("https://kyfw.12306.cn/otn/login/init");
		VHttpResponse res = Browser.execute(get);	
		res.getEntity().disconnect();							
	}
	
	/**
	 * 获取登录界面验证码
	 * @return
	 */
	public byte[] getLoginCode(){
		VHttpGet get = new VHttpGet("https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew?module=login&rand=sjrand&"+Math.random());
		VHttpResponse res = Browser.execute(get);								//获取验证码
		return HttpUtils.outCode(res.getBody());		
	}
	
	/**
	 * 表单校验
	 * @return
	 */
	private boolean IsChoiceCode(){
		if ("".equals(txtHao.getText())) {
			lblNewLabel_2.setText("✖请填写用户名");
			lblNewLabel_2.setForeground(Color.red);
			return false;
		}
		if (passwordField.getPassword().length==0) {
			lblNewLabel_2.setText("✖请填写密码");
			lblNewLabel_2.setForeground(Color.red);
			return false;
		}
		if (code.length()==0) {
			lblNewLabel_2.setText("✖请选择验证码");
			lblNewLabel_2.setForeground(Color.red);
			return false;
		}
		return true;
	}
	
	/**
	 * 验证验证码是否正确
	 */
	private void CheckCode(){
		
		if (!IsChoiceCode()) {
			return;
		}
		
		String newCode = code.toString();
		newCode = newCode.substring(0,newCode.length()-1);
		code.setLength(0);
		code.append(newCode);
		lblNewLabel_2.setText("当前验证码是："+newCode);
		
		VHttpPost post = new VHttpPost("https://kyfw.12306.cn/otn/passcodeNew/checkRandCodeAnsyn");
		VParames parames = new VParames();
		parames.put("rand", "sjrand");
		parames.put("randCode", newCode);
		post.setParames(parames);							//装配提交的Form
		VHttpResponse res = Browser.execute(post);
		String body = HttpUtils.outHtml(res.getBody());		//将网页内容转为文本
		try {
			JSONObject json = new JSONObject(body);
			JSONObject json2 = new JSONObject(json.get("data").toString());
			if ("1".equals(json2.get("result"))) {
				lblNewLabel_2.setText("验证码正确，开始提交表单");
				res.getEntity().disconnect();
				login();
			}else {
				lblNewLabel_2.setText("✖验证码错误");
				lblNewLabel_2.setForeground(Color.red);
				res.getEntity().disconnect();
				code.setLength(0);
				lblNewLabel_1.setIcon(new ImageIcon(getLoginCode()));
			}
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
	}
	
	public void login(){
		//开始提交登录信息
		VHttpPost post = new VHttpPost("https://kyfw.12306.cn/otn/login/loginAysnSuggest");
		VParames parames = new VParames();
		parames.put("loginUserDTO.user_name", txtHao.getText());
		parames.put("randCode", code.toString());
		parames.put("userDTO.password", new String(passwordField.getPassword()));
		
		post.setParames(parames);
		VHttpResponse res = Browser.execute(post);
		String body = HttpUtils.outHtml(res.getBody());			//将网页内容转为文本
		try {
			JSONObject json = new JSONObject(body);
			if ("true".equals(json.get("status").toString())) {
				JSONObject json2 = new JSONObject(json.get("data").toString());
				if (json2.length()>1 && "Y".equals(json2.get("loginCheck").toString())) {
					lblNewLabel_2.setText("登录成功，正在跳转到主页");
				}else {
					System.out.println(json.get("messages"));
					System.exit(0);
				}
			}else {
				System.out.println(json.get("messages"));
				System.exit(0);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		res.getEntity().disconnect();
		
		//开始第二次登录
		post = new VHttpPost("https://kyfw.12306.cn/otn/login/userLogin");
		VParames parames2 = new VParames();
		parames2.put("_json_att", "");
		post.setParames(parames2);								//装配参数
		res = Browser.execute(post);								//提交登录
		if (res.getEntity().getStaus()==200){
			if (HttpUtils.outHtml(res.getBody()).contains("欢迎您登录中国铁路客户服务中心网站")){		//验证是否登录成功
				lblNewLabel_2.setText("登录成功");
				Home_Page window = new Home_Page();
				window.textArea.append(format.format(new Date())+"：登录成功,欢迎使用V代码抢票工具\r\n");
				frame.dispose();
				window.show(window);
			}else {
				lblNewLabel_2.setText("✖登录失败");
				lblNewLabel_2.setForeground(Color.red);
			}
			res.getEntity().disconnect();
		}
	}
}
