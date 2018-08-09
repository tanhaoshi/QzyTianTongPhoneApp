package com.qzy.ttpcm;

import com.qzy.utils.LogUtils;
import com.qzy.voice.VoiceManager;

/**
 * Created by yj.zhang on 2018/8/8.
 */

public class TtAudioRecorder {

    private int isRead = -1;

    private IReadPcmData iReadPcmData;

    private Thread readThread;

    private boolean isRunning = false;

    public TtAudioRecorder(IReadPcmData listener) {
        iReadPcmData = listener;
    }


    public interface IReadPcmData {

        void readData(byte[] data);

    }


    private Runnable mReadThread = new Runnable() {
        @Override
        public void run() {
            try {
                while (isRead == 160 * 8 && isRunning) {
                    if (isRead == -1) {
                        break;
                    }
                    byte[] data = new byte[isRead];
                    //LogUtils.d("read pcm to local my tid = " + android.os.Process.myTid()+" name "+Thread.currentThread().getName());
                   // LogUtils.d("mReadThread running = ");
                    int len = VoiceManager.readPcmData(data);
                  //  LogUtils.d("mReadThread read pcm data len = " + len);
                    //LogUtils.d("read pcm data = " + ByteUtils.byteArrToHexString(data));
                    if (len == 0) {
                        if (iReadPcmData != null) {
                            iReadPcmData.readData(data);
                        }
                    }
                }
                isRunning = false;
                VoiceManager.releasePcmRecorder();
                LogUtils.d("mReadThread=  ..........finish...................");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public void initTtAudioRecorder() {
        LogUtils.d("initTtAudioRecorder = " + isRead);
        //try {
        isRead = VoiceManager.initPcmRecorder();
        LogUtils.d("read pcm data isRead = " + isRead);
        isRunning = true;
        readThread = new Thread(mReadThread);
        readThread.start();
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
    }


    /**
     * 释放资源
     */
    public void releaseTtAudioRecorder() {

        try {
            isRead = -1;
            isRunning = false;
            if (readThread != null && readThread.isAlive()) {
                readThread.interrupt();
            }
            readThread = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
