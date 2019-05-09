package com.qzy.tiantong.service.atcommand;

public class AtCommandTools {

    //打开北斗定位
    public static final String at_command_open_gps = "AT^BEIDOU=1";
    //关闭北斗定位
    public static final String at_command_close_gps = "AT^BEIDOU=0";

    //挂断电话
    public static final String at_command_hungup = "AT+CHUP";

    //获取天通模块版本号
    public static final String AT_COMMAND_VERSION = "AT+CGMR";

}
