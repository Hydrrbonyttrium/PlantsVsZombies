package com.pvz.bullets;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.Timer;
public class SunBullet extends Bullets  {
    private int SUN_DISAPPER_TIMER = 5000;
    private boolean isCollected;
    public Timer sunTimer;
    public boolean ifDisapper=false;

    public SunBullet(int x, int y, int row) {
        
        super(x, y,row);
        System.out.println("生成阳光");
        this.speed = 0;
        this.damage = 0;

        initsunTimer();
        
    }

    public void initsunTimer(){
        
        if (this.sunTimer != null) {
            this.sunTimer.cancel();
        }
        this.sunTimer = new Timer(true); // 使用守护线程的Timer更安全
        sunTimer.schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                ifDisapper=true;
                // System.out.println("sun disapper");
                // 当阳光消失后，这个TimerTask完成了它的使命，
                // 但Timer对象本身若不随SunBullet销毁而被cancel，则会泄露。
            }
        }, SUN_DISAPPER_TIMER);
    }


    @Override
    public void loadImage(String path) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        image = toolkit.getImage(getClass().getClassLoader().getResource("resource/images/Bullets/SunBullet0.gif"));
        
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
    }

    
        

    public boolean isPointInSun(int x, int y) {
        // 假设阳光图像宽高为width和height
        int width = this.width+10;
        int height = this.height+10;
        
        return x >= this.x && x <= this.x + width && 
               y >= this.y && y <= this.y + height;
    }

    // 收集阳光的方法
    public void collectSun() {
        // 增加游戏面板中的阳光数量
        com.pvz.main.GamePanel.sunCount += 25; // 每个阳光增加25点阳光值
        
        // 标记阳光为已收集，后续可以移除
        this.isCollected = true;
    }

    // 获取阳光是否被收集的状态
    public boolean isCollected() {
        return isCollected;
    }

    @Override
    public void dispose() {
        super.dispose(); // 调用父类的dispose方法
        if (sunTimer != null) {
            sunTimer.cancel();
            sunTimer = null; // 帮助垃圾回收
        }
    }
}
