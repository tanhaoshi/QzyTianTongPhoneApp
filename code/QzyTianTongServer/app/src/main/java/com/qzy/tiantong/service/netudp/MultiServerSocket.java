package com.qzy.tiantong.service.netudp;

import com.qzy.tiantong.service.utils.ThreadUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;

public class MultiServerSocket {

    private static final int MULTI_PORT = 8600;
    //228.5.6.7
    private static final String MULTI_HOST = "228.5.6.7";

    public static final String WAKE_UP_FLAG = "wake_up_flag";

    private static MultiServerSocket instance;

    MulticastSocket multiServerSocket;

    InetAddress address;

    private Thread mThread;

    public static MultiServerSocket getInstance(){
        if(instance == null){
            instance = new MultiServerSocket();
        }
        return instance;
    }

    private MultiServerSocket(){

    }

    public void init(){
        try {

            multiServerSocket = new MulticastSocket(MULTI_PORT);

            address = InetAddress.getByName(MULTI_HOST);

            multiServerSocket.joinGroup(address);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendData(){
        ExecutorService executorService = ThreadUtils.getCachedPool();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                if(multiServerSocket != null){
                    byte[] buf = WAKE_UP_FLAG.getBytes();
                    DatagramPacket packet = null;
                    try {
                        //192.168.43.255
                        packet = new DatagramPacket(buf,buf.length, InetAddress.getByName(MULTI_HOST),MULTI_PORT);
                        multiServerSocket.send(packet);
                        multiServerSocket.leaveGroup(address);
                        multiServerSocket.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void recycle(){
        try {
            multiServerSocket.leaveGroup(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
        multiServerSocket.close();
        if(mThread.isAlive()){
            mThread.interrupt();
        }
        mThread = null;
        instance = null;
    }
}
