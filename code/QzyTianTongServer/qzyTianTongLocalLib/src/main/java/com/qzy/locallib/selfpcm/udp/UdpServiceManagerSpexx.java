package com.qzy.locallib.selfpcm.udp;

import com.qzy.locallib.selfpcm.voice.VoiceReaderSpexx;
import com.qzy.locallib.selfpcm.voice.VoiceSenderSpexx;


/**
 * Created by yj.zhang on 2018/7/2/002.
 */

public class UdpServiceManagerSpexx {


    private VoiceReaderSpexx voiceReader;

    private VoiceSenderSpexx voiceSender;

    public UdpServiceManagerSpexx() {
    }


    /**
     * start接受手机端pcm
     */
    public void startVoiceReader(VoiceReaderSpexx.IReaderData callback) {
        voiceReader = new VoiceReaderSpexx(callback);
        voiceReader.start();
    }

    /**
     * stop接受手机端pcm
     */
    public void stopVoiceReader() {
        try {
            if (voiceReader != null) {
                voiceReader.releaseSocket();
                voiceReader.setIsStopped();
                if (voiceReader.isAlive()) {
                    voiceReader.interrupt();
                }
                voiceReader = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setVoiceReaderPause() {
        if (voiceReader != null) {
            voiceReader.setIsSuspended(true);
            voiceReader = null;
        }
    }

    public void setVoiceReaderResume() {
        if (voiceReader != null) {
            voiceReader.setIsSuspended(false);
            voiceReader = null;
        }
    }

    /**
     * start发送pcm到手机端
     */
    public void startVoiceSender() {
        voiceSender = new VoiceSenderSpexx();
      /*  try {
            voiceSender.initSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        voiceSender.start();
    }

    /**
     * start发送pcm到手机端
     */
    public void stopVoiceSender() {

        try {
            if (voiceSender != null) {
                voiceSender.releaseSocket();
                voiceSender.setIsStopped(true);
                //voiceSender.releaseSocket();
                if (voiceSender.isAlive()) {
                    voiceSender.interrupt();
                }

                voiceSender = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 设置发送pause
     */
    public void setVoiceSenderPause() {
        if (voiceSender != null) {
            voiceSender.setIsSuspended(true);
        }
    }

    /**
     * 设置发送resume
     */
    public void setVoiceSenderResume() {
        if (voiceSender != null) {
            voiceSender.setIsSuspended(false);
        }
    }

    public void setVoiceSenderData(byte[] data) {
        if (voiceSender != null) {
            voiceSender.setmByteBuffer(data);
        }
    }

    public void sendData(byte[] data) {
        if (voiceSender != null) {
            voiceSender.sendData(data);
        }
    }

    public byte[] getReadData() {
        if (voiceReader != null) {
            return voiceReader.getByteBuffer();
        }
        return null;
    }

    public void setReadData(byte[] data) {
        if (voiceReader != null) {
            voiceReader.setByteBuffer(data);
        }
    }


    public void release() {
        stopVoiceSender();
        stopVoiceReader();
    }
}
