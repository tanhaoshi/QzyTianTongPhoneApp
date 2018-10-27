package com.qzy.tiantong.lib.service.netty;


import com.qzy.tiantong.lib.utils.LogUtils;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by yj.zhang on 2018/8/3/003.
 */

public class NettyServer {

    public static final int Port = 9999;
    // public static final String IP = "192.168.43.1";

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    //连接线程
    private Thread mThread;

    //发送数据句柄
    // public ChannelHandlerContext connectHanlerCtx;


    private IServerListener iServerListener;


    public NettyServer(IServerListener listener) {
        iServerListener = listener;
    }


    /**
     * 启动服务，主要做数据通信
     */
    public void startServer(final int port) {
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                bossGroup = new NioEventLoopGroup();
                workerGroup = new NioEventLoopGroup();
                try {

                    ServerBootstrap b = new ServerBootstrap();
                    b.group(bossGroup, workerGroup)
                            .channel(NioServerSocketChannel.class)
                            .childHandler(connectChannelInitializer).option(ChannelOption.SO_BACKLOG, 128)
                            .childOption(ChannelOption.SO_KEEPALIVE, true);

                    // Bind and start to accept incoming connections.
                    ChannelFuture f = b.bind("0.0.0.0", port).sync();

                    f.channel().closeFuture().sync();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mThread.start();

    }

    private ChannelInitializer connectChannelInitializer = new ChannelInitializer<SocketChannel>() {
        @Override
        public void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new ChannelHander());
        }
    };


    @ChannelHandler.Sharable
    public class ChannelHander extends ChannelInboundHandlerAdapter {

        private ByteBuf dataBuf;

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            LogUtils.d("channelRegistered...");

        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
            LogUtils.d("channelUnregistered...");
            Channel channel = ctx.channel();
            // connectHanlerCtx = null;
            InetSocketAddress insocket = (InetSocketAddress) channel.remoteAddress();
            String ip = insocket.getAddress().getHostAddress();
            LogUtils.d("New client has disconnected:" + ip);
            if (iServerListener != null) {
                iServerListener.onConnected(ctx, ip, false);
            }
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            LogUtils.d("channelActive...");
            Channel channel = ctx.channel();
            // connectHanlerCtx = ctx;
            InetSocketAddress insocket = (InetSocketAddress) channel.remoteAddress();
            String ip = insocket.getAddress().getHostAddress();
            LogUtils.d("New client has connected:" + ip);
            if (iServerListener != null) {
                iServerListener.onConnected(ctx, ip, true);
            }

        }


        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            LogUtils.d("channelInactive...");
            Channel channel = ctx.channel();
            // connectHanlerCtx = null;
            InetSocketAddress insocket = (InetSocketAddress) channel.remoteAddress();
            String ip = insocket.getAddress().getHostAddress();
            LogUtils.d("New client has disconnected:" + ip);
            if (iServerListener != null) {
                iServerListener.onConnected(ctx, ip, false);
            }
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            try {
                ByteBuf buf = ((ByteBuf) msg);
                LogUtils.d("receve data = " + buf.array().length);
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
            LogUtils.d("channelReadComplete...");
            if (iServerListener != null && dataBuf != null) {
                ByteBufInputStream inputStream = new ByteBufInputStream(dataBuf);
                iServerListener.onReceiveData(inputStream);
                dataBuf = null;
            }
        }
    }

    /*public ChannelHandlerContext getConnectHanlerCtx() {
        return connectHanlerCtx;
    }*/

    public void stopServer() {

        try {
            if (bossGroup != null) {
                bossGroup.shutdownGracefully();
            }
            if (workerGroup == null) {
                workerGroup.shutdownGracefully();
            }

            if (mThread != null && mThread.isAlive()) {
                mThread.interrupt();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface IServerListener {
        void onConnected(ChannelHandlerContext ctx, String ip, boolean state);

        void onReceiveData(ByteBufInputStream inputStream);
    }

    public void release() {
        stopServer();
    }


}
