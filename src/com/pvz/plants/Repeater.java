package com.pvz.plants;

import java.awt.*;

import com.pvz.bullets.Bullets;
import com.pvz.bullets.PeaBullet;

public class Repeater extends Plants {
    public Repeater(int x, int y,int row,int column) {
        super(x, y, row, column);
        this.attackInterval = 1000; // Attack interval in milliseconds
    }

    @Override
    public void grow(Graphics g) {
        g.drawImage(image[0], x-width/2, y-height/2, width, height, null);
    }

    @Override
    public void update() {
        // Update logic for Repeater plant
    }

    @Override
    public Bullets attack() {
       return new PeaBullet(x, y,row);
    }

}
