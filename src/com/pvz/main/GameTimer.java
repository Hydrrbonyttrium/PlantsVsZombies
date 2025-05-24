package com.pvz.main;

import java.util.Timer;
import java.util.TimerTask;

import java.awt.*;

public class GameTimer {
    private long gameStartTime; // 游戏开始时间
    private long gameTime; // 游戏持续时间（毫秒）
    private Timer gameTimer; // 游戏计时器
    private boolean isGameTimerRunning = false; // 计时器状态
    
    public GameTimer() {

        initGameTimer();
        
    }

    private void initGameTimer() {
    gameStartTime = System.currentTimeMillis();
    isGameTimerRunning = true;
    gameTimer = new Timer();
    gameTimer.schedule(new TimerTask() {
        @Override
        public void run() {
            if (isGameTimerRunning) {
                gameTime = System.currentTimeMillis() - gameStartTime;
            }
        }
    }, 0, 100); // 每100毫秒更新一次
    }

    public long getGameTime() {
        return gameTime;
    }

    public void stopGameTimer() {
        if (gameTimer != null) {
            isGameTimerRunning = false;
            gameTimer.cancel();
            gameTimer = null;
        }
    }

    public void drawGameTime(Graphics g) {
    // 格式化时间为分:秒
        long seconds = gameTime / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        
        String timeText = String.format("游戏时间: %02d:%02d", minutes, seconds);
        
        // 保存原始字体和颜色
        Font originalFont = g.getFont();
        Color originalColor = g.getColor();
        
        // 设置新的字体和颜色
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.BLACK);
        
        // 在游戏界面上方显示时间
        g.drawString(timeText, GamePanel.width - 200, 30);
        
        // 恢复原始字体和颜色
        g.setFont(originalFont);
        g.setColor(originalColor);
    }


}
