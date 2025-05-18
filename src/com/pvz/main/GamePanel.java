package com.pvz.main;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.pvz.plants.Plants;
import com.pvz.ui.BackGround;
import com.pvz.ui.Cube;
import com.pvz.ui.SeedBank;



public class GamePanel extends JPanel implements Runnable ,MouseListener,MouseMotionListener{

	/**
	 * Create the panel.
	 */

	private static final long serialVersionUID = 1L;
	// 游戏窗口大小，游戏状态

	public static final int START = 0;
	public static final int RUNNING =1;
	public static final int GAME_OVER =2;

	public static int width = 800;
	public static int height = 533;

	// 游戏状态
	public static int state = START;

	public BackGround backGround = new BackGround();

	public static SeedBank seedBank = new SeedBank(0,0);

	private JFrame frame;

	public static int sunCount = 500;
	public int sunCountMax = 9999;

	public boolean isSelected = false;
	public boolean isPlanting = false;

	public int currentMousex;
	public int currentMousey;

	Image plantingImage = null;
	Cube[][] cubes = new Cube[5][9];
	Plants currentPlant=null;




	public GamePanel(JFrame frame) {
		
		this.frame = frame;

		for(int i=0;i<5;i++) {
			for(int j=0;j<9;j++) {
				cubes[i][j] = new Cube(i,j);
			}
		}

		this.addMouseListener(this);
		this.addMouseMotionListener(this);
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
			
			g.drawImage(seedBank.getImage(), seedBank.x, seedBank.y, SeedBank.width, SeedBank.height, null);

			g.drawString(""+sunCount, 50, 100);

			seedBank.loadSeedPacket(g);

			for(Cube[] rowCubes : cubes) {

				for(Cube cube : rowCubes) {
					cube.grow(g);
				}
			}

			if(isPlanting&&plantingImage!=null) {
				// 画植物
				g.drawImage(plantingImage, currentMousex-plantingImage.getWidth(null)/2, currentMousey-plantingImage.getHeight(null)/2, null);
			}
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
		if(state==RUNNING) {
			// 点击种子槽
			if(Mx>=seedBank.x&&Mx<=seedBank.x+SeedBank.width&&My>=seedBank.y&&My<=seedBank.y+SeedBank.height) {
				// 点击种子槽的植物
				for(int i=0;i<seedBank.seedPackets.length;i++) {
					if(seedBank.seedPackets[i] != null && seedBank.seedPackets[i].contains(Mx, My)) {
						// 选中种子包
						isSelected = true;
						seedBank.seedPackets[i].handleMouseClick(Mx, My);
						System.out.println("选中种子包"+i);

						// 如果种子包被选中，准备种植
						if(seedBank.seedPackets[i].isSelected()) {
							isPlanting = true;
							plantingImage = seedBank.seedPackets[i].getPlantImage();
							currentPlant = seedBank.seedPackets[i].getPlant();
							return;
							
						} else {
							isPlanting = false;
							plantingImage = null;
						}

						// 取消其他种子包的选中状态
						for(int j=0;j<seedBank.seedPackets.length;j++) {
							if(j!=i && seedBank.seedPackets[j] != null) {
								seedBank.seedPackets[j].setSelected(false);
							}
						}
					}
				}
			}
			if(isPlanting&&plantingImage!=null) {
				// 计算植物的坐标
				// int plantX = Mx - plantingImage.getWidth(null) / 2;
				// int plantY = My - plantingImage.getHeight(null) / 2;

				// 检查植物是否可以放置在当前坐标
				Cube nearestCube = getNearestCube(Mx, My);

				if(nearestCube != null && nearestCube.getPlant() == null) {

					nearestCube.setPlant(currentPlant);
					nearestCube.getPlant().isalive = true;
					nearestCube.getPlant().setX(nearestCube.getX());
					nearestCube.getPlant().setY(nearestCube.getY());
					// cubes[row][column].setPlant(currentPlant);
					// cubes[row][column].getPlant().isalive = true;
					// cubes[row][column].getPlant().setX(cubes[row][column].getX());
					// cubes[row][column].getPlant().setY(cubes[row][column].getY());
					// 减去阳光
					sunCount -= seedBank.seedPackets[0].getCost();
					isPlanting = false;
					plantingImage = null;
					currentPlant = null;
					// 取消选中状态
					for(int j=0;j<seedBank.seedPackets.length;j++) {
						if(seedBank.seedPackets[j] != null) {
							seedBank.seedPackets[j].setSelected(false);
						}
				}
					System.out.println("放置植物成功");
				} else {
					System.out.println("该格子已经有植物了"+nearestCube.getX()+" "+nearestCube.getY()+" "+nearestCube.getRow()+" "+nearestCube.getColumn());	
					
				}
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

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		// 获得鼠标的坐标
		
		currentMousex = e.getX();
		currentMousey = e.getY();

		// System.out.println(currentMousex+" "+currentMousey);

	}

	//输入坐标，返回距离最近的cube
	public Cube getNearestCube(int x, int y) {
		Cube nearestCube = null;
		int minDistance = Integer.MAX_VALUE;

		for (Cube[] rowCubes : cubes) {
			for (Cube cube : rowCubes) {
				int distance = cube.getDistance(x, y);
				if (distance < minDistance) {
					minDistance = distance;
					nearestCube = cube;
				}
			}
		}

		return nearestCube;
	}
	
}
