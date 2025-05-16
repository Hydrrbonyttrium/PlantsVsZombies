import java.awt.*;


public class BackGround {
    public Image[] images = new Image[3];
    
    public BackGround(){
        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        images[0] = toolkit.getImage(getClass().getClassLoader().getResource("images/background/start.jpg"));
        images[1] = toolkit.getImage(getClass().getClassLoader().getResource("images/background/running.jpg"));
        images[2] = toolkit.getImage(getClass().getClassLoader().getResource("images/background/gameover.jpg"));

        

    }

    public Image getImage(int state){
        switch (state) {
            case GamePanel.START:
                return images[0];
            case GamePanel.RUNNING:
                return images[1];
            case GamePanel.GAME_OVER:
                return images[2];
            default:
                return images[0];
        }
    }

    public int getWidth(int state){
        switch (state) {
            case GamePanel.START:
                return 800;
            case GamePanel.RUNNING:
                return 1400;
            case GamePanel.GAME_OVER:
                return 1361;
            default:
                return images[0].getWidth(null);
        }
    }

    public int getHeight(int state){
        switch (state) {
            case GamePanel.START:
                return 533;
            case GamePanel.RUNNING:
                return 800;
            case GamePanel.GAME_OVER:
                return 619;
            default:
                return images[0].getHeight(null);
        }
    }
    
}
