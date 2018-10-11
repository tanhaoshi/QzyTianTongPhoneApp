package com.qzy.tiantong.lib.localsocket;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;

import com.qzy.tiantong.lib.utils.ByteUtils;
import com.qzy.tiantong.lib.utils.LogUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2017/12/21/021.
 */

public class LocalSocketClient {
    /**
     * 底层服务名  例： htfsk
     */
    private static String SOCKET_NAME;
    private LocalSocket client;
    private LocalSocketAddress address;
    /**
     * 连接计数器
     */
    private int connetCount = 10;

    private ConnectThread mConnectThread;

    private ReadThread mReadThred;
    /**
     * 数据回调
     */
    private IReadDataCallback mIReadDataCallback;

    public LocalSocketClient(String socketName, IReadDataCallback readCallback) {
        SOCKET_NAME = socketName;
        mIReadDataCallback = readCallback;
        if (SOCKET_NAME != null && !SOCKET_NAME.equals("")) {
            client = new LocalSocket();
            address = new LocalSocketAddress(SOCKET_NAME, LocalSocketAddress.Namespace.RESERVED);
        }
    }

    /**
     * 启动socket连接
     */
    public void startSocketConnect() {
        try {
            //closeSocketConnect();
            mConnectThread = new ConnectThread();
            mConnectThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接线程
     */
    private class ConnectThread extends Thread {
        @Override
        public void run() {
            if (client != null) {
                while (!client.isConnected() && connetCount > 0) {
                    try {
                        Thread.sleep(1000);
                        LogUtils.i("Try to connect socket;connetCount:" + connetCount);
                        client.connect(address);

                        //连接成功启动 读取线程
                        mReadThred = new ReadThread();
                        mReadThred.start();

                        if (mIReadDataCallback != null) {
                            mIReadDataCallback.connected();
                        }

                    } catch (Exception e) {
                        connetCount--;
                        // LogUtils.i("Socket Connect fail");
                    }
                }

                if (connetCount < 0) {
                    if (mIReadDataCallback != null) {
                        mIReadDataCallback.disconneted();
                    }
                }
            }
        }

    }

    /**
     * 读取socket 数据
     */
    private class ReadThread extends Thread {

        @Override
        public void run() {

            try {
                while (client.isConnected()) {
                    // 这里从本地服务读取信息
                    InputStream in = client.getInputStream();
                    byte[] data = new byte[8 * 1024];
                    int length = readRilMessage(in, data);
                    //  LogUtils.d("length = " + length);
                    //  LogUtils.d("com.qzy.tt.data = " + ByteUtils.byteArrToHexString(com.qzy.tt.data));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 发送消息
     *
     * @param command 只做标识谁发送了这个命令
     * @return 返回Socket服务端的消息回执
     */
    public boolean sendCommand(String appPackage, byte[] command) {
        if (client == null || !client.isConnected()) {
            return false;
        }
        try {
            OutputStream out = client.getOutputStream();
            out.write(command);
            out.flush();
            LogUtils.d( "(" + appPackage + ") send command = " + ByteUtils.byteArrToHexString(command) );
        } catch (IOException e) {
            e.printStackTrace();
            if (mIReadDataCallback != null) {
                mIReadDataCallback.disconneted();
            }
            return false;
        }
        return true;
    }


    /**
     * 关闭Socket
     */
    public void closeSocketConnect() {
        try {
            if (client != null) {
                client.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据回调接口
     */
    public interface IReadDataCallback {
        void connected();

        void disconneted();

        void readDataCallback(byte[] data);
    }

    private int readRilMessage(InputStream is, byte[] buffer) throws IOException {

        // LogUtils.d("readRilMessage = " );
        int countRead;
        int offset;
        int remaining;
        int messageLength;
        byte[] headByte = new byte[]{(byte) 0x55, (byte) 0xaa};


        boolean isReadHead = false;
        do {
            countRead = is.read(buffer, 0, 1);

            if (countRead < 0) {
                // LogUtils.e( "Hit EOS reading message length");
                return -1;
            }
            // LogUtils.d("readRilMessage head buffer[0]  = " + ByteUtils.byteToHex(buffer[0]) );
            if (buffer[0] == headByte[0]) {
                isReadHead = true;
                continue;
            }

            if (buffer[0] == headByte[1] && isReadHead) {
                break;
            } else {
                isReadHead = false;
            }

        } while (true);

        countRead = is.read(buffer, 0, 1);

        if (countRead < 0) {
            // LogUtils.e( "Hit EOS reading message length");
            return -1;
        }
        byte commandType = buffer[0];


        // First, read in the length of the message
        offset = 0;
        remaining = 2;
        do {
            countRead = is.read(buffer, offset, remaining);

            if (countRead < 0) {
                //LogUtils.e( "Hit EOS reading message length");
                return -1;
            }

            offset += countRead;
            remaining -= countRead;
        } while (remaining > 0);

        messageLength = ((buffer[0] & 0xff) << 8) | (buffer[1] & 0xff);

        // LogUtils.e("1111 Hit EOS reading message.  messageLength=" + messageLength + " remaining=" + remaining);
        // Then, re-use the buffer and read in the message itself
        offset = 0;
        remaining = messageLength;
        do {
            countRead = is.read(buffer, offset, remaining);

            if (countRead < 0) {
                // LogUtils.e("Hit EOS reading message.  messageLength=" + messageLength + " remaining=" + remaining);
                return -1;
            }

            offset += countRead;
            remaining -= countRead;
        } while (remaining > 0);

        final byte[] data = new byte[messageLength];
        System.arraycopy(buffer, 0, data, 0, messageLength);

        if (mIReadDataCallback != null) {
            //   LogUtils.d("pcm index = " + ByteUtils.byteToInt(com.qzy.tt.data[0]));
            // LogUtils.d("pcm com.qzy.tt.data = " + ByteUtils.byteArrToHexString(com.qzy.tt.data));
            // byte[] value = new byte[com.qzy.tt.data.length -1];
            // System.arraycopy(com.qzy.tt.data,1,value,0,value.length);
            mIReadDataCallback.readDataCallback(data);
        }


        return messageLength;
    }


}
