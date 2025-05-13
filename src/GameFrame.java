import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class GameFrame extends JFrame {

	private JPanel contentPane;
	
	

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
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		GamePanel gamePanel = new GamePanel();
		gamePanel.setBounds(0, 0, 820, 540);
		contentPane.add(gamePanel);

		// 设置窗口标题
		setTitle("植物大战僵尸");

		// 设置窗口大小
		setSize(800, 533);
		// 设置窗口位置
		setLocationRelativeTo(null);
		// 设置窗口不可调整大小
		setResizable(false);

	}
}
