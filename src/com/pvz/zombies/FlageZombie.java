package com.pvz.zombies;

import java.awt.Graphics;
import java.awt.Toolkit;

public class FlageZombie  extends Zombies {


    public FlageZombie(int x, int y, int row) {
        super(x, y, row);
        this.speed = -1; // Speed of the FlageZombie

    }

    public void attack() {
        // Implement attack logic here
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            isAlive = false;
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void loadImage() {
        // Load the image for the FlageZombie
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        image = toolkit.getImage(getClass().getClassLoader().getResource("resourse/images/zombies/FlageZombie0.png"));
    }

    public void draw(Graphics g) {
        // Implement drawing logic here
        g.drawImage(image, x, y, null);
    }



}
