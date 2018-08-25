package com.sws;

import android.util.Log;
import android.widget.Toast;

import com.qzy.WebRtc;
import com.qzy.utils.LogUtils;
import com.sws.jni.WebRtcUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * Created by yj.zhang on 2018/8/7.
 */

public class WebRtcTools {

    public static int dbValue = 20;

    private static WebRtcTools tools;

    public static WebRtcTools getInstance() {
        if (tools == null) {
            tools = new WebRtcTools();
        }
        return tools;
    }

    private WebRtcTools() {
        WebRtcUtils.webRtcAgcInit(0, 255, 8000,dbValue);
        LogUtils.e("dvValue = " + dbValue);
        WebRtcUtils.webRtcNsInit(8000);
    }

    public byte[] process(byte[] data) {

        byte[] pcmdata = null;
        try {
            ByteArrayInputStream ins = new ByteArrayInputStream(data);

            byte[] buf = new byte[320];

            while (ins.read(buf) != -1) {
                short[] shortData = new short[buf.length >> 1];

                short[] processData = new short[buf.length >> 1];
                ByteBuffer.wrap(buf).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(shortData);

                short[] nsProcessData = WebRtcUtils.webRtcNsProcess(shortData.length, shortData);
                WebRtcUtils.webRtcAgcProcess(nsProcessData, processData, nsProcessData.length);
               // WebRtcUtils.webRtcAgcProcess(shortData, processData, shortData.length);

               // short[] nsProcessData = WebRtcUtils.webRtcNsProcess(processData.length, processData);

                byte[] tmp = shortsToBytes(nsProcessData);
                if (pcmdata == null) {
                    pcmdata = Arrays.copyOf(tmp, 0);
                } else {
                    byte[] temp = new byte[pcmdata.length + tmp.length];
                    System.arraycopy(pcmdata, 0, temp, 0, pcmdata.length);
                    System.arraycopy(tmp, 0, temp, pcmdata.length, tmp.length);
                    pcmdata = temp;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Log.e("sws", "ns end======");


        return pcmdata;
    }

    private static byte[] shortsToBytes(short[] data) {
        byte[] buffer = new byte[data.length * 2];
        int shortIndex, byteIndex;
        shortIndex = byteIndex = 0;
        for (; shortIndex != data.length; ) {
            buffer[byteIndex] = (byte) (data[shortIndex] & 0x00FF);
            buffer[byteIndex + 1] = (byte) ((data[shortIndex] & 0xFF00) >> 8);
            ++shortIndex;
            byteIndex += 2;
        }
        return buffer;
    }

    public void free(){

        WebRtcUtils.webRtcNsFree();
        WebRtcUtils.webRtcAgcFree();
        tools = null;
    }

}
