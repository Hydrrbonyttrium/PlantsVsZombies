import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;



public class GamePanel extends JPanel implements Runnable ,MouseListener{

	/**
	 * Create the panel.
	 */

	private static final long serialVersionUID = 1L;
	// 游戏窗口大小，游戏状态

	public static final int START = 0;
	public static final int RUNNING =1;
	public static final int GAME_OVER =2;

	private int width = 800;
	private int height = 533;

	// 游戏状态
	public static int state = START;

	public BackGround backGround = new BackGround();

	public static SeedBank seedBank = new SeedBank(0,0);

	private JFrame frame;

	public int sunCount = 0;
	public int sunCountMax = 9999;



	public GamePanel(JFrame frame) {
		
		this.frame = frame;

		this.addMouseListener(this);
		this.setFocusable(true);
		Thread t = new Thread(this);
		t.start();
		
	}

	public void paint(Graphics g) {
    
		super.paint(g);
		// 画背景
		g.drawImage(backGround.getImage(state), 0, 0, width, height, null);
		// 画种子银行
		if(state==RUNNING) {
			
			g.drawImage(seedBank.getImage(), seedBank.x, seedBank.y, (width/4)*3, seedBank.height, null);

			g.drawString(""+sunCount, 90, 100);
		}
		
	}
	
	public void run() {
		while(true) {
			
			width = backGround.getWidth(state);
			height = backGround.getHeight(state);

			
			frame.setSize(width, height);
			frame.setLocationRelativeTo(null);
			this.setSize(width, height);


			this.repaint();
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	



	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		// 获得鼠标的坐标
		int Mx = e.getX();
		int My = e.getY();

		System.out.println(Mx+" "+My);
		//如果点击开始游戏，将游戏状态改为运行
		if(state==START) {
			int x1 = 415;
			int x2 = 680;
			int y1 = 187;
			int y2 = 275;
			if(Mx>=x1&&Mx<=x2&&My>=y1&&My<=y2) {
				state = RUNNING;
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
}
