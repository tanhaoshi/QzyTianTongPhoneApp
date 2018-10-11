package com.qzy.locallib.selfpcm;

import com.qzy.locallib.selfpcm.jni.LocalPcmManager;
import com.qzy.locallib.selfpcm.udp.UdpServiceManagerSpexx;
import com.qzy.locallib.selfpcm.voice.VoiceReaderSpexx;
import com.qzy.tiantong.lib.utils.LogUtils;

/**
 * Created by yj.zhang on 2018/8/10.
 */

public class TtPcmIntercomManager {

    private LocalPcmManager localPcmManager;

    private UdpServiceManagerSpexx udpServiceManager;

    public TtPcmIntercomManager() {
        initPcmManager();
        initUdpFunction();

        localPcmManager.openPlayAndRecorderDevice();
    }

    /**
     * 底层pcm管理类
     */
    private void initPcmManager() {
        localPcmManager = new LocalPcmManager(new LocalPcmManager.IReadPcmData() {

            private int index = 0;

            @Override
            public void readData(byte[] data) {
                try {

                    if (data == null && data.length > 100) {
                        LogUtils.e("data is null");
                        return;
                    }
                    if (udpServiceManager != null) {


                        udpServiceManager.setVoiceSenderData(data);
                        udpServiceManager.setVoiceSenderResume();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 初始化应用层 udp pcm数据协议
     */

    private void initUdpFunction() {
        udpServiceManager = new UdpServiceManagerSpexx();
        udpServiceManager.startVoiceReader(readerUdpDataListener);
        udpServiceManager.startVoiceSender();


        //回环必须要打开
        //localPcmManager.openPlayAndRecorderDevice();
    }


    /**
     * 读取udp pcm 数据回调
     */
    private VoiceReaderSpexx.IReaderData readerUdpDataListener = new VoiceReaderSpexx.IReaderData() {
        @Override
        public void onReadData(byte[] data) {
            if (localPcmManager != null) {
                localPcmManager.setPcmData(data);
            }

        }

        @Override
        public void onReadData() {

        }
    };

    public void release() {
        if (localPcmManager != null) {
            localPcmManager.release();
        }

        if (udpServiceManager != null) {
            udpServiceManager.release();
        }
    }


}
