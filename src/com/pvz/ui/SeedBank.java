package com.pvz.ui;

import java.awt.*;

import com.pvz.main.GamePanel;
import com.pvz.seedpacket.SeedPacketRepeater;
import com.pvz.seedpacket.SeedPacketSunFlower;
import com.pvz.seedpacket.SeedPackets;

public class SeedBank {
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image image = toolkit.getImage(getClass().getClassLoader().getResource("resource/images/seedbank.png"));
    public int x = 0;
    public int y = 0;
    public static int width = (GamePanel.width/4)*3;//100
    public static int height = 120;
    public SeedPackets[] seedPackets = new SeedPackets[5]; // Array to hold seed packets
    public int seedPacketCount = 2; // Counter for the number of seed packets

    public  int selectedPacketIndex = -1; // Index of the selected seed packet

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

    public SeedPackets getSelectedSeedPacket() {
        if (selectedPacketIndex != -1 && selectedPacketIndex < seedPackets.length && seedPackets[selectedPacketIndex] != null) {
            return seedPackets[selectedPacketIndex];
        }
        return null;
    }

    public int getSelectedSeedPacketCost() {
        if (selectedPacketIndex != -1 && selectedPacketIndex < seedPackets.length && seedPackets[selectedPacketIndex] != null) {
            return seedPackets[selectedPacketIndex].cost; // 确保 SeedPackets 类有 public int cost;
        }
        // 如果没有选中或索引无效，打印警告并返回一个极大值，以防止购买
        System.err.println("Warning: Attempted to get cost for an unselected or invalid seed packet. Index: " + selectedPacketIndex);
        return Integer.MAX_VALUE; 
    }

    public String getSelectedSeedPacketName() { // 假设返回名称
        if (selectedPacketIndex != -1 && selectedPacketIndex < seedPackets.length && seedPackets[selectedPacketIndex] != null) {
            return seedPackets[selectedPacketIndex].name; // 确保 SeedPackets 类有 public String name;
        }
        return null;
    }

    public void handlePacketClick(int clickedIndex) {
        if (clickedIndex < 0 || clickedIndex >= seedPackets.length || seedPackets[clickedIndex] == null) {
            return; // 无效索引或空的种子包槽
        }
    
        SeedPackets currentClickedPacket = seedPackets[clickedIndex];
    
        if (!currentClickedPacket.isEnabled()) { // 如果种子包未冷却完毕
            // 可以选择让它取消选中状态，如果它之前被选中了
            if (this.selectedPacketIndex == clickedIndex) {
                currentClickedPacket.setSelected(false);
                this.selectedPacketIndex = -1;
            }
            System.out.println("Seed packet " + clickedIndex + " is not enabled (cooling down).");
            return;
        }
    
        // 如果点击的是当前已选中的包，则取消选中 (实现切换选中效果)
        if (this.selectedPacketIndex == clickedIndex) {
            currentClickedPacket.setSelected(false);
            this.selectedPacketIndex = -1;
        } else {
            // 取消之前选中的包（如果有）
            if (this.selectedPacketIndex != -1) {
                seedPackets[this.selectedPacketIndex].setSelected(false);
            }
            // 选中新的包
            currentClickedPacket.setSelected(true);
            this.selectedPacketIndex = clickedIndex;
        }
    }

    public void deselectCurrentPacket() {
        if (selectedPacketIndex != -1 && selectedPacketIndex < seedPackets.length && seedPackets[selectedPacketIndex] != null) {
            seedPackets[selectedPacketIndex].setSelected(false);
        }
        this.selectedPacketIndex = -1;
    }

}
