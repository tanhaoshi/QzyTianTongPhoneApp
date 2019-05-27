package com.qzy.netty;

import com.qzy.utils.LogUtils;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoop;
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

    private NioEventLoopGroup groupConnected = null;
    private Bootstrap bootstrap;

    private static NettyClient client;


    private Channel channel;

    //连接线程
    private Thread mThread;
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
    private boolean isUseThread = true;
    //发送数据句柄
    public ChannelHandlerContext connectHanlerCtx;

    //回调接口
    private IConnectedReadDataListener connectedReadDataListener;

    public static NettyClient getInstance(){
        return client;
    }

    public static void initNettyClient(IConnectedReadDataListener listener){
        if(client == null) {
            client = new NettyClient(listener);
        }
    }


    private NettyClient(IConnectedReadDataListener listener){
        connectedReadDataListener = listener;
        initNettyClient();
    }

    /**
     * 连接服务
     */
    public void starConnect(final int port, final String ip){
          if(isUseThread){
              startByThread(port,ip);
          }else{
              fixedThreadPool.execute(new Runnable() {
                  @Override
                  public void run() {
                      startNettyclient(port,ip);
                  }
              });
          }
    }


    private void startByThread(final int port, final String ip){
       try {
           if(mThread != null) {
               mThread.interrupt();
           }
           mThread = new Thread(new Runnable() {
               @Override
               public void run() {
                   startNettyclient(port, ip);
               }
           });
           mThread.start();
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    private void initNettyClient(){
        groupConnected = new NioEventLoopGroup();
        // Client服务启动器 3.x的ClientBootstrap
        // 改为Bootstrap，且构造函数变化很大，这里用无参构造。

        //Bootstrap bootstrap = new Bootstrap();
         bootstrap = new Bootstrap();
        // 指定channel类型
        bootstrap.channel(NioSocketChannel.class);
        // 指定Handler
        bootstrap.handler(connectedChannelInitializer);
        bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
        // 指定EventLoopGroup
        bootstrap.group(groupConnected);
    }


    private void startNettyclient(final int port, final String ip){

        try {

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
            /*if(groupConnected != null){
                groupConnected.shutdownGracefully();
            }*/
        }finally {
           /* if(groupConnected != null){
                groupConnected.shutdownGracefully();
            }*/
        }
    }


    /**
     * 连接工具
     */
    private ChannelInitializer connectedChannelInitializer = new ChannelInitializer<SocketChannel>() {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new NettyChannelHandle());
            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,4,4,-8,0));
        }
    };

    @ChannelHandler.Sharable
    public class NettyChannelHandle extends ChannelInboundHandlerAdapter{

        private ByteBuf dataBuf;

        @Override
        public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
            connectHanlerCtx = channelHandlerContext;
        }

        @Override
        public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {
            connectHanlerCtx = null;
        }

        @Override
        public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
            LogUtils.e("channelActive" );
            connectHanlerCtx = channelHandlerContext;
            if(connectedReadDataListener != null){
                connectedReadDataListener.onConnectedState(true);
            }
        }

        @Override
        public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
            LogUtils.e("channelInactive" );
            LogUtils.i(" no connect client netty ");
            connectHanlerCtx = null;
            if(connectedReadDataListener != null){
                connectedReadDataListener.onConnectedState(false);
            }
        }

        @Override
        public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
            LogUtils.e("channelRead ");
            try {
                ByteBuf buf = ((ByteBuf) o);
                // LogUtils.e("receve data = " + buf.array().length);
                if (dataBuf == null) {
                    dataBuf = buf;
                } else {
                    dataBuf.writeBytes(buf.array());
                }
                connectHanlerCtx = channelHandlerContext;

                connectHanlerCtx.flush();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
            LogUtils.e("channelReadComplete ");

            connectHanlerCtx = channelHandlerContext;

            if (connectedReadDataListener != null && dataBuf != null) {
                ByteBufInputStream inputStream = new ByteBufInputStream(dataBuf);
                connectedReadDataListener.onReceiveData(inputStream);
                dataBuf = null;
            }

            connectHanlerCtx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            super.exceptionCaught(ctx, cause);
            if(connectedReadDataListener != null){
                connectedReadDataListener.onException(ctx);
            }
        }

        @Override
        public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {

        }

        @Override
        public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {

        }

        @Override
        public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {

        }
    }

    public ChannelHandlerContext getConnectHanlerCtx() {
        return connectHanlerCtx;
    }

    /**
     * 断开连接
     */
    public void stopConnected(){
        try {
            if(channel != null){
                try {
                    channel.close().sync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }



            if(isUseThread) {
               /* if (mThread != null && mThread.isAlive()) {
                    mThread.interrupt();
                }
                mThread = null;*/

            }else{
                if(fixedThreadPool != null){
                    //fixedThreadPool.shutdownNow();
                }
            }
            //client = null;
        }catch (Exception e){
            LogUtils.i("error value ="+e.getMessage().toString());
            e.printStackTrace();
        }
    }

    public void free(){
        try{
           /* if(channel != null){
                try {
                    channel.close().sync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
*/
            if(groupConnected != null){
                groupConnected.shutdownGracefully();
            }
            client = null;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface IConnectedReadDataListener{
        void onReceiveData(ByteBufInputStream data);
        void onConnectedState(boolean state);
        void onException(ChannelHandlerContext ctx);
    }

}
