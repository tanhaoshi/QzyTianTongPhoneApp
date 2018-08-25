package com.qzy.intercom.output;

import android.os.Handler;
import android.os.Message;


import com.qzy.audiosocket.net.OkioSocketManager;
import com.qzy.intercom.data.AudioData;
import com.qzy.intercom.data.MessageQueue;
import com.qzy.intercom.job.JobHandler;
import com.qzy.intercom.network.Multicast;
import com.qzy.intercom.network.Unicast;
import com.qzy.intercom.util.AudioDataUtil;
import com.qzy.intercom.util.Buffer;
import com.qzy.intercom.util.Command;
import com.qzy.intercom.util.Constants;
import com.qzy.intercom.util.IPUtil;
import com.qzy.utils.ByteUtils;
import com.qzy.utils.LogUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by yanghao1 on 2017/4/12.
 */

public class Receiver extends JobHandler {
    private boolean isReceiver;

    public Receiver(Handler handler) {
        super(handler);

    }

    @Override
    public void run() {
        isReceiver = true;
        // 设置接收缓冲段
       // byte[] receivedData = new byte[28 * 3 + 1];
        byte[] receivedData = new byte[4096 + 1];
        while (isReceiver) {
            if(!isReceiver){
                break;
            }

            DatagramPacket datagramPacket = new DatagramPacket(receivedData, receivedData.length);
            try {
                // 接收数据报文
                Unicast.getUnicast().getReceiveSocket().receive(datagramPacket);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 判断数据报文类型，并做相应处理
            if (datagramPacket.getLength() == Command.DISC_REQUEST.getBytes().length ||
                    datagramPacket.getLength() == Command.DISC_LEAVE.getBytes().length ||
                    datagramPacket.getLength() == Command.DISC_RESPONSE.getBytes().length) {
                handleCommandData(datagramPacket);
            } else {
                handleAudioData(datagramPacket);
            }
        }
    }

    /**
     * 处理命令数据
     *
     * @param packet 命令数据包
     */
    private void handleCommandData(DatagramPacket packet) {
        String content = new String(packet.getData()).trim();
      /*  if (content.equals(Command.DISC_REQUEST) &&
                !packet.getAddress().toString().equals("/" + IPUtil.getLocalIPAddress())) {
            byte[] feedback = Command.DISC_RESPONSE.getBytes();
            // 发送数据
            DatagramPacket sendPacket = new DatagramPacket(feedback, feedback.length,
                    packet.getAddress(), Constants.MULTI_BROADCAST_PORT);
            try {
                Multicast.getMulticast().getMulticastSocket().send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 发送Handler消息
            //sendMsg2MainThread(packet.getAddress().toString(), IntercomService.DISCOVERING_RECEIVE);
        }
        else if (content.equals(Command.DISC_RESPONSE) &&
                !packet.getAddress().toString().equals("/" + IPUtil.getLocalIPAddress())) {
            // 发送Handler消息
           // sendMsg2MainThread(packet.getAddress().toString(), IntercomService.DISCOVERING_RECEIVE);
        } else if (content.equals(Command.DISC_LEAVE) &&
                !packet.getAddress().toString().equals("/" + IPUtil.getLocalIPAddress())) {
            //sendMsg2MainThread(packet.getAddress().toString(), IntercomService.DISCOVERING_LEAVE);
        }*/
    }

    /**
     * 处理音频数据
     *
     * @param packet 音频数据包
     */
    Buffer buffer;

    private void handleAudioData(DatagramPacket packet) {
       // LogUtils.e("read  pcm data inde =" + ByteUtils.byteToInt(packet.getData()[0]));
       // LogUtils.e("packet len =" + packet.getLength());
       // LogUtils.e("packet data len =" + packet.getData().length);
        byte[] data = Arrays.copyOfRange(packet.getData(), 1, packet.getLength());

       /* int readSize = data.length;
        byte[] chanelData = new byte[readSize * 2];
        for (int i = 0; i < readSize; i = i + 2) {
            chanelData[2 * i] = data[i];
            chanelData[2 * i + 1] = data[i + 1];
            chanelData[2 * i + 2] = data[i];
            chanelData[2 * i + 3] = data[i + 1];
        }

        short[] pcmData = ByteUtils.byteArrayToShortArray(chanelData);
        AudioData audioData = new AudioData(pcmData);*/
        AudioData audioData = new AudioData(data);
       // LogUtils.d("read  pcm data data.len =" + data.length);
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
