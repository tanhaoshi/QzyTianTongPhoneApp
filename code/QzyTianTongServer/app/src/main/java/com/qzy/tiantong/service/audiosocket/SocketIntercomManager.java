package com.qzy.audiosocket;

import android.os.Handler;
import android.os.Message;

import com.qzy.audiosocket.output.DecoderSoket;
import com.qzy.audiosocket.output.ReceiverSocket;
import com.qzy.audiosocket.output.TrackerSoket;
import com.qzy.tiantong.service.audiosocket.input.EncoderSoket;
import com.qzy.tiantong.service.audiosocket.input.RecorderSocket;
import com.qzy.tiantong.service.audiosocket.input.SenderSoket;
import com.qzy.tiantong.service.intercom.data.MessageQueue;
import com.qzy.tiantong.service.intercom.util.SpeexUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by yj.zhang on 2018/8/1/001.
 */

public class SocketIntercomManager {

    // 音频输入
    private RecorderSocket recorder;
    private EncoderSoket encoder;
    private SenderSoket sender;

    // 音频输出
    private ReceiverSocket receiver;
    private DecoderSoket decoder;
    private TrackerSoket tracker;

    // 创建7个线程的固定大小线程池，分别执行DiscoverServer，以及输入、输出音频
    private ExecutorService threadPool;

    public SocketIntercomManager() {
        init();
    }

    public void init() {
        threadPool = Executors.newCachedThreadPool();
        // 初始化JobHandler
        initJobHandler();
    }

    /**
     * 初始化JobHandler
     */
    private void initJobHandler() {
        // 初始化音频输入节点
        recorder = new RecorderSocket(handler);
        recorder.setRecording(true);
        encoder = new EncoderSoket(handler);
        sender = new SenderSoket(handler);
        // 初始化音频输出节点
        receiver = new ReceiverSocket(handler);
        decoder = new DecoderSoket(handler);
        tracker = new TrackerSoket(handler);
        tracker.setPlaying(true);
        // 开启音频输入、输出
        threadPool.execute(recorder);
        threadPool.execute(encoder);
        threadPool.execute(sender);

        threadPool.execute(receiver);
        threadPool.execute(decoder);
        threadPool.execute(tracker);


    }

    private Handler handler = new AudioHandler(this);

    /**
     * Service与Runnable的通信
     */
    private static class AudioHandler extends Handler {

        private SocketIntercomManager service;

        private AudioHandler(SocketIntercomManager service) {
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

    private void startRecord() {
        if (!recorder.isRecording()) {
            recorder.setRecording(true);
            // tracker.setPlaying(false);
            threadPool.execute(recorder);
        }
    }


    private void stopRecord() {
        if (recorder.isRecording()) {
            recorder.setRecording(false);
            // tracker.setPlaying(true);
        }
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
        // 释放线程池
        if (threadPool != null) {
            threadPool.shutdownNow();
        }
        threadPool = null;
        MessageQueue.release();
        SpeexUtils.free();
    }

}
