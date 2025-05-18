package com.pvz.plants;

import java.awt.*;

public class Repeater extends Plants {
    public Repeater(int x, int y) {
        super(x, y);
        // Toolkit toolkit = Toolkit.getDefaultToolkit();
        // image = new Image[5];
        // for (int i = 0; i < image.length; i++) {
        //     image[i] = toolkit.getImage(getClass().getClassLoader().getResource("resourse/images/plants/Repeater" + i + ".png"));
        // }
    }

    @Override
    public void grow(Graphics g) {
        g.drawImage(image[0], x-width/2, y-height/2, width, height, null);
    }

    @Override
    public void update() {
        // Update logic for Repeater plant
    }

}
