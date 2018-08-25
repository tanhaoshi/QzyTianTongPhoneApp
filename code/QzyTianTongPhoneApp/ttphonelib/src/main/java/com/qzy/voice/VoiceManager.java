package com.qzy.voice;

/**
 * Created by yj.zhang on 2018/7/31/031.
 */

public class VoiceManager {

    private VoiceSender mVoiceSender;
    private VoicePlayer mVoicePlayer;


    public void start(){
        startRecv();
        startSend();
    }

    /**
     * 启动播放器
     */
    private void startRecv() {
        stopRecv();
        mVoicePlayer = new VoicePlayer();
        mVoicePlayer.start();
    }

    /**
     * 停止播放器
     */
    private void stopRecv() {
        if(mVoicePlayer != null) {
            mVoicePlayer.setIsStopped();
            mVoicePlayer = null;
        }
    }


    /**
     * 启动recorder
     */
    private void startSend() {
        stopSend();
        mVoiceSender = new VoiceSender();
        mVoiceSender.start();
    }

    /**
     * 停止recorder
     */
    private void stopSend() {
        if(mVoiceSender != null) {
            mVoiceSender.setIsStopped(true);
            mVoiceSender = null;
        }
    }

    public void stop(){
        stopRecv();
        stopSend();
    }

}
