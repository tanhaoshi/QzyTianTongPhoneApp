package com.qzy.tiantong.service.netudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MultiServerSocket {

    private static final int MULTI_PORT = 8600;

    private static final String MULTI_HOST = "224.0.0.1";

    private static final String WAKE_UP_FLAG = "wake_up_flag";

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
        if(multiServerSocket != null){
            byte[] buf = WAKE_UP_FLAG.getBytes();
            DatagramPacket packet = new DatagramPacket(buf,buf.length,address,MULTI_PORT);
            try {
                multiServerSocket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
