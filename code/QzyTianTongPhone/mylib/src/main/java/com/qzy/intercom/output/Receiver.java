package com.qzy.intercom.output;

import android.os.Handler;
import android.os.Message;


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

    public Receiver(Handler handler) {
        super(handler);
    }

    @Override
    public void run() {
        while (true) {
            // 设置接收缓冲段
            byte[] receivedData = new byte[4096 + 1];
            DatagramPacket datagramPacket = new DatagramPacket(receivedData, receivedData.length);
            try {
                // 接收数据报文
                Unicast.getUnicast().getReceiveSocket().receive(datagramPacket);
            } catch (IOException e) {
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
       // LogUtils.d("read  pcm data inde =" + ByteUtils.byteToInt(data[0]));
        byte[] data = Arrays.copyOfRange(packet.getData(),1,1024 * 4  + 1);
        /*byte[] leftChannelAudioData = new byte[data.length/2];
        for(int i = 0; i <leftChannelAudioData.length ; i = i + 2)
        {
            leftChannelAudioData[i] = data[2*i];
            leftChannelAudioData[i+1] = data[2*i+1];

        }*/
        short[] pcmData = ByteUtils.byteArrayToShortArray(data);

      /*  short[] pcmData = ByteUtils.byteArrayToShortArray(data);
        short[] sdata = new short[(pcmData.length / 160 + 1) * 160];
        System.arraycopy(pcmData,0,sdata,0,pcmData.length);
        byte[] encodedData = AudioDataUtil.raw2spx(sdata);
        AudioData audioData = new AudioData(encodedData);*/

      /*  AudioData audioData = new AudioData();
        audioData.setRawData(ByteUtils.byteArrayToShortArray(data));*/
       /* if(buffer == null){
            buffer = new Buffer(data,data.length);

            byte[] tmp = buffer.readAndMove(12 * 320);
            short[] pcmData = ByteUtils.byteArrayToShortArray(tmp);
            AudioData audioData = new AudioData(pcmData);
            MessageQueue.getInstance(MessageQueue.DECODER_DATA_QUEUE).put(audioData);

        }else{
            buffer.append(data,data.length);
            int size = buffer.getAvailabeSize() / 320;
            if(size > 0){
                byte[] tmp = buffer.readAndMove(size * 320);
                short[] pcmData = ByteUtils.byteArrayToShortArray(tmp);
                AudioData audioData = new AudioData(pcmData);
                MessageQueue.getInstance(MessageQueue.DECODER_DATA_QUEUE).put(audioData);
            }
        }*/




       // byte[] encodedData = Arrays.copyOf(packet.getData(), packet.getLength());
       // AudioData audioData = new AudioData(encodedData);
        //MessageQueue.getInstance(MessageQueue.DECODER_DATA_QUEUE).put(audioData);

        AudioData audioData = new AudioData(pcmData);
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
        Unicast.getUnicast().free();
    }
}
