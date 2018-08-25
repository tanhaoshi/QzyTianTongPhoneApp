package com.qzy.audiosocket.input;

import android.os.Handler;

import com.qzy.audiosocket.net.OkioSocketManager;
import com.qzy.intercom.data.AudioData;
import com.qzy.intercom.data.MessageQueue;
import com.qzy.intercom.job.JobHandler;
import com.qzy.intercom.network.Unicast;
import com.qzy.intercom.util.Constants;
import com.qzy.utils.ByteUtils;

import java.io.IOException;
import java.net.DatagramPacket;

/**
 * Socket发送
 *
 * @author yanghao1
 */
public class SenderSoket extends JobHandler {

    private int id = 0;


    private OkioSocketManager socketManager;

    public SenderSoket(Handler handler) {
        super(handler);

        socketManager = OkioSocketManager.getInstance();
    }

    @Override
    public void run() {
        try {
            AudioData audioData;
            while ((audioData = MessageQueue.getInstance(MessageQueue.SENDER_DATA_QUEUE).take()) != null) {
                int len = audioData.getEncodedData().length;
                // LogUtils.e("audioData.getEncodedData().length = " + len);
               /* byte[] data = new byte[audioData.getEncodedData().length + 1];
                if (id < 256) {
                    id++;
                } else {
                    id = 1;
                }

                data[0] = ByteUtils.intToByte(id);
                System.arraycopy(audioData.getEncodedData(), 0, data, 1, len);*/

                if(socketManager == null || socketManager.isFree()){
                     break;
                }
                byte[] data = audioData.getEncodedData();
                socketManager.getmSink().write(data);
                socketManager.getmSink().flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void free() {
        if (socketManager != null) {
            socketManager.free();
        }
    }
}
