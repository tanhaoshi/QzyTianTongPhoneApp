package com.qzy.tiantong.service.intercom;

import android.os.Handler;
import android.os.Message;


import com.qzy.tiantong.service.intercom.data.MessageQueue;
import com.qzy.tiantong.service.intercom.input.Encoder;
import com.qzy.tiantong.service.intercom.input.Recorder;
import com.qzy.tiantong.service.intercom.input.Sender;
import com.qzy.tiantong.service.intercom.output.Decoder;
import com.qzy.tiantong.service.intercom.output.Receiver;
import com.qzy.tiantong.service.intercom.output.Tracker;
import com.qzy.tiantong.service.intercom.util.SpeexUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by yj.zhang on 2018/8/1/001.
 */

public class IntercomManager {

    // 音频输入
    private Recorder recorder;
    private Encoder encoder;
    private Sender sender;

    // 音频输出
    private Receiver receiver;
    private Decoder decoder;
    private Tracker tracker;

    // 创建7个线程的固定大小线程池，分别执行DiscoverServer，以及输入、输出音频
    private ExecutorService threadPool = Executors.newCachedThreadPool();

    public IntercomManager() {

        initData();
    }

    private void initData() {
        // 初始化JobHandler
        initJobHandler();
    }

    /**
     * 初始化JobHandler
     */
    private void initJobHandler() {


        // 初始化音频输入节点
        recorder = new Recorder(handler);
        recorder.setRecording(true);
        encoder = new Encoder(handler);
        sender = new Sender(handler);
        // 初始化音频输出节点
        receiver = new Receiver(handler);
        decoder = new Decoder(handler);
        tracker = new Tracker(handler);
        tracker.setPlaying(true);

        //recorder
        threadPool.execute(recorder);
        threadPool.execute(encoder);
        threadPool.execute(sender);


        //player
        threadPool.execute(receiver);
        threadPool.execute(decoder);
        threadPool.execute(tracker);

    }

    private Handler handler = new AudioHandler(this);

    /**
     * Service与Runnable的通信
     */
    private static class AudioHandler extends Handler {

        private IntercomManager service;

        private AudioHandler(IntercomManager service) {
            this.service = service;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            /*if (msg.what == DISCOVERING_SEND) {
                Log.i("IntercomService", "发送消息");
            } else if (msg.what == DISCOVERING_RECEIVE) {
                service.findNewUser((String) msg.obj);
            } else if (msg.what == DISCOVERING_LEAVE) {
                service.removeUser((String) msg.obj);
            }*/
        }
    }

    /**
     * 开始读取天通模块pcm数据线程
     */
    public void startRecord() {

        //recorder.setRecording(true);
        // 开启音频输入、输出
        threadPool.execute(recorder);
        // threadPool.execute(encoder);
        // threadPool.execute(sender);

    }

    /**
     * 停止读取天通模块pcm数据线程
     */
    public void stopRecord() {
        recorder.free();
    }


    /**
     * 开启往天通模块写入pcm数据
     */
    public void startPlayer() {
        //threadPool.execute(receiver);
        // threadPool.execute(decoder);
        threadPool.execute(tracker);
    }

    /**
     * 停止往天通模块写入pcm数据
     */
    public void stopPlayer() {
        tracker.free();
    }


    /**
     * 释放系统资源
     */
    public void release() {
        // 释放线程资源
        recorder.free();
        encoder.free();
        sender.free();
        receiver.free();
        decoder.free();
        tracker.free();

        // 释放线程池
        if (threadPool != null) {
            threadPool.shutdownNow();
        }
        threadPool = null;
        MessageQueue.release();
        SpeexUtils.free();
    }

}
