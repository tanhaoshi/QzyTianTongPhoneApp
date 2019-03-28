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

    private boolean isAudioDeviceInit = false;

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
           Log.e("zyj","...AllLocalPcmManager........init.......");
           thread = new Thread(new Runnable() {
               @Override
               public void run() {
                   Log.e("zyj","...AllLocalPcmManager........init...22222...");
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
            if(!isAudioDeviceInit) {
                isAudioDeviceInit = true;
                NativeAudio.createEngine(8000, 1, 1, 480);
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
            isAudioDeviceInit = false;
            NativeAudio.realeseAudioDevice();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void start(){
        try{
            Log.e("zyj","...AllLocalPcmManager........start.......");
            //if(thread != null) {

                initAudioDevice();

                Log.i("zyj","start play !!!!");
                NativeAudio.startPlayer();

                Log.i("zyj","start record !!!!");
                NativeAudio.startRecord();
           // }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void startPlayer(){
        try{
            Log.e("zyj","...AllLocalPcmManager........startPlayer.......");
            //if(thread != null){
                initAudioDevice();
                NativeAudio.startPlayer();
           // }
           // NativeAudio.startRecord();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stop(){
        try{
            Log.e("zyj","...AllLocalPcmManager........stop.......");
           // if(thread != null){
                NativeAudio.stopPlayer();
                NativeAudio.stopRecord();
                releaseAudioDevice();
          //  }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void free(){
        try{
            Log.e("zyj","...AllLocalPcmManager........free.......");
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
