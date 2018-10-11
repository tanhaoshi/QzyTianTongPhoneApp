package com.qzy.tiantong.ttpcm;

import com.qzy.tiantong.lib.buffer.MyBuffer;
import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.voice.VoiceManager;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yj.zhang on 2018/7/9/009.
 */

public class TtAudioTrack {


    private int isWrite = -1;


    private Thread writeThread;

    public TtAudioTrack() {

    }


    /**
     * 打开底层通话设备
     */
    public void initTtAudioTrack() {

        try {
            isWrite = VoiceManager.initPcmPlayer();
            LogUtils.d("write pcm data isWrite = " + isWrite);
            isRunning = true;
            writeThread = new Thread(mWriteThread);
            writeThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private boolean isRunning = true;
    private boolean isSuspended = true;
    private Object obj = new Object();
    private List<MyBuffer> mListPhoneData = new ArrayList<>();
    private Runnable mWriteThread = new Runnable() {
        @Override
        public void run() {
            while (isRunning) {

                // LogUtils.d("write pcm to local my tid = " + android.os.Process.myTid()+" name "+Thread.currentThread().getName());

                // 暂停当前线程
                if (isSuspended) {
                    synchronized (obj) {
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (mListPhoneData != null && mListPhoneData.size() > 0) {
                    if (mListPhoneData.get(0) != null) {
                        byte[] data = mListPhoneData.get(0).data;
                        if (data != null && data[0] != 0) {
                            byte[] dataValue = new byte[data.length - 1];
                            System.arraycopy(data, 1, dataValue, 0, dataValue.length);


                            //long startTime = System.currentTimeMillis();
                            VoiceManager.writePcmData(dataValue);
                            //long end = System.currentTimeMillis() - startTime;
                           // LogUtils.e("write pcm time == " + end);
                          //  LogUtils.d("write receive pohone data inde =" + ByteUtils.byteToInt(data[0]));
                            mListPhoneData.remove(0);
                        }
                    }


                } else {
                    isSuspended = true;
                }
            }

            VoiceManager.releasePcmPlayer();
            LogUtils.e("TtAudioTrack finish .....");
        }
    };

    public void setPcmData(byte[] data) {
        setIsSuspended(false);
        if (mListPhoneData == null) {
            mListPhoneData = new ArrayList<>();
        }
        if (data != null && data.length > 100 && data[0] != 0) {
            MyBuffer myBuffer = new MyBuffer();
            myBuffer.data = new byte[data.length];
            System.arraycopy(data, 0, myBuffer.data, 0, data.length);
            mListPhoneData.add(myBuffer);
        }
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public void setIsSuspended(boolean isSuspend) {
        this.isSuspended = isSuspend;
        if (!isSuspended) {
            synchronized (obj) {
                obj.notify();
            }
        }
    }

    /**
     * 释放资源
     */
    public void release() {
        try {
            isRunning = false;
            if (writeThread != null && writeThread.isAlive()) {
                writeThread.interrupt();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
