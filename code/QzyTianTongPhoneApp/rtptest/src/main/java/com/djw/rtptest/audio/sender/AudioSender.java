package com.djw.rtptest.audio.sender;

import android.os.Message;
import android.util.Log;

import com.djw.rtptest.Global;
import com.djw.rtptest.audio.AudioData;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AudioSender implements Runnable {
    String LOG = "AudioSender ";

    private boolean isSendering = false;
    private BlockingQueue<AudioData> dataList;

    private Thread thread;

    private int nSendPacks = 0;
    private int nSendBytes = 0;

    public AudioSender() {
        dataList = new LinkedBlockingQueue<AudioData>();
    }

    public void addData(byte[] data, int size) {
        AudioData encodedData = new AudioData();
        encodedData.setSize(size);
        byte[] tempData = new byte[size];
        System.arraycopy(data, 0, tempData, 0, size);
        encodedData.setRealData(tempData);
        dataList.add(encodedData);
    }

    /*
     * send data to server
     */
    private void sendData(byte[] data, int size) {
        if (!Global.ok) {
            return;
        }
        byte[] buf = new byte[size];
        System.arraycopy(data, 0, buf, 0, size);
        try {
            Global.rtpManager.rtpApp.rtpSession.sendData(buf);
        } catch (NullPointerException e) {
            return;
        }
        nSendPacks++;
        nSendBytes += size;
            /*Message message = new Message();
            message.what = 1;
            message.arg1 = nSendPacks;
            message.arg2 = nSendBytes;
            Global.rtpManager.handler.sendMessage(message);
			Log.e(LOG, "发送一段数据 " + data.length);*/
    }

    /*
     * start sending data
     */
    public void startSending() {
        if (isSendering) {
            Log.e(LOG, "sender is running...");
            return;
        }
        thread = new Thread(this);
        thread.start();
    }

    /*
     * stop sending data
     */
    public void stopSending() {
        this.isSendering = false;
    }

    // run
    public void run() {
        this.isSendering = true;
        System.out.println(LOG + "start....");
        AudioData encodedData;
        try {
            while ((encodedData = dataList.take()) != null) {
                if (!isSendering) {
                    break;
                }
                sendData(encodedData.getRealData(), encodedData.getSize());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(LOG + "stop!!!!");
    }
}