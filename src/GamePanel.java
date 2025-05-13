import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;



public class GamePanel extends JPanel {

	/**
	 * Create the panel.
	 */

	private static final long serialVersionUID = 1L;
	// 游戏窗口大小，游戏状态
	public static final int WIDTH = 1400;
	public static final int HEIGHT = 600;
	public static final int START = 0;
	public static final int RUNNING =1;
	public static final int GAME_OVER =2;

	// 游戏状态
	public static int state = START;

	
	Image start_Background = this.getToolkit().getImage("images/bg0.jpg");
	Image running_Background = this.getToolkit().getImage("images/bg1.jpg");
	Image gameover_Background = this.getToolkit().getImage("images/bg2.jpg");

	public GamePanel() {

		
		Thread t = new Thread();
		t.start();
		
	}

	public void paint(Graphics g) {
		
		super.paint(g);
		// 画背景
		if(state==START) {
			g.drawImage(start_Background, 0, 0, this);
		}else if(state==RUNNING) {
			g.drawImage(running_Background, 0, 0, this);
		}else if(state==GAME_OVER) {
			g.drawImage(gameover_Background, 0, 0, this);
		}
	}

	void run() {
		while(true) {
			this.repaint();
		}
	}

}
