package com.qzy.phone.pcm;

import android.util.Log;

import com.example.nativeaudio.NativeAudio;

/**
 * Created by yj.zhang on 2018/9/1.
 */

public class AllLocalPcmManager {

    private Thread thread;

    private static AllLocalPcmManager instance;

    public static AllLocalPcmManager getInstance(){
        if(instance == null){
            instance = new AllLocalPcmManager();
        }
        return  instance;
    }

    private AllLocalPcmManager(){
        init();
    }

    private  final void init(){

       try{
           Log.e("zyj","...AllLocalPcmManager........init.......");
           NativeAudio.createEngine(8000,1,1,480);
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

    public void start(){
        try{
            Log.e("zyj","...AllLocalPcmManager........start.......");
            NativeAudio.startPlayer();
            NativeAudio.startRecord();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    public void startPlayer(){
//        try{
//            Log.e("zyj","...AllLocalPcmManager........start.......");
//            NativeAudio.startPlayer();
//           // NativeAudio.startRecord();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    public void stop(){

        try{
            Log.e("zyj","...AllLocalPcmManager........stop.......");
            NativeAudio.stopPlayer();
            NativeAudio.stopRecord();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public final void free(){
        try{
            Log.e("zyj","...AllLocalPcmManager........free.......");

            NativeAudio.shutdown();

            if(thread != null || thread.isAlive()){
                thread.interrupt();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        instance = null;
    }

}
