package com.pvz.shovel;

import java.awt.*;

public class Shovel {
    
    public int shovelx = 0;
    public int shovely = 0;
    public int shovelWidth = 50;
    public int shovelHeight = 50;
    public int bankx = 890;
    public int banky = 10;
    public int bankWidth = 50;
    public int bankHeight = 50;
    public boolean isClicked = false;
    public Image shovelImage;
    public Image bankImage;
    
    public Shovel() {
        shovelx = 886;
        shovely = 8;
        bankx = 890;
        banky = 10;
        loadImage();

        bankHeight = 72;
        bankWidth = 70;
        shovelHeight = 80;
        shovelWidth = 80;
    }


    public void loadImage() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        shovelImage = toolkit.getImage(getClass().getClassLoader().getResource("resource/images/shovel/Shovel.png"));
        bankImage = toolkit.getImage(getClass().getClassLoader().getResource("resource/images/shovel/ShovelBank.png"));
        
    }

    //检测鼠标是否点击铲子
    public void checkClicked(int x, int y) {
        
        if (x >= bankx && x <= bankx + bankWidth && y >= banky && y <= banky + bankHeight) {
            isClicked = !isClicked;
        } 
    }
}
