package com.qzy.tt.phone.netty;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;

import com.google.protobuf.ByteString;
import com.qzy.data.PhoneCmd;
import com.qzy.data.PrototocalTools;
import com.qzy.eventbus.EventBusUtils;
import com.qzy.eventbus.IMessageEventBustType;
import com.qzy.eventbus.MessageEventBus;
import com.qzy.netty.NettyClientManager;
import com.qzy.tt.data.CallPhoneBackProtos;
import com.qzy.tt.data.CallPhoneProtos;
import com.qzy.tt.data.TtCallRecordProtos;
import com.qzy.tt.data.TtOpenBeiDouProtos;
import com.qzy.tt.data.TtPhonePositionProtos;
import com.qzy.tt.data.TtPhoneSmsProtos;
import com.qzy.tt.data.TtPhoneUpdateAppInfoProtos;
import com.qzy.tt.data.TtPhoneUpdateSendFileProtos;
import com.qzy.tt.data.TtShortMessageProtos;
import com.qzy.tt.phone.cmd.CmdHandler;
import com.qzy.tt.phone.common.CommonData;
import com.qzy.tt.phone.data.SmsBean;
import com.qzy.tt.phone.netty.fileupload.FileUploadClient;
import com.qzy.utils.IPUtil;
import com.socks.library.KLog;
import com.tt.qzy.view.bean.AppInfoModel;
import com.tt.qzy.view.bean.ServerPortIp;
import com.tt.qzy.view.bean.TtBeidouOpenBean;
import com.tt.qzy.view.utils.AssetFileUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import io.netty.buffer.ByteBufInputStream;

/**
 * Created by yj.zhang on 2018/9/17.
 */

public class PhoneNettyManager {
    private Context mContext;

    private NettyClientManager mNettyClientManager;

    private CmdHandler mCmdHandler;

    private String ip;
    private int port;

    public PhoneNettyManager(Context context) {
        mContext = context;
        CommonData.getInstance().setLocalWifiIp(IPUtil.getLocalIPAddress(context));
        EventBusUtils.register(this);
        mNettyClientManager = new NettyClientManager(nettyListener);
        mCmdHandler = new CmdHandler(context);
    }

    /**
     * 开始连接
     */
    public void connect(int port,String ip) {
        this.ip = ip;
        this.port = port;
        mNettyClientManager.startConnect(port,ip);
    }

    /**
     * 断开连接
     */
    public void stop() {
        mNettyClientManager.release();
    }

    /**
     * 拨打电话
     *
     * @param phoneNumber
     */
    private void dialPhone(String phoneNumber) {
        CallPhoneProtos.CallPhone callPhone = CallPhoneProtos.CallPhone.newBuilder()
                .setIp(CommonData.getInstance().getLocalWifiIp())
                .setPhoneNumber(phoneNumber)
                .setPhonecommand(CallPhoneProtos.CallPhone.PhoneCommand.CALL)
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.call_phone, callPhone));
    }

    /**
     * 挂断电话
     */
    private void endCall() {
        CallPhoneProtos.CallPhone callPhone = CallPhoneProtos.CallPhone.newBuilder()
                .setIp(CommonData.getInstance().getLocalWifiIp())
                .setPhonecommand(CallPhoneProtos.CallPhone.PhoneCommand.HUANGUP)
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.call_phone, callPhone));
    }

    /**
     * 接听电话接口
     */
    private void acceptCall() {
        CallPhoneProtos.CallPhone callPhone = CallPhoneProtos.CallPhone.newBuilder()
                .setIp(CommonData.getInstance().getLocalWifiIp())
                .setPhonecommand(CallPhoneProtos.CallPhone.PhoneCommand.ACCEPTCALL)
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.call_phone, callPhone));
    }

    /**
     * 发送连接状态
     */
    private void setConnectedState() {
        boolean isconnected = mNettyClientManager.isConnected();
        //设置全局状态
        CommonData.getInstance().setConnected(isconnected);
        if (isconnected) {
            EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_SUCCESS));
        } else {
            EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_FAILED));
        }
    }

    /**
     * 发送短信
     * @param object
     */
    private void sendSms(Object object){
        SmsBean smsBean = (SmsBean) object;
        TtPhoneSmsProtos.TtPhoneSms ttPhoneSms = TtPhoneSmsProtos.TtPhoneSms.newBuilder()
                .setIp(CommonData.getInstance().getLocalWifiIp())
                .setIsSend(true)
                .setPhoneNumber(smsBean.getPhoneNumber())
                .setMessageText(smsBean.getMsg())
                .setIsReceiverSuccess(false)
                .setIsSendSuccess(false)
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.phone_send_sms, ttPhoneSms));
    }

    /**
     * 请求gps准确位置
     */
    private void requestGpsPosition(Object o){
        TtBeidouOpenBean ttBeidouOpenBean = (TtBeidouOpenBean)o;
        TtPhonePositionProtos.TtPhonePosition ttPhonePosition = TtPhonePositionProtos.TtPhonePosition.newBuilder()
                .setIsOpen(ttBeidouOpenBean.isSwitch())
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_gps_position,ttPhonePosition));
    }

    /**
     * 打开usb升级
     */
    private void openBeidou(Object o){
        TtBeidouOpenBean ttBeidouOpenBean = (TtBeidouOpenBean)o;
        TtOpenBeiDouProtos.TtOpenBeiDou ttOpenBeiDou = TtOpenBeiDouProtos.TtOpenBeiDou.newBuilder()
                .setIsOpen(ttBeidouOpenBean.isSwitch())
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_open_beidou_usb,ttOpenBeiDou));
    }

    /**
     * 请求天通猫通话记录
     */
    private void requestCallRecord(){
        TtCallRecordProtos.TtCallRecordProto ttCallRecordProto = TtCallRecordProtos.TtCallRecordProto.newBuilder()
                .setRequest(true)
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_call_record,ttCallRecordProto));
    }

    /**
     * 请求天通猫短信记录
     */
    private void requestShortMessage(){
        TtShortMessageProtos.TtShortMessage ttShortMessage = TtShortMessageProtos.TtShortMessage.newBuilder()
                .setRequest(true)
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_short_message,ttShortMessage));
    }

    /**
     * 请求天通猫服务APP是否需要更新
     */
    private void requestServerVersion(Object o){
        AppInfoModel appInfoModel = (AppInfoModel)o;
        TtPhoneUpdateAppInfoProtos.UpdateAppInfo updateAppInfo = TtPhoneUpdateAppInfoProtos.UpdateAppInfo.newBuilder()
                .setPhoneAppVersion(appInfoModel.getPhoneAppVersion())
                .setServerAppVersion(appInfoModel.getServerAppVersion())
                .setIp(appInfoModel.getIp())
                .setTiantongUpdateMd(appInfoModel.getTiantongUpdateMd())
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_update_phone_aapinfo,updateAppInfo));
    }

    /**
     * 开始链接下载
     */
    private void startUpload(){
        //开始下载
        try{
            InputStream inputStream = mContext.getAssets().open("tiantong_update.zip");
            /*File file = new File("/mnt/sdcard/tiantong_udate.zip");
            if(!file.exists()){
                file.createNewFile();
            }*/
            /*FileOutputStream outputStream = new FileOutputStream(file);
            byte[] read = new byte[1024 * 1024];
            int len = 0;
            while ((len = inputStream.read(read)) != -1){
                KLog.i("1313123 len = " + len);
                outputStream.write(read,0,len);

            }
            outputStream.flush();
            inputStream.close();
            outputStream.close();*/
           // AssetFileUtils.CopyAssets(mContext,"tiantong_update.zip",file.getAbsolutePath());
           // FileInputStream in = new FileInputStream(file);

            byte[] read = new byte[512];
            int len = 0;
            while ((len = inputStream.read(read)) != -1){
                KLog.i("len = " + len);
                sendZipFile(false,Arrays.copyOf(read,len));
            }

            sendZipFile(true,new byte[1]);

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private void sendZipFile(boolean isFinish,byte[] data){
        TtPhoneUpdateSendFileProtos.UpdateSendFile.PFile pFile = TtPhoneUpdateSendFileProtos.UpdateSendFile.PFile.newBuilder()
                .setFilename("tiantong_update.zip")
                .setData(ByteString.copyFrom(data))
                .build();
        TtPhoneUpdateSendFileProtos.UpdateSendFile updateSendFile = TtPhoneUpdateSendFileProtos.UpdateSendFile.newBuilder()
                .setIp(CommonData.getInstance().getLocalWifiIp())
                .setIsSendFileFinish(isFinish)
                .setFileData(pFile)
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_update_send_zip,updateSendFile));
    }

    private NettyClientManager.INettyListener nettyListener = new NettyClientManager.INettyListener() {
        @Override
        public void onReceiveData(ByteBufInputStream inputStream) {
            KLog.i("netty onReceiveData ...");
            if (mCmdHandler != null) {
                mCmdHandler.handlerCmd(inputStream);
            }
        }

        @Override
        public void onConnected() {
            KLog.i("netty connected ...");
            setConnectedState();
        }

        @Override
        public void onDisconnected() {
            KLog.i("netty disconnected ...");
            if(mCmdHandler != null){
                mCmdHandler.resetPhoneState();
            }
            setConnectedState();
        }
    };

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(MessageEventBus event) {
//        KLog.i("event type = " + event.getType());
        switch (event.getType()) {
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG:
                ServerPortIp serverPortIp = (ServerPortIp) event.getObject();
                connect(serverPortIp.getPort(),serverPortIp.getIp());
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_DISCONNECT_TIANTONG:
                stop();
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_SELECTED:
                setConnectedState();
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_DIAL:
                dialPhone((String) event.getObject());
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_HUNGUP:
                endCall();
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_ACCEPTCALL:
                acceptCall();
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_SEND_SMS:
                sendSms(event.getObject());
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_ACCURACY_POSITION:
                requestGpsPosition(event.getObject());
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_BEIDOU_SWITCH:
                openBeidou(event.getObject());
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_CALL_RECORD:
                requestCallRecord();
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SHORT_MESSGAE:
                requestShortMessage();
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG__REQUEST_SERVER_APP_VERSION:
                requestServerVersion(event.getObject());
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG__REQUEST_SERVER_UPLOAD_APP:
                startUpload();
                break;
        }
    }

    /**
     * 发送命令到天通猫
     *
     * @param cmd
     */
    private void sendPhoneCmd(PhoneCmd cmd) {
        if (mNettyClientManager != null) {
            mNettyClientManager.sendData(cmd);
        }
    }

    /**
     * 释放
     */
    public void free() {
        EventBusUtils.unregister(this);
        if (mNettyClientManager != null) {
            mNettyClientManager.release();
        }
        CommonData.getInstance().free();
    }


}
