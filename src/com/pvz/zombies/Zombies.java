package com.pvz.zombies;

import java.awt.Graphics;
import java.awt.Image;

public abstract class Zombies {

    public  int x;
    public  int y;
    public  int row;
    public  int column;
    public  int health;
    public  int speed;
    public  int damage;
    public  boolean isAlive;
    public  Image image;
    public int width;

    public Zombies(int x, int y, int row) {
        this.x = x;
        this.y = y;
        this.row = row;
        this.isAlive = true;
        loadImage();
        this.width = image.getWidth(null);
    }

    public  void move(){
        x += speed;
    }

    public abstract void attack();

    public abstract void takeDamage(int damage);

    public boolean isAlive() {
        if (health <= 0) {
            isAlive = false;
        }
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public abstract void loadImage();

    public abstract void draw(Graphics g);

}
