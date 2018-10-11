package com.qzy.eventbus;

/**
 * Created by yj.zhang on 2018/9/17.
 */

public interface IMessageEventBustType {

    String EVENT_BUS_TYPE_CONNECT_TIANTONG = "event_bus_type_connect_tiantong";
    String EVENT_BUS_TYPE_DISCONNECT_TIANTONG = "event_bus_type_disconnect_tiantong";
    String EVENT_BUS_TYPE_CONNECT_TIANTONG_SELECTED = "event_bus_type_connect_tiantong_selected";
    String EVENT_BUS_TYPE_CONNECT_TIANTONG_SUCCESS = "event_bus_type_connect_tiantong_success";
    String EVENT_BUS_TYPE_CONNECT_TIANTONG_FAILED = "event_bus_type_connect_tiantong_failed";
    String EVENT_BUS_TYPE_CONNECT_TIANTONG_SIGNAL= "event_bus_type_connect_tiantong_signal";
    String EVENT_BUS_TYPE_CONNECT_TIANTONG_DIAL = "event_bus_type_connect_tiantong_dial";
    String EVENT_BUS_TYPE_CONNECT_TIANTONG_HUNGUP = "event_bus_type_connect_tiantong_hungup";
    String EVENT_BUS_TYPE_CONNECT_TIANTONG_ACCEPTCALL = "event_bus_type_connect_tiantong_acceptcall";
    String EVENT_BUS_TYPE_CONNECT_TIANTONG_STATE = "event_bus_type_connect_tiantong_state";
    String EVENT_BUS_TYPE_CONNECT_TIANTONG_SEND_SMS = "event_bus_type_connect_tiantong_send_sms";
    String EVENT_BUS_TYPE_CONNECT_TIANTONG_SEND_SMS_STATE = "event_bus_type_connect_tiantong_send_sms_state";
    String EVENT_BUS_TYPE_CONNECT_TIANTONG_SEND_BATTERY = "event_bus_type_connect_tiantong_send_battery";
    // 天通sim卡
    String EVENT_BUS_TYPE_CONNECT_TIANTONG_SIM_CARD = "event_bus_type_connect_tiantong_sim_card";
    // 天通北斗是否连接上
    String EVENT_BUS_TYPE_CONNECT_TIANTONG_BEIDOU = "event_bus_type_connect_tiantong_beidou";
    // 请求gps精确位置
    String EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_ACCURACY_POSITION = "event_bus_type_connect_request_accuracy_position";
    // 返回gps精确位置
    String EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_ACCURACY_POSITION = "event_bus_type_connect_response_accuracy_position";
    // 打开天通北斗卫星连接开关
    String EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_BEIDOU_SWITCH = "event_bus_type_connect_tiantong_beidou_open";
    // 关闭天通北斗卫星连接开关
    String EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_BEIDOU_SWITCH = "event_bus_type_connect_tiantong_beidou_close";
    // 请求天通猫通话记录
    String EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_CALL_RECORD = "event_bus_type_connect_tiantong_request_call_record";
    // 返回天通猫通话记录
    String EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_CALL_RECORD = "event_bus_type_connect_tiantong_response_call_record";
}
