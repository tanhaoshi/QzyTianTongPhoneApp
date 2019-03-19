package com.qzy.tiantong.service.utils;
import com.qzy.tiantong.lib.utils.LogUtils;

public class LedManager {
    public static final int FLAG_BATTERY_LOW_RED_LED_SWITCH = 0x00000001;   //charing led switch flag;
    public static final int FLAG_BATTERY_FULL_BLUE_LED_SWITCH = 0x00000002;   //charing led switch flag;
    public static final int FLAG_NET_GREEN_LED_SWITCH = 0x00000004;   //charing led switch flag;
    public static final int FLAG_POWER_BLUE_LED_SWITCH = 0x00000008;   //charing led switch flag;
    public static final int FLAG_BATTERY_LOW_LED_TIMER = 0x00000010;   //charing led switch flag;
    public static final int FLAG_BATTERY_FULL_BLUE_LED_TIMER = 0x00000020;   //charing led switch flag;
    public static final int FLAG_NET_GREEN_LED_TIMER = 0x00000040;   //charing led switch flag;
    public static final int FLAG_POWER_BLUE_LED_TIMER = 0x00000080;   //charing led switch flag;

    private static final String battery_low_led_switch = "/sys/class/leds/battery_low_led/brightness";
    private static final String battery_low_led_trigger = "/sys/class/leds/battery_low_led/trigger";
    private static final String battery_full_led_switch = "/sys/class/leds/charing_blue_led/brightness";
    private static final String battery_full_led_trigger = "/sys/class/leds/charing_blue_led/trigger";
    private static final String net_led_switch = "/sys/class/leds/net_blue/brightness";
    private static final String net_led_trigger = "/sys/class/leds/net_blue/trigger";
    private static final String power_led_switch = "/sys/class/leds/power_green/brightness";
    private static final String power_led_trigger = "/sys/class/leds/power_green/trigger";
    private static int GlobalLedStatus = 0;
    private static int SavedGlobalLedStatus = 0;
    private static int preGlobalLedStatus = 0;
    private static boolean mSosStatus = false;

    public static String get32BitBinString(int number) {
        StringBuilder sBuilder = new StringBuilder();
        for (int i = 0; i < 32; i++){
            sBuilder.append(number & 1);
            number = number >> 1;
        }
        return sBuilder.reverse().toString();
    }

    private static void setGlobalFlag(int flag) {
        //LogUtils.i("setGlobalFlag " + get32BitBinString(flag));
        GlobalLedStatus |= flag;
    }
    private static void cleanGlobalFlag(int flag){
        //LogUtils.i("cleanGlobalFlag " + get32BitBinString(flag));
        GlobalLedStatus &= ~flag;
    }

    public static void setandCleanLedFlag(int setFlag, int cleanFlag){
        if(!mSosStatus) {
            setGlobalFlag(setFlag);
            cleanGlobalFlag(cleanFlag);
            ControlLed();
        }
    }
    public static void setSosLedStatus(boolean status){
        mSosStatus = status;
        if(status){
            SavedGlobalLedStatus = GlobalLedStatus;
            setGlobalFlag(FLAG_BATTERY_FULL_BLUE_LED_TIMER | FLAG_BATTERY_FULL_BLUE_LED_SWITCH);
            cleanGlobalFlag(FLAG_BATTERY_LOW_RED_LED_SWITCH);
        }else{
            GlobalLedStatus = SavedGlobalLedStatus;
        }
        ControlLed();
    }

    public static void setRecoveryLedStatus(boolean status){
        mSosStatus = status;
        if(status){
            SavedGlobalLedStatus = GlobalLedStatus;
            setGlobalFlag(FLAG_BATTERY_FULL_BLUE_LED_SWITCH | FLAG_POWER_BLUE_LED_SWITCH);
            cleanGlobalFlag(FLAG_NET_GREEN_LED_SWITCH | FLAG_BATTERY_LOW_RED_LED_SWITCH
                        | FLAG_POWER_BLUE_LED_TIMER | FLAG_BATTERY_FULL_BLUE_LED_TIMER);
        }else{
            GlobalLedStatus = SavedGlobalLedStatus;
        }
        ControlLed();
    }

    private static void ControlLed() {
        if(GlobalLedStatus == preGlobalLedStatus){
            return;
        }else{
            preGlobalLedStatus = GlobalLedStatus;
        }
        LogUtils.i("preGlobalLedStatus = " + get32BitBinString(preGlobalLedStatus));

        if((FLAG_BATTERY_FULL_BLUE_LED_SWITCH & preGlobalLedStatus) > 0){
            //write "none" to battery full led trigger file
            if((FLAG_BATTERY_FULL_BLUE_LED_TIMER & preGlobalLedStatus) > 0){
                ModuleDormancyUtil.writeNode(battery_full_led_trigger, "timer");
            }else{ //write "timer" to battery full led trigger file
                ModuleDormancyUtil.writeNode(battery_full_led_trigger, "none");
            }
            ModuleDormancyUtil.writeNode(battery_full_led_switch, "1");
            //write "1" to battery full led brightness file
        }else{
            //write "0" to battery full led brightness file
            ModuleDormancyUtil.writeNode(battery_full_led_switch, "0");
        }
        if((FLAG_BATTERY_LOW_RED_LED_SWITCH & preGlobalLedStatus) > 0){
            //write "none" to battery full led trigger file
            if((FLAG_BATTERY_LOW_LED_TIMER & preGlobalLedStatus) > 0){
                ModuleDormancyUtil.writeNode(battery_low_led_trigger, "timer");
            }else{ //write "timer" to battery full led trigger file
                ModuleDormancyUtil.writeNode(battery_low_led_trigger, "none");
            }
            //write "1" to battery full led brightness file
            ModuleDormancyUtil.writeNode(battery_low_led_switch, "1");
        }else{
            //write "0" to battery full led brightness file
            ModuleDormancyUtil.writeNode(battery_low_led_switch, "0");
        }
        //brightness 写0 或 1 代表关和开 trigger 写 timer 和 none 代表 闪和不闪
        if((FLAG_NET_GREEN_LED_SWITCH & preGlobalLedStatus) > 0){
            //write "none" to battery full led trigger file
            if((FLAG_NET_GREEN_LED_TIMER & preGlobalLedStatus) > 0){
                ModuleDormancyUtil.writeNode(net_led_trigger, "timer");
            }else{ //write "timer" to battery full led trigger file
                ModuleDormancyUtil.writeNode(net_led_trigger, "none");
            }
            //write "1" to battery full led brightness file
            ModuleDormancyUtil.writeNode(net_led_switch, "1");
        }else{
            //write "0" to battery full led brightness file
            ModuleDormancyUtil.writeNode(net_led_switch, "0");
        }
        if((FLAG_POWER_BLUE_LED_SWITCH & preGlobalLedStatus) > 0){
            //write "none" to battery full led trigger file
            if((FLAG_POWER_BLUE_LED_TIMER & preGlobalLedStatus) > 0){
                ModuleDormancyUtil.writeNode(power_led_trigger, "timer");
            }else{ //write "timer" to battery full led trigger file
                ModuleDormancyUtil.writeNode(power_led_trigger, "none");
            }
            //write "1" to battery full led brightness file
            ModuleDormancyUtil.writeNode(power_led_switch, "1");
        }else{
            //write "0" to battery full led brightness file
            ModuleDormancyUtil.writeNode(power_led_switch, "0");
        }
    }
}
