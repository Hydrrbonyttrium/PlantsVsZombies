package com.pvz.zombies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

import com.pvz.plants.Plants;



public abstract class Zombies {

    public  int ORIGINAL_SPEED;
    public  int x;
    public  int y;
    public  int row;
    public  int column;
    public  int health;
    public  int speed;
    public  int damage;
    public  int attackInterval;
    public  int maxHealth;
    public  boolean isAlive;
    public  boolean canAttack = false; // Flag to indicate if the zombie can attack
    public  boolean isAttackCooldown = false; // Flag to indicate if the zombie is in attack cooldown
    public  Image image;
    public  Image[] moveImage;
    public  Image[] attackImage;
    public int width;
    public int height;
    public Timer moveAnimationTimer,attackTimer,attackAnimationTimer;
    public int moveIndex = 0;
    public int attackIndex = 0;
    public static final int MOVE_ANIMATION_DELAY = 200;  // 移动动画间隔(毫秒)
    public static final int ATTACK_ANIMATION_DELAY = 100; // 攻击动画间隔(毫秒)
    

    public Zombies(int x, int y, int row) {
        this.x = x;
        this.y = y-10;
        this.row = row;
        this.isAlive = true;
        loadImage();
        this.width = moveImage[0].getWidth(null);
        this.height = moveImage[0].getHeight(null);
        initMoveAnimation(); // Initialize the move animation timer
        loadMoveAnimation(); // Load the move animation images
        
        initAttackTimer(); // Initialize the attack timer
        setAttackCooldown(); // Set the initial attack cooldown

    }

    public void initMoveAnimation() {
        moveAnimationTimer = new Timer();
    }

    public void loadMoveAnimation() {
        // Load the move animation images
        moveAnimationTimer.schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                    image = moveImage[moveIndex];
                    if(moveIndex >= moveImage.length - 1)
                        moveIndex = 0;
                    else
                        moveIndex++;
                }
        }, 0, MOVE_ANIMATION_DELAY); 
    }
    public void initAttackAnimation() {
        attackAnimationTimer = new Timer();
    }

    public void loadAttackAnimation() {
        // Load the attack animation images
        attackAnimationTimer.schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                    image = attackImage[attackIndex];
                    if(attackIndex >= attackImage.length - 1)
                        attackIndex = 0;
                    else
                        attackIndex++;
                }
        }, 0, ATTACK_ANIMATION_DELAY); 
    }





    
    public void attack(Plants plant){
        if (canAttack()) {
            // System.out.println("Zombie is attacking the plant!");
            speed = 0; // Stop moving when attacking

            if(moveAnimationTimer != null) {
                moveAnimationTimer.cancel(); // Stop the move animation
                moveAnimationTimer = null; // Set to null to avoid memory leaks
            }
            if(attackAnimationTimer == null) {
                
                initAttackAnimation(); // Initialize the attack animation timer
                loadAttackAnimation(); // Load the attack animation images
            }

            if(isAttackCooldown) {
                plant.takeDamage(damage);
                setAttackCooldown(); // Set the attack cooldown
            }
        }
    }
    
    public void takeDamage(int damage){
        this.health -= damage;
        if (this.health <= 0) {
            isAlive = false;
        }
    }
    
    public void draw(Graphics g){
        g.drawImage(image, x, y, null);
        drawHealthBar(g); // Draw the health bar above the zombie
    }
    
    public  void move(){
        x += speed;
    }
    public boolean isAlive() {
        if (health <= 0) {
            isAlive = false;
        }
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public  void loadImage(){
        // Load the image for the zombie
        moveImage = new Image[5];
        attackImage = new Image[5];
  
        for (int i = 0; i < moveImage.length; i++) {
            
            moveImage[i] = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("resourse/images/zombies/"+getClass().getSimpleName()+i+".png"));
            
        }
        for (int i = 0; i < attackImage.length; i++) {
            attackImage[i] = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("resourse/images/zombies/"+getClass().getSimpleName()+"Attack"+i+".png"));
        }
    }

    // 初始化攻击计时器的方法
    public void initAttackTimer() {
        attackTimer = new Timer();
    }

    // 设置攻击冷却
    public void setAttackCooldown() {
        isAttackCooldown = false;
        attackTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                isAttackCooldown = true;
            }
        }, attackInterval);
    }

    // 检查是否可以攻击植物
    public boolean canAttack() {
        // 检查僵尸是否与植物碰撞
        return canAttack;
    }
    public boolean getIsAttackCooldown() {
        return isAttackCooldown;
    }

    public void stopAttack() {
        canAttack = false;
        speed = ORIGINAL_SPEED; // 恢复移动速度

        if(attackAnimationTimer != null) {
            attackAnimationTimer.cancel(); // 停止攻击动画
            attackAnimationTimer = null; // 设置为null以避免内存泄漏
        }
        initMoveAnimation(); // 初始化移动动画计时器
        loadMoveAnimation(); // 加载移动动画图像
    }

    public void drawHealthBar(Graphics g) {
        int barWidth = 40;
        int barHeight = 5;
        
        // 计算血条位置
        int barX = x+80;
        int barY = y - 10;
        
        // 绘制血条背景
        g.setColor(Color.GRAY);
        g.fillRect(barX, barY, barWidth, barHeight);
        
        // 计算当前血量对应的血条宽度
        int currentHealthWidth = (int)((double)health / maxHealth * barWidth);
        
        // 根据血量百分比选择颜色
        if (health > maxHealth * 0.6) {
            g.setColor(Color.GREEN);
        } else if (health > maxHealth * 0.3) {
            g.setColor(Color.YELLOW);
        } else {
            g.setColor(Color.RED);
        }
        
        // 绘制当前血量
        g.fillRect(barX, barY, currentHealthWidth, barHeight);
        
        // 绘制血条边框
        g.setColor(Color.BLACK);
        g.drawRect(barX, barY, barWidth, barHeight);
    }

    public void dispose() {
        if (attackTimer != null) {
            attackTimer.cancel();
            attackTimer = null;
        }

        if (moveAnimationTimer != null) {
            moveAnimationTimer.cancel();
            moveAnimationTimer = null;
        }
        if (attackAnimationTimer != null) {
            attackAnimationTimer.cancel();
            attackAnimationTimer = null;
        }
        image = null;
        moveImage = null;
        attackImage = null;
    }
}
