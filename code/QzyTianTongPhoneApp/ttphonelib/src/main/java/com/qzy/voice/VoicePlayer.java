package com.qzy.voice;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Process;
import android.util.Log;

import com.qzy.netty.NettyClient;
import com.qzy.utils.ByteUtils;
import com.qzy.utils.LogUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;


public class VoicePlayer extends Thread {

    private MulticastSocket mMulticastSocket;
    private DatagramSocket mDatagramSocket;

    private DatagramPacket mPacket;
    private InetAddress mInetAddress;
    private AudioTrack mAudioTrack;

    private short[] shortBuffer = new short[' '];
    private byte[] byteBuffer;

    private Object obj = new Object();
    private boolean isSuspended;
    private boolean isStopped;

    public void initAudioTrack() {
        int minBufSize = AudioTrack.getMinBufferSize(8000, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT);
        LogUtils.e("minBufSize = " + minBufSize);
        if (minBufSize == 0) {
            minBufSize = 4096;
        }
        mAudioTrack = new AudioTrack(AudioManager.STREAM_VOICE_CALL, 8000, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT, minBufSize * 2, AudioTrack.MODE_STREAM);
        mAudioTrack.play();
        //AudioFormat.CHANNEL_IN_STEREO
        //24576
    }

    /**
     * 其实这里只需要初始化个组播MultiSocket就可以了<p>
     * 因为：组播可以接收所有广播了.
     *
     * @throws Exception
     */
    private void initSocket() throws Exception {

        mInetAddress = InetAddress.getByName(NettyClient.IP);
        mDatagramSocket = new DatagramSocket(8999);

        mDatagramSocket.setSoTimeout(0);
    }

    private void releaseSocket() {
        if (mMulticastSocket != null) {
            try {
                mMulticastSocket.leaveGroup(mInetAddress);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mMulticastSocket = null;
        }
        if (mDatagramSocket != null) {
            mDatagramSocket.close();
            mDatagramSocket = null;
        }
    }

    private void releaseAudioTrack() {
        if (mAudioTrack != null) {
            if (mAudioTrack.getPlayState() != AudioTrack.PLAYSTATE_STOPPED) {
                mAudioTrack.stop();
            }
            mAudioTrack.release();
            mAudioTrack = null;
        }
    }

    @Override
    public void run() {
        super.run();
        Log.i("Player", "Player run()...Start");
        //
        Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);

        // 初始化AudioTrack
        initAudioTrack();
        try {
            // 初始化Socket
            initSocket();
        } catch (Exception e) {
            Log.i("Player", "初始化Socket失败！");
            e.printStackTrace();
            return;
        }


        byteBuffer = new byte[1024 * 4 + 1];
        // byteBuffer = new byte[320 + 1];
        mPacket = new DatagramPacket(byteBuffer, byteBuffer.length);

        while (!isStopped()) {
            try {
                //Log.i("Player", "Player receiver");
                mDatagramSocket.receive(mPacket);    // IOException

                // 暂停线程
                if (isSuspended) {
                    synchronized (obj) {
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }


            } catch (IOException e) {
                Log.i("Player", "Socket receive()...Error. 退出while循环.");
                e.printStackTrace();
                break;
            }


            Log.i("Player", "pcm index = " + ByteUtils.byteToInt(byteBuffer[0]));
            byte[] dv = new byte[byteBuffer.length - 1];
            System.arraycopy(byteBuffer, 1, dv, 0, dv.length);

            mAudioTrack.write(dv, 0, dv.length);
        }

        releaseSocket();
        releaseAudioTrack();
        Log.i("Player", "Player run()...End");
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

}
