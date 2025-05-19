package com.pvz.ui;

import java.awt.*;

import com.pvz.main.GamePanel;
import com.pvz.seedpacket.SeedPacketRepeater;
import com.pvz.seedpacket.SeedPacketSunFlower;
import com.pvz.seedpacket.SeedPackets;

public class SeedBank {
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image image = toolkit.getImage(getClass().getClassLoader().getResource("resourse/images/seedbank.png"));
    public int x = 0;
    public int y = 0;
    public static int width = (GamePanel.width/4)*3;//100
    public static int height = 120;
    public SeedPackets[] seedPackets = new SeedPackets[5]; // Array to hold seed packets
    public int seedPacketCount = 2; // Counter for the number of seed packets

    public static int selectedPacekets = -1; // Index of the selected seed packet

    public SeedBank(int x, int y) {
        this.x = x;
        this.y = y;
        seedPackets[0] = new SeedPacketRepeater(100,5);
        seedPackets[1] = new SeedPacketSunFlower(100+seedPackets[0].width,5);
    }
    public Image getImage() {
        return image;
    }

    public void loadSeedPacket(Graphics g) {
        // Load the seed packet image
        try {
            // 原有的加载种子包代码
            // 例如：遍历种子包数组并绘制
            for (SeedPackets packet : seedPackets) {
                if (packet != null) {
                    packet.checkEnabled(); // Check if the seed packet is enabled
                    packet.draw(g); // Draw each seed packet
                }
            }
        } catch (Exception e) {
            System.err.println("加载种子包时出错: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getSelectedSeedPacket() {
        if (selectedPacekets != -1) {
            return seedPackets[selectedPacekets].name; // Return the selected seed packet
        }
        return null; // Return null if no seed packet is selected
    }

}
