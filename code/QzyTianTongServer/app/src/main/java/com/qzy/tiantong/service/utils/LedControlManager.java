package com.qzy.tiantong.service.utils;

public class LedControlManager {

    private static final String BATTERY_LOW_LED_SWITCH = "/sys/class/leds/battery_low_led/brightness";
    private static final String BATTERY_LOW_RED_TRIGGER = "/sys/class/leds/battery_low_led/trigger";
    private static final String BATTERY_FULL_LED_SWITCH = "/sys/class/leds/charing_blue_led/brightness";
    private static final String BATTERY_FULL_LED_TRIGGER = "/sys/class/leds/charing_blue_led/trigger";
    private static final String NET_LED_SWITCH = "/sys/class/leds/net_blue/brightness";
    private static final String NET_LED_TRIGGER = "/sys/class/leds/net_blue/trigger";
    private static final String POWER_LED_SWITCH = "/sys/class/leds/power_green/brightness";
    private static final String POWER_LED_TRIGGER = "/sys/class/leds/power_green/trigger";

    //brightness 写0 或 1 代表关和开 trigger 写 timer 和 none 代表 闪和不闪


}
