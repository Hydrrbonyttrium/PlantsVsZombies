package com.pvz.plants;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.pvz.bullets.Bullets;
import com.pvz.main.GamePanel;


public abstract class Plants {
    public int x, y;
    public int width, height;
    public int row,column; // Row and column of the plant
    public int speed = 0; // Speed of the plant
    public int health = 0; // Health of the plant
    public Image[] image;
    public boolean isalive = true; // Flag to indicate if the plant is enabled
    public int attackInterval = 0; // Attack interval for the plant
    private Timer attackTimer; // Timer for the attack interval
    private Boolean canAttack = true; // Flag to indicate if the plant can attack
    
    public Plants(int x, int y,int row,int column) {
        this.row = row;
        this.column = column;
        this.x = x;
        this.y = y;
        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        image = new Image[5];
        for (int i = 0; i < image.length; i++) {
            image[i] = toolkit.getImage(getClass().getClassLoader().getResource("resourse/images/plants/" + getClass().getSimpleName() +"/"+getClass().getSimpleName()+ i + ".png"));
        }
        this.width = image[0].getWidth(null); // Set the width of the plant
        this.height = image[0].getHeight(null); // Set the height of the plant

        initAttackTimer(); // Initialize the attack timer
    }

    public abstract void grow(Graphics g); // Abstract method to draw the plant
    public abstract void update(); // Abstract method to update the plant's state
    public abstract Bullets attack(); // Abstract method to perform the plant's attack

    public void setX(int x) {
        
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
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
    }

    
    
}
