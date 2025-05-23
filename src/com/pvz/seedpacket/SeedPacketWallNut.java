package com.pvz.seedpacket;


import java.awt.Image;
import java.awt.Toolkit;

import com.pvz.plants.WallNut;

public class SeedPacketWallNut extends SeedPackets {

    public SeedPacketWallNut(int x, int y) {
        super(x, y); // Call the superclass constructor with the given x and y coordinates
        cost = 50; // Set the cost of the seed packet
        name = "WallNut"; // Set the name of the seed packet
        setPlant(new WallNut(x, y, 0, 0, 16)); // Create a new Repeater plant and set it as the associated plant
    }

    public Image getPlantImage() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        return toolkit.getImage(getClass().getClassLoader().getResource("resource/images/plants/WallNut/idleH/idleH_0.png"));
    }

}
