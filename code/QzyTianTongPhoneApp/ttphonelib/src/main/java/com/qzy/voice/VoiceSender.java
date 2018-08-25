package com.qzy.voice;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Process;
import android.util.Log;

import com.qzy.netty.NettyClient;
import com.qzy.utils.ByteUtils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;


public class VoiceSender extends Thread {

    private static final String TAG = "Send";

    private DatagramSocket mDatagramSocket;
    private MulticastSocket mMultiSocket;
    private DatagramPacket mPacket;
    private InetAddress mInetAddress;

    private AudioRecord mAudioRecord;
    private short[] mShortBuffer = new short[' '];
    private byte[] mByteBuffer;

    private Object obj = new Object();
    private boolean isSuspended;
    private boolean isStopped;

    public boolean isAec = false;

    private void initAudioRecord() {
        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, 8000, AudioFormat.CHANNEL_IN_STEREO, AudioFormat.ENCODING_PCM_16BIT, Config.AUDIO_RECORD_BUFFER);
        mAudioRecord.startRecording();
        //CHANNEL_IN_STEREO
    }

    private void initSocket() throws Exception {

        mDatagramSocket = new DatagramSocket();
        mInetAddress = InetAddress.getByName(NettyClient.IP);

        mDatagramSocket.setSoTimeout(0);
    }

    private void releaseSocket() {
        if (mMultiSocket != null) {
            mMultiSocket = null;
        }

        if (mDatagramSocket != null) {
            mDatagramSocket.close();
            mDatagramSocket = null;
        }
    }

    private void releaseAudioRecord() {
        if (mAudioRecord != null) {
            if (mAudioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
                mAudioRecord.startRecording();
            }
            mAudioRecord.release();
            mAudioRecord = null;
        }
    }

    int id = 0;

    @Override
    public void run() {
        super.run();
        Log.i(TAG, "run() =====>>> Begin");
        // Set Thread Priority
        Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);

        try {
            // Initialize AudioRecord
            initAudioRecord();
        } catch (Exception e) {
            Log.e(TAG, "Initialize AudioRecord Failed!");
            e.printStackTrace();
            return;
        }

        try {
            // Initialize Socket
            initSocket();
        } catch (Exception e) {
            Log.e(TAG, "Initialize Socket Failed!");
            e.printStackTrace();
            return;
        }


        mByteBuffer = new byte[1024 * 4];


        while (!isStopped()) {


            mAudioRecord.read(mByteBuffer, 0, mByteBuffer.length);
            if (isAec && VoiceInputUtils.calculateVolume(mByteBuffer, 16) < 3) {
                continue;
            }


          /*  short[] add = ByteUtils.toShortArray(mByteBuffer);
            SpeexTool.getmSpeex().dsp(add, add.length);
            mByteBuffer = ByteUtils.toByteArray(add);*/
            // LogUtils.e("mByteBuffer len = " + mByteBuffer.length);
            if (id < 256) {
                id++;
            } else {
                id = 0;
            }

            byte[] rebuf = new byte[mByteBuffer.length + 1];
            rebuf[0] = ByteUtils.intToByte(id);
            System.arraycopy(mByteBuffer, 0, rebuf, 1, mByteBuffer.length);


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

            try {

                //Log.i(TAG, "write data inde = " + ByteUtils.byteToInt(rebuf[0]));
                //LogUtils.e("data read = " + ByteUtils.byteArrToHexString(rebuf));
                mPacket = new DatagramPacket(rebuf, rebuf.length, mInetAddress, 8999);
                mDatagramSocket.send(mPacket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        releaseSocket();
        releaseAudioRecord();
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

    public boolean isStopped() {
        return isStopped;
    }

    public void setIsStopped(boolean isStop) {
        this.isStopped = isStop;
    }
}
