package com.pvz.seedpacket;
import java.awt.*;

import com.pvz.plants.Repeater;

public class SeedPacketRepeater extends SeedPackets {
    


    // Create a new SeedPacketRepeatPeaShooter object with the given x and y coordinates
    public SeedPacketRepeater(int x, int y) {
        super(x, y); // Call the superclass constructor with the given x and y coordinates
        cost = 200; // Set the cost of the seed packet
        name = "Repeater"; // Set the name of the seed packet
        setPlant(new Repeater(x, y,0,0)); // Create a new Repeater plant and set it as the associated plant
    }

    // Override the loadImage method to load the image for the Repeater seed packet


}
