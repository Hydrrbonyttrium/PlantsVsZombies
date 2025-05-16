import java.awt.Image;
import java.awt.Toolkit;

public class SeedBank {
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image image = toolkit.getImage(getClass().getClassLoader().getResource("images/seedbank.png"));
    int x = 0;
    int y = 0;
    int width = 100;
    int height = 120;

    public SeedBank(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Image getImage() {
        return image;
    }
}
