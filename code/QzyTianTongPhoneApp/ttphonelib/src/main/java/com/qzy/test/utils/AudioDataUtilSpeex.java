package com.qzy.test.utils;



import org.lansir.ip.codecs.Speex;

import java.util.Arrays;

/**
 * Created by yanghao1 on 2017/4/19.
 */

public class AudioDataUtilSpeex {

    /*The frame size in hardcoded for this sample code but it doesn't have to be*/
    private static int encFrameSize = 160;
    private static int decFrameSize = 160;
    private static int encodedFrameSize = 28;

    private static AudioDataUtilSpeex speex;


    public static AudioDataUtilSpeex getInstance() {
        if (speex == null) {
            speex = new AudioDataUtilSpeex();
        }
        return speex;
    }

    private AudioDataUtilSpeex() {
          Speex.open(5);
    }

    /**
     * 将raw原始音频文件编码为Speex格式
     *
     * @param audioData 原始音频数据
     * @return 编码后的数据
     */
    public byte[] raw2spx(short[] audioData) {
        byte[] encodedData = new byte[audioData.length / encFrameSize * encodedFrameSize];
        for(int i = 0;i < 3; i++) {
            byte[] data = new byte[encodedFrameSize];
            short[] raw = new short[encFrameSize];
            System.arraycopy(audioData,i * encFrameSize,raw,0,encFrameSize);
            Speex.encode(raw, data);
            System.arraycopy(data,0,encodedData,i*encodedFrameSize,data.length);
        }
        return encodedData;
    }

    /**
     * 将Speex编码音频文件解码为raw音频格式
     *
     * @param encodedData 编码音频数据
     * @return 原始音频数据
     */
    public short[] spx2raw(byte[] encodedData) {
        short[] shortRawData = new short[encodedData.length * decFrameSize / encodedFrameSize];
        for(int i = 0;i < 3; i++) {
            short[] data = new short[decFrameSize];
            byte[] encode = new byte[encodedFrameSize];
            System.arraycopy(encodedData,i * encodedFrameSize,encode,0,encodedFrameSize);
            Speex.decode(encode, 0, data);
            System.arraycopy(data,0,shortRawData,i*decFrameSize,data.length);
        }
        return shortRawData;
    }

   /* public static short[] dspraw(short[] encodedData) {
        Speex.getInstance().preprocessRunDenoise(encodedData);
        return encodedData;
    }*/

    /**
     * 释放音频编解码资源
     */
    public void free() {
        Speex.close();
    }
}
