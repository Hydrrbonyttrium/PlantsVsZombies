package com.pvz.plants;

import java.awt.Toolkit;

import com.pvz.bullets.Bullets;
import com.pvz.bullets.PeaBullet;
import com.pvz.bullets.SunBullet;

public class SunFlower extends Plants  {
    public SunFlower(int x, int y, int row, int column) {
        super(x, y, row, column,17); // Call the superclass constructor with the given x and y coordinates
        this.attackInterval = 60000; // Attack interval in milliseconds
        this.damage = 0; // Damage dealt by the Repeater
        this.health = 100; // Health of the Repeater
    }


    @Override
    public SunBullet attack() {
       return new SunBullet(x, y-25,row);
    }

    @Override
    public boolean canAttack(int row) {
        return canAttack;
    }

    @Override
    public void update() {
        
    }



}
