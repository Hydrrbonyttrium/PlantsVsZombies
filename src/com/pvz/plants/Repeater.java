package com.pvz.plants;

import java.awt.*;

import com.pvz.bullets.Bullets;
import com.pvz.bullets.PeaBullet;

public class Repeater extends Plants {
    
    
    public Repeater(int x, int y,int row,int column,int animationImageCount) {
        super(x, y, row, column,5); // Call the superclass constructor with the given x and y coordinates
        this.attackInterval = 1000; // Attack interval in milliseconds
        this.damage = 10; // Damage dealt by the Repeater
        this.health = 100; // Health of the Repeater
        this.animationImageCount = 5;
        
    }


    @Override
    public void update() {
        // Update logic for Repeater plant
    }

    @Override
    public Bullets attack() {
       return new PeaBullet(x, y-25,row);
    }

}
