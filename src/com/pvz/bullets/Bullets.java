package com.pvz.bullets;

import java.awt.Graphics;
import java.awt.Image;

public abstract class Bullets {
    Image image;
    float speed;
    float x;
    float y;
    int width;
    int height;
    int damage;

    public Bullets(float x, float y) {
        this.x = x;
        this.y = y;
        this.width = 50;
        this.height = 50;
    }

    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, width, height, null);
    }

}
