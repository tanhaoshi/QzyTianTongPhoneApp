package com.qzy.tiantong.service.rtptest;


import com.qzy.tiantong.service.rtptest.audio.AudioWrapper;
import com.qzy.tiantong.service.rtptest.audio.RtpApp;

import java.io.IOException;
import java.net.SocketException;

/**
 * Created by yj.zhang on 2018/8/13.
 */

public class RtpManager {

    public RtpApp rtpApp = null;

    private AudioWrapper audioWrapper;

    public RtpManager(){
        initRtp();
        Global.rtpManager = this;
    }

    private void initRtp(){
       new Thread(new Runnable() {
           @Override
           public void run() {
               Global.ok = false;
               if (rtpApp != null) {
                   try {
                       rtpApp.releaseSocket();
                   } catch (IOException e) {
                       return;
                   }
                   rtpApp = null;
               }
               try {
                   rtpApp = new RtpApp(Global.IP, Global.PROT);
               } catch (SocketException e) {
                   e.printStackTrace();
                   return;
               }

               audioWrapper = AudioWrapper.getInstance();

               audioWrapper.startRecord();
               audioWrapper.startListen();
               Global.ok = true;
           }
       }).start();
    }


    private void freeRtp(){
        Global.ok = false;
        audioWrapper.stopRecord();
        audioWrapper.stopListen();

        if(audioWrapper != null){
            audioWrapper.free();
        }

        if (rtpApp != null) {
            try {
                rtpApp.releaseSocket();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            rtpApp = null;
        }
    }

    public void free(){
        freeRtp();
        Global.free();
    }

}
