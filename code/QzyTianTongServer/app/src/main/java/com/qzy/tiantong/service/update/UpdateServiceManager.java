package com.qzy.tiantong.service.update;

import android.content.Context;

import com.google.protobuf.ByteString;
import com.qzy.tiantong.lib.utils.IniFileUtils;
import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.lib.utils.MD5Utils;
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
import java.io.FileOutputStream;
import java.io.FileWriter;

import io.netty.buffer.ByteBufInputStream;

public class UpdateServiceManager implements IUpdateManager {

    private Context mContext;

    //通讯管理
    private NettyServerManager mNettyServerManager;

    private CmdHandlerUpdate mCmdHandlerUpdate;
    private TianTongHandlerUpdate mTianTongHandler;

    private IniFile mIniFile;

    public UpdateServiceManager(Context context) {
        mContext = context;
         mIniFile = new IniFile();

        initNettyManager();

    }




    /**
     * 初始化netty
     */
    private void initNettyManager() {
        mTianTongHandler = new TianTongHandlerUpdate();
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
            int phoneAppVersion = Integer.parseInt(updateAppInfo.getPhoneAppVersion().replace(".",""));
            int serverAppVersion = Integer.parseInt(updateAppInfo.getServerAppVersion().replace(".",""));
            String zipMd5 = updateAppInfo.getTiantongUpdateMd();
            LogUtils.d(" phoneAppVersion = " + phoneAppVersion + " serverAppVersion = " + serverAppVersion + " zipMd5 = " + zipMd5);
            int nowServerVersion = Integer.parseInt(mIniFile.getUpdateConfigBean(true).getServer_version().replace(".",""));
            if(serverAppVersion > nowServerVersion){
                sendNeedUpdate(updateAppInfo.getIp(),true);
            }else{
                sendNeedUpdate(updateAppInfo.getIp(),false);
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
            File file = new File("/mnt/sdcard/" + fileName);
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            }

            FileOutputStream out = new FileOutputStream(file);
            out.write(updateSendFile.getFileData().getData().toByteArray());
            out.flush();
            out.close();
            if (updateSendFile.getIsSendFileFinish()) {
                if (MD5Utils.getFileMD5(file).equals(mIniFile.getUpdateConfigBean(true).getZip_md())) {
                    sendReceiveZipFileFinish(updateSendFile.getIp(), true);
                    //开始与底层进行通讯
                    ///
                    ///
                    ///
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
     * 发送是否需要升级
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
        freeNettyManager();
    }


}
