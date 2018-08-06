package com.qzy.data;

import com.qzy.intercom.util.ByteUtils;
import com.qzy.utils.LogUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import io.netty.buffer.ByteBufInputStream;

/**
 * Created by yj.zhang on 2018/8/3/003.
 */

public class PrototocalTools {

    public  static byte[] HEAD = {(byte) 0x5A,(byte) 0x5A,(byte) 0x5A,(byte) 0x5A};

    public interface IProtoServerIndex{
        int call_phone = 102;
    }

    public interface IProtoClientIndex{
        int call_phone_state = 101;
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
