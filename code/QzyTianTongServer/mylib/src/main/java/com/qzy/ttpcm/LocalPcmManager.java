package com.qzy.ttpcm;

import android.content.Context;


import com.qzy.buffer.MyBuffer;
import com.qzy.utils.LogUtils;
import com.qzy.voice.VoiceManager;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yj.zhang on 2018/7/9/009.
 */

public class LocalPcmManager {


    private int isRead = -1;

    private int isWrite = -1;

    private Thread readThread;

    private Thread writeThread;

    private IReadPcmData iReadPcmData;

    private boolean isOpen = false;

    public LocalPcmManager(IReadPcmData listener) {
        iReadPcmData = listener;
    }


    /**
     * 打开底层通话设备
     */
    public void openPlayAndRecorderDevice() {

        try {
            isWrite = VoiceManager.initPcmPlayer();
            isRead = VoiceManager.initPcmRecorder();
            if (isRead != -1 || isWrite != -1) {
                isOpen = true;
            }
            LogUtils.d("read pcm data isRead = " + isRead);
            readThread = new Thread(mReadThread);
            readThread.start();
            writeThread = new Thread(mWriteThread);
            writeThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Runnable mReadThread = new Runnable() {
        @Override
        public void run() {
            while (isRead == 4096) {
                byte[] data = new byte[isRead];
                //  LogUtils.d("read pcm to local my tid = " + android.os.Process.myTid()+" name "+Thread.currentThread().getName());
                int len = VoiceManager.readPcmData(data);
                //  LogUtils.d("read pcm data len = " + len);
                //LogUtils.d("read pcm data = " + ByteUtils.byteArrToHexString(data));
                if (len > 0) {
                    if (iReadPcmData != null) {
                        iReadPcmData.readData(data);
                    }
                }
            }
        }
    };

    private boolean isStop = true;
    private boolean isSuspended = true;
    private Object obj = new Object();
    private List<MyBuffer> mListPhoneData = new ArrayList<>();
    private Runnable mWriteThread = new Runnable() {
        @Override
        public void run() {
            while (isStop) {

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

                            // LogUtils.d("write receive pohone data inde =" + ByteUtils.byteToInt(data[0]));
                            writePcmData(dataValue);
                            mListPhoneData.remove(0);
                            // appServiceManager.setVoiceReaderResume();
                        }
                    }


                } else {
                    isSuspended = true;
                }
            }
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
     * 关闭底层通话设备
     */
    public void closePlayAndRecorderDevice() {

        try {
            VoiceManager.releasePcmPlayer();
            VoiceManager.releasePcmRecorder();
            isOpen = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void writePcmData(byte[] data) {
        VoiceManager.writePcmData(data);
    }

    /**
     * 释放资源
     */
    public void release() {

        try {
            closePlayAndRecorderDevice();
            if (readThread != null && readThread.isAlive()) {
                readThread.interrupt();
            }

            if (writeThread != null && writeThread.isAlive()) {
                writeThread.interrupt();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public interface IReadPcmData {

        void readData(byte[] data);

    }


}
