package com.pvz.plants;

import java.awt.Toolkit;
import java.awt.Image;
import java.util.List;

import com.pvz.bullets.Bullets;
import com.pvz.bullets.SunBullet;



public class WallNut extends Plants  {
    Image[] animationImageHigh = null;
    Image[] animationImageMid = null;
    Image[] animationImageLow = null;
    public WallNut(int x, int y, int row, int column, int animationImageCount) {

        super(x, y, row, column,animationImageCount); // Call the superclass constructor with the given x and y coordinates
        
        
        this.attackInterval = 20000; // Attack interval in milliseconds
        this.damage = 0; // Damage dealt by the Repeater
        this.health = 500; // Health of the Repeater
        this.maxHealth = health; // Maximum health of the Repeater
        this.canAttack = false; // WallNut does not attack
        setAttackCooldown();
    }

    @Override
    public void loadImage() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        animationImageCount = 16;
        animationImageHigh = new Image[animationImageCount]; // Assuming there are 3 images for the plant
        for (int i = 0; i < animationImageHigh.length; i++) {
            animationImageHigh[i] = toolkit.getImage(getClass().getClassLoader().getResource("resource/images/plants/WallNut/idleH/idleH_" + i + ".png"));
        }
        
        animationImageCount = 11;
        animationImageMid = new Image[animationImageCount]; // Assuming there are 3 images for the plant
        for (int i = 0; i < animationImageMid.length; i++) {
            animationImageMid[i] = toolkit.getImage(getClass().getClassLoader().getResource("resource/images/plants/WallNut/idleM/idleM_" + i + ".png"));
        }
        
        animationImageCount = 15;

        animationImageLow = new Image[animationImageCount]; // Assuming there are 3 images for the plant
        for (int i = 0; i < animationImageLow.length; i++) {
            animationImageLow[i] = toolkit.getImage(getClass().getClassLoader().getResource("resource/images/plants/WallNut/idleL/idleL_" + i + ".png"));
        }

        image = animationImageHigh[0]; // Set the initial image
        animationImage = animationImageHigh; // Set the initial image
    }


    @Override
    public SunBullet GenerateSun() {
       return null; // WallNut does not generate sun
    }

    @Override
    public boolean canAttack(int row) {
        return false;
    }

    @Override
    public void update() {
        
                           // 这会确保 TimerTask 内部的 animationImage.length 是正确的
    }



    @Override
    public List<Bullets> attack() {
        return null; 
    }



}
