package com.qzy.tt.phone.netudp;



import com.socks.library.KLog;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;


/**
 *  <pre>
 *       无序广播存在意义:一.主要避免Netty无法连接问题.
 *                      1.针对休眠问题,实际情况下,当系统休眠前是否有必要发出一条休眠信息？告诉客户端服务目前处于休眠状态,客户端可以不用回复。
 *                      2.假设如果不发的话客户端是无法知道客户端是处于休眠状态的。
 *                      1.我应该在休眠之前将 我现在连接的客户端 做一次持久化,将现在连接的客户端保存到文件当中。
 *
 *  </pre>
 */
public class MultiSocket {

    private static MultiSocket instance;

    MulticastSocket socket;

    InetAddress address;

    CallBack mCallBack;

    private Thread mThread ;

    private MultiSocket(){
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    receiveBroadcast();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        mThread.start();
    }

    public static MultiSocket init(){
        if(instance == null){
            instance = new MultiSocket();
        }
        return instance;
    }

    private void receiveBroadcast() throws IOException {

        KLog.v( "sendMultiBroadcast...");

        socket = new MulticastSocket(8600);

        address = InetAddress.getByName("224.0.0.1");

        socket.joinGroup(address);

        DatagramPacket packet;

        byte[] rev = new byte[512];

        packet = new DatagramPacket(rev, rev.length);

        socket.receive(packet);

        KLog.v( "get data = " + new String(packet.getData()).trim());

        if(mCallBack != null) mCallBack.receiveData(new String(packet.getData()).trim());

    }

    public void recycle(){
        try {
            socket.leaveGroup(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
        socket.close();
        if(mThread.isAlive()){
            mThread.interrupt();
        }
        mThread = null;
        instance = null;
    }

    public void setReceiveListener(CallBack callBack){
        this.mCallBack = callBack;
    }

    public interface CallBack{
        void receiveData(String data);
    }
}
