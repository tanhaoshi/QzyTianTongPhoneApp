package com.qzy.tiantong.service.utils;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ComUtil {

    public static final String BROADCAST_IP = "224.2.2.2";
    public static final int BOADCAST_PORT = 8600;
    private static final int DATA_LEN = 100 * 1024;
    //定义本程序的MulticastSocket实例
    private MulticastSocket socket = null;
    //定义广播的IP地址
    private InetAddress broadcastAddress = null;
    //定义接收网络数据的字符数组
    byte[] inBuff = new byte[DATA_LEN];
    //以指定字节数组创建准备接受的DatagramPacket对象
    private DatagramPacket inPacket = new DatagramPacket(inBuff , inBuff.length);
    //定义一个用于发送的DatagramPacket对象
    private DatagramPacket outPacket = null;
    private Handler handler;

    //构造器，初始化资源
    public ComUtil(Handler handler) throws Exception
    {
        this.handler = handler;
        //因为该MultcastSocket对象需要接受数据，所以有指定端口
        socket = new MulticastSocket(BOADCAST_PORT);
        broadcastAddress = InetAddress.getByName(BROADCAST_IP);
        //将该socket加入指定的多点广播地址
        socket.joinGroup(broadcastAddress);
        //设置本MultcastSocket发送的数据报将被送到本身
        socket.setLoopbackMode(false);
        //初始化发送用的DatagramSocket，它包含一个长度为0的字节数组
        outPacket = new DatagramPacket(new byte[0] , 0 , broadcastAddress , BOADCAST_PORT);
        new ReadBroad().start();
    }

    //广播消息的工具方法
    public void broadCast(byte[] msg)
    {
        try
        {
            //将msg字符串转换为字节数组
            byte[] buff = msg;
            //设置发送用的DatagramPacket里的字节数组
            outPacket.setData(buff);
            //发送数据
            socket.send(outPacket);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //持续读取MulticastSocket的线程
    class ReadBroad extends Thread
    {
        public void run()
        {
            while (true)
            {
                try
                {
                    //读取Socket中的数据
                    socket.receive(inPacket);
                    Message msg = new Message();
                    msg.what = 0x123;
                    msg.obj = inBuff;
                    handler.sendMessage(msg);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

}
