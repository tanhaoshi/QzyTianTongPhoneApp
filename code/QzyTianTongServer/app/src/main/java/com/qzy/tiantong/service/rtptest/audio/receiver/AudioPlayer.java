package com.qzy.rtptest.audio.receiver;

import android.util.Log;

import com.qzy.rtptest.audio.AudioData;
import com.qzy.voice.VoiceManager;

import java.util.concurrent.LinkedBlockingQueue;

public class AudioPlayer implements Runnable {
    String LOG = "AudioPlayer ";
    private static AudioPlayer player;

    private LinkedBlockingQueue<AudioData> dataList = null;
    private AudioData playData;
    private boolean isPlaying = false;

    //private AudioTrack audioTrack;

    //
    private int bufferSize = -1;

    private Thread thread;

    private AudioPlayer() {
        dataList = new LinkedBlockingQueue<>();
    }

    public static AudioPlayer getInstance() {
        if (player == null) {
            player = new AudioPlayer();
        }
        return player;
    }

    public void addData(byte[] rawData, int size) {
        AudioData decodedData = new AudioData();
        decodedData.setSize(size);
        byte[] tempData = new byte[size];
        System.arraycopy(rawData, 0, tempData, 0, size);
        decodedData.setRealData(tempData);
        dataList.add(decodedData);
       // Log.e(LOG, "Player添加一次数据 " + dataList.size());
    }

    /*
     * init Player parameters
     */
    private boolean initAudioTrack() {
        bufferSize = VoiceManager.initPcmPlayer();
        if (bufferSize < 0) {
            Log.e(LOG, LOG + "initialize error!");
            return false;
        }
        Log.i(LOG, "Player初始化的 buffersize是 " + bufferSize);
        return true;
    }

    private void playFromList() throws Exception {
        AudioData data;
        while ((data = dataList.take()) != null) {
           // Log.e(LOG, "playFromList");
            if (!isPlaying) {
                break;
            }
            VoiceManager.writePcmData(data.getRealData());
        }
    }

    public void startPlaying() {
        if (isPlaying) {
            Log.e(LOG, "验证播放器是否打开" + isPlaying);
            return;
        }
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        this.isPlaying = true;
        if (!initAudioTrack()) {
            Log.i(LOG, "播放器初始化失败");
            return;
        }
        Log.e(LOG, "开始播放");
        try {
            playFromList();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            VoiceManager.releasePcmPlayer();
            Log.d(LOG, LOG + "end playing");
        }

    }

    public void stopPlaying() {
        bufferSize = -1;
        this.isPlaying = false;

        if (thread != null && thread.isAlive()) {
            thread.interrupt();
        }

    }
}
