package com.qzy.tt.phone.netty.fileupload;

import android.content.Context;
import android.content.res.AssetManager;

import com.tt.qzy.view.application.TtPhoneApplication;
import com.tt.qzy.view.bean.FileUploadModel;

import java.io.File;
import java.io.InputStream;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class FileUploadClient {

    public static final String IP = "192.168.43.1";
    public static final int PORT = 9998;

    private NioEventLoopGroup groupConnected;

    private Thread mThread;

    public void startUpload(){
       try {
           AssetManager am = TtPhoneApplication.getInstance().getAssets();
           InputStream inputStream = am.open("update.zip");
           FileUploadModel uploadFile = new FileUploadModel();
           File file = new File("");
           String fileMd5 = file.getName();// 文件名
           uploadFile.setFile(file);
           uploadFile.setFileName(fileMd5);
           uploadFile.setStarPos(0);// 文件开始位置
           connectUpload(PORT, IP, uploadFile);
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

   private void connectUpload(final int port, final String host, final FileUploadModel fileUploadFile)throws Exception{
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                groupConnected = new NioEventLoopGroup();
                try {
                    Bootstrap b = new Bootstrap();
                    b.group(groupConnected).channel(NioSocketChannel.class).
                            option(ChannelOption.TCP_NODELAY, true).
                            handler(new ChannelInitializer<Channel>() {

                                @Override
                                protected void initChannel(Channel ch) throws Exception {
                                    ch.pipeline().addLast(new ObjectEncoder());
                                    ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(null)));
                                    ch.pipeline().addLast(new FileUploadClientHandler(fileUploadFile));
                                }
                            });

                    ChannelFuture f = b.connect(host, port).sync();
                    f.channel().closeFuture().sync();
                }catch (Exception e){

                    e.printStackTrace();

                }finally {
                    groupConnected.shutdownGracefully();
                }
            }
        });
        mThread.start();
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
}
