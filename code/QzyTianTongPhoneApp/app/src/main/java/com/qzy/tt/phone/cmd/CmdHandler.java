package com.qzy.tt.phone.cmd;

import android.content.Context;
import android.content.Intent;

import com.google.protobuf.GeneratedMessageV3;
import com.qzy.data.PhoneCmd;
import com.qzy.data.PrototocalTools;
import com.qzy.eventbus.EventBusUtils;
import com.qzy.eventbus.IMessageEventBustType;
import com.qzy.eventbus.MessageEventBus;
import com.qzy.ring.RingManager;
import com.qzy.tt.data.CallPhoneBackProtos;
import com.qzy.tt.data.CallPhoneStateProtos;
import com.qzy.tt.data.TimerSendProtos;
import com.qzy.tt.data.TtBeiDouStatuss;
import com.qzy.tt.data.TtCallRecordProtos;
import com.qzy.tt.data.TtOpenBeiDouProtos;
import com.qzy.tt.data.TtPhoneBatteryProtos;
import com.qzy.tt.data.TtPhoneGetServerVersionProtos;
import com.qzy.tt.data.TtPhoneMobileDataProtos;
import com.qzy.tt.data.TtPhonePositionProtos;
import com.qzy.tt.data.TtPhoneSignalProtos;
import com.qzy.tt.data.TtPhoneSimCards;
import com.qzy.tt.data.TtPhoneSmsProtos;
import com.qzy.tt.data.TtPhoneSosStateProtos;
import com.qzy.tt.data.TtPhoneUpdateResponseProtos;
import com.qzy.tt.data.TtPhoneUpdateSendFileProtos;
import com.qzy.tt.data.TtShortMessageProtos;
import com.qzy.tt.data.TtTimeProtos;
import com.qzy.utils.LogUtils;
import com.socks.library.KLog;
import com.tt.qzy.view.activity.TellPhoneActivity;
import com.tt.qzy.view.activity.TellPhoneIncomingActivity;
import com.tt.qzy.view.application.TtPhoneApplication;
import com.tt.qzy.view.presenter.manager.SyncManager;
import com.tt.qzy.view.utils.AppUtils;
import com.tt.qzy.view.utils.RingToneUtils;


import io.netty.buffer.ByteBufInputStream;

/**
 * Created by yj.zhang on 2018/8/3/003.
 */

public class CmdHandler {

    private Context context;
    private SyncManager mSyncManager;

    public CmdHandler(Context context) {
        this.context = context;
        mSyncManager = new SyncManager(context);
    }

    /**
     * 处理数据
     *
     * @param inputStream
     */
    public void handlerCmd(ByteBufInputStream inputStream) {
        try {
            synchronized (CmdHandler.class){
                if (inputStream.available() > 0 && PrototocalTools.readToFour0x5aHeaderByte(inputStream)) {
                    int protoId = inputStream.readInt();
                    int len = inputStream.readInt();//包长度
                    LogUtils.e(" protoId = " + protoId + " len = " + len);
                    if (protoId > 100 && protoId % 2 == 1) {
                        handProcessCmd(protoId, inputStream);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分协议id处理消息
     *
     * @param protoId
     * @param inputStream
     */
    private  CallPhoneStateProtos.CallPhoneState.PhoneState currentPhoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.NOCALL;
    private void handProcessCmd(int protoId, ByteBufInputStream inputStream) {
        try {
            switch (protoId) {
                case PrototocalTools.IProtoClientIndex.call_phone_state:
                    CallPhoneStateProtos.CallPhoneState callPhoneState = CallPhoneStateProtos.CallPhoneState.parseDelimitedFrom(inputStream);
                    pasreCallPhoneState(protoId,callPhoneState);
                    break;
                case PrototocalTools.IProtoClientIndex.tt_phone_signal:
                    TtPhoneSignalProtos.PhoneSignalStrength phoneSignalStrength = TtPhoneSignalProtos.PhoneSignalStrength.parseDelimitedFrom(inputStream);
                    sendCmdToView(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_SIGNAL,protoId, phoneSignalStrength);
                    break;
                case PrototocalTools.IProtoClientIndex.phone_send_sms_callback:
                    TtPhoneSmsProtos.TtPhoneSms ttPhoneSms = TtPhoneSmsProtos.TtPhoneSms.parseDelimitedFrom(inputStream);
                    sendCmdToView(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_SEND_SMS_STATE,protoId, ttPhoneSms);
                    break;
                case PrototocalTools.IProtoClientIndex.tt_phone_battery:
                    TtPhoneBatteryProtos.TtPhoneBattery ttPhoneBattery = TtPhoneBatteryProtos.TtPhoneBattery.parseDelimitedFrom(inputStream);
                    pasreCallPhoneBattery(protoId,ttPhoneBattery);
                    break;
                case PrototocalTools.IProtoClientIndex.tt_phone_simcard:
                    TtPhoneSimCards.TtPhoneSimCard ttPhoneSimCard = TtPhoneSimCards.TtPhoneSimCard.parseDelimitedFrom(inputStream);
                     parseSimCard(protoId,ttPhoneSimCard);
                    break;
                case PrototocalTools.IProtoClientIndex.tt_phone_beidoustatus_usb:
                    TtBeiDouStatuss.TtBeiDouStatus ttBeiDouStatus = TtBeiDouStatuss.TtBeiDouStatus.parseDelimitedFrom(inputStream);
                    sendCmdToView(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_BEIDOU,protoId,ttBeiDouStatus);
                    break;
                case PrototocalTools.IProtoClientIndex.tt_gps_position:
                    TtPhonePositionProtos.TtPhonePosition ttPhonePosition = TtPhonePositionProtos.TtPhonePosition.parseDelimitedFrom(inputStream);
                    //sendCmdToView(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_ACCURACY_POSITION,protoId,ttPhonePosition);
                    parseGpsPosition(protoId,ttPhonePosition);
                    break;
                case PrototocalTools.IProtoClientIndex.tt_beidou_switch:
                    TtOpenBeiDouProtos.TtOpenBeiDou ttOpenBeiDou = TtOpenBeiDouProtos.TtOpenBeiDou.parseDelimitedFrom(inputStream);
                    sendCmdToView(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_BEIDOU_SWITCH,protoId,ttOpenBeiDou);
                    break;
                case PrototocalTools.IProtoClientIndex.tt_call_record:
                    TtCallRecordProtos.TtCallRecordProto ttCallRecordProto = TtCallRecordProtos.TtCallRecordProto.parseDelimitedFrom(inputStream);
                    mSyncManager.syncCallRecord(ttCallRecordProto);
                    break;
                case PrototocalTools.IProtoClientIndex.tt_short_message:
                    TtShortMessageProtos.TtShortMessage ttShortMessage = TtShortMessageProtos.TtShortMessage.parseDelimitedFrom(inputStream);
                    mSyncManager.syncShortMessage(ttShortMessage);
                    break;
                case PrototocalTools.IProtoClientIndex.tt_receiver_short_message:
                    TtShortMessageProtos.TtShortMessage.ShortMessage ttShortMessageSignal = TtShortMessageProtos.TtShortMessage.ShortMessage.parseDelimitedFrom(inputStream);
                    startSystemRingTone();
                    mSyncManager.syncShortMessageSignal(protoId,ttShortMessageSignal,ttShortMessageSignal);
                    break;
                case PrototocalTools.IProtoClientIndex.tt_call_phone_back:
                    CallPhoneBackProtos.CallPhoneBack callPhoneBack = CallPhoneBackProtos.CallPhoneBack.parseDelimitedFrom(inputStream);
                    sendCmdToView(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_CALL_STATE,protoId,callPhoneBack);
                    break;
                case PrototocalTools.IProtoClientIndex.response_update_phone_aapinfo:
                    TtPhoneUpdateResponseProtos.UpdateResponse updateResponse = TtPhoneUpdateResponseProtos.UpdateResponse.parseDelimitedFrom(inputStream);
                    sendCmdToView(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG__RESPONSE_SERVER_APP_VERSION,protoId,updateResponse);
                    break;
                case PrototocalTools.IProtoClientIndex.response_update_send_zip:
                    TtPhoneUpdateResponseProtos.UpdateResponse updateResponse1 = TtPhoneUpdateResponseProtos.UpdateResponse.parseDelimitedFrom(inputStream);
                    sendCmdToView(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG__RESPONSE_SERVER_UPLOAD_FINSH,protoId,updateResponse1);
                    break;
                 case PrototocalTools.IProtoClientIndex.response_tt_time:
                     TtTimeProtos.TtTime ttTime = TtTimeProtos.TtTime.parseDelimitedFrom(inputStream);
                    break;
                 case PrototocalTools.IProtoClientIndex.response_phone_data_status:
                     TtPhoneMobileDataProtos.TtPhoneMobileData mobileData = TtPhoneMobileDataProtos.TtPhoneMobileData.parseDelimitedFrom(inputStream);
                     sendCmdToView(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_SERVER_ENABLE_DATA,protoId,mobileData);
                     break;
                 case PrototocalTools.IProtoClientIndex.response_update_send_failed:
                     TtPhoneUpdateResponseProtos.UpdateResponse updateResponse2 = TtPhoneUpdateResponseProtos.UpdateResponse.parseDelimitedFrom(inputStream);
                     sendCmdToView(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_SERVER_UPGRADLE,protoId,updateResponse2);
                     break;
                 case PrototocalTools.IProtoClientIndex.response_server_version_info:
                     TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion ttPhoneGetServerVersion = TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion
                             .parseDelimitedFrom(inputStream);
                     sendCmdToView(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_SERVER_VERSION,protoId,ttPhoneGetServerVersion);
                     break;
                 case PrototocalTools.IProtoClientIndex.response_server_mobile_data_init:
                     TtPhoneMobileDataProtos.TtPhoneMobileData ttPhoneMobileData = TtPhoneMobileDataProtos.TtPhoneMobileData.parseDelimitedFrom(inputStream);
                     sendCmdToView(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_SERVER_MOBILE_STATUS,protoId,ttPhoneMobileData);
                     break;
                 case PrototocalTools.IProtoClientIndex.response_server_sos_init_status:
                     TtPhoneSosStateProtos.TtPhoneSosState ttPhoneSosState = TtPhoneSosStateProtos.TtPhoneSosState.parseDelimitedFrom(inputStream);
                     sendCmdToView(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_SERVER_SOS_STATUS,protoId,ttPhoneSosState);
                     break;
                case PrototocalTools.IProtoClientIndex.response_server_timer_message:
                     TimerSendProtos.TimerSend timerSend = TimerSendProtos.TimerSend.parseDelimitedFrom(inputStream);
                     handlerAllTimeSend(timerSend);
                     break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析所有定时发送状态
     */
    private void handlerAllTimeSend(TimerSendProtos.TimerSend send){
        //电话状态
      pasreCallPhoneState(PrototocalTools.IProtoClientIndex.call_phone_state,send.getCallPhoneState());

      //电量
      pasreCallPhoneBattery(PrototocalTools.IProtoClientIndex.tt_phone_battery,send.getBatterValue());
      //simcard
      parseSimCard(PrototocalTools.IProtoClientIndex.tt_phone_simcard,send.getTtPhoneSimcard());

      //信号强度
      parseSiganStregth(PrototocalTools.IProtoClientIndex.tt_phone_signal,send.getSigalStrength());

      //gps 位置
      parseGpsPosition(PrototocalTools.IProtoClientIndex.tt_gps_position,send.getTtPhoneGpsPosition());
    }


    /**
     * 解析电话状态并发送到ui
     * @param protoId
     * @param callPhoneState
     */
    private void pasreCallPhoneState(int protoId,CallPhoneStateProtos.CallPhoneState callPhoneState){
        CallPhoneStateProtos.CallPhoneState.PhoneState state = callPhoneState.getPhoneState();
        KLog.i("currentPhoneState = " + currentPhoneState.ordinal() + "    state = " + state.ordinal());
        if(currentPhoneState != state){ // 与上次的状态不同才改变
            currentPhoneState = state;
            if( state == CallPhoneStateProtos.CallPhoneState.PhoneState.INCOMING){
                incommingState(callPhoneState.getPhoneNumber());
            }else {
                sendCmdToView(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_STATE,protoId, callPhoneState);
            }
        }
    }


    /**
     * 解析电话状态并发送到ui
     * @param protoId
     * @param ttPhoneBattery
     */
    private void pasreCallPhoneBattery(int protoId,TtPhoneBatteryProtos.TtPhoneBattery ttPhoneBattery){
        sendCmdToView(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_SEND_BATTERY,protoId, ttPhoneBattery);
    }

    /**
     * 解析发送sim
     * @param protoId
     * @param ttPhoneSimCard
     */
    private void parseSimCard(int protoId, TtPhoneSimCards.TtPhoneSimCard ttPhoneSimCard){
        sendCmdToView(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_SIM_CARD,protoId,ttPhoneSimCard);
    }

    /**
     * 解析并发送信号强度
     * @param protoId
     * @param phoneSignalStrength
     */
    private void parseSiganStregth(int protoId,TtPhoneSignalProtos.PhoneSignalStrength phoneSignalStrength){
        sendCmdToView(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_SIGNAL,protoId,phoneSignalStrength);
    }

    /**
     * 解析gps position
     * @param protoId
     * @param ttPhonePosition
     */
    private void parseGpsPosition(int protoId,TtPhonePositionProtos.TtPhonePosition ttPhonePosition){
        sendCmdToView(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_ACCURACY_POSITION,protoId,ttPhonePosition);

    }


    private void sendCmdToView(String messageType,int protoId, GeneratedMessageV3 messageV3) {
        EventBusUtils.post(new MessageEventBus(messageType,PhoneCmd.getPhoneCmd(protoId,messageV3)));
    }

    private void incommingState(String number){
       /* Intent intent = new Intent("com.qzy.tt.incoming");
        intent.putExtra("phone_number",number);
        context.sendBroadcast(intent);*/
        AppUtils.wakeUpAndUnlock(context);
        Intent intent = new Intent(context, TellPhoneIncomingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("diapadNumber", number);
        context.startActivity(intent);
    }

    /**
     * 重新设置电话状态
     */
    public void resetPhoneState(){
        currentPhoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.NOCALL;
    }

    /**
     *收到短信播放系统铃声
     */
    private void startSystemRingTone(){
        RingToneUtils ringToneUtils = new RingToneUtils(context);
        RingToneUtils.playRing(TtPhoneApplication.getInstance());
    }

    public void release(){
        mSyncManager.release();
    }

}
