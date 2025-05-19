package com.pvz.bullets;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
public class SunBullet extends Bullets implements MouseListener {
    private boolean isCollected;

    public SunBullet(int x, int y, int row) {
        
        super(x, y,row);
        this.speed = 0;
        this.damage = 0;
        
    }
    @Override
    public void loadImage(String path) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        image = toolkit.getImage(getClass().getClassLoader().getResource("resourse/images/Bullets/SunBullet0.gif"));
        
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        // 获取鼠标点击位置
        int mouseX = e.getX();
        int mouseY = e.getY();
        
        // 检查点击是否在阳光范围内
        if (isPointInSun(mouseX, mouseY)) {
            // 收集阳光
            collectSun();
        }
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
        
    }

    private boolean isPointInSun(int x, int y) {
        // 假设阳光图像宽高为width和height
        int width = this.image.getWidth(null);
        int height = this.image.getHeight(null);
        
        return x >= this.x && x <= this.x + width && 
               y >= this.y && y <= this.y + height;
    }

    // 收集阳光的方法
    private void collectSun() {
        // 增加游戏面板中的阳光数量
        com.pvz.main.GamePanel.sunCount += 25; // 每个阳光增加25点阳光值
        
        // 标记阳光为已收集，后续可以移除
        this.isCollected = true;
    }

    // 获取阳光是否被收集的状态
    public boolean isCollected() {
        return isCollected;
    }

}
