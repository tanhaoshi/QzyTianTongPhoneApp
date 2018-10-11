package com.qzy.intercom.output;

import android.os.Handler;
import android.os.Message;

import com.qzy.intercom.job.JobHandler;
import com.qzy.intercom.network.Unicast;
import com.qzy.intercom.util.Command;
import com.qzy.tiantong.ttpcm.TtAudioTrack;

import java.net.DatagramPacket;

/**
 * Created by yanghao1 on 2017/4/12.
 */

public class ReceiverNew extends JobHandler {

    private boolean isBreak = false;

    private TtAudioTrack audioTrack;

    public ReceiverNew(Handler handler) {
        super(handler);
        audioTrack = new TtAudioTrack();
        audioTrack.initTtAudioTrack();
    }

    @Override
    public void run() {
        while (true) {
            // LogUtils.d("ReceiverNew tid = " + android.os.Process.myTid()+" name "+Thread.currentThread().getName());
            if (isBreak) {
                break;
            }

            // 设置接收缓冲段
            byte[] receivedData = new byte[160 * 8 + 1];
            DatagramPacket datagramPacket = new DatagramPacket(receivedData, receivedData.length);
            try {
                // 接收数据报文
                if (Unicast.getUnicast().getReceiveSocket() != null) {
                    // long startTime = System.currentTimeMillis();
                    Unicast.getUnicast().getReceiveSocket().receive(datagramPacket);
                    // long end = System.currentTimeMillis() - startTime;
                    // LogUtils.e("receive pcm time == ====" + end);
                    /// LogUtils.e("receiver data .....");
                }
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
        Unicast.setUnicast(null);
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
    private void handleAudioData(DatagramPacket packet) {

        byte[] data = packet.getData();
        // AudioData audioData = new AudioData(packet.getData());
        //LogUtils.e("reece pcm index = " + ByteUtils.byteToInt(data[0]));
        //MessageQueue.getInstance(MessageQueue.DECODER_DATA_QUEUE).put(audioData);

        audioTrack.setPcmData(data);
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
        isBreak = true;
        //Multicast.getMulticast().free();
        Unicast.getUnicast().free();
        if (audioTrack != null) {
            audioTrack.release();
            audioTrack = null;
        }
    }
}
