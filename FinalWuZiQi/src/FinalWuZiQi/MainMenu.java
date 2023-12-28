package FinalWuZiQi;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

//MainMenu继承自JFrmae,可以使用JFrame内的函数
public class MainMenu extends JFrame {

	public MainMenu() {

//-----------------------------------------三种作战方式-------------------------------------------------------------------------
		JButton singlePlayerButton = new JButton("人机对战");
		singlePlayerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!checkHasLoggedIN()) {
					JOptionPane.showMessageDialog(null, "请先登陆", "提醒", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				showDifficultyOptions();
			}
		});
		
		JButton multiplayerButton = new JButton("好友作战");
		multiplayerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!checkHasLoggedIN()) {
					JOptionPane.showMessageDialog(null, "请先登陆", "提醒", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				openGameBoard3();
			}
		});

		JButton rankedBattleButton = new JButton("自定义作战");
		rankedBattleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!checkHasLoggedIN()) {
					JOptionPane.showMessageDialog(null, "请先登陆", "提醒", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				openGameBoard();
			}
		});

//---------------------------------------------右上角一系列按钮-------------------------------------------------------------------------------
		JButton SignInButton = new JButton("登陆/注册");
		SignInButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showLoginOrRegistrationDialog();
			}
		});

		JButton myselfButton = new JButton("我");
		myselfButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showMyInfoDialog();
			}
		});

		JToggleButton playPauseButton = new JToggleButton("🔇");
		//创建了一个JToggleButton对象，显示为带有"🔇"文本的按钮。
		//JToggleButton是一个带有两种状态（选中和未选中）的切换按钮。用户可以点击这个按钮来切换其状态。
		playPauseButton.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 15));
		//创建了一个JToggleButton对象，显示为带有"🔇"文本的切换按钮。(有的字体eclipse不含有，无法正确显示图标）
		playPauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (playPauseButton.isSelected()) {
					// 按钮被选中，表示开始播放音乐
					Vars.musicPlayer.play("D:/a-software/biancheng/eclipse/workspace/FinalWuZiQi/audio/NKU_SchoolSong.wav");
					playPauseButton.setText("🔊");

				} else {
					// 按钮未选中，表示暂停音乐
					Vars.musicPlayer.stop();
					playPauseButton.setText("🔇");
				}
			}
		});

		JButton leaderboardButton = new JButton("查看排行榜");
		leaderboardButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showLeaderboardDialog();
			}
		});

		JButton myFriendsButton = new JButton("我的好友");
		// 添加我的好友按钮的监听器和相应的逻辑
		myFriendsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showMyFriendsDialog();
			}
		});

//------------------------------------------------------------------右下角按钮-----------------------------------------------------------------
		JButton RulesButton = new JButton("规则");
		RulesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showRulesDialog();
			}
		});

//------------------------------------------------------------------保存UserInfo--------------------------------------------------------------
		// 添加窗口关闭事件监听器
		addWindowListener(new MainMenuWindowListener());

//------------------------------------------------------------------页面布局-----------------------------------------------------------------

		//创建一个面板 CenterPanel，该面板包含一个背景图片，并使用 FlowLayout 布局管理器，该布局在水平和垂直方向都居中对齐，并且在组件之间有一定的水平和垂直间距。
		BackgroundPanel CenterPanel = new BackgroundPanel("D:\\a-software\\biancheng\\eclipse\\workspace\\FinalWuZiQi\\img\\background.png");
		CenterPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 150));
		

		//首选大小是组件在没有受到任何限制的情况下，它希望拥有的大小。然而，实际上，布局管理器可能会根据它的规则和约束对组件的大小进行调整。
		//创建了一个 Dimension 对象 buttonSize，它指定了按钮的宽度和高度，即100x100像素。
		//然后，通过 setPreferredSize 方法将这个首选大小应用到每个按钮上。
		Dimension buttonSize = new Dimension(100, 100); // 调整按钮大小
		singlePlayerButton.setPreferredSize(buttonSize);
		multiplayerButton.setPreferredSize(buttonSize);
		rankedBattleButton.setPreferredSize(buttonSize);

		//每种颜色都用 Color 类的构造函数创建，RGB值分别指定为括号里的数字
		//浅蓝色 
		Color lightBlue = new Color(173, 216, 230); 
		 
		// 浅粉色 
		Color lightPink = new Color(255, 182, 193); 
		 
		//设置按钮的背景色
		 singlePlayerButton.setBackground(lightPink);
		 multiplayerButton.setBackground(lightPink);
		 rankedBattleButton.setBackground(lightPink);
		
		// 将按钮添加到面板
		CenterPanel.add(singlePlayerButton);
		CenterPanel.add(multiplayerButton);
		CenterPanel.add(rankedBattleButton);

		// 将包含按钮的面板添加到中间
		add(CenterPanel, BorderLayout.CENTER);

		// 创建一个面板用于包含右上角的按钮
		BackgroundPanel topRightPanel = new BackgroundPanel("D:\\a-software\\biancheng\\eclipse\\workspace\\FinalWuZiQi\\img\\background.png");
		
		SignInButton.setBackground(lightBlue);
		myselfButton.setBackground(lightBlue);
		playPauseButton.setBackground(lightBlue);
		myFriendsButton.setBackground(lightBlue);
		leaderboardButton.setBackground(lightBlue);
		
		
		topRightPanel.add(SignInButton);
		topRightPanel.add(myselfButton);
		topRightPanel.add(playPauseButton);
		topRightPanel.add(leaderboardButton);
		topRightPanel.add(myFriendsButton);

		// 添加面板到北部
		add(topRightPanel, BorderLayout.NORTH);

		// 创建包含底部右侧按钮的面板
		BackgroundPanel downRightPanel = new BackgroundPanel("D:\\a-software\\biancheng\\eclipse\\workspace\\FinalWuZiQi\\img\\background.png");
		RulesButton.setBackground(lightBlue);
		downRightPanel.add(RulesButton);
		downRightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		add(downRightPanel, BorderLayout.SOUTH);

		setSize(600, 800);
		//指定了窗口的默认大小，即在应用程序启动时，窗口将以这个尺寸显示。
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//指定了窗口的默认大小，即在应用程序启动时，窗口将以这个尺寸显示。
		setLocationRelativeTo(null);
		//表示窗口将居中显示在屏幕上
	}

//--------------------------------------------------------------所有的辅助函数-----------------------------------------------------------------------

//-------------------------------------------------------UserInfo---------------------------------------------------------
	// 在窗口关闭时保存当前用户数据到文件
	private class MainMenuWindowListener extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent e) {
			//用于在主窗口关闭时执行一些操
			saveDataToFile();
		}
	}

	//这个方法实现了将用户数据以文本文件的形式保存到磁盘上。文件的路径为"./info/"目录下，文件名为用户的id。
	//文件中包含用户的基本信息和好友列表。如果文件不存在，会创建新文件；如果文件已存在，会清空内容然后写入最新的用户数据。
	// 把当前用户数据写到其自己的数据文件中，其中好友只储存id，每次登陆时去好友的文件中拿他们的数据（id是死的）
	private void saveDataToFile() {
		//如果用户没有登录（id为null），则直接返回，不执行保存操作。
		if (Vars.userInfo.getId() == null)
			return;
		
		// 使用用户的id值构建文件名，这样每个用户的数据都可以通过其id唯一标识。
		String fileName = Vars.userInfo.getId() + ".txt"; 

		try {
			File file = new File("./info/" + fileName);

			// 如果文件不存在，则创建新文件
			if (!file.exists()) {
				file.createNewFile();
			}

			//全部清空然后全部重写
			// 使用 FileWriter 的第二个参数设置为 false，表示清空文件内容
			try (PrintWriter writer = new PrintWriter(new FileWriter(file, false))) {
				// 写入用户数据到文件
				writer.println(Vars.userInfo.getId());
				writer.println(Vars.userInfo.getPwd());
				writer.println(Vars.userInfo.getUserName());
				writer.println(Vars.userInfo.getAvatarPath());
				writer.println(Vars.userInfo.getRank());
				//如果用户有好友，遍历好友列表，将好友的id写入文件
				for (int i = 0; i < Vars.userInfo.getFriendsSize(); i++) {
					writer.println(Vars.userInfo.getFriendByIndex(i).getId());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 把id.txt中的数据写到Var.userInfo中。ip是不存储到文件中，每次登陆新设置。
	private void writeFileToData(String id, String ip) {

		try (BufferedReader reader = new BufferedReader(new FileReader("./info/" + id + ".txt"))) {

			//按照写入的顺序读取信息并set,即此时登录的人Vars.UserInfo内容存储成功
			Vars.userInfo.setId(reader.readLine());
			Vars.userInfo.setPwd(reader.readLine());
			Vars.userInfo.setUserName(reader.readLine());
			Vars.userInfo.setAvatarAndPath(reader.readLine());
			Vars.userInfo.setRank(Integer.parseInt(reader.readLine()));
			Vars.userInfo.setIp(ip);

			//循环读入好友id
			String friendsId;
			while ((friendsId = reader.readLine()) != null) {
				writeFriendToData(friendsId);
			}

		} catch (IOException exception) {
			// 处理文件读取异常
			exception.printStackTrace();

		}
	}

	// 把一位好友的信息写入Var.userInfo的friends中
	private void writeFriendToData(String friendId) {
		try (BufferedReader reader = new BufferedReader(new FileReader("./info/" + friendId + ".txt"))) {

			String id = reader.readLine();
			reader.readLine();//跳过密码行
			String userName = reader.readLine();
			reader.readLine();//跳过头像路径行
			int rank = Integer.parseInt(reader.readLine());
			Vars.userInfo.addFriend(id, userName, rank);
			//将好友信息添加到Vars.UserInfo的好友列表中

		} catch (IOException exception) {
			// 处理文件读取异常
			exception.printStackTrace();

		}
	}

//-----------------------------------------------自定义作战，打开游戏面板，开始游戏-----------------------------------------------------------------------
	private void openGameBoard() {

		JFrame frame = new JFrame("五子棋大战之自定义作战"); // 设置窗口名字
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//设置窗口关闭操作为 JFrame.DISPOSE_ON_CLOSE，表示当用户关闭窗口时，只关闭当前窗口而不终止整个应用程序。这通常用于子窗口或对话框。
		frame.getContentPane().add(Vars.paintPanel, BorderLayout.CENTER);
		//将窗口的内容窗格设置为包含在 Vars.paintPanel 中的内容。

		frame.setSize(600, 800);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null); // 居中显示

	}

//---------------------------------------------好友作战------------------------------------------------------------
	private void openGameBoard3() {

		JFrame frame = new JFrame("五子棋大战之好友作战"); // 设置窗口名字
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// 将 JFrame 的内容窗格设置为包含来自 Vars 类的 PaintPanel3
		frame.getContentPane().add(Vars.paintPanel3, BorderLayout.CENTER);

		frame.setSize(800, 800);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null); // 居中显示

	}

	// ---------------------------------------------人机作战----------------------------------------------------------
	private void showDifficultyOptions() {
		//创建一个包含两个字符串元素的数组，表示用户可以选择的两个难度级别
		Object[] options = { "低级人机", "高级人机" };
		//使用 JOptionPane.showOptionDialog 方法显示一个选项对话框。用户可以选择"低级人机"或"高级人机"。
		int result = JOptionPane.showOptionDialog(
				this, //  表示父组件，即对话框的父级窗口或组件
				"请选择AI难度级别：", // 是对话框中显示的消息内容
				"人机作战！", //是对话框的标题
				JOptionPane.YES_NO_OPTION, //表示对话框包含"是"和"否"两个选项。
				JOptionPane.QUESTION_MESSAGE,//表示对话框显示一个问号图标，用于表示问题。
				null, // 表示使用默认图标
				options, // 是一个包含用户可以选择的选项的数组
				options[0] // 是默认选中的选项。在你的情况下，将第一个选项"低级人机"设置为默认选中。
		);

		//当用户在对手难度选择对话框中选择了"低级人机"时，showOptionDialog 的结果值将为 JOptionPane.YES_OPTIONs
		if (result == JOptionPane.YES_OPTION) {
			openGameBoard2();
		} else if (result == JOptionPane.NO_OPTION) {
			openGameBoard4();
		}
	}

	private void openGameBoard2() {

		JFrame frame = new JFrame("五子棋大战之人机作战之低级"); // 设置窗口名字
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// 将 JFrame 的内容窗格设置为包含来自 Vars 类的 PaintPanel2
		frame.getContentPane().add(Vars.paintPanel2, BorderLayout.CENTER);

		frame.setSize(600, 800);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null); // 居中显示

	}

	private void openGameBoard4() {

		JFrame frame = new JFrame("五子棋大战之人机作战之高级"); // 设置窗口名字
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// 将 JFrame 的内容窗格设置为包含来自 Vars 类的 PaintPanel2
		frame.getContentPane().add(Vars.paintPanel4, BorderLayout.CENTER);

		frame.setSize(600, 800);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null); // 居中显示

	}

	// ----------------------------------------------------登陆/注册按钮辅助函数-------------------------------------------------------------------------

	// 多层判断，判断是否已登录，登录时判断是否已注册，登录时判断id与密码是否相匹配
	
	// 判断是否已经登陆，登陆了返回true
	private static boolean checkHasLoggedIN() {
		return Vars.userInfo.getId() != null;
	}

	// 注册时判断这个ID是否注册过，注册过返回true
	private boolean checkIdIsValid(String id) {
		//有一个文件，里面存储所有曾经注册过的用户的ID
		try (BufferedReader reader = new BufferedReader(new FileReader("./info/ID_Info.txt"))) {

			String line;
			//逐行读取文件内容
			while ((line = reader.readLine()) != null) {
				//如果找到了与输入的ID相匹配的行，表示ID有效，返回 true
				if (line.equals(id)) {
					return true;
				}
			}
			return false;

		} catch (IOException e) {
			// 处理文件读取异常
			e.printStackTrace();
			return false;
		}
	}

	// ID和Password是否匹配，匹配就返回true
	private boolean checkPwd(String id, String pwd) {

		try (BufferedReader reader = new BufferedReader(new FileReader("./info/" + id + ".txt"))) {
			// 读取第二行数据，跳过id行
			reader.readLine();
			String storedPwd = reader.readLine();

			// 判断密码是否相同
			return pwd.equals(storedPwd);
		} catch (IOException e) {
			// 处理文件读取异常
			e.printStackTrace();
			return false;
		}
	}

	//内部类可以轻松访问其外部类的成员，包括私有成员。
	//在这里，LogInForm 类可以直接访问 MainMenu 类的方法和属性，不需要额外的接口或暴露过多的细节。
	private static class LogInForm extends JFrame {

		private MainMenu mainMenu;

		public LogInForm(MainMenu mainMenu) {
			// 设置窗口标题
			super("登陆");

			this.mainMenu = mainMenu;
			JTextField idTextField = new JTextField("请输入您的ID", 23);
			JTextField pwdTextField = new JTextField("请输入您的密码  ", 23);
			JTextField ipTextField = new JTextField("请输入您的IP地址", 23);

			// 创建标签，空格是为了美观
			JLabel idLabel = new JLabel("   ID:");
			JLabel pwdLabel = new JLabel("密码:");
			JLabel ipLabel = new JLabel("   IP:");

			// 创建登陆按钮
			JButton registerButton = new JButton("登陆");
			// 登陆按钮的事件处理
			registerButton.addActionListener(e -> {
				// 在这里处理注册逻辑，获取文本框中的内容
				String id = idTextField.getText();
				String pwd = pwdTextField.getText();
				String ip = ipTextField.getText();

				//判断id与密码是否匹配
				if (mainMenu.checkIdIsValid(id) && mainMenu.checkPwd(id, pwd)) {
					mainMenu.writeFileToData(id, ip);
					// 登录成功，弹出成功的提示框
					JOptionPane.showMessageDialog(null, "恭喜您登录成功！", "登陆成功", JOptionPane.INFORMATION_MESSAGE);

				} else {
					// 登录失败，弹出失败的提示框
					JOptionPane.showMessageDialog(null, "您的ID或密码错误", "登陆失败", JOptionPane.ERROR_MESSAGE);
				}
				dispose();

			});

			JButton cancelButton = new JButton("取消");
			cancelButton.addActionListener(e -> {
				dispose();
			});

			JPanel panel = new JPanel();
			// 将组件添加到面板
			panel.add(idLabel);
			panel.add(idTextField);
			panel.add(pwdLabel);
			panel.add(pwdTextField);
			panel.add(ipLabel);
			panel.add(ipTextField);
			panel.add(registerButton);
			panel.add(cancelButton);
			//将panel添加到窗口
			add(panel);

			// 设置窗口属性
			setSize(300, 200);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setLocationRelativeTo(null); // 居中显示窗口
			setVisible(true);
		}
	}

	private void showLogInDialog() {
		// 如果已经登陆了一个账户，再起登陆时就把前面账户信息保存了，然后显示一个新的登录框
		if (checkHasLoggedIN())
			saveDataToFile();
		new LogInForm(this);

	}

	private static class RegistrationForm extends JFrame {

		MainMenu mainMenu;

		public RegistrationForm(MainMenu mainMenu) {
			// 设置窗口标题
			super("注册");
			this.mainMenu = mainMenu;
			JTextField idTextField = new JTextField("请输入您的ID", 23);
			JTextField userNameTextField = new JTextField("请输入您的昵称", 23);
			JTextField pwdTextField = new JTextField("请输入您的密码  ", 23);
			JTextField ipTextField = new JTextField("请输入您的IP地址", 23);

			// 创建标签
			JLabel idLabel = new JLabel("   ID:");
			JLabel userNameLabel = new JLabel("昵称:");
			JLabel pwdLabel = new JLabel("密码:");
			JLabel ipLabel = new JLabel("   IP:");

			// 创建提交按钮
			JButton registerButton = new JButton("提交");
			// 注册按钮的事件处理
			registerButton.addActionListener(e -> {
				// 在这里处理注册逻辑，例如获取文本框中的内容
				String id = idTextField.getText();
				String userName = userNameTextField.getText();
				String pwd = pwdTextField.getText();
				String ip = ipTextField.getText();

				if (mainMenu.checkIdIsValid(id)) {
					JOptionPane.showMessageDialog(null, "对不起，当前id已被注册", "注册失败", JOptionPane.ERROR_MESSAGE);
				} else {
					Vars.userInfo.setId(id);
					Vars.userInfo.setUserName(userName);
					Vars.userInfo.setAvatarAndPath("./img/NKU_default.png");
					Vars.userInfo.setRank(50);
					Vars.userInfo.setIp(ip);
					Vars.userInfo.setPwd(pwd);

					try {
						File file = new File("./info/ID_Info.txt");

						// 如果文件不存在，则创建新文件
						if (!file.exists()) {
							file.createNewFile();
						}

						// 使用 FileWriter 的第二个参数设置为 false，表示清空文件内容
						try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
							// 写入用户数据到文件
							writer.println(Vars.userInfo.getId());

						}
					} catch (IOException exception) {
						exception.printStackTrace();
					}

					dispose();
				}

			});

			JButton cancelButton = new JButton("取消");
			cancelButton.addActionListener(e -> {

				dispose();
			});

			JPanel panel = new JPanel();
			// 将组件添加到面板
			panel.add(idLabel);
			panel.add(idTextField);
			panel.add(userNameLabel);
			panel.add(userNameTextField);
			panel.add(pwdLabel);
			panel.add(pwdTextField);
			panel.add(ipLabel);
			panel.add(ipTextField);
			panel.add(registerButton);
			panel.add(cancelButton);
			//添加面板到窗口
			add(panel);

			// 设置窗口属性
			setSize(300, 200);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setLocationRelativeTo(null); // 居中显示窗口
			setVisible(true);
		}
	}

	private void showRegistrationDialog() {
		if (checkHasLoggedIN())
			saveDataToFile();
		new RegistrationForm(this);

	}

	private void showLoginOrRegistrationDialog() {
		String[] options = { "登陆", "注册 " };
		int choice = JOptionPane.showOptionDialog(this, "请选择操作", "登陆/注册", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

		if (choice == 0) {

			showLogInDialog();

		} else {

			showRegistrationDialog();
		}

	}

//-----------------------------------------------------------------我按钮辅助函数--------------------------------------------------------------------
	public static boolean isValidImageUrl(String imageUrl) {
		try {
			// 尝试通过 ImageIO 读取图像
			//read 方法是 ImageIO 类的静态方法，用于读取图像数据。它接受一个 URL 参数，表示图像所在的位置。
			BufferedImage image = ImageIO.read(new URL(imageUrl));

			// 如果图像不为 null，则认为是有效的图像
			return image != null;
		} catch (Exception e) {
			// 发生异常表示图像加载失败，不是有效的图像
			return false;
		}
	}

	private  class myInfoForm extends JFrame {
		myInfoForm() {

			// 创建信息界面
			JDialog myInfoDialog = new JDialog(this, "个人信息", true);
			myInfoDialog.setLayout(new BoxLayout(myInfoDialog.getContentPane(), BoxLayout.Y_AXIS));

			// 初始化信息标签
			JLabel nameLabel = new JLabel("名称: " + Vars.userInfo.getUserName());
			JLabel avatarLabel = new JLabel("头像: ");
			JLabel imageLabel = new JLabel(Vars.userInfo.getAvatar());
			JLabel rankLabel = new JLabel("棋力值: " + Vars.userInfo.getRank());

			// 设置字体样式
			Font font = new Font("宋体", Font.PLAIN, 16);
			nameLabel.setFont(font);
			avatarLabel.setFont(font);
			rankLabel.setFont(font);

			// 添加信息标签到信息窗口
			myInfoDialog.add(nameLabel);
			myInfoDialog.add(avatarLabel);
			myInfoDialog.add(imageLabel);
			myInfoDialog.add(rankLabel);

			// 添加修改信息的按钮
			JButton updateInfoButton = new JButton("修改信息");
			updateInfoButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// 弹出下拉框，让用户选择修改名字或者修改头像
					String[] options = { "修改名字", "修改头像", "修改密码" };
					int choice = JOptionPane.showOptionDialog(myInfoDialog, "请选择要修改的信息", "修改信息",
							JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

					// 处理用户的选择
					if (choice == 0) {
						// 用户选择修改名字
						String newName = JOptionPane.showInputDialog("请输入新的名字");
						if (!newName.isEmpty()) {
							nameLabel.setText("名称: " + newName);
							Vars.userInfo.setUserName(newName);
						} else {
							JOptionPane.showMessageDialog(null, "请输入正确的名称", "输入有误", JOptionPane.INFORMATION_MESSAGE);
						}
					} else if (choice == 1) {
						// 用户选择修改头像
						String newAvatarURL = JOptionPane.showInputDialog("请输入新的头像链接");
						if (isValidImageUrl(newAvatarURL)) {
							// 设置头像标签的图标
							Vars.userInfo.setAvatarAndPath(newAvatarURL);
						} else {
							JOptionPane.showMessageDialog(null, "请输入正确的图像地址", "输入有误", JOptionPane.INFORMATION_MESSAGE);
						}
					} else if (choice == 2) {
						String oldPwd = JOptionPane.showInputDialog("请输入原来的密码");
						if (!checkPwd(Vars.userInfo.getId(), oldPwd)) {
							JOptionPane.showMessageDialog(null, "原密码错误", "输入有误", JOptionPane.INFORMATION_MESSAGE);
						} else {
							String newPwd = JOptionPane.showInputDialog("请输入新的密码");
							if (!newPwd.isEmpty()) {
								Vars.userInfo.setPwd(newPwd);
							} else {
								JOptionPane.showMessageDialog(null, "请输入正确的密码", "输入有误",
										JOptionPane.INFORMATION_MESSAGE);
							}
						}

					} else {
						throw new RuntimeException("修改个人信息时没有选中要求的三个按钮");
					}

				}
			});

			myInfoDialog.add(updateInfoButton);

			// 设置信息窗口的大小和位置
			myInfoDialog.setSize(400, 400);
			myInfoDialog.setLocationRelativeTo(this);

			// 显示信息窗口
			myInfoDialog.setVisible(true);

		}
	}

//一些测试时的优化,在用户未登录或者注册时，点击我按钮，提示先登录
	private void showMyInfoDialog() {
		if (checkHasLoggedIN()) {
			new myInfoForm();
		} else {
			JOptionPane.showMessageDialog(null, "请先登陆", "提醒", JOptionPane.INFORMATION_MESSAGE);
		}
	}

//----------------------------------------------------------------查看排行榜辅助函数------------------------------------------------------------------

	private static class ScrollablePageDialog extends JFrame {
		public ScrollablePageDialog(ArrayList<String> data) {

			// 创建 JList 并设置数据和选择模式
			JList<String> leaderboardList = new JList<>(data.toArray(new String[0]));
			//JList<String>：声明并创建一个 JList 对象，其中的元素类型为 String。
			//data.toArray(new String[0])：将传入的 data 列表转换为数组。JList 的构造函数接受一个数组作为数据源。

			// 设置字体大小和样式
			Font font = new Font("宋体", Font.PLAIN, 20);
			leaderboardList.setFont(font);

			JScrollPane scrollPane = new JScrollPane(leaderboardList);

			// 创建滚动面板，并将 JList 放入其中
			JDialog leaderboardDialog = new JDialog();
			leaderboardDialog.getContentPane().add(scrollPane);
			leaderboardDialog.setSize(800, 600); // 设置对话框大小
			leaderboardDialog.setLocationRelativeTo(null);// 设置对话框位置居中
			leaderboardDialog.setVisible(true);
		}

	}

	private void showLeaderboardDialog() {
//测试时的优化
		if (!checkHasLoggedIN()) {
			JOptionPane.showMessageDialog(null, "请先登陆", "提醒", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		//如果用户已登录，则创建一个用于存储排行榜信息的临时列表 tmp，将排行榜标题和好友信息添加到列表中。
		ArrayList<String> tmp = new ArrayList<>();
		//这个格式化字符串的作用是将字符串 "昵称" 左对齐，输出宽度为30个字符。如果 "昵称" 的长度不足30个字符，将在其右侧用空格进行填充，以满足总宽度为30个字符的输出要求。
		tmp.add(String.format("%-30s", "昵称") + String.format("%-30s", "棋力值"));
		Vars.userInfo.sortByRankDescending();
		for (int i = 0; i < Vars.userInfo.getFriendsSize(); i++) {
			String info = String.format("%-30s", Vars.userInfo.getFriendByIndex(i).getUserName())
					+ String.format("%-30s", Vars.userInfo.getFriendByIndex(i).getRank());
			tmp.add(info);
		}
		new ScrollablePageDialog(tmp);

	}

//---------------------------------------------------------------  我的好友辅助函数---------------------------------------------------------------------

	public class FriendManagementFrame extends JFrame {

		public FriendManagementFrame() {

			JButton listButton = new JButton("好友列表");
			JButton addButton = new JButton("添加好友");
			JButton deleteButton = new JButton("删除好友");

			listButton.addActionListener(e -> {
				handleFriendOperation("好友列表");
			});

			addButton.addActionListener(e -> {
				handleFriendOperation("添加好友");
			});
			deleteButton.addActionListener(e -> {
				handleFriendOperation("删除好友");
			});

			add(listButton);
			add(addButton);
			add(deleteButton);

			//pack() 方法是 JFrame 类的方法之一，用于自动调整窗口的大小，以适应窗口中包含的所有组件的首选大小。
			//这通常在添加所有组件后调用，以确保窗口大小适应其内容。
			pack();
			// 设置布局管理器为FlowLayout，并指定居中对齐
			setLayout(new FlowLayout(FlowLayout.CENTER));
			setSize(400, 100);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setLocationRelativeTo(null);
			setVisible(true);

		}

		private void handleFriendOperation(String selectedOption) {
			if (selectedOption == null)
				return;

			if (selectedOption.equals("添加好友")) {
				String friendId = JOptionPane.showInputDialog(this, "请输入您要添加的用户ID:");
				if (checkIdIsInMyList(friendId)) {
					JOptionPane.showMessageDialog(this, "此人已是您的好友");
				} else if (!checkIdIsValid(friendId)) {
					JOptionPane.showMessageDialog(this, "您输入的ID有误");
				} else if (!checkIdIsInMyList(friendId) && checkIdIsValid(friendId)) {
					addNewFriend(friendId);
					JOptionPane.showMessageDialog(this, "添加成功");
				} else {
				}
			} else if (selectedOption.equals("好友列表")) {
				Vars.userInfo.sortByRankDescending();
				ArrayList<String> tmp = new ArrayList<>();

				tmp.add(String.format("%-30s", "ID") + String.format("%-30s", "昵称") + String.format("%-30s", "棋力值"));
				for (int i = 0; i < Vars.userInfo.getFriendsSize(); i++) {
					String info = String.format("%-30s", Vars.userInfo.getFriendByIndex(i).getId())
							+ String.format("%-30s", Vars.userInfo.getFriendByIndex(i).getUserName())
							+ String.format("%-30s", Vars.userInfo.getFriendByIndex(i).getRank());
					tmp.add(info);
				}

				new ScrollablePageDialog(tmp);

			} else {
				// 删除好友操作
				String friendId = JOptionPane.showInputDialog(this, "请输入好友ID:");
				if (!checkIdIsInMyList(friendId)) {
					JOptionPane.showMessageDialog(this, "此人不是您的好友");
				} else {
					deleteOldFriend(friendId);
					JOptionPane.showMessageDialog(this, "删除成功");
				}
			}

		}

		private boolean checkIdIsInMyList(String id) {
			try (BufferedReader reader = new BufferedReader(
					new FileReader("./info/" + Vars.userInfo.getId() + ".txt"))) {

				String line;
				while ((line = reader.readLine()) != null) {
					if (line.equals(id)) {
						return true;
					}
				}
				return false;

			} catch (IOException e) {
				// 处理文件读取异常
				e.printStackTrace();
				return false;
			}
		}

		private void addNewFriend(String id) {
			String fileName = "./info/" + id + ".txt";

			try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

				reader.readLine();//ID
				reader.readLine();//pwd
				String friendUserName = reader.readLine();
				reader.readLine();//头像
				int friendRank = Integer.parseInt(reader.readLine());

				Vars.userInfo.addFriend(id, friendUserName, friendRank);

			} catch (IOException exception) {
				// 处理文件读取异常
				exception.printStackTrace();

			}
		}

		private void deleteOldFriend(String id) {
			Vars.userInfo.deleteFriend(id);
		}

	}

	private void showMyFriendsDialog() {
		if (!checkHasLoggedIN()) {
			JOptionPane.showMessageDialog(this, "请先登录");
			return;

		}
		new FriendManagementFrame();

	}

//------------------------------------------------------------------规则辅助函数------------------------------------------------------------------------
	private void showRulesDialog() {
		
		// 创建规则界面的内容
		
		//定义了包含 HTML 格式文本的字符串。这里使用了 HTML 标签来定义标题 (<h2>、<h3>)、有序列表 (<ol>)、列表项 (<li>) 和段落 (<p>)。
		String rulesText = "<html>" + "<h2>欢迎来到五子棋的世界！下面是一些说明与规则哦~</h2>" + "<h3>一、说明：</h3>" + "<ol>"
				+ "<li>登录：<br>玩家需要提供有效的账户信息进行登录。包括正确的ID和正确的密码<br>成功登录后，将进入主菜单界面。</li>"
				+ "<li>注册：<br>若没有账户，玩家可选择注册。<br>需提供必要信息如ID，昵称，密码等完成注册。<br>注册成功后，自动登录并进入主菜单。</li>" + "</ol>"
				+ "<h3>游戏主菜单</h3>" + "<ol>"
				+ "<li>人机对战：<br>玩家可选择与电脑对战。<br>提供低级人机和高级人机两个难度选项。<br>游戏进行中，玩家可选择悔棋、重新开始游戏（清空棋盘）或认输（AI获胜）。</li>"
				+ "<li>好友对战：<br>玩家可与好友进行对战。<br>发起挑战的玩家胜利，战力值增加10分，否则战力值减少10分。<br>提供认输按钮。</li>"
				+ "<li>自定义对战：<br>玩家可自行与自己对战。<br>提供悔棋按钮（10秒倒计时内有效）。<br>提供重新开始游戏按钮（清空棋盘重新下棋）、认输按钮（轮到哪一方下棋，点击认输则对方获胜）、复盘按钮（包括上一步按钮和下一步按钮，实现一步一步复盘之前的棋局）。</li>"
				+ "</ol>" + "<h3>我的信息</h3>" + "<ol>" + "<li>查看个人信息：<br>玩家可查看自己的ID、昵称、头像、战力值等信息。</li>"
				+ "<li>修改个人信息：<br>提供修改昵称、修改头像等个人信息的选项。</li>" + "</ol>" + "<h3>我的好友</h3>" + "<ol>"
				+ "<li>查看好友列表：<br>玩家可查看好友的ID、昵称等信息。</li>" + "<li>添加好友：<br>提供添加好友的功能。需要输入好友ID</li>"
				+ "<li>删除好友：<br>玩家可删除好友。需要输入好友ID</li>" + "</ol>" + "<h3>排行榜</h3>" + "<ol>"
				+ "<li>查看排行榜：<br>提供按照战力值从高到低好友排列的排行榜。</li>" + "</ol>" + "<h3>规则：</h3>"
				+ "<p>在横、竖、斜线上先形成五颗相同棋子的一方获胜!</p>" + "</html>";
		JEditorPane rulesEditorPane = new JEditorPane();
		rulesEditorPane.setContentType("text/html");
		//setContentType("text/html") 设置 JEditorPane 的内容类型为 HTML，以便正确解析和显示 HTML 格式的文本。
		//setEditable(false) 设置 JEditorPane 为不可编辑，确保用户无法修改显示的内容。
		rulesEditorPane.setEditable(false);
		rulesEditorPane.setText(rulesText);

		JScrollPane scrollPane = new JScrollPane(rulesEditorPane);
		add(scrollPane);

		// 创建规则界面
		JDialog rulesDialog = new JDialog(this, "游戏规则", true);
		rulesDialog.setLayout(new BorderLayout());
		rulesDialog.add(scrollPane, BorderLayout.CENTER);

		// 设置规则界面的大小和关闭操作
		rulesDialog.setSize(400, 300);
		rulesDialog.setLocationRelativeTo(this);
		rulesDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// 将规则界面设置为可见
		rulesDialog.setVisible(true);


	}

//---------------------------------------------------------主函数----------------------------------------------------------------------------------
	public static void main(String[] args) {
		String currentDirectory = System.getProperty("user.dir");
		System.out.println("Current Working Directory: " + currentDirectory);

		// 创建总界面并显示
		MainMenu mainMenu = new MainMenu();
		mainMenu.setVisible(true);
	}
}
