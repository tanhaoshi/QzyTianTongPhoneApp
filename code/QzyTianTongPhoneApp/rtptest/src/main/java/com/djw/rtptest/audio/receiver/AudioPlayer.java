package com.djw.rtptest.audio.receiver;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.util.Log;

import com.djw.rtptest.audio.AudioConfig;
import com.djw.rtptest.audio.AudioData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class AudioPlayer implements Runnable {
    String LOG = "AudioPlayer ";
    private static AudioPlayer player;

    private LinkedBlockingQueue<AudioData> dataList = null;

    private Thread thread;

    private boolean isPlaying = false;

    private AudioTrack audioTrack;


    private AudioPlayer() {
        dataList = new LinkedBlockingQueue<AudioData>();

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
        Log.e(LOG, "Player添加一次数据 " + dataList.size());
    }

    /*
     * init Player parameters
     */
    private boolean initAudioTrack() {
        int bufferSize = AudioTrack.getMinBufferSize(
                AudioConfig.SAMPLERATE, AudioFormat.CHANNEL_OUT_MONO, AudioConfig.AUDIO_FORMAT);
        if (bufferSize < 0) {
            Log.e(LOG, LOG + "initialize error!");
            return false;
        }
        Log.i(LOG, "Player初始化的 buffersize是 " + bufferSize);
        audioTrack = new AudioTrack(AudioManager.STREAM_VOICE_CALL,
                AudioConfig.SAMPLERATE, AudioFormat.CHANNEL_OUT_MONO,
                AudioConfig.AUDIO_FORMAT, bufferSize, AudioTrack.MODE_STREAM);

        // set volume:设置播放音量
        audioTrack.setStereoVolume(1.0f, 1.0f);
        audioTrack.play();
        return true;
    }

    private void playFromList() throws Exception {

        AudioData playData;
        while ((playData = dataList.take()) != null) {
            if (!isPlaying) {
                break;
            }
            Log.e(LOG, "播放一次数据 " + dataList.size());
            audioTrack.write(playData.getRealData(), 0, playData.getSize());
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
        }
        // while (isPlaying) {
        // if (dataList.size() > 0) {
        // playFromList();
        // } else {
        //
        // }
        // }
        if (this.audioTrack != null) {
            if (this.audioTrack.getPlayState() == AudioTrack.PLAYSTATE_PLAYING) {
                this.audioTrack.stop();
                this.audioTrack.release();
            }
        }
        Log.d(LOG, LOG + "end playing");
    }

    public void stopPlaying() {
        this.isPlaying = false;
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
        }
    }
}
