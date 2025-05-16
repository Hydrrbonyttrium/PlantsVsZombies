import java.awt.*;

public class SeedPacketRepeater extends SeedPackets {
    


    // Create a new SeedPacketRepeatPeaShooter object with the given x and y coordinates
    public SeedPacketRepeater(int x, int y) {
        super(x, y); // Call the superclass constructor with the given x and y coordinates
        Toolkit toolkit = Toolkit.getDefaultToolkit(); // Get the default toolkit
        image = toolkit.getImage(getClass().getClassLoader().getResource("images/seedPacket/SeedPacket_Repeater.png")); // Load the image for the seed packet
    }

}
