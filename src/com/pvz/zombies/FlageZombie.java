package com.pvz.zombies;

import java.awt.Graphics;
import java.awt.Toolkit;

import com.pvz.plants.Plants;

public class FlageZombie  extends Zombies {


    public FlageZombie(int x, int y, int row) {
        super(x, y, row);
        this.speed = -1; // Speed of the FlageZombie
        this.ORIGINAL_SPEED = speed; // Original speed of the FlageZombie
        this.health = 100; // Health of the FlageZombie
        this.maxHealth = health; // Maximum health of the FlageZombie
        this.damage = 10; // Damage dealt by the FlageZombie
        this.attackInterval = 500; // Attack interval in milliseconds

    }
}
