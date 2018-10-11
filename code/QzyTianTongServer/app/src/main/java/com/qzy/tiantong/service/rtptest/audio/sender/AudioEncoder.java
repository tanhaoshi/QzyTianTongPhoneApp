package com.qzy.rtptest.audio.sender;

import android.util.Log;

import com.qzy.rtptest.audio.AudioCodec;
import com.qzy.rtptest.audio.AudioData;
import com.qzy.tiantong.lib.utils.LogUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AudioEncoder implements Runnable {
    String LOG = "AudioEncoder";

    private static AudioEncoder encoder;
    private boolean isEncoding = false;

    private Thread thread;

    private BlockingQueue<AudioData> dataList = null;

    public static AudioEncoder getInstance() {
        if (encoder == null) {
            encoder = new AudioEncoder();
        }
        return encoder;
    }

    private AudioEncoder() {
        dataList = new LinkedBlockingQueue<>();
    }

    public void addData(byte[] data, int size) {
        AudioData rawData = new AudioData();
        rawData.setSize(size);
        byte[] tempData = new byte[size];
        System.arraycopy(data, 0, tempData, 0, size);
       // LogUtils.e(LOG + "tempData = " + tempData.length);
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
        if(thread != null && thread.isAlive()){
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
                byte[] dat = rawData.getRealData();
                //LogUtils.e(LOG + " dat = " + dat.length);
                if (dat.length > 0) {
                    sender.addData(dat, dat.length);
                }
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            LogUtils.e(LOG + "end encoding");
            sender.stopSending();
        }

    }

}