package com.pvz.bullets;

import java.awt.Graphics;

public class PeaBullet extends Bullets {
    public PeaBullet(float x, float y,int row) {
        super(x, y,row);
        this.speed = 5;
        this.damage = 10;
    }


    @Override
    public void draw(Graphics g) {
        super.draw(g);
    }



}
