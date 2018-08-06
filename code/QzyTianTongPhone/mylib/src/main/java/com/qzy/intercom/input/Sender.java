package com.qzy.intercom.input;

import android.os.Handler;


import com.qzy.intercom.data.AudioData;
import com.qzy.intercom.data.MessageQueue;
import com.qzy.intercom.job.JobHandler;
import com.qzy.intercom.network.Multicast;
import com.qzy.intercom.network.Unicast;
import com.qzy.intercom.util.Constants;
import com.qzy.utils.ByteUtils;
import com.qzy.utils.LogUtils;

import java.io.IOException;
import java.net.DatagramPacket;

/**
 * Socket发送
 *
 * @author yanghao1
 */
public class Sender extends JobHandler {

    private int id = 0;

    public Sender(Handler handler) {
        super(handler);
    }

    @Override
    public void run() {
        AudioData audioData;
        while ((audioData = MessageQueue.getInstance(MessageQueue.SENDER_DATA_QUEUE).take()) != null) {
            int len = audioData.getEncodedData().length;
            //LogUtils.e("audioData.getEncodedData().length = " + len);
            byte[] data = new byte[audioData.getEncodedData().length + 1];
            if (id < 256) {
                id++;
            } else {
                id = 0;
            }
            data[0] = ByteUtils.intToByte(id);
            System.arraycopy(audioData.getEncodedData(),0,data,1,len);

            DatagramPacket datagramPacket = new DatagramPacket(
                    data, data.length,
                   Unicast.getUnicast().getInetAddress(), Constants.UNICAST_PORT);
            try {
                //Multicast.getMulticast().getMulticastSocket().send(datagramPacket);

                Unicast.getUnicast().getSendSocket().send(datagramPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void free() {
        //Multicast.getMulticast().free();
        Unicast.getUnicast().free();
    }
}
