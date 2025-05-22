package com.pvz.seedpacket;


import com.pvz.plants.SunFlower;

public class SeedPacketSunFlower extends SeedPackets {

    public SeedPacketSunFlower(int x, int y) {
        super(x, y); // Call the superclass constructor with the given x and y coordinates
        cost = 50; // Set the cost of the seed packet
        name = "SunFlower"; // Set the name of the seed packet
        setPlant(new SunFlower(x, y,0,0,17)); // Create a new Repeater plant and set it as the associated plant
    }

}
