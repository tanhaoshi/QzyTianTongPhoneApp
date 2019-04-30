package com.qzy.netty;

import com.qzy.utils.LogUtils;
import com.socks.library.KLog;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Created by yj.zhang on 2018/7/31/031.
 */

public class NettyClient {

    public static final int Port = 9999;
    public static final String IP = "192.168.43.1";

   // private NioEventLoopGroup groupConnected;

    private static NettyClient client;


    private Channel channel;

    //连接线程
    private Thread mThread;

    //发送数据句柄
    public ChannelHandlerContext connectHanlerCtx;

    //回调接口
    private IConnectedReadDataListener connectedReadDataListener;

    public static NettyClient getInstance(){
        return client;
    }

    public static void initNettyClient(IConnectedReadDataListener listener){
        client = new NettyClient(listener);
    }


    private NettyClient(IConnectedReadDataListener listener){

        connectedReadDataListener = listener;
    }

    /**
     * 连接服务
     */
    public void starConnect(final int port, final String ip){
        mThread =  new Thread(new Runnable() {
            @Override
            public void run() {
                NioEventLoopGroup groupConnected = new NioEventLoopGroup();
                try {
                    // Client服务启动器 3.x的ClientBootstrap
                    // 改为Bootstrap，且构造函数变化很大，这里用无参构造。

                    Bootstrap bootstrap = new Bootstrap();
                    // 指定channel类型
                    bootstrap.channel(NioSocketChannel.class);
                    // 指定Handler
                    bootstrap.handler(connectedChannelInitializer);
                    bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
                    // 指定EventLoopGroup
                    bootstrap.group(groupConnected);
                    // 连接到目标IP的8000端口的服务端
                    ChannelFuture cf = bootstrap.connect(new InetSocketAddress(ip, port));
                    cf.addListener(new ChannelFutureListener(){

                        @Override
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            if (!channelFuture.isSuccess()) {
                                final EventLoop loop = channelFuture.channel().eventLoop();
                                loop.schedule(new Runnable() {
                                    @Override
                                    public void run() {
                                        LogUtils.d("服务端链接不上，开始重连操作...");
                                        //start();
                                    }
                                }, 1L, TimeUnit.SECONDS);
                            } else {
                                channel = channelFuture.channel();
                                LogUtils.d("服务端链接成功...");
                            }

                        }
                    });
                    channel = cf.sync().channel();
                    channel.closeFuture().sync();



                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if(groupConnected != null){
                        groupConnected.shutdownGracefully();
                    }
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
           /* ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
            ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
            ch.pipeline().addLast(new ProtobufEncoder());*/
            //处理空闲状态事件的处理器
            //ch.pipeline().addLast(new IdleStateHandler(5,7,10, TimeUnit.SECONDS));
            ch.pipeline().addLast(connectedChannelHandler);
            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,4,4,-8,0));
        }
    };
    private int loss_connect_time = 0;
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
            KLog.i(" no connect client netty ");
            connectHanlerCtx = null;
            if(connectedReadDataListener != null){
                connectedReadDataListener.onConnectedState(false);
            }
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            LogUtils.e("channelRead ");
            connectHanlerCtx = ctx;
            try {
                ByteBuf buf = ((ByteBuf) msg);
               // LogUtils.e("receve data = " + buf.array().length);
                if (dataBuf == null) {
                    dataBuf = buf;
                } else {
                    dataBuf.writeBytes(buf.array());
                }

                ctx.flush();

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

            ctx.flush();
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            LogUtils.d("userEventTriggered ");
            connectHanlerCtx = ctx;
            IdleStateEvent event =(IdleStateEvent)evt;

            String eventType = null;

            switch (event.state()){
                case READER_IDLE:
                    eventType = "读空闲";
                    break;
                case WRITER_IDLE:
                    eventType = "写空闲";
                    break;
                case ALL_IDLE:
                    eventType ="读写空闲";
                    break;
            }

           LogUtils.d(ctx.channel().remoteAddress() + "超时事件：" +eventType);

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
         //   ctx.close();
        }

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            LogUtils.d("handlerAdded ");
            connectHanlerCtx = ctx;
            if(connectedReadDataListener != null){
                connectedReadDataListener.onException(ctx);
            }
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
           /* if(groupConnected != null){
                groupConnected.shutdownGracefully();
            }*/

            if(channel != null){
                channel.close().sync();
            }

            if(mThread != null && mThread.isAlive()){
                    mThread.interrupt();
            }
           // groupConnected = null;
            mThread = null;
//            if(connectedReadDataListener != null){
//                connectedReadDataListener.onConnectedState(false);
//            }
            client = null;
        }catch (Exception e){
//            if(connectedReadDataListener != null){
//                connectedReadDataListener.onConnectedState(false);
//            }
            KLog.i("error value ="+e.getMessage().toString());
            e.printStackTrace();
        }
    }


    public interface IConnectedReadDataListener{
        void onReceiveData(ByteBufInputStream data);
        void onConnectedState(boolean state);
        void onException(ChannelHandlerContext ctx);
    }

}
