package com.qzy.locallib.selfpcm.voice;

import android.os.Process;
import android.util.Log;

import com.qzy.locallib.util.LocalConstants;
import com.qzy.tiantong.lib.buffer.MyBuffer;
import com.qzy.tiantong.lib.utils.ByteUtils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yj.zhang on 2018/6/25/025.
 */

public class VoiceSenderSpexx extends Thread {

    private static final String TAG = "VoiceSender";

    private DatagramSocket mDatagramSocket;
    private DatagramPacket mPacket;
    //private InetAddress mInetAddress;

    private byte[] mByteBuffer;

    private Object obj = new Object();
    private boolean isSuspended = true;
    private boolean isStopped;

    private List<MyBuffer> listSender;

    private short[] shortBuffer = new short[' '];

    /**
     * 初始化socket
     *
     * @throws Exception
     */
    public void initSocket() throws Exception {
        listSender = new ArrayList<>();
        mDatagramSocket = new DatagramSocket();
        mDatagramSocket.setSoTimeout(0);
    }

    /**
     * 释放socket
     */
    public void releaseSocket() {
        if (mDatagramSocket != null) {
            mDatagramSocket.close();
            mDatagramSocket = null;
        }
    }

    public void sendData(byte[] data) {
        try {
            mPacket = new DatagramPacket(data, data.length, InetAddress.getByName(LocalConstants.UNICAST_BROADCAST_IP), LocalConstants.UNICAST_PORT);
            mDatagramSocket.send(mPacket);
            //  isSuspended = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int index = 0;

    @Override
    public void run() {
        super.run();
        Log.i(TAG, "run() =====>>> Begin");
        // Set Thread Priority
        Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);

        try {
            // Initialize Socket
            initSocket();
        } catch (Exception e) {
            Log.e(TAG, "Initialize Socket Failed!");
            e.printStackTrace();
            return;
        }


        while (!isStopped()) {
           /* if (GlobalVoiceSetting.isUseSpexx) {
                mAudioRecord.read(mShortBuffer, 0, 160);
                Speex.encode(mShortBuffer, mByteBuffer);
            } else {
                mAudioRecord.read(mByteBuffer, 0, 320);
            }*/
            // LogUtils.d("1111 write pcm to phone my tid = " + android.os.Process.myTid()+" name "+Thread.currentThread().getName());
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

            // LogUtils.d("00000 send receive pohone data inde sned");

            if (listSender != null && listSender.size() > 0) {
                mByteBuffer = listSender.get(0).data;
                // LogUtils.d("send receive pohone data inde sned mByteBuffer =" + mByteBuffer.length);

                if (mByteBuffer != null) {
                    if (index < 256) {
                        index++;
                    } else {
                        index = 1;
                    }
                    if (index != 0) {
                        byte[] dataValue = new byte[mByteBuffer.length + 1];
                        dataValue[0] = ByteUtils.intToByte(index);
                        System.arraycopy(mByteBuffer, 0, dataValue, 1, mByteBuffer.length);
                        try {
                            mPacket = new DatagramPacket(dataValue, dataValue.length,  InetAddress.getByName(LocalConstants.UNICAST_BROADCAST_IP), LocalConstants.UNICAST_PORT);
                            mDatagramSocket.send(mPacket);
                            //LogUtils.d("send receive pohone data inde sned =" + ByteUtils.byteToInt(dataValue[0]));
                            listSender.remove(0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }


            } else {
                isSuspended = true;
            }


        }

        releaseSocket();
        // releaseAudioRecord();
        Log.i(TAG, "run() =====>>> End");

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

    public byte[] getmByteBuffer() {
        return mByteBuffer;
    }

    public void setmByteBuffer(byte[] data) {
        if (data[0] != 0) {
            MyBuffer buffer = new MyBuffer();
            buffer.data = new byte[data.length];
            System.arraycopy(data, 0, buffer.data, 0, data.length);
            // LogUtils.d("add buffer pcm");
            listSender.add(buffer);
        }
    }

    public boolean isStopped() {
        return isStopped;
    }

    public void setIsStopped(boolean isStop) {
        this.isStopped = isStop;
    }


}
