package com.pvz.plants;


import java.util.ArrayList;
import java.util.List;

import com.pvz.bullets.Bullets;
import com.pvz.bullets.PeaBullet;
import com.pvz.bullets.SunBullet;

public class Repeater extends Plants {
    
    
    public Repeater(int x, int y,int row,int column,int animationImageCount) {
        super(x, y, row, column,animationImageCount); // Call the superclass constructor with the given x and y coordinates
        this.attackInterval = 1000; // Attack interval in milliseconds
        this.damage = 10; // Damage dealt by the Repeater
        this.health = 100; // Health of the Repeater

        
    }


    @Override
    public void update() {
        // Update logic for Repeater plant
    }
    /**
     * Repeater 的攻击方法。
     * 一次攻击会发射两颗豌豆子弹。
     * 第二颗子弹在X轴上有一个小的偏移，以模拟连续发射的效果。
     * @return 一个包含两颗 PeaBullet 对象的列表。
     */
    @Override
    public List<Bullets> attack() {
       List<Bullets> bulletsFired = new ArrayList<>();
       
       // 创建第一颗子弹
       // (this.x, this.y - 25) 是子弹的初始位置，与 PeaShooter 一致
       bulletsFired.add(new PeaBullet(this.x, this.y - 25, this.row));
       
       // 创建第二颗子弹
       // 为了模拟双发效果，第二颗子弹在X轴上稍微偏移一点
       // 这个偏移量可以根据你的游戏视觉效果和子弹速度进行调整
       int secondBulletOffsetX = 15; // 例如，偏移15像素
       bulletsFired.add(new PeaBullet(this.x + secondBulletOffsetX, this.y - 25, this.row));
       
       return bulletsFired;
    }


    @Override
    public SunBullet GenerateSun() {
        return null; // Repeater does not generate sun
    }

}
