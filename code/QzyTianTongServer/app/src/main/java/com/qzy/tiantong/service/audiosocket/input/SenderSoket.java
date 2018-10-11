package com.qzy.tiantong.service.audiosocket.input;

import android.os.Handler;

import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.audiosocket.net.OkioSocketManager;
import com.qzy.tiantong.service.intercom.data.AudioData;
import com.qzy.tiantong.service.intercom.data.MessageQueue;
import com.qzy.tiantong.service.intercom.job.JobHandler;


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

                if(socketManager == null || socketManager.isFree() || socketManager.getmSink() == null){
                    Thread.sleep(2000);
                    continue;
                }
                byte[] data = audioData.getEncodedData();
                socketManager.getmSink().write(data);
                socketManager.getmSink().flush();
                LogUtils.e("send data ....");
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
