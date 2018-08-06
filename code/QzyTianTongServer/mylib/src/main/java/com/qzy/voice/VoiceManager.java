package com.qzy.voice;

/**
 * Created by yj.zhang on 2018/7/12/012.
 */

public class VoiceManager {

    static {
        System.loadLibrary("voicemanager");
    }

    public static native int initPcmPlayer();
    public static native int releasePcmPlayer();

    public static native int initPcmRecorder();
    public static native int releasePcmRecorder();


    public static native int writePcmData(byte[] data);

    public static native int readPcmData(byte[] data);
}
