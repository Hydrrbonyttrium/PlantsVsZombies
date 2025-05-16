

import java.awt.*;


public  class SeedPackets {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Image image;
    private boolean enabled = false; // Flag to indicate if the seed packet is enabled
    private boolean isSelected = false; // Flag to indicate if the seed packet is selected

    public SeedPackets(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = SeedBank.width / 6; // Set the width of the seed packet
        this.height = SeedBank.height-15; // Set the height of the seed packet
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        image = toolkit.getImage(getClass().getClassLoader().getResource("images/seedPacket/SeedPacket_Larger.png"));
    }

    public Image getImage() {
        return image;
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }
}
