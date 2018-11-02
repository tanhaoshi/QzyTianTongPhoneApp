package com.qzy.tt.probuf.lib.data;

import java.io.IOException;

import io.netty.buffer.ByteBufInputStream;

/**
 * Created by yj.zhang on 2018/8/3/003.
 */

public class PrototocalTools {

    public  static byte[] HEAD = {(byte) 0x5A,(byte) 0x5A,(byte) 0x5A,(byte) 0x5A};

    public interface IProtoServerIndex{
        int call_phone = 102;
        int chang_pcmplayer_db = 104;
        int phone_audio = 106;
        int phone_send_sms = 108;
        int request_gps_position = 110;
        int request_open_beidou_usb = 112;
        int request_call_record = 114;
        int request_short_message = 116;
        int request_update_phone_aapinfo = 118;
        int request_update_send_zip = 120;
        int request_tt_time = 122;
        int request_phone_send_sms_read = 124;
    }

    public interface IProtoClientIndex{
        int call_phone_state = 101;
        int tt_phone_signal = 103;
        int tt_phone_audio = 105;
        int phone_send_sms_callback = 107;
        int tt_phone_battery = 109;
        int tt_phone_simcard = 111;
        int tt_phone_beidoustatus_usb = 113;
        int tt_gps_position = 115;
        int tt_beidou_switch = 117;
        int tt_call_record = 119;
        int tt_short_message = 121;
        int tt_receiver_short_message=123;
        int tt_call_phone_back = 125;
        int response_update_phone_aapinfo = 127;
        int response_update_send_zip = 129;
        int response_tt_time = 131;
    }



    public static boolean readToFour0x5aHeaderByte(ByteBufInputStream stream) throws IOException {
        int repeatTimes = 0;

        while (repeatTimes <= 3) {
            byte val = stream.readByte();
           // LogUtils.d("val = " + ByteUtils.byteToHex(val));
            if (!isStartHeaderByte(val)) {
                val = stream.readByte();
                continue;
            }
            val = stream.readByte();
            if (isStartHeaderByte(val)) {
                val = stream.readByte();
                if (isStartHeaderByte(val)) {
                    val = stream.readByte();
                    if (isStartHeaderByte(val)) {
                        return true;
                    } else {
                        repeatTimes++;
                    }
                } else {
                    repeatTimes++;
                }
            } else {
                repeatTimes++;
            }
        }

        return false;
    }

    private static boolean isStartHeaderByte(byte val) {
        return val >= 0 && (val & 0xFF) == 0x5A;
    }

}
