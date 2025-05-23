package com.pvz.plants;

import java.util.List;

import com.pvz.bullets.Bullets;
import com.pvz.bullets.SunBullet;

public class SunFlower extends Plants  {
    public SunFlower(int x, int y, int row, int column, int animationImageCount) {
        super(x, y, row, column,animationImageCount); // Call the superclass constructor with the given x and y coordinates
        this.attackInterval = 20000; // Attack interval in milliseconds
        this.damage = 0; // Damage dealt by the Repeater
        this.health = 100; // Health of the Repeater
        setAttackCooldown();
    }


    @Override
    public SunBullet GenerateSun() {
       return new SunBullet(x, y-25,row);
    }

    @Override
    public boolean canAttack(int row) {
        return canAttack;
    }

    @Override
    public void update() {
        
    }


    @Override
    public List<Bullets> attack() {
        return null; // SunFlower does not attack
    }



}
