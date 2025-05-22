package com.pvz.seedpacket;

import com.pvz.plants.PeaShooter;
import com.pvz.plants.Repeater;

public class SeedPacketPeaShooter extends SeedPackets {
    public SeedPacketPeaShooter(int x, int y) {
        super(x, y); // Call the superclass constructor with the given x and y coordinates
        cost = 100; // Set the cost of the seed packet
        name = "PeaShooter"; // Set the name of the seed packet
        setPlant(new PeaShooter(x, y,0,0,8)); // Create a new Repeater plant and set it as the associated plant
    }

}
