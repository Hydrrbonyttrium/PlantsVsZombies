package com.pvz.main;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.pvz.db.Mysql;

import java.awt.event.MouseAdapter;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GameFrame extends JFrame  {

	private JPanel contentPane;
	private GamePanel gamePanel;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameFrame frame = new GameFrame();
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
	public GameFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 611, 493);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("选项");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("选择难度");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] options = {"简单", "普通", "困难", "测试"};
				// 读取当前 GamePanel 的难度作为对话框的默认选项
				int currentDifficulty = gamePanel != null ? gamePanel.getDifficulty() : GamePanel.NORMAL;
				int defaultOptionIndex = 1; // 默认为普通
				if (currentDifficulty == GamePanel.EASY) defaultOptionIndex = 0;
				else if (currentDifficulty == GamePanel.NORMAL) defaultOptionIndex = 1;
				else if (currentDifficulty == GamePanel.HARD) defaultOptionIndex = 2;
				else if (currentDifficulty == GamePanel.TEST) defaultOptionIndex = 3;

				int choice = JOptionPane.showOptionDialog(GameFrame.this,
						"请选择游戏难度(当前难度为" + options[defaultOptionIndex] + "):",
						"选择难度",
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						options,
						options[defaultOptionIndex]);

				if (choice != JOptionPane.CLOSED_OPTION) {
					int selectedDifficulty;
					switch (choice) {
						case 0: // 简单
							selectedDifficulty = GamePanel.EASY;
							break;
						case 1: // 普通
							selectedDifficulty = GamePanel.NORMAL;
							break;
						case 2: // 困难
							selectedDifficulty = GamePanel.HARD;
							break;
						case 3: // 测试
							selectedDifficulty = GamePanel.TEST;
							break;
						default:
							selectedDifficulty = GamePanel.NORMAL; // 默认为普通
							break;
					}
					if (gamePanel != null) {
						gamePanel.setDifficulty(selectedDifficulty);
						JOptionPane.showMessageDialog(GameFrame.this, 
													"难度已设置为: " + options[choice] + "。\n僵尸生成速度和初始倒计时将更新。", 
													"难度设置", 
													JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
			
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("重新开始");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 // 确认重新开始
				 int choice = JOptionPane.showConfirmDialog(GameFrame.this,
				 "确定要重新开始游戏吗？当前进度将丢失。",
				 "重新开始",
				 JOptionPane.YES_NO_OPTION,
				 JOptionPane.QUESTION_MESSAGE);
		 
				if (choice == JOptionPane.YES_OPTION) {
					restartGame();
				}
		 }

			
		});
		mnNewMenu.add(mntmNewMenuItem_2);
		
		JMenu menu = new JMenu("帮助");
		menuBar.add(menu);
		
		JMenuItem menuItem = new JMenuItem("关于");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				About dialog = new About();
				dialog.setModal(true);
				dialog.setLocationRelativeTo(GameFrame.this);
				dialog.setVisible(true);
			}
		});
		menu.add(menuItem);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("排行榜");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (gamePanel != null) {
					Mysql mysqlInstance = gamePanel.getMysqlInstance();
					if (mysqlInstance != null) {
						LeaderboardDialog leaderboardDialog = new LeaderboardDialog(GameFrame.this, mysqlInstance);
						leaderboardDialog.setVisible(true);
					} else {
						System.err.println("无法打开排行榜：数据库服务未初始化。");
					}
				} else {
					System.err.println("无法打开排行榜：游戏面板未初始化。");

				}
			}
		});
		menu.add(mntmNewMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.gamePanel = new GamePanel(this);
		this.gamePanel.addMouseListener(new MouseAdapter() {
			
		});
		gamePanel.setBounds(0, 0, 820, 540);
		contentPane.add(gamePanel);
		GroupLayout gl_gamePanel = new GroupLayout(gamePanel);
		gl_gamePanel.setHorizontalGroup(
			gl_gamePanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 820, Short.MAX_VALUE)
		);
		gl_gamePanel.setVerticalGroup(
			gl_gamePanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 540, Short.MAX_VALUE)
		);
		this.gamePanel.setLayout(gl_gamePanel);

		// 设置窗口标题
		setTitle("植物大战僵尸");

		// 设置窗口大小
		setSize(800, 533);
		// 设置窗口位置
		setLocationRelativeTo(null);
		// 设置窗口不可调整大小
		// setResizable(false);
		
		
	}
	private void restartGame() {
			// 移除当前的游戏面板
			if (gamePanel != null) {
			contentPane.remove(gamePanel);
			// 停止当前游戏的所有计时器和线程
			gamePanel.stopAllTimers();
		}
		
		// 创建新的游戏面板
		this.gamePanel = new GamePanel(this);
		this.gamePanel.addMouseListener(new MouseAdapter() {
			
		});
		gamePanel.setBounds(0, 0, 820, 540);
		contentPane.add(gamePanel);
		
		// 重新设置布局
		GroupLayout gl_gamePanel = new GroupLayout(gamePanel);
		gl_gamePanel.setHorizontalGroup(
			gl_gamePanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 820, Short.MAX_VALUE)
		);
		gl_gamePanel.setVerticalGroup(
			gl_gamePanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 540, Short.MAX_VALUE)
		);
		this.gamePanel.setLayout(gl_gamePanel);
		this.gamePanel.setDifficulty(gamePanel.getDifficulty());
		
		// 刷新界面
		contentPane.revalidate();
		contentPane.repaint();
		
		// 重新设置窗口大小

		gamePanel.setFramSize();
		setLocationRelativeTo(null);
		
		System.out.println("游戏已重新开始");
	}
}

