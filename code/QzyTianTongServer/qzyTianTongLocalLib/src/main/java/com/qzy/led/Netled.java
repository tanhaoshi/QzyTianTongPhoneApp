package com.qzy.led;

/**
 * Created by Administrator on 2018-08-06.
 */

public class Netled {

    static {
        System.loadLibrary("netled");
    }

    // must init then can set the led status
    public static native void init();
    // set Net led flash/off    true->flash false->off
    public static native void setNetledFlash(boolean state);
    //set Net Led on/off  true->on false->off
    public static native void setNetledState(boolean state);
    //must destroy if not use the net led
    public static native void destroy();
}
