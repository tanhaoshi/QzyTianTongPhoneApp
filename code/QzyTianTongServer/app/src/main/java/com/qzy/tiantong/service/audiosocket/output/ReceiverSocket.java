package com.qzy.audiosocket.output;

import android.os.Handler;
import android.os.Message;


import com.qzy.tiantong.service.audiosocket.net.OkioSocketManager;
import com.qzy.tiantong.service.intercom.data.AudioData;
import com.qzy.tiantong.service.intercom.data.MessageQueue;
import com.qzy.tiantong.service.intercom.job.JobHandler;
import com.qzy.tiantong.service.intercom.network.Unicast;

import java.net.DatagramPacket;

/**
 * Created by yanghao1 on 2017/4/12.
 */

public class ReceiverSocket extends JobHandler {
    private boolean isReceiver;
    private OkioSocketManager socketManager;

    public ReceiverSocket(Handler handler) {
        super(handler);

        socketManager = OkioSocketManager.getInstance();
    }

    @Override
    public void run() {
        try {
            isReceiver = true;
            byte[] receivedData = new byte[160 * 6];
            while (isReceiver) {
                if (!isReceiver) {
                    break;
                }
                if(socketManager == null || socketManager.isFree() || socketManager.getmSource() == null){
                    Thread.sleep(2000);
                    continue;
                }
              int len = socketManager.getmSource().read(receivedData);
                if(len > 0){
                    handleAudioData(receivedData);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理命令数据
     *
     * @param packet 命令数据包
     */
    private void handleCommandData(DatagramPacket packet) {

    }

    /**
     * 处理音频数据
     *
     * @param packet 音频数据包
     */

    private void handleAudioData(byte[] data) {
        AudioData audioData = new AudioData(data);
        MessageQueue.getInstance(MessageQueue.DECODER_DATA_QUEUE).put(audioData);
    }

    /**
     * 发送Handler消息
     *
     * @param content 内容
     */
    private void sendMsg2MainThread(String content, int msgWhat) {
        Message msg = new Message();
        msg.what = msgWhat;
        msg.obj = content;
        handler.sendMessage(msg);
    }

    @Override
    public void free() {
        //Multicast.getMulticast().free();
        isReceiver = false;
        Unicast.getUnicast().free();
    }
}
