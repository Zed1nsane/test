package com.qst.dms.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

import com.qst.dms.entity.User;
import com.qst.dms.service.*;
import com.qst.dms.ui.*;

public class LoginFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textUsername;
	private JPasswordField PWD;
	private User user;
	private UserService userService=new UserService();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginFrame() {
		setTitle("登录");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 756, 529);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnLogin = new JButton("登录");
		btnLogin.addActionListener(new LoginListener());
		btnLogin.setFont(new Font("宋体", Font.PLAIN, 30));
		/*btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});*/
		btnLogin.setBounds(226, 327, 102, 54);
		contentPane.add(btnLogin);
		
		JButton btnReset = new JButton("重置");
		btnReset.addActionListener(new ResetListener());
		/*btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});*/
		btnReset.setFont(new Font("宋体", Font.PLAIN, 30));
		btnReset.setBounds(373, 327, 102, 54);
		contentPane.add(btnReset);
		
		JButton btnRegist = new JButton("注册");
		btnRegist.addActionListener(new RegisterListener());
		btnRegist.setFont(new Font("宋体", Font.PLAIN, 30));
		btnRegist.setBounds(510, 327, 102, 54);
		contentPane.add(btnRegist);
		
		JLabel lblUsername = new JLabel("用户名:");
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setFont(new Font("宋体", Font.BOLD, 30));
		lblUsername.setBounds(88, 148, 123, 47);
		contentPane.add(lblUsername);
		
		JLabel lblPWD = new JLabel("密  码:");
		lblPWD.setHorizontalAlignment(SwingConstants.CENTER);
		lblPWD.setFont(new Font("宋体", Font.BOLD, 30));
		lblPWD.setBounds(88, 220, 123, 47);
		contentPane.add(lblPWD);
		
		textUsername = new JTextField();
		textUsername.setFont(new Font("宋体", Font.PLAIN, 30));
		textUsername.setHorizontalAlignment(SwingConstants.LEFT);
		textUsername.setBounds(232, 148, 380, 47);
		contentPane.add(textUsername);
		textUsername.setColumns(10);
		
		PWD = new JPasswordField();
		PWD.setFont(new Font("宋体", Font.PLAIN, 30));
		PWD.setBounds(232, 225, 380, 47);
		contentPane.add(PWD);
	}
	//监听类，负责处理登录按钮
	public class LoginListener implements ActionListener
	{
		//重写actionPerformed()方法，事件处理逻辑
		public void actionPerformed(ActionEvent e)
		{
			//根据用户名查询用户
			user = userService.findUserByName(textUsername.getText().trim());
			//判断用户是否存在
			if(user!=null)
			{
				//判断输入的密码是否正确
				if(user.getPassword().equals(new String(PWD.getPassword())))
				{
					//登陆成功，隐藏登录窗口
					LoginFrame.this.setVisible(false);
					//显示主窗口
					new MainFrame_temp();
				}
				else
				{
					//输出提示信息
					System.out.println("密码错误！请重新输入！");
					JOptionPane.showMessageDialog(null,"密码错误！请重新输入！","错误提示",JOptionPane.ERROR_MESSAGE);
					//清空密码框
					PWD.setText("");
				}
			}
			else
			{
				//输出提示信息
				JOptionPane.showMessageDialog(null,"该用户不存在，请先注册!","错误提示",JOptionPane.ERROR_MESSAGE);
				System.out.println("该用户不存在，请先注册!");
			}
		}
	}
	//监听类，负责处理重置
	public class ResetListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//清空账号密码框内容
			textUsername.setText("");
			PWD.setText("");
		}
	}
	//监听类，负责处理注册
	public class RegisterListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//弹出注册窗口
			new RegistFrame();
		}
	}
}
