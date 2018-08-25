package com.qzy.test;

import android.media.AudioTrack;

import com.qzy.intercom.util.AudioDataUtilDe;
import com.qzy.intercom.util.Constants;
import com.qzy.test.buffer.MyBuffer;
import com.qzy.utils.ByteUtils;
import com.qzy.utils.LogUtils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yj.zhang on 2018/6/25/025.
 */

public class VoiceReaderSpexx extends Thread {

    private DatagramSocket receiveSocket;

    private AudioTrack audioTrack;
    // 音频大小
    private int outAudioBufferSize;
    // 播放标志
    private boolean isPlaying = true;

    private List<MyBuffer> mListPhoneData = new ArrayList<>();

    private Thread mPlayThread;

    public VoiceReaderSpexx() {

    }

    /**
     * 初始化soket
     */
    private void initSocket() {
        try {
            receiveSocket = new DatagramSocket(Constants.UNICAST_PORT);
            receiveSocket.setSoTimeout(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * free soket
     */
    private void freeSocket() {
        try {
            receiveSocket.close();
            receiveSocket = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    private void initAudioTracker() {
        // 获取音频数据缓冲段大小
        outAudioBufferSize = AudioTrack.getMinBufferSize(
                Constants.sampleRateInHz, Constants.outputChannelConfig, Constants.audioFormat);
        LogUtils.e("outAudioBufferSize = " + outAudioBufferSize);
        // 初始化音频播放
        audioTrack = new AudioTrack(Constants.streamType,
                Constants.sampleRateInHz, Constants.outputChannelConfig, Constants.audioFormat,
                outAudioBufferSize, Constants.trackMode);
        audioTrack.setVolume(audioTrack.getMaxVolume());
        if (audioTrack != null) {
            audioTrack.play();
        }
    }

    /**
     *
     */
    private void freeAudioTracker() {
        audioTrack.stop();
        audioTrack.release();
        audioTrack = null;
    }


    @Override
    public void run() {
        try {
            startPlaying();
            initSocket();

            byte[] receivedData = new byte[160 * 6 + 1];
            while (isPlaying) {
                DatagramPacket datagramPacket = new DatagramPacket(receivedData, receivedData.length);
                receiveSocket.receive(datagramPacket);
                handlerAudioData(datagramPacket.getData());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            stopPlaying();
            freeSocket();
            AudioDataUtilDe.free();
        }
    }


    private void handlerAudioData(byte[] data) {
        LogUtils.e("receiver data index = " + ByteUtils.byteToInt(data[0]));
        byte[] encode =  Arrays.copyOfRange(data, 1, data.length);
        byte[] pcmData = encode;
        //byte[] pcmData = ByteUtils.toByteArray(AudioDataUtilDe.spx2raw(encode));
        int readSize = pcmData.length;
        byte[] chanelData = new byte[readSize * 2];
        for (int i = 0; i < readSize; i = i + 2) {
            chanelData[2 * i] = pcmData[i];
            chanelData[2 * i + 1] = pcmData[i + 1];
            chanelData[2 * i + 2] = pcmData[i];
            chanelData[2 * i + 3] = pcmData[i + 1];
        }
        addData(chanelData);
    }


    private int count = 0;
    private void addData(byte[] data) {
        if (mListPhoneData == null) {
            mListPhoneData = new ArrayList<>();
        }
        MyBuffer myBuffer = new MyBuffer();
        myBuffer.data = new byte[data.length];
        System.arraycopy(data, 0, myBuffer.data, 0, data.length);
        mListPhoneData.add(myBuffer);
        if(count >16){
           setIsSuspended(false);
            //count = 0;
        }else{
            count = count + 1;
            LogUtils.e("count package audio " + count);
        }

    }

    public void setIsSuspended(boolean isSuspend) {
        this.isSuspended = isSuspend;
        if (!isSuspended) {
            synchronized (obj) {
                obj.notify();
            }
        }
    }

    private boolean isSuspended = true;
    private Object obj = new Object();


    private void startPlaying() {

        mPlayThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    initAudioTracker();

                    while (isPlaying) {
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
                                if (audioTrack != null) {
                                    //audioTrack.play();
                                    audioTrack.write(data, 0, data.length);
                                    LogUtils.e("palying ......................................... ");
                                   // LogUtils.e("payer one package");
                                    //audioTrack.stop();
                                    if (mListPhoneData != null) {
                                        mListPhoneData.remove(0);
                                    }
                                }
                            }

                        } else {
                            isSuspended = true;
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    freeAudioTracker();
                    mListPhoneData = null;
                }

            }
        });
        mPlayThread.start();
    }


    private void stopPlaying() {
        if (mPlayThread != null && mPlayThread.isAlive()) {
            mPlayThread.interrupt();
        }
    }


    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }


}


