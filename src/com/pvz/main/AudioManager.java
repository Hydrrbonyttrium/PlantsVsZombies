package com.pvz.main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioManager {
    private Clip bgmClip;
    private boolean isMuted = false;
    private float volume = 0.5f; // 默认音量50%
    
    /**
     * 播放BGM
     * @param audioPath 音频文件路径
     */
    public void playBGM(String audioPath) {
        stopBGM(); // 先停止当前播放的音乐
        
        try {
            File audioFile = new File(audioPath);
            if (!audioFile.exists()) {
                System.err.println("音频文件不存在: " + audioPath);
                return;
            }
            
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            bgmClip = AudioSystem.getClip();
            bgmClip.open(audioStream);
            
            // 设置音量
            setVolume(volume);
            
            // 循环播放
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            bgmClip.start();
            
            System.out.println("开始播放BGM: " + audioPath);
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("播放BGM失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 停止BGM
     */
    public void stopBGM() {
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
            bgmClip.close();
            bgmClip = null;
            System.out.println("BGM已停止");
        }
    }
    
    /**
     * 暂停BGM
     */
    public void pauseBGM() {
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
        }
    }
    
    /**
     * 恢复BGM
     */
    public void resumeBGM() {
        if (bgmClip != null && !bgmClip.isRunning()) {
            bgmClip.start();
        }
    }
    
    /**
     * 设置音量
     * @param volume 音量值 (0.0f - 1.0f)
     */
    public void setVolume(float volume) {
        this.volume = Math.max(0.0f, Math.min(1.0f, volume));
        
        if (bgmClip != null) {
            FloatControl volumeControl = (FloatControl) bgmClip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(this.volume) / Math.log(10.0) * 20.0);
            volumeControl.setValue(dB);
        }
    }
    
    /**
     * 静音/取消静音
     */
    public void toggleMute() {
        isMuted = !isMuted;
        if (isMuted) {
            setVolume(0.0f);
        } else {
            setVolume(volume);
        }
    }
    
    /**
     * 检查BGM是否正在播放
     */
    public boolean isPlaying() {
        return bgmClip != null && bgmClip.isRunning();
    }
    
    /**
     * 释放资源
     */
    public void dispose() {
        stopBGM();
    }
}
