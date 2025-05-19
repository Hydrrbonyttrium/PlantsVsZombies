package com.pvz.plants;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.pvz.bullets.Bullets;
import com.pvz.main.GamePanel;
import com.pvz.zombies.Zombies;


public abstract class Plants {
    public int x, y;
    public int width, height;
    public int row,column; // Row and column of the plant
    public int speed = 0; // Speed of the plant
    public int health = 0; // Health of the plant
    public Image image;
    public Image[] animationImage;
    public int animationImageCount;
    public boolean isalive = true; // Flag to indicate if the plant is enabled
    public int attackInterval = 0; // Attack interval for the plant
    private Timer attackTimer; // Timer for the attack interval
    public Timer animationTimer; // Timer for the animation
    public int attackIndex = 0; // Index for the attack animation
    public int ANIMATION_INTERVAL = 150; // Animation interval in milliseconds
    private Boolean canAttack = true; // Flag to indicate if the plant can attack
    public int damage = 0; // Damage dealt by the plant
    
    public Plants(int x, int y,int row,int column,int animationImageCount) {
        this.row = row;
        this.column = column;
        this.x = x;
        this.y = y;
        this.animationImageCount = animationImageCount; // Number of images for the plant
        
        loadImage(); // Load the plant images
        this.width = image.getWidth(null); // Set the width of the plant
        this.height = image.getHeight(null); // Set the height of the plant

        initAttackTimer(); // Initialize the attack timer
        initAnimationTimer();
        loadMoveAnimation(); // Load the move animation images
    }

    public void grow(Graphics g){
        g.drawImage(image, x-width/2, y-height/2, width, height, null);

        drawHealthBar(g);
    } // Abstract method to draw the plant
    public abstract void update(); // Abstract method to update the plant's state
    public abstract Bullets attack(); // Abstract method to perform the plant's attack

    public void initAnimationTimer() {
        animationTimer = new Timer();
    }
    public void loadMoveAnimation(){
        animationTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                attackIndex++;
                
                if (attackIndex >= animationImage.length) {
                    attackIndex = 0;
                }
                image = animationImage[attackIndex];
            }
        }, 0, ANIMATION_INTERVAL); // Adjust the delay as needed
    }

    public void loadImage() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        animationImage = new Image[animationImageCount]; // Assuming there are 3 images for the plant
        for (int i = 0; i < animationImage.length; i++) {
            animationImage[i] = toolkit.getImage(getClass().getClassLoader().getResource("resourse/images/plants/" + getClass().getSimpleName() +"/"+getClass().getSimpleName()+ i + ".png"));
        }
        image = animationImage[0]; // Set the initial image
    }
    public void setX(int x) {
        
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void initAttackTimer() {
        attackTimer = new Timer();
    }

    // 检查是否可以攻击
    public boolean canAttack(int row) {
        
        if(canAttack && GamePanel.zombies.get(row).size()>0)
            return true;
        else
            return false;
    }

    // 设置攻击冷却
    public void setAttackCooldown() {
        if (attackTimer != null) {
            canAttack = false;
            attackTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    canAttack = true;
                }
            }, attackInterval);
        }
    }

    public void dispose() {
        if (attackTimer != null) {
            attackTimer.cancel();
            attackTimer = null;
        }

        if (animationTimer != null) {
            animationTimer.cancel();
            animationTimer = null;
        }
        image = null;
        animationImage = null;
    }

    public void drawHealthBar(Graphics g) {
        if (isalive) {
            int healthBarWidth = 50;  // 血条宽度
            int healthBarHeight = 5;  // 血条高度
            int maxHealth = 100;      // 假设最大生命值为100，根据实际情况调整
            
            // 计算当前血量占比
            float healthRatio = (float) health / maxHealth;
            int currentHealthWidth = (int) (healthBarWidth * healthRatio);
            
            // 血条位置（植物头顶上方）
            int healthBarX = x - healthBarWidth / 2;
            int healthBarY = y - height / 2 - 10;  // 位于植物上方10像素
            
            // 绘制血条背景（灰色）
            g.setColor(Color.GRAY);
            g.fillRect(healthBarX, healthBarY, healthBarWidth, healthBarHeight);
            
            // 根据血量比例选择颜色
            if (healthRatio > 0.6) {
                g.setColor(Color.GREEN);  // 血量高，显示绿色
            } else if (healthRatio > 0.3) {
                g.setColor(Color.YELLOW); // 血量中等，显示黄色
            } else {
                g.setColor(Color.RED);    // 血量低，显示红色
            }
            
            // 绘制当前血量
            g.fillRect(healthBarX, healthBarY, currentHealthWidth, healthBarHeight);
            
            // 绘制血条边框
            g.setColor(Color.BLACK);
            g.drawRect(healthBarX, healthBarY, healthBarWidth, healthBarHeight);
        }
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            isalive = false;
        }
    }

    


}
