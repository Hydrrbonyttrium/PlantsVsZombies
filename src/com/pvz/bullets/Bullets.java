package com.pvz.bullets;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public abstract class Bullets {
    public Image image;
    public float speed;
    public float x;
    public float y;
    public int width;
    public int height;
    public int damage;
    public boolean isHit = false;
    public boolean isDead = false;
    public int row;
    public String path;

    public Bullets(float x, float y,int row) {
        loadImage("resourse/images/Bullets/"+getClass().getSimpleName()+"0.png");
        this.x = x;
        this.y = y;
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }

    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, width, height, null);
    }

    public void loadImage(String path) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        image = toolkit.getImage(getClass().getClassLoader().getResource(path));
    }


    public void move() {
        x += speed;
    }

    public boolean isOutOfBounds(int width, int height) {
        return x < 0 || x > width || y < 0 || y > height;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void Dead() {
        
        isDead = true;
    }

    public void dispose() {
        image = null; // Set the image to null for garbage collection
    }

}
