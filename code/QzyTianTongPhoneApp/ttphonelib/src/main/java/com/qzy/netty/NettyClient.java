package com.qzy.netty;

import com.qzy.utils.LogUtils;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * Created by yj.zhang on 2018/7/31/031.
 */

public class NettyClient {

    public static final int Port = 9999;
    public static final String IP = "192.168.43.1";

    private NioEventLoopGroup groupConnected;

    //连接线程
    private Thread mThread;

    //发送数据句柄
    public ChannelHandlerContext connectHanlerCtx;

    //回调接口
    private IConnectedReadDataListener connectedReadDataListener;

    public NettyClient(IConnectedReadDataListener listener){
        connectedReadDataListener = listener;
    }

    /**
     * 连接服务
     */
    public void starConnect(){
        mThread =  new Thread(new Runnable() {
            @Override
            public void run() {
                groupConnected = new NioEventLoopGroup();
                try {
                    // Client服务启动器 3.x的ClientBootstrap
                    // 改为Bootstrap，且构造函数变化很大，这里用无参构造。

                    Bootstrap bootstrap = new Bootstrap();
                    // 指定channel类型
                    bootstrap.channel(NioSocketChannel.class);
                    // 指定Handler
                    bootstrap.handler(connectedChannelInitializer);
                    // 指定EventLoopGroup
                    bootstrap.group(groupConnected);
                    // 连接到目标IP的8000端口的服务端
                    Channel channel = bootstrap.connect(new InetSocketAddress(IP, Port)).sync().channel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mThread.start();

    }


    /**
     * 连接工具
     */
    private ChannelInitializer connectedChannelInitializer = new ChannelInitializer<SocketChannel>() {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            LogUtils.d("initChannel ch=" + ch.localAddress());

            ch.pipeline().addLast(connectedChannelHandler);
            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,4,4,-8,0));
        }
    };

    private ChannelInboundHandler connectedChannelHandler = new ChannelInboundHandler() {
        private ByteBuf dataBuf;

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            LogUtils.e("channelRegistered" );
            connectHanlerCtx = ctx;
            Channel channel = ctx.channel();

        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
            LogUtils.e("channelUnregistered" );
            connectHanlerCtx = ctx;
            Channel channel = ctx.channel();

        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            LogUtils.e("channelActive" );
            connectHanlerCtx = ctx;
            if(connectedReadDataListener != null){
                connectedReadDataListener.onConnectedState(true);
            }
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            LogUtils.e("channelInactive" );
            connectHanlerCtx = null;
            if(connectedReadDataListener != null){
                connectedReadDataListener.onConnectedState(false);
            }
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            connectHanlerCtx = ctx;
            try {
                ByteBuf buf = ((ByteBuf) msg);
               // LogUtils.e("receve data = " + buf.array().length);
                if (dataBuf == null) {
                    dataBuf = buf;
                } else {
                    dataBuf.writeBytes(buf.array());
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            LogUtils.e("channelReadComplete ");
            connectHanlerCtx = ctx;

            if (connectedReadDataListener != null && dataBuf != null) {
                ByteBufInputStream inputStream = new ByteBufInputStream(dataBuf);
                connectedReadDataListener.onReceiveData(inputStream);
                dataBuf = null;
            }
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object o) throws Exception {
            LogUtils.d("userEventTriggered ");
            connectHanlerCtx = ctx;
        }

        @Override
        public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
            LogUtils.d("channelWritabilityChanged ");
            connectHanlerCtx = ctx;
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable throwable) throws Exception {
            LogUtils.e("exceptionCaught ",throwable);
            connectHanlerCtx = ctx;
        }

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            LogUtils.d("handlerAdded ");
            connectHanlerCtx = ctx;
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
            LogUtils.d("handlerRemoved ");
            connectHanlerCtx = ctx;
        }
    };

    public ChannelHandlerContext getConnectHanlerCtx() {
        return connectHanlerCtx;
    }

    /**
     * 断开连接
     */
    public void stopConnected(){
        try {
            if(groupConnected != null){
                groupConnected.shutdownGracefully();
            }
            if(mThread != null && mThread.isAlive()){
                mThread.interrupt();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public interface IConnectedReadDataListener{
        void onReceiveData(ByteBufInputStream data);
        void onConnectedState(boolean state);
    }

}
