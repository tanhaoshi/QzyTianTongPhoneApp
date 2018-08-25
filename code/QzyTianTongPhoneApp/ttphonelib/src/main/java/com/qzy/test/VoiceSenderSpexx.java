package com.qzy.test;

import android.media.AudioRecord;
import android.os.Process;
import android.util.Log;

import com.qzy.intercom.data.AudioData;
import com.qzy.intercom.util.AudioDataUtil;
import com.qzy.intercom.util.Constants;
import com.qzy.test.buffer.MyBuffer;
import com.qzy.utils.ByteUtils;
import com.qzy.utils.LogUtils;
import com.qzy.voice.VoiceInputUtils;

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
    private InetAddress mInetAddress;




    private AudioRecord audioRecord;
    // 音频大小
    private int inAudioBufferSize;
    // 录音标志
    private boolean isRecording = false;


    public VoiceSenderSpexx(){

    }

    private void initAudioRecoder(){
        // 获取音频数据缓冲段大小
        inAudioBufferSize = AudioRecord.getMinBufferSize(
                Constants.sampleRateInHz, Constants.inputChannelConfig, Constants.audioFormat);
        LogUtils.e("inAudioBufferSize = " + inAudioBufferSize);
        // 初始化音频录制
        audioRecord = new AudioRecord(Constants.audioSource,
                Constants.sampleRateInHz, Constants.inputChannelConfig, Constants.audioFormat, inAudioBufferSize);
    }


    /**
     * 初始化socket
     *
     * @throws Exception
     */
    public void initSocket() throws Exception {
        mDatagramSocket = new DatagramSocket();
        mInetAddress = InetAddress.getByName(Constants.UNICAST_BROADCAST_IP);
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
            initAudioRecoder();
        } catch (Exception e) {
            Log.e(TAG, "Initialize Socket Failed!");
            e.printStackTrace();
            return;
        }

        short[] rawData = new short[160 * 3 * 2 ];
        while (isRecording()) {

            // LogUtils.d("1111 write pcm to phone my tid = " + android.os.Process.myTid()+" name "+Thread.currentThread().getName());
            if (audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_STOPPED) {
                audioRecord.startRecording();
            }
            // 实例化音频数据缓冲
           /* short[] rawData = new short[1024 * 2];
            audioRecord.read(rawData, 0, inAudioBufferSize);*/

            audioRecord.read(rawData, 0, rawData.length);



            //LogUtils.e("rawData.length = " + rawData.length);
            int readSize = rawData.length;
            short[] leftChannelAudioData = new short[readSize / 2];
            for (int i = 0; i < readSize / 2; i = i + 2) {
                leftChannelAudioData[i] = rawData[2 * i];
                leftChannelAudioData[i + 1] = rawData[2 * i + 1];
                //rightChannelAudioData[i] =  audiodata[2*i+2];
                //rightChannelAudioData[i+1] = audiodata[2*i+3];
            }



            byte[] left = ByteUtils.toByteArray(leftChannelAudioData);
            /*if (VoiceInputUtils.calculateVolume(left, 16) < 1) {
                //rawData = new byte[160 * 6 * 2 ];
                continue;
            }*/
           // byte[] audioDat = AudioDataUtil.raw2spx(leftChannelAudioData);
            byte[] audioDat = left;
            if (leftChannelAudioData != null) {
                if (index < 256) {
                    index = index + 1;
                } else {
                    index = 1;
                }
                if (index != 0) {

                    byte[] dataValue = new byte[audioDat.length + 1];
                    dataValue[0] = ByteUtils.intToByte(index);
                    System.arraycopy(audioDat, 0, dataValue, 1, audioDat.length);

                    mPacket = new DatagramPacket(dataValue, dataValue.length, mInetAddress, Constants.UNICAST_PORT_1);
                    try {
                        mDatagramSocket.send(mPacket);
                        //LogUtils.e("send pcm to cat index = " + ByteUtils.byteToInt(dataValue[0]));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        freeAudioRecorder();
        releaseSocket();
        AudioDataUtil.free();
        Log.i(TAG, "run() =====>>> End");

    }

    private void freeAudioRecorder(){
        audioRecord.stop();
        audioRecord.release();
        audioRecord = null;
    }


    public boolean isRecording() {
        return isRecording;
    }

    public void setRecording(boolean recording) {
        isRecording = recording;
    }
}
