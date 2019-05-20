package com.qzy.tiantong.service.utils;

public interface Constant {

   String WAKE_PATH = "/sys/bus/platform/drivers/tt-platdata/bp_mode";

   String BATTERY_PATH = "/sys/class/power_supply/BATTERY/dsoc";

   String LAMP_PATH = "/sys/bus/platform/drivers/rk818-battery/charing";

   String WIFI_SSID = "HWA_6100G_";

   String WIFI_PASSWD = "12345678";

   String CONNECT_COUNT = "CONNECT_FLAG";

   String TIANTONG_MODULE_VERSION = "MODULE_VERSION";

}
