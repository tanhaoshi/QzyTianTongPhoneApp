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
    // 请求天通猫短信记录
    String EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SHORT_MESSGAE = "event_bus_type_connect_tiantong_request_short_message";
    // 返回天通猫短信记录
    String EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_SHORT_MESSAGE = "event_bus_type_connect_tiantong_response_short_message";
    // 返回天通猫通话状态
    String EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_CALL_STATE = "event_bus_type_connect_tiantong_response_call_state";
    // 不被占用情况下打电话
    String EVENT_BUS_TYPE_CONNECT_TIANTONG__CALL_PHONE = "event_bus_type_connect_tiantong_call_state";
    // 检查天通猫服务端APP版本是否更新
    String EVENT_BUS_TYPE_CONNECT_TIANTONG__REQUEST_SERVER_APP_VERSION = "event_bus_type_connect_tiantong_request_server_app_version";
    // 天通猫服务端APP返回是否更新
    String EVENT_BUS_TYPE_CONNECT_TIANTONG__RESPONSE_SERVER_APP_VERSION = "event_bus_type_connect_tiantong_response_server_app_version";
    // 开始上传文件
    String EVENT_BUS_TYPE_CONNECT_TIANTONG__REQUEST_SERVER_UPLOAD_APP = "event_bus_type_connect_tiantong_request_server_upload_app";
    // 完成下载
    String EVENT_BUS_TYPE_CONNECT_TIANTONG__RESPONSE_SERVER_UPLOAD_FINSH = "event_bus_type_connect_tiantong_response_server_upload_finsh";
    // 发送当前时间至服务器
    String EVENT_BUS_TYPE_CONNECT_TIANTONG__REQUEST_SERVER_TIME_DATE = "event_bus_type_connect_tiantong_request_server_time_date";
    // 获取服务时间返回响应
    String EVENT_BUS_TYPE_CONNECT_TIANTONG__RESPONSE_SERVER_TIME_DATE = "event_bus_type_connect_tiantong_response_server_time_date";
    // 发送短信已读状态
    String EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SERVER_SHORT_MESSAGE = "event_bus_type_connect_tiantong_request_server_short_message";
    // 设置wifi密码
    String EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SERVER_WIFI_PASSWORD = "event_bus_type_connect_tiantong_request_server_wifi_password";
    // 打开天通猫移动数据
    String EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SERVER_ENABLE_DATA = "event_bus_type_connect_tiantong_request_server_enable_data";
    // 打开天通猫移动数据返回情况
    String EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_SERVER_ENABLE_DATA = "event_bus_type_connect_tiantong_response_server_enable_data";
    // 服务端升级apk进度条展示
    String EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_SERVER_PERCENT = "event_bus_type_connect_tiantong_response_server_percent";
    // 服务端APK升级出现断链接
    String EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_SERVER_NONCONNECT = "event_bus_type_connect_tiantong_response_server_nonconnect";
}
