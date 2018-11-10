package com.qzy.tiantong.service.update;

import android.content.Context;

import com.google.protobuf.ByteString;
import com.qzy.ftpserver.FtpServerManager;
import com.qzy.tiantong.lib.utils.IniFileUtils;
import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.lib.utils.MD5Utils;
import com.qzy.tiantong.lib.utils.ZipUtils;
import com.qzy.tiantong.service.netty.NettyServerManager;
import com.qzy.tiantong.service.netty.cmd.CmdHandler;
import com.qzy.tiantong.service.netty.cmd.CmdHandlerUpdate;
import com.qzy.tiantong.service.netty.cmd.TianTongHandler;
import com.qzy.tiantong.service.netty.cmd.TianTongHandlerUpdate;
import com.qzy.tt.data.TtPhoneUpdateAppInfoProtos;
import com.qzy.tt.data.TtPhoneUpdateResponseProtos;
import com.qzy.tt.data.TtPhoneUpdateSendFileProtos;
import com.qzy.tt.probuf.lib.data.PhoneCmd;
import com.qzy.tt.probuf.lib.data.PrototocalTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import io.netty.buffer.ByteBufInputStream;

public class UpdateServiceManager implements IUpdateManager {

    private Context mContext;

    //通讯管理
    private NettyServerManager mNettyServerManager;

    private CmdHandlerUpdate mCmdHandlerUpdate;
    private TianTongHandlerUpdate mTianTongHandler;

    private IniFile mIniFile;

    //创建ftp上传文件
    private FtpServerManager mFtpServerManager;


    private IUpdateLocalTool mLocalUpdateSocketManager;

    public UpdateServiceManager(Context context) {
        mContext = context;
        mIniFile = new IniFile();

        initNettyManager();

        mLocalUpdateSocketManager = new LocalUpdateSocketManager();

        if (mFtpServerManager == null) {
            mFtpServerManager = new FtpServerManager();
            mFtpServerManager.onStartServer();
        }

    }


    /**
     * 初始化netty
     */
    private void initNettyManager() {
        mTianTongHandler = new TianTongHandlerUpdate(this);
        mCmdHandlerUpdate = new CmdHandlerUpdate(mTianTongHandler);
        mNettyServerManager = new NettyServerManager(new NettyServerManager.INettyServerListener() {
            @Override
            public void onReceiveData(ByteBufInputStream inputStream) {
                mCmdHandlerUpdate.handlerCmd(inputStream);
            }

            @Override
            public void onConnected(String ip) {

            }

            @Override
            public void onDisconnected(String ip) {

            }
        });
        mNettyServerManager.startNettyServer(9998);
    }

    /**
     * 释放netty
     */
    private void freeNettyManager() {
        if (mNettyServerManager == null) {
            LogUtils.e("mNettyServerManager is null ");
            return;
        }
        mNettyServerManager.release();
    }


    @Override
    public void checkUpdate(TtPhoneUpdateAppInfoProtos.UpdateAppInfo updateAppInfo) {
        try {
            String appVer = updateAppInfo.getPhoneAppVersion();
            String serverVer = updateAppInfo.getServerAppVersion();
            int phoneAppVersion = Integer.parseInt(appVer.replace(".", ""));
            int serverAppVersion = Integer.parseInt(serverVer.replace(".", ""));
            String zipMd5 = updateAppInfo.getTiantongUpdateMd();
            LogUtils.d(" phoneAppVersion = " + phoneAppVersion + " serverAppVersion = " + serverAppVersion + " zipMd5 = " + zipMd5);
            UpdateConfigBean updateConfigBean = mIniFile.getUpdateConfigBean(true);
            int nowServerVersion = Integer.parseInt(updateConfigBean.getServer_version().replace(".", ""));
            if (serverAppVersion > nowServerVersion) {
                //更新配置信息
                updateConfigBean.setApp_version(appVer);
                updateConfigBean.setServer_version(serverVer);
                updateConfigBean.setZip_md(zipMd5);
                mIniFile.setUpdateConfigBeanNew(updateConfigBean);

                sendNeedUpdate(updateAppInfo.getIp(), true);
            } else {
                sendNeedUpdate(updateAppInfo.getIp(), false);
            }

            //处理动作


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void receiverZipFile(TtPhoneUpdateSendFileProtos.UpdateSendFile updateSendFile) {

        try {

            String fileName = updateSendFile.getFileData().getFilename();
            File file = new File("/mnt/sdcard/tiantong_update/tiantong_update/" + fileName);

            boolean isFinish = updateSendFile.getIsSendFileFinish();
            LogUtils.d("isFinish = " + isFinish + " fileName = " + fileName);
            if (isFinish) {
                String md = mIniFile.getUpdateConfigBeanNew(true).getZip_md();
                String fileMd = MD5Utils.getFileMD5(file);
                LogUtils.d("md5 = " + md + " fileMd5 = " + fileMd);
                if (fileMd.equals(md)) {
                    sendReceiveZipFileFinish(updateSendFile.getIp(), true);
                    //开始与底层进行通讯
                    ///
                    ///
                    ///
                    if(!unzipUpdteZip(file.getAbsolutePath())){
                        return;
                    }
                    if(!copyShFile()){
                        return;
                    }
                    mLocalUpdateSocketManager.startLocalUpdte();

                } else {
                    sendReceiveZipFileFinish(updateSendFile.getIp(), false);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            sendReceiveZipFileFinish(updateSendFile.getIp(), false);
        }

    }


    /**
     * 解压文件
     *
     * @param fileName
     */
    private boolean unzipUpdteZip(String fileName) {
        try {
            ZipUtils.UnZipFolder(fileName,"/mnt/sdcard/update");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 复制脚本文件到操作区域
     * @return
     */
    private boolean copyShFile(){
        try {
             File shFiles = new File("/mnt/sdcard/update/sh");
             if(shFiles.exists()){
                for(File file:shFiles.listFiles()){
                    byte[] data = new byte[100 * 1024];
                    int len = 0;
                    FileInputStream inputStream = new FileInputStream(file);
                    File file1 = new File("/mnt/sdcard/update/" + file.getName());
                    FileOutputStream outputStream = new FileOutputStream(file1);
                    while ((len = inputStream.read(data)) != -1){
                        outputStream.write(Arrays.copyOf(data,len));
                        outputStream.flush();
                    }
                    outputStream.close();
                    inputStream.close();
                    outputStream = null;
                    inputStream = null;
                }
             }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }


    /**
     * 发送是否需要升级
     *
     * @param ip
     * @param flag
     */
    private void sendNeedUpdate(String ip, boolean flag) {
        if (mNettyServerManager != null) {
            TtPhoneUpdateResponseProtos.UpdateResponse updateResponse = TtPhoneUpdateResponseProtos.UpdateResponse.newBuilder()
                    .setIp(ip)
                    .setIsUpdate(flag)
                    .build();
            mNettyServerManager.sendData(ip, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.response_update_phone_aapinfo, updateResponse));
        }

    }

    /**
     * 发送接受文件完成的消息给手机
     */
    private void sendReceiveZipFileFinish(String ip, boolean flag) {
        if (mNettyServerManager != null) {
            TtPhoneUpdateResponseProtos.UpdateResponse updateResponse = TtPhoneUpdateResponseProtos.UpdateResponse.newBuilder()
                    .setIp(ip)
                    .setIsSendFileFinish(flag)
                    .build();
            mNettyServerManager.sendData(ip, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.response_update_send_zip, updateResponse));
        }

    }


    /**
     * 释放
     */
    public void free() {

        if (mLocalUpdateSocketManager != null) {
            mLocalUpdateSocketManager.free();
        }

        freeNettyManager();

        if (mFtpServerManager != null) {
            mFtpServerManager.stopFtpServer();
        }
    }


}
