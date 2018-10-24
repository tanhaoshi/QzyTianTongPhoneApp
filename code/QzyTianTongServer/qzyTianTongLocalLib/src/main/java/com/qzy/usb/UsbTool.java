package com.qzy.usb;


public class UsbTool {
    static {

        System.loadLibrary("ttswitch");
    }


    public native boolean switchMode(boolean mode);

}
