package com.pvz.zombies;

public class ConeheadZombie extends Zombies{
    public ConeheadZombie(int x, int y, int row) {
        super(x, y, row,5,5);
        this.speed = -0.5; // Speed of the FlageZombie
        this.ORIGINAL_SPEED = speed; // Original speed of the FlageZombie
        this.health = 300; // Health of the FlageZombie
        this.maxHealth = health; // Maximum health of the FlageZombie
        this.damage = 10; // Damage dealt by the FlageZombie
        this.attackInterval = 500; // Attack interval in milliseconds

    }
}
