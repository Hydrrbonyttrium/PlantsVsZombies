package com.pvz.seedpacket;


import java.awt.*;

import com.pvz.main.GamePanel;
import com.pvz.plants.PeaShooter;
import com.pvz.plants.Plants;
import com.pvz.plants.Repeater;
import com.pvz.plants.SunFlower;
import com.pvz.plants.WallNut;
import com.pvz.ui.SeedBank;


public  class SeedPackets {
    public int x;
    public int y;
    public int width;
    public int height;
    public Image image;
    public boolean enabled; // Flag to indicate if the seed packet is enabled
    public boolean isSelected; // Flag to indicate if the seed packet is selected
    public int cost;
    public String name;
    public Plants plant; // The plant associated with the seed packet
    public SeedPackets(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = SeedBank.width / 6; // Set the width of the seed packet
        this.height = SeedBank.height-15; // Set the height of the seed packet
        this.isSelected = false; // Initialize the selection state to false
        checkEnabled(); // Check if the seed packet is enabled based on the cost
        // Load the image for the seed packet        
        this.loadImage();

    }

    public void loadImage() {
        // Load the image for the seed packet
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        if(enabled&&!isSelected) {
            image = toolkit.getImage(getClass().getClassLoader().getResource("resource/images/seedPacket/"+getClass().getSimpleName()+".png"));
        } else {
            image = toolkit.getImage(getClass().getClassLoader().getResource("resource/images/seedPacket/"+getClass().getSimpleName()+"_disabled.png"));
        }
    }

    public Image getImage() {
        return image;
    }


    public Image getPlantImage() {
        // 默认实现，子类可以覆盖
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        return toolkit.getImage(getClass().getClassLoader().getResource("resource/images/plants/"+name+"/"+name+"0.png"));
    }
    
    public void setPlant(Plants plant) {
        this.plant = plant;
    }
    public Plants getPlant() {
        String name = getClass().getSimpleName();
        switch (name) {

            case "SeedPacketRepeater":
                return new Repeater(x, y,0,0,5);
            case "SeedPacketSunFlower" :
                return new SunFlower(x, y, 0,0,17);
            case "SeedPacketPeaShooter":
                return new PeaShooter(x, y,0,0,8);
            case "SeedPacketWallNut":
                return new WallNut(x, y,0,0,16);
            default:
                return null; // Handle other cases or throw an exception
        }
          
        
    }
    public int getCost() {
        return cost;
    }
    


    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }

    
    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }
    // 检查点击是否在种子包上
    public boolean contains(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && 
               mouseY >= y && mouseY <= y + height;
    }

    // 处理鼠标点击事件
    public void handleMouseClick(int mouseX, int mouseY) {
        if (contains(mouseX, mouseY)) {
            // Toggle the selection state
            isSelected = !isSelected;
            // Update the image based on the selection state
            if (isSelected) {
                image = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("resource/images/seedPacket/"+getClass().getSimpleName()+".png"));
            } else {
                image = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("resource/images/seedPacket/"+getClass().getSimpleName()+"_disabled.png"));
            }
        }
    }

    public void checkEnabled() {
        if(this.cost<=GamePanel.sunCount) {
            enabled = true; // Enable the seed packet if the cost is less than or equal to the sun count
            image = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("resource/images/seedPacket/"+getClass().getSimpleName()+".png"));
        } else {
            enabled = false; // Disable the seed packet if the cost is greater than the sun count
            image = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("resource/images/seedPacket/"+getClass().getSimpleName()+"_disabled.png"));
        }

    }

    

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }
    public boolean isSelected() {
        return isSelected;
    }


}
