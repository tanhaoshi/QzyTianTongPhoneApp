package com.qzy.intercom.input;

import android.media.AudioRecord;
import android.os.Handler;
import android.util.Log;

import com.qzy.intercom.data.AudioData;
import com.qzy.intercom.data.MessageQueue;
import com.qzy.intercom.job.JobHandler;
import com.qzy.intercom.util.AECUtil;
import com.qzy.intercom.util.Constants;
import com.qzy.utils.ByteUtils;
import com.qzy.utils.LogUtils;
import com.qzy.voice.VoiceInputUtils;

import java.util.Arrays;


/**
 * 音频录制数据格式ENCODING_PCM_16BIT，返回数据类型为short[]
 *
 * @author yanghao1
 */
public class Recorder extends JobHandler {

    private AudioRecord audioRecord;
    // 音频大小
    private int inAudioBufferSize;
    // 录音标志
    private boolean isRecording = false;

    private short[] lastRe;

    public Recorder(Handler handler) {
        super(handler);
        // 获取音频数据缓冲段大小
        inAudioBufferSize = AudioRecord.getMinBufferSize(
                Constants.sampleRateInHz, Constants.inputChannelConfig, Constants.audioFormat);
        LogUtils.e("inAudioBufferSize = " + inAudioBufferSize);
        // 初始化音频录制
        audioRecord = new AudioRecord(Constants.audioSource,
                Constants.sampleRateInHz, Constants.inputChannelConfig, Constants.audioFormat, inAudioBufferSize);

        if(AECUtil.isDeviceSupport()){
            AECUtil.initAEC(audioRecord.getAudioSessionId());
        }
    }

    public boolean isRecording() {
        return isRecording;
    }

    public void setRecording(boolean recording) {
        isRecording = recording;
    }

    @Override
    public void run() {
        short[] rawData = new short[inAudioBufferSize];
        while (isRecording) {
            if(!isRecording){
                break;
            }
            if (audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_STOPPED) {
                audioRecord.startRecording();
            }
            // 实例化音频数据缓冲
           /* short[] rawData = new short[1024 * 2];
            audioRecord.read(rawData, 0, inAudioBufferSize);*/

           int len = audioRecord.read(rawData, 0, rawData.length);
            //LogUtils.e("read len = " + len);


            //LogUtils.e("rawData.length = " + rawData.length);
            int readSize = len;
            short[] leftChannelAudioData = new short[readSize / 2];
            for (int i = 0; i < readSize / 2 ; i++) {
                leftChannelAudioData[i] = rawData[2 * i];
                //leftChannelAudioData[i + 1] = rawData[2 * i + 1];
                //rightChannelAudioData[i] =  audiodata[2*i+2];
                //rightChannelAudioData[i+1] = audiodata[2*i+3];
            }

            /*if (VoiceInputUtils.calculateVolume(ByteUtils.toByteArray(leftChannelAudioData), 16) < 2) {
                //rawData = new byte[160 * 6 * 2 ];
                continue;
            }*/


            if(handlerAudioData(leftChannelAudioData)){
                continue;
            }

        }
    }

    @Override
    public void free() {
        // 释放音频录制资源
        isRecording = false;
        audioRecord.stop();
        audioRecord.release();
        audioRecord = null;
        if(AECUtil.isDeviceSupport()){
            AECUtil.release();
        }


    }

    private boolean handlerAudioData(short[] leftChannelAudioData){
        //LogUtils.e("leftChannelAudioData len = " + leftChannelAudioData.length);
        int nowPackLen = 960;
        short[] myData = null;
        if(lastRe == null){
            myData = leftChannelAudioData;
        }else{
            myData = new short[lastRe.length + leftChannelAudioData.length];
            System.arraycopy(lastRe,0,myData,0,lastRe.length);
            System.arraycopy(leftChannelAudioData,0,myData,lastRe.length,leftChannelAudioData.length);
        }


        int count = myData.length / nowPackLen;
        for(int i=0;i < count;i++){
            short[] mdata = new short[nowPackLen];
            System.arraycopy(myData,i * nowPackLen,mdata,0,nowPackLen);
            AudioData audioData = new AudioData(mdata);
            MessageQueue.getInstance(MessageQueue.ENCODER_DATA_QUEUE).put(audioData);
        }

        int liuLen = myData.length % nowPackLen;
        if(liuLen == 0){
            lastRe = null;
        }else{
            lastRe = Arrays.copyOfRange(myData,count * nowPackLen,myData.length);
        }

        return false;
    }


    private boolean handlerAudioData1(short[] leftChannelAudioData){
        int nowPackLen = 960;
        short[] myData = null;
        if(leftChannelAudioData.length >= nowPackLen){ // 读取的数据比发送每包数据大
            if(lastRe != null){
                myData = new short[lastRe.length + leftChannelAudioData.length];
                System.arraycopy(lastRe,0,myData,0,lastRe.length);
                System.arraycopy(leftChannelAudioData,0,myData,lastRe.length,leftChannelAudioData.length);
            }else{
                myData = leftChannelAudioData;
            }

            int count = myData.length / nowPackLen;
            for(int i=0;i < count;i++){
                short[] mdata = new short[nowPackLen];
                System.arraycopy(myData,i * nowPackLen,mdata,0,nowPackLen);
                AudioData audioData = new AudioData(mdata);
                MessageQueue.getInstance(MessageQueue.ENCODER_DATA_QUEUE).put(audioData);
            }

            int liuLen = myData.length % nowPackLen;
            if(liuLen == 0){
                lastRe = null;
            }else{
                lastRe = Arrays.copyOfRange(myData,count * nowPackLen,myData.length);
            }

        }else{
            if(lastRe == null) {
                lastRe = leftChannelAudioData;
                return true;
            }

            myData = new short[lastRe.length + leftChannelAudioData.length];
            System.arraycopy(lastRe,0,myData,0,lastRe.length);
            System.arraycopy(leftChannelAudioData,0,myData,lastRe.length,leftChannelAudioData.length);

            int count = myData.length / nowPackLen;
            for(int i=0;i < count;i++){
                short[] mdata = new short[nowPackLen];
                System.arraycopy(myData,i * nowPackLen,mdata,0,nowPackLen);
                AudioData audioData = new AudioData(mdata);
                MessageQueue.getInstance(MessageQueue.ENCODER_DATA_QUEUE).put(audioData);
            }

            int liuLen = myData.length % nowPackLen;
            if(liuLen == 0){
                lastRe = null;
            }else{
                lastRe = Arrays.copyOfRange(myData,count * nowPackLen,myData.length);
            }

        }

        return false;
    }
}
