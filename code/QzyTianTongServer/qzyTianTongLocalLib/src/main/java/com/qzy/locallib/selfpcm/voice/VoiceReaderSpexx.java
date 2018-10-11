package com.qzy.locallib.selfpcm.voice;

import android.os.Process;
import android.util.Log;


import com.qzy.locallib.util.LocalConstants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by yj.zhang on 2018/6/25/025.
 */

public class VoiceReaderSpexx extends Thread {

    private static final String TAG = "VoiceReader";

    private DatagramSocket mDatagramSocket;

    private DatagramPacket mPacket;
   // private InetAddress mInetAddress;

    private byte[] byteBuffer;
    private byte[] byteAll = null;

    private Object obj = new Object();
    private boolean isSuspended;
    private boolean isStopped;

    private IReaderData iReaderData;

    private short[] shortBuffer = new short[' '];

    public VoiceReaderSpexx(IReaderData readerData) {
        iReaderData = readerData;
    }

    /**
     * 初始化socket
     *
     * @throws Exception
     */
    private void initSocket() throws Exception {

      //  mInetAddress = InetAddress.getByName(LocalConstants.UNICAST_BROADCAST_IP);
        mDatagramSocket = new DatagramSocket(LocalConstants.UNICAST_PORT);
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

    @Override
    public void run() {
        super.run();
        // LogUtils.d("read pcm to phone my tid = " + android.os.Process.myTid()+" name "+Thread.currentThread().getName());
        Log.i(TAG, "run() =VoiceReader====>>> Begin");
        //
        Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);

        try {
            // 初始化Socket
            initSocket();
        } catch (Exception e) {
            Log.i(TAG, "初始化Socket失败！");
            e.printStackTrace();
            return;
        }


        byteBuffer = new byte[160 * 8 + 1];

        mPacket = new DatagramPacket(byteBuffer, byteBuffer.length);

        while (!isStopped()) {
            try {
                mDatagramSocket.receive(mPacket);    // IOException


            } catch (IOException e) {
                Log.i("Player", "Socket receive()...Error. 退出while循环.");
                e.printStackTrace();
                break;
            }
            if (byteBuffer[0] == 0) {
                continue;
            }


            if (iReaderData != null) {
                //  LogUtils.d("read  receive pohone data inde =" + ByteUtils.byteToInt(byteBuffer[0]));
                iReaderData.onReadData(byteBuffer);
                iReaderData.onReadData();
            }


        }

        releaseSocket();
        Log.i(TAG, "run() =VoiceReader====>>> End");
    }


    public boolean isStopped() {
        return isStopped;
    }

    public void setIsStopped() {
        isStopped = true;
        // Socket异常退出
        releaseSocket();
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public void setIsSuspended(boolean isSuspend) {
        this.isSuspended = isSuspend;
        if (!isSuspended) {
            synchronized (obj) {
                // 恢复线程
                obj.notify();
            }
        }
    }

    public interface IReaderData {
        void onReadData(byte[] data);

        void onReadData();
    }

    public byte[] getByteBuffer() {
        return byteBuffer;
    }

    public void setByteBuffer(byte[] byteBuffer) {
        this.byteBuffer = byteBuffer;
    }
}
