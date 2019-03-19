package com.qzy.tt.phone.netty;

import android.content.Context;

import com.google.protobuf.ByteString;
import com.qzy.androidftp.FtpClienManager;
import com.qzy.data.PhoneCmd;
import com.qzy.data.PrototocalTools;
import com.qzy.eventbus.EventBusUtils;
import com.qzy.eventbus.IMessageEventBustType;
import com.qzy.eventbus.MessageEventBus;
import com.qzy.netty.NettyClientManager;
import com.qzy.tt.data.CallPhoneProtos;
import com.qzy.tt.data.TtCallRecordProtos;
import com.qzy.tt.data.TtDeleCallLogProtos;
import com.qzy.tt.data.TtOpenBeiDouProtos;
import com.qzy.tt.data.TtPhoneGetServerVersionProtos;
import com.qzy.tt.data.TtPhoneMobileDataProtos;
import com.qzy.tt.data.TtPhonePositionProtos;
import com.qzy.tt.data.TtPhoneRecoverSystemProtos;
import com.qzy.tt.data.TtPhoneSmsProtos;
import com.qzy.tt.data.TtPhoneSosMessageProtos;
import com.qzy.tt.data.TtPhoneSosStateProtos;
import com.qzy.tt.data.TtPhoneUpdateAppInfoProtos;
import com.qzy.tt.data.TtPhoneUpdateSendFileProtos;
import com.qzy.tt.data.TtPhoneWifiProtos;
import com.qzy.tt.data.TtShortMessageProtos;
import com.qzy.tt.data.TtTimeProtos;
import com.qzy.tt.phone.cmd.CmdHandler;
import com.qzy.tt.phone.common.CommonData;
import com.qzy.tt.phone.data.SmsBean;
import com.qzy.utils.LogUtils;
import com.socks.library.KLog;
import com.tt.qzy.view.bean.AppInfoModel;
import com.tt.qzy.view.bean.DatetimeModel;
import com.tt.qzy.view.bean.EnableDataModel;
import com.tt.qzy.view.bean.ProtobufMessageModel;
import com.tt.qzy.view.bean.SMAgrementModel;
import com.tt.qzy.view.bean.ServerPortIp;
import com.tt.qzy.view.bean.SosSendMessageModel;
import com.tt.qzy.view.bean.TtBeidouOpenBean;
import com.tt.qzy.view.bean.WifiSettingModel;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import io.netty.buffer.ByteBufInputStream;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;

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
        EventBusUtils.register(this);
        mNettyClientManager = new NettyClientManager(nettyListener);
        mCmdHandler = new CmdHandler(context);
        KLog.i("TtPhoneService boolean flag value = " + (Boolean) SPUtils.getShare(mContext, Constans.SERVER_FLAG,false));
        if((Boolean) SPUtils.getShare(mContext, Constans.SERVER_FLAG,false)){
            connect(Constans.PORT,Constans.IP);
            SPUtils.putShare(mContext,Constans.SERVER_FLAG,false);
        }
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
     * 请求设备通话记录
     */
    private void requestCallRecord(){
        TtCallRecordProtos.TtCallRecordProto ttCallRecordProto = TtCallRecordProtos.TtCallRecordProto.newBuilder()
                .setRequest(true)
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_call_record,ttCallRecordProto));
    }

    /**
     * 请求设备短信记录
     */
    private void requestShortMessage(){
        TtShortMessageProtos.TtShortMessage ttShortMessage = TtShortMessageProtos.TtShortMessage.newBuilder()
                .setRequest(true)
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_short_message,ttShortMessage));
    }

    /**
     * 请求设备服务APP是否需要更新
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
     * 发送当前时间至服务器
     */
    private void requestServerDatetime(Object o){
        DatetimeModel datetimeModel = (DatetimeModel)o;
        TtTimeProtos.TtTime ttTime = TtTimeProtos.TtTime.newBuilder()
                .setDateTime(datetimeModel.getDateTime())
                .setIsSync(true)
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_tt_time,ttTime));
    }

    /**
     * 发送至服务器修改短信读的状态
     */
    private void requestServerShortMessageStatus(Object o){
        SMAgrementModel smAgrementModel = (SMAgrementModel)o;
        TtShortMessageProtos.TtShortMessage.ShortMessage shortMessage = TtShortMessageProtos.TtShortMessage.ShortMessage.newBuilder()
                .setIsRead(true)
                .setId(smAgrementModel.getId())
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_phone_send_sms_read,shortMessage));
    }

    /**
     * 发送至服务器修改wifi密码
     */
    private void requestServerWifipassword(Object o){
        WifiSettingModel wifiSettingModel = (WifiSettingModel)o;
        TtPhoneWifiProtos.TtWifi ttWifi = TtPhoneWifiProtos.TtWifi.newBuilder()
                .setPasswd(wifiSettingModel.getWifiPassword())
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_phone_set_wifi_passwd,ttWifi));
    }

    /**
     * 开始链接下载
     */
    private void startUpload(){
        //开始下载
        try{
            InputStream inputStream = mContext.getAssets().open("tiantong_update.zip");
            File file = new File("/mnt/sdcard/tiantong_update.zip");
            if(!file.exists()){
                file.createNewFile();
            }else{
                file.delete();
                file.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] read = new byte[1024 * 1024];
            int len = 0;
            while ((len = inputStream.read(read)) != -1){
//                KLog.i("copy file len = " + len);
                outputStream.write(read,0,len);
            }
            outputStream.flush();
            inputStream.close();
            outputStream.close();
            // AssetFileUtils.CopyAssets(mContext,"tiantong_update.zip",file.getAbsolutePath());
            // FileInputStream in = new FileInputStream(file);

//            byte[] read = new byte[512];
//            int len = 0;
//            while ((len = inputStream.read(read)) != -1){
//                KLog.i("len = " + len);
//                sendZipFile(false,Arrays.copyOf(read,len));
//            }
//
//            sendZipFile(true,new byte[1]);
        }catch (Exception e){
            e.printStackTrace();
        }

        //发送命令告诉服务端开始上传文件
        sendZipFile(false,new byte[1]);
        final FtpClienManager mFtpClienManager = new FtpClienManager();
        mFtpClienManager.ftpConnet(new FtpClienManager.IConnectListener() {
            @Override
            public void onConnected(boolean isConnect) {
                if (isConnect) {
                    upload(mFtpClienManager);
                }
            }
        });
    }

    private void upload(FtpClienManager mFtpClienManager) {
        mFtpClienManager.upload("/mnt/sdcard/tiantong_update.zip", new FTPDataTransferListener() {
            @Override
            public void started() {
                LogUtils.e("-----------------started");
                EventBus.getDefault().post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_SERVER_PERCENT
                        ,Integer.valueOf(0)));
            }

            @Override
            public void transferred(int i) {
                //                   LogUtils.d("-----------------transferred");
                KLog.i("view download progress = " + i);
            }

            @Override
            public void completed() {
                LogUtils.e("-----------------completed");
                //发送命令告诉服务端开始上传文件完成
                sendZipFile(true,new byte[1]);
            }

            @Override
            public void aborted() {
                LogUtils.e("-----------------aborted");
            }

            @Override
            public void failed() {
                LogUtils.e("-----------------failed");
            }
        });
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

    /**
     * 请求服务端打开设备移动数据
     */
    private void requestEnableData(Object o){
        EnableDataModel dataModel = (EnableDataModel)o;
        TtPhoneMobileDataProtos.TtPhoneMobileData mobileData = TtPhoneMobileDataProtos.TtPhoneMobileData.newBuilder()
                .setIsEnableData(dataModel.isEnableData())
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_phone_server_enable_data,mobileData));
    }

    /**
     * 请求服务端版本号
     */
    private void requestServerVersion(){
        TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion ttPhoneGetServerVersion = TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion.newBuilder()
                .setIsRequest(true)
                .setIp(CommonData.getInstance().getLocalWifiIp())
                .build();
        KLog.i("ip : "+CommonData.getInstance().getLocalWifiIp());
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_server_version_info,ttPhoneGetServerVersion));
    }

    /**
     * 设备服务端sos设置保存
     */
    private void requestSosSendMessage(Object o){
        SosSendMessageModel sosSendMessageModel = (SosSendMessageModel)o;
        TtPhoneSosMessageProtos.TtPhoneSosMessage ttPhoneSosMessage = TtPhoneSosMessageProtos.TtPhoneSosMessage.newBuilder()
                .setMessageContent(sosSendMessageModel.getMessageContent())
                .setPhoneNumber(sosSendMessageModel.getPhoneNumber())
                .setDelaytime(sosSendMessageModel.getDelaytime())
                .setIp(CommonData.getInstance().getLocalWifiIp())
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_sos_message_send,ttPhoneSosMessage));
    }

    /**
     * 恢复设备出厂设置
     */
    private void requestServerRecoverSystem(){
        TtPhoneRecoverSystemProtos.TtPhoneRecoverSystem recoverSystem = TtPhoneRecoverSystemProtos.TtPhoneRecoverSystem.newBuilder()
                .setIsRecover(true)
                .setIp(CommonData.getInstance().getLocalWifiIp())
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_server_recover_system,recoverSystem));
    }

    /**
     * 获取设备移动数据状态
     */
    private void reuqestServerMobileStatus(){
        TtPhoneMobileDataProtos.TtPhoneMobileData mobileData = TtPhoneMobileDataProtos.TtPhoneMobileData.newBuilder()
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_phone_server_mobile_init,mobileData));
    }

    /**
     * 获取设备SOS初始状态
     */
    private void requestServerSosStatus(){
        TtPhoneSosStateProtos.TtPhoneSosState ttPhoneSosState = TtPhoneSosStateProtos.TtPhoneSosState.newBuilder()
                .setIsRequest(true)
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_server_sos_status,ttPhoneSosState));
    }

    /**
     * 关闭服务设备SOS
     */
    private void requestServerSosClose(){
        TtPhoneSosStateProtos.TtPhoneSosState ttPhoneSosState = TtPhoneSosStateProtos.TtPhoneSosState.newBuilder()
                .setIsRequest(true)
                .setIsSwitch(false)
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_server_sos_close,ttPhoneSosState));
    }

    /**
     * 请求服务删除该号码下通话记录
     */
    private void requestServerDeleteMessage(Object o){
        ProtobufMessageModel messageModel = (ProtobufMessageModel)o;
        if(messageModel.isDelete()){
            TtDeleCallLogProtos.TtDeleCallLog ttDeleCallLog = TtDeleCallLogProtos.TtDeleCallLog.newBuilder()
                    .setIsDeleAll(messageModel.isDelete())
                    .setIp(CommonData.getInstance().getLocalWifiIp())
                    .build();
            sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_server_del_calllog,ttDeleCallLog));
        }else{
            TtDeleCallLogProtos.TtDeleCallLog ttDeleCallLog = TtDeleCallLogProtos.TtDeleCallLog.newBuilder()
                    .setIsDeleAll(messageModel.isDelete())
                    .setIp(CommonData.getInstance().getLocalWifiIp())
                    .setPhonenumber(messageModel.getPhoneNumber())
                    .setServerDataId(messageModel.getServerId())
                    .build();
            sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_server_del_calllog,ttDeleCallLog));
        }
    }

    /**
     * 请求服务删除该短信息下的记录
     */
    private void requestServerShortMessageDelete(Object o){
        ProtobufMessageModel messageModel = (ProtobufMessageModel)o;
        if(messageModel.isDelete()){
            TtDeleCallLogProtos.TtDeleCallLog ttDeleCallLog = TtDeleCallLogProtos.TtDeleCallLog.newBuilder()
                    .setIp(CommonData.getInstance().getLocalWifiIp())
                    .setIsDeleAll(messageModel.isDelete())
                    .build();
            sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_server_del_sms,ttDeleCallLog));
        }else{
            TtDeleCallLogProtos.TtDeleCallLog ttDeleCallLog = TtDeleCallLogProtos.TtDeleCallLog.newBuilder()
                    .setIp(CommonData.getInstance().getLocalWifiIp())
                    .setIsDeleAll(messageModel.isDelete())
                    .setPhonenumber(messageModel.getPhoneNumber())
                    .build();
            sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_server_del_sms,ttDeleCallLog));
        }
    }

    /**
     * 将服务端的系统数据库修改未接状态
     * @param o
     */
    private void requestServerPhoneStatus(Object o){
        TtCallRecordProtos.TtCallRecordProto ttCallRecordProto = (TtCallRecordProtos.TtCallRecordProto)o;
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_server_call_status,ttCallRecordProto));
    }

    private NettyClientManager.INettyListener nettyListener = new NettyClientManager.INettyListener() {
        @Override
        public void onReceiveData(ByteBufInputStream inputStream) {
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
            EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_SERVER_NONCONNECT));
            if(mCmdHandler != null){
                mCmdHandler.resetPhoneState();
            }
            setConnectedState();
        }
    };

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(MessageEventBus event) {
        switch (event.getType()) {
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG:
                KLog.i("sadasdasdasdadasdasd");
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
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG__REQUEST_SERVER_TIME_DATE:
                requestServerDatetime(event.getObject());
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SERVER_SHORT_MESSAGE:
                requestServerShortMessageStatus(event.getObject());
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SERVER_WIFI_PASSWORD:
                requestServerWifipassword(event.getObject());
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SERVER_ENABLE_DATA:
                requestEnableData(event.getObject());
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SERVER_VERSION:
                requestServerVersion();
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SERVER_RECOVER_SYSTEM:
                requestServerRecoverSystem();
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SERVER_SOS_SENDMESSAGE:
                requestSosSendMessage(event.getObject());
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SERVER_MOBILE_STATUS:
                reuqestServerMobileStatus();
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SERVER_SOS_STATUS:
                requestServerSosStatus();
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SERVER_SOS_CLOSE:
                requestServerSosClose();
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SERVER_DELETE_SIGNAL_MESSAFGE:
                requestServerDeleteMessage(event.getObject());
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SERVER_DELETE_SIGNAL_SHORT_MESSAGE:
                requestServerShortMessageDelete(event.getObject());
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SERVER_RECORD_CALL_STATUS:
                requestServerPhoneStatus(event.getObject());
                break;
        }
    }

    /**
     * 发送命令到设备
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
        mCmdHandler.release();
    }


}
