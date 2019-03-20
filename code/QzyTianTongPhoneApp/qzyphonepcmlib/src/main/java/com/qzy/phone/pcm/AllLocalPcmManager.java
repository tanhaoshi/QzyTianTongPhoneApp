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
            if(thread != null) {
                Log.i("zyj","start play !!!!");
                NativeAudio.startPlayer();
                NativeAudio.startRecord();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void startPlayer(){
        try{
            Log.e("startPlayer","...AllLocalPcmManager........startPlayer.......");
            if(thread != null){
                NativeAudio.startPlayer();
            }
           // NativeAudio.startRecord();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stop(){
        try{
            Log.e("zyj","...AllLocalPcmManager........stop.......");
            if(thread != null){
                NativeAudio.stopPlayer();
                NativeAudio.stopRecord();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void free(){
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
        thread = null;
    }

}
