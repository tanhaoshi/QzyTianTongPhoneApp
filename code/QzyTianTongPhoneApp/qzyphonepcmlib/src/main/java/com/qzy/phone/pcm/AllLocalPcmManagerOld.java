package com.qzy.phone.pcm;

import android.content.Context;
import android.util.Log;

import com.example.nativeaudio.NativeAudio;

/**
 * Created by yj.zhang on 2018/9/1.
 */

public class AllLocalPcmManagerOld {

    private Thread thread;

    public static AllLocalPcmManagerOld instance;

    private Context mContext;

    private boolean isInitSuccess = false;

    private boolean isInitScoket = false;

    public static AllLocalPcmManagerOld getInstance(Context context) {
        if (instance == null) {
            instance = new AllLocalPcmManagerOld(context);
        }
        return instance;
    }

    private AllLocalPcmManagerOld(Context context) {
        this.mContext = context;
        init();
    }


    /**
     * 初始化底层资源
     */
    public void init() {
        try {
            Log.e("zyj", "...AllLocalPcmManager........init.......");
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.e("zyj", "...AllLocalPcmManager........init...22222...");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    NativeAudio.createUdpSocket("192.168.43.1", (short) 8999);
                    isInitScoket = true;
                }
            });
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 初始化 player
     */
    public void initPlayer() {
        try {
            Log.e("zyj", "...AllLocalPcmManager........initPlayer.......");
            NativeAudio.createEngine(8000, 1, 1, 480);
            isInitSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void start() {
        try {
            Log.e("zyj", "...AllLocalPcmManager........start.......");
            if (thread != null) {
                Log.i("zyj", "start play !!!!");
                NativeAudio.startPlayer();
                NativeAudio.startRecord();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startPlayer() {
        try {
            Log.e("startPlayer", "...AllLocalPcmManager........startPlayer.......");
            if (thread != null) {
                NativeAudio.startPlayer();
            }
            // NativeAudio.startRecord();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            Log.e("zyj", "...AllLocalPcmManager........stop.......");
            if (thread != null) {
                NativeAudio.stopPlayer();
                NativeAudio.stopRecord();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void free() {
        try {
            Log.e("zyj", "...AllLocalPcmManager........free.......");

            NativeAudio.shutdown();
            if (thread != null || thread.isAlive()) {
                thread.interrupt();
            }
            isInitSuccess = false;
            isInitScoket = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        instance = null;
        thread = null;
    }

    public boolean isInitSuccess() {
        return isInitSuccess;
    }

    public boolean isInitScoket() {
        return isInitScoket;
    }
}
