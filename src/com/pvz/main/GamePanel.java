package com.pvz.main;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.pvz.bullets.Bullets;
import com.pvz.bullets.SunBullet;
import com.pvz.plants.Plants;
import com.pvz.plants.SunFlower;
import com.pvz.seedpacket.SeedPackets;
import com.pvz.shovel.Shovel;
import com.pvz.ui.BackGround;
import com.pvz.ui.Cube;
import com.pvz.ui.SeedBank;
import com.pvz.zombies.BuckedtheadZombie;
import com.pvz.zombies.ConeheadZombie;
import com.pvz.zombies.FlageZombie;
import com.pvz.zombies.Zombies;





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

	//UI
	public BackGround backGround = new BackGround();
	public static SeedBank seedBank = new SeedBank(0,0);
	
	//数据
	public static int sunCount = 500;
	public int sunCountMax = 9999;

	private int countdownSeconds;
	private boolean isCountingDown = true;
	private Timer countdownTimer;

	private JFrame frame;


	//状态
	public boolean isSelected = false;
	public boolean isPlanting = false;

	public int currentMousex;
	public int currentMousey;

	Image plantingImage = null;
	public static Cube[][] cubes = new Cube[5][9];
	Plants currentPlant=null;

	public static List<List<Bullets>> bullets = new ArrayList<List<Bullets>>();
	public static List<List<Zombies>> zombies = new ArrayList<List<Zombies>>();
	public static List<SunBullet> sun = new ArrayList<SunBullet>();


	private Timer zombieSpawnTimer;
	private final int ZOMBIE_SPAWN_INTERVAL = 20000; // 5 seconds


		// 添加难度相关常量
	public static final int TEST = -1;
	public static final int EASY = 0;
	public static final int NORMAL = 1;
	public static final int HARD = 2;

	// 添加当前难度变量
	private int difficulty = TEST;

	public Font origingalFont = null;
	public Color origingalColor = null;

	public GameTimer gameTimer;
	public long finalTime;

	public Shovel shovel;


	public GamePanel(JFrame frame) {
		
		this.frame = frame;

		this.shovel = new Shovel();

		for(int i=0;i<5;i++) {
			for(int j=0;j<9;j++) {
				cubes[i][j] = new Cube(i,j);
			}
		}

		for(int i=0;i<5;i++) {
			bullets.add(new ArrayList<Bullets>());
		}

		for(int i=0;i<5;i++) {
			zombies.add(new ArrayList<Zombies>());
		}

		this.addMouseListener(this);

		this.addMouseMotionListener(this);
		this.setFocusable(true);

		
		initCountdownTimer();
		initSpawnZombieTimer();

		

		Thread t = new Thread(this);
		t.start();
		
		
	}

	public void paint(Graphics g) {
    
		super.paint(g);

		origingalFont = g.getFont();
		origingalColor = g.getColor();

		// 画背景
		drawBackGround(g);

		if(state==RUNNING) {
			//画倒计时
			drawCountDown(g);
			//画卡槽
			drawSeedBank(g);
			// 画铲子
			drawShovel(g);
			// 画格子中的植物
			drawCubes(g);
			// 画选中后跟随鼠标的植物
			drawMousePlant(g);
			// 画子弹
			drawBullets(g);
			// 画僵尸
			drawZombies(g);
		}
	}
		

	
	
	private void drawShovel(Graphics g) {
		g.drawImage(shovel.bankImage, shovel.bankx, shovel.banky, shovel.bankWidth, shovel.bankHeight, null);
		if(shovel.isClicked) {
			g.drawImage(shovel.shovelImage, currentMousex-shovel.shovelWidth/2, currentMousey-shovel.shovelHeight/2, shovel.shovelWidth, shovel.shovelHeight, null);
		}
		else{
			g.drawImage(shovel.shovelImage, shovel.shovelx, shovel.shovely, shovel.shovelWidth, shovel.shovelHeight, null);
		}
	}

	public void run() {
		while(true) {
			
			
			setFramSize();
	
			if (state == RUNNING) {

				updateZombies(); // 更新僵尸位置
				
				plantAttack(); // 攻击子弹


				checkBulletZombieCollisions(); // 检查子弹和僵尸的碰撞

				checkZombiePlantCollisions(); // 检查僵尸和植物的碰撞

				cleanDeadPlants(); // 清理已经死亡的植物

				checkGameOver(); // 检查游戏是否结束


			}
			
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

		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// 获得鼠标的坐标
		int Mx = e.getX();
		int My = e.getY();
	
		System.out.println("Mouse Released at: " + Mx + " " + My); // 用于调试
	
		//如果点击开始游戏，将游戏状态改为运行
		if(state == START) {
			int x1 = 415;
			int x2 = 680;
			int y1 = 187;
			int y2 = 275;
			if(Mx >= x1 && Mx <= x2 && My >= y1 && My <= y2) {
				state = RUNNING;
				gameTimer = new GameTimer(); // 假设 GameTimer 是你实现的计时器类
			}
			return;
		}
	
		if(state == RUNNING) {
			// 1. 处理阳光收集的点击
			Iterator<SunBullet> sunIterator = sun.iterator();
			while (sunIterator.hasNext()) {
				SunBullet sunBullet = sunIterator.next();

				if (!sunBullet.isCollected() && sunBullet.isPointInSun(Mx, My)) { 
					sunBullet.collectSun(); // 标记为已收集，并增加阳光计数
					sunCount += 25; // 假设每个阳光值25
					System.out.println("Sun collected. Total sun: " + sunCount);
					break; // 通常一次点击只收集一个阳光
				}
			}
	
			// 2. 处理点击种子槽的逻辑
			if(Mx >= seedBank.x && Mx <= seedBank.x + SeedBank.width && My >= seedBank.y && My <= seedBank.y + SeedBank.height) {
				boolean packetClickedInBank = false;
				for (int i = 0; i < seedBank.seedPackets.length; i++) {
					if (seedBank.seedPackets[i] != null && seedBank.seedPackets[i].contains(Mx, My)) {
						seedBank.handlePacketClick(i); // 调用 SeedBank 的方法来处理点击和状态更新
						packetClickedInBank = true;
						break; 
					}
				}

				// 更新 GamePanel 的种植状态基于 SeedBank 的选中状态
				SeedPackets currentSelectedPacket = seedBank.getSelectedSeedPacket();
				if (currentSelectedPacket != null && currentSelectedPacket.isSelected()) {
					isPlanting = true;
					plantingImage = currentSelectedPacket.getPlantImage();
					// 确保 currentPlant 是一个可以种植的实例，而不是原型。
					// 如果 getPlant() 返回原型，你可能需要 currentSelectedPacket.getPlant().createPlant() 或类似方法。
					currentPlant = currentSelectedPacket.getPlant(); 
					System.out.println("Selected seed packet: " + seedBank.selectedPacketIndex);
				} else {
					isPlanting = false;
					plantingImage = null;
					currentPlant = null;
					if (packetClickedInBank) { // 如果是点击了某个包导致取消选中
						System.out.println("Deselected seed packet or packet not available.");
					}
				}
			} 
	
			else if (isPlanting && plantingImage != null && currentPlant != null) { 
				// 确保 currentPlant 也被正确设置
				Cube nearestCube = getNearestCube(Mx, My);
				if (nearestCube != null && nearestCube.getPlant() == null) {
					int cost = seedBank.getSelectedSeedPacketCost(); // 获取成本
					
					// 检查成本是否有效（不是Integer.MAX_VALUE）
					if (cost == Integer.MAX_VALUE) {
						System.err.println("Cannot plant: Invalid cost for selected seed packet.");
						// 重置种植状态，因为无法获取成本
						isPlanting = false;
						plantingImage = null;
						currentPlant = null;
						seedBank.deselectCurrentPacket(); // 确保 SeedBank 也取消选中
						return;
					}

					if (sunCount >= cost) {
						// 假设 Plants 类有一个 createPlant() 方法来创建新实例，避免复用原型
						Plants plantToPlace = currentPlant; 
						nearestCube.setPlant(plantToPlace);
						plantToPlace.isalive = true;
						plantToPlace.setX(nearestCube.getX());
						plantToPlace.setY(nearestCube.getY());
						
						sunCount -= cost;
						
						// 种植成功后，启动冷却并重置状态
						SeedPackets plantedPacket = seedBank.getSelectedSeedPacket();
						if (plantedPacket != null) {
							// plantedPacket.startCooldown(); // 假设 SeedPackets 有 startCooldown 方法
						}
						seedBank.deselectCurrentPacket(); // 取消 SeedBank 中的选中状态
						
						isPlanting = false;
						plantingImage = null;
						currentPlant = null;
						
						System.out.println("Plant placed successfully.");
					} else {
						System.out.println("Not enough sun (" + sunCount + "/" + cost + ") to plant!");
					}
				} else {
					System.out.println("Cannot plant here: Cell is occupied or invalid.");	
					// 点击无效种植区域，取消种植状态
					isPlanting = false;
					plantingImage = null;
					currentPlant = null;
					seedBank.deselectCurrentPacket(); // 确保 SeedBank 也取消选中
				}
			} else if (isPlanting && (plantingImage == null || currentPlant == null)) {
				// 如果 isPlanting 为 true，但支持种植的变量未设置，说明状态不一致，进行重置
				System.err.println("Planting state inconsistency. Resetting.");
				isPlanting = false;
				plantingImage = null;
				currentPlant = null;
				seedBank.deselectCurrentPacket();
			}

			// 3. 处理铲子点击
			shovel.checkClicked(Mx, My);
			if (shovel.isClicked) {
				// 检查铲子是否点击在植物上
				Cube nearestCube = getNearestCube(Mx, My);
				if (nearestCube != null && nearestCube.getPlant() != null) {
					Plants plantToRemove = nearestCube.getPlant();
					if (plantToRemove != null && plantToRemove.isalive) {
						// 移除植物
						plantToRemove.isalive = false;
						plantToRemove.dispose(); // 释放资源
						nearestCube.setPlant(null); // 从格子中移除植物

						System.out.println("Plant removed");
					}
				}
			}

			
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// 获得鼠标的坐标
		currentMousex = e.getX();
		currentMousey = e.getY();
		// System.out.println(currentMousex+" "+currentMousey);

	}


	private void initCountdownTimer() {
		countdownTimer = new Timer();
		countdownTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (countdownSeconds > 0) {
					countdownSeconds--;
				} else {
					isCountingDown = false;
					countdownTimer.cancel();
				}
			}
		}, 0, 1000); // 每秒更新一次
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
	
	public void plantAttack() {
		// 遍历所有植物，攻击子弹
		for (int i = 0; i < cubes.length; i++) {
			for (int j = 0; j < cubes[i].length; j++) {
				Cube cube = cubes[i][j];
				if (cube.getPlant() != null && cube.getPlant().isalive) {
					Plants plant = cube.getPlant();
					int row = cube.getRow();
					// 检查植物是否可以攻击
					if(plant.canAttack(row)){
						if(plant.getClass().getSimpleName().equals("SunFlower"))
						{
							SunBullet sunBullet = (SunBullet)cube.getPlant().GenerateSun();
							sun.add(sunBullet);


						}
						else{
							List<Bullets> getBullet = cube.getPlant().attack();
							Iterator <Bullets> iterator = getBullet.iterator();
							while(iterator.hasNext()){
								Bullets bullet = iterator.next();
								bullets.get(row).add(bullet);
							}

						}
						plant.setAttackCooldown(); // 设置攻击冷却
					}
				}
			}
		}
	}

	private int getInitialDelayByDifficulty() {
		switch (difficulty) {
			case TEST:
				return 0; // 测试模式20秒
			case EASY:
				return 60000; // 简单模式30秒
			case NORMAL:
				return 30000; // 普通模式20秒
			case HARD:
				return 10000; // 困难模式10秒
			default:
				return 20000;
		}
	}

	private void spawnZombie() {
		int row = (int)(Math.random() * 5); // 随机选择行
		int x = 1000; // 屏幕右侧
		int y = 200 + (row-1) * 130; // 根据行计算y坐标
		
		// 随机选择僵尸类型
		double randomValue = Math.random(); 
		if (randomValue < 0.60) { // 60% 的概率生成 FlageZombie
			zombies.get(row).add(new FlageZombie(x, y, row));
		} else if (randomValue < 0.90) { // 30% 的概率生成 ConeheadZombie (0.60 <= randomValue < 0.90)
			zombies.get(row).add(new ConeheadZombie(x, y, row));
		} else { // 10% 的概率生成 BuckedtheadZombie (0.90 <= randomValue < 1.0)
			zombies.get(row).add(new BuckedtheadZombie(x, y, row));
		}
	}

	public void drawZombies(Graphics g) {
		for (List<Zombies> zombie_row : zombies) {
			Iterator<Zombies> iterator = zombie_row.iterator();
			while (iterator.hasNext()) {
				Zombies zombie = iterator.next();
				zombie.draw(g);
				if (!zombie.isAlive()) {
					iterator.remove();
				}
			}
		}
	}

	public void initSpawnZombieTimer() {
		zombieSpawnTimer = new Timer();
		int initialDelay = getInitialDelayByDifficulty();
		countdownSeconds = initialDelay / 1000;
		
		zombieSpawnTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				spawnZombie();
			}
		}, initialDelay, ZOMBIE_SPAWN_INTERVAL);
	}

	private void checkBulletZombieCollisions() {
		// 遍历每一行
		for (int row = 0; row < bullets.size(); row++) {
			List<Bullets> bulletList = bullets.get(row);
			List<Zombies> zombieList = zombies.get(row);
			
			// 使用迭代器避免并发修改异常
			Iterator<Bullets> bulletIterator = bulletList.iterator();
			while (bulletIterator.hasNext()) {
				Bullets bullet = bulletIterator.next();
				boolean hitZombie = false;
				
				// 检查该行的所有僵尸
				Iterator<Zombies> zombieIterator = zombieList.iterator();
				while (zombieIterator.hasNext() && !hitZombie) {
					Zombies zombie = zombieIterator.next();
					
					// 简单的矩形碰撞检测
					if (bullet.x + bullet.width > zombie.x+(zombie.width/2) && 
						bullet.x < zombie.x + zombie.width) {
						
						// 子弹击中僵尸
						zombie.health -= bullet.damage;
						hitZombie = true;
						bulletIterator.remove();
						bullet.dispose(); // 释放子弹资源
						
						// 检查僵尸是否死亡
						if (zombie.health <= 0) {
							zombie.isAlive = false;
							zombieIterator.remove();
							// 释放僵尸资源
							zombie.dispose();
						}
					}
				}
			}
		}
	}

	public void drawBullets(Graphics g) {
		for (int i = 0; i < bullets.size(); i++) {
			List<Bullets> bulletList = bullets.get(i);
			Iterator<Bullets> iterator = bulletList.iterator();
			while (iterator.hasNext()) {
				Bullets bullet = iterator.next();
				bullet.move();
				bullet.draw(g);
				if (bullet.isOutOfBounds(width, height)) {
					iterator.remove();
					bullet.dispose();
				}
			}
		}

		Iterator<SunBullet> iterator = sun.iterator();
		while (iterator.hasNext()){
			SunBullet sunBullet = iterator.next();
			if (sunBullet.isCollected()) { // 检查阳光是否已被收集
				iterator.remove(); // 从 sun 列表中移除

				sunBullet.dispose(); 
			} else if (sunBullet.ifDisapper) { // 检查阳光是否因超时而消失
				iterator.remove(); // 从 sun 列表中移除

				sunBullet.dispose();
			} else {
				sunBullet.draw(g); // 如果阳光既未被收集也未超时消失，则绘制它
			}
			
			
		}

			
	}

	public void drawMousePlant(Graphics g) {
		if(isPlanting&&plantingImage!=null) {
			// 画植物
			g.drawImage(plantingImage, currentMousex-plantingImage.getWidth(null)/2, currentMousey-plantingImage.getHeight(null)/2, null);
		}
	}

	public void drawCubes(Graphics g) {
		for(Cube[] rowCubes : cubes) {
			for(Cube cube : rowCubes) {
				cube.grow(g);
			}
		}
	}

	public void drawSeedBank(Graphics g) {
		
		g.setColor(origingalColor);
		g.setFont(origingalFont);
		
		g.drawImage(seedBank.getImage(), seedBank.x, seedBank.y, SeedBank.width, SeedBank.height, null);

		g.drawString(""+sunCount, 50, 100);

		seedBank.loadSeedPacket(g);
	}

	public void drawBackGround(Graphics g) {
		g.drawImage(backGround.getImage(state), 0, 0, width, height, null);
	}

	public void drawCountDown(Graphics g) {
	    if (state == RUNNING && isCountingDown) {
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.setColor(Color.RED);
        g.drawString("zombies are coming in: " + countdownSeconds + "s", width/2 - 150, 200);
    }
	}


	public void setFramSize() {
		width = backGround.getWidth(state);
		height = backGround.getHeight(state);

		
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		this.setSize(width, height);
	}

	public void updateZombies(){
		for (int i = 0; i < zombies.size(); i++) {
			List<Zombies> zombie_row = zombies.get(i);
			Iterator<Zombies> iterator = zombie_row.iterator();
			while (iterator.hasNext()) {
				Zombies zombie = iterator.next();
				zombie.move();
				if (!zombie.isAlive()) {
					iterator.remove();
				}
			}
		}
	}

	public void checkZombiePlantCollisions() {
		// 遍历每一行
		for (int row = 0; row < zombies.size(); row++) {
			List<Zombies> zombieList = zombies.get(row);
			
			// 遍历该行的所有僵尸
			for (Zombies zombie : zombieList) {
				boolean isAttacking = false;
				
				// 检查该行的所有植物
				for (int col = 0; col < cubes[row].length; col++) {
					Plants plant = cubes[row][col].getPlant();
					
					// 检查僵尸是否可以攻击植物
					if (plant != null && plant.isalive) {
						// 简单的碰撞检测 - 僵尸到达植物位置
						if (zombie.x+(zombie.width)/2 <= plant.getX() +1 && zombie.x + zombie.width >= plant.getX()) {

							
							zombie.canAttack = true;
							// 僵尸攻击植物
							zombie.attack(plant);

							// 检查植物是否死亡
							if (plant.health <= 0) {
								plant.isalive = false;
								plant.dispose(); // 释放资源
								cubes[row][col].setPlant(null); // 从格子中移除植物
							}

							isAttacking = true;
							break;  // 一次只攻击一个植物
						}
					}
					else if (plant != null && !plant.isalive) {
                    // 清理已经死亡的植物
						plant.dispose();
						cubes[row][col].setPlant(null);
					}
				}


				
				
				// 如果僵尸没有攻击任何植物，确保它在移动
				if (!isAttacking && zombie.speed == 0) {
					zombie.stopAttack();
				}
			}
		}
	}
	
	public void cleanDeadPlants() {
		for (int i = 0; i < cubes.length; i++) {
			for (int j = 0; j < cubes[i].length; j++) {
				Plants plant = cubes[i][j].getPlant();
				if (plant != null && !plant.isalive) {
					plant.dispose(); // 释放资源
					cubes[i][j].setPlant(null); // 从格子中移除植物
				}
			}
		}

	}
	
    public void checkGameOver() {
        // 检查僵尸是否到达左边界
        for (List<Zombies> zombieRow : zombies) {
            for (Zombies zombie : zombieRow) {
                // 如果僵尸到达左边界（例如 x < 50）
                if (zombie.x < 50) {
                    state = GAME_OVER;
                    stopGame();
                    return;
                }
            }
        }
    }


	private void stopGame() {
		// 停止所有计时器
		if (zombieSpawnTimer != null) {
			zombieSpawnTimer.cancel();
			zombieSpawnTimer = null;
			gameTimer.stopGameTimer();
			finalTime = gameTimer.getGameTime();
			System.out.println("Final Time: " + finalTime);
		}
		
		// 停止所有植物和僵尸的动画
		for (int i = 0; i < cubes.length; i++) {
			for (int j = 0; j < cubes[i].length; j++) {
				Plants plant = cubes[i][j].getPlant();
				if (plant != null) {
					plant.dispose();
				}
			}
		}
		
		// 停止所有僵尸
		for (List<Zombies> zombieRow : zombies) {
			for (Zombies zombie : zombieRow) {
				zombie.dispose(); // 假设僵尸类有dispose方法
			}
		}
	}
}


