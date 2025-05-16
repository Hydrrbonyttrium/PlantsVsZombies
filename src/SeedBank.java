
import java.awt.*;

public class SeedBank {
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image image = toolkit.getImage(getClass().getClassLoader().getResource("images/seedbank.png"));
    int x = 0;
    int y = 0;
    static int width = (GamePanel.width/4)*3;//100
    static int height = 120;
    public SeedPackets[] seedPackets = new SeedPackets[5]; // Array to hold seed packets
    public int seedPacketCount = 1; // Counter for the number of seed packets
    public SeedBank(int x, int y) {
        this.x = x;
        this.y = y;
        seedPackets[0] = new SeedPacketRepeater(100,5);
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
                    packet.draw(g); // Draw each seed packet
                }
            }
        } catch (Exception e) {
            System.err.println("加载种子包时出错: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
