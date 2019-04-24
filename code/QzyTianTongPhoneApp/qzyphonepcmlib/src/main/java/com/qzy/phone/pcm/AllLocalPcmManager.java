package com.qzy.phone.pcm;

import android.content.Context;
import android.support.annotation.Keep;
import android.util.Log;
import android.widget.Toast;

import com.example.nativeaudio.NativeAudio;

/**
 * Created by yj.zhang on 2018/9/1.
 */

public class AllLocalPcmManager {

    private Thread thread;

    public static AllLocalPcmManager instance;

    private Context mContext;

    private volatile boolean isAudioDeviceInit = false;

    private boolean isStart = false;

    public static AllLocalPcmManager getInstance(Context context){
        if(instance == null){
            instance = new AllLocalPcmManager(context);
        }
        return  instance;
    }

    private AllLocalPcmManager(Context context){
        this.mContext = context;
        init();
    }

    private void init(){
       try{
           Log.e("NativeAudio_zyj","...AllLocalPcmManager........init.......");
           thread = new Thread(new Runnable() {
               @Override
               public void run() {
                   Log.e("NativeAudio_zyj","...AllLocalPcmManager........init...22222...");
                   NativeAudio.createUdpSocket("192.168.43.1",(short) 8999);
               }
           });
           thread.start();
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    /**
     * 初始化audio device
     */
    public void initAudioDevice(){
        try{
            synchronized (AllLocalPcmManager.class) {
                if (!isAudioDeviceInit) {
                    isAudioDeviceInit = true;
                    NativeAudio.createEngine(8000, 1, 1, 480);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 释放播放器
     */
    public void releaseAudioDevice(){
        try{
            if(isAudioDeviceInit) {
                isAudioDeviceInit = false;
                NativeAudio.realeseAudioDevice();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void start(){
        try{
            Log.e("NativeAudio_zyj","...AllLocalPcmManager........start.......");
            //if(thread != null) {

                isStart = true;
                initAudioDevice();

                Log.i("NativeAudio_zyj", "start play !!!!");
                //NativeAudio.startPlayer();

                Log.i("NativeAudio_zyj", "start record !!!!");
            long startTime = System.currentTimeMillis();
            Log.e("NativeAudio_zyj","...AllLocalPcmManager........startRecord....start..... " );
            NativeAudio.startRecord();
            Log.e("NativeAudio_zyj","...AllLocalPcmManager........startRecord....end..... "  + (System.currentTimeMillis() - startTime) );

           // }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void startPlayer(){
        try{
            Log.e("NativeAudio_zyj","...AllLocalPcmManager........startPlayer.......");
            //if(thread != null){
                initAudioDevice();

            long startTime = System.currentTimeMillis();
            Log.e("NativeAudio_zyj","...AllLocalPcmManager........startPlayer....start..... " );
            NativeAudio.startPlayer();
            Log.e("NativeAudio_zyj","...AllLocalPcmManager........startPlayer....end..... "  + (System.currentTimeMillis() - startTime) );

           // }
           // NativeAudio.startRecord();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stop(){
        try{
            Log.e("NativeAudio_zyj","...AllLocalPcmManager........stop....isAudioDeviceInit... " + isAudioDeviceInit); // if(thread != null){
            if(isAudioDeviceInit) {
                Log.e("NativeAudio_zyj","...AllLocalPcmManager........stopPlayer....start..... " );
                Long startTime = System.currentTimeMillis();
                NativeAudio.stopPlayer();
                Log.e("NativeAudio_zyj","...AllLocalPcmManager........stopPlayer....end..... "  + (System.currentTimeMillis() - startTime) );

                startTime = System.currentTimeMillis();
                Log.e("NativeAudio_zyj","...AllLocalPcmManager........stopRecord....start..... " );
                NativeAudio.stopRecord();
                Log.e("NativeAudio_zyj","...AllLocalPcmManager........stopRecord....end..... "  + (System.currentTimeMillis() - startTime) );

                startTime = System.currentTimeMillis();
                Log.e("NativeAudio_zyj","...AllLocalPcmManager........releaseAudioDevice....start..... " );
                releaseAudioDevice();
                Log.e("NativeAudio_zyj","...AllLocalPcmManager........releaseAudioDevice....end..... "  + (System.currentTimeMillis() - startTime) );
            }
          //  }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void free(){
        try{
            Log.e("NativeAudio_zyj","...AllLocalPcmManager........free.......");
            NativeAudio.realeseAudioDevice();
            NativeAudio.shutdown();

            if(thread != null || thread.isAlive()){
                thread.interrupt();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        instance = null;
        thread = null;
    }

}
