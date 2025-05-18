package com.pvz.plants;
import java.awt.*;


public abstract class Plants {
    public int x, y;
    public int width, height;
    public int speed = 0; // Speed of the plant
    public int health = 0; // Health of the plant
    public Image[] image;
    public boolean isalive = false; // Flag to indicate if the plant is enabled

    public Plants(int x, int y) {
        this.x = x;
        this.y = y;
        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        image = new Image[5];
        for (int i = 0; i < image.length; i++) {
            image[i] = toolkit.getImage(getClass().getClassLoader().getResource("resourse/images/plants/" + getClass().getSimpleName() +"/"+getClass().getSimpleName()+ i + ".png"));
        }
        this.width = image[0].getWidth(null); // Set the width of the plant
        this.height = image[0].getHeight(null); // Set the height of the plant
    }

    public abstract void grow(Graphics g); // Abstract method to draw the plant
    public abstract void update(); // Abstract method to update the plant's state

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }



    
}
