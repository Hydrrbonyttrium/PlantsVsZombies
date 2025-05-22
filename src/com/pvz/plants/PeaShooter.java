package com.pvz.plants;

// import java.awt;
import java.util.ArrayList;
import java.util.List;

import com.pvz.bullets.Bullets;
import com.pvz.bullets.PeaBullet;
import com.pvz.bullets.SunBullet;

public class PeaShooter extends Plants {
    
    
    public PeaShooter(int x, int y,int row,int column,int animationImageCount) {
        super(x, y, row, column,animationImageCount); // Call the superclass constructor with the given x and y coordinates
        this.attackInterval = 1000; // Attack interval in milliseconds
        this.damage = 10; // Damage dealt by the Repeater
        this.health = 100; // Health of the Repeater

        
    }


    @Override
    public void update() {
        // Update logic for Repeater plant
    }

    @Override
    public List<Bullets> attack() {
       List<Bullets> bulletsFired = new ArrayList<Bullets>();
       bulletsFired.add(new PeaBullet(this.x, this.y - 25, this.row));
       return bulletsFired; // 返回包含一颗子弹的列表
    }


    @Override
    public SunBullet GenerateSun() {
       return null; // PeaShooter does not generate sunq
    }

}
