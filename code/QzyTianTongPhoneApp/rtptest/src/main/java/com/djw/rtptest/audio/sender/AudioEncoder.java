package com.djw.rtptest.audio.sender;

import android.util.Log;

import com.djw.rtptest.audio.AudioCodec;
import com.djw.rtptest.audio.AudioData;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AudioEncoder implements Runnable {
    String LOG = "AudioEncoder";

    private static AudioEncoder encoder;
    private boolean isEncoding = false;

    private BlockingQueue<AudioData> dataList = null;
    private Thread thread;

    public static AudioEncoder getInstance() {
        if (encoder == null) {
            encoder = new AudioEncoder();
        }
        return encoder;
    }

    private AudioEncoder() {
        dataList = new LinkedBlockingQueue<AudioData>();
    }

    public void addData(byte[] data, int size) {
        AudioData rawData = new AudioData();
        rawData.setSize(size);
        byte[] tempData = new byte[size];
        System.arraycopy(data, 0, tempData, 0, size);
        rawData.setRealData(tempData);
        dataList.add(rawData);
    }

    /*
     * start encoding
     */
    public void startEncoding() {
        System.out.println(LOG + "start encode thread");
        if (isEncoding) {
            Log.e(LOG, "encoder has been started  !!!");
            return;
        }
        thread = new Thread(this);
        thread.start();
    }

    /*
     * end encoding
     */
    public void stopEncoding() {
        this.isEncoding = false;
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
        }
    }

    public void run() {
        // start sender before encoder
        AudioSender sender = new AudioSender();
        sender.startSending();

        int encodeSize = 0;
        byte[] encodedData = new byte[256];

        // initialize audio encoder:mode is 30
        AudioCodec.audio_codec_init(30);

        isEncoding = true;
        AudioData rawData;
        try {
            while ((rawData = dataList.take()) != null) {

                if (!isEncoding) {
                    break;
                }
                //
                byte[] dat = rawData.getRealData();
                if (dat.length > 0) {
                    sender.addData(dat, dat.length);
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            System.out.println(LOG + "end encoding");
            sender.stopSending();
        }

    }

}